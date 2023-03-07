package com.gyeongbuk.spring.guarantee.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dao.InquiryIpManageDao;
import com.gyeongbuk.spring.guarantee.dto.InquiryIpDto;
import com.gyeongbuk.spring.guarantee.dto.Pagination;

@Service
public class InquiryIpManageServiceImpl extends RootService implements InquiryIpManageService {
	@Autowired
	private InquiryIpManageDao dao;
	
	//리스트 페이지 이동
	@Override
	public String listPage(InquiryIpDto searchDto, Model model) {
		Pagination pagination = new Pagination();
		pagination.setCurrentPageNo(searchDto.getPageIndex());
		pagination.setRecordCountPerPage(searchDto.getPageUnit());
		pagination.setPageSize(searchDto.getPageSize());
		
		searchDto.setFirstIndex(pagination.getFirstRecordIndex());
		searchDto.setRecordCountPerPage(pagination.getRecordCountPerPage());
		
		List<InquiryIpDto> result = dao.selectListData(searchDto);
		int totalCnt = dao.selectTotalCnt(searchDto);

		pagination.setTotalRecordCount(totalCnt);

		searchDto.setEndDate(pagination.getLastPageNoOnPageList());
		searchDto.setStartDate(pagination.getFirstPageNoOnPageList());
		searchDto.setPrev(pagination.getXprev());
		searchDto.setNext(pagination.getXnext());
		searchDto.setRealEnd(pagination.getRealEnd());
		
		model.addAttribute("resultList", result);
		model.addAttribute("totCnt",totalCnt);
		model.addAttribute("totalPageCnt",(int)Math.ceil(totalCnt / (double)searchDto.getPageUnit()));
		model.addAttribute("pagination",pagination);
		
		try {
			searchDto.setQustr();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "admin/inquiryIp/list";
	}
	
	/* 요청상태 변경
	 * result.result : 2 -> 요청상태 및 해당 IP까지 변경
	 * result.result : 1 -> 요청상태 변경 그러나 해당 IP는 변경 X
	 * result.result : 0 -> 요청상태 변경 실패
	 */
	@Override
	@Transactional
	public HashMap<String, Object> updateRequest(HashMap<String, String> params) {

		int data = dao.updateRequest(params);
			
		HashMap<String, Object> result = new HashMap<String, Object>();
			
		if (data == 1) {
			int approveAt = Integer.parseInt(params.get("requestStatus"));
			
			if(approveAt > 0) {
				dao.updateMemberIp(params);
			}
			result.put("result", true);
		} else {
			result.put("result", false);
		}
			
		return result;
			
	}
	
}
