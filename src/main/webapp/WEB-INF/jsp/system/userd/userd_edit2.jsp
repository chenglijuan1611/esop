<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

							<form action="userd/${msg }.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="USERD_ID" id="USERD_ID"
									value="${pd.USERD_ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<c:if test="${fx != 'head'}">
											<tr>
												<td
													style="width: 79px; text-align: right; padding-top: 13px;">主职角色:</td>
												<td id="juese"><select
													class="chosen-select form-control" name="ROLE_ID"
													id="role_id" data-placeholder="请选择角色"
													style="vertical-align: top;" style="width:98%;">
														<option value=""></option>
														<c:forEach items="${roleList}" var="role">
															<option value="${role.ROLE_ID }"
																<c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
														</c:forEach>
												</select></td>
											</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }"
												type="hidden" />
										</c:if>
										<c:if test="${fx != 'head'}">
											<tr>
												<td
													style="width: 79px; text-align: right; padding-top: 13px;">副职角色:</td>
												<td>
													<div>
														<select multiple="" class="chosen-select form-control"
															id="form-field-select-4" data-placeholder="选择副职角色">
															<c:forEach items="${roleList}" var="role">
																<option onclick="setROLE_IDS('${role.ROLE_ID }')"
																	value="${role.ROLE_ID }"
																	<c:if test="${role.RIGHTS == '1' }">selected</c:if>>${role.ROLE_NAME }</option>
															</c:forEach>
														</select>
													</div>
												</td>
											</tr>
										</c:if>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">用户名:</td>
											<td><input type="text" name="USERNAME" id="loginname"
												value="${pd.USERNAME }" maxlength="32" placeholder="这里输入用户名"
												title="用户名" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">编号:</td>
											<td><input type="text" name="NUMBER" id="NUMBER"
												value="${pd.NUMBER }" maxlength="32" placeholder="这里输入编号"
												title="编号" onblur="hasN('${pd.USERNAME }')"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">密码:</td>
											<td><input type="password" name="PASSWORD" id="password"
												maxlength="32" placeholder="输入密码" title="密码"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">确认密码:</td>
											<td><input type="password" name="chkpwd" id="chkpwd"
												maxlength="32" placeholder="确认密码" title="确认密码"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">姓名:</td>
											<td><input type="text" name="NAME" id="name"
												value="${pd.NAME }" maxlength="32" placeholder="这里输入姓名"
												title="姓名" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">手机号:</td>
											<td><input type="number" name="PHONE" id="PHONE"
												value="${pd.PHONE }" maxlength="32" placeholder="这里输入手机号"
												title="手机号" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">邮箱:</td>
											<td><input type="email" name="EMAIL" id="EMAIL"
												value="${pd.EMAIL }" maxlength="32" placeholder="这里输入邮箱"
												title="邮箱" onblur="hasE('${pd.USERNAME }')"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">备注:</td>
											<td><input type="text" name="BZ" id="BZ"
												value="${pd.BZ }" placeholder="这里输入备注" maxlength="64"
												title="备注" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">部门ID:</td>
											<td><input type="text" name="DEPARTMENT_ID"
												id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID}"
												maxlength="255" placeholder="这里输入部门ID" title="部门ID"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10"><a
												class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
												class="btn btn-mini btn-danger"
												onclick="top.Dialog.close();">取消</a> <input
												class="btn btn-mini" name="checkaddbox" id="checkaddbox"
												type="checkbox" />连续添加 <input type="hidden" id="checkadd"
												name="checkadd" value="${checkadd}" /></td>
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
		//保存
		function save(){
			if($("#USERNAME").val()==""){
				$("#USERNAME").tips({
					side:3,
		            msg:'请输入用户名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USERNAME").focus();
			return false;
			}
			if($("#PASSWORD").val()==""){
				$("#PASSWORD").tips({
					side:3,
		            msg:'请输入密码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PASSWORD").focus();
			return false;
			}
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
			return false;
			}
			if($("#ROLE_ID").val()==""){
				$("#ROLE_ID").tips({
					side:3,
		            msg:'请输入角色id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ROLE_ID").focus();
			return false;
			}
			if($("#ROLE_IDS").val()==""){
				$("#ROLE_IDS").tips({
					side:3,
		            msg:'请输入副职角色id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ROLE_IDS").focus();
			return false;
			}
			if($("#EMAIL").val()==""){
				$("#EMAIL").tips({
					side:3,
		            msg:'请输入邮箱',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#EMAIL").focus();
			return false;
			}
			if($("#PHONE").val()==""){
				$("#PHONE").tips({
					side:3,
		            msg:'请输入电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PHONE").focus();
			return false;
			}
			if($("#BZ").val()==""){
				$("#BZ").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BZ").focus();
			return false;
			}
			if($("#DEPARTMENT_ID").val()==""){
				$("#DEPARTMENT_ID").tips({
					side:3,
		            msg:'请输入部门ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPARTMENT_ID").focus();
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
		//移除副职角色
		function removeRoleId(ROLE_ID){
			var OROLE_IDS = $("#ROLE_IDS");
			var ROLE_IDS = OROLE_IDS.val();
			ROLE_IDS = ROLE_IDS.replace(ROLE_ID+",fh,","");
			OROLE_IDS.val(ROLE_IDS);
		}
		//添加副职角色
		function addRoleId(ROLE_ID){
			var OROLE_IDS = $("#ROLE_IDS");
			var ROLE_IDS = OROLE_IDS.val();
			if(!isContains(ROLE_IDS,ROLE_ID)){
				ROLE_IDS = ROLE_IDS + ROLE_ID + ",fh,";
				OROLE_IDS.val(ROLE_IDS);
			}
		}
		function isContains(str, substr) {
		     return str.indexOf(substr) >= 0;
		 }
		
		</script>
</body>
</html>