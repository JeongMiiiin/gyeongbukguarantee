commonUpdate.checkUpdateForm = function(){
	let check = true;

	if($('[name="checkDuplicateId"]').val() == '0'){
		alert('아이디 중복확인을 해주세요.');
		$('[name="memUserId"]').focus();
		check = false;
	}
		
	return check;	
}

let memberUpdate = {
	
	useridInput : null
	,checkDuplicateIdInput : null
	,initIdVal : null
	
	,init : function(){
		this.useridInput = $('[name="memUserId"]');
		this.checkDuplicateIdInput = $('[name="checkDuplicateId"]'); 
		this.initIdVal = $('[name="memUserId"]').val();
		this.registEvent(); 	
	}
	,registEvent : function(){
		let _this = this;

		let oldVal = "";
		$(this.useridInput).on("propertychange change keyup paste input", function() {
		    var currentVal = $(this).val();
		    if(currentVal == oldVal) {
		        return;
		    }
		    
		    if(_this.initIdVal != '' && _this.initIdVal == currentVal){
				$(_this.checkDuplicateIdInput).val("1");
				$('#duplicateIdText').hide();
				return;
			}
		 
		    oldVal = currentVal;
		    $(_this.checkDuplicateIdInput).val("0");
		    $('#duplicateIdText').hide();
		});
		
	}
	
	//ID 중복확인
	,checkDuplicateId : function(){
		let _this = this;
		
		if( _this.initIdVal != '' && _this.initIdVal == $(_this.useridInput).val() ){
			alert('이전설정과 동일하기에 중복확인을 진행 안해도 됩니다.');
			return false;
		}
		
		
		if($(_this.useridInput).val() == ''){
			alert('아이디를 입력해주세요.');
			$(_this.useridInput).focus();
			return false;
		}
		
		let params = JSON.stringify({"memUserId" : $(_this.useridInput).val()});
		
		$.ajax({
			url : "/reserve/adm/memberManage/checkDuplicateId"
			,contentType : 'application/json; charset=UTF-8'
			,dataType : "json"
			,data : params
			,type : "post"
			,timeoutDelay : 90000
			,success : function(result){
				if(result.result){
					alert('중복된 아이디입니다.');
					$('#duplicateIdText').hide();
				} else {
					alert('사용 가능한 아이디입니다.');
					$(_this.checkDuplicateIdInput).val("1");
					$('#duplicateIdText').show();
				}

			}
			,error:function(request,status,error){
    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    		}
		})
	}
	
	//비밀번호 초기화
	,initializePassword : function(){
		if(confirm('비밀번호를 초기화하시겠습니까?')){
			let params = JSON.stringify({"memIdx" : $('#memIdx').val()});
			$.ajax({
				url : "/reserve/adm/memberManage/initializePassword"
				,contentType : 'application/json; charset=UTF-8'
				,dataType : "json"
				,data : params
				,type : "post"
				,timeoutDelay : 90000
				,success : function(result){
					if(result.result){
						//변경 성공 시
						alert('초기화에 성공했습니다.');
					} else {
						alert('초기화에 실패했습니다. 잠시 후 다시 시도해주세요.');
					}
	
				}
				,error:function(request,status,error){
	    			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    		}
			})	
		}

	}
	
	,removeEvent: function(){
		
	}
	,destroy: function(){
		
	}
}

$(document).ready(function(){
	memberUpdate.init();
})