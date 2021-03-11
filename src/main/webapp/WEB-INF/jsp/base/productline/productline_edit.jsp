<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<!-- 树形下拉框start -->
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css"
	href="plugins/selectZtree/import_fh.css" />
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet"
	href="plugins/selectZtree/ztree/ztree.css"></link>
<!-- 树形下拉框end -->
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
							<form action="productline/${msg }.do" name="Form" id="Form" method="post">
								<input type="hidden" name="PRODUCTLINE_ID" id="PRODUCTLINE_ID" value="${pd.PRODUCTLINE_ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width: 90px; text-align: right; padding-top: 13px;">生产线名称:</td>
											<td>
												<input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="255" placeholder="这里输入生产线名称" title="生产线名称" onblur="hasNAME()" style="width: 98%;" />
											</td>
										</tr>
										<tr>
								            <td style="width:75px;text-align: right;padding-top: 13px;">生产线编码:</td>
								            <td>
								               <input type="text" name="CODE" id="CODE" value="${pd.CODE}" maxlength="255" placeholder="这里输入生产线编码" title="生产线编码" style="width: 98%;" <c:if test="${msg == 'edit' }">readonly="readonly"</c:if>/>
                                            </td>
							            </tr>
							           
										<tr>
											<td style="width: 90px; text-align: right; padding-top: 13px;">生产线线长:</td>
											<td>
												 <select class="chosen-select form-control" name="BZ" id="BZ"  data-placeholder="这里输入生产线线长" style="vertical-align: top;" style="width:98%;" >
											       <option value=""> </option>
											       <c:forEach items="${userList}" var="user">
											       <option value="${user.USERNAME }" <c:if test="${user.USERNAME == pd.BZ }">selected</c:if>>${user.NAME}</option>
											       </c:forEach>
								               	</select>
											</td>	
										</tr>
										
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a> 
												<input class="btn btn-mini" name="checkaddbox" id="checkaddbox" type="checkbox" />
													连续添加 
												<input type="hidden" id="checkadd" name="checkadd" value="${checkadd }" />
											</td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display: none">
									<br />
									<br />
									<br />
									<br />
									<br />
									<img src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>
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
			if($("#CODE").val()==""){
				$("#CODE").tips({
					side:3,
		            msg:'请输入生产线编码',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#CODE").focus();
			return false;
			}
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入生产线名称',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#NAME").focus();
			return false;
			}
			if($("#CATEGORY").val()==""){
				$("#CATEGORY").tips({
					side:3,
		            msg:'请输入生产线类别',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#CATEGORY").focus();
			return false;
			}else{
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
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
		
		//判断生产线名称是否存在
		function hasNAME(){
			var NAME = $.trim($("#NAME").val());
			//alert(PRODUCT_SPECIFICATIONS);
			//var PRODUCT_ID=jQuery("#PRODUCT_ID  option:selected").val();
			//alert(PRODUCT_ID);
			$.ajax({
				type: "POST",
				url: '<%=basePath%>productline/hasName.do',
		    	data: {NAME:NAME,tm:new Date().getTime()},
				//dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data){
						 //alert(data);
						 $("#NAME").tips({
								side:3,
					            msg:'生产线名称名称'+NAME+' 已存在',
					            bg:'#AE81FF',
					            time:1
					        });
						 $("#NAME").val('');
					 }
				}
			});
		}
		
		//判断登录操作员是否存在
		function hasLiner(){
			var Liner = $.trim($("#Liner").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>productline/hasLiner.do',
		    	data: {Liner:Liner,tm:new Date().getTime()},
				//dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data){
						 //alert(data);
						 $("#NAME").tips({
								side:3,
					            msg:'登录操作员'+Liner+' 已存在',
					            bg:'#AE81FF',
					            time:1
					        });
						 $("#NAME").val('');
					 }
				}
			});
		}
		
		
		//下拉树
// 		var defaultNodes = {"treeNodes":${zTreeNodes}};
// 		console.log(defaultNodes);
// 		function initComplete(){
// 			//绑定change事件
// 			$("#selectTree").bind("change",function(){
// 				if(!$(this).attr("relValue")){
// 			      //  top.Dialog.alert("没有选择节点");
// 			    }else{
// 					//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
// 					$("#DepartmentID").val($(this).attr("relValue"));
// 			    }
// 			});
// 			//赋给data属性
// 			$("#selectTree").data("data",defaultNodes);  
// 			$("#selectTree").render();
// 			$("#selectTree2_input").val("${null==depname?'请选择':depname}");
// 		}
		</script>
</body>
</html>