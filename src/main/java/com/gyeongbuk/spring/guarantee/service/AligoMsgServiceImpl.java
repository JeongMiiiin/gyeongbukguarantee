package com.gyeongbuk.spring.guarantee.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

@Service
public class AligoMsgServiceImpl implements AligoMsgService {
	
	/* 메세지 API 호출
	 * params : targetName -> 수신인명, targetHp -> 수신번호, targetMsg -> 보낼 내용
		*/
	@Override
	public int sendMsg(HashMap<String, Object> params) {
		int result = 0;
		
		try{
			
			final String encodingType = "utf-8";
			final String boundary = "____boundary____";
		
			/**************** 문자전송하기 예제 ******************/
			/* "result_code":결과코드,"message":결과문구, */
			/* "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 */
			/* 동일내용 > 전송용 입니다.  
			/******************** 인증정보 ********************/
			String sms_url = "https://apis.aligo.in/send/"; // 전송요청 URL
			
			Map<String, String> sms = new HashMap<String, String>();
			
			sms.put("user_id", "gbsinbo"); // SMS 아이디
			sms.put("key", "myg9w3n1x0wdqb1fic1cmcv0s157jz0n"); //인증키
			
			/******************** 인증정보 ********************/
			
			/******************** 전송정보 ********************/
			
			String user_name = (String) params.get("targetName");
			String user_phone = (String) params.get("targetHp");

			String destination = user_name + "|" + user_phone;
			
			String msg = (String) params.get("targetMsg");
			
			sms.put("msg", msg); // 메세지 내용
			sms.put("receiver", user_phone); // 수신번호
			sms.put("destination", destination); // 수신인 %고객명% 치환
			sms.put("sender", "15887679"); // 발신번호
			sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
			sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
			sms.put("testmode_yn", "N"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
			sms.put("title", msg); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)
			
			String image = "";
			//image = "/tmp/pic_57f358af08cf7_sms_.jpg"; // MMS 이미지 파일 위치
			
			/******************** 전송정보 ********************/
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			
			builder.setBoundary(boundary);
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setCharset(Charset.forName(encodingType));
			
			for(Iterator<String> i = sms.keySet().iterator(); i.hasNext();){
				String key = i.next();
				builder.addTextBody(key, sms.get(key)
						, ContentType.create("Multipart/related", encodingType));
			}
			
			File imageFile = new File(image);
			if(image!=null && image.length()>0 && imageFile.exists()){
		
				builder.addPart("image",
						new FileBody(imageFile, ContentType.create("application/octet-stream"),
								URLEncoder.encode(imageFile.getName(), encodingType)));
			}
			
			HttpEntity entity = builder.build();
			
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(sms_url);
			post.setEntity(entity);
			
			HttpResponse res = client.execute(post);
			
			String msgResult = "";
			if(res != null){
				
				BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
				String buffer = null;
				while((buffer = in.readLine())!=null){
					msgResult += buffer;
				}
				
				in.close();
			}
			
			System.out.println(msgResult);
			
			result = 1;
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		return result;
	}
}
