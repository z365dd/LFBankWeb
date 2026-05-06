
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getDetail(id);
	
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
	confirmx('是否更新权限维度', function(){
		save();
	});
}

/**
 * 保存函数--保存权限维度修改
 * @returns
 */
function save(){
	var formData = $("#sysPermissionWeightForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/sys/sysPermissionWeight/update", formData,
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
function getDetail(id){
	console.info('update sysPermissionWeight info......');
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
					$('#authLvlSwitchFlg').multiselect("select", [jsonObj.authLvlSwitchFlg]).multiselect('rebuild');
					$('#rmrk').val(jsonObj.rmrk);
				}
			}
			
			/*触发校验*/
			$('#wghtName').blur();
			$('#switchFlg').blur();
			$('#authLvlSwitchFlg').blur();

		}
	},
    "json");
}