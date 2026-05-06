let $table;
let $from;

$(function () {
    parent.window.$("#iframe_list").show();
    window.parent.document.getElementById("comDiv").innerHTML = "";
    $table = $("#merTable");
    $from = $("#from");
    initInstTable();
    chgHeight();
    $("#qryBtn").click(function () {
        refreshTable();
    });
    $("#export").click(function () {
        expExcel();
    });
});

function chgHeight() {
    const height = $(document.body).height();
    $(window.parent.document).find("#tab_list").find("iframe").height(height + 80);
}

function queryParams(params) {
    const formData = $from.serializeObject();
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
    const $preClick = $table.parent().parent().find(".page-pre");
    if ($preClick.siblings().length > 1) {
        $preClick.next().click();
    }
    $table.bootstrapTable("refresh");
    chgHeight();
};

const initInstTable = () => {
    const url = ctx + "/school/register/data/list";
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor("#merTable", columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
        pageSize: 10,
        responseHandler: res => {
            let total = 0;
            let rows = [];
            if (res.data !== undefined && res.data !== null) {
                const data = res.data;
                total = data.total;
                rows = data.data;
                if ((total === undefined && total === null) || total === 0 || rows === undefined || rows === null) {
                    total = 0;
                    rows = [];
                }
            }
            return { total: total, rows: rows };
        }
    });
    SmartWeb.bootstrapTable.init(config);
};

const tableColumns = () => {
    return [
        { field: "brch_NAME", title: "所属机构" },
        { field: "busi_NO", title: "业务编号" },
        { field: "busi_NAME", title: "业务名称" },
        { field: "entr_ACCT", title: "清算账户" },
        { field: "entr_ACCT_NAME", title: "清算账户名称" },
        {
            field: "entr_ADDR", title: "所属地区", formatter: (value, row) => {
                const addr = row.entr_ADDR;
                return addr.replaceAll("-", "");
            }
        },
        { field: "entr_TEL_NO", title: "咨询电话" },
        {
            field: "pay_INFO", title: "清算周期", formatter: (value, row) => {
                const payInfo = row.pay_INFO;
                const payInfos = payInfo.split("|");
                if (payInfos[0] === "Y") {
                    return "T1清算";
                }
                if (payInfos[0] === "N") {
                    return "D1清算";
                }
            }
        },
        {
            field: "open_STAT", title: "商户状态", formatter: (value, row) => {
                const operStat = row.open_STAT;
                if (operStat === "Y") {
                    return "已上架";
                }
                if (operStat === "N") {
                    return "已下架";
                }
            }
        },
        { field: "action", title: "操作", formatter: action }
    ];
};

const btnInfos = [{ text: "修改", act: "update" }, { text: "删除", act: "removeRow" }];

function action(value, row) {
    let btnhtml = "";
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' type="button" onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">${btnInfo.text}</button>`;
    });
    return btnhtml;
}

function update(row) {
    window.parent.document.getElementById("comDiv").innerHTML = Base64.decode(row);
    parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/school_register/page/update");
    parent.window.$("a[href^='#tab_update']").click();
}

function removeRow(row) {
    const data = JSON.parse(Base64.decode(row));
    confirmx("确认删除该学校商户吗？", function () {
        sendPostOfAjax(ctx + "/school/register/data/delete", { busiNo: data.busi_NO }, false, true, function () {
            refreshTable();
        });
    });
}

function expExcel() {
    top.$.jBox.confirm("是否要导出学校商户信息?", "系统提示", function (v) {
        if (v === "ok") {
            showLoading();
            $("#from").attr("action", ctx + "/school/register/data/export");
            $("#from").submit();
            $.ajax({
                url: ctx + "/school/register/data/export",
                type: "POST",
                data: $from.serializeObject()
            });
        }
    });
}
let $table;
let $from;

$(function () {
    parent.window.$("#iframe_list").show();
    window.parent.document.getElementById("comDiv").innerHTML = "";
    $table = $("#merTable");
    $from = $("#from");
    initInstTable();
    chgHeight();
    $("#qryBtn").click(function () {
        refreshTable();
    });
    $("#export").click(function () {
        expExcel();
    });
});

function chgHeight() {
    const height = $(document.body).height();
    $(window.parent.document).find("#tab_list").find("iframe").height(height + 80);
}

function queryParams(params) {
    const formData = $from.serializeObject();
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
    const $preClick = $table.parent().parent().find(".page-pre");
    if ($preClick.siblings().length > 1) {
        $preClick.next().click();
    }
    $table.bootstrapTable("refresh");
    chgHeight();
};

const initInstTable = () => {
    const url = ctx + "/school/register/data/list";
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor("#merTable", columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
        pageSize: 10,
        responseHandler: res => {
            let total = 0;
            let rows = [];
            if (res.data !== undefined && res.data !== null) {
                const data = res.data;
                total = data.total;
                rows = data.data;
                if ((total === undefined && total === null) || total === 0 || rows === undefined || rows === null) {
                    total = 0;
                    rows = [];
                }
            }
            return { total: total, rows: rows };
        }
    });
    SmartWeb.bootstrapTable.init(config);
};

const tableColumns = () => {
    return [
        { field: "brch_NAME", title: "所属机构" },
        { field: "busi_NO", title: "业务编号" },
        { field: "busi_NAME", title: "业务名称" },
        { field: "entr_ACCT", title: "清算账户" },
        { field: "entr_ACCT_NAME", title: "清算账户名称" },
        {
            field: "entr_ADDR", title: "所属地区", formatter: (value, row) => {
                const addr = row.entr_ADDR;
                return addr.replaceAll("-", "");
            }
        },
        { field: "entr_TEL_NO", title: "咨询电话" },
        {
            field: "pay_INFO", title: "清算周期", formatter: (value, row) => {
                const payInfo = row.pay_INFO;
                const payInfos = payInfo.split("|");
                if (payInfos[0] === "Y") {
                    return "T1清算";
                }
                if (payInfos[0] === "N") {
                    return "D1清算";
                }
            }
        },
        {
            field: "open_STAT", title: "商户状态", formatter: (value, row) => {
                const operStat = row.open_STAT;
                if (operStat === "Y") {
                    return "已上架";
                }
                if (operStat === "N") {
                    return "已下架";
                }
            }
        },
        { field: "action", title: "操作", formatter: action }
    ];
};

const btnInfos = [{ text: "修改", act: "update" }];

function action(value, row) {
    let btnhtml = "";
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' type="button" onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">${btnInfo.text}</button>`;
    });
    return btnhtml;
}

function update(row) {
    window.parent.document.getElementById("comDiv").innerHTML = Base64.decode(row);
    parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/school_register/page/update");
    parent.window.$("a[href^='#tab_update']").click();
}

function expExcel() {
    top.$.jBox.confirm("是否要导出学校商户信息?", "系统提示", function (v) {
        if (v === "ok") {
            showLoading();
            $("#from").attr("action", ctx + "/school/register/data/export");
            $("#from").submit();
            $.ajax({
                url: ctx + "/school/register/data/export",
                type: "POST",
                data: $from.serializeObject()
            });
        }
    });
}
