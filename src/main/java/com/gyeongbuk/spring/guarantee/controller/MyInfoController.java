//사용자 예약내역 확인 및 취소를 위한 Controller

package com.gyeongbuk.spring.guarantee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gyeongbuk.spring.guarantee.service.MyInfoService;

@Controller
@RequestMapping("/reserve/myInfo*")
public class MyInfoController {
	@Autowired
	private MyInfoService service;
	
	//인증 페이지 열기
	@RequestMapping(value = "")
	public String goInquiryPage() {
		return "redirect:/reserve/myInfo/inquiry";
	}
	
	//인증 페이지 열기	
	@RequestMapping(value = "/inquiry")
	public String inquiryPage() {
		return "client/myInfo/inquiry";
	}
	
	//본인인증 성공 페이지 열기
	@RequestMapping(value = "/successCertification")
	public String openSuccessCertificationPage() {
		return "client/myInfo/niceID/checkplus_success";
	}
			
	//본인인증 실패 페이지 열기
	@RequestMapping(value = "/failCertification")
	public String openFailCertificationPage() {
		return "client/myInfo/niceID/checkplus_fail";
	}
	
	//리스트 페이지 열기
	@RequestMapping(value = "/list")
	public String inquiryPage(HttpServletRequest request, Model model) {
		return service.listPage(request, model);
	}

	//내역 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteReserve(@RequestBody HashMap<String, ArrayList> map) {			
		return service.deleteReserve(map);
	}
	
}