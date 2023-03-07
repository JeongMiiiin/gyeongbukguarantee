<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>IP 변경요청</title>
		<%@ include file="../../common/headInfo/css.jsp" %>
    	<%@ include file="../headInfo/css.jsp" %>
    	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/membership.login.css'/>"/>
    	<%@ include file="../../common/headInfo/javascript.jsp" %>
    	<%@ include file="../headInfo/javascript.jsp" %>
		<script type="text/javascript" src="<c:url value='/reserve/adm/js/membership.js'/>"></script>
	</head>
	<body>
		<section class="col-12 login_wrap">
			<div class="col-12 login_con">
				<div class="col-12 mb30 login_logo_box">
					<img src="<c:url value='/reserve/common/img/logo.png'/>" alt="경북신용보증재단"/>
				</div>
				<form class="col-12 login_form_con" action="inquiryIpSubmit" method="post" id="inquiryIpForm">
					<div class="col-12 mb20 login_form_contents_con">
						<div class="col-12 login_form_contents">
							<label for="userId" class="col-12 col-0 label_box">아이디</label>
							<div class="col-12 col-0 input_box">
								<input type="text" name="userId" id="userId" placeholder="아이디를 입력해주세요."/>
							</div>
						</div>
						<div class="col-12 login_form_contents">
							<label for="userPw" class="col-12 col-0 label_box">비밀번호</label>
							<div class="col-12 col-0 input_box">
								<input type="password" name="userPw" id="user_pw" placeholder="비밀번호를 입력해주세요."/>
							</div>
						</div>
						<div class="col-12 login_form_contents">
							<label for="inquiryIp" class="col-12 col-0 label_box">요청IP</label>
							<div class="col-12 col-0 input_box">
								<input type="text" name="inquiryIp" id="inquiryIp" placeholder="IP를 000.000.000 형태로 입력해주세요."/>
							</div>
						</div>
					</div>
					<div class="col-12 login_form_bottom_con">
						<div class="col-12 tc login_btn_con">
 							<a href="/reserve/adm/login" class="login_btn type_2">취소</a>
							<input type="submit" value="요청" class="login_btn"/>
						</div>
					</div>
				</form>
			</div>	
		</section>
	</body>
</html>