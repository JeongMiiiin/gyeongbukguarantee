<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>사용자관리 - 등록</title>
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
    	<div class="col-12 admin_contents_con">
    		<h1 class="col-12 page_title">
	    		<a href="/reserve/adm/memberManage/list">사용자관리</a>
	    	</h1>
	    	<div class="col-12 admin_contents">
				<form class="col-12 admin_form_style_0_wrap" action="/reserve/adm/memberManage/insertAction" method="post" data-common-update-form>
					<input type="hidden" name="checkDuplicateId" value="0">
					<div class="col-12 admin_form_style_0_con">
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<label for="memUserName">
									이름
								</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200">
									<input type="text" class="admin_input_style_0" name="memUserName" id="memUserName" data-common-update-essential-input="이름을 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<label for="memUserId">
									아이디
								</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200">
									<input type="text" class="admin_input_style_0" name="memUserId" id="memUserId" data-common-update-essential-input="아이디를 입력해주세요"/>
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
							<div class="col-12 col-md-0 label_box">
								<label for="memPassword">
									비밀번호
								</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200">
									<input type="password" class="admin_input_style_0" name="memPassword" id="memPassword" value="12345678" readonly/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<label for="memAccessIp">
									허용IP
								</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200">
									<input type="text" class="admin_input_style_0" name="memAccessIp" id="memAccessIp" data-common-update-essential-input="허용IP를 입력해주세요"/>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12 mt30 admin_btn_style_0_con">
							<a href="/reserve/adm/memberManage/list?${searchDto.qustr}" class="admin_btn_style_0 bg_grey mr10" data-common-update-cancel-btn>
								<span>취소</span>
							</a>
							<a href="javascript:void(0)" class="admin_btn_style_0" data-common-insert-complete-btn>
								<span>등록</span>
							</a>
						</div>
				</form>
	    	</div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>