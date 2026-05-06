console.log('signStatsQry.js');

$(document).ready(function(){
	
	/*加载组件号下拉框*/
	getCompNo("COMP_NO");
	
	/*加载单位号下拉框*/
	getEntrNoByStat("ENTR_NO");
//	setSelect1("ENTR_NO","/comp/sign/tec/signParaEntr/qry","ENTR_NO","ENTR_NAME");
	
	/*查询业务编号*/
	$("#COMP_NO").change(function(){
		$("#BUSI_NO").val("");
		resBusiNo();
	});
	
	/*根据业务编号查询子业务编号*/
	$("#BUSI_NO").click(function(){
		var $compNo = getS("COMP_NO");
		var $url = "/comp/sign/pub/busiData?state=1";
		
		if($compNo != ""){
			$url += ("&compNo="+$compNo);
		}
		
		$("#BUSI_NO").attr("search_url", $url);
		busiClick("BUSI_NO", null, null, resBusiNo);
	});
	
	$("select[name=SUB_BUSI_NO]").multiselect('rebuild').multiselect('refresh');
//	$("select[name=ENTR_NO]").multiselect('rebuild').multiselect('refresh');
	
	/*查询*/
	$("#qryBtn").click(function(){
		qry();
	});
	
});


function resBusiNo(){
	var busiNo = $("#BUSI_NO").val();
	if(busiNo == ""){
		$("select[name=SUB_BUSI_NO]").empty();
	}else{
		qrySubBusiNo("SUB_BUSI_NO",busiNo);
	}
}

/*查询table*/
function qry(){
	if(proof()){
		freshTable("table");
	}
}

/*table参数*/
function queryParams(params){
	var END_DATE = (dateDelete(getI('END_DATE'))).replace(/(^\s*)|(\s*$)/g,"");
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
		ENTR_NO:getS('ENTR_NO'),
		STR_DATE:dateDelete(getI('STR_DATE')),
		END_DATE:END_DATE
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}



