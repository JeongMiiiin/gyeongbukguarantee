package com.gyeongbuk.spring.guarantee.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;

@Repository
public interface BranchTimeManageDao {
	String SERVICE_ID = "branch.manage";
	
	BranchDto selectViewData(int br_idx);
	int updateData(Map<String, Object> params);
	public void insertDefaultConsultTime(Map<String, Object> params);
	public void insertOperationTime(Map<String, Object> params);
	void insertTimeSettingData(BranchTimeSettingDto params);
	void deleteDateSettingData(int br_idx);
	void insertDateSettingData(BranchDateSettingDto params);
}
