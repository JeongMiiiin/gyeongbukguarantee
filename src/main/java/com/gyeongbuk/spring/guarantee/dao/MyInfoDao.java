package com.gyeongbuk.spring.guarantee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Repository
public interface MyInfoDao {
	
	String SERVICE_ID = "member.myInfo";
	
	public List<ReserveDto> selectMyReserveList(Map<String, Object> params);
	public int deleteReserve(Map<String, ArrayList> params);
}
