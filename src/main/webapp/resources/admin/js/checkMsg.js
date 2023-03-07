//인증번호 확인을 위한 변수
let checkMsg = {
	certificationCodeInput : null
	
	,init : function(){
		
		this.certificationCodeInput = $('#certificationCode'); 
		$(this.certificationCodeInput).focus();
		
		this.registEvent();
	}
	,registEvent: function(){
		let _this = this;
		
		$(_this.certificationCodeInput).on('keyup', function(key){
			if(key.keyCode == 13) {
				_this.onCheckMsg();
			} else {
            	if($(this).val()) {
            		$(this).next().addClass('hidden');
            	}
            }
		});
		
	}
	,onCheckMsg : function(){
		let _this = this;
		let certificationCode = $.trim($(_this.certificationCodeInput).val());
		
		if(!certificationCode) {
			$(_this.certificationCodeInput).next().removeClass('hidden').text('인증번호를 입력해 주세요.');
			$(_this.certificationCodeInput).val('').focus();
			return false;
		}
		
		let params = JSON.stringify({"certificationCode" : certificationCode});
		
		$.ajax({
			url : "/reserve/adm/checkMsgSubmit"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,timeoutDelay : 90000
			,success : function(result){
				console.log(result);
				if(result == 1){
					alert("인증에 성공했습니다.");
					
					window.location.href='/reserve/adm/memberManage/list';
				} else {
					alert("인증에 실패했습니다. 잠시 후 다시 시도해주세요.");
				}
			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
		})
		
	}
	
	,onResubmitCheckMsg : function(){
		let params = JSON.stringify({});
		
		$.ajax({
			url : "/reserve/adm/checkMsgResend"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,timeoutDelay : 90000
			,success : function(result){
				if(result.result = true){
					alert('인증번호 발송에 성공했습니다.');
					
				} else {
					alert('인증번호 발송에 실패했습니다. 잠시 후 다시 시도해주세요.');
				}

			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
		})
	}
	
	,removeEvent: function(){
		
	}
	,destroy: function(){
		
	}
	
}

$(document).ready(function(){
	checkMsg.init();
})