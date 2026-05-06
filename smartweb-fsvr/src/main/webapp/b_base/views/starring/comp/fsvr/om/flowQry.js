console.log('flowQry.js');
$(function() {
	initDate('STR_DATE', 'END_DATE');
	/*加载模型下拉框的数据*/
	setSelect3("COMP_NO","/comp/fsvr/om/qryCount/getCompNo","compNo","compName",null,false);
	
	//业务编号
	$("#companyName").click(function(){
		busiClick("companyName","BUSI_NAME");
	});
	
	
	/* 查询按钮点击进行查询 */
	$('#qryBtn').click(function() {
		if(proof()){
			freshTable('table');
		}
	});
	
});

//设置值，刷新table查询
function setVal(stat,compNo,busiNo,strDate,endDate){
	setS('FILE_TRAN_STAT',stat);
	setI('STR_DATE',dateAdd(strDate));
	setI('END_DATE',dateAdd(endDate));
	setI('companyName',busiNo);
	setS('COMP_NO',compNo);
	freshTable('table');
}


/* 查询table */
function queryParams(params) {
	var paramList = {
		pgside : 'server',// 服务器分页
		pageSize : params.limit,
		start : params.offset + 1,
		pageNo : getPage(params),
		sort : params.sort,
		order : params.order,
		// TODO
		TRAN_SEQ : getI('TRAN_SEQ'),
		FILE_TRAN_STAT : getS('FILE_TRAN_STAT'),
		STR_DATE:getDateValue("STR_DATE"),
		END_DATE:getDateValue("END_DATE"),
		COMP_NO:getS('COMP_NO'),
		BUSI_NO:getI('companyName')
	};
	return paramList;
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}


//流水详细     
function Detail(tranDate){
	parent.tab3(tranDate);
}
function setValById(id, val) {
    $('#' + id).val(val);
}
function initDate(strDateId, endDateId) {
    var date = new Date();
    var yyyy = date.getFullYear();
    var MM = date.getMonth();
    MM += 1;
    MM = MM >= 10 ? MM + '' : '0' + MM;
    var dd = date.getDate() >= 10 ? date.getDate() : '0' + date.getDate();
    var curDate = yyyy + '-' + MM + '-' + dd;
    setValById(strDateId, curDate);
    setValById(endDateId, curDate);
}


