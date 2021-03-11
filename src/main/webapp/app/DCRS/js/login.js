//页面加载完成执行方法块
 $(function(){
 if ($.cookie("userid") != null && $.cookie("password") != null) {
	        $("#username").val($.cookie("userid"));
	        $("#password").val($.cookie("password"));
	 }	 
});
/*请求地址*/
var locationUrl = 'http://'+window.location.host;  
function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	var lineName = getQueryString("id");
	if($("input[type='checkbox']").is(':checked')){
	    $.cookie("userid", username, { expires: 7 }); // 存储一个带7天期限的 cookie
	    $.cookie("password", password, { expires: 7 }); // 存储一个带7天期限的 cookie
	}	
	if(username == ''|| password == ''){
		alert('请输入用户名和密码');
	}				
		var data = {USERNAME:username,PASSWORD:password};
		$.ajax({
			url:locationUrl + '/dafeng_mes/android/appLogin1',
			type:'post',
			data:data,
			cache:false,
			success: function(data){
				console.log(data);
				var resStatus = data.resCode;			
				if(resStatus == 1){	
					console.log("登陆成功");
					var userID = data.USERNAME;
					var userName = data.NAME;
					window.location.href = "index.html?userID="+userID+"&userName="+userName+"";
				}else if(resStatus == 0){
					alert("账号或密码错误");
				}else{
					alert("配置信息错误");
				}																				
			},
			error:function(error){
				console.log(error)
			}
		})
}
/* 获取url参数 */
function getQueryString(name) {
	var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
		    return "";
		}
		return decodeURI(result[1]);
}