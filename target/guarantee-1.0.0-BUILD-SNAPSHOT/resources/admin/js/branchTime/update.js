let branchTimeUpdate = {
	
	timeSettingList : []
	,dateSettingList : []
	,specficDateTimeSettingList : []
	,timeSettingListCon : null
	,dateSettingListCon : null
	,specficDateTimeSettingListCon : null
	,settingListTemplate : '<li><a href="javascript:void(0)" class="admin_btn_style_1" onclick="branchTimeUpdate.deleteSettingList($(this).parent(),true)"><span>삭제</span></a></li>'
	,holidayList : makeHolidayArray()
	,reserveList : []
	
	,init : function(){
		let _this = this;
		
		this.resetTimeSettingList();
		
		this.dateSettingListCon = $('.custom_select_list.date');
		this.resetDateSettingList();
		
		this.specficDateTimeSettingListCon = $('.custom_select_list.specfic_date_time');
		this.resetSpecficDateTimeSettingList();
		
		this.reserveList = reserveList;
		
		//휴무일 
		$('#dateSetting[data-common-datepicker]').datepicker({
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
					} else if(_this.reserveList.includes(y+'-'+m+'-'+d)) {
						return [false, 'data-reserve-day'];	
					} else {
						return [true, ''];	
					}
					
				} else {
					return [false, ''];
				}

			}
			
		})
		
		//특정일 특정시간대 창구설정
		$('#specficDateSetting[data-common-datepicker]').datepicker({
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
			
		})
		
		this.registEvent(); 	
	}
	,registEvent : function(){
		let _this = this;
		
		$('[name^="gbtsOpenedTime"]').on('change', function(){
			_this.changeTimeSetting($(this), true);
		});
		
		$('[name^="gbtsClosedTime"]').on('change', function(){
			_this.changeTimeSetting($(this), false);
		});
		
		$('#datePlusBtn').on('click', function(){
			_this.addDateSettingList();
		});
		
		//특정일 특정시간대 개수 체크 이벤트
		$('[name="specficDateCounterNum"]').on('keyup', function(key){
			
			if($(this).val() < 1){
				alert('창구개수는 최소 1개 이상을 설정해야 합니다.');
				$(this).val('');
				$(this).focus();
			} else {
				if(key.keyCode == 13) {
					_this.addSpecficDateTimeSettingList();
            	}
			}
		});
		
		//특정일 특정시간대별 창구개수 추가 버튼
		$('#specficDatePlusBtn').on('click', function(){
			_this.addSpecficDateTimeSettingList();
		});
				
	}
	
	//요일별 시간설정이 바뀌었을 떄 값을 바꿔주는 함수
	,changeTimeSetting : function(target,open){
		let _this = this;
		
		let dataCon = $(target).closest('.custom_select_list.time');
		let changeData = parsingControl.parseStrToObj( dataCon.attr('data-time-setting'));
		let finalVal = "";
		if(open){
			finalVal = "{'startTime' : '" + $(target).val() +  "', 'endTime' : '" + changeData["'endTime'"] + "', 'gbtsStatus' : '" + changeData["'gbtsStatus'"] + "'}";
		} else {
			finalVal = "{'startTime' : '" + changeData["'startTime'"] +  "', 'endTime' : '" + $(target).val() + "', 'gbtsStatus' : '" + changeData["'gbtsStatus'"] + "'}";
		}
		
		dataCon.attr('data-time-setting', finalVal);
		
		_this.resetTimeSettingList();
		
	}
	
	//휴무일 세팅 추가
	,addDateSettingList : function(){
		let _this = this;
		
		if($('[name="dateSetting"]').val() == ""){
			alert('추가할 휴무일을 설정해주세요'); return false;	
		}		

		let dateValue = $('[name="dateSetting"]').val();

		let calcCheck = _this.calcDateSettingList(dateValue);
		
		if(calcCheck){
			let finalText = dateValue;
			let finalValue = "{'selectDate' : '"+ dateValue + "'}"
			
			$(_this.dateSettingListCon).append(_this.settingListTemplate);
			$(_this.dateSettingListCon).find('> li').last().prepend(finalText);
			$(_this.dateSettingListCon).find('> li').last().attr('data-date-setting', finalValue);
			
			_this.resetDateSettingList();
		} else {
			alert('이전에 설정한 날짜와 겹칩니다. 제거 후 다시 시도해주세요.');
		}
	}
	
	
	//특정일 특정시간대 세팅 추가
	,addSpecficDateTimeSettingList : function(){
		let _this = this;
		
		if($('[name="specficDateSetting"]').val() == ""){
			alert('추가할 특정일을 설정해주세요'); return false;	
		}

		if($('[name="specficDateSettingOpenTime"]').val() == ""){
			alert('특정일 특정시간대 창구설정 시작시간을 설정해주세요.'); return false;	
		}
		
		if($('[name="specficDateSettingClosedTime"]').val() == ""){
			alert('특정일 특정시간대 창구설정 마감시간을 설정해주세요.'); return false;
		}

		if($('[name="specficDateCounterNum"]').val() == ""){
			alert('특정일 특정시간대 창구설정 창구개수를 설정해주세요.'); return false;	
		}
		
		if( parseInt($('[name="specficDateCounterNum"]').val()) > parseInt($('[name="brDefaultCounterNum"]').val()) ){
			alert('특정일 특정시간대 창구설정 창구개수는 기본창구개수를 넘을 수 없습니다.'); return false;
		}

		let startTime = $('[name="specficDateSettingOpenTime"]').val();
		let endTime = $('[name="specficDateSettingClosedTime"]').val();

		let dateValue = $('[name="specficDateSetting"]').val();

		let calcCheck = _this.checkSpecficDateTimeSettingList(dateValue, startTime, endTime);
		
		if(calcCheck){
			let finalText = dateValue + " " + startTime + " - " + endTime + " 창구 " + $('[name="specficDateCounterNum"]').val() + "개 ";
			let finalValue = "{'selectDate' : '"+ dateValue + "','startTime' : '" + startTime + "', 'endTime' : '" + endTime + "', 'counterNum' : '" + $('[name="specficDateCounterNum"]').val() + "'}"
			
			$(_this.specficDateTimeSettingListCon).append(_this.settingListTemplate);
			$(_this.specficDateTimeSettingListCon).find('> li').last().prepend(finalText);
			$(_this.specficDateTimeSettingListCon).find('> li').last().attr('data-time-setting', finalValue);
			$(_this.specficDateTimeSettingListCon).find('> li').last().find('> a').attr('onclick', 'branchTimeUpdate.deleteSettingList($(this).parent(), false)');
			
			_this.resetSpecficDateTimeSettingList();
		} else {
			alert('이전 설정값과 겹칩니다. 제거 후 다시 시도해주세요.');
		}
	}
	
	//세팅 List li 삭제 time변수에 true가 들어오면 dateSettingList reset
	,deleteSettingList : function(target, status){
		let _this = this;
		
		$(target).remove();
		
		if(status){
			_this.resetDateSettingList();	
		} else {
			_this.resetSpecficDateTimeSettingList();
		}
		
	}
	
	,resetTimeSettingList : function(){
		let _this = this;
		
		_this.timeSettingList = [];
		
		$('.custom_select_list.time').each(function(){
				let timeSettingValue = $(this).attr('data-time-setting');
				_this.timeSettingList.push(timeSettingValue);
		});
		
		$('[name="branchTimeSettingDtoList"]').val("["+ _this.timeSettingList + "]");
	}
	
	,resetDateSettingList : function(){
		let _this = this;
		
		_this.dateSettingList = [];
		
		$('.custom_select_list.date > li').each(function(){
			let dateSettingValue = $(this).attr('data-date-setting');
			_this.dateSettingList.push(dateSettingValue);
		})
		$('[name="branchDateSettingDtoList"]').val("["+ _this.dateSettingList + "]");
	}
	
	,resetSpecficDateTimeSettingList : function(){
		let _this = this;
		
		_this.specficDateTimeSettingList = [];
		
		$(_this.specficDateTimeSettingListCon).find('> li').each(function(){
			let specficDateTimeSettingValue = $(this).attr('data-time-setting');
			_this.specficDateTimeSettingList.push(specficDateTimeSettingValue);
		})
		$('[name="branchSpecficDateTimeSettingList"]').val("["+ _this.specficDateTimeSettingList + "]");
	}
	
	//휴무일 세팅이 겹치지 않게 하기 위한 계산
	,calcDateSettingList : function(targetDate){
		let _this = this;

		let result = true;
		
		let compareTarget;
		for(let i=0; i < $(_this.dateSettingList).length; i++){
			compareTarget = parsingControl.parseStrToObj( _this.dateSettingList[i] );
			//날이 겹칠 경우
			if(targetDate == compareTarget["'selectDate'"]){
				result = false;
				return false;
			}
		}
		
		return result;
		
	}
	
	//특정일 특정시간대 세팅이 겹치지 않게 하기 위해 체크
	,checkSpecficDateTimeSettingList : function(targetDate, startTime, endTime){
		let _this = this;

		let result = true;
		
		let compareTarget;
		for(let i=0; i < $(_this.specficDateTimeSettingList).length; i++){
			compareTarget = parsingControl.parseStrToObj( _this.specficDateTimeSettingList[i] );
			//날이 겹칠 경우
			if(targetDate == compareTarget["'selectDate'"]){
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
		}
		
		return result;
		
	}
	
	,removeEvent: function(){
		
	}
	,destroy: function(){
		
	}
}

$(document).ready(function(){
	branchTimeUpdate.init();
})
