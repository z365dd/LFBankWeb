$(document).ready(function () {
    $("a[href^='#tab_']").each(function (i) {
        $(this).click(function (e) {
            showTabs($(this).attr("href"), $(this).attr("url"));
            e.preventDefault();
        });
    });
    $("a[href^='#tab_list']").click();
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
        $("a[href='#tab_add']").hide();
    } else {
        $("a[href='" + tabsId + "']").show();
        //siblings()返回所选元素的的所有同级元素
        $("a[href='" + tabsId + "']").parent().siblings().each(function (i) {
            if ($(this).find("a").attr("href") != "#tab_list") {
                $(this).find("a").hide();
            }
        });
    }
}
