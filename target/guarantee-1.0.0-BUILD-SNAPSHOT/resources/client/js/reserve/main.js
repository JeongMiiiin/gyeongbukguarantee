let main = {
	
	branchSelectCon : null
	,branchSelectInitVal : null
	,holidayList : makeHolidayArray()
	,branchHolidayList : null
	,branchFullReserveList : null
	
	,init : function(){
		
		let _this = this;
		
		this.branchHolidayList = branchDateList;
		this.branchFullReserveList = fullReserveList;
		
		let maxDate = new Date();
		maxDate.setMonth(maxDate.getMonth() + 2);
		
		$('[data-common-datepicker]').datepicker({
			dateFormat : "yy-mm-dd"
			,minDate : 1
			,maxDate : maxDate
			,setDate : new Date()
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
					
					if(_this.holidayList.includes(y+m+d)){
						//holiday에 포함이 되는지 확인
						return [false, 'data-holiday'];
					} else if(date <= todayDate){
						return [false, '']
					} else if(_this.branchHolidayList.includes(y+'-'+m+'-'+d)) {
						//branch-holiday에 포함이 되는지 확인
						return [false, 'data-branch-holiday'];	
					} else if(_this.branchFullReserveList.includes(y+'-'+m+'-'+d)){
						//full reserve에 포함이 되는지 확인
						return [false, 'data-branch-deadline'];
					} else {
						return [true, ''];	
					}
				} else {
					return [false, 'data-weekend'];
				}
				
			}
			
		});
		
		$('.calendar_cont').prepend($('#ui-datepicker-div'));
		
		this.branchSelectCon = $('#branchListSelect');
		this.branchSelectInitVal = $('#branchListSelect').val();
		
		
		this.registEvent();
	}
	,registEvent : function(){
		let _this = this;
		$(this.branchSelectCon).on('change', function(){
			if(_this.branchSelectInitVal != $(this).val()){
				let linkInfo = window.location.href.split('?')[0];
				linkInfo += '?br_idx=' + $(this).val();
				location.replace(linkInfo);
					
			}
		})

		let triggerInterval = setInterval(function(){
			if($('#ui-datepicker-div').find('> div').length == 0){
				$('[data-common-datepicker]').trigger('focus');
			} else {
				clearInterval(triggerInterval);
			}
		}, 500);
		
	}
	
	,removeEvent : function(){
		
	}
	,destroy : function(){
		
	}
}

$(document).ready(function(){
	main.init();
})