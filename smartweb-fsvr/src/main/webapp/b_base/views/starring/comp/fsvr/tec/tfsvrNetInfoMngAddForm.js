console.log('tfsvrNetInfoMngAddForm.js');

var fileSvrId = '';

$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	setSelect1("deponNetRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
		
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
		if(portion('tableDiv') && portion('memId')){
			tableActionRow('table', "add");
		} else {
			showTip("请输入完整的正确信息!", "error");
		}
	});

	/* table确认修改 */
	$('#reviceRowBtn').click(function() {
		if(portion('tableDiv') && portion('memId')){
			tableActionRow('table', "revice");
		} else {
			showTip("请输入完整的正确信息!", "error");
		}
	}); 
});


/* 取值，table新增一行 */
function tableActionRow(tableId, ifAddRevice) {
		var Data = {
				fileSvrId : getI('fileSvrId'),
				fileSvrTp : "A1",
				svrDesc : getI('svrDesc'),
				ip : getI('fileSvrId'),
				port : getI('port'),
				/*downloadFilePath : getI('downloadFilePath'),
				uploadFilePath : getI('downloadFilePath'),*/
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
	/*setI("downloadFilePath", data.downloadFilePath);*/
	setI("membId", data.membId);
	setS("openSvcFlg", data.openSvcFlg);
	setS("fileSvrStat", data.fileSvrStat);
	fileSvrId = data.fileSvrId;
}

function clearTableInput(){
	setI("fileSvrId", "");
	setI("svrDesc", "");
	setI("port", "");
	/*setI("downloadFilePath", "");*/
	//setI("membId", "");
	setS("openSvcFlg", "");
	setS("fileSvrStat", "");
	fileSvrId = "";
}
function submit(){
	confirmx('是否新增网络信息', function(){
		save();
	});
}

/**
 * 保存函数--保存网络信息新增
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
			deponNetRegion : getS('deponNetRegion'),
			list : row
	}

	/*向后台发送参数*/
	$.post(ctx + "/comp/fsvr/tec/tfsvrNetInfoMng/insert", data,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "新增交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("新增交易成功");
				cancle();
			}
	}, "json");
	
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}