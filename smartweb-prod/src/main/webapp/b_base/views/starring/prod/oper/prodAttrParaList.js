console.log('prodAttrList.js');

/*主页面全局变量SAVE_OR_REV_OR_DETAIL
默认SAVE为新增，REV为修改，DETAIL为详细
用于子页面判断*/
var SAVE_OR_REV_OR_DETAIL = "SAVE";

$(function(){

	
	/*查询*/
	$("#qryBtn").click(function(){
		freshTable("table");
		$('#COMP_NO').multiselect('rebuild');
		refreshS('COMP_NO', "/prod/oper/prodAttrPara/getCompList");
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('组件属性新增');
		SAVE_OR_REV_OR_DETAIL = "SAVE";
	});
	
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('组件属性新增');
		SAVE_OR_REV_OR_DETAIL = "SAVE";
		ifr('panel2','prod/oper/prodAttrPara/prodAttrParaForm',1000);
	});
	
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
		COMP_NO:getS('COMP_NO'),
		STAT:getS("STAT")
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

/*详细*/
function Detail(compNo,compName,BELONG_FLG){
	getDetail(compNo,compName,BELONG_FLG,"DETAIL");
} 

/*修改*/
function Revice(compNo,compName,BELONG_FLG){
	getDetail(compNo,compName,BELONG_FLG,"REV");
}

/*获取详细数据,打开tab2传入数据*/
function getDetail(compNo,compName,BELONG_FLG,tp){
	$('#tab2').click();
	
	if(tp=="DETAIL"){
		$('#tab2').text('组件属性详细');
		SAVE_OR_REV_OR_DETAIL = "DETAIL";
	}else if(tp=="REV"){
		$('#tab2').text('组件属性修改');
		SAVE_OR_REV_OR_DETAIL = "REV";
	}
	$("#panel2").find('iframe').on("load",function(){
		$("#panel2").find('iframe')[0].contentWindow.setData(compNo);
	});
}


//删除
function Delete(compNo){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
	$.ajax({
		url:ctx+"/prod/oper/prodAttrPara/delete",
		type:"POST",
		dataType:"json",
		data:{
			COMP_NO:compNo, 
		},
		async:true,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "删除["+data.message+"]"; 
				showContent(successMsg,"success");
				$("#qryBtn").click();
				// 刷新下拉列表
				
			}
		}
	});
	});
}


function refreshS(tableName,url){
	$.post(ctx+url,{},function(data){
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			if(data.list!=undefined&&data.list!=""){
				valArr = data.list;
				var dataArr = [];
				if (valArr.length != 0) {
					for (var a = 0; a < valArr.length; a++) {
						var jsonVal = valArr[a];
						dataArr[a] = {
							label : jsonVal["label"],
							value : jsonVal["value"]
						}
					}
					$("select[name=" + tableName + "]").multiselect('dataprovider',
							dataArr).multiselect('rebuild').multiselect('refresh');
				}
			}
		}
	},"json")
}
