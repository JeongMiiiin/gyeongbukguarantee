<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>경북신용보증재단 예약내역</title>
    <%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
    <script type="text/javascript" src="<c:url value='/reserve/client/js/myInfo/myInfo.js'/>"></script>
</head>	
	<%
	if( session.getAttribute("rlName") == null ||  session.getAttribute("rlHp") == null ) {
		%>
		<script>
			$(function(){
				alert('잘못된 접근입니다. 본인 인증 후 다시 시도해주세요.');
				window.location.href="/reserve/myInfo/inquiry";
			})
		</script>
		
		<%	
		return;
	}
	%>

<body>
    <%@ include file="../inc/myInfoHeader.jsp" %>
    <section class="wrap">
            <div class="tab_cont">
            <div class="cont_box on">
                <div class="tab_content">
                    <div class="tab_content_title">
                    	<div class="tab-content_main_title">
                            <span>나의 예약내역</span>
                        </div>
                        <table class="tbl tbl_sm inquiry_list">
                            <caption>보증제한업종의 목록으로 표준산업분류, 업종명을 제공</caption>
                            <colgroup>
                                <col style="width:15%;">
                                <col style="width:35%;">
                                <col style="width:35%;">
                                <col style="width:15%;">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">예약 지점</th>
                                    <th scope="col">예약 일자</th>
                                    <th scope="col">위치</th>
                                    <th scope="col">선택</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach var="list" items="${reserveList}">
                            	<fmt:parseDate var="parseTempDate" value="${list.rlReserveDate}" pattern="yyyy-MM-dd"/>
                            	<fmt:formatDate var="dateTempParse" value="${parseTempDate}" pattern="yyyy-MM-dd"/>
                            	<tr>
                            		<td class="tL tC">${list.brName}</td>
                            		<td class="tL tC">${list.rlReserveDateStr} ${list.rlReserveTimeStr}</td>
                            		<td class="tL tC">${list.brAddress}</td>
                            		<td class="tL tC">
                                        <div class="inquiry_checkbox">
                                            <input type="checkbox" id="reserveCheck${list.rlIdx}" value="${list.rlIdx}" data-reserve-info="${list.brIdx},<c:out value="${dateTempParse}"/>" name="reserveCheck"/>
                                            <label for="reserveCheck${list.rlIdx}"></label>
                                        </div>
                                    </td>
                            	</tr>
                            	</c:forEach>
                            	<c:if test="${empty reserveList}">
							    	<tr class="no_data">
							    		<td colspan="4">결과가 존재하지 않습니다.</td>
								    </tr>
							    </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tab_title_btn content3_btn">
                	<a href="/reserve/main" class="pre_btn tab_content2_pre_btn inquiry_pre_btn">처음으로</a>
	                <a href="javascript:void(0)" class="next_btn tab_content2_next_btn" onclick="myInfo.requestDelete()">예약취소</a>
                </div>
            </div>
        </div>
    </section>
    <%@ include file="../inc/footer.jsp" %>
</body>

<%@ include file="popup.jsp" %>

</html>