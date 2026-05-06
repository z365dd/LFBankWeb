console.log('tfsvrSvrPortParaUpdateForm.js');
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出compNo*/
	var HID_compNo = $.session.get('HID_compNo');
	/*从session中移除compNo*/
	$.session.remove('HID_compNo');
	setSelect1("netRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",null,false,true);
	getDetail(HID_compNo);
	
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
	
	getCompList();
	
});
function getCompList(){
	datass = {flg:"1", start:'0', pageSize:'0'};
	setSelect2("compNo", "/prod/comp/tPipComp/list", "compNo", "compName", datass, "nulls", false, false);
}
function submit(){
	confirmx('是否更新组件文件传输端口', function(){
		save();
	});
}

/**
 * 保存函数--保存组件文件传输端口修改
 * @returns
 */
function save(){
	var formData = getData();

	/*向后台发送参数*/
	$.post(ctx + "/comp/fsvr/tec/tfsvrSvrPortPara/update", formData,
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
		port:getI('port'),
		compNo:getS("compNo"),
		compName:getST("compNo"),
		netRegion:getS("netRegion")
	}
	return data;
}
function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(HID_compNo){
	console.info('update tfsvrSvrPortPara info......');
	$.post(ctx + "/comp/fsvr/tec/tfsvrSvrPortPara/get", {compNo:HID_compNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#compNo').multiselect("select", [jsonObj.compNo]).multiselect('rebuild');
					$('#compNo').multiselect("disable");
					$('#HID_compNo').val(jsonObj.compNo);
					$('#port').val(jsonObj.port);	
					if (jsonObj.list != undefined && jsonObj.list != "") {
						var deponArr = jsonObj.list;
						var arrS = [];
						for (var a = 0; a < deponArr.length; a++) {
							arrS.push(deponArr[a].netRegion); 
						}
						var netRegion = arrS.join(";");
						setS("netRegion", netRegion);
					} 
				} 
			}
			
			/*触发校验*/
			$('#compNo').blur();
			$('#port').blur();

		}
	},
    "json");
}