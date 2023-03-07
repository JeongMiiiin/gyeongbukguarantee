package com.gyeongbuk.spring.guarantee.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

@Repository
public class ReserveManageListDaoImpl implements ReserveManageListDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<ReserveDto> selectListData(ReserveDto searchDto) {
		List<ReserveDto> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectListData",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public int selectTotalCnt(ReserveDto searchDto) {
		int result = 0;
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTotalCnt",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public ReserveDto selectViewData(int rl_idx) {
		
		ReserveDto result = null;
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectViewData",rl_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public List<HashMap<String, Object>> selectDateTimeList(Map<String, Object> params) {
		List<HashMap<String, Object>> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectDateTimeList",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public String selectTargetConsultTime(Map<String, Object> params) {
		String result = "";
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTargetConsultTime",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void insertData(ReserveDto searchDto) {
		
		try {
			
			sqlSession.insert(SERVICE_ID+".insertData",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void updateFullReserve(Map<String, Object> params) {
		
		try {
			
			sqlSession.insert(SERVICE_ID+".updateFullReserve",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	@Transactional
	public void removeFullReserve(Map<String, Object> params) {
		try {
			
			sqlSession.delete(SERVICE_ID+".removeFullReserve",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void updateData(ReserveDto searchDto) {
		try {

			sqlSession.update(SERVICE_ID+".updateData",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	@Transactional
	public int updateReserve(Map<String, Object> params) {
		int result = 0;
		
		try {

			result = sqlSession.update(SERVICE_ID+".updateReserve",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public int deleteData(int rl_idx){
		int result = 0; 
		
		try {
			result = sqlSession.update(SERVICE_ID+".deleteData",rl_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<ReserveDto> selectReserveList(int br_idx){
		List<ReserveDto> result = new ArrayList<>();
		
		try {
			result = sqlSession.selectList(SERVICE_ID+".selectReserveList",br_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public int branchCheckReserve(int br_idx) {
		int result = 0;
		
		try {
			result = sqlSession.selectOne(SERVICE_ID+".branchCheckReserve",br_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<ReserveDto> selectTomorrowReserveList(){
		List<ReserveDto> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectTomorrowReserveList");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public int selectTargetReserve(HashMap<String,String> params) {
		int result = 0;
		try {
			result = sqlSession.selectOne(SERVICE_ID+".selectTargetReserve",params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<HashMap<String, Object>> reserveListAPI(HashMap<String, Object> params) {
		List<HashMap<String, Object>> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".reserveListAPI",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public int updateAudio(HashMap<String,Object> map) {
		int result = 0;
		
		try {

			result = sqlSession.update(SERVICE_ID+".updateAudio",map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
