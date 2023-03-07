package com.gyeongbuk.spring.guarantee.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Repository
public interface ReserveManageListDao {
	String SERVICE_ID = "reserve.manage.list";
	
	List<ReserveDto> selectListData(ReserveDto searchDto);
	int selectTotalCnt(ReserveDto searchDto);
	List<HashMap<String, Object>> selectDateTimeList(Map<String, Object> params);
	String selectTargetConsultTime(Map<String, Object> params);
	void insertData(ReserveDto params);
	public void updateFullReserve(Map<String, Object> params);
	public void removeFullReserve(Map<String, Object> params);
	ReserveDto selectViewData(int rl_idx);
	void updateData(ReserveDto params);
	int updateReserve(Map<String, Object> params);
	int deleteData(int rl_idx);
	List<ReserveDto> selectReserveList(int br_idx);
	int branchCheckReserve(int br_idx);
	List<ReserveDto> selectTomorrowReserveList();
	List<HashMap<String, Object>> reserveListAPI(HashMap<String, Object> params);
	int selectTargetReserve(HashMap<String,String> params);
	int updateAudio(HashMap<String,Object> map);
}
