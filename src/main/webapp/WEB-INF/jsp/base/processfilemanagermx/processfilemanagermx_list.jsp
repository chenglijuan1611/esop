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
<style>
	a {
		cursor: pointer;
	}
</style>
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
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">工艺编码：</td>
								<td>
									<div id="processCoding">
										<input type="text" name="PROCESS_CODING" id="PROCESS_CODING" value="${pd.PROCESS_CODING}" maxlength="30" placeholder="这里输入工艺编码" title="工艺编码" style="width:98%;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">工艺名称：</td>
								<td>
									<div id="processName">
										<input type="text" name="PROCESS_NAME" id="PROCESS_NAME" value="${pd.PROCESS_NAME}" maxlength="30" placeholder="这里输入工艺名称" title="工艺名称" style="width:98%;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品编码：</td>
								<td>
									<div id="productCoding">
										<input type="text" name="PRODUCT_CODING" id="PRODUCT_CODING" value="${pd.PRODUCT_CODING}" maxlength="30" placeholder="这里输入产品编码" title="产品编码" style="width:98%;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品名称：</td>
								<td>
									<div id="productName">
										<input type="text" name="PRODUCT_NAME" id="PRODUCT_NAME" value="${pd.PRODUCT_NAME}" maxlength="30" placeholder="这里输入产品名称" title="产品名称" style="width:98%;"/>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">规格型号：</td>
								<td>
									<div id="productSpecification">
										<input type="text" name="PRODUCT_SPECIFICATIONS" id="PRODUCT_SPECIFICATIONS" value="${pd.PRODUCT_SPECIFICATIONS}" maxlength="30" placeholder="这里输入规格型号" title="规格型号" style="width:98%;"/>
									</div>
								</td>
							</tr>
						</table>
					</div>	
						<!-- 检索  -->
						<form action="processfilemanagermx/list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="PROCESSFILEMANAGER_ID" id="PROCESSFILEMANAGER_ID" value="${pd.PROCESSFILEMANAGER_ID}"/>
						<table style="margin-top:5px;">
							<tr>
								<td>
									<input type="hidden" name="PROCESS_CODING" id="PROCESS_CODING" value="${pd.PROCESS_CODING}"/>
									<input type="hidden" name="PROCESS_NAME" id="PROCESS_NAME" value="${pd.PROCESS_NAME}"/>
									<input type="hidden" name="PRODUCT_CODING" id="PRODUCT_CODING" value="${pd.PRODUCT_CODING}"/>
									<input type="hidden" name="PRODUCT_NAME" id="PRODUCT_NAME" value="${pd.PRODUCT_NAME}"/>
									<input type="hidden" name="PRODUCT_SPECIFICATIONS" id="PRODUCT_SPECIFICATIONS" value="${pd.PRODUCT_SPECIFICATIONS}"/>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
							
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a id="search" class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>
							</tr>
						</table>
						<!-- 检索  -->
						<div style="overflow-x: scroll; scrolling: auto;width: 100%;">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
<!-- 									<th class="center" style="width:35px;"> -->
<!-- 									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label> -->
<!-- 									</th> -->
									<th class="center" style="width:50px;">序号</th>
									<th class="center">物料编码</th>
									<th class="center">物料名称</th>
									<th class="center">规格型号</th>
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
<!-- 											<td class='center'> -->
<%-- 												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.PROCESSFILEMANAGERMX_ID}" class="ace" /><span class="lbl"></span></label> --%>
<!-- 											</td> -->
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.MATERIAL_CODING}</td>
											<td class='center'>${var.PRODUCT_NAME}</td>
											<td class='center'>${var.PRODUCT_SPECIFICATIONS}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
														<a class="btn btn-xs btn-success" title="配置工位" onclick="configStation('${var.MATERIAL_CODING}');">
															<i title="配置工位">配置工位</i>
														</a>
<%-- 														<a class="btn btn-light btn-xs" onclick="fromExcel('${var.PROCESSFILEMANAGERMX_ID}','${var.MATERIAL_CODING}');"title="从EXCEL导入"> --%>
<!-- 															<i title="读取EXCEL">读取EXCEL</i> -->
<!-- 														</a> -->
													</c:if>
<%-- 													<c:if test="${QX.edit == 1 }"> --%>
<!-- 														<a class="btn btn-xs btn-info upload" title="上传工艺文件"> -->
<!-- 															<i title="上传工艺文件">上传文件</i> -->
<!-- 														</a> -->
<!-- 														<input type="hidden" name="parameter" id="parameter"/> -->
<!-- 														<input type="file" class="processFile" name="processFile" id="processFile" style="display: none;"> -->
<%-- 													</c:if> --%>
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
						</div>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="importfromErp();">ERP导入</a>
									</c:if>
									
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
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
	<!--Enter键触发事件-->
	<script type="text/javascript" src="static/js/myjs/enterEvent.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
// 			$("#PROCESS_CODING").attr("value",pd.PROCESS_CODING);
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
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		function getParameter(mxId,material){
			return mxId +'-'+material;
		}
		
		
		//点击上传工艺文件相当于上传
		$('.upload').click(function() {
			$('#processFile').click();
		});
		
		$("#processFile").on("change",function(){
			
		//上传工艺文件2
			var formData = new FormData();
			var files=$("#processFile")[0].files;
			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			formData.append("processFile",files[0]);
			alert(222);
			 $.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/uploadFile2.do?MATERIAL_CODING="+material+"&PROCESSFILEMANAGERMX_ID="+mxId+"&tm="+new Date().getTime()+"&PROCESSFILEMANAGER_ID="+PROCESSFILEMANAGER_ID,
			      type: "POST",
			      data: {'MATERIAL_CODING':material,'PROCESSFILEMANAGER_ID':PROCESSFILEMANAGER_ID,'PROCESSFILEMANAGERMX_ID':mxId,'tm':new Date().getTime()},
			      data: formData,
			      processData: false,
			      contentType: false,
			      success: function(data){
			    	  if("numOfFileNotSame" == data.result){
			    		  bootbox.alert({
				    			 message: '新上传的工艺文件和已有工艺文件页码不相同,请先删除工艺文件'
				    		  });
				    		  $('.scan').show();
				    		  $('.del').show();
				    		  document.getElementById('processFile').value = "";
				    	  }else if("commUpload" == data.result || "numOfFileSame"== data.result){
				    		  if("needDelete" == data.result2){
				    			  bootbox.alert({
						    			 message: '请先删除已上传工艺工艺文件'
						    		  });
				    			  document.getElementById('processFile').value = "";
				    		  }else{
				    			  bootbox.alert({
						    			 message: '文件上传成功',
					    				 callback: function() {
					    					 window.location.href='<%=basePath%>processfilemanagermxmx/terminalShowByTable.do?PRODUCTLINE_NAME='+lineName+'&PROCESSFILEMANAGERMX_ID='+materialId+'&MATERIAL_CODING='+material_coding+'&tm='+new Date().getTime()+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
					    		            },
						    		  });
						    		  $('.scan').show();
						    		  $('.del').show();
						    		  document.getElementById('processFile').value = "";
				    		  }
				    	  }
				      },
				      error:function () {
				    	  bootbox.alert({
				    		  message: '文件上传失败'
				    	  })
				      }
				})
		});
		//上传工艺文件
		$("#processFile").on("change",function(){
			alert(111);
			var str='';
			var formData = new FormData();
			var material_coding = $('#MATERIAL_CODING').val();
			var lineName = $('#PRODUCTLINE_NAME').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			var files=$("#processFile")[0].files;
			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			formData.append("processFile",files[0]);
			 $.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/uploadFile.do?MATERIAL_CODING="+material_coding+"&PRODUCTLINE_NAME="+lineName+"&PROCESSFILEMANAGERMX_ID="+materialId+"&tm="+new Date().getTime()+"&PROCESSFILEMANAGER_ID="+PROCESSFILEMANAGER_ID,
			      type: "POST",
			      data: {'MATERIAL_CODING':material_coding,'PRODUCTLINE_NAME':lineName,'PROCESSFILEMANAGERMX_ID':materialId,'tm':new Date().getTime()},
			      data: formData,
			      processData: false,
			      contentType: false,
			      success: function(data){
			    	  if("numOfFileNotSame" == data.result){
			    		  bootbox.alert({
				    			 message: '新上传的工艺文件和已有工艺文件页码不相同,请先删除工艺文件'
				    		  });
				    		  $('.scan').show();
				    		  $('.del').show();
				    		  document.getElementById('processFile').value = "";
//	 				    		  rush();
				    	  }else if("commUpload" == data.result || "numOfFileSame"== data.result){
				    		  if("needDelete" == data.result2){
				    			  bootbox.alert({
						    			 message: '请先删除已上传工艺工艺文件'
						    		  });
				    			  document.getElementById('processFile').value = "";
//	 		    				  rush();
				    		  }else{
				    			  bootbox.alert({
						    			 message: '文件上传成功',
					    				 callback: function() {
					    					 window.location.href='<%=basePath%>processfilemanagermxmx/terminalShowByTable.do?PRODUCTLINE_NAME='+lineName+'&PROCESSFILEMANAGERMX_ID='+materialId+'&MATERIAL_CODING='+material_coding+'&tm='+new Date().getTime()+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
					    		            },
						    		  });
						    		  $('.scan').show();
						    		  $('.del').show();
						    		  document.getElementById('processFile').value = "";
//	 					    		  rush();
				    		  }
				    	  }
				      },
				      error:function () {
				    	  bootbox.alert({
				    		  message: '文件上传失败'
				    	  })
				      }
				})
			 
		});
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>processfilemanagermx/goAdd.do?PROCESSFILEMANAGER_ID=${pd.PROCESSFILEMANAGER_ID}';
			 diag.Width = 450;
			 diag.Height = 280;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					tosearch();
				}
				diag.close();
				window.location.reload();
			 };
			 diag.show();
		}
		
		
		//ERP导入import
		function importfromErp(){
			  bootbox.confirm({
				    title: '',
	                message: "确认要导入吗？",
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
	                    	var url = "<%=basePath%>processfilemanagermx/findMessFromERP.do?PROCESSFILEMANAGER_ID=${pd.PROCESSFILEMANAGER_ID}&PRODUCT_CODING=${pd.PRODUCT_CODING}&tm="+new Date().getTime();
	                    	$.get(url,function(data){
	                			if(data=="success"){
	                				tosearch();
// 	                				nextPage(${page.currentPage});
// 	                				window.location.reload();
	                			}else if(data=="false"){
	                				bootbox.alert({
	                                     title: '',
	                                     message: "ERP中没有当前工艺的数据",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}
	                		})
	                    }
	                }
	            });
		
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>processfilemanagermx/delete.do?PROCESSFILEMANAGERMX_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
					window.location.reload();
				}
				
			});
		}
		//ceshi
		function del2(Id){
			  bootbox.confirm({
				    title: '',
	                message: "确认要删除吗？",
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
	                    	var url = "<%=basePath%>processfilemanagermx/delete2.do?PROCESSFILEMANAGERMX_ID="+Id+"&tm="+new Date().getTime();
	                		$.get(url,function(data){
	                			if(data=="success"){
	                				//alert(data);
	                				nextPage(${page.currentPage});
	                				window.location.reload();
	                			}else{
	                				bootbox.alert({
	                                     title: '',
	                                     message: "删除失败，请点击编辑按钮，先删除明细数据",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}
	                		})
	                    }
	                }
	            });
		
		}
		//删除22（删除时查询是否还有关联，有关联不能删除）(调试注销)
		function del23(Id){
			  bootbox.confirm({
				    title: '',
	                message: "确认要删除？",
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
	                    	var url = "<%=basePath%>processfilemanagermx/delete2.do?PROCESSFILEMANAGERMX_ID="+Id+"&tm="+new Date().getTime();
	                		$.get(url,function(data){
	                			if(data=="success"){
	                				//alert(data);
	                				$.get(url,function(data){
										tosearch();
									});
	                				window.location.reload();
	                				//nextPage(${page.currentPage});
	                			}else{
	                				bootbox.alert({
	                                     title: '',
	                                     message: "删除失败，请点击编辑按钮，先删除明细数据",
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
			 diag.URL = '<%=basePath%>processfilemanagermx/goEdit.do?PROCESSFILEMANAGERMX_ID='+Id;
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
				window.location.reload();
			 };
			 diag.show();
		}
		//去配置工位页面
		function configStation(Coding){
			var PROCESSFILEMANAGER_ID = $("#PROCESSFILEMANAGER_ID").val();
// 			alert(PROCESSFILEMANAGER_ID);
			var lineCode = 'aa';
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="配置工位";                           
			diag.URL = '<%=basePath%>processfilemanagermx/configStation.do?MATERIAL_CODING='+Coding+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID+'&lineCode='+lineCode;
			diag.Width = 900;
			diag.Height = 600;
			diag.Modal = true;				//有无遮罩窗口
			diag. ShowMaxButton = true;	//最大化按钮
		    diag.ShowMinButton = true;		//最小化按钮
			diag.CancelEvent = function(){ //关闭事件
				diag.close();
			};
			diag.show();
		}
		
		//打开上传excel页面
		function fromExcel(mxId,material){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="从工艺的EXCEL 中导出物料信息";
			 diag.URL = '<%=basePath%>processfilemanagermx/goUploadExcel.do?material='+material+'&mxId='+mxId;
			 diag.Width = 300;
			 diag.Height = 150;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		
		//复制工位
		function copyStation(Id){
			 //alert(Id);
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="复制工位";
			 diag.URL = '<%=basePath%>processfilemanagermx/copyStation.do?PROCESSFILEMANAGERMX_ID='+Id;
			 diag.Width = 1200;
			 diag.Height = 705;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>processfilemanagermx/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											tosearch();
									 });
								}
							});
						}
					}
				}
			});
		};
		//导出excel
		function toExcel(){
			var keywords = $("#nav-search-input").val();
			var PROCESSFILEMANAGER_ID = $("#PROCESSFILEMANAGER_ID").val();
			window.location.href='<%=basePath%>processfilemanagermx/excel.do?keywords='+keywords+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
		}
	</script>
</body>
</html>