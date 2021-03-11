/*---主页面---*/

//页面加载完成执行方法块
$(function(){
	$(".usernameHide").val((GetQueryString("USERNAME")));   //从url中获取用户名并存储到隐藏域
	$(".userName").text((GetQueryString("USERNAME")));		//展示用户信息	
	getMenuList();     //获取左侧菜单列表
});
/*获取左侧菜单列表*/
function getMenuList(){
	var url = "http://172.16.20.182:8080/dafeng_mes/android/getMenu";
	var username = $(".usernameHide").val();
	var data = {USERNAME:username};
	$.ajax({
		url:url,
		type:'post',
		cache:false,
		data:data,
		success: function(data) {
			console.log(data);
			//console.log(JSON.stringify(data));
			var leftMenuArray = data.appMenuList;
			//console.log(leftMenuArray);
			var append = "";
			//遍历数组菜单对象，生成菜单
			$.each(leftMenuArray,function(index,item){
				if(item.hasMenu){
					append = append + "<li><a onclick=\"displayData('"+item.MENU_URL+"','"+item.MENU_NAME+"')\" href='#'><i class="+item.MENU_ICON+"></i><span>"+item.MENU_NAME+"</span></a></li>";
				}		
			});
			$(".sidebar-menu").append(append);									
		},
		error:function(error){
			console.log(error)
		}
	})		
}
/*根据所点击菜单展示对应数据*/
function displayData(para_url,para_menuname){
	getCurrentTime();  //获取当前日期(填入日期选择框默认日期)
	$(".menuName").text(para_menuname);
	//var url = 'http://172.16.20.182:8080/dafeng_mes/' + para_url;
	//alert(url);
	var url = "http://172.16.20.182:8080/dafeng_mes/mes/plan";
	$.ajax({
		url:url,
		type:'post',
		cache:false,
		success: function(data) {
			console.log(data);
			console.log(JSON.stringify(data));
			var varList = data.varList;
			var append = "";
			var append1 = "";
			append1 = append1 + "<div class='row'>";
				append1 = append1 + "<div class='col-xs-4 col-md-4'>";
				append1 = append1 + "<input class='timepick startTime' type='date' name='bday'>";
				append1 = append1 + "</div>";
				append1 = append1 + "<div class='col-xs-4 col-md-4'>";
				append1 = append1 + "<input class='timepick endTime' type='date' name='bday' style='margin-left: 25px;'>";
				append1 = append1 + "</div>";
				append1 = append1 + "<div class='col-xs-4 col-md-4 text-right'>";
				append1 = append1 + "<button class='searchBtn'>查询</button>";
				append1 = append1 + "</div>";
				append1 = append1 + "</div>";
			//遍历数组菜单对象，生成数据列表
			$.each(varList,function(index,item){				
				append = append + "<div class='orderMsg container'>";
				append = append + "<div class='row'>";
				append = append +	"<div class='col-xs-12 col-sm-6 col-md-8'><span class='orderNumTitle'><strong>名称</strong></span>&nbsp;&nbsp;<span class='orderNum'>"+item.invStd+"</span></div>";
				append = append +	"<div class='col-xs-6 col-md-4'><span class='nameTitle'><strong>订单号</strong></span>&nbsp;&nbsp;<span class='name'>"+item.MoCode+"</span></div>";
				append = append + "</div>";
				append = append + "<div class='row'>";
				append = append +	"<div class='col-xs-6 col-md-4' ><span class='codeTitle'><strong>编码</strong></span>&nbsp;&nbsp;<span class='code'>"+item.invCode+"</span></div>";
				append = append +	"<div class='col-xs-6 col-md-4' ><span class='planAmountTitle'><strong>计划数量</strong></span>&nbsp;&nbsp;<span class='planAmount'>"+item.qty+"</span></div>";
				append = append + "</div>";
				append = append + "<div class='row'>";
				append = append +	"<div class='col-xs-6'><span class='startTimeTitle'><i class='fa fa-calendar' style='color:green;'></i>&nbsp;&nbsp;起</span>&nbsp;&nbsp;<span class='startTime'>"+item.startdate+"</span></div>";
				append = append +	"<div class='col-xs-6'><span class='endTimeTitle'><i class='fa fa-calendar' style='color:red;'></i>&nbsp;&nbsp;终</span>&nbsp;&nbsp;<span class='endTime'>"+item.DueDate+"</span></div>";
				append = append + "</div>";;			
				append = append + "</div>";			
			});
			var common = append1 + append;
			$(".orderContent").append(common);			
		},
		error:function(error){
			console.log(error)
		}
	})	
} 
/*退出登录*/
function exit(){
	window.location.href = "login.html";
}
/*获取当前日期*/
function getCurrentTime(){
	var date = new Date();
	var dateString = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
	//alert(dateString);
	$(".timepick").val(dateString);
}




