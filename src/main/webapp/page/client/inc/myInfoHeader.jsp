<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<header id="header">
   <div class="container">
        <div class="header_btn">
            <ul>
                <li class="h_ico1">
                    <a href="/reserve/myInfo/inquiry"><span class="material-icons">fact_check</span>예약내역 확인 및 취소</a></li>
                <li class="h_ico2"><a href="https://gbsinbo.co.kr/renew/main/main.html">경북신용보증재단</a></li>
            </ul>
        </div>
        <div class="web_title">
            <div class="web_logo">
                <a href="https://gbsinbo.co.kr/renew/main/main.html"><img src="<c:url value='/reserve/client/img/logo.png'/>" alt="logo"></a>
            </div>
            <h1>
            	<a href="/reserve/main">
            		보증상담<p>예약확인</p>
            	</a>
            </h1>
        </div>
    </div>
</header>