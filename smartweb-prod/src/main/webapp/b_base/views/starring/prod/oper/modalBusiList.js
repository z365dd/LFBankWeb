$(document).ready(function(){
	$("#modalQryBtn").click(function(){
		//默认生成方法...
		var $preClick = $("#busiListTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
		$("#busiListTable").bootstrapTable('refresh');
	});
	
	$("#modalResetBtn").click(function(){
		clearForm('busiListForm');
		$("#modalQryBtn").click();
	});

	//双击事件
	/*$("#busiListTable").on('dbl-click-row.bs.table', function (evnet, row, $element) {
		$('#modalBusiList').modal('hide');
	});*/
	
	$('#busiSelectBtn').on('click',showBusiList);
});

function showBusiList(){
	$('#modalBusiList').modal('show');
	$('.modal-content').css('width','130%');
}


function queryParams2(params){
	var formData = $("#busiListForm").serializeObject();
	var paramList = {
		pgside : 'server',//服务器分页
		pageSize : params.limit,
		start : params.offset+1,
		pageNo : getPage2(params),
		sort : params.sort,
		order : params.order,
		busiName:getI('modalBusiName'),
	};
	return paramList;
}

function getPage2(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}