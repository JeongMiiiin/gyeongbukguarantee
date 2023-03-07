let login = {
	init : function(){
		$('#userId').focus();

		this.registEvent();
	}
	
	,registEvent : function(){
		let _this = this;
		
		$('#userId, #userPw').on('keyup',function(key) {
			if(key.keyCode == 13) {
				_this.onLogin();
            } else {
            	if($(this).val()) {
            		$(this).next().addClass('hidden');
            	}
            }
    	});
	}
	
	,onLogin : function(){
		let id = $.trim($('#userId').val());
		let pw = $.trim($('#userPw').val());
		
		if(!id) {
			$('#userId').next().removeClass('hidden').text('아이디를 입력해 주세요.');
			$('#userId').val('').focus();
			return false;
		} else if(!pw) {
			$('#userPw').next().removeClass('hidden').text('비밀번호를 입력해 주세요.');
			$('#userPw').val('').focus();
			return false;
		}
		
		let params = JSON.stringify({"userId" : id, "userPw" : pw});
		
		$.ajax({
			url : "/reserve/adm/loginCheck"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,timeoutDelay : 90000
			,success : function(result){
				if(result.result == 1){
					//로그인 성공 시
					if(result.data.mem_level == 10){
						//최고 관리자 로그인 시
						alert('인증번호가 발송되었습니다.');
						window.location.href='/reserve/adm/checkMsg';
					} else {
						//서브 관리자 로그인 시
						if(result.data.mem_is_first == 1){
							//비밀번호 재설정을 해야 할 때
							alert('비밀번호를 재설정해주시기 바랍니다.');
							window.location.href='/reserve/adm/loginFirst';
						} else {
							window.location.href='/reserve/adm/branchTimeManage/list';
						}
					}
					
				} else if(result.result == 2) {
					//서브관리자인데 허용 IP가 아닐 때
					alert('허용된 IP가 아닙니다. IP변경 요청을 해주세요.');
				} else {
					alert('로그인에 실패했습니다. 잠시 후 다시 시도해주세요.');
				}

			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
		})
	}
	
	,removeEvent : function(){
		
	}
	
	,destroy: function(){
		
	}
}

$(document).ready(function(){
	login.init();
})
