<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String str=(String)request.getParameter("carton");
	String resulstr=(String)request.getParameter("resultdata");	
	
%>

<html lang="en">
<head>
<base href="<%=basePath%>">
<base href="<%=str%>">
<base href="<%=resulstr%>">
<meta http-equiv="Content-Type" content="IE=edge,chrome=1"/>
<meta charset="utf-8" />
<title>信息追溯</title>
<meta name="description" content="" />
<script type="text/javascript" src="static/login/js/jquery3.3.1.js"></script>
<script type="text/javascript" src="static/login/js/bootstrap3.3.7.js"></script>
</head>
<body>

 <script type="text/javascript">
		$(function() {
			var keywords= "<%=str%>";		
			var resulstr= "<%=resulstr%>";	
			if(resulstr=="cusbarcode"){
				 var url='<%=basePath%>informationTracking/list.do?CUSTOMERBARCODE='+keywords;	
				 windowOpen(url);
			}else if(resulstr=="barcode"){
				var  url='<%=basePath%>informationTracking/list.do?keywords='+keywords;	
				 windowOpen(url);
			}

			});
				
		 function windowOpen(url){//用post方式打开新窗口
	            var map = {};   
	            if (url.indexOf('?') != -1) {   
	                var str = url.substr(url.indexOf('?')+1);   
	                strs = str.split('&');   
	                for(var i = 0; i < strs.length; i ++) {   
	                    map[strs[i].split('=')[0]]=unescape(strs[i].split('=')[1]);   
	                }   
	                url = url.substr(0,url.indexOf('?'));
	            }   
	            var winFormName = "tempForm1";
	            var tempForm = document.createElement("form");    
	            tempForm.id = winFormName;  
	            //制定发送请求的方式为post  
	            tempForm.method="post";   
	            //此为window.open的url，通过表单的action来实现  
	            tempForm.action = url;  
	            //利用表单的target属性来绑定window.open的一些参数（如设置窗体属性的参数等）  
	            tempForm.target="_self"; 
	            $.each(map, function(key, val) {
	                var hideInput = document.createElement("input");    
	                hideInput.type ="hidden";    
	                hideInput.name = key;  
	                hideInput.value = val; 
	                //将input表单放到form表单里  
	                tempForm.appendChild(hideInput); 
	                //formHtml += "<input type='hidden' name='"+key+"' value='"+val+"'/>";
	            });
	            //将此form表单添加到页面主体body中  
	            document.body.appendChild(tempForm); 
	            //手动触发，提交表单  
	            tempForm.submit();
	            //从body中移除form表单  
	            document.body.removeChild(tempForm);  
	        }
		  
	</script> 
 
</body>
</html>