package com.gyeongbuk.spring.guarantee.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.dto.ReserveDto;

public interface ReserveManageAdmService {
	String branchListPage(HttpServletRequest request, BranchDto searchDto, Model model);
	String listPage(ReserveDto searchDto, int brIdx, Model model);
	String insertPage(ReserveDto searchDto, HttpServletRequest request, Model model);
	void insertData(ReserveDto searchDto, HttpServletResponse response);
	Map<String,Object> updateAudio(HashMap<String, Object> map);
}
