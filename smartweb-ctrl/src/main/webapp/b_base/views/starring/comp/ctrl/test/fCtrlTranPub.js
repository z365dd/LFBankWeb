console.log('组件测试公共js');

/* ------下拉框加载------   */
//组件号下拉框(Ajax同步)
function getCompS(Name){
	setSelect1(Name,"/comp/ctrl/oper/switches/modelNoS","COMP_NO","COMP_NAME",false,false);
}

//查询服务码加载下拉框
function svcCodeS(Name,Val){
	var Data={
			MODL_NO:Val
		}
	setSelect2(Name,"/comp/ctrl/oper/switches/svcCodeS","SVC_CODE","SVC_DESC",Data,Val);
}

//查询子服务码加载下拉框
function subSvcS(Name,Val1,Val2){
	var Data={
			MODL_NO:Val1,
			SVC_CODE:Val2
		}
	setSelect2(Name,"/comp/ctrl/oper/switches/subSvcS","SUB_SVC_CODE","SUB_SVC_DESC",Data,Val2);
}

//加载维度下拉框
function dimKey(Name,Val){
	var Data={
			COMP_NO:Val
		}
	setSelect2(Name,"/comp/ctrl/test/tranctrlchk/qryDim","DIM_KEY","DIM_DESC",Data,Val);
}

//柜员下拉框选择
function tlrSel(Name){
	setSelect1(Name,"/comp/ctrl/test/tranctrlchk/qryTlr","OUTSTR","TLR_NO");
}

/* -----公用维度----- */
//tale修改返回数据
function reviceRow(obj,str){
	var $reviceRowData= reviceRowData(obj);
	//根据数据设置值
	if(str=='pubDim'){
		setRevicePubDim($reviceRowData);
	}else if(str=='priDim'){
		setRevicePriDim($reviceRowData);
	}else if(str=='tlrNo'){
		setReviceTlrNo($reviceRowData);
	}
}

//根据table返回的数据设置值
function setRevicePubDim(data){
	setS('PUB_DIM_KEY',data.PUB_DIM_KEY);
	setI('PUB_DIM_KV',data.PUB_DIM_KV);
	portion("addPubDim");
}
/*私有维度*/
function setRevicePriDim(data){
	setS('PRI_DIM_KEY',data.PRI_DIM_KEY);
	setI('PRI_DIM_KV',data.PRI_DIM_KV);
	portion("addPriDim");
}
/*授权柜员*/
function setReviceTlrNo(data){
	setS('TLR_NO',data.TLR);
	setI('TLR_NAME',data.TLR_NAME);
	setI('TLR_LVL',data.TLR_LVL);
	setI('BRCH',data.BRCH);
	portion("addTlr");
}

//返回ACTION 字符串
function getPubAction(){
	var modStr = "<a href=\"JavaScript:void(0);\" onClick=\"reviceRow(this" + "," + "'pubDim'"+")\">修改</a>";
	var delStr = "<a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>";
	return modStr+" "+delStr;
}
/*私有维度*/
function getPriAction(){
	var modStr = "<a href=\"JavaScript:void(0);\" onClick=\"reviceRow(this" + "," + "'priDim'"+")\">修改</a>";
	var delStr = "<a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>";
	return modStr+" "+delStr;
}
/*授权柜员*/
function getTlrAction(){
	var modStr = "<a href=\"JavaScript:void(0);\" onClick=\"reviceRow(this" + "," + "'tlrNo'"+")\">修改</a>";
	var delStr = "<a href=\"JavaScript:void(0);\" onclick='deleteRow(this)'>删除</a>";
	return modStr+" "+delStr;
}

//自动为金额输入框补上.00
function changeNum(num){  
     num += '';  
     num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符  
        
      if(/^0+/) //清除字符串开头的0  
          num = num.replace(/^0+/, '');  
     if(!/\./.test(num)) //为整数字符串在末尾添加.00  
         num += '.00';  
     if(/^\./.test(num)) //字符以.开头时,在开头添加0  
         num = '0' + num;  
     num += '00';        //在字符串末尾补零  
     num = num.match(/\d+\.\d{2}/)[0];  
     return num;
}