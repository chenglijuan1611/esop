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
	body {
		overflow: scroll;
		background-color: white;
	}
</style>
</head>
<body class="no-skin">
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
			</div>
			<div class="modal-body">
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							
						<!-- 检索  -->
						<form action="product/list.do" method="post" name="Form" id="Form">
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
								<td>
											<div class="nav-search">
												<span class="input-icon"> <input type="text"
													placeholder="这里输入物料名称" class="nav-search-input"
													id="PRODUCT_NAME" autocomplete="off" name="PRODUCT_NAME"
													value="${pd.PRODUCT_NAME }" placeholder="这里输入物料编码" /> <i
													class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
								</td>
								<td>
											<div class="nav-search">
												<span class="input-icon"> <input type="text"
													placeholder="这里输入规格型号" class="nav-search-input"
													id="PRODUCT_SPECIFICATIONS" autocomplete="off" name="PRODUCT_SPECIFICATIONS"
													value="${pd.PRODUCT_SPECIFICATIONS }" placeholder="这里输入规格型号" /> <i
													class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
								</td>
										
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								</c:if>
								<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="simple-table" class="table table-striped table-bordered table-hover">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">产品编码</th>
									<th class="center">产品名称</th>
									<th class="center">规格型号</th>
<!-- 									<th class="center">标准工时</th> -->
									<th class="center">生产线名称</th>
									<th class="center">工艺文件上传</th>
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
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.PRODUCT_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center code'>${var.PRODUCT_CODE}</td>
											<td class='center'>${var.PRODUCT_NAME}</td>
											<td class='center'>${var.PRODUCT_SPECIFICATIONS}</td>
<%-- 											<td class='center'>${var.PRODUCT_SWHOURS}</td> --%>
											<td class='center'>${var.NAME}</td>
											<td class='center'>
												<a href="JavaScript:;" class="upload" title="上传工艺文件">
													<i title="上传工艺文件">上传</i>
												</a>
												<input type="file" class="processFile" name="processFile" id="processFile" style="display: none;">
											
												<a href="JavaScript:;" title="查看工艺" onclick="lookProcessfile('${var.PRODUCT_CODE}')">
											 		<i title="查看工艺">查看</i>
												</a>
												<a href="JavaScript:;" title="下发工艺" onclick="sentProcessfile('${var.PRODUCT_CODE}')">
											 		<i title="下发工艺">下发</i>
												</a>
												
												<a href="JavaScript:;" title="配置工位" onclick="configStation('${var.PRODUCT_CODE}')">
											 		<i title="配置工位">配置工位</i>
												</a>
												
											</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.PRODUCT_ID}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="del('${var.PRODUCT_ID}');">
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
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="edit('${var.PRODUCT_ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="del('${var.PRODUCT_ID}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
									<a class="btn btn-mini btn-success" onclick="importfromErp();">ERP导入</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
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
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>product/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 tosearch();
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 	//window.location.reload();
			 };
			 diag.show();
		}
		//去配置工位页面
		function configStation(productCode){
			alert(productCode);
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="配置工位";                           
			diag.URL = '<%=basePath%>product/configStation.do?PRODUCT_CODE='+productCode;
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
		
		
		
		
		 //下发工艺文件
		function sentProcessfile(productCode){
			bootbox.confirm({
			    title: '',
                message: "确认下发工艺文件吗？",
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
                    	$.ajax({
        					type: "POST",
        					url:'<%=basePath%>appuser/beforeFindMess.do',
        			    	data: {productCode:productCode},
        					dataType:'json',
        					cache: false,
        					success: function(data){
        						 var result = data.result;
        						 if("ok" == result){
        							 $('.modal-body').html("");
        							 $('#myModal').modal('show');
        							 $.ajax({
        									type: "POST",
        									url:'<%=basePath%>appuser/findMess.do',
        							    	data: {MATERIAL_CODING:MATERIAL_CODING,PRODUCTLINE_NAME:PRODUCTLINE_NAME,PLAN_ID:PLAN_ID},
        									dataType:'json',
        									//beforeSend: validateData,
        									cache: false,
        									success: function(data){
        										if(data.result == "false"){
        											bootbox.alert({
        									    		  message: '下发用的图片不存在，请重新上传工艺文件'
        									    	  })
        										}else{
        											var data_send = data.terminalMessList;
	        								    	 for(var i = 0;i < data_send.length;i++){
	        								    		 var dataNew1 = data_send[i].split("_");
	        								    		 var message = "fhadmin886"+dataNew1[1]+"fhfhadmin8881_"+dataNew1[2] +"_" +userId;
	        									    	 send(message);
	        								    	 }
        										}
        									}
        								});
        						 }else{
        							 bootbox.alert({
          					    		  message: '工艺文件未配置！'
          					    	  }) 
        						 }
        					}
        				});
                    }
                }
            });
		}
		
		
		
		
		
		
		
		var proCode;
		//上传工艺文件(上传到服务器本地)
		$('.upload').click(function() {
			proCode = $(this).parent().siblings('.code').text();
			console.log(proCode);
			$('#processFile').click();
		});
		$("#processFile").on("change",function(){
			top.jzts();
			var str='';
			var formData = new FormData();
			var PRODUCT_CODE = proCode;
			alert(PRODUCT_CODE);
			var files=$("#processFile")[0].files;
			formData.append("processFile",files[0]);
			 $.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/uploadFile.do?PRODUCT_CODE="+PRODUCT_CODE,
			      type: "POST",
			      data: {'PRODUCT_CODE':PRODUCT_CODE,'tm':new Date().getTime()},
			      data: formData,
			      processData: false,
			      contentType: false,
			      success: function(data){
			    	  top.hangge();
			    	  if("fail" == data.result){
			    		  
			    		  bootbox.alert({
				    			 message: '文件上传失败',
				    		  });
			    		  document.getElementById('processFile').value = "";
			    	  }else{
			    		  bootbox.alert({
				    			 message: '文件上传成功',
				    		  });
			    		  document.getElementById('processFile').value = "";
			    	  }
				      },
				      error:function () {
				    	  bootbox.alert({
				    		  message: '文件上传失败'
				    	  })
				      }
				})
		});
		
		//查看产品的工艺文件（文件在服务器本地）
		function lookProcessfile(productCode){
			$.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/lookProcessfile.do?PRODUCT_CODE="+productCode,
			      type: "POST",
			      success: function(data){
			    	  if("fileNotExist" == data.filePath){
			    		  bootbox.alert({
				    		  message: '请先上传工艺文件'
				    	  })
			    	  }else{
			    		  window.open(data.filePath); 
			    	  }
			      },
			      error: function () {
			    	  bootbox.alert({
			    		  message: '文件查看失败'
			    	  })
			      }
			})
			
		}
		
		//从ERP导入产品
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
	                    	var url = "<%=basePath%>product/importfromErp.do";
	                		$.get(url,function(data){
	                			console.log(data.res);
	                			console.log(data.num);
	                			if(data.res == 'success'){
	                				//alert(data.res);
	                				bootbox.alert({
	                                     title: '',
	                                     message: "新导入了"+data.num+"条产品数据,更新了"+data.checknum+"条已有产品信息",
	                                     callback: function() {
	                                    	 tosearch();
	                         		    },
	                                     backdrop: true
	                                 });            				
	                				top.hangge();
// 	                				tosearch();
//	                				nextPage(${page.currentPage});
//	                				window.location.reload();
	                			}else if(data.res == 'dbconfigReadFail'){
	                				bootbox.alert({
	                                     title: '',
	                                     message: "dbconfig.properties配置文件读取失败",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}else if(data.res == 'propertiesOfValueNotFind'){
	                    			bootbox.alert({
	                                     title: '',
	                                     message: "配置文件中链接数据库属性读取失败",
	                                     backdrop: false
	                                 });            				
	                				top.hangge();
	                    		}else if(data.res == 'dataSame'){
	                    			bootbox.alert({
	                                     title: '',
	                                     message: "产品已和ERP保持一致,更新了"+data.checknum+"条已有产品信息",
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
					var url = "<%=basePath%>product/delete.do?PRODUCT_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>product/goEdit.do?PRODUCT_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
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
								url: '<%=basePath%>product/deleteAll.do?tm='+new Date().getTime(),
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
			var PRODUCT_NAME = $("#PRODUCT_NAME").val();
			var PRODUCT_SPECIFICATIONS = $("#PRODUCT_SPECIFICATIONS").val();
			keywords = window.encodeURIComponent(keywords);
			PRODUCT_NAME = window.encodeURIComponent(PRODUCT_NAME);
			PRODUCT_SPECIFICATIONS = window.encodeURIComponent(PRODUCT_SPECIFICATIONS);
			window.location.href='<%=basePath%>product/excel.do?keywords='+keywords+'&PRODUCT_SPECIFICATIONS='+PRODUCT_SPECIFICATIONS+'&PRODUCT_NAME='+PRODUCT_NAME;
		}
	</script>


</body>
</html>