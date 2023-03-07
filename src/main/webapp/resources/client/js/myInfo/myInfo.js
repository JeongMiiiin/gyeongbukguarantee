let myInfo = {
	
	
	init : function(){
		this.registEvent();	
	}
	
	,registEvent : function(){
		
	}
	
	,requestDelete : function(){
		if($('[name="reserveCheck"]:checked').length > 0){
			popupManager.add('#checkDeleteReservePopup');
		} else {
			popupManager.add('#selectReservePopup');
		}
	}
	
	,deleteReserve : function(){
		let rl_idx_array = [];
		let rl_reserve_info_array = [];
				
		$('[name="reserveCheck"]:checked').each(function(){
			rl_idx_array.push($(this).val());
			rl_reserve_info_array.push($(this).attr('data-reserve-info'));
		})
				
		let params = JSON.stringify({rl_idx_array : rl_idx_array, rl_reserve_info_array : rl_reserve_info_array});
		$.ajax({
			url : "/reserve/myInfo/delete"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,async : false
			,timeoutDelay : 90000
			,success : function(result){
				popupManager.removePopLatest(true);
				if(result.result){
					popupManager.add('#completeDeleteReservePopup');
							
				} else {
					popupManager.add('#failDeleteReservePopup');
				}
		
			}
			,error:function(request,status,error){
		    	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		    }
		});

	}
	
	,completeDeleteReserve : function(){
		popupManager.removePopLatest(true);
		window.location.reload();
	}
	
	,removeEvent : function(){
		
	}
	
	,destroy: function(){
		
	}
}

$(document).ready(function(){
	myInfo.init();
});