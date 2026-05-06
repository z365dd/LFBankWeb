console.log('custSignList.js');

var $A_M_D = 'A';  //(判断A-新增、M-修改、D-详细页面标志、C-解约)

$(function(){
	/*加载组件号下拉框*/
	getCompNo("COMP_NO");
	
	/*加载法人号下拉框*/
	getLegaNo("LEGA_NO");
	
	/*组件号改变重新加载业务编号*/
	$("#COMP_NO").change(function(){
		$("#BUSI_NO").val("");
		resBusiNo();
		resetS("SUB_BUSI_NO");
	});
	
	$("#BUSI_NO").click(function(){
		var $compNo = getS("COMP_NO");
		var $url = "/comp/sign/pub/busiData?state=1";
		
		if($compNo != ""){
			$url += ("&compNo="+$compNo);
		}else{
			$url = "/comp/sign/pub/busiData?state=111";
		}
		
		$("#BUSI_NO").attr("search_url", $url);
		busiClick("BUSI_NO", null, null, resBusiNo);
	});
	
	/*法人号改变重新加载单位编号*/
	$('#LEGA_NO').change(function(){
		$("#ENTR_NO").val("");
		resEntrNo();
	});
	
	$('#ENTR_NO').click(function(){
		var legaNo = getS("LEGA_NO");
		if(legaNo == ""){
			var $url = "/comp/sign/pub/entrData?stat=11";  //stat=11随便赋值的(只有不为""、1、2、3即可)
		}else{
			var $url = "/comp/sign/pub/entrData?legaNo="+legaNo+"&stat=1";
		}
		$("#ENTR_NO").attr("search_url",$url);
		entrClick("ENTR_NO","ENTR_NAME",resEntrNo);
	});
	
	$("#qryBtn").click(function(){
		if(getI("BUSI_NO")=="" && getS("SUB_BUSI_NO")=="" && getI('ENTR_NO')=="" && getI('ACCT')==""
			&& getS("CERT_TP")=="" && getI('CERT_NO')=="" && getI('SIGN_PROT_NO')==""){
			
			showTip("不能所有查询条件均为空","error");
			return;
		}
		$("#qryTable").bootstrapTable('refresh');
		
	});
	
	/*tab1点击(业务查询)*/
	$('#tab1').click(function(){
		$(document).scrollTop(0);
		$('#tab2').text('客户签约新增');
		$A_M_D = "A";
	});
	
	/*tab2点击(业务新增)*/
	$('#tab2').on('click',tab2Click);
	
});

/*根据业务编号查询子业务编号(只返回可被签约的)*/
function resBusiNo(){
	var busiNo = $("#BUSI_NO").val();
	if(busiNo == ""){
		$("select[name=SUB_BUSI_NO]").empty();
	}else{
		var Data = {
				BUSI_NO:busiNo,
				signFlg:"Y"
			}
			setSelect2("SUB_BUSI_NO","/comp/sign/tec/signSubBusi/qrySubBusiNo","SUB_BUSI_NO","SUB_BUSI_NAME",Data,busiNo);
	}
}

/*保留*/
function resEntrNo(){
	
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
		BUSI_NO:getI('BUSI_NO'),
		ENTR_NO:getI('ENTR_NO'),
		SUB_BUSI_NO:getS('SUB_BUSI_NO'),
		ACCT:getI('ACCT'),
		CERT_TP:getS('CERT_TP'),
		CERT_NO:getI('CERT_NO'),
		SIGN_PROT_NO:getI('SIGN_PROT_NO')
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
//	var Json = JSON.parse(DataStr);
	$('#tab2').click().text('客户签约详情');
	$A_M_D = "D";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal("D",DataStr);
	});
}

//查询页面点修改操作
function modAction(DataStr){
//	var Json = JSON.parse(DataStr);
	$('#tab2').click().text('客户签约修改');
	$A_M_D = "M";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal("M",DataStr);
	});
}

//查询页面点击解约操作
function delAction(DataStr){
	$('#tab2').click().text('客户解约');
	$A_M_D = "C";
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal("C",DataStr);
	});
}

/*tab1选项卡点击*/
function tab1(tp){
	$("#tab1").click();
	if(tp == "M" || tp == "C"){
		$("#qryBtn").click();
	}
}

/*tab2选项卡点击 页面的跳转情况*/
function tab2Click(){
	if($A_M_D == "A"){
		$('#tab2').text('客户签约新增');
		ifr('panel2','/comp/sign/test/custsign/toForm');
		$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+200);
		});
	}else if($A_M_D == "M"){
		if($('#tab2').text() == '客户签约修改'){
			
		}else{
			$('#tab2').text('客户签约修改');
			ifr('panel2','/comp/sign/test/custsign/toForm');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}else if($A_M_D == "D"){
		if($('#tab2').text() == '客户签约详情'){
			
		}else{
			$('#tab2').text('客户签约详情');
			ifr('panel2','/comp/sign/test/custsign/toForm');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}else if($A_M_D == "C"){
		if($('#tab2').text() == '客户解约'){
			
		}else{
			$('#tab2').text('客户解约');
			ifr('panel2','/comp/sign/test/custsign/toForm');
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