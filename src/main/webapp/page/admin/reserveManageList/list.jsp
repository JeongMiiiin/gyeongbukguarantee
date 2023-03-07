<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>상담예약목록</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/reserveManageList/list.js'/>"></script>
	<script>
		let pagingActiveIndex = "${searchDto.pageIndex}"
	</script>
</head>
<body>

<%
	Date now = new Date();
	pageContext.setAttribute("curDateVal", now);
%>
	<fmt:formatDate var="curDate" value="${curDateVal}" pattern="yyyy-MM-dd"/>

	<%@ include file="../inc/header.jsp" %>
    <section class="col-12 admin_contents_wrap">
    	<div class="col-12 admin_contents_con">
    		<h1 class="col-12 page_title">
	    		상담예약목록
	    	</h1>
	    	<div class="col-12 admin_contents">
				<div class="col-12 admin_table_style_0_wrap">
					<%@ include file="filter.jsp" %>
					<div class="col-12 admin_table_style_0 print-padding0" id="printContent">
						<div class="col-12 admin_table_style_0_header no-print">
							<div>
			                	<div class="total_cnt_con mr20">
			                		<span>
			                			Total
			                		</span>
			                		<span class="total_cnt">
			                			<span class="fontweight500">${totCnt}</span>건
			                		</span>
			                	</div>
			                </div>
			                <div>
			                	<a href="javascript:void(0)" class="admin_btn_style_1 mr10 lightgreen" onclick="reserveManageListList.onExcelDownload()">
			                		<span>엑셀 다운로드</span>
			                	</a>
			                	<a href="javascript:void(0)" class="admin_btn_style_1 skyblue" onclick="reserveManageListList.onPrint()">
			                		<span>프린트</span>
			                	</a>
			                </div>
						</div>
						<table class="col-12">
							<colgroup>
								<col width="60px"/>
								<col/>
								<col/>
								<col width="140px"/>
								<col width="120px"/>
								<col width="130px"/>
								<col width="110px"/>
								<col width="100px"/>
								<col class="no-print" width="200px"/>
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>방문지점</th>
									<th>예약자명</th>
									<th>연락처</th>
									<th>사업자종류</th>
									<th>상담예약일자</th>
									<th>예약시간</th>
									<th>상태</th>
									<th class="no-print">관리</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${resultList}" varStatus="status">
								<tr>
									<td>${fn:length(resultList) - status.index}</td>
									<td>${list.brName}</td>
									<td>
							        	<a href="/reserve/adm/reserveManageList/update?rl_idx=${list.rlIdx}&${searchDto.qustr}" class="table_link">
							        		${list.rlName}
							        	</a>
							        </td>
									<td>${list.rlHp}</td>
									<td>
										<c:choose>
											<c:when test="${list.rlBusinessType eq '1'}">개인사업자</c:when>
											<c:otherwise>법인사업자</c:otherwise>
										</c:choose>
									</td>
									<td>${list.rlReserveDateStr}</td>
									<td>${list.rlReserveTimeStr}</td>
									<td>
										<c:choose>
											<c:when test="${list.rlStatus eq '1'}">상담대기</c:when>
											<c:when test="${list.rlStatus eq '2'}">상담중</c:when>
											<c:otherwise>상담종료</c:otherwise>
										</c:choose>
									</td>
									<td class="no-print">
										<c:choose>
											<c:when test="${list.rlReserveDateStr > curDate }">
												<a href="javascript:void(0)" class="admin_btn_style_1 grey reject_btn" onclick="reserveManageListList.deleteReserve(${list.rlIdx}, ${list.brIdx}, '${list.rlReserveDateStr}')">
											        <span>삭제</span>
											    </a>
											</c:when>
											<c:when test="${list.rlReserveDateStr < curDate }">
												상담종료
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${list.rlStatus eq '1'}">
														<a href="javascript:void(0)" class="admin_btn_style_1 skyblue reject_btn" onclick="reserveManageListList.updateReserve(${list.rlIdx}, 2)">
											        		<span>시작</span>
											        	</a>
														<a href="javascript:void(0)" class="admin_btn_style_1 grey reject_btn" onclick="reserveManageListList.updateReserve(${list.rlIdx}, 3)">
											        		<span>종료</span>
											        	</a>
														<a href="javascript:void(0)" class="admin_btn_style_1 grey reject_btn" onclick="reserveManageListList.deleteReserve(${list.rlIdx}, ${list.brIdx}, '${list.rlReserveDateStr}')">
											        		<span>삭제</span>
											        	</a>
													</c:when>
													<c:when test="${list.rlStatus eq '2'}">
														<a href="javascript:void(0)" class="admin_btn_style_1 reject_btn" onclick="reserveManageListList.updateReserve(${list.rlIdx}, 1)">
											        		<span>대기</span>
											        	</a>
														<a href="javascript:void(0)" class="admin_btn_style_1 grey reject_btn" onclick="reserveManageListList.updateReserve(${list.rlIdx}, 3)">
											        		<span>종료</span>
											        	</a>
													</c:when>
													<c:otherwise>
														<a href="javascript:void(0)" class="admin_btn_style_1 reject_btn" onclick="reserveManageListList.updateReserve(${list.rlIdx}, 1)">
											        		<span>대기</span>
											        	</a>
											        	<a href="javascript:void(0)" class="admin_btn_style_1 skyblue reject_btn" onclick="reserveManageListList.updateReserve(${list.rlIdx}, 2)">
											        		<span>시작</span>
											        	</a>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								</c:forEach>
								<c:if test="${empty resultList}">
							    	<tr class="no_data">
							    		<td colspan="9">결과가 존재하지 않습니다.</td>
								    </tr>
							    </c:if>
							</tbody>
						</table>
						<!-- Paging[s] -->
						<div class="col-12 admin_pagiation_style_0_con no-print">
							<ul class="admin_pagiation_style_0">
									  <c:if test="${searchDto.prev}">
									  <li class="admin_pagiation_btn prev" id="dataTable_previous">
									 		 <a href="javascript:void(0);" onclick="commonList.fn_go_page(${searchDto.startDate - 1}); return false;" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link">Previous</a>
									  </li>
									  </c:if>
									  <c:forEach var="num" begin="${searchDto.startDate}" end="${searchDto.endDate}">
									  <li class="admin_pagiation">
									 		 <a href="javascript:void(0);" onclick="commonList.fn_go_page(${num}); return false;" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link" title="${num}">${num}</a>
									  </li>
									  </c:forEach>
									  
									  <c:if test="${searchDto.next}">
									  <li class="admin_pagiation_btn next" id="dataTable_next">
									 		 <a href="javascript:void(0);" onclick="commonList.fn_go_page(${searchDto.endDate + 1}); return false;" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link">Next</a>
									  </li>
									  </c:if>
								  </ul>
						</div>
						<!-- Paging[e] -->
					</div>
				</div>
	    	</div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>