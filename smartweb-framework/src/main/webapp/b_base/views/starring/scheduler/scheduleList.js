let resultData = [];
let $scheduleTable;
$(function () {
    console.info("scheduleList.page");
    parent.window.$("#iframe_list").show();
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
    const Height = $(document.body).height();
    $(window.parent.document).find("#tab_list").find('iframe').attr("isLoaded", "true");
    $(window.parent.document).find("#tab_list").find('iframe').height(Height + 80);
}

function queryParams(params) {
    console.info('queryParams');
    const formData = $("#searchForm").serializeObject();
    return {
        ...formData,
        pgside: 'server',/* 服务器分页 */
        pageSize: params.limit,
        start: params.offset + 1,
        pageNo: getPage(params),
        sort: params.sort,
        order: params.order
    };
}

function getPage(params) {
    if (!isNaN(params.offset) || !isNaN(params.limit)) {
        return params.offset / params.limit + 1;
    }
}

const action = (beanName, type) => {
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
        fetch(ctx + "/sys/schedule/execOnce", beanName, true);
    }
    if (type === "chgStatus") {
        fetch(ctx + "/sys/schedule/chgStatus", beanName);
    }
    if (type === 'logDetail') {
        console.info("open log tab");
        parent.window.$("a[href^='#tab_log']").attr("url", ctx + "/sys/schedule/logPage?beanName=" + beanName);
        parent.window.$("a[href^='#tab_log']").click();
    }
}

const fetch = (url, beanName, hideMessage) => {
    $.post(url, { "beanName": beanName }, function (data) {
        if (data.returnCode !== undefined && "0000" !== data.returnCode) {
            const errMsg = "错误信息[" + data.message + "]";
            showTip(errMsg, "error");
        } else {
            if (!hideMessage) {
                const Msg = "提示:[" + data.message + "]";
                showContent(Msg, "success");
            }
            refreshTable();
        }
    }, "json");
}

const refreshTable = () => {
    const $preClick = $scheduleTable.parent().parent().find(".page-pre");
    if ($preClick.siblings().length > 1) {
        $preClick.next().click();
    }
    $scheduleTable.bootstrapTable('refresh');
    chgHeight();
}

const initInstTable = (id) => {
    const url = ctx + "/sys/schedule/list";
    const columns = scheduleTableColumns();
    let config = SmartWeb.bootstrapTable.constructor("#scheduleTable", columns, url, queryParams, renderSubtable);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
        pageSize: 10,
    });
    SmartWeb.bootstrapTable.init(config);
    /* $("#scheduleTable").on('load-success.bs.table', function (data) {
         SmartWeb.swJS.index.init({id:'scheduleTable'},{dftlen:30});
     });*/
}

const renderSubtable = (index, row, $detail) => {
    console.log('_myInitSubTable ...... row=');
    console.log(row);
    const parentid = row.MENU_ID;
    // 注意这个'table'不是一个id，他在任何情况下不需要改变
    const cur_table = $detail.html('<table id="' + row.id + '" class="table sub-table"></table>').find('table');
    cur_table.attr('id', row.id);
    $(cur_table).bootstrapTable({
        url: ctx + "/sys/schedule/getLog",
        method: 'post',
        queryParams: {
            beanName: row.beanName
        },
        //ajaxOptions:{strParentID:parentid},
        pagination: false,
        clickToSelect: true,
        detailView: true,
        uniqueId: "MENU_ID",
        striped: false,
        sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,						//初始化加载第一页，默认第一页
        pageSize: 5,						//每页的记录行数（*）
        pageList: [5, 10, 20],			//可供选择的每页的行数（*）
        columns: subTableColumns(),
        showHeader: true,
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
            var rows = data.rows;
            for (var i = 0; i < rows.length; i++) {
                $(".detail").hide();
                $('#' + rows[i].jobId + " tbody tr td:first-child").each(function () {
                    $(this).hide();
                });
            }
        },
        detailFormatter: function detailFormatter(index, row) {
            return row.errMsgStr;
        }
    });
}

const subTableColumns = () => {
    return [
        { field: 'platSeq', title: '流水号' },
        { field: 'ip', title: 'IP' },
        { field: 'port', title: '端口' },
        { field: 'strTime', title: '开始时间' },
        { field: 'endTime', title: '结束时间' },
        {
            field: 'succSwitchFlg', title: '运行情况', formatter: function (value, row, index) {
                return value === '0' ? '成功' : '失败';
            }
        },
        {
            field: '', title: '返回信息', formatter: function (value, row, index) {
                return row.succSwitchFlg === '0' ? '' : '<a href="#" id="' + row.id + '" onclick="viewMsg(\'' + row.jobId + '\',\'' + row.id + '\',\'' + index + '\');">异常信息</a>';
            }
        }
    ];
}

const viewMsg = (jobId, id, index) => {
    const $table = $('#' + jobId);
    const $subtable = $('#' + id);
    const text = $subtable.html();
    if (text === '异常信息') {
        $subtable.html('收起');
        $table.bootstrapTable('expandRow', index);
    } else {
        $subtable.html('异常信息');
        $table.bootstrapTable('collapseRow', index);
    }
}

const errorMsgFormatter = (index, row) => {
    const html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

const scheduleTableColumns = () => {
    return [
        { field: 'name', title: '中文名称' },
        { field: 'beanName', title: '处理类', width: 400 },
        { field: 'efftTime', title: '生效时间' },
        { field: 'invlTime', title: '失效时间' },
        { field: 'statStr', title: '状态' },
        {
            field: 'runSwitchFlg', title: '启动时执行', formatter: function (value, row, index) {
                return value === '1' ? '是' : '否';
            }
        },
        { field: 'rmrk', title: '说明', width: 400 },
        {
            field: 'action', title: '操作', formatter: function (value, row, index) {
                return SmartWeb.bootstrapTable.colspanBtn(value, row, index, '运行情况', '收起');
            }
        }
    ];
}

