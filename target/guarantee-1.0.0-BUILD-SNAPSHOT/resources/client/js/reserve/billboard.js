window.onload = function(){
	
	document.getElementById('checkAudio').addEventListener('click', function() {
		let context = new AudioContext();
		 context.resume().then(() => {
		 	console.log('Playback resumed successfully');
		 });
	});
		   
	$('#checkAudio').trigger('click');
}

let billboard = {
	
	
	brIdx : null
	,consultNum : 1
	,consultTime : null
	,playStatus : false
	,audioList : []
	,playedAudioList : []
	,prevAudioList : []
	,branchTimeSettingList : []
	,billboardFullDataList : []
	
	,init : function(){
		
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
		this.branchSpecficDateTimeSettingList = branchSpecficDateTimeSettingList;
		this.billboardFullDataList = billboardFullDataList;
		this.prevAudioList = prevAudioList;

		this.settingDefaultTimeList();		
		
		this.registEvent();
	}
	
	,registEvent : function(){
		let _this = this;
		
		_this.settingBillboardContent(this.consultNum);
		_this.checkBillboardNum();
		
		_this.settingBillboardList();
		
		_this.settingDefaultTimeList();
		
		setInterval(function(){
			
			let checkSec = new Date();
			let checkSecValue = checkSec.getSeconds();
			
			//1분마다 audio 체크
			if(checkSecValue == "00" ){
				_this.settingDefaultTimeList();
				/*_this.updateAudio();*/				
			}
			
			_this.checkAudio();
			_this.selectReserveList();
			_this.checkBillboardNum();
			_this.settingBillboardList();
			
		}, 1000);
		
	}
	
	,settingDefaultTimeList : function(){
		
		let _this = this;
		
		let targetOperationOpenTime = _this.openTime;
		let targetOperationLunchOpenTime = _this.lunchOpenTime;
		let targetOperationCloseTime = _this.closeTime;
		let targetOperationLunchCloseTime = _this.lunchCloseTime;
		
		let dayOfWeek = (new Date()).getDay();
		
		switch(dayOfWeek){
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
			default :
				if(TimeControl.compareTime(_this.fridayOpenTime, targetOperationOpenTime) == 1 ) targetOperationOpenTime = _this.fridayOpenTime;
				if(TimeControl.compareTime(targetOperationCloseTime, _this.fridayCloseTime) == 1 ) targetOperationCloseTime = _this.fridayCloseTime; 
				break;
		}
		
		
		_this.defaultTimeList = _this.getTimeList(targetOperationOpenTime, targetOperationLunchOpenTime, targetOperationLunchCloseTime, targetOperationCloseTime);
	}
	
	//현재 시간에 창구개수가 달라야하는지 체크하고 달라야 하면 settingBillboardContent 호출해서 창구개수 변경
	,checkBillboardNum : function(){
		let _this = this;
		
		let nowDate = parsingControl.parseDateToStr(new Date(), '-', true);
		
		let nowTime = new Date();
		let nowTimeHourValue = nowTime.getHours() < 10 ? '0' + nowTime.getHours() : nowTime.getHours();
		let nowTimeMinValue = nowTime.getMinutes() < 10 ? '0' + nowTime.getMinutes() : nowTime.getMinutes();
		let nowTimeValue = nowTimeHourValue + ':' + nowTimeMinValue;
		
		let prevNum = _this.consultNum;
		
		//특정시간대 창구개수에 해당하는 시간인지 체크
		for( let i=0; i < _this.branchTimeSettingList.length; i++ ){
			if(TimeControl.compareTime(nowTimeValue, _this.branchTimeSettingList[i].gbts_start_time) >= 0 && TimeControl.compareTime(nowTimeValue, _this.branchTimeSettingList[i].gbts_end_time) < 1){
				_this.consultNum = _this.branchTimeSettingList[i].gbts_counter_num;
			}
		}
		
		//특정일 특정시간대 창구개수에 해당하는 시간인지 체크
		for( let i2=0; i2 < _this.branchSpecficDateTimeSettingList.length; i2++ ){
			if(nowDate == _this.branchSpecficDateTimeSettingList[i2].gbts_target_date_str){
				if(TimeControl.compareTime(nowTimeValue, _this.branchSpecficDateTimeSettingList[i2].gbts_start_time) >= 0 && TimeControl.compareTime(nowTimeValue, _this.branchSpecficDateTimeSettingList[i2].gbts_end_time) < 1){
					_this.consultNum = _this.branchSpecficDateTimeSettingList[i2].gbts_counter_num;
				}	
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
			/*$('.billboard_contents_wrap').find('.billboard_contents_con').last().find('.billboard_contents_title > span').text((j + 1) + '번 상담창구');*/
			$('.billboard_contents_wrap').find('.billboard_contents_con').last().find('.billboard_contents_title > span').text('상담예약현황');
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
		
		let standardDay = parsingControl.parseDateToStr(new Date(), '-', true);
		
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
	
	//계속 예약목록을 가져오기
	,selectReserveList : function(){
		let _this = this;
		let params =  JSON.stringify({'brIdx' : _this.brIdx});
		
		$.ajax({
			url : "/reserve/selectBillboardReserveList"
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
	
	//방송을 해야할 상담내역이 있는지 체크
	,checkAudio : function (){
		let _this = this;
		
		let checkSec = new Date();
		checkSec.setMinutes(checkSec.getMinutes() - parseInt(_this.consultTime));
		
		let nowOneHMinHours = (checkSec.getHours() - 1) < 10 ? '0' + (checkSec.getHours() - 1) : (checkSec.getHours() - 1);
		let nowOneHMinMin = checkSec.getMinutes() < 10 ? '0' + checkSec.getMinutes() : checkSec.getMinutes();
		let nowOneHMin = nowOneHMinHours + ":" + nowOneHMinMin;
		
		checkSec.setMinutes(checkSec.getMinutes() + (parseInt(_this.consultTime) * 2));
		
		let nowTwoHPlusHours = (checkSec.getHours() + 2) < 10 ? '0' + (checkSec.getHours() + 2) : (checkSec.getHours() + 2);
		let nowTwoHPlusMin = checkSec.getMinutes() < 10 ? '0' + checkSec.getMinutes() : checkSec.getMinutes();
		let nowTwoHPlus = nowTwoHPlusHours + ":" + nowTwoHPlusMin;
		
		let checkTime = "00:00";

		if(_this.billboardFullDataList.length > 0){
			let checkConsultNum = 1;
			
			for(let i=0; i < _this.billboardFullDataList.length; i++){
				let targetReserve = _this.billboardFullDataList[i];
				
				if(checkTime != targetReserve.rl_reserve_time){
					checkConsultNum = 1;
					checkTime = targetReserve.rl_reserve_time;
				} else {
					checkConsultNum++;	
				}
				
				if( targetReserve.is_audio == 1 && _this.prevAudioList.indexOf(targetReserve.rl_idx) < 0){
					let addValue = {'rl_reserve_time' : targetReserve.rl_reserve_time, 'consult_num' : checkConsultNum, 'rl_idx' : targetReserve.rl_idx};
					_this.audioList.push(addValue);
					_this.prevAudioList.push(targetReserve.rl_idx);
				}
				
				/*if(TimeControl.compareTime(targetReserve.rl_reserve_time, nowTwoHPlus) <= 0 && TimeControl.compareTime(targetReserve.rl_reserve_time, nowOneHMin) >= 0 && targetReserve.rl_status == "2" && _this.prevAudioList.indexOf(targetReserve.rl_idx) == -1){
					let addValue = {'rl_reserve_time' : targetReserve.rl_reserve_time, 'consult_num' : checkConsultNum};
					_this.audioList.push(addValue);
					/*_this.playedAudioList(targetReserve.rl_idx);*/
					/*_this.prevAudioList.push(targetReserve.rl_idx);
				}*/
				
				/*if(targetReserve.rl_status != "2" && _this.prevAudioList.indexOf(targetReserve.rl_idx) > -1 && targetReserve.is_audio == 1 && !nowAddStatus){
					_this.prevAudioList.splice(_this.prevAudioList.indexOf(targetReserve.rl_idx), 1);
				}*/
				
				
			}
			_this.playAudio();
		}
		
	}
	
	//상담내역 방송 실행
	,playAudio : function(){
		let _this = this;
		//방송할 내역이 있는지?
		if(_this.audioList.length > 0 && !(_this.playStatus)){
			
			_this.playStatus = true;
			
			let audioTarget = _this.audioList[0];
			
			let audioTargetRlIdx = audioTarget.rl_idx;
			
			let audioHours = audioTarget.rl_reserve_time.split(":")[0];
			let audioMin = audioTarget.rl_reserve_time.split(":")[1];
			/*let audioName = '/reserve/client/audio/billboard/' + audioHours + 'H/' + audioMin + 'i_' + audioTarget.consult_num + '.mp3';*/
			let audioName = '/reserve/client/audio/billboard/' + audioHours + 'H/' + audioMin + 'i.mp3';
			let audio = new Howl({
				src : [audioName],
				volume : 1.0 ,
				onplay : function(){
					console.log("start");
					_this.updateAudio(audioTargetRlIdx);
				},
				onend : () => {
					_this.audioList.shift();
					_this.removeItem(_this.prevAudioList, audioTargetRlIdx);
					console.log(audioName);
					console.log("end");
					_this.playStatus = false;
					_this.playAudio();}
			});
				
			/*$('#checkAudio').trigger('click');*/
				
			audio.play();
		}
		
	}
	
	,updateAudio : function(rlIdx){
		let params = JSON.stringify({rlIdx : rlIdx});
		
		$.ajax({
			url : "/reserve/updateAudio"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,timeoutDelay : 90000
			,success : function(result){
				console.log(result.result);
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