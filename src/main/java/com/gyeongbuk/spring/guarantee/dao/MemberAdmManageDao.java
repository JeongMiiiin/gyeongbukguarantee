package com.gyeongbuk.spring.guarantee.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gyeongbuk.spring.guarantee.dto.MemberAdmDto;

@Repository
public interface MemberAdmManageDao {
	String SERVICE_ID = "member.manage";
	
	List<MemberAdmDto> selectListData(MemberAdmDto searchDto);
	int checkDuplicateId(Map<String, Object> params);
	int insertData(MemberAdmDto searchDto);
	MemberAdmDto selectViewData(int memIdx);
	int updateData(MemberAdmDto searchDto);
	int initializePassword(Map<String, Object> params);
	int deleteData(int memIdx);
	int selectTotalCnt(MemberAdmDto searchDto);
	List<MemberAdmDto> selectManagerList();
}
