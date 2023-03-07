<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko" style="font-size: 10px;">
	<head>
	    <meta charset="UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>경북신용보증재단 전광판</title>
	    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	    <link rel="stylesheet" href="<c:url value='/reserve/common/css/responsiveJM.css'/>"/>
	    <link rel="stylesheet" href="<c:url value='/reserve/client/css/consult.css'/>"/>
	    <script type="text/javascript" src="<c:url value='/reserve/common/js/jquery-3.6.0.min.js'/>"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/howler/2.2.1/howler.min.js"></script>
	    <script type="text/javascript" src="<c:url value='/reserve/common/js/parsingControl.js'/>"></script>
	    <script type="text/javascript" src="<c:url value='/reserve/common/js/TimeControl.js'/>"></script>
	    <script type="text/javascript" src="<c:url value='/reserve/client/js/reserve/billboard.js'/>"></script>
	</head>
	<%
	String brIdx = request.getParameter("br_idx");
	%>
	<script>
		let brIdx = <%=brIdx%>;
		let openTime = '${branchData.brOpenedTimeStr}';
		let closeTime = '${branchData.brClosedTimeStr}';
		let lunchOpenTime = '${branchData.brLunchOpenedTimeStr}';
		let lunchCloseTime = '${branchData.brLunchClosedTimeStr}';
		let defaultConsultNum = '${branchData.brDefaultCounterNum}';
		let defaultConsultTime = '${branchData.brDefaultConsultTime}';
		let branchTimeSettingList = [];
		let branchSpecficDateTimeSettingList = [];
		let branchDateList = [];
		let fullReserveList = [];
		let timeParam;
		let specficDateTimeParam;
		let billboardFullDataList = [];
		let billboardFullDataParam;
		let prevAudioList = [];
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
					specficDateTimeParam = {'gbts_status' : '${timeList.gbtsStatus}', 'gbts_target_date_str' : '${timeList.gbtsTargetDateStr}', 'gbts_counter_num' : '${timeList.gbtsCounterNum}', 'gbts_start_time' : '${timeList.gbtsStartTimeStr}', 'gbts_end_time' : '${timeList.gbtsEndTimeStr}' }
					branchSpecficDateTimeSettingList.push(specficDateTimeParam);
				</script>
			</c:when>
		</c:choose>
	</c:forEach>
	
	<c:forEach var="dateList" items="${branchData.branchDateSettingDtoList}">
		<c:if test="${list.gbdsStatus eq '2'}">
			<script>
				branchDateList.push('${dateList.gbdsDateStr}');
			</script>
		</c:if>
		<c:if test="${list.gbdsStatus eq '3'}">
			<script>
				fullReserveList.push('${dateList.gbdsDateStr}');
			</script>
		</c:if>
	</c:forEach>
	<c:forEach var="reserveList" items="${reserveList}">
		<script>
			billboardFullDataParam = {'rl_idx' : ${reserveList.rl_idx}, 'rl_reserve_time' : '${reserveList.rl_reserve_time_str}', 'rl_name' : '${reserveList.rl_name}', 'rl_status' : '${reserveList.rl_status}'};
			billboardFullDataList.push(billboardFullDataParam);
		</script>
		<c:if test="${reserveList.rl_status eq '2'}">
			<script>
				prevAudioList.push(${reserveList.rl_idx});
			</script>
		</c:if>
	</c:forEach>
	<body>
		<div style="width: 0; height: 0; position: absolute; top: 0; left: 0; opacity: 0;" id="checkAudio"></div>
		<section class="col-12 billboard_wrap">
			<div class="col-12 billboard_title_con">
				<img src="<c:url value='/reserve/client/img/billboard_logo.png'/>" alt="경북 로고">
				<h1 class="font_nanumGothic">${branchData.brName} 지점 상담 예약 현황</h1>
				<img src="<c:url value='/reserve/client/img/logo_3x.png'/>" alt="경북신용보증재단 로고">
			</div>
			<div class="col-12 billboard_contents_wrap">
				<div class="col-12 col-md-4 billboard_contents_con">
					<div class="col-12 billboard_contents">
						<div class="col-12 billboard_contents_inner">
							<div class="col-12 billboard_contents_box">
								<div class="col-12 billboard_contents_title">
									<span class="font_nanumGothic">상담예약현황</span>
								</div>
								<div class="col-12 billboard_contents_line">
									<span class="arrow_btn">
									</span>
								</div>
								<ul class="col-12 billboard_reserve_list">
									<li>
										<div class="col-4">10:00</div>
										<div class="col-4">홍길동</div>
										<div class="col-4 end">상담종료</div>
									</li>
									<li>
										<div class="col-4">10:30</div>
										<div class="col-4">홍길동</div>
										<div class="col-4 end">상담종료</div>
									</li>
									<li>
										<div class="col-4">11:00</div>
										<div class="col-4">홍길동</div>
										<div class="col-4">상담중</div>
									</li>
									<li>
										<div class="col-4">11:30</div>
										<div class="col-4">홍길동</div>
										<div class="col-4 wait">대기중</div>
									</li>
									<li>
										<div class="col-4">12:00</div>
										<div class="col-4">홍길동</div>
										<div class="col-4 wait">대기중</div>
									</li>
									<li>
										<div class="col-4">12:30</div>
										<div class="col-4">홍길동</div>
										<div class="col-4 wait">대기중</div>
									</li>
								</ul>
							</div>
						</div>	
					</div>	
				</div>
			</div>
			<div class="col-12 billboard_footer">
				<%-- <div class="col-0 logo_con">
					<img src="<c:url value='/reserve/common/img/logo_white.png'/>" alt="전광판 로고"/>
				</div> --%>
				<div class="col-12 footer_desc">
					카카오채널, 유튜브 등 SNS채널을 통해 경북신용보증재단의 새로운 소식을 받아보실 수 있습니다.
				</div>
			</div>
		</section>
	</body>
</html>