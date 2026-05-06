
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出relatSys*/
	var HID_relatSys = $.session.get('HID_relatSys');
	/*从session中移除relatSys*/
	$.session.remove('HID_relatSys');

	getDetail(HID_relatSys);
	
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
	confirmx('是否更新关联系统管理', function(){
		save();
	});
}

/**
 * 保存函数--保存关联系统管理修改
 * @returns
 */
function save(){
	var formData = $("#ctrlTParaRelatSysForm").serializeObject();
	var dimFlgStr = "NNNNNNNNNNNNNNNNNNN";
	 $('input[name="dimFlg"]:checked').each(function(){
		 var i = parseInt($(this).val());
		 dimFlgStr = dimFlgStr.substr(0,i) + "Y" + dimFlgStr.substr(i+1);

	 });
	 formData.dimFlg = dimFlgStr;
	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaRelatSys/update", formData,
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
function getDetail(HID_relatSys){
	console.info('update ctrlTParaRelatSys info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaRelatSys/get", {relatSys:HID_relatSys}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#relatSys').val(jsonObj.relatSys);	
					$('#sysTp').multiselect("select", [jsonObj.sysTp]).multiselect('rebuild');
					$('#sysName').val(jsonObj.sysName);	
					$('#membId').val(jsonObj.membId);	
					$('#openStat').multiselect("select", [jsonObj.openStat]).multiselect('rebuild');
					$('#sysStat').multiselect("select", [jsonObj.sysStat]).multiselect('rebuild');
					$('#origSysStat').multiselect("select", [jsonObj.origSysStat]).multiselect('rebuild');
					$('#clrBrch').val(jsonObj.clrBrch);	
					$('#clrBank').val(jsonObj.clrBank);	
					setDateValue('othDate', jsonObj.othDate);
					setDateValue('origOthDate', jsonObj.origOthDate);
					$('#nodeStat').multiselect("select", [jsonObj.nodeStat]).multiselect('rebuild');
					$('#loginStat').multiselect("select", [jsonObj.loginStat]).multiselect('rebuild');
					$('#hldFlg').multiselect("select", [jsonObj.hldFlg]).multiselect('rebuild');
					$('#msgSkey').val(jsonObj.msgSkey);	
					$('#seqCrtId').val(jsonObj.seqCrtId);	
					$('#fileSvrId').val(jsonObj.fileSvrId);	
					$('#loginId').val(jsonObj.loginId);	
					$('#loginPwd').val(jsonObj.loginPwd);	
					$('#reasnDesc').val(jsonObj.reasnDesc);	
					//$('#dimFlg').val(jsonObj.dimFlg);	
					var str = jsonObj.dimFlg;
					if (str.charAt(0) == 'Y') {
						$("#forcOut").prop("checked", true); 
					}
					if (str.charAt(1) == 'Y') {
						$("#login").prop("checked", true); 
					}
					if (str.charAt(2) == 'Y') {
						$("#logout").prop("checked", true); 
					}
					if (str.charAt(3) == 'Y') {
						$("#outNoti").prop("checked", true); 
					}
					if (str.charAt(4) == 'Y') {
						$("#statMod").prop("checked", true); 
					}
					if (str.charAt(5) == 'Y') {
						$("#pwd").prop("checked", true); 
					}
					if (str.charAt(6) == 'Y') {
						$("#comm").prop("checked", true); 
					}
				}
			}
			
			/*触发校验*/
			$('#relatSys').blur();
			$('#sysTp').blur();
			$('#sysName').blur();
			$('#membId').blur();
			$('#openStat').blur();
			$('#sysStat').blur();
			$('#origSysStat').blur();
			$('#clrBrch').blur();
			$('#clrBank').blur();
			$('#othDate').blur();
			$('#origOthDate').blur();
			$('#nodeStat').blur();
			$('#loginStat').blur();
			$('#hldFlg').blur();
			$('#msgSkey').blur();
			$('#seqCrtId').blur();
			$('#fileSvrId').blur();
			$('#loginId').blur();
			$('#loginPwd').blur();
			$('#reasnDesc').blur();
			//$('#dimFlg').blur();

		}
	},
    "json");
}