package com.gyeongbuk.spring.guarantee.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportAPIServiceImpl implements ExportAPIService {
	
	@Autowired
	private ReserveManageService reserveManageService;
	
	@Autowired
	private ReserveManageListAdmService reserveManageAdmService;
	
	//두달치의 예약 데이터 조회
	@Override
	public HashMap<String, Object> reserveListAPI(HashMap<String, Object> params) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		
		List<HashMap<String, Object>> data = reserveManageAdmService.reserveListAPI(params);
					
		result.put("result", true);
		result.put("data", data);
					
		return result;
	}
	
	/*
	 * 예약추천 
	 */ 
	@Override
	public HashMap<String, Object> reserveRecommend(HashMap<String, Object> params) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		
		List<HashMap<String, Object>> data = reserveManageAdmService.reserveListAPI(params);
					
		result.put("result", true);
		result.put("data", data);
					
		return result;
	}
	
	/*
	 * params : br_idx -> 해당지점 고유번호, rl_name -> 이름, rl_hp -> 핸드폰 번호, rl_business_type -> 사업자종류, rl_reserve_date -> 예약일, rl_reserve_time -> 예약시간
	 * result : HashMap ( result.result -> 0 : 실패, 1 : 성공, 2 : 해당 날짜에 중복 예약이 있음, 3 : 예약은 완료했으나 메세지 전송 실패 ) 
	 * API로 예약 추가 
	 */ 
	@Override
	public HashMap<String, Object> requestReserve(HashMap<String, Object> params) {

		params.put("rlDirect", 3);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
			
		int requestReserve = Integer.parseInt( String.valueOf(reserveManageService.requestReserveAPI(params).get("result")) );
		
		result.put("result", requestReserve);
		
		return result;
	}
	
}
