<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript">
	setTimeout("top.hangge()",500);
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/bootstrap3.3.7.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/login/css/bootstrap3.3.7.css" />
<style>
	html, body {
		background-color: white;
		width: 100%;
		height: 100%;
		font-size: 10px;
	}
	.wrap {
		width: 100%;
		margin-top: 20px;
	}
	.title {
		text-indent: 2rem;
		padding-bottom: 10px;
		border-bottom: 1px solid silver;
		font-size: 1.5rem;
	}
	.title>img {
		width: 18px;
	}
	.title>span{
		display: inline-block;
		text-indent: 0.5rem;
	}
	.logo_detail {
		width: 100%;
		margin-top: 1.5rem;
		display: flex;
		flex-wrap: wrap;
		/* border: 1px solid red; */
	}
	.logo_detail a {
		display:block;
		margin-left: 3rem;
		text-align: center;
		/* border: 1px solid red; */
	}
	a:link {
		text-decoration: none;
	}
	a:hover {
		cursor:pointer;
		text-decoration: none;
	}
	a:active {
		text-decoration: none;
	}
	a:visited {
		text-decoration: none;
	}
	.logo_detail a>img{
		width: 4rem;
		cursor: pointer;
	}
	.logo_detail a>img:hover {
		transform: scale(1.2);
	}
</style>
</head>
<body class="no-skin">
	<script>
		$(function() {
			$.ajax({
				url: '<%=basePath%>/homeIconLoad',
				type: 'GET',
				data: null,
				success: function(data) {
					/* console.log(data); */
					var ary = [];
					data.list.forEach(function(item, index) {
						var ary1 = [];
						//数组拍平实现大类分组
						 function get(arr) {
						        for (var i = 0; i < arr.length; i++) {
						            if(typeof arr[i] != 'object') {
						                ary1.push(arr[i]);
						            }
						            else {
						                get(arr[i]);
						            }
						        }
						        return ary1;
						    }
						    ary.push(get(item));
					})
				   /* console.log(ary); */
				   	//小类分组用数组包裹
				    var ary2 = [];
				    ary.forEach(function(item1, index){
				    	var ary3 = [];
				    	for (var i = 0; i < item1.length; i += 3) {
				    		ary3.push(item1.slice(i, i + 3));
				    	}
				    	ary2.push(ary3);
				    })
				    /* console.log(ary2); */
				    //小类由数组变为带有属性的对象
				    var trueAry = [];
				    ary2.forEach(function(item2, index2) {
				    	var ary4 = [];
				    	var obj1 = {};
 				    	 obj1.MENU_ID = item2[0][0];
			    		 obj1.MENU_NAME = item2[0][1];
			    		 obj1.MENU_URL = item2[0][2];
			    		 ary4.push(obj1);
				    	for(var i = 1; i < item2.length; i++) {
				    		 var obj = {};
				    		if(item2[i][2] == '#') {
				    			item2.splice(i, 1);
				    			/* i--; */
				    		}
				    		 obj.MENU_ID = item2[i][0];
				    		 obj.MENU_NAME = item2[i][1];
				    		 obj.MENU_URL = item2[i][2]; 
				    		 ary4.push(obj);
				    	}
				    	trueAry.push(ary4);
				    }) 
				    //根据ID大小排序
				    var list = trueAry.sort(function(a, b){
				    	return a[0].MENU_ID - b[0].MENU_ID;
				    })
				    /* console.log(list); */
				    //动态绑定logo
				    function bindImg() {
				    	var str = '';
				    	var imgStr = '<img src="${pageContext.request.contextPath}/static/login/images/pana_icon/';
				    	list.forEach(function(item, index){
				    		str += '<div class="wrap">'
				    		item.forEach(function(item1, index) {
				    			if(index < 1) {
				    				str += '<div class="title">';
					    			str += imgStr + parseInt(item1.MENU_ID) + '.png" />';
					    			str += '<span>' + item1.MENU_NAME + '</span></div>';
					    			str += '<div class="logo_detail">';
				    			}else {
				    				str += '<a href="<%=basePath%>'+ item1.MENU_URL + '">' + imgStr + parseInt(item1.MENU_ID) + '.png" />';
				    				str += '<h6>' + item1.MENU_NAME + '</h6></a>'
				    			}
				    		})
				    		str +='</div>';
				    	})
				    	document.body.innerHTML = str;
				    }
				   bindImg();
				}
			})
		})
	</script>
</body>
</html>