$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	
	busiNo = $.session.get("busiNo");
	amt = $.session.get("amt");
	payNo = $.session.get("payNo");
	arrStr = $.session.get("arr");
	$("#busiNo").val(busiNo);
	$("#amt").val(amt);
	$("#payNo").val(payNo);
	console.log(arrStr);
	if (arrStr != "undefined" && arrStr != undefined) {
		var arr = arrStr.split(",");
		for (var i=0;i<arr.length;i++){
			var value = $.session.get(arr[i])=="undefined"?"":$.session.get(arr[i]);
			var html = "<input type='hidden' id='"+arr[i]+"' name='"+arr[i]+"' value='"+value+"' />"
			$("#payNo").after(html);
		}
	}
	var ordNo = genOrdNo();
	$("#ordNo").val(ordNo);
	$("#payMethod").on("change", function(){
		$("#card").hide();
		$("#wechat").hide();
		$("#alipay").hide();
		if ($(this).val() == "01") {
			$(".qrcode").hide();
			$("#payBtn").show();
			$("#card").show();
		} else if ($(this).val() == "02" || $(this).val() == "03") {
			console.log($(this).val());
			$("#qrcode").html("");
			$("#payBtn").hide();
			$(".qrcode").show();
			jQuery('#qrcode').qrcode({
			    render: "table", //也可以替换为table
			    width: 100,
			    height: 100,
			    text: "http://www.baidu.com"
			});
		}
	});
	
	/*保存按钮*/
	$('#payBtn').click(function(){
		if(proof()){
			$("#payAcctName").val($("#payAcct option:selected").text().trim());
			submit();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function submit(){
	confirmx('是否付款', function(){
		save();
	});
}

/**
 * 保存函数--保存产品线新增
 * @returns
 */
function save(){
	var formData = $("#addForm").serializeObject();

	/*向后台发送参数*/
	$.post(ctx + "/prod/oper/cloudpay/pay/insert", formData,
		function(data){ 
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			    var errMsg = "错误信息["+data.message+"]"; 
				showContent(errMsg,"error");
				return '0';
			}else if(data.msg_type == "success"){
				var successMsg = "新增交易["+data.message+"]"; 
				showContent(successMsg,"success");
				console.info("新增交易成功");
				cancle();
			}
	}, "json");
	
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}


function genOrdNo() {
	var ordNo = ""
	var myDate = new Date();
	
	var year=myDate.getFullYear();        //获取当前年
	var month=myDate.getMonth()+1;   //获取当前月
	var date=myDate.getDate();            //获取当前日
	 
	 
	var h=myDate.getHours();              //获取当前小时数(0-23)
	var m=myDate.getMinutes();          //获取当前分钟数(0-59)
	var s=myDate.getSeconds();
	var tranTime = year+getNow(month)+getNow(date)+getNow(h)+getNow(m)+getNow(s);
	var ordNo=tranTime+RanNum(5);
	$("#tranTime").val(tranTime);
	var flg = $.session.get("ordNo"); 
	if (flg!=undefined&&flg!=null&&flg!="" ) {
		ordNo = $.session.get("ordNo"); 
		$.session.remove("ordNo"); 
	}
	return ordNo;
	
}

function getNow(s) {
	return s < 10 ? '0' + s: s;
}

function RanNum(n) {
	var rnd = "";
	for (var i = 0; i < n; i++) {
	  rnd += Math.floor(Math.random() * 10);
	}
	return rnd;
}