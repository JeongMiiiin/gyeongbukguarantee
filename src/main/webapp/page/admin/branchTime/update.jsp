<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	Calendar weekTimeTargetDate = Calendar.getInstance();
	weekTimeTargetDate.setTime(new Date());
	weekTimeTargetDate.add(Calendar.DATE, 1);

	String weekTimeDate = dateFormatter.format(weekTimeTargetDate.getTime());
	
	Calendar consultTimeTargetDate = Calendar.getInstance();
	consultTimeTargetDate.setTime(new Date());
	consultTimeTargetDate.add(Calendar.MONTH, 1);
	consultTimeTargetDate.set(Calendar.DAY_OF_MONTH,1);

	String consultTimeDate = dateFormatter.format(consultTimeTargetDate.getTime());
%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>상담시간관리 - 수정</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/branchTime/update.js'/>"></script>
</head>
<body>

<c:set var="timeList">09:00,09:10,09:20,09:30,09:40,09:50,10:00,10:10,10:20,10:30,10:40,10:50,11:00,11:10,11:20,11:30,11:40,11:50,12:00,12:10,12:20,12:30,12:40,12:50,13:00,13:10,13:20,13:30,13:40,13:50,14:00,14:10,14:20,14:30,14:40,14:50,15:00,15:10,15:20,15:30,15:40,15:50,16:00,16:10,16:20,16:30,16:40,16:50,17:00,17:10,17:20,17:30,17:40,17:50,18:00</c:set>


<script>
	let reserveList= [];

</script>

<c:forEach var="list" items="${reserveList}">
		<script>
		reserveList.push('${list.rlReserveDateStr}');
		</script>
</c:forEach>

<%@ include file="../inc/header.jsp" %>
    
    <section class="col-12 admin_contents_wrap">
    	<div class="col-12 admin_contents_con">
    		<h1 class="col-12 page_title">
	    		<a href="/reserve/adm/branchTimeManage/list">상담시간관리</a>
	    	</h1>
	    	<div class="col-12 admin_contents">
				<div class="col-12 admin_form_style_0_wrap">
					<form class="col-12 admin_form_style_0_con" action="/reserve/adm/branchTimeManage/updateAction" method="post" data-common-update-form>
						<input type="hidden" id="brIdx" name="brIdx" value="${updateData.brIdx}"/>
						<input type="hidden" id="brDefaultCounterNum" name="brDefaultCounterNum" value="${updateData.brDefaultCounterNum}"/>
						<input type="hidden" id="memIdx" name="memIdx" value="${updateData.memIdx}"/>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box type_essential">
								<label for="brName">영업점명</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" name="brName" id="brName" value="${updateData.brName}" data-common-update-essential-input="영업점명을 입력해주세요" placeholder="영업점명을 입력해주세요" readonly/>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0 type_select_time_range">
							<div class="col-12 col-md-0 label_box type_essential">
								<span>
									 요일별 시간설정<br/>
									 (월 ~ 금)
								</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<input type="hidden" name="branchTimeSettingDtoList"/>
								<div class="col-12 mb20 update_info_text">
									<span>변경시 적용시점 : <%=weekTimeDate%></span>
								</div>
								<div class="col-12 mb10 custom_select_list time" data-time-setting="{'startTime' : '${updateData.branchTimeSettingDtoList[0].gbtsStartTimeStr}', 'endTime' : '${updateData.branchTimeSettingDtoList[0].gbtsEndTimeStr}', 'gbtsStatus' : '1'}">
									<div class="col-0 mt14 mr10 day_of_week">월</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsOpenedTime0" id="gbtsOpenedTime0" data-common-update-essential-input="월요일 운영시작시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[0].gbtsStartTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-0 mt12 addr_text">-</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsClosedTime0" id="gbtsClosedTime0" data-common-update-essential-input="월요일 운영마감시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[0].gbtsEndTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-12 mb10 custom_select_list time" data-time-setting="{'startTime' : '${updateData.branchTimeSettingDtoList[1].gbtsStartTimeStr}', 'endTime' : '${updateData.branchTimeSettingDtoList[1].gbtsEndTimeStr}', 'gbtsStatus' : '2'}">
									<div class="col-0 mt14 mr10 day_of_week">화</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbts_opened_time1" id="gbts_opened_time1" data-common-update-essential-input="화요일 운영시작시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[1].gbtsStartTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-0 mt12 addr_text">-</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsClosedTime1" id="gbtsClosedTime1" data-common-update-essential-input="화요일 운영마감시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[1].gbtsEndTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-12 mb10 custom_select_list time" data-time-setting="{'startTime' : '${updateData.branchTimeSettingDtoList[2].gbtsStartTimeStr}', 'endTime' : '${updateData.branchTimeSettingDtoList[2].gbtsEndTimeStr}', 'gbtsStatus' : '3'}">
									<div class="col-0 mt14 mr10 day_of_week">수</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsOpenedTime2" id="gbtsOpenedTime2" data-common-update-essential-input="수요일 운영시작시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[2].gbtsStartTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-0 mt12 addr_text">-</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsClosedTime2" id="gbtsClosedTime2" data-common-update-essential-input="수요일 운영마감시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[2].gbtsEndTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-12 mb10 custom_select_list time" data-time-setting="{'startTime' : '${updateData.branchTimeSettingDtoList[3].gbtsStartTimeStr}', 'endTime' : '${updateData.branchTimeSettingDtoList[3].gbtsEndTimeStr}', 'gbtsStatus' : '4'}">
									<div class="col-0 mt14 mr10 day_of_week">목</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsOpenedTime3" id="gbtsOpenedTime3" data-common-update-essential-input="목요일 운영시작시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[3].gbtsStartTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-0 mt12 addr_text">-</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsClosedTime3" id="gbtsClosedTime3" data-common-update-essential-input="목요일 운영마감시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[3].gbtsEndTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-12 mb10 custom_select_list time" data-time-setting="{'startTime' : '${updateData.branchTimeSettingDtoList[4].gbtsStartTimeStr}', 'endTime' : '${updateData.branchTimeSettingDtoList[4].gbtsEndTimeStr}', 'gbtsStatus' : '5'}">
									<div class="col-0 mt14 mr10 day_of_week">금</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsOpenedTime4" id="gbtsOpenedTime4" data-common-update-essential-input="금요일 운영시작시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[4].gbtsStartTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-0 mt12 addr_text">-</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="gbtsClosedTime4" id="gbtsClosedTime4" data-common-update-essential-input="금요일 운영마감시간을 선택해주세요">
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}" <c:if test="${timeValue eq updateData.branchTimeSettingDtoList[4].gbtsEndTimeStr}">selected</c:if>>
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<span>기본 상담시간</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<input type="hidden" name="brPrevDefaultConsultTime" value="${updateData.brDefaultConsultTime}"/>
								<div class="col-12 mw-200 admin_select_style_0_wrap">
									<select name="brDefaultConsultTime" data-common-update-essential-input="기본 상담시간을 선택해주세요.">
										<option value="20" <c:if test="${updateData.brDefaultConsultTime eq 20}">selected</c:if>>20분</option>
										<option value="30" <c:if test="${updateData.brDefaultConsultTime eq 30}">selected</c:if>>30분</option>
										<option value="40" <c:if test="${updateData.brDefaultConsultTime eq 40}">selected</c:if>>40분</option>
									</select>
								</div>
								<div class="col-12 mt10 update_info_text">
									<span>변경시 적용시점 : <%=consultTimeDate%></span>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<label for="dateSetting">휴무일 설정</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" id="dateSetting" name="dateSetting" autocomplete="off" data-common-datepicker readonly/>
								</div>
								<div class="col-12 col-md-0 ml-md-10 mt10 plus_counter_con">
									<a id="datePlusBtn" href="javascript:void(0)" class="admin_btn_style_1">
										<span>추가</span>
									</a>
								</div>
								<ul class="col-12 custom_select_list date">
									<input type="hidden" name="branchDateSettingDtoList"/>
									<c:forEach var="dateSettingList" items="${updateData.branchDateSettingDtoList}">
									<li data-date-setting="{'selectDate' : '${dateSettingList.gbdsDateStr}'}">
										${dateSettingList.gbdsDateStr}<a href="javascript:void(0)" class="admin_btn_style_1" onclick="branchTimeUpdate.deleteSettingList($(this).parent(), true)"><span>삭제</span></a>
									</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="col-12 admin_form_style_0 type_select_time_range">
							<div class="col-12 col-md-0 label_box">
								<label for="specficDateSetting">
									특정일<br/>
									특정시간대<br/>
									창구설정
								</label>
							</div>
							<div class="col-12 col-md-0 input_box">
								<div class="col-12 mb10 mw-200 admin_input_style_0_con">
									<input type="text" class="admin_input_style_0" id="specficDateSetting" name="specficDateSetting" autocomplete="off" data-common-datepicker readonly/>
								</div>
								<div class="col-12">
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="specficDateSettingOpenTime" id="specficDateSettingOpenTime">
											<option value=""></option>
											<c:forEach var="timeValue" items="${timeList}">
											<option value="${timeValue}">
												${timeValue}
											</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-0 mt12 addr_text">-</div>
									<div class="col-12 mw-200 admin_select_style_0_wrap">
										<select name="specficDateSettingClosedTime" id="specficDateSettingClosedTime">
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
											<input type="number" class="admin_input_style_0" id="specficDateCounterNum" name="specficDateCounterNum" value="1" min="1" max=""/>
										</div>
										<a id="specficDatePlusBtn" href="javascript:void(0)" class="admin_btn_style_1">
											<span>추가</span>
										</a>
									</div>
									<ul class="col-12 custom_select_list specfic_date_time">
										<input type="hidden" name="branchSpecficDateTimeSettingList"/>
										<c:forEach var="specficDateTimeSettingList" items="${updateData.branchSpecficDateTimeSettingDtoList}">
										<li data-time-setting="{'selectDate' : '${specficDateTimeSettingList.gbtsTargetDateStr}','startTime' : '${specficDateTimeSettingList.gbtsStartTimeStr}', 'endTime' : '${specficDateTimeSettingList.gbtsEndTimeStr}', 'counterNum' : '${specficDateTimeSettingList.gbtsCounterNum}'}">
											${specficDateTimeSettingList.gbtsTargetDateStr} ${specficDateTimeSettingList.gbtsStartTimeStr} - ${specficDateTimeSettingList.gbtsEndTimeStr} 창구 ${specficDateTimeSettingList.gbtsCounterNum}개
											<a href="javascript:void(0)" class="admin_btn_style_1" onclick="branchTimeUpdate.deleteSettingList($(this).parent(), false)">
												<span>삭제</span>
											</a>
										</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						<div class="col-12 admin_form_style_0">
							<div class="col-12 col-md-0 label_box">
								<span>전광판 주소</span>
							</div>
							<div class="col-12 col-md-0 input_box">
								<a href="/reserve/billboard?br_idx=${updateData.brIdx}" target="_blank" style="display: inline-block; margin-top: 10px; text-decoration: underline; color: #005fc1;">
									전광판 접속
								</a>
							</div>
						</div>
					</form>
					<div class="col-12 mt30 admin_btn_style_0_con">
							<a href="/reserve/adm/branchTimeManage/list" class="admin_btn_style_0 bg_grey mr10" data-common-update-cancel-btn>
								<span>취소</span>
							</a>
							<a href="javascript:void(0)" class="admin_btn_style_0" data-common-update-complete-btn>
								<span>수정</span>
							</a>
						</div>
				</div>
	    	</div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>