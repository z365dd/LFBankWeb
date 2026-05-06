$(function () {
    parent.window.$("#iframe_update").show();
    const encodeData = window.parent.document.getElementById("comDiv").innerText;
    if (encodeData) {
        const data = JSON.parse(encodeData);
        setI("busiNo", data.busi_NO);
        setI("busiName", data.busi_NAME);
        setI("officeId", data.brch_ID);
        setI("officeName", data.brch_NAME);
        setI("payAcct", data.entr_ACCT);
        setI("payAcctName", data.entr_ACCT_NAME);
        const payInfos = data.pay_INFO.split("|");
        setI("clrCycle", payInfos[0] === "Y" ? "T1清算" : "D1清算");
        setI("sepaFlg", payInfos[1] === "Y" ? "是" : "否");
        setI("phoneNo", data.entr_TEL_NO);
        setI("name", data.name);
        setS("openStat", data.open_STAT);
    }
    $("#back").on("click", function () {
        parent.window.$("a[href^='#tab_list']").click();
    });
    $("#from").on("submit", function (event) {
        event.preventDefault();
        const data = $("#from").serializeObject();
        data.operStat = "2";
        sendPostOfAjax(ctx + "/school/register/data/modify", data, false, true, function () {
            parent.window.$("a[href^='#tab_list']").click();
        });
    });
});
