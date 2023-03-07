<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>사용자관리</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
	<script type="text/javascript" src="<c:url value='/reserve/adm/js/member/list.js'/>"></script>
	<script>
		let pagingActiveIndex = "${searchDto.pageIndex}"
	</script>
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
    <section class="col-12 admin_contents_wrap">
    	<div class="col-12 admin_contents_con">
	    	<h1 class="col-12 page_title">
	    		사용자관리
	    	</h1>
	    	<div class="col-12 admin_contents">
				<div class="col-12 admin_table_style_0_wrap">
					<%@ include file="filter.jsp" %>
					<div class="col-12 admin_table_style_0">
						<div class="col-12 admin_table_style_0_header">
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
						</div>
						<table class="col-12">
							<colgroup>
								<col width="60px"/>
								<col/>
								<col/>
								<col width="140px"/>
								<col width="120px"/>
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>아이디</th>
									<th>이름</th>
									<th>허용IP</th>
									<th>권한</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="list" items="${resultList}" varStatus="status">
							    <tr>
							    	<td>${fn:length(resultList) - status.index}</td>
							        <td>
							        	<a href="/reserve/adm/memberManage/update?mem_idx=${list.memIdx}&${searchDto.qustr}" class="table_link">
							        		${list.memUserId}
							        	</a>
							        </td>
							        <td>${list.memUserName}</td>
							        <td>${list.memAccessIp}</td>
							        <td>
							        	<c:choose>
							        		<c:when test="${list.memLevel eq 10}">최고관리자</c:when>
							        		<c:otherwise>
							        			영업점관리자
							        		</c:otherwise>
							        	</c:choose>
							        </td>
							    </tr>  
							    </c:forEach>
							    <c:if test="${empty resultList}">
							    	<tr class="no_data">
							    		<td colspan="5">결과가 존재하지 않습니다.</td>
								    </tr>
							    </c:if>
							</tbody>
						</table>
						<!-- Paging[s] -->
						<div class="col-12 admin_pagiation_style_0_con">
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