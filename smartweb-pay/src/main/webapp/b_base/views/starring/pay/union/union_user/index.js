$(document).ready(function () {
    $("a[href^='#tab_']").each(function (i) {
        $(this).click(function (e) {
            showTabs($(this).attr("href"), $(this).attr("url"));
            e.preventDefault();
        });
    });

    let data = {};
    data.validFlg = '0'

    const info = sendPostOfAjaxNoContent(ctx + '/union/data/user_appr_count', data);
    const num = info.data;
    if(num > 0){
        $("a[href^='#tab_appr']").click();
    }else{
        $("a[href^='#tab_list']").click();
    }

});

function showTabs(tabsId, url) {
    var $tabContent = $(tabsId);
    $("a[href='" + tabsId + "']").tab('show');
    $tabContent.find("iframe").attr("src", url);
    $tabContent.siblings().each(function (i) {
        $(this).find("iframe").hide();
    });
    if (tabsId == "#tab_list") {
        $("a[href='#tab_list']").show();
        $("a[href='#tab_add']").show();
        $("a[href='#tab_appr']").hide();
        $("a[href='#tab_info']").hide();
        $("a[href='#tab_update']").hide();
    } else {
        $("a[href='" + tabsId + "']").show();
        //如果是人员审批 只显示人员审批界面
        if(tabsId == "#tab_appr"){
            $("a[href='" + tabsId + "']").parent().siblings().each(function (i) {
                    $(this).find("a").hide();
            });
        }else{
            //siblings()返回所选元素的的所有同级元素
            $("a[href='" + tabsId + "']").parent().siblings().each(function (i) {
                if ($(this).find("a").attr("href") != "#tab_list") {
                    $(this).find("a").hide();
                }
            });
        }

    }
}

/**
 * 查询后不弹窗展示结果
 * @param url
 * @param data
 * @param isRefresh
 * @param tableId
 * @param successMethod
 * @param errorMthod
 * @returns {*}
 */
function sendPostOfAjaxNoContent(url, data, isRefresh, tableId, successMethod, errorMthod) {
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
