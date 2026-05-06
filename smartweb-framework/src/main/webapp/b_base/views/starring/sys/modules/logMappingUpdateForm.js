
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出id*/
	var HID_id = $.session.get('HID_id');
	/*从session中移除id*/
	$.session.remove('HID_id');
	$("#HID_id").val(HID_id);

	getDetail(HID_id);
	
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
	confirmx('是否更新日志记录映射', function(){
		save();
	});
}

/**
 * 保存函数--保存日志记录映射修改
 * @returns
 */
function save(){
	var formData = $("#logMappingForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/modules/logMapping/update", formData,
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
function getDetail(HID_id){
	console.info('update logMapping info......');
	$.post(ctx + "/sys/modules/logMapping/get", {id:HID_id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#HID_id').val(jsonObj.id);
					$('#requestUri').val(jsonObj.requestUri);	
					$('#uriCname').val(jsonObj.uriCname);	
					$('#logStat').multiselect("select", [jsonObj.logStat]).multiselect('rebuild');
					$('#rmrk').val(jsonObj.rmrk);	
				}
			}
			
			/*触发校验*/
			$('#requestUri').blur();
			$('#uriCname').blur();
			$('#logStat').blur();

		}
	},
    "json");
}