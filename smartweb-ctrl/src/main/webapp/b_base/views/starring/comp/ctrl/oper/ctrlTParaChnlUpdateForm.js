
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出chnlNo*/
	var HID_chnlNo = $.session.get('HID_chnlNo');
	/*从session中移除chnlNo*/
	$.session.remove('HID_chnlNo');

	getDetail(HID_chnlNo);
	
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
	confirmx('是否更新渠道管理', function(){
		save();
	});
}

/**
 * 保存函数--保存渠道管理修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaChnlForm").serializeObject(); 

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaChnl/update", formData,
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
function getDetail(HID_chnlNo){
	console.info('update ctrlTParaChnl info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaChnl/get", {chnlNo:HID_chnlNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#chnlNo').val(jsonObj.chnlNo);	
					$('#chnlName').val(jsonObj.chnlName);	
					//$('#chnlStat').val(jsonObj.chnlStat);	
					$('#chnlDesc').val(jsonObj.chnlDesc);	
					$('#chnlStat').multiselect("select", [jsonObj.chnlStat]).multiselect('rebuild');
					$('#brch').multiselect("select", [jsonObj.brch]).multiselect('rebuild');
					$('#chnlTp').multiselect("select", [jsonObj.chnlTp]).multiselect('rebuild');
					$('#chnlFlg').multiselect("select", [jsonObj.chnlFlg]).multiselect('rebuild');
					$('#tlrNo').multiselect("select", [jsonObj.tlrNo]).multiselect('rebuild');
					$('#relatSys').multiselect("select", [jsonObj.relatSys]).multiselect('rebuild');
				}
			}
			
			/*触发校验*/
			$('#chnlNo').blur();
			$('#chnlName').blur();
			$('#chnlStat').blur();
			$('#chnlDesc').blur();
			$('#brch').blur();
			$('#chnlTp').blur();
			$('#chnlFlg').blur();
			$('#tlrNo').blur();
			$('#relatSys').blur();

		}
	},
    "json");
}