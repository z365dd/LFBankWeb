$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	
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

function submit(){
	confirmx('是否新增原子产品', function(){
		save();
	});
}

/**
 * 保存函数--保存原子产品新增
 * @returns
 */
function save(){
	getSvcData();
	var formData = $("#addForm").serializeObject();
	if($("#data").val() == "") {
		showTip("场景服务不能为空","error");
		return '0';
	}
	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/definition/atom/tPipAtomProd/insert", formData,
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

function getSvcData() {
	var data = getS("svcCode");
	$("#data").val(data);
}

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