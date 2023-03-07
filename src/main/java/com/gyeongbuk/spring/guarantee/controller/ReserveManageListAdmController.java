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

import com.gyeongbuk.spring.guarantee.dto.ReserveDto;
import com.gyeongbuk.spring.guarantee.service.ReserveManageListAdmService;

@Controller
@RequestMapping("/reserve/adm/reserveManageList*")
public class ReserveManageListAdmController {
	@Autowired
	private ReserveManageListAdmService service;
	
	//list 조회
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String selectListData(@ModelAttribute("searchDto") ReserveDto searchDto, HttpServletRequest request, Model model) {
		return service.listPage(searchDto, request, model);
	}
	
	//해당 날짜의 예약목록 조회
	@RequestMapping("/selectDateTimeList")
	public @ResponseBody Map<String,Object> selectDateTimeList(@RequestBody HashMap<String, Object> map) {
		return service.selectDateTimeList(map);
	}
			
	//등록 페이지 이동
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertPage(@ModelAttribute("searchDto") ReserveDto searchDto, HttpServletRequest request, Model model) {
		return service.insertPage(searchDto, request, model);
	}
			
	//등록
	@RequestMapping(value = "/insertAction", method = RequestMethod.POST)
	public void insertData(@ModelAttribute("searchDto") ReserveDto searchDto, HttpServletResponse response) {
		service.insertData(searchDto, response);
	}
	
	//변경 페이지 이동
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updatePage(@ModelAttribute("searchDto") ReserveDto searchDto, HttpServletRequest request, @RequestParam("rl_idx") int rlIdx, Model model) {
		return service.updatePage(searchDto, rlIdx, request, model);
	}
	
	//변경
	@RequestMapping(value = "/updateAction", method = RequestMethod.POST)
	public void updateData(@ModelAttribute("searchDto") ReserveDto searchDto, HttpServletResponse response) {
		service.updateData(searchDto, response);
	}

	//요청상태 변경
	@RequestMapping(value = "/updateReserve", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateReserve(@RequestBody HashMap<String, Object> map) {
		return service.updateReserve(map);
	}
	
	//삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void deleteData(@RequestParam("rl_idx") int rlIdx, HttpServletResponse response) {
		service.deleteData(rlIdx, response);
	}
	
	//삭제 요청
	@RequestMapping(value = "/deleteReserve", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteReserve(@RequestBody HashMap<String, Object> map) {
		return service.deleteReserve(map);
	}

}
