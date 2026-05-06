var resultData = [];
var $scheduleTable;
$(function () {
    console.info("scheduleLogList.page");
    parent.window.$("#iframe_log").show();
    $scheduleTable = $("#scheduleTable");

    initInstTable();
    chgHeight();

    $("#btnSearch").click(function () {
        if (proof()) {
            refreshTable();
        }
    });
});

function chgHeight() {
    var Height = $(document.body).height();
    $(window.parent.document).find("#tab_list").find('iframe').attr("isLoaded", "true");
    $(window.parent.document).find("#tab_list").find('iframe').height(Height + 200);
}

function queryParams(params) {
    console.info('queryParams');
    var formData = $("#searchForm").serializeObject();
    return {
        pgside: 'server',/* 服务器分页 */
        pageSize: params.limit,
        start: params.offset + 1,
        pageNo: getPage(params),
        sort: params.sort,
        order: params.order,
        beanName: $('#beanName').val(),
        strTime: formData.strTime,
        endTime: formData.endTime,
        succSwitchFlg: formData.succSwitchFlg,
        ip: formData.ip,
        platSeq: formData.platSeq
    };
}

function getPage(params) {
    if (!isNaN(params.offset) || !isNaN(params.limit)) {
        return params.offset / params.limit + 1;
    }
}

function action(beanName, type) {
    if (type === "detail") {
        console.info("open detail tab");
        parent.window.$("a[href^='#tab_detail']").attr("url", ctx + "/sys/schedule/detailPage?beanName=" + beanName);
        parent.window.$("a[href^='#tab_detail']").click();
    }
    if (type === "update") {
        console.info("open Update tab");
        parent.window.$("a[href^='#tab_update']").attr("url", ctx + "/sys/schedule/updatePage?beanName=" + beanName);
        parent.window.$("a[href^='#tab_update']").click();
    }
    if (type === "delete") {
        console.info("delete schedule");
        confirmx('是否删除该自动任务', function () {
            fetch(ctx + "/sys/schedule/delete", beanName);
        });
    }
    if (type === "execOnce") {
        fetch(ctx + "/sys/schedule/execOnce", beanName);
    }
    if (type === "chgStatus") {
        fetch(ctx + "/sys/schedule/chgStatus", beanName);
    }
}

function fetch(url, beanName) {
    $.post(url, { "beanName": beanName }, function (data) {
        if (data.returnCode !== undefined && "0000" !== data.returnCode) {
            var errMsg = "错误信息[" + data.message + "]";
            showTip(errMsg, "error");
        } else {
            var Msg = "提示:[" + data.message + "]";
            showContent(Msg, "success");
            refreshTable();
        }
    }, "json");
}

function refreshTable() {
    var $preClick = $scheduleTable.parent().parent().find(".page-pre");
    if ($preClick.siblings().length > 1) {
        $preClick.next().click();
    }
    $scheduleTable.bootstrapTable('refresh');
    chgHeight();
}

function initInstTable(id) {
    var url = ctx + "/sys/schedule/getLog";
    var columns = scheduleTableColumns();
    var config = SmartWeb.bootstrapTable.constructor("#scheduleTable", columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 800,
        pageSize: 10,
        pagination: true,
        detailView: true,
        striped: false,//是否显示行间隔色
        rowStyle: function rowStyle(row, index) {
            if (row.succSwitchFlg === '0') {
                return {
                    classes: 'success'
                };
            }
            return {
                classes: 'danger'
            };
        },
        onLoadSuccess: function (data) {
            $(".detail").hide();
            $("#scheduleTable tbody tr td:first-child").each(function () {
                $(this).hide();
            });
        },
        detailFormatter: function detailFormatter(index, row) {
            return row.errMsgStr;
        }
    });
    SmartWeb.bootstrapTable.init(config);
}

function viewMsg(jobId, id, index) {
    var $table = $('#' + jobId);
    var $subtable = $('#' + id);
    var text = $subtable.html();
    if (text === '异常信息') {
        $subtable.html('收起');
        $table.bootstrapTable('expandRow', index);
    } else {
        $subtable.html('异常信息');
        $table.bootstrapTable('collapseRow', index);
    }
}

function errorMsgFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

function scheduleTableColumns() {
    return [
        { field: 'beanName', title: '处理类', width: 400 },
        { field: 'platSeq', title: '流水号' },
        { field: 'ip', title: 'IP' },
        { field: 'port', title: '端口' },
        { field: 'strTime', title: '开始时间' },
        { field: 'endTime', title: '结束时间' },
        {
            field: 'action', title: '操作', formatter: function (value, row, index) {
                if (row.succSwitchFlg === '0') {
                    return '';
                }
                return SmartWeb.bootstrapTable.colspanBtn(value, row, index, '异常', '收起');
            }
        }
    ];
}

