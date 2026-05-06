console.log('tfsvrSvrParaUpdateForm.js');
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出fileSvrId*/
	var HID_fileSvrId = $.session.get('HID_fileSvrId');
	/*从session中移除fileSvrId*/
	$.session.remove('HID_fileSvrId');
	setSelect1("deponFileSvrId","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
	getDetail(HID_fileSvrId);
	
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
	confirmx('是否更新外部文件服务器', function(){
		save();
	});
}

/**
 * 保存函数--保存外部文件服务器修改
 * @returns
 */
function save(){
	var formData = getData();

	/*向后台发送参数*/
	$.post(ctx + "/comp/fsvr/tec/tfsvrSvrPara/update", formData,
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
/*获取页面数据data*/
function getData(){
	var data= {
			//界面
			ip:getI('ip'),
			port:getI('port'),
			openSvcFlg:getS("openSvcFlg"),
			svrDesc:getI("svrDesc"),
			fileSvrId:getI('fileSvrId'),
			
			userNo:getI('userNo'),
			commProtGrpTp:getS('commProtGrpTp'),
			pwd:getI('pwd2'),
			downloadFilePath:getI('downloadFilePath'),
			uploadFilePath:getI('uploadFilePath'),
			contFlg:getS("contFlg"),
			//其他默认数据
			fileSvrStat:'01',
			
			//TRAN_TP:"",
			deponFileSvrId:getS("deponFileSvrId")
	}
	return data;
}
function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(HID_fileSvrId){
	console.info('update tfsvrSvrPara info......');
	$.post(ctx + "/comp/fsvr/tec/tfsvrSvrPara/get", {fileSvrId:HID_fileSvrId}, function(data){
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
					$('#openSvcFlg').multiselect("select", [jsonObj.openSvcFlg]).multiselect('rebuild');
					$('#ip').val(jsonObj.ip);	
					$('#port').val(jsonObj.port);	
					$('#userNo').val(jsonObj.userNo);	
					$('#pwd1').val(jsonObj.pwd);	
					$('#pwd2').val(jsonObj.pwd);	
					$('#stat').multiselect("select", [jsonObj.stat]).multiselect('rebuild');
					$('#downloadFilePath').val(jsonObj.downloadFilePath);
					$('#uploadFilePath').val(jsonObj.uploadFilePath);	
					$('#contFlg').multiselect("select", [jsonObj.contFlg]).multiselect('rebuild');
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
				}
			}
			
			/*触发校验*/
			$('#fileSvrId').blur();
			$('#svrDesc').blur();
			$('#fileSvrTp').blur();
			$('#commProtGrpTp').blur();
			$('#openSvcFlg').blur();
			$('#ip').blur();
			$('#port').blur();
			$('#userNo').blur();
			$('#pwd').blur();
			$('#stat').blur();
			$('#downloadFilePath').blur();
			$('#uploadFilePath').blur();
			$('#contFlg').blur();

		}
	},
    "json");
}

function getMultiple(id){
	var arr = [];
	var value = "";
	$("#"+id+" option:selected").each(function(){
		arr.push($(this).val());
	});
	value = arr.join("|");
	return value;
}

