console.log('tfsvrSvrPortParaAddForm.js');
$(document).ready(function(){
	/*把新增页面内容显示出来*/
	parent.window.$("#iframe_add").show();
	setSelect1("netRegion","/comp/fsvr/tec/tfsvrSvrPara/deponNetQry","netRegion","netRegion",true,false,true);
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
	
	getCompList();
});

function submit(){
	confirmx('是否新增组件文件传输端口', function(){
		save();
	});
}
function getCompList(){
	datass = {flg:"1", start:'0', pageSize:'0'};
	setSelect4("compNo", "/prod/comp/tPipComp/list", "compNo", "compName", datass, "nulls", false, false);
}
/**
 * 保存函数--保存组件文件传输端口新增
 * @returns
 */
function save(){
	var formData = getData();
	/*向后台发送参数*/ 
	$.post(ctx + "/comp/fsvr/tec/tfsvrSvrPortPara/insert", formData,
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
		port:getI('port'),
		compNo:getS("compNo"),
		compName:getST("compNo"),
		netRegion:getS("netRegion")
	}
	return data;
}

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}