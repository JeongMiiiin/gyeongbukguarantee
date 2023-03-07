package com.gyeongbuk.spring.guarantee.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dao.BranchManageDao;
import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;
import com.gyeongbuk.spring.guarantee.dto.MemberAdmDto;
import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Service
public class BranchManageAdmServiceImpl extends RootService implements BranchManageAdmService {
	@Autowired
	private BranchManageDao dao;

	@Autowired
	private MemberAdmManageService memberAdminManageService;

	@Autowired
	private ReserveManageListAdmService reserveManageListAdminService;

	private int defaultConsultNum;

	private List<BranchTimeSettingDto> privateTimeSettingList;
	private List<BranchTimeSettingDto> privateDateTimeSettingList;

	@Override
	public BranchDto selectViewData(int br_idx) {
		return dao.selectViewData(br_idx);
	}

	@Override
	public List<BranchTimeSettingDto> selectTimeSettingList(HashMap<String, Object> params) {
		return dao.selectTimeSettingList(params);
	};

	@Override
	public List<BranchDateSettingDto> selectDateSettingList(HashMap<String, Object> params) {
		return dao.selectDateSettingList(params);
	};

	@Override
	public String listPage(BranchDto searchDto, Model model) {
		List<BranchDto> result = selectListData(searchDto);
		int totalCnt = dao.selectTotalCnt(searchDto);

		model.addAttribute("resultList", result);
		model.addAttribute("totCnt", totalCnt);

		return "admin/branch/list";
	}

	@Override
	public List<BranchDto> selectListData(BranchDto searchDto) {
		return dao.selectListData(searchDto);
	}

	@Override
	public String insertPage(BranchDto searchDto, Model model) {
		List<MemberAdmDto> managerList = memberAdminManageService.selectManagerList();
		model.addAttribute("managerList", managerList);
		return "admin/branch/insert";
	}

	@Override
	public BranchDto selectFrontViewData(int brIdx) {
		BranchDto result = dao.selectFrontViewData(brIdx);

		if (result != null) {

			String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

			HashMap<String, Object> timeParams = new HashMap<String, Object>();
			timeParams.put("brIdx", brIdx);
			timeParams.put("selectDate", nowDate);

			List<BranchTimeSettingDto> timeSettingList = dao.selectTimeSettingList(timeParams);
			result.setBranchTimeSettingDtoList(timeSettingList);

			HashMap<String, Object> dateParams = new HashMap<String, Object>();
			dateParams.put("brIdx", brIdx);

			List<BranchDateSettingDto> dateSettingList = dao.selectDateSettingList(dateParams);
			result.setBranchDateSettingDtoList(dateSettingList);
		}

		return result;
	}

	@Override
	@Transactional
	public void insertData(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> params = formatJmMapRequest(request);

		int brIdx = dao.insertData(params);

		String targetDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		params.put("brIdx", brIdx);
		params.put("targetDate", targetDate);

		// 기본 상담시간 세팅
		Map<String, Object> ConsultTimeParams = new HashMap<String, Object>();
		ConsultTimeParams.put("brIdx", brIdx);
		ConsultTimeParams.put("targetDate", targetDate);
		ConsultTimeParams.put("brDefaultConsultTime", "40");

		dao.insertDefaultConsultTime(ConsultTimeParams);

		// 운영시간 세팅
		dao.insertOperationTime(params);

		// 기본 창구개수 세팅
		Map<String, Object> ConsultNumParams = new HashMap<String, Object>();
		ConsultNumParams.put("brIdx", brIdx);
		ConsultNumParams.put("brDefaultCounterNum", params.get("brDefaultCounterNum"));
		ConsultNumParams.put("targetDate", targetDate);

		dao.insertDefaultConsultNum(ConsultNumParams);

		if (!((String) params.get("branchTimeSettingDtoList")).equals("[]")
				&& params.get("branchTimeSettingDtoList") != null) {
			String[] branchTimeSettingDtoList = convertObjectArrayStringToStringArray(
					(String) params.get("branchTimeSettingDtoList"));
			for (int i = 0; i < branchTimeSettingDtoList.length; i++) {

				Map<String, String> targetVal = stringToHashMap(branchTimeSettingDtoList[i]);

				String startTime = targetVal.get("startTime");
				String endTime = targetVal.get("endTime");
				String counterNum = targetVal.get("counterNum");

				BranchTimeSettingDto targetValue = new BranchTimeSettingDto();
				targetValue.setBrIdx(brIdx);
				targetValue.setGbtsStatus(6);
				targetValue.setGbtsStartTime(startTime);
				targetValue.setGbtsEndTime(endTime);
				targetValue.setGbtsCounterNum(counterNum);
				targetValue.setGbtsAppliedAt(targetDate);
				targetValue.setGbtsTargetDate(targetDate);

				dao.insertTimeSettingData(targetValue);
			}
		}

		int j = 0;

		// 요일별 상담시간 기본세팅
		BranchTimeSettingDto defaultWeekParams = new BranchTimeSettingDto();
		defaultWeekParams.setBrIdx(brIdx);
		defaultWeekParams.setGbtsStartTime((String) params.get("brOpenedTime"));
		defaultWeekParams.setGbtsEndTime((String) params.get("brClosedTime"));
		defaultWeekParams.setGbtsCounterNum("0");
		defaultWeekParams.setGbtsAppliedAt(targetDate);
		while (j < 5) {
			j++;
			defaultWeekParams.setGbtsStatus(j);
			dao.insertTimeSettingData(defaultWeekParams);
		}

		if (!((String) params.get("branchDateSettingDtoList")).equals("[]")
				&& params.get("branchDateSettingDtoList") != null) {
			String[] branchDateSettingDtoList = convertObjectArrayStringToStringArray(
					(String) params.get("branchDateSettingDtoList"));
			for (int i = 0; i < branchDateSettingDtoList.length; i++) {

				Map<String, String> targetVal = stringToHashMap(branchDateSettingDtoList[i]);

				String selectDate = targetVal.get("selectDate");
				int counterNum = Integer.parseInt(targetVal.get("counterNum"));

				BranchDateSettingDto targetValue = new BranchDateSettingDto();
				targetValue.setBrIdx(brIdx);
				targetValue.setGbdsDate(selectDate);
				targetValue.setGbdsAppliedAt(targetDate);
				targetValue.setGbdsCounterNum(counterNum);

				dao.insertDateSettingData(targetValue);
			}
		}

		String msg = "등록이 완료되었습니다.";
		String targetLink = "/reserve/adm/branchManage/list";

		makeSubmitAlertMsg(response, msg, targetLink);

	}

	@Override
	public String updatePage(BranchDto searchDto, int brIdx, Model model) {
		BranchDto result = selectViewData(brIdx);

		if (result != null) {
			// 담당자관리 리스트 받아오기
			List<MemberAdmDto> managerList = memberAdminManageService.selectManagerList();
			result.setmemberAdminDtoList(managerList);

			// 특정시간대 세팅
			HashMap<String, Object> timeParams = new HashMap<String, Object>();
			timeParams.put("brIdx", brIdx);
			timeParams.put("gbtsStatus", 6);

			List<BranchTimeSettingDto> timeSettingList = dao.selectTimeSettingList(timeParams);
			result.setBranchTimeSettingDtoList(timeSettingList);

			// 특정일 세팅
			HashMap<String, Object> dateParams = new HashMap<String, Object>();
			dateParams.put("brIdx", brIdx);
			dateParams.put("gbdsStatus", 1);

			List<BranchDateSettingDto> dateSettingList = dao.selectDateSettingList(dateParams);
			result.setBranchDateSettingDtoList(dateSettingList);

		}

		model.addAttribute("updateData", result);

		List<ReserveDto> reserveList = reserveManageListAdminService.selectReserveList(brIdx);

		model.addAttribute("reserveList", reserveList);

		return "admin/branch/update";

	}

	@Override
	@Transactional
	public void updateData(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> params = formatJmMapRequest(request);

		// 특정시간대와 특정일별 세팅을 위한 idx 세팅
		int brIdx = Integer.parseInt((String) params.get("brIdx"));

		// 특정일 데이터 삭제
		HashMap<String, Object> deleteTimeSettingParams = new HashMap<>();
		deleteTimeSettingParams.put("brIdx", brIdx);

		int status = 0;
		while (status < 1) {
			deleteTimeSettingParams.put("gbtsStatus", status + 6);
			dao.deleteTimeSettingData(deleteTimeSettingParams);
			status++;
		}

		Calendar tomorrowDateVal = Calendar.getInstance();
		tomorrowDateVal.setTime(new Date());
		tomorrowDateVal.add(Calendar.DATE, 1);

		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		// 특정시간 세팅
		if (!((String) params.get("branchTimeSettingDtoList")).equals("[]")
				&& params.get("branchTimeSettingDtoList") != null) {
			String[] branchTimeSettingDtoList = convertObjectArrayStringToStringArray(
					(String) params.get("branchTimeSettingDtoList"));
			for (int i = 0; i < branchTimeSettingDtoList.length; i++) {

				Map<String, String> targetVal = stringToHashMap(branchTimeSettingDtoList[i]);

				String startTime = targetVal.get("startTime");
				String endTime = targetVal.get("endTime");
				String counterNum = targetVal.get("counterNum");
				String appliedAt = targetVal.get("appliedAt");

				BranchTimeSettingDto targetValue = new BranchTimeSettingDto();
				targetValue.setBrIdx(brIdx);
				targetValue.setGbtsStatus(6);
				targetValue.setGbtsStartTime(startTime);
				targetValue.setGbtsEndTime(endTime);
				targetValue.setGbtsCounterNum(counterNum);
				targetValue.setGbtsAppliedAt(appliedAt);

				int checkTimeSetting = dao.checkTimeSetting(targetValue);

				if (checkTimeSetting == 0) {
					/* targetValue.setGbtsAppliedAt(tomorrowDate); */
					dao.insertTimeSettingData(targetValue);
				}

			}
		}

		/*
		 * //특정일 특정시간대 세팅 if( !((String)
		 * params.get("branchSpecficDateTimeSettingList")).equals("[]") &&
		 * params.get("branchSpecficDateTimeSettingList") != null) { String[]
		 * branchSpecficDateTimeSettingList =
		 * convertObjectArrayStringToStringArray((String)
		 * params.get("branchSpecficDateTimeSettingList")); for(int i=0; i <
		 * branchSpecficDateTimeSettingList.length; i++) {
		 * 
		 * Map<String, String> targetVal =
		 * stringToHashMap(branchSpecficDateTimeSettingList[i]);
		 * 
		 * String targetDate = targetVal.get("selectDate"); String startTime =
		 * targetVal.get("startTime"); String endTime = targetVal.get("endTime"); String
		 * counterNum = targetVal.get("counterNum");
		 * 
		 * BranchTimeSettingDto targetValue = new BranchTimeSettingDto();
		 * targetValue.setGbtsTargetDate(targetDate); targetValue.setBrIdx(brIdx);
		 * targetValue.setGbtsStatus(7); targetValue.setGbtsStartTime(startTime);
		 * targetValue.setGbtsEndTime(endTime);
		 * targetValue.setGbtsCounterNum(counterNum);
		 * targetValue.setGbtsAppliedAt(nowDate);
		 * 
		 * int checkTimeSetting = dao.checkTimeSetting(targetValue);
		 * 
		 * if(checkTimeSetting == 0) { targetValue.setGbts_applied_at(tomorrowDate);
		 * dao.insertTimeSettingData(targetValue); }
		 * 
		 * } }
		 */

		// 날짜 세팅
		dao.deleteDateSettingData(brIdx);

		if (!((String) params.get("branchDateSettingDtoList")).equals("[]")
				&& params.get("branchDateSettingDtoList") != null) {
			String[] branchDateSettingDtoList = convertObjectArrayStringToStringArray(
					(String) params.get("branchDateSettingDtoList"));
			for (int i = 0; i < branchDateSettingDtoList.length; i++) {

				Map<String, String> targetVal = stringToHashMap(branchDateSettingDtoList[i]);

				String selectDate = targetVal.get("selectDate");
				int counterNum = Integer.parseInt(targetVal.get("counterNum"));

				BranchDateSettingDto targetValue = new BranchDateSettingDto();
				targetValue.setBrIdx(brIdx);
				targetValue.setGbdsStatus(1);
				targetValue.setGbdsDate(selectDate);
				targetValue.setGbdsCounterNum(counterNum);
				targetValue.setGbdsAppliedAt(nowDate);

				int checkDateSetting = dao.checkDateSetting(targetValue);

				if (checkDateSetting == 0) {
					/* targetValue.setGbds_applied_at(tomorrowDate); */
					dao.insertDateSettingData(targetValue);
				}

			}
		}

		dao.updateData(params);

		boolean checkOperationTime = checkUpdateOperationTime(params);

		// 운영시간 변경이 있을 때
		if (checkOperationTime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, 7);
			String targetDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			params.put("targetDate", targetDate);
			dao.insertOperationTime(params);
		}

		int prevDefaultNum = Integer.parseInt(String.valueOf(params.get("prevBrDefaultCounterNum")));
		int defaultNum = Integer.parseInt(String.valueOf(params.get("brDefaultCounterNum")));

		// 기본창구개수가 변했을 때
		if (prevDefaultNum != defaultNum) {
			List<ReserveDto> reserveList = reserveManageListAdminService.selectReserveList(brIdx);

			if (reserveList.size() > 0) {
				for (int i = 0; i < reserveList.size(); i++) {
					String targetDate = (String) reserveList.get(i).getRlReserveDateStr();

					boolean checkFullReserve = checkDateFullReserve(brIdx, targetDate);

					HashMap<String, Object> reserveParams = new HashMap<String, Object>();

					reserveParams.put("brIdx", brIdx);
					reserveParams.put("selectDate", targetDate);

					if (checkFullReserve) {
						reserveManageListAdminService.updateFullReserve(reserveParams);
					} else {
						reserveManageListAdminService.removeFullReserve(reserveParams);
					}

				}

			}

			// 기본 창구개수 세팅
			Map<String, Object> ConsultNumParams = new HashMap<String, Object>();
			ConsultNumParams.put("brIdx", brIdx);
			ConsultNumParams.put("brDefaultCounterNum", defaultNum);
			ConsultNumParams.put("targetDate", params.get("prevBrDefaultCounterNumPossibleDate"));

			dao.insertDefaultConsultNum(ConsultNumParams);

		}

		String msg = "변경이 완료되었습니다.";
		String targetLink = "/reserve/adm/branchManage/list";

		makeSubmitAlertMsg(response, msg, targetLink);

	}

	@Override
	public HashMap<String, Object> selectTargetConsultNum(Map<String, Object> params) {
		return dao.selectTargetConsultNum(params);
	}

	/*
	 * params : br_idx -> 지점 고유번호, select_date -> 지정 날짜 지정 날짜의 지점 데이터 조회
	 */
	@Override
	public HashMap<String, Object> selectTargetDateData(HashMap<String, Object> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		// 기본상담시간 세팅
		String consultTime = dao.selectTargetConsultTime(params);
		result.put("consultTime", consultTime);

		// 운영시간 세팅
		List<HashMap<String, Object>> operationTime = dao.selectTargetOperationTime(params);

		// 요일시간 세팅
		HashMap<String, Object> weekParams = params;
		int dayOfWeekVal = 0;
		try {
			dayOfWeekVal = getDayofWeek(String.valueOf(weekParams.get("selectDate")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		weekParams.put("gbtsStatus", dayOfWeekVal);

		HashMap<String, Object> operationWeekTime = dao.selectTargetOperationWeekTime(weekParams);

		String operationStartTime = (String) operationTime.get(0).get("br_opened_time");
		String operationEndTime = (String) operationTime.get(0).get("br_closed_time");
		String operationWeekStartTime = (String) operationWeekTime.get("start_time");
		String operationWeekEndTime = (String) operationWeekTime.get("close_time");

		try {
			if (compareTime(operationStartTime, operationWeekStartTime) == -1)
				operationTime.get(0).put("br_opened_time", operationWeekStartTime);
			if (compareTime(operationEndTime, operationWeekEndTime) == 1)
				operationTime.get(0).put("br_closed_time", operationWeekEndTime);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result.put("operationTime", operationTime);

		// 상담창구 세팅
		int consultNum = -1;
		HashMap<String, Object> consultDefaultNum = dao.selectTargetConsultDefaultNum(params);
		HashMap<String, Object> consultTargetNum = dao.selectTargetConsultNum(params);

		if (consultDefaultNum != null && consultTargetNum != null) {
			int defaultNum = (int) consultDefaultNum.get("br_default_counter_num");
			int targetNum = (int) consultTargetNum.get("gbds_counter_num");
			consultNum = defaultNum < targetNum ? defaultNum : targetNum;

		} else if (consultDefaultNum != null) {
			consultNum = (int) consultDefaultNum.get("br_default_counter_num");
		} else if (consultTargetNum != null) {
			consultNum = (int) consultTargetNum.get("gbds_counter_num");
		}

		result.put("consultNum", consultNum);
		
		List<HashMap<String, Object>> specialTimeList = dao.selectSpecialTimeList(params);
		
		result.put("specialTimeList", specialTimeList);

		return result;
	}

	@Override
	@Transactional
	public void deleteData(int br_idx, HttpServletResponse response) {

		int checkReserve = 0;
		try {
			checkReserve = reserveManageListAdminService.branchCheckReserve(br_idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String msg = "";
		String targetLink = "/reserve/adm/branchManage/list";

		if (checkReserve > 0) {
			msg = "해당 지점에 예약이 있어 삭제가 불가합니다. 예약을 삭제 후 다시 시도해주세요.";
		} else {
			dao.deleteData(br_idx);
			msg = "삭제가 완료되었습니다.";
		}

		makeSubmitAlertMsg(response, msg, targetLink);

	}

	// 운영시간이 바뀌었는지 확인
	public boolean checkUpdateOperationTime(Map<String, Object> params) {
		boolean result = false;

		String brPrevOpenedTime = (String) params.get("brPrevOpenedTime");
		String brOpenedTime = (String) params.get("brOpenedTime");

		if (!brPrevOpenedTime.equals(brOpenedTime)) {
			result = true;
			return result;
		}

		String brPrevClosedTime = (String) params.get("brPrevClosedTime");
		String brClosedTime = (String) params.get("brClosedTime");

		if (!brPrevClosedTime.equals(brClosedTime)) {
			result = true;
			return result;
		}

		String brPrevLunchOpenedTime = (String) params.get("brPrevLunchOpenedTime");
		String brLunchOpenedTime = (String) params.get("brLunchOpenedTime");

		if (!brPrevLunchOpenedTime.equals(brLunchOpenedTime)) {
			result = true;
			return result;
		}

		String brPrevLunchClosedTime = (String) params.get("brPrevLunchClosedTime");
		String brLunchClosedTime = (String) params.get("brLunchClosedTime");

		if (!brPrevLunchClosedTime.equals(brLunchClosedTime)) {
			result = true;
			return result;
		}

		return result;
	}

	// 해당 날짜에 예약이 다 찼는지 확인
	public boolean checkDateFullReserve(int brIdx, String targetDate) {
		boolean result = false;

		BranchDto branchData = selectViewData(brIdx);

		// 상담창구 세팅
		defaultConsultNum = -1;

		HashMap<String, Object> consultNumParams = new HashMap<String, Object>();
		consultNumParams.put("brIdx", brIdx);
		consultNumParams.put("selectDate", targetDate);

		HashMap<String, Object> consultDefaultNum = dao.selectTargetConsultDefaultNum(consultNumParams);
		HashMap<String, Object> consultTargetNum = dao.selectTargetConsultNum(consultNumParams);

		if(consultDefaultNum != null) {
			defaultConsultNum = (int) consultDefaultNum.get("br_default_counter_num");
		}
		
		if (consultTargetNum != null) {
			defaultConsultNum = (int) consultTargetNum.get("gbds_counter_num");
		}
		
		if (consultDefaultNum != null && consultTargetNum != null) {
			int defaultNum = (int) consultDefaultNum.get("br_default_counter_num");
			int targetNum = (int) consultTargetNum.get("gbds_counter_num");
			defaultConsultNum = defaultNum < targetNum ? defaultNum : targetNum;
		} 

		// 기본 상담시간 설정
		HashMap<String, Object> consultTimeParams = new HashMap<String, Object>();

		consultTimeParams.put("brIdx", brIdx);
		consultTimeParams.put("selectDate", targetDate);

		String consultTime = dao.selectTargetConsultTime(consultTimeParams);

		int defaultConsultTime = 20;
		if (consultTime.equals("")) {
			defaultConsultTime = Integer.parseInt(branchData.getBrDefaultConsultTime());
		} else {
			defaultConsultTime = Integer.parseInt(consultTime);
		}

		// 무슨요일인지 판별
		int dayNum = 1;
		try {
			dayNum = getDayofWeek(targetDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String startTime = branchData.getBrOpenedTimeStr();
		String endTime = branchData.getBrClosedTimeStr();

		String lunchStartTime = branchData.getBrLunchOpenedTimeStr();
		String lunchEndTime = branchData.getBrLunchClosedTimeStr();

		HashMap<String, Object> privateTimeParams = new HashMap<String, Object>();
		privateTimeParams.put("brIdx", brIdx);
		privateTimeParams.put("gbtsStatus", 6);

		privateTimeSettingList = dao.selectTimeSettingList(privateTimeParams);

		privateTimeParams.put("gbtsStatus", 7);

		privateDateTimeSettingList = dao.selectTimeSettingList(privateTimeParams);

		HashMap<String, Object> timeParams = new HashMap<String, Object>();
		timeParams.put("brIdx", brIdx);
		timeParams.put("gbtsStatus", dayNum);
		timeParams.put("selectDate", targetDate);
		timeParams.put("limitCnt", 1);

		List<BranchTimeSettingDto> weekTimeSettingList = dao.selectTimeSettingList(timeParams);

		String targetStartTime = "";
		String targetEndTime = "";

		String dayStartTime = weekTimeSettingList.get(0).getGbtsStartTimeStr();
		String dayEndTime = weekTimeSettingList.get(0).getGbtsEndTimeStr();

		try {
			if (compareTime(dayStartTime, startTime) == 1) {
				targetStartTime = dayStartTime;
			} else {
				targetStartTime = startTime;
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (compareTime(dayEndTime, endTime) == -1) {
				targetEndTime = dayEndTime;
			} else {
				targetEndTime = endTime;
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<String> timeList = new ArrayList<String>();
		try {
			timeList = getTimeList(targetStartTime, lunchStartTime, lunchEndTime, targetEndTime, targetDate, defaultConsultTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("brIdx", brIdx);
		params.put("selectDate", targetDate);

		List<HashMap<String, Object>> reserveList = reserveManageListAdminService.selectBranchDateReserveList(params);

		int j = 0;

		while (j < reserveList.size()) {

			if (timeList.contains(String.valueOf(reserveList.get(j).get("rl_reserve_time_str")))) {
				timeList.remove(String.valueOf(reserveList.get(j).get("rl_reserve_time_str")));
			}

			j++;
		}

		if (timeList.size() == 0) {
			result = true;
		}

		return result;
	}

	// 날짜의 요일을 얻는 함수
	private int getDayofWeek(String targetDate) throws ParseException {
		int result = 0;

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

		Date standardDay = dateFormatter.parse(targetDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(standardDay);

		result = cal.get(Calendar.DAY_OF_WEEK) - 1;

		return result;
	}

	/*
	 * 시간 비교해주는 함수 param : targetTime, compareTime (HH:mm 형식) return int; -1 :
	 * targetTime이 과거인 경우 0 : 같은 경우 1 : targetTime이 미래인 경우
	 */
	private int compareTime(String targetTime, String compareTime) throws ParseException {
		int result = 0;
		String standardDay = "2022-04-06";
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date targetTimeValue = dateFormatter.parse(standardDay + " " + targetTime);
		Date compareTimeValue = dateFormatter.parse(standardDay + " " + compareTime);

		result = targetTimeValue.compareTo(compareTimeValue);

		return result;
	}

	// 시간 리스트를 얻는 함수
	@SuppressWarnings("deprecation")
	private ArrayList<String> getTimeList(String startTime, String lunchStartTime, String lunchEndTime, String endTime,
			String targetDate, int defaultConsultTime) throws ParseException {
		ArrayList<String> timeList = new ArrayList<String>();

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String standardDay = targetDate;

		Date openTimeValue = dateFormatter.parse(targetDate + " " + startTime);
		Date closeTimeValue = dateFormatter.parse(targetDate + " " + endTime);

		Date lunchOpenTimeValue = dateFormatter.parse(targetDate + " " + lunchStartTime);
		Date lunchCloseTimeValue = dateFormatter.parse(targetDate + " " + lunchEndTime);

		Date timeAMStartTimeValue = openTimeValue.before(lunchOpenTimeValue) ? openTimeValue : lunchOpenTimeValue;
		String timeAMStartTimeHours = timeAMStartTimeValue.getHours() < 10
				? '0' + Integer.toString(timeAMStartTimeValue.getHours())
				: Integer.toString(timeAMStartTimeValue.getHours());
		String timeAMStartTimeMin = timeAMStartTimeValue.getMinutes() < 10
				? '0' + Integer.toString(timeAMStartTimeValue.getMinutes())
				: Integer.toString(timeAMStartTimeValue.getMinutes());
		String timeAMStartTime = timeAMStartTimeHours + ':' + timeAMStartTimeMin;
		Date timeAMEndTimeValue = lunchOpenTimeValue.before(closeTimeValue) ? lunchOpenTimeValue : closeTimeValue;

		Date timePMStartTimeValue = openTimeValue.before(lunchCloseTimeValue) ? lunchCloseTimeValue : openTimeValue;
		String timePMStartTimeHours = timePMStartTimeValue.getHours() < 10
				? '0' + Integer.toString(timePMStartTimeValue.getHours())
				: Integer.toString(timePMStartTimeValue.getHours());
		String timePMStartTimeMin = timePMStartTimeValue.getMinutes() < 10
				? '0' + Integer.toString(timePMStartTimeValue.getMinutes())
				: Integer.toString(timePMStartTimeValue.getMinutes());
		String timePMStartTime = timePMStartTimeHours + ':' + timePMStartTimeMin;
		Date timePMEndTimeValue = lunchCloseTimeValue.before(closeTimeValue) ? closeTimeValue : lunchCloseTimeValue;

		long timeAMInterval = (timeAMEndTimeValue.getTime() - timeAMStartTimeValue.getTime()) / 60000
				/ defaultConsultTime;
		long timePMInterval = (timePMEndTimeValue.getTime() - timePMStartTimeValue.getTime()) / 60000
				/ defaultConsultTime;

		int i = 0;
		while (i < timeAMInterval + 1) {
			Date targetAMStartTimeValue = dateFormatter.parse(standardDay + " " + timeAMStartTime);
			targetAMStartTimeValue.setMinutes(targetAMStartTimeValue.getMinutes() + (defaultConsultTime * i));

			Date targetAMEndTimeValue = dateFormatter.parse(standardDay + " " + timeAMStartTime);
			targetAMEndTimeValue.setMinutes(targetAMEndTimeValue.getMinutes() + (defaultConsultTime * (i + 1)));

			if (!targetAMEndTimeValue.after(timeAMEndTimeValue)) {
				String timeAMHourValue = targetAMStartTimeValue.getHours() < 10
						? '0' + Integer.toString(targetAMStartTimeValue.getHours())
						: Integer.toString(targetAMStartTimeValue.getHours());
				String timeAMMinValue = targetAMStartTimeValue.getMinutes() < 10
						? '0' + Integer.toString(targetAMStartTimeValue.getMinutes())
						: Integer.toString(targetAMStartTimeValue.getMinutes());
				String timeAMValue = timeAMHourValue + ':' + timeAMMinValue;

				int z = defaultConsultNum;

				// 특정시간대에 해당하는지 체크
				for (int j = 0; j < privateTimeSettingList.size(); j++) {
					if (compareTime(timeAMValue, privateTimeSettingList.get(j).getGbtsStartTimeStr()) >= 0
							&& compareTime(timeAMValue, privateTimeSettingList.get(j).getGbtsEndTimeStr()) < 1) {
						z = Integer.parseInt(privateTimeSettingList.get(j).getGbtsCounterNum());
					}
				}

				// 특정일 특정시간대에 해당하는지 체크
				for (int j2 = 0; j2 < privateDateTimeSettingList.size(); j2++) {
					if (standardDay.equals(privateDateTimeSettingList.get(j2).getGbtsTargetDateStr())) {
						if (compareTime(timeAMValue, privateDateTimeSettingList.get(j2).getGbtsStartTimeStr()) >= 0
								&& compareTime(timeAMValue,
										privateDateTimeSettingList.get(j2).getGbtsEndTimeStr()) < 1) {
							z = Integer.parseInt(privateDateTimeSettingList.get(j2).getGbtsCounterNum());
						}
					}
				}

				int x = 0;
				while (x < z) {
					timeList.add(timeAMValue);
					x++;
				}

			}

			i++;
		}

		int _i = 0;
		while (_i < timePMInterval + 1) {
			Date targetPMStartTimeValue = dateFormatter.parse(standardDay + " " + timePMStartTime);
			targetPMStartTimeValue.setMinutes(targetPMStartTimeValue.getMinutes() + (defaultConsultTime * _i));

			Date targetPMEndTimeValue = dateFormatter.parse(standardDay + " " + timePMStartTime);
			targetPMEndTimeValue.setMinutes(targetPMEndTimeValue.getMinutes() + (defaultConsultTime * (_i + 1)));

			if (!targetPMEndTimeValue.after(timePMEndTimeValue)) {
				String timePMHourValue = targetPMStartTimeValue.getHours() < 10
						? '0' + Integer.toString(targetPMStartTimeValue.getHours())
						: Integer.toString(targetPMStartTimeValue.getHours());
				String timePMMinValue = targetPMStartTimeValue.getMinutes() < 10
						? '0' + Integer.toString(targetPMStartTimeValue.getMinutes())
						: Integer.toString(targetPMStartTimeValue.getMinutes());
				String timePMValue = timePMHourValue + ':' + timePMMinValue;

				int _z = defaultConsultNum;

				for (int _j = 0; _j < privateTimeSettingList.size(); _j++) {
					if (compareTime(timePMValue, privateTimeSettingList.get(_j).getGbtsStartTimeStr()) >= 0
							&& compareTime(timePMValue, privateTimeSettingList.get(_j).getGbtsEndTimeStr()) < 1) {
						_z = Integer.parseInt(privateTimeSettingList.get(_j).getGbtsCounterNum());
					}
				}

				// 특정일 특정시간대에 해당하는지 체크
				for (int _j2 = 0; _j2 < privateDateTimeSettingList.size(); _j2++) {
					if (standardDay.equals(privateDateTimeSettingList.get(_j2).getGbtsTargetDateStr())) {
						if (compareTime(timePMValue, privateDateTimeSettingList.get(_j2).getGbtsStartTimeStr()) >= 0
								&& compareTime(timePMValue,
										privateDateTimeSettingList.get(_j2).getGbtsEndTimeStr()) < 1) {
							_z = Integer.parseInt(privateDateTimeSettingList.get(_j2).getGbtsCounterNum());
						}
					}
				}

				int _x = 0;
				while (_x < _z) {
					timeList.add(timePMValue);
					_x++;
				}

			}

			_i++;
		}

		return timeList;
	}

	public HashMap<String, Object> selectTargetBranchData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		HashMap<String, Object> data = dao.selectTargetBranchData(map);

		result.put("data", data);

		List<HashMap<String, Object>> targetTimeList = dao.selectTargetBranchTimeData(map);

		result.put("timeList", targetTimeList);

		List<HashMap<String, Object>> targetDateList = dao.selectTargetBranchDateData(map);

		result.put("dateList", targetDateList);

		return result;

	}
	
	@Override
	public HashMap<String, Object> selectTargetConsultDefaultNum(Map<String, Object> params) {
		return dao.selectTargetConsultDefaultNum(params);
	}
	
	@Override
	public List<HashMap<String, String>> selectTargetPrivateTimeSettingList(HashMap<String, String> params) {
		return dao.selectTargetPrivateTimeSettingList(params);
	}

	@Override
	public int checkBranchManager(int mem_idx) {
		return dao.checkBranchManager(mem_idx);
	};
}
