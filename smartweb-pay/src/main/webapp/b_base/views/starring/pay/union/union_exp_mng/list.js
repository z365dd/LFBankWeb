let $table;
let $from;
let busiM = new Map();
let apprStatM = new Map();
let expStatM = new Map();
let statMap = new Map();
let batFlg = false;
//全局变量 存放被选中的数据
let selectRows = [];

/**
 * 审批状态
 * @returns {Map<any, any>}
 */
function apprStatMap() {
    var map = new Map();
    map.set('00', '待提交');
    map.set('01', '审批中');
    map.set('02', '审批通过');
    map.set('03', '待补充材料');
    map.set('04', '拒收');
    return map;
}

/**
 * 项目类型
 * @returns {Map<any, any>}
 */
function expStatMap() {
    var map = new Map();
    map.set('00', '未报销');
    map.set('01', '已报销');
    return map;
}




$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#expMngTable');
    $from = $('#from');

    //审批状态
    apprStatM = apprStatMap();
    //报销状态
    expStatM = expStatMap();



    initInstTable();
    chgHeight();


    $('#qryBtn').click(function () {
        initInstTable();
    });

    $('#batDisburse').click(function () {
        batFlg = true;
        showMsgModal();
    });


    //确认批量退费
    $("#confirmBtn").click(function () {
        let data = {};
        //如果是批量退款
        if(batFlg){
            const keysToRemove = ["bill_URL", "case_HIS_URL","cert_URL","pay_ACCT_URL","stat","exp_STAT","reg_DATE","ret_MSG","short_RMRK","mid_RMRK","long_RMRK"];
            const result = selectRows.map((item) => {
                return Object.keys(item).reduce((acc, key) => {
                    if (!keysToRemove.includes(key)) {
                        acc[key] = item[key];
                    }
                    return acc;
                }, {});
            });
            console.info("全选内容");
            console.info(result);
            data.selectedData = JSON.stringify(result);
        }else{ //如果不是批量退款
            let req = {};
            let list = [];
            req.plat_DATE = selectRows[0].plat_DATE;
            req.plat_SEQ = selectRows[0].plat_SEQ;
            req.busi_NO = selectRows[0].busi_NO;
            req.busi_NAME = selectRows[0].busi_NAME;
            req.name = selectRows[0].name;
            req.cert_NO = selectRows[0].cert_NO;
            req.pay_ACCT = selectRows[0].pay_ACCT;
            req.phone_NO = selectRows[0].phone_NO;
            req.amt = selectRows[0].amt;
            list.push(req);

            data.selectedData = JSON.stringify(list);

        }

        data.vrfyNoCrtId = getI('vrfyNoCrtId');
        data.vrfyNo = getI('vrfyNo');
        //传递预退款列表数据到后端 进行退款
        sendPostOfAjax(ctx + '/union/data/batDisburse', data, false, true, () => {
            showContent('批量出账成功，详情请查看退款查询');
            //清空退款列表
            selectRows = [];
            setI('vrfyNo', '');
            $("#msgModal").modal('hide');
            batFlg = false;
            refreshTable();

        })
    });

});

function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}


function queryParams(params) {

    let formData = $from.serializeObject();
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

const initInstTable = () => {
    const url = ctx + '/union/data/appr_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#expMngTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
        pageSize: 10,
        // 全选时候将所有不重复的数据添加到
        onCheckAll: function (row) {
            allCheckAddSelectRows(row);
            console.info('全选方法');
            console.info(selectRows);
        },
        //取消全选时将所有不重复的数据在全局变量中删除
        onUncheckAll: function (row) {
            delSelectRowsAllUnCheck(row);
            console.info('取消全选');
            console.info(selectRows);
        },
         onCheck: function (row, $element) {
            addElementToSelectRows(row);
            console.info('复选框选中');
            console.info(selectRows);
        },
        //复选框取消选中时从数据中移除
        onUncheck: function (row, $element) {
            addElementToSelectRows(row);
            console.info('复选框取消选中');
            console.info(selectRows);

        },
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                total = data.total;
                rows = data.data;
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


const tableColumns = () => {
    return [
        {
            field: 'checked', align: 'center', checkbox: true, formatter: function (value, row) {
                if (selectRows.some(item => item.plat_DATE === row.plat_DATE
                    && item.plat_SEQ === row.plat_SEQ)) {
                    return {
                        checked: true
                    }
                }
                return value;
            }
        },
        {field: 'reg_DATE', title: '申请日期'},
        {field: 'busi_NAME', title: '单位名称'},
        {field: 'name', title: '客户名称'},
        {field: 'cert_NO', title: '身份证号码'},
        {field: 'phone_NO', title: '手机号码'},
        {field: 'pay_ACCT', title: '银行卡号'},
        {
            field: 'exp_STAT', title: '报销状态', formatter: (value, row) => {
                const expStat = row.exp_STAT;
                return getOrDefaltOfMap(expStatM, expStat);
            }
        },
        {field: 'amt', title: '报销金额'},
        {field: 'ret_MSG', title: '出账结果'},
        {field: 'action', title: '操作', formatter: action}
    ];
}


function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' style="display:${row.exp_STAT === '00' ? '' : 'none'} " onclick="disburse('${Base64.encode(JSON.stringify(row))}')">出账</button>`
    return btnhtml;
}

/**
 * 出账
 * @param row
 */
function disburse(row) {
    const decodeRow = JSON.parse(Base64.decode(row));
    selectRows.push(decodeRow);
    batFlg = false;
    showMsgModal();
}


/**
 * 输入短信验证码窗口展示
 */
function showMsgModal(){
    let data = {};
    //todo 展示业务编号
    data.busiNo = getI('busiNo');
    data.busiName = getI('busiName');
    /*sendPostOfAjaxNoShowContent(ctx + '/offline/data/sendMsg', data, false, true, (data) => {
        showTip('短信验证码已发送到你的手机', 'success', 1000, 0);
        setI('vrfyNoCrtId', data.data.vrfy_NO_CRT_ID);
        $("#msgModal").modal({
            backdrop: "static",
            show: true
        });
        $("#msgModal").modal('show');
    })*/

    //页面展示
    showTip('短信验证码已发送到你的手机', 'success', 1000, 0);
    $("#msgModal").modal({
        backdrop: "static",
        show: true
    });
    $("#msgModal").modal('show');

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

//全局变量中添加元素
function addElementToSelectRows(elementItem) {
    //如果报销状态不是已报销
    if(elementItem.exp_STAT !== '01'){
        if (selectRows.length > 0) {
            var isExists = false;
            selectRows.forEach((item, value) => {
                if (item.plat_DATE === elementItem.plat_DATE
                    && item.plat_SEQ === elementItem.plat_SEQ) {
                    isExists = true;
                }
            })
            //如果没有重复数据并且报销状态为未报销
            if (isExists === false) {
                selectRows.push(elementItem);
            }
        } else {
            selectRows.push(elementItem);
        }
    }

}

//全局变量中删除元素
function deleElementFromSelectRows(elementItem) {
    if (selectRows.length > 0) {
        selectRows.forEach((item, key) => {
            if (item.plat_DATE === elementItem.plat_DATE
                && item.plat_SEQ === elementItem.plat_SEQ)  {
                selectRows.splice(key, 1);
            }
        })
    }
}






