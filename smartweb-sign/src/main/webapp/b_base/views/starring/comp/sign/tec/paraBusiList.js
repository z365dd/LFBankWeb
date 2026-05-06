var $A_M_D = 'A';  //(判断A-新增、M-修改、D-详细页面标志)

$(function(){
	/*加载模型号下拉框*/
	getCompNo("COMP_NO");
	
	/*加载法人号下拉框*/
	getLegaNo("LEGA_NO");
	
	/*组件号下拉改变则重新加载业务编号下拉框*/
	$("#COMP_NO").change(function(){
		$("#BUSI_NO").val("");
//		resBusiNo();
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
	
	
	/*查询按钮点击进行查询*/
	/*startJudge('qryBtn');
	cOpt('MODL_NO');
	endJudge(qryBtnFun);*/
	$("#qryBtn").click(function(){
		if(proof()){
			$("#qryTable").bootstrapTable('refresh');
		}
	});
	
	/*tab1点击(业务查询)*/
	$('#tab1').click(function(){
		$(document).scrollTop(0);
		$('#tab2').text('业务新增');
		$A_M_D = "A";
	});
	
	/*tab2点击(业务新增)*/
	$('#tab2').on('click',tab2Click);
	
	/*tab2点击(业务新增)
	$('#tab2').click(function(){
		$A_M_D = "A";
		$('#tab2').text('业务新增');
		ifr('panel2','/comp/sign/tec/parabusi/toForm');
		$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+400);
		});
	});*/
	
});

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
		COMP_NAME:getST('COMP_NO'),
		BUSI_NO:getI('BUSI_NO'),
		OPEN_STAT:getS('OPEN_STAT')
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
	var Json = JSON.parse(DataStr);
	$A_M_D = "D";
	$('#tab2').click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setDetailVal(Json);
	});
}

//查询页面点修改操作
function modAction(DataStr){
	var Json = JSON.parse(DataStr);
	$A_M_D = "M";
	$('#tab2').click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setModVal(Json);
	});
}

//查询页面点击删除操作
function delAction(BUSI_NO){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			type : "POST",
			url : ctx+"/comp/sign/tec/parabusi/paraBusiDel",
			data : {
				BUSI_NO:BUSI_NO
			},
			dataType : "json",
			success : function(data) {
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var Msg = "错误信息["+data.message+"]";
					showContent(Msg,"error");
					$("#table").bootstrapTable("removeAll");
				}else{
//					showTip("删除成功","success");
					var Msg = data.message; 
				    showContent(Msg,"success");
			        $("#qryTable").bootstrapTable('refresh');
				}
			  }
		   });
	   });
}

//tab1选项卡点击事件
function tab1(tp){
	$('#tab1').click();
	$(document).scrollTop(0);
	$('#tab2').text('业务新增');
	$A_M_D = "A";
	if(tp=="M"){
		$("#qryBtn").click();
	}
}

function tab2Click(){
	if($A_M_D == "A"){
		$('#tab2').text('业务新增');
		ifr('panel2','/comp/sign/tec/parabusi/toForm');
		$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+200);
		});
	}else if($A_M_D == "M"){
		if($('#tab2').text() == "业务修改"){
			
		}else{
			$('#tab2').text('业务修改');
			ifr('panel2','/comp/sign/tec/parabusi/toForm');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}else if($A_M_D == "D"){
		if($('#tab2').text() == "业务详情"){
			
		}else{
			$('#tab2').text('业务详情');
			ifr('panel2','/comp/sign/tec/parabusi/toForm');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}
}

function resBusiNo(){}