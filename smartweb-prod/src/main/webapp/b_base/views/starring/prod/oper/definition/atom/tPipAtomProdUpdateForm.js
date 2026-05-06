
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	//setS("readAuthList", jsonObj.readListStr, ";");
	getCompList();
	getDetail(id);
	
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



function getCompList() {
	datass = {flg:"1", start:'0', pageSize:'0'};
	setSelect2("compNoSelect", "/prod/comp/tPipComp/list", "compNo", "compName", datass, "nulls", false, false);
	$("#compNoSelect").on('change', function(){
		compNo = $(this).val();
		getSvc(compNo);
	});
}

function getSvc(compNo) {
	datass = {compNo:compNo, start:'0', pageSize:'0'};
	setSelect2("svcCode", "/prod/comp/tPipComp/getSvc", "svcScen", "svcScenName", datass, "nulls", false, false);
}


function submit(){
	confirmx('是否更新原子产品', function(){
		save();
	});
}

/**
 * 保存函数--保存原子产品修改
 * @returns
 */
function save(){
	getSvcData();
	var formData = $("#tPipAtomProdForm").serializeObject();
	if($("#data").val() == "") {
		showTip("场景服务不能为空","error");
		return '0';
	}
	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/definition/atom/tPipAtomProd/update", formData,
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

function getSvcData() {
	var data = getS("svcCode");
	$("#data").val(data);
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(id){
	console.info('update tPipAtomProd info......');
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
					$('#compNo').val(jsonObj.compNo)
					$('#compNoSelect').multiselect("select", jsonObj.compNo).multiselect('rebuild');
					$('#compNoSelect').multiselect('disable');
					getSvc(jsonObj.compNo);
					setS("svcCode", jsonObj.longRmrk, ";");
				}
			}
			
			/*触发校验*/
			$('#atomProdCode').blur();
			$('#atomProdDesc').blur();

		}
	},
    "json");
}