<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
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
					
					<form action="processfilemanagermxmx/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PROCESSFILEMANAGERMXMX_ID" id="PROCESSFILEMANAGERMXMX_ID" value="${pd.PROCESSFILEMANAGERMXMX_ID}"/>
						<input type="hidden" name="PROCESSFILEMANAGERMX_ID" id="PROCESSFILEMANAGERMX_ID" value="${pd.PROCESSFILEMANAGERMX_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">工位名称:</td>
								<td><input type="text" name="STATION_NAME" id="STATION_NAME" value="${pd.STATION_NAME}" maxlength="255" placeholder="这里输入工位名称" title="工位名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属分类:</td>
								<td>
									<input type="text" name="PAGE" id="PAGE" value="${pd.CLASSIFICATION}" maxlength="255" placeholder="这里输入对应分类" title="对应分类" style="width:98%;"/>
								</td>
								
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">对应页码:</td>
								<td><input type="text" name="PAGE" id="PAGE" value="${pd.PAGE}" maxlength="255" placeholder="这里输入对应页码" title="对应页码" style="width:98%;"/></td>
								<td><button id="lookf" class="lookf"></button></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">包装前必检工序:</td>
								<td><input type="text" name="PACKAGE_CHECK" id="PACKAGE_CHECK" value="${pd.PACKAGE_CHECK}" maxlength="255" placeholder="这里输入包装前必检工序" title="包装前必检工序" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">喷胶、灌胶标志:</td>
								<td><input type="text" name="GLUE_SYMBOL" id="GLUE_SYMBOL" value="${pd.GLUE_SYMBOL}" maxlength="255" placeholder="这里输入喷胶、灌胶标志" title="喷胶、灌胶标志" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">终端:</td>
								<td><input type="text" name="TERMINAL_EQUIPMENT" id="TERMINAL_EQUIPMENT" value="${pd.TERMINAL_EQUIPMENT}" maxlength="255" placeholder="这里输入终端" title="终端" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">对应工艺文件:</td>
								<td><input type="text" name="PROCESS_FILE" id="PROCESS_FILE" value="${pd.PROCESS_FILE}" maxlength="255" placeholder="这里输入对应工艺文件" title="对应工艺文件" style="width:98%;"/></td>
							
							</tr>
							<tr>
							<td><input type="hidden" name="LINE_NAME" value="${pd.LINE_CODE}"></td>
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
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#STATION_NAME").val()==""){
				$("#STATION_NAME").tips({
					side:3,
		            msg:'请输入工位名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATION_NAME").focus();
			return false;
			}
			if($("#CLASSIFICATION").val()==""){
				$("#CLASSIFICATION").tips({
					side:3,
		            msg:'请输入所属分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CLASSIFICATION").focus();
			return false;
			}
			if($("#PAGE").val()==""){
				$("#PAGE").tips({
					side:3,
		            msg:'请输入对应页码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PAGE").focus();
			return false;
			}
			if($("#PACKAGE_CHECK").val()==""){
				$("#PACKAGE_CHECK").tips({
					side:3,
		            msg:'请输入包装前必检工序',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PACKAGE_CHECK").focus();
			return false;
			}
			if($("#GLUE_SYMBOL").val()==""){
				$("#GLUE_SYMBOL").tips({
					side:3,
		            msg:'请输入喷胶、灌胶标志',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GLUE_SYMBOL").focus();
			return false;
			}
			if($("#TERMINAL_EQUIPMENT").val()==""){
				$("#TERMINAL_EQUIPMENT").tips({
					side:3,
		            msg:'请输入终端',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TERMINAL_EQUIPMENT").focus();
			return false;
			}
			if($("#PROCESS_FILE").val()==""){
				$("#PROCESS_FILE").tips({
					side:3,
		            msg:'请输入对应工艺文件',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROCESS_FILE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>