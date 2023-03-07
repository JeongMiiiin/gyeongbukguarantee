package com.gyeongbuk.spring.guarantee.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface ReserveManageService {
	String mainPage(HttpServletRequest request, Model model);
	String settingStep(int stepIdx, HttpServletRequest request, Model model);
	HashMap<String, Object> requestReserve(HashMap<String, Object> map);
	HashMap<String, Object> requestReserveAPI(HashMap<String, Object> map);
	String billboardPage(HttpServletRequest request, Model model);
	Map<String, Object> selectBillboardReserveList(HashMap<String, Object> map);
	Map<String, Object> updateAudio(HashMap<String, Object> map);
	Map<String,Object> getHoliday();
	void updateHoliday(String params, boolean status);
}
