
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出brch*/
	var HID_brch = $.session.get('HID_brch');
	/*从session中移除brch*/
	$.session.remove('HID_brch');
	$("#HID_brch").val(HID_brch);
	/*从session中拿出tlrNo*/
	var HID_tlrNo = $.session.get('HID_tlrNo');
	/*从session中移除tlrNo*/
	$.session.remove('HID_tlrNo');
	$("#HID_tlrNo").val(HID_tlrNo);

	getDetail(HID_brch, HID_tlrNo);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(brch, tlrNo){
	console.info('get tParaTlr info......');
	$.post(ctx + "/comp/ctrl/oper/tParaTlr/get", {brch:brch, tlrNo:tlrNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#brch').multiselect("select", [jsonObj.brch]).multiselect('rebuild');
					$('#brch').multiselect("disable");
					$('#tlrNo').val(jsonObj.tlrNo);	
					$('#tlrName').val(jsonObj.tlrName);	
					$('#tlrLvl').multiselect("select", [jsonObj.tlrLvl]).multiselect('rebuild');
					$('#tlrLvl').multiselect("disable");
					$('#flg').multiselect("select", [jsonObj.flg]).multiselect('rebuild');
					$('#flg').multiselect("disable");
				}
			}

		}
	},
    "json");
}