<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- 취소할예약선택확인팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="selectReservePopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인해주세요!
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					취소할 예약을 1개 이상 선택해주세요
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

<!-- 취소확인팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="checkDeleteReservePopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					해당 예약을 취소하시겠습니까?
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="myInfo.deleteReserve();">
						<span>예</span>
					</a>
					<a href="javascript:void(0)" onclick="popupManager.removePopLatest(true);">
						<span>아니요</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 삭제완료팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="completeDeleteReservePopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					예약내역 삭제가 완료되었습니다.
				</div>
				<div class="col-12 popup_small_btn_con">
					<a href="javascript:void(0)" onclick="myInfo.completeDeleteReserve();">
						<span>확인</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 삭제실패팝업 -->
<div class="col-12 popup_style_0_wrap type_2" data-popup-page-access-block id="failDeleteReservePopup">
	<div class="col-12 popup_con" style="max-width: 500px;">
		<div class="col-12 popup_inner" style="max-width: 500px;">
			<div class="col-12 popup_top_con">
				<div class="col-12 popup_title">
					확인
				</div>
			</div>
			<div class="col-12 popup_contents_con">
				<div class="col-12 popup_contents_desc">
					오류가 발생했습니다. 잠시 후 다시 시도해주세요.
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