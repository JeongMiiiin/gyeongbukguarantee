//관리자 로그인 및 비밀번호 변경 등 관리자를 관장하는 Controller

package com.gyeongbuk.spring.guarantee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gyeongbuk.spring.guarantee.service.MemberAdmService;

@Controller
public class MemberAdmController {
	
	@Autowired
	private MemberAdmService service;
	
	@RequestMapping("/reserve/adm")
	public String admMain() {
		return "redirect:adm/login";
	}
	
	@RequestMapping("/reserve/adm/")
	public String admHome() {
		return "redirect:adm/login";
	}
	
	//로그인 페이지 연결
	@RequestMapping(value="/reserve/adm/login")
	public String loginPage(HttpServletRequest request) {
		return service.loginPage(request);
	}

	//로그인 시도 시	
	@RequestMapping(value="/reserve/adm/loginCheck", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> checkLogin(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
		return service.loginSubmit(map,request);
	}	
	
	//로그아웃 시도 시
	@RequestMapping(value="/reserve/adm/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		service.logout(request, response);
	}
	
	//비밀번호 변경 후 최초 로그인시
	@RequestMapping(value="/reserve/adm/loginFirst")
	public String loginFirstPage() {
		return "admin/common/loginFirst";
	}
	
	//인증번호 확인 페이지 이동
	@RequestMapping(value="/reserve/adm/checkMsg")
	public String checkMsgPage() {
		return "admin/common/checkMsg";
	}
	
	//메세지 API 체크 ( 인증 번호를 service에서 비교해서 보여주기 )
	@RequestMapping(value="/reserve/adm/checkMsgSubmit")
	public @ResponseBody int checkMsg(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
		return service.certificationCodeCheck(map,request);
	}
	
	//인증번호 메세지 재전송
	@RequestMapping(value="/reserve/adm/checkMsgResend")
	public @ResponseBody HashMap<String, Object> checkMsgResend(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
		return service.checkMsgResend(request);
	}
	
	//비밀번호 변경 시
	@RequestMapping(value="/reserve/adm/changePassword", method=RequestMethod.POST)
	public void changePassword(HttpServletRequest request, HttpServletResponse response) {
		service.changePassword(request, response);
	}
	
	
	//ip 변경 요청 페이지 접근 시
	@RequestMapping(value="/reserve/adm/inquiryIp")
	public String inquiryIpPage() {
		return "admin/common/inquiryIp";
	}
	
	//ip변경 요청 시
	@RequestMapping(value="/reserve/adm/inquiryIpSubmit", method=RequestMethod.POST)
	public void inquiryIp(HttpServletRequest request, HttpServletResponse response) {
		service.inquiryIp(request, response);
	}
	
}
