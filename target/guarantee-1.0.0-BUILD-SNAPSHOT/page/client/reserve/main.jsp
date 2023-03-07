<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>경북신용보증재단 보증상담예약신청</title>
    <%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
    <script type="text/javascript" src="<c:url value='/reserve/client/js/reserve/main.js'/>"></script>
</head>

<%
	if( session.getAttribute("rlName") != null || session.getAttribute("rlHp") != null
	|| session.getAttribute("rlReserveDate") != null || session.getAttribute("rlReserveTime") != null){
		session.invalidate();
	}
%>

<body>
<script>
		let branchDateList = [];
		let fullReserveList = [];
	</script>
	<c:forEach var="list" items="${branchData.branchDateSettingDtoList}" varStatus="status">
		<c:if test="${list.gbdsStatus eq '2'}">
			<script>
				branchDateList.push('${list.gbdsDateStr}');
			</script>
		</c:if>
		<c:if test="${list.gbdsStatus eq '3'}">
			<script>
				fullReserveList.push('${list.gbdsDateStr}');
			</script>
		</c:if>
	</c:forEach>
    <%@ include file="../inc/header.jsp" %>
    <section class="wrap">
    	<div class="cont">
    		<div class="firstpage_listview">
                <%-- <img src="<c:url value='/reserve/client/img/cont_tit_icon.png'/>" alt="화살표 아이콘 이미지"/> --%>
                <span>지점별 예약 현황</span>
            </div>
            <div class="firstpage_selectbox">
                <select name="branchListSelect" id="branchListSelect">
                	<c:forEach var="list" items="${branchList}">
                		<option value="${list.brIdx}" <c:if test="${list.brIdx eq select_idx}">selected</c:if>>${list.brName}지점 (${list.brCategoryName})</option>
                	</c:forEach>
                 </select>
            </div>
            <div class="reservation_date_wrap">
                    <div class="calendar_timelist first_calendar">
                    	<input type="text" class="admin_input_style_0" id="dateSetting" name="dateSetting" autocomplete="off" data-common-datepicker>
                        <div class="calendar_cont">
                        </div>
                    </div>
            </div>
            <div class="tab_title_btn">
                <button type="button" class="next_btn" value="top" onclick="location.href='/reserve/step?step_idx=1';">상담예약신청</button>
            </div>
    	</div>
    </section>
	<%@ include file="../inc/footer.jsp" %>
    
</body>
</html>