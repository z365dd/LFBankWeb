console.log('portManageForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改 ，detail详细
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;

$(function(){
	/*加载模型下拉框的数据*/
	//setSelect1("COMP_NO","/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME",null,false);
	
	/*返回按钮*/
	$('#closeBtn').on('click',cancel);	
	
	/*提交按钮*/
	$('#addBtn').on('click',sendData);	

});

/*关闭执行*/
function cancel(){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		parent.tab1();
	});
}

/*获取页面数据data*/
function getData(){
	var data= {
			//界面
			COMP_NO:getS('COMP_NO'),
			DEPON_NET_REGION:getS('DEPON_NET_REGION'),
			COMP_NAME:getST('COMP_NO'),
			PORT:getI('PORT')
	}
	return data;
}

/*提交执行*/
function sendData(){
	if(proof()){
		var $url = ctx ;
		var $data = getData();
		var $ifasync = true;
		
		if(SAVE_OR_REV=="add"){
			$url += "/comp/fsvr/tec/fileTec/portAdd";
		}else if(SAVE_OR_REV=="revice"){
			$url += "/comp/fsvr/tec/fileTec/portRevice";
		}
		$.ajax({
			url:$url, 
			type:"POST",
			dataType:"json",
			data:$data, 
			async:$ifasync,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交["+data.message+"]"; 
					showContent(successMsg,"success");
					parent.tab1();
				}
			}
		});
	}
}


//设置值
function setVal(compNo,port){
	setS("COMP_NO",compNo);
	setI("PORT",port);
	//setExchangeMultiple("DEPON_NET_REGION",data.LIST)
	if(SAVE_OR_REV=="detail"){
		disDiv("formId_299274");
		$('#addBtn').hide();
	}else{
		disabledS("COMP_NO");
	}
}


