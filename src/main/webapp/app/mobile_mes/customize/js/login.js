//页面加载完成执行方法块
$(function(){
	 var changePassflag = GetQueryString("id");
	 if(changePassflag == "1"){
		 $(".username").val("");
		 $(".password").val("");
		 return false;
	 }else if ($.cookie("userid") != null && $.cookie("password") != null) {		 	
	        $(".username").val($.cookie("userid"));
	        $(".password").val($.cookie("password"));
	        login();											//如果cookie存在，就自动登录
	 }
});	
/*请求地址*/
var locationUrl = window.location.protocol + "//" + window.location.host; 										//公有请求地址
var projectName = window.location.pathname;
var proArr = projectName.split('/');
var proname = proArr[1];
locationUrl = locationUrl + '/' + proname;  
function login(){
	var username = $(".username").val();
	var password = $(".password").val();
	if ($("#remid").is(':checked') == true) {
        $.cookie("userid", username, { expires: 30,path:'/' }); // 存储一个带30天期限的 cookie
        $.cookie("password", password, { expires: 30,path:'/'}); // 存储一个带30天期限的 cookie
	}
	if(username == ''|| password == '请输入用户名和密码'){
		$(".alert-content").text('请输入用户名和密码');
		$(".myAlert").show();
		//alert('请输入用户名和密码');		
		return false;
	}else{			
		var data = {USERNAME:username,PASSWORD:password};
		$.ajax({
			/*url:'http://172.16.20.182:8080/dafeng_mes/android/appLogin1',*/
			url:locationUrl + '/android/appLogin1',
			type:'post',
			data:data,
			cache:false,
			success: function(data){
				console.log(data);
				var resStatus = data.resCode;			
				if(resStatus == 1){						
					var username_rec = data.USERNAME;
					var name = data.NAME;
					$.cookie("name", name, { expires: 30 }); // 存储一个带30天期限的 cookie
					//alert("username_rec"+username_rec);
					var url = encodeURI("adminNew.html?USERNAME="+username_rec+"&NAME="+name+"");
					window.location.href = url;					
				}else if(resStatus == 0){
					$(".alert-content").text('账号或密码错误');
					$(".myAlert").show();
					//alert("账号或密码错误");
					return false;
				}else{
					$(".alert-content").text('配置信息错误');
					$(".myAlert").show();
					//alert("配置信息错误");
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