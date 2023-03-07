//지점시간관리를 위한 controller

package com.gyeongbuk.spring.guarantee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gyeongbuk.spring.guarantee.dto.BranchDto;
import com.gyeongbuk.spring.guarantee.service.BranchTimeManageAdmService;

@Controller
@RequestMapping("/reserve/adm/branchTimeManage*")
public class BranchTimeManageAdmController {
	@Autowired
	private BranchTimeManageAdmService service;
	
	//list 조회
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listPage(@ModelAttribute("searchDto") BranchDto searchDto, HttpServletRequest request, Model model) {
		return service.listPage(searchDto, request, model);
	}
		
	//변경 페이지 이동
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updatePage(@ModelAttribute("searchDto") BranchDto searchDto, @RequestParam("br_idx") int brIdx, Model model) {
		return service.updatePage(brIdx, model);
	}
		
	//변경
	@RequestMapping(value = "/updateAction", method = RequestMethod.POST)
	public void updateData(HttpServletRequest request, HttpServletResponse response) {
		service.updateData(request, response);
	}
}
