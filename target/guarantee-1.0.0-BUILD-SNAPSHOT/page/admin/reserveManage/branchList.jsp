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
</head>
<body>


	<%@ include file="../inc/header.jsp" %>
    
    <section class="col-12 admin_contents_wrap">
    	<h1 class="col-12 page_title">
    		상담예약관리
    	</h1>
    	<div class="col-12 admin_contents">
			<div class="col-12 admin_card_list_style_0_wrap">
				<div class="col-12 admin_card_list_style_0_con">
					<c:forEach var="list" items="${resultList}">
					<div class="col-6 col-md-4 col-lg-3 col-xl-20 admin_card_list_style_0">
						<div class="col-12 admin_card_list_style_0_inner">
							<div class="col-12 top_box">
								<div class="col-12 title">
									${list.brName}지점
								</div>
								<div class="col-12 category">
									(${list.brCategoryName})
								</div>
							</div>
							<div class="col-12 bottom_box">
								<div class="col-12 manager_name">
									담당자 : 
									<c:choose>
										<c:when test="${list.memUserName eq null}">
											<span class="point_red">배정안됨</span>
										</c:when>
										<c:otherwise>
											<span>${list.memUserName}</span>
										</c:otherwise>
									</c:choose>
								</div> 
							</div>
							<div class="col-12 mt20 btn_con">
								<a href="/reserve/adm/reserveManage/list?br_idx=${list.brIdx}" class="col-12 admin_btn_style_0">
									<span>예약관리</span>
								</a>
							</div>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>


</body>
</html>