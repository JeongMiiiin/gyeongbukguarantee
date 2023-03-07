package com.gyeongbuk.spring.guarantee.service;

import java.util.HashMap;

import org.springframework.ui.Model;

import com.gyeongbuk.spring.guarantee.dto.InquiryIpDto;

public interface InquiryIpManageService {
	String listPage(InquiryIpDto searchDto, Model model);
	HashMap<String, Object> updateRequest(HashMap<String, String> map);
}