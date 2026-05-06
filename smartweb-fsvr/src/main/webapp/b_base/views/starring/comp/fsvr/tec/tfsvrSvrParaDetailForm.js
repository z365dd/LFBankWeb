console.log('tfsvrSvrParaDetailForm.js');
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出fileSvrId*/
	var HID_fileSvrId = $.session.get('HID_fileSvrId');
	/*从session中移除fileSvrId*/
	$.session.remove('HID_fileSvrId');
	$("#HID_fileSvrId").val(HID_fileSvrId);
	setSelect1("deponFileSvrId","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",null,false);
	getDetail(HID_fileSvrId);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(fileSvrId){
	console.info('get tfsvrSvrPara info......');
	$.post(ctx + "/comp/fsvr/tec/tfsvrSvrPara/get", {fileSvrId:fileSvrId}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#fileSvrId').val(jsonObj.fileSvrId);	
					$('#svrDesc').val(jsonObj.svrDesc);	
					$('#commProtGrpTp').multiselect("select", [jsonObj.commProtGrpTp]).multiselect('rebuild');
					$('#commProtGrpTp').multiselect("disable");
					$('#openSvcFlg').multiselect("select", [jsonObj.openSvcFlg]).multiselect('rebuild');
					$('#openSvcFlg').multiselect("disable");
					$('#ip').val(jsonObj.ip);	
					$('#port').val(jsonObj.port);	
					$('#userNo').val(jsonObj.userNo);	
					$('#pwd1').val(jsonObj.pwd);	
					$('#pwd2').val(jsonObj.pwd);	
					$('#stat').multiselect("select", [jsonObj.stat]).multiselect('rebuild');
					$('#stat').multiselect("disable");
					$('#downloadFilePath').val(jsonObj.downloadFilePath);	
					$('#uploadFilePath').val(jsonObj.uploadFilePath);	
					$('#contFlg').multiselect("select", [jsonObj.contFlg]).multiselect('rebuild');
					$('#contFlg').multiselect("disable");
					//$('#membId').val(jsonObj.membId);	
					if (jsonObj.deponList != undefined && jsonObj.deponList != "") {
						var deponArr = jsonObj.deponList;
						var arrS = [];
						for (var a = 0; a < deponArr.length; a++) {
							arrS.push(deponArr[a].netRegion); 
						}
						var netRegionStr = arrS.join(";");
						setS("deponFileSvrId", netRegionStr);
					}
					$('#deponFileSvrId').multiselect("disable");
				}
			}

		}
	},
    "json");
}