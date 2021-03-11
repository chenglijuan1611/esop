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
					
					<form action="model/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="MODEL_ID" id="MODEL_ID" value="${pd.MODEL_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<%-- <table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物料名称:</td>
								<td><input type="text" name="MATERIALNAME" id="MATERIALNAME" value="${pd.MATERIALNAME}" maxlength="255" placeholder="这里输入物料名称" title="物料名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">物料编码:</td>
								<td><input type="text" name="MATERIALCODE" id="MATERIALCODE" value="${pd.MATERIALCODE}" maxlength="255" placeholder="这里输入物料编码" title="物料编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格型号:</td>
								<td><input type="text" name="SPECIFICATIONTYPE" id="SPECIFICATIONTYPE" value="${pd.SPECIFICATIONTYPE}" maxlength="255" placeholder="这里输入规格型号" title="规格型号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">供应商名称:</td>
								<td><input type="text" name="VENDORNAME" id="VENDORNAME" value="${pd.VENDORNAME}" maxlength="255" placeholder="这里输入供应商名称" title="供应商名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">抽样标准:</td>
								<td><input type="text" name="  SAMPLINGSTANDARD" id="  SAMPLINGSTANDARD" value="${pd.  SAMPLINGSTANDARD}" maxlength="255" placeholder="这里输入抽样标准" title="抽样标准" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品类别:</td>
								<td><input type="text" name="PRODUCTCATEGORY" id="PRODUCTCATEGORY" value="${pd.PRODUCTCATEGORY}" maxlength="255" placeholder="这里输入产品类别" title="产品类别" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">计量单位:</td>
								<td><input type="text" name="MEASUREMENTUNIT" id="MEASUREMENTUNIT" value="${pd.MEASUREMENTUNIT}" maxlength="255" placeholder="这里输入计量单位" title="计量单位" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">版本号:</td>
								<td><input type="text" name="VERSION" id="VERSION" value="${pd.VERSION}" maxlength="255" placeholder="这里输入版本号" title="版本号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建日期:</td>
								<td><input type="text" name="CREATIONDATE" id="CREATIONDATE" value="${pd.CREATIONDATE}" maxlength="255" placeholder="这里输入创建日期" title="创建日期" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">负责部门:</td>
								<td><input type="text" name="RESPONSIBLEDEPT" id="RESPONSIBLEDEPT" value="${pd.RESPONSIBLEDEPT}" maxlength="255" placeholder="这里输入负责部门" title="负责部门" style="width:98%;"/></td>
							</tr>
						</table> --%>
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

<c:if test="${'edit' == msg }">
	<div>
		<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/modelmx/list.do?MODEL_ID=${pd.MODEL_ID}" style="margin:0 auto;width:100%;height:368px;;"></iframe>
	</div>
</c:if>

<footer>
<div style="width: 100%;padding-bottom: 2px;" class="center">
	<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
	<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
</div>
</footer>

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
			if($("#MATERIALNAME").val()==""){
				$("#MATERIALNAME").tips({
					side:3,
		            msg:'请输入物料名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MATERIALNAME").focus();
			return false;
			}
			if($("#MATERIALCODE").val()==""){
				$("#MATERIALCODE").tips({
					side:3,
		            msg:'请输入物料编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MATERIALCODE").focus();
			return false;
			}
			if($("#SPECIFICATIONTYPE").val()==""){
				$("#SPECIFICATIONTYPE").tips({
					side:3,
		            msg:'请输入规格型号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SPECIFICATIONTYPE").focus();
			return false;
			}
			if($("#VENDORNAME").val()==""){
				$("#VENDORNAME").tips({
					side:3,
		            msg:'请输入供应商名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VENDORNAME").focus();
			return false;
			}
			if($("#  SAMPLINGSTANDARD").val()==""){
				$("#  SAMPLINGSTANDARD").tips({
					side:3,
		            msg:'请输入抽样标准',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#  SAMPLINGSTANDARD").focus();
			return false;
			}
			if($("#PRODUCTCATEGORY").val()==""){
				$("#PRODUCTCATEGORY").tips({
					side:3,
		            msg:'请输入产品类别',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRODUCTCATEGORY").focus();
			return false;
			}
			if($("#MEASUREMENTUNIT").val()==""){
				$("#MEASUREMENTUNIT").tips({
					side:3,
		            msg:'请输入计量单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MEASUREMENTUNIT").focus();
			return false;
			}
			if($("#VERSION").val()==""){
				$("#VERSION").tips({
					side:3,
		            msg:'请输入版本号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VERSION").focus();
			return false;
			}
			if($("#CREATIONDATE").val()==""){
				$("#CREATIONDATE").tips({
					side:3,
		            msg:'请输入创建日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATIONDATE").focus();
			return false;
			}
			if($("#RESPONSIBLEDEPT").val()==""){
				$("#RESPONSIBLEDEPT").tips({
					side:3,
		            msg:'请输入负责部门',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RESPONSIBLEDEPT").focus();
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