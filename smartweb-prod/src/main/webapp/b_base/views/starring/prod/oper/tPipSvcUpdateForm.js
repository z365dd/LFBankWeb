
$(document).ready(function(){
	/*把修改页面内容显示出来*/
	parent.window.$("#iframe_update").show();
	/*从session中拿出id*/
	compNo = $.session.get('compNo');
	svcCode = $.session.get('svcCode');
	/*从session中移除id*/
	$.session.remove('compNo');
	$.session.remove('svcCode');
	// $("#compNo").val(compNo);
	// $("#svcCode").val(svcCode);

	compList();
	attrList();

	getDetail(compNo, svcCode);
	
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			if (checkPara() && checkScen()) {
				submit();
			}
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});

	$("#addAttrBtn").on("click" ,function(){
		tableActionAttrRow('attrTable',"add");
	});

	$("#addBtn").on("click" ,function(){
		tableActionRow('table',"add");
	});

});

function submit(){
	confirmx('是否更新服务', function(){
		save();
	});
}

/**
 * 保存函数--保存服务修改
 * @returns
 */
function save(){
	var formData = $("#tPipSvcForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/tPipSvc/update", formData,
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

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/*查询明细*/
function getDetail(compNo, svcCode){
    console.info('update tPipSvc info......');
    $.post(ctx + "/prod/oper/tPipSvc/get", {compNo:compNo,svcCode:svcCode}, function(data){
            if(data.returnCode!==undefined && "0000"!=data.returnCode){
                var errMsg = "错误信息["+data.message+"]";
                showContent(errMsg,"error");
            }else {
                for(var i = 0 ; i < data.dataSetResult.length; i++){

                    if (data.dataSetResult[i].dataSetName == "tPipSvcDODs") {
                        for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
                            var jsonObj = data.dataSetResult[i].data[j];
                            console.info(jsonObj);
                            $('#compNo').multiselect("select", [jsonObj.compNo]).multiselect('rebuild');
                            $('#compNo').multiselect("disable");
                            $('#temp1').val(jsonObj.compNo);
                            $('#svcCode').val(jsonObj.svcCode);
                            $('#svcName').val(jsonObj.svcName);
                            $('#svcDesc').val(jsonObj.svcDesc);
                            // $('#signFlg').val(jsonObj.signFlg);
                            // $('#chkFlg').val(jsonObj.chkFlg);
                            // $('#othConnFlg').val(jsonObj.othConnFlg);
                            $('#signFlg').multiselect("select", [jsonObj.signFlg]).multiselect('rebuild');
                            $('#chkFlg').multiselect("select", [jsonObj.chkFlg]).multiselect('rebuild');
                            $('#othConnFlg').multiselect("select", [jsonObj.othConnFlg]).multiselect('rebuild');
                            $('#othSvcCodeFlg').multiselect("select", [jsonObj.othSvcCodeFlg]).multiselect('rebuild');
                            $('#shortRmrk').val(jsonObj.shortRmrk);
                            $('#midRmrk').val(jsonObj.midRmrk);
                            $('#longRmrk').val(jsonObj.longRmrk);
							$('#dac').val(jsonObj.dac);
							$('#openStat').val(jsonObj.openStat);
                        }
                    }

                    if (data.dataSetResult[i].dataSetName == "tPipCompSvcParaDODs") {
                        var list = data.dataSetResult[i].data;
                        if (undefined != list) {
                            for(var j=0;j<list.length;j++){
                                list[j].action = "<a href='javascript:;' onclick='deleteRow(this)'>删除</a>";
                            }
                        }
                        $('#attrTable').bootstrapTable('load', list);
                    }

                    if (data.dataSetResult[i].dataSetName == "tPipSvcCompScenDODs") {
                        var list = data.dataSetResult[i].data;
                        if (undefined != list) {
                            for(var j=0;j<list.length;j++){
                                list[j].action = "<a href='javascript:;' onclick='deleteRow(this)'>删除</a>";
                            }
                        }
                        $('#table').bootstrapTable('load', list);
                    }

                }

            }
        },
        "json");

}


/**
 * 获取属性下拉框
 */
function attrList() {
	datass = {KEY_TP:"01", start:'0', pageSize:'0'};
	setSelect2("KEY_NO", "/prod/oper/prodAttrDict/qry", "KEY_NO", "KEY_NAME", datass, "nulls", false, false);
}

/**
 * 获取组件下拉框
 */
function compList() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("compNo", "/prod/comp/tPipComp/list", "compNo", "compName", datass, "nulls", false, false);
}

/* 取值，table新增一行 */
function tableActionAttrRow(tableId,ifAddRevice){
	var rowArr = $('#attrTable').bootstrapTable('getData');
	if(portion("colAttrDiv")){
		var KEY_NO = getS('KEY_NO');
		if ("" == KEY_NO) {
			showTip("属性不能为空", "error");
			return 0;
		}
		var KEY_NAME = $('#KEY_NO option:selected').text();
		var Data={
			KEY_NO : KEY_NO,
			KEY_NAME : KEY_NAME,
			action:"<a href='javascript:;' onclick='deleteRow(this)'>删除</a>",
		}
		if(ifAddRevice=="add"){
			/*table增加行*/
			for (var i=0;i<rowArr.length;i++) {
				var data = rowArr[i];
				if (data.KEY_NO == KEY_NO) {
					showTip("已存在相同属性["+KEY_NAME+"]", "error");
					return 0;
				}
			}
			tableAddRow(tableId,Data);
			clearAttrInput();
		}else if(ifAddRevice=="revice"){
			/*修改行*/
			tableReviceRow(tableId,Data);
			clearAttrInput();
		}
	}
}

/*清空输入框*/
function clearAttrInput(){
	$("#KEY_NO").multiselect("select", "").multiselect('rebuild');
}

/* 取值，table新增一行 */
function tableActionRow(tableId,ifAddRevice){
	var rowArr = $('#table').bootstrapTable('getData');
	if(portion("colDiv")){
		var sceneNo = getI('sceneNo');
		if ("" == sceneNo) {
			showTip("场景不能为空", "error");
			return 0;
		}
		var sceneName = getI("sceneName");
		if ("" == sceneName) {
			showTip("场景名称不能为空", "error");
			return 0;
		}
		var sceneDesc = getI("sceneDesc");
		var Data={
			sceneNo : sceneNo,
			sceneName : sceneName,
			sceneDesc : sceneDesc,
			action:"<a href='javascript:;' onclick='deleteRow(this)'>删除</a>",
		}
		if(ifAddRevice=="add"){
			/*table增加行*/
			for (var i=0;i<rowArr.length;i++) {
				var data = rowArr[i];
				if (data.sceneNo == sceneNo) {
					showTip("已存在相同场景["+sceneNo+"]", "error");
					return 0;
				}
				if (data.sceneName == sceneName) {
					showTip("已存在相同场景名称["+sceneName+"]", "error");
					return 0;
				}
			}
			tableAddRow(tableId,Data);
			clearInput();
		}else if(ifAddRevice=="revice"){
			/*修改行*/
			tableReviceRow(tableId,Data);
			clearInput();
		}
	}
}

/*清空输入框*/
function clearInput(){
	$("#sceneNo").val("");
	$("#sceneName").val("");
	$("#sceneDesc").val("");
}


/*
 * 检查是否已添加属性
 */
function checkPara() {

	var rowArrIndex = $('#attrTable').bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#rowPara").val(row);

	if ($("#rowPara").val() == "[]") {
		// showTip("请选择属性", "error");
		// return false;
	}
	return true;
}

/*
 * 检查是否已添加场景
 */
function checkScen() {

	var rowArrIndex = $('#table').bootstrapTable('getData');
	var row = JSON.stringify(rowArrIndex);
	$("#rowScen").val(row);

	if ($("#rowScen").val() == "[]") {
		// showTip("请配置场景", "error");
		// return false;
	}
	return true;
}
