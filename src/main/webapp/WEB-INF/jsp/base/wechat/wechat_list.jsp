<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
							
						<!-- 检索  -->
						<form action="wechat/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="关注日期起"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="${pd.lastEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="关注日期止"/></td>
								<td style="vertical-align:top;padding-left:2px;">
								 	<select class="chosen-select form-control" name="id" id="id" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="男" <c:if test="${'男' == pd.id}">selected</c:if>>男</option>
									<option value="女" <c:if test="${'女' == pd.id}">selected</c:if>>女</option>
								  	</select>
								</td>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
<%-- 								<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if> --%>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">微信昵称</th>
									<th class="center">地区</th>
									<th class="center">性别</th>
									<th class="center">关注日期</th>
									<th class="center">用户名</th>
									<th class="center">用户编码</th>
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
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.WECHATNICKNAME}</td>
											<td class='center'>${var.REGION}</td>
											<td class='center'>${var.GENDER}</td>
											<td class='center'>${var.DAYOFPAYATTENTION}</td>
											<td class='center'>${var.USERNAME}</td>
											<td class='center'>${var.NAME}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.WECHAT_ID}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑">编辑</i>
													</a>
													<a class="btn btn-xs btn-success" title="发送消息" onclick="sendmessage('${var.OPENID}','${var.WECHATNICKNAME}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="发送消息">发送消息</i>
													</a>
													</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.WECHAT_ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
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
									<c:if test="${QX.cha == 0 }">
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
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
										<a class="btn btn-mini btn-success" onclick="updateFocusUser();">更新关注用户列表</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
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
		
		
		//发送消息
		function sendmessage(openId,nickname){
			 bootbox.confirm({
				    title: '',
	                message: "确认向"+nickname+"发送消息吗？",
	                buttons: {
	                    confirm: {
	                        label: '确认',
	                        className: 'btn-primary'
	                    },
	                    cancel: {
	                        label: '取消',
	                        className: 'btn-default'
	                    }
	                },
	                backdrop: false,
	                callback: function (result) {
	                    if (result) {
	                    	top.jzts();
	                    	var url = "<%=basePath%>wechat/sendmessage.do?openId="+openId+"&content=你好";
	                		$.get(url,function(data){
	                			if(data.res == 'success'){
	                				bootbox.alert({
	                                     title: '',
	                                     message: "给关注用户"+nickname+"发送了信息",
	                                     callback: function() {
	                                    	 tosearch();
	                         		    },
	                                     backdrop: true
	                                 });            				
	                				top.hangge();
	                			}else if(data.res == 'accessfail'){
	                    			bootbox.alert({
	                                     title: '',
	                                     message: "access_token获取失败！",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}else if(data.res == 'sendfail'){
	                    			bootbox.alert({
	                                     title: '',
	                                     message: "给关注用户"+nickname+"发送信息失败！",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}
	                		})
	                    }
	                }
	            });
		}
		
		//更新关注用户列表
		function updateFocusUser(){
			 bootbox.confirm({
				    title: '',
	                message: "确认要更新关注用户列表吗？",
	                buttons: {
	                    confirm: {
	                        label: '确认',
	                        className: 'btn-primary'
	                    },
	                    cancel: {
	                        label: '取消',
	                        className: 'btn-default'
	                    }
	                },
	                backdrop: false,
	                callback: function (result) {
	                    if (result) {
	                    	top.jzts();
	                    	var url = "<%=basePath%>wechat/updateFocusUser.do";
	                		$.get(url,function(data){
	                			if(data.res == 'success'){
	                				//alert(data.res);
	                				bootbox.alert({
	                                     title: '',
	                                     message: "更新了关注用户信息信息",
	                                     callback: function() {
	                                    	 tosearch();
	                         		    },
	                                     backdrop: true
	                                 });            				
	                				top.hangge();
	                			}else if(data.res == 'accessIsNull'){
	                    			bootbox.alert({
	                                     title: '',
	                                     message: "access_token获取失败！",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}else if(data.res == 'userListIsNull'){
	                    			bootbox.alert({
	                                     title: '',
	                                     message: "关注用户列表为空",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}else if(data.res == 'interfaceFail'){
	                    			bootbox.alert({
	                                     title: '',
	                                     message: "调用微信接口获取关注用户信息失败",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}else if(data.res == 'userInfoFail'){
	                    			bootbox.alert({
	                                     title: '',
	                                     message: "获取关注用户信息失败",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}
	                		})
	                    }
	                }
	            });
		}
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>wechat/goEdit.do?WECHAT_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>wechat/excel.do';
		}
	</script>


</body>
</html>