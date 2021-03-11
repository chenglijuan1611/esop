
var locationUrl = 'http://'+window.location.host; 									//公有请求地址
/*---主页面---*/
//页面加载完成执行方法块
$(function(){
	$(".usernameHide").val((GetQueryString("USERNAME")));    		                //从url中获取用户ID并存储到隐藏域
	$(".userName").html((GetQueryString("NAME")));		     		                //展示当前登录的用户名信息		
	getMenuList();                                           		                //获取左侧权限菜单列表															//获取计划完成情况
	var currentTime = getCurrentTime();								                //获取当前时间用于页面数据的初始化
	$(".timepick").val(currentTime);												//设置检索条件中的时间选择器的默认值是当前时间	
});	
/*刷新页面*/
function fresh(){
	location.reload();
}
/*切换用户*/
function changeUser(){
	$.cookie('userid', null);
	$.cookie('password', null);
	$.ajax({		
		url:locationUrl + '/dafeng_mes/android/appLogout',
		type:'post',
		cache:false,
		success: function(data){
			console.log(data);
			if(data.resCode == "0"){
				window.location.href = "login.html";
			}			
		},
		error:function(error){
			console.log(error)
		}
	})	
}
/*请求数据核心方法loadList,参数规定如下：
 * @pno:第几页
 * @url：请求的地址
 * @menuname：当前的菜单名
 * @startTime：时间过滤-开始时间
 * @endTime：时间过滤-终止时间
 * @flag：判断用户是否点击了菜单栏
 * @likeName：检索条件中的模糊查询关键词
*/
function loadList(pno,url,menuname,startTime,endTime,flag,likeName){
	$(".planList").hide();												//隐藏每个计划的统计图
	$(".echarts_totalBrowse").hide();											//隐藏首页计划完成情况的统计图
	$(".echarts_workshopBrowse").hide();										//隐藏车间生产计划的统计图
	$(".echarts_outAmountAnalyse").hide();										//隐藏产量分析的统计图
	$(".echarts_outValueAnalyse").hide();										//隐藏产值分析的统计图
	$(".shopMenu").hide();														//隐藏面包屑导航
	$(".firstShopMenu,.secondShopMenu,.thirdShopMenu").hide();					//隐藏面包屑导航
	if(flag == '3'){															//判断，当flag = 3 的时候（即用户点击了菜单栏里的某一项菜单，我会传过来“3”）
		startTime = getCurrentTime();											//起始时间设置为当前时间
		endTime = getCurrentTime();												//结束时间设置为当前时间
		$(".startTimeHide").val(startTime);										//起始时间保存到隐藏域中待用
		$(".endTimeHide").val(endTime);											//结束时间保存到隐藏域中待用
		$(".likeSearchHide").val(likeName);										//模糊查询关键字保存到隐藏域中待用
	}else if(flag == '5'){														//点击“查询”按钮时，flag我会传过来一个“5”，为了将当前页码重置为“1”
		$(".now").text("1");
	}
	$(".backIndex").show();
	$(".outputAnalyse").hide();
	$(".echartsArea").hide();
	$(".dataList").show();	
	$(".pages").show();															//底部分页显示
	$(".timeSearch").show();													//时间选择器显示
	$(".conditionSearch").hide();												//条件选择器隐藏
/*	console.log("%%%%%%%%%%%%%%%%%%%%%%%%%%");
	console.log(pno);
	console.log(url);
	console.log(menuname);
	console.log(startTime);
	console.log(endTime);
	console.log(flag);
	console.log("%%%%%%%%%%%%%%%%%%%%%%%%%%");*/
/*点击菜单栏的产量分析*/	
if(url == "mes/output.do"){
	$(".pages").hide();
	$(".timeSearch").hide();
	outputAnalyse();
}	
/*当天生产情况查询模块开始（根据url == "mes/dailyProduction.do"）判断用户是否选择了当天生产情况查询*/
if(url == "mes/dailyProduction.do"){
	$("body").removeClass("sidebar-open");
	$(".pages").hide();										//当天生产计划不需要分页，分页隐藏
	$(".timeSearch").hide();								//当天生产计划不需要按时间查询，时间选择器隐藏
	$(".conditionSearch").show();							//当天生产计划需要按条件查询，条件选择器显示
	$(".menuName").text(menuname);                          //右侧数据头部展示当前的菜单名
	$(".menuNameHide").val(menuname);					    //将当前选择的菜单名保存到隐藏域待用
	$(".menuUrl").val(url);									//将当前选择的菜单url保存到隐藏域待用
	$(".dataList").html("");								
	var theDayData = "";									//声明http请求数据，根据不同条件进行赋值
	var classification = $(".condition").val();
	console.log(classification);
	if(flag == "1"){										//flag=1意味着出次查询当天的生产情况
		$(".condition").val("");
		theDayData = { 'pageNum': 1,'pageSize': 4};			//初次查询不需要CLASSIFICATION参数
	}else{
		theDayData = { 'pageNum': 1,'pageSize': 4,'CLASSIFICATION':classification};    //非初次，按条件查询
	}
	/*发送请求查询当天生产情况*/
	$.ajax({
		url:locationUrl + '/dafeng_mes/'+ url,
		type:'post',
		data: theDayData,
		cache:false,
		success: function(data){
			console.log(data);
/*			console.log("---------当天生产情况-----------");
			console.log(JSON.stringify(data));
			console.log("--------------------");*/
			var append0 = "";
			var theDayDataList = data.varList;
			console.log(theDayDataList);
			if(theDayDataList == ""){
				append0 = append0 + "<span>暂无计划</span>";
				$(".pages").hide();	
			}
			if(flag == "1"){                                               //flag=1意味着初次查询当天的生产情况
				$.each(theDayDataList, function(i, item) {
					append0 = append0 + "<div class='orderMsg container'>";
						append0 = append0 + "<div class='row'>";
						append0 = append0 +	"<div class='col-xs-12 col-sm-6 col-md-8'><span class='orderNumTitle'><strong>名称</strong></span>&nbsp;&nbsp;<span class='orderNum'>"+item.PRODUCT_SPECIFICATIONS+"</span></div>";
						append0 = append0 +	"<div class='col-xs-6 col-md-4'><span class='nameTitle'><strong>订单号</strong></span>&nbsp;&nbsp;<span class='name'>"+item.PRODUCT_ID+"</span></div>";
						append0 = append0 + "</div>";
						append0 = append0 + "<div class='row'>";
						append0 = append0 +	"<div class='col-xs-6 col-md-4'><span class='codeTitle'><strong>计划数量</strong></span>&nbsp;&nbsp;<span class='code'>"+item.PLAN_AMOUNT+"</span></div>";
						append0 = append0 +	"<div class='col-xs-6 col-md-4'><span class='planAmountTitle'><strong>实际数量</strong></span>&nbsp;&nbsp;<span class='planAmount'>"+item.PRODUCT_AMOUNT+"</span></div>";
						append0 = append0 + "<div class='row container'>";
						append0 = append0 +	"<div class='col-xs-6'><span class='startTimeTitle'><i class='fa fa-calendar' style='color:green;'></i>&nbsp;&nbsp;起</span>&nbsp;&nbsp;<span class='startTime'>"+item.PLAN_TIME+"</span></div>";
						append0 = append0 + "</div>";
						append0 = append0 + "<div class='row container'>";
						append0 = append0 +	"<div class='col-xs-12 col-md-4'>";
						append0 = append0 + "<div class='progress progress-striped progressOfMyStyle'>";
							append0 = append0 + "<div class='progress-bar progress-bar-success progressOfMyStyleInner' role='progressbar';aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: "+item.PERCENT+"%;'>"+item.PERCENT+"%";
								append0 = append0 + "<span class='sr-only'>90% 完成（成功）</span>";
							append0 = append0 + "</div>";
						append0 = append0 + "</div>";
						append0 = append0 +	"</div>";						
						append0 = append0 +	"</div>";						
						append0 = append0 + "</div>";							
				   append0 = append0 + "</div>";	
		        });
			}else if(classification == "2"){                                //classification =2表示按产线查询
				$.each(theDayDataList, function(i, item) {
					append0 = append0 + "<div class='orderMsg container'>";
					append0 = append0 + "<div class='row'>";
					append0 = append0 +	"<div class='col-xs-12 col-sm-6 col-md-8'><span class='orderNumTitle'><strong>产线名称</strong></span>&nbsp;&nbsp;<span class='orderNum'>"+item.LINENAME+"</span></div>";
					append0 = append0 + "</div>";
					append0 = append0 + "<div class='row'>";
					append0 = append0 +	"<div class='col-xs-6 col-md-4'><span class='codeTitle'><strong>计划数量</strong></span>&nbsp;&nbsp;<span class='code' style='color:orange;'>"+item.PLAN_AMOUNT+"</span></div>";
					append0 = append0 +	"<div class='col-xs-6 col-md-4'><span class='planAmountTitle'><strong>实际数量</strong></span>&nbsp;&nbsp;<span class='planAmount' style='color:green;'>"+item.PRODUCT_AMOUNT+"</span></div>";
					append0 = append0 + "</div>";			
					append0 = append0 + "<div class='row'>";
					append0 = append0 +	"<div class='col-xs-12 col-md-4'>";
					append0 = append0 + "<div class='progress progress-striped progressOfMyStyle'>";
						append0 = append0 + "<div class='progress-bar progress-bar-success progressOfMyStyleInner' role='progressbar';aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: "+item.PERCENT+"%;'>"+item.PERCENT+"%";
							append0 = append0 + "<span class='sr-only'>90% 完成（成功）</span>";
						append0 = append0 + "</div>";
					append0 = append0 + "</div>";
					append0 = append0 +	"</div>";						
					append0 = append0 +	"</div>";
					append0 = append0 + "</div>";	
		        });
			}else if(classification == "3"){								//classification =3表示按车间查询		
				$.each(theDayDataList, function(i, item) {
					append0 = append0 + "<div class='orderMsg container'>";
					append0 = append0 + "<div class='row'>";
					append0 = append0 +	"<div class='col-xs-12 col-sm-6 col-md-8'><span class='orderNumTitle'><strong>车间名称</strong></span>&nbsp;&nbsp;<span class='orderNum'>"+item.WORKSHOP+"</span></div>";
					append0 = append0 + "</div>";
					append0 = append0 + "<div class='row'>";
					append0 = append0 +	"<div class='col-xs-6 col-md-4'><span class='codeTitle'><strong>计划产量</strong></span>&nbsp;&nbsp;<span class='code' style='color:orange;'>"+item.PLAN_AMOUNT+"</span></div>";
					append0 = append0 +	"<div class='col-xs-6 col-md-4'><span class='planAmountTitle'><strong>实际产量</strong></span>&nbsp;&nbsp;<span class='planAmount' style='color:green;'>"+item.PRODUCT_AMOUNT+"</span></div>";
					append0 = append0 + "</div>";			
					append0 = append0 + "<div class='row'>";
					append0 = append0 +	"<div class='col-xs-12 col-md-4'>";
					append0 = append0 + "<div class='progress progress-striped progressOfMyStyle'>";
						append0 = append0 + "<div class='progress-bar progress-bar-success progressOfMyStyleInner' role='progressbar';aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: "+item.PERCENT+"%;'>"+item.PERCENT+"%";
							append0 = append0 + "<span class='sr-only'>90% 完成（成功）</span>";
						append0 = append0 + "</div>";
					append0 = append0 + "</div>";
					append0 = append0 +	"</div>";						
					append0 = append0 +	"</div>";
					append0 = append0 + "</div>";	
		        });
			}	
			$(".dataList").append(append0);	
		},
		error:function(error){
			console.log(error)
		}
	})	
	return false;
}
/*当天生产情况查询模块结束*/
/*ERP/MES查询模块开始*/
	/*通过flag验证是否点击了左侧菜单栏*/
	if(flag == "3"){
		$("body").removeClass("sidebar-open");
	    $(".now").text("1");
	    $(".likeSearch").val("");
	    var thisCurrentTime = getCurrentTime()
	    $(".timepick").val(thisCurrentTime);
	}	
	$(".menuName").text(menuname);                         //右侧数据头部展示当前的菜单名
	$(".menuNameHide").val(menuname);					   //将当前选择的菜单名保存到隐藏域待用
	$(".menuUrl").val(url);								   //将当前选择的菜单地址保存到隐藏域待用
	$(".dataList").html("");
	var likeName_first = $(".likeSearchHide").val();	   //获取模糊查询的关键字
	var totalPage = 1;                                     //总共多少页
	var totalRecords = 1;                                  //总共多少条
	var pageSize = 4;                                      //每页显示多少条	
	var loadListUrl = locationUrl+"/dafeng_mes/"+url;	   //请求地址
	/*发送请求获取ERP/MES查询数据*/
	$.ajax({
		type:"post",
	    url:loadListUrl,	   
		dataType: "json",
		cache:false,
		data: { 'pageNum': pno,'pageSize': pageSize,'startTime':startTime,'endTime':endTime,'likeName':likeName},
		beforeSend:function(){
			$("#loading").show();
		},
		success:function(result) {
/*			console.log(result);
			console.log(JSON.stringify(result));*/
			$("#loading").hide();
			var append1 = "";													//append1用于拼接ERP生产计划查询结果(自定义的)
			if (result) {
				var count = result.total;
                var dataList = result.varList;
                if(dataList == ""){												//如果没有数据，向页面展示“暂无计划”
    				append1 = append1 + "<span>暂无计划</span>";
    				$(".pages").hide();	
    			}
                totalRecords = count;
                totalPage = Math.ceil(count / pageSize);                
                if(url == "mes/plan.do"){                                        //ERP生产计划查询
					$.each(dataList, function(i, item) {
						append1 = append1 + "<div class='orderMsg container'>";
						append1 = append1 + "<div class='row'>";
						append1 = append1 +	"<div class='col-xs-12 col-sm-6 col-md-8'><span class='orderNumTitle'><strong>名称</strong></span>&nbsp;&nbsp;<span class='orderNum'>"+item.invStd+"</span></div>";
						append1 = append1 +	"<div class='col-xs-6 col-md-4'><span class='nameTitle'><strong>订单号</strong></span>&nbsp;&nbsp;<span class='name'>"+item.MoCode+"</span></div>";
						append1 = append1 + "</div>";
						append1 = append1 + "<div class='row'>";
						append1 = append1 +	"<div class='col-xs-6 col-md-4' ><span class='codeTitle'><strong>编码</strong></span>&nbsp;&nbsp;<span class='code'>"+item.invCode+"</span></div>";
						append1 = append1 +	"<div class='col-xs-6 col-md-4' ><span class='planAmountTitle'><strong>计划产量</strong></span>&nbsp;&nbsp;<span class='planAmount'>"+item.qty+"</span></div>";
						append1 = append1 + "</div>";
						append1 = append1 + "<div class='row'>";
						append1 = append1 +	"<div class='col-xs-6'><span class='startTimeTitle'><i class='fa fa-calendar' style='color:green;'></i>&nbsp;&nbsp;起</span>&nbsp;&nbsp;<span class='startTime'>"+item.startdate+"</span></div>";
						append1 = append1 +	"<div class='col-xs-6'><span class='endTimeTitle'><i class='fa fa-calendar' style='color:red;'></i>&nbsp;&nbsp;终</span>&nbsp;&nbsp;<span class='endTime'>"+item.DueDate+"</span></div>";
						append1 = append1 + "</div>";			
						append1 = append1 + "</div>";	
			        });	
                }else if(url == "mes/plan1.do"){                                 //MES生产计划查询
                	dataList = result.vaList;
                	$.each(dataList, function(i, item) {
    					append1 = append1 + "<div class='orderMsg container'>";
    					append1 = append1 + "<div class='row'>";
    					append1 = append1 +	"<div class='col-xs-12 col-sm-6 col-md-8'><span class='productNameTitle'><strong>名称</strong></span>&nbsp;&nbsp;<span class='productName'>"+item.PRODUCT_SPECIFICATIONS+"</span></div>";
    					append1 = append1 +	"<div class='col-xs-6 col-md-4'><span class='productLineNameTitle'><strong>产线名称</strong></span>&nbsp;&nbsp;<span class='productLineName'>"+item.NAME+"</span></div>";
    					append1 = append1 + "</div>";
    					append1 = append1 + "<div class='row'>";
    					append1 = append1 +	"<div class='col-xs-6 col-md-4' ><span class='planAmountTitle'><strong>计划产量</strong></span>&nbsp;&nbsp;<span class='planAmount'>"+item.PLAN_AMOUNT+"</span></div>";
    					append1 = append1 +	"<div class='col-xs-6 col-md-4' ><span class='actrueAmountTitle'><strong>实际产量</strong></span>&nbsp;&nbsp;<span class='actrueAmount'>"+item.PRODUCT_AMOUNT+"</span></div>";
    					append1 = append1 + "</div>";
    					append1 = append1 + "<div class='row'>";
    					append1 = append1 +	"<div class='col-xs-6'><span class='planTimeTitle'><i class='fa fa-calendar' style='color:green;'></i>&nbsp;&nbsp;起始</span>&nbsp;&nbsp;<span class='planTime'>"+item.PLAN_TIME+"</span></div>";
    					if(item.STATUS == "生产中"){
    						append1 = append1 +	"<div class='col-xs-6'><span class='statusTitle'><i class='fa fa-circle' style='color:orange;'></i>&nbsp;&nbsp;状态</span>&nbsp;&nbsp;<span class='status'>"+item.STATUS+"</span></div>";
    					}else if(item.STATUS == "计划完成"){
    						append1 = append1 +	"<div class='col-xs-6'><span class='statusTitle'><i class='fa fa-circle' style='color:green;'></i>&nbsp;&nbsp;状态</span>&nbsp;&nbsp;<span class='status'>"+item.STATUS+"</span></div>";
    					}else if(item.STATUS == "未执行"){
    						append1 = append1 +	"<div class='col-xs-6'><span class='statusTitle'><i class='fa fa-circle' style='color:red;'></i>&nbsp;&nbsp;状态</span>&nbsp;&nbsp;<span class='status'>"+item.STATUS+"</span></div>";
    					}    					
    					append1 = append1 + "</div>";
    					append1 = append1 + "</div>";	
    		        });	
//                	};
                }
				$(".dataList").append(append1);
				$('.total').text(totalPage); 
				$('.count').text(count); 
				/*配置分页内容*/
				$('.M-box').pagination({
					pageCount: totalPage,
					current:pno,                     //当前第几页
					jump: false,
					coping: true,
					homePage: '首',
					endPage: '末',
					mode: 'fixed',
					num_display_entries:5,
					items_per_page:5,
					prevContent: '上一页',
					nextContent: '下一页',
					callback:PageClick
				});
			}			
		},
        error: function () {
        		$("#loading").hide();
				alert('网络连接异常，请重试')
        }
	});
/*ERP/MES查询模块结束*/
};
/*loadList方法体结束*/
//分页回调函数  
PageClick = function(index){
	$(".dataList").html("");
	var thisStartTime = $(".startTimeHide").val();        //开始时间（用于翻页）
	var thisEndTime = $(".endTimeHide").val();			  //结束时间（用于翻页）
	var thisMenuName = $(".menuNameHide").val();		  //菜单名称（用于翻页）
	var thisMenuUrl = $(".menuUrl").val();				  //菜单地址（用于翻页）
    var likeName_callback = $(".likeSearchHide").val();	  //模糊查询关键字（用于翻页）
	$('.now').text(index.getCurrent()); 
	loadList(index.getCurrent(),thisMenuUrl,thisMenuName,thisStartTime,thisEndTime,"0",likeName_callback);           //点击分页加载列表数据  */
}
/*获取左侧菜单列表*/
function getMenuList(){
	var url = locationUrl+"/dafeng_mes/android/getMenu";
	var username = $(".usernameHide").val();                               //后端要的，用于权限方面
	var data = {USERNAME:username};
	var currentTimeMoren = getCurrentTime();                               //当前时间参数，用于展示数据（点击左侧菜单栏任一菜单默认展示当天数据）
	$.ajax({
		url:url,
		type:'post',
		cache:false,
		data:data,
		success: function(data) {
			getPlanCompeleteCharts();
			getOutAmountCharts();
			outputValueAnalyse();
			console.log("..............");
			console.log(data);
			console.log("..............");
			var leftMenuArray = data.appMenuList;
			var append = "";
			//遍历数组菜单对象，生成菜单
			$.each(leftMenuArray,function(index,item){
				if(item.hasMenu){
						//在这里传递给loadList方法的flag设置为“3”，表示“点了菜单栏中的某一菜单”
						append = append + "<li><a onclick=\"loadList('"+1+"','"+item.MENU_URL+"','"+item.MENU_NAME+"','"+currentTimeMoren+"','"+currentTimeMoren+"','"+3+"')\" href='javascript:void(0);'><i class="+item.MENU_ICON+"></i><span>"+item.MENU_NAME+"</span></a></li>";
				}		
			});
			$(".sidebar-menu").append(append);			
		},
		error:function(error){
			console.log(error)
		}
	})		
}
/*获取当前日期*/
function getCurrentTime(){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(month<10) {
		month='0'+month
	}
	var da = date.getDate();
	if(da<10) {
		da='0'+da
	}
	var dateString = year + "-" + month + "-" + da;
	$(".timepick").val(dateString);                                 //设置时间选择框的默认日期为当天
	return dateString;	
}
/*按时间进行检索(点击‘查询’按钮执行该方法，包括了模糊查询)*/
function searchAsTime(){
  	var seachUrl = $(".menuUrl").val();                             		//获取请求地址
	var startTime = $(".startTime").val();	                        		//获取起始时间
	var startTimeSave = $(".startTimeHide").val(startTime);	        		//将起始时间保存于隐藏域待用
	var endTime = $(".endTime").val();                             		 	//终止时间
	var endTimeSave = $(".endTimeHide").val(endTime);						//将终止时间保存于隐藏域待用
	var menunameOfTiem = $(".menuNameHide").val();							//菜单名称
	var likeName = $(".likeSearch").val();									//关键字查询
	loadList("1",seachUrl,menunameOfTiem,startTime,endTime,"5",likeName);   //参数"1"表示默认显示第一页
}
/*按条件进行检索(点击‘查询’按钮执行该方法)*/
function searchAsCondition(){
	var menuName = $(".menuNameHide").val();	
  	loadList("1","mes/dailyProduction.do",menuName,"","","5");    //参数"1"表示默认显示第一页
}
/*点击菜单栏中的产量分析*/
function outputAnalyse(){                               
	$(".backIndex").show();									//显示面包屑导航
	$(".outputAnalyse").show();								//显示产量分析统计图
	var myChart_day = echarts.init(document.getElementById('main_day'));
	myChart_day.showLoading({ text: '正在加载数据' });  //增加等待提示 
	var myChart_month = echarts.init(document.getElementById('main_month')); 
	myChart_month.showLoading({ text: '正在加载数据' });  //增加等待提示 
    var myChart_year = echarts.init(document.getElementById('main_year'));	
    myChart_year.showLoading({ text: '正在加载数据' });  //增加等待提示 
	var data = {CLASSIFICATION:"1"};
	$.ajax({
		url:locationUrl+'/dafeng_mes/mes/output1',
		type:'get',
		cache:false,
		data:data,
		success: function(data){			
			/*console.log(data);
			console.log("产量分析");
			console.log(JSON.stringify(data));
			console.log("产量分析");*/
			var theDayAmount = data.varList;
			var theMonthAmount = data.varList1;
			var theYearAmount = data.varList2;
			//日产量数据
			var planAmountOfDay = theDayAmount[0].PLAN_AMOUNT;
			var actualAmountOfDay = theDayAmount[0].PRODUCT_AMOUNT;
			var planAmountOfTheLastDay = theDayAmount[1].PLAN_AMOUNT;
			var actualAmountOfTheLastDay = theDayAmount[1].PRODUCT_AMOUNT;
			var planAmountOfPrevDay = theDayAmount[2].PLAN_AMOUNT;
			var actualAmountOfPrevDay = theDayAmount[2].PRODUCT_AMOUNT;
			//月产量数据
			var planAmountOfMonth = theMonthAmount[0].PLAN_AMOUNT;
			var actualAmountOfMonth = theMonthAmount[0].PRODUCT_AMOUNT;
			var planAmountOfTheLastMonth = theMonthAmount[1].PLAN_AMOUNT;
			var actualAmountOfTheLastMonth = theMonthAmount[1].PRODUCT_AMOUNT;
			var planAmountOfPrevMonth = theMonthAmount[2].PLAN_AMOUNT;
			var actualAmountOfPrevMonth = theMonthAmount[2].PRODUCT_AMOUNT;
			//年产量数据
			var planAmountOfYear = theYearAmount[0].PLAN_AMOUNT;
			var actualAmountOfYear = theYearAmount[0].PRODUCT_AMOUNT;
			var planAmountOfTheLastYear = theYearAmount[1].PLAN_AMOUNT;
			var actualAmountOfTheLastYear = theYearAmount[1].PRODUCT_AMOUNT;
			var planAmountOfPrevYear = theYearAmount[2].PLAN_AMOUNT;
			var actualAmountOfPrevYear = theYearAmount[2].PRODUCT_AMOUNT;			
			//日产量分析
			 var option_day = {
			            title: {
			            	text: '产量分析(日)',
		                    x:'center',
		                    y: 10,
		                    textStyle: {
		                    	color:'#000',
            	                fontFamily: 'Arial, Verdana, sans...',
            	                fontSize: 12,
            	                fontStyle: 'normal',
            	                fontWeight: 'normal'
		                    },	
			            },
			            grid:{
		                    x:50                   
		                },
		                legend: {
		                	x:200,
		                	y:10		                	
		                },
			            tooltip: {},
			            dataset: {
			                // 这里指定了维度名的顺序，从而可以利用默认的维度到坐标轴的映射。
			                // 如果不指定 dimensions，也可以通过指定 series.encode 完成映射，参见后文。
			                dimensions: ['product', '计划', '实际'],
			                source: [
			                    {product: '前天', '计划': planAmountOfPrevDay, '实际': actualAmountOfPrevDay},
			                    {product: '昨天', '计划': planAmountOfTheLastDay, '实际': actualAmountOfTheLastDay},
			                    {product: '今天', '计划': planAmountOfDay, '实际': actualAmountOfDay}
			                ]
			            },
			            xAxis: {
			            	type: 'category',
			            	axisLine: {
		    	                show: false
		    	            },
		    	            axisLabel: {
		    	                show: true,
		    	                 textStyle: {
		    	                   color: '#000',  //更改坐标轴文字颜色	    	                   
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
		    	                   color: '#000',  //更改坐标轴文字颜色	    	                   
		    	                 }
		    	              },
		    	            axisTick:{
		    	            	show: false		   //显示/隐藏刻度
		    	            }
			            },
			            series: [
			                {
			                	type: 'bar',
			                	itemStyle: {
							        normal: {
							        	color:'#531dab',
							            label: {
							                show: true,
							                position: 'top',
							                textStyle: {
							                    color: '#000'
							                }
							            }
							        }
							    }
			                },
			                {
			                	type: 'bar',
			                	itemStyle: {
							        normal: {
							        	color:'#ffc53d',					        	
							            label: {
							                show: true,
							                position: 'top',
							                textStyle: {
							                    color: '#000'
							                }
							            }
							        }
							    }
				                			               			                
			                }
			            ]
			        }; 
		       // 月产量分析
		        var option_month = {
		        		title: {
		                    text: '(月)',
		                    x:'center',
		                    y: 10,
		                    textStyle: {
            	                fontFamily: 'Arial, Verdana, sans...',
            	                fontSize: 12,
            	                fontStyle: 'normal',
            	                fontWeight: 'normal',
		                    },		                    	
		                },
		                grid:{
		                    x:70,                   
		                },
		                tooltip: {},
		                dataset: {
			                // 这里指定了维度名的顺序，从而可以利用默认的维度到坐标轴的映射。
			                // 如果不指定 dimensions，也可以通过指定 series.encode 完成映射，参见后文。
			                dimensions: ['product', '计划', '实际'],
			                source: [ 
			                    {product: '前一月', '计划': planAmountOfPrevMonth, '实际': actualAmountOfPrevMonth},
			                    {product: '上一月', '计划': planAmountOfTheLastMonth, '实际': actualAmountOfTheLastMonth},
			                    {product: '当    月', '计划': planAmountOfMonth, '实际': actualAmountOfMonth}
			                ]
			            },
			            xAxis: {
			            	type: 'category',
			            	axisLine: {
		    	                show: false
		    	            },
		    	            axisLabel: {
		    	                show: true,
		    	                 textStyle: {
		    	                   color: '#000',  //更改坐标轴文字颜色	    	                   
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
		    	                   color: '#000',  //更改坐标轴文字颜色	    	                   
		    	                 }
		    	              },
		    	            axisTick:{
		    	            	show: false		   //显示/隐藏刻度
		    	            }
		                },
		                series: [
			                {
			                	type: 'bar',
			                	itemStyle: {
							        normal: {
							        	color:'#531dab',
							            label: {
							                show: true,
							                position: 'top',
							                textStyle: {
							                    color: '#000'
							                }
							            }
							        }
							    }
			                },
			                {
			                	type: 'bar',
			                	itemStyle: {
							        normal: {
							        	color:'#ffc53d',					        	
							            label: {
							                show: true,
							                position: 'top',
							                textStyle: {
							                    color: '#000'
							                }
							            }
							        }
							    }
				                			               			                
			                }
			            ]
		         };		       
		        //年产量分析
		        var option_year = {
		        		title: {
		        			text: '(年)',
		                    x:'center',
		                    y: 10,
		                    textStyle: {
            	                fontFamily: 'Arial, Verdana, sans...',
            	                fontSize: 12,
            	                fontStyle: 'normal',
            	                fontWeight: 'normal',
		                    },	
		                },
		                grid:{
		                    x:70,                   
		                },
		                tooltip: {},
		                dataset: {
			                // 这里指定了维度名的顺序，从而可以利用默认的维度到坐标轴的映射。
			                // 如果不指定 dimensions，也可以通过指定 series.encode 完成映射，参见后文。
			                dimensions: ['product', '计划', '实际'],
			                source: [
			                    {product: '前年', '计划': planAmountOfPrevYear, '实际': actualAmountOfPrevYear},
			                    {product: '去年', '计划': planAmountOfTheLastYear, '实际': actualAmountOfTheLastYear},
			                    {product: '当年', '计划': planAmountOfYear, '实际': actualAmountOfYear}
			                ]
			            },
			            xAxis: {
			            	type: 'category',
			            	axisLine: {
		    	                show: false
		    	            },
		    	            axisLabel: {
		    	                show: true,
		    	                 textStyle: {
		    	                   color: '#000',  //更改坐标轴文字颜色	    	                   
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
		    	                   color: '#000',  //更改坐标轴文字颜色	    	                   
		    	                 }
		    	              },
		    	            axisTick:{
		    	            	show: false		   //显示/隐藏刻度
		    	            }
		                },
		                series: [
			                {
			                	type: 'bar',
			                	itemStyle: {
							        normal: {
							        	color:'#531dab',
							            label: {
							                show: true,
							                position: 'top',
							                textStyle: {
							                    color: '#000'
							                }
							            }
							        }
							    }
			                },
			                {
			                	type: 'bar',
			                	itemStyle: {
							        normal: {
							        	color:'#ffc53d',					        	
							            label: {
							                show: true,
							                position: 'top',
							                textStyle: {
							                    color: '#000'
							                }
							            }
							        }
							    }
				                			               			                
			                }
			            ]
		    };
			myChart_day.setOption(option_day);
			myChart_day.hideLoading(); 											//关闭提示
			myChart_month.setOption(option_month);
			myChart_month.hideLoading(); 
	        myChart_year.setOption(option_year);
	        myChart_year.hideLoading(); 
		},
		error:function(error){
			console.log(error);
		}
	});
};
/*获取车间生产情况*/
function displayWorkshop(){															
	$(".firstShopMenu,.secondShopMenu,.thirdShopMenu").hide();							//隐藏面包屑导航中的每个车间字段
	var workshop_charts = echarts.init(document.getElementById('workshop_charts'));
	workshop_charts.showLoading({ text: '正在加载数据' });  //增加等待提示 
	$.ajax({
		url:locationUrl+'/dafeng_mes/mes/workshopComplete',
		type:'post',
		cache:false,
		data:{CLASSIFICATION:"3"},
		success: function(data){
			console.log("车间总数以及分车间计划数量");
			console.log(data);
			console.log("车间总数以及分车间计划数量");
			/*
			console.log(JSON.stringify(data.varList));			
			console.log(JSON.stringify(data.varList1));
			console.log(JSON.stringify(data.varList2));
			console.log(JSON.stringify(data.varList3));*/
			var workShopData = data.varList;
			var firstWorkshopData = data.varList1;
			console.log(firstWorkshopData);
			var secondWorkshopData = data.varList2;
			console.log(secondWorkshopData);
			var thirdWorkshopData = data.varList3;
			console.log(thirdWorkshopData);
			var firstShop = {};
			var secondShop = {};
			var thirdShop = {};
			$.each(workShopData,function(index,item){
				if(item.TYPE == "1"){
					firstShop.planAmount = item.PLAN_AMOUNT;
					firstShop.actualAmount = item.PRODUCT_AMOUNT;
					if(firstShop.actualAmount == 0){
						firstShop.actualAmount = "";
					}					
					return firstShop;
				}else if(item.TYPE == "2"){
					secondShop.planAmount = item.PLAN_AMOUNT;
					secondShop.actualAmount = item.PRODUCT_AMOUNT;
					if(secondShop.actualAmount == 0){
						secondShop.actualAmount = "";
					}
					return secondShop;
				}else if(item.TYPE == "3"){
					thirdShop.planAmount = item.PLAN_AMOUNT;
					thirdShop.actualAmount = item.PRODUCT_AMOUNT;
					if(thirdShop.actualAmount == 0){
						thirdShop.actualAmount = "";
					}
					return thirdShop;
				}
			})		
			var option_workshop = {
	    		title: {
	            	text: '车间完成情况',
	            	subtext:'(点击查看详细)',
	                x: 10,
	                y: 5,
	                textStyle: {
	                	color:'#fff',
		                fontFamily: 'Arial, Verdana, sans...',
		                fontSize: '16px',
		                fontStyle: 'normal',
		                fontWeight: 'normal'
	                },	
	                subtextStyle:{
	                	color:'#ccc',
	                	fontFamily: 'Arial, Verdana, sans...',
	                	fontSize: '10px',
		                fontStyle: 'normal',
		                fontWeight: 'normal'
	                }
	            },
	            grid:{
	                x:50                   
	            },
	            legend: {
	            	x:130,
	            	y:10,
	            	textStyle:{
	            		color:'#fff'
	            	}
	            },
	            tooltip: {},
	            dataset: {
	                // 这里指定了维度名的顺序，从而可以利用默认的维度到坐标轴的映射。
	                // 如果不指定 dimensions，也可以通过指定 series.encode 完成映射，参见后文。
	                dimensions: ['product', '计划', '实际'],
	                source: [
	                    {product: '一车间', '计划': firstShop.planAmount, '实际': firstShop.actualAmount},
	                    {product: '二车间', '计划': secondShop.planAmount, '实际': secondShop.actualAmount},
	                    {product: '三车间', '计划': thirdShop.planAmount, '实际': thirdShop.actualAmount}
	                ]
	            },
	            xAxis: {
	            	type: 'category',
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
					    itemStyle: {
					        normal: {
					        	color:'#fff',
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
					    itemStyle: {
					        normal: {
					        	color:'#1890ff',
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
			 workshop_charts.setOption(option_workshop);
			 workshop_charts.hideLoading(); //关闭提示
			 	//点击车间展示对应车间计划
				workshop_charts.on('click', function (params) {
					$(".planList").html("");
					//展示一车间计划
					if(params.value.product == "一车间"){
						$(".breadcrumb").append("<li class='firstShopMenu'><strong>一车间生产计划</strong></li>");
						//展示一车间计划
/*						console.log("一车间数据");
						console.log(firstWorkshopData);
						console.log("一车间数据");*/
						var append1 = "";
						append1 = append1 + "<div class='legend'>";
						append1 = append1 + "<div class='leg plan'></div><span style='margin-left:3px;'>计划数量</span>";
						append1 = append1 + "<div class='leg theo'></div><span style='margin-left:3px;'>理论数量</span>";
						append1 = append1 + "<div class='leg actu'></div><span style='margin-left:3px;'>实际数量</span>";
						append1 = append1 + "</div>";
						$.each(firstWorkshopData,function(index,item){
							var firstPlan = parseInt(item.PLAN_AMOUNT);
							console.log(firstPlan);
							var firstActual = parseInt(item.PRODUCT_AMOUNT);
							console.log(firstActual);
							var firstTheory = parseInt(item.THEORY_AMOUNT);
							console.log(firstTheory);
							var firstPercent = "";							//生产进度
							var firstEfficiency = "";						//理论进度
							var firstWorkpiece = "";						//生产效率
							if(firstActual != 0){
								firstPercent = (firstActual/firstPlan);
								console.log(firstPercent);
								firstPercent = (firstPercent*100).toFixed(0)+"%";
								console.log(firstPercent);								
							}else{
								firstPercent = "0%";
							}
							if(firstTheory != 0){
								firstEfficiency = (firstActual/firstTheory);
								firstWorkpiece = (firstTheory/firstPlan);
								console.log(firstEfficiency);
								firstEfficiency = (firstEfficiency*100).toFixed(0)+"%";
								firstWorkpiece = (firstWorkpiece*100).toFixed(0)+"%";
								console.log(firstEfficiency);
							}else{
								firstEfficiency = "0%";
							}
							append1 = append1 + "<div class='progessGroup'>";
							append1 = append1 + "<div class='planName'>";
							append1 = append1 +	"<span>"+item.PRODUCT_SPECIFICATIONS+"</span>";
							append1 = append1 + "</div>";
							append1 = append1 + "<div class='lineName'>";
							append1 = append1 +	"<span>"+item.NAME+"</span>";
							append1 = append1 + "</div>";
							append1 = append1 + "<div class='progress plan'>";
							append1 = append1 +	"<div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: 100%;'>"+item.PLAN_AMOUNT+"</div>";
							append1 = append1 + "</div>";
							append1 = append1 + "<div class='progress theory'>";
							append1 = append1 +	"<div class='progress-bar progress-bar-warning' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:"+firstWorkpiece+"'>"+item.THEORY_AMOUNT+"</div>";
							append1 = append1 + "</div>";
							append1 = append1 + "<div class='progress actual'>";
							append1 = append1 + "<div class='progress-bar progress-bar-success' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:"+firstPercent+"'>"+item.PRODUCT_AMOUNT+"</div>";							
							append1 = append1 + "</div>";
							append1 = append1 + "<div class='row'>";
							append1 = append1 + "<div class='col-xs-6'><span><strong>生产效率</strong>&nbsp;&nbsp;</span><span class='proInefficiency'>"+firstEfficiency+"</span></div>";	
							append1 = append1 + "<div class='col-xs-6'><span><strong>生产进度</strong>&nbsp;&nbsp;</span><span class='proProcess'>"+firstPercent+"</span></div>";
							append1 = append1 + "</div>";
							append1 = append1 + "</div>";
						});
						$(".planList").append(append1);
						$(".echarts_workshopBrowse").hide();
						$(".echarts_outAmountAnalyse").hide();
						$(".echarts_outValueAnalyse").hide();
						$(".planList").show();
						//展示二车间计划
					}else if(params.value.product == "二车间"){
						$(".breadcrumb").append("<li class='secondShopMenu'><strong>二车间生产计划</strong></li>");										
/*						console.log("二车间数据");
						console.log(secondWorkshopData);
						console.log("二车间数据");*/
						var append2 = "";
						append2 = append2 + "<div class='legend'>";
						append2 = append2 + "<div class='leg plan'></div><span style='margin-left:3px;'>计划数量</span>";
						append2 = append2 + "<div class='leg theo'></div><span style='margin-left:3px;'>理论数量</span>";
						append2 = append2 + "<div class='leg actu'></div><span style='margin-left:3px;'>实际数量</span>";
						append2 = append2 + "</div>";
						$.each(secondWorkshopData,function(index,item){
							var secondPlan = parseInt(item.PLAN_AMOUNT);
							var secondActual = parseInt(item.PRODUCT_AMOUNT);
							var secondTheory = parseInt(item.THEORY_AMOUNT);
							console.log(secondTheory);
							var secondPercent = "";
							var secondEfficiency = "";
							var secondWorkpiece = "";
							if(secondActual != 0){
								secondPercent = (secondActual/secondPlan);
								console.log(secondPercent);
								secondPercent = (secondPercent*100).toFixed(0)+"%";
								console.log(secondPercent);								
							}else{
								secondPercent = "0%";
							}
							if(secondTheory != 0){
								secondEfficiency = (secondActual/secondTheory);
								secondWorkpiece = (secondTheory/secondPlan);
								console.log(secondEfficiency);
								secondEfficiency = (secondEfficiency*100).toFixed(0)+"%";
								secondWorkpiece = (secondWorkpiece*100).toFixed(0)+"%";
								console.log(secondEfficiency);
							}else{
								secondEfficiency = "0%";
							}
							append2 = append2 + "<div class='progessGroup'>";
							append2 = append2 + "<div class='planName'>";
							append2 = append2 +	"<span>"+item.PRODUCT_SPECIFICATIONS+"</span>";
							append2 = append2 + "</div>";
							append2 = append2 + "<div class='lineName'>";
							append2 = append2 +	"<span>"+item.NAME+"</span>";
							append2 = append2 + "</div>";
							append2 = append2 + "<div class='progress plan'>";
							append2 = append2 +	"<div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: 100%;'>"+item.PLAN_AMOUNT+"</div>";
							append2 = append2 + "</div>";
							append2 = append2 + "<div class='progress theory'>";
							append2 = append2 +	"<div class='progress-bar progress-bar-warning' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:"+secondWorkpiece+"'>"+item.THEORY_AMOUNT+"</div>";
							append2 = append2 + "</div>";
							append2 = append2 + "<div class='progress actual'>";
							append2 = append2 + "<div class='progress-bar progress-bar-success' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:"+secondPercent+"'>"+item.PRODUCT_AMOUNT+"</div>";							
							append2 = append2 + "</div>";
							append2 = append2 + "<div class='row'>";
							append2 = append2 + "<div class='col-xs-6'><span><strong>生产效率</strong>&nbsp;&nbsp;</span><span class='proInefficiency'>"+secondEfficiency+"</span></div>";	
							append2 = append2 + "<div class='col-xs-6'><span><strong>生产进度</strong>&nbsp;&nbsp;</span><span class='proProcess'>"+secondPercent+"</span></div>";
							append2 = append2 + "</div>";
							append2 = append2 + "</div>";
						});
						$(".planList").append(append2);	
						$(".echarts_workshopBrowse").hide();
						$(".echarts_outAmountAnalyse").hide();
						$(".echarts_outValueAnalyse").hide();
						$(".planList").show();	
							//展示三车间计划
						}else if(params.value.product == "三车间"){
							$(".breadcrumb").append("<li class='thirdShopMenu'><strong>三车间生产计划</strong></li>");						
/*							console.log("三车间数据");
							console.log(thirdWorkshopData);
							console.log("三车间数据");*/
							var append3 = "";
							append3 = append3 + "<div class='legend'>";
							append3 = append3 + "<div class='leg plan'></div><span style='margin-left:3px;'>计划数量</span>";
							append3 = append3 + "<div class='leg theo'></div><span style='margin-left:3px;'>理论数量</span>";
							append3 = append3 + "<div class='leg actu'></div><span style='margin-left:3px;'>实际数量</span>";
							append3 = append3 + "</div>";
							$.each(thirdWorkshopData,function(index,item){
								var thirdPlan = parseInt(item.PLAN_AMOUNT);
								var thirdActual = parseInt(item.PRODUCT_AMOUNT);
								var thirdTheory = parseInt(item.THEORY_AMOUNT);
								console.log(thirdTheory);
								var thirdPercent = "";
								var thirdEfficiency = "";
								var thirdWorkpiece = "";
								if(thirdActual != 0){
									thirdPercent = (thirdActual/thirdPlan);
									console.log(thirdPercent);
									thirdPercent = (thirdPercent*100).toFixed(0)+"%";
									console.log(thirdPercent);									
								}else{
									thirdPercent = "0%";
								}
								if(thirdTheory != 0){
									thirdEfficiency = (thirdActual/thirdTheory);
									thirdWorkpiece = (thirdTheory/thirdPlan);
									console.log(thirdEfficiency);
									thirdEfficiency = (thirdEfficiency*100).toFixed(0)+"%";
									thirdWorkpiece = (thirdWorkpiece*100).toFixed(0)+"%";
									console.log(thirdEfficiency);
								}else{
									thirdEfficiency = "0%";
								}
								append3 = append3 + "<div class='progessGroup'>";
								append3 = append3 + "<div class='planName'>";
								append3 = append3 +	"<span>"+item.PRODUCT_SPECIFICATIONS+"</span>";
								append3 = append3 + "</div>";
								append3 = append3 + "<div class='lineName'>";
								append3 = append3 +	"<span>"+item.NAME+"</span>";
								append3 = append3 + "</div>";
								append3 = append3 + "<div class='progress plan'>";
								append3 = append3 +	"<div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: 100%;'>"+item.PLAN_AMOUNT+"</div>";
								append3 = append3 + "</div>";
								append3 = append3 + "<div class='progress theory'>";
								append3 = append3 +	"<div class='progress-bar progress-bar-warning' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:"+thirdWorkpiece+"'>"+item.THEORY_AMOUNT+"</div>";
								append3 = append3 + "</div>";
								append3 = append3 + "<div class='progress actual'>";
								append3 = append3 + "<div class='progress-bar progress-bar-success' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width:"+thirdPercent+"'>"+item.PRODUCT_AMOUNT+"</div>";							
								append3 = append3 + "</div>";
								append3 = append3 + "<div class='row'>";
								append3 = append3 + "<div class='col-xs-6'><span><strong>生产效率</strong>&nbsp;&nbsp;</span><span class='proInefficiency'>"+thirdEfficiency+"</span></div>";	
								append3 = append3 + "<div class='col-xs-6'><span><strong>生产进度</strong>&nbsp;&nbsp;</span><span class='proProcess'>"+thirdPercent+"</span></div>";
								append3 = append3 + "</div>";
								append3 = append3 + "</div>";
							});		
							$(".planList").append(append3);	
							$(".echarts_workshopBrowse").hide();
							$(".echarts_outAmountAnalyse").hide();
							$(".echarts_outValueAnalyse").hide();
							$(".planList").show();						
						}  
			    })
		},					
		error:function(error){
			console.log(error)
		}
	})
//显示面包屑导航
var append03 = "";
$(".echarts_totalBrowse").hide();
$(".echarts_workshopBrowse").show();
append03 = append03 + "> <li class='shopMenu' onclick='jumpToWorkshop()'><strong>车间</strong></li>";
$(".breadcrumb").append(append03);
}
/*跳到车间生产情况*/
function jumpToWorkshop(){														
	$(".echarts_totalBrowse").hide();
	$(".planList").hide();
	$(".echarts_workshopBrowse").show();
	$(".planMenu").hide();
	$(".firstShopMenu,.secondShopMenu,.thirdShopMenu").hide();
	$(".echarts_outAmountAnalyse").show();
	$(".echarts_outValueAnalyse").show();	
}
/*获取首页计划完成情况图表*/
function getPlanCompeleteCharts(){
	var total_charts = echarts.init(document.getElementById('total_charts'));
	total_charts.showLoading({ text: '正在加载数据' }); 
	$.ajax({
		url:locationUrl+'/dafeng_mes/mes/complete.do',
		type:'post',
		cache:false,
		dataType:'json',
		success: function(data){
/*			console.log("首页计划完成情况统计图数据");
			console.log(data);
			console.log("首页计划完成情况统计图数据");*/
			var erpAmount = parseInt(data.pd1.ERP_AMOUNT);
			var mesAmount = parseInt(data.pd1.PLAN_AMOUNT);
			var actualAmount = parseInt(data.pd1.PRODUCT_AMOUNT);	
			var option_total = {
					title: {
		            	text: '当日计划总览',
		            	subtext:'(点击查看详细)',
		                x: 10,
		                y: 5, 
		                textStyle: {
		                	color:'#fff',
			                fontFamily: 'Arial, Verdana, sans...',
			                fontSize: '16px',
			                fontStyle: 'normal',
			                fontWeight: 'normal'
		                },
		                subtextStyle:{
		                	color:'#ccc',
		                	fontFamily: 'Arial, Verdana, sans...',
		                	fontSize: '10px',
			                fontStyle: 'normal',
			                fontWeight: 'normal'
		                }
		            },
		            grid:{
		                x:45                   
		            },
		            legend: {
		            	data:['产量'],
		            	x:180,
		            	y:10,
		            	textStyle: {
		                    color: '#fff'         //legend文字颜色
		                }
		            },
				    xAxis: {
				        data: ["ERP","MES","实际"],
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
				    series: [{
				        name: '产量',
				        type: 'bar',
				        barWidth:20,
				        data: [erpAmount,mesAmount,actualAmount],
					    itemStyle: {
					        normal: {
					        	color:'#fff',
					            label: {
					                show: true,
					                position: 'top',
					                textStyle: {
					                    color: '#fff'
					                },
					                formatter:function(params){
					                    if(params.value==0){
					                        return '';
					                    }else
					                    {
					                        return params.value;
					                    }
					                }
					            }
					        }
					    }
				    }]
				};
			total_charts.setOption(option_total);
			total_charts.hideLoading(); //关闭提示
		},
		error:function(error){
			console.log(error)
		}
	})
};
/*获取首页产量分析图表*/
function getOutAmountCharts(){
	var outAmount_charts = echarts.init(document.getElementById('outAmount_charts'));
	var option_outAmount = {
    		title:{
    			text:'产量分析',
    			x: 10,
                y: 0, 
                textStyle: {
                	color:'#fff',
	                fontFamily: 'Arial, Verdana, sans...',
	                fontSize: '16px',
	                fontStyle: 'normal',
	                fontWeight: 'normal'
                },
    		},
    		tooltip : {
    	        trigger: 'axis'
    	    },
    	    grid:{
                x:50,
            },
            color:["#fff","#595959"],
    	    legend: {
    	        data:['计划','实际'],
    	        y:5,
    	        x:130,
    	        textStyle: {
                    color: '#fff'         //legend文字颜色
                }
    	    },    	    
    	    calculable : true,
    	    xAxis : [
    	        {
    	            type : 'category',
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
    	            boundaryGap : false,
    	            data : ['周一','周二','周三','周四','周五','周六','周日']
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value',
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
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'计划',	    	          
    	            type:'line',
    	            symbol: 'circle',     //设定折点为实心点
                    symbolSize: 11,       //设定折点的大小
    	            lineStyle :{
    	            	normal:{
    	            		color:"#fff"			//折线颜色
    	            	}
    	            },
    	            areaStyle : {
                        color : '#fff'
                    },
    	            data:[5000, 9000, 8000, 10000, 6000, 8000, 11000]
    	        },
    	        {
    	            name:'实际',
    	            type:'line',
    	            symbol: 'circle',     			//设定折点为实心点
                    symbolSize: 11,      			//设定折点的大小
    	            lineStyle :{
    	            	normal:{
    	            		color:"#595959"			//折线颜色
    	            	}
    	            },
    	            areaStyle : {
                        color : '#595959'
                    },
                    data:[4500, 3000, 7500, 6000, 4000, 7000, 10000]
    	        },   	        
    	    ]
     };
    outAmount_charts.setOption(option_outAmount);
	//outAmount_charts.showLoading({ text: '正在加载数据' }); 
	$.ajax({
		url:locationUrl + '/dafeng_mes/mes/output',
		type:'post',
		cache:false,
		success: function(data){
			console.log("首页产量分析数据");
			console.log(data);
			console.log("首页产量分析数据");
		var weekAmountData = data.varList;
		var monPlan = weekAmountData[0].PLAN_AMOUNT;
		var monActual = weekAmountData[0].PRODUCT_AMOUNT;
		var tuePlan = weekAmountData[1].PLAN_AMOUNT;
		var tueActual = weekAmountData[1].PRODUCT_AMOUNT;
		var wedPlan = weekAmountData[2].PLAN_AMOUNT;
		var wedActual = weekAmountData[2].PRODUCT_AMOUNT;
		var thuPlan = weekAmountData[3].PLAN_AMOUNT;
		var thuActual = weekAmountData[3].PRODUCT_AMOUNT;
		var friPlan = weekAmountData[4].PLAN_AMOUNT;
		var friActual = weekAmountData[4].PRODUCT_AMOUNT;
		var satPlan = weekAmountData[5].PLAN_AMOUNT;
		var satActual = weekAmountData[5].PRODUCT_AMOUNT;
		var sunPlan = weekAmountData[6].PLAN_AMOUNT;
		var sunActual = weekAmountData[6].PRODUCT_AMOUNT;					
	    /*var option_outAmount = {
	    		title:{
	    			text:'产量分析',
	    			x: 10,
	                y: 0, 
	                textStyle: {
	                	color:'#fff',
		                fontFamily: 'Arial, Verdana, sans...',
		                fontSize: '16px',
		                fontStyle: 'normal',
		                fontWeight: 'normal'
	                },
	    		},
	    		tooltip : {
	    	        trigger: 'axis'
	    	    },
	    	    grid:{
	                x:50,
	            },
	            color:["#fff","#595959"],
	    	    legend: {
	    	        data:['计划','实际'],
	    	        y:5,
	    	        x:130,
	    	        textStyle: {
	                    color: '#fff'         //legend文字颜色
	                }
	    	    },    	    
	    	    calculable : true,
	    	    xAxis : [
	    	        {
	    	            type : 'category',
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
	    	            boundaryGap : false,
	    	            data : ['周一','周二','周三','周四','周五','周六','周日']
	    	        }
	    	    ],
	    	    yAxis : [
	    	        {
	    	            type : 'value',
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
	    	        }
	    	    ],
	    	    series : [
	    	        {
	    	            name:'计划',	    	          
	    	            type:'line',
	    	            symbol: 'circle',     //设定折点为实心点
                        symbolSize: 11,       //设定折点的大小
	    	            lineStyle :{
	    	            	normal:{
	    	            		color:"#fff"			//折线颜色
	    	            	}
	    	            },
	    	            areaStyle : {
	                        color : '#fff'
	                    },
	    	            data:[monPlan, tuePlan, wedPlan, thuActual, friPlan, satPlan, sunPlan]
	    	        },
	    	        {
	    	            name:'实际',
	    	            type:'line',
	    	            symbol: 'circle',     			//设定折点为实心点
                        symbolSize: 11,      			//设定折点的大小
	    	            lineStyle :{
	    	            	normal:{
	    	            		color:"#595959"			//折线颜色
	    	            	}
	    	            },
	    	            areaStyle : {
	                        color : '#595959'
	                    },
	                    data:[monActual, tueActual, wedActual, thuActual, friActual, satActual, sunActual]
	    	        },   	        
	    	    ]
	     };
	    outAmount_charts.setOption(option_outAmount);*/
	    //outAmount_charts.hideLoading(); //关闭提示
		},
		error:function(error){
			console.log(error)
		}
	})	
}

/*获取首页产值分析统计图*/
function outputValueAnalyse(){
	// 基于准备好的dom，初始化echarts实例
	var outValue_charts_bar = echarts.init(document.getElementById('outValue_charts_bar'));
	var outValue_charts = echarts.init(document.getElementById('outValue_charts'));
	outValue_charts.showLoading({ text: '正在加载数据' });
	outValue_charts_bar.showLoading({ text: '正在加载数据' });
	$.ajax({
		url:locationUrl+'/dafeng_mes/mes/outputValue',
		type:'post',
		cache:false,
		dataType:'json',
		success: function(data){
/*			console.log("首页产值分析统计图数据");
			console.log(data);
			console.log("首页产值分析统计图数据");*/
			var sum = data.data.SUM;
			var sum1 = data.data.SUM1;
			var sum2 = data.data.SUM2;
			var sum3 = data.data.SUM3;
			// 指定图表的配置项和数据
		    /*产值分析*/
		    var option_outValue = {
		    		title:{
		    			text:'月/季度产值占比',
		    			x: 10,
		                y: 0, 
		                textStyle: {
		                	color:'#d4b106',
			                fontFamily: 'Arial, Verdana, sans...',
			                fontSize: '16px',
			                fontStyle: 'normal',
			                fontWeight: 'normal'
		                },
		    		},
		    		 tooltip : {
		    		        trigger: 'item',
		    		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    		    },
		    		    /*legend: {
		    		        orient : 'vertical',
		    		        x : 'left',
		    		        data:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月','13月']
		    		    },*/
		    		    /*toolbox: {
		    		        show : true,
		    		        feature : {
		    		            mark : {show: true},
		    		            dataView : {show: true, readOnly: false},
		    		            magicType : {
		    		                show: true, 
		    		                type: ['pie', 'funnel']
		    		            },
		    		            restore : {show: true},
		    		            saveAsImage : {show: true}
		    		        }
		    		    },*/
		    		    calculable : false,
		    		    series : [
		    		        {
		    		            name:'产值统计',
		    		            type:'pie',
		    		            selectedMode: 'single',
		    		            radius : [0, 60],
		    		            x: '20%',
		    		            width: '40%',
		    		            funnelAlign: 'right',
		    		            max: 1548,
		    		            color:['#fa541c', '#a0d911','#40a9ff','#f759ab'],
		    		            itemStyle : {
		    		                normal : {
		    		                    label : {
		    		                        position : 'inner'
		    		                    },
		    		                    labelLine : {
		    		                        show : false
		    		                    }
		    		                }
		    		            },
		    		            data:[
		    		                {value:3000, name:'一季度'},
		    		                {value:2900, name:'二季度'},
		    		                {value:3100, name:'三季度'},
		    		                {value:2890, name:'四季度'},		    		                
		    		            ]
		    		        },
		    		        {
		    		            name:'产值统计',
		    		            type:'pie',
		    		            radius : [80, 90],		    		            
		    		            // for funnel
		    		            x: '60%',
		    		            width: '35%',
		    		            funnelAlign: 'left',
		    		            max: 1048,
		    		            color:['#d46b08', '#7cb305','#096dd9','#c41d7f','#531dab', '#08979c','#d4b106','#d4380d','#cf1322', '#d48806','#389e0d','#1d39c4'],
		    		            data:[
		    		                {value:335, name:'1月'},
		    		                {value:310, name:'2月'},
		    		                {value:234, name:'3月'},
		    		                {value:135, name:'4月'},
		    		                {value:104, name:'5月'},
		    		                {value:251, name:'6月'},
		    		                {value:147, name:'7月'},
		    		                {value:102, name:'8月'},
		    		                {value:102, name:'9月'},
		    		                {value:102, name:'10月'},
		    		                {value:102, name:'11月'},
		    		                {value:102, name:'12月'}
		    		            ]
		    		        }
		    		    ]
		    	};
		    var option_outValue_bar = {
		    		title:{
		    			text:'产值分析',
		    			x: 10,
		                y: 0, 
		                textStyle: {
		                	color:'#d4b106',
			                fontFamily: 'Arial, Verdana, sans...',
			                fontSize: '16px',
			                fontStyle: 'normal',
			                fontWeight: 'normal'
		                },
		    		},
		    		grid:{
		                x:45                   
		            },
		            legend: {
		            	data:['产值'],
		            	x:180,
		            	y:10,
		            	textStyle: {
		                    color: '#000'         //legend文字颜色
		                }
		            },
		    		xAxis: {
				        data: ["一季度","二季度","三季度","四季度"],
				        axisLine: {
	    	                show: false
	    	            },
	    	            axisLabel: {
	    	                show: true,
	    	                 textStyle: {
	    	                   color: '#000',  //更改坐标轴文字颜色	    	                   
	    	                 }
	    	              },
	    	            axisTick:{
	    	            	show: false		   //显示/隐藏刻度
	    	            },
				    },
				    yAxis: {
				    	axisLine: {
	    	                show: false
	    	            },
	    	            axisLabel: {
	    	                show: true,
	    	                 textStyle: {
	    	                   color: '#000',  //更改坐标轴文字颜色	    	                   
	    	                 }
	    	              },
	    	            axisTick:{
	    	            	show: false		   //显示/隐藏刻度
	    	            },
				    },
		    	    series : [
		    	        {
		    	            name:'产值',
		    	            type:'bar',
		    	            barWidth:20,
		                	itemStyle: {
						        normal: {
						        	color:'#ffc53d',					        	
						            label: {
						                show: true,
						                position: 'top',
						                textStyle: {
						                    color: '#000'
						                },
						                formatter:function(params){
						                    if(params.value==0){
						                        return '';
						                    }else
						                    {
						                        return params.value;
						                    }
						                }
						            }
						        }
						    },
		    	            data:[3000,2900,3100,2950]
		    	        }
		    	    ]
		    	};
		    	                    
			outValue_charts.setOption(option_outValue);
			outValue_charts_bar.setOption(option_outValue_bar);
			outValue_charts.hideLoading(); //关闭提示
			outValue_charts_bar.hideLoading(); //关闭提示
		},
		error:function(error){
			console.log(error)
		}
	})	
}
/*修改密码*/
function changeMyPass(){
	var user = $(".usernameHide").val();
	window.location.href = "changepassword.html?USERNAME="+user+"";
}