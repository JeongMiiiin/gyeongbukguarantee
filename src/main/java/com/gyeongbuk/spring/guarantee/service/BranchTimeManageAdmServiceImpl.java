package com.gyeongbuk.spring.guarantee.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.gyeongbuk.spring.guarantee.dao.BranchManageDao;
import com.gyeongbuk.spring.guarantee.dao.BranchTimeManageDao;
import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;
import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Service
public class BranchTimeManageAdmServiceImpl extends RootService implements BranchTimeManageAdmService {
	@Autowired
	private BranchTimeManageDao dao;
	
	@Autowired
	private BranchManageDao branchDao;
	
	@Autowired
	private ReserveManageListAdmService reserveManageListAdminService;
	
	@Override
	public String listPage(BranchDto searchDto, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();

		int userLevel = session.getAttribute("userLevel") != null ? (int) session.getAttribute("userLevel") : 9;
		
		List<BranchDto> resultList = new ArrayList<>();
		
		if( userLevel == 9) {
			int memIdx = session.getAttribute("memIdx") != null ? (int) session.getAttribute("memIdx") : -1;
			searchDto.setMemIdx(memIdx);
			
		}
		
		resultList = branchDao.selectListData(searchDto);
		
		int totalCnt = branchDao.selectTotalCnt(searchDto);
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("totCnt",totalCnt);
		
		return "admin/branchTime/list";
	};
	
	@Override
	public String updatePage(int brIdx, Model model) {
		BranchDto result = dao.selectViewData(brIdx);
		
		if(result != null ) {
			
			HashMap<String, Object> timeParams = new HashMap<String, Object>();
			timeParams.put("brIdx", brIdx);
			timeParams.put("limitCnt", 1);
			
			List<BranchTimeSettingDto> timeSettingList = new ArrayList<>();
			
			int gbtsStatus = 0;
			while(gbtsStatus < 5) {
				gbtsStatus++;
				timeParams.put("gbtsStatus", gbtsStatus);
				List<BranchTimeSettingDto> timeSetting = branchDao.selectTimeSettingList(timeParams);
				timeSettingList.add(timeSetting.get(0));
				
			}
			
			result.setBranchTimeSettingDtoList(timeSettingList);
			
			//특정일 특정시간대 세팅
			HashMap<String, Object> specficDateTimeParams = new HashMap<String, Object>();
			specficDateTimeParams.put("brIdx", brIdx);
			specficDateTimeParams.put("gbtsStatus", 7);
			
			List<BranchTimeSettingDto> specficDateTimeSettingList = branchDao.selectTimeSettingList(specficDateTimeParams);
			result.setBranchSpecficDateTimeSettingDtoList(specficDateTimeSettingList);
			
			
			HashMap<String, Object> dateParams = new HashMap<String, Object>();
			dateParams.put("brIdx", brIdx);
			dateParams.put("gbdsStatus", 2);
			
			List<BranchDateSettingDto> dateSettingList = branchDao.selectDateSettingList(dateParams);
			result.setBranchDateSettingDtoList(dateSettingList);
			
		}
		
		model.addAttribute("updateData", result);
		
		List<ReserveDto> reserveList = reserveManageListAdminService.selectReserveList(brIdx);
		
		model.addAttribute("reserveList", reserveList);
		
		return "admin/branchTime/update";
	}
	
	@Override
	@Transactional
	public void updateData( HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> params = formatJmMapRequest(request);
		
		//특정시간대와 특정일별 세팅을 위한 idx 세팅
		int brIdx = Integer.parseInt((String) params.get("brIdx"));
		
		// 특정일 특정시간대에 대한 데이터만 삭제
		HashMap<String, Object> deleteTimeSettingParams = new HashMap<>();
		deleteTimeSettingParams.put("brIdx", brIdx);
		deleteTimeSettingParams.put("gbtsStatus", 7);
		branchDao.deleteTimeSettingData(deleteTimeSettingParams);
		
		
		//시간 세팅
		/* dao.deleteTimeSettingData(br_idx); */
		
		Calendar tomorrowDateVal = Calendar.getInstance();
		tomorrowDateVal.setTime(new Date());
		tomorrowDateVal.add(Calendar.DATE, 1);
		
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String tomorrowDate = new SimpleDateFormat("yyyy-MM-dd").format(tomorrowDateVal.getTime());
		
		if( !((String) params.get("branchTimeSettingDtoList")).equals("[]") && params.get("branchTimeSettingDtoList") != null) {
			String[] branchTimeSettingDtoList = convertObjectArrayStringToStringArray((String) params.get("branchTimeSettingDtoList"));
			for(int i=0; i < branchTimeSettingDtoList.length; i++) {
				
				Map<String, String> targetVal = stringToHashMap(branchTimeSettingDtoList[i]);
				
				String startTime = targetVal.get("startTime");
				String endTime = targetVal.get("endTime");
				int gbtsStatus = Integer.parseInt( targetVal.get("gbtsStatus") );
				
				BranchTimeSettingDto targetValue = new BranchTimeSettingDto();
				targetValue.setBrIdx(brIdx);
				targetValue.setGbtsStartTime(startTime);
				targetValue.setGbtsEndTime(endTime);
				targetValue.setGbtsStatus(gbtsStatus);
				targetValue.setGbtsAppliedAt(nowDate);
				targetValue.setGbtsCounterNum("0");
				
				/* int checkTimeSetting = branchDao.checkTimeSetting(targetValue); */
				
				targetValue.setGbtsAppliedAt(tomorrowDate);
				dao.insertTimeSettingData(targetValue);
			}
		}
		
		//특정일 특정시간대 세팅
		if( !((String) params.get("branchSpecficDateTimeSettingList")).equals("[]") && params.get("branchSpecficDateTimeSettingList") != null) {
			String[] branchSpecficDateTimeSettingList = convertObjectArrayStringToStringArray((String) params.get("branchSpecficDateTimeSettingList"));
			for(int i=0; i < branchSpecficDateTimeSettingList.length; i++) {
										
				Map<String, String> targetVal = stringToHashMap(branchSpecficDateTimeSettingList[i]);
										
				String specficTargetDate = targetVal.get("selectDate");
				String startTime = targetVal.get("startTime");
				String endTime = targetVal.get("endTime");
				String counterNum = targetVal.get("counterNum");
										
				BranchTimeSettingDto targetValue = new BranchTimeSettingDto();
				targetValue.setGbtsTargetDate(specficTargetDate);
				targetValue.setBrIdx(brIdx);
				targetValue.setGbtsStatus(7);
				targetValue.setGbtsStartTime(startTime);
				targetValue.setGbtsEndTime(endTime);
				targetValue.setGbtsCounterNum(counterNum);
				targetValue.setGbtsAppliedAt(specficTargetDate);
										
				dao.insertTimeSettingData(targetValue);

			}
		}
		
		//날짜 세팅
		dao.deleteDateSettingData(brIdx);

		if( !((String) params.get("branchDateSettingDtoList")).equals("[]") && params.get("branchDateSettingDtoList") != null) {
			String[] branchDateSettingDtoList = convertObjectArrayStringToStringArray((String) params.get("branchDateSettingDtoList"));
			for(int i=0; i < branchDateSettingDtoList.length; i++) {
				
				Map<String, String> targetVal = stringToHashMap(branchDateSettingDtoList[i]);
						
				String selectDate = targetVal.get("selectDate");
						
				BranchDateSettingDto targetValue = new BranchDateSettingDto();
				targetValue.setBrIdx(brIdx);
				targetValue.setGbdsDate(selectDate);
						
				dao.insertDateSettingData(targetValue);
			}
		}
		
		boolean checkDefaultTime = checkUpdateDefaultConsultTime(params);
		
		if(checkDefaultTime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, 1);
			
			String targetDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

			params.put("targetDate", targetDate);			
			dao.insertDefaultConsultTime(params);
		}
		
		int result = dao.updateData(params);
		
		
		String msg = "";
		String targetLink = "/reserve/adm/branchTimeManage/list";
		
		if(result > 0) {
			msg = "변경이 완료되었습니다.";
		} else {
			msg = "변경에 실패했습니다. 잠시 후 다시 시도해주세요.";
		}

		makeSubmitAlertMsg(response, msg, targetLink);
		
	}
	
	public boolean checkUpdateDefaultConsultTime(Map<String, Object> params) {
		boolean result = false;
		
		String brPrevDefaultConsultTime = (String) params.get("brPrevDefaultConsultTime");
		String brDefaultConsultTime = (String) params.get("brDefaultConsultTime");
		
		if( !brPrevDefaultConsultTime.equals(brDefaultConsultTime) ) {
			result = true;
		}
		
		return result;
	}
	
}
