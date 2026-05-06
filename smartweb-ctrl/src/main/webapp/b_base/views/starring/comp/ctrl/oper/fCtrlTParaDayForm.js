$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	
	$("#platNo").change(function(){
		getDetail();
	});
	
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
	confirmx('是否新增平台日切', function(){
		save();
	});
}

/**
 * 保存函数--保存平台日切新增
 * @returns
 */
function save(){
	var formData = {
			platDate:getDateValue('newPlatDate')
	}

	/*向后台发送参数*/
	$.post(ctx + "/comp/ctrl/oper/fCtrlTParaDay/dayChgNotice", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "平台日切["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("平台日切成功");
				getDetail();
			}
	}, "json");
	
}

/*查询明细*/
function getDetail(){
	var platNo = getS("platNo");
	$.post(ctx + "/comp/ctrl/oper/fCtrlTParaDay/get", {platNo:platNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#platStat').multiselect("select", [jsonObj.platStat]).multiselect('rebuild');
					$('#platStat').multiselect("disable");
					setDateValue('platDate', jsonObj.platDate);
					setDateValue('newPlatDate',addDateOne(jsonObj.platDate));
					if (jsonObj.platStat == "2") {
						$("#saveBtn").attr('disabled', 'true');
					} else {
						$("#saveBtn").removeAttr('disabled');
					}
				}
			}
			
			/*触发校验*/
			$('#platStat').blur();
			$('#platDate').blur();

		}
	},
    "json");
}

function addDateOne(date) {
	var date1 = date.slice(0,4) + "/" + date.slice(4,6) + "/" + date.slice(6);
	var date2 = new Date(Date.parse(date1));
	date2.setDate(date2.getDate()+1);
	var date3=date2.format("yyyyMMdd");
	return date3;
}
function cancle(){
	closeBtnToDo(); 
}