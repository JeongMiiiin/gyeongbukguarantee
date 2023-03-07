<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>상담예약관리 - 추가</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/reserveManage/update.js'/>"></script>
</head>
<body>

<script>
	let targetStatus = false;		
</script>

<%
	int brIdx = request.getParameter("br_idx") != null ? Integer.parseInt(request.getParameter("br_idx")) : -1;
	String targetDate = request.getParameter("target_date") != null ? request.getParameter("target_date") : "";
	String targetTime = request.getParameter("target_time") != null ? request.getParameter("target_time") : "";
	
	if(brIdx > -1 && targetDate != "" && targetTime != ""){
		%>
		<script>
			targetStatus = true;
			let targetBrIdx = <%=brIdx%>;
			let target_date = '<%=targetDate%>';
			let target_time = '<%=targetTime%>';
		</script>
		<%
	}
	
	pageContext.setAttribute("target_br_idx", brIdx);
	pageContext.setAttribute("target_date", targetDate);
%>

<%@ include file="../inc/header.jsp" %>
    <section class="col-12 admin_contents_wrap">
    	<div class="col-12 admin_contents_con">
    		<h1 class="col-12 page_title">
	    		<a href="/reserve/adm/reserveManage/branchList">상담예약관리</a>
	    	</h1>
	    	<div class="col-12 admin_contents">
				<div class="col-12 admin_form_style_0_wrap">
					<form class="col-12 admin_form_style_0_con" action="/reserve/adm/reserveManage/insertAction" method="post" data-common-update-form>
						<input type="hidden" name="rlStatus" value="1"/>
						<input type="hidden" name="rlDirect" value="2"/>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<label for="rlName">성명</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="rlName" id="rlName" data-common-update-essential-input="성명을 입력해주세요" placeholder="성명을 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="rlHp">휴대전화번호</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="rlHp" id="rlHp" data-common-update-essential-input="휴대전화번호를 입력해주세요" placeholder="전화번호를 '-'없이 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>사업자등록여부</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="rlBusinessType" id="rlBusinessType" class="admin_select_style_0">
										<option value="1" selected>
											개인사업자
										</option>
										<option value="2">
											법인사업자
										</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>영업점</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="brIdx" id="brIdx" class="admin_select_style_0" data-common-update-essential-input="영업점을 선택해주세요">
											<option value=""></option>
											<c:forEach var="list" items="${branchList}" varStatus="status">
												<option value="${list.brIdx}" <c:if test="${list.brIdx eq target_br_idx}">selected</c:if>>
													${list.brName}
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="rlReserveDate">방문일자</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" value="${target_date}" data-common-update-essential-input="방문일자를 선택해주세요" name="rlReserveDate" id="rlReserveDate" autocomplete="off" data-common-datepicker readonly/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>방문시간</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="rlReserveTime" id="rlReserveTime" data-common-update-essential-input="방문시간을 선택해주세요">
									</select>
								</div>
							</div>
						</div>
					</form>
					<div class="col-12 mt30 admin_btn_style_0_con">
						<a href="/reserve/adm/reserveManage/list?br_idx=${target_br_idx}" class="admin_btn_style_0 bg_grey mr10" data-common-update-cancel-btn>
							<span>취소</span>
						</a>
						<a href="javascript:void(0)" class="admin_btn_style_0" data-common-insert-complete-btn>
							<span>등록</span>
						</a>
					</div>
				</div>
	    	</div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>