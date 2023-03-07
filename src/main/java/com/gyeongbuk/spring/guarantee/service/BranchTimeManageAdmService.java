package com.gyeongbuk.spring.guarantee.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dto.BranchDto;

public interface BranchTimeManageAdmService {
	String listPage(BranchDto searchDto, HttpServletRequest request, Model model);
	String updatePage(int brIdx, Model model);
	void updateData( HttpServletRequest request, HttpServletResponse response);
}
