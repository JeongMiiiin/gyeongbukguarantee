//사용자(서브관리자)관리를 위한 Controller 
package com.gyeongbuk.spring.guarantee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gyeongbuk.spring.guarantee.dto.MemberAdmDto;
import com.gyeongbuk.spring.guarantee.service.MemberAdmManageService;

@Controller
@RequestMapping("/reserve/adm/memberManage*")
public class MemberAdmManageController {

	@Autowired
	private MemberAdmManageService service;
	
	//list 조회
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String selectListData(@ModelAttribute("searchDto") MemberAdmDto searchDto, Model model) {
		return service.listPage(searchDto, model);
	}
	
	//페이지 상세
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewPage(@ModelAttribute("searchDto") MemberAdmDto searchDto, @RequestParam("mem_idx") int memIdx, Model model) {
		return service.viewPage(searchDto, memIdx, model);
	}
	
	//등록 페이지 이동
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertPage(@ModelAttribute("searchDto") MemberAdmDto searchDto) {
		return service.insertPage(searchDto);
	}
	
	//아이디 중복확인
	@RequestMapping(value = "/checkDuplicateId", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> checkDuplicateId(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
		return service.checkDuplicateId(map,request);
	}
	
	//등록
	@RequestMapping(value = "/insertAction", method = RequestMethod.POST)
	public void insertData(@ModelAttribute("searchDto") MemberAdmDto searchDto, HttpServletResponse response) {
		service.insertData(searchDto, response);
	}
	
	//변경 페이지 이동
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updatePage(@ModelAttribute("searchDto") MemberAdmDto searchDto, @RequestParam("mem_idx") int memIdx, Model model){
		return service.updatePage(searchDto, memIdx, model);
	}
	
	//변경
	@RequestMapping(value = "/updateAction", method = RequestMethod.POST)
	public void updateData(@ModelAttribute("searchDto") MemberAdmDto searchDto, HttpServletResponse response) {
		service.updateData(searchDto, response);
	}
	
	//비밀번호초기화
	@RequestMapping(value = "/initializePassword", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> initializePassword(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
		return service.initializePassword(map,request);
	}
	
	//삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void deleteData(@ModelAttribute("searchDto") MemberAdmDto searchDto, @RequestParam("mem_idx") int memIdx, HttpServletResponse response) {
		service.deleteData(searchDto, memIdx, response);
	}
	
}