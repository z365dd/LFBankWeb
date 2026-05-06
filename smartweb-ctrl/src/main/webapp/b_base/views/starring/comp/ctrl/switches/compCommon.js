console.log('控制组件公共js');



//查询模型号加载下拉框
function modelNoS(Name){
	setSelect1(Name,"/comp/ctrl/oper/switches/modelNoS","COMP_NO","COMP_NAME",null,false);
}

//查询服务码加载下拉框
function svcCodeS(Name,Val){
	var Data={
			MODL_NO:Val
		}
	setSelect2(Name,"/comp/ctrl/oper/switches/svcCodeS","SVC_CODE","SVC_DESC",Data,Val,null,false);
}

//查询子服务码加载下拉框
function subSvcS(Name,Val1,Val2){
	var Data={
			MODL_NO:Val1,
			SVC_CODE:Val2
		}
	setSelect2(Name,"/comp/ctrl/oper/switches/subSvcS","SUB_SVC_CODE","SUB_SVC_DESC",Data,Val2,null,false);
}

//上移
/*function upSort(tableId,Num){
	console.info('上移');
	//选择的行数组
	var selRowArr  = $('#table').bootstrapTable('getSelections');
	//当前行序号
	var exctSer = selRow[0].EXCT_SER;
	var $table = $('#'+tableId);
	//所选择的行
	var $selRow = $table.find('tbody tr[class="selected"]');
	//所选择行的序号
	var selRowNo  = $selRow.find('td:eq('+Num+')').text();
	//选择行的上一行
	var $selRowPre = $table.find('tbody tr[class="selected"]').prev('tr');
	//选择行的上一行的序号
	var selRowPreNo  = $selRowPre.find('td:eq('+Num+')').text();
	
	
	
    if ($selRow.index() != 0) {
    	//选择行和上一行序号调转
    	$selRow.find('td:eq('+Num+')').text(selRowPreNo);
    	$selRowPre.find('td:eq('+Num+')').text(selRowNo);
    	
    	$selRow.fadeOut().fadeIn();
    	$selRowPre.before($selRow);
    	$table.bootstrapTable('refresh');
    }
}*/
//上移
/*function upSort(tableId,Num){
	console.info('上移');
	var $table = $('#'+tableId);
	//所选择的行数
	var $selRow = $table.find('tbody tr[class="selected"]').index();
	var $selData = $('#table').bootstrapTable('getSelections');
	if($selRow != 0){
		//先删除，再插入
		var ids = $.map($selData, function(row) {
			return row.state;
		});
		$('#' + tableId).bootstrapTable('remove', {
			field : 'state',
			values : ids
		});
		$('#' + tableId).bootstrapTable('insertRow', {index:$selRow-1, row:$selData[0]}); 
		
		获取全部数据重新设置序号
		var dateArr = $('#'+tableId).bootstrapTable('getData');
		for(var a=0;a<dateArr.length;a++){
			dateArr[a].ORDER_NO = a+1;
		}
		$('#' + tableId).bootstrapTable('load', dateArr);
	}
	
}*/
function upSort(tableId,num){
	//所选择的行序号
	var $sRowIndex = getSRowIndex(tableId);
	//首行 和 table无数据时 上移按钮无效
	if($sRowIndex != 0 && $sRowIndex != -1){
		tableUpSort(tableId);
	}
}

//下移
/*function downSort(tableId,Num){
	console.info('下移');
	var $table = $('#'+tableId);
	//所选择的行
	var $selRow = $table.find('tbody tr[class="selected"]');
	//所选择行的序号
	var selRowNo  = $selRow.find('td:eq('+Num+')').text();
	//选择行的下一行
	var $selRowNext = $table.find('tbody tr[class="selected"]').next('tr');
	//选择行的下一行的序号
	var selRowPreNo  = $selRowNext.find('td:eq('+Num+')').text();
	
	
	
	var index = $table.bootstrapTable('getData').length;
	
	if ($selRow.index() != index-1) {
		//选择行和下一行序号调转
		$selRow.find('td:eq('+Num+')').text(selRowPreNo);
		$selRowNext.find('td:eq('+Num+')').text(selRowNo);
		
		$selRow.fadeOut().fadeIn();
		$selRowNext.after($selRow);
		$table.bootstrapTable('refresh');
	}
}*/
//下移
/*function downSort(tableId,Num){
	console.info('下移');
	var $table = $('#'+tableId);
	//所选择的行
	var $selRow = $table.find('tbody tr[class="selected"]').index();
	var $selData = $('#table').bootstrapTable('getSelections');
	
	
	var index = $table.bootstrapTable('getData').length;
	
	if ($selRow != index-1) {
		//先删除，再插入
		var ids = $.map($selData, function(row) {
			return row.state;
		});
		$('#' + tableId).bootstrapTable('remove', {
			field : 'state',
			values : ids
		});
		$('#' + tableId).bootstrapTable('insertRow', {index:$selRow+1, row:$selData[0]}); 
		
		获取全部数据重新设置序号
		var dateArr = $('#'+tableId).bootstrapTable('getData');
		for(var a=0;a<dateArr.length;a++){
			dateArr[a].ORDER_NO = a+1;
		}
		$('#' + tableId).bootstrapTable('load', dateArr);
	}
	
}*/
function downSort(tableId,num){
	//table总条数
	var index = $('#'+tableId).bootstrapTable('getData').length;
	//所选择的行序号
	var $sRowIndex = getSRowIndex(tableId);
	//尾行 和 table无数据时 下移按钮无效
	if($sRowIndex != index-1 && $sRowIndex != -1){
		tableDownSort(tableId);
	}
}


