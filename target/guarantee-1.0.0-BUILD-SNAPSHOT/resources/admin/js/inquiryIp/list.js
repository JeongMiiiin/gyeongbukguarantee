let inquiryIpList = {
	
	keywordSelectVal : null
	
	,init : function(){
			
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
	
	,updateRequest(milIdx, memIdx, milAccessIp, status){
		
		let milApproveAt = 1;
		
		if( !status ) milApproveAt = -1; 
		
		let params = JSON.stringify({"milIdx" : milIdx, "memIdx" : memIdx, "milAccessIp" : milAccessIp, "requestStatus" : milApproveAt});
		
		$.ajax({
			url : "/reserve/adm/inquiryIpManage/updateRequest"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,timeoutDelay : 90000
			,success : function(result){
				if(result.result){
					//변경 성공시
					if(milApproveAt == -1){
						alert('거절되었습니다.');
					} else {
						alert('승인되었습니다.');
					}
					
					window.location.reload();
				} else {
					alert('상태 변경에 실패했습니다. 잠시 후 다시 시도해주세요.');
				}

			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
		})
		
	}
	
	,removeEvent : function(){
		
	}
	,destroy : function(){
		
	}
}

$(document).ready(function(){
	inquiryIpList.init();
})
