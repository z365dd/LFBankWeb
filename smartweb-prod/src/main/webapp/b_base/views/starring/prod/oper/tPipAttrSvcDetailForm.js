
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	compNo = $.session.get('compNo');
	svcCode = $.session.get('svcCode');
	/*从session中移除id*/
	$.session.remove('compNo');
	$.session.remove('svcCode');
	$("#compNo").val(compNo);
	$("#svcCode").val(svcCode);
	getDetail(compNo, svcCode);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});

	compList();
	svcList();
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(compNo, svcCode){
	console.info('get tPipAttrSvc info......');
	$.post(ctx + "/prod/oper/tPipAttrSvc/get", {compNo:compNo,svcCode:svcCode}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				if (data.dataSetResult[i].dataSetName == "tPipCompSvcParaDODs") {
					var list = data.dataSetResult[i].data;
					if (undefined != list) {
						for(var i=0;i<list.length;i++){
							list[i].action = "<a href='javascript:;' onclick='deleteRow(this)'>删除</a>";
						}
					}
					$('#attrTable').bootstrapTable('load', list);
				}

				if (data.dataSetResult[i].dataSetName == "tPipSvcCompScenDODs") {
					var list = data.dataSetResult[i].data;
					if (undefined != list) {
						for(var i=0;i<list.length;i++){
							list[i].action = "<a href='javascript:;' onclick='deleteRow(this)'>删除</a>";
						}
					}
					$('#table').bootstrapTable('load', list);
				}

				if (data.dataSetResult[i].dataSetName == "tPipSvcDODs") {
					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
						var jsonObj = data.dataSetResult[i].data[j];
						console.info(jsonObj);
						$('#compNo').multiselect("select", [jsonObj.compNo]).multiselect('rebuild');
						$('#compNo').multiselect("disable");
						$('#svcCode').multiselect("select", [jsonObj.svcCode]).multiselect('rebuild');
						$('#svcCode').multiselect("disable");
						$('#svcName').val(jsonObj.svcName);
						$('#signFlg').multiselect("select", [jsonObj.signFlg]).multiselect('rebuild');
						$('#signFlg').multiselect("disable");
						$('#othConnFlg').multiselect("select", [jsonObj.othConnFlg]).multiselect('rebuild');
						$('#othConnFlg').multiselect("disable");
					}
				}
			}

		}
	},
    "json");
}

/**
 * 获取组件下拉框
 */
function compList() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("compNo", "/prod/comp/tPipComp/list", "compNo", "compName", datass, "nulls", false, false);
}

/**
 * 获取服务码下拉框
 */
function svcList() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("svcCode", "/prod/oper/tPipSvc/list", "svcCode", "svcName", datass, "nulls", false, false);
	$("#svcCode").on('change', function(){
		$("#svcName").val($('#svcCode option:selected').text());
	});
}