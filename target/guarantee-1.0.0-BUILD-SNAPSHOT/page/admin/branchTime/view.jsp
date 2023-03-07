<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>상담지점관리 - 상세</title>
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
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/header.js'/>"></script>
</head>
<body>


<%@ include file="../inc/header.jsp" %>
    
    <section class="col-12 admin_contents_wrap">
    	<h1 class="col-12 page_title">
    		<a href="/reserve/adm/branchTimeManage/list">상담시간관리</a>
    	</h1>
    	<div class="col-12 admin_contents">
			<div class="col-12 admin_form_style_0_wrap">
				<div class="col-12 admin_form_style_0_con type_view">
					<div class="col-12 admin_form_style_0">
						<div class="col-12 col-md-0 label_box">
							<span>영업점명</span>
						</div>
						<div class="col-12 col-md-0 input_box">
							${viewData.br_name}
						</div>
					</div>
					<div class="col-12 admin_form_style_0">
						<div class="col-12 col-md-0 label_box">
							<span>관할지역</span>
						</div>
						<div class="col-12 col-md-0 input_box">
							${viewData.br_category_name}
						</div>
					</div>
					<div class="col-12 admin_form_style_0">
						<div class="col-12 col-md-0 label_box">
							<span>주소</span>
						</div>
						<div class="col-12 col-md-0 input_box">
							${viewData.br_address}
						</div>
					</div>
					<div class="col-12 admin_form_style_0">
						<div class="col-12 col-md-0 label_box">
							<span>전화번호</span>
						</div>
						<div class="col-12 col-md-0 input_box">
							${viewData.br_hp}
						</div>
					</div>
					<div class="col-12 admin_form_style_0">
						<div class="col-12 col-md-0 label_box">
							<span>담당자</span>
						</div>
						<div class="col-12 col-md-0 input_box">
							${viewData.mem_username}
						</div>
					</div>
					<div class="col-12 admin_form_style_0">
						<div class="col-12 col-md-0 label_box">
							<span>운영시간</span>
						</div>
						<div class="col-12 col-md-0 input_box">
							${viewData.br_opened_time_str} - ${viewData.br_closed_time_str} 
						</div>
					</div>
					<div class="col-12 admin_form_style_0">
						<div class="col-12 col-md-0 label_box">
							<span>점심시간</span>
						</div>
						<div class="col-12 col-md-0 input_box">
							${viewData.br_lunch_opened_time_str} - ${viewData.br_lunch_closed_time_str} 
						</div>
					</div>
					<div class="col-12 admin_form_style_0">
						<div class="col-12 col-md-0 label_box">
							<span>기본창구개수</span>
						</div>
						<div class="col-12 col-md-0 input_box">
							${viewData.br_default_counter_num} 
						</div>
					</div>
				</div>
				<div class="col-12 mt30 admin_btn_style_0_con">
						<a href="/reserve/adm/branchManage/list" class="admin_btn_style_0 mr10 bg_grey">
							<span>목록</span>
						</a>
						<a href="/reserve/adm/branchManage/update?br_idx=${viewData.br_idx}" class="admin_btn_style_0 mr10" data-common-view-update-btn>
							<span>수정</span>
						</a>
						<a href="/reserve/adm/branchManage/delete?br_idx=${viewData.br_idx}" class="admin_btn_style_0 cancel_btn" data-common-view-delete-btn>
							<span>삭제</span>
						</a>
				</div>
			</div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>