<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					<form action="processfilemanagermx/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PROCESSFILEMANAGER_ID" id="PROCESSFILEMANAGER_ID" value="${pd.PROCESSFILEMANAGER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品编码:</td>
 								<td><input type="text" name="PRODUCT_CODING" id="PRODUCT_CODING" value="${pd.PRODUCT_CODING}" maxlength="255" onblur="queryProduct();" placeholder="这里输入产品编码" title="产品编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品名称:</td>
								<td><input type="text" name="PRODUCT_NAME" id="PRODUCT_NAME" readonly="readonly" value="${pd.PRODUCT_NAME}" maxlength="255" placeholder="这里输入产品名称" title="产品名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格型号:</td>
								<td><input type="text" name="PRODUCT_SPECIFICATIONS" id="PRODUCT_SPECIFICATIONS" readonly="readonly" value="${pd.PRODUCT_SPECIFICATIONS}" maxlength="255" placeholder="这里输入规格型号" title="规格型号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#PRODUCT_CODING").val()==""){
				$("#PRODUCT_CODING").tips({
					side:3,
		            msg:'请输入物料编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRODUCT_CODING").focus();
			return false;
			}else{
				var materialCode = $.trim($("#PRODUCT_CODING").val());
 				var PROCESSFILEMANAGER_ID = $.trim($("#PROCESSFILEMANAGER_ID").val());
 				$.ajax({
 					type: "POST",
 					url: '<%=basePath%>product/queryProduct2.do',
 			    	data: {materialCode:materialCode,PROCESSFILEMANAGER_ID:PROCESSFILEMANAGER_ID,tm:new Date().getTime()},
 					//dataType:'json',
 					cache: false,
 					success: function(data){
 						 var processCode = data.processCode;
 						 if("error" == data.result){
 							 bootbox.alert({
					    		  message: '物料编码'+materialCode+' 不存在'
					    	  });
							 $("#PRODUCT_CODING").val('');
 						 }else if("success" == data.result && "no" == data.result2){
							 $('#PRODUCT_NAME').val(data.invName);
							 $('#PRODUCT_SPECIFICATIONS').val(data.invStd);
 						 }else if("yes" == data.result2){
 							bootbox.alert({
 								message: '物料编码'+materialCode+'已经存在,不可重复添加!'
					    	  });
							 $("#PRODUCT_CODING").val('');
	 						 }
 					}
 				});
			}
			if($("#PRODUCT_NAME").val()==""){
				$("#PRODUCT_NAME").tips({
					side:3,
		            msg:'请输入物料名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRODUCT_NAME").focus();
			return false;
			}
			if($("#PRODUCT_SPECIFICATIONS").val()==""){
				$("#PRODUCT_SPECIFICATIONS").tips({
					side:3,
		            msg:'请输入规格型号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRODUCT_SPECIFICATIONS").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		
		//根据物料编码查找产品
 		function queryProduct(){
 				var materialCode = $.trim($("#PRODUCT_CODING").val());
 				var PROCESSFILEMANAGER_ID = $.trim($("#PROCESSFILEMANAGER_ID").val());
 				$.ajax({
 					type: "POST",
 					url: '<%=basePath%>product/queryProduct2.do',
 			    	data: {materialCode:materialCode,PROCESSFILEMANAGER_ID:PROCESSFILEMANAGER_ID,tm:new Date().getTime()},
 					//dataType:'json',
 					cache: false,
 					success: function(data){
 						 var processCode = data.processCode;
 						 if("error" == data.result){
 							 bootbox.alert({
					    		  message: '物料编码'+materialCode+' 不存在'
					    	  });
							 $("#PRODUCT_CODING").val('');
 						 }else if("success" == data.result && "no" == data.result2){
							 $('#PRODUCT_NAME').val(data.invName);
							 $('#PRODUCT_SPECIFICATIONS').val(data.invStd);
 						 }else if("yes" == data.result2){
 							bootbox.alert({
 								message: '物料编码'+materialCode+'已经存在,不可重复添加!'
					    	  });
							 $("#PRODUCT_CODING").val('');
	 						 }
 					}
 				});
 			}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>