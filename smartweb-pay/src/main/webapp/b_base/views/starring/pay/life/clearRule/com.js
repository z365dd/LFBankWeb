function clrTpSelect(CLR_TP) {
    var clrTpMap = new Map();
    clrTpMap.set('0', '汇总清算一笔');
    clrTpMap.set('2', '按手续费清算');
    clrTpMap.set('3', '按渠道分别清算');
    if (CLR_TP) {
        clrTpMap.forEach((v, k) => {
            CLR_TP.append(`<option value = '${k}'}>${v}</option>`);
        })
        CLR_TP.multiselect('rebuild').multiselect('refresh');
    }
    return clrTpMap;
}

function rfndTpMapSelect(RFND_TP) {
    var rfndTpMap = new Map();
    rfndTpMap.set('1', '单位户直接退费');
    rfndTpMap.set('2', '内部户退费');
    rfndTpMap.set('3', '垫款户退费');
    rfndTpMap.set('4', '不允许退费');
    if (RFND_TP) {
        rfndTpMap.forEach((v, k) => {
            RFND_TP.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        RFND_TP.multiselect('rebuild').multiselect('refresh');
    }
    return rfndTpMap;
}

function chkTpMapSelect(CHK_TP) {
    var chkTpMap = new Map();
    chkTpMap.set('1', '行内对账');
    chkTpMap.set('2', '三方对账');
    if (CHK_TP) {
        chkTpMap.forEach((v, k) => {
            CHK_TP.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        CHK_TP.multiselect('rebuild').multiselect('refresh');
    }
    return chkTpMap;
}

function noteTpMapSelect(NOTE_TP) {
    var noteTpMap = new Map();
    noteTpMap.set('1', '对账完成通知三方');
    noteTpMap.set('2', '对账完成不通知三方');
    if (NOTE_TP) {
        noteTpMap.forEach((v, k) => {
            NOTE_TP.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        NOTE_TP.multiselect('rebuild').multiselect('refresh');
    }
    return noteTpMap;
}

function dayNoteTpMapSelect(DAY_NOTE_TP) {
    var dayNoteTpMap = new Map();
    dayNoteTpMap.set('1', '通知三方');
    dayNoteTpMap.set('2', '不通知三方');
    if (DAY_NOTE_TP) {
        dayNoteTpMap.forEach((v, k) => {
            DAY_NOTE_TP.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        DAY_NOTE_TP.multiselect('rebuild').multiselect('refresh');
    }
    return dayNoteTpMap;
}


function check() {
    var acctCheck = true;
    $("#acctErrInfo").hide();
    $("#ADVANCE_ACCT").on("blur", function (event) {
        const acct = getI("ADVANCE_ACCT");
        if (getI("RFND_TP") == 3 || acct == undefined || acct == null || acct.length == 0) {
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
    $("#ADVANCE_ACCT_NAME").on("blur", function (event) {
        const acctName = getI("ADVANCE_ACCT_NAME");
        if (getI("RFND_TP") == 3 || acctName == undefined || acctName == null || acctName.length == 0) {
            acctNameCheck = false;
            $("#acctNameErrInfo").text("请输入垫款账号");
            $("#acctNameErrInfo").show();
            return;
        }
        acctNameCheck = true;
        $("#acctNameErrInfo").hide();
    });
    return acctCheck == true && acctNameCheck == true;
}


