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
import com.gyeongbuk.spring.guarantee.dto.ReserveDto;
import com.gyeongbuk.spring.guarantee.service.ReserveManageAdmService;

@Controller
@RequestMapping("/reserve/adm/reserveManage*")
public class ReserveManageAdmController {
	
	@Autowired
	private ReserveManageAdmService service;

	//지점페이지 이동
	@RequestMapping(value = "/branchList", method = RequestMethod.GET)
	public String branchListPage(HttpServletRequest request, @ModelAttribute("searchDto") BranchDto searchDto, Model model) {
		return service.branchListPage(request, searchDto, model);
	}

	//리스트페이지 이동
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String selectListData(@ModelAttribute("searchDto") ReserveDto searchDto, @RequestParam("br_idx") int brIdx, Model model){
		return service.listPage(searchDto, brIdx, model);
	}
	
	//등록 페이지 이동
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertPage(@ModelAttribute("searchDto") ReserveDto searchDto, HttpServletRequest request, Model model) {
		return service.insertPage(searchDto, request, model);
	}
	
	//등록 실행
	@RequestMapping(value = "/insertAction", method = RequestMethod.POST)
	public void insertData(@ModelAttribute("searchDto") ReserveDto searchDto, HttpServletResponse response) {
		service.insertData(searchDto, response);
	};
	
	//audio 실행
	@RequestMapping(value = "/updateAudio", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateAudio(@RequestBody HashMap<String, Object> map) {
		return service.updateAudio(map);
	};
	
}
