
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出busiNo*/
	var HID_busiNo = $.session.get('HID_busiNo');
	/*从session中移除busiNo*/
	$.session.remove('HID_busiNo');
	$("#HID_busiNo").val(HID_busiNo);
	/*从session中拿出brch*/
	var HID_brch = $.session.get('HID_brch');
	/*从session中移除brch*/
	$.session.remove('HID_brch');
	$("#HID_brch").val(HID_brch);

	getDetail(HID_busiNo, HID_brch);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(busiNo, brch){
	console.info('get ctrlTPipBusiBrchOpen info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTPipBusiBrchOpen/get", {busiNo:busiNo, brch:brch}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#busiNo').multiselect("select", [jsonObj.busiNo]).multiselect('rebuild');
					$('#busiNo').multiselect("disable");
					$('#brch').multiselect("select", [jsonObj.brch]).multiselect('rebuild');
					$('#brch').multiselect("disable");  
				}
			}

		}
	},
    "json");
}