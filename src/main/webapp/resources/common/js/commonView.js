let commonView = {
	
	updateBtn : null
	,deleteBtn : null
	
	,init : function(){
		this.updateBtn = $('[data-common-view-update-btn]');
		this.deleteBtn = $('[data-common-view-delete-btn]');
		
		this.registEvent();	
	}
	,registEvent : function(){
		
		$(this.updateBtn).on('click', function(e){
			e.preventDefault();
			e.stopPropagation();
			if(confirm('수정하시겠습니까?')){
				window.location.href= $(this).attr('href');	
			}
		});
		
		$(this.deleteBtn).on('click', function(e){
			e.preventDefault();
			e.stopPropagation();
			if(confirm('삭제하시겠습니까?')){
				window.location.href= $(this).attr('href');	
			}
		});
		
	}
	,removeEvent : function(){
		
	}
	,destroy : function(){
		
	}
}
