$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_grant").show();
	chgHeight(300);
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);

	getCenters();
	getWriteParts();
	getDetail(id);

	$("#readAuthLvl").on('change', function(){
		// console.log($(this).val());
		$(".readAuthList").css("display", "block");
		$(".readAuthListInput").hide();
		$("input[name='readAuthLvl']").val($(this).val());
		if($(this).val() == "01"){ // 租户
			$(".authlist").show();
			getTenancies();
		}else if($(this).val() == "02") { // 参与者
			$(".authlist").show();
			getReadParts();
		}	else if ($(this).val() == "00") { // 公共
			$(".readAuthList").css("display", "none");
			$(".readAuthListInput").show();
			$(".authlist").hide();
		}
	});

	// $("#tntNolist").on('change', function(){
	// 	var tntNo = $(this).val();
	// 	getPartList(tntNo);
	// });

	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof() && getReadAuthData()){
			save();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
});


/*获取缓存中心*/
function getCenters() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("center", "/para/center/list", "id", "chName", datass, "nulls", true, false);
}

/*获取本租户与下级租户列表*/
function getTenancies() {
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


/*获取缓存中心详情信息*/
function getDetail(id){
	console.info('get rules info......');
	$.post(ctx + "/para/rules/stg/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			col_num = 0;
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				console.log("dataSetName:"+data.dataSetResult[i].dataSetName);
				var dataSetName = data.dataSetResult[i].dataSetName;
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
//					console.log(jsonObj);
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
	// console.log(jsonObj.centerId);
	$('#center').multiselect("select", jsonObj.cacheCentrId).multiselect('rebuild');
	$('#center').multiselect("disable")
	$('#cacheCentrId').val(jsonObj.cacheCentrId);
	$("#tntNo").val(jsonObj.tntNo);
	$("#chName").val(jsonObj.chName);
	$("#engName").val(jsonObj.engName);
	$("#uniqKey").val(jsonObj.uniqKey);
	$("#storgRuleTp").val(jsonObj.storgRuleTp);
	// $("#readAuthLvl").val(jsonObj.readAuthLvl);
	$('#readAuthLvl').multiselect("select", jsonObj.readAuthLvl).multiselect('rebuild');
	$('#storgRuleType').multiselect("select", jsonObj.storgRuleTp).multiselect('rebuild');
	$('#storgRuleType').multiselect("disable");

	if (jsonObj.readAuthLvl != "00") {
		$(".authlist").show();
		$(".readAuthList").css("display", "block");
		$(".readAuthListInput").hide();
		if (jsonObj.readAuthLvl == "01"){
			getTenancies();
		}else if(jsonObj.readAuthLvl == "02"){
			getReadParts();
		}
		setS("readAuthList", jsonObj.readListStr, ";");
	} else {
		$(".authlist").hide();
	}
	setS("writeAuthList", jsonObj.writeListStr, ";");

	if (jsonObj.storgRuleTp == "00") {
		$(".storgRuleTpParam").show();
		$(".storgRuleTpCache").hide();
	} else if (jsonObj.storgRuleTp == "01") {
		$(".storgRuleTpCache").show();
		$(".storgRuleTpParam").hide();
		getTenancies();
		// setS("tntNoList", jsonObj.readListStr, ";");
		var tntNo = jsonObj.readListStr.split("$")[0];
		if (jsonObj.readListStr!="" && jsonObj.readListStr.split("$").length >= 2) {
			var partId = jsonObj.readListStr.split("$")[1].replace(";", "");
		}
		$("#tntNolist").multiselect("select", tntNo).multiselect('rebuild');
		getPartList();
		$("#partList").multiselect("select", partId).multiselect('rebuild');
	}
}

/*获取读取权限*/
function getReadAuthData() {
	$("#readListStr").val("");
	var jsonStr = getS("readAuthList");
	if ($("#storgRuleTp").val() == "00") {
		if ($("#readAuthLvl").val() == "01") {
			if (jsonStr == "") {
				showTip("请选择租户", "error");
				return false;
			}
		}
	} else if ($("#storgRuleTp").val() == "01") {
		var tenantStr = getS("tntNolist");
		var partStr = getS("partList");
		if (tenantStr == "" || partStr == "") {
			showTip("请选择租户与参与者", "error");
			return false;
		}
		jsonStr = tenantStr + "$" + partStr;
	}
	$("#readListStr").val(jsonStr);
	return true;
}

/*获取写入权限*/
function getWrithAuthData() {
	$("#writeListStr").val("");
	var jsonStr = getS("writeAuthList", ";");
	$("#writeListStr").val(jsonStr);
}


/**
 * 保存函数--保存缓存中心信息新增
 * @returns
 */
function save(){
	// getWrithAuthData();
	var formData = $("#grantForm").serializeObject();
	/*向后台发送参数*/
	$.post(ctx + "/para/rules/stg/grant", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "修改存储规则["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("修改存储规则成功");
				cancle();
			}
	}, "json");
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}
/*改变内容高度*/
function chgHeight(h) {
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_update").find('iframe').height(Height);
}

function getPartList() {
	var datass = {tenant:"", start:'0', pageSize:'0'};
	setSelect2("partList", "/para/rules/stg/auth/getPartList", "membNo", "partName", datass, "nulls", true, false);
}