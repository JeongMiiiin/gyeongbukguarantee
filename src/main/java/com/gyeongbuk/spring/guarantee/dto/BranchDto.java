package com.gyeongbuk.spring.guarantee.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BranchDto {
	private int brIdx;
	private String brName;
	private String brCategoryName;
	private String brAddress;
	private String brHp;
	private int memIdx;
	private String memUserName;
	private String brOpenedTime;
	private String brOpenedTimeStr;
	private String brClosedTime;
	private String brClosedTimeStr;
	private String brLunchOpenedTime;
	private String brLunchOpenedTimeStr;
	private String brLunchClosedTime;
	private String brLunchClosedTimeStr;
	private String brDefaultCounterNum;
	private String brDefaultConsultTime;
	
	//매니저를 관리하기 위한 List
	private List<MemberAdmDto> memberAdminDtoList;
	
	//특정시간대별 세팅 관리를 위한 List
	private List<BranchTimeSettingDto> branchTimeSettingDtoList;

	//특정일별 세팅 관리를 위한 List
	private List<BranchDateSettingDto> branchDateSettingDtoList;
	
	//특정일 특정시간대별 세팅 관리를 위한 List
	private List<BranchTimeSettingDto> branchSpecficDateTimeSettingDtoList;
	
	public int getBrIdx() {
		return brIdx;
	}
	public void setBrIdx(int brIdx) {
		this.brIdx = brIdx;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getBrCategoryName() {
		return brCategoryName;
	}
	public void setBrCategoryName(String brCategoryName) {
		this.brCategoryName = brCategoryName;
	}
	public String getBrAddress() {
		return brAddress;
	}
	public void setBrAddress(String brAddress) {
		this.brAddress = brAddress;
	}
	public String getBrHp() {
		return brHp;
	}
	public void setBrHp(String brHp) {
		this.brHp = brHp;
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
	public String getBrOpenedTime() {
		return brOpenedTime;
	}
	public void setBrOpenedTime(String brOpenedTime) {
		this.brOpenedTime = brOpenedTime;
	}
	public String getBrOpenedTimeStr() {
		return brOpenedTimeStr;
	}
	public void setBrOpenedTimeStr(String brOpenedTimeStr) {
		this.brOpenedTimeStr = brOpenedTimeStr;
	}
	public String getBrClosedTime() {
		return brClosedTime;
	}
	public void setBrClosedTime(String brClosedTime) {
		this.brClosedTime = brClosedTime;
	}
	public String getBrClosedTimeStr() {
		return brClosedTimeStr;
	}
	public void setBrClosedTimeStr(String brClosedTimeStr) {
		this.brClosedTimeStr = brClosedTimeStr;
	}
	public String getBrLunchOpenedTime() {
		return brLunchOpenedTime;
	}
	public void setBrLunchOpenedTime(String brLunchOpenedTime) {
		this.brLunchOpenedTime = brLunchOpenedTime;
	}
	public String getBrLunchOpenedTimeStr() {
		return brLunchOpenedTimeStr;
	}
	public void setBrLunchOpenedTimeStr(String brLunchOpenedTimeStr) {
		this.brLunchOpenedTimeStr = brLunchOpenedTimeStr;
	}
	public String getBrLunchClosedTime() {
		return brLunchClosedTime;
	}
	public void setBrLunchClosedTime(String brLunchClosedTime) {
		this.brLunchClosedTime = brLunchClosedTime;
	}
	public String getBrLunchClosedTimeStr() {
		return brLunchClosedTimeStr;
	}
	public void setBrLunchClosedTimeStr(String brLunchClosedTimeStr) {
		this.brLunchClosedTimeStr = brLunchClosedTimeStr;
	}
	public String getBrDefaultCounterNum() {
		return brDefaultCounterNum;
	}
	public void setBrDefaultCounterNum(String brDefaultCounterNum) {
		this.brDefaultCounterNum = brDefaultCounterNum;
	}
	public String getBrDefaultConsultTime() {
		return brDefaultConsultTime;
	}
	public void setBrDefaultConsultTime(String brDefaultConsultTime) {
		this.brDefaultConsultTime = brDefaultConsultTime;
	}
	public List<MemberAdmDto> getmemberAdminDtoList() {
		return memberAdminDtoList;
	}
	public void setmemberAdminDtoList(List<MemberAdmDto> memberAdminDtoList) {
		this.memberAdminDtoList = memberAdminDtoList;
	}
	public List<BranchTimeSettingDto> getBranchTimeSettingDtoList() {
		return branchTimeSettingDtoList;
	}
	public void setBranchTimeSettingDtoList(List<BranchTimeSettingDto> branchTimeSettingDtoList) {
		this.branchTimeSettingDtoList = branchTimeSettingDtoList;
	}
	public List<BranchDateSettingDto> getBranchDateSettingDtoList() {
		return branchDateSettingDtoList;
	}
	public void setBranchDateSettingDtoList(List<BranchDateSettingDto> branchDateSettingDtoList) {
		this.branchDateSettingDtoList = branchDateSettingDtoList;
	}
	public List<BranchTimeSettingDto> getBranchSpecficDateTimeSettingDtoList() {
		return branchSpecficDateTimeSettingDtoList;
	}
	public void setBranchSpecficDateTimeSettingDtoList(List<BranchTimeSettingDto> branchSpecficDateTimeSettingDtoList) {
		this.branchSpecficDateTimeSettingDtoList = branchSpecficDateTimeSettingDtoList;
	}
}
