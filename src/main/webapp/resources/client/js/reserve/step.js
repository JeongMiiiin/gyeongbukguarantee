let reserveStep = {
	
	currentStep : 0
	,prevBtn : null
	,prevLinkInfo : null
	,nextBtn : null
	,nextLinkInfo : null
	,brIdx : null
	,defaultTimeList : null
	,reserveList : null
	,holidayList : null
	,branchHolidayList : null
	
	
	,init : function(){
		
		this.currentStep = curStep;
		this.prevBtn = $('[data-step-prev-btn]');
		this.nextBtn = $('[data-step-next-btn]');
		
		if(this.currentStep == 4){
			if($('[name="certificateStatus"]').val() == 1){
				$('[name="userinfoAgreeY"]').prop("checked", true);
				$('#hpCertificateCon').show();
			}
		}
		
		this.registEvent();
	}
	
	,registEvent : function(){
		let _this = this;
		
		$(this.prevBtn).on('click', function(e){
			
			e.preventDefault();
			e.stopPropagation();
			
			_this.prevLinkInfo = $(this).attr('href');
			
			popupManager.add('#prevStepPopup');
		});
		
		$(this.nextBtn).on('click', function(e){
			
			e.preventDefault();
			e.stopPropagation();
			
			_this.nextLinkInfo = $(this).attr('href');
			
			popupManager.add('#nextStepPopup');
		});
		
		switch(this.currentStep){
			case 5 :
				$('#rlBusinessY').on('click', function(){
					if($(this).is(':checked')){
						//예를 선택했을 시
						$('[name="rlBusinessType"]').prop('checked', false);
						$('#rlBusinessTypeC').prop('checked', true);
					}
				});
				$('#rlBusinessN').on('click', function(){
					if($(this).is(':checked')){
						//아니오를 선택했을 시
						popupManager.add('#step5Popup');
					}
				});				
				break;
			case 6 :
				$('.place_list_box').find('a').on('click', function(){
					$('.place_list_box').find('a').removeClass('active');
					$(this).addClass('active');
					
					let br_idx = $(this).attr('data-br-idx');
					
					$('#selectBrIdx').val(br_idx);
					
				})
				
				break;
			case 7 :
				let nowDate = parsingControl.parseDateToStr(new Date(), '-', true);   			
			
				_this.branchTimeSettingList = branchTimeSettingList;
				_this.branchSpecficDateTimeSettingList = branchSpecficDateTimeSettingList;
				_this.brIdx = curBrIdx;
				_this.defaultConsultNum = defaultConsultNum;
				_this.openTime = openTime;
				_this.closeTime = closeTime;
				_this.lunchOpenTime = lunchOpenTime;
				_this.lunchCloseTime = lunchCloseTime;
				_this.defaultConsultTime = defaultConsultTime;
				_this.mondayOpenTime = mondayOpenTime;
				_this.mondayCloseTime = mondayCloseTime;
				_this.tuesdayOpenTime = tuesdayOpenTime;
				_this.tuesdayCloseTime = tuesdayCloseTime;
				_this.wedndayOpenTime = wedndayOpenTime;
				_this.wedndayCloseTime = wedndayCloseTime;
				_this.thursdayOpenTime = thursdayOpenTime;
				_this.thursdayCloseTime = thursdayCloseTime;
				_this.fridayOpenTime = fridayOpenTime;
				_this.fridayCloseTime = fridayCloseTime;
				_this.defaultTimeList = _this.getTimeList(_this.openTime, _this.lunchOpenTime, _this.lunchCloseTime, _this.closeTime, nowDate);
				_this.holidayList = makeHolidayArray();
				_this.branchHolidayList = branchDateList;
				_this.fullReserveList = fullReserveList;
				
				_this.openCalendar();
				break;
			default : break;
		}
		
	}
	
	,prevStep : function(){
		let _this = this;
		
		window.location.href = _this.prevLinkInfo;
	}
	
	,nextStep : function(){
		let _this = this;
		
		let checkStatus = false;
		
		switch(_this.currentStep){
			case 2 : //이용안내 확인
				if( !$('#checkStep2').is(':checked') ){
					popupManager.removePopLatest(true);
					popupManager.add('#checkStep2Popup');
				} else {
					checkStatus = true;		
				}
				break;
			case 3 : //자가진단 확인
				if( !$('#checkStep3N').is(':checked') ){
					popupManager.removePopLatest(true);
					popupManager.add('#checkStep3Popup');
				} else {
					checkStatus = true;
				}
				break;
			case 4 :
				if( !$('#userinfoAgreeY').is(':checked') ){
					popupManager.removePopLatest(true);
					popupManager.add('#checkStep4Popup1');
				} else if($('#certificateStatus').val() != 1) {
					popupManager.removePopLatest(true);
					popupManager.add('#checkStep4Popup2');
				} else {
					checkStatus = true;	
				}
				
				
				break;
			case 5 :
				if($('#rlBusinessN').is(':checked')){
					popupManager.removePopLatest(true);
					popupManager.add('#checkStep5Popup');
				} else {
					let getBsType = $('[name="rlBusinessType"]:checked').val(); 				
					_this.nextLinkInfo += ('&rl_business_type=' + getBsType);
					checkStatus = true;	
				}
				break;
			case 6 :
				if($('.place_list_box').find('a.active').length == 0){
					popupManager.removePopLatest(true);
					popupManager.add('#checkStep6Popup');
				} else {
					
					let search = location.search;
					let params = new URLSearchParams(search);
					
					let getRlBsType = params.get('rl_business_type');
	
					_this.nextLinkInfo += ('&rl_business_type=' + getRlBsType);
	
					let getBrIdx = $('#selectBrIdx').val();
					
					_this.nextLinkInfo += ('&br_idx=' + getBrIdx);
					
					checkStatus = true; 
				}
				
				break;
			default :
				checkStatus = true;
				break;
		}
		
		if( checkStatus ){
			window.location.href = _this.nextLinkInfo;
		}
	}
	
	//개인정보 이용동의 체크 시 본인인증 container 등장
	,controlCertificateCon : function(checkbox){
		if( $(checkbox).is(':checked') ){
			$('#hpCertificateCon').show();
		} else {
			$('#hpCertificateCon').hide();
		}
	}
	
	//날짜 선택 달력 열기
	,openCalendar : function(){
		let _this = this;
		
		let maxDate = new Date();
		maxDate.setMonth(maxDate.getMonth() + 2);
		
		$('[data-common-datepicker]').datepicker({
			dateFormat : "yy-mm-dd"
			,minDate : 1
			,maxDate : maxDate
			,nextText: "다음달"
			,prevText: "이전달"
			,closeText: "닫기"
			,currentText: "오늘"
			,dayNames: ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"]
			,dayNamesMin: ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"]
			,monthNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
			,monthNamesShort: ["1","2","3","4","5","6","7","8","9","10","11","12"]
			,showMonthAfterYear: true
			,yearSuffix: "년"
			,beforeShowDay: function(date){
				let day = date.getDay();
				
				if( day != 0 && day != 6){
					
					let y = date.getFullYear();
					let m = ((date.getMonth()+1) > 9) ? (date.getMonth()+1) : "0" + (date.getMonth()+1);
					let d = (date.getDate() > 9) ? date.getDate() : "0" + date.getDate();
					
					let todayDate = new Date();
					todayDate.setHours(0);
					todayDate.setMinutes(0);
					todayDate.setSeconds(0);
					todayDate.setMilliseconds(0);
					
					//holiday에 포함이 되는지 확인
					if(_this.holidayList.includes(y+m+d)){
						//holiday에 포함이 되는지 확인
						return [false, 'data-holiday'];
					} else if(date <= todayDate){
						//오늘 이전에 해당하는 날짜인지 확인
						return [false, '']
					} else if(_this.branchHolidayList.includes(y+'-'+m+'-'+d)) {
						//휴무 설정한 날짜에 포함 되는지 확인
						return [false, 'data-branch-holiday'];	
					} else if(_this.fullReserveList.includes(y+'-'+m+'-'+d)){
						//예약마감한 날짜에 포함 되는지 확인
						return [false, 'data-branch-deadline'];
					} else {
						return [true, ''];
					}
					
				} else {
					return [false, 'data-weekend'];
				}
				
			}
			
			,onSelect : function(dateText, inst){
								
				_this.selectDateTime(dateText);
				
				let selectedDay = inst.selectedDay;
				
				$('[data-handler="selectDay"]').removeClass('active');
				$('[data-date="'+ selectedDay + '"]').parent().addClass('active');
				
			}
			
		});
		
		$('.calendar_cont').prepend($('#ui-datepicker-div'));
		
		let curDate = new Date();
		
		curDate.setDate(curDate.getDate() + 1);
		
		if(curDate.getDay() == 6){
			curDate.setDate(curDate.getDate() + 2);	
		} else if(curDate.getDay() == 0){
			curDate.setDate(curDate.getDate() + 1);
		}
		
		curDate = parsingControl.parseDateToStr(curDate, '-', true);
		
		_this.selectDateTime(curDate);
		$('[data-common-datepicker]').val(curDate);
		
		_this.checkPossibleCalendar();
		
	}
	
	,checkPossibleCalendar : function(){
		let _this = this;
		
		let checkPossible = setInterval(function(){
			//달력을 강제로 열기
			if($('#ui-datepicker-div').find('> div').length == 0){
				$('[data-common-datepicker]').trigger('focus');
			} else {
				//선택 가능한 날짜가 없을 때
				if($('#ui-datepicker-div').find('td').not('.ui-state-disabled').length == 0){
					$('.ui-datepicker-next').trigger('click');
				} else {
					let target = $('#ui-datepicker-div').find('td').not('.ui-state-disabled')[0];
					let targetYearVal = $(target).attr('data-year');
					let targetMonthVal = (Number($(target).attr('data-month')) + 1) < 10 ? '0' + (Number($(target).attr('data-month')) + 1) : (Number($(target).attr('data-month')) + 1);
					let targetDateVal =  $(target).find('> a').attr('data-date') < 10 ? '0' + $(target).find('> a').attr('data-date') : $(target).find('> a').attr('data-date');
										
					let targetDate = targetYearVal + '-' + targetMonthVal + '-' + targetDateVal;

					$(target).addClass('active');   
					_this.selectDateTime(targetDate);
					$('[data-common-datepicker]').val(targetDate);
					clearInterval(checkPossible);		
				}
			}
		}, 500);
		
		
	}
	
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
			
			if( targetAMEndTimeValue <= timeAMEndTimeValue ){
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
						if(TimeControl.compareTime(timeAMValue, _this.branchSpecficDateTimeSettingList[j2].gbts_start_time_str) >= 0 && TimeControl.compareTime(timeAMValue, _this.branchSpecficDateTimeSettingList[j2].gbts_end_time_str) < 1){
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
			
			if( targetPMEndTimeValue <= timePMEndTimeValue ){
				let timePMHourValue = targetPMStartTimeValue.getHours() < 10 ? '0' + targetPMStartTimeValue.getHours() : targetPMStartTimeValue.getHours(); 
				let timePMMinValue = targetPMStartTimeValue.getMinutes() < 10 ? '0' + targetPMStartTimeValue.getMinutes() : targetPMStartTimeValue.getMinutes();
				let timePMValue = timePMHourValue + ':' + timePMMinValue;
				
				let _z = _this.defaultConsultNum;
				
				//특정시간대 창구개수에 해당하는지 체크
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
	
	,selectDateTime : function(targetDate){
		
		$('.check_again').hide();
		
		let _this = this;

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
		
		/*switch(dayOfWeek){
			case 1 :
				if(TimeControl.compareTime(_this.mondayOpenTime, targetOperationOpenTime) == 1 ) targetOperationOpenTime = _this.mondayOpenTime;
				if(TimeControl.compareTime(targetOperationCloseTime, _this.mondayCloseTime) == 1 ) targetOperationCloseTime = _this.mondayCloseTime;
			 	break;
			case 2 :
				if(TimeControl.compareTime(_this.tuesdayOpenTime, targetOperationOpenTime) == 1 ) targetOperationOpenTime = _this.tuesdayOpenTime;
				if(TimeControl.compareTime(targetOperationCloseTime, _this.tuesdayCloseTime) == 1 ) targetOperationCloseTime = _this.tuesdayCloseTime;
			 	break;
			case 3 :
				if(TimeControl.compareTime(_this.wedndayOpenTime, targetOperationOpenTime) == 1 ) targetOperationOpenTime = _this.wedndayOpenTime;
				if(TimeControl.compareTime(targetOperationCloseTime, _this.wedndayCloseTime) == 1 ) targetOperationCloseTime = _this.wedndayCloseTime;
			 	break;
			case 4 :
				if(TimeControl.compareTime(_this.thursdayOpenTime, targetOperationOpenTime) == 1 ) targetOperationOpenTime = _this.thursdayOpenTime;
				if(TimeControl.compareTime(targetOperationCloseTime, _this.thursdayCloseTime) == 1 ) targetOperationCloseTime = _this.thursdayCloseTime;
			 	break;
			case 5 :
				if(TimeControl.compareTime(_this.fridayOpenTime, targetOperationOpenTime) == 1 ) targetOperationOpenTime = _this.fridayOpenTime;
				if(TimeControl.compareTime(targetOperationCloseTime, _this.fridayCloseTime) == 1 ) targetOperationCloseTime = _this.fridayCloseTime;
			 	break;
			default : 
				break;
		}*/
		
		let changeTimeList = _this.getTimeList(targetOperationOpenTime, targetOperationLunchOpenTime, targetOperationLunchCloseTime, targetOperationCloseTime, targetDate);
		
		_this.changeDateTime(changeTimeList, reserveList);
		
	}
	
	,changeDateTime : function(changeTimeList, timeList){
		let _this = this;

		_this.reserveList = [];
				
		if(timeList.length > 0){
			let i = 0;
			
				while(i < timeList.length){
					
					_this.reserveList.push(timeList[i].rl_reserve_time_str);
					
					i++;
				}
			
		}
		
		this.settingTimeList(changeTimeList);
		
	}
	
	,settingTimeList(timeList){
		let _this = this;
		
		$('#selectTime').val('');
		$('.time_select').empty('');
		
		let initTimeList =  Array.from(new Set(timeList));
		
		let compareTimeList = [];
		
		if(timeList.length > 0){

			compareTimeList = timeList.slice();

			let i = 0;
			
			//예약목록에 있는지 확인
			while(i < timeList.length){
				
				if(jQuery.inArray(timeList[i], _this.reserveList) > -1){
					
					compareTimeList = _this.removeItem(compareTimeList, timeList[i]);
					_this.reserveList= _this.removeItem(_this.reserveList, timeList[i]);

				}
				i++;	
			}
			
			let j = 0;
			
			
			
			while(j < initTimeList.length){
				if(jQuery.inArray(initTimeList[j], compareTimeList) > -1){
					$('.time_select').append('<a href="javascript:void(0)" class="btn_able" data-target-time="'+ initTimeList[j] +'" onclick="reserveStep.selectReserveTime($(this))">' + initTimeList[j] + ' 예약가능</a>');
				} else {
					//예약이 다 되었을 때					
					$('.time_select').append('<a href="javascript:void(0)" class="btn_null">' + initTimeList[j] + ' 예약종료</a>');
				}
				
				j++;
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
	
	,selectReserveTime(target){
		$(target).siblings('a').removeClass('active');
		$(target).addClass('active');
		let targetTime = $(target).attr('data-target-time');
		
		$('#selectTime').val(targetTime);
		
		let targetDate = $('[name="dateSetting"]').val();

		$('.check_again').find('> h1').eq(0).text( targetDate + ' ' + targetTime);		
		$('.check_again').show();
		
	}
	
	,checkReserve(){
		if($('#dateSetting').val() == '' || $('#dateSetting').val() == null || $('#dateSetting').val() == undefined){
			popupManager.add('#checkStep7Popup1');
		}
		
		if($('#selectTime').val() == '' || $('#selectTime').val() == null || $('#selectTime').val() == undefined){
			popupManager.add('#checkStep7Popup2');
			return false;
		}
		
		popupManager.add('#step7Popup');
	}
	
	,requestReserve(){
		let _this = this;
		
		if( $('#rlNameVal').val() == "" || $('#rlHp').val() == ""){
			alert('본인인증 정보가 없습니다. 본인 인증 후 다시 시도해주세요.');
			window.location.href="/reserve/step?step_idx=4";
		}
		
		let params = JSON.stringify({rlName : $('#rlName').val(), rlHp : $('#rlHp').val(), brIdx : _this.brIdx, rlBusinessType : $('#rlBusinessType').val(), selectDate : $('#dateSetting').val(), targetTime : $('#selectTime').val(), brName : $('#brName').val(), brAddress : $('#brAddress').val()});
			$.ajax({
				url : "/reserve/requestReserve"
				,contentType : 'application/json; charset=UTF-8'
				,dataType : "json"
				,data : params
				,type : "post"
				,async : false
				,timeoutDelay : 90000
				,success : function(result){
					popupManager.removePopLatest(true);
					if(result.result == 1){
						popupManager.removePopLatest(true);
						popupManager.add('#completeReservePopup');
					} else if(result.result == 2){
						popupManager.removePopLatest(true);
						popupManager.add('#duplicateReservePopup');
					} else if(result.result == 3){
						popupManager.removePopLatest(true);
						popupManager.add('#endReservePopup');
					} else {
						popupManager.removePopLatest(true);
						popupManager.add('#failReservePopup');
					}
	
				}
				,error:function(request,status,error){
	    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    		}
		});
	}
	
	,endReserve : function(){
		popupManager.removePopLatest(true);
		window.location.reload();
	}
	
	,completeReserve : function(){
		popupManager.removePopLatest(true);
		window.location.href='/reserve/main';
	}
	
	,removeEvent : function(){
		
	}
	
	,destroy : function(){
		
	}
}

$(document).ready(function(){
	reserveStep.init();
})