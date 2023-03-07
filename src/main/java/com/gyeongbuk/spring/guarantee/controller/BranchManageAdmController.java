//지점관리를 위한 controller

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

import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.service.BranchManageAdmService;

@Controller
@RequestMapping("/reserve/adm/branchManage*")
public class BranchManageAdmController {
	@Autowired
	private BranchManageAdmService service;
		
	//list 조회
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String selectListData(@ModelAttribute("searchDto") BranchDto searchDto, Model model) {
		return service.listPage(searchDto, model);
	}
		
	//등록 페이지 이동
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertPage(@ModelAttribute("searchDto") BranchDto searchDto, Model model) {
		return service.insertPage(searchDto, model);
	}
		
	//등록
	@RequestMapping(value = "/insertAction", method = RequestMethod.POST)
	public void insertData(HttpServletRequest request, HttpServletResponse response) {
		service.insertData(request, response);
	}
		
	//변경 페이지 이동
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updatePage(@ModelAttribute("searchDto") BranchDto searchDto, @RequestParam("br_idx") int brIdx, Model model) {
		return service.updatePage(searchDto, brIdx, model);
	}
		
	//변경
	@RequestMapping(value = "/updateAction", method = RequestMethod.POST)
	public void updateData(HttpServletRequest request, HttpServletResponse response) {
		service.updateData(request, response);
	}
		
	//삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void deleteData(@ModelAttribute("searchDto") BranchDto searchDto, @RequestParam("br_idx") int brIdx, HttpServletResponse response) {
		service.deleteData(brIdx, response);
	}
		
	//선택한 지점 데이터 가져오기 (json 형태)
	@RequestMapping("/selectTargetBranchData")
	public @ResponseBody Map<String,Object> selectBranchData(@RequestBody HashMap<String, Object> map) {
		return service.selectTargetBranchData(map);
	}
	
}
