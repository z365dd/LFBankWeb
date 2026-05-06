
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	getCompList();
	
	getDetail(id);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
	
});


function getCompList() {
	datass = {flg:"1", start:'0', pageSize:'0'};
	setSelect2("compNo", "/prod/comp/tPipComp/list", "compNo", "compName", datass, "nulls", false, false);
	$("#compNo").on('change', function(){
		compNo = $(this).val();
		getSvc(compNo)
	});
}

function getSvc(compNo) {
	datass = {compNo:compNo, start:'0', pageSize:'0'};
	setSelect2("svcCode", "/prod/comp/tPipComp/getSvc", "svcScen", "svcScenName", datass, "nulls", false, false);
}


function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('get tPipAtomProd info......');
	$.post(ctx + "/prod/oper/definition/atom/tPipAtomProd/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					console.info(jsonObj);
					$('#atomProdCode').val(jsonObj.atomProdCode);	
					$('#atomProdDesc').val(jsonObj.atomProdDesc);	
					$('#compNo').multiselect("select", jsonObj.compNo).multiselect('rebuild');
					getSvc(jsonObj.compNo);
					setS("svcCode", jsonObj.longRmrk, ";");
				}
			}

		}
	},
    "json");
}