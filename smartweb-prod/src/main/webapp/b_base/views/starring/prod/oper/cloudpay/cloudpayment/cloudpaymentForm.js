console.log('cloudpaymentForm.js');

/*
 * 父页面变量SAVE_OR_REV
 * add新增，revice修改
 */
var SAVE_OR_REV = parent.SAVE_OR_REV;
//测试
/*var SAVE_OR_REV = "detail";*/

//下拉框加载 同步or异步 参数
/*var $IF_ASYNC=true;
if(SAVE_OR_REV !="add"){
	$IF_ASYNC=false;
}*/


$(function(){
	getSaleProdList("", "");
	
	//自测试
	/*var dateArr =[
        {
            "UP_LVL_PROJ_NO":"ROOT",
            "LVL_SER":"1",
            "AMT":"100",
            "PROJ_NAME":"第一层级项目1",
            "PROJ_NO":"proj1"
        },
        {
            "UP_LVL_PROJ_NO":"ROOT",
            "LVL_SER":"1",
            "AMT":"120",
            "PROJ_NAME":"第一层级项目2",
            "PROJ_NO":"proj2"
        },
        {
            "UP_LVL_PROJ_NO":"ROOT",
            "LVL_SER":"1",
            "AMT":"130",
            "PROJ_NAME":"第一层级项目3",
            "PROJ_NO":"proj3"
        },
        {
            "UP_LVL_PROJ_NO":"proj1",
            "LVL_SER":"2",
            "AMT":"210",
            "PROJ_NAME":"第二层级项目1",
            "PROJ_NO":"proj1_1"
        },
        {
            "UP_LVL_PROJ_NO":"proj1",
            "LVL_SER":"2",
            "AMT":"220",
            "PROJ_NAME":"第二层级项目2",
            "PROJ_NO":"proj1_2"
        },
        {
            "UP_LVL_PROJ_NO":"proj1_1",
            "LVL_SER":"3",
            "AMT":"310",
            "PROJ_NAME":"第三层级项目1",
            "PROJ_NO":"proj1_1_1"
        },
        {
            "UP_LVL_PROJ_NO":"proj1_1",
            "LVL_SER":"3",
            "AMT":"320",
            "PROJ_NAME":"第三层级项目2",
            "PROJ_NO":"proj1_1_2"
        }
    ];
	setData(dateArr,"111","123","111","20180914");*/
	
	//========
	/*$("#IF_AMT").change(function(){
		var LVL_NUM=0;
		//节点树数据数组
		var dataArr = []; 
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var ztreeArr =  zTree.transformToArray(zTree.getNodes());
		alert(ztreeArr);
		console.log(ztreeArr);
		//对数据进行数据转换与接口对应
		var rootTip = true;
		for(var a=0;a<ztreeArr.length;a++){
			
		}
	})*/
	
	$("#saleProdCode").on("change", function(){
		getBusiNo($(this).val());
	});
	
	//初始化ztree
	if(SAVE_OR_REV=="add"){
		intialZtree("treeDemo",null,true);
		$("#reviceMsgDiv").hide();
	}
	//增加
	$("#addNodeBtn").on("click",{id:"treeDemo",tp:"add"},actionNode);
	//确认修改
	$("#reviceNodeBtn").on("click",{id:"treeDemo",tp:"revice"},actionNode);
	
	//控制金额是否必输
	sCi("IF_AMT","AMT",["Y"]);
	
	
//	//业务编号
//	$("#BUSI_NO").click(function(){
//		busiClick("BUSI_NO","BUSI_NAME",null);
//	});

	/*返回按钮*/
	$('#cancelBtn').on('click',cancel);	
	
	/*提交按钮*/
	$('#addBtn').on('click',sendData);	
	
});

//节点树增加修改操作
function actionNode(e){
	if(portion("ztreeDataDiv")){
		var markData = e.data;
		var data={
				id:getI("PROJ_NO"),
				PROJ_NO:getI("PROJ_NO"),
				name:getI("PROJ_NO")+"-"+getI("PROJ_NAME"),
				PROJ_NAME:getI("PROJ_NAME"),
				PROJ_AMT:getI("AMT")
		}
		if("add"==markData.tp){
			addNode(markData.id,data,true);
		}else if("revice"==markData.tp){
			reviceNode(markData.id,data,true);
		}
	}
}

//节点数点击修改回显值函数
function setReviceNodeVal(data){
	setI("PROJ_NO",data.PROJ_NO);
	setI("PROJ_NAME",data.PROJ_NAME);
	if(data.PROJ_AMT==""||data.PROJ_AMT=="0"){
		setS("IF_AMT","N");
		$("#IF_AMT").change();
	}else{
		setS("IF_AMT","Y");
		$("#IF_AMT").change();
		setI("AMT",data.PROJ_AMT);
	}
}


/*关闭执行*/
function cancel(){
	parent.goTop();
	Ewin.confirm({
		title : "操作提示",
		message : "确定返回吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		parent.tab1();
	});
}


/*获取页面数据data*/
function getData(){
	//层级数量
	var LVL_NUM=0;
	//节点树数据数组
	var dataArr = [];
	
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var ztreeArr =  zTree.transformToArray(zTree.getNodes());
	//对数据进行数据转换与接口对应
	var rootTip = true;
	for(var a=0;a<ztreeArr.length;a++){
		//判断是否是根节点，是就删除
		if(rootTip==true){
			if(ztreeArr[a].ifRoot==true){
				ztreeArr.splice(a,1);
				rootTip=false;
				a-=1;
				continue;
			}
		}
		/*对调用api取出来的对象数组操作会直接影响到现在节点树上的数据
		所以放弃操作，改成取数据*/
		/*ztreeArr[a].UP_LVL_PROJ_NO = ztreeArr[a].pId;
		ztreeArr[a].LVL_SER = ztreeArr[a].level;
		if(ztreeArr[a].level>LVL_NUM){
			LVL_NUM = ztreeArr[a].level;
		}
		for(var x in ztreeArr[a]){
			if(x == "LVL_SER" || x == "PROJ_NO" || x == "PROJ_NAME" || x == "AMT" || x == "UP_LVL_PROJ_NO"){
				
			}else{
				delete ztreeArr[a][x];
			}
		}*/
		if(ztreeArr[a].level>LVL_NUM){
			LVL_NUM = ztreeArr[a].level;
		}
		var data = {
				UP_LVL_PROJ_NO:ztreeArr[a].pId,
				LVL_SER:ztreeArr[a].level,
				PROJ_NO:ztreeArr[a].PROJ_NO,
				PROJ_NAME:ztreeArr[a].PROJ_NAME,
				PROJ_AMT:ztreeArr[a].PROJ_AMT
		};
		dataArr.push(data);
	}
	var ztreeData = JSON.stringify(dataArr);
	
	//拼接lvlList
	var lvlListArr = [];
	for(var b=0;b<LVL_NUM;b++){
		var lvlData={
				LVL_SER:b+1,
				LVL_NAME:""
		}
		lvlListArr.push(lvlData);
	}
	var lvlList = JSON.stringify(lvlListArr);
	if(SAVE_OR_REV=="revice"){
		var data= {
				saleProdCode:getI("saleProdCode"),
				BUSI_NO:getI("BUSI_NO"),
				BUSI_NAME:getI("BUSI_NAME"),
				LVL_NUM:LVL_NUM,
				LVL_LIST:lvlList,
				FLG:1,
				LIST:ztreeData
		}
	}else{
		var data= {
				saleProdCode:getI("saleProdCode"),
				BUSI_NO:getI("BUSI_NO"),
				BUSI_NAME:getI("BUSI_NAME"),
				LVL_NUM:LVL_NUM,
				LVL_LIST:lvlList,
				FLG:0,
				LIST:ztreeData
	}
	
	}
	/*var data= {
			BUSI_NO:getI("BUSI_NO"),
			LVL_NUM:LVL_NUM,
			LVL_LIST:lvlList,
			LIST:ztreeData
	}*/
	return data;
}

/*提交执行*/
function sendData(){
	if(portion("needDiv")){
		var $url = ctx ;
		var $data = getData();
		
		/*if(SAVE_OR_REV=="add"){
			$url += "/mbc/gpm/oper/payment/add";
		}else if(SAVE_OR_REV=="revice"){
			$url += "/mbc/gpm/oper/payment/revice";
		}*/
		//新增修改合并为同一个接口——提交
		$url += "/prod/oper/cloudpay/payment/submit";
		$.ajax({
			url:$url, 
			type:"POST",
			dataType:"json",
			data:$data, 
			async:true,
			success:function(data) {
				if (data.returnCode !== undefined
						&& "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交["+data.message+"]"; 
					showContent(successMsg,"success");
					parent.tab1();
				}
			}
		});
	}
}


//设置值
function setData(DataArr,busiNo,busiName,modTlrNo,modDt){
	console.log(DataArr);
	
	getSaleProdList(DataArr[0].SALE_PROD_CODE, DataArr[0].BUSI_NO);
	
//	setI('BUSI_NAME',DataArr[0].BUSI_NAME);
	setI('MOD_TLR_NO',DataArr[0].MOD_TLR_NO);
	setI('MOD_DT',DataArr[0].MOD_DT);
	
	//转换字段，ztree加载需要
	for(var a=0;a<DataArr.length;a++){
		/*if(DataArr[a].UP_LVL_PROJ_NO==""){
			DataArr[a].pId=null;
		}else{
			DataArr[a].pId=DataArr[a].UP_LVL_PROJ_NO;
		}*/
		DataArr[a].pId=DataArr[a].UP_LVL_PROJ_NO;
		DataArr[a].id=DataArr[a].PROJ_NO;
		DataArr[a].name=DataArr[a].PROJ_NO+"-"+DataArr[a].PROJ_NAME;
		/*DataArr[a].LVL_SER = DataArr[a].LVL_SER+"";
		DataArr[a].AMT = DataArr[a].AMT+"";*/
	}
	
	
	//初始化ztree
	intialZtree("treeDemo",DataArr,true);
	
	if(SAVE_OR_REV=="detail"){
		$("#btnDiv").hide();
		$("#addBtn").hide();
		//ztree不显示删除按钮
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.setting.edit.showRemoveBtn = false;
		disabledI("BUSI_NO");
		disDiv("ztreeDataDiv");
	}else if (SAVE_OR_REV=="revice"){
		disabledI("BUSI_NO");
	}
	
}


function getSaleProdList(SALE_PROD_CODE, BUSI_NO) {
	var datass = {compNo:"999301", start:'0', pageSize:'0'};
	setSelect2("saleProdCode", "/prod/oper/cloudpay/pay/listSaleProd", "saleProdCode", "saleProdDesc", datass, "nulls", false, false);

	if (SALE_PROD_CODE != "") {
		$("#saleProdCode").multiselect("select", SALE_PROD_CODE).multiselect('rebuild');
	}
	
	getBusiNo(SALE_PROD_CODE, BUSI_NO);
}

function getBusiNo(saleProdCode, BUSI_NO) {
	var datass = {saleProdCode:saleProdCode, start:'0', pageSize:'0'};
	setSelect2("BUSI_NO", "/prod/oper/cloudpay/pay/listBusiNo", "busiNo", "text", datass, "nulls", false, false);
	if (BUSI_NO != "") {
		$("#BUSI_NO").multiselect("select", BUSI_NO).multiselect('rebuild');
	}
}

