var arr=[];
var selectData = [];
$(document).ready(function(){
	parent.window.$("#iframe_list").show();

	getSaleProdList();
	
//	var saleProdCode = $.session.get();
	
	$(".pay").hide();
	$(".pro").hide();
	$(".btn-danger").hide();
	$(".xf").hide();
	$(".online").hide();
//	$(".add-conf-1:fisrt").hide();
	
	$("#saleProdCode").on("change", function(){
		$(".pay").hide();
		removePara();
		$(".add-pro-1").remove();
		getBusiNo($(this).val());
		getPayPara($(this).val());
		$(".xf").hide();
		$(".online").hide();
	});
	
	$("#busiNo").on("change", function(){
		$(".pay").show();
		$(".pro").hide();
		var value = $("#busiNo").val();
		var valArr = value.split("$@$");
		var busiNo = valArr[0];
		var busiTp = valArr[1];
		selectPro(busiNo,busiTp);
		$("#end_time").val("");
		selectData = [];
		
	});
	
//	var amt = $("input[data-ordamtflg='Y']").val()==""?0:$("input[data-ordamtflg='Y']").val();
	$("#pro").on('change',function(){
//		var amt = 0;
		var valStr = getS("pro", ";");
//		var val = $("#pro option:selected").val();
		var valArr = valStr.split(";");
		var val = 0;
		for (var i=0;i<valArr.length;i++) {
			var payAmt = valArr[i].split("$@$")[1];
			val = Number(val)+ Number(payAmt);
		}
		var amt = Number(val);
		
		$("input[data-ordamtflg='Y']").val(amt);
	});
});

function getSaleProdList() {
	var datass = {compNo:"999301", start:'0', pageSize:'0'};
	setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd", "saleProdCode", "saleProdDesc", datass, "nulls", false, false);
}

function getBusiNo(saleProdCode) {
	var datass = {saleProdCode:saleProdCode, start:'0', pageSize:'0'};
	setSelect2("busiNo", "/prod/oper/cloudpay/pay/listBusiNo", "value", "text", datass, "nulls", false, false);
	
}

function getStrTime(busiNo) {
	var datass = {busiNo:busiNo, start:'0', pageSize:'0'};
	setSelect2("projNo", "/prod/oper/cloudpay/pay/listPayTime", "projNo", "text", datass, "nulls", false, false);
	
}

function getPayPara(saleProdCode) {
	/*查询明细*/
	console.info('get payFaceConf info......');
	$.post(ctx + "/prod/oper/cloudpay/payconf/payFaceConf/get", {saleProdCode:saleProdCode}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				var dataSetName = data.dataSetResult[i].dataSetName;
				if (dataSetName == "paraDs") {
					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
						var jsonObj = data.dataSetResult[i].data[j];
						if (jsonObj == "[" || jsonObj == "]") {
							return true;
						}
						console.info(jsonObj);
						copyPara(jsonObj);
					}
					
				}
				
			}
			
//			$(".pay").show();
			$("#hidden").parents("div.add-conf-1").hide();
			$.session.set("arr", arr);
		}
	},
	"json");

	
}

function jump() {
	console.info("open pay tab");
	var value = $("#busiNo").val();
	var valArr = value.split("$@$");
	var busiNo = valArr[0];
	var amt = $("input[data-ordamtflg='Y']").val();
	$.session.set('amt', amt);
	$.session.set('payNo', $("#payNo").val());
	$.session.set('saleProdCode', $("#saleProdCode").val());
	$.session.set('busiNo', busiNo);
	parent.window.$("a[href^='#tab_add']").attr("url", ctx + "/prod/oper/cloudpay/pay/payConfirm");
	parent.window.$("a[href^='#tab_add']").click();
}

function copyPara(jsonObj) {
//    var rnd = RanNum(5);
    $('.add-conf-1:first').clone().appendTo($('.add-conf'));
    
    $('.add-conf-1:last').find(".control-label").html(jsonObj.keyName);
    $('.add-conf-1:last').find('.add-conf-1-1').attr('id', jsonObj.keyNo);//payNo, amt,....
    $('.add-conf-1:last').find('.add-conf-1-1').attr('name', jsonObj.keyNo); // jsonObj.keyNo : custName
    $('.add-conf-1:last').find('.add-conf-1-1').attr('data-trgqryflg', jsonObj.trgQryFlg);
    if (jsonObj.trgQryFlg == "Y") {
    	var $input = $('.add-conf-1:last').find('.add-conf-1-1');
    	getAmt($input);
    }
    if (jsonObj.enterFlg == "02") {
    	$('.add-conf-1:last').find('.add-conf-1-1').attr('readonly', "readonly");
    }
    $('.add-conf-1:last').find('.add-conf-1-1').attr('data-ordamtflg', jsonObj.ordAmtFlg);
    
    if (jsonObj.ordAmtFlg == "N" && jsonObj.trgQryFlg == "N") {
    	var keyNo = jsonObj.keyNo;
		arr.push(
			keyNo
        );
    	$('.add-conf-1:last').find('.add-conf-1-1').attr('data-dataflg', "N");
    }
    
    $('.add-conf-1:last').attr('data-delflag', "Y");
    
    $('.add-conf-1:last').show();
   
}

function getAmt($input){
	$input.bind("blur",function(){
		if ($(this).val() == "") {
			return '0';
		}
		$(".add-conf-1:visible").find("input[data-dataflg='N']").val("");
		$("input[data-ordamtflg='Y']").val("");
		$("#payNo").val($(this).val());
//        if($(this).val().length >= "8"){
		if(true){
        	$(".add-pro-1").remove();
        	
        	if (selectData.length > 0) {
        		return '0';
        	} 
        	
        	var value = $("#busiNo").val();
    		var valArr = value.split("$@$");
    		var busiNo = valArr[0];
        	console.log("触发查询");
        	$.post(ctx + "/prod/oper/cloudpay/pay/getPayIndexPara",
        		{
        			busiNo:busiNo,
        			payNo:$(this).val()
        		}, function(data){
        		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
        			var errMsg = "错误信息["+data.message+"]"; 
        			showContent(errMsg,"error");	
        		}else {
        			var checkbox = "<div class='form-group add-pro-1' style='margin-left:45px'>";
        			for(var i = 0 ; i < data.dataSetResult.length; i++){
        				var dataSetName = data.dataSetResult[i].dataSetName;
    					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
    						var jsonObj = data.dataSetResult[i].data[j];
    						
    						if (dataSetName == "payInfo") {
    							$("input[data-ordamtflg='Y']").val(jsonObj.ordAmt);
    							$.session.set("ordNo", jsonObj.ordNo);
    							if (jsonObj.busiTp == "00") { // 联网
    								var jsonStr = jsonObj.clob;
    								var json = JSON.parse(jsonStr);
    								console.log(arr[0]);
    								console.log(json[arr[0]]);
    								for(var a=0;a<arr.length;a++){
        								$("input[id='"+arr[a]+"']").val(json[arr[a]]); //jsonObj.CUST_NAME 
        								$.session.set(arr[a], json[arr[a]]);
        							}
    							} else {
    								for(var a=0;a<arr.length;a++){
        								$("input[id='"+arr[a]+"']").val(jsonObj[arr[a]]); //jsonObj.CUST_NAME 
        								$.session.set(arr[a], jsonObj[arr[a]]);
        							}
    							}
    						} else if (dataSetName == "cloudPayment") {
    							$(".add-conf-1:visible").find("input[data-dataflg='N']").parents("div.add-conf-1").find(".btn").click();
    							checkbox += copyProInfo(jsonObj);
    						}
    						
    					}
        			}
        			checkbox += "</div>";
        			$(checkbox).appendTo($('.add-conf'));
        			bindCheck();
        			count = 0;
        		}
        	},
        	"json");
        }
    });
}

var count = 0;
function copyProInfo(jsonObj){
	count++;
	var html = "<div class='col-sm-4'>" +
			   "<input type='checkbox' style='margin-left:10px' name='pro"+"_"+count+"' id='pro"+"_"+count+"' value='"+jsonObj.PROJ_AMT+"'> "+jsonObj.PROJ_NAME+": "+jsonObj.PROJ_AMT+"元"+"</input>" +
			   "</div>";
	if(count%2==1){
		html += "<div class='col-sm-1'></div>";
	}
	if(count%2==0){
		html+="<br/>";
	}
	return html;
}

function removePara(){
	var $btn = $("div[data-delflag='Y']").find(".btn");
	
	$btn.click();
}

function rmKey(ele) {
	console.log("remove");
    $(ele).closest('.add-conf-1').remove();
}

function bindCheck(){
	var amt = $("input[data-ordamtflg='Y']").val()==""?0:$("input[data-ordamtflg='Y']").val();
	var _checkbox = $("input[type='checkbox']");
	_checkbox.each(function(){
		$(this).on("click", function(){
			if ($(this).is(":checked")) {
				amt	= Number(amt) + Number($(this).val());
			} else {
				amt -= $(this).val();
			}
			$("input[data-ordamtflg='Y']").val(amt);
		})
	});
}

function selectPro(busiNo, busiTp){
//	selectData = [];
	$("#pro").multiselect('dataprovider', []).multiselect('rebuild').multiselect('refresh');
	$.post(ctx + "/prod/oper/cloudpay/payment/getAllLeaf", {busiNo:busiNo}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				if (data.dataSetResult[i].data == "[]") {
					if(busiTp == "01"){
						//getStrTime($("#busiNo").val());
						$(".xf").show();
					}
					if(busiTp == "00"){
						$(".online").show();
					}
					return true;
				}
				for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
					var jsonObj = data.dataSetResult[i].data[j];
					selectData.push({
			            label: jsonObj.PROJ_NAME + ": " + jsonObj.PROJ_AMT + "元",
			            value: jsonObj.PROJ_NO + "$@$" + jsonObj.PROJ_AMT
			        });
				}
			}
			$("#pro").multiselect('dataprovider', selectData).multiselect('rebuild').multiselect('refresh');
			if (selectData.length > 0) {
				$(".xf").hide();
				$(".online").hide();
				$(".pro").show();
			}
		}
	},
	"json");
}

