package com.gyeongbuk.spring.guarantee.dto;

import org.springframework.stereotype.Component;

@Component
public class BranchDateSettingDto {
	private int gbdsIdx;
	private int brIdx;
	private int gbdsStatus;
	private int gbdsCounterNum;
	private String gbdsDate;
	private String gbdsDateStr;
	private String gbdsAppliedAt;
	private String gbdsAppliedAtStr;
	public int getGbdsIdx() {
		return gbdsIdx;
	}
	public void setGbdsIdx(int gbdsIdx) {
		this.gbdsIdx = gbdsIdx;
	}
	public int getBrIdx() {
		return brIdx;
	}
	public void setBrIdx(int brIdx) {
		this.brIdx = brIdx;
	}
	public int getGbdsStatus() {
		return gbdsStatus;
	}
	public void setGbdsStatus(int gbdsStatus) {
		this.gbdsStatus = gbdsStatus;
	}
	public int getGbdsCounterNum() {
		return gbdsCounterNum;
	}
	public void setGbdsCounterNum(int gbdsCounterNum) {
		this.gbdsCounterNum = gbdsCounterNum;
	}
	public String getGbdsDate() {
		return gbdsDate;
	}
	public void setGbdsDate(String gbdsDate) {
		this.gbdsDate = gbdsDate;
	}
	public String getGbdsDateStr() {
		return gbdsDateStr;
	}
	public void setGbdsDateStr(String gbdsDateStr) {
		this.gbdsDateStr = gbdsDateStr;
	}
	public String getGbdsAppliedAt() {
		return gbdsAppliedAt;
	}
	public void setGbdsAppliedAt(String gbdsAppliedAt) {
		this.gbdsAppliedAt = gbdsAppliedAt;
	}
	public String getGbdsAppliedAtStr() {
		return gbdsAppliedAtStr;
	}
	public void setGbdsAppliedAtStr(String gbdsAppliedAtStr) {
		this.gbdsAppliedAtStr = gbdsAppliedAtStr;
	}
}
