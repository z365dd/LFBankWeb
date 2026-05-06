console.log('cloudpaymentList.js');

/*主页面全局变量SAVE_OR_REV
默认add为新增，revice为修改，detail为详细
用于子页面判断*/
var SAVE_OR_REV = "add";

$(function(){
	
	getSaleProdList();
	
	$("#saleProdCode").on("change", function(){
		getBusiNo($(this).val());
	});
	
//	//业务编号
//	$("#BUSI_NO").click(function(){
//		busiClick("BUSI_NO",null,null);
//	});
	
	/*查询*/
	$("#qryBtn").click(function(){
		if(proof()){
			freshTable("table");
		}
	});
	
	/*tab1点击*/
	$('#tab1').click(function(){
		$('#tab2').text('缴费项新增');
		SAVE_OR_REV = "add";
	});
	/*tab2点击*/
	$('#tab2').click(function(){
		$('#tab2').text('缴费项新增');
		SAVE_OR_REV = "add";
		ifr('panel2','/prod/oper/cloudpay/payment/paymentForm');
		/*setIfrHeight1('panel2',300);*/
	});
	/*$('#tab2').click();*/
	
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
		BUSI_NO:getI('BUSI_NO')
	}; 
	return paramList; 
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		 return params.offset / params.limit + 1;
	}
}


/*详细*/
function Detail(busiNo,busiName,modTlrNo,modDt){
	getDetail(busiNo,busiName,modTlrNo,modDt,"detail");
} 

/*修改*/
function Revice(busiNo,busiName,modTlrNo,modDt){
	getDetail(busiNo,busiName,modTlrNo,modDt,"revice");
}

/*删除*/
function Delete(busiNo){
	
	Ewin.confirm({
		  title : "操作提示",
		  message : "确定删除吗？"
		 }).on(function(e) {
		  if (!e) {
		   return;
		  }
		  $.post(ctx+"/prod/oper/cloudpay/payment/delete",{
				BUSI_NO : busiNo
			},function(data){
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					freshTable("table");
				}
			},"json")
		  })
	
}

/*获取详细数据,打开tab2传入数据*/
function getDetail(busiNo,busiName,modTlrNo,modDt,tp){
	$.ajax({
		url:ctx + "/prod/oper/cloudpay/payment/getDetail", 
		type:"GET",
		dataType:"json",
		data:{
			BUSI_NO:busiNo,
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
				showTip(successMsg,"success");
				
				var DataArr = eval(data.dataSetResult[0].data);
				
				$('#tab2').click();
				
				if(tp=="detail"){
					$('#tab2').text('缴费项详细');
					SAVE_OR_REV = "detail";
				}else if(tp=="revice"){
					$('#tab2').text('缴费项修改');
					SAVE_OR_REV = "revice";
				}
				$("#panel2").find('iframe').load(function() {
					$("#panel2").find('iframe')[0].contentWindow.setData(DataArr,busiNo,busiName,modTlrNo,modDt);
				});
			}
		}
	});
}


function getSaleProdList() {
	var datass = {compNo:"999301", start:'0', pageSize:'0'};
	setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd", "saleProdCode", "saleProdDesc", datass, "nulls", false, false);
}

function getBusiNo(saleProdCode) {
	var datass = {saleProdCode:saleProdCode, start:'0', pageSize:'0'};
	setSelect2("BUSI_NO", "/prod/oper/cloudpay/pay/listBusiNo", "busiNo", "text", datass, "nulls", false, false);
	
}
