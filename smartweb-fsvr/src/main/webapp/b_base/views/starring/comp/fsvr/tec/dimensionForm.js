console.log('dimensionForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改 ，detail详细
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;
/*状态*/
var STAT = "01";

$(function(){
	/*加载模型下拉框的数据*/
	setSelect1("COMP_NO","/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME",null,false);
	
	/*返回按钮*/
	$('#closeBtn').on('click',cancel);	
	
	/*提交按钮*/
	$('#addBtn').on('click',sendData);	

	//业务编号
	$("#companyName").click(function(){
		busiClick("companyName","BUSI_NAME","ENTR_NO");
	});
	
	
	/*加载渠道下拉框的数据*/
	setSelect1("CHNL_NO","/comp/ctrl/oper/channel/getChnl","CHNL_NO","CHNL_NAME",undefined,false);
	
	/*加载文件服务标志*/
	setSelect2("FILE_SVR_NO","/comp/fsvr/tec/fileTec/qryFileSvr","FILE_SVR_NO","FILE_SVR_NO",{FILE_SVR_TP:"B1"},"B1",undefined,false);
});

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

/*获取页面数据data*/
function getData(){
	var data= {
			//界面
			BUSI_NO:getI('companyName'),
			ENTR_NO:getS('ENTR_NO'),
			CHNL_NO:getS('CHNL_NO'),
			LEGA_NO:getS("LEGA_NO"),
			TRAN_CODE:getI("TRAN_CODE"),
			DEF_VAL:getI('DEF_VAL'),
			DIM_DESC:getI('DIM_DESC'),
			FILE_SVR_NO:getS('FILE_SVR_NO'),
			STAT:STAT,
			
			COMP_NO:getS("COMP_NO"),
			COMP_NAME:getST("COMP_NO"),
			
			TRAN_TP:""
	}
	return data;
}

/*提交执行*/
function sendData(){
	if(proof()){
		var $url = ctx ;
		var $data = getData();
		var $ifasync = true;
		
		if(SAVE_OR_REV=="add"){
			$url += "/comp/fsvr/tec/fileTec/dimensionAdd";
		}else if(SAVE_OR_REV=="revice"){
			$url += "/comp/fsvr/tec/fileTec/dimensionRevice";
			$data.TRAN_TP = "01";
		}
		$.ajax({
			url:$url, 
			type:"POST",
			dataType:"json",
			data:$data, 
			async:$ifasync,
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
}

//详细查询数据
function getDetail(busiNo,entrNo,chnlNo,legaNo,tranCode,defVal){
	$.ajax({
		url:ctx + "/comp/fsvr/tec/fileTec/dimensionGetDetail", 
		type:"GET",
		dataType:"json",
		data:{
			BUSI_NO:busiNo,
			ENTR_NO:entrNo,
			CHNL_NO:chnlNo,
			LEGA_NO:legaNo,
			TRAN_CODE:tranCode,
			DEF_VAL:defVal
		},
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "获取数据["+data.message+"]"; 
				showContent(successMsg,"success");
				var Data = data.dataSetResult[0].data[0];
				setVal(Data);
			}
		}
	});
}

//设置值
function setVal(data){
	
	setI("companyName",data.BUSI_NO);
	getEntrNo(data.BUSI_NO,"ENTR_NO")
	setS("ENTR_NO",data.ENTR_NO);
	setS("CHNL_NO",data.CHNL_NO);
	setS("LEGA_NO",data.LEGA_NO);
	setI("TRAN_CODE",data.TRAN_CODE);
	setI("DEF_VAL",data.DEF_VAL);
	setI("DIM_DESC",data.DIM_DESC);
	setS("FILE_SVR_NO",data.FILE_SVR_NO);
	setS("COMP_NO",data.COMP_NO,";");
	
	STAT = data.STAT;
	
	if(SAVE_OR_REV=="detail"){
		disDiv("formId_299274");
		$('#addBtn').hide();
		//TODO下拉框可看不可修改 实现？
	}else{
		disDiv("commonDiv");
	}
}

//业务bi安好click执行
//ifrBox显示业务编号



