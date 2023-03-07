package com.gyeongbuk.spring.guarantee.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;

@Repository
public class BranchTimeManageDaoImpl implements BranchTimeManageDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public BranchDto selectViewData(int br_idx) {
		
		BranchDto result = null;
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectViewData",br_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	@Transactional
	public int updateData(Map<String, Object> params) {
		
		int result = 0;
		try {

			result = sqlSession.update(SERVICE_ID+".updateTimeData",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;

	}
	
	@Override
	@Transactional
	public void insertDefaultConsultTime(Map<String, Object> params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertDefaultConsultTime",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	@Transactional
	public void insertOperationTime(Map<String, Object> params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertOperationTime",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	@Override
	@Transactional
	public void insertTimeSettingData(BranchTimeSettingDto params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertTimeSettingData",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	@Transactional
	public void deleteDateSettingData(int br_idx) {
		try {

			sqlSession.delete(SERVICE_ID+".deleteTimeDateSettingData",br_idx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	@Transactional
	public void insertDateSettingData(BranchDateSettingDto params) {
		try {

			sqlSession.insert(SERVICE_ID+".insertTimeDateSettingData",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
