console.info("paraMod...");
var $ModArr = "";
$(document).ready(function(){
	
	/*进入先查询参数*/
	paraQry();
	
	/*提交按钮*/
	$('#submitBtn').on('click',submit);
	
	/*关闭按钮*/
	$('#closeBtn').on('click',close);	
	
});

function madeHtml(key, keyName, kv){
	var $html = "<div ravo='rainbow_fx' class='form-group'>";
	$html += "<label for='inputEmail3' class='col-sm-4 control-label'>"+keyName+"</label>";
	$html += "<div class='col-sm-2'>";
	$html += "<input type='number' class='form-control' id='"+key+"' name='"+key+"' min='1' check-integer='true' value='"+kv+"'>";
	$html += "</div>";
	$html += "</div>";
	return $html;
}


function paraQry(){
	
	$.ajax({
		type:"POST",
		url:ctx+"/comp/sign/tec/signPara/qry",
		dataType:"json",
		async : true,
		data:{KEY : ""},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				var valData = data.dataSetResult[0].data;
//				valData = eval(valData);
				$ModArr = valData;
				for (var i = 0; i < valData.length; i++) {
					var $html = madeHtml(valData[i].KEY, valData[i].KEY_NAME, valData[i].KV);
					$("#rowDiv").append($html);
				}
			}
		}
	});
}

function submit(){
	
	var $paraArr = $("#rowDiv").find("input");
	
	if($ModArr==undefined || $ModArr.length==0){
		showTip("系统出错,请刷新后再尝试!", "success");
		return;
	}else{
		for(var i=0;i<$ModArr.length;i++){
			$ModArr[i].KV = $paraArr[i].value;
		}
	}
	
	if(proof()){
		var KEY_LIST = JSON.stringify($ModArr);
		$.ajax({
			type:"POST",
			url:ctx+"/comp/sign/tec/signPara/mod",
			dataType:"json",
			data:{
				KEY_LIST:KEY_LIST
			},
			success:function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]";
					showTip(errMsg,"error");
				}else{
					var successMsg = "参数维护["+data.message+"]"; 
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