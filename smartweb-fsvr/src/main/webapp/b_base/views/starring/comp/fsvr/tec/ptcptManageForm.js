console.log('ptcptManageForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改 ，detail详细
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;

$(function(){
	/*返回按钮*/
	$('#closeBtn').on('click',cancel);	
	
	/*提交按钮*/
	$('#addBtn').on('click',sendData);	

});

/*关闭执行*/
function cancel(){
	if(SAVE_OR_REV=="detail"){
		parent.tab1();
	}else{
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
	
}

/*获取页面数据data*/
function getData(){
	var data= {
			//界面
			FILE_PTCPT_NO:getI('FILE_PTCPT_NO'),
			PTCPT_DESC:getI('PTCPT_DESC'),
			PTCPT_GRP_TP:getS('PTCPT_GRP_TP'),
			PORT:getI('PORT'),
			STAT:getI('STAT'),
			ENCRP_FLG:getS('ENCRP_FLG'),
			ENCRP_GRP_TP:getS('ENCRP_GRP_TP'),
			ENCRP_KEY:getI('ENCRP_KEY'),
			SIGN_FLG:getS('SIGN_FLG'),
			SIGN_GRP_TP:getS('SIGN_GRP_TP'),
			REDUCE_FLG:getS('REDUCE_FLG'),
			REDUCE_GRP_TP:getS('REDUCE_GRP_TP'),
			RESUME_FLG:getS('RESUME_FLG'),
			REDUCE_GRP_TP:"adtec.Z",
			LIM_FLG:getS('LIM_FLG'),
			SND_LIM_SIZE:getI('SND_LIM_SIZE'),
			RECV_LIM_SIZE:getI('RECV_LIM_SIZE')
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
			$url += "/comp/fsvr/tec/fileTec/ptcptAdd";
			$data.STAT = "01";
		}else if(SAVE_OR_REV=="revice"){
			$url += "/comp/fsvr/tec/fileTec/ptcptRevice";
			$data.TRAN_TP = "01";
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

//详细查询数据
function getDetail(filePtcptNo){
	$.ajax({
		url:ctx + "/comp/fsvr/tec/fileTec/ptcptDetail", 
		type:"GET",
		dataType:"json",
		data:{
			FILE_PTCPT_NO:filePtcptNo
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
				showContent(successMsg,"success");
				var Data = data.dataSetResult[0].data[0];
				setVal(Data);
			}
		}
	});
}


//设置值
function setVal(data){
	
	setI("FILE_PTCPT_NO", data.FILE_PTCPT_NO);
	setI("PTCPT_DESC", data.PTCPT_DESC);
	setS("PTCPT_GRP_TP", data.PTCPT_GRP_TP);
	setI("PORT", data.PORT);
	//setI("USER_NO", data.USER_NO);
	//setI("PWD", data.PWD);
	setI("STAT", data.STAT);
	setS("ENCRP_FLG", data.ENCRP_FLG);
	setS("ENCRP_GRP_TP", data.ENCRP_GRP_TP);
	setI("ENCRP_KEY", data.ENCRP_KEY);
	setI("ENCRP_KEY_CF", data.ENCRP_KEY);
	setS("SIGN_FLG", data.SIGN_FLG);
	setS("SIGN_GRP_TP", data.SIGN_GRP_TP);
	setS("REDUCE_FLG", data.REDUCE_FLG);
	setS("REDUCE_GRP_TP", data.REDUCE_GRP_TP);
	setS("RESUME_FLG", data.RESUME_FLG);
	setS("LIM_FLG", data.LIM_FLG);
	
	if(data.PTCPT_GRP_TP == "SDK"){
		setI("SND_LIM_SIZE", data.SND_LIM_SIZE);
		setI("RECV_LIM_SIZE", data.RECV_LIM_SIZE);
	}
	
	if(SAVE_OR_REV=="detail"){
		disDiv("formId_299274");
		$('#addBtn').hide();
	}else{
		disabledS("COMP_NO");
	}
}


