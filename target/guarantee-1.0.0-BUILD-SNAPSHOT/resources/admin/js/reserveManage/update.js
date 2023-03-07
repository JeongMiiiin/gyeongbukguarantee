let reserveManageUpdate = {
	brIdx : null
	,openTime : null
	,closeTime : null
	,lunchOpenTime : null
	,lunchCloseTime : null
	,mondayOpenTime : null
	,mondayCloseTime : null
	,tuesdayOpenTime : null
	,tuesdayCloseTime : null
	,wedndayOpenTime : null
	,wedndayCloseTime : null
	,thursdayOpenTime : null
	,thursdayCloseTime : null
	,fridayOpenTime : null
	,fridayCloseTime : null
	,branchTimeSettingList : null
	,branchSpecficDateTimeSettingList : []
	,defaultConsultNum : 1
	,defaultConsultTime : null
	,defaultTimeList : null
	,selectTimeCon : null
	,initSettingTimeStatus : false
	,initSettingTimeValue : null
	,holidayList : makeHolidayArray()
	,branchHolidayList : null
	,branchFullReserveList : null
	,targetStatus : false

	,init : function(){
		
		this.selectBrCon = $('[name="brIdx"]');
		this.selectTimeCon = $('[name="rlReserveTime"]');
		this.brIdx = $(this.selectBrCon).val();
		
		this.targetStatus = targetStatus;
		
		this.getBranchData();
		
		if(this.brIdx != ''){
			this.makeCalendar();	
		} else {
			$('[data-common-datepicker]').attr("rea")
		}
		
		if(targetStatus || $('[name="rlReserveDate"]').val() != ''){
			
			let targetDate = $('[name="rlReserveDate"]').val() != '' ? $('[name="rlReserveDate"]').val() : target_date;  
			
			this.initSettingTimeValue = typeof($('[name="prevRlReserveTime"]').val()) != "undefined" ? $('[name="prevRlReserveTime"]').val() : target_time; 
						
			this.selectDateTime(targetDate, true);			
		}
		
		this.registEvent(); 	
	}
	,registEvent : function(){
		let _this = this;
		
		//영업전 변경시 이벤트
		$(_this.selectBrCon).on('change', function(){
			if(_this.brIdx != $(this).val() && $(this).val() != ''){
				_this.brIdx = $(this).val();
				_this.getBranchData();
				$('[name="rlReserveDate"]').val('');
				$(this).find('option[value=""]').remove();
				_this.makeCalendar();
				$('[name="rlReserveTime"]').empty();
			}
		});
		
		//날짜 선택시 이벤트
		$('[data-common-datepicker]').on('focus', function(){
			if($(_this.selectBrCon).val() == ""){
				$(_this.selectBrCon).focus();
				alert('영업점을 선택해주세요.');
				return false;
			} else {
				_this.makeCalendar();	
			}	
		});
				
	}
	
	,makeCalendar : function(){
		let _this = this;
		
		let maxDate = new Date();
		maxDate.setMonth(maxDate.getMonth() + 2);
		
		$('[data-common-datepicker]').datepicker({
				dateFormat : "yy-mm-dd"
				,showMonthAfterYear:true
				,yearSuffix: "년"
				,nextText: "다음달"
				,prevText: "이전달"
				,closeText: "닫기"
				,currentText: "오늘"
				,dayNames: ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"]
				,dayNamesMin: ["일","월","화","수","목","금","토"]
				,monthNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
				,monthNamesShort: ["1","2","3","4","5","6","7","8","9","10","11","12"]
				,minDate : 0
				,maxDate : maxDate
				,beforeShowDay: function(date){
					
					let day = date.getDay();
					
					if( day != 0 && day != 6 ){
						let y = date.getFullYear();
						let m = ((date.getMonth()+1) > 9) ? (date.getMonth()+1) : "0" + (date.getMonth()+1);
						let d = (date.getDate() > 9) ? date.getDate() : "0" + date.getDate();
							
						//holiday에 포함이 되는지 확인
						if(_this.holidayList.includes(y+m+d)){
							return [false, 'data-holiday'];
						} else if(_this.branchHolidayList.includes(y+'-'+m+'-'+d)) {
							return [false, 'data-branch-holiday'];	
						} else if(_this.branchFullReserveList.includes(y+'-'+m+'-'+d)) {
							return [false, 'data-branch-deadline'];	
						} else {
							return [true, ''];
						}
						
					} else {
						return [false, ''];
					}
	
				}
				,onSelect : function(dateText){
					_this.selectDateTime(dateText, false);
				}
				
		})
		
		$(this).datepicker('option', 'disabled', true);
	}
	
	,getBranchData : function(){
		let _this = this;
		
		let params = JSON.stringify({brIdx : _this.brIdx});
		$.ajax({
			url : "/reserve/adm/branchManage/selectTargetBranchData"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,async : false
			,timeoutDelay : 90000
			,success : function(result){
				_this.settingBranchData(result.data, result.timeList, result.dateList);

			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
		});
	}
	
	//지점 값 받아와서 세팅해주는 곳.
	,settingBranchData(data, timeList, dateList){
		let _this = this;
		
		_this.openTime = data.br_opened_time_str;
		_this.closeTime = data.br_closed_time_str;
		_this.lunchOpenTime = data.br_lunch_opened_time_str;
		_this.lunchCloseTime = data.br_lunch_closed_time_str;
		_this.defaultConsultTime = data.br_default_consult_time;
		
		_this.defaultConsultNum = data.br_default_counter_num;
		
		_this.branchTimeSettingList = [];
		
		if(timeList != null && timeList.length > 0){
			for(let i = 0; i < timeList.length; i++){
				let targetTimeStatus = timeList[i].gbts_status;
				
				switch(targetTimeStatus){
					case 1 :
					  _this.mondayOpenTime = timeList[i].gbts_start_time_str;
					  _this.mondayCloseTime = timeList[i].gbts_end_time_str;
					  break;
					case 2 :
					  _this.tuesdayOpenTime = timeList[i].gbts_start_time_str;
					  _this.tuesdayCloseTime = timeList[i].gbts_end_time_str;
					  break;
					case 3 :
					  _this.wedndayOpenTime = timeList[i].gbts_start_time_str;
					  _this.wedndayCloseTime = timeList[i].gbts_end_time_str;
					  break;
					case 4 :
					  _this.thursdayOpenTime = timeList[i].gbts_start_time_str;
					  _this.thursdayCloseTime = timeList[i].gbts_end_time_str;
					  break;
					case 5 :
					  _this.fridayOpenTime = timeList[i].gbts_start_time_str;
					  _this.fridayCloseTime = timeList[i].gbts_end_time_str;
					  break;
					case 6 :
					  _this.branchTimeSettingList.push(timeList[i]);
					  break;
					case 7 :
					  _this.branchSpecficDateTimeSettingList.push(timeList[i]);
					  break;
					default :
						break;
				}
			}
		}
		
		let nowDate = parsingControl.parseDateToStr(new Date(), '-', true);
		
		_this.defaultTimeList = _this.getTimeList(_this.openTime, _this.lunchOpenTime, _this.lunchCloseTime, _this.closeTime, nowDate);
		
		_this.branchHolidayList = [];
		_this.branchFullReserveList = [];
		
		if(dateList != null && dateList.length > 0){
			for(let j = 0; j < dateList.length; j++){
				let targetDateStatus = dateList[j].gbds_status;
				
				switch(targetDateStatus){
					case 2 :
						_this.branchHolidayList.push(dateList[j].gbds_date_str);
						break;
					case 3 :
						_this.branchFullReserveList.push(dateList[j].gbds_date_str);
						break;
					default :
						break;
				}
				
			}
		}
	}
	
	//가능시간대 구하기
	,getTimeList : function(startTime, lunchStartTime, lunchEndTime, endTime, targetDate){
		let _this = this;
		let result = [];
		
		let standardDay = targetDate.replace(/-/g, "/");
		
		let startTimeValue = new Date(standardDay + " " + startTime);
		let endTimeValue = new Date(standardDay + " " + endTime);
		
		let lunchStartTimeValue = new Date(standardDay + " " + lunchStartTime);
		let lunchEndTimeValue = new Date(standardDay + " " + lunchEndTime);
		
		let timeAMStartTimeValue = startTimeValue < lunchStartTimeValue ? startTimeValue : lunchStartTimeValue;
		let timeAMStartTimeHours = timeAMStartTimeValue.getHours() < 10 ? '0' + timeAMStartTimeValue.getHours() : timeAMStartTimeValue.getHours();
		let timeAMStartTimeMin = timeAMStartTimeValue.getMinutes() < 10 ? '0' + timeAMStartTimeValue.getMinutes() : timeAMStartTimeValue.getMinutes();
		let timeAMStartTime = timeAMStartTimeHours + ':' + timeAMStartTimeMin;
		let timeAMEndTimeValue = lunchStartTimeValue < endTimeValue ? lunchStartTimeValue : endTimeValue;
		
		let timePMStartTimeValue = startTimeValue < lunchEndTimeValue ? lunchEndTimeValue: startTimeValue;
		let timePMStartTimeHours = timePMStartTimeValue.getHours() < 10 ? '0' + timePMStartTimeValue.getHours() : timePMStartTimeValue.getHours();
		let timePMStartTimeMin = timePMStartTimeValue.getMinutes() < 10 ? '0' + timePMStartTimeValue.getMinutes() : timePMStartTimeValue.getMinutes();
		let timePMStartTime = timePMStartTimeHours + ':' + timePMStartTimeMin;
		let timePMEndTimeValue = lunchEndTimeValue < endTimeValue ? endTimeValue : lunchEndTimeValue;  
		
		let timeAMInterval = Math.floor((timeAMEndTimeValue - timeAMStartTimeValue) / 1000 / 60 / parseInt(_this.defaultConsultTime));
		let timePMInterval = Math.floor((timePMEndTimeValue - timePMStartTimeValue) / 1000 / 60 / parseInt(_this.defaultConsultTime));
		
		let targetAMStartTimeValue, targetAMEndTimeValue;
		
		//오전시간대 구하기
		for(let i=0; i < timeAMInterval; i++){
			targetAMStartTimeValue = new Date(standardDay + " " + timeAMStartTime);
			targetAMStartTimeValue.setMinutes(targetAMStartTimeValue.getMinutes() + parseInt(_this.defaultConsultTime)*i);
			
			targetAMEndTimeValue = new Date(standardDay + " " + timeAMStartTime);
			targetAMEndTimeValue.setMinutes(targetAMEndTimeValue.getMinutes() + parseInt(_this.defaultConsultTime)*(i+1));
			
			if(targetAMEndTimeValue <= timeAMEndTimeValue ){
				let timeAMHourValue = targetAMStartTimeValue.getHours() < 10 ? '0' + targetAMStartTimeValue.getHours() : targetAMStartTimeValue.getHours(); 
				let timeAMMinValue = targetAMStartTimeValue.getMinutes() < 10 ? '0' + targetAMStartTimeValue.getMinutes() : targetAMStartTimeValue.getMinutes();
				let timeAMValue = timeAMHourValue + ':' + timeAMMinValue;
				
				let z = _this.defaultConsultNum;
				
				//특정시간대 창구개수에 해당하는지 체크
				for(let j=0; j < _this.branchTimeSettingList.length; j++){
					if(TimeControl.compareTime(timeAMValue, _this.branchTimeSettingList[j].gbts_start_time) >= 0 && TimeControl.compareTime(timeAMValue, _this.branchTimeSettingList[j].gbts_end_time) < 1){
					 z = _this.branchTimeSettingList[j].gbts_counter_num;
					}
				}
				//특정일 특정시간대 창구개수에 해당하는지 체크
				for(let j2=0; j2 < _this.branchSpecficDateTimeSettingList.length; j2++){
					//특정일에 해당할때
					if(standardDay == _this.branchSpecficDateTimeSettingList[j2].gbts_target_date_str){
						if(TimeControl.compareTime(timeAMValue, _this.branchSpecficDateTimeSettingList[j2].gbts_start_time) >= 0 && TimeControl.compareTime(timeAMValue, _this.branchSpecficDateTimeSettingList[j2].gbts_end_time) < 1){
						 z = _this.branchSpecficDateTimeSettingList[j2].gbts_counter_num;
						}		
					}
				}
				
				let x = 0;
				while(x < z){
					result.push(timeAMValue);
					x++;	
				}
			}
			
		}
		
		let targetPMStartTimeValue, targetPMEndTimeValue;
		
		//오후시간대 구하기
		for(let _i=0; _i < timePMInterval; _i++){
			targetPMStartTimeValue = new Date(standardDay + " " + timePMStartTime);
			targetPMStartTimeValue.setMinutes(targetPMStartTimeValue.getMinutes() + parseInt(_this.defaultConsultTime)*_i);
			
			targetPMEndTimeValue = new Date(standardDay + " " + timePMStartTime);
			targetPMEndTimeValue.setMinutes(targetPMEndTimeValue.getMinutes() + parseInt(_this.defaultConsultTime)*(_i+1));
			
			if(targetPMEndTimeValue <= timePMEndTimeValue ){
				let timePMHourValue = targetPMStartTimeValue.getHours() < 10 ? '0' + targetPMStartTimeValue.getHours() : targetPMStartTimeValue.getHours(); 
				let timePMMinValue = targetPMStartTimeValue.getMinutes() < 10 ? '0' + targetPMStartTimeValue.getMinutes() : targetPMStartTimeValue.getMinutes();
				let timePMValue = timePMHourValue + ':' + timePMMinValue;
				
				let _z = _this.defaultConsultNum;
				
				for(let _j=0; _j < _this.branchTimeSettingList.length; _j++){
					if(TimeControl.compareTime(timePMValue, _this.branchTimeSettingList[_j].gbts_start_time) >= 0 && TimeControl.compareTime(timePMValue, _this.branchTimeSettingList[_j].gbts_end_time) < 1){
					 _z = _this.branchTimeSettingList[_j].gbts_counter_num;
					}
				}
				//특정일 특정시간대 창구개수에 해당하는지 체크
				for(let _j2=0; _j2 < _this.branchSpecficDateTimeSettingList.length; _j2++){
					//특정일에 해당할때
					if(standardDay == _this.branchSpecficDateTimeSettingList[_j2].gbts_target_date_str){
						if(TimeControl.compareTime(timePMValue, _this.branchSpecficDateTimeSettingList[_j2].gbts_start_time) >= 0 && TimeControl.compareTime(timePMValue, _this.branchSpecficDateTimeSettingList[_j2].gbts_end_time) < 1){
						 _z = _this.branchSpecficDateTimeSettingList[_j2].gbts_counter_num;
						}		
					}
				}
				
				let _x = 0;
				while(_x < _z){
					result.push(timePMValue);
					_x++;	
				}
			}
			
		}
		
		return result;
	}
	
	,selectDateTime : function(targetDate, initStatus){
		
		let _this = this;
		
		if( initStatus ){
			_this.initSettingTimeStatus = true;	
		} else {
			_this.initSettingTimeStatus = false;
		}
		
		let params = JSON.stringify({brIdx : _this.brIdx, selectDate : targetDate});
		$.ajax({
			url : "/reserve/adm/reserveManageList/selectDateTimeList"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,async : false
			,timeoutDelay : 90000
			,success : function(result){
				if(result.result){
					_this.defaultConsultTime = result.consultTime;
					
					if(result.consultNum > -1){
						_this.defaultConsultNum = result.consultNum;	
					}
					
					_this.divideSpeicalTimeList(result.specialTimeList);
					
					_this.checkOperationTime(result.operationTime, result.data, targetDate);
					
				}

			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
		});
	}
	
	,divideSpeicalTimeList : function(specialTimeList){
		let _this = this;

		_this.branchTimeSettingList = [];
		_this.branchSpecficDateTimeSettingList = [];

		for(let i=0; i < specialTimeList.length; i++){
			if(specialTimeList[i].gbts_status == 6){
				_this.branchTimeSettingList.push(specialTimeList[i]);		
			} else {
				_this.branchSpecficDateTimeSettingList.push(specialTimeList[i]);
			}
		}
	}
	
	,checkOperationTime : function(operationTime, reserveList, targetDate){
		let _this = this;
		
		let targetOperationOpenTime = operationTime[0].br_opened_time;
		let targetOperationLunchOpenTime = operationTime[0].br_lunch_opened_time;
		let targetOperationCloseTime = operationTime[0].br_closed_time;
		let targetOperationLunchCloseTime = operationTime[0].br_lunch_closed_time;
		
		let changeTimeList = _this.getTimeList(targetOperationOpenTime, targetOperationLunchOpenTime, targetOperationLunchCloseTime, targetOperationCloseTime, targetDate);
		
		_this.changeDateTime(changeTimeList, reserveList);
		
	}
	
	,changeDateTime : function(changeTimeList, timeList){
		let _this = this;
		
		if(timeList.length > 0){
			let i = 0;
			if( _this.initSettingTimeStatus ){
				while(i < timeList.length){
					if(_this.initSettingTimeValue != timeList[i].rl_reserve_time_str) changeTimeList = _this.removeItem(changeTimeList, timeList[i].rl_reserve_time_str);
					i++;
				}	
			} else {
				while(i < timeList.length){
					changeTimeList = _this.removeItem(changeTimeList, timeList[i].rl_reserve_time_str);				
					i++;
				}
			}
			
		}
		
		this.settingTimeList(changeTimeList);
		
	}
	
	,settingTimeList : function(timeList){
		
		let _this = this;
		
		timeList = Array.from(new Set(timeList));
		
		$(_this.selectTimeCon).val('');
		$(_this.selectTimeCon).empty();
		
		if(timeList.length > 0){
			let i = 0;
	
			if( _this.initSettingTimeStatus ){
				
				while(i < timeList.length){
					$(_this.selectTimeCon).append('<option value="'+ timeList[i] + '">' + timeList[i] + '</option>');
				
					if(_this.initSettingTimeValue == timeList[i]) $(_this.selectTimeCon).find('> option').last().attr('selected', true); 
				
					i++;	
				}			
			} else {
				while(i < timeList.length){
					$(_this.selectTimeCon).append('<option value="'+ timeList[i] + '">' + timeList[i] + '</option>'); 
					i++;	
				}
			}
			
		}
		
	}
	
	//아이템 값이 처음 나오는 배열 아이템 삭제
	,removeItem : function(arr, value) {
	  var index = arr.indexOf(value);
	  if (index > -1) {
	    arr.splice(index, 1);
	  }
	  return arr;
	}
	
	,removeEvent: function(){
		
	}
	,destroy: function(){
		
	}
}

$(document).ready(function(){
	reserveManageUpdate.init();
})