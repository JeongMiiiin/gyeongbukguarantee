<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	Calendar operationTimeTargetDate = Calendar.getInstance();
	operationTimeTargetDate.setTime(new Date());
	operationTimeTargetDate.add(Calendar.DATE, 7);

	String operationTimeDate = dateFormatter.format(operationTimeTargetDate.getTime());
	String nowDate = dateFormatter.format(new Date());
	pageContext.setAttribute("nowDate", nowDate);
%> 
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
<script>
	let reserve1DateList = [];
	let reserve2DateList = [];
	let reserve3DateList = [];
	let reserve4DateList = [];
	let reserve5DateList = [];
	let reserveIdx = 0;
	let reserveTime = "00:00";
</script>
<c:forEach var="reserveList" items="${reserveList}">
	<script>
		if(reserveTime != '${reserveList.rlReserveTimeStr}'){
			reserveIdx = 1;
			reserveTime = '${reserveList.rlReserveTimeStr}';
		} else {
			reserveIdx++;
		}
		switch(reserveIdx){
			case 1 :
				reserve1DateList.push('${reserveList.rlReserveDateStr}');
				break;
			case 2 :
				reserve2DateList.push('${reserveList.rlReserveDateStr}');
				break;
			case 3 :
				reserve3DateList.push('${reserveList.rlReserveDateStr}');
				break;
			case 4 :
				reserve4DateList.push('${reserveList.rlReserveDateStr}');
				break;
			case 5 :
				reserve5DateList.push('${reserveList.rlReserveDateStr}');
				break;
		}
	</script>
</c:forEach>

<c:set var="timeList">09:00,09:10,09:20,09:30,09:40,09:50,10:00,10:10,10:20,10:30,10:40,10:50,11:00,11:10,11:20,11:30,11:40,11:50,12:00,12:10,12:20,12:30,12:40,12:50,13:00,13:10,13:20,13:30,13:40,13:50,14:00,14:10,14:20,14:30,14:40,14:50,15:00,15:10,15:20,15:30,15:40,15:50,16:00,16:10,16:20,16:30,16:40,16:50,17:00,17:10,17:20,17:30,17:40,17:50,18:00</c:set>
    <section class="col-12 admin_contents_wrap">
    	<div class="col-12 admin_contents_con">
    		<h1 class="col-12 page_title">
	    		<a href="/reserve/adm/branchManage/list">상담지점관리</a>
	    	</h1>
	    	<div class="col-12 admin_contents">
				<div class="col-12 admin_form_style_0_wrap">
					<form class="col-12 admin_form_style_0_con" action="/reserve/adm/branchManage/updateAction" method="post" data-common-update-form>
						<input type="hidden" id="brIdx" name="brIdx" value="${updateData.brIdx}"/>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brName">영업점명</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brName" id="brName" value="${updateData.brName}" data-common-update-essential-input="영업점명을 입력해주세요" placeholder="영업점명을 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brCategoryName">관할지역</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brCategoryName" id="brCategoryName" value="${updateData.brCategoryName}" data-common-update-essential-input="관할지역을 입력해주세요" placeholder="관할지역을 입력해주세요 (','로 구분)"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brAddress">주소</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-420 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brAddress" id="brAddress" value="${updateData.brAddress}" placeholder="주소를 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brHp">전화번호</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-420 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brHp" id="brHp" value="${updateData.brHp}" placeholder="전화번호를 '-'없이 입력해주세요"/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>담당자</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="memIdx" id="memIdx" class="admin_select_style_0">
										<option value=""></option>
										<c:forEach var="managerList" items="${updateData.memberAdminDtoList}">
										<option value="${managerList.memIdx}" <c:if test="${managerList.memIdx eq updateData.memIdx}">selected</c:if>>
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
								<input type="hidden" name="brPrevOpenedTime" value="${updateData.brOpenedTimeStr}"/>
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brOpenedTime" id="brOpenedTime">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}">
										<option value="${timeValue}" <c:if test="${timeValue eq updateData.brOpenedTimeStr}">selected</c:if>>
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-0 addr_text">
									-
								</div>
								<input type="hidden" name="brPrevClosedTime" value="${updateData.brClosedTimeStr}"/>
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brClosedTime" id="brClosedTime">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}">
										<option value="${timeValue}" <c:if test="${timeValue eq updateData.brClosedTimeStr}">selected</c:if>>
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
								<input type="hidden" name="brPrevLunchOpenedTime" value="${updateData.brLunchOpenedTimeStr}"/>
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brLunchOpenedTime" id="brLunchOpenedTime">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}">
										<option value="${timeValue}" <c:if test="${timeValue eq updateData.brLunchOpenedTimeStr}">selected</c:if>>
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-0 addr_text">
									-
								</div>
								<input type="hidden" name="brPrevLunchClosedTime" value="${updateData.brLunchClosedTimeStr}"/>
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brLunchClosedTime" id="brLunchClosedTime">
										<option value=""></option>
										<c:forEach var="timeValue" items="${timeList}">
										<option value="${timeValue}" <c:if test="${timeValue eq updateData.brLunchClosedTimeStr}">selected</c:if>>
											${timeValue}
										</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<input type="hidden" class="admin_input_style_0" id="prevBrDefaultCounterNum" name="prevBrDefaultCounterNum" value="${updateData.brDefaultCounterNum}" min="1" max="5"/>
								<input type="hidden" class="admin_input_style_0" id="prevBrDefaultCounterNumPossibleDate" name="prevBrDefaultCounterNumPossibleDate"/>
								<label for="brDefaultCounterNum">기본창구개수</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="number" class="admin_input_style_0" id="brDefaultCounterNum" name="brDefaultCounterNum" value="${updateData.brDefaultCounterNum}" min="1" max="5" data-common-update-essential-input="기본창구 개수를 입력해주세요"/>
								</div>
								<div class="col-12 pt10 update_info_text" style="display: none;" id="defaultCounterInfo">
									<span></span>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0 type_select_time_range">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>
									시간대별 창구설정
								</span>
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
										<input type="number" class="admin_input_style_0" id="timezoneCounterNum" name="timezoneCounterNum" value="1" min="1" max=""/>
									</div>
									<a id="timezonePlusBtn" href="javascript:void(0)" class="admin_btn_style_1">
										<span>추가</span>
									</a>
								</div>
								<ul class="col-12 custom_select_list time">
									<input type="hidden" name="branchTimeSettingDtoList"/>
									<c:forEach var="timeSettingList" items="${updateData.branchTimeSettingDtoList}">
									<li data-time-setting="{'startTime' : '${timeSettingList.gbtsStartTimeStr}', 'endTime' : '${timeSettingList.gbtsEndTimeStr}', 'counterNum' : '${timeSettingList.gbtsCounterNum}', 'appliedAt' : '${timeSettingList.gbtsAppliedAtStr }'}">
										${timeSettingList.gbtsStartTimeStr} - ${timeSettingList.gbtsEndTimeStr} 창구 ${timeSettingList.gbtsCounterNum}개
										<a href="javascript:void(0)" class="admin_btn_style_1" onclick="branchUpdate.deleteSettingList($(this).parent(), 1)">
											<span>삭제</span>
										</a>
										<c:if test="${timeSettingList.gbtsAppliedAtStr > nowDate}">
											<div class="ml3 vm show update_info_text">
												<span>${timeSettingList.gbtsAppliedAtStr} 적용</span>
											</div>
										</c:if>
									</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<label for="dateSetting">
									특정일별 창구설정
								</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" id="dateSetting" name="dateSetting" autocomplete="off" data-common-datepicker readonly/>
								</div>
								<div class="col-12 col-md-0 ml-md-10 plus_counter_con">
									<div class="col-12 mr10 mw-md-100 admin_input_style_0_con">
										<input type="number" class="admin_input_style_0" id="dateCounterNum" name="dateCounterNum" value="1" min="1" max=""/>
									</div>
									<a id="datePlusBtn" href="javascript:void(0)" class="admin_btn_style_1">
										<span>추가</span>
									</a>
								</div>
								<ul class="col-12 custom_select_list date">
									<input type="hidden" name="branchDateSettingDtoList"/>
									<c:forEach var="dateSettingList" items="${updateData.branchDateSettingDtoList}">
									<li data-date-setting="{'selectDate' : '${dateSettingList.gbdsDateStr}', 'counterNum' : '${dateSettingList.gbdsCounterNum}'}">
										${dateSettingList.gbdsDateStr} 창구 ${dateSettingList.gbdsCounterNum}개
										<a href="javascript:void(0)" class="admin_btn_style_1" onclick="branchUpdate.deleteSettingList($(this).parent(), 0)">
											<span>삭제</span>
										</a>
									</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</form>
					<div class="col-12 mt30 admin_btn_style_0_con">
						<a href="/reserve/adm/branchManage/list" class="admin_btn_style_0 bg_grey mr10" data-common-update-cancel-btn>
							<span>취소</span>
						</a>
						<a href="javascript:void(0)" class="admin_btn_style_0 mr10" data-common-update-complete-btn>
							<span>수정</span>
						</a>
						<a href="/reserve/adm/branchManage/delete?br_idx=${updateData.brIdx}" class="admin_btn_style_0 cancel_btn" data-common-update-delete-btn>
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