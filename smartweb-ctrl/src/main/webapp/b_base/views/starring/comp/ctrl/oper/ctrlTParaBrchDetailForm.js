
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出brch*/
	var HID_brch = $.session.get('HID_brch');
	/*从session中移除brch*/
	$.session.remove('HID_brch');
	$("#HID_brch").val(HID_brch);

	getDetail(HID_brch);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(brch){
	console.info('get ctrlTParaBrch info......');
	$.post(ctx + "/comp/ctrl/oper/ctrlTParaBrch/get", {brch:brch}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#brch').val(jsonObj.brch);	
					$('#brchName').val(jsonObj.brchName);	
					$('#brchLvlNo').val(jsonObj.brchLvlNo);	
					$('#upBrchName').val(jsonObj.upBrchName);
					$('#legaNo').multiselect("select", [jsonObj.legaNo]).multiselect('rebuild');
					$('#legaNo').multiselect("disable");
					$('#tntNo').multiselect("select", [jsonObj.tntNo]).multiselect('rebuild');
					$('#tntNo').multiselect("disable");
					$('#bank').val(jsonObj.bank);	
					$('#bankName').val(jsonObj.bankName);	
				}
			}

		}
	},
    "json");
}