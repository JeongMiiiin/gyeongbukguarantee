$(function(){
	$('.logout_btn').on('click', function(e){
		e.preventDefault();
		e.stopPropagation();
		if(confirm('로그아웃하시겠습니까?')){
			window.location.href = $(this).attr('href');
		}
	})
})
