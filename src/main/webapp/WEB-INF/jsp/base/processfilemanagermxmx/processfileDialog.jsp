<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	out.clear();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<meta HTTP-EQUIV="expires" CONTENT="0">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/bootstrap3.3.7.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/css/bootstrap3.3.7.css" />
<script src="${pageContext.request.contextPath}/static/ace/js/bootbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/pdf/pdf1.js"></script>

<script src="static/ace/js/bootbox.js"></script>
<script src="static/ace/js/ace/ace.js"></script>
<script src="static/ace/js/chosen.jquery.js"></script>
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<style>
        html, body {
            height: 100%;
            width: 100%;
        }
        .pdfClass {
            border: 1px solid black;
        }
        .wrapper {
        	cursor:pointer;
        	width: 100%;
        	height: 100%;
        }
        .inner {
        	text-align: center;
        	margin-bottom: 20px;
        }
        
        .pg {
        	font-size: 16px;
			margin-left: 0 auto;
			font-weight: border;
			color: red;
			text-shadow: 0 0 1px black;
        }
        
        .color {
			border: 2px solid rgba(0, 255, 0, 0.6);
			box-shadow: 0 0 3px blue;
		}
        .cls {
        	display: block;
        	position: fixed;
        	top: 0;
        	left: 0;
        }
</style>

</head>
<body>
<button class="cls">关闭</button>
<div class="wrapper" id="pdf-container">
		</div>
		
		

		
    <script>
    	var url = window.parent.dialogUrl;
    	var pdfContainer = document.getElementById('pdf-container');
        function createPdfContainer(id,className) {
        	var canvasNew = document.createElement('canvas');
            var div = document.createElement('div');
            var p = document.createElement('p');
            
            p.innerText = '第  ' + id.slice(1) + ' 页';
            p.setAttribute('class', 'pg')
            div.setAttribute('class', 'inner');
            
            canvasNew.id = id;
            canvasNew.setAttribute('page', id.slice(1));
            canvasNew.className = className;
            
            div.appendChild(canvasNew);
            div.appendChild(p);
            pdfContainer.appendChild(div);
        };
        //渲染pdf
        function renderPDF(pdf,i,id) {
            pdf.getPage(i).then(function(page) {
                var scale = 1.0;
                var viewport = page.getViewport(scale);
                //  准备用于渲染的 canvas 元素
                var canvas = document.getElementById(id);
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
        };
        //创建和pdf页数等同的canvas数
        function createSeriesCanvas(num,template) {
            var id = '';
            for(var j = 1; j <= num; j++){
                id = template + j;
                createPdfContainer(id,'pdfClass');
            }
        }
        //读取pdf文件，并加载到页面中
        function loadPDF(fileURL) {
        	PDFJS.workerSrc = '<%=basePath%>plugins/pdf/pdf1.work.js';
            PDFJS.getDocument(fileURL).then(function(pdf) {
                //用 promise 获取页面
                var id = '';
                var idTemplate = 'd';
                var pageNum = pdf.numPages;
                //根据页码创建画布
                createSeriesCanvas(pageNum,idTemplate);
                //将pdf渲染到画布上去
                for (var i = 1; i <= pageNum; i++) {
                    id = idTemplate + i;
                    renderPDF(pdf,i,id);
                }
                $('.inner').click(function() {
                	$(this).addClass('color').siblings().removeClass();
                })
                $('.inner').dblclick(function(e) {
                	
                	var paraData = window.parent.parameter;
                	var num = parseInt($(this).children().attr('id').slice(1));
                	var canvas = window.parent.pid;
                	canvas.setAttribute('page', num);
                	
                	if (canvas.nextSibling) {
                		canvas.nextSibling.innerText = '第  ' + num + ' 页';
                	}
                	else {
                		var newP = document.createElement('p');
                		newP.innerText = '第  ' + num + ' 页';
                		newP.setAttribute('class', 'pg');
                		canvas.parentNode.appendChild(newP);
                	}
                	/* console.log(paraData.PAGE); */
                	//判断页码是否存在
                	if (paraData.PAGE.indexOf(num.toString()) > -1) {
                		bootbox.alert({
				    		  message: '页码已存在！'
				    	  });
                		return false;
                	}
                	 
                	var id = canvas.getAttribute('id');
                	paraData.PAGE.push(num);
                	paraData.PAGE = paraData.PAGE.toString();
                	/* console.log(paraData); */
               	    $.ajax({
               	        url: "<%=basePath%>processfilemanagermxmx/saveStationMess.do",
               	    	type: 'POST',
						dataType: 'json',
						data: paraData,
               	    	success: function(data) {
                	    		if (data.res == "success") {
                	    			pdf.getPage(num).then(function(page) {
                                        var scale = 1.0;
                                        var viewport = page.getViewport(scale);
                                        //  准备用于渲染的 canvas 元素
                                        var canvas = window.parent.document.getElementById(id);
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
	               	    		}
               	    	},
               	    	error: function() {
               	    		alert('修改失败');
               	    	}
               	    })
               	    
               	    window.parent.dialog.style.display = 'none'; 
                })
            });
            
        }
        //如果在本地运行，需要从服务器获取pdf文件
        /* loadPDF('http://www.cityworks.cn/baoan_water/234.pdf'); */
        //如果在服务端运行，需要再不跨域的情况下，获取pdf文件
        loadPDF(url);
        $('.cls').click(function() {
        	
        	
        	window.parent.dialog.style.display = 'none';
        })
	</script>
</body>
</html>