<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>비밀번호 재설정</title>
		<%@ include file="../../common/headInfo/css.jsp" %>
    	<%@ include file="../headInfo/css.jsp" %>
    	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/membership.login.css'/>"/>
    	<%@ include file="../../common/headInfo/javascript.jsp" %>
    	<%@ include file="../headInfo/javascript.jsp" %>
		<script type="text/javascript" src="<c:url value='/reserve/adm/js/membership.js'/>"></script>
	</head>
	<%
	//로그인 체크
	String loginCheck = (String) session.getAttribute("userId"); 
	if(loginCheck == null){
		%>
		<script>
			$(function(){
				alert('로그인을 진행해주시기 바랍니다.');
				window.location.href="/reserve/adm/login";
			})
		</script>
	<%		
		return;	
		}
	%>
	<head>
	<body>
		<section class="col-12 login_wrap">
			<div class="col-12 login_con">
				<div class="col-12 mb30 login_logo_box">
					<img src="<c:url value='/reserve/common/img/logo.png'/>" alt="경북신용보증재단"/>
				</div>
				<form class="col-12 login_form_con" action="/reserve/adm/changePassword" method="post" id="changePasswordForm">
					<div class="col-12 mb20 login_form_contents_con">
						<div class="col-12 login_form_contents">
							<label for="userPw" class="col-12 col-0 label_box">비밀번호</label>
							<div class="col-12 col-0 input_box">
								<input type="password" name="userPw" id="userPw" placeholder="비밀번호를 입력해주세요."/>
							</div>
						</div>
						<div class="col-12 login_form_contents">
							<label for="userPwRe" class="col-12 col-0 label_box">
								 비밀번호 확인
							</label>
							<div class="col-12 col-0 input_box">
								<input type="password" name="userPwRe" id="userPwRe" placeholder="비밀번호를 한번 더 입력해주세요."/>
							</div>
						</div>
					</div>
					<div class="col-12 login_form_bottom_con">
						<div class="col-12 tc login_btn_con">
							<a href="/reserve/adm/logout" class="login_btn type_2">취소</a>
							<input type="submit" value="변경" class="login_btn"/>
						</div>
					</div>
				</form>
			</div>	
		</section>
	</body>
</html>