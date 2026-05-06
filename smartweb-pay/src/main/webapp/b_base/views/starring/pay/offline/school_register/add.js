$(function () {
    parent.window.$("#iframe_add").show();
    $("#back").on("click", function () {
        parent.window.$("a[href^='#tab_list']").click();
    });
    $("#from").on("submit", function (event) {
        event.preventDefault();
        const data = $("#from").serializeObject();
        data.operStat = "1";
        sendPostOfAjax(ctx + "/school/register/data/modify", data, false, true, function () {
            parent.window.$("a[href^='#tab_list']").click();
        });
    });
});
