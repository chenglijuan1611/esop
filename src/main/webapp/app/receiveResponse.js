/*页面初始化*/
$(function(){
	getData();
});
/*请求地址*/
var locationUrl = 'http://'+window.location.host;                              
/*获取url参数*/	
function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)','i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}
/*-------------------websocket配置部分-start---------------------------*/
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
/*初始化事件监听*/
function initEventHandle() {
    ws.onclose = function () {
        reconnect(wsUrl);
        console.log("ws连接关闭!"+new Date().toUTCString());
    };
    ws.onerror = function () {
        reconnect(wsUrl);
        console.log("ws连接错误!");
    };
    ws.onopen = function () {
    	ws.send('FHadminqq313596790' + getQueryString('id'));
        heartCheck.reset().start();      //心跳检测重置
        console.log("ws连接成功!"+new Date().toUTCString());
        //ws.send('FHadminqq313596790' + getQueryString('id'));
    };	
    ws.onmessage = function (event) {    //如果获取到消息，心跳检测重置	  	    	
    	$('#myCarousel').carousel({
    		interval: 5000,
    	});
    	var height = window.screen.height-20;
    	var data_received = event.data;
    	/*console.log("------------------ws接受到的消息--------------------");
		console.log(data_received);
		console.log("------------------ws接受到的消息---------------------");*/		
    	if(data_received.length > 8){
        	var DataNew = data_received.split("_");
        	if(DataNew[0] == "[私信]  1"){
        		$("#singlePage").html("");
        		if(DataNew[1].indexOf("&") == -1){      		
        			/*console.log("/------------------websocket发来的单个url----------------------");
        			console.log(DataNew[1]);
        			console.log("------------------websocket发来的单个url----------------------/");*/
        			$("#singlePage").html("");
        			$("#myCarousel").hide();
        			$("#singlePage").show();        			
    			  	var append0 = "";
    			  	append0 = append0 + "<div class='' style='background:#000'>";
    			  	append0 = append0 + "<div class='' style='width:100%;height:"+height+"px;margin-left:11%;background:url("+DataNew[1]+") no-repeat;background-size:contain'></div>";
    			  	append0 = append0 + "</div>";
 	    			$("#singlePage").append(append0);
 	    			send("fhadmin886" + DataNew[2] + "fhfhadmin888success" + getQueryString('id'));
        		}else if(DataNew[1].indexOf("&") != -1){
/*        			$('#myCarousel').carousel({
	    				interval: false,
	    			});*/
        			$(".lunbodis").empty();
        			$("#myCarousel").show();
        			$("#singlePage").hide();
        			var append1 = "";
        			var theseUrl = getUrlList(DataNew[1]);
        			/*console.log("/------------------websocket发来的多个url----------------------");
        			console.log(theseUrl);
        			console.log("------------------websocket发来的多个url----------------------/");*/
        			for(var i = 0;i<theseUrl.length;i++){
	    		 		if(i==0){
	    		 			append1 = append1 + "<div class='item active' style='background:#000'>";
	    		 		}else if(i>=1){
	    		 			append1 = append1 + "<div class='item' style='background:#000'>";
	    		 		}		    			 	
	    		 			append1 = append1 + "<div class='divin' style='width:100%;height:"+height+"px;margin-left:11%;background:url("+theseUrl[i]+") no-repeat;background-size:contain'></div>";
	    		 			append1 = append1 + "</div>";
	    		 	}	       			
	    		 	$(".lunbodis").append(append1);	
	    			$('#myCarousel').carousel({
	    				interval: 5000,
	    			});
//	    		 	$('#myCarousel').on('slide.bs.carousel', function () {
//	    				$("#myCarousel .item:first").toggleClass("active");//重复切换类名“active”
//	    		});
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
//重连
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
/*-------------------websocket配置部分-end---------------------------*/
/*-------------------第一次使用ajax请求后台数据------------------- */  
function getData(){	
	var height = window.screen.height-20;
	console.log(height);
	$("#singlePage").html("");
	$(".lunbodis").html("");
	var app = locationUrl + "/dafeng_mes/appuser/findFileUrl?lineName="+getQueryString("id").split("-")[0]+"&terminalCode="+getQueryString("id")+"&random="+Math.random();
	var lineName= getQueryString("id").split("-");
	$.ajax({    
	  url:app,
	  type: "POST",
      dataType:"JSON",
      cache:false,
      success: function(data){
    	 /* console.log("-----------第一次请求收到的消息-----------------");
    	  console.log(data);
    	  console.log("-----------第一次请求收到的消息-----------------");*/	    	  	  		  
    	  if(data){
    		  var firstImgURL = data.url;
    		  if(firstImgURL.indexOf("&") == -1){
    			  $("#myCarousel").hide();
  			  	  $("#singlePage").show();
  			  	  var append2 = "";
    			  if(firstImgURL == "http://172.16.20.182:8080/processFile/default.png"){
    				  append2 = append2 + "<div class='' style='background:#000'>";
    				  append2 = append2 + "<div class='' style='width:100%;height:"+height+"px;margin-left:25%;background:url("+firstImgURL+") no-repeat;background-size:contain'></div>";
    				  append2 = append2 + "</div>";
    			  }else{
    				  append2 = append2 + "<div class='' style='background:#000'>";
    				  append2 = append2 + "<div class='' style='width:100%;height:"+height+"px;margin-left:11%;background:url("+firstImgURL+") no-repeat;background-size:contain'></div>";
    				  append2 = append2 + "</div>";
    			  }    			  	    			  	   			  	
 	    			$("#singlePage").append(append2);
    		  }else if(firstImgURL.indexOf("&") != -1){
    			  $('#myCarousel').carousel({
	    				interval: false,
	    			});
    			  	$("#myCarousel").show();
    			  	$("#singlePage").hide();    			  	
    			 	var append3 = "";
    			  	var firstImgList = new Array();
    		  		firstImgList = getUrlList(firstImgURL);
    		 	for(var j = 0;j<firstImgList.length;j++){
    		 		if(j==0){
    		 			append3 = append3 + "<div class='item active' style='background:#000'>";
    		 		}else if(j>=1){
    		 			append3 = append3 + "<div class='item' style='background:#000'>";
    		 		}		    			 	
    		 			append3 = append3 + "<div class='divin' style='width:100%;height:"+height+"px;margin-left:11%;background:url("+firstImgList[j]+") no-repeat;background-size:contain'></div>";
    		 			append3 = append3 + "</div>";
    		 	}
    		 	$(".lunbodis").append(append3);
    		 	$(".lunbodis").append(append1);   		 		
    		 }
    	  }
	}
})
};
/*发送消息*/
//消息的格式：{content = "fhadmin886"+toUser+"fhfhadmin888" + content;}
function send(message) {
    //var message = document.getElementById('text').value;
    ws.send(message);
}
/*分隔工具，把字符串分隔成数组*/
function getUrlList(para){	
	var aiaxUrlList = new Array();
	aiaxUrlList = para.split("&");
	return aiaxUrlList;
}
	