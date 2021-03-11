$(function() {
	setTimeout(function() {
		$('.logo_mes').show();
	}, 500);
	
		 //模态框弹出
	        $('.mes')
	        .on('mouseenter', function(){
	        	$(this).attr('src', '/esop/static/login/images/background/mesB.png');
	        })
	        .on('mouseleave', function() {
	        	$(this).attr('src', '/esop/static/login/images/background/mes.png');
	        })
	        .on('click', function() {
	               $('.form_wrap').toggle(600);
	         })
	 })
	 
