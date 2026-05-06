console.log('授权检查');
$(function(){
	
	//查询模型号加载下拉框
	getCompS("COMP_NO");
	
	/*模型下拉改变则重新加载服务码下拉框,并清空子服务码下拉框*/
	$('#COMP_NO').change(function(){
		svcCodeS('SVC_CODE',getS('COMP_NO'));
		/*私有有维度下拉框加载数据*/
		dimKey('PRI_DIM_KEY',getS('COMP_NO'));
		resetS('SUB_SVC_CODE');
	});
	
	/*服务码下拉框改变则重新加载子服务码下拉框*/
	$('#SVC_CODE').change(function(){
		subSvcS('SUB_SVC_CODE',getS('COMP_NO'),getS('SVC_CODE'));
	});
	
	/*公有维度下拉框加载数据*/
	dimKey("PUB_DIM_KEY","0");
	$("#PUB_DIM_KEY").change(function(){
		$("#PUB_DIM_KV").val();
	});
	
	/*柜员下拉框选择*/
	tlrSel("TLR_NO");
	$("#TLR_NO").change(function(){
		var outStr = getS("TLR_NO");
		if(outStr!=""){
			TLR_NAME = outStr.split("-")[1];
			TLR_LVL = outStr.split("-")[2];
			BRCH = outStr.split("-")[3];
			$("#TLR_NAME").val(TLR_NAME);
			$("#TLR_LVL").val(TLR_LVL);
			$("#BRCH").val(BRCH);
		}
	});
	
	/*交易金额补00*/
	$("#TRAN_AMT").blur(function(){
		var money = getI("TRAN_AMT");
		var num = changeNum(money);
		setI("TRAN_AMT",num);
	});
	
	/*---------- 公用维度table  start------------------- */
	//table 点击一行变色
	tableClick("pubDimTable"); 
	
	//addBtn1，table添加一行
	$('#addBtn1').click(function(){
		if(portion("addPubDim")){
			var data={
					PUB_DIM_KEY:getS('PUB_DIM_KEY'),
					PUB_DIM_KV:getI('PUB_DIM_KV'),
					ACTION:getPubAction()
//					ACTION:"<a href=\"JavaScript:void(0);\" onclick='reviceRow(this,'pubDim')'>修改</a>  <a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>"
			}
			//table增加行
			tableAddRow('pubDimTable',data);
			resetForm("addPubDim");
		}
	});
	// modBtn1点击事件 确认修改行
	$('#modBtn1').click(function(){
		if(portion("addPubDim")){
			var data={
					PUB_DIM_KEY:getS('PUB_DIM_KEY'),
					PUB_DIM_KV:getI('PUB_DIM_KV'),
					ACTION:getPubAction()
			}
			tableReviceRow('pubDimTable',data);
			resetForm("addPubDim");
		}
	});
/*---------- 公用维度table  end------------------- */
	
/*---------- 私有维度table  start------------------ */
	//table 点击一行变色
	tableClick("priDimTable"); 
	
	//addBtn2，table添加一行
	$('#addBtn2').click(function(){
		if(portion("addPriDim")){
			var data={
					PRI_DIM_KEY:getS('PRI_DIM_KEY'),
					PRI_DIM_KV:getI('PRI_DIM_KV'),
					ACTION:getPriAction()
			}
			//table增加行
			tableAddRow('priDimTable',data);
			resetForm("addPriDim");
		}
	});
	// modBtn2点击事件 确认修改行
	$('#modBtn2').click(function(){
		if(portion("addPriDim")){
			var data={
					PRI_DIM_KEY:getS('PRI_DIM_KEY'),
					PRI_DIM_KV:getI('PRI_DIM_KV'),
					ACTION:getPriAction()
			}
			tableReviceRow('priDimTable',data);
			resetForm("addPriDim");
		}
	});
/*---------- 私有维度table  end------------------ */
	
/*---------- 授权柜员table  start------------------ */
	//table 点击一行变色
	tableClick("tlrTable"); 
	
	//addBtn2，table添加一行
	$('#addBtn3').click(function(){
		if(portion("addTrl")){
			var data={
					TLR:getS('TLR_NO'),
					TLR_NO:getST('TLR_NO'),
					TLR_NAME:getI('TLR_NAME'),
					TLR_LVL:getI('TLR_LVL'),
					BRCH:getI('BRCH'),
					ACTION:getTlrAction()
			}
			//table增加行
			tableAddRow('tlrTable',data);
			resetForm("addTrl");
		}
	});
	// modBtn3点击事件 确认修改行
	$('#modBtn3').click(function(){
		if(portion("addTrl")){
			var data={
					TLR:getS('TLR_NO'),
					TLR_NO:getST('TLR_NO'),
					TLR_NAME:getI('TLR_NAME'),
					TLR_LVL:getI('TLR_LVL'),
					BRCH:getI('BRCH'),
					ACTION:getTlrAction()
			}
			tableReviceRow('tlrTable',data);
			resetForm("addTrl");
		}
	});
/*---------- 授权柜员table  end------------------ */
	
	
	
	//载入案例按钮点击进入载入案例页面
	$("#addBtn").click(function(){
		parent.tab2();
	});
	
	//保存按钮 点击事件
	$("#saveBtn").on('click',saveBtnFun);
	
	//另存为按钮 点击事件
	$("#saveBtn1").on('click',saveBtnFun1);
	/*startJudge('saveBtn1');
	cOpt('COMP_NO');
	cOpt('SVC_CODE');
	cOpt('SUB_SVC_CODE');
	endJudge(saveBtnFun1);*/
	
	//删除按钮 点击事件
	$("#delBtn").on('click',delBtnFun);
	
	//测试按钮 点击事件
	$("#testBtn").on('click',testBtnFun);
	
});


//获取案例 数据回显
function setVal(Data){
	
	//设置下拉框
	var compArr = Data.COMP_NO;
	var COMP_NO = compArr.split("-")[0];
	setS("COMP_NO",COMP_NO);
	
	var svcArr = Data.SVC_CODE;
	var SVC_CODE = svcArr.split("-")[0];
	var SVC_DESC = svcArr.split("-")[1];
	var backS = [{label:SVC_DESC,value:SVC_CODE}];
	$("select[name='SVC_CODE']").multiselect('dataprovider', backS);
	var subArr = Data.SUB_SVC_CODE;
	var SUB_SVC_CODE = subArr.split("-")[0];
	var SUB_SVC_DESC = subArr.split("-")[1];
	backS = [{label:SUB_SVC_DESC,value:SUB_SVC_CODE}];
	$("select[name='SUB_SVC_CODE']").multiselect('dataprovider', backS);
	
    //设置table
    var PUB_DIM_LIST = Data.PUB_DIM_LIST;
    $("#pubDimTable").bootstrapTable('load', PUB_DIM_LIST);
    
    var PRI_DIM_LIST = Data.PRI_DIM_LIST;
    $("#priDimTable").bootstrapTable('load', PRI_DIM_LIST);
    
    var TRL_LIST = Data.TRL_LIST;
    $("#tlrTable").bootstrapTable('load', TRL_LIST);
    
    var TRAN_AMT = Data.TRAN_AMT;
    $('#TRAN_AMT').val(TRAN_AMT);
    
    var caseName = Data.caseName;
    var caseNo = Data.caseNo;
    $('#case_Name').val(caseName);
    $('#case_No').val(caseNo);
    
   //重新加载私有维度下拉框
    dimKey('PRI_DIM_KEY',COMP_NO);
    
}


//保存案例 按钮实现()
function saveBtnFun(){
	if(portion("pubSel")){
		var caseNo = $("#case_No").val();
		var caseName = $("#case_Name").val();
		/* 当前案例名称不为空的情况：(只更新文件的内容，不保存到数据库) */
		if(caseName!=""){
			//获取下拉框数据(格式：no-name)
			var COMP_NO = getS("COMP_NO")+"-"+getST("COMP_NO");
			var SVC_CODE = getS("SVC_CODE")+"-"+getST("SVC_CODE");
			var SUB_SVC_CODE = getS("SUB_SVC_CODE")+"-"+getST("SUB_SVC_CODE");
			//获取table中的数据
			var PUB_DIM_JSON = $("#pubDimTable").bootstrapTable('getData');
			var PUB_DIM_LIST=JSON.stringify(PUB_DIM_JSON);
			
			var PRI_DIM_JSON = $("#priDimTable").bootstrapTable('getData');
			var PRI_DIM_LIST=JSON.stringify(PRI_DIM_JSON);
			
			var TRL_JSON = $("#tlrTable").bootstrapTable('getData');
			var TRL_LIST = JSON.stringify(TRL_JSON);
			
			var TRAN_AMT = getI('TRAN_AMT');
			
			$.ajax({
				type:"POST",
				url:ctx + "/comp/ctrl/test/tranauthchk/saveAuth",
				dataType:"json",
				data:{
					caseNo:caseNo,
					caseName:caseName,
					COMP_NO:COMP_NO,
					SVC_CODE:SVC_CODE,
					SUB_SVC_CODE:SUB_SVC_CODE,
					PUB_DIM_LIST:PUB_DIM_LIST,
					PRI_DIM_LIST:PRI_DIM_LIST,
					TRL_LIST:TRL_LIST,
					TRAN_AMT:TRAN_AMT
					},
				success:function(data){
					if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
						var errMsg = "错误信息["+data.message+"]";
						showTip(errMsg,"error");
					}else{
						showTip("提交成功","success");
						console.info("保存成功");
					}
				}
			});
		}else{
			saveBtnFun1();
		}
	}else{
		parent.goTop();
	}
}

//另保存案例
function saveBtnFun1(){
	if(portion("pubSel")){
		var saveDataArr = getSaveArr();
		parent.showTabs("#tab_save", ctx+"/comp/ctrl/test/tranauthchk/toSave");
		parent.tab3(saveDataArr);
	}else{
		parent.goTop();
	}
}

function getSaveArr(){
	//获取下拉框数据(格式：no-name)
	var COMP_NO = getS("COMP_NO")+"-"+getST("COMP_NO");
	var SVC_CODE = getS("SVC_CODE")+"-"+getST("SVC_CODE");
	var SUB_SVC_CODE = getS("SUB_SVC_CODE")+"-"+getST("SUB_SVC_CODE");
	//获取table中的数据
	var PUB_DIM_JSON = $("#pubDimTable").bootstrapTable('getData');
	var PUB_DIM_LIST=JSON.stringify(PUB_DIM_JSON);
	
	var PRI_DIM_JSON = $("#priDimTable").bootstrapTable('getData');
	var PRI_DIM_LIST=JSON.stringify(PRI_DIM_JSON);
	
	var TRL_JSON = $("#tlrTable").bootstrapTable('getData');
	var TRL_LIST = JSON.stringify(TRL_JSON);
	
	var TRAN_AMT = getI('TRAN_AMT');
	
	var saveArr = COMP_NO+"##"+SVC_CODE+"##"+SUB_SVC_CODE+"##"+PUB_DIM_LIST+"##"+PRI_DIM_LIST+"##"+TRL_LIST+"##"+TRAN_AMT;
	return saveArr;
}

//删除案例
function delBtnFun(){
	var caseNo = $("#case_No").val();
	var caseName = $("#case_Name").val();
	console.info(caseNo+caseName);
	if(caseName!=""){
		parent.goTop();
		Ewin.confirm({
			title : "操作提示",
			message : "确定删除吗？"
		}).on(function(e) {
			if (!e) {
				return;
			}
			$.ajax({
				type:"POST",
				url:ctx + "/comp/ctrl/test/tranauthchk/delAuth",
				dataType:"json",
				data:{
					caseName:caseName,
					caseNo:caseNo
					},
				success:function(data){
					if(data.rs > 0){ 
						console.info("del成功");
						//清空页面数据
						parent.tab11("Y");
//						resetForm('page1');
						showContent("删除成功!","success");
					}else{
						showTip("删除失败!","error");
					}
				}
			});
		});
	}
}

//测试案例
function testBtnFun(){
	if(portion("pubSel")){
		var COMP_NO = getS("COMP_NO");
		var SVC_CODE = getS("SVC_CODE");
		var SUB_SVC_CODE = getS("SUB_SVC_CODE");
		var TRAN_AMT =getI("TRAN_AMT");
		
		var PUB_DIM_JSON = $("#pubDimTable").bootstrapTable('getData');
		var PUB_DIM_LIST='"PUB_DIMLIST"'+":"+JSON.stringify(PUB_DIM_JSON);
		
		var PRI_DIM_JSON = $("#priDimTable").bootstrapTable('getData');
		
		var TLR_JSON = $("#tlrTable").bootstrapTable('getData');
		var TLR_ARR = JSON.stringify(TLR_JSON);
		
		var json={};
		if(PRI_DIM_JSON.length>0){
			for(var i=0;i<PRI_DIM_JSON.length;i++){
				var priKey = PRI_DIM_JSON[i].PRI_DIM_KEY;
				if(priKey!=""){
					priKey = priKey.substring(priKey.lastIndexOf('.')+1);
					var priVal = PRI_DIM_JSON[i].PRI_DIM_KV;
					json[priKey]=priVal;
				}
			}
		}
		var DYN_DATA = JSON.stringify(json);
		console.info(DYN_DATA);
		
		$.ajax({
			type:"POST",
			url:ctx + "/comp/ctrl/test/tranauthchk/testAuth",
			dataType:"json",
			data:{
				COMP_NO:COMP_NO,
				SVC_CODE:SVC_CODE,
				SUB_SVC_CODE:SUB_SVC_CODE,
				TRAN_AMT:TRAN_AMT,
				PUB_DIM_LIST:PUB_DIM_LIST,
				DYN_DATA:DYN_DATA,
				TLR_ARR:TLR_ARR
			},
			success:function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]";
					showTip(errMsg,"error");
				}else{
					showTip("交易成功","success");
					//清空检查结果
					$("#resMsg").text('');
					$("#rsTable").bootstrapTable('removeAll');
					console.info("交易测试成功");
					var valData=data.dataSetResult[0].data[0];
					var res_msg = valData.RES_MSG;
					var res_code = valData.RES_CODE;
					if(res_msg!=""&&res_msg!=undefined){
						res_msg = "最终检查结果: "+"  <"+res_code+","+res_msg+">";
						$("#resMsg").text(res_msg);
					}else{
						$("#resMsg").text("最终检查结果:");
					}
					var rsList = data.dataSetResult[0].data;
					$("#rsTable").bootstrapTable('load', rsList);
				}
			}
		});
	}else{
		parent.goTop();
	}
}
