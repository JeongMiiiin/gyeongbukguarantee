package com.gyeongbuk.spring.guarantee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gyeongbuk.spring.guarantee.dto.InquiryIpDto;

@Repository
public class InquiryIpManageDaoImpl implements InquiryIpManageDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<InquiryIpDto> selectListData(InquiryIpDto searchDto) {
		List<InquiryIpDto> result = new ArrayList<>();
		
		try {
			result = sqlSession.selectList(SERVICE_ID+".selectListData",searchDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public int selectTotalCnt(InquiryIpDto searchDto) {
		int result = 0;
		try {

			result = sqlSession.selectOne(SERVICE_ID+".selectTotalCnt",searchDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	@Transactional
	public int updateRequest(Map<String, String> params) {
		int result = 0;
		
		try {

			result = sqlSession.update(SERVICE_ID+".updateRequest",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	
	
	@Override
	@Transactional
	public int updateMemberIp(Map<String, String> params) {
		int result = 0;
		
		try {

			result = sqlSession.update(SERVICE_ID+".updateMemberIp",params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
