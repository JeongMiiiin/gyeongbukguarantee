<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>상담지점관리 - 등록</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/branch/update.js'/>"></script>
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
<c:set var="timeList">09:00,09:10,09:20,09:30,09:40,09:50,10:00,10:10,10:20,10:30,10:40,10:50,11:00,11:10,11:20,11:30,11:40,11:50,12:00,12:10,12:20,12:30,12:40,12:50,13:00,13:10,13:20,13:30,13:40,13:50,14:00,14:10,14:20,14:30,14:40,14:50,15:00,15:10,15:20,15:30,15:40,15:50,16:00,16:10,16:20,16:30,16:40,16:50,17:00,17:10,17:20,17:30,17:40,17:50,18:00</c:set>
    <section class="col-12 admin_contents_wrap">
    	<div class="col-12 admin_contents_con">
    		<h1 class="col-12 page_title">
	    		<a href="/reserve/adm/branchManage/list">상담지점관리</a>
	    	</h1>
	    	<div class="col-12 admin_contents">
				<div class="col-12 admin_form_style_0_wrap">
					<form class="col-12 admin_form_style_0_con" action="/reserve/adm/branchManage/insertAction" method="post" data-common-update-form>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<label for="brName">영업점명</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brName" id="brName" data-common-update-essential-input="영업점명을 입력해주세요" placeholder="영업점명을 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brCategoryName">관할지역</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brCategoryName" id="brCategoryName" data-common-update-essential-input="관할지역을 입력해주세요" placeholder="관할지역을 입력해주세요 (','로 구분)"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brAddress">주소</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-420 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brAddress" id="brAddress" data-common-update-essential-input="주소를 입력해주세요" placeholder="주소를 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brHp">전화번호</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-420 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brHp" id="brHp" data-common-update-essential-input="전화번호를 입력해주세요" placeholder="전화번호를 '-'없이 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>담당자</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="memIdx" id="memIdx" class="admin_select_style_0" data-common-update-essential-input="담당자를 선택해주세요">
										<option value=""></option>
										<c:forEach var="managerList" items="${managerList}">
										<option value="${managerList.memIdx}">
											${managerList.memUserName}
										</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0 type_select_time_range">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>운영시간</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brOpenedTime" id="brOpenedTime" data-common-update-essential-input="운영 시작시간을 선택해주세요">
										<c:forEach var="timeValue" items="${timeList}" varStatus="status">
										<option value="${timeValue}" <c:if test="${status.first}">selected</c:if>>
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-0 addr_text">-</div>
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brClosedTime" id="brClosedTime" data-common-update-essential-input="운영 마감시간을 선택해주세요">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}" varStatus="status">
										<option value="${timeValue}" <c:if test="${status.last}">selected</c:if>>
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0 type_select_time_range">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>점심시간</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brLunchOpenedTime" id="brLunchOpenedTime" data-common-update-essential-input="점심 시작시간을 선택해주세요">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}">
										<option value="${timeValue}" <c:if test="${timeValue eq '12:00' }">selected</c:if>>
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-0 addr_text">-</div>
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brLunchClosedTime" id="brLunchClosedTime" data-common-update-essential-input="점심 마감시간을 선택해주세요">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}">
										<option value="${timeValue}" <c:if test="${timeValue eq '13:00' }">selected</c:if>>
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brDefaultCounterNum">기본창구개수</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="number" class="admin_input_style_0" id="brDefaultCounterNum" name="brDefaultCounterNum" value="5" min="1" max="5" data-common-update-essential-input="기본창구 개수를 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0 type_select_time_range">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>시간대별 창구설정</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="settingOpenTime" id="settingOpenTime">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}">
										<option value="${timeValue}">
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-0 addr_text">-</div>
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="settingClosedTime" id="settingClosedTime">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}">
										<option value="${timeValue}">
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-12 col-md-0 ml-md-10 plus_counter_con">
									<div class="col-12 mr10 mw-md-100 admin_input_style_0_con">
										<input type="number" class="admin_input_style_0" id="timezoneCounterNum" name="timezoneCounterNum" value="1" min="1" max="5"/>
									</div>
									<a id="timezonePlusBtn" href="javascript:void(0)" class="admin_btn_style_1">
										<span>추가</span>
									</a>
								</div>
								<ul class="col-12 custom_select_list time">
									<input type="hidden" name="branchTimeSettingDtoList"/>
								</ul>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<label for="dateSetting">특정일별 창구설정</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" id="dateSetting" name="dateSetting" autocomplete="off" data-common-datepicker readonly/>
								</div>
								<div class="col-12 col-md-0 ml-md-10 plus_counter_con">
									<div class="col-12 mr10 mw-md-100 admin_input_style_0_con">
										<input type="number" class="admin_input_style_0" id="dateCounterNum" name="dateCounterNum" value="1" min="1" max="5"/>
									</div>
									<a id="datePlusBtn" href="javascript:void(0)" class="admin_btn_style_1">
										<span>추가</span>
									</a>
								</div>
								<ul class="col-12 custom_select_list date">
									<input type="hidden" name="branchDateSettingDtoList"/>
								</ul>
							</div>
						</div>
					</form>
					<div class="col-12 mt30 admin_btn_style_0_con">
							<a href="/reserve/adm/branchManage/list" class="admin_btn_style_0 bg_grey mr10" data-common-update-cancel-btn>
								<span>취소</span>
							</a>
							<a href="javascript:void(0)" class="admin_btn_style_0 mr10" data-common-insert-complete-btn>
								<span>등록</span>
							</a>
							<a href="javascript:void(0)" class="admin_btn_style_0 cancel_btn" data-common-insert-complete-btn>
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