var unixKey = "";
var rulesKey = "";
$(document).ready(function(){
	chgHeight(500);
	parent.window.$("#iframe_import").show();
//	getCenters();
	getTenancies()
	$("#importBtn").click(function(){
		$("#messageBox").hide();
		if (proof()) {
			save();
		}
	});
	$("#cancleBtn").click(function(){
		cancle();
	});
	$(".divCacheCertrId").hide();
	$(".divStorgRuleTp").hide();
	$(".divReadAuthLvl").hide();
	$("#storgRuleTp").multiselect("disable");
	$("#readAuthLvl").multiselect("disable");
});
function getCenters() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("cacheCentrId", "/para/center/list", "id", "chName", datass, "nulls", true, false);
}

function getTenancies() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("tntNo", "/para/rules/stg/auth/getTenanices", "engName", "name", datass, "nulls", true, false);
}

// function save() {
// 	var formData = $("#addForm").serializeObject();
// 	/*向后台发送参数*/
// 	$.post(ctx + "/cache/test/local/import", formData,
// 		function(data){
// 			if(data.returnCode!==undefined && "0000"!=data.returnCode){
// 				console.info(data);
// 			    var errMsg = "错误信息["+data.message+"]";
// 				showContent(errMsg,"error");
// 				return '0';
// 			}else if(data.msg_type == "success"){
// 				var ret = data.dataSetResult[0].dataSetName;
// 				if (ret != "[]") {
// 					var successMsg = "导入成功";
// 					showTip(successMsg,"success");
// 					$("#tableDiv").show();
// 					$('#table').bootstrapTable('load', JSON.parse(ret));
// 				} else {
// 					var successMsg = "导入失败["+data.message+"]";
// 					showContent(successMsg,"success");
// 					$("#tableDiv").hide();
// 				}
//
// 			}
// 	}, "json");
// }

function save() {
	$("#storgRuleTp").multiselect("rebuild");
	$("#readAuthLvl").multiselect("rebuild");
	console.log("导入");
	var options = {
		url: ctx+"/para/test/local/import",
		type: 'post',
		dataType: 'json',
		clearFrom: false,
		resetForm: false,
		success: function (data) {
			if(data.returnCode!==undefined && "0000"!=data.returnCode){
				console.info(data);
				var errMsg = "错误信息["+data.message+"]";
				showContent(errMsg,"error");
				$("#storgRuleTp").multiselect("disable");
				$("#readAuthLvl").multiselect("disable");
				return '0';
			}else if(data.msg_type == "success"){
				var ret = data.dataSetResult[0].dataSetName;
				if (ret != "[]") { // 有错误的列表
					var successMsg = "导入成功";
					showTip(successMsg,"success");
					$("#tableDiv").show();
					$('#table').bootstrapTable('load', JSON.parse(ret));
					$("#storgRuleTp").multiselect("disable");
					$("#readAuthLvl").multiselect("disable");
				} else {
					var successMsg = "导入成功["+data.message+"]";
					showContent(successMsg,"success");
					$("#tableDiv").hide();
					cancel();
				}
			}
		},
		error: function (xhr, status, msg) {
			showTip(msg, "error");
			$("#storgRuleTp").multiselect("disable");
			$("#readAuthLvl").multiselect("disable");
		}
	};
	$("#addForm").ajaxSubmit(options);
}


function cancel(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*改变内容高度*/
function chgHeight(h){
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_add").find('iframe').height(Height);
}