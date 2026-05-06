console.log("importPayInfo.js");

$(document).ready(function(){
	getSaleProdList();
	
	$("#saleProdCode").on("change", function(){
		getBusiNo($(this).val());
	});
	

$("#expBtn").click(function(){
	if(!proof()){
		return;
	}
        var formData = $("#ex_importForm").serializeObject();
        top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
            if(v=="ok"){
                $("#ex_importForm").attr("action",ctx+"/prod/oper/importOrExportComp/exportData");
                $("#ex_importForm").submit();
            }
        },{buttonsFocus:1});
        top.$('.jbox-body .jbox-icon').css('top','55px');
    });
    
    
$("#submitBtn").click(function(){
	if(!proof()){
		return;
	}
    	var options  = {
                url:ctx+"/prod/oper/cloudpay/leadIn/importPayInfo",   // 同action
                type:'post',
                dataType: "json",
                clearForm: true,    /*成功提交后，清除所有表单元素的值*/
                resetForm: true,    /*成功提交后，重置所有表单元素的值*/
                success:function(data){
                    console.info(data);
                    if(data.returnCode!==undefined && "0000"!=data.returnCode){
                        var errMsg = "错误信息["+data.message+"]";
                        showContent(errMsg,"error");
                    }else{
                        var Msg = "提示:[" + data.message + "]";
                        showContent(Msg,"success");
                        refreshS('BUSI_NO', "/prod/oper/importOrExportEntr/getBusiNo");
                    }
                },
                error:function(xhr,status,msg){
                    showTip(msg,"error");
                }
            };
            $("#ex_importForm").ajaxSubmit(options);
    })

});

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

function getSaleProdList() {
	var datass = {compNo:"999301", start:'0', pageSize:'0'};
	setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd", "saleProdCode", "saleProdDesc", datass, "nulls", false, false);
}

function getBusiNo(saleProdCode) {
	var datass = {saleProdCode:saleProdCode, start:'0', pageSize:'0'};
	setSelect2("BUSI_NO", "/prod/oper/cloudpay/pay/listBusiNo", "busiNo", "text", datass, "nulls", false, false);
	
}
