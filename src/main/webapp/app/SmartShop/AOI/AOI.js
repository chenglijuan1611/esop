//页面初始化
$(function(){	
	getAOIChartZhu();
	var timeout1 = setTimeout(function(){				//每隔十分钟重新请求数据一次
		getAOIChartZhu();
	    setTimeout(arguments.callee,60000);
	},60000);
});
/*显示AOI不良情况饼图*/
/*function getAOIChartBing(){
	var myChart = echarts.init(document.getElementById('aoiChartBing'));
	$.ajax({
		url:'http://172.16.60.253:8080/dafeng_mes/plan3/listNGForAOI',
		type:'post',
		cache:false,
		success: function(data){
			console.log(data);
			var num = data.num;									//总体情况
			var data = data.data;								//具体情况
			var option = {
				    title : {
				        text: 'AOI不良情况统计',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient : 'vertical',
				        x : 'left',
				        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {
				                show: true, 
				                type: ['pie', 'funnel'],
				                option: {
				                    funnel: {
				                        x: '25%',
				                        width: '50%',
				                        funnelAlign: 'left',
				                        max: 1548
				                    }
				                }
				            },
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    series : [
				        {
				            name:'aoi不良类型',
				            type:'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:data
				        }
				    ]
				};
			myChart.setOption(option);
		},
		error:function(error){
			console.log(error)
		}
	})
	var mydata = {"num":{"NGNUM":199,"rate":"0.80994‰","NUM":245696},"data":[{"name":"短路","value":7332},{"name":"其他","value":1222},{"name":"缺件","value":944},{"name":"虚焊","value":179},{"name":"错件","value":143},{"name":"立碑","value":121},{"name":"极性","value":103},{"name":"少锡","value":66},{"name":"偏移","value":65},{"name":"翻面","value":42},{"name":"锡洞","value":42},{"name":"多锡","value":32},{"name":"破损","value":24},{"name":"污染","value":23},{"name":"多件","value":16}]}
	var num = mydata.num;									//总体情况
	var data = mydata.data;								//具体情况
	var option = {
		    title : {
		        text: 'AOI不良情况统计',
		        x:'center',
		        textStyle:{
		        	color:'#fff'
		        }
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'aoi不良类型',
		            type:'pie',
		            radius : '60%',
		            center: ['50%', '60%'],
		            data:data,
		            itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                         },
                        normal:{
                            color:function(params) {
                            //自定义颜色
                            var colorList = [          
                                    '#00FFFF', '#00FF00', '#FFFF00', '#FF8C00', '#FF0000', '#FE8463',
                                ];
                                return colorList[params.dataIndex]
                             }
                        }
                  }
		        }
		    ]
		};
	myChart.setOption(option);	
}
*/
var urlHeader ='http://' + window.location.host;
var rate;
var myChart1;
var myChart2;
/*获取柱状图*/
function getAOIChartZhu(){
	if (myChart1 != null && myChart1 != "" && myChart1 != undefined) {
		myChart1.dispose();
    }
// 基于准备好的dom，初始化echarts实例
	myChart1 = echarts.init(document.getElementById('aoiChartZhu'));
	$.ajax({
		url: urlHeader + '/dafeng_mes/plan3/listNGForAOI',
		type:'post',
		cache:false,
		success: function(data){
			console.log(data);
			rate = data.num.rate;									//总体情况
			console.log(rate);
			getCurrentTime(1572316242);
			var data = data.data;										//具体情况
			var xdata = [];
			var ydata = [];
			$.each(data,function(index,item){
				xdata.push(item.name);
				ydata.push(item.value);
			})
			console.log(xdata);
			console.log(ydata);
			var option = {
					title : {
				        text: 'AOI不良情况分类',
				        x:'center',
				        y:10,
				        textStyle:{
				        	color:'#fff',
				        	fontSize:25
				        }
				    },
		            gird:{
		            	top:'50'
		            },
		            xAxis: {
		                data: xdata,
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
			            }
		            },
		            series: [{
		                name: '不良类型',
		                type: 'bar',
		                data: ydata,
						barWidth : 20,
						//配置样式
						itemStyle: {   
							//通常情况下：
							normal:{  
								color:'#fadb14',
		/*		　　　　　　　　　　　//每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
								color: function (params){
									var colorList = ['#ff7875','#ff9c6e','#ffc069','#d3f261','#5cdbd3','#69c0ff','#b37feb','#ffd666'];
									return colorList[params.dataIndex];
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
						}]
		        };
			myChart1.setOption(option);
			getAOIChartYiBiao();
		},
		error:function(error){
			console.log(error)
		}
	})
	
}
/*获取仪表图*/
function getAOIChartYiBiao(){
	if (myChart2 != null && myChart2 != "" && myChart2 != undefined) {
		myChart2.dispose();
    }
	myChart2 = echarts.init(document.getElementById('aoiChartYiBiao'));
	rate = Math.floor(parseFloat(rate)/1000*1000000);
	//总体情况
	console.log("-----------");
	console.log(rate);
	console.log("-----------");
	var option = {
			title : {
		        text: 'AOI不良总数占比',
		        x:'center',
		        y:10,
		        textStyle:{
		        	color:'#fff',
		        	fontSize:25
		        }
		    },
		    gird:{
		    	
		    },
		    series : [
		        {
		            name:'AOI不良率',
		            type:'gauge',
		            max:100000,
		            min:0,
		            startAngle: 180,
		            itemStyle:{
		            	 color: 'rgba(255,255,255,0.6)'
	                },
		            endAngle: 0,
		            center : ['50%', '90%'],    // 默认全局居中
		            radius : 200,
		            axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.09, 'lime'],[0.82, '#1e90ff'],[1, '#ff4500']],
                            width: 3,
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
		            axisTick: {            // 坐标轴小标记
		                splitNumber: 10,    // 每份split细分多少段
		                length :12,        // 属性length控制线长
		            },
		            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
		                formatter: function(v){
		                    switch (v+''){
		                        case '10': return '低';
		                        case '50': return '中';
		                        case '90': return '高';
		                        default: return '';
		                    }
		                },
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    color: '#fff',
		                    fontSize: 15		                  
		                }
		            },
		            pointer: {
		                width:15,
		                length: '60%',		                		                
		            },
		            title : {
		                show : true,
		                offsetCenter: [0, '-60%'],       // x, y，单位px
		                textStyle: {       				 // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    color: '#fff',
		                    fontSize: 30
		                }
		            },
		            detail : {
		                show : true,
		                backgroundColor: 'rgba(0,0,0,0)',
		                borderWidth: 0,
		                borderColor: '#ccc',
		                width: 105,
		                height: 40,
		                offsetCenter: [0, -50],       // x, y，单位px
		                formatter:'{value}PPM',
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontSize : 30,
		                    color:'#fff'
		                }
		            },
		            data:[{value: rate, name: 'AOI不良率'}]
		        }
		    ]
		};	                    
	myChart2.setOption(option);	
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
          $(".time").text(myTime);
          //return myTime;          
      }
}