console.log('signStatsQry.js');

$(document).ready(function(){
	
	/*提交按钮*/
	$('#submitBtn').on('click',submit);
	
	/*关闭按钮*/
	$('#closeBtn').on('click',close);	
	
});


function submit(){
	
	if(proof()){
		var Data = {
				BUSI_NO:getI("BUSI_NO"),
				SUB_BUSI_NO:getI("SUB_BUSI_NO"),
				ENTR_NO:getI("ENTR_NO"),
				OLD_ACCT:getI("OLD_ACCT"),
				ACCT_NAME:getI("ACCT_NAME"),
				BANK:getI("BANK"),
				BANK_NAME:getI("BANK_NAME"),
				PER_TEL_NO:getI("PER_TEL_NO"),
				CERT_TP:getS("CERT_TP"),
				CERT_NO:getI("CERT_NO"),
				OTH_CUST_NO:getI("OTH_CUST_NO"),
				ENTR_ACCT:getI("ENTR_ACCT"),
				SIGN_PROT_NO:getI("SIGN_PROT_NO"),
				OPP_ACCT:getI("OPP_ACCT"),
				OPP_ACCT_NAME:getI("OPP_ACCT_NAME"),
				OPP_BANK:getI("OPP_BANK"),
				OPP_BANK_NAME:getI("OPP_BANK_NAME"),
				ENTR_NAME:getI("ENTR_NAME"),
				TRAN_AMT:getI("TRAN_AMT"),
				FLG:getS("FLG"),
				CHNL_NO:getI("CHNL_NO")
		};
		
		$.ajax({
			type:"POST",
			url:ctx+"/comp/sign/test/signChk/chk",
			dataType:"json",
			data:Data,
			success:function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]";
					showTip(errMsg,"error");
				}else{
					var successMsg = "签约校验["+data.message+"]"; 
					showContent(successMsg,"success");
				}
			}
		});
	}
}

/*关闭执行*/
function close(){
	Ewin.confirm({
		title : "操作提示",
		message : "将会关闭此页面，确定关闭吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		top.$.fn.jerichoTab.closeCurrentTab(); 
	});
}


