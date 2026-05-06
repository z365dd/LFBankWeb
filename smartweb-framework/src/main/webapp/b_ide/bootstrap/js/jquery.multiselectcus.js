//default-single
//$(document).ready(function() {
//$('#default-select').multiselect();
//});

$(document).ready(function () {
    //设置允许模糊查询
    /*改成有页面设置是否过滤
     * $('select').multiselect({
            includeSelectAllOption:true,
            enableFiltering:true,
            filterPlaceholder: '搜索'
        });
        */
    initMultiSelect();
});

/**
 * 支持指定下拉框id列表进行初始化
 * @param ids    1,2,3,
 * @returns
 */
function initMultiSelect(ids) {
    /*$("select[data-role='multiselect']").each(function(i) {
        loadMultiSelect($(this));
    });*/
    if (undefined !== ids) {
        $("select").each(function (i) {
            var $this = $(this);
            var id = $this.attr('id');
            if (undefined !== id && ids.indexOf(id) > -1) {
                loadMultiSelect($this);
            }
        });
    } else {
        $("select").each(function (i) {
            loadMultiSelect($(this));
        });
    }
}

function loadMultiSelect(_this) {
    var dRole = _this.attr("data-role");
    var ulval = _this.attr("data-url");
    var value = _this.attr("data-value");//20190103 add by gongxl 新增multiselect加载后赋初值功能
    var idval = _this.attr("id");
    var isAsync = _this.attr("data-async");//20170914 add by chenyl 新增multiselect同步加载功能
    var role = _this.attr("multiple");
    var blank = _this.attr("blank-item");
    var blank_text = _this.attr("blank-text");
    var blank_value = _this.attr("blank-value");
    console.info('role=' + role + ' , blank=' + blank + ' , blank_text=' + blank_text + ' , blank_value=' + blank_value);
    if (ulval === "" || ulval === undefined) {
        //20170814 add by chenyl for 设置非url获取数据的初始化下拉框
        //20181119 mod by chenyl for 判断原生不调用api，否则会初始化
        if (dRole === "multiselect") {
            _this.multiselect({});
        }
        //20170810 mody chenyl for 只跳过当前循环:break-return false(结束迭代)，continue-return true(跳过当前循环)
        return true;
    }
    if (idval === "" || idval === undefined) {
        //20170810 mody chenyl for 只跳过当前循环:break-return false(结束迭代)，continue-return true(跳过当前循环)
        return true;
    }

    var url = ulval; //mody by chenyl
    var params = {label: "label"};
    //单选时需要增加空选项
    if (role !== 'multiple') {
        var blankText = '--请选择--';
        var blankValue = '';
        if (blank !== undefined && blank === 'true') {
            if (blank_text !== undefined && blank_text !== '') {
                blankText = blank_text;
            }
            if (blank_value !== undefined && blank_value !== '') {
                blankValue = blank_value;
            }
            params = {label: "label", isblank: blank, blankText: blankText, blankValue: blankValue}
        }
    }
    console.info("selectDataUrl=" + url);
    /*if (isAsync=="true") {
        $.ajax({
            type:"post",
            url:url,
            dataType:"json",
            data:params,
            async:false,
            success:function(data){
                if(data.retCode==="0000"){
                    //$("#"+idval).multiselect("destroy");
                    $("#"+idval).multiselect("dataprovider", data.list);
                }
            }
        });
    }else{
        $.post(url,params,function(data){
            if(data.retCode==="0000"){
                //$("#"+idval).multiselect("destroy");
                $("#"+idval).multiselect("dataprovider", data.list);
            }
        },"json");
    }*/
    //20181119 mod by chenyl for 整合一下
    //并增加原生下拉框加载，用于原生下拉框直接调用此方法时使用
    var getAsync = true;
    if (isAsync === "true") {
        getAsync = false;
    }
    //达州添加head校验
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        data: params,
        async: getAsync,
        headers:{"authorization":authorization},
        success: function (data) {
            if (data.retCode === "0000" && data.list !== undefined) {
                if ($("#" + idval).attr("data-role") === "multiselect") {
                    $("#" + idval).multiselect("dataprovider", data.list);

                    //20190103 add by gongxl 新增multiselect加载后赋初值功能
                    if (value != null && value !== "") {
                        $("#" + idval).multiselect("select", value).multiselect('rebuild');
                    }
                } else {
                    //此处为原生下拉框
                    _this.empty();
                    for (var a = 0; a < data.list.length; a++) {
                        var jsonVal = data.list[a];
                        var optionAdd = "<option value='" + jsonVal["value"] + "'";

                        //20190103 add by gongxl 新增multiselect加载后赋初值功能
                        if (value != null && value !== "" && value === jsonVal["value"]) {
                            optionAdd += " selected"
                        }

                        optionAdd += ">" + jsonVal["label"] + "</option>";
                        $("#" + idval).append(optionAdd);
                    }
                }
            }
        }
    });
}

/**
 * 初始化某个元素下的下拉列表
 *
 * @param elementSelector 元素选择器 如 '#id'
 */
function loadSelectUnder(elementSelector) {
    $(elementSelector).find("select").each(function () {
        loadMultiSelect($(this));
    });
}
