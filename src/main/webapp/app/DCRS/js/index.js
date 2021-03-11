/*页面初始化，获取用户*/
$(function(){
	var userName = getQueryString("userName");
	var userID = getQueryString("userID");
	$(".userName").val(userName);				//存储到隐藏域待用
	$(".userID").val(userID);				//存储到隐藏域待用
	$("#user").text(userName);
	//fullScreen();
});

/* 跳转到点检页面 */
function jumpToDianjian(){
	var userName = $(".userName").val();
	var userID = $(".userID").val();
	window.location.href = "dianjian.html?userName="+userName+"&userID="+userID+"";
}
/* 跳转到巡检页面 */
function jumpToXunjian(){
	var userName = $(".userName").val();
	var userID = $(".userID").val();
	window.location.href = "xunjian.html?userName="+userName+"&userID="+userID+"";
}
/* 跳转到监控记录页面 */
function jumpToJiankongjilu(){
	var userName = $(".userName").val();
	var userID = $(".userID").val();
	window.location.href = "jiankongjilu.html?userName="+userName+"&userID="+userID+"";
}
/* 跳转到监控查询页面 */
function jumpToJiankongchaxun(){
	var userName = $(".userName").val();
	var userID = $(".userID").val();
	window.location.href = "jiankongchaxun.html?userName="+userName+"&userID="+userID+"";
}
/* 跳转到首检登记页面 */
function jumpToShouJianDengJi(){
	var userName = $(".userName").val();
	var userID = $(".userID").val();
	window.location.href = "shoujiandengji.html?userName="+userName+"&userID="+userID+"";
}
/* 跳转到首检确认页面 */
function jumpToShouJianQueRen(){
	var userName = $(".userName").val();
	var userID = $(".userID").val();
	window.location.href = "shoujianqueren.html?userName="+userName+"&userID="+userID+"";
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
//全屏
/*function fullScreen(){
  var el = document.documentElement;
  var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;   
    if(typeof rfs != "undefined" && rfs) {
      rfs.call(el);
    };
   return;
}*/
//退出全屏
/*function exitScreen(){
  if (document.exitFullscreen) { 
    document.exitFullscreen(); 
  } 
  else if (document.mozCancelFullScreen) { 
    document.mozCancelFullScreen(); 
  } 
  else if (document.webkitCancelFullScreen) { 
    document.webkitCancelFullScreen(); 
  } 
  else if (document.msExitFullscreen) { 
    document.msExitFullscreen(); 
  } 
  if(typeof cfs != "undefined" && cfs) {
    cfs.call(el);
  }
}*/