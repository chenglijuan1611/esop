/*页面初始化，获取用户*/
$(function(){
	//getEquMessage();
	var userName = getQueryString("userName");
	var userID = getQueryString("userID");
	$(".userName").val(userName);										//存储到隐藏域待用
	$(".userID").val(userID);										//存储到隐藏域待用
	$("#user").text(userName);
	$("#checker").text(userName);
	$("#workshop").text($("#workshopHidden").val());
	$("#equname").text($("#equnameHidden").val());
	
});
/*公共数据接口地址*/
var locationUrl = 'http://'+window.location.host;	
/* 返回上一页 */
function back(){
	history.go(-1);
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
			getDianJianItems();
		},
		error:function(error){
			console.log(error)
		}
	})
}
/*获取点检项*/
function getDianJianItems(){
	$(".main_content").html("");
	var equCode = $("#equCode").val();
	$.ajax({
		url:locationUrl + '/dafeng_mes/inspectiontickt1/checkItems?INSPECTIONTICKETCATEGORY=04&EQUIPMENTCODE='+equCode,
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
			append = append + 		"<th>检查项名称</th>";
			append = append + 		"<th style='width: 8%;'>检查属性</th>";
			append = append + 		"<th style='width: 5%;'>频率</th>";
			append = append + 		"<th style='width: 8%;'>备注</th>";
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
				append = append + 		"<td>"+item.REMARK+"</td>";
				append = append + 		"<td>"+item.FREQUENCYCONVERT+"</td>";				
				var type = item.INSPECTIONITEMPROP;				
				var num =(item.INSPECTIONITEMRESULT.split(',')).length-1;
				var resArr = item.INSPECTIONITEMRESULT.split(',');
				//console.log(num);
				var append0 = "";
				if(type == "数值"){
					for(var i=0;i<num+1;i++){					
						append0 = append0 + "<input id='num"+index+''+i+"' onchange='submitNumberData("+(i+1)+",this.value,this.id)' value='"+resArr[i]+"' class='textInput' style='margin-right: 5px;width:10%;border-top:none;border-left:none;border-right:none;border-bottom:1px solid #000' type='text'>";
					}					
				}else if(type == "开关"){
					for(var j=0;j<num+1;j++){
						if(resArr[j] == "1"){
							append0 = append0 + "<input id='check"+index+''+j+"' checked onclick='submitCheckData("+(j+1)+",this.id)' value='' class='checkInput' style='margin-left: 5px;border:1px solid #000' type='checkbox'><span style=''>第"+(j+1)+"次</span>";
						}
						else{
							append0 = append0 + "<input id='check"+index+''+j+"' onclick='submitCheckData("+(j+1)+",this.id)' value='' class='checkInput' style='margin-left: 5px;border:1px solid #000' type='checkbox'><span style=''>第"+(j+1)+"次</span>";
						}						
					}						
				}
				append = append + 	"<td class='result'>"+append0+"</td>";																
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

/*输入数值型数据后提交数据*/
function submitNumberData(n,val,id){
	console.log("change occured");
	var id = id;																//当前input的id
	var maxVal = parseFloat($('#'+ id).parent().siblings('.maxValue').text());				//最大值
	var minVal = parseFloat($('#'+ id).parent().siblings('.minValue').text());				//最小值
	var equCode = $("#equCode").val();											//设备编码
    var n = n;																	//第n次检查	
	var val = val;																//填入的值
	if(val>maxVal || val<minVal){
		alert("警告，实际值不符合标准，请注意！")
	}
	var INSPECTIONRECORD_ID = $(".INSPECTIONRECORD_ID").val();
	var itemID = $('#'+ id).parent().siblings('.checkItemName').text();			//当前检查项
	var userID = $(".userID").val();	
	var data = {DISPLAYORDERID:itemID,INSPECTIONITEMRESULT:val,ID:n,EQUIPMENTCODE:equCode,INSPECTOR:userID,INSPECTIONTICKETCATEGORY:'04',INSPECTIONRECORD_ID:INSPECTIONRECORD_ID};
	console.log(data);
	$.ajax({
		url:locationUrl + '/dafeng_mes/inspectionrecorditem/insert',
		type:'post',
		data:data,
		cache:false,
		success: function(data){
			console.log(data);	
			$(".INSPECTIONRECORD_ID").val(data.INSPECTIONRECORD_ID);
		},
		error:function(error){
			console.log(error)
		}
	})
}
/*点击复选框后提交数据*/
function submitCheckData(n,id){
	console.log("change occured");
	var id = id;																//当前input的id
	var equCode = $("#equCode").val();											//设备编码
	var n = n;																	//第n次检查
	var INSPECTIONRECORD_ID = $(".INSPECTIONRECORD_ID").val();
	var itemID = $('#'+ id).parent().siblings('.checkItemName').text();		//当前检查项
	var userID = $(".userID").val();	
	var flag = "";
	if($('#'+ id).is(":checked")){
		flag = "*1";
	}else{
		flag = "(1";
	}
	console.log(flag);
	var data = {DISPLAYORDERID:itemID,INSPECTIONITEMRESULT:flag,ID:n,EQUIPMENTCODE:equCode,INSPECTOR:userID,INSPECTIONTICKETCATEGORY:'04',INSPECTIONRECORD_ID:INSPECTIONRECORD_ID};
	console.log(data);
	$.ajax({
		url:locationUrl + '/dafeng_mes/inspectionrecorditem/insert',
		type:'post',
		data:data,
		cache:false,
		success: function(data){
			console.log(data);	
			$(".INSPECTIONRECORD_ID").val(data.INSPECTIONRECORD_ID);
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