
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出relatSys*/
	var HID_relatSys = $.session.get('HID_relatSys');
	/*从session中移除relatSys*/
	$.session.remove('HID_relatSys');
	$("#HID_relatSys").val(HID_relatSys);

	getDetail(HID_relatSys);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(relatSys){
	console.info('get ctrlTParaRelatSys info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaRelatSys/get", {relatSys:relatSys}, function(data){
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
					$('#sysTp').multiselect("disable");
					$('#sysName').val(jsonObj.sysName);	
					$('#membId').val(jsonObj.membId);	
					$('#openStat').multiselect("select", [jsonObj.openStat]).multiselect('rebuild');
					$('#openStat').multiselect("disable");
					$('#sysStat').multiselect("select", [jsonObj.sysStat]).multiselect('rebuild');
					$('#sysStat').multiselect("disable");
					$('#origSysStat').multiselect("select", [jsonObj.origSysStat]).multiselect('rebuild');
					$('#origSysStat').multiselect("disable");
					$('#clrBrch').val(jsonObj.clrBrch);	
					$('#clrBank').val(jsonObj.clrBank);	
					setDateValue('othDate', jsonObj.othDate);
					setDateValue('origOthDate', jsonObj.origOthDate);
					$('#nodeStat').multiselect("select", [jsonObj.nodeStat]).multiselect('rebuild');
					$('#nodeStat').multiselect("disable");
					$('#loginStat').multiselect("select", [jsonObj.loginStat]).multiselect('rebuild');
					$('#loginStat').multiselect("disable");
					$('#hldFlg').multiselect("select", [jsonObj.hldFlg]).multiselect('rebuild');
					$('#hldFlg').multiselect("disable");
					$('#msgSkey').val(jsonObj.msgSkey);	
					$('#seqCrtId').val(jsonObj.seqCrtId);	
					$('#fileSvrId').val(jsonObj.fileSvrId);	
					$('#loginId').val(jsonObj.loginId);	
					$('#loginPwd').val(jsonObj.loginPwd);	
					$('#reasnDesc').val(jsonObj.reasnDesc);	
					$('#dimFlg').val(jsonObj.dimFlg);	
				}
			}

		}
	},
    "json");
}