package com.gyeongbuk.spring.guarantee.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository // 현재 클래스를 dao bean으로 등록
public class MemberAdmDaoImpl implements MemberAdmDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public HashMap<String, Object> login(Map<String, Object> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".logincheck",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public int checkIp(Map<String, Object> params) {
		int result = 0;
		
		try {
			result = sqlSession.selectOne(SERVICE_ID+".checkIp",params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public int inquiryIp(HashMap<String, Object> params) {
		int result = 0;
		
		try {

			result = sqlSession.update(SERVICE_ID+".inquiryIp",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}

	//ip 변경 요청할 아이디 정보 조회
	@Override
	public HashMap<String, Object> inquiryIpCheckId(HashMap<String, Object> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".inquiryIpCheckId",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	@Transactional
	public int changePassword(HashMap<String, Object> params) {
		int result = 0;
		
		try {

			result = sqlSession.update(SERVICE_ID+".changePassword",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	@Transactional
	public void updateCertificationCode(HashMap<String, Object> params) {
		try {
			sqlSession.update(SERVICE_ID+".updateCertificationCode",params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int checkCertificationCode(HashMap<String, Object> params) {
		int result = 0;
		
		try {
			result = sqlSession.selectOne(SERVICE_ID+".checkCertificationCode",params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
