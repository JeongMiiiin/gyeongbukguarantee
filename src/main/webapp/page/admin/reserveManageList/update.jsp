<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="../"></c:set> 
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>상담지점관리 - 수정</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/reserveManageList/update.js'/>"></script>
</head>
<body>

<%@ include file="../inc/header.jsp" %>
    <section class="col-12 admin_contents_wrap">
    	<div class="col-12 admin_contents_con">
    		<h1 class="col-12 page_title">
	    		<a href="/reserve/adm/reserveManageList/list">상담예약목록</a>
	    	</h1>
	    	<div class="col-12 admin_contents">
				<div class="col-12 admin_form_style_0_wrap">
					<form class="col-12 admin_form_style_0_con" action="/reserve/adm/reserveManageList/updateAction" method="post" data-common-update-form>
						<input type="hidden" id="rlIdx" name="rlIdx" value="${updateData.rlIdx}"/>
						<input type="hidden" name="rlStatus" value="${updateData.rlStatus}"/>
						<input type="hidden" name="rlDirect" value="${updateData.rlDirect}"/>
						<input type="hidden" name="rlPrevReserveDate" value="${updateData.rlReserveDateStr}"/>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="rlName">성명</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="rlName" id="rlName" value="${updateData.rlName}" data-common-update-essential-input="성명을 입력해주세요" placeholder="성명을 입력해주세요" <c:if test="${updateData.rlDirect eq '1'}">readonly</c:if>/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="rlHp">휴대전화번호</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="rlHp" id="rlHp" value="${updateData.rlHp}" data-common-update-essential-input="휴대전화번호를 입력해주세요" placeholder="전화번호를 '-'없이 입력해주세요" <c:if test="${updateData.rlDirect eq '1'}">readonly</c:if>/>
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
										<option value="1" <c:if test="${updateData.rlBusinessType eq '1'}">selected</c:if>>개인사업자</option>
										<option value="2" <c:if test="${updateData.rlBusinessType eq '2'}">selected</c:if>>법인사업자</option>
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
										<select name="brIdx" id="brIdx" class="admin_select_style_0">
											<c:forEach var="list" items="${branchList}">
												<option value="${list.brIdx}" <c:if test="${list.brIdx eq updateData.brIdx}">selected</c:if>>
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
									<input type="hidden" name="prevRlReserveDate" value="${updateData.rlReserveDateStr}"/>
									<input type="text" class="admin_input_style_0" value="${updateData.rlReserveDateStr}" data-common-update-essential-input="방문일자를 선택해주세요" name="rlReserveDate" id="rlReserveDate" autocomplete="off" data-common-datepicker readonly/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>방문시간</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<input type="hidden" name="prevRlReserveTime" value="${updateData.rlReserveTimeStr}"/>
									<select name="rlReserveTime" id="rlReserveTime" data-common-update-essential-input="방문시간을 선택해주세요">
									</select>
								</div>
							</div>
						</div>
					</form>
					<div class="col-12 mt30 admin_btn_style_0_con">
						<a href="/reserve/adm/reserveManageList/list?${searchDto.qustr}" class="admin_btn_style_0 bg_grey mr10" data-common-update-cancel-btn>
							<span>취소</span>
						</a>
						<a href="javascript:void(0)" class="admin_btn_style_0 mr10" data-common-update-complete-btn>
							<span>수정</span>
						</a>
						<a href="/reserve/adm/reserveManageList/delete?rl_idx=${updateData.rlIdx}" class="admin_btn_style_0 cancel_btn" data-common-update-delete-btn>
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