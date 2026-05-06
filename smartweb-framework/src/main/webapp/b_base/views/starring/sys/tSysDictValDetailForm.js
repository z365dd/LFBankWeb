
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_valdetail").show();
	/*从session中拿出dictTp*/
	var dictTp = $.session.get('dictTp');
	/*从session中移除dictTp*/
	$.session.remove('dictTp');
	$("#dictTp").val(dictTp);

	/*从session中拿出dictVal*/
	var dictVal = $.session.get('dictVal');
	/*从session中移除dictVal*/
	$.session.remove('dictVal');
	$("#dictVal").val(dictVal);

	getDetail(dictTp, dictVal);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(dictTp, dictVal){
	console.info('get tSysDict info......');
	$.post(ctx + "/sys/tSysDict/getVal", {dictTp:dictTp,dictVal:dictVal}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#HID_id').val(jsonObj.id);
					$('#dictTp').val(jsonObj.dictTp);	
					$('#dictInfo').val(jsonObj.dictInfo);	
					$('#dictLabel').val(jsonObj.dictLabel);	
					$('#dictVal').val(jsonObj.dictVal);	
					$('#sort').val(jsonObj.sort);	
				}
			}

		}
	},
    "json");
}