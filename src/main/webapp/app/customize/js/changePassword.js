var locationUrl = 'http://'+window.location.host;  
/*修改密码*/
function changePassword(){
	var userName = GetQueryString("USERNAME");
	var oldpassword = $(".oldpassword").val();
	var newpassword = $(".newpassword").val();
	var againpassword = $(".againpassword").val();
	if(newpassword != againpassword){
		$(".alert-content").text('两次密码输入不一致，请重新输入');
		$(".myAlert").show();
		//alert("两次密码输入不一致，请重新输入");
		$(".newpassword").val("");
		$(".oldpassword").val("");
		$(".againpassword").val("");
		return false; 
	}else if(oldpassword == ""||newpassword == ""||againpassword == ""){
		$(".alert-content").text('请输入密码');
		$(".myAlert").show();
		//alert("请输入密码");
	}else{
	var data = {USERNAME:userName,OLDPASSWORD:oldpassword,NEWPASSWORD:newpassword}
	$.ajax({
		url:locationUrl + '/dafeng_mes/android/changePassword',
		type:'post',
		data:data,
		cache:false,
		success: function(data){
			console.log(data);																
			if(data.resCode == "0"){
				$(".alert-content").text('修改成功,正在跳转...');
				$(".myAlert").show();
				//alert("修改成功");
				setTimeout(function(){
					window.location.href ="login.html?id=1";
				},2000)				
			}else if(data.resCode == "1"){
				$(".alert-content").text('原始密码错误,请重新输入');
				$(".myAlert").show();
				//alert("原始密码错误,请重新输入");
				$(".newpassword").val("");
				$(".oldpassword").val("");
				$(".againpassword").val("");
				return false; 				
			}			
		},
		error:function(error){
			console.log(error)
		}
	})
	}
}
/*确定按钮*/
function myEvent(){
	$(".myAlert").hide();
}
/*返回按钮*/
function backHistory(){
	 window.history.go(-1);
}