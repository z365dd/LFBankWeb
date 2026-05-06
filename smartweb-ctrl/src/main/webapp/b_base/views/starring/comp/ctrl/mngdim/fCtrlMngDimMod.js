$(document).ready(function(){
	
//	$("#updateBtn").attr("disabled", true); 
	$("#cancelBtn").click(function(){
		cancle();
	});
	
	startJudge('updateBtn');
	cOpt('DIM_DESC');
	endJudge(update);
});

//为修改页面赋值
function setVal(Data){
	var COMP_NO = Data.split(",")[0];
	var COMP_NAME = Data.split(",")[1];
	var backS = [{label:COMP_NAME,value:COMP_NO}];
	$("select[name='COMP_NO']").multiselect('dataprovider', backS).multiselect('disable');
	
	var DIM_KEY = Data.split(",")[2];
	var DIM_DESC = Data.split(",")[3];
	var TAB_NAME = Data.split(",")[4];
	$("#DIM_KEY").val(DIM_KEY);
	$("#DIM_DESC").val(DIM_DESC);
	if(TAB_NAME!="null" && TAB_NAME!=null){
		$("#TAB_NAME").val(TAB_NAME);
	}
}


function cancle(){
	Ewin.confirm({
		title : "操作提示",
		message : "数据会清空，确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
//		parent.window.$("a[href^='#tab_list']").click();
		//返回查询页面(不重新加载)
		parent.tab1("N");
		parent.changeName("add");
	});
}

function update(){
	var COMP_NO = getS("COMP_NO");
	var DIM_KEY = $("#DIM_KEY").val();
	var DIM_DESC = $("#DIM_DESC").val();
	var TAB_NAME = $("#TAB_NAME").val();
	if(isChange()){
		$.ajax({
			type:"POST",
			url:ctx+"/comp/ctrl/oper/mngdim/FCtrlMngDimMod",
			dataType:"json",
			data:{
				COMP_NO:COMP_NO,
				DIM_KEY:DIM_KEY,
				DIM_DESC:DIM_DESC,
				TAB_NAME:TAB_NAME
			},
			success:function(data){
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
					var errMsg = "错误信息["+data.message+"]";
					showTip(errMsg,"error");
				}else{
					showTip("修改成功","success");
					//返回查询页面(重新加载)
					parent.tab1("Y");
					parent.changeName("add");
				}
			}
		});
	}
}

/**
 * 判断是否有改动到数据
 */
function isChange(){
	//页面获取的值
	var DIM_DESC = $.trim($("#DIM_DESC").val());
	//初始值
	var DIM_DESC_hide = $("#DIM_DESC_hide").val();
	console.info(DIM_DESC_hide);
	if(DIM_DESC===DIM_DESC_hide){
		showTip("数据没有修改，不可提交!", "success");
		return false
	}else{
		return true;
	}
}