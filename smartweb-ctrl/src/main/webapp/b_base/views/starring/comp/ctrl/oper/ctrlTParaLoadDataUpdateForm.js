
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出paraName*/
	var HID_paraName = $.session.get('HID_paraName');
	/*从session中移除paraName*/
	$.session.remove('HID_paraName');

	getDetail(HID_paraName);
	
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
	confirmx('是否更新数据装载管理', function(){
		save();
	});
}

/**
 * 保存函数--保存数据装载管理修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaLoadDataForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaLoadData/update", formData,
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
function getDetail(HID_paraName){
	console.info('update ctrlTParaLoadData info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaLoadData/get", {paraName:HID_paraName}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#paraName').val(jsonObj.paraName);	
					$('#tabName').val(jsonObj.tabName);	
					$('#srcTabDesc').val(jsonObj.srcTabDesc);	
					$('#sameDbFlg').multiselect("select", [jsonObj.sameDbFlg]).multiselect('rebuild');
					$('#srcDataSrc').multiselect("select", [jsonObj.srcDataSrc]).multiselect('rebuild');
				}
			}
			
			/*触发校验*/
			$('#paraName').blur();
			$('#tabName').blur();
			$('#srcTabDesc').blur();

		}
	},
    "json");
}