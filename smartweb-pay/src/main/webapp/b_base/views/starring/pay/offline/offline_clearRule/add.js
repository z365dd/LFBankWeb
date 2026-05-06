let $from;
let busiM = new Map();
//清算类型
let clrTpMap = new Map();
//对账类型
let chkTpMap = new Map();
//退款类型
let rfndTpMap = new Map();
//通知类型
let noteTpMap = new Map();

$(function () {
    parent.window.$('#iframe_add').show();
    $("#showAcct").hide();
    $("#showAcctName").hide();
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='BUSI_NO']"), '01');
    $from = $('#from');
    // busiInfo.forEach(item => {
    //     busiM.set(item.busiNo, item.busiName);
    // });
    busiNo();
    //业务编号
    $("#BUSI_NO").on("change", function (e) {
        busiNo();
    });
    //清算类型
    // clrTpMap = clrTpSelect($("select[name='CLR_TP']"));
    //对账类型
    // chkTpMap = chkTpMapSelect($("select[name='CHK_TP']"))
    //退款类型
    // rfndTpMap = rfndTpMapSelect($("select[name='RFND_TP']"))
    //通知类型
    // noteTpMap = noteTpMapSelect($("select[name='NOTE_TP']"))
    $("#RFND_TP").on("change", function (e) {
        const rfndTp = getS("RFND_TP");
        if (rfndTp == 3) {
            $("#showAcct").show();
            $("#showAcctName").show();
        } else {
            $("#showAcct").hide();
            $("#showAcctName").hide();
        }
    });
    var acctCheck = true;
    $("#acctErrInfo").hide();
    $("#TEMP_ACCT").on("blur", function (event) {
        const acct = getI("TEMP_ACCT");
        if (getI("RFND_TP") == 3 && acct.length == 0 || acct == undefined || acct == null) {
            acctCheck = false;
            $("#acctErrInfo").text("请输入垫款账号");
            $("#acctErrInfo").show();
            return;
        }
        acctCheck = true;
        $("#acctErrInfo").hide();
    });
    var acctNameCheck = true;

    $("#acctNameErrInfo").hide();
    $("#TEMP_ACCT_NAME").on("blur", function (event) {
        const acctName = getI("TEMP_ACCT_NAME");
        if (getI("RFND_TP") == 3 && acctName.length == 0 || acctName == undefined || acctName == null) {
            acctNameCheck = false;
            $("#acctNameErrInfo").text("请输入垫款账户名称");
            $("#acctNameErrInfo").show();
            return;
        }
        acctNameCheck = true;
        $("#acctNameErrInfo").hide();
    });
    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (getI("RFND_TP") == 3) {
            $("#TEMP_ACCT").blur();
            $("#TEMP_ACCT_NAME").blur();
        }
        if (acctCheck == true && acctNameCheck == true) {
            save();
        } else {
            showTip("请检查必填项是否已填写", "error", 2000, 100);
        }
    });
});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='BUSI_NO']"), '01');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI("busiName", busiM.get(getS("BUSI_NO")));
}

function save() {
    confirmx('是否保存', function () {
        const data = $("#from").serializeObject();
        sendPostOfAjax(ctx + '/clearRule/data/add', data, false, true, () => {
            setS("BUSI_NO", "");
            setI("busiName", "");
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}

