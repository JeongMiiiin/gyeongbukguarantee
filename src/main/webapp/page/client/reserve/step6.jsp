<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% 
response.setHeader("Cache-Control","no-store"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0); 
if (request.getProtocol().equals("HTTP/1.1"))
        response.setHeader("Cache-Control", "no-cache");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<title>경북신용보증재단 보증상담예약신청</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
    <script type="text/javascript" src="<c:url value='/reserve/client/js/reserve/step.js'/>"></script>
</head>
<body>
	<%
	if( session.getAttribute("rlName") == null ||  session.getAttribute("rlHp") == null ) {
		%>
		<script>
			$(function(){
				alert('잘못된 접근입니다. 본인 인증 후 다시 시도해주세요.');
				window.location.href="/reserve/step?step_idx=4";
			})
		</script>
		
		<%	
		return;
	}
	
	if(request.getParameter("rl_business_type") == null) {
		%>
		<script>
			$(function(){
				alert('잘못된 접근입니다. 사업자 선택 후 다시 시도해주세요.');
				window.location.href="/reserve/step?step_idx=5";
			})
		</script>
		<%	
		return;
	}
	%>
	<%
	int step = request.getParameter("step_idx") != null ? Integer.parseInt(request.getParameter("step_idx")) : -1;
	%>
	<script>
		let curStep = <%=step%>
	</script>
	<%@ include file="../inc/header.jsp" %>
    <section class="wrap">
        <div class="content">
        	<ul class="tab_title">
	            <li class="tab_li prev">상담안내</li>
	            <li class="tab_li prev">이용안내</li>
	            <li class="tab_li prev">자가진단</li>
	            <li class="tab_li prev">본인인증</li>
	            <li class="tab_li on">지점예약</li>
	            <!-- <li class="tab_li">예약확인</li> -->
	        </ul>
	        <div class="tab_cont">
	            <!-- 6지점예약//사업장 소재지 -->
	            <div class="cont_box on">
	                <div class="tab_content">
	                    <div class="place_list_wrap">
	                        <div class="place_list_title">
	                            <span>현재 사업자등록상 소재지를 선택해 주세요.</span>
	                        </div>
	                        <div class="place_list_box">
	                        	<input type="hidden" name="selectBrIdx" id="selectBrIdx"/>
	                        	<c:forEach var="list" items="${branchList}">
	                        		<div class="col-12 col-sm-6 col-md-4">
	                        			<a href="javascript:void(0)" data-br-idx="${list.brIdx}">
		                        			<span><span>${list.brName}지점</span><br/>(${list.brCategoryName})</span>
		                        		</a>
	                        		</div>
	                        		
	                        	</c:forEach>
	                        </div>
	                    </div>
	                </div>
	                <div class="tab_title_btn">
	                    <a href="/reserve/step?step_idx=5" class="pre_btn tab_content2_pre_btn" data-step-prev-btn>이전단계</a>
	                    <a href="/reserve/step?step_idx=7" class="next_btn tab_content2_next_btn" data-step-next-btn>다음단계</a>
	                </div>
	            </div>
	        </div>
        </div>
    </section>
    <%@ include file="../inc/footer.jsp" %>
    <%@ include file="popup.jsp" %>
</body>
</html>