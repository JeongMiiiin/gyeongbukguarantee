<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpSession"%>
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
	
	//권한체크
	int memLevel = (int) session.getAttribute("userLevel");
%>
<header class="col-12 header_wrap">
	<div class="col-12 origin_header_wrap">
		<div class="col-12 header_con">
			<a href="javascript:void(0)" class="hamburger_con" onclick="header.openClickHeader()">
				<span></span>
			</a>
			<ul class="util_con">
				<li>
					<a href="/reserve/adm/logout" class="logout_btn">
						로그아웃
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="col-12 click_header_wrap">
		<div class="col-12 header_con">
			<h1 class="col-0 logo_con">
				<a href="/reserve/adm">
					<img src="<c:url value='/reserve/common/img/logo.png'/>" alt="경북신용보증재단" style="width: 200px;"/>
				</a>
			</h1>
			<a href="javascript:void(0)" class="x_icon_con" onclick="header.closeClickHeader()">
			</a>
		</div>
		
		<ul class="col-12 gnb_wrap">
		<% if(memLevel > 9){
			%>
			<li>
				<a href="/reserve/adm/memberManage/list" target="_self">
					<span>
						사용자관리
					</span>
				</a>
			</li>
			<li>
				<a href="/reserve/adm/inquiryIpManage/list" target="_self">
					<span>
						IP변경요청
					</span>
				</a>
			</li>
			<li>
				<a href="/reserve/adm/branchManage/list" target="_self">
					<span>
						상담지점관리
					</span>
				</a>
			</li>
			<%
		}
		%>
			<li>
				<a href="/reserve/adm/branchTimeManage/list" target="_self">
					<span>
						상담시간관리
					</span>
				</a>
			</li>
			<li>
				<a href="/reserve/adm/reserveManageList/list" target="_self">
					<span>
						상담예약목록
					</span>
				</a>
			</li>
			<li>
				<a href="/reserve/adm/reserveManage/branchList" target="_self">
					<span>
						상담예약관리
					</span>
				</a>
			</li>
		</ul>
	</div>
	
</header>