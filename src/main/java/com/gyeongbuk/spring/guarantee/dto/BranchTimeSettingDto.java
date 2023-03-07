package com.gyeongbuk.spring.guarantee.dto;

import org.springframework.stereotype.Component;

@Component
public class BranchTimeSettingDto {
	private int gbtsIdx;
	private int brIdx;
	private int gbtsStatus;
	private String gbtsStartTime;
	private String gbtsStartTimeStr;
	private String gbtsEndTime;
	private String gbtsEndTimeStr;
	private String gbtsAppliedAt;
	private String gbtsAppliedAtStr;
	private String gbtsCounterNum;
	private String gbtsTargetDate;
	private String gbtsTargetDateStr;
	public int getGbtsIdx() {
		return gbtsIdx;
	}
	public void setGbtsIdx(int gbtsIdx) {
		this.gbtsIdx = gbtsIdx;
	}
	public int getBrIdx() {
		return brIdx;
	}
	public void setBrIdx(int brIdx) {
		this.brIdx = brIdx;
	}
	public int getGbtsStatus() {
		return gbtsStatus;
	}
	public void setGbtsStatus(int gbtsStatus) {
		this.gbtsStatus = gbtsStatus;
	}
	public String getGbtsStartTime() {
		return gbtsStartTime;
	}
	public void setGbtsStartTime(String gbtsStartTime) {
		this.gbtsStartTime = gbtsStartTime;
	}
	public String getGbtsStartTimeStr() {
		return gbtsStartTimeStr;
	}
	public void setGbtsStartTimeStr(String gbtsStartTimeStr) {
		this.gbtsStartTimeStr = gbtsStartTimeStr;
	}
	public String getGbtsEndTime() {
		return gbtsEndTime;
	}
	public void setGbtsEndTime(String gbtsEndTime) {
		this.gbtsEndTime = gbtsEndTime;
	}
	public String getGbtsEndTimeStr() {
		return gbtsEndTimeStr;
	}
	public void setGbtsEndTimeStr(String gbtsEndTimeStr) {
		this.gbtsEndTimeStr = gbtsEndTimeStr;
	}
	public String getGbtsAppliedAt() {
		return gbtsAppliedAt;
	}
	public void setGbtsAppliedAt(String gbtsAppliedAt) {
		this.gbtsAppliedAt = gbtsAppliedAt;
	}
	public String getGbtsAppliedAtStr() {
		return gbtsAppliedAtStr;
	}
	public void setGbtsAppliedAtStr(String gbtsAppliedAtStr) {
		this.gbtsAppliedAtStr = gbtsAppliedAtStr;
	}
	public String getGbtsCounterNum() {
		return gbtsCounterNum;
	}
	public void setGbtsCounterNum(String gbtsCounterNum) {
		this.gbtsCounterNum = gbtsCounterNum;
	}
	public String getGbtsTargetDate() {
		return gbtsTargetDate;
	}
	public void setGbtsTargetDate(String gbtsTargetDate) {
		this.gbtsTargetDate = gbtsTargetDate;
	}
	public String getGbtsTargetDateStr() {
		return gbtsTargetDateStr;
	}
	public void setGbtsTargetDateStr(String gbtsTargetDateStr) {
		this.gbtsTargetDateStr = gbtsTargetDateStr;
	}
}
