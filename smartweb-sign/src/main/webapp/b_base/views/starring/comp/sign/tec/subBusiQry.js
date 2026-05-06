console.log('subBusiQry.js');
var $A_M_D = "A";

$(document).ready(function(){
	
	/*加载模型号下拉框*/
	getCompNo("COMP_NO");
	
	/*查询*/
	$("#COMP_NO").change(function(){
		$("#BUSI_NO").val("");
		resBusiNo();
	});
	
	$("#BUSI_NO").click(function(){
		var $compNo = getS("COMP_NO");
		var $url = "/comp/prod/oper/entrManage/busiData";
		
		if($compNo != ""){
			$url += ("?compNo="+$compNo);
		}
		
		$("#BUSI_NO").attr("search_url", $url);
		busiClick("BUSI_NO", null, null, resBusiNo);
	});
	
	$("select[name=SUB_BUSI_NO]").multiselect('rebuild').multiselect('refresh');
	
	/*查询*/
	$("#qryBtn").click(function(){
		if(proof()){
			qry();
		}
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('子业务新增');
		$A_M_D ='A';
	});
	
	/*tab2点击(业务新增)*/
	$('#tab2').on('click',tab2Click);
	
	/*tab2点击
	$('#tab2').click(function(){
		$('#tab2').text('子业务新增');
		ifr('panel2','comp/sign/tec/signSubBusi/form');
		setIfrHeight1('panel2',300);
	});*/
	
});

/*tab1点击执行函数*/
function tab1(tp){
	$('#tab1').click();
	goTop();
	if(tp=="M"){
		qry();
	}
}

function resBusiNo(){
	var busiNo = $("#BUSI_NO").val();
	if(busiNo == ""){
		$("select[name=SUB_BUSI_NO]").empty();
	}else{
		qrySubBusiNo("SUB_BUSI_NO",busiNo);
	}
	
	/*else{
		$.ajax({
			url : ctx + "/comp/sign/tec/signSubBusi/qry",
			type : "POST",
			dataType : "json",
			data : {
				BUSI_NO:busiNo,
				start : 0,
				pageSize : 0
			},
			async : false,
			success : function(data) {
				if (data.returnCode !== undefined && "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var valData = data.dataSetResult[0].data;
					valData = eval(valData);
					$("select[name=subPage]").empty();
					if (valData.length != 0) {
						for (var i = 0; i < valData.length; i++) {
							$("select[name=SUB_BUSI_NO]").append("<option value='"+valData[i].SUB_BUSI_NO+"'>"+valData[i].SUB_BUSI_NAME+"</option>");
						}
						$("select[name=SUB_BUSI_NO]").multiselect('rebuild').multiselect('refresh');
					}
				}
			}
		});
	}*/
}

/*查询table*/
function qry(){
	if(proof()){
		$("#table").bootstrapTable('refresh'); 
//		freshTable("table");
	}
}

/*table参数*/
function queryParams(params){
	
	var paramList = { 
		pgside:'server',//服务器分页
		pageSize:params.limit,
		start:params.offset+1,
		pageNo:getPage(params),
		sort:params.sort,
		order:params.order,
		
		COMP_NO:getS('COMP_NO'),
		BUSI_NO:getI('BUSI_NO'),
		SUB_BUSI_NO:getS('SUB_BUSI_NO'),
		OPEN_STAT:getS('OPEN_STAT')
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

//详细调用
function Detail(compNo, busiNo, busiName, subBusiNo, subBusiName, openStat, mngBrch,mngBrchName, busiBrch, busiBrchName, clrBrch, clrBrchName,signFlg){
	$A_M_D ='D';
	$('#tab2').click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal("detail",compNo, busiNo, busiName, subBusiNo, subBusiName, openStat, mngBrch, mngBrchName, busiBrch, busiBrchName, clrBrch, clrBrchName, signFlg);
	});
}

//修改调用
function Revice(compNo, busiNo, busiName, subBusiNo, subBusiName, openStat, mngBrch, mngBrchName, busiBrch, busiBrchName, clrBrch, clrBrchName, signFlg){
	$A_M_D ='M';
	$('#tab2').click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal("revice",compNo, busiNo, busiName, subBusiNo, subBusiName, openStat, mngBrch, mngBrchName, busiBrch, busiBrchName, clrBrch, clrBrchName, signFlg);
	});
}

//删除调用
function Delete(busiNo, subBusiNo){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			url:ctx + "/comp/sign/tec/signSubBusi/del", 
			type:"POST",
			dataType:"json",
			data:{
				BUSI_NO:busiNo,
				SUB_BUSI_NO:subBusiNo
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

function tab2Click(){
	if($A_M_D == "A"){
		$('#tab2').text('子业务新增');
		ifr('panel2','comp/sign/tec/signSubBusi/form');
		$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+200);
		});
	}else if($A_M_D == "M"){
		if($('#tab2').text() == "子业务修改"){
			
		}else{
			$('#tab2').text('子业务修改');
			ifr('panel2','comp/sign/tec/signSubBusi/form');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}else if($A_M_D == "D"){
		if($('#tab2').text() == "子业务详情"){
			
		}else{
			$('#tab2').text('子业务详情');
			ifr('panel2','comp/sign/tec/signSubBusi/form');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}
}


