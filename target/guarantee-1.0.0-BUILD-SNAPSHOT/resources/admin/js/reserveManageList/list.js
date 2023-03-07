reserveManageListList = {
	
	holidayList : makeHolidayArray()
	,keywordSelectVal : null
	
	,init : function(){
		let _this = this;
		
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
			/*,minDate : 0*/
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
		
		this.keywordSelectVal = $('#keywordSelect').val();
		
		this.registEvent();
	}
	
	,registEvent : function(){
		let _this = this;
		
		$('#keywordSelect').on('change', function(){
			if(_this.keywordSelectVal != $(this).val()){
				let target = $(this).attr('data-keyword-select');
				$('[data-keyword-input="' + target + '"]').attr('id', $(this).val());
				$('[data-keyword-input="' + target + '"]').attr('name', $(this).val());
				_this.keywordSelectVal = $(this).val();
			}
		});
	}
	
	,onExcelDownload : function(){
		if(confirm("엑셀 다운로드를 하시겠습니까?")){
			
			if($('.admin_table_style_0 table > tbody > tr').length > 0){
				
				/*let brName = $('.admin_table_style_0 table > tbody > tr').first().find('> td').eq(1).text();*/
				
				let nowDateVal = new Date();
				let nowDateYear = nowDateVal.getFullYear();
				let nowDateMonth = (nowDateVal.getMonth() + 1) < 10 ? '0' + (nowDateVal.getMonth() + 1) : (nowDateVal.getMonth() + 1);
				let nowDateDate = nowDateVal.getDate() < 10 ? '0' + nowDateVal.getDate() : nowDateVal.getDate();
				
				let nowDate = nowDateYear + nowDateMonth + nowDateDate;   
				
				let reserveName = "경북신용보증재단_예약목록_" + nowDate; 
				
				let infoList = [reserveName, reserveName,"경북신용보증재단","경북신용보증재단","경북신용보증재단","경북신용보증재단","경북신용보증재단","경북신용보증재단","경북신용보증재단","경북신용보증재단", reserveName, reserveName];
				let dataList = [["지점명", "예약자명", "연락처", "사업자종류", "상담예약일자", "예약시간"]];
				
				let dataCon = $('.admin_table_style_0 table > tbody > tr');
				
				let targetParams, targetBrName, targetName, targetHp, targetBsType, targetDate, targetTime;
				$(dataCon).each(function(){
					targetBrName = $(this).find('> td').eq(1).text();
					targetName = $(this).find('> td').eq(2).find('> a').text();
					targetHp = $(this).find('> td').eq(3).text();
					targetBsType = $(this).find('> td').eq(4).text();
					targetDate = $(this).find('> td').eq(5).text();
					targetTime = $(this).find('> td').eq(6).text();
					
					targetParams = [targetBrName, targetName, targetHp, targetBsType, targetDate, targetTime];
					
					dataList.push(targetParams);
				})
				
				excelUtil.excelDownload(infoList, dataList);	
			} else {
				alert('다운받을 예약목록이 없습니다.');
			}

		}
	}
	
	,onPrint : function(){
		if(confirm("프린트 하시겠습니까?")){
			printUtil.printStart([$('#printContent')], 80, false);
		}
	}
	
	//status 2 : 시작, 3 : 종료
	,updateReserve(rlIdx, status){
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
	
	,deleteReserve(rlIdx, brIdx, selectDate){ 
		
		if(confirm('삭제하시겠습니까?')){
			let params = JSON.stringify({"rlIdx" : rlIdx, "brIdx" : brIdx, "selectDate" : selectDate});
		
			$.ajax({
				url : "/reserve/adm/reserveManageList/deleteReserve"
				,contentType : 'application/json; charset=UTF-8'
				,dataType : "json"
				,data : params
				,type : "post"
				,timeoutDelay : 90000
				,success : function(result){
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
	
	,removeEvent : function(){
		
	}
	
	,destroy : function(){
		
	}
}

$(document).ready(function(){
	reserveManageListList.init();
})