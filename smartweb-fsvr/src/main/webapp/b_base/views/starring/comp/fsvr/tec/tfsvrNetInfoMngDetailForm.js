
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	setSelect1("deponNetRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
	/*从session中拿出netRegion*/
	var HID_netRegion = $.session.get('HID_netRegion');
	/*从session中移除netRegion*/
	$.session.remove('HID_netRegion');

	getDetail(HID_netRegion);
	/* table初始化 */
	tableClick("table");
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}
//tale修改返回数据
function reviceRow(obj) {
	console.log(obj);
	var $reviceRowData = reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData($reviceRowData);
}
//点击详情回显值
function setReviceRowData(data) {
	setI("fileSvrId", data.fileSvrId);
	setI("svrDesc", data.svrDesc);
	setI("port", data.port);
	setI("membId", data.membId);
	setS("openSvcFlg", data.openSvcFlg);
	setS("fileSvrStat", data.fileSvrStat);
	fileSvrId = data.fileSvrId;
}

/*查询明细*/
function getDetail(netRegion){
	console.info('get tfsvrNetInfoMng info......');
	$.post(ctx + "/comp/fsvr/tec/tfsvrNetInfoMng/get", {netRegion:netRegion}, function(data){
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
							svrArr[a].action = "<a onclick='reviceRow(this)'>详情</a>";
						}
						setI('membId',svrArr[0].memId);
					}
					$('#table').bootstrapTable('load', svrArr);
					disDiv('tfsvrNetInfoMngForm');
				}
			}

		}
	},
    "json");
}