console.log('paraEntrQry.js');

var $A_M_D = 'A';  //(判断A-新增、M-修改、D-详细页面标志)
$(document).ready(function(){
	
//	setSelect1("LEGA_NO","/comp/sign/pub/getLegaNo","LEGA_NO","LEGA_NAME");
	getLegaNo("LEGA_NO");
	
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
			var $url = "/comp/sign/pub/entrData?legaNo="+legaNo;
		}
		$("#ENTR_NO").attr("search_url",$url);
		entrClick("ENTR_NO","ENTR_NAME",resEntrNo);
	});
	
	/*查询*/
	$("#qryBtn").click(function(){
		qry();
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$(document).scrollTop(0);
		$('#tab2').text('单位新增');
		$A_M_D ='A';
	});
	
	/*tab2点击*/
	$('#tab2').on('click',tab2Click);
	/*$('#tab2').click(function(){
		$A_M_D ='A';
		$('#tab2').text('单位新增');
		ifr('panel2','comp/sign/tec/signParaEntr/form');
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


/*查询table*/
function qry(){
	$("#table").bootstrapTable('removeAll');
	freshTable("table");
	/*if(proof()){
		
	}*/
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

		LEGA_NO:getS('LEGA_NO'),
		ENTR_NO:getI('ENTR_NO'),
		OPEN_STAT:getS('OPEN_STAT'),
		CERT_TP:getS('CERT_TP'),
		CERT_NO:getI("CERT_NO")
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}

//详细调用
function Detail(DataStr){
//	var Json = JSON.parse(DataStr);
	$A_M_D ='D';
	$('#tab2').click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal(DataStr);
	});
}

//修改调用
function Revice(DataStr){
//	var Json = JSON.parse(DataStr);
	$A_M_D ='M';
	$('#tab2').click();
	$("#panel2").find('iframe').load(function(){
		$("#panel2").find('iframe')[0].contentWindow.setVal(DataStr);
	});
}

//删除调用
function Delete(entrNo){
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		$.ajax({
			url:ctx + "/comp/sign/tec/signParaEntr/del", 
			type:"POST",
			dataType:"json",
			data:{
				/*BUSI_NO:busiNo,
				SUB_BUSI_NO:subBusiNo*/
				ENTR_NO:entrNo
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
		$('#tab2').text('单位新增');
		ifr('panel2','comp/sign/tec/signParaEntr/form');
		$("#panel2").find('iframe').load(function(){
			$(this).height($(this).contents().find('body').height()+200);
		});
	}else if($A_M_D == "M"){
		if($('#tab2').text() == "单位修改"){
			
		}else{
			$('#tab2').text('单位修改');
			ifr('panel2','comp/sign/tec/signParaEntr/form');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}else if($A_M_D == "D"){
		if($('#tab2').text() == "单位详情"){
			
		}else{
			$('#tab2').text('单位详情');
			ifr('panel2','comp/sign/tec/signParaEntr/form');
			$("#panel2").find('iframe').load(function(){
				$(this).height($(this).contents().find('body').height()+200);
			});
		}
	}
}

function resEntrNo(){
	
}

