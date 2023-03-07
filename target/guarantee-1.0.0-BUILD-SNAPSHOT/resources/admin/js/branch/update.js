commonUpdate.checkUpdateForm = function(){
	let check = true;
		
		if($('[name="brDefaultCounterNum"]').val() == ''|| $('[name="brDefaultCounterNum"]').val() == "undefined"){
			alert('기본창구 개수를 설정해주세요.');
			$('[name="brDefaultCounterNum"]').focus();
			check = false;
		}
		
		if($('[name="brDefaultCounterNum"]').val() < 1 || $('[name="brDefaultCounterNum"]').val() > 5){
			alert('기본창구 개수는 1이상 5이하로 설정해주세요.');
			$('[name="brDefaultCounterNum"]').focus();
			check = false;
		}
		
		return check;	
}

let branchUpdate = {
	
	openTimeSelect : null
	,closeTimeSelect : null
	,openLunchTimeSelect : null
	,closeLunchTimeSelect : null
	,timeSettingList : []
	,dateSettingList : []
	,timeSettingListCon : null
	,dateSettingListCon : null
	,settingListTemplate : '<li><a href="javascript:void(0)" class="admin_btn_style_1" onclick="branchUpdate.deleteSettingList($(this).parent(), 0)"><span>삭제</span></a></li>'
	,holidayList : makeHolidayArray()
	
	,init : function(){
		let _this = this;
		
		this.openTimeSelect = $('[name="brOpenedTime"]'); 
		this.closeTimeSelect = $('[name="brClosedTime"]');
		this.openLunchTimeSelect = $('[name="brLunchOpenedTime"]');
		this.closeLunchTimeSelect = $('[name="brLunchClosedTime"]');
		
		this.timeSettingListCon = $('.custom_select_list.time');
		this.resetTimeSettingList();
		
		this.dateSettingListCon = $('.custom_select_list.date');
		this.resetDateSettingList();
		
		this.updateStatus = typeof($('[name="brIdx"]').val()) != "undefined" ? true : false;
		if(this.updateStatus){
			this.updateBrDefaultNum = $('[name="brDefaultCounterNum"]').val();
			this.reserve1DateList = reserve1DateList;
			this.reserve2DateList = reserve2DateList;
			this.reserve3DateList = reserve3DateList;
			this.reserve4DateList = reserve4DateList;
			this.reserve5DateList = reserve5DateList;
		}
		
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
			,minDate : 1
			,beforeShowDay: function(date){
				
				let day = date.getDay();
				
				if( day != 0 && day != 6 ){
					let y = date.getFullYear();
					let m = ((date.getMonth()+1) > 9) ? (date.getMonth()+1) : "0" + (date.getMonth()+1);
					let d = (date.getDate() > 9) ? date.getDate() : "0" + date.getDate();
						
					//holiday에 포함이 되는지 확인
					if(_this.holidayList.includes(y+m+d)){
						return [false, 'data-holiday'];
					} else {
						return [true, ''];	
					}
					
				} else {
					return [false, ''];
				}

			}
			
		});
		
		this.registEvent(); 	
	}
	
	,registEvent : function(){
		let _this = this;
		
		//업데이트 시 기본창구개수 변경 시 적용시점 변경 이벤트 등록
		if(this.updateStatus){
				$('[name="brDefaultCounterNum"]').on('keyup', function(){
					if(parseInt(_this.updateBrDefaultNum) != parseInt($(this).val()) && $(this).val() != ""){
						_this.possibleDefaultNum($(this).val());
					} else {
						$('#defaultCounterInfo').hide();
						$('[name="prevBrDefaultCounterNumPossibleDate"]').val('');
					}
				});
		}
		
		//운영시작시간 체크 이벤트
		$(this.openTimeSelect).on('change', function(){
			if( TimeControl.compareTime($(this).val(), $(_this.closeTimeSelect).val()) == 1 ){
				alert('운영시작시간이 운영마감시간보다 늦게 설정할 수 없습니다. 다시 시도해주세요.');
				$(this).val($(_this.closeTimeSelect).val());
			};
		});
		
		//운영마감시간 체크 이벤트
		$(this.closeTimeSelect).on('change', function(){
			if( TimeControl.compareTime($(this).val(), $(_this.openTimeSelect).val()) == -1 ){
				alert('운영마감시간이 운영시작시간보다 일찍 설정할 수 없습니다. 다시 시도해주세요.');
				$(this).val($(_this.openTimeSelect).val());
			};
		});
		
		//점심시작시간 체크 이벤트
		$(this.openLunchTimeSelect).on('change', function(){
			if(TimeControl.compareTime($(this).val(), $(_this.openTimeSelect).val()) == -1){
				alert('점심시작시간이 운영시작시간보다 일찍 설정할 수 없습니다. 다시 시도해주세요.');
				$(this).val($(_this.openTimeSelect).val());
				return false;
			}
			
			if(TimeControl.compareTime($(this).val(), $(_this.closeTimeSelect).val()) == 1){
				alert('점심시작시간이 운영마감시간보다 늦게 설정할 수 없습니다. 다시 시도해주세요.');
				$(this).val($(_this.closeTimeSelect).val());
				return false;
			}
			
			if( TimeControl.compareTime($(this).val(), $(_this.closeLunchTimeSelect).val()) == 1 ){
				alert('점심시작시간이 점심마감시간보다 늦게 설정할 수 없습니다. 다시 시도해주세요.');
				$(this).val($(_this.closeLunchTimeSelect).val());
			};
		});
		
		//점심마감시간 체크 이벤트
		$(this.closeLunchTimeSelect).on('change', function(){
			if(TimeControl.compareTime($(this).val(), $(_this.closeTimeSelect).val()) == 1){
				alert('점심마감시간이 운영마감시간보다 늦게 설정할 수 없습니다. 다시 시도해주세요.');
				$(this).val($(_this.closeTimeSelect).val());
				return false;
			}
			
			if(TimeControl.compareTime($(this).val(), $(_this.openTimeSelect).val()) == -1){
				alert('점심마감시간이 운영시작시간보다 일찍 설정할 수 없습니다. 다시 시도해주세요.');
				$(this).val($(_this.openTimeSelect).val());
				return false;
			}
			
			if( TimeControl.compareTime($(this).val(), $(_this.openLunchTimeSelect).val()) == -1 ){
				alert('점심마감시간이 점심시작시간보다 일찍 설정할 수 없습니다. 다시 시도해주세요.');
				$(this).val($(_this.openLunchTimeSelect).val());
			};
		});
		
		
		
		/*//기본창구개수 체크 이벤트
		$('[name="br_default_counter_num"]').on('keyup', function(key){
			if($(this).val() > 5){
				alert('기본창구개수는 5개를 넘을 수 없습니다.');
				$(this).val('');
				$(this).focus();
			} else if($(this).val() < 1){
				alert('기본창구개수는 최소 1개 이상을 설정해야 합니다.');
				$(this).val('');
				$(this).focus();
			}
		});*/
		
		//특정시간대별 창구개수 체크 이벤트
		$('[name="timezoneCounterNum"]').on('keyup', function(key){
			if(key.keyCode == 13) {
				_this.addTimeSettingList();
            } else {
				if($(this).val() < 1){
					alert('창구개수는 최소 1개 이상을 설정해야 합니다.');
					$(this).val('');
					$(this).focus();
				} else {
					_this.compareCounterNum($(this));	
				}
            }
		});
		
		//특정일별개수 체크 이벤트
		$('[name="dateCounterNum"]').on('keyup', function(key){
			if(key.keyCode == 13) {
				_this.addDateSettingList();
            } else {
				if($(this).val() < 1){
					alert('창구개수는 최소 1개 이상을 설정해야 합니다.');
					$(this).val('');
					$(this).focus();
				} else {
					_this.compareCounterNum($(this));	
				}
            }
		});
		
		//특정시간대별 창구개수 추가 버튼
		$('#timezonePlusBtn').on('click', function(){
			
			_this.addTimeSettingList();
		});
		
		//특정일별 창구개수 추가 버튼
		$('#datePlusBtn').on('click', function(){
			_this.addDateSettingList();
		});
		
	}
	
	,compareCounterNum : function(target){
		if($('[name="brDefaultCounterNum"]').val() == ''|| $('[name="brDefaultCounterNum"]').val() == "undefined"){
			alert('기본창구 개수를 먼저 설정해주세요.');
			$(target).val('');
			$('[name="brDefaultCounterNum"]').focus();
		} else {
			if(parseInt($(target).val()) > parseInt($('[name="brDefaultCounterNum"]').val())){
				alert('창구설정을 기본창구개수보다 많이 설정할 수 없습니다.');
				$(target).val('');
				$(target).focus();
			}	
		}
		
	}
	
	
	//특정시간대별 세팅 추가
	,addTimeSettingList : function(){
		let _this = this;
		
		if($('[name="settingOpenTime"]').val() == ""){
			alert('시간대별 창구설정 시작시간을 설정해주세요.'); return false;	
		}
		
		if($('[name="settingClosedTime"]').val() == ""){
			alert('시간대별 창구설정 마감시간을 설정해주세요.'); return false;
		}
		if($('[name="timezoneCounterNum"]').val() == ""){
			alert('시간대별 창구설정 창구개수를 설정해주세요.'); return false;
		}
		
		let startTime = $('[name="settingOpenTime"]').val();
		let endTime = $('[name="settingClosedTime"]').val();		

		let checkTime = TimeControl.compareTime(startTime, endTime);

		if( checkTime > -1 ){
			alert('시간대별 시작시간은 시간대별 마감시간과 같거나 크게 설정할 수 없습니다.\n변경 후 다시 시도해주세요.'); return false;
		}
		
		if(TimeControl.compareTime(startTime, $(_this.closeTimeSelect).val()) == 1){
			alert('시작시간은 운영마감시간보다 늦게 설정할 수 없습니다.\n변경 후 다시 시도해주세요.'); return false;
		}
			
		if(TimeControl.compareTime(startTime, $(_this.openTimeSelect).val()) == -1){
			alert('시작시간은 운영시작시간보다 일찍 설정할 수 없습니다.\n변경 후 다시 시도해주세요.'); return false;
		}
		
		if(TimeControl.compareTime(endTime, $(_this.closeTimeSelect).val()) == 1){
			alert('마감시간은 운영마감시간보다 늦게 설정할 수 없습니다.\n변경 후 다시 시도해주세요.'); return false;
		}
			
		if( (TimeControl.compareTime(startTime, $(_this.openLunchTimeSelect).val()) == 0) || ((TimeControl.compareTime(startTime, $(_this.openLunchTimeSelect).val()) == 1) && (TimeControl.compareTime(startTime, $(_this.closeLunchTimeSelect).val()) == -1)) ||
		(TimeControl.compareTime(endTime, $(_this.closeLunchTimeSelect).val()) == 0) ||  ((TimeControl.compareTime(endTime, $(_this.openLunchTimeSelect).val()) == 1) && (TimeControl.compareTime(endTime, $(_this.closeLunchTimeSelect).val()) == -1)) ||
		( (TimeControl.compareTime(startTime, $(_this.openLunchTimeSelect).val()) == -1) && (TimeControl.compareTime(endTime, $(_this.closeLunchTimeSelect).val()) == 1) )
		){
			alert('설정하려는 시간은 점심시간과 일부 겹쳐 설정할 수 없습니다.\n변경 후 다시 시도해주세요.'); return false;
		};

		let calcCheck = _this.checkTimeSettingList(startTime, endTime);
		
		if(calcCheck){
			
			let targetDateVal = new Date();
			if(_this.updateStatus){
				targetDateVal.setDate(targetDateVal.getDate() + 1);	
			}
			let targetDate = parsingControl.parseDateToStr(targetDateVal, '-', true);
			
			let finalText = $('[name="settingOpenTime"]').val() + " - " + $('[name="settingClosedTime"]').val() + " 창구 " + $('[name="timezoneCounterNum"]').val() + "개 ";
			let finalValue = "{'startTime' : '"+ $('[name="settingOpenTime"]').val() +"', 'endTime' : '" + $('[name="settingClosedTime"]').val() + "', 'counterNum' : '" + $('[name="timezoneCounterNum"]').val() + "', 'appliedAt' : '" + targetDate + "'}"
			
			$(_this.timeSettingListCon).append(_this.settingListTemplate);
			$(_this.timeSettingListCon).find('> li').last().prepend(finalText);
			$(_this.timeSettingListCon).find('> li').last().attr('data-time-setting', finalValue);
			$(_this.timeSettingListCon).find('> li').last().find('> a').attr('onclick', 'branchUpdate.deleteSettingList($(this).parent(), 1)');
			$(_this.timeSettingListCon).find('> li').last().append('<div class="ml3 vm show update_info_text"><span>' + targetDate + ' 적용</span></div>')
			
			_this.resetTimeSettingList();
		} else {
			alert('이전에 설정한 시간과 겹칩니다.\n제거 후 다시 시도해주세요.');
		}
		
	}
	
	//특정일별 세팅 추가
	,addDateSettingList : function(){
		let _this = this;
		
		if($('[name="dateSetting"]').val() == ""){
			alert('추가할 특정일을 설정해주세요'); return false;	
		}

		if($('[name="dateCounterNum"]').val() == ""){
			alert('특정일별 창구설정 창구개수를 설정해주세요.'); return false;
		}		

		let dateValue = $('[name="dateSetting"]').val();

		let calcCheck = _this.checkDateSettingList(dateValue);
		
		if(calcCheck){
			let finalText = dateValue + " 창구 " + $('[name="dateCounterNum"]').val() + "개 ";
			let finalValue = "{'selectDate' : '"+ dateValue + "', 'counterNum' : '" + $('[name="dateCounterNum"]').val() + "'}"
			
			$(_this.dateSettingListCon).append(_this.settingListTemplate);
			$(_this.dateSettingListCon).find('> li').last().prepend(finalText);
			$(_this.dateSettingListCon).find('> li').last().attr('data-date-setting', finalValue);
			
			_this.resetDateSettingList();
		} else {
			alert('이전에 설정한 날짜와 겹칩니다. 제거 후 다시 시도해주세요.');
		}
	}
	
	/*
	params : target -> li, status -> 0 - 특정일, 1 - 특정시간대, 2 - 특정일 특정시간대
	세팅 List li 삭제 함수
	*/
	,deleteSettingList : function(target, status){
		let _this = this;
		
		$(target).remove();
		
		switch(status){
			case 1 :
				_this.resetTimeSettingList();
				break;
			/*case 2 :
				_this.resetSpecficDateTimeSettingList();
				break;*/
			default :
				_this.resetDateSettingList();
				break;
		}
		
	}
	
	,resetTimeSettingList : function(){
		let _this = this;
		
		_this.timeSettingList = [];
		
		$(_this.timeSettingListCon).find('> li').each(function(){
				let timeSettingValue = $(this).attr('data-time-setting');
				_this.timeSettingList.push(timeSettingValue);
		});
		
		$('[name="branchTimeSettingDtoList"]').val("["+ _this.timeSettingList + "]");
	}
	
	,resetDateSettingList : function(){
		let _this = this;
		
		_this.dateSettingList = [];
		
		$(_this.dateSettingListCon).find('> li').each(function(){
			let dateSettingValue = $(this).attr('data-date-setting');
			_this.dateSettingList.push(dateSettingValue);
		})
		$('[name="branchDateSettingDtoList"]').val("["+ _this.dateSettingList + "]");
	}
	
	//특정 시간대별 세팅이 겹치지 않게 하기 위한 계산
	,checkTimeSettingList : function(startTime, endTime){
		let _this = this;

		let result = true;
		
		//겹치는지 비교 (시작시간이 같은 경우, 시작시간이 비교 시작시간보다 큰데 비교 마감시간보다 작은 경우, 마감시간이 같은 경우, 마감시간이 비교 시작시간보다 큰데 비교 마감시간보다 작은 경우 )
		let compareTarget;
		for(let i=0; i < $(_this.timeSettingList).length; i++){
			compareTarget = parsingControl.parseStrToObj( _this.timeSettingList[i] );
			
			let compareStartTime = compareTarget["'startTime'"];
			let compareEndTime = compareTarget["'endTime'"];
			
			
			if( (TimeControl.compareTime(startTime, compareStartTime) == 0) || ( (TimeControl.compareTime(startTime, compareStartTime) == 1) && (TimeControl.compareTime(startTime, compareEndTime) == -1) ) ||
				(TimeControl.compareTime(endTime, compareEndTime) == 0) || ( (TimeControl.compareTime(endTime, compareStartTime) == 1) && (TimeControl.compareTime(endTime, compareEndTime) == -1) ) ||
				( (TimeControl.compareTime(startTime, compareStartTime) == -1) && (TimeControl.compareTime(endTime, compareEndTime) == 1) )
			) {
				result = false;
				break;
			}

		}
		
		return result;
		
	}
	
	//특정 일별 세팅이 겹치지 않게 하기 위해 체크
	,checkDateSettingList : function(targetDate){
		let _this = this;

		let result = true;
		
		let compareTarget;
		for(let i=0; i < $(_this.dateSettingList).length; i++){
			compareTarget = parsingControl.parseStrToObj( _this.dateSettingList[i] );
			//날이 겹칠 경우
			if(targetDate == compareTarget["'selectDate'"]){				
				result = false;
				break;
			}
		}
		
		return result;
		
	}
	
	//업데이트 시, 기본창구개수를 변경하였을 때 적용날짜를 계산해주는 이벤트
	,possibleDefaultNum : function( defaultNum ){
		let targetDate = "";
		switch(defaultNum){
			case "4" :
				targetDate = reserve4DateList[0];
				break;
			case "3" :
				targetDate = reserve3DateList[0];
				break;
			case "2" :
				targetDate = reserve2DateList[0];
				break;
			case "1" :
				targetDate = reserve1DateList[0];
				break;
			default :
				targetDate = reserve5DateList[0];
				break;
		}
		
		if( typeof(targetDate) == "undefined" ){
			let tomorrowDateVal = new Date();
			tomorrowDateVal.setDate(tomorrowDateVal.getDate() + 1);
			let tomorrowDateYear = tomorrowDateVal.getFullYear();
			let tomorrowDateMonth = (tomorrowDateVal.getMonth() + 1) < 10 ? '0' + (tomorrowDateVal.getMonth() + 1) : (tomorrowDateVal.getMonth() + 1);
			let tomorrowDateDate = tomorrowDateVal.getDate() < 10 ? '0' + tomorrowDateVal.getDate() : tomorrowDateVal.getDate(); 
			targetDate = tomorrowDateYear + '-' + tomorrowDateMonth + '-' + tomorrowDateDate;   
		}
		
		$('[name="prevBrDefaultCounterNumPossibleDate"]').val(targetDate);
		let targetTxt = "적용시점 : " + targetDate;
		$('#defaultCounterInfo').find('> span').text(targetTxt);
		$('#defaultCounterInfo').show();
				
	}
	
	,removeEvent: function(){
		
	}
	,destroy: function(){
		
	}
}

$(document).ready(function(){
	branchUpdate.init();
})
