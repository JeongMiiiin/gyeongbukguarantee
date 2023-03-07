$(document).ready(function(){
	//로그인 폼 체크
	$("#loginForm").submit(function(){
		let loginCheck = true;
		$.each($("input, textarea"), function(){
			if($(this).val() === null || $(this).val() === ' ' || $(this).val().length === 0 ){
				if($(this).attr("id") == "userId"){
					alert("아이디를 입력해주세요.");
					loginCheck = false;	
					return loginCheck;					
				} else {
					alert("비밀번호를 입력해주세요.");
					loginCheck = false;	
					return loginCheck;
				}
				
			}
		})
		
		return loginCheck;
	});
	
	//IP 변경요청 폼 체크
	$("#inquiryIpForm").submit(function(){
		let inquiryIpCheck = true;
		$.each($("input, textarea"), function(){
			if($(this).val() === null || $(this).val() === ' ' || $(this).val().length === 0 ){
				let emptyInputId = $(this).attr("id");
				if(emptyInputId == "userId"){
					alert("아이디를 입력해주세요.");
					inquiryIpCheck = false;	
					return inquiryIpCheck;					
				} else if(emptyInputId == "userPw") {
					alert("비밀번호를 입력해주세요.");
					inquiryIpCheck = false;	
					return inquiryIpCheck;
				} else {
					alert("요청 IP를 입력해주세요.");
					loginCheck = false;	
					return inquiryIpCheck;
				}
			} else {
				if($(this).attr("id") == "inquiryIp"){
					if( !is_valid_ip_addr($(this).val()) ){
						alert("요청 IP를 정확히 입력해주세요.");
						inquiryIpCheck = false;
						return inquiryIpCheck;
					}
				}
			}
		})
		
		return inquiryIpCheck;
	})
	
	$("#changePasswordForm").submit(function(){
		let changePasswordCheck = true;
		$.each($("input, textarea"), function(){
			if($(this).val() === null || $(this).val() === ' ' || $(this).val().length === 0 ){
				if($(this).attr("id") == "userPw"){
					alert("비밀번호를 입력해주세요.");
					changePasswordCheck = false;	
					return changePasswordCheck;					
				} else {
					alert("비밀번호 확인을 입력해주세요.");
					changePasswordCheck = false;	
					return changePasswordCheck;
				}
				
			}
		})
		
		if($("input[name='userPw']").val() != $("input[name='userPwRe']").val()){
			alert("비밀번호와 비밀번호 확인이 불일치합니다.");
			changePasswordCheck = false;	
			return changePasswordCheck;
		}
		
		return changePasswordCheck;
	});
	
});

/*ip 유효성 검사 */
function is_valid_ip_addr(_target) {
		if( /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(_target) ) {
		return true;
		}
return false;
}
