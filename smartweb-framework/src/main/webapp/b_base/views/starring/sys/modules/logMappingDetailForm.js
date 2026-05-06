
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	var HID_id = $.session.get('HID_id');
	/*从session中移除id*/
	$.session.remove('HID_id');
	$("#HID_id").val(HID_id);

	getDetail(HID_id);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('get logMapping info......');
	$.post(ctx + "/sys/modules/logMapping/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#requestUri').val(jsonObj.requestUri);	
					$('#uriCname').val(jsonObj.uriCname);	
					$('#logStat').multiselect("select", [jsonObj.logStat]).multiselect('rebuild');
					$('#logStat').multiselect("disable");
					$('#rmrk').val(jsonObj.rmrk);	
				}
			}

		}
	},
    "json");
}