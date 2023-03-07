reserveManageList = {
	
	holidayList : makeHolidayArray()
	,branchHolidayList : null
	,branchFullReserveList : null
	,reserveList : null
	,todayStatus : 1
	
	,init : function(){
		let _this = this;
		
		this.branchHolidayList = branchDateList;
		this.branchFullReserveList = fullReserveList;
		
		let maxDate = new Date();
		maxDate.setMonth(maxDate.getMonth() + 2);
		
		$('[data-common-datepicker]').datepicker({
			dateFormat : "yy-mm-dd"
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
			,minDate : 0
			,maxDate : maxDate 
			,beforeShowDay: function(date){
				
				let day = date.getDay();
				
				if( day != 0 && day != 6 ){
					let y = date.getFullYear();
					let m = ((date.getMonth()+1) > 9) ? (date.getMonth()+1) : "0" + (date.getMonth()+1);
					let d = (date.getDate() > 9) ? date.getDate() : "0" + date.getDate();
					
					let todayDate = new Date();
					todayDate.setHours(0);
					todayDate.setMinutes(0);
					todayDate.setSeconds(0);
					todayDate.setMilliseconds(0);
					
					if(_this.holidayList.includes(y+m+d)){
						//holiday에 포함이 되는지 확인
						return [false, 'data-holiday'];
					} else if(date < todayDate){
						//오늘 이전에 해당하는 날짜인지 확인
						return [false, '']
					} else if(_this.branchHolidayList.includes(y+'-'+m+'-'+d)) {
						//휴무 설정한 날짜에 포함 되는지 확인
						return [false, 'data-branch-holiday'];	
					} else if(_this.branchFullReserveList.includes(y+'-'+m+'-'+d)) {
						//예약마감한 날짜에 포함 되는지 확인
						return [true, 'data-branch-deadline'];	
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
			
		})
		
		$('.calendar_cont').append($('#ui-datepicker-div'));
		
		//달력을 강제로 열게 하는 trigger
		let triggerInterval = setInterval(function(){
			if($('#ui-datepicker-div').find('> div').length == 0){
				$('[data-common-datepicker]').trigger('focus');
			} else {
				//처음 select-date에 active 추가
				$('.ui-datepicker-current-day').addClass('active');
				clearInterval(triggerInterval);
			}
		}, 500);
		
		this.brIdx = brIdx;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.lunchOpenTime = lunchOpenTime;
		this.lunchCloseTime = lunchCloseTime;
		this.defaultConsultTime = defaultConsultTime;
		
		this.branchTimeSettingList = branchTimeSettingList;
		this.branchSpecficDateTimeSettingList = branchSpecficDateTimeSettingList;
		
		this.defaultConsultNum = defaultConsultNum;
		
		let nowDate = parsingControl.parseDateToStr(new Date(), '-', true);
		
		this.defaultTimeList = _this.getTimeList(this.openTime, this.lunchOpenTime, this.lunchCloseTime, this.closeTime, nowDate);
		
		this.mondayOpenTime = mondayOpenTime;
		this.mondayCloseTime = mondayCloseTime;
		this.tuesdayOpenTime = tuesdayOpenTime;
		this.tuesdayCloseTime = tuesdayCloseTime;
		this.wedndayOpenTime = wedndayOpenTime;
		this.wedndayCloseTime = wedndayCloseTime;
		this.thursdayOpenTime = thursdayOpenTime;
		this.thursdayCloseTime = thursdayCloseTime;
		this.fridayOpenTime = fridayOpenTime;
		this.fridayCloseTime = fridayCloseTime;
		
		
		_this.checkPossibleCalendar();
			
	}
	
	,registEvent : function(){
		setInterval(intervalClearTime, 300000)
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
	
	,intervalClearTime : function(){
		let _this = this;
		
		let targetDate = $('#dateSetting').val();
		
		_this.selectDateTime(targetDate);
		
	}
	
	,getTimeList : function(startTime, lunchStartTime, lunchEndTime, endTime, targetDate){
		let _this = this;
		let result = [];
		
		let standardDay = targetDate;
		
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

	,selectDateTime : function(targetDate){
		
		let _this = this;

		let targetDateCompareVal = new Date(targetDate);  
		
		let curDateCompareVal = new Date();
		let curDateCompare = parsingControl.parseDateToStr(new Date(), '-', true);
		
		curDateCompareVal = new Date(curDateCompare);
		
		if(targetDateCompareVal > curDateCompareVal){
			_this.todayStatus = 1;
		} else if(targetDateCompareVal < curDateCompareVal){
			_this.todayStatus = -1;
		} else {
			_this.todayStatus = 0;
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
		
		_this.reserveList = [];
		_this.reserveNameList = [];
		
		if(timeList.length > 0){
			let i = 0;
			while(i < timeList.length){
					_this.reserveList.push(timeList[i].rl_reserve_time_str);
					let reserveParam = {"rlIdx" : timeList[i].rl_idx, "rlName" : timeList[i].rl_name, "rlHp" : timeList[i].rl_hp, "rlStatus" : timeList[i].rl_status}
					
					_this.reserveNameList.push(reserveParam);							
					i++;
			}
			
		}
		
		this.settingTimeList(changeTimeList);
		
	}
	
	,settingTimeList : function(timeList){
		
		let _this = this;
		
		$('.time_list tbody').empty();
		
		/*let initTimeList =  Array.from(new Set(timeList));
		
		let compareTimeList = [];
		if(timeList.length > 0){
			compareTimeList = timeList.slice();
		}*/
		
		if(timeList.length > 0){
			let i = 0;
			
			//예약목록에 있는지 확인
			let prevPossibleTimeVal = "00:00";
			let prevTimeVal = "00:00";
			let prevRlIdx = -1;
			let rangeIdx = 0;
			let consultNum = 0;
			while(i < timeList.length){
				
				if(prevTimeVal != timeList[i]){
					consultNum = 1;
					prevTimeVal = timeList[i];
				} else {
					consultNum++;
				}
				
				if(jQuery.inArray(timeList[i], _this.reserveList, rangeIdx) > -1){
					
					let targetIdx = jQuery.inArray(timeList[i], _this.reserveList, rangeIdx); 
					
					rangeIdx = targetIdx + 1;
					
					let rlIdx = _this.reserveNameList[targetIdx].rlIdx;
					
					if(prevRlIdx != rlIdx){						
						let rlName = _this.reserveNameList[targetIdx].rlName;
						let rlHp = _this.reserveNameList[targetIdx].rlHp;
						let rlStatus = _this.reserveNameList[targetIdx].rlStatus;
						
						let rlStatusTxt = '상담대기';
						
						/*switch(_this.todayStatus){
							case -1 :
								rlStatusTxt = '상담종료';
								$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + consultNum + '번' + '</td><td>' + timeList[i] + '</td><td>' + rlStatusTxt + '</td></tr>');
								break;
							case 0 :
								switch(rlStatus){
									case 2 :
										rlStatusTxt = '상담중';
										$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + consultNum + '번' + '</td><td>' + timeList[i] + '</td><td><a href="javascript:void(0)" class="mr5 admin_btn_style_1" onclick="reserveManageList.updateReserve(' + rlIdx + ',1)"><span>대기</span></a><a href="javascript:void(0)" class="admin_btn_style_1 grey" onclick="reserveManageList.updateReserve(' + rlIdx + ',3)"><span>종료</span></a></td></tr>');
										break;
									case 3 :
										rlStatusTxt = '상담종료';
										$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + consultNum + '번' + '</td><td>' + timeList[i] + '</td><td><a href="javascript:void(0)" class="mr5 admin_btn_style_1" onclick="reserveManageList.updateReserve(' + rlIdx + ',1)"><span>대기</span></a><a href="javascript:void(0)" class="admin_btn_style_1 skyblue" onclick="reserveManageList.updateReserve(' + rlIdx + ',2)"><span>시작</span></a></td></tr>');
										break;
									default :
										$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + consultNum + '번' + '</td><td>' + timeList[i] + '</td><td><a href="javascript:void(0)" class="mr5 admin_btn_style_1 skyblue" onclick="reserveManageList.updateReserve(' + rlIdx + ',2)"><span>시작</span></a><a href="javascript:void(0)" class="admin_btn_style_1 grey" onclick="reserveManageList.updateReserve(' + rlIdx + ',3)"><span>종료</span></a></td></tr>');
										break;
								}
								break;
							case 1 :
								rlStatusTxt = '상담대기';
								$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + consultNum + '번' + '</td><td>' + timeList[i] + '</td><td>' + rlStatusTxt + '</td></tr>');
								break;
							default :
								break;
						}*/
						
						switch(_this.todayStatus){
							case -1 :
								rlStatusTxt = '상담종료';
								$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + timeList[i] + '</td><td>' + rlStatusTxt + '</td></tr>');
								break;
							case 0 :
								switch(rlStatus){
									case 2 :
										rlStatusTxt = '상담중';
										$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + timeList[i] + '</td><td><a href="javascript:void(0)" class="mr5 admin_btn_style_1" onclick="reserveManageList.updateReserve(' + rlIdx + ',1)"><span>대기</span></a><a href="javascript:void(0)" class="admin_btn_style_1 grey" onclick="reserveManageList.updateReserve(' + rlIdx + ',3)"><span>종료</span></a></td></tr>');
										break;
									case 3 :
										rlStatusTxt = '상담종료';
										$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + timeList[i] + '</td><td><a href="javascript:void(0)" class="mr5 admin_btn_style_1" onclick="reserveManageList.updateReserve(' + rlIdx + ',1)"><span>대기</span></a><a href="javascript:void(0)" class="admin_btn_style_1 skyblue" onclick="reserveManageList.updateReserve(' + rlIdx + ',2)"><span>시작</span></a></td></tr>');
										break;
									default :
										$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + timeList[i] + '</td><td><a href="javascript:void(0)" class="mr5 admin_btn_style_1 skyblue" onclick="reserveManageList.updateReserve(' + rlIdx + ',2)"><span>시작</span></a><a href="javascript:void(0)" class="admin_btn_style_1 grey" onclick="reserveManageList.updateReserve(' + rlIdx + ',3)"><span>종료</span></a></td></tr>');
										break;
								}
								break;
							case 1 :
								rlStatusTxt = '상담대기';
								$('.time_list tbody').append('<tr class="not_reserve"><td>' + rlStatusTxt + '</td><td>' + rlName + '</td><td>' + rlHp + '</td><td>' + timeList[i] + '</td><td>' + rlStatusTxt + '</td></tr>');
								break;
							default :
								break;
						}
						
						prevRlIdx = rlIdx;
					}
					
				} else {
					if(prevPossibleTimeVal != timeList[i]){
						
						if(_this.todayStatus > -1){
							$('.time_list tbody').append('<tr><td>예약가능</td><td>없음</td><td></td><td>' + timeList[i] + '</td><td><a href="javascript:void(0)" class="admin_btn_style_1" onclick="reserveManageList.insertReserve(\'' + timeList[i] + '\')"><span>예약등록</span></a></td></tr>');
						} else {
							$('.time_list tbody').append('<tr><td>상담종료</td><td>없음</td><td></td><td>' + timeList[i] + '</td><td>상담종료</td></tr>');
						}
							
						prevPossibleTimeVal = timeList[i];
							
					}

				}
				i++;	
			}
			
		}
		
	}
	
	,insertReserve(timeValue){
		_this = this;
		if(confirm('예약을 추가하시겠습니까?')){
			window.location.href = '/reserve/adm/reserveManage/insert?br_idx=' + _this.brIdx + '&target_date=' + $('#dateSetting').val() + '&target_time=' + timeValue;
		}
	}
	
	//status 2 : 시작, 3 : 종료
	,updateReserve(rlIdx, status){
		let _this = this;
		if(confirm('상태를 변경하시겠습니까?')){
			
			let params = JSON.stringify({"rlIdx" : rlIdx, "rlStatus" : status});
			$.ajax({
				url : "/reserve/adm/reserveManageList/updateReserve"
				,contentType : 'application/json; charset=UTF-8'
				,dataType : "json"
				,data : params
				,type : "post"
				,timeoutDelay : 90000
				,success : function(result){
					console.log(result);
					if(result.result){
						//변경 성공시
						alert('변경 완료했습니다.');
						
						_this.selectDateTime($('[data-common-datepicker]').val());
						
					} else {
						alert('삭제에 실패했습니다.\n잠시 후 다시 시도해주세요.');
					}
	
				}
				,error:function(request,status,error){
	    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    		}
			})
		}
	}
	
	,deleteReserve(rlIdx){ 
		
		if(confirm('삭제하시겠습니까?')){
			let params = JSON.stringify({"rlIdx" : rlIdx});
		
			$.ajax({
				url : "/reserve/adm/reserveManageList/deleteReserve"
				,contentType : 'application/json; charset=UTF-8'
				,dataType : "json"
				,data : params
				,type : "post"
				,timeoutDelay : 90000
				,success : function(result){
					console.log(result);
					if(result.result){
						//변경 성공시
						alert('삭제 완료했습니다.');
						
						window.location.reload();
						
					} else {
						alert('삭제에 실패했습니다.\n잠시 후 다시 시도해주세요.');
					}
	
				}
				,error:function(request,status,error){
	    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    		}
			})			
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
	
	,removeEvent : function(){
		
	}
	
	,destroy : function(){
		
	}
}

$(document).ready(function(){
	reserveManageList.init();
})