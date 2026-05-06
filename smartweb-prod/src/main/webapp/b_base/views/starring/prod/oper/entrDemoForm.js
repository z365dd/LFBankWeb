console.log('entrDemoForm1');



// 从父页面获取全局变量
// add新增 revice修改 detial详细
var SAVE_OR_REV = parent.SAVE_OR_REV;

// 页面同步加载还是异步加载下拉框标志
var IF_ASYNC = false;
if (SAVE_OR_REV == "add") {
	IF_ASYNC = true;
	getEntrNo();
} else {
	SET_DATA_NOW = true;
}

$(function () {
	console.log('entrDemoForm2');
    setSelect1("province", "/prod/oper/entrDemo/area?parentId=1", "id", "name", undefined, false);
    if (SAVE_OR_REV == 'add') {
        //设置默认河北省
        setS("province", '38');
        setSelect1("city", `/prod/oper/entrDemo/area?parentId=${getS('province')}`, "name", "name", undefined, false);
    }
    $("#province").on("change", function (e) {
        setSelect1("city", `/prod/oper/entrDemo/area?parentId=${getS('province')}`, "name", "name", undefined, false);
    });
    //setSelect1 p1-组件名 p2-url p3-下拉框实际取值(返回对象的对应key，在提交表单时使用) p4-下拉框展示值（返回对象的对应key） p5-是否去重 p6-默认使用undefined p7-默认false
    setSelect1("busiTypeId", `/prod/oper/entrDemo/getBusiType`, "busiTypeId","busiTypeName",false ,undefined);

    //$("#ENTR_NO_DIV").hide();
    //初始默认值
    //签约
    setB("SIGN_FLG", '0');
    //清算
    setB('CLR_TP', '0');
    //手续费
    setB('FEE_TP', '0');
    //对账
    setS('CHK_TP', '0');


    ifArrow(0);
    $("#leftGo").on("click", {
        tp: "left"
    }, goLR);
    $("#rightGo").on("click", {
        tp: "right"
    }, goLR);

	/*signMsg业务签约信息*/
	// 渠道
	//setSelect1("CHNL_NO", "/comp/ctrl/oper/channel/getChnl", "CHNL_NO","CHNL_NAME", undefined, false); 2020年1月6日17:46:38
	// 是否签约控制div
	rCd("SIGN_FLG", "signCommonDiv", [ "1" ]);
	rCd("SIGN_FLG", "signConDiv", [ "1" ]);
	rCd("SIGN_FLG", "signTableDiv", [ "1" ]);
	
	rCd("SIGN_FLG", "signTpDiv", [ "1" ]);
	rCd("SIGN_FLG", "signConDiv", [ "1" ]);
	$("[name='SIGN_FLG']").change(function() {
		$("[name='SIGN_TP']").change();
	});
	// 签约类型控制限额信息
	rCd("SIGN_TP", "ifCustDiv", [ "02", "03" ]);
	rCd("SIGN_TP", "limitSetDiv", [ "02", "03" ]);
	/* tableSign1（签约内嵌table）初始化 */
	tableClick("tableSign1");
	/* 增加btn，tableSign1增加一行 */
	$('#addRowBtnSign1').click(function() {
		tableActionRowSign('tableSign1', "add", "sign1");
	});
	/* tableSign1确认修改 */
	$('#reviceRowBtnSign1').click(function() {
		tableActionRowSign('tableSign1', "revice", "sign1");
	});
	/* tableSign2（签约总table）初始化 */
	tableClick("tableSign2");
	/* 增加btn，tableSign2增加一行 */
	$('#addRowBtnSign2').click(function() {
		tableActionRowSign('tableSign2', "add", "sign2");
	});
	/* tableSign2确认修改 */
	$('#reviceRowBtnSign2').click(function() {
		tableActionRowSign('tableSign2', "revice", "sign2");
	});

	// tab3
	//清算类型控制全部
	rCd("CLR_TP", "clrComDiv",  ["1"]);
	rCd("CLR_TP", "clrAttrDiv",  ["1"]);
	rCd("CLR_TP", "table3Div",  ["1"]);
	$("[name='CLR_TP']").change(function(){
		$("[name='CLR_METH']").change();
	});
	
	
	//清算标志控制清算参数非公共部分
	//不清算非公共部分不必输
	rCd("CLR_METH", "feeDiv0",  ["1","2"]);
	// 清算标志控制手续费公共部分
	//本金和手续费统一清算，清算公共部分必输
	rCd("CLR_METH", "feeDiv1",  [ "2" ]);
	$("[name='CLR_METH']").change(function(){
		$("[name='FEE_CALT_FLG3']").change();
	});
	//手续费计算方式控制手续费信息
	rCd("FEE_CALT_FLG3", "feeDiv2", ["1"]);

	/* table3初始化 */
	tableClick("table3");
	/* 增加btn，table3增加一行 */
	$('#addRowBtn3').click(function() {
		tableActionRow3('table3', "add");
	});
	/* table3确认修改 */
	$('#reviceRowBtn3').click(function() {
		tableActionRow3('table3', "revice");
	});

	// tab4
	
	//手续费类型
	rCd("FEE_TP", "feeComDiv",  ["1"]);
	rCd("FEE_TP", "table4Div",  ["1"]);
	$("[name='CLR_TP']").change(function(){
		$("[name='FEE_CLR_METH4']").change();
	});
	
	//手续费清算方式控制手续费清算参数
	rCd("FEE_CLR_METH4", "feeMsgMin",  ["1"]);
	/* table4初始化 */
	tableClick("table4");
	/* 增加btn，table3增加一行 */
	$('#addRowBtn4').click(function() {
		tableActionRow4('table4', "add");
	});
	/* table4确认修改 */
	$('#reviceRowBtn4').click(function() {
		tableActionRow4('table4', "revice");
	});

	// tab5
	//对账模型控制对账信息
	sCd("CHK_TP", "chkDiv", ["1","2"]);
	$("[name='CHK_TP']").change(function(){
		$("[name='MONTH_END_FLG']").change();
		$("[name='QUARTER_END_FLG']").change();
		$("[name='YEAR_END_FLG']").change();
	});
	// 月季年
	$('[name="MONTH_END_FLG"]').change(function() {
		bChecked("MONTH_END_FLG", "Y", "N");
	});
	rCi("MONTH_END_FLG", "MONTH_END_STR_TIME", "Y");
	rCi("MONTH_END_FLG", "MONTH_END_END_TIME", "Y");

	$('[name="QUARTER_END_FLG"]').change(function() {
		bChecked("QUARTER_END_FLG", "Y", "N");
	});
	rCi("QUARTER_END_FLG", "QUARTER_END_STR_TIME", "Y");
	rCi("QUARTER_END_FLG", "QUARTER_END_END_TIME", "Y");

	$('[name="YEAR_END_FLG"]').change(function() {
		bChecked("YEAR_END_FLG", "Y", "N");
	});
	rCi("YEAR_END_FLG", "YEAR_END_STR_TIME", "Y");
	rCi("YEAR_END_FLG", "YEAR_END_END_TIME", "Y");

	
	
	
	$('#addBtn').on("click", {
		tp : "effect"
	}, Save);
	$('#cancelBtn').on("click", cancel);
	
	// ystep点击事跳转
	//$(".ystep2").on("click", "li", ystepJump);
});

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

//判断显示隐藏左右移动箭头
function ifArrow(num) {
	if (num > 4) {
		//动态可售产品业务配置大于4显示左右移动箭头
		$("#leftGo").show();
		$("#rightGo").show();
	} else {
		//隐藏左右移动箭头，进度条位置初始化
		$("#leftGo").hide();
		$("#rightGo").hide();
		$("#myStep").stop().animate({
			"left" : "0px"
		}, 200);
	}
}

/*
 * 页面控制，上一步、下一步、取消
 */
// 页面跳转操作类
function StepJump() {
};
// 类方法，类方法指向此类，因此不会受调用方的影响（on加事件的时候）
// 下一步
StepJump.nextStep = function() {
	// 回调stepCallBackFunc进度条改变
	$(".ystep2").nextStep(stepCallBackFunc);
	parent.goTop();
	$(window.parent.document).find("#panel2").find('iframe').height(3000);
};
// 上一步
StepJump.backStep = function() {
	// 回调stepCallBackFunc进度条改变
	$(".ystep2").prevStep(stepCallBackFunc);
	parent.goTop();
};
// 跳转到指定步骤页
StepJump.step = function(num) {
	// 回调stepCallBackFunc进度条改变，判断加载业务编号下拉框
	$(".ystep2").setStep(num, stepCallBackFunc);
	$(window.parent.document).find("#panel2").find('iframe').height(3000);
}
// 方法的原型对象
// 多加的导航arr，导航初始位置step
StepJump.prototype = {
	// 初始化步骤
	initStep : function(arr, step) {
		$(".ystep2").empty();
		var dataArr = [];
		if (arr == undefined) {
			dataArr = [ {
				// 步骤名称
				title : "单位基本信息",
				stepIcon : 'glyphicon glyphicon-leaf'
			// 步骤内容(鼠标移动到本步骤节点时，会提示该内容)
			}, {
				title : "业务签约信息",
				stepIcon : 'glyphicon glyphicon-leaf'
			}, {
				title : "单位清算信息",
				stepIcon : 'glyphicon glyphicon-leaf'
			}, {
				title : "单位手续费配置",
				stepIcon : 'glyphicon glyphicon-leaf'
			}, {
				title : "单位对账信息",
				stepIcon : 'glyphicon glyphicon-leaf'
			}, {
				title : "预览",
				stepIcon : 'glyphicon glyphicon-leaf'
			} ];
		} else {
			dataArr = arr;
		}
		// 根据jQuery选择器找到需要加载ystep的容器
		// loadStep 方法可以初始化ystep
		$(".ystep2").loadStep({
			// ystep的外观大小
			// 可选值：small,large
			size : "large",
			// ystep配色方案
			// 可选值：green,blue
			color : "lightblue",
			// ystep中包含的步骤
			steps : dataArr,
			/* filterSteps:filters */
			// 自定义参数，初始步骤位置
			firstStep : step,
		});
		// ystep插件与bootstrpTable的tab相链接
		$(".ystep2").bindTabs('tranConfig_tab');
	},
	// 下一步
	next : function() {
		//顶层内容块
		var $nowPage = $(this).parents(".tab-pane").eq(0);
		// 当前页面的id
		var nowPageId = $nowPage.attr("id");
		
		// 预览页面
		if (nowPageId == "chkMsg") {
			//如果为最后一个对账配置页面，进行预览界面加载
			initPreview();
		}
		StepJump.nextStep();
	},
	// 上一步
	back : function() {
		StepJump.backStep();
	},
	// 取消操作
	cancel : function(e) {
		var num = 0;
		if (e.data != undefined) {
			num = e.data.num;
		}
		if (num == 6) {
			//预览界面取消全部
			resetForm('contentMain', "noDisabled");
			StepJump.step(1);
		} else {
			var nowId = $(this).parents(".tab-pane").attr("id");
			resetForm(nowId, "noDisabled");
		}
		$(window.parent.document).scrollTop(0);
	},
}
//页面跳转操作对象
var stepJump = new StepJump();


// 加载预览页面
function initPreview() {
	console.info("预览信息");

	$('#detail1').empty();
	// 里面存了table不需要清除
	// $('#detail3').empty();
	// $('#detail4').empty();
	$('#detail5').empty();

	var $label = '<label for="inputEmail3" class="col-sm-2"></label>';

	// tab1
	var $ENTR_NO1 = $label + '<span>单位编号：' + getI('ENTR_NO1') + '</span>';
	var $ENTR_NAME = $label + '<span>单位名称：' + getI('ENTR_NAME') + '</span>';
	var $ENTR_TP = $label + '<span>单位类型：' + getST('ENTR_TP') + '</span>';
	var $ENTR_TMPL_NO = $label + '<span>单位模板：' + getST('ENTR_TMPL_NO')
			+ '</span>';
	var $ENTR_NATURE = $label + '<span>单位性质：' + getST('ENTR_NATURE')
			+ '</span>';
	var $ENTR_CERT_TP = $label + '<span>单位证件类型：' + getST('ENTR_CERT_TP')
			+ '</span>';
	var $ENTR_CERT_NO = $label + '<span>单位证件号码：' + getI('ENTR_CERT_NO')
			+ '</span>';

	var $LEGA_CERT_TP = $label + '<span>法人证件类型：' + getST('LEGA_CERT_TP')
			+ '</span>';
	var $LEGA_CERT_NO = $label + '<span>法人证件号码：' + getI('LEGA_CERT_NO')
			+ '</span>';
	var $LEGA_NAME = $label + '<span>法人名称：' + getI('LEGA_NAME') + '</span>';
	var $ENTR_ADDR = $label + '<span>地址：' + getI('ENTR_ADDR') + '</span>';
	var $ENTR_TEL_NO = $label + '<span>办公电话：' + getI('ENTR_TEL_NO') + '</span>';
	var $EMAIL = $label + '<span>E-MAIL：' + getI('EMAIL') + '</span>';
	var $CTCT_PER_NAME = $label + '<span>联系人：' + getI('CTCT_PER_NAME')
			+ '</span>';
	var $CTCT_PHONE_NO = $label + '<span>联系电话：' + getI('CTCT_PHONE_NO')
			+ '</span>';

	var $baseRow1 = $('<div>', {
		'ravo' : 'rainbow_fx_layout',
		'class' : 'row clearfix',
		'style' : 'margin-left:20px;'
	}).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_NO1)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_NAME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_TP)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_TMPL_NO)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_NATURE)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_CERT_TP)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_CERT_NO)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($LEGA_CERT_TP)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($LEGA_CERT_NO)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($LEGA_NAME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_ADDR)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ENTR_TEL_NO)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($EMAIL)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CTCT_PER_NAME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CTCT_PHONE_NO));
	$('#detail1').append($baseRow1);

	

	// tab3
	$('#tableDetail3').bootstrapTable('load',
			$('#table3').bootstrapTable('getData'));
	$('#tableDetail3').bootstrapTable('resetView');

	// tab4
	$('#tableDetail4').bootstrapTable('load',
			$('#table4').bootstrapTable('getData'));
	$('#tableDetail4').bootstrapTable('resetView');

	// tab5
	var $CHK_DIM_TP = $label + '<span>对账维度：' + getST('CHK_DIM_TP') + '</span>';
	var $CONT_FLG = $label + '<span>下周期对账：' + getST('CONT_FLG') + '</span>';
	var $CHK_TP = $label + '<span>对账模型：' + getST('CHK_TP') + '</span>';
	var $CHK_SND_GRP_FLG = $label + '<span>对账发起方式：' + getBT('CHK_SND_GRP_FLG')
			+ '</span>';
	var $ASYNC_FLG = $label + '<span>柜面对账模式：' + getBT('ASYNC_FLG') + '</span>';

	var $CHK_CYC = $label + '<span>对账周期：' + getI("CHK_CYC") + '</span>';
	var $MONTH_END_FLG = $label + '<span>处理标志：' + getBT("MONTH_END_FLG")
			+ getBT("QUARTER_END_FLG") + getBT("YEAR_END_FLG") + '</span>';
	var $MONTH_END_STR_TIME = $label + '<span>月末开始时间：'
			+ getI("MONTH_END_STR_TIME") + '</span>';
	var $MONTH_END_END_TIME = $label + '<span>月末结束时间：'
			+ getI("MONTH_END_END_TIME") + '</span>';
	var $QUARTER_END_STR_TIME = $label + '<span>季末开始时间：'
			+ getI("QUARTER_END_STR_TIME") + '</span>';
	var $QUARTER_END_END_TIME = $label + '<span>季末结束时间：'
			+ getI("QUARTER_END_END_TIME") + '</span>';
	var $YEAR_END_STR_TIME = $label + '<span>年末开始时间：'
			+ getI("YEAR_END_STR_TIME") + '</span>';
	var $YEAR_END_END_TIME = $label + '<span>年末结束时间：'
			+ getI("YEAR_END_END_TIME") + '</span>';

	var $STR_TIME5 = $label + '<span>清算时间（起始）：' + getI('STR_TIME5') + '</span>';
	var $END_TIME5 = $label + '<span>清算时间（结束）：' + getI('END_TIME5') + '</span>';
	var $CHK_RSLT_PUSH_FLG = $label + '<span>对账文件获取/推送模式：'
			+ getBT('CHK_RSLT_PUSH_FLG') + '</span>';
	var $CHK_FILE_NAME = $label + '<span>对账文件格式：' + getI('CHK_FILE_NAME')
			+ '</span>';

	var $baseRow5 = $('<div>', {
		'ravo' : 'rainbow_fx_layout',
		'class' : 'row clearfix',
		'style' : 'margin-left:20px;'
	}).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CHK_DIM_TP)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CONT_FLG)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CHK_TP)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CHK_SND_GRP_FLG)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($ASYNC_FLG)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CHK_CYC)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($MONTH_END_FLG)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($MONTH_END_STR_TIME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($MONTH_END_END_TIME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($QUARTER_END_STR_TIME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($QUARTER_END_END_TIME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($YEAR_END_STR_TIME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($YEAR_END_END_TIME)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($STR_TIME5)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($END_TIME5)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CHK_RSLT_PUSH_FLG)).append($('<div>', {
		'class' : 'column col-md-6',
		'style' : 'padding:10px;'
	}).append($CHK_FILE_NAME));

	$('#detail5').append($baseRow5);

	// 变蓝
	previewPage("preview");
}

/*
 * 进度条
 */
// ystep点击跳转对应页面，并控制进度条的移动
// 如果超过当前进度条1则没反应，超过1则触发下一步事件（要校验），前面则进行跳转
function ystepJump() {
	// 当前点击的进度位置
	var num = $(this).index();
	// 当前页面位置
	var nowNum = $(".ystep2").getStep() - 1;
	// 差值
	var differNum = num - nowNum;
	if (differNum <= 0) {
		StepJump.step(num + 1);
	} else if (differNum == 1) {
		$("#contentMain").children().eq(nowNum).find("[name*=nextBtn]").click();
	} else {
		return;
	}
}

// 页面发生跳转，进度条改变回调 总事件
function stepCallBackFunc() {
	// 进度条
	ystepLR();
}

// （进度条改变回调）控制进度条的左右移动
function ystepLR() {
	// 点击第几个进行移动
	var moveNum = 5;
	// 进度条节点总个数
	var liLength = $(".ystep2").find("li").length;
	if (liLength > 10) {
		// 与10的差距
		var differW10 = liLength - 10;
		// 当前激活的节点位置
		var activeLiNum = $(".ystep2").find("li.ystep-step-active").index() + 1;
		if (activeLiNum < 5) {
			// 回到最左
			$("#myStep").animate({
				"left" : "0"
			}, 200);
		} else if (activeLiNum >= differW10 + 5 - 1) {
			// 移到最右
			$("#myStep").stop().animate({
				"left" : "-" + differW10 * 100 + "px"
			}, 200);
		} else {
			// 中间一段部分移动
			$("#myStep").stop().animate({
				"left" : "-" + (activeLiNum - 5 + 1) * 100 + "px"
			}, 200);
		}
	} else {
		return;
	}
}
// 左右箭头点击移动
function goLR(e) {
	var tp = e.data.tp;

	// 进度条节点总个数
	var liLength = $(".ystep2").find("li").length;
	// 与10的差距
	var differW10 = liLength - 10;
	// 当前left绝对值
	var leftNow = Math.abs(parseInt($("#myStep").css("left")));

	if (tp == "left") {
		if (leftNow == 0) {
			showTip("已到头", "success");
			$("#myStep").stop().animate({
				"left" : "0px"
			}, 200);
		} else {
			if (leftNow - 100 >= 0) {
				$("#myStep").stop().animate({
					"left" : "-" + (leftNow - 100) + "px"
				}, 200);
			} else {
				$("#myStep").stop().animate({
					"left" : "0px"
				}, 200);
			}
		}
	} else if (tp == "right") {
		if (leftNow >= 100 * differW10) {
			showTip("已到尾", "success");
			$("#myStep").stop().animate({
				"left" : "-" + (100 * differW10) + "px"
			}, 200);
		} else {
			if (100 * differW10 - leftNow >= 100) {
				$("#myStep").stop().animate({
					"left" : "-" + (leftNow + 100) + "px"
				}, 200);
			} else {
				$("#myStep").stop().animate({
					"left" : "-" + (100 * differW10) + "px"
				}, 200);
			}
		}
	}
}


/*
 * signMsg业务签约信息
 */
// 页面取值，tableSign新增或修改行
function tableActionRowSign(tableId, ifAddRevice, tp) {
	var Data;
	if (tp == "sign1") {
		if (!portion("tableSignDiv1")) {
			return;
		}
		Data = {
			CHNL_NO : getS("CHNL_NO"),
			LIM_FLG : getS("LIM_FLG"),
			LIM_FLG_STR : getST("LIM_FLG"),
			LIM_NUM : getI('LIM_NUM'),
			LIM_AMT : getI('LIM_AMT'),
			ACTION : "<a onclick='reviceRowSign(this,\"1\")'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
		}
	} else if (tp == "sign2") {
		if (!portion("signConDiv") || !portion("signCommonDiv")) {
			return;
		}
		var rowArr = $('#tableSign1').bootstrapTable('getData');
		if (getB("SIGN_TP") == "02" || getB("SIGN_TP") == "03") {
			if (rowArr.length == 0) {
				showTip("请先增加限额信息", "success");
				return;
			}
		}
		var row = JSON.stringify(rowArr);
		Data = {
			//BUSI_NO : getS("BUSI_NO_SIGN").replace(/[;]/g, "</br>"),
			//BUSI_NAME : getST('BUSI_NO_SIGN').replace(/[;]/g, "</br>"),
			SIGN_FLG_STR : getBT('SIGN_FLG'),
			SIGN_FLG : getB('SIGN_FLG'),
			RULE_NAME : getI("RULE_NAME"),

			SIGN_TP : getB('SIGN_TP'),
			CUST_DEF_LIM_FLG : getB('CUST_DEF_LIM_FLG'),
			LIMIT_SET : row,

			ACCT_STAT_LIST : getS("ACCT_STAT_LIST", "|"),
			VCH_STAT_LIST : getS("VCH_STAT_LIST", "|"),
			VRFY_ENTR_CUST_FLG : getB("VRFY_ENTR_CUST_FLG"),
			VRFY_PER_CUST_FLG : getB("VRFY_PER_CUST_FLG"),
			VRFY_ACCT_NAME_FLG : getB("VRFY_ACCT_NAME_FLG"),
			VRFY_CERT_FLG : getB("VRFY_CERT_FLG"),
			VRFY_PHONE_FLG : getB("VRFY_PHONE_FLG"),
			VRFY_ACCT_CARD_FLG : getB("VRFY_ACCT_CARD_FLG"),
			VRFY_MOD_BRCH_FLG : getB("VRFY_MOD_BRCH_FLG"),
			VRFY_CANCL_BRCH_FLG : getB("VRFY_CANCL_BRCH_FLG"),
			OTH_CUST_SIGN_FLG : getB("OTH_CUST_SIGN_FLG"),

			ACTION : "<a onclick='reviceRowSign(this,\"2\")'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
		}
	}
	if (ifAddRevice == "add") {
		// table增加行
		tableAddRow(tableId, Data);
	} else if (ifAddRevice == "revice") {
		// 修改行
		tableReviceRow(tableId, Data);
	}
}

// tableSign获取选中行数据，回显
function reviceRowSign(obj, tp) {
	var $reviceRowData = reviceRowData(obj);
	// 根据数据设置值
	setReviceRowDataSign($reviceRowData, tp);
}

// 根据数据回显页面值
function setReviceRowDataSign(data, tp) {
	if (tp == "1") {
		setS("CHNL_NO", data.CHNL_NO);
		setS("LIM_FLG", data.LIM_FLG);
		setI("LIM_NUM", data.LIM_NUM);
		setI("LIM_AMT", data.LIM_AMT);
	} else if (tp == "2") {
		/*if(undefined!=data.BUSI_NO){
			setS("BUSI_NO_SIGN", data.BUSI_NO.replace(/[<\/br>]/g, ";"));
		}*/
		// setS("BUSI_NAME", data.BUSI_NAME);
		setB("SIGN_FLG", data.SIGN_FLG);
		$("[name='SIGN_FLG']").change();
		setI("RULE_NAME", data.RULE_NAME);

		setB("SIGN_TP", data.SIGN_TP);
		$("[name='SIGN_TP']").change();
		setB("CUST_DEF_LIM_FLG", data.CUST_DEF_LIM_FLG);
		var tableArr = [];
		if (data.LIMIT_SET != "" && data.LIMIT_SET != undefined
				&& data.LIMIT_SET != null && typeof(data.LIMIT_SET)=="string") {
			tableArr = JSON.parse(data.LIMIT_SET);
		}else{
			tableArr = data.LIMIT_SET;
		}
		$('#tableSign1').bootstrapTable('load', tableArr);

		setS("ACCT_STAT_LIST", data.ACCT_STAT_LIST, "|");
		setS("VCH_STAT_LIST", data.VCH_STAT_LIST, "|");
		setB("VRFY_ENTR_CUST_FLG", data.VRFY_ENTR_CUST_FLG);
		setB("VRFY_PER_CUST_FLG", data.VRFY_PER_CUST_FLG);
		setB("VRFY_ACCT_NAME_FLG", data.VRFY_ACCT_NAME_FLG);
		setB("VRFY_CERT_FLG", data.VRFY_CERT_FLG);
		setB("VRFY_PHONE_FLG", data.VRFY_PHONE_FLG);
		setB("VRFY_ACCT_CARD_FLG", data.VRFY_ACCT_CARD_FLG);
		setB("VRFY_MOD_BRCH_FLG", data.VRFY_MOD_BRCH_FLG);
		setB("VRFY_CANCL_BRCH_FLG", data.VRFY_CANCL_BRCH_FLG);
		setB("OTH_CUST_SIGN_FLG", data.OTH_CUST_SIGN_FLG);
	}
}

/*
 * tab3
 */
// 页面取值，table3新增或修改行
function tableActionRow3(tableId, ifAddRevice) {
	if (!portion("clearMsg")) {
		return;
	}
	var Data = {
		// 判断写死
		INTRM_ACCT_FLG : (getI("INTRM_ACCT") == "") ? "N" : "Y",
		FEE_CLR_METH : "2",

		//BUSI_NO : getS("BUSI_NO3").replace(/[;]/g, "</br>"), // tab3
		//BUSI_NAME : getST('BUSI_NO3').replace(/[;]/g, "</br>"), // tab3
		CLR_DIM_TP : getS('CLR_DIM_TP'),
		CLR_METH : getB('CLR_METH'),

		CLR_CYC : getI('CLR_CYC'),
		INTRM_ACCT : getI('INTRM_ACCT'), // tab3
		ENTR_ACCT : getI('ENTR_ACCT'), // tab3
		POSTING_SUM_CODE : getI('POSTING_SUM_CODE'),
		ENTR_ACCT_BANK : getI('ENTR_ACCT_BANK'), // tab3
		POSTING_SUM_DESC : getI('POSTING_SUM_DESC'),
		CLR_SND_GRP_FLG : getB('CLR_SND_GRP_FLG', "|"),
		STR_TIME : timeDelete(getI('STR_TIME3')),
		END_TIME : timeDelete(getI('END_TIME3')),
		BAT_PROC_FLG : getB('BAT_PROC_FLG'),
		VRFY_FLG : getB('VRFY_FLG'),

		FEE_CALT_FLG : getB('FEE_CALT_FLG3'),
		FEE_SND_GRP_FLG : getB('FEE_SND_GRP_FLG3', "|"),
		FEE_COLT_STR_TIME : timeDelete(getI('FEE_COLT_STR_TIME3')),
		FEE_COLT_END_TIME : timeDelete(getI('FEE_COLT_END_TIME3')),
		FEE_TF_OUT_ACCT : getI('FEE_TF_OUT_ACCT3'),
		FEE_SUM_CODE : getI('FEE_SUM_CODE3'),
		FEE_TF_IN_ACCT : getI('FEE_TF_IN_ACCT3'),
		FEE_SUM_DESC : getI('FEE_SUM_DESC3'),
		BAT_PROC_FEE_FLG : getB('BAT_PROC_FEE_FLG3'),
		FEE_PAY_FLG : getB('FEE_PAY_FLG3'),
		FEE_CALT_METH : getS("FEE_CALT_METH3"),
		FEE_RULE_EXPR : getDI("FEE_RULE_EXPR_DIV3"),
		MIN_FEE : getI('MIN_FEE3'),
		MAX_FEE : getI('MAX_FEE3'),
		FEE_PRCSN_TP : getS('FEE_PRCSN_TP3'),

		ACTION : "<a onclick='reviceRow3(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
	}
	if (ifAddRevice == "add") {
		// table增加行
		tableAddRow(tableId, Data);
	} else if (ifAddRevice == "revice") {
		// 修改行
		tableReviceRow(tableId, Data);
	}
}

// tale3获取选中行数据，回显
function reviceRow3(obj) {
	var $reviceRowData = reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData3($reviceRowData);
}

// 根据数据回显页面值
function setReviceRowData3(data) {
	//setS("BUSI_NO3", data.BUSI_NO.replace(/[<\/br>]/g, ";")); // tab3
	setS('CLR_DIM_TP', data.CLR_DIM_TP);
	setB('CLR_METH', data.CLR_METH);
	$("[name='CLR_METH']").change();

	setI('CLR_CYC', data.CLR_CYC);
	setI('INTRM_ACCT', data.INTRM_ACCT); // tab3
	setI('ENTR_ACCT', data.ENTR_ACCT); // tab3
	setI('POSTING_SUM_CODE', data.POSTING_SUM_CODE);
	setI('ENTR_ACCT_BANK', data.ENTR_ACCT_BANK); // tab3
	setI('POSTING_SUM_DESC', data.POSTING_SUM_DESC);
	/* setB('CLR_TASK_FLG', data.CLR_TASK_FLG); */
	setB('CLR_SND_GRP_FLG', data.CLR_SND_GRP_FLG, "|");
	setI('STR_TIME3', timeAdd(data.STR_TIME));
	setI('END_TIME3', timeAdd(data.END_TIME));
	setB('BAT_PROC_FLG', data.BAT_PROC_FLG);
	/* setB('CHK_NEED_CLR_FLG', data.CHK_NEED_CLR_FLG); */
	setB('VRFY_FLG', data.VRFY_FLG);

	setB('FEE_CALT_FLG3', data.FEE_CALT_FLG);
	$("[name='FEE_CALT_FLG3']").change();
	setB('FEE_SND_GRP_FLG3', data.FEE_SND_GRP_FLG, "|");
	setI('FEE_COLT_STR_TIME3', timeAdd(data.FEE_COLT_STR_TIME));
	setI('FEE_COLT_END_TIME3', timeAdd(data.FEE_COLT_END_TIME));
	setI('FEE_TF_OUT_ACCT3', data.FEE_TF_OUT_ACCT);
	setI('FEE_SUM_CODE3', data.FEE_SUM_CODE);
	setI('FEE_TF_IN_ACCT3', data.FEE_TF_IN_ACCT);
	setI('FEE_SUM_DESC3', data.FEE_SUM_DESC);
	setB('BAT_PROC_FEE_FLG3', data.BAT_PROC_FEE_FLG);
	setB('FEE_PAY_FLG3', data.FEE_PAY_FLG);
	setS("FEE_CALT_METH3", data.FEE_CALT_METH);
	setDI("FEE_RULE_EXPR_DIV3", data.FEE_RULE_EXPR);
	setI('MIN_FEE3', data.MIN_FEE);
	setI('MAX_FEE3', data.MAX_FEE);
	setS('FEE_PRCSN_TP3', data.FEE_PRCSN_TP);
}

/*
 * tab4
 */
/* 页面取值，table4新增或修改行 */
function tableActionRow4(tableId, ifAddRevice) {
	if (!portion("feeMsg")) {
		return;
	}
	var Data = {
		//BUSI_NO : getS("BUSI_NO4").replace(/[;]/g, "</br>"), // tab4
		//BUSI_NAME : getST('BUSI_NO4').replace(/[;]/g, "</br>"), // tab4

		FEE_CLR_METH : getB('FEE_CLR_METH4'),
		FEE_CALT_FLG : getB('FEE_CALT_FLG4'),

		FEE_SND_GRP_FLG : getB('FEE_SND_GRP_FLG4', "|"),
		FEE_COLT_STR_TIME : timeDelete(getI('FEE_COLT_STR_TIME4')),
		FEE_COLT_END_TIME : timeDelete(getI('FEE_COLT_END_TIME4')),
		FEE_TF_OUT_ACCT : getI('FEE_TF_OUT_ACCT4'), // tab4
		FEE_SUM_CODE : getI('FEE_SUM_CODE4'),
		FEE_TF_IN_ACCT : getI('FEE_TF_IN_ACCT4'), // tab4
		FEE_SUM_DESC : getI('FEE_SUM_DESC4'),
		BAT_PROC_FEE_FLG : getB('BAT_PROC_FEE_FLG4'),
		FEE_PAY_FLG : getB('FEE_PAY_FLG4'),
		FEE_CALT_METH : getS("FEE_CALT_METH4"),
		FEE_RULE_EXPR : getDI("FEE_RULE_EXPR_DIV4"),
		MIN_FEE : getI('MIN_FEE4'),
		MAX_FEE : getI('MAX_FEE4'),
		FEE_PRCSN_TP : getS('FEE_PRCSN_TP4'),

		ACTION : "<a onclick='reviceRow4(this)'>修改</a> <a onclick='deleteRow(this)'>删除</a>"
	}
	if (ifAddRevice == "add") {
		// table增加行
		tableAddRow(tableId, Data);
	} else if (ifAddRevice == "revice") {
		// 修改行
		tableReviceRow(tableId, Data);
	}
}

// tale4获取选中行数据，回显
function reviceRow4(obj) {
	var $reviceRowData = reviceRowData(obj);
	// 根据数据设置值
	setReviceRowData4($reviceRowData);
}

// 根据数据回显页面值
function setReviceRowData4(data) {
	//setS("BUSI_NO4", data.BUSI_NO.replace(/[<\/br>]/g, ";")); // tab4

	setB('FEE_CLR_METH4', data.FEE_CLR_METH);
	$("[name='FEE_CLR_METH4']").change();
	setB('FEE_CALT_FLG4', data.FEE_CALT_FLG);

	setB('FEE_SND_GRP_FLG4', data.FEE_SND_GRP_FLG, "|");
	setI('FEE_COLT_STR_TIME4', timeAdd(data.FEE_COLT_STR_TIME));
	setI('FEE_COLT_END_TIME4', timeAdd(data.FEE_COLT_END_TIME));
	setI('FEE_TF_OUT_ACCT4', data.FEE_TF_OUT_ACCT); // tab4
	setI('FEE_SUM_CODE4', data.FEE_SUM_CODE);
	setI('FEE_TF_IN_ACCT4', data.FEE_TF_IN_ACCT); // tab4
	setI('FEE_SUM_DESC4', data.FEE_SUM_DESC);
	setB('BAT_PROC_FEE_FLG4', data.BAT_PROC_FEE_FLG);
	setB('FEE_PAY_FLG4', data.FEE_PAY_FLG);
	setS("FEE_CALT_METH4", data.FEE_CALT_METH);
	setDI("FEE_RULE_EXPR_DIV4", data.FEE_RULE_EXPR);
	setI('MIN_FEE4', data.MIN_FEE);
	setI('MAX_FEE4', data.MAX_FEE);
	setS('FEE_PRCSN_TP4', data.FEE_PRCSN_TP);
}

/*
 * 页面公共方法
 */
// 设置页面单位编号
function setOtherEntrNo() {
	//setI("ENTR_NO2", getI("ENTR_NO1"));
	setI("ENTR_NO_SIGN", getI("ENTR_NO1"));
	setI("ENTR_NO3", getI("ENTR_NO1"));
	setI("ENTR_NO4", getI("ENTR_NO1"));
	setI("ENTR_NO5", getI("ENTR_NO1"));
}



// 回显全部页面数据
function setData(Data) {
	if (SAVE_OR_REV == "detail") {
	}else{
		disabledI("ENTR_NO1");
		disabledI("ENTR_NAME");
	}

    // tab1
    //var enData = Data.ENTR_NODE;
    var enData = Data;
    if (undefined != enData) {
        /*		setI("ENTR_NO1", enData.ENTR_NO);
                setOtherEntrNo();
                setI("ENTR_NAME", enData.ENTR_NAME);
                setS("ENTR_TP", enData.ENTR_TP);
                $("select[name='ENTR_TP']").change();
                setS("ENTR_TMPL_NO", enData.ENTR_TMPL_NO);
                setS("ENTR_NATURE", enData.ENTR_NATURE);
                setS("ENTR_CERT_TP", enData.ENTR_CERT_TP);
                setI("ENTR_CERT_NO", enData.ENTR_CERT_NO);
                setS("LEGA_CERT_TP", enData.LEGA_CERT_TP);
                setI("LEGA_CERT_NO", enData.LEGA_CERT_NO);
                setI("LEGA_NAME", enData.LEGA_NAME);
                setI("ENTR_ADDR", enData.ENTR_ADDR);
                setI("ENTR_TEL_NO", enData.ENTR_TEL_NO);
                setI("EMAIL", enData.EMAIL);
                setI("CTCT_PER_NAME", enData.CTCT_PER_NAME);
                setI("CTCT_PHONE_NO", enData.CTCT_PHONE_NO);
                setB('CLR_TP',enData.CLR_TP);
                setB('FEE_TP',enData.FEE_TP);*/

        setI("ENTR_NO1", enData.entrNo);
        setI("ENTR_NAME", enData.entrName);
        setS("ENTR_NATURE", enData.entrNature);
        setS("ENTR_CERT_TP", enData.entrCertTp);
        setI("ENTR_CERT_NO", enData.entrCertNo);
		setS("busiTypeId",enData.shortRmrk);
        setS("LEGA_CERT_TP", enData.legaCertTp);
        setI("LEGA_CERT_NO", enData.legaCertNo);

        setI("LEGA_NAME", enData.legaName);
        // setI("ENTR_ADDR", enData.entrAddr);
        const entrAddrStr = enData.entrAddr
        console.info(entrAddrStr);
        if (entrAddrStr.indexOf('-') > 0) {
            const entrAddrs = entrAddrStr.split('-')
            var index = entrAddrs[0].length;
            $('#province option:contains(' + entrAddrs[0] + ')').each(function () {
                if ($(this).text() == entrAddrs[0]) {
                    console.info($(this).text())
                    setS('province', $(this).val())
                }
            })
            setSelect1('city', `/prod/oper/entrDemo/area?parentId=${getS('province')}`, 'name', 'name', undefined, false)
            if (entrAddrs.length > 1) {
                setS('city', entrAddrs[1])
                index = index + entrAddrs[1].length
            }
            if (entrAddrs.length > 2) {
                setI('detailedAddress', entrAddrStr.slice(index + 2))
            }
        }
        setI("COMN_DESC", enData.comnDesc);

        setI("ENTR_TEL_NO", enData.entrTelNo);
        setI("EMAIL", enData.email);
        setI("CTCT_PER_NAME", enData.ctctPerName);
        setI("CTCT_PHONE_NO", enData.ctctPhoneNo);
        // setI("url", enData.url);
        $('[name=url]').val(enData.url);
        urlPreview();
    }

	

	// signMsg业务签约信息页面
	var signArr = Data.SIGN_LIST;
	var arrTableSign = [];
	if (undefined != signArr) {
		for (var signNum = 0; signNum < signArr.length; signNum++) {
			var dataTableSign = {};
			var signData = signArr[signNum];

			var busiArr = signData.BUSI_LIST;
			// 循环取出业务名称
			if(busiArr!=undefined){
				var busiNoArr = [];
				var busiNameArr = [];
				for (var busiNum = 0; busiNum < busiArr.length; busiNum++) {
					var busiNo = busiArr[busiNum].BUSI_NO;
					busiNoArr.push(busiNo);
					if (undefined != blArr) {
						for (var busiLNum = 0; busiLNum < blArr.length; busiLNum++) {
							if (blArr[busiLNum].BUSI_NO == busiNo) {
								busiNameArr.push(blArr[busiLNum].BUSI_NAME);
								break;
							}
						}
					}
				}
				dataTableSign.BUSI_NO = busiNoArr.join("</br>");
				dataTableSign.BUSI_NAME = busiNameArr.join("</br>");
			}
			
			if(signData.SIGN_FLG=="Y"){
				dataTableSign.SIGN_FLG_STR = "是";
			}else if(signData.SIGN_FLG=="N"){
				dataTableSign.SIGN_FLG_STR = "否";
			}
			dataTableSign.SIGN_FLG = signData.SIGN_FLG;
			dataTableSign.RULE_NAME = signData.RULE_NAME;

			dataTableSign.SIGN_TP = signData.SIGN_TP;
			dataTableSign.CUST_DEF_LIM_FLG = signData.CUST_DEF_LIM_FLG;
			if(signData.LIMIT_LIST!=undefined){
				for(var limitNum=0;limitNum<signData.LIMIT_LIST.length;limitNum++){
					var limitObj = signData.LIMIT_LIST[limitNum];
					limitObj.ACTION = "<a onclick='reviceRowSign(this,\"1\")'>修改</a>";
					if (SAVE_OR_REV == "revice") {
						limitObj.ACTION += " <a onclick='deleteRow(this)'>删除</a>";
					}
					if(limitObj.LIM_FLG=="00"){
						limitObj.LIM_FLG_STR = "单笔";
					}else if(limitObj.LIM_FLG=="01"){
						limitObj.LIM_FLG_STR = "日";
					}else if(limitObj.LIM_FLG=="02"){
						limitObj.LIM_FLG_STR = "旬";
					}else if(limitObj.LIM_FLG=="03"){
						limitObj.LIM_FLG_STR = "月";
					}else if(limitObj.LIM_FLG=="04"){
						limitObj.LIM_FLG_STR = "季";
					}else if(limitObj.LIM_FLG=="05"){
						limitObj.LIM_FLG_STR = "年";
					}
				}
				dataTableSign.LIMIT_SET = JSON.stringify(signData.LIMIT_LIST);
			}else{
				dataTableSign.LIMIT_SET= JSON.stringify([]);
			}

			dataTableSign.ACCT_STAT_LIST = signData.ACCT_STAT_LIST;
			dataTableSign.VCH_STAT_LIST = signData.VCH_STAT_LIST;
			dataTableSign.VRFY_ENTR_CUST_FLG = signData.VRFY_ENTR_CUST_FLG;
			dataTableSign.VRFY_PER_CUST_FLG = signData.VRFY_PER_CUST_FLG;
			dataTableSign.VRFY_ACCT_NAME_FLG = signData.VRFY_ACCT_NAME_FLG;
			dataTableSign.VRFY_CERT_FLG = signData.VRFY_CERT_FLG;
			dataTableSign.VRFY_PHONE_FLG = signData.VRFY_PHONE_FLG;
			dataTableSign.VRFY_ACCT_CARD_FLG = signData.VRFY_ACCT_CARD_FLG;
			dataTableSign.VRFY_MOD_BRCH_FLG = signData.VRFY_MOD_BRCH_FLG;
			dataTableSign.VRFY_CANCL_BRCH_FLG = signData.VRFY_CANCL_BRCH_FLG;
			dataTableSign.OTH_CUST_SIGN_FLG = signData.OTH_CUST_SIGN_FLG;
			
			dataTableSign.ACTION = "<a onclick='reviceRowSign(this,\"2\")'>修改</a>";
			if (SAVE_OR_REV == "revice") {
				dataTableSign.ACTION += " <a onclick='deleteRow(this)'>删除</a>";
			}

			arrTableSign.push(dataTableSign);
		}
	}
	$('#tableSign2').bootstrapTable('load', arrTableSign);

	// tab3
	var clArr = Data.CLR_LIST;
	var arrTable3 = [];
	if (undefined != clArr) {
		for (var b = 0; b < clArr.length; b++) {
			var dataTable3 = {};
			var clData = clArr[b];

			var busiArr = clData.BUSI_LIST;
			// 循环取出业务名称
			if(busiArr!=undefined){
				var busiNoArr = [];
				var busiNameArr = [];
				for (var busiNum = 0; busiNum < busiArr.length; busiNum++) {
					var busiNo = busiArr[busiNum].BUSI_NO;
					busiNoArr.push(busiNo);
					if (undefined != blArr) {
						for (var busiLNum = 0; busiLNum < blArr.length; busiLNum++) {
							if (blArr[busiLNum].BUSI_NO == busiNo) {
								busiNameArr.push(blArr[busiLNum].BUSI_NAME);
								break;
							}
						}
					}
					
				}
				dataTable3.BUSI_NO = busiNoArr.join("</br>");
				dataTable3.BUSI_NAME = busiNameArr.join("</br>");
			}
			

			// BUSI_NAME TODO
			var cnData = clData.CLR_NODE;
			dataTable3.CLR_DIM_TP = cnData.CLR_DIM_TP;
			dataTable3.CLR_METH = cnData.CLR_METH;
			dataTable3.CLR_CYC = cnData.CLR_CYC;
			dataTable3.CLR_SND_GRP_FLG = cnData.CLR_SND_GRP_FLG;
			dataTable3.STR_TIME = cnData.STR_TIME;
			dataTable3.END_TIME = cnData.END_TIME;
			dataTable3.BAT_PROC_FLG = cnData.BAT_PROC_FLG;
			dataTable3.VRFY_FLG = cnData.VRFY_FLG;
			dataTable3.INTRM_ACCT_FLG = cnData.INTRM_ACCT_FLG;
			dataTable3.INTRM_ACCT = cnData.INTRM_ACCT;
			dataTable3.ENTR_ACCT = cnData.ENTR_ACCT;
			dataTable3.ENTR_ACCT_BANK = cnData.ENTR_ACCT_BANK;
			dataTable3.POSTING_SUM_CODE = cnData.POSTING_SUM_CODE;
			dataTable3.POSTING_SUM_DESC = cnData.POSTING_SUM_DESC;

			var fcnData = clData.FEE_CLR_NODE;
			if (undefined != fcnData) {
				dataTable3.FEE_CLR_METH = fcnData.FEE_CLR_METH;
				dataTable3.BAT_PROC_FEE_FLG = fcnData.BAT_PROC_FEE_FLG;
				dataTable3.FEE_PAY_FLG = fcnData.FEE_PAY_FLG;
				dataTable3.FEE_CALT_FLG = fcnData.FEE_CALT_FLG;
				dataTable3.FEE_SND_GRP_FLG = fcnData.FEE_SND_GRP_FLG;
				dataTable3.FEE_COLT_STR_TIME = fcnData.FEE_COLLT_STR_TIME;
				dataTable3.FEE_COLT_END_TIME = fcnData.FEE_COLLT_END_TIME;
				dataTable3.FEE_TF_OUT_ACCT = fcnData.FEE_TF_OUT_ACCT;
				dataTable3.FEE_TF_IN_ACCT = fcnData.FEE_TF_IN_ACCT;
				dataTable3.FEE_SUM_CODE = fcnData.FEE_SUM_CODE;
				dataTable3.FEE_SUM_DESC = fcnData.FEE_SUM_DESC;

				if (undefined != fcnData.FEE_CALT_LIST) {
					var fclData = fcnData.FEE_CALT_LIST[0];
					dataTable3.FEE_CALT_METH = fclData.FEE_CALT_METH;
					dataTable3.FEE_RULE_EXPR = fclData.FEE_RULE_EXPR;
					dataTable3.MIN_FEE = fclData.MIN_FEE;
					dataTable3.MAX_FEE = fclData.MAX_FEE;
					dataTable3.FEE_PRCSN_TP = fclData.FEE_PRCSN_TP;
				}
			}

			dataTable3.ACTION = "<a onclick='reviceRow3(this)'>修改</a>";
			if (SAVE_OR_REV == "revice") {
				dataTable3.ACTION += " <a onclick='deleteRow(this)'>删除</a>";
			}

			arrTable3.push(dataTable3);
		}
	}
	$('#table3').bootstrapTable('load', arrTable3);

	// tab4
	var flArr = Data.FEE_LIST;
	var arrTable4 = [];
	if (undefined != flArr) {
		for (var d = 0; d < flArr.length; d++) {
			var dataTable4 = {};
			var flData = flArr[d];

			var busiArr = flData.BUSI_LIST;
			// 循环取出业务名称
			if(busiArr!=undefined){
				var busiNoArr = [];
				var busiNameArr = [];
				for (var busiNum = 0; busiNum < busiArr.length; busiNum++) {
					var busiNo = busiArr[busiNum].BUSI_NO;
					busiNoArr.push(busiNo);
					if (undefined != blArr) {
						for (var busiLNum = 0; busiLNum < blArr.length; busiLNum++) {
							if (blArr[busiLNum].BUSI_NO == busiNo) {
								busiNameArr.push(blArr[busiLNum].BUSI_NAME);
								break;
							}
						}
					}
				}
				dataTable4.BUSI_NO = busiNoArr.join("</br>");
				dataTable4.BUSI_NAME = busiNameArr.join("</br>");
			}
			

			dataTable4.BAT_PROC_FEE_FLG = flData.BAT_PROC_FEE_FLG;
			dataTable4.FEE_PAY_FLG = flData.FEE_PAY_FLG;
			dataTable4.FEE_CALT_FLG = flData.FEE_CALT_FLG;
			dataTable4.FEE_CLR_METH = flData.FEE_CLR_METH;
			dataTable4.FEE_SND_GRP_FLG = flData.FEE_SND_GRP_FLG;
			dataTable4.FEE_COLT_STR_TIME = flData.FEE_COLLT_STR_TIME;
			dataTable4.FEE_COLT_END_TIME = flData.FEE_COLLT_END_TIME;
			dataTable4.FEE_TF_OUT_ACCT = flData.FEE_TF_OUT_ACCT;
			dataTable4.FEE_TF_IN_ACCT = flData.FEE_TF_IN_ACCT;
			dataTable4.FEE_SUM_CODE = flData.FEE_SUM_CODE;
			dataTable4.FEE_SUM_DESC = flData.FEE_SUM_DESC;

			if (undefined != flData.FEE_CALT_LIST) {
				var fcnData = flData.FEE_CALT_LIST[0];
				dataTable4.FEE_CALT_METH = fcnData.FEE_CALT_METH;
				dataTable4.FEE_RULE_EXPR = fcnData.FEE_RULE_EXPR;
				dataTable4.MIN_FEE = fcnData.MIN_FEE;
				dataTable4.MAX_FEE = fcnData.MAX_FEE;
				dataTable4.FEE_PRCSN_TP = fcnData.FEE_PRCSN_TP;
			}

			dataTable4.ACTION = "<a onclick='reviceRow4(this)'>修改</a>";
			if (SAVE_OR_REV == "revice") {
				dataTable4.ACTION += " <a onclick='deleteRow(this)'>删除</a>";
			}

			arrTable4.push(dataTable4);
		}
	}
	$('#table4').bootstrapTable('load', arrTable4);

	// tab5 TODO
	if (undefined != Data.CHK_LIST) {
		var clData = Data.CHK_LIST[0];
		setS("CHK_DIM_TP", clData.CHK_DIM_TP);
		setS("CONT_FLG", clData.CONT_FLG);
		setS("CHK_TP", clData.CHK_TP);
		$('[name="CHK_TP"]').change();
		setB("CHK_SND_GRP_FLG", clData.CHK_SND_GRP_FLG);
		setB("ASYNC_FLG", clData.ASYNC_FLG);

		setI("CHK_CYC", clData.CHK_CYC);
		setC("MONTH_END_FLG", clData.MONTH_END_FLG, "Y");
		$('[name="MONTH_END_FLG"]').change();
		setC("QUARTER_END_FLG", clData.QUARTER_END_FLG, "Y");
		$('[name="QUARTER_END_FLG"]').change();
		setC("YEAR_END_FLG", clData.YEAR_END_FLG, "Y");
		$('[name="YEAR_END_FLG"]').change();
		setI("MONTH_END_STR_TIME", timeAdd(clData.MONTH_END_STR_TIME));
		setI("MONTH_END_END_TIME", timeAdd(clData.MONTH_END_END_TIME));
		setI("QUARTER_END_STR_TIME", timeAdd(clData.QUARTER_END_STR_TIME));
		setI("QUARTER_END_END_TIME", timeAdd(clData.QUARTER_END_END_TIME));
		setI("YEAR_END_STR_TIME", timeAdd(clData.YEAR_END_STR_TIME));
		setI("YEAR_END_END_TIME", timeAdd(clData.YEAR_END_END_TIME));

		setI("STR_TIME5", timeAdd(clData.STR_TIME));
		setI("END_TIME5", timeAdd(clData.END_TIME));
		setB("CHK_RSLT_PUSH_FLG", clData.CHK_RSLT_PUSH_FLG);
		setI("CHK_FILE_NAME", clData.CHK_FILE_NAME);
	}

	if (SAVE_OR_REV == "revice") {

	} else if (SAVE_OR_REV == "detail") {
		$("#addBtn").hide();
		
		// tab1
		disDiv("baseMsg");
		$("#saveBtn1").hide();
		$("#cancelBtn1").hide();

		
		
		// sign
		disDiv("signConDiv");
		$("#saveBtnSign").hide();
		$("#tableBtnSign").hide();
		$("#cancelBtnSign").hide();

		// tab3
		disDiv("clearMsg");
		$("#saveBtn3").hide();
		$("#tableBtn3").hide();
		$("#cancelBtn3").hide();

		// tab4
		disDiv("feeMsg");
		$("#saveBtn4").hide();
		$("#tableBtn4").hide();
		$("#cancelBtn4").hide();

		// tab5
		disDiv("chkMsg");
		$("#saveBtn5").hide();
		$("#cancelBtn5").hide();

		$("#saveBtn6").hide();
		$("#cancelBtn6").hide();
	}
}

// 获取页面数据
function getData() {

	//signMsg业务签约信息
	var rowArrSign = $('#tableSign2').bootstrapTable('getData');
	//把字段LIMIT_SET转回arr再重新后面统一转字符串，双重JSON.stringify会出问题
	for(var rowSignNum=0;rowSignNum<rowArrSign.length;rowSignNum++){
		if(typeof(rowArrSign[rowSignNum].LIMIT_SET) =="string" ){
			rowArrSign[rowSignNum].LIMIT_SET = JSON.parse(rowArrSign[rowSignNum].LIMIT_SET);
		}
		
	}
	var rowSign = JSON.stringify(rowArrSign);
	
	// tab3 table
	var rowArr3 = $('#table3').bootstrapTable('getData');
	var row3 = JSON.stringify(rowArr3);
	// tab4 table
	var rowArr4 = $('#table4').bootstrapTable('getData');
	var row4 = JSON.stringify(rowArr4);

	var data = {
		// 写死的字段
		SIGN_TP:getB('SIGN_FLG'),
		
		CLR_TP : getB('CLR_TP'),
		CHK_TP_FIX : "2",
		FEE_TP : getB('FEE_TP'),
		SUB_ENTR_FLG : "N",
		OPEN_STAT : "Y",
		//这个取公共
		//CHK_TP:getS('CHK_TP'),

        // tab1
        ENTR_NO: getI("ENTR_NO1"),
        ENTR_NAME: getI("ENTR_NAME"),
        busiTypeId:getS("busiTypeId"),
        ENTR_TP: getS("ENTR_TP"),
        ENTR_TMPL_NO: getS("ENTR_TMPL_NO"),
        ENTR_NATURE: getS("ENTR_NATURE"),
        ENTR_CERT_TP: getS("ENTR_CERT_TP"),
        ENTR_CERT_NO: getI("ENTR_CERT_NO"),
        LEGA_CERT_TP: getS("LEGA_CERT_TP"),
        LEGA_CERT_NO: getI("LEGA_CERT_NO"),
        LEGA_NAME: getI("LEGA_NAME"),
        ENTR_ADDR: $('#province').find('option:selected').text() + '-' + $('#city').val() + '-' + $('#detailedAddress').val(),
        COMN_DESC: getI("COMN_DESC"),
        ENTR_TEL_NO: getI("ENTR_TEL_NO"),
        EMAIL: getI("EMAIL"),
        CTCT_PER_NAME: getI("CTCT_PER_NAME"),
        CTCT_PHONE_NO: getI("CTCT_PHONE_NO"),
        url: getI("url"),

		
		//signMsg
		TABLE_SIGN:rowSign,

		// tab3
		TABLE3 : row3,

		// tab4
		TABLE4 : row4,

		// tab5
		// 写死
		CHK_CYC_TP : "T",

		CHK_DIM_TP : getS("CHK_DIM_TP"),
		CONT_FLG:getS("CONT_FLG"),
		CHK_TP : getS("CHK_TP"),
		CHK_SND_GRP_FLG : getB("CHK_SND_GRP_FLG"),
		ASYNC_FLG : getB("ASYNC_FLG"),

		CHK_CYC : getI("CHK_CYC"),
		MONTH_END_FLG : getC("MONTH_END_FLG"),
		QUARTER_END_FLG : getC("QUARTER_END_FLG"),
		YEAR_END_FLG : getC("YEAR_END_FLG"),
		MONTH_END_STR_TIME : timeDelete(getI("MONTH_END_STR_TIME")),
		MONTH_END_END_TIME : timeDelete(getI("MONTH_END_END_TIME")),
		QUARTER_END_STR_TIME : timeDelete(getI("QUARTER_END_STR_TIME")),
		QUARTER_END_END_TIME : timeDelete(getI("QUARTER_END_END_TIME")),
		YEAR_END_STR_TIME : timeDelete(getI("YEAR_END_STR_TIME")),
		YEAR_END_END_TIME : timeDelete(getI("YEAR_END_END_TIME")),

		STR_TIME5 : timeDelete(getI("STR_TIME5")),
		END_TIME5 : timeDelete(getI("END_TIME5")),
		CHK_RSLT_PUSH_FLG : getB("CHK_RSLT_PUSH_FLG"),
		CHK_FILE_NAME : getI("CHK_FILE_NAME"),
	}
	return data;
}

// 暂存、生效
function Save(e) {
	if(!proof()){
		return;
	}
	var url = $("[name=url]").val();
	if (url == "") {
		showTip("请添加图片", "error");
		return;
	}
	if(SAVE_OR_REV != 'revice'){
	//检查单位编号/名称
	$.post(ctx+"/prod/oper/entrDemo/qry",{ENTR_NO:getI("ENTR_NO1")},function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[单位编号检测异常]";
			showContent(errMsg, "error");
		} else {
			var dataArr = data.dataSetResult[0].data;
			if(dataArr.length>0 && dataArr[0]!="["){
				flg=true;
				showContent("单位编号重复,操作失败", "error")
			}else{
				$.post(ctx+"/prod/oper/entrDemo/qryName",{ENTR_NAME:getI("ENTR_NAME")},function(data){
					if (data.returnCode !== undefined
							&& "0000" != data.returnCode) {
						var errMsg = "错误信息[单位编号检测异常]";
						showContent(errMsg, "error");
					} else {
						var dataArr = data.dataSetResult[0].data;
						if(dataArr.length>0 && dataArr[0]!="["){
							flg=true;
							showContent("单位名称重复,操作失败", "error")
						}else{
							var tp = e.data.tp;
							var reqData = getData();
							reqData.OPEN_STAT="Y";
							if (tp == "save") {
								reqData.OPER_TP = "3";
							} else if (tp == "effect") {
								if (SAVE_OR_REV == "add") {
									reqData.OPER_TP = "1";
								} else if (SAVE_OR_REV == "revice") {
									reqData.OPER_TP = "2";
								}
							}
							$.ajax({
								url : ctx + "/prod/oper/entrDemo/add",
								type : "POST",
								dataType : "json",
								data : reqData,
								async : true,
								success : function(data) {
									if (data.returnCode !== undefined && "0000" != data.returnCode) {
										var errMsg = "错误信息[" + data.message + "]";
										showContent(errMsg, "error");
									} else {
										console.log(data.message);
										var successMsg = "提交后台[" + data.message + "]";
										showContent(successMsg, "success");

										// 回调刷新操作
										//freshBack();

										parent.tab1();
									}
								}
							});
							}
						}
				},"json")
				
			}
		}
		},"json")
	}else{
		var tp = e.data.tp;
		var reqData = getData();
		reqData.OPEN_STAT="Y";
		if (tp == "save") {
			reqData.OPER_TP = "3";
		} else if (tp == "effect") {
			if (SAVE_OR_REV == "add") {
				reqData.OPER_TP = "1";
			} else if (SAVE_OR_REV == "revice") {
				reqData.OPER_TP = "2";
			}
		}
		$.ajax({
			url : ctx + "/prod/oper/entrDemo/add",
			type : "POST",
			dataType : "json",
			data : reqData,
			async : true,
			success : function(data) {
				if (data.returnCode !== undefined && "0000" != data.returnCode) {
					var errMsg = "错误信息[" + data.message + "]";
					showContent(errMsg, "error");
				} else {
					console.log(data.message);
					var successMsg = "提交后台[" + data.message + "]";
					showContent(successMsg, "success");

					// 回调刷新操作
					//freshBack();

					parent.tab1();
				}
			}
		});
	}
}

// 提交刷新回调
/*function freshBack() {
	$.ajax({
		url : ctx + "/prod/om/freshPara/fresh",
		type : "POST",
		dataType : "json",
		data : {},
		async : true,
		complete : function() {
			return;
		}
	});
}*/

// 获取div里面input的所有值|分隔
function getDI(Id) {
	var valArr = [];
	var grounpArr = [];
	var a = 1;
	$("#" + Id + " " + "input[type='text']").each(function() {
		grounpArr.push($(this).val());
		if (a % 4 == 0) {
			valArr.push(grounpArr.join("|"));
			grounpArr = [];
		}
		a++;
	});
	return valArr.join(";");
}

// 设置div里面input的所有值
function setDI(Id, val) {
	if (undefined == val) {
		return;
	}
	var valAllArr = [];
	var valArr = val.split(";");
	for (var a = 0; a < valArr.length; a++) {
		valAllArr = valAllArr.concat(valArr[a].split("|"));
	}

	var $objArr = $("#" + Id + " " + "input[type='text']");
	for (var a = 0; a < valAllArr.length; a++) {
		$objArr.eq(a).val(valAllArr[a]);
	}
}

function getEntrNo(){
	$.post(ctx+"/prod/oper/entrDemo/getEntrNo",{},function(data){
		if (data.returnCode !== undefined
				&& "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			setI("ENTR_NO1",data.dataSetResult[0].data[0].entrNo);
		}
		},"json")
	

}