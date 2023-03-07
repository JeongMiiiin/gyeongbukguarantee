package com.gyeongbuk.spring.guarantee.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dao.ReserveManageDao;
import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;

@Service
public class ReserveManageServiceImpl extends RootService implements ReserveManageService {
	@Autowired
	private ReserveManageDao dao;
	
	@Autowired
	private ReserveManageListAdmService ReserveManageListAdmService;
	
	@Autowired
	private BranchManageAdmService branchManageAdmService;
	
	@Autowired
	private AligoMsgService aligoMsgService;
	
	public String mainPage(HttpServletRequest request, Model model) {
		
		BranchDto searchDto = new BranchDto(); 
		List<BranchDto> branchList = branchManageAdmService.selectListData(searchDto);
		model.addAttribute("branchList", branchList);
		
		int selectIdx = Integer.parseInt(String.valueOf(branchList.get(0).getBrIdx())); 
		
		int brIdx = (request.getParameter("br_idx") != null) ? Integer.parseInt(request.getParameter("br_idx")) : selectIdx;
		
		model.addAttribute("select_idx", brIdx);
		
		BranchDto branchData = new BranchDto();
		
		HashMap<String, Object> dateParams = new HashMap<String, Object>();
		dateParams.put("brIdx", brIdx);
		
		List<BranchDateSettingDto> branchDateSettingList = branchManageAdmService.selectDateSettingList(dateParams);
		
		branchData.setBranchDateSettingDtoList(branchDateSettingList);
		
		model.addAttribute("branchData", branchData);
		
		return "client/reserve/main";
	}

	public String settingStep(int stepIdx, HttpServletRequest request, Model model) {
		
		switch (stepIdx) {
			
			case 6 : //지점선택 페이지
				
				BranchDto searchDto = new BranchDto(); 
				List<BranchDto> branchList = branchManageAdmService.selectListData(searchDto);
				model.addAttribute("branchList", branchList);
				
				break;

			case 7 :
				
				int brIdx = Integer.parseInt( (String) (request.getParameter("br_idx") != null ? request.getParameter("br_idx") : 25) );
				
				String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				
				BranchDto branchData = branchManageAdmService.selectFrontViewData(brIdx);
				
				List<BranchTimeSettingDto> timeSettingList = new ArrayList<>();
				
				HashMap<String, Object> timeParams = new HashMap<String, Object>();
				timeParams.put("brIdx", brIdx);
				timeParams.put("gbtsStatus",6);
				timeParams.put("selectDate", nowDate);
				
				List<BranchTimeSettingDto> specficeTimeSetting = branchManageAdmService.selectTimeSettingList(timeParams);
				int i =0;
				while(i < specficeTimeSetting.size()) {
					timeSettingList.add(specficeTimeSetting.get(i));
					i++;
				}

				timeParams.put("gbtsStatus",7);
				
				List<BranchTimeSettingDto> specficeDateTimeSetting = branchManageAdmService.selectTimeSettingList(timeParams);
				
				int j = 0;
				while(j < specficeDateTimeSetting.size()) {
					timeSettingList.add(specficeDateTimeSetting.get(j));
					j++;
				}
				
				int gbts_status = 0;
				timeParams.put("limitCnt", 1);
				while(gbts_status < 5) {
					gbts_status++;
					timeParams.put("gbtsStatus", gbts_status);
					List<BranchTimeSettingDto> timeSetting = branchManageAdmService.selectTimeSettingList(timeParams);
					timeSettingList.add(timeSetting.get(0));
					
				}
				
				branchData.setBranchTimeSettingDtoList(timeSettingList);
				
				HashMap <String, Object> dateParams = new HashMap<String, Object>();
				dateParams.put("brIdx", brIdx);
				
				List<BranchDateSettingDto> branchDateSettingList = branchManageAdmService.selectDateSettingList(dateParams);
				branchData.setBranchDateSettingDtoList(branchDateSettingList);

				model.addAttribute("branchData", branchData);
				
			default : break;
		}
		
		return "client/reserve/step" + stepIdx;
	}
	
	//예약요청	
	public HashMap<String, Object> requestReserve(HashMap<String, Object> map){

		map.put("rlDirect", 1);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int checkReserve = dao.checkReserve(map);

		if(checkReserve > 0) {
			result.put("result", 2);
		} else {
			
			HashMap<String, String> checkParams = new HashMap<String, String>();
			checkParams.put("brIdx", String.valueOf(map.get("brIdx")));
			checkParams.put("rlReserveDate", String.valueOf(map.get("selectDate")));
			checkParams.put("rlReserveTime", String.valueOf(map.get("targetTime")));
			boolean checkPossible = ReserveManageListAdmService.checkPossibleReserve(checkParams);
			
			if(checkPossible) {
				int success = dao.requestReserve(map);

				if(success > 0) {
					
					int brIdx = Integer.parseInt(String.valueOf(map.get("brIdx")));
					String targetDate = (String) map.get("selectDate");
					
					boolean checkDateFullReserve = branchManageAdmService.checkDateFullReserve(brIdx, targetDate);
					
					//해당 날짜가 예약이 꽉찼을 때
					if(checkDateFullReserve) {
						HashMap<String, Object> params = new HashMap<String, Object>();
						
						params.put("brIdx", brIdx);
						params.put("selectDate", targetDate);
						
						ReserveManageListAdmService.updateFullReserve(params);
					}
					
					HashMap<String, Object> msgParams = new HashMap<String, Object>();
					
					String targetName = (String) map.get("rlName");
					String targetHp = (String) map.get("rlHp");
					
					String[] targetTime = ((String) map.get("targetTime")).split(":");
					
					String targetMsg = targetName + "님. 안녕하세요. 경북신용보증재단입니다.\n"
							+ "고객님께서 요청하신 상담예약이 완료되었습니다.\n"
							+ "아래 내용을 확인하시어 예약시간 내에 방문 부탁드립니다.\n"
							+ "※ 예약시간 15분 지연시, 예약이 취소됩니다.\n"
							+ "※ 예약 후, 상담일에 미방문시 패널티가 부과되니 필히 예약 취소를 하시기 바랍니다.\n"
							+ "\n"
							+ "\n"
							+ "- 예약지점 : " + map.get("brName") + "지점\n" 
							+ "(" + map.get("brAddress") + ")\n"
							+ "- 예약날짜 : " + map.get("selectDate") + "\n"
							+ "- 예약시간 : " + targetTime[0] + "시 " + targetTime[1] + "분\n"
							+ "- 필요서류\n"
							+ "· 개인사업자 : 사업자등록증, 신분증, 사업장 및 거주지 임대차 계약서(자가일시 생략)\n"
							+ "· 법인사업자 : 개인사업자 서류에 주주명부, 주식상황변동표, 재무제표, 법인인감도장, 법인인감증명서, 정관\n"
							+ "· 운수업 : 개인사업자 서류에 지입(위탁)계약서\n"
							+ "\n"
							+ "경북신용보증재단 보증사업부";
					
					msgParams.put("targetName", targetName);
					msgParams.put("targetHp", targetHp);
					
					msgParams.put("targetMsg", targetMsg);
					
					int msgSuccess = aligoMsgService.sendMsg(msgParams);
					
					if(msgSuccess > 0) {
						result.put("result", 1);
					} else {
						result.put("result", 3);
					}
					
				} else {
					result.put("result", 0);
				}
			} else {
				result.put("result", 3);
			}

		}
		
		return result;
	};
	
	//예약요청	 (API로)
	public HashMap<String, Object> requestReserveAPI(HashMap<String, Object> map){
			
		HashMap<String, Object> result = new HashMap<String, Object>();
			
		int checkReserve = dao.checkReserve(map);

		if(checkReserve > 0) {
			result.put("result", 2);
		} else {
			map.put("rl_direct", 1);
			
			int success = dao.requestReserve(map);

			if(success > 0) {
					
				int brIdx = Integer.parseInt(String.valueOf(map.get("brIdx")));
				String targetDate = (String) map.get("selectDate");
					
				boolean checkDateFullReserve = branchManageAdmService.checkDateFullReserve(brIdx, targetDate);
					
				//해당 날짜가 예약이 꽉찼을 때
				if(checkDateFullReserve) {
					HashMap<String, Object> params = new HashMap<String, Object>();
						
					params.put("br_idx", brIdx);
					params.put("select_date", targetDate);
					
					ReserveManageListAdmService.updateFullReserve(params);
				}
					
				HashMap<String, Object> msgParams = new HashMap<String, Object>();
					
				String targetName = (String) map.get("rlName");
				String targetHp = (String) map.get("rlHp");
					
				String[] targetTime = ((String) map.get("targetTime")).split(":");
					
				String targetMsg = targetName + "님. 안녕하세요. 경북신용보증재단입니다.\n"
						+ "고객님께서 요청하신 상담예약이 완료되었습니다.\n"
						+ "아래 내용을 확인하시어 예약시간 내에 방문 부탁드립니다.\n"
						+ "※ 예약시간 15분 지연시, 예약이 취소됩니다.\n"
						+ "※ 예약 후, 상담일에 미방문시 패널티가 부과되니 필히 예약 취소를 하시기 바랍니다.\n"
						+ "\n"
						+ "\n"
						+ "- 예약지점 : " + map.get("brName") + "지점\n" 
						+ "(" + map.get("brAddress") + ")\n"
						+ "- 예약날짜 : " + map.get("selectDate") + "\n"
						+ "- 예약시간 : " + targetTime[0] + "시 " + targetTime[1] + "분\n"
						+ "- 필요서류\n"
						+ "· 개인사업자 : 사업자등록증, 신분증, 사업장 및 거주지 임대차 계약서(자가일시 생략)\n"
						+ "· 법인사업자 : 개인사업자 서류에 주주명부, 주식상황변동표, 재무제표, 법인인감도장, 법인인감증명서, 정관\n"
						+ "· 운수업 : 개인사업자 서류에 지입(위탁)계약서\n"
						+ "\n"
						+ "경북신용보증재단 보증사업부";
				
				msgParams.put("targetName", targetName);
				msgParams.put("targetHp", targetHp);
					
				msgParams.put("targetMsg", targetMsg);
					
				int msgSuccess = aligoMsgService.sendMsg(msgParams);
					
				if(msgSuccess > 0) {
					result.put("result", 1);
				} else {
					result.put("result", 3);
				}
					
			} else {
				result.put("result", 0);
			}
		}
			
		return result;
	};
	
	
	//전광판 페이지 데이터 세팅
	public String billboardPage(HttpServletRequest request, Model model) {
		
		int brIdx = Integer.parseInt( (String) (request.getParameter("br_idx") != null ? request.getParameter("br_idx") : 25) );
		
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		BranchDto branchData = branchManageAdmService.selectFrontViewData(brIdx);
		
		List<BranchTimeSettingDto> timeSettingList = new ArrayList<>();
		
		HashMap <String, Object> timeParams = new HashMap<String, Object>();
		timeParams.put("brIdx", brIdx);
		timeParams.put("gbtsStatus",6);
		timeParams.put("selectDate", nowDate);
		
		List<BranchTimeSettingDto> specficeTimeSetting = branchManageAdmService.selectTimeSettingList(timeParams);
		int i =0;
		while(i < specficeTimeSetting.size()) {
			timeSettingList.add(specficeTimeSetting.get(i));
			i++;
		}

		timeParams.put("gbtsStatus",7);
		
		List<BranchTimeSettingDto> specficeDateTimeSetting = branchManageAdmService.selectTimeSettingList(timeParams);
		
		int j = 0;
		while(j < specficeDateTimeSetting.size()) {
			timeSettingList.add(specficeDateTimeSetting.get(j));
			j++;
		}
		
		int gbts_status = 0;
		timeParams.put("limitCnt", 1);
		while(gbts_status < 5) {
			gbts_status++;
			timeParams.put("gbtsStatus", gbts_status);
			List<BranchTimeSettingDto> timeSetting = branchManageAdmService.selectTimeSettingList(timeParams);
			timeSettingList.add(timeSetting.get(0));
			
		}
		
		branchData.setBranchTimeSettingDtoList(timeSettingList);
		
		HashMap<String, Object> dateParams = new HashMap<String, Object>();
		dateParams.put("brIdx", brIdx);
		
		List<BranchDateSettingDto> branchDateSettingList = branchManageAdmService.selectDateSettingList(dateParams);
		branchData.setBranchDateSettingDtoList(branchDateSettingList);
		
		String targetDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		HashMap<String, Object> consultNumParams = new HashMap<String, Object>(); 
		
		consultNumParams.put("brIdx", brIdx);
		consultNumParams.put("selectDate", targetDate);
		
		HashMap<String,Object> consultNum = branchManageAdmService.selectTargetConsultNum(consultNumParams);
		
		if(consultNum != null) {
			//default...
			//branchData.setBrDefaultCounterNum((String) consultNum.get("gbds_counter_num"));
			
			//220722 by ban
			 branchData.setBrDefaultCounterNum(String.valueOf(consultNum.get("gbds_counter_num")));
			//220722 by ban
		}
		
		model.addAttribute("branchData", branchData);
		
		List<HashMap<String, Object>> reserveList = dao.selectBillBoardContent(brIdx);
		model.addAttribute("reserveList", reserveList);
		
		return "client/reserve/billboard";
	};
	
	public Map<String, Object> selectBillboardReserveList(HashMap<String, Object> map){
		Map<String, Object> result = new HashMap<String, Object>();
		
		int brIdx = Integer.parseInt(String.valueOf(map.get("brIdx")));
		
		List<HashMap<String, Object>> reserveList = dao.selectBillBoardContent(brIdx);
		
		if(reserveList.size() > 0) {
			result.put("result", true);
			
			for(int i=0; i < reserveList.size(); i++) {
				HashMap<String, Object> target = reserveList.get(i);
				target.remove("br_idx");
				target.remove("rl_business_type");
				target.remove("rl_created_at");
				target.remove("rl_direct");
				target.remove("rl_hp");
				target.remove("rl_reserve_date");
				target.remove("rl_reserve_date_str");
				
				target.put("rl_reserve_time", target.get("rl_reserve_time_str"));
				
				reserveList.set(i, target);
			}
			
			result.put("data", reserveList);
			
		} else {
			result.put("result", false);
		}
		
		return result;
		
	}
	
	@Override
	public Map<String,Object> updateAudio(HashMap<String, Object> map){
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		int data = dao.updateAudio(map);
		if(data >= 0) {
			result.put("result", true);
		} else {
			result.put("result", false);
		}
		
		return result;
	}
	
	@Override
	public Map<String,Object> getHoliday(){
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<String> dateList = dao.getHoliday();
		
		result.put("result", true);
		result.put("data", dateList);
		
		return result;
	};
	
	@Override
	public void updateHoliday(String params, boolean status) {
		if(status) {
			dao.addHoliday(params);
		} else {
			dao.removeHoliday(params);
		}
	};
	
}