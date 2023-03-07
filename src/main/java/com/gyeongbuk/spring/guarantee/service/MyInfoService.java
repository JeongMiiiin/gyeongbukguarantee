package com.gyeongbuk.spring.guarantee.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MyInfoService {
	String listPage(HttpServletRequest request, Model model);
	HashMap<String, Object> deleteReserve(HashMap<String, ArrayList> map);
}
