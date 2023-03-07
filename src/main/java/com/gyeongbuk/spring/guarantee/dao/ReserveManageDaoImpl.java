package com.gyeongbuk.spring.guarantee.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ReserveManageDaoImpl implements ReserveManageDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int checkReserve(HashMap<String, Object> map) {
		
		int result = 0;
		
		try {
			result = sqlSession.selectOne(SERVICE_ID+".checkReserve", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public int requestReserve(HashMap<String, Object> map) {
		
		int result = 0;
		
		try {
			result = sqlSession.insert(SERVICE_ID+".requestReserve", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<HashMap<String, Object>> selectBillBoardContent(int brIdx){
		List<HashMap<String, Object>> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectBillBoardContent", brIdx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public int updateAudio(HashMap<String, Object> map) {
		
		int result = 0;
		
		try {
			result = sqlSession.insert(SERVICE_ID+".updateAudio", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<String> getHoliday(){
		List<String> result = new ArrayList<>();
		
		try {
			result = sqlSession.selectList(SERVICE_ID+".getHoliday");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	};
	
	@Override
	public void addHoliday(String params) {
		try {
			sqlSession.insert(SERVICE_ID+".addHoliday", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	public void removeHoliday(String params) {
		try {
			sqlSession.delete(SERVICE_ID+".removeHoliday", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
}
