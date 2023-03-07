<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>경북신용보증재단 보증상담예약신청</title>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
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
	            <li class="tab_li prev">상담안내</li>
	            <li class="tab_li prev">이용안내</li>
	            <li class="tab_li on">자가진단</li>
	            <li class="tab_li">본인인증</li>
	            <li class="tab_li">지점예약</li>
	            <!-- <li class="tab_li">예약확인</li> -->
	        </ul>
	        <div class="tab_cont">
	            <!-- 3자가진단 -->
	            <div class="cont_box on">
	                <div class="tab_content">
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title">
	                            <span>보증신청 가능여부에 대한 자가 진단 항목입니다.</span>
	                        </div>
	                        <div class="tab-content_question_box">
                                <dl>
                                    <dt>
                                        신청일 현재 휴업 또는 폐업 중에 있으십니까? 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        보증 기관(신용보증재단, 신용보증기금, 기술보증기금)에 연체 중이거나 사고(대위변제 포함) 등록된 기업과 관련(대표자, 연대보증인 등)이 있으십니까?
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        보증신청기업 및 대표자(실제 경영자 포함)에게 최근 3개월 이내에 다음의 경우가 있었습니까?
                                    </dt>
                                    <dd>
                                        <ol>
                                            <li>
                                                당좌 부도(1차 이상) 발생
                                            </li>
                                            <li>
                                                신용 관리 정보 대상자로 등록
                                            </li>
                                            <li>
                                                신용보증사고(사고 유보 포함) 발생
                                            </li>
                                            <li>
                                                소유 부동산에 대한 권리침해(압류, 가압류, 가처분, 경매 등)
                                            </li>
                                        </ol>
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>
                                        현재 연체 중이거나 최근 3개월 이내에 대출금을 30일 이상 연체, 또는 10일 이상 연체를 4회 이상 한 적이 있으십니까?
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        신청일 현재 국세 및 지방세 체납(체납처분 포함) 사실이 있으십니까?
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        파산·(개인) 회생·신용 회복 절차를 진행 중이십니까?
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        신용보증기금과 기술보증기금을 동시에 이용 중이십니까?
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        보증 제한업종, 보증 금지 및 제한사항에 해당되는 부분이 있으십니까?
                                    </dt>
                                </dl>
                            </div>
	                    </div>
	                </div>
	                <div class="tab_content_checkbox2">
	                    <div class="yn_chb_title">
	                        <span>※ 위 항목 중 어느 하나에 해당하는 사항이 있습니까?</span>
	                    </div>
	                    <div class="yn_chb">
	                        <input type="radio" id="checkStep3Y" name="checkStep3" class="check_step">
	                        <label for="checkStep3Y">
	                            <h3>예</h3>
	                        </label>
	                        <h4>/</h4>
	                        <input type="radio" id="checkStep3N" name="checkStep3" class="check_step" checked>
	                        <label for="checkStep3N">
	                            <h3>아니오</h3>
	                        </label>
	                    </div>
	                </div>
	                <div class="tab_content_checkbox3">
	                    <h5>※ 하나 이상에 해당하시면 예약 상담 진행이 불가합니다. [아니오]를 체크 하신 경우에만 다음 단계로 진행됩니다.</h5>
	                </div>
	                <div class="tab_title_btn">
	                	<a href="/reserve/step?step_idx=2" class="pre_btn tab_content2_pre_btn" data-step-prev-btn>이전단계</a>
	                    <a href="/reserve/step?step_idx=4" class="next_btn tab_content2_next_btn" data-step-next-btn>다음단계</a>
	                </div>
	            </div>
	            <!-- 자가진단 end -->
	
	        </div>
    	</div>
    </section>
    <%@ include file="../inc/footer.jsp" %>
    <%@ include file="popup.jsp" %>
</body>
</html>