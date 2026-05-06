/*
	time:20171219
	author:汤想成
	Effect:获取属性是deta-key input的val
	Y 给应龙写的校验
	Q 给锐新写的
*/
/*获取当前时间*/
var errorBorder = "#F77F5B";
var normalBorder = "#E6EBF5";
$(this).hover(function () {
    $("[data-toggle='tooltip']").tooltip();
})

function hong() {
    $("input").keyup(function () {
        if ($(this).attr("data-toggle") == "tooltip") {
            if ($(this).val().length != "0") {
                $("input[data-toggle='tooltip']").css('border-color', '#9EBED7');
            }
        }
    });
}


function inputcheck(self) {
    var _self = self;
    /* 获取属性值 */
    input_check = {};
    /* 不为空 */
    input_check.notEmpty = $(_self).val(),
        /* 电话号码 */
        input_check.telephone = $(_self).val(),
        /* 固定电话号码 */
        input_check.telephone1 = $(_self).val(),
        /* 身份证 */
        input_check.idCard = $(_self).val(),
        /* 中文 */
        input_check.chinese = $(_self).val(),
        /* IP地址 */
        input_check.ipAddress = $(_self).val(),
        /* 版本 */
        input_check.edition = $(_self).val(),
        /* 英文 */
        input_check.english = $(_self).val(),
        /* 邮件 */
        input_check.mail = $(_self).val(),
        /* 数字和英文 */
        input_check.NAL = $(_self).val(),
        /* 数字和英文还有字符 */
        input_check.character = $(_self).val(),
        /* 密码 */
        input_check.password = $(_self).val(),
        /* 空格 */
        input_check.blank = $(_self).val(),
        /* 整数 */
        input_check.integer = $(_self).val(),
        /* 正常开始时间验证 */
        input_check.startTime_one = $(_self).val(),
        /* 结束时间验证 */
        input_check.endTime_one = $(_self).val()

}

$(function () {
    addValidate();
});

function addValidate() {
    /* 添加红字提示 */
    function Prompt(tipVal, nameId) {
        var script = tipVal;

        if ($("#" + nameId).parent().next("span.warnBlock").length == 0) {
            var tag = "<span id='" + nameId + "Err' class='warnBlock'>" + script + "</span>";
            $("#" + nameId).parent().after(tag);
            $("#" + nameId).removeClass('trueInput');
            $("#" + nameId).css('border-color',errorBorder);
        }

    }

    function lengtPrompt(tipVal, nameId) {
        var script = tipVal;
        $("#" + nameId).parent().next("span.warnBlock").remove();
        if ($("#" + nameId).parent().next("span.warnBlock").length == 0) {
            var tag = "<span id='" + nameId + "Err' class='warnBlock'>" + script + "</span>";
            $("#" + nameId).parent().after(tag);
            $("#" + nameId).removeClass('trueInput');
            $("#" + nameId).css('border-color',errorBorder);
        }

    }


    /* 添加灰色提示 */
    var tag;

    function greyHints(tipVal, nameId) {
        var script = tipVal;
        if (tag == "<span class='promptScript'>输入不能为空</span>") {
            tag = "<span class='promptScript'>" + script + ' ' + "</span>";
            $("#" + nameId).parent().after(tag);
        } else {
            if ($("#" + nameId).parent().next("span.promptScript").length == 0) {
                tag = "<span class='promptScript'>" + script + "</span>";
                $("#" + nameId).parent().after(tag);
            }

        }

    }

    function lengthHints(tipVal, nameId) {
        var script = tipVal;
        $("#" + nameId).parent().next("span.promptScript").remove();
        if ($("#" + nameId).parent().next("span.promptScript").length == 0) {
            var tag = "<span class='promptScript'>" + script + "</span>";
            $("#" + nameId).parent().after(tag);
        }

    }


    function cue(self, nameId) {
        console.log("校验代码读取");
        var tipVal;

        /* 验证input是否为空 */
        if ($(self).attr("check-empty") == "true") {
            var tipVal = '输入不能为空'
            greyHints(tipVal, nameId);
        }


        /* 判断电话号码 */
        if ($(self).attr("check-telephone") == "true") {
            var tipVal = '请输入正确的电话号码'
            greyHints(tipVal, nameId);

        }

        /* 判断固定电话号码 */
        if ($(self).attr("check-telephone1") == "true") {
            var tipVal = '请输入正确的电话号码'
            greyHints(tipVal, nameId);

        }


        /* 身份证号码 */
        if ($(self).attr("check-idCard") == "true") {
            var tipVal = '请输入正确的身份证号码'
            greyHints(tipVal, nameId);
        }

        /* 验证中文 */
        if ($(self).attr("check-chinese") == "true") {
            var tipVal = '请输入中文'
            greyHints(tipVal, nameId);
        }

        /* 验证IP地址 */
        if ($(self).attr("check-ipAddress") == "true") {
            var tipVal = '请输入正确的ip地址'
            greyHints(tipVal, nameId);
        }

        /* 验证版本 */
        if ($(self).attr("check-edition") == "true") {
            var tipVal = '请输入类似1.0或1.0.1或1.0.0.1的版本格式,主版本号不能为0,长度不能大于999'
            greyHints(tipVal, nameId);
        }


        /* 验证纯中文 */
        if ($(self).attr("check-purechinese") == "true") {
            var tipVal = '请输入中文'
            greyHints(tipVal, nameId);
        }


        /* 验证英文 金额币种 */
        if ($(self).attr("check-english") == "true") {
            tipVal = "只能输入大写英文";
            greyHints(tipVal, nameId);
        }

        /* 验证邮件 */
        if ($(self).attr("check-mail") == "true") {
            var tipVal = '请输入正确的邮箱'
            greyHints(tipVal, nameId);
        }

        /* 验证数字和英文 */
        if ($(self).attr("check-NAL") == "true") {
            var tipVal = '只能输入数字和大小写字母'
            greyHints(tipVal, nameId);
        }


        /* 验证数字和英文 */
        if ($(self).attr("check-brackets") == "true") {
            var tipVal = '不能输入尖括号'
            greyHints(tipVal, nameId);
        }

        /* 验证英文、数字、特殊字符 */
        if ($(self).attr("check-character") == "true") {
            var tipVal = '只能输入数字和字母和部分特殊符号'
            greyHints(tipVal, nameId);
        }

        /* 验证密码 */
        if ($(self).attr("check-password") == "true") {
            var tipVal = '请输入非中文字符,长度在6~18之间'
            greyHints(tipVal, nameId);
        }

        /* 验证空格 */
        if ($(self).attr("check-blank") == "true") {
            var tipVal = '不能输入空格'
            greyHints(tipVal, nameId);
        }

        /* 验证非零的正整数 */
        if ($(self).attr("check-integer") == "true") {
            var tipVal = '请输入非零的正整数'
            greyHints(tipVal, nameId);
        }

        /* 验证非负的整数 */
        if ($(self).attr("check-nonNegativeInteger") == "true") {
            var tipVal = '请输入非负的整数'
            greyHints(tipVal, nameId);
        }

        /* 验证是否正则 */
        if ($(self).attr("check-regExp") == "true") {
            var tipVal = '请输入正确的正则表达式'
            greyHints(tipVal, nameId);
        }


        /* 词素专属英文 20181017*/
        if ($(self).attr("check-MorphemeEnglish") == "true") {
            var tipVal = '首字母必须输入大写,可输如英文大小写,空格,点,下划线'
            greyHints(tipVal, nameId);
        }


        /* 验证英文和数字 20181017*/
        if ($(self).attr("check-englishAndNum") == "true") {
            var tipVal = '只能输入大小写英文和数字以及.空格 _'
            greyHints(tipVal, nameId);
        }


        /* 验证请输入3到10*/
        if ($(self).attr("check-TtoT") == "true") {
            var tipVal = '请输入3到10的整数'
            greyHints(tipVal, nameId);
        }


        /* 验证请输入1到5 */
        if ($(self).attr("check-OtoF") == "true") {
            var tipVal = '请输入1-5的整数'
            greyHints(tipVal, nameId);
        }



        /* 验证正常开始时间 */
        if ($(self).attr("check-startTime-one") == "true") {
            var tipVal = '开始时间要小于结束时间'
            greyHints(tipVal, nameId);
        }

        /* 验证结束时间 */
        if ($(self).attr("check-endTime-one") == "true") {
            var tipVal = '开始时间要小于结束时间'
            greyHints(tipVal, nameId);
        }


        /* 验证正常开始时间   开始时间大于当前时间  add qiu*/
        /*if($(self).attr("check-startTime") == "true" ){*/
        if ($(self).is("[check-startTime]")) {
            var tipVal = '时间要大于当前时间'
            greyHints(tipVal, nameId);
        }

        /* 验证结束时间  结束时间不小于开始时间   add qiu   */
        /*if($(self).attr("check-endTime") == "true" ){*/
        if ($(self).is("[check-endTime]")) {
            var attrVal = $(self).attr('check-endTime');
            if ($("[check-startTime='" + attrVal + "']").val() == "") {
                var tipVal = '请先选择开始时间'
                greyHints(tipVal, nameId);
            } else {
                var tipVal = '结束时间不小于开始时间'
                greyHints(tipVal, nameId);
            }

        }


        /* 验证中文数字横线中括号小括号 */
        if ($(self).attr("check-chineseDigitalSymbols") == "true") {
            var tipVal = '只能输入中文、数字、横线、中括号、小括号'
            greyHints(tipVal, nameId);
        }

        /*校验长度 格式16,2*/
        if ($(self).attr("check-lengthFormat") == "true") {
            var tipVal = '请输入格式(16.2。数字 点 数字)，点后面的数不的大于点前面的'
            greyHints(tipVal, nameId);
        }

        if ($(self).attr("check-fixedLength") != undefined && $(self).attr("check-fixedLength") != "false" && $(self).attr("check-fixedLength") != "") {
            var _fixedLength = $(self).attr("check-fixedLength");
            var tipVal = '请输入固定长度,长度为' + _fixedLength + '';
            greyHints(tipVal, nameId);
        }


        /* 验证英文数字下划线 */
        if ($(self).attr("check-alphanumericSymbols") == "true") {
            var tipVal = '只能输入英文、数字、下划线'
            greyHints(tipVal, nameId);
        }

        /* 验证部分特殊符号 */
        if ($(self).attr("check-partCharacter") == "true") {
            var tipVal = '请勿输入单引、双引号、以及斜线'
            greyHints(tipVal, nameId);
        }

        /* 验证部分特殊符号 */
        if ($(self).attr("check-pCharacter") == "true") {
            var tipVal = '请勿输入单引'
            greyHints(tipVal, nameId);
        }


        /* 验证时间不能为空 */
        if ($(self).attr("check-time-empty") == "true") {
            var tipVal = '不能为空'
            greyHints(tipVal, nameId);
        }


        /* 校验整数 正负和0  add qiu */
        if ($(self).attr("check-integer-pos-neg") == "true") {
            var tipVal = '请输入整数'
            greyHints(tipVal, nameId);
        }

        /* 校验double 整数 正负和0  add qiu */
        if ($(self).attr("check-double") == "true") {
        	var tipVal = '请输入浮点数'
        		greyHints(tipVal, nameId);
        }


        /* 校验金额 add qiu */
        if ($(self).attr("check-money") == "true") {
            var tipVal = '请输入金额'
            greyHints(tipVal, nameId);
        }


        if ($(self).attr("check-enSpace") == "true") {
            var tipVal = '只能输入英文允许中间有空格、上引号'
            greyHints(tipVal, nameId);
        }

        if ($(self).attr("check-enAndSpace") == "true") {
            var tipVal = '只能输入英文允许中间有空格、上引号'
            greyHints(tipVal, nameId);
        }

        if ($(self).attr("check-zhSpace") == "true") {
            var tipVal = '只能输入中文允许中间有空格'
            greyHints(tipVal, nameId);
        }


        if ($(self).attr("check-numTwo") == "true") {
            var tipVal = '保留小数点后两位'
            greyHints(tipVal, nameId);
        }


        /* 验证小写英文、数字、特殊字符   20191127 add by lijunbin*/
        if ($(self).attr("check-lowerCharacter") == "true") {
            var tipVal = '只能输入数字、小写字母和部分特殊符号'
            greyHints(tipVal, nameId);
        }


        return tipVal;
    }


    /* 获得焦点 */
    $("input,textarea").focus(function () {
        var self = this;
        var nameId = $(self).attr("id");
        /*最大长度*/
        var _maxlength = $(self).attr("maxlength");
        /*校验定长*/
        var _fixedLength = $(self).attr("check-fixedLength");
        /*最小长度*/
        var _min = $(self).attr("check-minlength");
        inputcheck(self);
        $("#" + nameId).removeClass('trueInput');
        $(self).parent().siblings('span.warnBlock').remove();

        if (_maxlength != undefined || _min != undefined) {
            var tipVal = cue(self, nameId);
            /*判断tipVal是否为空*/
            if (tipVal != undefined) {
                var tipVal = tipVal + '，';
            } else {
                var tipVal = '';
            }

            /* 最大长度提示 */
            if (_maxlength != undefined) {
                if (_min != undefined) {
                    var tipVal = tipVal + '请输入' + _min + "至" + _maxlength + "长度之间";
                    lengthHints(tipVal, nameId);
                } else {
                    var tipVal = tipVal + '不超过' + _maxlength + "个字符";
                    lengthHints(tipVal, nameId);
                }
            } else
                /* 最小长度提示 */
            if (_min != undefined) {
                if (_maxlength != undefined) {
                    var tipVal = tipVal + '请输入' + _min + "--" + _maxlength + "长度之间";
                    lengthHints(tipVal, nameId);
                } else {
                    var tipVal = tipVal + '最小长度' + _min + "个字符";
                    lengthHints(tipVal, nameId);
                }
            }
        } else {
            cue(self, nameId);

        }
    })

    /* 失去焦点 */
    $("input,textarea").blur(function () {
    	console.log("-------blur--------");
        /* 调整指针 */
        var self = this;
        /* 获取点击input的id */
        var nameId = $(self).attr("id");
        /* 获取点击input的val */
        var _val = $(self).val().length;
        /*最大长度*/
        var _maxlength = $(self).attr("maxlength");
        /*定长校验*/
        var _fixedLength = $(self).attr("check-fixedLength");
        /*最小长度*/
        var _min = $(self).attr("check-minlength");
        /* 失去焦点删除灰色提示 */
        $(self).parent().siblings('span.promptScript').remove();
        /* 获取val值 */
        inputcheck(self);

        function redPrompt(self, nameId) {
            var tipVal;
            if ($(self).is(":visible")) {
                if ($(self).attr("disabled") != "disabled") {
                    if ($(self).attr("readonly") == undefined) {
                        /* 验证input是否为空 */
                        if ($(self).attr("check-empty") == "true") {
                            if ($.trim(input_check.notEmpty) == "") {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '输入不能为空'
                                Prompt(tipVal, nameId);
                            } else {
                                $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                $("#" + nameId).addClass('trueInput');
								setNormalBorder(nameId);
                            }
                        }


                        /* 验证电话号码 */
                        if ($(self).attr("check-telephone") == "true") {
                            var reg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
                            if (_val != 0 && !reg.test(input_check.telephone)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正确的电话号码'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }


                        /* 验证固定电话号码 */
                        if ($(self).attr("check-telephone1") == "true") {
                            var reg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
                            if (_val != 0 && !reg.test(input_check.telephone1)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正确的电话号码'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证身份证号码 */
                        if ($(self).attr("check-idCard") == "true") {
                            var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                            if (_val != 0 && !reg.test(input_check.idCard)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正确的身份证号码'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {

                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }

                        }

                        /* 验证最大取值范围,和最小取值范围 */
                        if ($(self).attr("min") || $(self).attr("max")) {
                            var _minVal = Number($(self).attr("min"));
                            var _maxVal = Number($(self).attr("max"));
                            var inputVal = $(self).val() == '' ? -1 : Number($(self).val());
                            /*输入值满足：_minVal <= inputVal <= _maxVal 成立*/
                            if (inputVal >= _minVal && inputVal <= _maxVal) {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            } else {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入' + _minVal + "-" + _maxVal + "之间的数";
                                Prompt(tipVal, nameId);
                            }
                        }

                        /* 验证中文 */
                        if ($(self).attr("check-chinese") == "true") {
                            var reg = /[\u4e00-\u9fa5]/;
                            if (_val != 0 && !reg.test(input_check.chinese)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入中文'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证中文 */
                        if ($(self).attr("check-purechinese") == "true") {
                            var reg = /^[\u4e00-\u9fa5]+$/;
                            if (_val != 0 && !reg.test(input_check.chinese)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入中文'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证IP地址 */
                        if ($(self).attr("check-ipAddress") == "true") {
                            var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                            if (_val != 0 && !reg.test(input_check.ipAddress)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正确的ip地址'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证版本 */
                        if ($(self).attr("check-edition") == "true") {
                            var reg = /^[0-9]{1,3}(\.[0-9]{1,3}){1,3}$/;
                            if (_val != 0 && !reg.test(input_check.edition)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入类似1.0或1.0.1或1.0.0.1的版本格式,主版本号不能为0,长度不能大于999'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val > 0) {
                                    /*字符串变数组 取第0位*/
                                    var arr = input_check.edition.split(".")
                                    var num = Number(arr[0]);
                                    if (num > 0) {
                                        $("#" + nameId).addClass('trueInput');
                                        setNormalBorder(nameId);
                                        return
                                    }
                                } else {
                                    $("#" + nameId).removeClass('trueInput');
                                    var tipVal = '请输入类似1.0或1.0.1或1.0.0.1的版本格式,主版本号不能为0,长度不能大于999'
                                    Prompt(tipVal, nameId);
                                }
                            }
                        }

                        /* 验证英文 金额币种 */
                        if ($(self).attr("check-english") == "true") {
                            var reg = /^[A-Za-z]+$/;
                            if (_val != 0 && !reg.test(input_check.english)) {
                                $("#" + nameId).removeClass('trueInput');
                                /*var tipVal = $(self).parent().siblings("label").text();*/
                                tipVal = "只能输入大小写英文";
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }


                        /* 验证英文和数字 20181017*/
                        if ($(self).attr("check-englishAndNum") == "true") {
                            var reg = /^[A-Za-z0-9.' _\- ]+$/;
                            if (_val != 0 && !reg.test(input_check.english)) {
                                $("#" + nameId).removeClass('trueInput');
                                /*var tipVal = $(self).parent().siblings("label").text();*/
                                tipVal = "只能输入大小写英文和数字以及.空格,下划线";
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 词素专属英文 20181017 */
                        if ($(self).attr("check-MorphemeEnglish") == "true") {
                            var reg = /^(\b[A-Z][a-zA-Z 0-9\-'._]*\s*)*$/;
                            if (_val != 0 && !reg.test(input_check.english)) {
                                $("#" + nameId).removeClass('trueInput');
                                /*var tipVal = $(self).parent().siblings("label").text();*/
                                tipVal = "首字母必须输入大写,可输如英文大小写,空格,点,下划线";
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证邮件 */
                        if ($(self).attr("check-mail") == "true") {
                            var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                            if (_val != 0 && !reg.test(input_check.mail)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正确的邮箱'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证数字和英文 */
                        if ($(self).attr("check-NAL") == "true") {
                            var reg = /^[A-Za-z0-9]+$/;
                            if (_val != 0 && !reg.test(input_check.NAL)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入数字和大小写字母'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证英文、数字、特殊字符 */
                        if ($(self).attr("check-character") == "true") {
                            var reg = /^[a-zA-Z0-9\s!@#$%^&*(){}<>?]+$/;
                            if (_val != 0 && !reg.test(input_check.character)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入数字和字母和部分特殊符号'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证密码 */
                        if ($(self).attr("check-password") == "true") {
                            var reg = /[\u4e00-\u9fa5]/;
                            if (_val != 0 && reg.test(input_check.password)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入非中文字符,长度在6~18之间'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val >= 6 && _val <= 18) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                } else {
                                    $("#" + nameId).removeClass('trueInput');
                                    var tipVal = '请输入非中文字符,长度在6~18之间'
                                    Prompt(tipVal, nameId);
                                }
                            }
                        }


                        /* 验证英文数字下划线 */
                        if ($(self).attr("check-alphanumericSymbols") == "true") {
                            var reg = /^[A-Za-z0-9_']+$/;
                            if (_val != 0 && !reg.test(input_check.blank)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入英文、数字、下划线'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }

                            }
                        }

                        /*不能输入尖括号*/
                        if ($(self).attr("check-brackets") == "true") {
                            var reg = /\<a-zA-Z\>/;
                            if (_val != 0 && reg.test(input_check.blank)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '不能输入成对尖括号'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }

                            }
                        }


                        /*只能输入英文允许中间有空格 */
                        if ($(self).attr("check-enSpace") == "true") {
                            var reg = /^[A-Za-z .']+$/;
                            if (_val != 0 && !reg.test(input_check.blank)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入英文允许中间有空格、上引号'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    var newVal = input_check.blank.trim();
                                    $(self).val(newVal);
                                    //出去头尾空格之后 在重新校验
                                    if (_val != 0 && !reg.test(newVal)) {
                                        $("#" + nameId).removeClass('trueInput');
                                        var tipVal = '只能输入英文允许中间有空格、上引号'
                                        Prompt(tipVal, nameId);
                                    } else {
                                        if (_val != 0) {
                                            console.log(input_check.blank);
                                            $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                            $("#" + nameId).addClass('trueInput');
                                            setNormalBorder(nameId);
                                            return
                                        }
                                    }
                                }
                            }
                        }

                        /*只能输入英文允许中间有空格 */
                        if ($(self).attr("check-enAndSpace") == "true") {
                            var reg = /^[A-Za-z ]+$/;
                            if (_val != 0 && !reg.test(input_check.blank)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入英文允许中间有空格'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    var newVal = input_check.blank.trim();
                                    $(self).val(newVal);
                                    //出去头尾空格之后 在重新校验
                                    if (_val != 0 && !reg.test(newVal)) {
                                        $("#" + nameId).removeClass('trueInput');
                                        var tipVal = '只能输入英文允许中间有空格'
                                        Prompt(tipVal, nameId);
                                    } else {
                                        if (_val != 0) {
                                            console.log(input_check.blank);
                                            $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                            $("#" + nameId).addClass('trueInput');
                                            setNormalBorder(nameId);
                                            return
                                        }
                                    }
                                }
                            }
                        }

                        /*只能输入中文允许中间有空格 */
                        if ($(self).attr("check-zhSpace") == "true") {
                            var reg = /^[\u4e00-\u9fa5\0-9_()[\]（）【】-]+$/;
                            if (_val != 0 && !reg.test(input_check.blank)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入中文允许中间有空格'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    var newVal = input_check.blank.trim();
                                    $(self).val(newVal);
                                    //出去头尾空格之后 在重新校验
                                    if (_val != 0 && !reg.test(newVal)) {
                                        $("#" + nameId).removeClass('trueInput');
                                        var tipVal = '只能输入中文允许中间有空格'
                                        Prompt(tipVal, nameId);
                                    } else {
                                        if (_val != 0) {
                                            console.log(input_check.blank);
                                            $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                            $("#" + nameId).addClass('trueInput');
                                            setNormalBorder(nameId);
                                            return
                                        }
                                    }
                                }
                            }
                        }


                        /* 验证数字3-10 */
                        if ($(self).attr("check-TtoT") == "true") {
                            var reg = /^[0-9]+$/;
                            if (3 <= input_check.blank && input_check.blank <= 10) {
                                if (_val != 0 && !reg.test(input_check.blank)) {
                                    $("#" + nameId).removeClass('trueInput');
                                    var tipVal = '只能输入3-10的整数'
                                    Prompt(tipVal, nameId);
                                } else {
                                    if (_val != 0) {
                                        $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                        $("#" + nameId).addClass('trueInput');
                                        setNormalBorder(nameId);
                                        return
                                    }
                                }
                            } else {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入3-10的整数'
                                Prompt(tipVal, nameId);
                            }
                        }

                        /* 验证数字1-5 */
                        if ($(self).attr("check-OtoF") == "true") {
                            var reg = /^[1-5]+$/;
                            if (1 <= input_check.blank && input_check.blank <= 5) {
                                if (_val != 0 && !reg.test(input_check.blank)) {
                                    $("#" + nameId).removeClass('trueInput');
                                    var tipVal = '只能输入1-5的数字'
                                    Prompt(tipVal, nameId);
                                } else {
                                    if (_val != 0) {
                                        $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                        $("#" + nameId).addClass('trueInput');
                                        setNormalBorder(nameId);
                                        return
                                    }
                                }
                            } else {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入1-5的数字'
                                Prompt(tipVal, nameId);
                            }
                        }
                        /* 验证中文数字横线中括号小括号 */
                        if ($(self).attr("check-chineseDigitalSymbols") == "true") {
                            var reg = /^[\u4e00-\u9fa5\0-9_()[\]（）【】-]+$/;
                            var str = input_check.blank.replace(/[ ]/g, "")
                            $(self).val(str);
                            if (_val != 0 && !reg.test(str)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '只能输入中文、数字、下划线、中括号、小括号'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /*不能输入html标签*/
                        if ($(self).attr("check-label") == "true") {
                            var reg = /<[^>]+>/gim;
                            input_check.integer
                            var str = input_check.integer.replace(/<[^>]+>/g, "")
                            $(self).val(str);
                        }

                        /* 保留两位小数 */
                        if ($(self).attr("check-numTwo") == "true") {
                            var reg = /^[0-9]+[.][0-9]{2}$/;
                            if (_val != 0 && !reg.test(input_check.integer)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '保留小数点后两位'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }


                        /*校验长度 格式16.2*/
                        if ($(self).attr("check-lengthFormat") == "true") {
                            var reg = /^\d+([.]\d+)*$/;
                            if (_val != 0 && !reg.test(input_check.integer)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入格式(16.2。数字 点 数字)，点后面的数不的大于点前面的'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    var arr = input_check.integer.split('.');
                                    var arrOne = Number(arr[0]);
                                    var arrTow = arr[1] != undefined ? Number(arr[1]) : 0;
                                    if (arrOne <= 0) {
                                        $("#" + nameId).removeClass('trueInput');
                                        var tipVal = '请输入大于1的数'
                                        Prompt(tipVal, nameId);
                                    }
                                    if (arrOne > arrTow) {
                                        $("#" + nameId).addClass('trueInput');
                                        setNormalBorder(nameId);
                                        return
                                    } else {
                                        $("#" + nameId).removeClass('trueInput');
                                        var tipVal = '请输入格式(16.2。数字 点 数字)，点后面的数不的大于点前面的'
                                        Prompt(tipVal, nameId);
                                    }
                                }
                            }
                        }


                        /* 验证非零的正整数 */
                        if ($(self).attr("check-integer") == "true") {
                            var reg = /^([1-9][0-9]*){1,3}$/;
                            if (_val != 0 && !reg.test(input_check.integer)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正整数'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证非负的整数 */
                        if ($(self).attr("check-nonNegativeInteger") == "true") {
                            var reg = /^[1-9]+[0-9]*$|^0$/;
                            if (_val != 0 && !reg.test(input_check.integer)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入非负的整数'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                        /* 验证是否正则 */
                        if ($(self).attr("check-regExp") == "true") {
                        	var isReg = true;
                        	try{
                        		new RegExp(input_check.integer);
                        	}catch(e){
                        		isReg = false;
                        	}
                            if (_val != 0 && !isReg) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正确的正则表达式'
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }


                        /* 验证部分特殊符号 */
                        if ($(self).attr("check-partCharacter") == "true") {
                            var reg = /[''""\\\/]/;
                            if (_val != 0 && reg.test(input_check.integer)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请勿输入单引、双引号、以及正反斜线';
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }


                        /* 定长校验 _fixedLength*/
                        if ($(self).attr("check-fixedLength") != undefined && $(self).attr("check-fixedLength") != "false" && $(self).attr("check-fixedLength") != "") {
                            var _fixedLength = Number($(self).attr("check-fixedLength"));
                            if (input_check.integer.length > 0 && input_check.integer.length != _fixedLength) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入固定长度,长度为' + _fixedLength + '';
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }


                        /* 验证不能输入单引号 */
                        if ($(self).attr("check-pCharacter") == "true") {
                            var reg = /['']/;
                            if (_val != 0 && reg.test(input_check.integer)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请勿输入单引';
                                Prompt(tipVal, nameId);
                            } else {
                                if (_val != 0) {
                                    $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                    $("#" + nameId).addClass('trueInput');
                                    setNormalBorder(nameId);
                                    return
                                }
                            }
                        }

                    }


                    /* 校验长度提示 */
                    /*if(_val<_min){
						$("#"+ nameId).removeClass('trueInput');
						var tipVal = $(self).parent().siblings("label").text();
						tipVal = '请输入'+tipVal+'，最小'+_min+"个字符";
						Prompt(tipVal,nameId);
					}*/


                    /* 验证整数  包括正负和0 add qiu*/
                    if ($(self).attr("check-integer-pos-neg") == "true") {
                        var reg = /^(0|[1-9][0-9]*|-[1-9][0-9]*)$/g;
                        if (_val != 0 && !reg.test(input_check.integer)) {
                            $("#" + nameId).removeClass('trueInput');
                            var tipVal = '请输入整数'
                            Prompt(tipVal, nameId);
                        } else {
                            if (_val != 0) {
                                $("#" + nameId).addClass('trueInput');
                                setNormalBorder(nameId);
                                return
                            }
                        }
                    }"^[-\\+]?(\\d+|\\d+\\.\\d+)$"

                    /* 验证double   包括整数、正负和0 add qiu*/
                    if ($(self).attr("check-double") == "true") {
                        var reg = /^(0|[-\+]?([1-9][0-9]*|[1-9][0-9]*[.][0-9]+|0[.][0-9]+))$/g;
                        if (_val != 0 && !reg.test(input_check.integer)) {
                            $("#" + nameId).removeClass('trueInput');
                            var tipVal = '请输入浮点数'
                            Prompt(tipVal, nameId);
                        } else {
                            if (_val != 0) {
                                $("#" + nameId).addClass('trueInput');
                                setNormalBorder(nameId);
                                return
                            }
                        }
                    }


                    /*
					 * 整数小数长度校验
					 * checkNumLen="16,2"
					 * 16代表整数位不能超过16位
					 * 2代表小数位不能超过2位
					 * */
                    if ($(self).attr("checkNumLen") != "" && $(self).attr("checkNumLen") != undefined) {
                        var reg = /^(0|[1-9][0-9]*),(0|[1-9][0-9]*)$/g;
                        if (reg.test($(self).attr("checkNumLen"))) {
                            var val = $(self).val();
                            var valArr = val.split(".");
                            var chkArr = $(self).attr("checkNumLen").split(",");
                            if (val != "") {
                                var numReg1 = /^(0|[1-9][0-9]*)$/g;
                                var numReg2 = /^[0-9]*$/g;
                                if (valArr.length > 1) {
                                    if (!(numReg1.test(valArr[0]))) {
                                        var tipVal = "请输入正确的数字";
                                        Prompt(tipVal, nameId);
                                        return;
                                    }
                                    if (!(numReg2.test(valArr[1]))) {
                                        var tipVal = "请输入正确的数字";
                                        Prompt(tipVal, nameId);
                                        return;
                                    }
                                    if (valArr[0].length > Number(chkArr[0])) {
                                        var tipVal = "整数位不能超过" + chkArr[0];
                                        Prompt(tipVal, nameId);
                                        return;
                                    }
                                    if (valArr[1].length > Number(chkArr[1])) {
                                        var tipVal = "小数位不能超过" + chkArr[1];
                                        Prompt(tipVal, nameId);
                                        return;
                                    }
                                } else {
                                    if (!(numReg1.test(valArr[0]))) {
                                        var tipVal = "请输入正确的数字";
                                        Prompt(tipVal, nameId);
                                        return;
                                    }
                                    if (valArr[0].length > Number(chkArr[0])) {
                                        var tipVal = "整数位不能超过" + chkArr[0];
                                        Prompt(tipVal, nameId);
                                        return;
                                    }
                                }
                            }
                        }
                    }


                    /* 校验金额  add qiu */
                    /*允许格式："0" "0." "0.1" 会自动把前面没用的0去掉*/
                    /*如果需要确定小数数位  decimal-num 加上小数数位*/
                    if ($(self).attr("check-money") == "true") {
                        if (_val != 0) {
                            /*1、判断数字和点*/
                            var reg = /[^\d | \.]/g;
                            if (reg.test(input_check.notEmpty)) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正确金额';
                                Prompt(tipVal, nameId);
                                return;
                            }
                            /*2、判断只有一个点或没有*/
                            var reg1 = /[\.]/g;
                            /*if((reg1.exec(input_check.notEmpty)) !=null &&(reg1.exec(input_check.notEmpty)).length>1){*/
                            if (input_check.notEmpty.match(reg1) != null && input_check.notEmpty.match(reg1).length > 1) {
                                $("#" + nameId).removeClass('trueInput');
                                var tipVal = '请输入正确金额';
                                Prompt(tipVal, nameId);
                                return;
                            }
                            /*3、去掉开头无用的0或者加上0*/
                            var reg2 = /^0*/g;
                            var $manageList = $(self).val().split(".");
                            $manageList[0] = $manageList[0].replace(reg2, "");
                            if ($manageList[0] == "") {
                                $manageList[0] = 0;
                            }
                            var $manageVal_new = $manageList.join(".");
                            $(self).val($manageVal_new);

                            /*验证通过*/
                            $("#" + nameId).parent().siblings('span.warnBlock').remove();
                            $("#" + nameId).addClass('trueInput');
                            setNormalBorder(nameId);


                            /*如果还需要确定小数和整数位数处理*/
                            /*整数超长报错，小数自动处理*/
                            if ($(self).attr("decimal-num") != undefined) {
                                var $num0 = ""; /*小数数位*/
                                var $manageList_new = $manageVal_new.split(".");  /*值分隔成数组*/

                                if ($(self).attr("decimal-num").match(",")) {
                                    /*整数小数都要*/
                                    $num0 = $(self).attr("decimal-num").split(",")[1];

                                    $numInteger = $(self).attr("decimal-num").split(",")[0];
                                    if ($manageList_new[0].length > $numInteger) {
                                        $("#" + nameId).removeClass('trueInput');
                                        var tipVal = '金额整数位超长';
                                        Prompt(tipVal, nameId);
                                        return;
                                    }

                                } else {
                                    /*只要小数*/
                                    $num0 = $(self).attr("decimal-num");
                                }


                                var str0 = "";
                                for (var num0 = 0; num0 < $num0; num0++) {
                                    str0 += "0";
                                }

                                if ($manageList_new.length == 1) {
                                    /*没有小数内容*/
                                    $(self).val($manageList_new[0] + "." + str0);
                                } else {
                                    /*有小数内容进行转化*/
                                    $manageList_new[1] = ($manageList_new[1] + str0).substr(0, $num0);
                                    $(self).val($manageList_new.join("."));
                                }

                            }

                            return;
                        } else {
                            $("#" + nameId).removeClass('trueInput');
                            var tipVal = '请输入金额';
                            Prompt(tipVal, nameId);
                            return;
                        }


                    }

                    /* 验证小写英文、数字、特殊字符 20191127 add by lijunbin */
                    if ($(self).attr("check-lowerCharacter") == "true") {
                        var reg = /^[a-z0-9\s!@#$%\-_^&*(){}<>?]+$/;
                        if (_val != 0 && !reg.test(input_check.character)) {
                            $("#" + nameId).removeClass('trueInput');
                            var tipVal = '只能输入数字、小写字母和部分特殊符号'
                            Prompt(tipVal, nameId);
                        } else {
                            if (_val != 0) {
                                $("#" + nameId).parent().siblings('span.warnBlock').remove();
                                $("#" + nameId).addClass('trueInput');
                                setNormalBorder(nameId);
                                return
                            }
                        }
                    }
                }


                /* 开始时间不能大于结束时间 */
                function addTime() {
                    var MyDate = new Date();
                    var startTime_one = Date.parse($("[check-startTime-one]").val());
                    var endTime_one = Date.parse($("[check-endTime-one]").val());
                    /*年月日*/
                    var year = MyDate.getFullYear();
                    var month = MyDate.getMonth() + 1;
                    var ri = MyDate.getDate();
                    var slash = "/";
                    var my_Date = year + slash + month + slash + ri;
                    var myDate = Date.parse(my_Date);
                    if (myDate <= startTime_one) {
                        if (startTime_one > endTime_one) {
                            var tipVal = '开始时间要小于结束时间';
                            var nameId = $("[check-startTime-one]").attr("id");
                            Prompt(tipVal, nameId);
                        } else {
                            var startTimeId = $("[check-startTime-two]").attr("id");
                            var endTimeId = $("[check-endTime-two]").attr("id");
                            $("#" + startTimeId).addClass('trueInput');
                            setNormalBorder(nameId);
                            $("#" + startTimeId).parent().siblings('span.warnBlock').remove();
                            $("#" + endTimeId).parent().siblings('span.warnBlock').remove();

                        }
                    } else {
                        var tipVal = '开始时间要大于当前时间';
                        var nameId = $("[check-startTime-one]").attr("id");
                        $("#" + nameId).removeClass('trueInput');
                        Prompt(tipVal, nameId);
                    }
                }

                function unTime() {
                    var startTime_one = Date.parse($("[check-startTime-two]").val());
                    var endTime_one = Date.parse($("[check-endTime-two]").val());
                    if (startTime_one > endTime_one) {
                        var tipVal = '开始时间要小于结束时间';
                        var nameId = $("[check-startTime-two]").attr("id");
                        $("#" + nameId).removeClass('trueInput')
                        Prompt(tipVal, nameId);
                    } else {
                        var startTimeId = $("[check-startTime-two]").attr("id");
                        var endTimeId = $("[check-endTime-two]").attr("id");
                        $("#" + startTimeId).addClass('trueInput');
                        setNormalBorder(nameId);
                        $("#" + startTimeId).parent().siblings('span.warnBlock').remove();
                        $("#" + endTimeId).parent().siblings('span.warnBlock').remove();
                    }

                }


                /*var startThree = $(self).attr("check-startTime-three");
				var endThree = $(self).attr("check-endTime-three");
				自定义时间校验
				if(startThree == endThree){

				}*/


                /*开头结尾不能要空格*/
                if ($(self).attr("check-space") == "true") {
                    var str = input_check.blank
                    var spaceStr = str.replace(/(^\s*)|(\s*$)/g, "");
                    $(self).val(spaceStr);
                    inputcheck(self);
                }

                /*判断时间不为空*/
                if ($(self).attr("check-time-empty") == "true") {
                    if (input_check.endTime_one != "") {
                        $("#" + nameId).parent().siblings('span.warnBlock').remove();
                        $("#" + nameId).addClass('trueInput');
                        setNormalBorder(nameId);
                    } else {
                        var tipVal = '输入不能为空'
                        Prompt(tipVal, nameId);
                    }
                }

                /* 验证开始时间  不小于当前时间  add qiu */
                /*if($(self).attr("check-startTime") == "true"){*/
                if ($(self).is("[check-startTime]")) {
                    var dastartTimeValte = $(self).val();
                    if (_val > 0) {
                        var today = new Date();
                        var todayUTC = today.getTime() - 1000 * 60 * 60 * 24;
                        var y = dastartTimeValte.slice(0, 4);
                        var m = dastartTimeValte.slice(5, 7);
                        var d = dastartTimeValte.slice(8, 10);
                        var newDate = m + '/' + d + '/' + y;
                        var setDay = Date.parse(newDate);
                        if (setDay >= todayUTC) {
                            $(self).addClass('trueInput');
                            setNormalBorder(nameId);
                            $(self).parent().siblings('span.warnBlock').remove();
                        } else {
                            var tipVal = '时间要大于当前时间';
                            $(self).removeClass('trueInput');
                            Prompt(tipVal, nameId);
                        }
                    } else {
                        var tipVal = '时间不能为空';
                        Prompt(tipVal, nameId);
                    }
                }
                /* 验证开始时间  不小于当前时间  add qiu */
                /*if($(self).attr("check-endTime") == "true"){*/
                if ($(self).is("[check-endTime]")) {
                    var attrVal = $(self).attr("check-endTime");
                    var startDay = $("[check-startTime='" + attrVal + "']").val();
                    var endDay = $(self).val();
                    if (endDay != "") {
                        if (startDay != "") {
                            var y = startDay.slice(0, 4);
                            var m = startDay.slice(5, 7);
                            var d = startDay.slice(8, 10);
                            startDay = m + '/' + d + '/' + y;
                            startDay = Date.parse(startDay);


                            var y = endDay.slice(0, 4);
                            var m = endDay.slice(5, 7);
                            var d = endDay.slice(8, 10);
                            endDay = m + '/' + d + '/' + y;
                            endDay = Date.parse(endDay);
                            if (startDay <= endDay) {
                                $(self).addClass('trueInput');
                                setNormalBorder(nameId);
                                $(self).parent().siblings('span.warnBlock').remove();
                            } else {
                                var tipVal = '结束时间不小于开始时间';
                                $(self).removeClass('trueInput');
                                Prompt(tipVal, $(self).attr("id"));
                            }
                        } else {
                            /*清空结束时间*/
                            $(self).val("");
                        }

                    } else {
                        var tipVal = '时间不能为空';
                        Prompt(tipVal, nameId);
                    }
                }


                /* 验证正常开始时间 */
                if ($(self).attr("check-startTime-one") == "true") {
                    if (input_check.startTime_one != "") {
                        $("#" + nameId).addClass('trueInput');
                        addTime()
                    } else {
                        var tipVal = '输入不能为空'
                        Prompt(tipVal, nameId);
                    }
                }

                /* 验证结束开始时间 */
                if ($(self).attr("check-endTime-one") == "true") {
                    if (input_check.endTime_one != "") {
                        $("#" + nameId).addClass('trueInput');
                        setNormalBorder(nameId);
                        addTime()
                    } else {
                        var tipVal = '输入不能为空'
                        Prompt(tipVal, nameId);
                    }
                }

                /*one  是开始时间要大于当前时间
				tow 的开始时间则当前时间不受控制*/

                /* 验证修改正常开始时间 */
                if ($(self).attr("check-startTime-two") == "true") {
                    if (input_check.startTime_one != "") {
                        $("#" + nameId).addClass('trueInput');
                        setNormalBorder(nameId);
                        unTime()
                    } else {
                        var tipVal = '输入不能为空'
                        Prompt(tipVal, nameId);
                    }
                }

                /* 验证修改结束开始时间 */
                if ($(self).attr("check-endTime-two") == "true") {
                    if (input_check.endTime_one != "") {
                        $("#" + nameId).addClass('trueInput');
                        setNormalBorder(nameId);
                        unTime()
                    } else {
                        var tipVal = '输入不能为空'
                        Prompt(tipVal, nameId);
                    }
                }
            }
            return tipVal;
        }

        if ($(self).is(":visible")) {
            if (_maxlength != undefined || _min != undefined) {
                var tipVal = redPrompt(self, nameId);
                /*判断tipVal是否为空*/
                if (tipVal != undefined) {
                    var tipVal = tipVal + '，';
                } else {
                    var tipVal = '';
                }

                /*add qiu*/
                /*把双字节转换成两个字节内容，再作判断*/
                var inputVal = Number($(self).val().replace(/[^\x00-xff]/g, "xx"));
                if (!inputVal) {
                    inputVal = $(self).val().length;
                }
                /* 最大长度提示 */
                if (inputVal < _min) {
                    if (_maxlength != undefined) {
                        if (_min != undefined) {
                            var tipVal = tipVal + '请输入' + _min + "至" + _maxlength + "长度之间";
                            lengtPrompt(tipVal, nameId);
                        } else {
                            var tipVal = '请输入' + tipVal + '，不超过' + _maxlength + "个字符";
                            lengtPrompt(tipVal, nameId);
                        }
                    } else
                        /* 最大小度提示 */
                    if (_min != undefined) {
                        if (_maxlength != undefined) {
                            var tipVal = tipVal + '请输入' + _min + "--" + _maxlength + "长度之间";
                            lengtPrompt(tipVal, nameId);
                        } else {
                            if (tipVal == undefined) {
                                var tipVal = '最小长度' + _min + "个字符";
                                lengtPrompt(tipVal, nameId);
                            } else {
                                var tipVal = tipVal + '，最小长度' + _min + "个字符";
                                lengtPrompt(tipVal, nameId);
                            }
                        }
                    }
                } else {
                    redPrompt(self, nameId);
                }
            } else {
                redPrompt(self, nameId);
            }
        }
    });
}

/*
 * add qiu
 * 验真jBox输入框
 * 一点击就弹出jBox框，直接触发了blur
 * 不可以用blur验证（此时为空），在点击下一步时才做验证
 */
function checkJBox(ID) {
    if (ID != undefined) {
        $("#" + ID).find("[check-jBox-empty]").each(function () {
            if ($(this).attr("check-jBox-empty") == true || $(this).attr("check-jBox-empty") == "true") {
                if ($(this).val() == "") {
                    var tipVal = '输入不能为空'
                    Prompt(tipVal, $(this).attr("id"));
                }
            }
        });
    } else {
        $("[check-jBox-empty]").each(function () {
            if ($(this).attr("check-jBox-empty") == true || $(this).attr("check-jBox-empty") == "true") {
                if ($(this).val() == "") {
                    var tipVal = '输入不能为空'
                    Prompt(tipVal, $(this).attr("id"));
                }
            }

        });
    }

}


/*
 * 页面加载完,自动判断是否要加readonly
 */
$(document).ready(function () {
    var myDate = new Date;
    var myTime = Date.parse(myDate);
    var startTime_one = $("[check-startTime-ones]").val();
    if (startTime_one != "") {
        var startTimeOne = Date.parse(startTime_one);
        if (myTime > startTimeOne) {
            $("[check-startTime-ones]").attr("readonly", "readonly");
        }
    } else {

    }

});

/*普通验证方法 */
function Prompt(tipVal, nameId) {
    var script = tipVal;
    if ($("#" + nameId).parent().next("span.warnBlock").length == 0) {
        var tag = "<span id='" + nameId + "Err'  class='warnBlock'>" + script + "</span>";
        $("#" + nameId).parent().after(tag);
        $("#" + nameId).removeClass('trueInput');
    }

}


/*单选复选验证方法 */
function Prompts(tipVal, Checkbtn) {
    var script = tipVal;
    if ($("input[checkbtn=" + Checkbtn + "]").parent().parent().next("span.warnBlock").length == 0) {
        var tag = "<span id='" + Checkbtn + "Err' class='warnBlock warnBlock2'>" + script + "</span>";
        $("input[checkbtn=" + Checkbtn + "]:last").parent().parent().after(tag);
    }

}

/* 添加下拉框红字提示 */
function selectPrompt(tipVal, Checkbtn) {
    var script = tipVal;
    if ($("select[checkbtn=" + Checkbtn + "]").parent().next("span.warnBlock").length == 0) {
        var tag = "<span id='" + Checkbtn + "Err' class='warnBlock warnBlock2'>" + script + "</span>";
        $("select[checkbtn=" + Checkbtn + "]").parent().after(tag);
        $("select[checkbtn=" + Checkbtn + "]").removeClass('trueInput');
        $("select[checkbtn=" + Checkbtn + "]").next().children("button").css('border-color',errorBorder);
    }
}


/*判断是否选中单选 */
function checkUnChecked() {
    if ($("input[type='radio']").is(":visible")) {
        var radioCheckbtn = [];
        var i = 0;
        var j = 0;
        $("input[type='radio']").each(function () {
            radioCheckbtn.push($("input[type='radio']").eq(i).attr("checkbtn"));
            i++;
        });
        var arrCheckbtn = $.unique(radioCheckbtn);
        var tipVal = '请选择一项';
        $(arrCheckbtn).each(function () {
            var Checkbtn = arrCheckbtn[j];
            if ($("input[checkbtn=" + Checkbtn + "]").is(":visible")) {
                if ($("input[checkbtn=" + Checkbtn + "]").is(":checked")) {
                    return j++;
                } else {
                    Prompts(tipVal, Checkbtn);
                }
            }
            j++;
        });
    }
}

/*判断部分是否选中单选 */
function portionChecked(ID) {
    if ($("#" + ID + " " + "input[type='radio']").is(":visible")) {
        var radioCheckbtn = [];
        var i = 0;
        var j = 0;
        $("#" + ID + " " + "input[type='radio']").each(function () {
            radioCheckbtn.push($("#" + ID + " " + "input[type='radio']").eq(i).attr("checkbtn"));
            i++;
        });
        var arrCheckbtn = $.unique(radioCheckbtn);
        var tipVal = '请选择一项';
        $(arrCheckbtn).each(function () {
            var Checkbtn = arrCheckbtn[j];
            if ($("#" + ID + " " + "input[checkbtn=" + Checkbtn + "]").is(":visible")) {
                if ($("#" + ID + " " + "input[checkbtn=" + Checkbtn + "]").is(":checked")) {
                    return j++;
                } else {
                    Prompts(tipVal, Checkbtn);
                }
            }
            j++;
        });
    }
}

/*判断是否选中复选框*/
function unCheckBox() {
    var checkboxCheckbtn = [];
    var i = 0;
    var j = 0;
    $("input[type='checkbox']").each(function () {
        checkboxCheckbtn.push($("input[type='checkbox']").eq(i).attr("checkbtn"));
        i++;
    });
    var arrCheckbtn = $.unique(checkboxCheckbtn);
    var tipVal = '请选择一项';
    $(arrCheckbtn).each(function () {
        var Checkbtn = arrCheckbtn[j]
        if ($("input[checkbtn=" + Checkbtn + "]").is(":visible")) {
            if ($("input[checkbtn=" + Checkbtn + "]").is(":checked")) {
                return j++;
            } else {
                Prompts(tipVal, Checkbtn);
            }
        }

        j++;
    });
}

/*部分判断是否选中复选框*/
function portionCheckBox(ID) {
    var checkboxCheckbtn = [];
    var i = 0;
    var j = 0;
    $("#" + ID + " " + "input[type='checkbox']").each(function () {
        checkboxCheckbtn.push($("#" + ID + " " + "input[type='checkbox']").eq(i).attr("checkbtn"));
        i++;
    });
    var arrCheckbtn = $.unique(checkboxCheckbtn);
    var tipVal = '请选择一项';
    $(arrCheckbtn).each(function () {
        var Checkbtn = arrCheckbtn[j]
        if ($("#" + ID + " " + "input[checkbtn=" + Checkbtn + "]").is(":visible")) {
            if ($("#" + ID + " " + "input[checkbtn=" + Checkbtn + "]").is(":checked")) {
                return j++;
            } else {
                Prompts(tipVal, Checkbtn);
            }
        }

        j++;
    });
}


/* 下拉框验证 */
function selects() {
    var selectsId = [];
    var i = 0;
    var j = 0;
    $("select").each(function () {
        selectsId.push($("select").eq(i).attr("checkbtn"));
        i++;
    });
    var tipVal = '请选择一项';
    $(selectsId).each(function () {
        var Checkbtn = selectsId[j]
        if ($("select[checkbtn=" + Checkbtn + "]").is(":visible")) {
            if ($("select[checkbtn=" + Checkbtn + "]").attr("disabled")) {
                console.log("有禁用属性");
            } else {
                var coun = $("select[checkbtn=" + Checkbtn + "]").val();
                if (coun == "" || coun == null) {
                    selectPrompt(tipVal, Checkbtn);
                } else {
                    $("select[checkbtn=" + Checkbtn + "]").parent().siblings('span.warnBlock').remove();
                    $("select[checkbtn=" + Checkbtn + "]").next().children("button").css('border-color',normalBorder);
                }
            }
        }

        j++;
    });
}

/* 部分下拉框验证 */
function portionSelects(ID) {
    var selectsId = [];
    var i = 0;
    var j = 0;
    $("#" + ID + " " + "select").each(function () {
        selectsId.push($("#" + ID + " " + "select").eq(i).attr("checkbtn"));
        i++;
    });
    var tipVal = '请选择一项';
    $(selectsId).each(function () {
        var Checkbtn = selectsId[j]
        if ($("#" + ID + " " + "select[checkbtn=" + Checkbtn + "]").is(":visible")) {
            var coun = $("#" + ID + " " + "select[checkbtn=" + Checkbtn + "]").val();
            if (coun == "" || coun == null) {
                selectPrompt(tipVal, Checkbtn);
            } else {
                $("#" + ID + " " + "select[checkbtn=" + Checkbtn + "]").parent().siblings('span.warnBlock').remove();
            }
        }

        j++;
    });
}

/* Y部分下拉框验证 */
function yPortionSelects(ID) {
    var selectsId = [];
    var tipVal = '请选择一项';
    var $this = $("#" + ID);
    var $ID = ID;
    if (undefined != $this.attr('checkbtn') && '' != $this.attr('checkbtn')) {
        $ID = $this.attr('checkbtn');
    }
    if ($("select[checkbtn=" + $ID + "]").is(":visible")) {
        var coun = $("select[checkbtn=" + $ID + "]").val();
        if (coun == "") {
            selectPrompt(tipVal, $ID);
        } else {
            $("#" + $ID + "Err").remove();
        }
    }

}

/* Y部分复选框 */
function yPortionCheckBox(ID) {
    var selectsId = [];
    var tipVal = '请选择一项';
    var $this = $("#" + ID);
    var $ID = ID;
    if (undefined == $this.attr('checkbtn') && '' != $this.attr('checkbtn')) {
        $ID = $this.attr('checkbtn');
    }
    if ($("input[checkbtn=" + $ID + "]").is(":visible")) {
        if ($("input[checkbtn=" + $ID + "]").is(":checked")) {
            return j++;
        } else {
            Prompts(tipVal, $ID);
        }
    }

}

/* Y部分下拉框验证 */
function yPortionChecked(ID) {
    var selectsId = [];
    var tipVal = '请选择一项';
    var $this = $("#" + ID);
    var $ID = ID;
    if (undefined == $this.attr('checkbtn') && '' != $this.attr('checkbtn')) {
        $ID = $this.attr('checkbtn');
    }
    if ($("input[checkbtn=" + $ID + "]").is(":visible")) {
        if ($("input[checkbtn=" + $ID + "]").is(":checked")) {
            return j++;
        } else {
            Prompts(tipVal, $ID);
        }
    }

}


/* 调用校验方法名 */
function proofTest(ID) {
    /*add qiu用于部分验证
	否则部分外面有warnBlock也返回false*/
    if (ID) {
        if ($("#" + ID + " span.warnBlock:visible").length == 0) {
            return true;
        } else {
            return false;
        }
    }
    if ($("span.warnBlock:visible").length == 0) {
        return true;
    } else {
        return false;
    }
}

/*表格验证*/
function cTableLine1(tableId, classList) {
    if ($('#' + tableId).is(":visible")) {
        /*判断table至少有一行*/
        var nextState = true;
        var index = $('#' + tableId).bootstrapTable('getSelections').length;
        if (index == 0) {
            if (nextState) {
                nextState = false;
                showContent("保存时至少选择一条地址信息", "error");
            }
        } else {
            /*被选择的条内容必输*/
            for (var a = 0; a < classList.length; a++) {
                $('#' + tableId).find('tbody tr[class="selected"]').find("input[class=" + classList[a] + "]").each(function () {
                    var blank = /^\s*|\s*$/g;
                    var singleQuotes = /['']/;
                    $(this).val($(this).val().replace(blank, ''));
                    if ($(this).val() == '') {
                        $(this).css('border-color', '#f9918f');
                        nextState = false;
                        hong();
                    } else if (singleQuotes.test($(this).val())) {
                        $(this).css('border-color', '#f9918f');
                        nextState = false;
                        showContent("请勿输入单引号", "error");
                        hong();
                    }
                });
            }
        }
        return nextState
    }
}


function hint(tableId, classList) {
    var cue;
    if (cTableLine1(tableId, classList) == true) {
        cue = cTableLine1(tableId, classList);
        return cue;
    }
}

/*单独一个删除 e为删除红名的id fatherId校验的id*/
function cutOff(e, fatherId) {
    $("#" + e).remove();
    portion(fatherId)
}

/*删除dom以内的红名  e为id 然后又重新校验*/
function idCutOff(e) {
    $("#" + e + "Err").remove();
    yPortion(e);
}

/*删除dom以内的红名 e为class*/
function classCutOff(e) {
    $("." + e + " .warnBlock").remove();
}

/* 文档校验 不能输入不能输入HTML标签*/
function checkDoucment() {
    var arrId = [];
    var i = 0;
    $("div.edui-editor.edui-default").each(function () {
        arrId.push($("div.edui-editor.edui-default").parent().eq(i).attr("id"));
        i++;
    })
    for (var j = 0; j < arrId.length; j++) {
        var ue = UE.getEditor(arrId[j]);
        var txt = ue.getContentTxt();
        console.info("txt=" + txt);
        var reg = /<[^>]+>/gim;
        if (reg.test(txt)) {
            var tipVal = '不能输入HTML标签'
            Prompt(tipVal, arrId[j]);
        } else {
            $("#" + arrId[j]).parent().siblings('span.warnBlock').remove();
            $("#" + arrId[j]).addClass('trueInput');
            return
        }
    }
}

/*校验富文本不为空 20181008*/
function richText() {
    var arrId = [];
    var i = 0;
    $("div.edui-editor.edui-default").each(function () {
        arrId.push($(".edui-editor.edui-default").parent().eq(i).attr("id"));
        i++;
    })
    for (var j = 0; j < arrId.length; j++) {
        var ue = UE.getEditor(arrId[j]);
        var txt = ue.getContentTxt();
        console.info("txt=" + txt);
        var richTextId = $("#" + arrId[j]).attr("check-empty");
        if (richTextId) {
            if (txt.length > 0) {
                var reg = /<[^>]+>/gim;
                if (reg.test(txt)) {
                    var tipVal = '不能输入HTML标签'
                    Prompt(tipVal, arrId[j]);
                } else {
                    $("#" + arrId[j]).parent().siblings('span.warnBlock').remove();
                    $("#" + arrId[j]).addClass('trueInput');
                    return
                }
            } else {
                var tipVal = '编辑框不能为空';
                Prompt(tipVal, arrId[j]);
            }
        }
    }
}

/*给富文本设置check-empty*/
function setRichCheck(id) {
    $("#" + id).attr("check-empty", "true");
}

/*给富文本取消check-empty*/
function rmRichCheck(id) {
    $("#" + id).removeAttr("check-empty");
}

/*校验部分富文本传入ID*/
function portionRichText(id) {
    var richTextId = $("#" + id).attr("check-empty");
    if (richTextId) {
        var ue = UE.getEditor(id);
        var txt = ue.getContentTxt();
        console.info("txt=" + txt);
        if (txt.length > 0) {
            var reg = /<[^>]+>/gim;
            if (reg.test(txt)) {
                var tipVal = '不能输入HTML标签'
                Prompt(tipVal, id);
            } else {
                $("#" + id).parent().siblings('span.warnBlock').remove();
                $("#" + id).addClass('trueInput');

            }
        } else {
            var tipVal = '编辑框不能为空';
            Prompt(tipVal, id);
        }
    }
}

function proof() {
    $(".warnBlock").remove();
    checkJBox();   /*jBox输入框 add qiu*/
    showProof();
    $("input,textarea:visible").trigger("blur");
    checkUnChecked();
    checkDoucment();
    unCheckBox();
    selects();
    richText();
    /*var _proofArr = [];
	_proofArr[0]=tiShi();
	_proofArr[1]=proofTest();*/
    var _proofArr = proofTest();
    return _proofArr;
}

/* input */
function showProof(id) {
	console.log("--showProof--");
//	var nameId = $("input[type=hidden]").attr("id");
//	for(var i=0;i<name.length;i++){

//
//	}
//	if($(nameId).attr("check-empty") == "true" ){
//		if($.trim(input_check.notEmpty) == ""){
//			$("#"+ nameId).removeClass('trueInput');
//			var tipVal = '输入不能为空'
//			Prompt(tipVal,nameId);
//	  	}else{
//	  		$("#"+nameId).parent().siblings('span.warnBlock').remove();
//	  	}
//	};
    /*20200213 mod by chenyl 校验隐藏域支持指定范围*/
    var expr = '';
    if (undefined != id && '' !== id) {
        expr = '#' + id + ' input[type="hidden"]';
    } else {
        expr = 'input[type="hidden"]';
    }
    $(expr).each(function () {
        var $this = $(this);
        var $checkEmpty = $this.attr('check-empty');
        var $value = $this.val();
        if (undefined != $checkEmpty && $checkEmpty == 'true') {
            if (undefined != $value && $value != '') {
                // 关闭错误信息
                $this.parent().siblings('span.warnBlock').remove();
                $this.next().css('border-color',normalBorder);
            } else {
                // 显示错误信息
                $this.removeClass('trueInput');
                var tipVal = '输入不能为空';
                var nameId = $this.attr('id')
                Prompt(tipVal, nameId);
				$this.next().css('border-color',errorBorder);
            }
        } else {
            // 关闭错误信息
            $this.parent().siblings('span.warnBlock').remove();
            $this.next().css('border-color',normalBorder);
        }
    });
}

/*部分校验指定某个组件ID 锐新的需求*/
function portion(ID) {
    showProof(ID);
    checkJBox(ID);   /*jBox输入框校验 add qiu*/
    $("#" + ID + " " + "input,textarea:visible").trigger("blur");
    portionChecked(ID);
    portionCheckBox(ID);
    portionSelects(ID);
    portionRichText(ID);
    /*var _proofArr = [];
	_proofArr[0]=tiShi();
	_proofArr[1]=proofTest();*/
    var _proofArr = proofTest(ID);
    return _proofArr;
}

/*部分校验指定某个组件ID 应龙的需求*/
function yPortion(ID) {
    showProof(ID);
    if (ID != '' && ID != undefined) {
        checkJBox(ID);   /*jBox输入框校验 add qiu*/
        $("#" + ID + " " + "input,textarea:visible").trigger("blur");
        yPortionChecked(ID);
        yPortionCheckBox(ID);
        yPortionSelects(ID);
        var _proofArr = proofTest(ID);
    } else {
        alert('id为空');
    }
    /*var _proofArr = [];
	_proofArr[0]=tiShi();
	_proofArr[1]=proofTest();*/
    return _proofArr;
}

function setErrorBorder(id){
	$("#" + id).css('border-color',errorBorder);
}

function setNormalBorder(id){
	$("#" + id).css('border-color',normalBorder);
}

function setElementBorder(element){
	console.log("--setElementBorder e--");
	$(element).css("border-color",errorBorder);
}

function setElementPrevBorder(element){
	console.log("--setElementPrevBorder n--");
	$(element).prev().css("border-color",normalBorder);
	$(element).remove();
}


/* 页面加载完,自动判断是否要加readonly */
$(document).ready(function () {
    /*$("#saveBtn").click(function(){

		if(proof()){

		};
	});*/
    /* 点击复选之后取消红字提示 */
    $("input[type='checkbox']").click(function () {
        var self = this;
        $(self).parent().parent().siblings('span.warnBlock').remove();
    })
    /* 点击单选之后取消红字提示 */
    $("input[type='radio']").click(function () {
        var self = this;
        $(self).parent().parent().siblings('span.warnBlock').remove();
    })
    /* 点击下拉框之后取消红字提示 */
    $("select").change(function () {
        var self = this;
        $(self).parent().siblings('span.warnBlock').remove();
        $(self).next().children("button:first").css("border-color",normalBorder);
    });
});
