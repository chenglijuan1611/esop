$(function(){
	getData();	
});	
/*	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
*/	var locationUrl = 'http://'+window.location.host;
/*	function callAndroid(){
		// 由于对象映射，所以调用test对象等于调用Android映射的对象
		SysShutdown.shutdown();
	} */
	function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)','i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
	}
	//---------------心跳包-----------------------
	//var userId=$("#userId").val();
	var lockReconnect = false;  //避免ws重复连接
	var ws = null;          // 判断当前浏览器是否支持WebSocket
	var wsUrl = "ws://"+window.location.hostname+":8887";
	createWebSocket(wsUrl);   //连接ws
	
	function createWebSocket(url) {
	    try{
	         if('WebSocket' in window){
	            ws = new WebSocket(url);
	        }else if('MozWebSocket' in window){  
	            ws = new MozWebSocket(url);
	        }else{
	            console.log("你的浏览器不支持WEBSOCKET");
	        } 
	        initEventHandle();
	    }catch(e){
	        reconnect(url);
	        console.log(e);
	    }     
	}
	
	function initEventHandle() {
	    ws.onclose = function () {
	        reconnect(wsUrl);
	        console.log("llws连接关闭!"+new Date().toUTCString());
	    };
	    ws.onerror = function () {
	        reconnect(wsUrl);
	        console.log("llws连接错误!");
	    };
	    ws.onopen = function () {
	    	ws.send('FHadminqq313596790' + getQueryString('id'));
	        heartCheck.reset().start();      //心跳检测重置
	        console.log("llws连接成功!"+new Date().toUTCString());
	        //ws.send('FHadminqq313596790' + getQueryString('id'));
	    };	
	    ws.onmessage = function (event) {    //如果获取到消息，心跳检测重置	  	    	
	        var data_received = event.data;
	    	console.log("------------------ws接受到的消息--------------------");
			console.log(data_received);
			console.log("------------------ws接受到的消息---------------------");			
	        if(data_received.length > 8){
	        	var DataNew = data_received.split("_");
	        	if(DataNew[0] == "[私信]  1"){
	        		$("#singlePage").html("");
	        		if(DataNew[1].indexOf("&") == -1){      		
	        			console.log("/------------------websocket发来的单个url----------------------");
	        			console.log(DataNew[1]);
	        			console.log("------------------websocket发来的单个url----------------------/");
	        			$("#myCarousel").hide();
	        			$("#singlePage").show();
	    			  	var append0 = "";
	    			  	append0 = append0 + "<div class='' style='background:#000'>";
//	    			  	append0 = append0 + "<div class='divin' style='width:100%;height:750px;background-size:contain;margin-left:11%;background:url('"+firstImgURL+"') no-repeat'></div>";
	    			  	append0 = append0 + "<div class='' style='width:100%;height:"+getQueryString('height')+"px;margin-left:11%;background:url("+DataNew[1]+") no-repeat;background-size:contain'></div>";
	    			  	append0 = append0 + "</div>";
	 	    			$("#singlePage").append(append0);
	 	    			send("fhadmin886" + DataNew[2] + "fhfhadmin888success" + getQueryString('id'));
	        		}else if(DataNew[1].indexOf("&") != -1){
	        			$(".lunbodis").html("");
	        			$("#myCarousel").show();
	        			 $("#singlePage").hide();
	        			var append3 = "";
	        			var theseUrl = getUrlList(DataNew[1]);
	        			console.log("/------------------websocket发来的多个url----------------------");
	        			console.log(theseUrl);
	        			console.log("------------------websocket发来的多个url----------------------/");
	        			for(var i = 0;i<theseUrl.length;i++){
		    		 		if(i==0){
		    		 			append3 = append3 + "<div class='item active' style='background:#000'>";
		    		 		}else{
		    		 			append3 = append3 + "<div class='item' style='background:#000'>";
		    		 		}		    			 	
		    		 			append3 = append3 + "<div class='divin' style='width:100%;height:"+getQueryString('height')+"px;margin-left:11%;background:url("+theseUrl[i]+") no-repeat;background-size:contain'></div>";
		    		 			append3 = append3 + "</div>";
		    		 	}
//	        			console.log(append3);
		    		 	$(".lunbodis").append(append3);
		    		 	$('#myCarousel').carousel({
		    				interval: 5000,
		    				pause:false
		    			});
		    			send("fhadmin886" + DataNew[2] + "fhfhadmin888success" + getQueryString('id'));
	        		}
	        		
	        	}	        	
	    	}			
	        heartCheck.reset().start();      //拿到任何消息都说明当前连接是正常的
	        console.log("ws收到消息啦:" +event.data); 
		}
	}
	// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	/* window.onbeforeunload = function() {
	    ws.close();
	} */  
	window.onbeforeunload = function() {
	    closeWebSocket();
	}
	
	//关闭WebSocket连接
	function closeWebSocket() {
	    ws.close();
	}
	
	function reconnect(url) {
	    if(lockReconnect) return;
	    lockReconnect = true;
	    setTimeout(function () {     //没连接上会一直重连，设置延迟避免请求过多
	        createWebSocket(url);
	        lockReconnect = false;
	    }, 2000);
	}
	
	//心跳检测
	var heartCheck = {
	    timeout: 540000,        //9分钟发一次心跳
	    timeoutObj: null,
	    serverTimeoutObj: null,
	    reset: function(){
	        clearTimeout(this.timeoutObj);
	        clearTimeout(this.serverTimeoutObj);
	        return this;
	    },
	    start: function(){
	        var self = this;
	        this.timeoutObj = setTimeout(function(){
	            //这里发送一个心跳，后端收到后，返回一个心跳消息，
	            //onmessage拿到返回的心跳就说明连接正常
	            ws.send("ping");
	            console.log("ping!");
	            self.serverTimeoutObj = setTimeout(function(){//如果超过一定时间还没重置，说明后端主动断开了
	                ws.close();     //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
	            }, self.timeout)
	        }, this.timeout)
	    }
	}

	//-------------------第一次使用ajax请求后台数据-------------------   
    function getData(){	
		$("#singlePage").html("");
    	$(".lunbodis").html("");
    	var app = locationUrl + "/dafeng_mes/appuser/findFileUrl?lineName="+getQueryString("id").split("-")[0]+"&terminalCode="+getQueryString("id")+"&random="+Math.random();
    	//alert(app);
    	var lineName= getQueryString("id").split("-");
    	$.ajax({    
		  url:app,
		  type: "POST",
	      dataType:"JSON",
	      cache:false,
	      success: function(data){
	    	  console.log("-----------第一次请求收到的消息-----------------");
	    	  console.log(data);
	    	  console.log("-----------第一次请求收到的消息-----------------");	    	  	  		  
	    	  if(data){
	    		  var firstImgURL = data.url;
	    		  if(firstImgURL.indexOf("&") == -1){
	    			  	console.log(0);
	    			  	$("#myCarousel").hide();
	    			  	$("#singlePage").show();
	    			  	var append1 = "";
	    			  	append1 = append1 + "<div class='' style='background:#000'>";
//	    			  	append1 = append1 + "<div class='divin' style='width:100%;height:750px;background-size:contain;margin-left:11%;background:url('"+firstImgURL+"') no-repeat'></div>";
	    			  	append1 = append1 + "<div class='' style='width:100%;height:"+getQueryString('height')+"px;margin-left:11%;background:url("+firstImgURL+") no-repeat;background-size:contain'></div>";
	    			  	append1 = append1 + "</div>";
	 	    			$("#singlePage").append(append1);
	    		  }else if(firstImgURL.indexOf("&") != -1){
	    			  $("#myCarousel").show();
	    			  $("#singlePage").hide();
	    			  	console.log(1);
	    			 	var append2 = "";
	    			  	var firstImgList = new Array();
	    		  		firstImgList = getUrlList(firstImgURL);
	    		  		console.log(firstImgList);
	    		 	for(var j = 0;j<firstImgList.length;j++){
	    		 		if(j==0){
	    		 			append2 = append2 + "<div class='item active' style='background:#000'>";
	    		 		}else{
	    		 			append2 = append2 + "<div class='item' style='background:#000'>";
	    		 		}		    			 	
		 	    			append2 = append2 + "<div class='divin' style='width:100%;height:"+getQueryString('height')+"px;margin-left:11%;background:url("+firstImgList[j]+") no-repeat;background-size:contain'></div>";
		 	    			append2 = append2 + "</div>";
	    		 	}
	    		 	console.log(append2);
	    		 	$(".lunbodis").append(append2);
	    		 	$('#myCarousel').carousel({
	    				interval: 5000,
	    				pause:false
	    			})
	    		 }
	    	  }
    	}
    })
	};
    //发送消息
    //消息的格式：{content = "fhadmin886"+toUser+"fhfhadmin888" + content;}
    function send(message) {
        //var message = document.getElementById('text').value;
        ws.send(message);
    }
	//分隔工具，把字符串分隔成数组
	function getUrlList(para){	
		var aiaxUrlList = new Array();
		aiaxUrlList = para.split("&");
		return aiaxUrlList;
	}
	/**
	 * 获取设备、浏览器的宽度和高度
	 * @returns
	 */
	function deviceBrowserWH(){
		//获取浏览器窗口的内部宽高 - IE9+、chrome、firefox、Opera、Safari：
		var w = window.innerWidth;
		var h = window.innerHeight;
		
		// HTML文档所在窗口的当前宽高 - IE8.7.6.5
		document.documentElement.clientWidth;
		document.documentElement.clientHeight;
		document.body.clientWidth;
		document.body.clientHeight;
		
		var screenW = window.screen.width;//设备的宽度
		var screenH = document.body.clientHeight;
		
		//网页可见区域宽高，不包括工具栏和滚动条（浏览器窗口可视区域大小）
		var webpageVisibleW = document.documentElement.clientWidth || document.body.clientWidth;
		var webpageVisibleH = document.documentElement.clientHeight || document.body.clientHeight;
		
		//网页正文全文宽高(不包括滚动条)
		var webpageW = document.documentElement.scrollWidth || document.body.scrollWidth;
		var webpageH = document.documentElement.scrollHeight || document.body.scrollHeight;
		
		//网页可见区域宽高，包括滚动条等边线（会随窗口的显示大小改变）
		var webpageVisibleW2 = document.documentElement.offsetWidth || document.body.offsetWidth ;
		var webpageVisibleH2 = document.documentElement.offsetHeight || document.body.offsetHeight ;
		
		/*console.log(w+'*'+h);
		console.log(screenW+'*'+screenH);
		console.log(webpageVisibleW+'*'+webpageVisibleH);
		console.log(webpageW+'*'+webpageH);
		console.log(webpageVisibleW2+'*'+webpageVisibleH2);*/
		//网页卷去的距离与偏移量
		/*
		1.scrollLeft:设置或获取位于给定对象左边界与窗口中目前可见内容的最左端之间的距离；
		2.scrollTop:设置或获取位于给定对象最顶端与窗口中目前可见内容的最左端之间的距离；
		3.offsetLeft:设置或获取位于给定对象相对于版面或由offsetParent属性指定的父坐标的计算左侧位置；
		4.offsetTop:设置或获取位于给定对象相对于版面或由offsetParent属性指定的父坐标的计算顶端位置；
		 */	 
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	