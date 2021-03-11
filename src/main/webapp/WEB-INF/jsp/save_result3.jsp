<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保存结果</title>
<base href="<%=basePath%>">
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>

</head>
<body>
	<div id="zhongxin"></div>
	<script type="text/javascript">
	$(top.hangge());//关闭加载状态
		var msg = "${msg}";
		if(msg == "success" || msg == ""){
			document.getElementById('zhongxin').style.display = 'none';	
			top.Dialog.close();
			//top.hangge()
		}else if("propertiesIsNotExist" == msg){
			document.getElementById('zhongxin').style.display = 'none';
			bootbox.alert({
   			 message: 'E盘下面未找到FTP配置文件',
   		  });
		}else if("ftpConnFail" == msg){
			bootbox.alert({
	   			 message: 'FTP连接失败',
	   			 callback: function() {
	   				 top.Dialog.close();
	            },
	   		});
		}else if("pdfDeleFail" == msg){
			bootbox.alert({
	   			 message: '产品已有的PDF文件删除失败',
	   			 callback: function() {
	   				 top.Dialog.close();
	            },
	   		});
		}else if("pngDeleFail" == msg){
			bootbox.alert({
	   			 message: '产品已有的图片删除失败',
	   			 callback: function() {
	   				 top.Dialog.close();
	            },
	   		});
		}else if("pdfUploadFail" == msg){
			bootbox.alert({
	   			 message: 'PDF文件复制失败',
	   			 callback: function() {
	   				 top.Dialog.close();
	            },
	   		});
		}else if("copyMaterPdfNotExist" == msg){
			bootbox.alert({
	   			 message: '要复制产品PDF文件未找到',
	   			 callback: function() {
	   				 top.Dialog.close();
	            },
	   		});
		}else if("pngUploadFail" == msg){
			bootbox.alert({
	   			 message: '图片复制失败',
	   			 callback: function() {
	   				 top.Dialog.close();
	            },
	   		});
		}else if("copyMaterPngNotExist" == msg){
			bootbox.alert({
	   			 message: '要复制产品的图片未找到',
	   			 callback: function() {
	   				 top.Dialog.close();
	            },
	   		});
		}
	</script>
</body>
</html>