package com.gyeongbuk.spring.guarantee.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dao.ReserveManageListDao;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.Pagination;
import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Service
public class ReserveManageListAdmServiceImpl extends RootService implements ReserveManageListAdmService {
	@Autowired
	private ReserveManageListDao dao;
	
	@Autowired
	private TimeControlService TimeControl;
	
	@Autowired
	private BranchManageAdmService BranchManageAdmService;
	
	@Autowired
	private AligoMsgService aligoMsgService; 
	
	@Override
	public String listPage(ReserveDto searchDto, HttpServletRequest request , Model model) {

		Pagination pagination = new Pagination();
		pagination.setCurrentPageNo(searchDto.getPageIndex());
		pagination.setRecordCountPerPage(100);
		pagination.setPageSize(searchDto.getPageSize());
		
		searchDto.setFirstIndex(pagination.getFirstRecordIndex());
		searchDto.setRecordCountPerPage(100);
		
		List<ReserveDto> result = new ArrayList<>();
		int totalCnt = 0;
		
		HttpSession session = request.getSession();
		
		BranchDto branchDto = new BranchDto(); 
		
		int userLevel = session.getAttribute("userLevel") != null ? (int) session.getAttribute("userLevel") : 9;
		
		if(userLevel == 9) {
			int memIdx = session.getAttribute("memIdx") != null ? (int) session.getAttribute("memIdx") : -1;
			
			searchDto.setSearchMemIdx(memIdx);
			branchDto.setMemIdx(memIdx);
		}
		
		result = dao.selectListData(searchDto);
		totalCnt = dao.selectTotalCnt(searchDto);
		
		List<BranchDto> branchList = BranchManageAdmService.selectListData(branchDto);
		
		pagination.setTotalRecordCount(totalCnt);

		searchDto.setEndDate(pagination.getLastPageNoOnPageList());
		searchDto.setStartDate(pagination.getFirstPageNoOnPageList());
		searchDto.setPrev(pagination.getXprev());
		searchDto.setNext(pagination.getXnext());
		searchDto.setRealEnd(pagination.getRealEnd());
		
		model.addAttribute("branchList", branchList);
		model.addAttribute("resultList", result);
		model.addAttribute("totCnt",totalCnt);
		model.addAttribute("totalPageCnt",(int)Math.ceil(totalCnt / (double)searchDto.getPageUnit()));
		model.addAttribute("pagination",pagination);
		
		try {
			searchDto.setQustr();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "admin/reserveManageList/list";
		
	};
	
	//해당 날짜에 스케줄 확인
	@Override
	public HashMap<String, Object> selectDateTimeList(HashMap<String, Object> params) {
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			
			List<HashMap<String, Object>> data = dao.selectDateTimeList(params);
			HashMap<String,Object> branchDateData = BranchManageAdmService.selectTargetDateData(params);
			
			result.put("result", true);
			result.put("data", data);
			result.put("consultTime", branchDateData.get("consultTime"));
			result.put("consultNum", branchDateData.get("consultNum"));
			result.put("operationTime", branchDateData.get("operationTime"));
			result.put("specialTimeList", branchDateData.get("specialTimeList"));
			
			return result;
			
	};
	
	@Override
	public List<HashMap<String, Object>> selectBranchDateReserveList(HashMap<String, Object> params){return dao.selectDateTimeList(params);};
	
	@Override
	public String insertPage(ReserveDto searchDto, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		
		BranchDto branchDto = new BranchDto();
		
		int userLevel = session.getAttribute("userLevel") != null ? (int) session.getAttribute("userLevel") : 9;

		if(userLevel == 9) {
			int memIdx = session.getAttribute("memIdx") != null ? (int) session.getAttribute("memIdx") : -1;
			branchDto.setMemIdx(memIdx);
		}
		
		List<BranchDto> branchList = BranchManageAdmService.selectListData(branchDto);
		
		model.addAttribute("branchList", branchList);
		
		try {
			searchDto.setQustr();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "admin/reserveManageList/insert";
	}
	
	@Override
	@Transactional
	public void insertData( ReserveDto searchDto, HttpServletResponse response) {
		
		String msg = "등록이 완료되었습니다.";
		String targetLink = "/reserve/adm/reserveManageList/list";
		
		HashMap<String, String> checkParams = new HashMap<String, String>();
		checkParams.put("brIdx", String.valueOf(searchDto.getBrIdx()));
		checkParams.put("rlReserveDate", searchDto.getRlReserveDate());
		checkParams.put("rlReserveTime", searchDto.getRlReserveTime());
		boolean checkPossible = checkPossibleReserve(checkParams);
		
		if(checkPossible) {
			dao.insertData(searchDto);
			
			int brIdx = searchDto.getBrIdx();
			String targetDate = searchDto.getRlReserveDate();
			
			boolean checkDateFullReserve = BranchManageAdmService.checkDateFullReserve(brIdx, targetDate);
			
			//해당 날짜가 예약이 꽉찼을 때
			if(checkDateFullReserve) {
				HashMap<String, Object> params = new HashMap<String, Object>();
				
				params.put("brIdx", brIdx);
				params.put("selectDate", targetDate);
				
				dao.updateFullReserve(params);
			}
			
			HashMap<String, Object> msgParams = new HashMap<String, Object>();
			
			BranchDto branchData = BranchManageAdmService.selectFrontViewData(brIdx);
			
			String branchName = branchData.getBrName();
			String branchAddress = branchData.getBrAddress();
			
			searchDto.setBrName(branchName);
			searchDto.setBrAddress(branchAddress);
			
			
			msgParams.put("targetName", searchDto.getRlName());
			msgParams.put("targetHp", searchDto.getRlHp());
			
			String[] targetTime = (searchDto.getRlReserveTime()).split(":");
			
			String targetMsg = searchDto.getRlName() + "님. 안녕하세요. 경북신용보증재단입니다.\n"
					+ "요청하신 상담예약이 완료되었습니다.\n"
					+ "아래 내용을 확인하시어 예약시간 내에 방문 부탁드립니다.\n"
					+ "※ 예약시간 15분 지연시, 예약이 취소됩니다.\n"
					+ "※ 예약 후, 상담일에 미방문시 패널티가 부과되니 필히 예약 취소를 하시기 바랍니다.\n"
					+ "\n"
					+ "\n"
					+ "- 예약지점 : " + searchDto.getBrName() + "지점\n"
					+ "(" + searchDto.getBrAddress() + ")\n"
					+ "- 예약날짜 : " + searchDto.getRlReserveDate() + "\n"
					+ "- 예약시간 : " + targetTime[0] + "시 " + targetTime[1] + "분\n"
					+ "- 필요서류\n"
					+ "· 개인사업자 : 사업자등록증, 신분증, 사업장 및 거주지 임대차 계약서(자가일시 생략)\n"
					+ "· 법인사업자 : 개인사업자 서류에 주주명부, 주식상황변동표, 재무제표, 법인인감도장, 법인인감증명서, 정관\n"
					+ "· 운수업 : 개인사업자 서류에 지입(위탁)계약서\n"
					+ "\n"
					+ "경북신용보증재단 보증사업부";
			
			msgParams.put("targetMsg", targetMsg);

			aligoMsgService.sendMsg(msgParams);
			
			
			try {
				searchDto.setQustr();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			msg = "해당 시간은 예약이 마감되었습니다. 잠시 후 다시 시도해주세요.";
			targetLink = "/reserve/adm/reserveManageList/insert";
		}

		makeSubmitAlertMsg(response, msg, targetLink);
		
	}
	
	@Override
	public String updatePage(ReserveDto searchDto, int rlIdx, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		
		BranchDto branchDto = new BranchDto();
		
		int userLevel = session.getAttribute("userLevel") != null ? (int) session.getAttribute("userLevel") : 9;

		if(userLevel == 9) {
			int memIdx = session.getAttribute("memIdx") != null ? (int) session.getAttribute("memIdx") : -1;
			branchDto.setMemIdx(memIdx);
		}
		
		List<BranchDto> branchList = BranchManageAdmService.selectListData(branchDto);
		
		model.addAttribute("branchList", branchList);
		
		ReserveDto viewData = dao.selectViewData(rlIdx);
		model.addAttribute("updateData", viewData);
		
		try {
			searchDto.setQustr();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "admin/reserveManageList/update";
	}
	
	@Override
	@Transactional
	public void updateData( ReserveDto searchDto, HttpServletResponse response) {
		
		String msg = "변경이 완료되었습니다.";
		String targetLink = "/reserve/adm/reserveManageList/list";
		
		HashMap<String, String> checkParams = new HashMap<String, String>();
		checkParams.put("brIdx", String.valueOf(searchDto.getBrIdx()));
		checkParams.put("rlReserveDate", searchDto.getRlReserveDate());
		checkParams.put("rlReserveTime", searchDto.getRlReserveTime());
		boolean checkPossible = checkPossibleReserve(checkParams);
		
		if(checkPossible) {
			dao.updateData(searchDto);
			
			int brIdx = searchDto.getBrIdx();
			String targetDate = searchDto.getRlReserveDate();
			
			String prevDate = searchDto.getRlPrevReserveDate();
			
			if( !prevDate.equals(targetDate) ) {
				HashMap<String, Object> prevParams = new HashMap<String, Object>();
				prevParams.put("brIdx", brIdx);
				prevParams.put("selectDate", prevDate);

				dao.removeFullReserve(prevParams);
			}
			
			boolean checkDateFullReserve = BranchManageAdmService.checkDateFullReserve(brIdx, targetDate);
			
			//해당 날짜가 예약이 꽉찼을 때
			if(checkDateFullReserve) {
				HashMap<String, Object> params = new HashMap<String, Object>();
				
				params.put("brIdx", brIdx);
				params.put("selectDate", targetDate);
				
				dao.updateFullReserve(params);
			}
			
			if( (searchDto.getPrevRlReserveDate() != searchDto.getRlReserveDate()) || (searchDto.getPrevRlReserveTime() != searchDto.getRlReserveTime()) ) {
				HashMap<String, Object> msgParams = new HashMap<String, Object>();
				
				msgParams.put("targetName", searchDto.getRlName());
				msgParams.put("targetHp", searchDto.getRlHp());
				
				String[] targetTime = (searchDto.getRlReserveTime()).split(":");
				
				BranchDto branchData = BranchManageAdmService.selectFrontViewData(brIdx);
				
				String branchName = branchData.getBrName();
				String branchAddress = branchData.getBrAddress();
				
				searchDto.setBrName(branchName);
				searchDto.setBrAddress(branchAddress);
				
				String targetMsg = searchDto.getRlName() + "님. 안녕하세요. 경북신용보증재단입니다.\n"
						+ "요청하신 상담예약이 관리자에 의해 변경되었습니다.\n"
						+ "아래 내용을 확인하시어 예약시간 내에 방문 부탁드립니다.\n"
						+ "(예약시간 15분 지연시, 예약이 취소됩니다)\n"
						+ "- 예약지점 : " + searchDto.getBrName() + "지점\n"
						+ "(" + searchDto.getBrAddress() + ")\n"
						+ "- 예약날짜 : " + searchDto.getRlReserveDate() + "\n"
						+ "- 예약시간 : " + targetTime[0] + "시 " + targetTime[1] + "분\n"
						+ "- 필요서류\n"
						+ "· 개인사업자 : 사업자등록증, 신분증, 사업장 및 거주지 임대차 계약서(자가일시 생략)\n"
						+ "· 법인사업자 : 개인사업자 서류에 주주명부, 주식상황변동표, 재무제표, 법인인감도장, 법인인감증명서, 정관\n"
						+ "· 운수업 : 개인사업자 서류에 지입(위탁)계약서"
						+ "\n"
						+ "경북신용보증재단 보증사업부";
				
				
				msgParams.put("targetMsg", targetMsg);

				aligoMsgService.sendMsg(msgParams);
			}
			
			try {
				searchDto.setQustr();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			msg = "해당 시간은 예약이 마감되었습니다. 잠시 후 다시 시도해주세요.";
			targetLink = "/reserve/adm/reserveManageList/update?rl_idx=" + searchDto.getRlIdx();
		}

		makeSubmitAlertMsg(response, msg, targetLink);

	}
	
	@Override
	public void deleteData( int rlIdx, HttpServletResponse response ) {
		
		ReserveDto reserveData = dao.selectViewData(rlIdx);
		
		int brIdx = reserveData.getBrIdx();
		String selectDate = reserveData.getRlReserveDateStr(); 

		int result = dao.deleteData(rlIdx);
		
		String msg = "삭제가 완료되었습니다.";
		String targetLink = "/reserve/adm/reserveManageList/list";
		
		if(result > 0) {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("brIdx", brIdx);
			params.put("selectDate", selectDate);
			
			dao.removeFullReserve(params);
		} else {
			msg = "삭제에 실패했습니다. 잠시 후 다시 시도해주세요.";
		}
		makeSubmitAlertMsg(response, msg, targetLink);
	}
	
	@Override
	@Transactional
	public HashMap<String, Object> updateReserve( HashMap<String, Object> map ) {
		
		Map<String, Object> params = map;

		int data = dao.updateReserve(params);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if (data == 1) {
			result.put("result", true);
		} else {
			result.put("result", false);
		}
		
		return result;
		
	}
	
	
	@Override
	@Transactional
	public HashMap<String, Object> deleteReserve( HashMap<String, Object> map ) {
		
		Map<String, Object> params = map;
		
		int rlIdx = Integer.parseInt(String.valueOf(params.get("rlIdx")));
		
		int data = dao.deleteData(rlIdx);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if (data == 1) {
			
			dao.removeFullReserve(params);
			
			result.put("result", true);
			
		} else {
						
			result.put("result", false);
		}
		
		return result;
		
	}
	
	@Override
	public void updateFullReserve(HashMap<String, Object> params) {
		dao.updateFullReserve(params);
	};
	@Override
	public void removeFullReserve(HashMap<String, Object> params) {
		dao.removeFullReserve(params);
	};
	
	@Override
	public int branchCheckReserve(int brIdx) {
		return dao.branchCheckReserve(brIdx);
	}
	
	@Override
	public List<ReserveDto> selectTomorrowReserveList(){
		return dao.selectTomorrowReserveList();
	}
	
	@Override
	public List<ReserveDto> selectReserveList(int brIdx){
		return dao.selectReserveList(brIdx);
	}
	
	//해당 날짜에 스케줄 확인
	@Override
	public List<HashMap<String, Object>> reserveListAPI(HashMap<String, Object> params) {			
		return dao.reserveListAPI(params);
	}
	
	/*
	 * params : brIdx : 지점 고유번호, rlReserveDate : 예약날짜, rlReserveTime : 예약시간
	 * result : true : 예약가능, false : 예약불가능
	 * 해당 시간에 예약이 가능한지 한번 더 확인하는 함수
	 * 
	 */
	@Override
	public boolean checkPossibleReserve(HashMap<String, String> params) {
		boolean result = false;
		
		int checkCnt = dao.selectTargetReserve(params);
		
		//해당 시간에 예약이 있을 때
		if(checkCnt > 0) {
			
			
			HashMap<String, Object> consultNumParams = new HashMap<String, Object>();
			
			consultNumParams.put("brIdx", params.get("brIdx"));
			consultNumParams.put("selectDate", params.get("rlReserveDate"));
			
			HashMap<String, Object> consultDefaultNum = BranchManageAdmService.selectTargetConsultDefaultNum(consultNumParams);
			HashMap<String, Object> consultTargetNum = BranchManageAdmService.selectTargetConsultNum(consultNumParams);

			int defaultConsultNum = 0;
			
			if (consultDefaultNum != null && consultTargetNum != null) {
				int defaultNum = (int) consultDefaultNum.get("br_default_counter_num");
				int targetNum = (int) consultTargetNum.get("gbds_counter_num");
				defaultConsultNum = defaultNum < targetNum ? defaultNum : targetNum;

			} else if (consultDefaultNum != null) {
				defaultConsultNum = (int) consultDefaultNum.get("br_default_counter_num");
			} else if (consultTargetNum != null) {
				defaultConsultNum = (int) consultTargetNum.get("gbds_counter_num");
			}
			
			String targetTime = params.get("rlReserveTime");
			
			HashMap<String, String> privateTimeSettingParams = new HashMap<String, String>();
			privateTimeSettingParams.put("brIdx", params.get("brIdx"));
			privateTimeSettingParams.put("selectDate", params.get("rlReserveDate"));
			privateTimeSettingParams.put("gbtsStatus", "6");
			
			List<HashMap<String, String>> privateTimeSettingList = BranchManageAdmService.selectTargetPrivateTimeSettingList(privateTimeSettingParams);
			
			for(int i=0; i < privateTimeSettingList.size(); i++) {
				HashMap<String, String> target = privateTimeSettingList.get(i);
				String startTime = target.get("gbts_start_time_str");
				String endTime = target.get("gbts_end_time_str");
				
				try {
					if( (TimeControl.compareTime(targetTime, startTime) >= 0) && (TimeControl.compareTime(targetTime, endTime) <= 0) ) {
						defaultConsultNum = Integer.parseInt(String.valueOf(target.get("gbts_counter_num")));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			privateTimeSettingParams.put("gbtsStatus", "7");
			privateTimeSettingParams.put("targetDate", params.get("rlReserveDate"));
			
			List<HashMap<String, String>> privateDateTimeSettingList = BranchManageAdmService.selectTargetPrivateTimeSettingList(privateTimeSettingParams);
			
			for(int i=0; i < privateDateTimeSettingList.size(); i++) {
				HashMap<String, String> target = privateDateTimeSettingList.get(i);
				String startTime = target.get("gbts_start_time_str");
				String endTime = target.get("gbts_end_time_str");
				
				try {
					if(TimeControl.compareTime(targetTime, startTime) >= 0 && TimeControl.compareTime(targetTime, endTime) <= 0) {
						defaultConsultNum = Integer.parseInt(String.valueOf(target.get("gbts_counter_num")));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(checkCnt < defaultConsultNum) {
				result = true;
			}
			
		} else {
			result = true;
		}
		
		return result;
	};
	
}
