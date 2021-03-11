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

							<form action="departstaff/${msg }.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="DEPARTSTAFF_ID" id="DEPARTSTAFF_ID"
									value="${pd.DEPARTSTAFF_ID}" /> <input type="hidden"
									name="PARENT_ID" id="PARENT_ID"
									value="${null == pd.PARENT_ID ? DEPARTSTAFF_ID:pd.PARENT_ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">上级:</td>
											<td>
												<div
													class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
													<b>${null == pds.NAME ?'(无) 此为顶级':pds.NAME}</b>
												</div>
											</td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">名称:</td>
											<td><input type="text" name="NAME" id="NAME"
												value="${pd.NAME}" placeholder="这里输入名称" title="名称"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">员工编号:</td>
											<td><input type="text" name="STAFF_ID" id="STAFF_ID"
												value="${pd.STAFF_ID}" maxlength="100"
												placeholder="这里输入员工编号" title="员工编号" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">员工姓名:</td>
											<td><input type="text" name="STAFF_NAME" id="STAFF_NAME"
												value="${pd.STAFF_NAME}" maxlength="50"
												placeholder="这里输入员工姓名" title="员工姓名" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">英文名字:</td>
											<td><input type="text" name="STAFF_NAME_EN"
												id="STAFF_NAME_EN" value="${pd.STAFF_NAME_EN}"
												maxlength="50" placeholder="这里输入英文名字" title="英文名字"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">员工编码:</td>
											<td><input type="text" name="STAFF_BIANMA"
												id="STAFF_BIANMA" value="${pd.STAFF_BIANMA}" maxlength="100"
												placeholder="这里输入员工编码" title="员工编码" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">员工职能:</td>
											<td><input type="text" name="STAFF_FUNCTIONS"
												id="STAFF_FUNCTIONS" value="${pd.STAFF_FUNCTIONS}"
												maxlength="255" placeholder="这里输入员工职能" title="员工职能"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">员工电话:</td>
											<td><input type="text" name="STAFF_TEL" id="STAFF_TEL"
												value="${pd.STAFF_TEL}" maxlength="20"
												placeholder="这里输入员工电话" title="员工电话" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">员工邮箱:</td>
											<td><input type="text" name="STAFF_EMAIL"
												id="STAFF_EMAIL" value="${pd.STAFF_EMAIL}" maxlength="50"
												placeholder="这里输入员工邮箱" title="员工邮箱" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">性别:</td>
											<td><input type="text" name="STAFF_SEX" id="STAFF_SEX"
												value="${pd.STAFF_SEX}" maxlength="10" placeholder="这里输入性别"
												title="性别" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">员工生日:</td>
											<td><input type="text" name="STAFF_BIRTHDAY"
												id="STAFF_BIRTHDAY" value="${pd.STAFF_BIRTHDAY}"
												maxlength="32" placeholder="这里输入员工生日" title="员工生日"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">民族:</td>
											<td><input type="text" name="STAFF_NATION"
												id="STAFF_NATION" value="${pd.STAFF_NATION}" maxlength="10"
												placeholder="这里输入民族" title="民族" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">工作类别:</td>
											<td><input type="text" name="STAFF_JOBTYPE"
												id="STAFF_JOBTYPE" value="${pd.STAFF_JOBTYPE}"
												maxlength="30" placeholder="这里输入工作类别" title="工作类别"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">入职时间:</td>
											<td><input type="text" name="STAFF_JOBJOINTIME"
												id="STAFF_JOBJOINTIME" value="${pd.STAFF_JOBJOINTIME}"
												maxlength="32" placeholder="这里输入入职时间" title="入职时间"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">籍贯:</td>
											<td><input type="text" name="STAFF_FADDRESS"
												id="STAFF_FADDRESS" value="${pd.STAFF_FADDRESS}"
												maxlength="100" placeholder="这里输入籍贯" title="籍贯"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">政治面貌:</td>
											<td><input type="text" name="STAFF_POLITICAL"
												id="STAFF_POLITICAL" value="${pd.STAFF_POLITICAL}"
												maxlength="30" placeholder="这里输入政治面貌" title="政治面貌"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">工作时间:</td>
											<td><input type="text" name="STAFF_PJOINTIME"
												id="STAFF_PJOINTIME" value="${pd.STAFF_PJOINTIME}"
												maxlength="32" placeholder="这里输入工作时间" title="工作时间"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">SFID:</td>
											<td><input type="text" name="STAFF_SFID" id="STAFF_SFID"
												value="${pd.STAFF_SFID}" maxlength="20"
												placeholder="这里输入SFID" title="SFID" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">婚否:</td>
											<td><input type="text" name="STAFF_MARITAL"
												id="STAFF_MARITAL" value="${pd.STAFF_MARITAL}"
												maxlength="10" placeholder="这里输入婚否" title="婚否"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">入职时间:</td>
											<td><input type="text" name="STAFF_DJOINTIME"
												id="STAFF_DJOINTIME" value="${pd.STAFF_DJOINTIME}"
												maxlength="32" placeholder="这里输入入职时间" title="入职时间"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">职位:</td>
											<td><input type="text" name="STAFF_POST" id="STAFF_POST"
												value="${pd.STAFF_POST}" maxlength="30" placeholder="这里输入职位"
												title="职位" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">上岗时间:</td>
											<td><input type="text" name="STAFF_POJOINTIME"
												id="STAFF_POJOINTIME" value="${pd.STAFF_POJOINTIME}"
												maxlength="32" placeholder="这里输入上岗时间" title="上岗时间"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">最高学历:</td>
											<td><input type="text" name="STAFF_EDUCATION"
												id="STAFF_EDUCATION" value="${pd.STAFF_EDUCATION}"
												maxlength="10" placeholder="这里输入最高学历" title="最高学历"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">毕业学校:</td>
											<td><input type="text" name="STAFF_SCHOOL"
												id="STAFF_SCHOOL" value="${pd.STAFF_SCHOOL}" maxlength="30"
												placeholder="这里输入毕业学校" title="毕业学校" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">专业:</td>
											<td><input type="text" name="STAFF_MAJOR"
												id="STAFF_MAJOR" value="${pd.STAFF_MAJOR}" maxlength="30"
												placeholder="这里输入专业" title="专业" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">职业职称:</td>
											<td><input type="text" name="STAFF_FTITLE"
												id="STAFF_FTITLE" value="${pd.STAFF_FTITLE}" maxlength="30"
												placeholder="这里输入职业职称" title="职业职称" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">资格证书:</td>
											<td><input type="text" name="STAFF_CERTIFICATE"
												id="STAFF_CERTIFICATE" value="${pd.STAFF_CERTIFICATE}"
												maxlength="30" placeholder="这里输入资格证书" title="资格证书"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">合同时长:</td>
											<td><input type="number" name="STAFF_CONTRACTLENGTH"
												id="STAFF_CONTRACTLENGTH" value="${pd.STAFF_CONTRACTLENGTH}"
												maxlength="32" placeholder="这里输入合同时长" title="合同时长"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">签订日期:</td>
											<td><input type="text" name="STAFF_CSTARTTIME"
												id="STAFF_CSTARTTIME" value="${pd.STAFF_CSTARTTIME}"
												maxlength="32" placeholder="这里输入签订日期" title="签订日期"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">终止日期:</td>
											<td><input type="text" name="STAFF_CENDTIME"
												id="STAFF_CENDTIME" value="${pd.STAFF_CENDTIME}"
												maxlength="32" placeholder="这里输入终止日期" title="终止日期"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">现住址:</td>
											<td><input type="text" name="STAFF_ADDRESS"
												id="STAFF_ADDRESS" value="${pd.STAFF_ADDRESS}"
												maxlength="100" placeholder="这里输入现住址" title="现住址"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">部门名称:</td>
											<td><input type="text" name="DEPART_NAME"
												id="DEPART_NAME" value="${pd.DEPART_NAME}" maxlength="255"
												placeholder="这里输入部门名称" title="部门名称" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">英文名称:</td>
											<td><input type="text" name="DEPART_NAME_EN"
												id="DEPART_NAME_EN" value="${pd.DEPART_NAME_EN}"
												maxlength="255" placeholder="这里输入英文名称" title="英文名称"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">部门编号:</td>
											<td><input type="text" name="DEPART_BIANMA"
												id="DEPART_BIANMA" value="${pd.DEPART_BIANMA}"
												maxlength="255" placeholder="这里输入部门编号" title="部门编号"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">负责人:</td>
											<td><input type="text" name="DEPART_HEADMAN"
												id="DEPART_HEADMAN" value="${pd.DEPART_HEADMAN}"
												maxlength="255" placeholder="这里输入负责人" title="负责人"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">部门地址:</td>
											<td><input type="text" name="DEPART_ADDRESS"
												id="DEPART_ADDRESS" value="${pd.DEPART_ADDRESS}"
												maxlength="255" placeholder="这里输入部门地址" title="部门地址"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">部门职能:</td>
											<td><input type="text" name="DEPART_FUNCTIONS"
												id="DEPART_FUNCTIONS" value="${pd.DEPART_FUNCTIONS}"
												maxlength="255" placeholder="这里输入部门职能" title="部门职能"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">成立日期:</td>
											<td><input type="text" name="DEPART_STARTTIME"
												id="DEPART_STARTTIME" value="${pd.DEPART_STARTTIME}"
												maxlength="255" placeholder="这里输入成立日期" title="成立日期"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10"><a
												class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
												class="btn btn-mini btn-danger"
												onclick="top.Dialog.close();">取消</a></td>
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
			if($("#STAFF_ID").val()==""){
				$("#STAFF_ID").tips({
					side:3,
		            msg:'请输入员工编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_ID").focus();
			return false;
			}
			if($("#STAFF_NAME").val()==""){
				$("#STAFF_NAME").tips({
					side:3,
		            msg:'请输入员工姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_NAME").focus();
			return false;
			}
			if($("#STAFF_NAME_EN").val()==""){
				$("#STAFF_NAME_EN").tips({
					side:3,
		            msg:'请输入英文名字',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_NAME_EN").focus();
			return false;
			}
			if($("#STAFF_BIANMA").val()==""){
				$("#STAFF_BIANMA").tips({
					side:3,
		            msg:'请输入员工编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_BIANMA").focus();
			return false;
			}
			if($("#STAFF_FUNCTIONS").val()==""){
				$("#STAFF_FUNCTIONS").tips({
					side:3,
		            msg:'请输入员工职能',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_FUNCTIONS").focus();
			return false;
			}
			if($("#STAFF_TEL").val()==""){
				$("#STAFF_TEL").tips({
					side:3,
		            msg:'请输入员工电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_TEL").focus();
			return false;
			}
			if($("#STAFF_EMAIL").val()==""){
				$("#STAFF_EMAIL").tips({
					side:3,
		            msg:'请输入员工邮箱',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_EMAIL").focus();
			return false;
			}
			if($("#STAFF_SEX").val()==""){
				$("#STAFF_SEX").tips({
					side:3,
		            msg:'请输入性别',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_SEX").focus();
			return false;
			}
			if($("#STAFF_BIRTHDAY").val()==""){
				$("#STAFF_BIRTHDAY").tips({
					side:3,
		            msg:'请输入员工生日',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_BIRTHDAY").focus();
			return false;
			}
			if($("#STAFF_NATION").val()==""){
				$("#STAFF_NATION").tips({
					side:3,
		            msg:'请输入民族',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_NATION").focus();
			return false;
			}
			if($("#STAFF_JOBTYPE").val()==""){
				$("#STAFF_JOBTYPE").tips({
					side:3,
		            msg:'请输入工作类别',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_JOBTYPE").focus();
			return false;
			}
			if($("#STAFF_JOBJOINTIME").val()==""){
				$("#STAFF_JOBJOINTIME").tips({
					side:3,
		            msg:'请输入入职时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_JOBJOINTIME").focus();
			return false;
			}
			if($("#STAFF_FADDRESS").val()==""){
				$("#STAFF_FADDRESS").tips({
					side:3,
		            msg:'请输入籍贯',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_FADDRESS").focus();
			return false;
			}
			if($("#STAFF_POLITICAL").val()==""){
				$("#STAFF_POLITICAL").tips({
					side:3,
		            msg:'请输入政治面貌',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_POLITICAL").focus();
			return false;
			}
			if($("#STAFF_PJOINTIME").val()==""){
				$("#STAFF_PJOINTIME").tips({
					side:3,
		            msg:'请输入工作时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_PJOINTIME").focus();
			return false;
			}
			if($("#STAFF_SFID").val()==""){
				$("#STAFF_SFID").tips({
					side:3,
		            msg:'请输入SFID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_SFID").focus();
			return false;
			}
			if($("#STAFF_MARITAL").val()==""){
				$("#STAFF_MARITAL").tips({
					side:3,
		            msg:'请输入婚否',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_MARITAL").focus();
			return false;
			}
			if($("#STAFF_DJOINTIME").val()==""){
				$("#STAFF_DJOINTIME").tips({
					side:3,
		            msg:'请输入入职时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_DJOINTIME").focus();
			return false;
			}
			if($("#STAFF_POST").val()==""){
				$("#STAFF_POST").tips({
					side:3,
		            msg:'请输入职位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_POST").focus();
			return false;
			}
			if($("#STAFF_POJOINTIME").val()==""){
				$("#STAFF_POJOINTIME").tips({
					side:3,
		            msg:'请输入上岗时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_POJOINTIME").focus();
			return false;
			}
			if($("#STAFF_EDUCATION").val()==""){
				$("#STAFF_EDUCATION").tips({
					side:3,
		            msg:'请输入最高学历',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_EDUCATION").focus();
			return false;
			}
			if($("#STAFF_SCHOOL").val()==""){
				$("#STAFF_SCHOOL").tips({
					side:3,
		            msg:'请输入毕业学校',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_SCHOOL").focus();
			return false;
			}
			if($("#STAFF_MAJOR").val()==""){
				$("#STAFF_MAJOR").tips({
					side:3,
		            msg:'请输入专业',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_MAJOR").focus();
			return false;
			}
			if($("#STAFF_FTITLE").val()==""){
				$("#STAFF_FTITLE").tips({
					side:3,
		            msg:'请输入职业职称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_FTITLE").focus();
			return false;
			}
			if($("#STAFF_CERTIFICATE").val()==""){
				$("#STAFF_CERTIFICATE").tips({
					side:3,
		            msg:'请输入资格证书',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_CERTIFICATE").focus();
			return false;
			}
			if($("#STAFF_CONTRACTLENGTH").val()==""){
				$("#STAFF_CONTRACTLENGTH").tips({
					side:3,
		            msg:'请输入合同时长',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_CONTRACTLENGTH").focus();
			return false;
			}
			if($("#STAFF_CSTARTTIME").val()==""){
				$("#STAFF_CSTARTTIME").tips({
					side:3,
		            msg:'请输入签订日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_CSTARTTIME").focus();
			return false;
			}
			if($("#STAFF_CENDTIME").val()==""){
				$("#STAFF_CENDTIME").tips({
					side:3,
		            msg:'请输入终止日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_CENDTIME").focus();
			return false;
			}
			if($("#STAFF_ADDRESS").val()==""){
				$("#STAFF_ADDRESS").tips({
					side:3,
		            msg:'请输入现住址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_ADDRESS").focus();
			return false;
			}
			if($("#DEPART_NAME").val()==""){
				$("#DEPART_NAME").tips({
					side:3,
		            msg:'请输入部门名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_NAME").focus();
			return false;
			}
			if($("#DEPART_NAME_EN").val()==""){
				$("#DEPART_NAME_EN").tips({
					side:3,
		            msg:'请输入英文名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_NAME_EN").focus();
			return false;
			}
			if($("#DEPART_BIANMA").val()==""){
				$("#DEPART_BIANMA").tips({
					side:3,
		            msg:'请输入部门编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_BIANMA").focus();
			return false;
			}
			if($("#DEPART_HEADMAN").val()==""){
				$("#DEPART_HEADMAN").tips({
					side:3,
		            msg:'请输入负责人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_HEADMAN").focus();
			return false;
			}
			if($("#DEPART_ADDRESS").val()==""){
				$("#DEPART_ADDRESS").tips({
					side:3,
		            msg:'请输入部门地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_ADDRESS").focus();
			return false;
			}
			if($("#DEPART_FUNCTIONS").val()==""){
				$("#DEPART_FUNCTIONS").tips({
					side:3,
		            msg:'请输入部门职能',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_FUNCTIONS").focus();
			return false;
			}
			if($("#DEPART_STARTTIME").val()==""){
				$("#DEPART_STARTTIME").tips({
					side:3,
		            msg:'请输入成立日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DEPART_STARTTIME").focus();
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