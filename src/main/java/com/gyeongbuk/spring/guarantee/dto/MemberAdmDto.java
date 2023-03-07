package com.gyeongbuk.spring.guarantee.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

@Component
public class MemberAdmDto extends PageDto {
	private int memIdx;
	private String memUserId;
	private String memPassword;
	private String memUserName;
	private int memLevel;
	private String memPhone;
	private String memAccessIp;
	private String memIsFirst;
	
	public int getMemIdx() {
		return memIdx;
	}
	public void setMemIdx(int memIdx) {
		this.memIdx = memIdx;
	}
	public String getMemUserId() {
		return memUserId;
	}
	public void setMemUserId(String memUserId) {
		this.memUserId = memUserId;
	}
	public String getMemPassword() {
		return memPassword;
	}
	public void setMemPassword(String memPassword) {
		this.memPassword = memPassword;
	}
	public String getMemUserName() {
		return memUserName;
	}
	public void setMemUserName(String memUserName) {
		this.memUserName = memUserName;
	}
	public int getMemLevel() {
		return memLevel;
	}
	public void setMemLevel(int memLevel) {
		this.memLevel = memLevel;
	}
	public String getMemPhone() {
		return memPhone;
	}
	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}
	public String getMemAccessIp() {
		return memAccessIp;
	}
	public void setMemAccessIp(String memAccessIp) {
		this.memAccessIp = memAccessIp;
	}
	public String getMemIsFirst() {
		return memIsFirst;
	}
	public void setMemIsFirst(String memIsFirst) {
		this.memIsFirst = memIsFirst;
	}
	
	private String searchId;
	private String searchName;

	public String getSearchId() {
		return searchId;
	}
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	private String qustr;
	
	public String getQustr() {
		return qustr;
	}
	
	public void setQustr() throws UnsupportedEncodingException {
		String qs = "";
		this.setQueryString();
		qs += this.getQueryString();
		if( this.searchId != null && this.searchId != "" ) {
			qs +="&searchId="+URLEncoder.encode(this.searchId, "UTF-8");
		}
		
		if( this.searchName != null && this.searchName != "" ) {
			qs +="&searchName="+URLEncoder.encode(this.searchName, "UTF-8");
		}

		this.qustr = qs;
		
	}
}
