
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出compNo*/
	var HID_compNo = $.session.get('HID_compNo');
	/*从session中移除compNo*/
	$.session.remove('HID_compNo');
	$("#HID_compNo").val(HID_compNo);
	setSelect1("netRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
	getDetail(HID_compNo);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(compNo){
	console.info('get tfsvrSvrPortPara info......');
	$.post(ctx + "/comp/fsvr/tec/tfsvrSvrPortPara/get", {compNo:compNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#compNo').multiselect("select", [jsonObj.compNo]).multiselect('rebuild');
					$('#compNo').multiselect("disable");
					$('#port').val(jsonObj.port);	
				}
			}

		}
	},
    "json");
}