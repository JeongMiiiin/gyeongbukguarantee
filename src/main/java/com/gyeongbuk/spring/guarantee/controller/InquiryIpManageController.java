//IP변경요청 관리를 위한 Controller

package com.gyeongbuk.spring.guarantee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gyeongbuk.spring.guarantee.dto.InquiryIpDto;
import com.gyeongbuk.spring.guarantee.service.InquiryIpManageService;

@Controller
@RequestMapping("/reserve/adm/inquiryIpManage*")
public class InquiryIpManageController {
	@Autowired
	private InquiryIpManageService service;
	
	//리스트 페이지
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listPage(@ModelAttribute("searchDto") InquiryIpDto searchDto, Model model) {
		return service.listPage(searchDto, model);
	}
		
	//요청상태 변경
	@RequestMapping(value = "/updaterRequest", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateRequest(@RequestBody HashMap<String, String> map) {
		return service.updateRequest(map);
	}
	
}
