function ViewModel(props) {
    const self = this;
    self.modelMap = new Map();
    self.id = props['id'];
    self.data = model;
    self.displayFields = props['displayFields'];

    const keys = Object.keys(self.data);
    $.each(keys, (index, key) => {
        const model = {};
        const $field = $(`#${self.id} [name=${key}]`);
        if ($field !== undefined) {
            switch ($field.prop("tagName")) {
                case 'INPUT':
                    const $element = $(`#${key}`);
                    if ($element.attr('datetime-skin') !== undefined) {
                        $element.val(self.data[key]);
                    }
                    $field.val(self.data[key]);
                    break;
                case 'SELECT':
                    if (self.data[key] !== undefined) {
                        $field.attr('data-value', self.data[key].trim());
                    }
                    loadMultiSelect($field);
                    break;
                default:
                    $field.val(self.data[key]);
                    break;
            }
            Object.defineProperty(model, key, {
                configurable: true,
                set: (value) => {
                    switch ($field.prop("tagName")) {
                        case 'INPUT':
                            const $element = $(`#${key}`);
                            if ($element.attr('datetime-skin') !== undefined) {
                                $element.val(value);
                            }
                            $field.val(value);
                            break;
                        case 'SELECT':
                            $field.multiselect("select", value).multiselect('rebuild').multiselect('refresh');
                            break;
                        default:
                            $field.val(value);
                            break;
                    }

                }
            });
            self.modelMap.set(key, model);
            $field.change(() => {
                self[key] = $field.val();
            });
            if (self.displayFields instanceof Array) {
                $.each(self.displayFields, (index, field) => {
                    $(`#${self.id} [name=${field}]`).attr('disabled', 'true');
                });
            }
        }
    });

    self.get = function (name) {
        return $(`#${self.id} [name=${name}]`).val();
    }

    self.set = function (name, value) {
        self.modelMap.get(name)[name] = value;
    }

    self.disable = function (name) {
        $(`#${self.id} [name=${name}]`).attr('disabled', 'true');
    }

    self.enable = function (name) {
        $(`#${self.id} [name=${name}]`).removeAttr('disabled');
    }

    self.show = function (name) {
        if (name instanceof Array) {
            name.forEach(item => _toggleVisibility(item, 'show'));
        } else {
            _toggleVisibility(name, 'show');
        }
    }

    self.hide = function (name) {
        if (name instanceof Array) {
            name.forEach(item => _toggleVisibility(item, 'hide'));
        } else {
            _toggleVisibility(name, 'hide');
        }
    }

    self.toggleVisibility = function (name) {
        if (name instanceof Array) {
            name.forEach(item => _toggleVisibility(item));
        } else {
            _toggleVisibility(name);
        }
    }

    /**
     * 更改元素可见性
     * @param name 元素
     * @param type hide | show
     * @private
     */
    function _toggleVisibility(name, type) {
        if (name !== undefined) {
            let parent;
            const $e = $(`#${self.id} [name=${name}]`);
            if ($e.prop('tagName') === 'SELECT') {
                parent = $e.parent('span').parent('div').parent('div');
            } else {
                parent = $e.parent('div').parent('div');
            }
            switch (type) {
                case 'show':
                    parent.show();
                    break;
                case 'hide':
                    parent.hide();
                    break;
                default:
                    if (parent.isVisible()) {
                        parent.hide();
                    } else {
                        parent.hide();
                    }
                    break;
            }
        }
    }
}

// 数据绑定
ViewModel.prototype = model;

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
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        data: params,
        async: getAsync,
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