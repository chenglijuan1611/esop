<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<!-- ztree引入 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery3.3.1.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/css/bootstrap3.3.7.css" />
<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" />
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.exedit.js"></script>
<style>
	#tree li a span{
		display: inline-block;
		font-size: 14px;
	}
</style>
</head>
<body class="no-skin" style="width:100%; height: 100%; overflow: hidden;">

<table style="width: 100%;" border="0">

		<tr>
			<td style="width: 12%;" valign="top" bgcolor="#F9F9F9">
				<div style="width: 15%;">
					<!-- <ul id="leftTree" class="tree"></ul> -->
					<ul id="tree" class="ztree"></ul>
				</div>
			</td>
			<td style="width: 88%;" valign="top">
				<iframe name="treeFrame"
						id="treeFrame" frameborder="0"
						src="<%=basePath%>/product/listBy.do?listYESOrNO=${listYESOrNO}&PRODUCTLINE_ID=${pd.PRODUCTLINE_ID}"
						style="margin: 0 auto; width: 100%; height: 100%;">
				</iframe>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		$(top.hangge());
	//列表点击事件
		$(function() {
			var _classfication = JSON.parse('${classificationList2}');
			var _productlineList =JSON.parse('${productlineList2}');
			/* console.log(_classfication,_productlineList); */
			//下拉树
			var zTreeObj,
         setting = {
             view: {
                 selectedMulti: false,
                 showIcon: false,
                 fontCss: {
                     color: 'black',
                 }
             },
             edit: {
                 enable: true,
                 showRemoveBtn: false,
                 showRenameBtn: false
             },
             callback: {
             	onClick: zTreeOnclick 
             }, 
             data: {
             	simpleData: {
             		enable: true,
             		idKey: 'id',
             		pIdKey: 'pId'
             	}
             }
         };
			function zTreeOnclick(e, treeId, treeNode) {
				//alert(treeNode.url);
				$("#treeFrame")[0].contentWindow.location.href = treeNode.url;
			}
			
			
			var zTreeNodes = [];
			var treeObj = {};
			treeObj.open = false;
			treeObj.name = '产品分类';
			treeObj.url = "<%=basePath%>product/listBy.do?classifi="+'aa';
			treeObj.children = [];
			$.each(_classfication, function(index, item) {
				var classObj = {};
				classObj.open = false;
				classObj.name = item.CLASSIFICATION_NAME;
				classObj.code = item.CLASSIFICATION_CODE;
				classObj.classfication = item.CLASSIFICATION_ID;
				classObj.target = '#treeFrame';
				classObj.url = "<%=basePath%>product/listBy.do?CLASSIFICATION_CODE="+item.CLASSIFICATION_CODE;
				classObj.children = [];
				treeObj.children.push(classObj);
				
			})
		  zTreeNodes.push(treeObj);
			console.log(zTreeNodes);
         zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
        
		})
	 
	 
	 
		function treeFrameT(){
			var hmainT = document.getElementById("treeFrame");
			var bheightT = document.documentElement.clientHeight;
			hmainT .style.width = '100%';
			hmainT .style.height = (bheightT-26) + 'px';
		}
		treeFrameT();
		window.onresize=function(){  
			treeFrameT();
		};
</SCRIPT>
</body>
</html>