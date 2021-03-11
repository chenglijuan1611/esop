/*页面初始化*/
$(function(){	
	getData();
});
var height =  window.screen.height-20;
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
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
/*安卓关机*/
function callAndroid(){
	// 由于对象映射，所以调用test对象等于调用Android映射的对象
	SysShutdown.shutdown();
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
    	var data_received = event.data;	
		if(data_received.indexOf("shutdown") != -1)                         //判断一件关机的条件
		{
			console.log("------------------------");
			console.log(event.data);
			
			//android shutdown
			if(isAndroid){
				callAndroid();
			}				
			
			//windows shutdown
			if (typeof jsObj == "undefined") {
				//alert("jsObj参数未初始化")
				return;
			}
			jsObj.MessageText = "shutdown";
			jsObj.ShowTest();				
			
		}  	
    	if(data_received.length > 8){
        	var DataNew = data_received.split("_");
        	if(DataNew[0] == "[私信]  1"){
        		$("#singlePage").html("");
        		if(DataNew[1].indexOf("&") == -1){        			
        			console.log("/------------------websocket发来的单个url----------------------");
        			console.log(DataNew[1]);
        			console.log("------------------websocket发来的单个url----------------------/");
        			var imgurl0 = DataNew[1].split("vv")[0];	    			
        			$(".swiper-container").remove();
        			$("#singlePage").hide();
        			$("#lunboArea").show();       			
    			  	var append0 = ""; 
    			  	var appendV0 = "";
    			  	append0 = append0 + "<div class='swiper-container'>";
    			  	append0 = append0 + "<div class='swiper-wrapper'>";
    			  	append0 = append0 +	"<div class='swiper-slide'>";
    			  	append0 = append0 + "<img class='swiper-lazy' data-src='"+imgurl0+"' height='"+height+"' width='100%' />";
    			  	append0 = append0 + "<div class='swiper-lazy-preloader'></div>";
    			  	append0 = append0 + "</div>";
    			  	append0 = append0 + "</div>";
    			  	append0 = append0 + "</div>";   			   	
 	    			$("#lunboArea").append(append0); 	    			 	    			
 	    			hdp_swiper(0);
 	    			if(DataNew[1].indexOf("vv") != -1){
 	    				$(".ved").html('');
 	    				var urllist0 = DataNew[1].split("vv");
 	    				var vedioUrl0 = urllist0[urllist0.length-1]; 	    				
 	    				console.log(vedioUrl0); 	    				
 	    				appendV0 = appendV0 + "<video poster='loading.gif' muted autoplay webkit-playsinline playsinline x5-video-player-type='h5' loop width='95%' height='700px'id='video1'>";
 	    			  	appendV0 = appendV0 + "<source src='"+vedioUrl0+"' type='video/mp4' />";
 	    			  	appendV0 = appendV0 + "</video>";
 	    			}
 	    			$(".ved").append(appendV0);
 	    			send("fhadmin886" + DataNew[2] + "fhfhadmin888success" + getQueryString('id'));
        		}else if(DataNew[1].indexOf("&") != -1){
        			$(".swiper-container").remove();
        			$("#lunboArea").show();
        			$("#singlePage").hide();
        			var append1 = "";
        			var appendV1 = "";
        			var imgurl1 = DataNew[1].split("vv")[0];
        			var theseUrl = getUrlList(imgurl1);
        			console.log("/------------------websocket发来的多个url----------------------");
        			console.log(theseUrl);
        			console.log("------------------websocket发来的多个url----------------------/");        			
        			append1 = append1 + "<div class='swiper-container'>";
        			append1 = append1 + "<div class='swiper-wrapper'>"; 
        			for(var i = 0;i<theseUrl.length;i++){ 
        				append1 = append1 +	"<div class='swiper-slide'>";
        				append1 = append1 + "<img class='swiper-lazy' data-src='"+theseUrl[i]+"' height='"+height+"' width='100%' />";
        				append1 = append1 + "<div class='swiper-lazy-preloader'></div>";
        				append1 = append1 + "</div>";        				        				        				
	    		 	}
        			append1 = append1 + "</div>";
        			append1 = append1 + "</div>";
        			$("#lunboArea").append(append1);
        			hdp_swiper();
        			if(DataNew[1].indexOf("vv") != -1){
        				$(".ved").html('');
 	    				var urllist1 = DataNew[1].split("vv");
 	    				var vedioUrl1 = urllist1[urllist1.length-1];
 	    				console.log(vedioUrl1);
 	    				appendV1 = appendV1 + "<video poster='loading.gif' muted autoplay webkit-playsinline playsinline x5-video-player-type='h5' loop width='95%' height='700px' id='video1'>";
 	    				appendV1 = appendV1 + "<source src='"+vedioUrl1+"' type='video/mp4' />";
 	    				appendV1 = appendV1 + "</video>";
 	    			}
 	    			$(".ved").append(appendV1);
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
	$("#singlePage").html("");	
	var app = locationUrl + "/esop/appuser/findFileUrl?lineName="+getQueryString("id").split("-")[0]+"&terminalCode="+getQueryString("id")+"&random="+Math.random();
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
    		  console.log("-----------firstImgURL-----------------");
        	  console.log(firstImgURL);
        	  console.log("-----------firstImgURL-----------------");
    		  if(firstImgURL.indexOf("&") == -1){    			  
  			  	  $("#singlePage").show();
  			  	  var append2 = "";
  			  	  var appendV2 = "";
    			  if(firstImgURL == "http://172.16.60.191:8080/processFile/default.png"){                 //如果得到的数据是默认的背景图
    				  $("#lunboArea").hide();
    				  $("#singlePage").show();
    				  append2 = append2 + "<div class='' style='background:#000'>";
    				  append2 = append2 + "<div class='' style='width:100%;height:"+height+"px;margin-left:23%;background:url("+firstImgURL+") no-repeat;background-size:contain'></div>";
    				  append2 = append2 + "</div>";
    				  $("#singlePage").append(append2);
    			  }
    			  if(firstImgURL.indexOf("vv") == -1){																				  //若不是背景图片则是工艺文件图
    				  $(".swiper-container").remove();
    				  $("#lunboArea").show();
    				  $("#singlePage").hide();
    				  append2 = append2 + "<div class='swiper-container'>";
    				  append2 = append2 + "<div class='swiper-wrapper'>";
    				  append2 = append2 + "<div class='swiper-slide'>";
    				  append2 = append2 + "<img class='swiper-lazy' data-src='"+firstImgURL+"' height='"+height+"' width='100%' />";
    				  append2 = append2 + "<div class='swiper-lazy-preloader'></div>";
    				  append2 = append2 + "</div>";
    				  append2 = append2 + "</div>";
    				  append2 = append2 + "</div>";
    				  $("#lunboArea").append(append2);
    				  hdp_swiper(0);
    			  }
    			  if(firstImgURL.indexOf("vv") != -1){
    				  	$(".ved").html('');
   	    				var urllist2 = firstImgURL.split("vv");
   	    				var vedioUrl2 = urllist2[urllist.length-1];
   	    				console.log(vedioUrl2);
   	    				appendV2 = appendV2 + "<video poster='loading.gif' muted autoplay  webkit-playsinline playsinline x5-video-player-type='h5' loop width='95%' height='700px' id='video1'>";
   	    				appendV2 = appendV2 + "<source src='"+vedioUrl2+"' type='video/mp4' />";
   	    				appendV2 = appendV2 + "</video>";
   	    		  }
   	    		$(".ved").append(appendV2);    			  	    			  	   			  	 	    			 	    			
    		  }else if(firstImgURL.indexOf("&") != -1){													//如果得到的不仅是一张工艺文件图
    			    if(firstImgURL.indexOf("vv") == -1){
    			    	$(".swiper-container").remove();    			   
        			  	$("#lunboArea").show();
        			  	$("#singlePage").hide();    			  	
        			 	var append3 = "";
        			 	var appendV3 = "";
        			  	var firstImgList = new Array();
        		  		firstImgList = getUrlList(firstImgURL);
        		  		append3 = append3 + "<div class='swiper-container'>";
        		  		append3 = append3 + "<div class='swiper-wrapper'>";
        		  		for(var j = 0;j<firstImgList.length;j++){ 
            				append3 = append3 +	"<div class='swiper-slide'>";
        		 			append3 = append3 + "<img class='swiper-lazy' data-src='"+firstImgList[j]+"' height='"+height+"' width='100%' />";
        		 			append3 = append3 + "<div class='swiper-lazy-preloader'></div>";
        		 			append3 = append3 + "</div>";    		 		   		 		
        		  		}
        		  		append3 = append3 + "</div>";
        		  		append3 = append3 + "</div>"; 
        			$("#lunboArea").append(append3);
        			hdp_swiper(); 
    			    }else if(firstImgURL.indexOf("vv") != -1){
    			    	$(".ved").html('');
		    			$(".swiper-container").remove();    			   
					  	$("#lunboArea").show();
					  	$("#singlePage").hide();    			  	
					 	var append3 = "";
					 	var appendV3 = "";
					  	var firstImgList = new Array();
					  	firstImgURL = firstImgURL.split("vv")[0];
				  		firstImgList = getUrlList(firstImgURL);
				  		append3 = append3 + "<div class='swiper-container'>";
				  		append3 = append3 + "<div class='swiper-wrapper'>";
				  		for(var j = 0;j<firstImgList.length;j++){ 
		    				append3 = append3 +	"<div class='swiper-slide'>";
				 			append3 = append3 + "<img class='swiper-lazy' data-src='"+firstImgList[j]+"' height='"+height+"' width='100%' />";
				 			append3 = append3 + "<div class='swiper-lazy-preloader'></div>";
				 			append3 = append3 + "</div>";    		 		   		 		
				  		}
				  		append3 = append3 + "</div>";
				  		append3 = append3 + "</div>"; 
				  		$("#lunboArea").append(append3);
				  		hdp_swiper(); 
		    			var urllist3 = firstImgURL.split("vv");
		    			var vedioUrl3 = urllist3[urllist.length-1];
		    			console.log(vedioUrl3);
		    			appendV3 = appendV3 + "<video poster='loading.gif' muted autoplay  webkit-playsinline playsinline x5-video-player-type='h5' loop width='95%' height='700px' id='video1'>";
		    			appendV3 = appendV3 + "<source src='"+vedioUrl3+"' type='video/mp4' />";
		    			appendV3 = appendV3 + "</video>";
    			    }
    			    $(".ved").append(appendV3);
    			
    		 }
    	  }
	}
})
};
/*发送消息*/
//消息的格式：{content = "fhadmin886"+toUser+"fhfhadmin888" + content;}
function send(message) {
    ws.send(message);
}
/*分隔工具，把字符串分隔成数组*/
function getUrlList(para){	
	var aiaxUrlList = new Array();
	aiaxUrlList = para.split("&");
	return aiaxUrlList;
}
/*执行轮播*/
function hdp_swiper(para){
	//获取配置文件中的时间间隔
	var timeInterval = timeConfig();
	console.log("时间间隔");
	console.log(timeInterval);
	console.log("时间间隔");
	if(para == 0){												//只有一张图片则不滚动
		var myswiper = new Swiper('.swiper-container', {       				          		 		 
	 		autoplay:timeInterval,
	 	    loop : false, 	    
	 	    observer:true,										//修改swiper自己或子元素时，自动初始化swiper 
	 	    autoplayDisableOnInteraction:false,
	 	    lazyLoading :true,
	 	    observeParents:false,								//修改swiper的父元素时，自动初始化swiper 
	 	}); 
	}else{														//多张图片才滚动
	var myswiper = new Swiper('.swiper-container', {       				          		 		 
 		autoplay:timeInterval,
 	    loop : true, 	    
 	    observer:true,											
 	    autoplayDisableOnInteraction:false,
 	    lazyLoading :true,
 	    observeParents:false,									 
 	});
	};
}

//打开视频
function openVideo(){
	$(".ved").show();
	$(".on").hide();
	$(".off").show();
}

//关闭视频
function closeVideo(){
	var video = document.getElementById("video1");
	$(".ved").hide();
	$(".on").show();
	$(".off").hide();
	video.pause();

}
