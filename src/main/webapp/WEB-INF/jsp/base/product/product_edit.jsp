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
					
					<form action="product/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品编码:</td>
								<td><input type="text" name="PRODUCT_CODE" id="PRODUCT_CODE" value="${pd.PRODUCT_CODE}" maxlength="50" placeholder="这里输入产品编码" title="产品编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品名称:</td>
								<td><input type="text" name="PRODUCT_NAME" id="PRODUCT_NAME" value="${pd.PRODUCT_NAME}" maxlength="50" placeholder="这里输入产品名称" title="产品名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格型号:</td>
								<td><input type="text" name="PRODUCT_SPECIFICATIONS" id="PRODUCT_SPECIFICATIONS" value="${pd.PRODUCT_SPECIFICATIONS}" maxlength="100" placeholder="这里输入规格型号" title="规格型号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">标准工时:</td>
								<td><input type="text" name="PRODUCT_SWHOURS" id="PRODUCT_SWHOURS" value="${pd.PRODUCT_SWHOURS}" maxlength="20" placeholder="这里输入标准工时" title="标准工时" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">生产线:</td>
							    <td>
							    	<input type="hidden" name="PRODUCTLINE_ID" id="PRODUCTLINE_ID">	
								    	<c:forEach items="${productlineList}" var="productline">
											<input type="checkbox" name="productline" value="${productline.PRODUCTLINE_ID }" 
												<c:forEach items="${productline_ids}" var="ids">
													<c:if test="${productline.PRODUCTLINE_ID == ids }">checked</c:if>
												</c:forEach>
											>${productline.NAME}
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
									<input class="btn btn-mini" name="checkaddbox" id="checkaddbox" type="checkbox"/>连续添加
									<input type="hidden" id="checkadd" name="checkadd" value=""/>
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
		
		$(document).ready(function(){
			if($("#checkadd").val() == "checked"){
				$("#checkaddbox").attr("checked","checked");
			}
		});
		
		//是否连续添加
		$("#checkaddbox").click(function(){
			if($("#checkaddbox").is(":checked")){
				$("#checkadd").val("checked");
			}else{
				$("#checkadd").val("");
			}
		});
		//保存
		function save(){
			var result="";  
	        $("input[name='productline']:checked").each(function(){  
	               result+=$(this).val()+',';  
	        });  
	        if(result!=""){  
	            result=result.substring(0,result.lastIndexOf(','));  
	        }  
	        $("#PRODUCTLINE_ID").val(result); 
			if($("#PRODUCT_CODE").val()==""){
				$("#PRODUCT_CODE").tips({
					side:3,
		            msg:'请输入产品编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRODUCT_CODE").focus();
			return false;
			}
			if($("#PRODUCT_NAME").val()==""){
				$("#PRODUCT_NAME").tips({
					side:3,
		            msg:'请输入产品名称',
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
			if($("#PRODUCT_SWHOURS").val()==""){
				$("#PRODUCT_SWHOURS").tips({
					side:3,
		            msg:'请输入标准工时',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRODUCT_SWHOURS").focus();
			return false;
			}
			
			if($("#PRODUCTLINE_ID").val()==""){
				$("#PRODUCTLINE_ID").tips({
					side:3,
		            msg:'请选择产线',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRODUCTLINE_ID").focus();
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