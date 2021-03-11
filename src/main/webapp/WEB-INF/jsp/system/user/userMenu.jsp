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
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<link type="text/css" rel="stylesheet"
	href="plugins/zTree/2.6/zTreeStyle.css" />
<script type="text/javascript"
	src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
<style type="text/css">
html, body {
	width:330px;
}
#tree li a span{
		display: inline-block;
		font-size: 14px;
	}
footer {
	height: 50px;
	position: fixed;
	bottom: 0px;
	left: 0px;
	width: 100%;
	text-align: center;
}
</style>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container" >
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<div id="zhongxin" >
								<div
									style="overflow: scroll; scrolling: yes; height: 415px; width: 319px;">
									<ul id="tree" class="tree" style="overflow: auto;"></ul>
								</div>
							</div>
							<div id="zhongxin2" class="center" style="display: none">
								<br />
								<br />
								<br />
								<br />
								<img src="static/images/jiazai.gif" /><br />
								<h4 class="lighter block green">正在保存...</h4>
							</div>
						</div>
						<!-- /.col -->
					</div>
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->

	<div style="padding-top: 5px;" class="center">
		<a class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
			class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
	</div>

	<script type="text/javascript">
		$(top.hangge());
		var zTree;
		$(function() {
			 function checkSelected(menu,hasselectmenu){
				  for(var i=0;i<menu.length;i++){
					for(var j=0;j<hasselectmenu.length;j++){
						if(menu[i].MENU_ID==hasselectmenu[j].MENU_ID){
							/*  console.log(menu[i].MENU_ID); */ 
							if(menu[i].subMenu.length!=0){
								checkSelected(menu[i].subMenu,hasselectmenu);
							}
							menu[i].hasMenu=true;
						}
					}
				}
				return menu;
			} 
		
			var zn = JSON.parse('${jsonObject}');
			var hasSelect=zn.menuHasSelected;
			var zn1 = JSON.stringify(zn.allmenuList);
			//初始化把checked设置为false
			var reg = /\"hasMenu\":true/g;
			zn2 = zn1.replace(reg, '"hasMenu":false');
			var zn3 = JSON.parse(zn2);
			checkSelected(zn3,hasSelect);
			
			 //用于保存创建的树节点
			 var setting = {
					    showLine: true,
					    checkable: true,
					    nameCol: "MENU_NAME",
					    nodesCol: "subMenu",
					    checkedCol : "hasMenu" 
					};
			 /* console.log(zn3); */
			 zTree = $("#tree").zTree(setting, zn3);
		});
		//保存
			 function save(){
				var nodes = zTree.getCheckedNodes();
				/* console.dir(nodes); */
				var menuStr = '';
				for (var i = 0; i < nodes.length; i++) {
					if(i != nodes.length-1){
						menuStr += nodes[i].MENU_ID + ',';
					} 
					else {
						menuStr += nodes[i].MENU_ID;
					}
				}
				$.ajax({
					url:'<%=basePath%>/saveHomeIcon.do',
					type: 'POST',
					dataType: 'json',
					data:{"menuStr": menuStr},
					success: function(res) {
					}
				})
				/* console.log(menuStr); */
				top.Dialog.close();
			 }
	</script>
</body>
</html>