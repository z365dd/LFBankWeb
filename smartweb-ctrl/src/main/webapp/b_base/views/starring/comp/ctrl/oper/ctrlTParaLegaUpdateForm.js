
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出legaNo*/
	var HID_legaNo = $.session.get('HID_legaNo');
	/*从session中移除legaNo*/
	$.session.remove('HID_legaNo');

	getDetail(HID_legaNo);
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			submit();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function submit(){
	confirmx('是否更新法人管理', function(){
		save();
	});
}

/**
 * 保存函数--保存法人管理修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaLegaForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaLega/update", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "修改交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("修改交易成功");
				cancle();
			}
	}, "json");
	
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(HID_legaNo){
	console.info('update ctrlTParaLega info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaLega/get", {legaNo:HID_legaNo}, function(data){
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
				}
			}
			
			/*触发校验*/
			$('#legaNo').blur();
			$('#legaName').blur();
			$('#tntNo').blur();

		}
	},
    "json");
}