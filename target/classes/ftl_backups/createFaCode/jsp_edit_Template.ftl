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
					
					<form action="${objectNameLower}/${r"${msg }"}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="${objectNameUpper}_ID" id="${objectNameUpper}_ID" value="${r"${pd."}${objectNameUpper}_ID${r"}"}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
				<#list fieldList as var>
					<#if var[3] == "是">
						<#if var[1] == 'Date'>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">${var[2] }:</td>
								<td><input class="span10 date-picker" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="${var[2] }" title="${var[2] }" style="width:98%;"/></td>
							</tr>
						<#elseif var[1] == 'Integer'>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">${var[2] }:</td>
								<td><input type="number" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" maxlength="32" placeholder="这里输入${var[2] }" title="${var[2] }" style="width:98%;"/></td>
							</tr>
						<#elseif var[1] == 'Double'>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">${var[2] }:</td>
								<td><input type="number" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" maxlength="32" placeholder="这里输入${var[2] }" title="${var[2] }" style="width:98%;"/></td>
							</tr>
						<#else>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">${var[2] }:</td>
								<td><input type="text" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" maxlength="${var[5] }" placeholder="这里输入${var[2] }" title="${var[2] }" style="width:98%;"/></td>
							</tr>
						</#if>
					</#if>
				</#list>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					
					 <!-- 增加一个保存的按钮和一个提交的checkadd -->
				        <tr>
						<c:if test="${msg == 'save' }">
							<div style="width: 100%; padding-bottom: 2px;" class="center">
								a class="btn btn-mini btn-primary" onClick="save();">保存</a>
							</div>
						</c:if>
						<c:if test="${msg == 'save'|| addedit=='addedit' }">
                            <input type="hidden" id="checkadd" name="checkadd" value="${checkadd}" />
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
<c:if test="${r"${'save' != msg }"}">
	<div>
		<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/${objectNameLower}mx/list.do?${objectNameUpper}_ID=${r"${pd."}${objectNameUpper}_ID${r"}"}" style="margin:0 auto;width:805px;height:368px;;"></iframe>
	</div>
</c:if>



<!-- 判断点击的是新增还是编辑 -->
<c:if test="${r"${'msg' == 'save'}"}">
<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							
						<!-- 检索  -->
						<form action="${objectNameLower}/list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="${faobject}_ID" id="${faobject}_ID" value="${r"${pd."}${faobject}_ID${r"}"}"/>
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${r"${pd.keywords }"}" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="name" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="">1</option>
									<option value="">2</option>
								  	</select>
								</td>
								<c:if test="${r"${QX.cha == 1 }"}">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<c:if test="${r"${QX.toExcel == 1 }"}"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>
							</tr>
						</table>
						<!-- 检索  -->
					
						<div style="overflow-x: scroll; scrolling: auto;width: 100%;">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
								<#list fieldList as var>
									<th class="center">${var[2]}</th>
								</#list>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${r"${not empty varList}"}">
									<c:if test="${r"${QX.cha == 1 }"}">
									<c:forEach items="${r"${varList}"}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${r"${var."}${objectNameUpper}_ID${r"}"}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${r"${vs.index+1}"}</td>
										<#list fieldList as var>
											<td class='center'>${r"${var."}${var[0]}${r"}"}</td>
										</#list>
											<td class="center">
												<c:if test="${r"${QX.edit != 1 && QX.del != 1 }"}">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${r"${QX.edit == 1 }"}">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${r"${var."}${objectNameUpper}_ID${r"}"}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${r"${QX.del == 1 }"}">
													<a class="btn btn-xs btn-danger" onclick="del('${r"${var."}${objectNameUpper}_ID${r"}"}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
													</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${r"${QX.edit == 1 }"}">
															<li>
																<a style="cursor:pointer;" onclick="edit('${r"${var."}${objectNameUpper}_ID${r"}"}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${r"${QX.del == 1 }"}">
															<li>
																<a style="cursor:pointer;" onclick="del('${r"${var."}${objectNameUpper}_ID${r"}"}');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
													</div>
												</div>
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${r"${QX.cha == 0 }"}">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						</div>
						
					    </br></br>
						</form>
					
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
</c:if>


<footer>
<div class="btn-group btn-group-justified">
<div style="width: 100%;padding-bottom: 2px;" class="center">
	<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
	<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
		<!-- 添加连续添加的按钮 -->
	<c:if test="${msg == 'save'|| addedit=='addedit' }">
		<input class="btn btn-mini" style="margin-top: -2px;" name="checkaddbox" id="checkaddbox" type="checkbox" title="连续添加" />连续添加
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
		<#list fieldList as var>
		<#if var[3] == "是">
			if($("#${var[0] }").val()==""){
				$("#${var[0] }").tips({
					side:3,
		            msg:'请输入${var[2] }',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#${var[0] }").focus();
			return false;
			}
		</#if>
		</#list>
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