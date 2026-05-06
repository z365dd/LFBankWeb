
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出tntNo*/
	var HID_tntNo = $.session.get('HID_tntNo');
	/*从session中移除tntNo*/
	$.session.remove('HID_tntNo');
	$("#HID_tntNo").val(HID_tntNo);

	getDetail(HID_tntNo);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(tntNo){
	console.info('get ctrlTParaTnt info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaTnt/get", {tntNo:tntNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#tntNo').val(jsonObj.tntNo);	
					$('#tntName').val(jsonObj.tntName);	
				}
			}

		}
	},
    "json");
}