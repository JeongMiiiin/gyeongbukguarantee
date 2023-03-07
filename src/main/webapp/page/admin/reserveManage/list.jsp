<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>상담예약관리</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/reserveManage/list.js'/>"></script>
</head>
<body>
<script>
	let brIdx = '${branchData.brIdx}';
	let openTime = '${branchData.brOpenedTimeStr}';
	let closeTime = '${branchData.brClosedTimeStr}';
	let lunchOpenTime = '${branchData.brLunchOpenedTimeStr}';
	let lunchCloseTime = '${branchData.brLunchClosedTimeStr}';
	let defaultConsultTime = '${branchData.brDefaultConsultTime}';
	let defaultConsultNum = '${branchData.brDefaultCounterNum}';
	let branchTimeSettingList = [];
	let branchSpecficDateTimeSettingList = [];
	let branchDateList = [];
	let initTime = '${updateData.rlReserveTimeStr}';
	let fullReserveList = [];
	let timeParam;
	let specficDateTimeParam;
</script>
<c:forEach var="timeList" items="${branchData.branchTimeSettingDtoList}">
	<c:choose>
		<c:when test="${timeList.gbtsStatus eq '1'}">
			<script>
				let mondayOpenTime = '${timeList.gbtsStartTimeStr}';
				let mondayCloseTime = '${timeList.gbtsEndTimeStr}';
			</script>
		</c:when>
		<c:when test="${timeList.gbtsStatus eq '2'}">
			<script>
				let tuesdayOpenTime = '${timeList.gbtsStartTimeStr}';
				let tuesdayCloseTime = '${timeList.gbtsEndTimeStr}';
			</script>
		</c:when>
		<c:when test="${timeList.gbtsStatus eq '3'}">
			<script>
				let wedndayOpenTime = '${timeList.gbtsStartTimeStr}';
				let wedndayCloseTime = '${timeList.gbtsEndTimeStr}';
			</script>
		</c:when>
		<c:when test="${timeList.gbtsStatus eq '4'}">
			<script>
				let thursdayOpenTime = '${timeList.gbtsStartTimeStr}';
				let thursdayCloseTime = '${timeList.gbtsEndTimeStr}';
			</script>
		</c:when>
		<c:when test="${timeList.gbtsStatus eq '5'}">
			<script>
				let fridayOpenTime = '${timeList.gbtsStartTimeStr}';
				let fridayCloseTime = '${timeList.gbtsEndTimeStr}';
			</script>
		</c:when>
		<c:when test="${timeList.gbtsStatus eq '6'}">
			<script>
				timeParam = {'gbts_status' : '${timeList.gbtsStatus}', 'gbts_counter_num' : '${timeList.gbtsCounterNum}', 'gbts_start_time' : '${timeList.gbtsStartTimeStr}', 'gbts_end_time' : '${timeList.gbtsEndTimeStr}' }
				branchTimeSettingList.push(timeParam);
			</script>
		</c:when>
		<c:when test="${timeList.gbtsStatus eq '7'}">
			<script>
				specficDateTimeParam = {'gbts_status' : '${timeList.gbtsStatus}', 'gbts_target_date' : '${timeList.gbtsTargetDateStr}', 'gbts_counter_num' : '${timeList.gbtsCounterNum}', 'gbts_start_time' : '${timeList.gbtsStartTimeStr}', 'gbts_end_time' : '${timeList.gbtsEndTimeStr}' }
				branchSpecficDateTimeSettingList.push(specficDateTimeParam);
			</script>
		</c:when>
	</c:choose>
</c:forEach>
<c:forEach var="dateList" items="${branchData.branchDateSettingDtoList}">
	<c:choose>
		<c:when test="${dateList.gbdsStatus eq '2'}">
			<script>
				branchDateList.push('${dateList.gbdsDateStr}');
			</script>
		</c:when>
		<c:when test="${dateList.gbdsStatus eq '3'}">
			<script>
				fullReserveList.push('${dateList.gbdsDateStr}');
			</script>
		</c:when>
	</c:choose>
</c:forEach>


	<%@ include file="../inc/header.jsp" %>
    <section class="col-12 admin_contents_wrap">
    	<h1 class="col-12 page_title">
	    		<a href="/reserve/adm/reserveManage/branchList">상담예약관리</a>
	    	</h1>
	    	<div class="col-12 admin_contents">
				<div class="col-12 admin_relative_calendar_con">
					<input type="text" class="admin_input_style_0" id="dateSetting" name="dateSetting" autocomplete="off" data-common-datepicker readonly/>
					<div class="col-12">
						<div class="col-12 col-lg-6 pr-lg-20 calendar_cont">
							<div class="col-12 admin_relative_calendar_title">날짜선택 (<c:out value="${branchData.brName}"></c:out>지점)</div>
						</div>
						<div class="col-12 col-lg-6 pl-lg-20 time_cont">
							<div class="col-12 admin_relative_calendar_title">예약목록</div>
							<div class="col-12 admin_table_style_0" style="padding: 0;">
								<table class="col-12 time_list">
								<colgroup>
									<col width="100px"/>
									<col width="auto"/>
									<col width="120px"/>
									<col width="100px"/>
									<col width="200px"/>
									<col width="100px"/>
								</colgroup>
								<thead>
									<tr>
										<th>상태</th>
										<th>예약자명</th>
										<th>연락처</th>
										<th>예약시간</th>
										<th>관리</th>
										<th>음성</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
							</div>
						</div>
					</div>
				</div>
	    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>