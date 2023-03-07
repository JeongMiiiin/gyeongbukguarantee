package com.gyeongbuk.spring.guarantee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gyeongbuk.spring.guarantee.service.ReserveManageService;

@Controller
public class ReserveManageController {
	
	@Autowired
	private ReserveManageService service;
	
	@RequestMapping("/reserve")
	public String clientMain() {
		return "redirect:/reserve/main";
	}
	
	@RequestMapping("/reserve/")
	public String clientHome() {
		return "redirect:/reserve/main";
	}
	
	//메인 페이지 열기
	@RequestMapping(value = "/reserve/main", method = RequestMethod.GET)
	public String clientMainPage(HttpServletRequest request, Model model) {
		return service.mainPage(request, model);
	}

	//스텝 페이지 상세
	@RequestMapping(value = "/reserve/step", method = RequestMethod.GET)
	public String stepPage(@RequestParam("step_idx") int stepIdx, HttpServletRequest request, Model model) {
		return service.settingStep(stepIdx, request, model);
	}
	
	//본인인증 성공 페이지 열기
	@RequestMapping(value = "/reserve/successCertification")
	public String openSuccessCertificationPage() {
		return "client/reserve/niceID/checkplus_success";
	}
	
	//본인인증 실패 페이지 열기
	@RequestMapping(value = "/reserve/failCertification")
	public String openFailCertificationPage() {
		return "client/reserve/niceID/checkplus_fail";
	}
	
	//등록
	@RequestMapping(value = "/reserve/requestReserve", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> requestReserve(@RequestBody HashMap<String, Object> map) {
		return service.requestReserve(map);
	}
	
	//전광판 페이지 열기
	@RequestMapping(value = "/reserve/billboard", method = RequestMethod.GET)
	public String clientBillboardPage(HttpServletRequest request, Model model) {
		return service.billboardPage(request, model);
	}
		
	//전광판 예약 리스트 조회
	@RequestMapping(value = "/reserve/selectBillboardReserveList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectBillboardReserveList(@RequestBody HashMap<String, Object> map) {
		return service.selectBillboardReserveList(map);
	}
	
	//전광판 예약 리스트 조회
	@RequestMapping(value = "/reserve/updateAudio", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateAudio(@RequestBody HashMap<String, Object> map) {
		return service.updateAudio(map);
	}
	
	//휴일정보 조회
	@RequestMapping("/reserve/getHoliday")
	public @ResponseBody Map<String,Object> getHoliday() {
		return service.getHoliday();
	}
	
}
