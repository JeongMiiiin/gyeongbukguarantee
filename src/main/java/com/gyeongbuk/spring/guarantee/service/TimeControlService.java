package com.gyeongbuk.spring.guarantee.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TimeControlService {
	/*
	 * 시간 비교해주는 함수 param : targetTime, compareTime (HH:mm 형식)
	 * return int; 
	 * -1 : targetTime이 과거인 경우 0 : 같은 경우 1 : targetTime이 미래인 경우
	 */
	public int compareTime(String targetTime, String compareTime) throws ParseException {
		int result = 0;
		String standardDay = "2022-04-06";
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date targetTimeValue = dateFormatter.parse(standardDay + " " + targetTime);
		Date compareTimeValue = dateFormatter.parse(standardDay + " " + compareTime);

		result = targetTimeValue.compareTo(compareTimeValue);

		return result;
	}
}
