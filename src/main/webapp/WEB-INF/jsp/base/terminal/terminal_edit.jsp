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
					
					<form action="terminal/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="TERMINAL_ID" id="TERMINAL_ID" value="${pd.TERMINAL_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">终端编码:</td>
								<td><input type="text" name="TERMINAL_CODE" id="TERMINAL_CODE" value="${pd.TERMINAL_CODE}" maxlength="255" onblur="hasCode()" placeholder="这里输入终端编码" title="终端编码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">终端名称:</td>
								<td><input type="text" name="TERMINAL_NAME" id="TERMINAL_NAME" value="${pd.TERMINAL_NAME}" maxlength="255" onblur="hasName()" placeholder="这里输入终端名称" title="终端名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">位置:</td>
								<td><input type="text" name="LOCATION" id="LOCATION" value="${pd.LOCATION}" maxlength="255" placeholder="这里输入位置" title="位置" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">部署产线:</td>
<%-- 							<td><input type="text" name="DEPLOY_LINE" id="DEPLOY_LINE" value="${pd.DEPLOY_LINE}" maxlength="255" placeholder="这里输入部署产线" title="部署产线" style="width:98%;"/></td> --%>
								<td>
									<select class="chosen-select form-control" name="DEPLOY_LINE" id="DEPLOY_LINE" data-placeholder="请选择部署产线"  style="vertical-align: top;" style="width:98%;">
											<option value=""></option> 
											<c:forEach items="${lineList}" var="lineList">
												<option value="${lineList.CODE }" <c:if test="${lineList.CODE == pd.DEPLOY_LINE }">selected</c:if>>${lineList.CODE}</option>
											</c:forEach>
									</select> 
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">终端类型:</td>
<%-- 							<td><input type="text" name="TERMINAL_TYPE" id="TERMINAL_TYPE" value="${pd.TERMINAL_TYPE}" maxlength="255" placeholder="这里输入终端类型" title="终端类型" style="width:98%;"/></td> --%>
								<td>
									<select class="chosen-select form-control" name="TERMINAL_TYPE" id="TERMINAL_TYPE" data-placeholder="请选择终端类型"  style="vertical-align: top;" style="width:98%;">
											<option value=""></option> 
											<c:forEach items="${terminalTypeList}" var="terminalTypeList">
												<option value="${terminalTypeList.TERMINAL_TYPE }" <c:if test="${terminalTypeList.TERMINAL_TYPE == pd.TERMINAL_TYPE }">selected</c:if>>${terminalTypeList.TERMINAL_TYPE}</option>
											</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">注册日期:</td>
								<c:if test="${msg == 'save' }">
									<td><input type="text" name="REGISTER_DATE" id="REGISTER_DATE"
										value="<%=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) %>"
										maxlength="40" placeholder="这里输入注册日期" title="注册日期"
										style="width: 98%;" /></td>
								</c:if>
								<c:if test="${msg == 'edit' }">
									<td><input class="span10" name="REGISTER_DATE"
										id="REGISTER_DATE" value="${pd.REGISTER_DATE}" type="text"
										data-date-format="yyyy-mm-dd" placeholder="注册日期"
										title="注册日期" style="width: 98%;" />
									</td>
								</c:if>
<%-- 							<td><input type="text" name="REGISTER_DATE" id="REGISTER_DATE" value="${pd.REGISTER_DATE}" maxlength="255" placeholder="这里输入注册日期" title="注册日期" style="width:98%;"/></td> --%>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">屏幕宽:</td>
								<c:if test="${msg == 'edit' }">
									<td><input type="text" name="SCREEN_WIDE" id="SCREEN_WIDE" value="${pd.SCREEN_WIDE}" 
										maxlength="255" placeholder="这里输入屏幕宽" title="屏幕宽" style="width:98%;"/>
									</td>
								</c:if>
								<c:if test="${msg == 'save' }">
									<td><input type="text" name="SCREEN_WIDE" id="SCREEN_WIDE" value="1600" 
										maxlength="255" placeholder="这里输入屏幕宽" title="屏幕宽" style="width:98%;"/>
									</td>
								</c:if>
<%-- 								<td><input type="text" name="SCREEN_WIDE" id="SCREEN_WIDE" value="${pd.SCREEN_WIDE}" maxlength="255" placeholder="这里输入屏幕宽" title="屏幕宽" style="width:98%;"/></td> --%>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">屏幕高:</td>
								
								<c:if test="${msg == 'edit' }">
									<td><input type="text" name="SCREEN_HEIGHT" id="SCREEN_HEIGHT" value="${pd.SCREEN_HEIGHT}" 
										maxlength="255" placeholder="这里输入屏幕宽" title="屏幕宽" style="width:98%;"/>
									</td>
								</c:if>
								<c:if test="${msg == 'save' }">
									<td><input type="text" name="SCREEN_HEIGHT" id="SCREEN_HEIGHT" value="800" 
										maxlength="255" placeholder="这里输入屏幕宽" title="屏幕宽" style="width:98%;"/>
									</td>
								</c:if>
<%-- 								<td><input type="text" name="SCREEN_HEIGHT" id="SCREEN_HEIGHT" value="${pd.SCREEN_HEIGHT}" maxlength="255" placeholder="这里输入屏幕高" title="屏幕高" style="width:98%;"/></td> --%>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">显示序号:</td>
								<td><input type="text" name="SHOW_NUM" id="SHOW_NUM" value="${pd.SHOW_NUM}" maxlength="255" onblur="hasNum()" placeholder="这里输入显示序号" title="显示序号" style="width:98%;"/></td>
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
			if($("#TERMINAL_CODE").val()==""){
				$("#TERMINAL_CODE").tips({
					side:3,
		            msg:'请输入终端编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TERMINAL_CODE").focus();
			return false;
			}
			if($("#TERMINAL_NAME").val()==""){
				$("#TERMINAL_NAME").tips({
					side:3,
		            msg:'请输入终端名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TERMINAL_NAME").focus();
			return false;
			}
			if($("#LOCATION").val()==""){
				$("#LOCATION").tips({
					side:3,
		            msg:'请输入位置',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LOCATION").focus();
			return false;
			}
			if($("#DEPLOY_LINE").val()==""){
				$("#DEPLOY_LINE").tips({
					side:3,
		            msg:'请输入部署产线',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPLOY_LINE").focus();
			return false;
			}
			if($("#TERMINAL_TYPE").val()==""){
				$("#TERMINAL_TYPE").tips({
					side:3,
		            msg:'请输入终端类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TERMINAL_TYPE").focus();
			return false;
			}
			if($("#REGISTER_DATE").val()==""){
				$("#REGISTER_DATE").tips({
					side:3,
		            msg:'请输入注册日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#REGISTER_DATE").focus();
			return false;
			}
			if($("#SCREEN_WIDE").val()==""){
				$("#SCREEN_WIDE").tips({
					side:3,
		            msg:'请输入屏幕宽',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SCREEN_WIDE").focus();
			return false;
			}
			if($("#SCREEN_HEIGHT").val()==""){
				$("#SCREEN_HEIGHT").tips({
					side:3,
		            msg:'请输入屏幕高',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SCREEN_HEIGHT").focus();
			return false;
			}
			if($("#SHOW_NUM").val()==""){
				$("#SHOW_NUM").tips({
					side:3,
		            msg:'请输入显示序号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SHOW_NUM").focus();
			return false;
			}
			if($("#FILE_PAGENUM").val()==""){
				$("#FILE_PAGENUM").tips({
					side:3,
		            msg:'请输入显示页码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FILE_PAGENUM").focus();
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
		$(function() {
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
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
		//选择产线之后的触发事件
		$('#DEPLOY_LINE').on('change', function() {
			if ($(this).val()) {
				var lineName = $(this).val();
				$.ajax({
					  url: "<%=basePath%>terminal/lineTrigger.do?PRODUCTLINE_NAME="+lineName,
				      type: "POST",
				      processData: false,
				      contentType: false,
				      success: function(data){
				    	  document.getElementById("SHOW_NUM").value=data
				      },
				})
				
			} 
		})
		
		
		//判断TERMINAL_CODE是否存在
		function hasCode(){
			var TERMINAL_CODE = $.trim($("#TERMINAL_CODE").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>terminal/hasCode.do',
				data: {TERMINAL_CODE:TERMINAL_CODE,tm:new Date().getTime()},
				/*data:{"NAME":name,tm:new Date().getTime()}, */
				dataType:'json',
				cache: false,
				success: function(data){
					  if("success" != data){
						 $("#TERMINAL_CODE").tips({
								side:3,
					            msg:'终端编码'+TERMINAL_CODE+'已存在',
					            bg:'#AE81FF',
					            time:1
					        });
						 $("#TERMINAL_CODE").val('');
					 } 
				}
			});
		}
		
		//判断TERMINAL_NAME是否存在
		//hasName
		function hasName(){
			var TERMINAL_NAME = $.trim($("#TERMINAL_NAME").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>terminal/hasName.do',
				data: {TERMINAL_NAME:TERMINAL_NAME,tm:new Date().getTime()},
				/*data:{"NAME":name,tm:new Date().getTime()}, */
				dataType:'json',
				cache: false,
				success: function(data){
					  if("success" != data){
						 $("#TERMINAL_NAME").tips({
								side:3,
					            msg:'终端编码'+TERMINAL_NAME+'已存在',
					            bg:'#AE81FF',
					            time:1
					        });
						 $("#TERMINAL_NAME").val('');
					 } 
				}
			});
		}
		
		//判断该产线下是否已有该显示序号的终端
		function hasNum(){
			var lineName = $.trim($("#DEPLOY_LINE").val());
			var showNum = $.trim($("#SHOW_NUM").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>terminal/hasNum.do',
				data: {lineName:lineName,showNum:showNum,tm:new Date().getTime()},
				/*data:{"NAME":name,tm:new Date().getTime()}, */
				dataType:'json',
				cache: false,
				success: function(data){
					  if("success" != data){
						 $("#SHOW_NUM").tips({
								side:3,
					            msg:lineName+'下显示序号为'+showNum+'的终端已存在',
					            bg:'#AE81FF',
					            time:1
					        });
						 $("#showNum").val('');
					 } 
				}
			}); 
		}
		</script>
</body>
</html>