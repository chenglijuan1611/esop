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
					
					<form action="modelmx/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="MODELMX_ID" id="MODELMX_ID" value="${pd.MODELMX_ID}"/>
						<input type="hidden" name="MODEL_ID" id="MODEL_ID" value="${pd.MODEL_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">一级检测项:</td>
								<td><input type="text" name="PROCEDURE1" id="PROCEDURE1" value="${pd.PROCEDURE1}" maxlength="255" placeholder="这里输入一级检测项" title="一级检测项" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">二级检测项:</td>
								<td><input type="text" name="PROCEDURE2" id="PROCEDURE2" value="${pd.PROCEDURE2}" maxlength="255" placeholder="这里输入二级检测项" title="二级检测项" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准值:</td>
								<td><input type="text" name="STANDARDVALUE" id="STANDARDVALUE" value="${pd.STANDARDVALUE}" maxlength="255" placeholder="这里输入标准值" title="标准值" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">抽检方案:</td>
								<td><input type="text" name="SAMPLING" id="SAMPLING" value="${pd.SAMPLING}" maxlength="255" placeholder="这里输入抽检方案" title="抽检方案" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">检验数量:</td>
								<td><input type="text" name="QUANTITY" id="QUANTITY" value="${pd.QUANTITY}" maxlength="255" placeholder="这里输入检验数量" title="检验数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">实际值:</td>
								<td><input type="text" name="MEASUREDVALUE" id="MEASUREDVALUE" value="${pd.MEASUREDVALUE}" maxlength="255" placeholder="这里输入实际值" title="实际值" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">检验类型:</td>
								<td><input type="text" name="TESTTYPE" id="TESTTYPE" value="${pd.TESTTYPE}" maxlength="255" placeholder="这里输入检验类型" title="检验类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">检验工具:</td>
								<td><input type="text" name="TOOL" id="TOOL" value="${pd.TOOL}" maxlength="255" placeholder="这里输入检验工具" title="检验工具" style="width:98%;"/></td>
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
			if($("#PROCEDURE1").val()==""){
				$("#PROCEDURE1").tips({
					side:3,
		            msg:'请输入一级检测项',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROCEDURE1").focus();
			return false;
			}
			if($("#PROCEDURE2").val()==""){
				$("#PROCEDURE2").tips({
					side:3,
		            msg:'请输入二级检测项',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROCEDURE2").focus();
			return false;
			}
			if($("#STANDARDVALUE").val()==""){
				$("#STANDARDVALUE").tips({
					side:3,
		            msg:'请输入标准值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STANDARDVALUE").focus();
			return false;
			}
			if($("#SAMPLING").val()==""){
				$("#SAMPLING").tips({
					side:3,
		            msg:'请输入抽检方案',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SAMPLING").focus();
			return false;
			}
			if($("#QUANTITY").val()==""){
				$("#QUANTITY").tips({
					side:3,
		            msg:'请输入检验数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#QUANTITY").focus();
			return false;
			}
			if($("#MEASUREDVALUE").val()==""){
				$("#MEASUREDVALUE").tips({
					side:3,
		            msg:'请输入实际值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MEASUREDVALUE").focus();
			return false;
			}
			if($("#TESTTYPE").val()==""){
				$("#TESTTYPE").tips({
					side:3,
		            msg:'请输入检验类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TESTTYPE").focus();
			return false;
			}
			if($("#TOOL").val()==""){
				$("#TOOL").tips({
					side:3,
		            msg:'请输入检验工具',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TOOL").focus();
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