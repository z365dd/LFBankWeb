
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出dataSrc*/
	var HID_dataSrc = $.session.get('HID_dataSrc');
	/*从session中移除dataSrc*/
	$.session.remove('HID_dataSrc');

	getDetail(HID_dataSrc);
	
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
	confirmx('是否更新数据源', function(){
		save();
	});
}

/**
 * 保存函数--保存数据源修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaDataSourceForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaDataSource/update", formData,
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
function getDetail(HID_dataSrc){
	console.info('update ctrlTParaDataSource info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaDataSource/get", {dataSrc:HID_dataSrc}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#dataSrc').val(jsonObj.dataSrc);	
					$('#dataSrcName').val(jsonObj.dataSrcName);	
					$('#dbTp').multiselect("select", [jsonObj.dbTp]).multiselect('rebuild');
					$('#dataSrcAddr').val(jsonObj.dataSrcAddr);	
					$('#dbUserName').val(jsonObj.dbUserName);	
					$('#dbPwd').val(jsonObj.dbPwd);	
				}
			}
			
			/*触发校验*/
			$('#dataSrc').blur();
			$('#dataSrcName').blur();
			$('#dbTp').blur();
			$('#dataSrcAddr').blur();
			$('#dbUserName').blur();
			$('#dbPwd').blur();

		}
	},
    "json");
}