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
					
					<form action="modelmxmx/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="MODELMXMX_ID" id="MODELMXMX_ID" value="${pd.MODELMXMX_ID}"/>
						<input type="hidden" name="MODELMX_ID" id="MODELMX_ID" value="${pd.MODELMX_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">名称:</td>
								<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="255" placeholder="这里输入名称" title="名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">Br溴:</td>
								<td><input type="text" name="BR" id="BR" value="${pd.BR}" maxlength="255" placeholder="这里输入Br溴" title="Br溴" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">Cd镉:</td>
								<td><input type="text" name="CD" id="CD" value="${pd.CD}" maxlength="255" placeholder="这里输入Cd镉" title="Cd镉" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">Hg汞:</td>
								<td><input type="text" name="HG" id="HG" value="${pd.HG}" maxlength="255" placeholder="这里输入Hg汞" title="Hg汞" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">Pb铅:</td>
								<td><input type="text" name="PB" id="PB" value="${pd.PB}" maxlength="255" placeholder="这里输入Pb铅" title="Pb铅" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">Cr铬:</td>
								<td><input type="text" name="CR" id="CR" value="${pd.CR}" maxlength="255" placeholder="这里输入Cr铬" title="Cr铬" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">Cl氯:</td>
								<td><input type="text" name="CL" id="CL" value="${pd.CL}" maxlength="255" placeholder="这里输入Cl氯" title="Cl氯" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">Cl+Br 氯+溴:</td>
								<td><input type="text" name="CL+BR" id="CL+BR" value="${pd.CL+BR}" maxlength="255" placeholder="这里输入Cl+Br 氯+溴" title="Cl+Br 氯+溴" style="width:98%;"/></td>
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
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
			return false;
			}
			if($("#BR").val()==""){
				$("#BR").tips({
					side:3,
		            msg:'请输入Br溴',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BR").focus();
			return false;
			}
			if($("#CD").val()==""){
				$("#CD").tips({
					side:3,
		            msg:'请输入Cd镉',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CD").focus();
			return false;
			}
			if($("#HG").val()==""){
				$("#HG").tips({
					side:3,
		            msg:'请输入Hg汞',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#HG").focus();
			return false;
			}
			if($("#PB").val()==""){
				$("#PB").tips({
					side:3,
		            msg:'请输入Pb铅',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PB").focus();
			return false;
			}
			if($("#CR").val()==""){
				$("#CR").tips({
					side:3,
		            msg:'请输入Cr铬',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CR").focus();
			return false;
			}
			if($("#CL").val()==""){
				$("#CL").tips({
					side:3,
		            msg:'请输入Cl氯',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CL").focus();
			return false;
			}
			if($("#CL+BR").val()==""){
				$("#CL+BR").tips({
					side:3,
		            msg:'请输入Cl+Br 氯+溴',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CL+BR").focus();
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