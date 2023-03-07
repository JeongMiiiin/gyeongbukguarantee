package com.gyeongbuk.spring.guarantee.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

@Component
public class InquiryIpDto extends PageDto {
	private int milIdx;
	private int memIdx;
	private String memUserName;
	private String memUserId;
	private String milOriginIp;
	private String milAccessIp;
	private String milIsApproved;
	private String milRequestAtStr;
	public int getMilIdx() {
		return milIdx;
	}
	public void setMilIdx(int milIdx) {
		this.milIdx = milIdx;
	}
	public int getMemIdx() {
		return memIdx;
	}
	public void setMemIdx(int memIdx) {
		this.memIdx = memIdx;
	}
	public String getMemUserName() {
		return memUserName;
	}
	public void setMemUserName(String memUserName) {
		this.memUserName = memUserName;
	}
	public String getMemUserId() {
		return memUserId;
	}
	public void setMemUserId(String memUserId) {
		this.memUserId = memUserId;
	}
	public String getMilOriginIp() {
		return milOriginIp;
	}
	public void setMilOriginIp(String milOriginIp) {
		this.milOriginIp = milOriginIp;
	}
	public String getMilAccessIp() {
		return milAccessIp;
	}
	public void setMilAccessIp(String milAccessIp) {
		this.milAccessIp = milAccessIp;
	}
	public String getMilIsApproved() {
		return milIsApproved;
	}
	public void setMilIsApproved(String milIsApproved) {
		this.milIsApproved = milIsApproved;
	}
	public String getMilRequestAtStr() {
		return milRequestAtStr;
	}
	public void setMilRequestAtStr(String milRequestAtStr) {
		this.milRequestAtStr = milRequestAtStr;
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
