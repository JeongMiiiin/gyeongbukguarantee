<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% 
response.setHeader("Cache-Control","no-store"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0); 
if (request.getProtocol().equals("HTTP/1.1"))
        response.setHeader("Cache-Control", "no-cache");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<title>경북신용보증재단 보증상담예약신청</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
    <script type="text/javascript" src="<c:url value='/reserve/client/js/reserve/step.js'/>"></script>
</head>
<body>
	<%
	
	/* session.setAttribute("rlName","정민");
	session.setAttribute("rlHp","01075310206"); */
	
	if( session.getAttribute("rlName") == null ||  session.getAttribute("rlHp") == null ) {
		%>
		<script>
			$(function(){
				alert('잘못된 접근입니다. 본인 인증 후 다시 시도해주세요.');
				window.location.href="/reserve/step?step_idx=4";
			})
		</script>
		
		<%	
		return;
	}
	
	if(request.getParameter("br_idx") == null) {
		%>
		<script>
			$(function(){
				alert('잘못된 접근입니다. 지점선택 후 다시 시도해주세요.');
				window.location.href="/reserve/step?step_idx=5";
			})
		</script>
		<%	
		return;
	}

	int step = request.getParameter("step_idx") != null ? Integer.parseInt(request.getParameter("step_idx")) : -1;
	String rlBusinessType = request.getParameter("rl_business_type");
	String rlName = (String) session.getAttribute("rlName");
	String rlHp = (String) session.getAttribute("rlHp");
	String brIdx = request.getParameter("br_idx");
	%>
	<script>
		$(function(){
			window.onpageshow = function(event){
				if(event.persisted || (window.performance && window.performance.navigation.type == 2)){
					alert('뒤로가기로 접근은 불가 합니다.');
					window.location.href='/reserve/main';
				}
			}
		})
		
	
		let curStep = <%=step%>;
		let curBrIdx = <%=brIdx%>;
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
		<c:if test="${dateList.gbdsStatus eq '2'}">
			<script>
				branchDateList.push('${dateList.gbdsDateStr}');
			</script>
		</c:if>
		<c:if test="${dateList.gbdsStatus eq '3'}">
			<script>
				fullReserveList.push('${dateList.gbdsDateStr}');
			</script>
		</c:if>
	</c:forEach>
    <%@ include file="../inc/header.jsp" %>
    <section class="wrap">
    	<input type="hidden" name="rlName" id="rlName" value="<%=rlName%>"/>
    	<input type="hidden" name="rlHp" id="rlHp" value="<%=rlHp%>"/>
    	<input type="hidden" name="rlBusinessType" id="rlBusinessType" value="<%=rlBusinessType%>"/>
    	<input type="hidden" name="brName" id="brName" value="${branchData.brName}"/>
    	<input type="hidden" name="brAddress" id="brAddress" value="${branchData.brAddress}"/>
        <div class="content">
        	<ul class="tab_title">
	            <li class="tab_li prev">상담안내</li>
	            <li class="tab_li prev">이용안내</li>
	            <li class="tab_li prev">자가진단</li>
	            <li class="tab_li prev">본인인증</li>
	            <li class="tab_li on">지점예약</li>
	            <!-- <li class="tab_li">예약확인</li> -->
	        </ul>
	        <div class="tab_cont">
	            <!-- 7지점예약//달력 -->
	            <div class="cont_box on">
	                <div class="reservation_date_wrap">
	                <div class="col-12" style="position: relative;">
	                	<input type="text" class="admin_input_style_0" id="dateSetting" name="dateSetting" autocomplete="off" data-common-datepicker="">
	                </div>
	                    <div class="reservation_date_box">
	                        <span><em>●</em>예약일자와 시간을 선택해 주세요.</span>
	                        <div class="date_time">
	                            <p class="date_time_date"><em>●</em>예약가능</p>
	                            <p class="date_time_time"><em>●</em>예약종료</p>
	                        </div>
	                    </div>
	                    <div class="calendar_timelist">
	                        <div class="calendar_cont">
	                        </div>
	                        <div class="time_cont">
	                            <h3><span class="blind">예약시간 선택</span></h3>
	                            <div class="time-box">
	                            	<input type="hidden" name="selectTime" value="" id="selectTime"/>
	                                <div class="time_select">
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	                <div class="check_again" style="display: none;">
                          <span class="material-icons">error</span>
                          <h1>2020-03-15 11:20</h1>
                          <p>에</p>
                          <h1>예약신청</h1>
                          <p>하시겠습니까?</p>
                     </div>
	                <div class="tab_title_btn">
	                	<a href="/reserve/step?step_idx=6&rl_business_type=<%=rlBusinessType %>" class="pre_btn tab_content2_pre_btn" data-step-prev-btn>이전단계</a>
	                    <a href="javascript:void(0)" class="next_btn tab_content2_next_btn" onclick="reserveStep.checkReserve()">예약신청</a>
	                </div>
	            </div>
	            <!-- 지점예약//달력 end -->
	        </div>
        </div> 
    </section>
    <%@ include file="../inc/footer.jsp" %>
    <%@ include file="popup.jsp" %>
</body>
</html>