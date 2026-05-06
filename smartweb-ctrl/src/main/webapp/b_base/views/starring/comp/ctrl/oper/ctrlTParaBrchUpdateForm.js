
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出brch*/
	var HID_brch = $.session.get('HID_brch');
	/*从session中移除brch*/
	$.session.remove('HID_brch');

	getDetail(HID_brch);
	
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
	
	/*清除上级机构父div中的空格*/
	$("#upBrchDiv").html($("#upBrchDiv").html().replace(/&nbsp;/g,""))
	
});

function submit(){
	confirmx('是否更新机构管理', function(){
		save();
	});
}

/**
 * 保存函数--保存机构管理修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaBrchForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaBrch/update", formData,
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
function getDetail(HID_brch){
	console.info('update ctrlTParaBrch info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaBrch/get", {brch:HID_brch}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#brch').val(jsonObj.brch);	
					$('#brchName').val(jsonObj.brchName);	
					$('#brchLvlNo').val(jsonObj.brchLvlNo);	
					$('#upBrchId').val(jsonObj.upBrch);
					$('#upBrchName').val(jsonObj.upBrchName);
					$('#legaNo').multiselect("select", [jsonObj.legaNo]).multiselect('rebuild');
					$('#tntNo').multiselect("select", [jsonObj.tntNo]).multiselect('rebuild');
					$('#bank').val(jsonObj.bank);	
					$('#bankName').val(jsonObj.bankName);	
				}
			}
			
			/*触发校验*/
			$('#brch').blur();
			$('#brchName').blur();
			$('#brchLvlNo').blur();
			$('#upBrch').blur();
			$('#legaNo').blur();
			$('#tntNo').blur();
			$('#bank').blur();
			$('#bankName').blur();

		}
	},
    "json");
}