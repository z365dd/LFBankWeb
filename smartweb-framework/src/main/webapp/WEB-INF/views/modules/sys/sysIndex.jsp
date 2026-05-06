<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <link rel="icon" href="${ctxStatic}/mainframe/img/favicon.ico" type="image/x-icon"/>
    <meta name="decorator" content="blank"/>
    <link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css"/>
    <c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>
    <script type="text/javascript">
        var ctx = '${ctx}', ctxStatic = '${ctxStatic}', ctxTheme = '${ctxTheme}';
    </script>
    <c:if test="${tabmode eq '1'}">
        <link rel="Stylesheet" href="${ctxStatic}/mainframe/css_${ctxTheme}/top.css"/>
        <style>
            .form-horizontal .control-label {
                float: left;
                width: 90px;
                padding-top: 5px;
                text-align: right;
            }

            .modal-body p {
                height: 32px;
                margin-top: 18px;
            }

            .modal-header h4 {
                font-size: 25px;
                text-align: center;
                margin-top: 18px;
            }

            element.style {
                display: block;
            }

            .modal.fade.in {
                top: 10%;
            }

            .modal.fade {
                top: -25%;
                -webkit-transition: opacity 0.3s linear, top 0.3s ease-out;
                -moz-transition: opacity 0.3s linear, top 0.3s ease-out;
                -o-transition: opacity 0.3s linear, top 0.3s ease-out;
                transition: opacity 0.3s linear, top 0.3s ease-out;
            }

            .fade.in {
                opacity: 1;
            }

            .modal {
                position: fixed;
                left: 50%;
                z-index: 1050;
                width: 520px;
                margin-left: -280px;
                background-color: #ffffff;
                border: 1px solid #999;
                -moz-border-radius: 6px;
                outline: none;
                -moz-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
                box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
                background-clip: padding-box;
            }

            .modal-header {
                min-height: 50px;
                padding: 15px;
                background-color: #f5f5f5;
                border-bottom: 1px solid #eee;
            }

            .modal-body {
                position: relative;
                max-height: 500px;
                padding: 20px;
                overflow-y: auto;
            }

            /*.modal-footer:after {
                    display: table;
                    line-height: 0;
                    height: 20px;
                }*/
            .modal-footer {
                padding: 20px 15px 15px;
                margin-bottom: 0;
                margin-top: 20px;
                text-align: right;
                background-color: #f5f5f5;
                border-top: 1px solid #ddd;
                -webkit-border-radius: 0 0 6px 6px;
                -moz-border-radius: 0 0 6px 6px;
                zoom: 1;
                -moz-box-shadow: inset 0 1px 0 #ffffff;
                box-shadow: inset 0 1px 0 #ffffff;
            }
        </style>
        <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script>
    </c:if>
    <!-- <style type="text/css">

    </style> -->
    <script type="text/javascript">
        var $maxWidth = '${maxWidth}';
        var screenHeight = window.screen.height;
        var pageHeight = window.innerHeight;

        $(document).ready(function () {
            $.session.set('MenuInitFlag', 'true');	//主页初始化
            console.info('网页可见区域宽： ' + document.body.clientWidth);
            console.info('屏幕分辨率的宽： ' + window.screen.width);
            console.info('后台返回屏幕分辨率的宽：' + $maxWidth);
            // <c:if test="${tabmode eq '1'}"> 初始化页签
            $.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: {'height': $('#right').height() - tabTitleHeight},
                tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
            });//</c:if>

            $(".top5-1").click(function () {
                openMenu("我的面板->个人信息->我的首页");
                setCookie("openNew", "true", 7);
            });

            $(".top6").click(function () {
                $("#switchModal").modal('show');
            });

            // 建立与websocket的连接
            // connect();

            $("#mainFrame").hide();
            $("#content").height(pageHeight - $("#header").height());

            //根据窗口大小调整页面大小
            $(window).resize(function () {
                var clientHeight = window.innerHeight;
                var contentHeight = clientHeight - $("#header").height();
                $("#content").css("height", contentHeight + "px");
                $("#left-index,#transverter,#right").css("height", contentHeight - 20 + "px");
                $(".tab_content").css("height", contentHeight - 20 - $(".tab_pages").height() + "px");

            })

            //设置下拉显隐
            $(document).on('click', function (e) {
                var upinstall = $(e.target).attr("class");
                if (upinstall != "sb2") {
                    $(".dropdown-menu").hide();
                }
            })

            // 绑定菜单单击事件
            $(".firstMenu-link").click(function () {
                console.info('menu click start');
                console.info('firstMenu MenuInitFlag=' + $.session.get('MenuInitFlag'));
                // 左侧区域隐藏
                if ($(this).attr("target") == "mainFrame") {
                    $("#left-index,#transverter").hide();
                    wSizeWidth();
                    // <c:if test="${tabmode eq '1'}"> 隐藏页签
                    $(".jericho_tab").hide();
                    $("#mainFrame").show();//</c:if>
                    return true;
                }
                // 左侧区域显示
                $("#left-index,#transverter").show();
                if (!$("#transverter").children().hasClass("fold")) {
                    $("#transverter").click();
                }
                // 显示二级菜单
                var menuId = "#menu-" + $(this).attr("data-id");
                if ($(menuId).length > 0) {
                    $("#left-index .menuHeader").hide();
                    $(menuId).show();
                    // 初始化点击第一个二级菜单
                    if (!$(menuId + " .menu2:first").children().eq(1).hasClass('icon-change-down')) {
                        $(menuId + " .menu2:first").click();
                    }

                    $(menuId + " a:first").click();
                } else {
                    // 获取二级菜单数据
                    $.get($(this).attr("data-href"), function (data) {
                        if (data.indexOf("id=\"loginForm\"") != -1) {
                            alert('未登录或登录超时。请重新登录，谢谢！');
                            top.location = "${ctx}";
                            return false;
                        }
                        $("#left-index .menuHeader").hide();
                        $("#left-index").append(data);
                        // 链接去掉虚框
                        $(menuId + " a").bind("focus", function () {
                            if (this.blur) {
                                this.blur()
                            }
                            ;
                        });

                        $(menuId).find('a').each(function () {
                            var thirdMenuText = $.trim($(this).text());
                            if (thirdMenuText.length > 6) {
                                $(this).text(thirdMenuText.substring(0, 6) + "...");
                            }
                        });

                        //一级菜单过长时自动省略后面一部分
                        $(menuId).find(".c2-2").each(function () {
                            var menuReg = new RegExp("[\u4E00-\u9FA5]|[\u3002|\uff1f|\uff01|\uff0c|\u3001|" +
                                "\uff1b|\uff1a|\u201c|\u201d|\u2018|\u2019|\uff08|\uff09|\u300a|\u300b|" +
                                "\u3008|\u3009|\u3010|\u3011|\u300e|\u300f|\u300c|\u300d|\ufe43|\ufe44|" +
                                "\u3014|\u3015|\u2026|\u2014|\uff5e|\ufe4f|\uffe5]");
                            var menuText = $(this).text().trim();
                            var menuTextLength = menuText.length;
                            var charLength = 0;
                            for (var i = 0; i < menuTextLength; i++) {
                                if (menuReg.test(menuText.substring(i, i + 1))) {
                                    charLength += 2;
                                } else {
                                    charLength += 1;
                                }
                            }
                            if (charLength > 16) {
                                $(this).text(menuText.substring(0, 7) + "...");
                            }
                        })

                        //一级菜单点击事件
                        $(menuId + " .menuTree").click(function () {
                            if ($(".menuda").hasClass("show")) {
                                $(".firstIndex").toggle();
                                $("#menuModel,.tab_pages,.tab_content").toggle();
                            } else {
                                $("#menuModel,.tab_pages,.tab_content").toggle();
                                $(".firstIndex").toggle();
                                if ($(".firstIndex").html().trim() == "") {
                                    $.get("${ctx}/sys/menu/mainMenu", function (data) {
                                        if (data.indexOf("id=\"loginForm\"") != -1) {
                                            alert('未登录或登录超时。请重新登录，谢谢！');
                                            top.location = "${ctx}";
                                            return false;
                                        }
                                        $(".firstIndex").append(data);
                                        $(".menuTarget").mouseup(function () {
                                            $(".firstIndex").hide();
                                            $("#menuModel,.tab_pages,.tab_content").show();
                                            var menuPath = $(this).attr("menuPath");
                                            openMenu(menuPath);
                                        })
                                    })
                                } else {
                                    $(".firstIndex").show();
                                }
                            }

                        })


                        //二级菜单点击事件
                        $(".menu2").click(function () {
                            $(".icon-change-down").removeClass("icon-change-down").addClass("icon-change-right");
                            $(this).children().eq(1).removeClass("icon-change-right").addClass("icon-change-down");
                            $(".list-show").removeClass("list-show").addClass("list-hidden");
                            $(this).next().removeClass("list-hidden").addClass("list-show");
                        })
                        // 展现三级
                        $(menuId + " a").click(function () {
                            $(".firstIndex").hide();
                            $("#menuModel,.tab_pages,.tab_content").show();
                            $(".menu3-list-focus").removeClass("menu3-list-focus").addClass("menu3-list-blur");
                            $(this).removeClass("menu3-list-blur").addClass("menu3-list-focus");
                            $(".menu3-circle-focus").removeClass("menu3-circle-focus").addClass("menu3-circle-blur");
                            $(this).parent().prev().removeClass("menu3-circle-blur").addClass("menu3-circle-focus");
                            var href = $(this).attr("data-href");
                            if ($(href).length > 0) {
                                $(href).toggle().parent().toggle();
                                return false;
                            }
                            // <c:if test="${tabmode eq '1'}"> 打开显示页签
                            return addTab($(this), true); // </c:if>
                        });
                        // 默认选中第一个菜单,menuId=#menu-27(我的面板)
                        console.info('defaultMenu MenuInitFlag=' + $.session.get('MenuInitFlag'));
                        $(menuId + " .menu2:first").click();
                        $(menuId + " a:first").click();
                    });
                }
                // 大小宽度调整
                wSizeWidth();
                //关闭更多的一级菜单显示
                if ($('#navMoreUl').css('display') == 'block') {
                    $("#navMore").click();
                }
                return false;
            });
            // 初始化点击第一个一级菜单
            $(".firstMenu-link:first").click();
            console.info('0000');
            // <c:if test="${tabmode eq '1'}"> 下拉菜单以选项卡方式打开
            $(".sb2").click(function () {
                $(".dropdown-menu").toggle();
            });
            $(".top4 .dropdown-menu a").mouseup(function () {
                $(".firstIndex").hide();
                $("#menuModel,.tab_pages,.tab_content").show();
                $(".sb2").click();
                //return addTab($(this), true);
                var menuPath = $(this).attr("menuPath");
                openMenu(menuPath);
            });// </c:if>
            // 鼠标移动到边界自动弹出左侧菜单
            $("#transverter").mouseover(function () {
                if ($(this).children().hasClass("unfold")) {
                    $(this).click();
                }
            });

            //选中搜索框,菜单页面显示
            $("#searchMenuName").focus(function () {
                $("#menuModel,.tab_pages,.tab_content").hide();
                $(".firstIndex").show();
                if ($(".firstIndex").html().trim() == "") {
                    $.get("${ctx}/sys/menu/mainMenu", function (data) {
                        if (data.indexOf("id=\"loginForm\"") != -1) {
                            alert('未登录或登录超时。请重新登录，谢谢！');
                            top.location = "${ctx}";
                            return false;
                        }
                        $(".firstIndex").append(data);
                        $(".menuTarget").mouseup(function () {
                            $(".firstIndex").hide();
                            $("#menuModel,.tab_pages,.tab_content").show();
                            var menuPath = $(this).attr("menuPath");
                            openMenu(menuPath);
                        })
                    })
                } else {
                    $(".firstIndex").show();
                }
            })

            $("#searchMenuName").on("input propertychange", searchMenu);
            $("#searchMenuName").keydown(function (e) {
                if (e.keyCode == 13) {
                    searchMenu();
                    $(".firstIndex").hide();
                    setTimeout(function () {
                        $(".firstIndex").show();
                    }, 5)
                }
            })
            $(".s2").click(function () {
                searchMenu();
                $(".firstIndex").hide();
                setTimeout(function () {
                    $(".firstIndex").show();
                }, 5)
            })

            //设置时间显示格式
            $("#lastLoginDate").text($("#lastLoginDate").text().substring(0, 23));

            // 获取通知数目
            function getNotifyNum() {
                $.get("${ctx}/oa/oaNotify/self/count?updateSession=0&t=" + new Date().getTime(), function (data) {
                    var num = parseFloat(data);
                    if (num > 0) {
                        $("#notifyNum,#notifyNum2").show().html("(" + num + ")");
                    } else {
                        $("#notifyNum,#notifyNum2").hide()
                    }
                });
            }

            //判断是否首次登录，强制要求修改密码
            if (${fns:getUser().loginIp == ""}) {
                $("#firstLoginPwdModal").css("display", "block");
                $("#firstLoginPwdModal").modal('show');
                var errorMessage = getUrlParam("message");
                if (null != errorMessage) {
                    $("#pwdMessage").empty().append("<p class=\"text-center\" style=\"color: red;font-size: 18px;\">" + errorMessage + "</p>");
                }
                $.validator.addMethod("pwd", function (value, element) {
                    var v_regex = /^(?!.*[！·（）【】“”：；，》￥、。‘’——……\n\t\s\v\r])(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~`!@#$%^&*)(?,.></_|+=}{;:'"\\\]\[\-])[^\u4e00-\u9fa5]{8,20}$/;
                    value = "" + value;
                    if (value) {
                        if (!v_regex.test(value)) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }, "8-20位，其中必须包含数字、小写字母、大写字母及特殊字符，不支持空格及中文");
                var errorFlag = $("#firstLoginPwdForm").validate({
                    rules: {
                        oldPassword: {required: true},
                        newPassword: {required: true},
                        confirmNewPassword: {required: true}
                    },
                    messages: {
                        confirmNewPassword: {equalTo: "两次输入密码不一致", required: "必输项"},
                        oldPassword: {required: "必输项"},
                        newPassword: {required: "必输项"}
                    }
                });

                $("#changePasswordBtn").click(function () {
                    if (errorFlag.valid()) {
                        var pwdVal = $('#newPassword').val()
                        var oldPwdVal = $('#oldPassword').val()
                        var conPwdVal = $('#confirmNewPassword').val()
                        if (pwdVal == '' || oldPwdVal == '' || conPwdVal == '') {
                            showTip('请保证信息正确后提交', 'error', 1000, 0);
                            return;
                        }
                        var v_regex = /^(?!.*[！·（）【】“”：；，》￥、。‘’——……\n\t\s\v\r])(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~`!@#$%^&*)(?,.></_|+=}{;:'"\\\]\[\-])[^\u4e00-\u9fa5]{8,20}$/;
                        if (!v_regex.test(pwdVal)) {
                            showTip('请保证信息正确后提交', 'error', 1000, 0);
                            return;
                        }

                        $('#encyptOldPassword').val(encryptRSA($('#oldPassword').val()));
                        $('#encyptNewPassword').val(encryptRSA($('#newPassword').val()));
                        $('#encyptConfirmNewPassword').val(encryptRSA($('#confirmNewPassword').val()));
                        $("#firstLoginPwdForm").submit();

                    } else {
                        showTip('请保证信息正确后提交', 'error', 1000, 0);
                    }
                });

            }

        });

        //获取url中的参数
        function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURI(r[2]);
            return null;
        }

        // <c:if test="${tabmode eq '1'}"> 添加一个页签
        function addTab($this, refresh) {
            $(".jericho_tab").show();
            $("#mainFrame").hide();
            /*20200319 add by chenyl for 添加是否开多tab判断,默认开启 */
            var multTab = true;		/*是否多标签tab页面*/
            var closeable = true;	/*是否启用关闭tab功能*/
            if (!multTab) {
                var selTab = $('.tab_selected');
                var pathname = $this.attr('href');
                var datalink = selTab.attr('datalink');
                /*已存在打开的tab页*/
                if (undefined != datalink) {
                    /**
                     1、如果已经打开相同的页面则不再往后执行
                     */
                    if (pathname == datalink) {
                        return false;
                    } else {
                        console.info("jericho_tab", $this);
                        if (closeable) {
                            $.fn.jerichoTab.closeCurrentTab();
                        } else {
                            selTab.attr('datalink', pathname);
                            var newTitle = $this.text();
                            selTab.attr('name', newTitle);
                            selTab.find('div .tab_text').each(function (e) {
                                var $that = $(this);
                                $that.attr('title', newTitle);
                                $that.html(newTitle);
                            });
                            var jerichotabiframe = 'jerichotabiframe_0';
                            var src = pathname;
                            src += (src.indexOf('?') == -1 ? '?' : '&') + 'tabPageId=' + jerichotabiframe;
                            $('#' + jerichotabiframe).attr('src', src);
                            return false;
                        }
                    }
                }
            } else {
                /*多tab时，开启关闭tab功能*/
                closeable = true;
            }
            $.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $this.text(),
                closeable: closeable,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('href')
                }
            }).loadData(refresh);
            //左侧菜单随着tab标签选中改变
            var eventClick = $(".jericho_tabs:last").data("events");
            if (eventClick && !eventClick['click']) {
                $(".jericho_tabs:last").click(function () {
                    var tabText = $(this).attr("datalink").trim();
                    var hrefTarget = "";
                    $(".menuHeader").find("a").each(function () {
                        if (tabText == $(this).attr("href").trim()) {
                            $("#left-index .menuHeader").hide();
                            $(this).parent().parent().parent().parent().parent().show();
                            $(this).parent().parent().parent().parent().prev().click();
                            $(".menu3-list-focus").removeClass("menu3-list-focus").addClass("menu3-list-blur");
                            $(this).removeClass("menu3-list-blur").addClass("menu3-list-focus");
                            $(".menu3-circle-focus").removeClass("menu3-circle-focus").addClass("menu3-circle-blur");
                            $(this).parent().prev().removeClass("menu3-circle-blur").addClass("menu3-circle-focus");
                            hrefTarget = $(this).attr("id");
                        }
                    })
                    window.location.hash = "#" + hrefTarget;
                });
            }
            $(".tab_selected").click();
            return false;
        }// </c:if>

        //下拉框选择后调用打开左侧菜单
        function openLeftMenu(pId, pName) {
            var menuId = $("#searchMenuId").val();
            var menuName = $("#searchMenuName").val();
            var url = ctx + "/sys/menu/getMenuPath";
            $.post(url,
                {menuId: menuId, menuName: menuName, pId: pId, pName: pName},
                function (data) {
                    if (data) {
                        var msg = "查询菜单[" + menuName + "]成功";
                        top.$.jBox.tip(msg, "success", {persistent: true, opacity: 0});
                        openMenu(data);
                    } else {
                        var msg = "查询菜单[" + menuName + "]失败";
                        top.$.jBox.tip(msg, "error", {persistent: true, opacity: 0});
                    }
                }
            )
        }

        //设置cookie
        function setCookie(cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + d.toUTCString();
            document.cookie = cname + "=" + cvalue + "; " + expires + "; path=/";//path=/是根路径
        }

        //获取cookie
        function getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1);
                if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
            }
            return undefined;
        }

        //清除cookie
        function clearCookie(name) {
            setCookie(name, "", -1);
        }

        //设置新消息旁边红点是否显示
        function trackPointShow() {
            $.post(ctx + '/sys/modules/sysNotice/getNoticeList', function (data) {
                var index = 0;
                for (var i = 0; i < data.length; i++) {
                    var date = new Date(data[i].date);
                    var now = new Date();
                    var days = parseInt((now.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
                    if (days < 8) {
                        index++;
                    }
                }
                if (index <= 0) {
                    $(".top5-2").hide();
                }
            }, "json")
        }

        //搜索框搜索内容变化执行方法
        function searchMenu() {
            var searchText = new RegExp(".*" + $("#searchMenuName").val() + ".*");
            $(".menuda .menu3title").each(function () {
                var mainMenuText = $(this).parent().parent().text().trim();

                if (searchText.test(mainMenuText)) {
                    var firstIndex = 0;
                    $(this).parent().parent().removeClass("menuHide").addClass("menuShow");
                    $(this).parent().find(".menuModel4").each(function () {

                        var secondMenuText = $(this).text().trim();
                        if (searchText.test(secondMenuText)) {
                            firstIndex++;
                            $(this).removeClass("menuHide").addClass("menuShow");
                            var index = 0;
                            $(this).find("a").each(function () {
                                var thirdMenuText = $(this).text().trim();
                                if (searchText.test(thirdMenuText)) {
                                    index++;
                                    $(this).parent().parent().removeClass("menuHide").addClass("menuShow");
                                } else {
                                    $(this).parent().parent().removeClass("menuShow").addClass("menuHide");
                                }
                            })
                            if (index <= 0) {
                                $(this).find("a").each(function () {
                                    $(this).parent().parent().removeClass("menuHide").addClass("menuShow");
                                })
                            }
                        } else {
                            $(this).removeClass("menuShow").addClass("menuHide");
                        }


                    })

                    if (firstIndex <= 0) {
                        $(this).parent().find(".menuModel4").each(function () {
                            $(this).removeClass("menuHide").addClass("menuShow");
                            $(this).find("a").each(function () {
                                $(this).parent().parent().removeClass("menuHide").addClass("menuShow");
                            })
                        })
                    }
                } else {
                    $(this).parent().parent().removeClass("menuShow").addClass("menuHide");
                }

            })
        }

        /**
         *  2020.12.13 启用websocket zhangyq
         */
        /* 进行连接 */
        function connect() {
            var subscriptions = [
                {
                    destination: "/noticeMessage",
                    onMessage: function (receiveMessage) {
                        // 收到消息后弹出窗口并在窗口里显示信息
                        if (receiveMessage["popupFlg"] === "Y") {
                            showInfo(receiveMessage["noteTitle"], receiveMessage["noteCntt"], receiveMessage["id"], receiveMessage["timeout"]);
                        }
                    },
                    headers: {}
                }
            ];
            stompC.connect(subscriptions);
        }
    </script>
</head>
<body>
<!-- 首次登录修改密码模态框（Modal） -->
<div
        class="modal fade"
        style="display: none"
        id="firstLoginPwdModal"
        tabindex="-1"
        role="dialog"
        aria-labelledby="myModalLabel"
        aria-hidden="true"
        data-keyboard="false"
>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">首次登录密码修改</h4>
            </div>
            <div class="modal-body">
                <div id="pwdMessage">
                    <p class="text-center" style="color: #0e90d2; font-size: 15px">检测到您的账号是首次登录，请修改密码</p>
                </div>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <form
                                class="form-horizontal"
                                id="firstLoginPwdForm"
                                action="${ctx}/sys/user/firstLoginModifyPwd"
                                method="post"
                        >
                            <div class="form-group" style="padding-top: 10px">
                                <label class="col-sm-2 control-label" for="oldPassword"
                                       style="padding-right: 20px">旧密码:</label>
                                <input
                                        type="password"
                                        class="form-control"
                                        id="oldPassword"
                                        placeholder="请输入旧密码"
                                        required
                                        style="width: 260px"
                                />
                                <input type="hidden" id="encyptOldPassword" name="oldPassword"/>
                            </div>
                            <div class="form-group" style="padding-top: 10px">
                                <label class="col-sm-2 control-label" for="newPassword"
                                       style="padding-right: 20px">新密码:</label>
                                <input
                                        type="password"
                                        class="form-control"
                                        id="newPassword"
                                        placeholder="请输入新密码"
                                        pwd="true"
                                        required
                                        style="width: 260px"
                                />
                                <input type="hidden" id="encyptNewPassword" name="newPassword"/>
                            </div>
                            <div class="form-group" style="padding-top: 10px">
                                <label class="col-sm-2 control-label" for="confirmNewPassword"
                                       style="padding-right: 20px">
                                    确认新密码:
                                </label>
                                <input
                                        type="password"
                                        class="form-control"
                                        id="confirmNewPassword"
                                        placeholder="请确认新密码"
                                        required
                                        equalTo="#newPassword"
                                        style="width: 260px"
                                />
                                <input type="hidden" id="encyptConfirmNewPassword" name="confirmNewPassword"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="changePasswordBtn" type="button" class="btn btn-primary">提交更改</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div
        class="modal fade"
        id="switchModal"
        style="display: none"
        tabindex="-1"
        role="dialog"
        aria-hidden="true"
        style="margin-top: 100px"
>
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">切换租户</h4>
            </div>
            <div class="modal-body">
                <form
                        ravo="rainbow_fx_layout_bd"
                        class="form-horizontal"
                        action="${ctx}/sys/rent/switchRent"
                        method="post"
                        enctype="multipart/form-data"
                        id="switchForm"
                        style="margin-top: 20px; margin-left: 80px"
                        onsubmit="loading('正在切换，请稍等...');"
                >
                    <div class="col-md-12 column">
                        <div ravo="rainbow_fx" class="form-group">
                            <label for="inputEmail3" class="col-sm-5 control-label"></label>
                            <div class="col-sm-7">
                                <sys:treeselect
                                        id="rentId"
                                        name="rentId"
                                        value=""
                                        treesearch_required="true"
                                        label_name="rentIdName"
                                        label_value=""
                                        title="租户"
                                        url="/sys/rent/getUserTntData"
                                        allow_clear="true"
                                        css_class="form-control input-small"
                                        is_all="false"
                                ></sys:treeselect>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="cancle2" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" id="" class="btn btn-primary" onclick="switchBtn()">确定</button>
            </div>
        </div>
    </div>
</div>
<div id="main">
    <div class="top" id="header">
        <div class="top1">
            <div class="top11">
                <div class="topright"></div>
            </div>
        </div>
        <div class="top2">
            <div class="logoleft"></div>
            <div class="sysName">${fns:getConfig('productName')}</div>
            <div class="sousuo">
                <div class="s1">
                    <input
                            type="text"
                            class="sp1 required"
                            placeholder="请输入搜索内容"
                            onfocus="this.placeholder=''"
                            onblur="this.placeholder='请输入搜索内容'"
                            id="searchMenuName"
                            autocomplete="new-password"
                    />
                </div>
                <div class="s2">
                    <button class="sb1">GO</button>
                </div>
            </div>
            <a href="${ctx}/logout">
                <div class="guanbi">
                    <button class="guanbi1"></button>
                </div>
            </a>
            <div class="top3">
                <div class="top3-1">
                    <span class="zi1">您好，</span>
                    <span class="zi2">${fns:getUser().name}</span>
                </div>
                <div class="top3-2">
                    <span class="zi3">当前租户：</span>
                    <span class="zi2">${fns:getUser().rent}</span>
                </div>
                <div class="top3-3">
                    <span class="zi3" id="lastLoginDate">上次登录时间：${fns:getUser().loginDate }</span>
                </div>
            </div>
            <div class="top4">
                <button class="sb2"></button>
                <ul class="dropdown-menu">
                    <li>
                        <a href="${ctx}/sys/user/info" target="mainFrame" menuPath="我的面板->个人信息->个人信息">
                            <i class="icon-user"></i>
                            &nbsp; 个人信息
                        </a>
                    </li>
                    <li>
                        <a href="${ctx}/sys/user/modifyPwd" target="mainFrame" menuPath="我的面板->个人信息->修改密码">
                            <i class="icon-lock"></i>
                            &nbsp; 修改密码
                        </a>
                    </li>
                    <li>
                        <a
                                href="${ctx}/sys/modules/sysQuickEntry/mainIndex"
                                target="mainFrame"
                                menuPath="我的面板->个人信息->我的首页"
                        >
                            <i class="icon-bell"></i>
                            &nbsp; 我的首页
                            <span id="notifyNum2" class="label label-info hide"></span>
                        </a>
                    </li>
                </ul>
            </div>

            <div class="top5">
                <div class="top5-1">
                    <button class="sb3"></button>
                    <div class="top5-2"></div>
                </div>
            </div>
            <div class="top6">
                <div class="top6-1">
                    <button class="sb6"></button>
                </div>
            </div>
        </div>
    </div>
    <div class="line"></div>

    <!-- 隐藏的一级菜单 -->
    <div class="menu-hidden" style="display: none">
        <ul id="firstMenu-hiddenList">
            <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                <c:if test="${menu.parent.id eq '1'&&menu.dpyFlg eq '1'}">
                    <li class="firstMenu-hidden">
                        <c:if test="${empty menu.menuLink}">
                            <a
                                    class="firstMenu-link"
                                    href="javascript:"
                                    data-href="${ctx}/sys/menu/tree?parentId=${menu.id}"
                                    data-id="${menu.id}"
                            >
                                    ${menu.name}
                            </a>
                        </c:if>
                        <c:if test="${not empty menu.menuLink}">
                            <a
                                    class="firstMenu-link"
                                    href="${fn:indexOf(menu.menuLink, '://') eq -1 ? ctx : ''}${menu.menuLink}"
                                    data-id="${menu.id}"
                                    target="mainFrame"
                            >
                                    ${menu.name}
                            </a>
                        </c:if>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>

    <div class="index1" id="content">
        <div class="index1-1" id="left-index"></div>
        <div class="index1-1-2" id="transverter">
            <div class="fold"></div>
        </div>
        <div class="index-menu" id="right">
            <div class="firstIndex" data-href="${ctx}/sys/menu/mainMenu"></div>
            <iframe
                    id="mainFrame"
                    name="mainFrame"
                    src=""
                    style="overflow: visible"
                    scrolling="yes"
                    frameborder="no"
                    width="100%"
                    height="650"
            ></iframe>
        </div>
        <div id="footer-div"></div>
<%--        <div id="footer-div">&copy; ${fns:getConfig('copyrightYear')} ADTEC CO.,Ltd . All rights reserved.</div>--%>
    </div>
</div>
<script type="text/javascript">
    var leftWidth = 157 // 左侧窗口大小
    var tabTitleHeight = 36 // 页签的高度
    var htmlObj = $('html'),
        mainObj = $('#main')
    var headerObj = $('#header'),
        footerObj = $('#footer-div')
    var frameObj = $('#left-index, #transverter, #right, #right iframe')

    function wSize() {
        var minHeight = 500,
            minWidth = 980
        var strs = getWindowSize().toString().split(',')
        htmlObj.css({
            'overflow-x': strs[1] < minWidth ? 'auto' : 'hidden',
            'overflow-y': strs[0] < minHeight ? 'auto' : 'hidden'
        })
        mainObj.css('width', strs[1] < minWidth ? minWidth - 10 : 'auto')
        /*frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));*/
        frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height())
        $('#transverter').height($('#transverter').height() - 0) // <c:if test="${tabmode eq '1'}">
        $('.jericho_tab iframe').height($('#right').height()) // </c:if>
        wSizeWidth()
    }

    function wSizeWidth() {
        if (!$('#transverter').is(':hidden')) {
            var leftWidth = $('#left-index').width() < 0 ? 0 : $('#left-index').width()
            $('#right').width($('#content').width() - leftWidth - $('#transverter').width() - 0)
        } else {
            $('#right').width('100%')
        }
    } // <c:if test="${tabmode eq '1'}">

    function openCloseClickCallBack(b) {
        $.fn.jerichoTab.resize()
    } // </c:if>

    function switchBtn() {
        var rentId = $('#rentIdId').val()
        if (rentId == '') {
            showTip('请选择租户', 'error')
            return
        }
        $('#switchForm').submit()
    }
</script>
<script src="${ctxStatic}/common/wsize.js" type="text/javascript"></script>
</body>
</html>
