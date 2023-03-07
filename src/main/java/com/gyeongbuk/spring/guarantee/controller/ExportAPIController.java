//API를 위한 controller

package com.gyeongbuk.spring.guarantee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gyeongbuk.spring.guarantee.service.ExportAPIService;

@Controller
@RequestMapping("/reserve/API*")
public class ExportAPIController {
	@Autowired
	private ExportAPIService service;
	
	//예약리스트를 가져가는 API
	@RequestMapping(value = "/reserveListAPI", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> reserveListAPI(@RequestBody HashMap<String, Object> map) {
		return service.reserveListAPI(map);
	}
	
	//예약리스트를 가져가는 API
	@RequestMapping(value = "/reserveRecommend", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> reserveRecommend(@RequestBody HashMap<String, Object> map) {
		return service.reserveRecommend(map);
	}
	
	//외부 API로 예약 추가
	@RequestMapping("/requestReserve")
	public @ResponseBody Map<String,Object> requestReserve(@RequestBody HashMap<String, Object> map) {
		return service.requestReserve(map);
	}
}
