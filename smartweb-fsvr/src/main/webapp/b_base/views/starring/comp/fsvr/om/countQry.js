console.log('countQry.js');

$(function() {
	initDate('STR_DATE', 'END_DATE');
	/*加载模型下拉框的数据*/
	setSelect3("COMP_NO","/comp/fsvr/om/qryCount/getCompNo","compNo","compName",false,false);
	//组件号change事件
	//加载模板下拉框x2
	$('#COMP_NO').on('change',compChange);
	$('#COMP_NO').change();
	
	$('#tab3').hide(); 
	
	$("#tab1").on("click",tab1);
	
	$("#tab2").on("click",tab2);

	
	/* 查询按钮点击进行查询 */
	$('#qryBtn').click(function() {
		if(proof()){
			freshTable('table');
		}
	});
	
	//业务编号
	$("#companyName").click(function(){
		busiClick("companyName","BUSI_NAME");
	});
	
	//调用方式
	$("#PTCPT_GRP_TP").change(function(){
		var type = getS("PTCPT_GRP_TP");
		var ptcptTp = "";
		var protTp = "";
		if(type == "01"){
			ptcptTp = "&PTCPT_GRP_TP=SDK";
			protTp = "&COMM_PROT_GRP_TP=SDK";
		}else if(type == "02"){
			ptcptTp = "&PTCPT_GRP_TP=FSERVER";
			protTp = "&COMM_PROT_GRP_TP=NSDK";
		}
		/*加载文件调用方下拉框的数据*/
		setSelect1("FILE_PTCPT_NO","/comp/fsvr/tec/fileTec/ptcptQry?start=0&pageSize=0"+ptcptTp,"FILE_PTCPT_NO","PTCPT_DESC");
		/*加载文件服务器下拉框的数据*/
		setSelect1("FILE_SVR_NO","/comp/fsvr/tec/fileTec/qry?start=0&pageSize=0"+protTp,"FILE_SVR_NO","SVR_DESC");
	});
});

//组件号change执行
function compChange(){
	//修改业务名称url，清空业务名称值
	if(getS('COMP_NO') != ""){
		$("#companyName").attr('search_url','/comp/fsvr/om/qryCount/busiList?compNo='+getS('COMP_NO'));
	}else{
		$("#companyName").attr('search_url','/comp/fsvr/om/qryCount/busiList');
	}
	$("#companyName").val('');
	$("#BUSI_NAME").val('');
}

//tab1点击
function tab1(){
	$('#tab3').hide();
	$('#tab1').click();
}

//tab2返回
function tab2(flag){
	$('#tab3').hide();
	if(flag==false){
		$("#tab2").off("click",tab2);
		$('#tab2').click();
		$("#tab2").on("click",tab2);
		return;
	}
	ifr('panel2','comp/fsvr/om/qryCount/flowQry');
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
		JRNL_STATS_TP : getS('JRNL_STATS_TP'),
		FILE_TRAN_STAT : getS('FILE_TRAN_STAT'),
		STR_DATE:getDateValue("STR_DATE"),
		END_DATE:getDateValue("END_DATE"),
		COMP_NO:getS('COMP_NO'),
		BUSI_NO:getI('companyName'),
		PTCPT_GRP_TP:getS('PTCPT_GRP_TP'),
		FILE_PTCPT_NO:getS('FILE_PTCPT_NO'),
		FILE_SVR_NO:getS('FILE_SVR_NO')
	};
	return paramList;
}
function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}



// 点击详细，进入流水查询界面，并赋值
function Detail(stat,compNo,busiNo,strDate,endDate){
	$('#tab2').click();
	$("#panel2").find('iframe').load(function() {
		$("#panel2").find('iframe')[0].contentWindow.setVal(stat,compNo,busiNo,strDate,endDate);
	});
}


//流水详细查询
function tab3(tranDate){
	$('#tab3').show().click();
	ifr('panel3','comp/fsvr/om/qryCount/flowDetail');
	$("#panel3").find('iframe').load(function() {
		$("#panel3").find('iframe')[0].contentWindow.getVal(tranDate);
	});
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


