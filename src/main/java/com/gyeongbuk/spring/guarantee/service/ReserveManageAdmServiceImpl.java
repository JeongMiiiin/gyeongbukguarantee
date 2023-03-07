package com.gyeongbuk.spring.guarantee.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gyeongbuk.spring.guarantee.dao.ReserveManageListDao;
import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;
import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Service
public class ReserveManageAdmServiceImpl extends RootService implements ReserveManageAdmService {
	
	@Autowired
	private AligoMsgService aligoMsgService;
	
	@Autowired
	private ReserveManageListAdmService ReserveManageListAdmService;
	
	@Autowired
	private BranchManageAdmService branchManageAdmService;
	
	@Autowired
	private ReserveManageListDao dao;
	
	@Override
	public String branchListPage(HttpServletRequest request, BranchDto searchDto, Model model) {
		HttpSession session = request.getSession();
		
		int userLevel = session.getAttribute("userLevel") != null ? (int) session.getAttribute("userLevel") : 9;
		
		if( userLevel == 9) {
			int memIdx =  session.getAttribute("memIdx") !=null ? (int) session.getAttribute("memIdx") : -1 ;
			searchDto.setMemIdx(memIdx);
		}
		
		List<BranchDto> resultList = branchManageAdmService.selectListData(searchDto);
		
		model.addAttribute("resultList", resultList);
		
		return "admin/reserveManage/branchList";
	};
	
	@Override
	public String listPage(ReserveDto searchDto, int brIdx, Model model) {
		BranchDto branchData = branchManageAdmService.selectViewData(brIdx);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		List<BranchTimeSettingDto> timeSettingList = new ArrayList<>();
		
		params.put("brIdx", brIdx);
		
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = formatter.format(now);
		
		params.put("selectDate", nowDate);
		
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
		
		List<BranchDateSettingDto> branchDateSettingList = branchManageAdmService.selectDateSettingList(params);

		branchData.setBranchDateSettingDtoList(branchDateSettingList);
		
		model.addAttribute("branchData", branchData);
		
		return "admin/reserveManage/list";
	}
	
	@Override
	public String insertPage(ReserveDto searchDto, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		
		BranchDto branchDto = new BranchDto();
		
		int userLevel = session.getAttribute("userLevel") != null ? (int) session.getAttribute("userLevel") : 9;
		
		if(userLevel == 9) {
			int memIdx = session.getAttribute("memIdx") != null ? (int) session.getAttribute("memIdx") : -1;
			branchDto.setMemIdx(memIdx);
		}
		
		List<BranchDto> branchList = branchManageAdmService.selectListData(branchDto);
		
		model.addAttribute("branchList", branchList);
		
		try {
			searchDto.setQustr();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "admin/reserveManage/insert";
	};
	
	//등록
	@Override
	public void insertData(@ModelAttribute("searchDto") ReserveDto searchDto, HttpServletResponse response) {
		
		String msg = "등록이 완료되었습니다.";
		String targetLink = "/reserve/adm/reserveManage/list?br_idx=" + searchDto.getBrIdx();
		
		HashMap<String, String> checkParams = new HashMap<String, String>();
		checkParams.put("brIdx", String.valueOf(searchDto.getBrIdx()));
		checkParams.put("rlReserveDate", searchDto.getRlReserveDate());
		checkParams.put("rlReserveTime", searchDto.getRlReserveTime());
		boolean checkPossible = ReserveManageListAdmService.checkPossibleReserve(checkParams);
		
		if(checkPossible) {
			dao.insertData(searchDto);
			
			int brIdx = searchDto.getBrIdx();
			String targetDate = searchDto.getRlReserveDate();
			
			boolean checkDateFullReserve = branchManageAdmService.checkDateFullReserve(brIdx, targetDate);
			
			//해당 날짜가 예약이 꽉찼을 때
			if(checkDateFullReserve) {
				HashMap<String, Object> params = new HashMap<String, Object>();
				
				params.put("brIdx", brIdx);
				params.put("selectDate", targetDate); 
				
				dao.updateFullReserve(params);
			}
			
			HashMap<String, Object> msgParams = new HashMap<String, Object>();
			
			BranchDto branchData = branchManageAdmService.selectFrontViewData(brIdx);
			
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
			targetLink = "/reserve/adm/reserveManage/insert?br_idx=" + searchDto.getBrIdx();
		}
		
		makeSubmitAlertMsg(response, msg, targetLink);
		
		
	};
	
	@Override
	public Map<String,Object> updateAudio(HashMap<String, Object> map){
		Map<String,Object> result = new HashMap<String, Object>();
		
		Integer resultCnt = dao.updateAudio(map);
		
		result.put("result", resultCnt);
		
		return result;
	};
	
}
