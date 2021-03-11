<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	out.clear();
%>
<!DOCTYPE html>
<html lang="en">
<head>
 <!-- HTML页面自动清缓存 -->
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="content-type" content="no-cache, must-revalidate"/>
	<meta http-equiv="expires" content="0"/>
<base href="<%=basePath%>">
<!-- <link rel="stylesheet" href="static/ace/css/bootstrap.min.css" />
<link rel="stylesheet" href="static/ace/css/fileinput.min.css" /> -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/pdf/pdf1.js"></script>
<style>
	body {
			overflow: scroll;
			background-color: white;
		}
</style>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						<form action="processfilemanagermxmx/terminalShowByTable.do" method="post" name="Form" id="Form" >
<%-- 						<input type="hidden"  name="PROCESSFILEMANAGERMX_ID" id="PROCESSFILEMANAGERMX_ID" value="${PROCESSFILEMANAGERMX_ID}"/> --%>
<%-- 						<input type="hidden"  name="PROCESSFILEMANAGER_ID" id="PROCESSFILEMANAGER_ID" value="${pd.PROCESSFILEMANAGER_ID}"/> --%>
						<input type="hidden" id="productCode"  value="${PRODUCT_CODE}">
						<table style="margin-top:5px;">
							<tr>
<!-- 								<td> -->
<!-- 									<select class="chosen-select form-control" name=PRODUCTLINE_NAME id="PRODUCTLINE_NAME" data-placeholder="请选择产线" style="vertical-align:top;width: 120px;" > -->
<!-- 										<option value=""></option> -->
<%-- 										<c:forEach items="${lineList}" var="productline"> --%>
<%-- 											<option value="${productline.CODE }" <c:if test="${productline.CODE == PRODUCTLINE_NAME}">selected</c:if>>${productline.CODE}</option> --%>
<%-- 										</c:forEach> --%>
<!-- 								    </select> -->
<!-- 								</td> -->
								<td>
									<a class="btn btn-xs btn-warning copy" title="复制工位" onclick="copyStation();" >
										<i title="复制工位">复制工位</i>
									</a>
									
									<a class="btn btn-xs btn-success upload" title="上传工艺文件">
										<i title="上传工艺文件">上传工艺文件</i>
									</a>
									<input type="file" class="processFile" name="processFile" id="processFile" style="display: none;">
									<a class="btn btn-xs btn-info scan" title="查看工艺文件" onclick="lookFile();">
										<i title="查看工艺文件">查看工艺文件</i>
									</a>
									<a class="btn btn-xs btn-danger del" title="删除工艺文件" onclick="delFile();">
										 <i title="删除工艺文件">删除工艺文件</i>
									</a>																		
<!-- 									<a class="btn btn-xs btn-success upload1" title="上传工艺文件"> -->
<!-- 										<i title="上传工艺文件">上传工艺文件</i> -->
<!-- 									</a> -->
<!-- 									<a class="btn btn-xs btn-warning" title="刷新" onclick="refresh();"> -->
<!-- 										 <i title="刷新">刷新</i> -->
<!-- 									</a> -->
								</td>
							</tr>
						</table>
						<!-- <div style="width: 100%;height: 60px;padding:10px;margin-top:5px;border: 1px solid #ccc;border-radius: 10px;">
							<input type="file" name="file111" id="myFile" value="" multiple="multiple" data-min-file-count="1"/>
						</div> -->
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>	
						<div style="width: 100%;" class="terminal">
							<div class="wrapper" id="pdf-container">
							</div>
							<!-- 检索  -->
						<div style="overflow-x: scroll; scrolling: auto;width: 100%;">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>									
									<th class="center" style="width:50px;">序号</th>
									<th class="center">终端编码</th>
									<th class="center">对应页码</th>
									<th class="center">对应工艺文件</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty terminaList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${terminaList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.TERMINAL_CODE}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.TERMINAL_CODE}</td>
											<td class='center'><input type="text" id="${var.TERMINAL_CODE}" value="${var.PAGE}"/></td>
											<td class='center'>
												<a href="JavaScript:;" title="查看图片" onclick="lookTerminalFile('${var.TERMINAL_CODE}','${var.PAGE}')">
											 		<i title="查看">预览</i>
												</a>
<%-- 												<a href="JavaScript:;" title="预览视频" onclick="previewVideo('${var.TERMINAL_EQUIPMENT}')"> --%>
<!-- 											 		<i title="预览视频">预览视频</i> -->
<!-- 												</a> -->
											</td>
											
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="btn-sm btn-xs btn-group">
														<a class="btn btn-xs btn-info" title="保存" onclick="saveStationMess('${var.TERMINAL_CODE}');">
															保存
														</a>
														
<%-- 														<a class="btn btn-xs btn-success" title="上传视频" onclick="uploadVideo('${var.TERMINAL_EQUIPMENT}')"> --%>
<!-- 															<i title="上传视频">上传视频</i> -->
<!-- 														</a> -->
<!-- 														<input type="file" class="file" name="file" id="file" style="display: none;"> -->
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
									<a class="btn btn-xs btn-info" onclick="makeAllSave('确定要保存选中的数据吗?');" title="批量保存" >批量保存</a>
								</td>
							</tr>
						</table>
						</div>
						</div>
						</div>
						</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	 
<!-- 	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div> -->
	<%@ include file="../../system/index/foot.jsp"%>
	<script src="static/ace/js/bootbox.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="static/ace/js/chosen.jquery.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<!-- 	<script type="text/javascript" src="static/ace/js/fileinput.min.js"></script> -->
<!-- 	<script type="text/javascript" src="static/ace/js/zh.js"></script> -->
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			var lineValue = $('#PRODUCTLINE_NAME').val();
			if (lineValue!=null && lineValue!=undefined && lineValue !='') {
// 				rush();
				$('.copy').show();
			} 
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			//下拉
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
		
		//上传视频
		var terminalCode;
		function uploadVideo(terCode){
			terminalCode = terCode;
			$('#file').click();
		}
// 		$('.uploadVideo').click(function() {
// 		});
		$("#file").on("change",function(){
			if(checkTv()){
				top.jzts();
				var str='';
				var formData = new FormData();
				var material_coding = $('#MATERIAL_CODING').val();
				var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
				var files=$("#file")[0].files;
				var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
				formData.append("file",files[0]);
				 $.ajax({
					  url: "<%=basePath%>processfilemanagermxmx/uploadVideo.do?MATERIAL_CODING="+material_coding+"&PROCESSFILEMANAGERMX_ID="+materialId+"&terminalCode="+terminalCode+"&PROCESSFILEMANAGER_ID="+PROCESSFILEMANAGER_ID,
				      type: "POST",
				      data: formData,
				      processData: false,
				      contentType: false,
				      success: function(data){
				    	  top.hangge();
				    	  if("success" == data.result){
				    		  bootbox.alert({
					    			 message: '视频上传成功'
				    		  });
				    	  }else if("fileIsNull" == data.result){
				    		  bootbox.alert({
					    		  message: '上传视频不存在'
					    	  })
				    	  }else if("propertiesIsNotExist" == data.result){
				    		  bootbox.alert({
					    		  message: 'E盘下不存在FTP配置文件'
					    	  })
				    	  }else if("firseVideoDeleFail" == data.result){
				    		  bootbox.alert({
					    		  message: '已有视频删除失败'
					    	  })
				    	  }else if("FtpConnectionFail" == data.result){
				    		  bootbox.alert({
					    		  message: 'FTP连接失败'
					    	  })
				    	  }else if("uploadFail" == data.result){
				    		  bootbox.alert({
					    		  message: '视频上传失败'
					    	  })
				    	  }
				    	  document.getElementById('file').value = "";
				      },
				      error:function () {
				    	  bootbox.alert({
				    		  message: '视频上传失败！'
				    	  })
				      }
					})
			}
		});
		//验证视频格式
		function checkTv(){
			 var tv_id =document.getElementById('file').value;				//根据id得到值
			 var index= tv_id.indexOf("."); 								//（考虑严谨用lastIndexOf(".")得到,得到"."在第几位
			 tv_id=tv_id.substring(index); 									//截断"."之前的，得到后缀
			     if(tv_id!=".mp4"){ 										//根据后缀，判断是否符合视频格式
			         alert("不是指定视频格式,重新选择"); 
			       document.getElementById('file').value="";   				// 不符合，就清除，重新选择
				   return false;
			     }else{
					return true;
				 }		
		}
		//预览视频previewVideo
		function previewVideo(terCode){
			var MATERIAL_CODING = $('#MATERIAL_CODING').val();
			$.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/checkVideo.do?MATERIAL_CODING="+MATERIAL_CODING+"&terminalCode="+terCode,
			      type: "POST",
			      success: function(data){
			    	  if("success" == data.remark){
			    		  var path = data.path;
			    		  window.open(path);
			    	  }else if("propertiesIsNotExist" == data.remark){
			    		  bootbox.alert({
				    		  message: 'E盘下不存在FTP配置文件！'
				    	  })
			    	  }else if("false" == data.remark ){
			    		  bootbox.alert({
				    		  message: '请先上传视频！'
				    	  })
			    	  }
			      }
		   })
		}
		
		//产线选择事件
		$('#PRODUCTLINE_NAME').on('change', function() {
			$('.upload').show();
			var PRODUCTLINE_NAME = document.getElementById('PRODUCTLINE_NAME').value;
			var PROCESSFILEMANAGERMX_ID = document.getElementById('PROCESSFILEMANAGERMX_ID').value;
			var MATERIAL_CODING = document.getElementById('MATERIAL_CODING').value;  
			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			window.location.href='<%=basePath%>processfilemanagermxmx/terminalShowByTable.do?PRODUCTLINE_NAME='+PRODUCTLINE_NAME+'&PROCESSFILEMANAGERMX_ID='+PROCESSFILEMANAGERMX_ID+'&MATERIAL_CODING='+MATERIAL_CODING+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
		})
		//刷新
		function refresh(){
			var PRODUCTLINE_NAME = document.getElementById('PRODUCTLINE_NAME').value;
			var PROCESSFILEMANAGERMX_ID = document.getElementById('PROCESSFILEMANAGERMX_ID').value;
			var MATERIAL_CODING = document.getElementById('MATERIAL_CODING').value;  
			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			alert(PRODUCTLINE_NAME);
			if(PRODUCTLINE_NAME != "" || PRODUCTLINE_NAME != undefined || PRODUCTLINE_NAME != null){
				window.location.href='<%=basePath%>processfilemanagermxmx/terminalShowByTable.do?PRODUCTLINE_NAME='+PRODUCTLINE_NAME+'&PROCESSFILEMANAGERMX_ID='+PROCESSFILEMANAGERMX_ID+'&MATERIAL_CODING='+MATERIAL_CODING+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
			}else{
				bootbox.alert({
		    		  message: '请先选择产线！'
		    	  })
			}
		}
		//查看某个终端的png文件（png在服务器本地）
		function lookTerminalFile(terCode,page){
			var PRODUCT_CODE = document.getElementById('productCode').value;
			if(page == "" || page == undefined || page == null || page == "0"){
				bootbox.alert({
		    		  message: '请先输入有效页码并保存！'
		    	  })
			}else{
				$.ajax({
					  url: "<%=basePath%>processfilemanagermxmx/lookTerminalFileByPng.do?page="+page+"&PRODUCT_CODE="+PRODUCT_CODE,
				      type: "POST",
				      success: function(data){
				    	  var urlList = data.filePath;
				    	  window.open("<%=basePath%>app/imgLook.html?imgurl="+urlList+"");
				      },
				      error: function () {
				    	  bootbox.alert({
				    		  message: '文件查看失败'
				    	  })
				      }
				})
			}
		}
		
		//查看某个终端的png文件（png在另一服务器）
// 		function lookTerminalFile(terCode,page){
// // 			var PROCESSFILEMANAGERMX_ID = document.getElementById('PROCESSFILEMANAGERMX_ID').value;
// 			if(page == "" || page == undefined || page == null || page == "0"){
// 				bootbox.alert({
// 		    		  message: '请先输入有效页码并保存！'
// 		    	  })
// 			}else{
// 				$.ajax({
<%-- 					  url: "<%=basePath%>processfilemanagermxmx/lookTerminalFileByPng2.do?terCode="+terCode+"&page="+page, --%>
// 				      type: "POST",
// 				      success: function(data){
// 				    	  if("propertiesIsNotExist" == data.result){
// 				    		  bootbox.alert({
// 					    		  message: 'FTP连接失败'
// 					    	  });
// 				    	  }else{
// 				    		  var urlList = data.filePath;
<%-- 					    		window.open("<%=basePath%>app/imgLook.html?imgurl="+urlList+""); --%>
// 				    	  }
// 				      },
// 				      error: function () {
// 				    	  bootbox.alert({
// 				    		  message: '文件查看失败'
// 				    	  })
// 				      }
// 				})
// 			}
// 		}
		//保存终端待显示页码saveStationMess
		function saveStationMess(terminalCode){
			var productCode = document.getElementById('productCode').value;
			var page = document.getElementById(terminalCode).value;
			alert(terminalCode);
			alert(productCode);
			alert(page);
			var sign = "success";
 			var allNum = '0';
			if("0" != page && "" != page){
				$.ajax({
					  url: "<%=basePath%>processfilemanagermxmx/checkFileNum.do?PRODUCT_CODE="+productCode,
				      type: "POST",
				      success: function(data){
				    	  top.hangge();
				    	  if("success" == data.remark){
				    		  sign = 'success';
				    		  allNum = data.fileNum;
				    		  while(page.indexOf("，")!=-1){
				    			  page = page.replace("，",",");
								}
				    		  var arr = page.split(',');
				    		  for(var i=0;i<arr.length;i++){
				    			  var num = $.trim(arr[i]);
				    			  if(num < 1 || num > allNum || num % 1 !== 0){
				    				  sign = 'false';
				    				  break; 
				    			  }
				    		  }
				    		  if('false' == sign ){
				    			  bootbox.alert({
						    		  message: '页码格式不正确！文件一共仅有'+allNum+'页'
						    	  })
						    	  //document.getElementById(terminalCode).value = '';
				    		  }else{
				    			  window.location.href='<%=basePath%>processfilemanagermxmx/saveStationMess.do?PAGE='+page+'&TERMINAL='+terminalCode+'&PRODUCT_CODE='+productCode;
				    		  }
				    	  }else if("false" == data.remark){
				    		  bootbox.alert({
					    		  message: '请先上传文件！'
					    	  })
				    	  }
				      }
			   })
			}else{
				window.location.href='<%=basePath%>processfilemanagermxmx/clearStationMess.do?TERMINAL='+terminalCode+'&PRODUCT_CODE='+productCode;
			}
		}
		//批量保存
		function makeAllSave(msg){
			var PRODUCTLINE_NAME = document.getElementById('PRODUCTLINE_NAME').value;
			var PROCESSFILEMANAGERMX_ID = document.getElementById('PROCESSFILEMANAGERMX_ID').value;
			var MATERIAL_CODING = document.getElementById('MATERIAL_CODING').value;
			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
			  if(document.getElementsByName('ids')[i].checked){
				var terminalCode = document.getElementsByName('ids')[i].value;
		  		var page1 = document.getElementById(terminalCode).value;
		  		var page = $.trim(page1);
		  		if(page == "" || page == undefined || page == null){
		  			if(str==''){
				  		str = str + '0H'+terminalCode;
				  	}else{
				  		str = str + 'W0H'+terminalCode;
				  	} 
		  		}else{
		  			if(str==''){
				  		str = str + page+'H'+terminalCode;
				  	}else{
				  		str = str + 'W' + page+'H'+terminalCode;
				  	} 
		  		}
			  	
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
				while(str.indexOf("，")!=-1){
					str = str.replace("，",",");
				}
				$.ajax({
					  url: "<%=basePath%>processfilemanagermxmx/checkFileNum.do?MATERIAL_CODING="+MATERIAL_CODING+"&PRODUCTLINE_NAME="+PRODUCTLINE_NAME+"&PROCESSFILEMANAGERMX_ID="+PROCESSFILEMANAGERMX_ID,
						      type: "POST",
						      success: function(data){
						    	  top.hangge();
						    	  if("success" == data.remark){
			 			    		  var sign = 'success';
						    		  var fileAllNum = data.fileNum;
			 			    		  var arr = str.split('W');
			 			    		  var ter;
			 			    		  for(var i = 0;i < arr.length;i++){
			 			    			  if("false" == sign){
			 			    				  alert("false" == sign);
			 			    				  break;
			 			    			  }else{
			 			    				  var mess = arr[i].split('H');
				 			    			  var page = mess[0];
				 			    			  var terminal = mess[1];
				 			    			  var p = page.split(',');
				 			    			  for(var j = 0;j < p.length;j++){
				 			    				  var page1 = $.trim(p[j]);
				 			    				  if(page1 < 0 || page1 > fileAllNum || page1 % 1 !== 0){
					 			    				  sign = 'false';
					 			    				  ter = terminal;
					 			    				  break;
					 			    			   }
				 			    			  }
			 			    			  }
			 			    		  }
			 			    		  if('false' == sign ){
			 			    			  bootbox.alert({
			 					    		  message: '文件一共仅有'+fileAllNum+'页,终端'+ter+'的页码格式不正确！'
			 					    	  })
			 			    		  }else{
			 			    			 if(msg == '确定要保存选中的数据吗?'){
			 								top.jzts();
			 								window.location.href='<%=basePath%>processfilemanagermxmx/makeAllSave.do?PRODUCTLINE_NAME='+PRODUCTLINE_NAME+'&PROCESSFILEMANAGERMX_ID='+PROCESSFILEMANAGERMX_ID+'&DATA_IDS='+str+'&MATERIAL_CODING='+MATERIAL_CODING+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
	 							}
	 			    		  }
				    	  }else{
				    		  bootbox.alert({
					    		  message: '请先上传工艺文件！'
					    	  })
				    	  }
				      }
			   })
			}
		};
		//隐藏的文件框触发事件测试2
		var dialog = document.querySelector('.dalog');
		var pdfContainer = document.getElementById('pdf-container');
		var pid = null;
		var dialogUrl = null;
		var parameter = {}; 
		var material_code = $('#PROCESSFILEMANAGERMX_ID').val();
		var productLine = null;
		var pageAry;
// 		$('.upload').click(function() {
// 			$('#processFile').click()
// 		});
		//上传工艺文件(文件上传在另一服务器)
// 		$("#processFile").on("change",function(){
// 			top.jzts();
// 			var str='';
// 			var formData = new FormData();
// 			var material_coding = $('#MATERIAL_CODING').val();
// 			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
// 			var files=$("#processFile")[0].files;
// 			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
// 			formData.append("processFile",files[0]);
// 			 $.ajax({
<%-- 				  url: "<%=basePath%>processfilemanagermxmx/uploadFile2.do?MATERIAL_CODING="+material_coding+"&PROCESSFILEMANAGERMX_ID="+materialId+"&tm="+new Date().getTime()+"&PROCESSFILEMANAGER_ID="+PROCESSFILEMANAGER_ID, --%>
// 			      type: "POST",
// 			      data: {'MATERIAL_CODING':material_coding,'PROCESSFILEMANAGERMX_ID':materialId,'tm':new Date().getTime()},
// 			      data: formData,
// 			      processData: false,
// 			      contentType: false,
// 			      success: function(data){
// 			    	  top.hangge();
// 			    	  if("success" == data.result){
// 			    		  bootbox.alert({
// 				    			 message: '文件上传成功'
// 			    		  });
// 			    	  }else if("fileIsNull" == data.result){
// 			    		  bootbox.alert({
// 				    		  message: '上传的文件不存在'
// 				    	  })
// 			    	  }else if("propertiesIsNotExist" == data.result){
// 			    		  bootbox.alert({
// 				    		  message: 'E盘下不存在FTP配置文件！'
// 				    	  })
// 			    	  }else if("pdfDeleFail" == data.result){
// 			    		  bootbox.alert({
// 				    		  message: '已存在的PDF文件删除失败'
// 				    	  })
// 			    	  }else if("pngDeleFail" == data.result){
// 			    		  bootbox.alert({
// 				    		  message: '已存在的图片删除失败'
// 				    	  })
// 			    	  }else if("ftpConnFail" == data.result ){
// 			    		  bootbox.alert({
// 				    		  message: 'FTP连接失败'
// 				    	  })
// 			    	  }else if("pdfUploadFail" == data.result){
// 			    		  bootbox.alert({
// 				    		  message: 'PDF上传失败'
// 				    	  })
// 			    	  }else if("localPngNotExist" == data.result){
// 			    		  bootbox.alert({
// 				    		  message: '本地图片未找到'
// 				    	  })
// 			    	  }else if("pngUploadFail" == data.result){
// 			    		  bootbox.alert({
// 				    		  message: '图片上传失败'
// 				    	  })
// 			    	  }
// 			    	  document.getElementById('processFile').value = "";
// 			      },
// 			      error:function () {
// 			    	  bootbox.alert({
// 			    		  message: '文件上传失败'
// 			    	  })
// 			      }
// 				})
// 		});
		
		
		
		//上传工艺文件(上传到服务器本地)
		$('.upload').click(function() {
			$('#processFile').click();
		});
		$("#processFile").on("change",function(){
			top.jzts();
			var str='';
			var formData = new FormData();
			var material_coding = $('#MATERIAL_CODING').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			var files=$("#processFile")[0].files;
			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			formData.append("processFile",files[0]);
			 $.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/uploadFile.do?MATERIAL_CODING="+material_coding+"&PROCESSFILEMANAGERMX_ID="+materialId+"&tm="+new Date().getTime()+"&PROCESSFILEMANAGER_ID="+PROCESSFILEMANAGER_ID,
			      type: "POST",
			      data: {'MATERIAL_CODING':material_coding,'PROCESSFILEMANAGERMX_ID':materialId,'tm':new Date().getTime()},
			      data: formData,
			      processData: false,
			      contentType: false,
			      success: function(data){
			    	  top.hangge();
			    	  if("numOfFileNotSame" == data.result){
			    		  bootbox.alert({
				    			 message: '新上传的工艺文件和已有工艺文件页码不相同,请先删除工艺文件'
				    		  });
				    		  $('.scan').show();
				    		  $('.del').show();
			    		  document.getElementById('processFile').value = "";
				    	  }else if("fail" == data.result){
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

		//复制工位
		function copyStation(){
			 var Id = $('#PROCESSFILEMANAGERMX_ID').val();
			// var lineName = $('#PRODUCTLINE_NAME').val();
			 var material_coding = $('#MATERIAL_CODING').val();
			 var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="复制工位";
			 diag.URL = '<%=basePath%>processfilemanagermxmx/goCopyStation.do?PROCESSFILEMANAGERMX_ID='+Id+'&MATERIAL_CODING='+material_coding+'&tm='+new Date().getTime()+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
			 diag.Width = 1200;
			 diag.Height = 705;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
				window.location.href='<%=basePath%>processfilemanagermxmx/terminalShowByTable.do?PRODUCTLINE_NAME='+lineName+'&PROCESSFILEMANAGERMX_ID='+Id+'&MATERIAL_CODING='+material_coding+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
			 };
			    diag.show();
		}
		
		//查看工艺文件(文件存储在本服务器)
		function lookFile(){
			var material_coding = $('#MATERIAL_CODING').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			 $.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/lookFile.do?MATERIAL_CODING="+material_coding+"&PROCESSFILEMANAGERMX_ID="+materialId+"&tm="+new Date().getTime(),
				      type: "POST",
				      cache: false,
				      success: function(data){
				    	  alert(data.filePath);
				    	  if(data.filePath == "" || data.filePath == undefined || data.filePath == null){
				    		  alert("请先上传工艺文件");
// 				    		  bootbox.alert({
// 					    		  message: '请先上传工艺文件'
// 					    	  });
				    	  }else{
				    		  window.open(data.filePath); 
				    	  }
				      },
				      error: function () {
				    	  bootbox.alert({
				    		  message: '文件查看失败'
				    	  });
				      }
				})
		}
		//查看工艺文件(文件存储在另一服务器)
		function lookFile2(){
			var material_coding = $('#MATERIAL_CODING').val();
			//var lineName = $('#PRODUCTLINE_NAME').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			 $.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/lookFile2.do?MATERIAL_CODING="+material_coding+"&PROCESSFILEMANAGERMX_ID="+materialId+"&tm="+new Date().getTime(),
				      type: "POST",
				      cache: false,
				      success: function(data){
				    	  if("propertiesIsNotExist" == data.result){
				    		  bootbox.alert({
					    		  message: 'E盘下不存在FTP的配置文件'
					    	  });
				    	  }else if(data.filePath == "" || data.filePath == undefined || data.filePath == null){
				    		  bootbox.alert({
					    		  message: '请先上传工艺文件'
					    	  });
				    	  }else{
// 				    		  top.jzts();
				    		  window.open(data.filePath); 
				    	  }
				      },
				      error: function () {
				    	  bootbox.alert({
				    		  message: '文件查看失败'
				    	  });
				      }
				})
		}
		
		//删除工艺文件（文件在服务器本地）
		function delFile(){
			top.jzts();
			var material_coding = $('#MATERIAL_CODING').val();
			//var lineName = $('#PRODUCTLINE_NAME').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			 $.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/delFile.do?MATERIAL_CODING="+material_coding+"&tm="+new Date().getTime()+"&PROCESSFILEMANAGERMX_ID="+materialId+"&PROCESSFILEMANAGER_ID="+PROCESSFILEMANAGER_ID,
			      type: "POST",
			      success: function(data){
			    	  top.hangge();
			    	  if("success" == data.result){
			    		  bootbox.alert({
			    			 message: '文件删除成功',
			    			 callback: function() {
		    					 window.location.href='<%=basePath%>processfilemanagermx/configStation.do?MATERIAL_CODING='+material_coding+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
		    		            },
			    		  });
			    	  }else if("pngDeleFail" == data.result){
			    		  bootbox.alert({
				    		  message: '图片删除失败'
				    	  });
			    	  }
			      },
			})
		}
		
		//删除工艺文件（文件在另一服务器上）
		function delFile2(){
			top.jzts();
			var material_coding = $('#MATERIAL_CODING').val();
			//var lineName = $('#PRODUCTLINE_NAME').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			var PROCESSFILEMANAGER_ID = document.getElementById('PROCESSFILEMANAGER_ID').value;
			 $.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/delFile2.do?MATERIAL_CODING="+material_coding+"&tm="+new Date().getTime()+"&PROCESSFILEMANAGERMX_ID="+materialId+"&PROCESSFILEMANAGER_ID="+PROCESSFILEMANAGER_ID,
			      type: "POST",
			      success: function(data){
			    	  top.hangge();
			    	  if("success" == data.result){
			    		  bootbox.alert({
			    			 message: '文件删除成功',
			    			 callback: function() {
		    					 window.location.href='<%=basePath%>processfilemanagermx/configStation.do?MATERIAL_CODING='+material_coding+'&PROCESSFILEMANAGER_ID='+PROCESSFILEMANAGER_ID;
		    		            },
			    		  });
			    	  }else if("properitesIsNotExist" == data.result){
			    		  bootbox.alert({
				    		  message: 'E盘下的FTP配置文件不存在'
				    	  });
			    	  }else if("ftpConnFail" == data.result){
			    		  bootbox.alert({
				    		  message: 'FTP连接失败'
				    	  });
			    	  }else if("pdfDeleFail" == data.result){
			    		  bootbox.alert({
				    		  message: 'PDF删除失败'
				    	  });
			    	  }else if("pngDeleFail" == data.result){
			    		  bootbox.alert({
				    		  message: '图片删除失败'
				    	  });
			    	  }
			      },
			})
		}
	</script>
</body>
</html>