/*页面初始化*/
$(function(){
	$("#workline").html('');
	getWorkLine();
	$("#workshop").change(function(){		//监听车间变化
		getWorkLine();
	})	
});
/*请求地址*/
var locationUrl = window.location.protocol + "//" + window.location.host; 										//公有请求地址
var projectName = window.location.pathname;
var proArr = projectName.split('/');
var proname = proArr[1];
locationUrl = locationUrl + '/' + proname; 
//车间产线联动
function getWorkLine(){
	$("#workline").html('');
	var append = "";
	var shop = $("#workshop").val();
	console.log(shop);
	if(shop == 'workshop0'){
		append = append + "<option value ='line0'>请选择产线</option>";					//line0表示未选择产线
	}
	else if(shop == 'w1'){
		append = append + "<option value ='w1All'></option>";
		append = append + "<option value ='SMT01'>SMT01线</option>";
		append = append + "<option value ='SMT02'>SMT02线</option>";
		append = append + "<option value ='SMT03'>SMT03线</option>";
		append = append + "<option value ='SMT04'>SMT04线</option>";
		append = append + "<option value ='SMT05'>SMT05线</option>";
		append = append + "<option value ='SMT06'>SMT06线</option>";
		append = append + "<option value ='SMT07'>SMT07线</option>";
		append = append + "<option value ='SMT08'>SMT08线</option>";
		append = append + "<option value ='SMT09'>SMT09线</option>";
		append = append + "<option value ='SMT10'>SMT10线</option>";
	}else if(shop == 'w2'){
		append = append + "<option value ='w2All'></option>";
		append = append + "<option value ='B01'>B1线</option>";
		append = append + "<option value ='B02'>B2线</option>";
		append = append + "<option value ='B03'>B3线</option>";
		append = append + "<option value ='B04'>B4线</option>";
		append = append + "<option value ='B05'>B5线</option>";
		append = append + "<option value ='B06'>B6线</option>";
		append = append + "<option value ='B07'>B7线</option>";
		append = append + "<option value ='B08'>B8线</option>";
		append = append + "<option value ='B09'>B9线</option>";
		append = append + "<option value ='B10'>B10线</option>";
	}else if(shop == 'w3'){
		append = append + "<option value ='w3All'></option>";
		append = append + "<option value ='C01'>C1线</option>";
		append = append + "<option value ='C02'>C2线</option>";
		append = append + "<option value ='C03'>C3线</option>";
		append = append + "<option value ='C04'>C4线</option>";
		append = append + "<option value ='C05'>C5线</option>";
		append = append + "<option value ='C06'>C6线</option>";
		append = append + "<option value ='C07'>C7线</option>";
		append = append + "<option value ='C08'>C8线</option>";
		append = append + "<option value ='C09'>C9线</option>";
		append = append + "<option value ='C10'>C10线</option>";
	}else if(shop == 'w4'){
		append = append + "<option value ='w4All'></option>";
		append = append + "<option value ='D01'>D1线</option>";
		append = append + "<option value ='D02'>D2线</option>";
		append = append + "<option value ='D03'>D3线</option>";
		append = append + "<option value ='D04'>D4线</option>";
		append = append + "<option value ='D05'>D5线</option>";
		append = append + "<option value ='D06'>D6线</option>";
		append = append + "<option value ='D07'>D7线</option>";
		append = append + "<option value ='D08'>D8线</option>";
		append = append + "<option value ='D09'>D9线</option>";
		append = append + "<option value ='D10'>D10线</option>";
	}
	$("#workline").append(append);
}

//提交信息
function submitMsg(){
	var username = $("#username").val();
	var level = $("#level").val();
	var workshop = $("#workshop").val();
	var workline = $("#workline").val();
	if(username == '' || username == null){
		alert("请输入您的真实姓名");
		return false;
	}
	if(level == 'level0'){
		alert("请选择您的职位");
		return false;
	}
	var data = {
		username:username,
		level:level,
		workshop:workshop,
		workline:workline
	}	
	$.ajax({		
		url:locationUrl + '',
		type:'post',
		data:data,
		cache:false,
		success: function(data){
			console.log("进入success钩子");
			console.log(data);																				
		},
		error:function(error){
			console.log(error)
		}
	})
	
		
}
//帮助
function help(){
	$("#helpText").toggle()
}	