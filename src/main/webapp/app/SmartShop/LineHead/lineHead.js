/*请求地址*/
var locationUrl = 'http://'+window.location.host; 
var yibiaoChart;
var zhuChart;
var bingChart;
//页面加载完成执行方法块
$(function(){
	getNewData();
	setTimeout(function(){
		getNewData();								//获取最新数据
	    setTimeout(arguments.callee,120000);
	},120000);						
	//getCurrentTime();								//获取当前服务器时间
	//getThroughRateChart();		    			//获取直通率仪表图
	//getZhuChart();				    			//获取柱状图
	//getBingChart();								//获取饼图
});
//获取最新数据
function getNewData(){
	console.log("success");
	var lineName = getQueryString("line");
	var data = {lineName:lineName};
	//console.log(lineName);
	$.ajax({
		url:locationUrl + '/dafeng_mes/app/SmartShop/LineHead/lineHead.json',
		type:'post',
		data:data,
		cache:false,
		success: function(data){
			//console.log(data);
			var info = data.contents.info;											//线长信息
			//console.log(info);
			$(".name").text(info.name);
			$(".job").text(info.job);
			$(".phone").text(info.phone);
			$(".IE").text(info.IE);			
			getCurrentTime(info.serverTime);										////获取当前服务器时间
			var currentProduction = data.contents.currentProduction;				//正在生产的产品信息
			//console.log(currentProduction);
			var productionName = currentProduction.productionName;
			if(productionName != ""){
				$(".productionName").text(productionName);
			}else{
				$(".productionName").text("暂无产品");
			}
			$(".productionNameCode").text(currentProduction.productionNameCode);
			$(".ICTTime").text(currentProduction.ICTTime);
			$(".planBeat").text(currentProduction.planBeat);
			$(".planAmount").text(currentProduction.planAmount);
			$(".FCTTime").text(currentProduction.FCTTime);
			$(".startTime").text(currentProduction.startTime);
			$(".actualAmount").text(currentProduction.actualAmount);
			$(".AOITime").text(currentProduction.AOITime);
			$(".endTime").text(currentProduction.endTime);
			$(".productBeat").text(currentProduction.productBeat);
			$(".baozhaungTime").text(currentProduction.baozhaungTime);
			$(".productiveness").text(currentProduction.productiveness);			
			getThroughRateChart(currentProduction.throughRate);
			var eachProdProgress = data.contents.eachProdProgress;					//柱状图数据
			//console.log(eachProdProgress);
			getZhuChart(eachProdProgress);
			var lineTotalProgress = data.contents.lineTotalProgress;				//饼图数据
			//console.log(lineTotalProgress);
			getBingChart(lineTotalProgress.finished,lineTotalProgress.unfinished);	
						
		},
		error:function(error){
			console.log(error)
		}
	})
	
}
//获取当前服务器时间
function getCurrentTime(serverTime){						//测试通过
	var serverTime = serverTime*1000;
    //console.log(serverTime)
      if (serverTime != "") {
    	  var time = new Date(serverTime);
    	  var year = time.getFullYear();
    	  var month = time.getMonth();
          month = month + 1; 
          month < 10 ? month ='0'+ month : month;             
          var date = time.getDate(); 
          date < 10 ? date ='0'+ date : date;
          var myTime = year +" 年 "+ month +" 月 "+ date + " 日 " ;
          //console.log(myTime);
          $("#currentTime").text(myTime);
          //return myTime;          
      }
}
//获取直通率仪表图
function getThroughRateChart(val){
	if (yibiaoChart != null && yibiaoChart != "" && yibiaoChart != undefined) {
		yibiaoChart.dispose();
    }
	yibiaoChart = echarts.init(document.getElementById('rateChart'));
	var yibiaoOption = {
			title:{
				text:'直通率',
				x:42,
		        y:30,
		        textStyle: {										//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
	                /*fontFamily: 'Arial',
	                fontStyle: 'normal',
	                fontWeight: 'normal',*/
	                color:'#fff',
	                fontSize: 20,
	                
	            },
			},
            tooltip : {
                formatter: "{a} <br/>{b} : {c}%"
            },
            series : [
                {
                    name:'工作进度',
                    type:'gauge',
                    startAngle: 180,
                    itemStyle:{
                        color: 'rgba(255,255,255,0.6)',
                        
                  },
                    endAngle: 0,
                    center : ['43%', '80%'],    // 默认全局居中
                    radius : 75,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.09, 'lime'],[0.82, '#1e90ff'],[1, '#ff4500']],
                            width: 4,
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        splitNumber: 5,    // 每份split细分多少段
                        length :12,        // 属性length控制线长
                    },
                    axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                        formatter: function(v){
                            switch (v+''){
                                case '10': return '低';
                                case '50': return '';
                                case '90': return '高';
                                default: return '';
                            }
                        },
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            color: '#fff',
                            fontSize: 15,
                            fontWeight: 'bolder'
                        }
                    },
                    pointer: {
                        width:6,
                        length: '75%',
                        color: 'rgba(255, 255, 255, 0.8)'
                    },
                    title : {
                        show : true,
                        offsetCenter: [0, '-60%'],       // x, y，单位px
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            color: '#fadb14',
                            fontSize: 15
                        }
                    },
                    detail : {
                        show : true,
                        backgroundColor: 'rgba(0,0,0,0)',
                        borderWidth: 0,
                        borderColor: 'red',
                        width: 80,
                        height: 30,
                        offsetCenter: [0, -100],       // x, y，单位px
                        formatter:'{value}%',
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontSize : 20,
                            color:'#fadb14'
                        }
                    },
                    data:[{value:val, name: ''}]
                    // data:[{value: this.shopRate.firstShopRate, name: '按时完工率'}]
                }
            ]
          };     
          // 绘制图表
		  yibiaoChart.setOption(yibiaoOption); 
	
}
//获取柱状图
function getZhuChart(eachProdProgress){
	if (zhuChart != null && zhuChart != "" && zhuChart != undefined) {
		zhuChart.dispose();
    }
	zhuChart = echarts.init(document.getElementById('eachZhuChart'));
	var zhuOption = {
			title: {
            	text: '产品完成情况',           	
                x: 'center',
                y: 5,
                textStyle: {
                	color:'#fff',
	                //fontFamily: 'Arial',
	                fontSize: 20,
	                //fontStyle: 'normal',
	                //fontWeight: 'normal'
                }	               
            },
            grid:{
                x:45,
                y2:80
            },
            legend: {
            	x:50,
            	y:10,
            	textStyle:{
            		color:'#fff'
            	}, 
            	formatter: function(v){
                    switch (v){
                        case 'plan': return '计划';
                        case 'actual': return '实际';
                        default: return '';
                    }
                },
            },
            tooltip: {},
            dataset: {
                // 这里指定了维度名的顺序，从而可以利用默认的维度到坐标轴的映射。
                // 如果不指定 dimensions，也可以通过指定 series.encode 完成映射。
                dimensions: ['productionName', 'plan', 'actual'],
                source:eachProdProgress
                /*source: [
                    {"productionName": '大东OS-318', 'plan': 5000, 'actual': 1000},
                    {"productionName": '尼得科', 'plan': 3000, 'actual': 1500},
                    {"productionName": '大东OS-338', 'plan': 1000, 'actual': 100},
                    {"productionName": '松下MC-CJ915', 'plan': 7000, 'actual': 2000},
                    {"productionName": '飞利浦CELERY', 'plan': 6000, 'actual': 1000},
                    {"productionName": '威乐', 'plan': 4000, 'actual': 1500},
                    {"productionName": '海信', 'plan': 2000, 'actual': 1800},
                    {"productionName": '松下MC-SB30J', 'plan': 1000, 'actual': 500}
                ]*/
            },
            xAxis: {
            	type: 'category',
            	axisLine: {
	                show: false
	            },
	            axisLabel: {
	                show: true,
	                interval:0,
			        rotate:40,
	                textStyle: {
	                   color: '#fff',  //更改坐标轴文字颜色	    	                   
	                }
	              },
	            axisTick:{
	            	show: false		   //显示/隐藏刻度
	            }	            	
            },
            yAxis: {
            	axisLine: {
	                show: false
	            },
	            axisLabel: {
	                show: true,
	                 textStyle: {
	                   color: '#fff',  //更改坐标轴文字颜色	    	                   
	                 }
	              },
	            axisTick:{
	            	show: false		   //显示/隐藏刻度
	            },
            },
            series: [
            	{
                	type: 'bar',
                	barWidth:15,
				    itemStyle: {
				        normal: {
				        	color:'#08979c',
				            label: {
				                show: true,
				                position: 'top',
				                textStyle: {
				                    color: '#fff'
				                }
				            }
				        }
				    }
                },
                {
                	type: 'bar',
                	barWidth:15,
				    itemStyle: {
				        normal: {
				        	color:'#ff9c31',
				        	/*color:function(){
				        		for(var i = 0;i<eachProdProgress.length;i++){
				        		if(eachProdProgress[i].actual<Math.ceil(eachProdProgress[i].plan/3)){
				        			return 'red';
				        		}else{
				        			return 'yellow';
				        		}				        		
				        	}				        
				        	},*/
				            label: {
				                show: true,
				                position: 'top',
				                textStyle: {
				                    color: '#fff'
				                }
				            }
				        }
				    }
                }
            ]	           	

	};
	// 绘制图表
	zhuChart.setOption(zhuOption); 
}
//获取饼图
function getBingChart(valA,valB){
	if (bingChart != null && bingChart != "" && bingChart != undefined) {
		bingChart.dispose();
    }
	bingChart = echarts.init(document.getElementById('allBingChart'));
	var bingOption = {
			title:{
				text:'生产总进度',
				x:20,
		        y:5,
		        textStyle: {				
		        	fontSize: 20,
	                color:'#fff'
	            },
			},
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        // orient: 'vertical',
		        // top: 'middle',
		        bottom: 10,
		        left: 5,
		        data: ['已完成','未完成'],
		        textStyle: { //图例文字的样式
                    color: '#fff'                    
                },
		    },
		    color:['#08979c','#fb6a7b'],
		    series : [
		        {
		            type: 'pie',
		            radius : '60%',
		            center: ['40%', '50%'],
		            selectedMode: 'single',
		            data:[
		                {	
		                	
		                    value:valA,
		                    name: '已完成',
		                    label: {
		                    	show: true, 
	                            formatter: '{b} : {c} ({d}%)',
	                            labelLine :{show:true}, 
		                        normal: {
		                        	show:true,
		                        	position: 'inner',
			                        	 textStyle : {
			                                 color:'#fff',
			                                 fontSize : 16    //文字的字体大小
			                             },
			                             formatter:'{d}%'
		                        }
		                    }
		                },
		                {
		                	
		                	value:valB, 
		                	name: '未完成',
		                	label: {
		                		show: true, 
	                            formatter: '{b} : {c} ({d}%)',
	                            labelLine :{show:true}, 
		                        normal: {
		                        	show:true,
		                        	position: 'inner',
			                        	textStyle : {
			                                 color:'#fff',
			                                 fontSize : 16    //文字的字体大小
			                        	},	
			                        	 formatter:'{d}%'
		                            		                        
		                        }
		                    }
		                },		   
		            ],
		            /*itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }*/
		        }
		    ]
		};
		// 绘制图表
		bingChart.setOption(bingOption); 
}
//获取url参数
function getQueryString(name) {
	var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
		    return "";
		}
	return result[1];
}