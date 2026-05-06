
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出dictTp*/
	var dictTp = $.session.get('dictTp');
	/*从session中移除dictTp*/
	$.session.remove('dictTp');
	$("#dictTp").val(dictTp);

	getDetail(dictTp);
	
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
	confirmx('是否更新页面参数', function(){
		save();
	});
}

/**
 * 保存函数--保存页面参数修改
 * @returns
 */
function save(){
	var formData = $("#tSysDictForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/tSysDict/update", formData,
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
function getDetail(dictTp){
	console.info('update tSysDict info......');
	$.post(ctx + "/sys/tSysDict/get", {dictTp:dictTp}, function(data){
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
			
			/*触发校验*/
			$('#dictTp').blur();
			$('#dictInfo').blur();
			$('#dictLabel').blur();
			$('#dictVal').blur();
			$('#sort').blur();

		}
	},
    "json");
}