package com.gyeongbuk.spring.guarantee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dto.BranchDateSettingDto;
import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.BranchTimeSettingDto;

public interface BranchManageAdmService {
	String listPage(BranchDto searchDto, Model model);
	List <BranchDto> selectListData(BranchDto searchDto);
	String insertPage(BranchDto searchDto, Model model);
	void insertData(HttpServletRequest request, HttpServletResponse response);
	BranchDto selectFrontViewData(int brIdx);
	BranchDto selectViewData(int brIdx);
	List<BranchTimeSettingDto> selectTimeSettingList(HashMap<String, Object> params);
	List<BranchDateSettingDto> selectDateSettingList(HashMap<String, Object> params);
	String updatePage(BranchDto searchDto, int brIdx, Model model);
	void updateData(HttpServletRequest request, HttpServletResponse response);
	void deleteData(int brIdx, HttpServletResponse response);
	boolean checkDateFullReserve(int brIdx, String targetDate);
	HashMap<String, Object> selectTargetBranchData(HashMap<String, Object> params);
	HashMap<String, Object> selectTargetDateData(HashMap<String, Object> params);
	HashMap<String, Object> selectTargetConsultDefaultNum(Map<String, Object> params);
	HashMap<String, Object> selectTargetConsultNum(Map<String, Object> params);
	List<HashMap<String, String>> selectTargetPrivateTimeSettingList(HashMap<String, String> params);
	int checkBranchManager(int memIdx);
}
