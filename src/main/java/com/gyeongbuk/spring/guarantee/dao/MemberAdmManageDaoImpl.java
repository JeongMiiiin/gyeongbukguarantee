package com.gyeongbuk.spring.guarantee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gyeongbuk.spring.guarantee.dto.MemberAdmDto;

@Repository
public class MemberAdmManageDaoImpl implements MemberAdmManageDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<MemberAdmDto> selectListData(MemberAdmDto searchDto) {
		List<MemberAdmDto> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectListData",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public int selectTotalCnt(MemberAdmDto searchDto) {
		int result = 0;
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTotalCnt",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public MemberAdmDto selectViewData(int memIdx) {
		
		MemberAdmDto result = null;
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectViewData",memIdx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	@Transactional
	public int checkDuplicateId(Map<String, Object> params) {
		int result = 0;
		
		try {

			result = sqlSession.selectOne(SERVICE_ID+".checkDuplicateId",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	@Transactional
	public int insertData(MemberAdmDto searchDto) {
		int result = 0;
		try {
			result = sqlSession.insert(SERVICE_ID+".insertData",searchDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	@Transactional
	public int updateData(MemberAdmDto searchDto) {
		int result = 0;
		try {
			result = sqlSession.update(SERVICE_ID+".updateData",searchDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;

	}
	
	@Override
	@Transactional
	public int initializePassword(Map<String, Object> params) {
		int result = 0;
		
		try {

			result = sqlSession.update(SERVICE_ID+".initializePassword",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	@Transactional
	public int deleteData(int memIdx) {
		int result = 0;
		
		try {
			result = sqlSession.update(SERVICE_ID+".deleteData",memIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<MemberAdmDto> selectManagerList(){
		List<MemberAdmDto> result = new ArrayList<>();
		
		try {

			result = sqlSession.selectList(SERVICE_ID+".selectManagerList");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
