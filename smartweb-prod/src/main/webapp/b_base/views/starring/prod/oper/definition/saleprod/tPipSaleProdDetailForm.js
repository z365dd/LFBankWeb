
$(document).ready(function(){
	/*把修详情面内容显示出来*/
	parent.window.$("#iframe_detail").show();
	/*从session中拿出id*/
	id = $.session.get('id');
	/*从session中移除id*/
	$.session.remove('id');
	$("#id").val(id);

	prodLineList();
//	getCompList();
	
	/* table初始化 */
	tableClick("table");
	
	getDetail(id);
	
	/*取消按钮*/
	$('#cancleBtn').click(function(){
		cancle();
	});
	
});

function cancle(){
	parent.window.$("a[href^='#tab_list']").click();
}

/**
 * 获取产品线下拉框
 */
function prodLineList() {
	datass = {key:"", start:'0', pageSize:'0'};
	setSelect2("prodLineCode", "/prod/oper/line/tPipLineProd/list", "prodLineCode", "prodLineName", datass, "nulls", false, false);
}

/*查询明细*/
function getDetail(id){
	console.info('update tPipSaleProd info......');
	$.post(ctx + "/prod/oper/definition/saleprod/tPipSaleProd/get", {id:id}, function(data){
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			var errMsg = "错误信息["+data.message+"]"; 
			showContent(errMsg,"error");	
		}else {
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				console.info(data);
				if (data.dataSetResult[i].dataSetName == "saleAtomListDs") {
					var list = data.dataSetResult[i].data;
					$('#table').bootstrapTable('load', list);
				}else {
					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
						var jsonObj = data.dataSetResult[i].data[j];
						$('#saleProdCode').val(jsonObj.saleProdCode);	
						$('#saleProdDesc').val(jsonObj.saleProdDesc);
						$('#longRmrk').val(jsonObj.longRmrk);
						$('#brchIdName').val(jsonObj.brchIdName);
						$('#brchIdId').val(jsonObj.brchId);
						$("#prodLineCode").multiselect("select", jsonObj.prodLineCode).multiselect('rebuild');
						$('[name=url]').val(jsonObj.url);	
					}
				}
			}
			urlPreview();
			/*触发校验*/
			$('#saleProdDesc').blur();
			$('#prodLineCode').blur();

		}
	},
    "json");
}
