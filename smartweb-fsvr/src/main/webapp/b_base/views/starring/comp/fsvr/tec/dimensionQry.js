console.log('dimensionQry.js');

/*主页面全局变量saveOrRev
默认add为新增，revice为修改 ，detail详细
用于子页面判断*/
var SAVE_OR_REV = "add";

$(function(){
	/*查询*/
	$("#qryBtn").click(function(){
		freshTable("table");
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('消费方维度新增');
		SAVE_OR_REV = "add";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('消费方维度新增');
		SAVE_OR_REV = "add";
		ifr('panel2','comp/fsvr/tec/fileTec/dimensionForm');
		setIfrHeight1('panel2',500);
	});
	
	//业务编号
	$("#companyName").click(function(){
		busiClick("companyName","BUSI_NAME","ENTR_NO");
	});
	
	/*加载渠道下拉框的数据*/
	setSelect1("CHNL_NO","/comp/ctrl/oper/channel/getChnl","CHNL_NO","CHNL_NAME",undefined,false);
	
});

/*tab1点击执行函数*/
function tab1(){
	$('#tab1').click();
	goTop();
	$("#qryBtn").click();
}

/*查询table*/
function queryParams(params){
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
//		TODO
		BUSI_NO:getI('companyName'),
		ENTR_NO:getS('ENTR_NO'),
		CHNL_NO:getS('CHNL_NO'),
		LEGA_NO:getS("LEGA_NO"),
		TRAN_CODE:getI("TRAN_CODE"),
		DEF_VAL:getI('DEF_VAL'),
		FILE_SVR_NO:"",
		STAT:getS("STAT")
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}


//详细调用
function Detail(busiNo,entrNo,chnlNo,legaNo,tranCode,defVal){
	$('#tab2').click().text('消费方维度详细');
	SAVE_OR_REV = "detail";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.getDetail(busiNo,entrNo,chnlNo,legaNo,tranCode,defVal);
	});
}

//修改调用
function Revice(busiNo,entrNo,chnlNo,legaNo,tranCode,defVal){
	$('#tab2').click().text('消费方维度修改');
	SAVE_OR_REV = "revice";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.getDetail(busiNo,entrNo,chnlNo,legaNo,tranCode,defVal);
	});
}

//删除调用
function Delete(busiNo,entrNo,chnlNo,legaNo,tranCode,defVal){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			url:ctx + "/comp/fsvr/tec/fileTec/dimensionDelete", 
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
					var successMsg = "删除数据["+data.message+"]"; 
					showContent(successMsg,"success");
					freshTable("table");
				}
			}
		});
	});
}

//修改状态 启用 停用 
function ChangeStat(busiNo,entrNo,chnlNo,legaNo,tranCode,defVal,fileSvrNo,stat){
	$.ajax({
		url:ctx + "/comp/fsvr/tec/fileTec/dimensionChangeStat", 
		type:"GET",
		dataType:"json",
		data:{
			TRAN_TP:"02",
			BUSI_NO:busiNo,
			ENTR_NO:entrNo,
			CHNL_NO:chnlNo,
			LEGA_NO:legaNo,
			TRAN_CODE:tranCode,
			DEF_VAL:defVal,
			FILE_SVR_NO:fileSvrNo,
			STAT:stat
		},
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "修改状态["+data.message+"]"; 
				showContent(successMsg,"success");
				freshTable("table");
			}
		}
	});
}



