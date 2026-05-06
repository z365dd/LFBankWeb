/*签约组件 一些通用的js方法*/

//下拉框通过查接口返回数据的情况(selectId:该下拉框的id)
/*加载组件号*/
function getCompNo(selectId){
	setSelect1(selectId,"/comp/sign/pub/getCompNo","COMP_NO","COMP_NAME");
}

/*加载组件号(同步)*/
function getCompNo1(selectId){
	setSelect1(selectId,"/comp/sign/pub/getCompNo","COMP_NO","COMP_NAME",false,false);
}

/*加载法人号*/
function getLegaNo(selectId){
	setSelect1(selectId,"/comp/sign/pub/getLegaNo","LEGA_NO","LEGA_NAME");
}

/*加载法人号(同步)*/
function getLegaNo1(selectId){
	setSelect1(selectId,"/comp/sign/pub/getLegaNo","LEGA_NO","LEGA_NAME",false,false);
}

/*获取业务编号下拉框(根据组件号)*/
function qryBusiNo(selectId,Val){
	var Data = {
		COMP_NO:Val
	}
	setSelect2(selectId,"/comp/sign/tec/parabusi/qryBusiNo","BUSI_NO","BUSI_NAME",Data,Val);
}

/*获取子业务编号下拉框(根据业务编号)*/
function qrySubBusiNo(selectId,Val){
	var Data = {
			BUSI_NO:Val
		}
		setSelect2(selectId,"/comp/sign/tec/signSubBusi/qrySubBusiNo","SUB_BUSI_NO","SUB_BUSI_NAME",Data,Val);
}

/*加载单位号(根据开通状态查询  ""-全部 1-开通)*/
function getEntrNoByStat(selectId){
	var Data = {
			OPEN_STAT:"1"
	}
	setSelect2(selectId,"/comp/sign/tec/signParaEntr/qry","ENTR_NO","ENTR_NAME",Data,Data.OPEN_STAT);
}


/*签约组件 测试部分  一些通用的js方法*/

//根据业务编号查询组件号
function qryCompByBusi(selectId,BUSI_NO){
	var compNo = "";
	$.ajax({
		type : "POST",
		url : ctx+"/comp/sign/tec/parabusi/paraBusiQry",
		data : {
			BUSI_NO:BUSI_NO,
			start:1,
			pageSize:10
		},
		dataType : "json",
		success : function(data) {
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var Msg = "错误信息["+data.message+"]";
				setS(selectId,compNo);
				showContent(Msg,"error");
			}else{
				var Msg = data.message; 
				var valData=data.dataSetResult[0].data[0];
				compNo = valData.COMP_NO;
				setS(selectId,compNo);
			}
		  }
	});
}

//根据单位号查询法人号
function qryLegaByEntr(selectId,entrNo){
	$.ajax({
		type : "POST",
		url : ctx+"/comp/sign/tec/signParaEntr/qry",
		data : {
			ENTR_NO:entrNo,
			start:1,
			pageSize:10
		},
		dataType : "json",
		success : function(data) {
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var Msg = "错误信息["+data.message+"]";
				setS(selectId,"");
				showContent(Msg,"error");
			}else{
				var Msg = data.message; 
				var valData=data.dataSetResult[0].data[0];
				var legaNo = valData.LEGA_NO;
				setS(selectId,legaNo);
			}
		  }
	});
}

//根据单位编号查询已签约的业务或子业务
function qryBusiByEntr(selectId,entrNo,busiNo){
	var compNo = "";
	$.ajax({
		type : "POST",
		url : ctx+"/comp/sign/tec/parabusi/paraBusiQry",
		data : {
			BUSI_NO:BUSI_NO,
			start:1,
			pageSize:10
		},
		dataType : "json",
		success : function(data) {
			if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				var Msg = "错误信息["+data.message+"]";
				setS(selectId,compNo);
				showContent(Msg,"error");
			}else{
				var Msg = data.message; 
				var valData=data.dataSetResult[0].data[0];
				compNo = valData.COMP_NO;
				setS(selectId,compNo);
			}
		  }
	});
}