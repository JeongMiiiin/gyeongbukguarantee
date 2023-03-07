<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
	            <li class="tab_li on">이용안내</li>
	            <li class="tab_li">자가진단</li>
	            <li class="tab_li">본인인증</li>
	            <li class="tab_li">지점예약</li>
	            <!-- <li class="tab_li">예약확인</li> -->
	        </ul>
	        <div class="tab_cont">
	            <!-- 2이용안내 -->
	            <div class="cont_box on">
	                <div class="tab_content">
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title">
	                            <span>보증불가기업</span>
	                        </div>
	                        <div class="tab-content_question_box type_2">
                                <dl>
                                    <dt>
                                        지역신용보증재단, 기술보증기금 또는 신용보증기금이 보증채무를 이행한 후 채권을 회수하지 못한 기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        위 기업에 대표자로 되어 있는 법인기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        휴업중인 기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        금융기관의 대출금을 빈번히 연체하고 있는 기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        전국은행연합회의 “신용정보관리규약”에서 정한 신용관리정보 등록사유가 발생한 기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        당재단이 보증채무를 이행한 후 채권을 회수하지 못한 기업의 연대보증인인 기업 및 연대보증인이 대표자로 되어 있는 법인기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        신용상태가 극히 불량하거나 보증제한이 필요하다고 인정되어 이사장이 따로 정하는 기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        회생, 파산, 개인회생절차 개시신청, 개인신용회복지원 신청 및 채무불이행 명부등재 신청이 있거나 청산에 들어간 기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        신용보증기금 및 기술보증기금을 모두 이용하고 있는 기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        신용보증기금 또는 기술보증기금과 재단의 보증금액 합계액이 8억원을 초과하는 기업 
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        보증제한업종을 영위하는 기업 
                                    </dt>
                                </dl>
                            </div>
	                    </div>
	
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title">
	                            <span>보증제한업종</span>
	                        </div>
	                        <div class="tab-content_sub_title">
	                            <table class="tbl tbl_sm">
	                                <caption>보증제한업종의 목록으로 표준산업분류, 업종명을 제공</caption>
	                                <colgroup>
	                                    <col style="width:20%;">
	                                    <col style="width:80%;">
	                                </colgroup>
	                                <thead>
	                                    <tr>
	                                        <th scope="col">표준산업분류</th>
	                                        <th scope="col">업종명</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                    <tr>
	                                        <th scope="row">56211</th>
	                                        <td class="tL">일반유흥 주점업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">56212</th>
	                                        <td class="tL">무도유흥 주점업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">68</th>
	                                        <td class="tL">부동산업. 다만, 부동산관련 서비스업(682)은 제외</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">91121</th>
	                                        <td class="tL">골프장 운영업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">91291</th>
	                                        <td class="tL">무도장 운영업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">91249</th>
	                                        <td class="tL">기타 갬블링 및 베팅업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">9612 중</th>
	                                        <td class="tL">증기탕 및 안마시술소</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">46102 중</th>
	                                        <td class="tL">담배 중개업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">46209 중</th>
	                                        <td class="tL">잎담배 도매업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">46333</th>
	                                        <td class="tL">담배 도매업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">46416 중</th>
	                                        <td class="tL">모피제품 도매업. 단, 인조모피제품 도매업 제외</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">64</th>
	                                        <td class="tL">금융업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">65</th>
	                                        <td class="tL">보험 및 연금업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">66</th>
	                                        <td class="tL">금융 및 보험관련 서비스업. 다만 손해사정업(66201), 보험대리 및 중개업(66202) 및 그 외 기타 금융지원 서비스업(66199) 중 핀테크 관련 사업에 대한 보증을 지원하는 경우는 제외</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">75993</th>
	                                        <td class="tL">신용조사 및 추심대행업</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">-</th>
	                                        <td class="tL">방문판매등에관한법률 제2조제6호의 다단계판매자가 동조제5호에서 정한 다단계판매(업)를 영위하는 경우</td>
	                                    </tr>
	                                    <tr>
	                                        <th scope="row">-</th>
	                                        <td class="tL">업종을 변형하여 운영되는 도박/향락 등 불건전 업종, 기타 국민보건/건전문화에 반하거나 사치/투기조장 등 우려가 있다고 중앙회장이 지정한 업종</td>
	                                    </tr>
	                                </tbody>
	                            </table>
	
	                        </div>
	                    </div>
	                    <div class="tab_content_checkbox">
	                        <input type="checkbox" id="checkStep2" value="" name="checkStep2" class="check_step"/>
	                        <label for="checkStep2"><span>위 내용을 모두 확인하셨습니까?</span></label>
	                    </div>
	                </div>
	                <div class="tab_title_btn">
	                    <a href="/reserve/step?step_idx=1" class="pre_btn tab_content2_pre_btn" data-step-prev-btn>이전단계</a>
	                    <a href="/reserve/step?step_idx=3" class="next_btn tab_content2_next_btn" data-step-next-btn>다음단계</a>
	                </div>
	            </div>
	            <!-- 이용안내 end -->
	
	        </div>
    	</div>
    </section>
    <%@ include file="../inc/footer.jsp" %>
    <%@ include file="popup.jsp" %>
</body>
</html>