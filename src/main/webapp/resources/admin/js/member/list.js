let memberList = {
	
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
	
}

$(document).ready(function(){
	memberList.init();
})