<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${pd.SYSNAME}</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

<!-- <link rel="stylesheet" href="static/login/bootstrap.min.css" />
<script type="text/javascript" src="static/login/js/jquery-1.5.1.min.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/bootstrap3.3.7.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/css/bootstrap3.3.7.css" />
<link rel="stylesheet" href="static/login/css/logIn.css" />
</head>
<body>
	
	<div class="contain">
		<img src="${pageContext.request.contextPath}/static/login/images/background/dafeng.png" class="dafeng logo_mes">
		<img src="${pageContext.request.contextPath}/static/login/images/background/mes.png" class="mes logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/Ai.png" class="Ai logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/circle.png" class="circle1 logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/circle.png" class="circle2 logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/circle.png" class="circle3 logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/circle.png" class="circle4 logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/cloud.png" class="cloud logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/computer.png" class="computer logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/net.png" class="net logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/plain.png" class="plain logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/trans.png" class="trans logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/wifi.png" class="wifi logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/hotel.png" class="hotel logo_mes">
        <img src="${pageContext.request.contextPath}/static/login/images/background/web.png" class="web logo_mes">
       <%--  <img src="${pageContext.request.contextPath}/static/login/images/background/dafeng.png" class="daFeng">
        <img src="${pageContext.request.contextPath}/static/login/images/background/inter.png" class="inter"> --%>
		<!-- 登录 -->
			
		<div id="loginbox" class="text-left"></div>
			 <div class="form_wrap">
                   <form class="form-group" action="" method="post" name="loginForm" id="loginForm">
                        <label class="platform">
                            <img src="${pageContext.request.contextPath}/static/login/images/background/mesB.png" class="logo1">
                            <span class="logo_show">用户登录</span>
                        </label>
                           <div class="input-group form-group">
                                <div class="input-group-addon">
                                    <img src="${pageContext.request.contextPath}/static/login/images/background/user.png">
                                </div>
                                <input type="text" name="loginname" id="loginname" value="" placeholder="请输入用户名" class="form-control user">
                           </div>
                            <div class="input-group form-group">    
                                <div class="input-group-addon">
                                    <img src="${pageContext.request.contextPath}/static/login/images/background/password.png">
                                </div>
                                <input type="password" name="password" id="password" placeholder="请输入密码" class="form-control password" value="">
                            </div>
                            <div class="checkInp text-right">
                                <input type="checkbox" name="form-field-checkbox" id="saveid" onclick="savePaw();"><span class="passText">记住密码</span>
                            </div>
                            <!-- <div class="auth_code text-left">
                                <label>验证码:</label>
                                <input type="text" class="proof" name="code" id="code">
                                <img class="proofShow" id="codeImg" alt="点击更换" title="点击更换" src="" />
                            </div> -->
                        <a class="btn btn-primary register"onclick="severCheck();" id="to-recover"><span>登录</span></a>
                        <!-- <div class="copyRight">Copyright © 达峰智能制造平台</div> -->
                   </form>
               </div> 
		</div>
	<script type="text/javascript" src="static/login/js/login.js"></script>
	<script type="text/javascript">
		//服务器校验
		function severCheck(){
			if(check()){
				var loginname = $("#loginname").val();
				var password = $("#password").val();
				var code = "qq313596790fh"+loginname+",fh,"+password+"QQ978336446fh";
				$.ajax({
					type: "POST",
					url: 'login_login',
			    	data: {KEYDATA:code,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" == data.result){
							saveCookie();
							$("#loginbox").tips({
								side : 1,
								msg : '正在登录 , 请稍后 ...',
								bg : '#68B500',
								time : 2
							});
							window.location.href="main/index";
						}else if("usererror" == data.result){
							$("#loginname").tips({
								side : 1,
								msg : "用户名或密码有误",
								bg : '#FF5080',
								time : 0.5
							});
							showfh();
							$("#loginname").focus();
						} /* else if("codeerror" == data.result){
							$("#code").tips({
								side : 1,
								msg : "验证码输入有误",
								bg : '#FF5080',
								time : 15
							});
							showfh();
							$("#code").focus();
						} *//* else{
							$("#loginname").tips({
								side : 1,
								msg : "缺少参数",
								bg : '#FF5080',
								time : 15
							});
							showfh();
							$("#loginname").focus();
						} */
					}
				});
			}
		}
	
		/*  $(document).ready(function() {
			changeCode1();
			$("#codeImg").bind("click", changeCode1);
			$("#zcodeImg").bind("click", changeCode2);
		});  */
	
		//键盘回车事件，执行登录
		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		/* function changeCode1() {
			$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		}
		function changeCode2() {
			$("#zcodeImg").attr("src", "code.do?t=" + genTimestamp());
		}  */

		//客户端校验
		function check() {

			if ($("#loginname").val() == "") {
				$("#loginname").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}
			if ($("#password").val() == "") {
				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#password").focus();
				return false;
			}
			/* if ($("#code").val() == "") {
				$("#code").tips({
					side : 1,
					msg : '验证码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				showfh();
				$("#code").focus();
				return false;
			} */
			

			return true;
		}

		function savePaw() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('loginname', '', {
					expires : -1
				});
				$.cookie('password', '', {
					expires : -1
				});
				$("#loginname").val('');
				$("#password").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('loginname', $("#loginname").val(), {
					expires : 7
				});
				$.cookie('password', $("#password").val(), {
					expires : 7
				});
			}
		}
		
		jQuery(function() {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof(loginname) != "undefined"
					&& typeof(password) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				/* $("#code").focus(); */
			}
		});
		
		//登录注册页面切换
		function changepage(value) {
			if(value == 1){
				$("#windows1").hide();
				$("#windows2").show();
				changeCode2();
			}else{
				$("#windows2").hide();
				$("#windows1").show();
				changeCode1();
			}
		}
		
	//注册
	function rcheck(){
		if($("#USERNAME").val()==""){
			$("#USERNAME").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USERNAME").focus();
			$("#USERNAME").val('');
			return false;
		}else{
			$("#USERNAME").val(jQuery.trim($('#USERNAME').val()));
		}
		if($("#PASSWORD").val()==""){
			$("#PASSWORD").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PASSWORD").focus();
			return false;
		}
		if($("#PASSWORD").val()!=$("#chkpwd").val()){
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		if($("#EMAIL").val()==""){
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
		if ($("#rcode").val() == "") {
			$("#rcode").tips({
				side : 1,
				msg : '验证码不得为空',
				bg : '#AE81FF',
				time : 3
			});
			$("#rcode").focus();
			return false;
		}
		return true;
	}
	
	//提交注册
	function register(){
		if(rcheck()){
			var nowtime = date2str(new Date(),"yyyyMMdd");
			$.ajax({
				type: "POST",
				url: 'appSysUser/registerSysUser.do',
		    	data: {USERNAME:$("#USERNAME").val(),PASSWORD:$("#PASSWORD").val(),NAME:$("#name").val(),EMAIL:$("#EMAIL").val(),rcode:$("#rcode").val(),FKEY:$.md5('USERNAME'+nowtime+',fh,'),tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("00" == data.result){
						$("#windows2").hide();
						$("#windows1").show();
						$("#loginbox").tips({
							side : 1,
							msg : '注册成功,请登录',
							bg : '#68B500',
							time : 3
						});
						changeCode1();
					}else if("04" == data.result){
						$("#USERNAME").tips({
							side : 1,
							msg : "用户名已存在",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#USERNAME").focus();
					}else if("06" == data.result){
						$("#rcode").tips({
							side : 1,
							msg : "验证码输入有误",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#rcode").focus();
					}
				}
			});
		}
	}
	
	//邮箱格式校验
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}
	//js  日期格式
	function date2str(x,y) {
	     var z ={y:x.getFullYear(),M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()};
	     return y.replace(/(y+|M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-(v.length>2?v.length:2))});
	 	};
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
	<c:if test="${'1' == pd.msg}">
		<script type="text/javascript">
		$(tsMsg());
		function tsMsg(){
			alert('此用户在其它终端已经早于您登录,您暂时无法登录');
		}
		</script>
	</c:if>
	<c:if test="${'2' == pd.msg}">
		<script type="text/javascript">
			$(tsMsg());
			function tsMsg(){
				alert('您被系统管理员强制下线或您的帐号在别处登录');
			}
		</script>
	</c:if>
	<script src="static/login/js/bootstrap.min.js"></script>
	<script src="static/js/jquery-1.7.2.js"></script>
	<script src="static/login/js/jquery.easing.1.3.js"></script>
	<script src="static/login/js/jquery.mobile.customized.min.js"></script>
	<script type="text/javascript" src="static/js/jQuery.md5.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
	
</body>

</html>