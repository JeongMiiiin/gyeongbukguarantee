<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>사용자관리 - 상세</title>
	<link rel="stylesheet" href="<c:url value='/reserve/common/css/common.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/common/css/responsiveJM.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.common.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.layout.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.header.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.footer.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.button.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.table.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.filter.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.paging.css'/>"/>
	<link rel="stylesheet" href="<c:url value='/reserve/adm/css/admin.form.css'/>"/>
	<script type="text/javascript" src="<c:url value='/reserve/common/js/jquery-3.6.0.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/reserve/common/js/commonView.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/reserve/common/js/common.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/common.js'/>"></script>
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
				<div class="col-12 admin_form_style_0_wrap">
					<div class="col-12 admin_form_style_0_con type_view">
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<span>이름</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<span>${viewData.mem_username}</span>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<span>아이디</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<span>${viewData.mem_userid}</span>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<span>허용IP</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<span>${viewData.mem_access_ip}</span>
							</div>
						</div>
					</div>
					<div class="col-12 mt30 admin_btn_style_0_con">
						<a href="/reserve/adm/memberManage/list" class="admin_btn_style_0 mr10 bg_grey">
							<span>목록</span>
						</a>
						<a href="/reserve/adm/memberManage/update?mem_idx=${viewData.mem_idx}&${searchDto.qustr}" class="admin_btn_style_0 mr10" data-common-view-update-btn>
							<span>수정</span>
						</a>
						<a href="/reserve/adm/memberManage/delete?mem_idx=${viewData.mem_idx}&${searchDto.qustr}" class="admin_btn_style_0 cancel_btn" data-common-view-delete-btn>
							<span>삭제</span>
						</a>
					</div>
				</div>
	    	</div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>