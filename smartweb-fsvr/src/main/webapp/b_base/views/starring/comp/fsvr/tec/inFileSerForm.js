console.log('inFileSerForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改 ，detail详细
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;
/*状态*/
var STAT = "01";

$(function(){
	/*返回按钮*/
	$('#closeBtn').on('click',cancel);	
	
	/*提交按钮*/
	$('#addBtn').on('click',sendData);	
	
	setSelect1("DEPON_FILE_SVR_ID","/comp/fsvr/tec/fileTec/qry?FILE_SVR_TP=A1&start=1&pageSize=999","FILE_SVR_ID","SVR_DESC",null,false);
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
			IP:getI('IP'),
			PORT:getI('PORT'),
			DOWNLOAD_PATH:getI('DOWNLOAD_PATH'),
			OPEN_SVC_FLG:getS("OPEN_SVC_FLG"),
			SVR_DESC:getI("SVR_DESC"),
			//其他默认数据
			FILE_SVR_ID:getI('IP'),
			FILE_SVR_TP:"A1",
			COMM_PROT_TP:"Fserver",
			USER_NO:"",
			PWD:"",
			UPLOAD_PATH:getI('DOWNLOAD_PATH'),
			STAT:STAT,
			MEMB_ID:getI("MEMB_ID"),
			TRAN_TP:"",
			CONT_FLG:0
			//DEPON_FILE_SVR_ID:getMultiple("DEPON_FILE_SVR_ID")
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
			$url += "/comp/fsvr/tec/fileTec/add";
		}else if(SAVE_OR_REV=="revice"){
			$url += "/comp/fsvr/tec/fileTec/revice";
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
function getDetail(fileSvrNo){
	$.ajax({
		url:ctx + "/comp/fsvr/tec/fileTec/getDetail", 
		type:"GET",
		dataType:"json",
		data:{
			FILE_SVR_ID:fileSvrNo
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
	setI("IP",data.IP);
	setI("PORT",data.PORT);
	setI("DOWNLOAD_PATH",data.DOWNLOAD_FILE_PATH);
	setS("OPEN_SVC_FLG",data.OPEN_SVC_FLG);
	setI("SVR_DESC",data.SVR_DESC);
	STAT = data.STAT;
	setI("MEMB_ID",data.MEMB_ID);
	if(data.LIST!=undefined){
		//setExchangeMultiple("DEPON_FILE_SVR_ID",data.LIST)
	}

	if(SAVE_OR_REV=="detail"){
		disDiv("formId_299274");
		$('#addBtn').hide();
	}else if(SAVE_OR_REV=="revice"){
		disabledI("IP");
	}
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

function setMultiple(id,str){
if(str==undefined){
	return;
}
var arr = [];
arr = str.split("|");
$("#"+id).multiselect('select',arr);
}

function setExchangeMultiple(id,str){
	var arrStr = str.replace("\\","");
	var json = JSON.parse(arrStr)
	var arr=[];
	if(json!=undefined&&json.length!=0){
		for(var i=0;i<json.length;i++){
			arr.push(json[i].DEPON_FILE_SVR_ID);
		}
	}
	console.log(arr);
	$("#"+id).multiselect('select',arr);
	
}
