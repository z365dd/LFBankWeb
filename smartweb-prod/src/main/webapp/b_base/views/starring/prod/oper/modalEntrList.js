$(document).ready(function(){
	$("#modalQryBtn").click(function(){
		//默认生成方法...
		var $preClick = $("#entrListTable").parent().parent().find(".page-pre");
		if($preClick.siblings().length>1){
			$preClick.next().click();
		}
		$("#entrListTable").bootstrapTable('refresh');
	});
	
	$("#modalResetBtn").click(function(){
		clearForm('entrListForm');
		$("#modalQryBtn").click();
	});

	$('#entrSelectBtn').on('click',showEntrList);
});

function showEntrList(){
	$('#modalEntrList').modal('show');
	$('.modal-content').css('width','130%');
}

function queryParams2(params){
	var formData = $("#entrListForm").serializeObject();
	var paramList = {
		pgside : 'server',//服务器分页
		pageSize : params.limit,
		start : params.offset+1,
		pageNo : getPage2(params),
		sort : params.sort,
		order : params.order,
		ENTR_NAME : (typeof(formData.modalEntrName)==undefined)?'':formData.modalEntrName
	};
	return paramList;
}

function getPage2(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}