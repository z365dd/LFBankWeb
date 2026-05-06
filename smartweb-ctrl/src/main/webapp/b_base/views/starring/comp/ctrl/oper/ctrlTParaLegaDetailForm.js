
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出legaNo*/
	var HID_legaNo = $.session.get('HID_legaNo');
	/*从session中移除legaNo*/
	$.session.remove('HID_legaNo');
	$("#HID_legaNo").val(HID_legaNo);

	getDetail(HID_legaNo);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(legaNo){
	console.info('get ctrlTParaLega info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaLega/get", {legaNo:legaNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#legaNo').val(jsonObj.legaNo);	
					$('#legaName').val(jsonObj.legaName);	
					$('#tntNo').multiselect("select", [jsonObj.tntNo]).multiselect('rebuild');
					$('#tntNo').multiselect("disable");
				}
			}

		}
	},
    "json");
}