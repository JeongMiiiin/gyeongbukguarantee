package com.gyeongbuk.spring.guarantee.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface MemberAdmDao {
	
	String SERVICE_ID = "member.admin";
	
	public HashMap<String, Object> login(Map<String, Object> params);
	public int checkIp(Map<String, Object> params);
	public int inquiryIp(HashMap<String, Object> params);
	public HashMap<String, Object> inquiryIpCheckId(HashMap<String, Object> params);
	public int changePassword(HashMap<String, Object> params);
	public void updateCertificationCode(HashMap<String, Object> params);
	public int checkCertificationCode(HashMap<String, Object> params);
}
