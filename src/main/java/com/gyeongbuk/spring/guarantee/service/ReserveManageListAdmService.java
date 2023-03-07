package com.gyeongbuk.spring.guarantee.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

public interface ReserveManageListAdmService {
	String listPage( ReserveDto searchDto, HttpServletRequest request, Model model);
	HashMap<String, Object> selectDateTimeList(HashMap<String, Object> params);
	List<HashMap<String, Object>> selectBranchDateReserveList(HashMap<String, Object> params);
	String insertPage(ReserveDto searchDto, HttpServletRequest request, Model model);
	void insertData( ReserveDto searchDto, HttpServletResponse response);
	String updatePage(ReserveDto searchDto, int rl_idx, HttpServletRequest request, Model model);
	void updateData( ReserveDto searchDto, HttpServletResponse response);
	void deleteData( int rl_idx, HttpServletResponse response );
	HashMap<String, Object> updateReserve(HashMap<String, Object> map);
	HashMap<String, Object> deleteReserve(HashMap<String, Object> map);
	void updateFullReserve(HashMap<String, Object> params);
	void removeFullReserve(HashMap<String, Object> params);
	int branchCheckReserve(int br_idx);
	List<ReserveDto> selectTomorrowReserveList(); //스케줄러에서 내일 보내야 할 예약목록을 조회하기위한 함수
	List<ReserveDto> selectReserveList(int br_idx);
	List<HashMap<String, Object>> reserveListAPI(HashMap<String, Object> map);
	boolean checkPossibleReserve(HashMap<String, String> params);
}
