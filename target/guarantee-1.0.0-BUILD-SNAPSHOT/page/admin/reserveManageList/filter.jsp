<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form method="get" id="filterForm" action="/reserve/adm/reserveManageList/list" class="col-12 admin_filter_style_0_wrap" data-common-filter-form>
	<input type="hidden" id="pageIndex" name="pageIndex" value="${searchDto.pageIndex}" />
	<table class="col-12 col-lg-0 admin_filter_style_0_con">
		<colgroup>
			<col width="100px"/>
			<col width="400px"/>
			<col width="100px"/>
			<col width="400px"/>
			<col width="auto"/>
		</colgroup>
		<tbody>
			<tr>
				<th>
					키워드검색
				</th>
				<td>
					<div class="admin_search_select_category_text_con">
						<c:choose>
							<c:when test="${searchDto.searchBrName ne '' && not empty searchDto.searchBrName}">
								<div class="admin_select_style_0_wrap">
									<select name="keyword_select" id="keywordSelect" data-keyword-select="keyword">
										<option value="">선택</option>
										<option value="searchBrName" selected>지점명</option>
										<option value="searchName">예약자명</option>
										<option value="searchHp">연락처</option>
									</select>
								</div>
								<input type="text" class="admin_input_style_0" id="searchBrName" name="searchBrName" value="${searchDto.searchBrName}" data-keyword-input="keyword"/>		
						 	</c:when>
						 	<c:when test="${searchDto.searchName ne '' && not empty searchDto.searchName}">
						 		<div class="admin_select_style_0_wrap">
									<select name="keyword_select" id="keywordSelect" data-keyword-select="keyword">
										<option value="">선택</option>
										<option value="searchBrName">지점명</option>
										<option value="searchName" selected>예약자명</option>
										<option value="searchHp">연락처</option>
									</select>
								</div>
								<input type="text" class="admin_input_style_0" id="searchName" name="searchName" value="${searchDto.searchName}" data-keyword-input="keyword"/>		
						 	</c:when>
						 	<c:when test="${searchDto.searchHp ne '' && not empty searchDto.searchHp}">
						 		<div class="admin_select_style_0_wrap">
									<select name="keyword_select" id="keywordSelect" data-keyword-select="keyword">
										<option value="">선택</option>
										<option value="searchBrName">지점명</option>
										<option value="searchName">예약자명</option>
										<option value="searchHp" selected>연락처</option>
									</select>
								</div>
								<input type="text" class="admin_input_style_0" id="searchHp" name="searchHp" value="${searchDto.searchHp}" data-keyword-input="keyword"/>		
						 	</c:when>
						 	<c:otherwise>
						 		<div class="admin_select_style_0_wrap">
									<select name="keyword_select" id="keywordSelect" data-keyword-select="keyword">
										<option value="" selected>선택</option>
										<option value="searchBrName">지점명</option>
										<option value="searchName">예약자명</option>
										<option value="searchHp">연락처</option>
									</select>
								</div>
						 		<input type="text" class="admin_input_style_0" id="" name="" value="" data-keyword-input="keyword"/>
						 	</c:otherwise>
						</c:choose>
					</div>
				</td>
				<th>
					<label for="searchStartDate">
						기간검색
					</label>
				</th>
				<td>
					<div class="col-12 admin_filter_style_0_date_range">
						<input type="text" class="admin_input_style_0" id="searchStartDate" name="searchStartDate" value="${searchDto.searchStartDate}" autocomplete="off" data-common-datepicker readonly/>
						<span class="addr_text">~</span>
						<input type="text" class="admin_input_style_0" id="searchEndDate" name="searchEndDate" value="${searchDto.searchEndDate}" autocomplete="off" data-common-datepicker readonly/>
					</div>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>
					상태검색
				</th>
				<td>
					<div class="admin_select_style_0_wrap">
						<select name="searchRlStatus" id="searchRlStatus">
							<option value="" <c:if test="${searchDto.searchRlStatus eq ''}">selected</c:if>>전체</option>
							<option value="1" <c:if test="${searchDto.searchRlStatus eq '1'}">selected</c:if>>상담대기</option>
							<option value="2" <c:if test="${searchDto.searchRlStatus eq '2'}">selected</c:if>>상담중</option>
							<option value="3" <c:if test="${searchDto.searchRlStatus eq '3'}">selected</c:if>>상담종료</option>
						</select>
					</div>
				</td>
				<td colspan="3"></td>
			</tr>
		</tbody>
	</table>
	<div class="col-12 col-lg-0 admin_filter_style_0_btn_con">
		<a href="/reserve/adm/reserveManageList/list" class="admin_btn_style_0 mr5">
			<span>초기화</span>
		</a>
		<a href="javascript:void(0)" class="admin_btn_style_0 mr5" data-common-filter-btn>
			<span>검색</span>
		</a>
		<a href="/reserve/adm/reserveManageList/insert?${searchDto.qustr}" class="admin_btn_style_0">
			<span>등록</span>
		</a>
	</div>
</form>