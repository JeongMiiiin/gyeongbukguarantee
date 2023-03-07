<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>경북신용보증재단 보증상담예약신청</title>
	<%@ include file="../../common/headInfo/css.jsp" %>
    <%@ include file="../headInfo/css.jsp" %>
    <%@ include file="../../common/headInfo/javascript.jsp" %>
    <%@ include file="../headInfo/javascript.jsp" %>
    <script type="text/javascript" src="<c:url value='/reserve/client/js/reserve/step.js'/>"></script>
</head>
<body>
	<%
	int step = request.getParameter("step_idx") != null ? Integer.parseInt(request.getParameter("step_idx")) : -1;
	%>
	<script>
		let curStep = <%=step%>
	</script>
    <%@ include file="../inc/header.jsp" %>
    <section class="wrap">
    	<div class="content">
    		<ul class="tab_title">
	            <li class="tab_li on">상담안내</li>
	            <li class="tab_li">이용안내</li>
	            <li class="tab_li">자가진단</li>
	            <li class="tab_li">본인인증</li>
	            <li class="tab_li">지점예약</li>
	            <!-- <li class="tab_li">예약확인</li> -->
	        </ul>
	        <div class="tab_cont">
	            <!-- 1상담안내 -->
	            <div class="cont_box on">
	                <div class="tab_content">
	                    <div class="tab_content_title">
	                        <h6>정부·지자체 소상공인 정책 자금, 일반자금 등에 대한 신용보증을 신청하기 위한 상담으로 경상북도에서 사업을 영위 중인 사업자(개인·법인)이라면 누구든 상담 가능합니다.</h6>
	                    </div>
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title">
	                            <span>대상</span>
	                        </div>
	                        <div class="tab-content_question_box type_2">
                                <dl>
                                    <dt>
                                        경상북도에서 사업을 영위 중인 사업자 
                                    </dt>
                                </dl>
                            </div>
	                    </div>
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title">
	                            <span>기본서류</span>
	                        </div>
	                        <div class="tab-content_question_box type_2">
                                <dl>
                                    <dt>
                                        신분증, 사업자등록증 사본, 사업장 / 거주주택 임대차 계약서(임차인 경우에 한함) 
                                    </dt>
                                </dl>
                            </div>
	                    </div>
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title">
	                            <span>추가서류</span>
	                        </div>
	                        <div class="tab-content_question_box type_2">
                                <dl>
                                    <dt>
                                        법인기업 : 주주명부, 주식상황변동표, 재무제표, 법인인감도장, 법인인감증명서 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        운수업(건설기계) : 자동차 등록 원부 또는 건설기계 등록 원부, 위수탁 계약서(지입 계약서) 
                                    </dt>
                                </dl>
                            </div>
	                    </div>
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title">
	                            <span>유의사항</span>
	                        </div>
	                        <div class="tab-content_question_box type_2">
                                <dl>
                                    <dt>
                                        대표자가 아니면 상담 내용에 제한이 있을 수 있습니다. 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        보증상담 시 대표자 본인(공동대표일 경우 공동대표자 포함, 법인의 경우 대표이사)이 직접 내방 하시기 바랍니다.
                                    </dt>
                                </dl>
                            </div>
	                    </div>
	                </div>
	                <div class="tab_title_btn content3_btn">
	                	<a href="/reserve/main" class="pre_btn tab_content2_pre_btn">뒤로가기</a>
	                	<a href="/reserve/step?step_idx=2" class="next_btn tab_content2_next_btn" data-step-next-btn>상담예약신청하기</a>
	                </div>
	            </div>
	            <!-- 상담안내 end -->
	
	       </div>
    	</div>
    </section>
<%@ include file="../inc/footer.jsp" %>
<%@ include file="popup.jsp" %>
</body>
</html>