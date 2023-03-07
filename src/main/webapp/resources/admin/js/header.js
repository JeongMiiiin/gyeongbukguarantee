let header = {

	activeUrl : null

	,init : function(){
		
		this.activeUrl = this.getContextPath();
		
		this.registEvent();		
	}
	
	,registEvent: function(){
		
		let _this = this;
		
		if($('.gnb_wrap a').length > 0){
			$('.gnb_wrap a').each(function(){
				_this.compareLink($(this));
			})
		}
		
	}
	
	,compareLink : function(targetLink){
		let _this = this;
		
		let targetUrlList = $(targetLink).attr('href');
		targetUrlList = targetUrlList.split('/');
		targetUrlList.shift();
		targetUrlList.pop();
		
		let targetUrl = '';
		
		for(let i = 0; i < targetUrlList.length; i++){
			targetUrl += '/' + targetUrlList[i];
		}
		
		if(targetUrl == _this.activeUrl){
			$(targetLink).parent('li').addClass('active');
		}
		
	}
	
	/* 클릭헤더 열기 */
	,openClickHeader(){
		$('html, body').css('overflow', 'hidden');
		$('.click_header_wrap').addClass('active');
		
	}
	
	/* 클릭헤더 닫기 */
	,closeClickHeader(){
		$('html, body').css('overflow', '');
		$('.click_header_wrap').removeClass('active');
		
	}
	
	,getContextPath : function() {
		
		let result = "";
		
		let contextPath = window.location.pathname;
		
		contextPath = contextPath.split('/');
		contextPath.shift();
		contextPath.pop();
		
		for(let i = 0; i <contextPath.length; i++){
			result += '/' + contextPath[i];
		}
		
		return result;
	}
	
	,removeEvent: function(){
		
	}
	
	,destroy : function(){
		
	}
	
}

$(function(){
	header.init();
});