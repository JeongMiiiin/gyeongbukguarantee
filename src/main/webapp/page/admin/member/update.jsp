<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="../"></c:set> 
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>사용자관리 - 수정</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/member/update.js'/>"></script>
</head>
<body>


<%@ include file="../inc/header.jsp" %>
    <% 
	if(memLevel == 9){
		%>
		<script>
			$(function(){
				alert('접근 권한이 없습니다.');
				window.location.href="/reserve/adm/branchTimeManage/list";
			})
		</script>		
<%	
	return;
	}
%>    
    <section class="col-12 admin_contents_wrap">
    	<div class="col-12 admin_contens_con">
    		<h1 class="col-12 page_title">
	    		<a href="/reserve/adm/memberManage/list">사용자관리</a>
	    	</h1>
	    	<div class="col-12 admin_contents">
				<form class="col-12 admin_form_style_0_wrap" action="/reserve/adm/memberManage/updateAction" method="post" data-common-update-form>
					<input type="hidden" name="checkDuplicateId" value="1">
					<div class="col-12 admin_form_style_0_con">
						<input type="hidden" id="memIdx" name="memIdx" value="${updateData.memIdx}"/>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="memUserName">이름</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<input type="text" class="admin_input_style_0" name="memUserName" id="memUserName" value="${updateData.memUserName}" data-common-update-essential-input="이름을 입력해주세요"/>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="memUserId">아이디</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200">
									<input type="text" class="admin_input_style_0" name="memUserId" id="memUserId" value="${updateData.memUserId}" data-common-update-essential-input="아이디를 입력해주세요"/>
								</div>
								<a href="javascript:void(0);" class="mt9 ml10 admin_btn_style_1" onclick="memberUpdate.checkDuplicateId()">
									<span>중복확인</span>
								</a>
								<div class="col-12 check_info_text" id="duplicateIdText" style="display: none;">
									<span>사용 가능합니다.</span>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>비밀번호</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<a href="javascript:void(0)" class="admin_btn_style_0 bg_white" onclick="memberUpdate.initializePassword()">
									<span>초기화</span>
								</a>
								<%-- <input type="password" name="mem_password" id="mem_password" value="${updateData.mem_password}" readonly/> --%>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="memAccessIp">
									허용IP
								</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<input type="text" class="admin_input_style_0" name="memAccessIp" id="memAccessIp" value="${updateData.memAccessIp}" data-common-update-essential-input="허용IP를 입력해주세요"/>
							</div>
						</div>
					</div>
					<div class="col-12 mt30 admin_btn_style_0_con">
						<a href="/reserve/adm/memberManage/list?${searchDto.qustr}" class="admin_btn_style_0 bg_grey mr10" data-common-update-cancel-btn>
							<span>취소</span>
						</a>
						<a href="javascript:void(0)" class="admin_btn_style_0 mr10" data-common-update-complete-btn>
							<span>수정</span>
						</a>
						<a href="/reserve/adm/memberManage/delete?mem_idx=${updateData.memIdx}&${searchDto.qustr}" class="admin_btn_style_0 cancel_btn" data-common-update-delete-btn>
							<span>삭제</span>
						</a>
					</div>
				</form>
	    	</div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>