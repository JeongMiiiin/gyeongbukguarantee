<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form method="get" id="filterForm" action="/reserve/adm/memberManage/list" class="col-12 admin_filter_style_0_wrap" data-common-filter-form>
	<input type="hidden" id="pageIndex" name="pageIndex" value="${searchDto.pageIndex}" />
	<table class="col-12 col-lg-0 admin_filter_style_0_con">
		<colgroup>
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
							<c:when test="${searchDto.searchId ne '' && not empty searchDto.searchId}">
								<div class="admin_select_style_0_wrap">
									<select name="keyword_select" id="keywordSelect" data-keyword-select="keyword">
										<option value="">선택</option>
										<option value="searchId" selected>아이디</option>
										<option value="searchName">이름</option>
									</select>
								</div>
								<input type="text" class="admin_input_style_0" id="searchId" name="searchId" value="${searchDto.searchId}" data-keyword-input="keyword"/>		
						 	</c:when>
						 	<c:when test="${searchDto.searchName ne '' && not empty searchDto.searchName}">
						 		<div class="admin_select_style_0_wrap">
									<select name="keyword_select" id="keywordSelect" data-keyword-select="keyword">
										<option value="">선택</option>
										<option value="searchId">아이디</option>
										<option value="searchName" selected>이름</option>
									</select>
								</div>
								<input type="text" class="admin_input_style_0" id="searchName" name="searchName" value="${searchDto.searchName}" data-keyword-input="keyword"/>		
						 	</c:when>
						 	<c:otherwise>
						 		<div class="admin_select_style_0_wrap">
									<select name="keyword_select" id="keywordSelect" data-keyword-select="keyword">
										<option value="" selected>선택</option>
										<option value="searchId">아이디</option>
										<option value="searchName">이름</option>
									</select>
								</div>
						 		<input type="text" class="admin_input_style_0" id="" name="" value="" data-keyword-input="keyword"/>
						 	</c:otherwise>
						</c:choose>
					</div>
				</td>
				<td></td>
			</tr>
		</tbody>
	</table>
	<div class="col-12 col-lg-0 admin_filter_style_0_btn_con">
		<a href="/reserve/adm/memberManage/list" class="admin_btn_style_0 mr5">
			<span>초기화</span>
		</a>
		<a href="javascript:void(0)" class="admin_btn_style_0 mr5" data-common-filter-btn>
			<span>검색</span>
		</a>
		<a href="/reserve/adm/memberManage/insert?${searchDto.qustr}" class="admin_btn_style_0">
			<span>등록</span>
		</a>
	</div>
</form>