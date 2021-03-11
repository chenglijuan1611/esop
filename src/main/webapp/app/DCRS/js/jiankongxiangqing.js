/*页面初始化，获取用户*/
$(function(){
	//getEquMessage();
	var userName = getQueryString("userName");
	var userID = getQueryString("userID");
	var id = getQueryString("id");
	var equname = getQueryString("equname");
	var workshop = getQueryString("workshop");
	var equCode = getQueryString("equCode");
	$(".userName").val(userName);										//存储到隐藏域待用
	$(".userID").val(userID);										//存储到隐藏域待用
	$("#user").text(userName);
	$("#checker").text(userName);
	$("#workshop").text(workshop);
	$("#equCode").val(equCode);
	$("#equname").text(equname);
	getDetailContent(id);												//展示选择查看的监控记录详情	
});
/*公共数据接口地址*/
var locationUrl = 'http://'+window.location.host;	
/* 返回上一页 */
function back(){
	var workshop = $("#workshop").text();
	var equCode = $("#equCode").val();
	var equname = $("#equname").text();
	var userName = $(".userName").val();										//存储到隐藏域待用
	var userID = $(".userID").val();
	window.location.href = "jiankongchaxun.html?userName="+userName+"&userID="+userID+"&workshop="+workshop+"&equname="+equname+"&equCode="+equCode+"";
}
/*退出按钮*/
function exit(){
	window.location.href = "login.html";
}
/*展示详情*/
function getDetailContent(id){
	var id = id;
	$(".main_content").html("");
	$.ajax({
		url:locationUrl + '/dafeng_mes/inspectionrecorditem/detailById?INSPECTIONRECORD_ID='+id,
		type:'post',
		cache:false,
		success: function(data){
			console.log(data);
			//$(".INSPECTIONRECORD_ID").val(data.INSPECTIONRECORD_ID);
			var dataArr = data.varList;
			var append = "";
			append = append + "<table class='table'>";				
			append = append + "<thead>";
			append = append + 	"<tr>";
			append = append + 		"<th style='width: 5%;'>序号</th>";
			append = append + 		"<th>检查项名称</th>";
			append = append + 		"<th style='width: 8%;'>检查属性</th>";
			append = append + 		"<th style='width: 5%;'>频率</th>";
			append = append + 		"<th>频率转换</th>";			
			append = append + 		"<th>检查结果</th>";			
			append = append + 	"</tr>";
			append = append + "</thead>";
			append = append + "<tbody id='dataBody'>";			
			$.each(dataArr,function(index, item){
				append = append + 	"<tr>";
				append = append + 		"<td>"+(index+1)+"</td>";
				append = append + 		"<td class='checkItemName' style='display:none;'>"+item.DISPLAYORDERID+"</td>";
				append = append + 		"<td class='maxValue' style='display:none;'>"+item.INSPECTIONITEMUP+"</td>";
				append = append + 		"<td class='minValue' style='display:none;'>"+item.INSPECTIONITEMDOWN+"</td>";
				append = append + 		"<td>"+item.INSPECTIONITEMNAME+"</td>";
				append = append + 		"<td>"+item.INSPECTIONITEMPROP+"</td>";
				append = append + 		"<td>"+item.FREQUENCY+"</td>";
				append = append + 		"<td>"+item.FREQUENCYCONVERT+"</td>";
				append = append + 		"<td>"+item.INSPECTIONITEMRESULT+"</td>";																
				append = append + 	"</tr>";
			});											
			append = append + "</tbody>";
			append = append + "</table>";
			append = append + "<div style='text-align:right;margin-right:10px;'>";
			append = append + "<button class='btn btn-primary' onclick='confirm()'>确认</button>";
			append = append + "</div>";
			$(".main_content").html(append);
		},
		error:function(error){
			console.log(error)
		}
	})

}
/*确认记录*/
function confirm(){
	var id = getQueryString("id");
	var userID = getQueryString("userID");
	var flag = "1";
	var data = {INSPECTIONRECORD_ID:id,CONFIRMOR:userID,ISCONFIRM :flag};
	console.log(data);
	$.ajax({
		url:locationUrl + '/dafeng_mes/inspectionrecord1/confirm',
		type:'post',
		data:data,
		cache:false,
		success: function(data){
			console.log(data);
			back();															
		},
		error:function(error){
			console.log(error)
		}
	})	
}
/* 获取url参数 */
function getQueryString(name) {
	var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
		    return "";
		}
		return decodeURI(result[1]);
}