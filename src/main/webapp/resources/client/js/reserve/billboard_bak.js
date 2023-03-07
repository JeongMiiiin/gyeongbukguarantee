let billboard = {
	
	
	brIdx : null
	,consultNum : 1
	,consultTime : null
	,audioList : []
	,branchTimeSettingList : []
	,billboardFullDataList : []
	
	,init : function(){
		let _this = this;
		
		this.brIdx = brIdx;
		this.consultNum = defaultConsultNum;
		this.consultTime = defaultConsultTime;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.lunchOpenTime = lunchOpenTime;
		this.lunchCloseTime = lunchCloseTime;
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
		this.branchTimeSettingList = branchTimeSettingList;
		this.billboardFullDataList = billboardFullDataList;

		this.settingDefaultTimeList();		
		
		this.registEvent();
	}
	
	,registEvent : function(){
		let _this = this;
		
		_this.settingBillboardContent(this.consultNum);
		_this.checkBillboardNum();
		
		_this.settingBillboardList();
		
		setInterval(function(){
			
			/*let checkSec = new Date();
			let checkSecValue = checkSec.getSeconds();

			if(checkSecValue == "00" ){
				_this.checkBillboardNum();
				_this.settingDefaultTimeList();
				_this.settingBillboardList();
			}*/
			_this.selectReserveList();
			_this.checkBillboardNum();
			_this.settingDefaultTimeList();
			_this.settingBillboardList();
			
		}, 1000);
		
	}
	
	,settingDefaultTimeList : function(){
		
		let _this = this;
		
		let todayOfWeek = new Date().getDay();
		
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.lunchOpenTime = lunchOpenTime;
		this.lunchCloseTime = lunchCloseTime;
		
		let targetOperationOpenTime = _this.openTime;
		let targetOperationLunchOpenTime = _this.lunchOpenTime;
		let targetOperationCloseTime = _this.closeTime;
		let targetOperationLunchCloseTime = _this.lunchCloseTime;
		
		switch(todayOfWeek){
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
		}
		
		_this.defaultTimeList = _this.getTimeList(targetOperationOpenTime, targetOperationLunchOpenTime, targetOperationLunchCloseTime, targetOperationCloseTime);
	}
	
	//현재 시간에 창구개수가 달라야하는지 체크하고 달라야 하면 settingBillboardContent 호출해서 창구개수 변경
	,checkBillboardNum : function(){
		let _this = this;
		
		let nowTime = new Date();
		let nowTimeHourValue = nowTime.getHours() < 10 ? '0' + nowTime.getHours() : nowTime.getHours();
		let nowTimeMinValue = nowTime.getMinutes() < 10 ? '0' + nowTime.getMinutes() : nowTime.getMinutes();
		let nowTimeValue = nowTimeHourValue + ':' + nowTimeMinValue;
		
		let prevNum = _this.consultNum;
		
		//현재 시간에 운영되어야 하는 창구 개수 구하기
		for( let i=0; i < _this.branchTimeSettingList.length; i++ ){
			if(TimeControl.compareTime(nowTimeValue, _this.branchTimeSettingList[i].gbts_start_time) >= 0 && TimeControl.compareTime(nowTimeValue, _this.branchTimeSettingList[i].gbts_end_time) < 1){
				_this.consultNum = _this.branchTimeSettingList[i].counter_num;
			}
		}
		
		if(prevNum != _this.consultNum){
			_this.settingBillboardContent(_this.consultNum);		
		}
	}
	
	,settingBillboardContent : function(consultNum){
		//content template 세팅
		let billboardContentTemplate;
		switch(consultNum){
			case "4" : 
				$('.billboard_contents_wrap').addClass('type_2');
				billboardContentTemplate = '<div class="col-12 col-md-3 billboard_contents_con">'+
			 		'<div class="col-12 billboard_contents">' +
			 			'<div class="col-12 billboard_contents_inner">' +
			 				'<div class="col-12 billboard_contents_box">' +
			 					'<div class="col-12 billboard_contents_title"><span class="font_nanumGothic"></span></div>' +
			 					'<div class="col-12 billboard_contents_line"><span class="arrow_btn"></span></div>' +
			 					'<ul class="col-12 billboard_reserve_list"></ul>' +
			 				'</div>' +
			 			'</div>' +
			 		'</div>' +
				'</div>';
			
				break;
			case "5" :
				$('.billboard_contents_wrap').addClass('type_2');
				billboardContentTemplate = '<div class="col-12 col-md-20 billboard_contents_con">'+
			 		'<div class="col-12 billboard_contents">' +
			 			'<div class="col-12 billboard_contents_inner">' +
			 				'<div class="col-12 billboard_contents_box">' +
			 					'<div class="col-12 billboard_contents_title"><span class="font_nanumGothic"></span></div>' +
			 					'<div class="col-12 billboard_contents_line"><span class="arrow_btn"></span></div>' +
			 					'<ul class="col-12 billboard_reserve_list"></ul>' +
			 				'</div>' +
			 			'</div>' +
			 		'</div>' +
				'</div>'; 
				break;
			default :
				billboardContentTemplate = '<div class="col-12 col-md-4 billboard_contents_con">'+
			 		'<div class="col-12 billboard_contents">' +
			 			'<div class="col-12 billboard_contents_inner">' +
			 				'<div class="col-12 billboard_contents_box">' +
			 					'<div class="col-12 billboard_contents_title"><span class="font_nanumGothic"></span></div>' +
			 					'<div class="col-12 billboard_contents_line"><span class="arrow_btn"></span></div>' +
			 					'<ul class="col-12 billboard_reserve_list"></ul>' +
			 				'</div>' +
			 			'</div>' +
			 		'</div>' +
				'</div>';
				$('.billboard_contents_wrap').removeClass('type_2');
				break;
		}
		
		let j = 0;
		
		$('.billboard_contents_wrap').empty();
		
		while(j < consultNum){
			$('.billboard_contents_wrap').append(billboardContentTemplate);
			$('.billboard_contents_wrap').find('.billboard_contents_con').last().find('.billboard_contents_title > span').text((j + 1) + '번 상담창구');
			j++;
		}
		
	}
	
	//예약 세팅
	,settingBillboardList : function(){
		let _this = this;
		
		$('.billboard_contents_wrap').find('.billboard_contents_con .billboard_reserve_list > li').remove();
		
		let listTemplate = '<li><div class="col-4"></div><div class="col-4"></div><div class="col-4"></div></li>';
		
		
		let defaultTimeList = [];
		if(_this.defaultTimeList.length > 0){
			defaultTimeList = _this.defaultTimeList.slice();
		}
		
		let reserveDataList = [];
		if(_this.billboardFullDataList.length > 0){
			reserveDataList = _this.billboardFullDataList.slice();
		}
		
		let nowTime = new Date();
		
		let nowTimeConsultStartTime = new Date(nowTime.setMinutes(nowTime.getMinutes() - parseInt(_this.consultTime)));
		
		let nowTimeConsultStartHourValue = nowTimeConsultStartTime.getHours() < 10 ? '0' + nowTimeConsultStartTime.getHours() : nowTimeConsultStartTime.getHours();
		let nowTimeConsultStartMinValue = nowTimeConsultStartTime.getMinutes() < 10 ? '0' + nowTimeConsultStartTime.getMinutes() : nowTimeConsultStartTime.getMinutes();
		let nowTimeConsultStartTimeValue = nowTimeConsultStartHourValue + ':' + nowTimeConsultStartMinValue;
		
		let nowTimeConsultEndTime = new Date(nowTime.setMinutes(nowTime.getMinutes() + parseInt(_this.consultTime)));
		
		let nowTimeConsultEndHourValue = nowTimeConsultEndTime.getHours() < 10 ? '0' + nowTimeConsultEndTime.getHours() : nowTimeConsultEndTime.getHours();
		let nowTimeConsultEndMinValue = nowTimeConsultEndTime.getMinutes() < 10 ? '0' + nowTimeConsultEndTime.getMinutes() : nowTimeConsultEndTime.getMinutes();
		let nowTimeConsultEndTimeValue = nowTimeConsultEndHourValue + ':' + nowTimeConsultEndMinValue;  
		
		let i = 0;
		let maxlen = parseInt(defaultTimeList.length / _this.consultNum) < 5 ? parseInt(defaultTimeList.length / _this.consultNum) : 5;
		
		let targetContentCon, targetContent, targetTimeText, targetNameText, targetStatusText;
		do{
			if(defaultTimeList.length > 0){
				let j = 0;
				while(j < _this.consultNum){
					targetContentCon = $($('.billboard_contents_wrap').find('.billboard_contents_con')[j]).find('.billboard_reserve_list');
					
					$(targetContentCon).append(listTemplate);
						
						targetContent = $(targetContentCon).find('> li').last();
						targetTimeText = defaultTimeList[0];
						defaultTimeList.shift();
						
						targetNameText = '없음';
						targetStatusText = '없음';
						
						let z = 0;
						while(z < reserveDataList.length){
							//해당 시간에 예약이 있을 때
							if(targetTimeText == reserveDataList[z].rl_reserve_time){
								targetNameText = reserveDataList[z].rl_name;
								targetStatus = reserveDataList[z].rl_status;
								
								reserveDataList.splice(z, 1);
								
								targetStatusText = '상담대기';
								switch(targetStatus){
								 	case '2' :
								 		targetStatusText = '상담중';
								 		break;
								 	case '3' :
								 		targetStatusText = '상담종료';
								 		break;
								 	case 2 :
								 		targetStatusText = '상담중';
								 		break;
								 	case 3 :
								 		targetStatusText = '상담종료';
								 		break;
								 	default :
								 		break;	
								}
								
														
								/*if(_this.compareTime(nowTimeConsultStartTimeValue, targetTimeText) == 3 ){
									//현재 상담시작시간보다 과거인 경우
									targetStatusText = '상담종료';
								} else if(_this.compareTime(targetTimeText, nowTimeConsultEndTimeValue) == 3){
									//현재 상담종료시간보다 미래인 경우
									targetStatusText = '상담대기';
								}*/
								
								break;
							}
							
							z++;
						}
						
						$(targetContent).find('> div').first().text(targetTimeText);
						$(targetContent).find('> div').eq(1).text(targetNameText);
						$(targetContent).find('> div').last().text(targetStatusText);
						
						switch(targetStatusText){
							case '상담종료' :
								$(targetContent).find('> div').last().addClass('end');
								break;
							case '상담대기' :
								$(targetContent).find('> div').last().addClass('wait');
								break;
							case '없음' :
								$(targetContent).find('> div').last().addClass('empty');
								break;
							default : 
								break;
						}
					
					j++;
				}
			} else {
				$('.billboard_contents_wrap').empty();
					$('.billboard_contents_wrap').append('<div class="col-12 billboard_contents_con no_data">'+
			 		'<div class="col-12 billboard_contents">' +
			 			'<div class="col-12 billboard_contents_inner">' +
			 				'<div class="col-12 billboard_contents_box">' +
			 					'<div class="col-12 billboard_contents_title"><span class="font_nanumGothic"></span></div>' +
			 				'</div>' +
			 			'</div>' +
			 		'</div>' +
				'</div>');
				$('.billboard_contents_wrap').find('.billboard_contents_con').last().find('.billboard_contents_title > span').text('현재는 운영시간이 아닙니다.');					
					return false;
			}
			
			i++;
		} while(i < maxlen);
		
	}
	
	,getTimeList : function(startTime, lunchStartTime, lunchEndTime, endTime){
		let _this = this;
		let result = [];
		
		let nowMinDate = new Date();
		nowMinDate.setHours(nowMinDate.getHours() - 1);
		
		let nowYear = nowMinDate.getFullYear();
		let nowMonth = (nowMinDate.getMonth() + 1) < 10 ? '0' + (nowMinDate.getMonth() + 1) : nowMinDate.getMonth() + 1;
		let nowDateDate = nowMinDate.getDate() < 10 ? '0' + nowMinDate.getDate() : nowMinDate.getDate();
		
		let standardDay = nowYear + '-' + nowMonth + '-' + nowDateDate;
		
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
		
		let timeAMInterval = Math.floor((timeAMEndTimeValue - timeAMStartTimeValue) / 1000 / 60 / parseInt(_this.consultTime));
		let timePMInterval = Math.floor((timePMEndTimeValue - timePMStartTimeValue) / 1000 / 60 / parseInt(_this.consultTime));
		
		let targetAMStartTimeValue, targetAMEndTimeValue;
		
		//오전시간대 구하기
		for(let i=0; i < timeAMInterval; i++){
			targetAMStartTimeValue = new Date(standardDay + " " + timeAMStartTime);
			targetAMStartTimeValue.setMinutes(targetAMStartTimeValue.getMinutes() + parseInt(_this.consultTime)*i);
			
			targetAMEndTimeValue = new Date(standardDay + " " + timeAMStartTime);
			targetAMEndTimeValue.setMinutes(targetAMEndTimeValue.getMinutes() + parseInt(_this.consultTime)*(i+1));
			
			if( (targetAMEndTimeValue <= timeAMEndTimeValue) && (nowMinDate < targetAMEndTimeValue) ){
				
				let timeAMHourValue = targetAMStartTimeValue.getHours() < 10 ? '0' + targetAMStartTimeValue.getHours() : targetAMStartTimeValue.getHours(); 
				let timeAMMinValue = targetAMStartTimeValue.getMinutes() < 10 ? '0' + targetAMStartTimeValue.getMinutes() : targetAMStartTimeValue.getMinutes();
				let timeAMValue = timeAMHourValue + ':' + timeAMMinValue;
				
				let z = _this.consultNum;
				
				for(let j=0; j < _this.branchTimeSettingList.length; j++){
					if(TimeControl.compareTime(timeAMValue, _this.branchTimeSettingList[j].gbts_start_time) >= 0 && TimeControl.compareTime(timeAMValue, _this.branchTimeSettingList[j].gbts_end_time) < 1){
					 z = _this.branchTimeSettingList[j].counter_num;
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
			targetPMStartTimeValue.setMinutes(targetPMStartTimeValue.getMinutes() + parseInt(_this.consultTime)*_i);
			
			targetPMEndTimeValue = new Date(standardDay + " " + timePMStartTime);
			targetPMEndTimeValue.setMinutes(targetPMEndTimeValue.getMinutes() + parseInt(_this.consultTime)*(_i+1));
			
			if( (targetPMEndTimeValue <= timePMEndTimeValue) && (nowMinDate < targetPMEndTimeValue) ){
				let timePMHourValue = targetPMStartTimeValue.getHours() < 10 ? '0' + targetPMStartTimeValue.getHours() : targetPMStartTimeValue.getHours(); 
				let timePMMinValue = targetPMStartTimeValue.getMinutes() < 10 ? '0' + targetPMStartTimeValue.getMinutes() : targetPMStartTimeValue.getMinutes();
				let timePMValue = timePMHourValue + ':' + timePMMinValue;
				
				let _z = _this.consultNum;
				
				for(let _j=0; _j < _this.branchTimeSettingList.length; _j++){
					if(TimeControl.compareTime(timePMValue, _this.branchTimeSettingList[_j].gbts_start_time) >= 0 && TimeControl.compareTime(timePMValue, _this.branchTimeSettingList[_j].gbts_end_time) < 1){
					 _z = _this.branchTimeSettingList[_j].counter_num;
					}
				}
				let _x = 0;
				while(_x < _z){
					result.push(timePMValue);
					_x++;	
				}
			}
			
		}
		
		/*for(let i=0; i < timeInterval; i++){
			let targetTimeValue = new Date(standardDay + " " + startTime);
			
			targetTimeValue.setMinutes(startTimeValue.getMinutes() + parseInt(_this.consultTime)*i);
			
			if(lunchStartTimeValue <= targetTimeValue && targetTimeValue < lunchEndTimeValue ){
				continue;
			} else {
				
				let timeHourValue = targetTimeValue.getHours() < 10 ? '0' + targetTimeValue.getHours() : targetTimeValue.getHours(); 
				let timeMinValue = targetTimeValue.getMinutes() < 10 ? '0' + targetTimeValue.getMinutes() : targetTimeValue.getMinutes();
				let timeValue = timeHourValue + ':' + timeMinValue;
				
				let nowTime = new Date();
				let nowTimeMinus1H = (nowTime.getHours() - 1) < 10 ? '0' + (nowTime.getHours() - 1) : (nowTime.getHours() - 1);
				let nowTimeMinValue = nowTime.getMinutes() < 10 ? '0' + nowTime.getMinutes() : nowTime.getMinutes();
				let nowTimeMinus1HValue = nowTimeMinus1H + ':' + nowTimeMinValue;
				
				if(_this.compareTime(nowTimeMinus1HValue, timeValue) != 3){
					let z = _this.consultNum;
				
					for(let j=0; j < _this.branchTimeSettingList.length; j++){
						if(_this.compareTime(timeValue, _this.branchTimeSettingList[j].gbts_start_time) == 3 && _this.compareTime(timeValue, _this.branchTimeSettingList[j].gbts_end_time) < 3){
						 z = _this.branchTimeSettingList[j].gbts_counter_num;
						}
					}
					let x = 0;
					while(x < z){
						result.push(timeValue);
						x++;	
					}	
				}
				
			}
			
		}*/	
		
		return result;
	}
	
	//계속 예약목록을 가져오기
	,selectReserveList : function(){
		let _this = this;
		let params =  JSON.stringify({'br_idx' : _this.brIdx});
		
		$.ajax({
			url : "/reserve/select_billboard_reserve_list"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,timeoutDelay : 90000
			,success : function(result){
				if(result.result){
					_this.billboardFullDataList = result.data;
				}

			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
		})
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
	billboard.init();
})