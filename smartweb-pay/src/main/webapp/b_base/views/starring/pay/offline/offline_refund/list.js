let $table;
let $preRefundTable;
let $from;
let $querydate;
let busiM = new Map();
let projM = new Map();
let payTpM = new Map();
let rfndAmtCheck = true;
let decodeRow = {};
let avaAmt;
//全局变量 存放被选中的数据
let selectRows = [];

$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#table');
    $preRefundTable = $('#preRefundTable');

    $from = $('#from');
    //选择框内容查询
    busiNo();
    projName();
    payTpM = payTpMap();
    //详情页返回后展示最近一次查询的记录
    if ($querydate.innerHTML.length > 0) {
        var queryData = JSON.parse($querydate.innerHTML);
        setS('busiNo', queryData.busiNo);
        setS('projName', queryData.projName);
        setI('oweMoth', queryData.oweMoth);
        setI('payNo', queryData.payNo);
        setI('strDate', queryData.strDate);
        setI('endDate', queryData.endDate);
    }

    //待退款账单明细查询
    initInstTable();

    //预退款列表初始化
    initPreRefundTable();

    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
        //点击查询的时候清空
        selectRows = [];
    });
    if (busiM == undefined && busiM.size == 0) {
        busiNo();
        projName();
    }
    $("#busiNo").on("change", function (e) {
        if (busiM == undefined && busiM.size == 0) {
            busiNo();
        }
        projName();
    });
    //项目名称变更字段获取项目名称列表
    $("#projName").on("change", function (e) {
        if (projM == undefined || projM == undefined) {
            projName();
        }
    });

    $('#export').click(function () {
        top.$.jBox.confirm("是否要导出账单明细查询数据?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/offline/data/export");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportDtl();
            }
        })
    });


    //收费项目名称校验
    document.getElementById("rfndAmt").addEventListener("blur", function (event) {
        const rfndAmt = getI("rfndAmt");
        if (rfndAmt < 0.01) {
            showTip('退款金额不能为0', 'error', 1000, 0);
            rfndAmtCheck = false;
            return;
        }
        if (accSub(rfndAmt, avaAmt) > 0) {
            showTip('退款金额不能大于可退金额', 'error', 1000, 0);
            rfndAmtCheck = false;
            return;
        }
        var validate = /^\d+(\.\d{1,2})?$/;
        if (!validate.test(rfndAmt)) {
            showTip('请输入正确的金额格式', 'error', 1000, 0);
            rfndAmtCheck = false;
            return;
        }
        rfndAmtCheck = true;
    });

    //批量退款按钮发送短信
    /*$("#refundBtn").click(function () {
        let flg = true;
        selectRows.forEach((item, index) => {
            if (item.rfndAmt === undefined || item.rfndAmt <= 0) {
                showContent('退款列表中第' + (index + 1) + '条数据请正确填写退款金额', 'error');
                flg = false;
            }
        })
        if (flg === false) {
            return;
        }
        let data = {};
        data.selectedData = JSON.stringify(selectRows);
        //传递预退款列表数据到后端 进行退款
        sendPostOfAjax(ctx + '/offline/data/batRefund', data, false, true, () => {
            showContent('批量退款成功!');
            //清空退款列表
            selectRows = [];
            $("#myModal").modal('hide');
        })

    })*/

    //打开预退款列表
    $("#batRefund").click(function () {
        $("#myModal").modal('show');
        //展示组装预退款列表
        showRefundTable()
    });

    //刷新列表但是页码保留
    $("#cancelBtn").click(function () {
        $("#myModal").modal('hide');
        //清空预退款列表
        clearTable();
        let options = $table.bootstrapTable('getOptions');
        console.info(options);
        const pageNum = options.pageNumber;
        refreshTable();
        $table.bootstrapTable('selectPage', pageNum);
    });

    /**
     * 发送短信验证码
     */
    $('#sendMsgBtn').click(function () {
        if (selectRows.length === 0) {
            return;
        }
        $("#myModal").modal('hide');
        //清空预退款列表
        clearTable();

        var data = {};
        data.busiNo = getI('busiNo');
        data.busiName = getI('busiName');
        sendPostOfAjax1(ctx + '/offline/data/sendMsg', data, false, true, (data) => {
            showTip('短信验证码已发送到你的手机', 'success', 1000, 0);
            setI('vrfyNoCrtId', data.data.vrfy_NO_CRT_ID);
            $("#msgModal").modal({
                backdrop: "static",
                show: true
            });
            $("#msgModal").modal('show');
        })
    });


    //确认批量退费
    $("#refundConfm").click(function () {
        let data = {};
        data.selectedData = JSON.stringify(selectRows);
        data.vrfyNoCrtId = getI('vrfyNoCrtId');
        data.vrfyNo = getI('vrfyNo');
        //传递预退款列表数据到后端 进行退款
        sendPostOfAjax(ctx + '/offline/data/batRefund', data, false, true, () => {
            showContent('批量退款成功，详情请查看退款查询');
            //清空退款列表
            selectRows = [];
            setI('vrfyNo', '');
            $("#msgModal").modal('hide');
            refreshTable();

        })
    });


    //短信验证码输入界面 返回按钮
    $("#cancelBtn2").click(function () {
        //清空标识
        setI('vrfyNoCrtId', '');
        $("#msgModal").modal('hide');
        setI('vrfyNo', '');
    });


    //添加退款数据到待退款列表
    $("#save").click(function () {
        const rfndAmt = getI("rfndAmt");
        checkAmt(avaAmt, rfndAmt);
        if (!rfndAmtCheck) {
            return;
        }
        decodeRow.refund = rfndAmt;
        //保存数据
        addElementToSelectRows(decodeRow);

        //关闭退款信息modal
        $("#refundModal").modal('hide');
        setI('rfndAmt', '');
        //清空变量
        decodeRow = {};
        let options = $table.bootstrapTable('getOptions');
        console.info(options);
        const pageNum = options.pageNumber;
        refreshTable();
        $table.bootstrapTable('selectPage', pageNum);
    });

    //关闭退款信息
    $("#cancelBtn1").click(function () {
        //关闭退款信息modal
        $("#refundModal").modal('hide');
        setI('rfndAmt', '');
        //清空变量
        decodeRow = {};
    });


});

function clearTable(){
    if (selectRows.length > 0) {
        var table = document.getElementById("preRefundTable");
        selectRows.forEach((item, index) => {
            table.deleteRow(1);
        })
    }
}


//初始化预退款列表数据
function dealTable() {
    var table = document.getElementById("preRefundTable");
    selectRows.forEach((item, index) => {
        let lastRow = table.insertRow(-1);
        let busiNo = item.busi_NO;
        lastRow.insertCell(0).innerHTML = item.plat_DATE === undefined ? '' : item.plat_DATE;
        lastRow.insertCell(1).innerHTML = item.proj_NAME === undefined ? '' : item.proj_NAME;
        lastRow.insertCell(2).innerHTML = item.owe_MONTH === undefined ? '' : item.owe_MONTH;
        lastRow.insertCell(3).innerHTML = item.stu_CLASS === undefined ? '' : item.stu_CLASS;
        lastRow.insertCell(4).innerHTML = item.pay_NO === undefined ? '' : item.pay_NO;
        lastRow.insertCell(5).innerHTML = item.name === undefined ? '' : item.name;
        lastRow.insertCell(6).innerHTML = item.tot_AMT === undefined ? '' : item.tot_AMT;
        lastRow.insertCell(7).innerHTML = item.prctl_AMT === undefined ? '' : item.prctl_AMT;
        let avaAmt = accSub(item.prctl_AMT, item.rfnd_AMT);
        lastRow.insertCell(8).innerHTML = avaAmt;
        lastRow.insertCell(9).innerHTML = item.refund;
        //原计划在预退款列表中填写退款金额
        /*lastRow.insertCell(9).innerHTML = `<input type="text" id="rtnAmt${index}" name="rtnAmt" pattern="[0-9]" onblur="getRfnAmt(this,${index},'${avaAmt}')" style="width:100px" />`;
        setI(`rtnAmt${index}`, item.rfndAmt);*/
        lastRow.insertCell(10).innerHTML = payTpM.get(item.pay_TP);
        lastRow.insertCell(11).innerHTML = `<button onclick="deleteTableRow(this,'${item.busi_NO}','${item.proj_NAME}','${item.owe_MONTH}','${item.pay_NO}')">删除</button>`
    })
}

//删除预退款列表中的某行数据
function deleteTableRow(button, busiNo, projName, oweMonth, payNo) {

    var row = button.parentNode.parentNode;
    var rowIndex = row.rowIndex;
    document.getElementById("preRefundTable").deleteRow(rowIndex);
    //将被删除行的数据传递 全局变量根据该行数据删除掉已被删除的元素
    var data = {};
    data.busi_NO = busiNo;
    data.proj_NAME = projName;
    data.owe_MONTH = oweMonth;
    data.pay_NO = payNo;
    //全局变量中删除
    deleElementFromSelectRows(data);
}


function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '01');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
}


function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}


function projName() {
    const dom = $("select[name='projName']")
    dom.empty();
    var tempMap = new Map();
    tempMap.set('', '请选择');
    tempMap.forEach((v, k) => {
        dom.append(`<option value = '${k}'>${v}</option>`);
    })
    dom.multiselect('rebuild').multiselect('refresh');
    const proInfo = projSelect(dom, getI('busiNo'));

    if (proInfo != undefined && proInfo != null) {
        proInfo.forEach(item => {
            projM.set(item.busiNo, item.projName);
        });
    }
}


//当前业务的缴费项目名称查询
function projSelect(projDom, busiNo) {
    const resp = sendGetOfAjax(ctx + `/offline/data/projNameList?busiNo=${busiNo}`, false);
    if (resp == undefined || resp == null) {
        return null;
    }
    if (resp != undefined && resp != null) {
        resp.forEach((tp, index) => {
            const optStr = `<option value = '${tp.projName}'>${tp.projName}</option>`;
            projDom.append(optStr);
        });
    }
    projDom.multiselect('rebuild').multiselect('refresh');
    return resp;
}


const tableColumns = () => {
    //返回初始化
    return [
        {
            field: 'checked', align: 'center', checkbox: true, formatter: function (value, row) {
                if (selectRows.some(item => item.busi_NO === row.busi_NO
                    && item.proj_NAME === row.proj_NAME
                    && item.owe_MONTH === row.owe_MONTH
                    && item.pay_NO === row.pay_NO)) {
                    return {
                        checked: true
                    }
                }
                return value;
            }
        },
        {field: 'plat_DATE', title: '缴费日期'},
        {field: 'proj_NAME', title: '收费项目'},
        {field: 'owe_MONTH', title: '收费周期'},
        {field: 'stu_CLASS', title: '班级'},
        {field: 'pay_NO', title: '学号'},
        {field: 'name', title: '学生姓名'},
        {field: 'tot_AMT', title: '缴费金额'},
        {field: 'prctl_AMT', title: '实付金额'},
        {
            field: 'rfn_AMT', title: '可退金额', formatter: function (value, row) {
                return accSub(row.prctl_AMT, row.rfnd_AMT);
            }
        },
        {
            field: 'pay_TP', title: '缴费方式', formatter: function (value, row) {
                const payTp = row.pay_TP;
                return payTpM.get(payTp);
            }
        },
        {field: 'action', title: '操作', formatter: action}

    ];
}

//预退款列表标题行初始化
function PreRefundTableColumns() {
    //返回初始化
    return [
        {field: 'plat_DATE', title: '缴费日期'},
        {field: 'proj_NAME', title: '收费项目'},
        {field: 'owe_MONTH', title: '收费周期'},
        {field: 'stu_CLASS', title: '班级'},
        {field: 'pay_NO', title: '学号'},
        {field: 'name', title: '学生姓名'},
        {field: 'tot_AMT', title: '缴费金额'},
        {field: 'prctl_AMT', title: '实付金额'},
        {
            field: 'ktje', title: '可退金额', formatter: function (value, row) {
                return accSub(row.prctl_AMT, row.rfnd_AMT);
            }
        },
        {field: 'rfnAmt', title: '退款金额', formatter: appendDom, width: 100},
        {
            field: 'pay_TP', title: '缴费方式', formatter: function (value, row) {
                const payTp = row.pay_TP;
                console.info(payTp);
                getOrDefaltOfMap(payTpM, payTp);
            }
        },
        {field: 'action', title: '操作', formatter: action}
    ];
}

function appendDom(value, row) {
    return `<input type="text" class="form-control" onblur="getRfnAmt(${row},this.value)" />`;
}

//获取输入的退款金额
function getRfnAmt(input, index) {
    selectRows[index].rfndAmt = input.value;
}

//全选时候将所有不重复的数据添加到全局变量中
function allCheckAddSelectRows(rows) {
    rows.forEach(item => {
        addElementToSelectRows(item);
    })
}

//将所有取消全选的数据从全局变量中删除
function delSelectRowsAllUnCheck(rows) {
    rows.forEach(item => {
        deleElementFromSelectRows(item);
    })
}

const initInstTable = () => {
    const url = ctx + '/offline/data/dtl_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#table', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
        pageSize: 10,
        //复选框选中时保存数据
        /* onCheck: function (row, $element) {
             addElementToSelectRows(row);
             console.info('复选框选中');
             console.info(selectRows);
         },*/
        //复选框取消选中时从数据中移除
        /*onUncheck: function (row, $element) {
            addElementToSelectRows(row);
            console.info('复选框取消选中');
            console.info(selectRows);

        },*/
        // 全选时候将所有不重复的数据添加到
        /*onCheckAll: function (row) {
            allCheckAddSelectRows(row);
            console.info('全选方法');
            console.info(selectRows);

        },*/
        //取消全选时将所有不重复的数据在全局变量中删除
        /*onUncheckAll: function (row) {
            delSelectRowsAllUnCheck(row);
            console.info('取消全选方法');
            console.info(selectRows);

        },*/
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                total = data.total;
                rows = data.data.list;
                if ((total == undefined && total == null) || total == 0 || rows == undefined || rows == null) {
                    total = 0;
                    rows = [];
                }
            }
            return {
                total: total,
                rows: rows
            };
        }
    });
    SmartWeb.bootstrapTable.init(config);
}


//初始化预退款列表
const initPreRefundTable = () => {
    const columns = PreRefundTableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#preRefundTable', columns);
    SmartWeb.bootstrapTable.init(config);
    var table = document.getElementById("preRefundTable");
    //删除第一行
    table.deleteRow(1);
}

function queryParams(params) {

    let formData = $from.serializeObject();
    //默认查询已缴费的缴费账单明细
    formData.stat = '01';
    return {
        ...formData,
        limit: params.limit,
        start: getPage(params)
    };
}

function getPage(params) {
    if (!isNaN(params.offset) || !isNaN(params.limit)) {
        return params.offset / params.limit + 1;
    }
}

const refreshTable = () => {
    const $preClick = $table.parent().parent().find('.page-pre');
    if ($preClick.siblings().length > 1) {
        $preClick.next().click();
    }
    $table.bootstrapTable('refresh');
    chgHeight();
}


function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' style="display:${accSub(row.prctl_AMT, row.rfnd_AMT) > 0 && (row.pay_TP === '1' || row.pay_TP === '5') && row.oper_STAT === '00' ? '' : 'none'} " onclick="preRefund('${Base64.encode(JSON.stringify(row))}' )">预退款</button>`
    return btnhtml;
}

//检查输入的退款金额
function checkAmt(avaAmt, rfndAmt) {
    if (rfndAmt < 0.01) {
        showTip('退款金额不能为0', 'error', 1000, 0);
        return;
    }
    if (accSub(rfndAmt, avaAmt) > 0) {
        showTip('退款金额不能大于可退金额', 'error', 1000, 0);
        return;
    }
    var validate = /^\d+(\.\d{1,2})?$/;
    if (!validate.test(rfndAmt)) {
        showTip('请输入正确的金额格式', 'error', 1000, 0);
        return;
    }
}


//退款按钮
function preRefund(row) {

    decodeRow = JSON.parse(Base64.decode(row));
    avaAmt = accSub(decodeRow.prctl_AMT, decodeRow.rfnd_AMT);

    const data = {};
    data.platDate = decodeRow.plat_DATE;
    data.platSeq = decodeRow.plat_SEQ;
    //根据平台日期和平台流水查询原交易流水 获取对应的插卡人信息
    sendPostOfAjax1(ctx + '/offline/data/getPayerInfo', data, false, '', (res) => {
        console.info(res.data);
        //展示持卡人信息
        $("#refundModal").modal({
            backdrop: "static",
            show: true
        });
        $("#refundModal").modal('show');
        setI('payAcct', res.pay_ACCT);
        setI('payAcctName', res.pay_ACCT_NAME);
    })

}

//展示组装预退款列表
function showRefundTable() {
    $("#myModal").modal({
        backdrop: "static",
        show: true
    });
    $("#myModal").modal('show');

    if (selectRows.length > 0) {
        //初始化数据
        dealTable();
    }
}


function exportDtl() {
    $.ajax({
        url: ctx + '/offline/data/export',
        type: 'POST',
        data: $from.serializeObject()
    });
}

//全局变量中添加元素
function addElementToSelectRows(elementItem) {
    //全局变量最多存放20条数据
    if (selectRows.length >= 20) {
        showContent("待退款列表已满，请进行批量退款");
        return;
    }
    if (selectRows.length > 0) {
        var isExists = false;
        selectRows.forEach((item, value) => {
            if (item.busi_NO === elementItem.busi_NO
                && item.proj_NAME === elementItem.proj_NAME
                && item.owe_MONTH === elementItem.owe_MONTH
                && item.pay_NO === elementItem.pay_NO) {
                isExists = true;
            }
        })
        if (isExists === false) {
            selectRows.push(elementItem);
        }
    } else {
        selectRows.push(elementItem);
    }

}

//全局变量中删除元素
function deleElementFromSelectRows(elementItem) {
    if (selectRows.length > 0) {
        selectRows.forEach((item, key) => {
            if (item.busi_NO === elementItem.busi_NO
                && item.proj_NAME === elementItem.proj_NAME
                && item.owe_MONTH === elementItem.owe_MONTH
                && item.pay_NO === elementItem.pay_NO) {
                selectRows.splice(key, 1);
            }
        })
    }
}


function sendPostOfAjax1(url, data, isRefresh, tableId, successMethod, errorMthod) {
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