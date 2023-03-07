package com.gyeongbuk.spring.guarantee.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

@Component
public class ReserveDto extends PageDto {
	private int rlIdx;
	private int brIdx;
	private int memIdx;
	private int rlStatus;
	private int rlDirect;
	private String rlName;
	private String brName;
	private String brAddress;
	private String rlHp;
	private String rlBusinessType;
	private String rlReserveDate;
	private String prevRlReserveDate;
	private String rlReserveDateStr;
	private String rlReserveTime;
	private String rlReserveTimeStr;
	private String prevRlReserveTime;
	public int getRlIdx() {
		return rlIdx;
	}
	public void setRlIdx(int rlIdx) {
		this.rlIdx = rlIdx;
	}
	public int getBrIdx() {
		return brIdx;
	}
	public void setBrIdx(int brIdx) {
		this.brIdx = brIdx;
	}
	public int getMemIdx() {
		return memIdx;
	}
	public void setMemIdx(int memIdx) {
		this.memIdx = memIdx;
	}
	public int getRlStatus() {
		return rlStatus;
	}
	public void setRlStatus(int rlStatus) {
		this.rlStatus = rlStatus;
	}
	public int getRlDirect() {
		return rlDirect;
	}
	public void setRlDirect(int rlDirect) {
		this.rlDirect = rlDirect;
	}
	public String getRlName() {
		return rlName;
	}
	public void setRlName(String rlName) {
		this.rlName = rlName;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getBrAddress() {
		return brAddress;
	}
	public void setBrAddress(String brAddress) {
		this.brAddress = brAddress;
	}
	public String getRlHp() {
		return rlHp;
	}
	public void setRlHp(String rlHp) {
		this.rlHp = rlHp;
	}
	public String getRlBusinessType() {
		return rlBusinessType;
	}
	public void setRlBusinessType(String rlBusinessType) {
		this.rlBusinessType = rlBusinessType;
	}
	public String getRlReserveDate() {
		return rlReserveDate;
	}
	public void setRlReserveDate(String rlReserveDate) {
		this.rlReserveDate = rlReserveDate;
	}
	public String getPrevRlReserveDate() {
		return prevRlReserveDate;
	}
	public void setPrevRlReserveDate(String prevRlReserveDate) {
		this.prevRlReserveDate = prevRlReserveDate;
	}
	public String getRlReserveDateStr() {
		return rlReserveDateStr;
	}
	public void setRlReserveDateStr(String rlReserveDateStr) {
		this.rlReserveDateStr = rlReserveDateStr;
	}
	public String getRlReserveTime() {
		return rlReserveTime;
	}
	public void setRlReserveTime(String rlReserveTime) {
		this.rlReserveTime = rlReserveTime;
	}
	public String getRlReserveTimeStr() {
		return rlReserveTimeStr;
	}
	public void setRlReserveTimeStr(String rlReserveTimeStr) {
		this.rlReserveTimeStr = rlReserveTimeStr;
	}
	public String getPrevRlReserveTime() {
		return prevRlReserveTime;
	}
	public void setPrevRlReserveTime(String prevRlReserveTime) {
		this.prevRlReserveTime = prevRlReserveTime;
	}
	
	private String searchBrName;
	private String searchName;
	private String searchHp;
	private String searchRlStatus;
	private String searchStartDate;
	private String searchEndDate;
	private int searchMemIdx;
	
	public String getSearchBrName() {
		return searchBrName;
	}
	public void setSearchBrName(String searchBrName) {
		this.searchBrName = searchBrName;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchHp() {
		return searchHp;
	}
	public void setSearchHp(String searchHp) {
		this.searchHp = searchHp;
	}
	public String getSearchRlStatus() {
		return searchRlStatus;
	}
	public void setSearchRlStatus(String searchRlStatus) {
		this.searchRlStatus = searchRlStatus;
	}
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public int getSearchMemIdx() {
		return searchMemIdx;
	}
	public void setSearchMemIdx(int searchMemIdx) {
		this.searchMemIdx = searchMemIdx;
	}
	
	private String qustr;
	
	public String getQustr() {
		return qustr;
	}
	
	public void setQustr() throws UnsupportedEncodingException {
		String qs = "";
		this.setQueryString();
		qs += this.getQueryString();
		if( this.searchName != null && this.searchName != "" ) {
			qs +="&searchName="+URLEncoder.encode(this.searchName, "UTF-8");
		}
		
		if( this.searchStartDate != null && this.searchStartDate != "" ) {
			qs +="&searchStartDate="+URLEncoder.encode(this.searchStartDate, "UTF-8");
		}
		
		if( this.searchEndDate != null && this.searchEndDate != "" ) {
			qs +="&searchEndDate="+URLEncoder.encode(this.searchEndDate, "UTF-8");
		}
		
		if( this.searchBrName != null && this.searchBrName != "" ) {
			qs +="&searchBrName="+URLEncoder.encode(this.searchBrName, "UTF-8");
		}

		this.qustr = qs;
		
	}
	
	private String rlPrevReserveDate;
	public String getRlPrevReserveDate() {
		return rlPrevReserveDate;
	}
	public void setRlPrevReserveDate(String rlPrevReserveDate) {
		this.rlPrevReserveDate = rlPrevReserveDate;
	}
	
}
