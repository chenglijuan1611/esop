<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	out.clear();
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
					
					<form action="processfilemanager/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PROCESSFILEMANAGER_ID" id="PROCESSFILEMANAGER_ID" value="${pd.PROCESSFILEMANAGER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">工艺编码:</td>
								<c:if test="${msg == 'save' }">
									<td><input type="text" name="PROCESS_CODING" id="PROCESS_CODING"
										value="GY<%=new SimpleDateFormat("yyMMddHHMMss").format(Calendar.getInstance().getTime()) %>"
										maxlength="40" placeholder="这里输入工艺编码" title="工艺编码" readonly="readonly"
										style="width: 98%;" />
									</td>
								</c:if>
								<c:if test="${msg == 'edit' }">
									<td><input class="span10" name="PROCESS_CODING"
										id="PROCESS_CODING" value="${pd.PROCESS_CODING}" type="text"
										data-date-format="yyyy-mm-dd" placeholder="工艺编码" readonly="readonly"
										title="工艺编码" style="width: 98%;" />
									</td>
								</c:if>
								
								
<%-- 								<td><input type="text" name="PROCESS_CODING" id="PROCESS_CODING" value="${pd.PROCESS_CODING}" maxlength="255" placeholder="这里输入工艺编码" onblur="hasCode()" title="工艺编码" style="width:98%;"/></td> --%>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">工艺名称:</td>
								<td><input type="text" name="PROCESS_NAME" id="PROCESS_NAME" value="${pd.PROCESS_NAME}" maxlength="255" placeholder="这里输入工艺名称" title="工艺名称" style="width:98%;"/></td>
							</tr>
							
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格型号:</td>
								<td>
									<select class="chosen-select form-control" name="PRODUCT_CODING" id="PRODUCT_CODING" data-placeholder="请选择产品规格型号" style="vertical-align:top;" style="width:98%;" >
										<option value=""> </option>
										<c:forEach items="${productList}" var="product">
											<option value="${product.PRODUCT_CODE }" <c:if test="${product.PRODUCT_SPECIFICATIONS == pd.PRODUCT_SPECIFICATIONS }">selected</c:if>>${product.PRODUCT_SPECIFICATIONS}</option>
										</c:forEach>
									</select>
								</td>
							</tr> --%>
							
							  
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
								<td style="width:75px;text-align: right;padding-top: 13px;">操作时间:</td>
								<c:if test="${msg == 'save' }">
									<td><input type="text" name="OPERATION_TIME" id="OPERATION_TIME"
										value="<%=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) %>"
										maxlength="40" placeholder="这里输入操作时间" title="操作时间"
										style="width: 98%;" /></td>
								</c:if>
								<c:if test="${msg == 'edit' }">
									<td><input class="span10" name="OPERATION_TIME"
										id="OPERATION_TIME" value="${pd.OPERATION_TIME}" type="text"
										data-date-format="yyyy-mm-dd" placeholder="操作时间"
										title="操作时间" style="width: 98%;" />
									</td>
								</c:if>
<%-- 							<td><input type="text" name="OPERATION_TIME" id="OPERATION_TIME" value="${pd.OPERATION_TIME}" maxlength="255" placeholder="这里输入操作时间" title="操作时间" style="width:98%;"/></td> --%>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">操作人:</td>
								<c:if test="${msg == 'save' }">
									<td>
										<input type="text" name="OPERATION_PERSON" id="OPERATION_PERSON" value="${pd.name}" maxlength="40" placeholder="这里输入操作人" title="操作人" style="width: 98%;" />
									</td>
								</c:if>
								<c:if test="${msg == 'edit' }">
									<td>
										<input class="span10" name="OPERATION_PERSON" id="OPERATION_PERSON" value="${pd.OPERATION_PERSON}" type="text" placeholder="操作人" title="操作人" style="width: 98%;" />
									</td>
								</c:if>
								<!-- ${pd.SYSNAME} -->
<%-- 								<td><input type="text" name="OPERATION_PERSON" id="OPERATION_PERSON" value="${pd.OPERATION_PERSON}" maxlength="255" placeholder="这里输入操作人" title="操作人" style="width:98%;"/></td> --%>
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
	<script src="static/ace/js/bootbox.js"></script>
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
			if($("#PROCESS_CODING").val()==""){
				$("#PROCESS_CODING").tips({
					side:3,
		            msg:'请输入工艺编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROCESS_CODING").focus();
			return false;
			}
			if($("#PROCESS_NAME").val()==""){
				$("#PROCESS_NAME").tips({
					side:3,
		            msg:'请输入工艺名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROCESS_NAME").focus();
			return false;
			}
			if($("#PRODUCT_CODING").val()==""){
				$("#PRODUCT_CODING").tips({
					side:3,
		            msg:'请输入产品编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRODUCT_CODING").focus();
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
			if($("#OPERATION_TIME").val()==""){
				$("#OPERATION_TIME").tips({
					side:3,
		            msg:'请输入操作时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OPERATION_TIME").focus();
			return false;
			}
			if($("#OPERATION_PERSON").val()==""){
				$("#OPERATION_PERSON").tips({
					side:3,
		            msg:'请输入操作人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OPERATION_PERSON").focus();
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
		
		
		//根据物料编码查找产品
 		function queryProduct(){
 				var materialCode = $.trim($("#PRODUCT_CODING").val());
 				//alert(materialCode);
 				$.ajax({
 					type: "POST",
 					url: '<%=basePath%>product/queryProduct.do',
 			    	data: {materialCode:materialCode,tm:new Date().getTime()},
 					//dataType:'json',
 					cache: false,
 					success: function(data){
 						 var processCode = data.processCode;
 						 if("success" != data.result){
 							 
 							 bootbox.alert({
					    		  message: '物料编码'+materialCode+' 不存在'
					    	  });
							 $("#PRODUCT_CODING").val('');
 						 }else if("success" == data.result && "no" == data.result2){
							 $('#PRODUCT_NAME').val(data.invName);
							 $('#PRODUCT_SPECIFICATIONS').val(data.invStd);
 						 }else if("yes" == data.result2){
 							bootbox.alert({
					    		  message: '物料编码'+materialCode+'在工艺'+processCode+'下已经存在,不可重复添加!'
					    	  });
							 $("#PRODUCT_CODING").val('');
	 						 }
 					}
 				});
 			}
		
		
		//判断PROCESS_CODE是否存在
		function hasCode(){
			var PROCESS_CODING = $.trim($("#PROCESS_CODING").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>processfilemanager/hasCode.do',
				data: {PROCESS_CODING:PROCESS_CODING,tm:new Date().getTime()},
				/*data:{"NAME":name,tm:new Date().getTime()}, */
				dataType:'json',
				cache: false,
				success: function(data){
					  if("success" != data){
						 $("#PROCESS_CODING").tips({
								side:3,
					            msg:'工艺编码'+PROCESS_CODING+'已存在',
					            bg:'#AE81FF',
					            time:1
					        });
						 $("#PROCESS_CODING").val('');
					 } 
				}
			});
		}
		$(function() {
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': '100%'});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
		});
		</script>
</body>
</html>