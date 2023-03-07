function makeHolidayArray(){
	
	let params = JSON.stringify({});
	let holidayList = [];
	$.ajax({
			url : "/reserve/getHoliday"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,async : false
			,timeoutDelay : 90000
			,success : function(result){
				if(result.result){
					holidayList = result.data;
				}

			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
	});
	
	return holidayList;
}

$(function(){
	commonList.init();
	commonUpdate.init();
	commonView.init();
})