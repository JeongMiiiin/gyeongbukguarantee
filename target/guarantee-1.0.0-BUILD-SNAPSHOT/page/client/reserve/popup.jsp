<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- 다음단계 Popup -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="nextStepPopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					다음단계로 이동하시겠습니까?
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="reserveStep.nextStep();">
						<span>예</span>
					</a>
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>아니오</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 다음단계 Popup -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="prevStepPopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					이전단계로 돌아가시겠습니까?
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="reserveStep.prevStep()">
						<span>예</span>
					</a>
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>아니오</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<%
 switch(step){
 	case 2 :
	 	%>
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkStep2Popup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					내용 확인 체크 버튼을 표시해주시기 바랍니다.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>	 	
	 	<%
	 	break;
 
 	case 3 :
		%>
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkStep3Popup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					해당 사항이 있으신 경우 예약이 불가합니다.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>		 	
	 	<%
	 	break;
 	case 4:
 		%>
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkStep4Popup1">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					개인정보 수집이용에 동의 후<br/>
					본인 인증을 완료해주시기 바랍니다.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkStep4Popup2">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					본인 인증을 완료해주시기 바랍니다.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
 		<%
 		break;
 	case 5 :
 		%>
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkStep5Popup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					사업자등록을 하지 않으면 보증상담이 불가합니다.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
 		<%
 		break;
 	case 6 :
 		%>
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkStep6Popup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					예약할 지점을 선택해주세요.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
 		<%
 		break;
 	case 7 :
 		%>
<!-- 예약날짜확인팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkStep7Popup1">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					예약할 날짜를 선택해주세요.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 예약시간확인팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkStep7Popup2">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					예약할 시간을 선택해주세요.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 예약최종확인팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="step7Popup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
				<a href="javascript:void(0)" class="popup_close_btn" onclick="popupManager.removePopLatest(true);">
					닫기버튼
				</a>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					코로나19 확산 방지를 위하여 발열, 기침 등의 증상이 있거나 최근 해외방문 이력이 있으신 고객님들은 지점 방문을 자제하여 주시기 바랍니다.<br/>
					또한 실내 다수 인원이 머무르는 시간을 최소화 하고자 반드시 예약하신 날짜와 시간에 맞추어 지점 방문을 하여주시기 바랍니다.<br/>
					예약을 진행하시겠습니까?
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="reserveStep.requestReserve();">
						<span>예</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 예약완료팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="completeReservePopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					예약완료
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					예약이 완료되었습니다.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="reserveStep.completeReserve();">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div> 		
<!-- 예약중복팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="duplicateReservePopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					같은 날짜에 예약이 있습니다. 다른 날짜를 선택해주세요.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true)">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 예약마감팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="endReservePopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					해당 시간은 마감이 되었습니다. 잠시 후 다시 시도해주세요.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="reserveStep.endReserve();">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 예약실패팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="failReservePopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					예약실패
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					예약에 실패했습니다. 잠시 후 다시 시도해주세요.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true)">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
 		<%
 		break;
 		
	default :
		break;
 }
%>
