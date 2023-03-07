package com.gyeongbuk.spring.guarantee.service;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MemberAdmService {
		String loginPage(HttpServletRequest request); //로그인 페이지 이동
		HashMap<String, Object> loginSubmit(HashMap<String, Object> map, HttpServletRequest request); //로그인 폼 Submit
		void logout(HttpServletRequest request, HttpServletResponse response); //로그아웃 실행
		void inquiryIp(HttpServletRequest request, HttpServletResponse response);
		void changePassword(HttpServletRequest request, HttpServletResponse response);
		int certificationCodeCheck(HashMap<String, Object> map, HttpServletRequest request);
		HashMap<String, Object> checkMsgResend(HttpServletRequest request);
}
