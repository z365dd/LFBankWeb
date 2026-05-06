let $from;
let busiM = new Map();
let fieldM = new Map();

$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');
    getStatMap($("select[name='excelStat']"));
    getStatMap($("select[name='queryStat']"));
    fieldM = getField($("select[name='keyNo']"));

    busiNo();
    if (busiM == undefined || busiM.size==0) {
        busiNo();
    }
    $("#busiNo").on("change", function (e) {
        if (busiM == undefined || busiM.size==0) {
            busiNo();
        }
    });

    $('#keyNo').on("change", function (e) {
        setI('keyDesc', fieldM.get(getS('keyNo')));
        //设置  readonly属性
        if (getS('keyNo') == 'SHORT_RMRK' || getS('keyNo') == 'MID_RMRK' || getS('keyNo') == 'LONG_RMRK') {
            $('#keyDesc').removeAttr('readonly');
        } else {
            $('#keyDesc').attr("readonly", "readonly")
        }
    })


    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        save();
    });

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });
});

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

function getStatMap(tpDom) {
    var tempMap = new Map();
    tempMap.set('N', '否');
    tempMap.set('Y', '是');
    if (tpDom) {
        tempMap.forEach((v, k) => {
            tpDom.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        tpDom.multiselect('rebuild').multiselect('refresh');
    }
    return tempMap;
}

function getFieldList() {
    let tempMap = new Map();
    tempMap.set('COMMUNITY', '小区名称');
    tempMap.set('BUID_NO', '楼号');
    tempMap.set('UNIT_NO', '单元编号');
    tempMap.set('ROOM_NO', '房间编号');
    tempMap.set('TOT_AREA', '总面积');
    tempMap.set('UNIT_PRICE', '单价');
    tempMap.set('USE_NUM', '使用数量');
    tempMap.set('CERT_TP', '证件类型');
    tempMap.set('DCT_AMT', '优惠金额');
    tempMap.set('FEE_AMT', '手续费金额');
    tempMap.set('LATE_FEE_AMT', '滞纳金金额');
    tempMap.set('SHORT_RMRK', '短拓展字段');
    tempMap.set('MID_RMRK', '中拓展字段');
    tempMap.set('LONG_RMRK', '长拓展字段');

    return tempMap;
}

function getField(dom) {
    let tempMap = getFieldList();
    if (dom) {
        tempMap.forEach((v, k) => {
            dom.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${k + '-' + v}</option>`);
        })
        dom.multiselect('rebuild').multiselect('refresh');
    }
    setI('keyDesc', tempMap.get(getS('keyNo')));
    return tempMap;
}


//获取当前登录用户的可操作业务
function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '01');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI('busiName', busiM.get(getS('busiNo')))
}

function save() {
    confirmx('是否保存', function () {
        var data = $("#from").serializeObject();
        data.operTp = '1';
        console.info(data);
        sendPostOfAjax(ctx + '/offline/data/tmpl_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}



