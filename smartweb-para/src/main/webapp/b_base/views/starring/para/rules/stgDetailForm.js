var num;
$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	chgHeight(50);
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);
	$(".storgRuleTpParam").hide();
	$(".storgRuleTpCache").hide();
	getCenters();
	getWriteParts();
	getDetail(id);
	
	/*取消按钮*/
	$("button[id^='cancleBtn']").click(function(){
		cancle();
	});
	
});


/*获取缓存中心*/
function getCenters() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("cacheCentrId", "/para/center/list", "id", "chName", datass, "nulls", false, false);
}

/*获取缓存中心详情信息*/
function getDetail(id){
	console.info('get rules info......');
	$.post(ctx + "/para/rules/stg/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			var list = data.dataSetResult[1].data;
			$("#table").bootstrapTable('load', list);
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				var dataSetName = data.dataSetResult[i].dataSetName;
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					if (dataSetName === "stgDs") {
						stgInfo(jsonObj);
					}
				}
			}
		}
	},
    "json");
};

/*存储规则-基本信息*/
function stgInfo(jsonObj) {

	$("#chName").val(jsonObj.chName);
	$("#engName").val(jsonObj.engName);
	$("#uniqKey").val(jsonObj.uniqKey);
	$("#tabName").val(jsonObj.tabName);
	$("#srcDataSrc").val(jsonObj.srcDataSrc);
	if ( jsonObj.sameDbFlg == "" ) {
		$('#sameDbFlg').multiselect("select", "Y").multiselect('rebuild');
	} else {
		$('#sameDbFlg').multiselect("select", jsonObj.sameDbFlg).multiselect('rebuild');
	}
	$('#sameDbFlg').multiselect("disable");
	if ( jsonObj.busiNoFlg == "" ) {
		$('#busiNoFlg').multiselect("select", "N").multiselect('rebuild');
	} else {
		$('#busiNoFlg').multiselect("select", jsonObj.busiNoFlg).multiselect('rebuild');
	}
	$('#busiNoFlg').multiselect("disable");
	
	if(jsonObj.sameDbFlg == "N") {
		$(".divSrcDataSrc").show();
		$("#srcDataSrc").attr("min","1");
		$("#srcDataSrc").attr("max","19");
		$("#srcDataSrc").attr("check-empty","true");
	} else {
		$(".divSrcDataSrc").hide();
	}
	
//	if (jsonObj.readAuthLvl !== "00") {
//		$(".readAuthList").css("display", "block");
//		$(".readAuthListInput").hide();
//		if (jsonObj.readAuthLvl == "01"){
//			getTenancies();
//		}else if(jsonObj.readAuthLvl == "02"){
//			getReadParts();
//		}
//		setS("readAuthList", jsonObj.readListStr, ";");
//	}
//	setS("writeAuthList", jsonObj.writeListStr, ";");
}

/*获取本租户与下级租户列表*/
function getTenancies() {
	// var datass = {key:"", start:'0', pageSize:'0'};
	// setSelect2("readAuthList", "/para/rules/stg/auth/getChildRents", "enname", "name", datass, "nulls", true, false);
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("readAuthList", "/para/rules/stg/auth/getTenanices", "engName", "name", datass, "nulls", true, false);
	setSelect2("tntNolist", "/para/rules/stg/auth/getTenanices", "engName", "name", datass, "nulls", true, false);
}

/*读取权限 - 获取参与者列表*/
function getReadParts() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("readAuthList", "/para/rules/stg/auth/getParts", "tenantPart", "tenantPartName", datass, "nulls", true, false);
}

/*写入权限 - 获取参与者列表*/
function getWriteParts() {
	var datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("writeAuthList", "/para/rules/stg/auth/getParts", "tenantPart", "tenantPartName", datass, "nulls", true, false);
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}
/*改变内容高度*/
function chgHeight(h) {
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_detail").find('iframe').height(Height);
}

function getPartList() {
	var datass = {tenant:"", start:'0', pageSize:'0'};
	setSelect2("partList", "/para/rules/stg/auth/getPartList", "membNo", "partName", datass, "nulls", true, false);
}