package com.gyeongbuk.spring.guarantee.service;

import java.util.HashMap;

public interface ExportAPIService {
	HashMap<String, Object> reserveListAPI(HashMap<String, Object> params);
	HashMap<String, Object> reserveRecommend(HashMap<String, Object> params);
	HashMap<String, Object> requestReserve(HashMap<String, Object> params);
}