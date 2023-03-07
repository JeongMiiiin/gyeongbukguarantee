package com.gyeongbuk.spring.guarantee.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Component
public class Scheduler {
	
	@Autowired
	private AligoMsgService AligoMsgService;
	
	@Autowired
	private ReserveManageService ReserveManageService; 
	
	@Autowired
	private ReserveManageListAdmService ReserveManageListAdminService;
	
	/*
	 * 1. 매일 17시에 다음날 예약자들에게 다시한번 문자 보내는 스케줄러
	*/
	@Scheduled(cron="0 0 17 * * *")
	public void sendConfirmMsg() {
		
		List<ReserveDto> result = new ArrayList<>();
		
		result = ReserveManageListAdminService.selectTomorrowReserveList();
		
		int x = 0;
		
		while( x < result.size()) {
			
			ReserveDto target = result.get(x);
			HashMap<String, Object> msgParams = new HashMap<String, Object>();
			
			String targetName = target.getRlName();
			String targetHp = target.getRlHp();
			String brName = target.getBrName();
			String brAddress = target.getBrAddress();
			String targetDate = target.getRlReserveDateStr();
			
			msgParams.put("targetName", targetName);
			msgParams.put("targetHp", targetHp);
			
			String[] targetTime = (target.getRlReserveTimeStr()).split(":");
			
			String targetMsg = targetName + "님. 안녕하세요. 경북신용보증재단입니다.\n"
					+ "요청하신 예약 방문 하루 전입니다.\n"
					+ "아래 내용을 확인하시어 예약시간 내에 방문 부탁드립니다.\n"
					+ "(예약시간 15분 지연시, 예약이 취소됩니다)\n"
					+ "- 예약지점 : " + brName + "지점\n"
					+ "(" + brAddress + ")\n"
					+ "- 예약날짜 : " + targetDate + "\n"
					+ "- 예약시간 : " + targetTime[0] + "시 " + targetTime[1] + "분\n"
					+ "· 개인사업자 : 사업자등록증, 신분증, 사업장 및 거주지 임대차 계약서(자가일시 생략)\n"
					+ "· 법인사업자 : 위 서류에 주주명부, 주식상황변동표, 재무제표, 법인인감도장, 법인인감증명서\n"
					+ "· 운수업 : 개인사업자 서류에 지입(위탁)계약서";
			
			msgParams.put("targetMsg", targetMsg);

			AligoMsgService.sendMsg(msgParams);
			
			x++;
		}
	}
	
	/*
	 * 1. 매월 1일에 휴일 리스트 가지고 와서 내부 DB 최신화
	*/
	@Scheduled(cron = "0 0 0 1 * *")
	public void refreshHoliday() {
		
		List<String> currentList = (List<String>) ReserveManageService.getHoliday().get("data");
		
		ArrayList<String> dateList = new ArrayList<>();
		
		List<String> addList = new ArrayList<>();
		
		try {
			 
			// 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
			LocalDate currentDate = LocalDate.now();
		 	 
			int currentYear = currentDate.getYear();
			int currentMonth = currentDate.getMonthValue();
		  
			for(int i=0; i < 2; i++) {
				String yearText = Integer.toString(currentYear +i);
				if(i == 1) currentMonth = 0;
		 
				while(currentMonth < 13) {
		 
					String monthText = (currentMonth > 9) ? Integer.toString(currentMonth) : "0" + Integer.toString(currentMonth);
		 
					StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /*URL*/
					urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + "kcIHcw0iI1okq8cG%2FZXFUdkMKVk04x6ARy1Z%2BQ2fvPHooUQOqJOG%2Fq2AimLYo6TgH71EtS18qeD28PgojRczQw%3D%3D"); /*Service Key*/
					urlBuilder.append("&" + URLEncoder.encode("solYear","UTF-8") +"=" + URLEncoder.encode(yearText, "UTF-8")); /*연*/ 
					urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(monthText, "UTF-8"));/* 월 */
					URL url = new URL(urlBuilder.toString());
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Content-type", "application/json");
					BufferedReader rd;
					if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
						rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					} else {
						rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
					}
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = rd.readLine()) != null) {
						sb.append(line);
					}
					rd.close();
					conn.disconnect();
		 
					String res = sb.toString();
		 
					if(res != null) {
						// xml을 파싱해주는 객체를 생성
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						factory.setIgnoringElementContentWhitespace(true); DocumentBuilder
						documentBuilder = factory.newDocumentBuilder();
		 
						InputStream is = new ByteArrayInputStream(res.getBytes());
		 
						// 파싱 시작
						Document doc = documentBuilder.parse(is);
		 
						doc.getDocumentElement().normalize();
		 
						//원하는 태그 데이터 찾아오기
						NodeList items = doc.getElementsByTagName("locdate");
						//데이터 개수 찾이오기
						int n = items.getLength();
						for (int j = 0; j < n; j++) {
							Node item = items.item(j);
							Node text = item.getFirstChild();
							String itemValue = text.getNodeValue();
		 
							dateList.add(itemValue);
						}
					}
					currentMonth++;
				}
			}
			
			//DB에 저장되어있는 리스트와 가지고 온 리스트 비교
			for(String target  : dateList) {
				if ( currentList.contains(target) ) {
					currentList.remove(target);
				} else {
					addList.add(target);
				}
			}
			
			for(String addVal : addList) {
				ReserveManageService.updateHoliday(addVal, true);
			}
			
			for(String minVal : currentList) {
				ReserveManageService.updateHoliday(minVal, false);
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		 
	}
	
}
