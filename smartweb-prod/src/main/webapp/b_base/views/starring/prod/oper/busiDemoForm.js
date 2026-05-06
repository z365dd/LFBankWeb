console.log('busiDemoForm');


var SAVE_OR_REV = parent.SAVE_OR_REV;
var detailData = {};
var IF_ASYNC  =true;
if(SAVE_OR_REV!='add'){
	IF_ASYNC = false;
}

//可售产品页面配置
var PER_PAGE_DATA = {};

//可售产品页面配置值
var PROD_DATA = {};

//页面处于修改回显值状态
var SET_DATA_NOW = false;

$(function(){
	if(SAVE_OR_REV=='add'){
		$("#entrSelectBtn").show(); 
	}else{
		$("#entrSelectBtn").hide(); 
	}
	//*返回按钮*/
	$('#cancelBtn').on('click',cancel);

	/*提交按钮*/
	$('#addBtn').on('click',sendData);

	/*立即生效按钮*/
	$('#effect').on('click',sendDataAndEffect);

	//单位编号
	setSelect1("ENTR_NAME","/prod/oper/entrDemo/qry","entrNo","entrName",null,true);

	
	$('#ENTR_NAME').change(function(){
		setI('ENTR_NO',getS('ENTR_NAME'));
	});
	
	// 产品线
	setSelect1("PROD_LINE_CODE","/prod/oper/line/tPipLineProd/list","prodLineCode","prodLineName",null,false);
	
	// 产品线加载可售产品
	$('#PROD_LINE_CODE').change(function(){
		//获取可售产品并加载选择页面
		getSaleProdCode();
		/*20200117 add by zengxj 通用参数*/
		var PROD_LINE_CODE = getS("PROD_LINE_CODE");
		console.log("----------------PROD_LINE_CODE---------------"+PROD_LINE_CODE);
		uploadCommon(PROD_LINE_CODE, undefined, undefined);
	});
	$('#PROD_LINE_CODE').change();
	// 每个块点击事件
	$("#prodField").on("click", "li", liClick);
	//显隐可售产品
	$("#prodField").on("click", ".openBtn", openBtnClick);
	
	//根据可售产品加载页面配置
	//$('#SALE_PROD_CODE').on('change',loadSalePage);
	
	/*保存按钮*/
	$('#settingBtn').click(function(){
		tableActionRow('table',"revice");
	});
	
	//双击事件
	$("#entrListTable").on('dbl-click-row.bs.table', function (evnet, row, $element) {
		$('#modalEntrList').modal('hide');
		var dataArr = [];
		dataArr[0] = {label : row.entrName,value : row.entrNo};
		$('#ENTR_NAME').multiselect('dataprovider', dataArr);
		$('#ENTR_NAME').change();
	});

});

function qryAcctInfo(acct,type){
	var url = ctx + "/prod/oper/busiDemo/factQryAcctInfo";
	$.post(url,{ACCT:acct},
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				if($("#intrmAcct").val()==$("#entrAcct").val()){
					showTip("过渡账户和清算账户/客户账号相同！");
				}
				for(var i = 0 ; i < data.dataSetResult.length; i++){
					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
						var jsonObj = data.dataSetResult[i].data[j];
						if(type=="intrmAcct"){
							$('#intrmAcctName').val(jsonObj.ACCT_NAME);	
						}else{
							$('#entrAcctName').val(jsonObj.ACCT_NAME);	
						}
					}
				}
				return '0';
			}
		}, 
	"json");
}

//打开关闭产品线多层(多余产品显示隐藏)
function openBtnClick() {
	if ($(this).attr("tp") == "open") {
		//已打开，进行关闭操作
		$(this).attr("tp", "close");
		$(this).parents(".centerCon").find("li").slice(9).fadeOut(
				function() {
					if ($(this).parents(".centerCon").find("li").slice(9).is(
							"[boxSelected='true']")) {
						//大于9的可售产品被选中，边框提示隐藏中有被选中的
						$(this).parents(".panelBody").css("border-color",
								"#7ec0ff");
					}
				});
		//改成提示图片
		$(this).css("background-image",
				"url(/smartweb/b_base/views/starring/prod/oper/open.png)");
	} else if ($(this).attr("tp") == "close") {
		//已关闭，进行打开操作
		$(this).attr("tp", "open");
		$(this).parents(".centerCon").find("li").slice(9).fadeIn(function() {
			//取消隐藏选中提示
			$(this).parents(".panelBody").css("border-color", "#ddd");
		});
		$(this).css("background-image",
						"url(/smartweb/b_base/views/starring/prod/oper/close.png)");
	}
}

function getSaleProdCode() {
	var PROD_LINE_CODE = getS("PROD_LINE_CODE");
	var Data={
		prodLineCode:PROD_LINE_CODE
	}
	$
			.ajax({
				url : ctx + "/prod/oper/definition/adapter/tPipSaleProdAdapter/list",
				type : "GET",
				dataType : "json",
				data : Data,
				async : IF_ASYNC,
				success : function(data) {
					console.log("-------------查询可售产品包装----------"+PROD_LINE_CODE);
					if (data.returnCode !== undefined
							&& "0000" != data.returnCode) {
						var errMsg = "错误信息[" + data.message + "]";
						showContent(errMsg, "error");
					} else {
						console.log(data.message);
						var arr = data.dataSetResult[0].data;
						arr = eval(arr);
						
						//产品线json,key以 产品线编号!@#产品线名称，val以 可售产品arr
						var prodData = {};
						//根据产品线分类
						for(var a=0;a<arr.length;a++){
							var arrData = arr[a];
							var prodLineCode = arrData.prodLineCode;
							var prodLineName = arrData.prodLineName;
							if(prodData[prodLineCode+"!@#"+prodLineName]==undefined){
								prodData[prodLineCode+"!@#"+prodLineName] = [];
							}
							var prodJson = {
									saleProdCode:arrData.saleProdCode,
									saleProdDesc:arrData.saleProdDesc,
									IMG_PATH:arrData.url
							}
							prodData[prodLineCode+"!@#"+prodLineName].push(prodJson);
						}
						
						// 重新加载产品线和可售产品
						loadProd(prodData);
					}
				}
			});
}

// 加载产品线、可售产品于tab2
function loadProd(prodData) {
	//清除原有产品线可售产品选择
	$("#prodField").empty();
	//清除原有可售产品配置页面
	$('#busiDiv').empty();
	
	for(var prodCN in prodData){
		var prodLineCode = prodCN.split("!@#")[0];
		var prodLineName = prodCN.split("!@#")[1];
		var arr = prodData[prodCN];
		if (arr == undefined || arr.length==0) {
			return;
		}
		//产品线大块
		var labelField = '<div ravo="rainbow_fx_layout_panel" class="panel panel-default" id="">'
				+ '<div class="panel-heading clearBorder"><div ravo="rainbow_fx_bj"><h4 contenteditable="false">'
				+ prodLineName
				+ '</h4></div></div>'
				+ '<div class="panel-body panelBody" contenteditable="false"><div class="centerCon"><ul>'
				+ '</ul></div></div>' + '</div>';
		var $labelField = $(labelField);
		//加载产品线下的可售产品
		for (var b = 0; b < arr.length; b++) {
			//可售产品信息
			var saleProdData = arr[b];
			var imgPath = saleProdData.IMG_PATH;
			//无图片保存则展示暂无图片
			if(imgPath==undefined || imgPath==""){
				imgPath = '/smartweb/b_base/views/starring/prod/oper/pictures/NoImg.png';
			}
			//可售产品的小块（随机生成图片）
			var liField = '<li><div><img src='
					+ imgPath
					+ '/></div><p title="'+saleProdData.saleProdDesc+'">'
					+ saleProdData.saleProdDesc + '</p></li>';
			var $liField = $(liField);
			// 可售产品编号 自定义属性
			$liField.attr("SALE_PROD_CODE", saleProdData.saleProdCode);
			//产品线块加入可售产品
			$labelField.find("ul").append($liField);
		}
		if (arr.length > 9) {
			//多于9个可售产品隐藏，加上显示控制块
			var openBtn = '<div class="openBtn" tp="close"><div></div></div>';
			$labelField.find("ul").after(openBtn);
			$labelField.find("li").slice(9).hide();
		}
		$("#prodField").append($labelField);
	}
}


/*关闭执行*/
function cancel(){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		parent.tab1();
	});
}


//获取此可售产品业务配置页面的数据存于全局变量PROD_DATA
function getProdData() {
	//var prodNoNow = getS('SALE_PROD_CODE');
	var prodNoNow = getBoxselected()[0].PROD_NO;
	var busiNo = $("#BUSI_NO").val();
	var busiName = $("#BUSI_NAME").val();
	var data = {
		PROD_NO : prodNoNow,
		BUSI_NO : busiNo,
		BUSI_NAME : busiName,
		ENTR_NO : getI('ENTR_NO'),
		ENTR_NAME : getST('ENTR_NAME')
	}
	// KEY_LIST
	var KEY_LIST = [];
	
	
	//当前可售产品页面配置数据
	var perPageArr = PER_PAGE_DATA[prodNoNow];
	//获取页面的值加入到数组
	for(var a=0;a<perPageArr.length;a++){
		var perPageData = perPageArr[a];
		var keyList = perPageData.KEY_LIST;
		if(keyList!=undefined){
			for(var b=0;b<keyList.length;b++){
				//键页面配置信息
				var keyData = keyList[b];
				var keyNo = keyData["KEY_NO"];
				var keyNoId = keyData["KEY_NO"].replace(/[\.]/g,"-_-");
				var keyName = keyData["KEY_NAME"];
				if(keyData["FLG"]=="Y"){
					KEY_LIST.push(joinKeyData(keyNo,keyName,keyData["DEFA_KV"]));
					continue;
				}
				//var keyNo = keyData["KEY_NO"];
				var kv="";
//				
//				var tagName = $("[name="+keyNoId+"]")[0].tagName;
//				var type = $("[name="+keyNoId+"]").attr("type");
//				if(tagName=="INPUT" && type=="text"){
//					kv = getI(keyNoId);
//				}else if(tagName=="SELECT"){
//					kv = getS(keyNoId,"|");
//				}else if(tagName=="INPUT" && (type=="radio" || type=="checkbox")){
//					kv = getB(keyNoId,"|");
//				}else if(tagName=="INPUT" && type=="hidden"){
//					kv = getDateValue(keyNoId);
//				}

				// var strs= new Array();
				// strs=keyNoId.split(".");
				// keyNoId = "";
				// for (j=0;j<strs.length ;j++ ){
				// 	if (j == strs.length-1) {
				// 		keyNoId += strs[j];
				// 	} else {
				// 		keyNoId += strs[j]+"\\.";
				// 	}
				// }

				kv = getS(keyNoId,"|");
				var type = $("[name="+keyNoId+"]").attr("type");
				if(type=="text"){
					kv = getI(keyNoId);
				}
				
				KEY_LIST.push(joinKeyData(keyNo,keyName,kv));
			}
		}
	} 
	//设置键值对数据
	data.KEY_LIST = KEY_LIST;
	//加入可售产品业务参数全局变量
	PROD_DATA[prodNoNow] = data;
	
	return data;
}

//创建KeyList对象
//多加一个键类型（用于回显值判断）
function joinKeyData(key, keyName, kv) {
	var json = {
		KEY_NO : key,
		KEY_NAME : keyName,
		KV : kv,
	};
	return json;
}

//取出选中的产品信息
//busiS取出选中产品业务编号和名称数组，用于加载业务下拉框
//busiList用于取出选中产品的信息数据数组用于传后台
function getBusiSL() {
	var boxSelectedArr = getBoxselected();
	// 数组
	var selectArr = [];
	for (var a = 0; a < boxSelectedArr.length; a++) {
		// 产品编号
		var prodNo = boxSelectedArr[a]["PROD_NO"];
		// 产品属性
		var prodData = PROD_DATA[prodNo];
		selectArr.push(prodData);
	}
	return selectArr;
}

/*获取页面数据data*/
function getData(){
	//获取动态页面信息存于全局变量，有返回数据
	var data = getProdData();
	
	data.PROD_DATA_STR = JSON.stringify(data.KEY_LIST);
	delete data.KEY_LIST;
	
	if(SAVE_OR_REV=='add'){
		data.OPER_TP = '1';
	}else if(SAVE_OR_REV=='revice'){
		data.OPER_TP = '2';
	}
	
	//文件名
	data.FILE_PATH1 = $('[name="fileArea1"]').val();
	data.FILE_PATH2 = $('[name="fileArea2"]').val();
	data.FILE_PATH3 = $('[name="fileArea3"]').val();
	data.bgImg = getI("bgImg");
	
	/*20200117 add by zh*/
	data.busiDesc = $("#busiDesc").val();
	if($("#openGrpChnlNo").val() != undefined){
		data.openGrpChnlNo = $("#openGrpChnlNo").val().join(",");
	}
	if($("#openGrpBrch").val() != undefined){
		data.openGrpBrch = $("#openGrpBrch").val().join(",");
	}
	data.brchId = $("#brchIdId").val();
	data.clrTp = getR("clrTp");
	if(data.clrTp=="00" || data.clrTp=="01"){
		data.intrmAcct = getI("intrmAcct");
		data.intrmAcctName = getI("intrmAcctName");
		data.entrAcct = getI("entrAcct");
		data.entrAcctName = getI("entrAcctName");
		data.inOutBankFlg = getS("inOutBankFlg");
		data.isClrFlg = getS("isClrFlg");
		data.shortRmrk = getI("intrmAcctOpenInst");
		data.entrAcctBank = getI("entrAcctBank");
	}
	data.signPat = getR("signPat");
	data.chkPat = getR("chkPat");
	data.clrFeeTp = getR("clrFeeTp");
	if(data.clrFeeTp=="01" || data.clrFeeTp=="02"){
		data.defFrt = getI("defFrt");
		data.amt = getI("amt");
		data.feeTfInAcct = getI("feeTfInAcct");
		data.feeTfOutAcct = getI("feeTfOutAcct");
	}

	data.busiTp = getR("busiTp");
	if(data.busiTp=="00"){
		data.relatSysNo = getS("relatSysNo");
	}
	data.keyJson = getI("keyJson");
	return data;
}

/*提交执行*/
function sendData(){
	if(!portion('formId_65948')){
		return;
	}
	if (getBoxselected().length == 0) {
		showTip("请选择可售产品", "error");
		return;
	}
//	if ($("#openGrpChnlNo").val() == null) {
//		showTip("请选择业务开通渠道", "error");
//		return;
//	}
	var brchId = $("[name=brchId]").val();
	if (brchId == "brchIdValue") {
		showTip("请选择清算机构", "error");
		return;
	}

	var $data = getData();
	console.info($data);
	$.ajax({
		url:ctx + "/prod/oper/busiDemo/add",
		type:"POST",
		dataType:"json",
		data:$data, 
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "提交["+data.message+"]"; 
				showContent(successMsg,"success");
				parent.tab1();
			}
		}
	});
}

/*提交执行*/
function sendDataAndEffect(){
	if(!portion('formId_65948')){
		return;
	}
	if (getBoxselected().length == 0) {
		showTip("请选择可售产品", "error");
		return;
	}
	// if ($("#openGrpChnlNo").val() == null) {
	// 	showTip("请选择业务开通渠道", "error");
	// 	return;
	// }
	var brchId = $("[name=brchId]").val();
	if (brchId == "brchIdValue") {
		showTip("请选择清算机构", "error");
		return;
	}

	var $data = getData();
	$data.effect = "1";
	console.info($data);
	$.ajax({
		url:ctx + "/prod/oper/busiDemo/add",
		type:"POST",
		dataType:"json",
		data:$data,
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "提交["+data.message+"]";
				showContent(successMsg,"success");
				parent.tab1();
			}
		}
	});
}

//可售产品单击 选中或取消选中(选中的动态加载后面产品配置界面)
function liClick() {
	var $li = $(this);
	console.log("click");
	if ($li.attr("boxSelected") == "false"
			|| $li.attr("boxSelected") == undefined) {
		//未选中，进行选中操作
		//修改为只能选择一个可售产品，需要作判断
		/*if($("#prodField").find("li").is("[boxSelected='true']")){
			
		};*/
		//移除全部所选重新选择
		cancelSelect($("#prodField").find("li[boxSelected='true']"));
		panelBodyBorder();
		
		$li.attr("boxSelected", "true");
		$li.css({
			"border-color" : "#328ee3",
			"outline" : "solid #328ee3 1px"
		});
		
	} else if ($li.attr("boxSelected") == "true") {
		//已选中，进行取消选中操作
		cancelSelect($li);
	}
	// 动态加载界面
	loadSalePage();
}
//取消选择样式操作
function cancelSelect(obj){
	obj.attr("boxSelected", "false");
	obj.css({
		"border-color" : "#ddd",
		"outline" : "solid #328ee3 0px"
	});
}
//判断所有产品线超过序号9的产品有没有被选中改变大边框颜色
function panelBodyBorder() {
	// 每条产品线的内容区域
	var $prodLineBody = $("#prodField").find(".panelBody");
	$prodLineBody.each(function() {
		if ($(this).find("li").slice(9).is("[boxSelected='true']")) {
			$(this).css("border-color", "#7ec0ff");
		}else{
			$(this).css("border-color", "#ddd");
		}
	});
}
//获取被选中可售产品的 json数组
//产品号、产品名称、组件编号拼接字符串
function getBoxselected() {
	var arr = [];
	var $li = $("#prodField").find("li[boxSelected='true']");
	$li.each(function() {
		var json = {
			PROD_NO : $(this).attr("SALE_PROD_CODE"),
			PROD_NAME : $(this).find("p").text(),
		}
		arr.push(json);
	});
	return arr;
}

//动态加载页面、回显值
function loadSalePage(){
	$tabPage = $('#busiDiv');
	$tabPage.empty();
	
	// 被选中的可售产品信息（可售产品编号、名称、组件拼接字符串）
	var boxSelectedArr = getBoxselected();
	//var PROD_NO_NOW = getS('SALE_PROD_CODE');
	
	for (var a = 0; a < boxSelectedArr.length; a++) {
		var PROD_NO_NOW = boxSelectedArr[a]["PROD_NO"];
		//var PROD_NAME_NOW = boxSelectedArr[a]["PROD_NAME"];
		// 根据可售产品编号判断回显业务编号
		if (PROD_DATA[PROD_NO_NOW] == undefined) {
			//之前未配置过此可售产品
			// 生成一个业务编号
			setInput("BUSI_NO", "/prod/oper/busiDemo/getBusiNo","busiNo", false, {
						SALE_PROD_CODE : PROD_NO_NOW
					});
			setInput("brchName", "/prod/oper/busiDemo/getBrchName","brchName", false, {
				SALE_PROD_CODE : PROD_NO_NOW
			});
			$('#brchName').attr('disabled', 'disabled');
			var busiNo = getI("BUSI_NO");
			// 生成过业务编号了加入到暂存的  可售产品数据全局变量  中
			if (busiNo != "") {
				var prodData = {
					PROD_NO : PROD_NO_NOW,
					BUSI_NO : busiNo
				}
				PROD_DATA[PROD_NO_NOW] = prodData;
			}
		}
		
		// 根据可售产品编号判断加载动态配置页面
		// 查询可售产品页面配置详细，进行动态页面生成
		if (PER_PAGE_DATA[PROD_NO_NOW] == undefined) {
			// 之前没有获取过此可售产品页面配置参数
			PER_PAGE_DATA[PROD_NO_NOW] = getPerData(PROD_NO_NOW,"2");
		}
		// 根据数据生成界面并加入到tab内容区
		createPage(PER_PAGE_DATA[PROD_NO_NOW],$tabPage);
		
//		// 对下拉框进行初始化操作
//		$tabPage.find("select").multiselect('destroy').multiselect('rebuild')
//				.multiselect('refresh');
//		// 判断禁用下拉框
//		if(SAVE_OR_REV =="detail"){
//			$tabPage.find("select").multiselect('disable').multiselect('refresh');
//		}
		// 回显值
		setProdData(PROD_DATA[PROD_NO_NOW]);
		
		//TODO 自适应高度
		//ifrChildAut('busiDiv');
		ifrChildAut('panel2');
		
		/*getKeys(PROD_NO_NOW);*/
	}
	ifrChildAut('panel2');
}

//回显可售产品业务属性
function setProdData(data) {
	if(data == undefined){
		return;
	}
	var divId = 'busiDiv';
	
	console.log("回显的数据：" + data);
	console.log(data);
	// div内设置值开始
	startFormSet(divId);
	// 回显业务编号和业务名称
	$("#BUSI_NO").val(data.BUSI_NO);
	
	if(SET_DATA_NOW==true){
		$("#BUSI_NAME").val(data.BUSI_NAME);
		var keyList = data.KEY_LIST;
		if (undefined != keyList) {
			for (var a = 0; a < keyList.length; a++) {
				var keyData = keyList[a];
				var keyNo = keyData.KEY_NO;
				var keyNoId = keyNo.replace(/[\.]/g,"-_-");
				var kv = keyData.KV;
				var $key = $("#" + divId).find("[name="+keyNoId+"]");
				if($key.length>0){
					var tagName = $("#" + divId).find("[name="+keyNoId+"]")[0].tagName;
					var type = $("#" + divId).find("[name="+keyNoId+"]").attr("type");
					if(tagName=="INPUT" && type=="text"){
						setI(keyNoId,kv);
					}else if(tagName=="SELECT"){
						setS(keyNoId,kv,"|");
					}else if(tagName=="INPUT" && (type=="radio" || type=="checkbox")){
						setB(keyNoId,kv,"|");
					}else if(tagName=="INPUT" && type=="hidden"){
						setDateValue(keyNoId,kv);
					}
				}
			}
		}
	}
	
	// div内设置值结束
	endFormSet();
}


//回显全部页面数据
function setData(Data) {
	console.log("--------------------回显全部页面数据--------------------");
	console.log("--------------------SAVE_OR_REV--------------------"+SAVE_OR_REV);
	SET_DATA_NOW =true;
	console.log(Data);
	$('[name="fileArea1"]').val(Data.FILE_PATH1);
	fileArea1Preview();
	$('[name="fileArea2"]').val(Data.FILE_PATH2);
	fileArea2Preview();
	$('[name="fileArea3"]').val(Data.FILE_PATH3);
	fileArea3Preview();
	$('[name=bgImg]').val(Data.bgImg);
	bgImgPreview();
	
	// 业务参数
	PROD_DATA[Data.PROD_NO] = Data;
	setS('PROD_LINE_CODE',Data.PROD_LINE_CODE);
	$('#PROD_LINE_CODE').change();
	var dataArr = [];
	dataArr[0] = {label : Data.ENTR_NAME,value : Data.ENTR_NO};
	$('#ENTR_NAME').multiselect('dataprovider', dataArr);
	setS('ENTR_NAME',Data.ENTR_NO);
	$('#ENTR_NAME').change();
	
	var $selectLi = $("#prodField").find(
			"li[SALE_PROD_CODE='" + Data.saleProdCode + "']");
	$selectLi.click();
	if (SAVE_OR_REV == "detail") {
//		$("#prodField").on("click", "li", liClick);
		$("#addBtn").hide();
		$("#effect").hide();
	}
	if(SAVE_OR_REV == "detail" || SAVE_OR_REV=="revice"){
		setI("BUSI_NO",Data.BUSI_NO);
		setI("BUSI_NAME",Data.BUSI_NAME);
		setI("brchName",Data.brchName);
		disDiv("formId_65948");
		
		if (Data.SALEPROD != undefined) {
			var SALEPROD = Data.SALEPROD;

			console.log("--------------设置属性-----------------"+SALEPROD);

			for (var i=0;i<SALEPROD.length;i++) {
				var KEY_NO = SALEPROD[i].KEY_NO;
				KEY_NO = KEY_NO.replace(/[\.]/g,"-_-");
				console.log("--------------SALEPROD[i]KEY_NO-----------------"+SALEPROD[i].KEY_NO);
				console.log("--------------SALEPROD[i]KV-----------------"+SALEPROD[i].KV);
				// var strs= new Array();
				// strs=KEY_NO.split(".");
				// var KEY_NO_TEMP = "";
				// for (j=0;j<strs.length ;j++ ){
				// 	if (j == strs.length-1) {
				// 		KEY_NO_TEMP += strs[j];
				// 	} else {
				// 		KEY_NO_TEMP += strs[j]+"\\.";
				// 	}
				// }

				// console.log("--------------KEY_NO_TEMP-----------------"+KEY_NO_TEMP);

				var KV = SALEPROD[i].KV;
				if (KV.indexOf("|") != -1) {
					setS(KEY_NO, KV, "|");
					$('#'+KEY_NO).multiselect('enable');
				} else {
					setS(KEY_NO, KV);
				}
				
				$('#'+KEY_NO).change();

				var type = $("[name="+KEY_NO+"]").attr("type");
				console.log("--------------type-----------------"+type);

				if(type=="text"){
					setI(KEY_NO, KV);
					$('#'+KEY_NO).removeAttr("readonly");
					$('#'+KEY_NO).removeAttr("disabled");
				}

			}
		}
		console.log("----------------Data.saleProdCode---------------"+Data.saleProdCode);
		uploadCommon(Data.saleProdCode,Data,SAVE_OR_REV);

		// 取消点击事件，不可修改选中的可售产品包装
		if(SAVE_OR_REV == "detail"){
			$("#prodField").unbind();
			$("#prodField").on("click", ".openBtn", openBtnClick);
		}
	}
	// 超过9选中显示提示边框
	panelBodyBorder();
	/*setS('SALE_PROD_CODE',Data.PROD_NO);
	$('#SALE_PROD_CODE').change();*/

	/*
	if (SAVE_OR_REV == "revice") {
		setI("busiNo",Data.BUSI_NO);
		setI("busiName",Data.BUSI_NAME);
		disDiv("formId_65948");
	} else if (SAVE_OR_REV == "detail") {
		setI("busiNo",Data.BUSI_NO);
		setI("busiName",Data.BUSI_NAME);
		disDiv("formId_65948");
		$("#addBtn").hide();
	}
	*/
	detailData = Data;
	SET_DATA_NOW = false;
	
}

function uploadCommon(saleProdCode,Data,SAVE_OR_REV){
	console.info("----------------saleProdCode-------------"+saleProdCode);
	console.info("----------------Data-------------"+Data);
	console.info("----------------SAVE_OR_REV-------------"+SAVE_OR_REV);
	if(saleProdCode!=null && saleProdCode.length>0){
		$.get(ctx+"/prod/oper/busiDemo/busiCommon?saleProdCode="+saleProdCode,function(data){
			$("#commonDiv").empty();
			$("#commonDiv").append(data);

			setSelect2("openGrpChnlNo", "/prod/oper/busiDemo/getChnl", "chnlNo", "chnlName", {}, "nulls", true, false);
			
			setSelect2("openGrpBrch", "/prod/oper/busiDemo/getBrch", "brch", "brchName", {}, "nulls", true, false);

			setSelect2("relatSysNo", "/prod/oper/busiDemo/selectRelatSysNo", "relatSys", "sysName", {}, "nulls", true, false);

			setSelect2("inOutBankFlg", "/prod/oper/busiDemo/getInOutBankFlg", "dictVal", "dictLabel", {}, "nulls", true, false);

			// $("select[name=clrTp]").multiselect('dataprovider',
			// 		[{"label":"无需清算","value":"00"},{"label":"使用过渡户","value":"01"},{"label":"其他模式","value":"02"}]).multiselect('rebuild').multiselect('refresh');
			// $("select[name=inOutBankFlg]").multiselect('dataprovider',
			// 		[{"label":"行内","value":"00"},{"label":"行外","value":"01"}]).multiselect('rebuild').multiselect('refresh');
			$("select[name=isClrFlg]").multiselect('dataprovider',
					[{"label":"是","value":"0"},{"label":"否","value":"1"}]).multiselect('rebuild').multiselect('refresh');
			// $("select[name=signPat]").multiselect('dataprovider',
			// 		[{"label":"不检验","value":"00"},{"label":"检查","value":"01"},{"label":"其他模式检查","value":"02"}]).multiselect('rebuild').multiselect('refresh');
			// $("select[name=chkPat]").multiselect('dataprovider',
			// 		[{"label":"不对账","value":"00"},{"label":"两方对账","value":"01"},{"label":"三方对账","value":"02"}]).multiselect('rebuild').multiselect('refresh');
			// $("select[name=clrFeeTp]").multiselect('dataprovider',
			// 		[{"label":"不收取手续费","value":"00"},{"label":"按成功金额比例收取","value":"01"},{"label":"按成功笔数收取","value":"02"},{"label":"其他模式","value":"03"}])
			// 		.multiselect('rebuild').multiselect('refresh');
			// $("select[name=busiTp]").multiselect('dataprovider',
			// 		[{"label":"非联网","value":"01"},{"label":"联网","value":"00"}]).multiselect('rebuild').multiselect('refresh');

		// $("#clrTp").change(function(){
		// 	if(getS("clrTp")=="01"){
		// 		$("#showClrTp").removeClass("hide");
		// 	}else{
		// 		$("#showClrTp").addClass("hide");
		// 	}
		// })
			
			
		$('input[name="clrTp"]').change(function(){
			if ($('input[name="clrTp"][value="00"]').prop("checked")) {
				$("#showClrTp").removeClass("hide");
				$('#entrAcct').attr('check-empty', 'true');
				$('#intrmAcct').removeAttr('check-empty');
			} else if ($('input[name="clrTp"][value="01"]').prop("checked")) {
				$("#showClrTp").removeClass("hide");
				$('#entrAcct').removeAttr('check-empty');
				$('#intrmAcct').attr('check-empty', 'true');
			} else {
				$("#showClrTp").addClass("hide");
			}
		})

		// $("#clrFeeTp").change(function(){
		// 	if(getS("clrFeeTp")=="01") {
		// 		$("#showClrFeeTp").removeClass("hide");
		// 		$('#defFrt').attr('check-empty', 'true');
		// 		$('#amt').removeAttr('check-empty');
		// 	}else if (getS("clrFeeTp")=="02") {
		// 		$("#showClrFeeTp").removeClass("hide");
		// 		$('#defFrt').removeAttr('check-empty');
		// 		$('#amt').attr('check-empty', 'true');
		// 	}else{
		// 		$("#showClrFeeTp").addClass("hide");
		// 	}
		// })
		$('input[name="clrFeeTp"]').change(function(){
			if ($('input[name="clrFeeTp"][value="01"]').prop("checked")) {
				$("#showClrFeeTp").removeClass("hide");
				$('#defFrt').attr('check-empty', 'true');
				$('#amt').removeAttr('check-empty');
			} else if ($('input[name="clrFeeTp"][value="02"]').prop("checked")) {
				$("#showClrFeeTp").removeClass("hide");
				$('#defFrt').removeAttr('check-empty');
				$('#amt').attr('check-empty', 'true');
			} else {
				$("#showClrFeeTp").addClass("hide");
			}
		})

		// $("#busiTp").change(function(){
		// 	if(getS("busiTp")=="00"){
		// 		$("#show_busiTp").removeClass("hide");
		// 	}else{
		// 		$("#show_busiTp").addClass("hide");
		// 	}
		// })

		$('input[name="busiTp"]').change(function(){
			if ($('input[name="busiTp"][value="00"]').prop("checked")) {
				$("#show_busiTp").removeClass("hide");
			} else {
				$("#show_busiTp").addClass("hide");
			}
		})

		$("#inOutBankFlg").change(function(){
			if(getS("inOutBankFlg")=="00"){
				$("#entrAcctBank").removeAttr('check-empty');
			}else{
				$("#entrAcctBank").attr('check-empty', 'true');
			}
		})
		
		$('#intrmAcct').change(function() {
			var acct = $(this).val();
			if(acct!=""){
				qryAcctInfo(acct,"intrmAcct");
			}
		});
		
		$('#entrAcct').change(function() {
			var acct = $(this).val();
			if(acct!=""){
				qryAcctInfo(acct,"entrAcct");
			}
		});	

		if((SAVE_OR_REV!=undefined && SAVE_OR_REV=="detail") || (SAVE_OR_REV!=undefined && SAVE_OR_REV=="revice")){
			setI("busiDesc", Data.busiDesc);
			setI("brchIdName",Data.brchIdName);
			setI("brchIdId",Data.brchId);
			if(Data.openGrpChnlNo!=undefined&&Data.openGrpChnlNo.length>0 ){
				console.log(Data.openGrpChnlNo);	
				var openGrpChnlNoArr = Data.openGrpChnlNo;
				var CHNL_NOs = "";
				for (var i=0;i<openGrpChnlNoArr.length;i++) {
					CHNL_NOs += CHNL_NOs + openGrpChnlNoArr[i].chnlNo + ";";
				}
				
				setS("openGrpChnlNo", CHNL_NOs, ";");
//				var CHNL_NOs = Data.openGrpChnlNo.split(",");
//				$("#openGrpChnlNo").multiselect("select",CHNL_NOs).multiselect('rebuild');
//				multiselectReadOnly("openGrpChnlNo");
			}

			if(Data.openGrpBrch!=undefined&&Data.openGrpBrch.length>0 ){
//				var BRCH_NOs = Data.openGrpBrch.split(",");
//				$("#openGrpBrch").multiselect("select",BRCH_NOs).multiselect('rebuild');
//				multiselectReadOnly("openGrpBrch");
				
				
				console.log(Data.openGrpBrch);	
				var openGrpBrchArr = Data.openGrpBrch;
				var BRCH_NOs = "";
				for (var i=0;i<openGrpBrchArr.length;i++) {
					BRCH_NOs += BRCH_NOs + openGrpBrchArr[i].brch + ";";
				}
				
				setS("openGrpBrch", BRCH_NOs, ";");
//				$('#openGrpBrch').removeAttr("disabled");
//				$('#openGrpChnlNo').removeAttr("disabled");
			}
			
			setR("clrTp",Data.clrTp);
			$("#clrTp").change();

			if(Data.clrTp=="00" || Data.clrTp=="01"){
				setI("intrmAcct",Data.intrmAcct);
				setI("intrmAcctName",Data.intrmAcctName);
				setS("inOutBankFlg",Data.inOutBankFlg);
				setS("isClrFlg",Data.isClrFlg);
				setI("intrmAcctOpenInst",Data.shortRmrk);
				setI("entrAcct",Data.entrAcct);
				setI("entrAcctName",Data.entrAcctName);
				setI("entrAcctBank",Data.entrAcctBank);
			}
			
			setR("signPat",Data.signPat);
			setR("chkPat",Data.chkPat);

			setR("clrFeeTp",Data.clrFeeTp);
			$("#clrFeeTp").change();

			if(Data.clrFeeTp=="01" || Data.clrFeeTp=="02"){
				setI("defFrt",Data.defFrt);
				setI("amt",Data.amt);
				setI("feeTfOutAcct",Data.feeTfOutAcct);
				setI("feeTfInAcct",Data.feeTfInAcct);
			}

			setR("busiTp",Data.busiTp);
			$("#busiTp").change();
			if(Data.busiTp=="00"){
				setS("relatSysNo",Data.relatSysNo);
			}

			if(SAVE_OR_REV=="detail"){
				disDiv("commonDiv");
			}
		}
		})
	}else{
		$("#commonDiv").empty();
	}
	
	
}
