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
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<%@ include file="../../system/index/top.jsp"%>
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/pdf/pdf1.js"></script>
<style>
	.terminal {
		width: 100%;
		heiht: 100%;
	}
	.wrapper {
		width: 85%;
		cursor:pointer;
	}
	.dalog {
		width: 75%;
       	height: 100%;
       	position: fixed;
       	cursor:pointer;
       	left: 25%;
       	top: 10%;
       	background-color: rgba(0, 0, 0, 0.4);
       	display: none;
	}
	iframe {
		width: 100%;
		height: 100%;
	}
	.inc {
		margin-left: 10px;
	}
	.outer {
		width: 100%; 
		border: 1px solid gray;
		margin-bottom: 10px;
	}
	.color {
		border: 2px solid rgba(255, 0, 0, 0.6);
		box-shadow: 0 0 3px blue;
	}
	.cvsItem {
		dispaly: inline-block;
	}
	.inner {
		border: 1px dashed silver;
		margin: 5px 0 0 5px;
		width: 900px;
		height: 630px;
		overflow-Y: scroll;
	}
	.pg {
		font-size: 16px;
		margin-top: -10px;
		margin-left: 400px;
		font-weight: border;
		color: red;
		text-shadow: 0 0 1px black;
	}
	.terminal_name {
		display: inline-block;
		margin-left: 10px;
		font-weight: bold;
		color: gray;
		text-shadow: 0 0 1px black;
	} 
</style>
</head>
<body class="no-skin">
<input id="material_coding" type="hidden" value="${MATERIAL_CODING}">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						<!-- <input type="file" class="processFile" name="processFile" id="processFile"> -->
						<form action="processfilemanagermxmx/list.do" method="post" name="Form" id="Form" >
						<input type="hidden"  name="PROCESSFILEMANAGERMX_ID" id="PROCESSFILEMANAGERMX_ID" value="${PROCESSFILEMANAGERMX_ID}"/>
						<table style="margin-top:5px;">
							<tr>
								<td>
									<select class="chosen-select form-control" name=PRODUCTLINE_NAME id="PRODUCTLINE_NAME" data-placeholder="请选择产线" style="vertical-align:top;width: 120px;" >
										<option value=""></option>
										<c:forEach items="${lineList}" var="productline">
											<option value="${productline.CODE }" <c:if test="${productline.CODE == lineCode}">selected</c:if>>${productline.CODE}</option>
										</c:forEach>
								    </select>
								</td>
								<td>
									<input type="hidden" name="PROCESSFILEMANAGERMX_ID" id="PROCESSFILEMANAGERMX_ID" value="${pd.PROCESSFILEMANAGERMX_ID}"/>
									<a class="btn btn-xs btn-warning copy" title="复制工位" onclick="copyStation();" style="display:none;">
										<i title="复制工位">复制工位</i>
									</a>
									
									<a class="btn btn-xs btn-success upload" title="上传工艺文件" style="display:none;">
										<i title="上传工艺文件">上传工艺文件</i>
									</a>
									<input type="file" class="processFile" name="processFile" id="processFile" style="display: none;">
									<a class="btn btn-xs btn-info scan" title="查看工艺文件" onclick="lookFile();" style="display: none;">
										<i title="查看工艺文件">查看工艺文件</i>
									</a>
									<a class="btn btn-xs btn-danger del" title="删除工艺文件" onclick="delFile();" style="display: none;">
										 <i title="删除工艺文件">删除工艺文件</i>
									</a>
								</td>
							</tr>
						</table>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>	
						<div style="width: 100%;" class="terminal">
							<div class="wrapper" id="pdf-container">
							</div>
							<div class="dalog">
								<%-- <iframe src="<%=basePath%>processfilemanagermxmx/processDialog.do" width="100%" height="100%"></iframe>  --%>
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
	 
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
	<%@ include file="../../system/index/foot.jsp"%>
	<script src="static/ace/js/bootbox.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="static/ace/js/chosen.jquery.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
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
				rush();
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
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>processfilemanagermxmx/goAdd.do?PROCESSFILEMANAGERMX_ID=${pd.PROCESSFILEMANAGERMX_ID}';
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
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>processfilemanagermxmx/delete.do?PROCESSFILEMANAGERMXMX_ID="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>processfilemanagermxmx/goEdit.do?PROCESSFILEMANAGERMXMX_ID='+Id;
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
								url: '<%=basePath%>processfilemanagermxmx/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>processfilemanagermxmx/excel.do';
		}
		//隐藏的文件框触发事件测试2
		
		var dialog = document.querySelector('.dalog');
		var pdfContainer = document.getElementById('pdf-container');
		var pid = null;
		var dialogUrl = null;
		var parameter = {}; 
		var material_code = $('#PROCESSFILEMANAGERMX_ID').val();
		var productLine = null;
		var pageAry;
		
		function rush() {
			
			//首次加载先清空内容
			pdfContainer.innerHTML = '';
			//获得物料编码、产线信息、定义终端号变量
			var material_coding = $('#material_coding').val();
			var lineName = $('#PRODUCTLINE_NAME').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			var terminal = null;
			
			//ajax获取终端信息数据
			$.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/search.do?MATERIAL_CODING="+material_coding+"&PRODUCTLINE_NAME="+lineName+"&tm="+new Date().getTime(),  
			      type: "POST",
			      async: false,//同步为了下个ajax获取数据terminal
			      data: {'MATERIAL_CODING':material_coding,'PRODUCTLINE_NAME':lineName,'PROCESSFILEMANAGERMX_ID':materialId,'tm':new Date().getTime()},
			      success: function(data){
			    	  terminal = data;
			    	  console.log(terminal); 
			      },
			      error: function () {
			      }
			});
			
			//获取pdf文件信息进行操作
			$.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/searchPath.do?MATERIAL_CODING="+material_coding+"&PRODUCTLINE_NAME="+lineName+"&tm="+new Date().getTime(),
			      type: "POST",
			      data: {'MATERIAL_CODING':material_coding,'PRODUCTLINE_NAME':lineName,'PROCESSFILEMANAGERMX_ID':materialId,'tm':new Date().getTime()},
			      success: function(data){
			    	  if(data.remark) {
			    		  //上传、查看、删除按钮的显示与否的控制
			    		  $('.upload').show();
			    		  $('.scan').show();
			    		  $('.del').show();
			    		  //获取文件路径
			    		  dialogUrl = data.filePath;
					      //接收终端数据 
				    	  var terItem = terminal.terminalMessList;
				    	  /* console.log(terItem); */
				    	  productLine = terItem[0].DEPLOY_LINE;
				    	  
				    	  //遍历生成终端渲染在页面中
						  terItem.forEach(function(item, index) {
							  	//定义清除、增加按钮、canvas元素以及其外部包裹的div
					            var div = document.createElement('div');
					            var divInner = document.createElement('div');
					            var btn = document.createElement('button');
					            var btn1 = document.createElement('button');
					            var span = document.createElement('span');
					            
					            //给新定义的变量添加属性
					            span.innerText = item.TERMINAL_NAME;
					            span.setAttribute('class', 'terminal_name');
					            btn.innerText = '清除';
					            btn.setAttribute('class', 'clear');
					            btn1.innerText = '新增';
					            btn1.setAttribute('class', 'inc');
					            
					            div.setAttribute('class', 'outer');
					            div.setAttribute('id', item.SHOW_NUM);
					            div.setAttribute('ZD', item.TERMINAL_CODE);
					            divInner.setAttribute('class', 'inner');
					            //dom元素添加节点渲染到页面上   
					            div.appendChild(btn);
					            div.appendChild(btn1);
					            div.appendChild(span);
					            div.appendChild(divInner);
					            pdfContainer.appendChild(div);
					            //通过获取的文件路径绑定到终端上,同时渲染每个终端信息中传的页码
					            PDFJS.workerSrc = '<%=basePath%>plugins/pdf/pdf1.work.js';
					            PDFJS.getDocument(dialogUrl).then(function(pdf) {
					            	/* console.log(item); */
					                //用 promise 获取页面
					                var pageNum = pdf.numPages;
					                //将pdf渲染到画布上去
					                //如果终端没有页码，那么渲染一张空白的画布占位在页面中当做显示的终端信息
					                if (item.page == 0 && item.page.length < 2) {
					                	/* console.log(item.page); */
					                	var canvasNew = document.createElement('canvas');
					                	canvasNew.id = 'p' + item.SHOW_NUM +  item.page;
					                	canvasNew.setAttribute('class', 'cvsItem');
							            /* canvasNew.setAttribute('page', item.page); */
							            divInner.appendChild(canvasNew);
					                	var canvas = document.getElementById(canvasNew.id);
					                	canvas.width = '841';
					                	canvas.height = '595';
					                	
						                var cxt = canvas.getContext('2d');
						                cxt.fillStyle="white"; 
						                cxt.beginPath();  
					            	    cxt.fillRect(0, 0, canvas.width, canvas.height);  
					            	    cxt.closePath();
					                }
					                //如果终端中有页码信息，那么就遍历页码，渲染对应的页码文件
					                else {
					                	//数组保存获取的字符串页码
					                	pageAry = item.page.split(',');
					                	
					                	//解析数组,如果有0,则清除
					                	for (var i = 0; i < pageAry.length; i++) {
					                		if(pageAry[i] == 0) {
					                			pageAry.splice(i, 1);
					                		}
					                	}
					                	/* console.log(pageAry); */
					                	//遍历生成对应的页码文件、同时给对应的页码描述添加在图片下面
					                	pageAry.forEach(function(item1, index1) {
					                		var p = document.createElement('p');
					                        p.innerText = '第  ' + item1 + ' 页';
					                        p.setAttribute('class', 'pg');
					                		var canvasNew = document.createElement('canvas');
						                	canvasNew.id = 'p' + item.SHOW_NUM +  item1;
						                	canvasNew.setAttribute('class', 'cvsItem');
								            canvasNew.setAttribute('page', item1);
								            divInner.appendChild(canvasNew);
								            divInner.appendChild(p);
								            
								            //遍历到的页码渲染到相应的终端上（pdfjs插件来完成）
					                		pdf.getPage(parseInt(item1)).then(function(page) {
								                var scale = 1.0;
								                var viewport = page.getViewport(scale);
								                //  准备用于渲染的 canvas 元素
								                var canvas = document.getElementById(canvasNew.id);
								                var context = canvas.getContext('2d');
								                canvas.height = viewport.height;
								                canvas.width = viewport.width;
								                // 将 PDF 页面渲染到 canvas 上下文中
								                var renderContext = {
								                    canvasContext: context,
								                    viewport: viewport
								                };
								                page.render(renderContext);
								            });
					                	})
					                }
					            });
							})
							//清除终端所有页码
							$('.clear').on('click', function(e) {
								e.preventDefault();
								e.stopPropagation();
								var terminal_code = e.target.parentNode.getAttribute('zd');
								var that = $(this).siblings('.inner').children('canvas:eq(0)').attr('id');
								var clearData = {};
								clearData.PRODUCTLINE_NAME = productLine;
								clearData.PROCESSFILEMANAGERMX_ID = material_code;
								clearData.TERMINAL_EQUIPMENT = terminal_code;
								clearData.PAGE = 0;
								
								var canvasNew = document.createElement('canvas');
								canvasNew.id = that;
								$(this).siblings('.inner').html(canvasNew);
								$.ajax({
									 url: "<%=basePath%>processfilemanagermxmx/clearStationMess.do",
			               	    	 type: 'POST',
									 dataType: 'json',
									 data: clearData,
			               	    	 success: function(data) {
			                	    		if (data.res == "success") {
			                	    			var canvas = document.getElementById(that);
			                	    			canvas.width = '841';
							                	canvas.height = '595';
								                var cxt = canvas.getContext('2d');
								                cxt.fillStyle="white"; 
								                cxt.beginPath();  
							            	    cxt.fillRect(0, 0, canvas.width, canvas.height);  
							            	    cxt.closePath();
				               	    		}
			               	    	 },
			               	    	 error: function() {
			               	    		alert('失败');
			               	    	 }
								})
							})
							//增加新页码
							var incNum = 0;
							$('.inc').click(function(e) {
								e.preventDefault();
								e.stopPropagation();
								
								if (!$(this).siblings('.inner').children('canvas:eq(0)').attr('page')) {
									bootbox.alert({
							    		  message: '请在空白处双击添加文件！'
							    	  });
									return false;
								};
								
								if (!$(this).siblings('.inner').children('canvas:last').attr('page')) {
									bootbox.alert({
							    		  message: '请在灰色框处双击添加文件！'
							    	  });
									return false;
								};
								++incNum;
								var canvasNew = document.createElement('canvas');
								canvasNew.id = 'inc' + incNum;
								$(this).siblings('.inner').append(canvasNew);
								var canvas = document.getElementById(canvasNew.id);
            	    			canvas.width = '841';
			                	canvas.height = '595';
				                var cxt = canvas.getContext('2d');
				                cxt.fillStyle="#F1F1F1"; 
				                cxt.beginPath();  
			            	    cxt.fillRect(0, 0, canvas.width, canvas.height);  
			            	    cxt.closePath();
			            	    var position = '#' + canvasNew.id;
			            	    $(this).siblings('.inner').animate({scrollTop: $(position).offset().top}, 500);
							})
							//点击选择框颜色变化
							$('.outer').on('click', function() {
								$(this).addClass('outer color').siblings().removeClass('color');
							});
						  	//双击事件触发弹框选择弹框中的元素
							$('.outer').on('dblclick', function(e) {
								  var terminal_code = e.target.parentNode.parentNode.getAttribute('zd');
								  var innerNode = e.target.parentNode;
								  var innerCanvas = innerNode.getElementsByTagName('canvas');
								  pid = e.target;
								  /* console.log(innerCanvas[0].getAttribute('page'));
								  console.log(e.target.getAttribute('page')); */
								  parameter.PRODUCTLINE_NAME = productLine;
								  parameter.PROCESSFILEMANAGERMX_ID = material_code;
								  parameter.TERMINAL_EQUIPMENT = terminal_code;
								  parameter.PAGE = [];
								  for (var i = 0; i < innerCanvas.length; i++) {
									 if (innerCanvas[i].getAttribute('page') === null) {
										 
									 }
									 else{
										 parameter.PAGE.push(innerCanvas[i].getAttribute('page')); 
									 }
								  }
								  /* console.log(parameter.PAGE); */
								  //因为异步加载数据，所以iframe需要放在数据读取之后渲染到弹框中，便于父子页面参数之间相互调用
								  var frame = document.createElement('iframe');
						    	  frame.setAttribute('src', '<%=basePath%>processfilemanagermxmx/processDialog.do');
						    	  frame.setAttribute('name', 'dalog');
						    	  dialog.appendChild(frame);
								  $('.dalog').show();
							});
							
			    	  }else{
			    		  $('.upload').show();
			    		  
			    		  $('.scan').hide();
			    		  $('.del').hide();
			    	  }
			    	  
			    	  /* if (data.terminalMessList.length == 0) return; */
			      },
			      error: function () {
			      }
			})
			
		}
		//产线选择事件
		$('#PRODUCTLINE_NAME').on('change', function() {
			if ($(this).val()) {
				rush();
				$('.copy').show();
			} 
			else{
				$('.upload').hide();
				$('.copy').show();
			}
		})
		$('.upload').click(function() {
			/* e.preventDefault(); */
			$('#processFile').click()
		});
		
		//上传工艺文件
		$("#processFile").on("change",function(){
			var str='';
			var formData = new FormData();
			var material_coding = $('#material_coding').val();
			var lineName = $('#PRODUCTLINE_NAME').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			var files=$("#processFile")[0].files;
			formData.append("processFile",files[0]);
			/* console.log(formData); */
			$.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/uploadFile.do?MATERIAL_CODING="+material_coding+"&PRODUCTLINE_NAME="+lineName+"&PROCESSFILEMANAGERMX_ID="+materialId+"&tm="+new Date().getTime(),
			      type: "POST",
			      data: {'MATERIAL_CODING':material_coding,'PRODUCTLINE_NAME':lineName,'PROCESSFILEMANAGERMX_ID':materialId,'tm':new Date().getTime()},
			      data: formData,
			      processData: false,
			      contentType: false,
			      success: function(data){
			    	  /* console.log('uploadFile:::::'+data.filePath); */
			    	  if("numOfFileNotSame" == data.result){
			    		  bootbox.alert({
				    			 message: '新上传的工艺文件和已有工艺文件页码不相同,请先删除工艺文件'
				    		  });
				    		  $('.scan').show();
				    		  $('.del').show();
				    		  document.getElementById('processFile').value = ""
				    		  rush();
			    	  }else if("commUpload" == data.result || "numOfFileSame"== data.result){
			    		  if("needDelete" == data.result2){
			    			  bootbox.alert({
					    			 message: '请先删除已上传工艺工艺文件'
					    		  });
			    			  document.getElementById('processFile').value = ""
		    				  rush();
			    		  }else{
			    			  bootbox.alert({
					    			 message: '文件上传成功'
					    		  });
					    		  $('.scan').show();
					    		  $('.del').show();
					    		  document.getElementById('processFile').value = ""
					    		  rush();
			    		  }
			    	
			    	  }
		    		  
			      },
			      error: function () {
			    	  bootbox.alert({
			    		  message: '文件上传失败'
			    	  })
			      }
			})
		});
		
		
		//复制工位copyStation
		function copyStation(){
			
			 var Id = $('#PROCESSFILEMANAGERMX_ID').val();
// 			 alert(Id);
			 var lineName = $('#PRODUCTLINE_NAME').val();
			 var material_coding = $('#material_coding').val();
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="复制工位";
			 diag.URL = '<%=basePath%>processfilemanagermxmx/goCopyStation.do?PROCESSFILEMANAGERMX_ID='+Id+'&PRODUCTLINE_NAME='+lineName+'&MATERIAL_CODING='+material_coding+'&tm='+new Date().getTime();
			 diag.Width = 1200;
			 diag.Height = 705;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				
				diag.close();
				rush();
			 };
			 diag.show();
		}
		
		
		//查看工艺文件
		function lookFile(){
			var material_coding = $('#material_coding').val();
			var lineName = $('#PRODUCTLINE_NAME').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			$.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/lookFile.do?MATERIAL_CODING="+material_coding+"&PRODUCTLINE_NAME="+lineName+"&PROCESSFILEMANAGERMX_ID="+materialId+"&tm="+new Date().getTime(),
			      type: "POST",
			      success: function(data){
			    	  	/* console.log(data.filePath); */
			    		window.open(data.filePath); 
			      },
			      error: function () {
			    	  bootbox.alert({
			    		  message: '文件查看失败'
			    	  })
			      }
		})
		}
		//删除工艺文件
		function delFile(){
			var material_coding = $('#material_coding').val();
			var lineName = $('#PRODUCTLINE_NAME').val();
			var materialId = $('#PROCESSFILEMANAGERMX_ID').val();
			$.ajax({
				  url: "<%=basePath%>processfilemanagermxmx/delFile.do?MATERIAL_CODING="+material_coding+"&PRODUCTLINE_NAME="+lineName+"&tm="+new Date().getTime()+"&PROCESSFILEMANAGERMX_ID="+materialId,
			      type: "POST",
			      success: function(data){
			    	  if(data == 'success'){
			    		  bootbox.alert({
				    		  message: '文件删除成功'
				    	  });
				    	  $('.scan').hide();
				    	  $('.del').hide(); 
				    	  pdfContainer.innerHTML = '';
			    	  }else{
			    		  bootbox.alert({
				    		  message: '文件删除失败，请先删除终端显示文件对该文件的引用'
				    	  })
				    	  $('.scan').show();
			    		  $('.del').show(); 
			    	  }
			    	  
			      },
		})
		}
		
	</script>
</body>
</html>