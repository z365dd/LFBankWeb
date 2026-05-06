function sendPostOfAjax(url, data, isRefresh, tableId, successMethod, errorMthod) {
    let result;
    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        async: false,
        success: function (data, textStatus) {
            if (data && data.returnCode && '0000' == data.returnCode) {
                showContent('操作成功', 'success');
                if (isRefresh == undefined ? true : typeof isRefresh != 'boolean' ? true : isRefresh) {
                    $('#' + tableId ? tableId : 'smsIndexTable').bootstrapTable('refreshOptions', queryParams);
                }
                if (typeof data == 'string') {
                    data = JSON.parse(data);
                }
                result = data.data;
                if (successMethod) {
                    successMethod(result);
                }
                return;
            }
            showContent('操作失败:[' + data && data.message ? data.message : '' + ']', 'error');
            if (errorMthod) {
                errorMthod();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            showContent('请求发生错误,未能成功获取响应结果', 'error');
            if (errorMthod) {
                errorMthod();
            }
        }
    });
    return result;
}

function sendGetOfAjax(url, isSuccessShow, successMethod) {
    let result;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data, textStatus) {
            if (data && data.returnCode && '0000' == data.returnCode) {

                if (isSuccessShow) {
                    showContent('操作成功', 'success');
                }
                if (typeof data == 'string') {
                    1
                    data = JSON.parse(data);
                }
                result = data.data;
                if (successMethod) {
                    successMethod(result);
                }
                return;
            }
            showContent('操作失败:[' + data && data.message ? data.message : '' + ']', 'error');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            showContent('请求发生错误,未能成功获取响应结果', 'error');
        }
    });
    return result;
}



function getProvince(url) {
    let result;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data, textStatus) {
            if (data && data.returnCode && '0000' == data.returnCode) {
                result = data.dataSetResult[0].data;
                return;
            }
            showContent('操作失败:[' + data && data.message ? data.message : '' + ']', 'error');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            showContent('请求发生错误,未能成功获取响应结果', 'error');
        }
    });
    return result;
}



function sendGetOfAjax1(url, isSuccessShow, successMethod) {
    let result;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data, textStatus) {
            if (data && data.returnCode && '0000' == data.returnCode) {

                if (isSuccessShow) {
                    showContent('操作成功', 'success');
                }
                if (typeof data == 'string') {
                    1
                    data = JSON.parse(data);
                }
                result = data.data;
                if (successMethod) {
                    successMethod(result);
                }
                return;
            }
            // showContent('操作失败:[' + data && data.message ? data.message : '' + ']', 'error');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // showContent('请求发生错误,未能成功获取响应结果', 'error');
        }
    });
    return result;
}


//获取前一天日期 yyyy-MM-dd
function getYesterday() {
    var time = new Date().getTime() - 24 * 60 * 60 * 1000;
    //根据js方法获取年月日时分秒
    var yy = new Date(time).getFullYear();
    var mm = new Date(time).getMonth() + 1 < 10
        ? "0" + (new Date(time).getMonth() + 1)
        : new Date(time).getMonth() + 1;

    var dd = new Date(time).getDate() < 10
        ? "0" + new Date(time).getDate()
        : new Date(time).getDate();

    var hh = new Date(time).getHours();

    var mf = new Date(time).getMinutes() < 10
        ? "0" + new Date(time).getMinutes()
        : new Date(time).getMinutes();

    var ss = new Date(time).getSeconds() < 10
        ? "0" + new Date(time).getSeconds()
        : new Date(time).getSeconds();
    //将自己所需要的的数据字段拼接即可
    var yesterday = yy + "-" + mm + "-" + dd;
    return yesterday;
}

//获取当天日期 yyyy-MM-dd
function getCurrentday() {
    var myDate = new Date();     //获取当前年份(2位)
    var year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    var month = myDate.getMonth();       //获取当前月份(0-11,0代表1月)
    month = (month + 1) > 9 ? (month + 1) : "0" + (month + 1);
    var day = myDate.getDate();        //获取当前日(1-31)
    day = day > 9 ? day : "0" + day;
    var dayNow = year + '-' + month + "-" + day;
    return dayNow;
}


function sendGetReqOfBusi(busiTp) {
    const info = sendGetOfAjax(ctx + `/comQuery/data/busiList?busiTp=${busiTp}`, false);
    if (info != undefined && info != null) {
        return info;
    }
    return null;
}

function busiSelect(busiSelectDom, busiTp) {
    const resp = sendGetReqOfBusi(busiTp);
    if (resp == null) {
        return null;
    }
    if (resp != undefined && resp != null) {
        resp.forEach((tp, index) => {
            const optStr = `<option value = '${tp.busiNo}'>${tp.busiNo + '-' + tp.busiName}</option>`;
            busiSelectDom.append(optStr);
        });
    }
    busiSelectDom.multiselect('rebuild').multiselect('refresh');
    return resp;
}

function busiSelectByName(busiSelectDom, busiTp) {
    const resp = sendGetReqOfBusi(busiTp);
    if (resp == null) {
        return null;
    }
    resp.forEach((tp, index) => {
        const optStr = `<option value = '${tp.busiNo}'>${tp.busiName}</option>`;
        busiSelectDom.append(optStr);
    });
    busiSelectDom.multiselect('rebuild').multiselect('refresh');
    return resp;
}

function busiMap(busiTp) {
    const resp = sendGetReqOfBusi(busiTp);
    if (resp == null) {
        return null;
    }
    var busiMap = new Map();
    resp.forEach((tp, index) => {
        busiMap.set(tp.busiNo, tp.busiName)
    });
    return busiMap;
}

function sendGetReqOfChnl() {
    const info = sendGetOfAjax(ctx + '/comQuery/data/chnlList', false);
    if (info != undefined && info != null) {
        return info;
    }
    return null;
}

function chnlSelect(busiSelectDom) {
    const resp = sendGetReqOfChnl();
    if (resp == null) {
        return null;
    }
    resp.forEach((tp, index) => {
        const optStr = `<option value = '${tp.chnlNo}'>${tp.chnlNo + '-' + tp.chnlName}</option>`;
        busiSelectDom.append(optStr);
    });
    busiSelectDom.multiselect('rebuild').multiselect('refresh');
    return resp;
}

function chnlSelectPrint(busiSelectDom) {
    const resp = sendGetReqOfChnl();
    if (resp == null) {
        return null;
    }
    resp.forEach((tp, index) => {
        if (tp.chnlNo != '800') {
            const optStr = `<option value = '${tp.chnlNo}'>${tp.chnlNo + '-' + tp.chnlName}</option>`;
            busiSelectDom.append(optStr);
        }

    });
    busiSelectDom.multiselect('rebuild').multiselect('refresh');
    return resp;
}

function chnlMap() {
    const resp = sendGetReqOfChnl();
    if (resp == null) {
        return null;
    }
    var chnlMap = new Map();
    resp.forEach((tp, index) => {
        chnlMap.set(tp.chnlNo, tp.chnlName)
    });
    return chnlMap;
}


function sendGetReqOfTranStat() {
    const info = sendGetOfAjax(ctx + '/comQuery/data/tranStat', false);
    if (info != undefined && info != null) {
        return info;
    }
    return null;
}

function tranStatSelect(busiSelectDom, needMap) {
    const resp = sendGetReqOfTranStat();
    if (resp == null) {
        return null;
    }
    if (needMap && needMap == true) {
        var tranStatMap = new Map();
    }

    resp.forEach((tp, index) => {
        if (busiSelectDom) {
            const optStr = `<option value = '${tp.code}'>${tp.msg}</option>`;
            busiSelectDom.append(optStr);
        }
        if (needMap && needMap == true) {
            tranStatMap.set(tp.code, tp.msg);
        }
    });
    if (busiSelectDom) {
        busiSelectDom.multiselect('rebuild').multiselect('refresh');
    }
    return tranStatMap;
}

function sendGetReqOfOffice() {
    const info = sendGetOfAjax(ctx + '/comQuery/data/officeList', false);
    if (info != undefined && info != null) {
        return info;
    }
    return null;
}

/**
 * 获取登录用户的信息
 * @returns {*|null}
 */
function sendGetUserInfo() {
    const info = sendGetOfAjax(ctx + '/comQuery/data/getLoginUserInfo', false);
    if (info != undefined && info != null) {
        return info;
    }
    return null;
}


function officeSelect(busiSelectDom) {
    const resp = sendGetReqOfOffice();
    if (resp == null) {
        return null;
    }
    resp.forEach((tp, index) => {
        const optStr = `<option value = '${tp.id}'>${tp.name}</option>`;
        busiSelectDom.append(optStr);
    });
    busiSelectDom.multiselect('rebuild').multiselect('refresh');
    return resp;
}

function autoDeductSelect(autoDeductDom) {
    var autoDeductMap = new Map();
    autoDeductMap.set('0', '否');
    autoDeductMap.set('1', '是');
    if (autoDeductDom) {
        autoDeductMap.forEach((v, k) => {
            autoDeductDom.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        autoDeductDom.multiselect('rebuild').multiselect('refresh');
    }
    return autoDeductMap;
}

function signStatSelect(signDeductDom) {
    var signStatMap = new Map();
    signStatMap.set('00', '已签约');
    signStatMap.set('10', '已解约');
    if (signDeductDom) {
        signStatMap.forEach((v, k) => {
            signDeductDom.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        signDeductDom.multiselect('rebuild').multiselect('refresh');
    }
    return signStatMap;
}

function batStatSelect(batStatDom, type) {
    var batStatMap = new Map();
    if (type == 'list') {
        batStatMap.set('00', '待启用');
        batStatMap.set('01', '已启用');
    } else {
        batStatMap.set('00', '待缴费');
        batStatMap.set('01', '已缴费');
    }
    batStatMap.set('02', '已作废');
    batStatMap.set('03', '已过期');
    if (batStatDom) {
        batStatMap.forEach((v, k) => {
            batStatDom.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        batStatDom.multiselect('rebuild').multiselect('refresh');
    }
    return batStatMap;
}


function strIsBlank(str) {
    return str == undefined || str == null || str == '';
}

function payTpMap() {
    var payMap = new Map();
    payMap.set('0', '现金');
    payMap.set('1', '转账');
    payMap.set('2', '微信');
    payMap.set('3', '支付宝');
    payMap.set('4', 'pos');
    payMap.set('5', '本行卡支付');
    return payMap;
}

function errStatMap() {
    var errMap = new Map();
    errMap.set('00', '未处理');
    errMap.set('01', '已处理');
    errMap.set('02', '处理失败');
    errMap.set('03', '处理超时');
    return errMap;
}


function getOrDefaltOfMap(map, k) {
    if (map instanceof Map) {
        if (map.has(k)) {
            return map.get(k);
        }
    }
    return k;
}

function accSub(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split('.')[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split('.')[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    n = r1 >= r2 ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}


