package com.gyeongbuk.spring.guarantee.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ReserveManageDao {
	String SERVICE_ID = "reserve.manage";
	
	public int checkReserve(HashMap<String, Object> map);
	public int requestReserve(HashMap<String, Object> map);
	public List<HashMap<String, Object>> selectBillBoardContent(int brIdx);
	public int updateAudio(HashMap<String, Object> map);
	List<String> getHoliday();
	void addHoliday(String params);
	void removeHoliday(String params);
}
