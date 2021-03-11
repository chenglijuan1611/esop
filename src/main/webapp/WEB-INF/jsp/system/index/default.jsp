<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 百度echarts -->
<script type="text/javascript">
	setTimeout("top.hangge()",500);
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/bootstrap3.3.7.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/css/bootstrap3.3.7.css" />
<style>
	html, body {
		background-color: white;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}
</style>
</head>
<body class="no-skin">
		<iframe name="mainFrame" id="mainFrame" frameborder="0" src="<%=basePath%>login_defaultFrame.do" style="margin: 0 auto; width: 100%; height: 100%;"></iframe>
</body>
</html>