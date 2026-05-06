console.log('tfsvrNetInfoMngUpdateForm.js');

var fileSvrId = '';

$(document).ready(function(){
	setMemId();
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	setSelect1("deponNetRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);

	/*从session中拿出netRegion*/
	var HID_netRegion = $.session.get('HID_netRegion');
	/*从session中移除netRegion*/
	$.session.remove('HID_netRegion');

	getDetail(HID_netRegion);
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(portion("saveDiv")){
			submit();
		} else {
			parent.goTop();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});

	/* table初始化 */
	tableClick("table");

	/* 增加btn，table增加一行 */
	$('#addRowBtn').click(function() {
			tableActionRow('table', "add");
	});

	/* table确认修改 */
	$('#reviceRowBtn').click(function() {
			tableActionRow('table', "revice");
	}); 
});


/* 取值，table新增一行 */
function tableActionRow(tableId, ifAddRevice) {
	if (portion("tableDiv" && portion('memId'))) {
		var Data = {
				fileSvrId : getI('fileSvrId'),
				fileSvrTp : "A1",
				svrDesc : getI('svrDesc'),
				ip : getI('fileSvrId'),
				port : getI('port'),
				openSvcFlg : getS('openSvcFlg'),
				membId : getI('membId'),
				fileSvrStat : getS('fileSvrStat'),
				commProtGrpTp : 'FServer',
				action : "<a onclick='reviceRow(this)'>详情</a> <a onclick='deleteRow(this)'>删除</a>"
		}
		var rowArr = $('#table').bootstrapTable('getData');
		var num = rowArr.length;
		if (ifAddRevice == "add") {
			var i = 0
			for (; i < num; i++) {
				if (rowArr[i].fileSvrId == Data.fileSvrId)
					break;
			}
			if (i >= num) {
				// table增加行
				tableAddRow(tableId, Data);
				clearTableInput();
			} else {
				showTip("表格中已有相同的主机名！", "error");
			}
		} else if (ifAddRevice == "revice") {
			if (Data.fileSvrId==fileSvrId) {
				// 修改行
				tableReviceRow(tableId, Data);
				clearTableInput();
			} else {
				var i = 0
				for (; i < num; i++) {
					if (rowArr[i].fileSvrId == Data.fileSvrId)
						break;
				}
				if (i >= num) {
					// 修改行
					tableReviceRow(tableId, Data);
					clearTableInput();
				} else {
					showTip("表格中已有相同的主机名！", "error");
				}
			}

		}
	} else {
		showTip("请输入完整的正确信息!", "error");
	}
}

// tale修改返回数据
function reviceRow(obj) {
	console.log(obj);
	var $reviceRowData = reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData($reviceRowData);
}
// 点击修改回显值
function setReviceRowData(data) {
	setI("fileSvrId", data.fileSvrId);
	setI("svrDesc", data.svrDesc);
	setI("port", data.port);
	setI("membId", data.membId);
	setS("openSvcFlg", data.openSvcFlg);
	setS("fileSvrStat", data.fileSvrStat);
	fileSvrId = data.fileSvrId;
}
function setMemId() {
	var $reviceRowData = reviceRowData(obj);
	var data = $reviceRowData;

}

function clearTableInput(){
	setI("fileSvrId", "");
	setI("svrDesc", "");
	setI("port", "");
	//setI("membId", "");
	setS("openSvcFlg", "");
	setS("fileSvrStat", "");
	fileSvrId = "";
}
function submit(){
	confirmx('是否更新网络信息管理', function(){
		save();
	});
}

/**
 * 保存函数--保存网络信息管理修改
 * @returns
 */
function save(){
	/*获取页面数据*/
	var rowArr = $('#table').bootstrapTable('getData');
	if (rowArr.length < 1) {
		showTip("表格不能为空！", "error");
		return;
	}
	var row = JSON.stringify(rowArr);
	var data = {
			netRegion : getI('netRegion'),
			membId : getI('membId'),
			deponNetRegion : getS('deponNetRegion'),
			list : row
	}
	/*向后台发送参数*/
	$.post(ctx + "/comp/fsvr/tec/tfsvrNetInfoMng/update", data,
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
function getDetail(HID_netRegion){
	console.info('update tfsvrNetInfoMng info......');
	$.post(ctx + "/comp/fsvr/tec/tfsvrNetInfoMng/get", {netRegion:HID_netRegion}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#netRegion').val(jsonObj.netRegion);	
					setS('deponNetRegion',jsonObj.deponNetRegion);
					var svrArr = [];
					if (jsonObj.svrList != undefined && jsonObj.svrList != "") {
						svrArr = jsonObj.svrList;
						for (var a = 0; a < svrArr.length; a++) {
							svrArr[a].action = "<a onclick='reviceRow(this)'>详情</a> <a onclick='deleteRow(this)'>删除</a>";
						}
						setI('membId',svrArr[0].membId);
					}
						
					$('#table').bootstrapTable('load', svrArr);
				}
			}

		}
	},
    "json");
}