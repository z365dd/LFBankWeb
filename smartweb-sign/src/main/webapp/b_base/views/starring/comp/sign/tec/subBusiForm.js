console.log('subBusiForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改 ，detail详细
 */
var $SAVE_OR_REV = "add";
var $A_M_D = parent.$A_M_D;

$(document).ready(function(){
	
	$("#BUSI_NO").click(function(){
		qryBusiList();
	});
	
	if($A_M_D == "M"){
		//修改页面:开通状态没有删除状态
		$("#OPEN_STAT option[value='3']").remove();
		$("#OPEN_STAT").multiselect('rebuild').multiselect('refresh');
	}
	
	/*返回按钮*/
	$('#closeBtn').on('click',close);
	
	/*提交按钮*/
	$('#submitBtn').on('click',submit);

});

/*筛选有子业务的业务编号*/
function qryBusiList(){
	var $url = "/comp/sign/pub/busiData?state=1&flg=Y";
	$("#BUSI_NO").attr("search_url", $url);
	busiClick("BUSI_NO");
}

/*关闭执行*/
function close(){
	parent.goTop();
	if($A_M_D == "D"){
		parent.tab1();
	}else{
		Ewin.confirm({
			title : "操作提示",
			message : "数据会清空，确定关闭吗？"
		}).on(function(e) {
			if (!e) {
				return;
			}
			parent.tab1();
		});
	}
}

/*提交执行*/
function submit(){
	if(proof()){
		var $url = ctx ;
		if($SAVE_OR_REV=="add"){
			$url += "/comp/sign/tec/signSubBusi/add";
		}else if($SAVE_OR_REV=="revice"){
			$url += "/comp/sign/tec/signSubBusi/mod";
		}
		$.ajax({
			url:$url, 
			type:"POST",
			dataType:"json",
			data:{
				BUSI_NO:getI("BUSI_NO"),
				SUB_BUSI_NO:getI("SUB_BUSI_NO"),
				SUB_BUSI_NAME:getI("SUB_BUSI_NAME"),
				OPEN_STAT:getS("OPEN_STAT"),
				MNG_BRCH:getI("MNG_BRCHId"),
				BUSI_BRCH:getI("BUSI_BRCHId"),
				CLR_BRCH:getI("CLR_BRCHId"),
				SIGN_FLG:getS("SIGN_FLG")
			}, 
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交["+data.message+"]"; 
					showContent(successMsg,"success");
					if($SAVE_OR_REV=="add"){
						parent.tab1();
					}else if($SAVE_OR_REV=="revice"){
						parent.tab1("M");
					}
				}
			}
		});
	}
}


/*设置值*/
function setVal(mode, compNo, busiNo, busiName, subBusiNo, subBusiName, openStat, mngBrch, mngBrchName, busiBrch, busiBrchName, clrBrch, clrBrchName, signFlg){
	$SAVE_OR_REV = mode;
	if(mode == "revice"){
		$("#BUSI_NO").off("click", qryBusiList);
		$("#SUB_BUSI_NO").prop("readonly", true);
	}else if(mode == "detail"){
		$("#infoDiv").find("select").multiselect("disable");
		$("#infoDiv").find("input").prop("disabled", true);
		$("#submitBtn").hide();
	}
	
	setI("BUSI_NO",busiNo);
	setI("SUB_BUSI_NO",subBusiNo);
	setI("SUB_BUSI_NAME",subBusiName);
	setS("OPEN_STAT",openStat);
	setI("MNG_BRCHId",mngBrch);
	if(mngBrchName==null||mngBrchName=="null"){
		setI("MNG_BRCHName","");
	}else{
		setI("MNG_BRCHName",mngBrchName);
	}
	setI("BUSI_BRCHId",busiBrch);
	if(busiBrchName==null||busiBrchName=="null"){
		setI("BUSI_BRCHName","");
	}else{
		setI("BUSI_BRCHName",busiBrchName);
	}
	setI("CLR_BRCHId",clrBrch);
	if(clrBrchName==null||clrBrchName=="null"){
		setI("CLR_BRCHName","");
	}else{
		setI("CLR_BRCHName",clrBrchName);
	}
	setS("SIGN_FLG",signFlg);

}


