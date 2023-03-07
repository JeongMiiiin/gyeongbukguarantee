package com.gyeongbuk.spring.guarantee.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;

@Repository
public interface BranchManageDao {
	String SERVICE_ID = "branch.manage";
	
	List<BranchDto> selectListData(BranchDto searchDto);
	int selectTotalCnt(BranchDto searchDto);
	int insertData(Map<String, Object> params);
	void insertDefaultConsultTime(Map<String, Object> params);
	void insertDefaultConsultNum(Map<String, Object> params);
	void insertOperationTime(Map<String, Object> params);
	BranchDto selectViewData(int brIdx);
	BranchDto selectFrontViewData(int brIdx);
	List<BranchTimeSettingDto> selectTimeSettingList(HashMap<String, Object> params);
	List<BranchDateSettingDto> selectDateSettingList(HashMap<String, Object> params);
	HashMap<String, Object> selectTargetConsultDefaultNum(Map<String, Object> params);
	HashMap<String, Object> selectTargetConsultNum(Map<String, Object> params);
	List<HashMap<String, String>> selectTargetPrivateTimeSettingList(HashMap<String, String> params);
	String selectTargetConsultTime(Map<String, Object> params);
	void updateData(Map<String, Object> params);
	int checkTimeSetting(BranchTimeSettingDto params);
	int checkDateSetting(BranchDateSettingDto params);
	void deleteTimeSettingData(HashMap<String, Object> params);
	void insertTimeSettingData(BranchTimeSettingDto params);
	void deleteDateSettingData(int brIdx);
	void insertDateSettingData(BranchDateSettingDto params);
	void deleteData(int brIdx);
	HashMap<String, Object> selectTargetBranchData(HashMap<String, Object> map);	
	List<HashMap<String, Object>> selectTargetBranchTimeData(HashMap<String, Object> map);
	List<HashMap<String, Object>> selectTargetBranchDateData(HashMap<String, Object> map);
	List<HashMap<String, Object>> selectTargetOperationTime(Map<String, Object> params);
	HashMap<String, Object> selectTargetOperationWeekTime(HashMap<String, Object> params);
	List<HashMap<String, Object>> selectSpecialTimeList(HashMap<String, Object> params);
	int checkBranchManager(int memIdx);
}
