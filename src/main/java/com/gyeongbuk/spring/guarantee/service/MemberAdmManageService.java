package com.gyeongbuk.spring.guarantee.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dto.MemberAdmDto;

public interface MemberAdmManageService {
	String listPage(MemberAdmDto searchDto, Model model);
	String insertPage( MemberAdmDto searchDto );
	HashMap<String, Object> checkDuplicateId(HashMap<String, Object> params, HttpServletRequest request);
	void insertData( MemberAdmDto searchDto, HttpServletResponse response );
	String viewPage( MemberAdmDto searchDto, int memIdx, Model model );
	String updatePage( MemberAdmDto searchDto, int memIdx, Model model );
	void updateData( MemberAdmDto searchDto, HttpServletResponse response );
	HashMap<String, Object> initializePassword(HashMap<String, Object> map, HttpServletRequest request);
	void deleteData( MemberAdmDto searchDto, int memIdx, HttpServletResponse response );
	List<MemberAdmDto> selectManagerList();
}
