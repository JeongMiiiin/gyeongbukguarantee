package com.gyeongbuk.spring.guarantee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Repository
public class MyInfoDaoImpl implements MyInfoDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<ReserveDto> selectMyReserveList(Map<String, Object> params) {
		List<ReserveDto> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectMyreserveList", params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	@Transactional
	public int deleteReserve(Map<String, ArrayList> params) {
		int result = 0;
		
		try {

			result = sqlSession.update(SERVICE_ID+".deleteReserve",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
