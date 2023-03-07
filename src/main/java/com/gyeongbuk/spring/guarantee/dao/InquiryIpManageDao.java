package com.gyeongbuk.spring.guarantee.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gyeongbuk.spring.guarantee.dto.InquiryIpDto;

@Repository
public interface InquiryIpManageDao {
String SERVICE_ID = "inquiryIp.manage";
	
	public List<InquiryIpDto> selectListData(InquiryIpDto searchDto);
	int selectTotalCnt(InquiryIpDto searchDto);
	public int updateRequest(Map<String, String> params);
	public int updateMemberIp(Map<String, String> params);
}
