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

							<form action="attached/${msg }.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="ATTACHED_ID" id="ATTACHED_ID"
									value="${pd.ATTACHED_ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">名称:</td>
											<td><input type="text" name="NAME" id="NAME"
												value="${pd.NAME}" maxlength="255" placeholder="这里输入名称"
												title="名称" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">描述:</td>
											<td><input type="text" name="FDESCRIBE" id="FDESCRIBE"
												value="${pd.FDESCRIBE}" maxlength="255" placeholder="这里输入描述"
												title="描述" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">价格:</td>
											<td><input type="number" name="PRICE" id="PRICE"
												value="${pd.PRICE}" maxlength="32" placeholder="这里输入价格"
												title="价格" style="width: 98%;" /></td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display: none">
									<br /> <br /> <br /> <br /> <br /> <img
										src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>

								<!--#############################################################################################  -->

								<tr>
									<c:if test="${msg == 'save' }">
										<div style="width: 100%; padding-bottom: 2px;" class="center">
											<a class="btn btn-mini btn-primary" onClick="save();">保存</a>
										</div>
									</c:if>
									<c:if test="${msg == 'save'|| addedit=='addedit' }">

										<input type="hidden" id="checkadd" name="checkadd"
											value="${checkadd}" />

									</c:if>
								</tr>


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

	<!-- ############################################################################################################## -->
	<c:if test="${msg != 'save' }">
		<div>
			<iframe name="treeFrame" id="treeFrame" frameborder="0"
				src="<%=basePath%>/attachedmx/list.do?ATTACHED_ID=${pd.ATTACHED_ID}"
				style="margin: 0 auto; width: 805px; height: 368px;"></iframe>
		</div>
	</c:if>

	<!-- 添加条件，判断点击的是增加还是其他的，若是点击的新增，就用下面的，如实其他，就用上面的 -->
	<!-- 以下代码是attachedmx-list中的一些 -->
	<c:if test="${msg == 'save' }">
		<div class="main-container" id="main-container">
			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">

								<!-- 检索  -->
								<form action="attachedmx/list.do" method="post" name="Form1"
									id="Form1">
									<input type="hidden" name="ATTACHED_ID" id="ATTACHED_ID"
										value="${pd.ATTACHED_ID }" />
									<table style="margin-top: 5px;">
										<tr>
											<td>
												<div class="nav-search">
													<span class="input-icon"> <input type="text"
														placeholder="这里输入关键词" class="nav-search-input"
														id="nav-search-input" autocomplete="off" name="keywords"
														value="${pd.keywords }" placeholder="这里输入关键词" /> <i
														class="ace-icon fa fa-search nav-search-icon"></i>
													</span>
												</div>
											</td>
											<c:if test="${QX.cha == 1 }">
												<td style="vertical-align: top; padding-left: 2px"><a
													class="btn btn-light btn-xs" onclick="tosearch();"
													title="检索"><i id="nav-search-icon"
														class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
											</c:if>
											<c:if test="${QX.toExcel == 1 }">
												<td style="vertical-align: top; padding-left: 2px;"><a
													class="btn btn-light btn-xs" onclick="toExcel();"
													title="导出到EXCEL"><i id="nav-search-icon"
														class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td>
											</c:if>
										</tr>
									</table>
									<!-- 检索  -->

									<div style="overflow-x: scroll; scrolling: auto; width: 100%;">
										<table id="simple-table"
											class="table table-striped table-bordered table-hover"
											style="margin-top: 5px;">
											<thead>
												<tr>
													<th class="center" style="width: 35px;"><label
														class="pos-rel"><input type="checkbox" class="ace"
															id="zcheckbox" /><span class="lbl"></span></label></th>
													<th class="center" style="width: 50px;">序号</th>
													<th class="center">名称</th>
													<th class="center">标题</th>
													<th class="center">创建日期</th>
													<th class="center">单价</th>
													<th class="center">操作</th>
												</tr>
											</thead>

											<tbody>
												<!-- 开始循环 -->
												<c:choose>
													<c:when test="${not empty varList}">
														<c:if test="${QX.cha == 1 }">
															<c:forEach items="${varList}" var="var" varStatus="vs">
																<tr>
																	<td class='center'><label class="pos-rel"><input
																			type='checkbox' name='ids'
																			value="${var.ATTACHEDMX_ID}" class="ace" /><span
																			class="lbl"></span></label></td>
																	<td class='center' style="width: 30px;">${vs.index+1}</td>
																	<td class='center'>${var.NAME}</td>
																	<td class='center'>${var.TITLE}</td>
																	<td class='center'>${var.CTIME}</td>
																	<td class='center'>${var.PRICE}</td>
																	<td class="center"><c:if
																			test="${QX.edit != 1 && QX.del != 1 }">
																			<span
																				class="label label-large label-grey arrowed-in-right arrowed-in"><i
																				class="ace-icon fa fa-lock" title="无权限"></i></span>
																		</c:if>
																		<div class="hidden-sm hidden-xs btn-group">
																			<c:if test="${QX.edit == 1 }">
																				<a class="btn btn-xs btn-success" title="编辑"
																					onclick="edit('${var.ATTACHEDMX_ID}');"> <i
																					class="ace-icon fa fa-pencil-square-o bigger-120"
																					title="编辑"></i>
																				</a>
																			</c:if>
																			<c:if test="${QX.del == 1 }">
																				<a class="btn btn-xs btn-danger"
																					onclick="del('${var.ATTACHEDMX_ID}');"> <i
																					class="ace-icon fa fa-trash-o bigger-120"
																					title="删除"></i>
																				</a>
																			</c:if>
																		</div>
																		<div class="hidden-md hidden-lg">
																			<div class="inline pos-rel">
																				<button
																					class="btn btn-minier btn-primary dropdown-toggle"
																					data-toggle="dropdown" data-position="auto">
																					<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																				</button>

																				<ul
																					class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																					<c:if test="${QX.edit == 1 }">
																						<li><a style="cursor: pointer;"
																							onclick="edit('${var.ATTACHEDMX_ID}');"
																							class="tooltip-success" data-rel="tooltip"
																							title="修改"> <span class="green"> <i
																									class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																							</span>
																						</a></li>
																					</c:if>
																					<c:if test="${QX.del == 1 }">
																						<li><a style="cursor: pointer;"
																							onclick="del('${var.ATTACHEDMX_ID}');"
																							class="tooltip-error" data-rel="tooltip"
																							title="删除"> <span class="red"> <i
																									class="ace-icon fa fa-trash-o bigger-120"></i>
																							</span>
																						</a></li>
																					</c:if>
																				</ul>
																			</div>
																		</div></td>
																</tr>

															</c:forEach>
														</c:if>
														<c:if test="${QX.cha == 0 }">
															<tr>
																<td colspan="100" class="center">您无权查看</td>
															</tr>
														</c:if>
													</c:when>
													<c:otherwise>
														<tr class="main_info">
															<td colspan="100" class="center">没有相关数据</td>
														</tr>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>

									</br> </br>

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

			<!-- 返回顶部 -->
			<a href="#" id="btn-scroll-up"
				class="btn-scroll-up btn btn-sm btn-inverse"> <i
				class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>

		</div>

	</c:if>








	<footer>
		<div class="btn-group btn-group-justified">
			<div style="width: 100%; padding-bottom: 2px;" class="center">

				<a class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
					class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				<!-- #################在点击取消之前先调用save()事件，目的是为了能够刷新整个界面，得到attached列表################### -->
				<!-- 添加连续添加的按钮 -->
				<c:if test="${msg == 'save'|| addedit=='addedit' }">
					<input class="btn btn-mini" style="margin-top: -2px;"
						name="checkaddbox" id="checkaddbox" type="checkbox" title="连续添加" />连续添加
	            </c:if>
			</div>
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
			if($("#FDESCRIBE").val()==""){
				$("#FDESCRIBE").tips({
					side:3,
		            msg:'请输入描述',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FDESCRIBE").focus();
			return false;
			}
			if($("#PRICE").val()==""){
				$("#PRICE").tips({
					side:3,
		            msg:'请输入价格',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRICE").focus();
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
		

		//####点击复选框时改变checkadd的值，并在点击到这个页面时就加在checkadd的值
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
		

		</script>
</body>
</html>