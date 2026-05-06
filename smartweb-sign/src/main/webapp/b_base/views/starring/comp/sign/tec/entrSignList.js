var $A_M_D = "A";
$(function(){
	/*加载单位编号*/
	$("#ENTR_NO").click(function(){
		var $url = "/comp/sign/pub/entrData?stat=1";
		$("#ENTR_NO").attr("search_url", $url);
		entrClick("ENTR_NO", null, null, resEntrNo);
	});
	
	/*加载业务编号*/
	$("#BUSI_NO").click(function(){
		var $url = "/comp/sign/pub/busiData?state=1";
		$("#BUSI_NO").attr("search_url", $url);
		busiClick("BUSI_NO", null, null, resBusiNo);
	});
	
	/*tab1点击(业务查询)*/
	$('#tab1').click(function(){
		$(document).scrollTop(0);
		$('#tab2').text('单位签约新增');
		$A_M_D = "A";
	});
	
	/*tab2点击(业务新增)*/
	$('#tab2').on('click',tab2Click);
	
	/*点击查询按钮*/
	$("#qryBtn").click(function(){
		if(getI('BUSI_NO')=="" && getI('ENTR_NO')=="" && getI('ACCT')=="" &&
			       getS('CERT_TP')=="" && getI('CERT_NO')=="" &&
			       getI('SIGN_PROT_NO')==""){
				
				showTip("不能所有查询条件均为空","error");
				return;
			}
		$("#qryTable").bootstrapTable('refresh');
	});
});

/*单位编号*/
function resEntrNo(){
	
}

/*根据业务加载子业务*/
function resBusiNo(){
	var busiNo = $("#BUSI_NO").val();
	if(busiNo == ""){
		$("select[name=SUB_BUSI_NO]").empty();
	}else{
		qrySubBusiNo("SUB_BUSI_NO",busiNo);
	}
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
		BUSI_NO:getI('BUSI_NO'),
		SUB_BUSI_NO:getS('SUB_BUSI_NO'),
		ACCT:getI('ACCT'),
		CERT_TP:getS('CERT_TP'),
		CERT_NO:getI('CERT_NO'),
		SIGN_PROT_NO:getI('SIGN_PROT_NO'),
		SIGN_STAT:getS('SIGN_STAT'),
		ENTR_NO:getI('ENTR_NO')
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

//查询页面点详细操作
function detailAction(DataStr){
	//var Json = JSON.parse(DataStr);
	$A_M_D = "D";
	$("#tab2").click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal("D", DataStr);
	});
}

//查询页面点修改操作
function modAction(DataStr){
	//var Json = JSON.parse(DataStr);
	$A_M_D = "M";
	$("#tab2").click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal("M", DataStr);
	});
}

//查询页面点击解约操作
function delAction(DataStr){
	$A_M_D = "C";
	$("#tab2").click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal("C", DataStr);
	});
}

/*tab1选项卡点击*/
function tab1(tp){
	$('#tab1').click();
	if(tp == 'M' || tp == 'C'){
		$("#qryBtn").click();
	}
}

/*tab2选项卡点击 页面的跳转情况*/
function tab2Click(){
	if($A_M_D == "A"){
		$('#tab2').text('单位签约新增');
		ifr('panel2','/comp/sign/tec/entrsign/toForm');
		$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+200);
		});
	}else if($A_M_D == "M"){
		if($('#tab2').text() == '单位签约修改'){
			
		}else{
			$('#tab2').text('单位签约修改');
			ifr('panel2','/comp/sign/tec/entrsign/toForm');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}else if($A_M_D == "D"){
		if($('#tab2').text() == '单位签约详情'){
			
		}else{
			$('#tab2').text('单位签约详情');
			ifr('panel2','/comp/sign/tec/entrsign/toForm');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}else if($A_M_D == "C"){
		if($('#tab2').text() == '单位解约'){
			
		}else{
			$('#tab2').text('单位解约');
			ifr('panel2','/comp/sign/tec/entrsign/toForm');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}
}

//返回顶页
function goTop(){
	$(document).scrollTop(0);
}