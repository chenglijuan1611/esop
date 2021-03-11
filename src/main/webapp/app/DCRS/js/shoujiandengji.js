/*页面初始化，获取用户*/
$(function(){
	//getEquMessage();
	var userName = getQueryString("userName");
	var userID = getQueryString("userID");
	$(".userName").val(userName);										//存储到隐藏域待用
	$(".userID").val(userID);										//存储到隐藏域待用
	$("#user").text(userName);
	$("#checker").text(userName);
	$("#workshop").text($("#workshopHidden").val());
	$("#equname").text($("#equnameHidden").val());
	
});
/*公共数据接口地址*/
var locationUrl = 'http://'+window.location.host;	
/* 返回上一页 */
function back(){
	history.go(-1);
}
/*退出按钮*/
function exit(){
	window.location.href = "login.html";
}
/* 获取url参数 */
function getQueryString(name) {
	var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
		    return "";
		}
		return decodeURI(result[1]);
}