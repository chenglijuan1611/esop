/*页面初始化，获取用户*/
$(function(){
	//getEquMessage();
	var userName = getQueryString("userName");
	var userID = getQueryString("userID");
	var equname = getQueryString("equname");
	var workshop = getQueryString("workshop");
	var equCode = getQueryString("equCode");	
	$(".userName").val(userName);										//存储到隐藏域待用
	$(".userID").val(userID);										//存储到隐藏域待用
	$("#user").text(userName);
	$("#checker").text(userName);
	$("#workshop").text(workshop);
	$("#equname").text(equname);
	$("#equCode").val(equCode);
	getUnConfirmRecords(equCode);
	
});
/*公共数据接口地址*/
var locationUrl = 'http://'+window.location.host;	
/* 返回上一页 */
function back(){
	var userName = $(".userName").val();										//存储到隐藏域待用
	var userID = $(".userID").val();		
	window.location.href = "index.html?userName="+userName+"&userID="+userID+"";
}
/*退出按钮*/
function exit(){
	window.location.href = "login.html";
}
/*获取设备信息*/
function getEquMessage(){
	var equCode = $("#equCode").val();
	//$("#equCodeHidden").val(equCode);
	//$.cookie("equCode", equCode);
	$.ajax({
		url:locationUrl + '/dafeng_mes/equipmentFile1/detail?EQUIPMENTCODE='+equCode,
		type:'post',
		cache:false,
		success: function(data){
			console.log(data);
			$("#workshopHidden").val(data.pd.EQUIPMENTPOSITION);
			$("#equnameHidden").val(data.pd.EQUIPMENTNAME);
			$("#workshop").text(data.pd.EQUIPMENTPOSITION);
			$("#equname").text(data.pd.EQUIPMENTNAME);	
			getUnConfirmRecords();
		},
		error:function(error){
			console.log(error)
		}
	})
}
/*获取未确认记录*/
function getUnConfirmRecords(a){
	var equCode = "";
	if(arguments.length>0){
		equCode = a;
	}else{
		equCode = $("#equCode").val();
	}	
	$(".main_content").html("");
	console.log("=========");
	console.log(equCode);
	console.log("=========");
	$.ajax({
		url:locationUrl + '/dafeng_mes/inspectionrecord1/listByCode?INSPECTIONTICKETCATEGORY=06&EQUIPMENTCODE='+equCode,
		type:'post',
		cache:false,
		success: function(data){
			console.log(data);
			$(".INSPECTIONRECORD_ID").val(data.INSPECTIONRECORD_ID);
			var dataArr = data.varList;
			var append = "";
			append = append + "<table class='table'>";				
			append = append + "<thead>";
			append = append + 	"<tr>";
			append = append + 		"<th style='width: 5%;'>序号</th>";
			append = append + 		"<th>单据号</th>";
			append = append + 		"<th style='width: 8%;'>生产线</th>";
			append = append + 		"<th style='width: 5%;'>检查人</th>";
			append = append + 		"<th style='width: 10%;'>检查日期</th>";
			append = append + 		"<th>未确认项</th>";					
			append = append + 	"</tr>";
			append = append + "</thead>";
			append = append + "<tbody id='dataBody'>";
			$.each(dataArr,function(index, item){
				console.log(item.INSPECTIONRECORD_ID+typeof(item.INSPECTIONRECORD_ID));
				var id = item.INSPECTIONRECORD_ID;
				append = append + 	"<tr>";
				append = append + 		"<td>"+(index+1)+"</td>";
				append = append + 		"<td>"+item.INSPECTIONRECORD_ID+"</td>";
				append = append + 		"<td></td>";
				append = append + 		"<td>"+item.INSPECTOR+"</td>";
				append = append + 		"<td>"+item.INSPECTIONTIME+"</td>";
				append = append + 		"<td><a id='"+index+"' class='btn btn-primary btn-sm' href='javascript:void(0)' onclick=\"jumpToDetail('"+id+"')\">查看</a></td>";																			
				append = append + 	"</tr>";
			});											
			append = append + "</tbody>";
			append = append + "</table>";
			$(".main_content").html(append);
		},
		error:function(error){
			console.log(error)
		}
	})
}

/*跳转到监控记录详情*/
function jumpToDetail(id){	
	var id = id;
	var userName = $(".userName").val();										//存储到隐藏域待用
	var userID = $(".userID").val();
	var workshop = $("#workshop").text();
	var equname = $("#equname").text();
	var equCode = $("#equCode").val();
	window.location.href = "jiankongxiangqing.html?userName="+userName+"&userID="+userID+"&id="+id+"&workshop="+workshop+"&equname="+equname+"&equCode="+equCode+"";
}
/* 获取url参数 */
function getQueryString(name) {
	var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
		    return "";
		}
		return decodeURI(result[1]);
}