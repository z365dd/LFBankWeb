$(function(){
	
	/*加载模型下拉框的数据*/
	setSelect1("COMP_NO","/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME");
	
	/*点击取消按钮 不保存页面数据并 返回主页面*/
	$("#cancelBtn").click(function(){
		cancle();
	});
	
	/*点击确认按钮 */
	$("#addBtn").click(function(){
		dimAdd();
	});
	/*startJudge('addBtn');
	cOpt('COMP_NO');
	cOpt('DIM_KEY');
	cOpt('DIM_DESC');
	endJudge(dimAdd);*/
	
	
	/**/
	$("#DIM_KEY").blur(function(){
		var DIM_KEY = $("#DIM_KEY").val();
		if($.trim(DIM_KEY)==""){
			$("#TAB_NAME").removeAttr("disabled");
		}
	});
});

/*function qryDimTable(){
	var DIM_KEY = $("#DIM_KEY").val();
	$.ajax({
		url:ctx+"/comp/ctrl/oper/mngdim/getDim",
		type:"POST",
		dataType:"json",
		data:{
			DIM_KEY:DIM_KEY,
		},
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var errMsg = "错误信息["+data.message+"]";
				showTip(errMsg,"error");
			}else{
				var valData = data.dataSetResult[0].data;
				valData = eval(valData);
				$("#TAB_NAME").val(valData[0].TAB_NAME);
				$("#TAB_NAME").attr("disabled",true);
			}
		}
	});
}*/

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

function dimAdd(){
	if(proof()){
		var COMP_NO = getS("COMP_NO");
		var DIM_KEY = $("#DIM_KEY").val();
		var DIM_DESC = $("#DIM_DESC").val();
		var TAB_NAME = $("#TAB_NAME").val();
		$.ajax({
			url:ctx+"/comp/ctrl/oper/mngdim/FCtrlMngDimAdd",
			type:"POST",
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
					showTip("添加成功!","success");
//					parent.window.$("a[href^='#tab_list']").click();
					//返回查询页面(重新加载)
					parent.tab1("Y");
				}
			}
		});
	}
}

function checkAddForm(){
	return true;
}

