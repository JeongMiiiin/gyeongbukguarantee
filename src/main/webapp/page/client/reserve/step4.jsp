<%@page import="NiceID.Check.CPClient"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%
    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
    
    String sSiteCode = "G8515";			// NICE로부터 부여받은 사이트 코드
    String sSitePassword = "C4IC5FMAA1SB";		// NICE로부터 부여받은 사이트 패스워드
    
    String sRequestNumber = "REQ0000000001";        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
                                                    	// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
    sRequestNumber = niceCheck.getRequestNO(sSiteCode);
    session.setAttribute("REQ_SEQ" , sRequestNumber);	// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
  	
   	String sAuthType = "";      	// 없으면 기본 선택화면, M(휴대폰), X(인증서공통), U(공동인증서), F(금융인증서), S(PASS인증서), C(신용카드)
	String customize 	= "";		//없으면 기본 웹페이지 / Mobile : 모바일페이지
	
    // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
	//리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
    String sReturnUrl = "https://gbsinbo.co.kr/reserve/successCertification";      // 성공시 이동될 URL
    String sErrorUrl = "https://gbsinbo.co.kr/reserve/failCertification";          // 실패시 이동될 URL

    // 입력될 plain 데이타를 만든다.
    String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;
    
    String sMessage = "";
    String sEncData = "";
    
    int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
    if( iReturn == 0 )
    {
        sEncData = niceCheck.getCipherData();
    }
    else if( iReturn == -1)
    {
        sMessage = "암호화 시스템 에러입니다.";
    }    
    else if( iReturn == -2)
    {
        sMessage = "암호화 처리오류입니다.";
    }    
    else if( iReturn == -3)
    {
        sMessage = "암호화 데이터 오류입니다.";
    }    
    else if( iReturn == -9)
    {
        sMessage = "입력 데이터 오류입니다.";
    }    
    else
    {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }
%>

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
	String rlName = (String) session.getAttribute("rlName") != null ? (String) session.getAttribute("rlName") : "";
	String rlHp = (String) session.getAttribute("rlHp") != null ? (String) session.getAttribute("rlHp") : "";
	
	int certificationCheck = 0;
	if(rlName != "" && rlHp != ""){
		certificationCheck = 1;
	}
	
	%>
	
	<script>
	window.name ="Parent_window";
	
	let popupWindow;
	
	function fnPopup(){
		popupWindow = window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
		document.form_chk.target = "popupChk";
		document.form_chk.submit();
	}
	
	function settingInfo(name, hp, success){
		if(success){
			document.getElementById("rlName").value = name;
			document.getElementById("rlHp").value = hp;
			document.getElementById("certificateStatus").value = "1";
			
		} else {
			/* alert('본인인증에 실패했습니다. 잠시 후 다시 시도해주세요.') */
			document.getElementById("rlName").value = "";
			document.getElementById("rlHp").value = "";
			document.getElementById("certificateStatus").value = "0";
		}
	}
	
	</script>
	
	<script>
		let curStep = <%=step%>
	</script>
    <%@ include file="../inc/header.jsp" %>
    <section class="wrap">
        <div class="content">
        	<ul class="tab_title">
	            <li class="tab_li prev">상담안내</li>
	            <li class="tab_li prev">이용안내</li>
	            <li class="tab_li prev">자가진단</li>
	            <li class="tab_li on">본인인증</li>
	            <li class="tab_li">지점예약</li>
	            <!-- <li class="tab_li">예약확인</li> -->
	        </ul>
	        <div class="tab_cont">
	            
	            <!-- 4본인인증 -->
	            <div class="cont_box on">
	                <div class="tab_content">
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title no_before">
	                            <h5>유의사항</h5>
	                        </div>
	                        <div class="tab-content_sub_title_clause2">
	                            <div class="tab-content_sub_title_clause_content2">
	                                <h6>금융(신용 정보 등) 거래와 관련하여 신용 조회회사 또는 신용 정보 집중기관에 대한 개인신용 정보 조회, 금융(신용 정보 등) 거래 관계 설정 여부의 판단, 금융(신용보증 등) 거래 관계의 설정•유지•이행•관리, 금융사고 조사, 분쟁 해결, 민원 처리 및 법령상 의무 이행 등의 목적으로 개인 정보를 처리합니다.</h6>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="tab_content_title">
	                        <div class="tab-content_main_title no_before">
	                            <h5>개인정보 수집•이용 동의</h5>
	                        </div>
	                        <div class="tab-content_sub_title_clause">
	                            <div class="tab-content_sub_title_clause_content">
	                                <h6><b>개인정보의 수집•이용에 관한 사항</b></h6>
	                                <h6>경북신용보증재단은 ｢개인정보보호법｣ 제15조 제1항 제1호, 제17조 제1항 제1호, 제23조 제1호에 따라 아래와 같이 개인 정보의 수집•이용에 관하여 귀하의 동의를 얻고자 합니다.</h6>
	                                <h6>경북신용보증재단은 이용자의 사전 동의 없이는 이용자의 개인 정보를 함보로 공개하지 않으며, 수집된 정보는 아래와 같이 이용하고 있습니다.</h6>
	                                <h6>이용자가 제공한 모든 정보는 아래의 목적에 필요한 용도 이외로는 사용되지 않으며 이용목적이 변경될 시에는 이를 알리고 동의를 구할 것입니다.</h6>
	                                <h6><b>가. 개인 정보의 수집•이용 목적</b>소상공인 지원 사업 신청 및 정책 연동 : 보증상담 신청, SMS 제공을 위하여 개인의 정보를 수집, 이용하고 있습니다.</h6>
	                                <h6><b>나. 수집하려는 개인 정보의 항목</b>필수 3개 항목(성명, 사업장 주소, 휴대전화 번호)</h6>
	                                <h6><b>다. 개인정보의 보유 및 이용기간</b>개인 정보의 보유 및 이용 기간 개인 정보 및 관련 정보는 원칙적으로 보유기간의 경과, 개인 정보의 수집 및 이용목적의 달성 등 그 개인 정보가 불필요하게 되었을 때에는 지체 없이 파기합니다.(지원 관련 통계 및 컨설팅, 자금 등 지원 신청 자료는 제외) 다만, 다른 법령에 따라 보존하여야 하는 경우에는 그러하지 않을 수 있습니다. 불필요하게 되었을 때에는 지체 없이 해당 개인 정보를 파기합니다.</h6>
	                                <h6><b>라. 동의를 거부할 권리 및 동의를 거부할 경우의 불이익</b>동의를 거부할 권리 및 동의를 거부할 경우의 불이익 정보주체는 개인 정보 수집에 동의를 거부할 권리가 있습니다. 다만, 필수 항목에 대한 동의를 거부할 시 저희가 제공하는 서비스를 이용할 수 없습니다. </h6>
	                                <h6><b style="margin-bottom: 0;">마. 14세 미만 아동의 경우 회원가입 및 저희가 제공하는 서비스를 이용할 수 없습니다.</b></h6>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="tab_content_checkbox">
		                    <input type="checkbox" id="userinfoAgreeY" value="" name="userinfoAgreeY" onchange="reserveStep.controlCertificateCon($(this))" class="check_step"/>
		                    <label for="userinfoAgreeY">
			                    <span>위 개인정보 수집 이용에 동의합니까?</span>
			                </label>
		                </div>
	                    <div class="tab_content_title" id="hpCertificateCon" style="display : none;">
	                        <div class="tab-content_main_title no_before">
	                            <h5>휴대전화 본인인증</h5>
	                        </div>
	                        <div class="tab-content_sub_title_clause3">
	                            <div class="certification">
	                                <div class="certification_box">
	                                    <table>
	                                        <caption>휴대전화 본인인증의 목록으로 본인인증, 성명, 휴대전화를 제공</caption>
	                                        <colgroup>
	                                            <col style="width:25%;">
	                                            <col style="width:75%;">
	                                        </colgroup>
	                                        <tbody>
	                                        <!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
													<form name="form_chk" method="post" style="width: 0; height: 0; opacity: 0; position: absolute; top: 0; left: 0; z-index: -10;">
														<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
														<input type="hidden" name="EncodeData" value="<%= sEncData %>">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
													</form>
	                                            <tr>
	                                                <th scope="row">본인인증</th>
	                                                <td>
	                                                	<div style="display: inline-block;">이름 또는 휴대전화번호는 임의로 입력할 수 없습니다.</div>
	                                                    <button type="button" class="button small gray phone btnCertChk" onclick="fnPopup()" style="float: right;">
	                                                        <span class="material-icons">phone_iphone</span>본인인증하기
	                                                    </button>
	                                                </td>
	                                            </tr>
	                                            <input type="hidden" name="certificateStatus" id="certificateStatus" value="<%=certificationCheck %>" readonly/>
	                                            <tr>
	                                                <th scope="row">성명</th>
	                                                <td><input type="text" class="full" id="rlName" name="rlName" value="<%=rlName%>" readonly/></td>
	                                            </tr>
	                                            <tr>
	                                                <th scope="row">휴대전화</th>
	                                                <td><input type="text" class="full" id="rlHp" name="rlHp" value="<%=rlHp%>" readonly/></td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                            </div>
	                            <!-- <div class="check_again">
	                                <span class="material-icons">error</span>
	                                <h1>2020-03-15 11:20</h1>
	                                <p>에</p>
	                                <h1>예약신청</h1>
	                                <p>하시겠습니까?</p>
	                            </div> -->
	                        </div>
	                    </div>
	                </div>
	                <div class="tab_title_btn content3_btn">
	                	<a href="/reserve/step?step_idx=3" class="pre_btn tab_content2_pre_btn" data-step-prev-btn>이전단계</a>
	                    <a href="/reserve/step?step_idx=5" class="next_btn tab_content2_next_btn" data-step-next-btn>다음단계</a>
	                </div>
	            </div>
	            <!-- 본인인증 end -->
	            
	        </div>
        </div>
    </section>
    <%@ include file="../inc/footer.jsp" %>
    <%@ include file="popup.jsp" %>
</body>
</html>