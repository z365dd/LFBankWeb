
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getDetail(id);
	
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
	console.info('get sysPermissionWeight info......');
	$.post(ctx + "/sys/sysPermissionWeight/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#wghtName').val(jsonObj.wghtName);
					$('#switchFlg').multiselect("select", [jsonObj.switchFlg]).multiselect('rebuild');
					$('#switchFlg').multiselect("disable");
					$('#authLvlSwitchFlg').multiselect("select", [jsonObj.authLvlSwitchFlg]).multiselect('rebuild');
					$('#authLvlSwitchFlg').multiselect("disable");
					$('#rmrk').val(jsonObj.rmrk);
				}
			}

		}
	},
    "json");
}