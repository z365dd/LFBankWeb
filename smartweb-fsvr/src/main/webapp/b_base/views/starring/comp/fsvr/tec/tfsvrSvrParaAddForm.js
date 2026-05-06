console.log('tfsvrSvrParaAddForm.js');

$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	setSelect1("deponFileSvrId","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
	/*保存按钮*/
	$('#saveBtn').click(function(){
		if(proof()){
			submit();
		};
	});
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function submit(){
	confirmx('是否新增外部文件服务器', function(){
		save();
	});
}

/**
 * 保存函数--保存外部文件服务器新增
 * @returns
 */
function save(){
	var formData = getData();

	/*向后台发送参数*/
	$.post(ctx + "/comp/fsvr/tec/tfsvrSvrPara/insert", formData,
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

/*获取页面数据data*/
function getData(){
	var data= {
			//界面
			ip:getI('ip'),
			port:getI('port'),
			openSvcFlg:getS("openSvcFlg"),
			svrDesc:getI("svrDesc"),
			fileSvrId:getI('fileSvrId'),
			
			userNo:getI('userNo'),
			commProtGrpTp:getS('commProtGrpTp'),
			pwd:getI('pwd2'),
			downloadFilePath:getI('downloadFliePath'),
			uploadFilePath:getI('uploadFilePath'),
			contFlg:getS("contFlg"),
			//其他默认数据
			fileSvrStat:'01',
			
			//TRAN_TP:"",
			deponFileSvrId:getS("deponFileSvrId")
	}
	return data;
}
function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

function getMultiple(id){
	var arr = [];
	var value = "";
	$("#"+id+" option:selected").each(function(){
		arr.push($(this).val());
	});
	value = arr.join("|");
	return value;
}