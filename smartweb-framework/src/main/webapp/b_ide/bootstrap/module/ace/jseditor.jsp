<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <%
    String webapp = request.getContextPath();
    String basePath = webapp; %>
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <title>Editor</title>
        <script type="text/javascript" src="<%=basePath%>/b_base/jquery-1.8.3.min.js" charset="utf-8">
        </script>
        <!-- easyUI -->
        <link rel="stylesheet" type="text/css" href="<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/default/easyui.css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/icon.css" />
        <script type="text/javascript" src="<%=basePath%>/b_base/jquery-easyui-1.4.2/jquery.easyui.min.js">
        </script>
        <script src="src-noconflict/ace.js" type="text/javascript" charset="utf-8">
        </script>
        <script src="src-noconflict/ext-language_tools.js" type="text/javascript" charset="utf-8">
        </script>
        <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/jsformat/jsformat.js" charset="utf-8"></script>
	    <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/jsformat/htmlformat.js" charset="utf-8"></script>
        <!-- messenger -->
        <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/messenger.js" charset="utf-8">
        </script>
        <style type="text/css" media="screen">
            body {
                overflow: hidden;
            }
            
            #editor {
                margin: auto;
                position: absolute;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
                width: 100%;
                border: 0px solid lightgray;
                z-index: 5;
            }
        </style>
    </head>
    <body>
        <pre id="editor">
			//	开始编辑 JS
		</pre>
        <script type="text/javascript">
            var editor = ace.edit("editor");
            /**
             1.0自动补全
             enableBasicAutocompletion：快捷键补全
             enableSnippets：判断补全
             enableLiveAutocompletion：自动补全
             */
            var autoCompleteData = [{
                "meta": "function",
                "caption": "ajax",
                "value": "$.ajax({ url: 'test.html', context: document.body, success: function(){});",
                "score": 1
            }];
            var completions = [];
            completions.push({
                caption: 'ajax',
                snippet: "$.ajax({\n  type:\"POST\",\n  url: \"some.php\",\n  data: \"\",\n  success: function(msg){\n  }\n});",
                meta: "snippet",
                type: "snippet"
            });
            
            //editor.indent();
            /*
             meta：显示在提示框的右边（如下图）。
             caption：显示在提示框的左边（如下图）。
             value：是实际插入的数据。
             score：表示优先级，高的排在前面。
             */
            var tangideCompleter = {
                getCompletions: function(editor, session, pos, prefix, callback){
                    if (prefix.length === 0) {
                        return callback(null, []);
                    }
                    else {
                        return callback(null, completions);
                    }
                }
            }
            var langTools = ace.require("ace/ext/language_tools");
            
            editor.$blockScrolling = Infinity;
            
            editor.setOptions({
                enableBasicAutocompletion: true,
                enableSnippets: true,
                enableLiveAutocompletion: false
            });
            langTools.addCompleter(tangideCompleter);
            
            //2.0主题
            /*ambiance'chaos'chrome'clouds_midnigh'clouds'cobalt'crimson_editor'dawn'dreamweaver'eclipse'github'idle_fingers'
             iplastic'katzenmilch'kr_theme'kuroir'merbivore_soft'merbivore'mono_industrial'monokai'pastel_on_dark'solarized_dark'
             solarized_light'sqlserver'terminal'textmate'tomorrow_night_blue'tomorrow_night_bright'tomorrow_night_eighties'
             tomorrow_night'tomorrow'twilight'vibrant_ink'xcode
             */
            editor.setTheme("ace/theme/xcode");// cobalt  merbivore_soft sqlserver terminal textmate tomorrow_night_blue tomorrow_night_bright tomorrow_night'tomorrow vibrant_ink'xcode
            //3.0设置程序语言模式
            editor.session.setMode("ace/mode/javascript");
            //4.0设置代码折叠:
            editor.getSession().setUseWrapMode(true);
            //5.0设置高亮:
            editor.setHighlightActiveLine(true);
            //6.0设置、获取内容:
            var value = editor.getValue();
            /* editor.setValue('-------------'); */
            //7.0获取总行数:
            var length = editor.session.getLength();
            //8.0跳转到行:
            editor.gotoLine();
            //9.0使用软标签:
            editor.getSession().setUseSoftTabs(true);
            editor.getSession().setTabSize(4);
            //10.0在光标处插入:
            /* editor.insert("Something cool"); */
            
            //11.0获取光标所在行或列:
            /* editor.selection.getCursor(); */
            
            /**
             12.0搜索
             needle: 要查找的字符串或正则表达式
             backwards: 是否反向搜索，默认为false
             wrap: 搜索到文档底部是否回到顶端，默认为false
             caseSensitive: 是否匹配大小写搜索，默认为false
             wholeWord: 是否匹配整个单词搜素，默认为false
             range: 搜索范围，要搜素整个文档则设置为空
             regExp: 搜索内容是否是正则表达式，默认为false
             start: 搜索起始位置
             skipCurrent: 是否不搜索当前行，默认为false
             editor.findNext();
             editor.findPrevious();
             **/
            /* editor.find('function',{
             backwards: false,
             wrap: false,
             caseSensitive: false,
             wholeWord: false,
             regExp: false
             });**/
            /**
             13.0 监听改变事件
             
             */
            var isChange = false;
            editor.getSession().on('change', function(e){
                /* console.info(e); */
                isChange = true;
                sendMessage('Parent', 'jschange');
            });
            //设置字体大小
            document.getElementById('editor').style.fontSize = '13px';
            
            /**
             * 以下为扩展JS
             */
            var messenger;
            var jscriptUrl = "";
            function setJspUrl(url){
                jscriptUrl = url;
            }
            
            $(function(){
                var jspUrl = "";
                
                messenger = new Messenger('JSEditerWindow', 'faceui');
                messenger.listen(function(msg){
                    if (typeof msg === "string") {
                        var position = editor.getCursorPosition();
                        if (msg === "111") {
                            jqueryMethod(JqueryMethod.post,position.row);
                        }
                        else if(msg==="112"){
                        	jqueryMethod(JqueryMethod.get,position.row);
                        }
                        else if(msg==="113"){
                        	jqueryMethod(JqueryMethod.loadM,position.row);
                        }
                        else if(msg==="114"){
                        	jqueryMethod(JqueryMethod.ajaxM,position.row);
                        }
                        else if(msg==="121"){
                        	jqueryMethod(JqueryMethod.each,position.row);
                        }
                        else if(msg==="122"){
                        	jqueryMethod(JqueryMethod.parseJSON,position.row);
                        }
                        else if(msg==="123"){
                        	jqueryMethod(JqueryMethod.browser,position.row);
                        }
                        else if(msg==="124"){
                        	jqueryMethod(JqueryMethod.serializeObject,position.row);
                        }
                        /*20181018 add by chenyl for 新增自定义组件渲染*/
                        else if(msg==="125"){
                            jqueryMethod(JqueryMethod.cstCompnentRender,position.row);
                        }
                        /*20181021 add by chenyl for 新增session数据相关操作*/
                        else if(msg==="126"){
                            jqueryMethod(JqueryMethod.sessionAdd,position.row);
                        }
                        else if(msg==="127"){
                            jqueryMethod(JqueryMethod.sessionGet,position.row);
                        }
                        else if(msg==="128"){
                            jqueryMethod(JqueryMethod.sessionRemove,position.row);
                        }
                        else if(msg==="129"){
                            jqueryMethod(JqueryMethod.sessionClear,position.row);
                        }
                        /*20181021 add by chenyl for 新增打开菜单操作*/
                        else if(msg==="1210"){
                            jqueryMethod(JqueryMethod.openMenu,position.row);
                        }
                        /*20181021 add by chenyl for 新增设置组件的回调函数*/
                        else if(msg==="1211"){
                            jqueryMethod(JqueryMethod.setEnterKeyEvent,position.row);
                        }
                        /*20181021 add by chenyl for 新增清空表单方法*/
                        else if(msg==="1212"){
                            jqueryMethod(JqueryMethod.clearForm,position.row);
                        }
                        /*20181021 add by chenyl for 新增异步装载页面到指定的div*/
                        else if(msg==="1213"){
                            jqueryMethod(JqueryMethod.loadJspByDiv,position.row);
                        }
                        /*20181021 add by chenyl for 新增文件下载，支持多文件下载*/
                        else if(msg==="1214"){
                            jqueryMethod(JqueryMethod.download,position.row);
                        }
                        /*20181022 add by chenyl for 新增数据导入、导出*/
                        else if(msg==="1215"){
                            jqueryMethod(JqueryMethod.exportData,position.row);
                        }
                        else if(msg==="1216"){
                            jqueryMethod(JqueryMethod.importData,position.row);
                        }
                        else if(msg==="131"){
                        	jqueryMethod(JqueryMethod.done,position.row);
                        }
                        else if(msg==="132"){
                        	jqueryMethod(JqueryMethod.fail,position.row);
                        }
                        else if(msg==="141"){
                        	jqueryMethod(JqueryMethod.add,position.row);
                        }
                        else if(msg==="142"){
                        	jqueryMethod(JqueryMethod.disable,position.row);
                        }
                        else if(msg==="143"){
                        	jqueryMethod(JqueryMethod.empty,position.row);
                        }
                        else if(msg==="144"){
                        	jqueryMethod(JqueryMethod.remove,position.row);
                        }
                        else if(msg==="151"){
                        	jqueryMethod(JqueryMethod.edefault,position.row);
                        }
                        else if(msg==="152"){
                        	jqueryMethod(JqueryMethod.estop,position.row);
                        }
                        else if(msg==="161"){
                        	jqueryMethod(JqueryMethod.on,position.row);
                        }
                        else if(msg==="162"){
                        	jqueryMethod(JqueryMethod.bind,position.row);
                        }
                        else if(msg==="163"){
                        	jqueryMethod(JqueryMethod.unbind,position.row);
                        }
                        else if(msg==="164"){
                        	jqueryMethod(JqueryMethod.keydown,position.row);
                        }
                        //表格事件(Jquery)
                        else if(msg==='2011'){
                        	jqueryMethod(JqueryMethod.onAll,position.row);
                        }
                        else if(msg==='2012'){
                        	jqueryMethod(JqueryMethod.onClickRow,position.row);
                        }
                        else if(msg==='2013'){
                        	jqueryMethod(JqueryMethod.onDblClickRow,position.row);
                        }
                        else if(msg==='2014'){
                        	jqueryMethod(JqueryMethod.onClickCell,position.row);
                        }
                        else if(msg==='2015'){
                        	jqueryMethod(JqueryMethod.onDblClickCell,position.row);
                        }
                        else if(msg==='2016'){
                        	jqueryMethod(JqueryMethod.onSort,position.row);
                        }
                        else if(msg==='2017'){
                        	jqueryMethod(JqueryMethod.onCheck,position.row);
                        }
                        else if(msg==='2018'){
                        	jqueryMethod(JqueryMethod.onUncheck,position.row);
                        }
                        else if(msg==='2019'){
                        	jqueryMethod(JqueryMethod.onCheckAll,position.row);
                        }
                        else if(msg==='20110'){
                        	jqueryMethod(JqueryMethod.onUncheckAll,position.row);
                        }
                        else if(msg==='20111'){
                        	jqueryMethod(JqueryMethod.onCheckSome,position.row);
                        }
                        else if(msg==='20112'){
                        	jqueryMethod(JqueryMethod.onUncheckSome,position.row);
                        }
                        else if(msg==='20113'){
                        	jqueryMethod(JqueryMethod.onLoadSuccess,position.row);
                        }
                        else if(msg==='20114'){
                        	jqueryMethod(JqueryMethod.onLoadError,position.row);
                        }
                        else if(msg==='20115'){
                        	jqueryMethod(JqueryMethod.onColumnSwitch,position.row);
                        }
                        else if(msg==='20116'){
                        	jqueryMethod(JqueryMethod.onColumnSwitch,position.row);
                        }
                        else if(msg==='20117'){
                        	jqueryMethod(JqueryMethod.onPageChange,position.row);
                        }
                        else if(msg==='20118'){
                        	jqueryMethod(JqueryMethod.onSearch,position.row);
                        }
                        else if(msg==='20119'){
                        	jqueryMethod(JqueryMethod.onToggle,position.row);
                        }
                        else if(msg==='20120'){
                        	jqueryMethod(JqueryMethod.onPreBody,position.row);
                        }
                        else if(msg==='20121'){
                        	jqueryMethod(JqueryMethod.onPostBody,position.row);
                        }
                        else if(msg==='20122'){
                        	jqueryMethod(JqueryMethod.onPostHeader,position.row);
                        }
                        else if(msg==='20123'){
                        	jqueryMethod(JqueryMethod.onExpandRow,position.row);
                        }
                        else if(msg==='20124'){
                        	jqueryMethod(JqueryMethod.onCollapseRow,position.row);
                        }
                        else if(msg==='20125'){
                        	jqueryMethod(JqueryMethod.onRefreshOptions,position.row);
                        }
                        else if(msg==='20126'){
                        	jqueryMethod(JqueryMethod.onResetView,position.row);
                        }
                        else if(msg==='2001'){
                        	jqueryMethod(JqueryMethod.queryParams,position.row);
                        }
                        else if(msg==='2002'){
                        	jqueryMethod(JqueryMethod.queryParamsForLimit,position.row);
                        }
                        else if(msg==='2003'){
                        	jqueryMethod(JqueryMethod.rowStyle,position.row);
                        }
                        else if(msg==='2004'){
                        	jqueryMethod(JqueryMethod.cellStyle,position.row);
                        }
                        /*表格方法*/
                        else if(msg==='2031'){
                        	jqueryMethod(JqueryMethod.getOptions,position.row);
                        }
                        else if(msg==='2032'){
                        	jqueryMethod(JqueryMethod.getSelections,position.row);
                        }
                        else if(msg==='2033'){
                        	jqueryMethod(JqueryMethod.getAllSelections,position.row);
                        }
                        else if(msg==='2034'){
                        	jqueryMethod(JqueryMethod.getData,position.row);
                        }
                        else if(msg==='2035'){
                        	jqueryMethod(JqueryMethod.getRowByUniqueId,position.row);
                        }
                        else if(msg==='2036'){
                        	jqueryMethod(JqueryMethod.load,position.row);
                        }
                        else if(msg==='2037'){
                        	jqueryMethod(JqueryMethod.append,position.row);
                        }
                        else if(msg==='2038'){
                        	jqueryMethod(JqueryMethod.prepend,position.row);
                        }
                        else if(msg==='2039'){
                        	jqueryMethod(JqueryMethod.remove,position.row);
                        }
                        else if(msg==='20310'){
                        	jqueryMethod(JqueryMethod.removeAll,position.row);
                        }
                        else if(msg==='20311'){
                        	jqueryMethod(JqueryMethod.removeByUniqueId,position.row);
                        }
                        else if(msg==='20312'){
                        	jqueryMethod(JqueryMethod.insertRow,position.row);
                        }
                        else if(msg==='20313'){
                        	jqueryMethod(JqueryMethod.updateRow,position.row);
                        }
                        else if(msg==='20314'){
                        	jqueryMethod(JqueryMethod.updateByUniqueId,position.row);
                        }
                        else if(msg==='20315'){
                        	jqueryMethod(JqueryMethod.showRow,position.row);
                        }
                        else if(msg==='20316'){
                        	jqueryMethod(JqueryMethod.hideRow,position.row);
                        }
                        else if(msg==='20317'){
                        	jqueryMethod(JqueryMethod.refresh,position.row);
                        }
                        else if(msg==='20318'){
                        	jqueryMethod(JqueryMethod.refreshOptions,position.row);
                        }
                        else if(msg==='20319'){
                        	jqueryMethod(JqueryMethod.resetView,position.row);
                        }
                        else if(msg==='20320'){
                        	jqueryMethod(JqueryMethod.mergeCells,position.row);
                        }
                        else if(msg==='20321'){
                        	jqueryMethod(JqueryMethod.checkAll,position.row);
                        }
                        else if(msg==='20322'){
                        	jqueryMethod(JqueryMethod.uncheckAll,position.row);
                        }
                        else if(msg==='20323'){
                        	jqueryMethod(JqueryMethod.check,position.row);
                        }
                        else if(msg==='20324'){
                        	jqueryMethod(JqueryMethod.uncheck,position.row);
                        }
                        else if(msg==='20325'){
                        	jqueryMethod(JqueryMethod.checkBy,position.row);
                        }
                        else if(msg==='20326'){
                        	jqueryMethod(JqueryMethod.uncheckBy,position.row);
                        }
                        else if(msg==='20327'){
                        	jqueryMethod(JqueryMethod.showColumn,position.row);
                        }
                        else if(msg==='20328'){
                        	jqueryMethod(JqueryMethod.hideCoulumn,position.row);
                        }
                        else if(msg==='20329'){
                        	jqueryMethod(JqueryMethod.expandRow,position.row);
                        }
                        else if(msg==='20330'){
                        	jqueryMethod(JqueryMethod.collapseRow,position.row);
                        }
                        /* 20181018 add by chenyl for 添加表格查询重置下标*/
                        else if(msg==="20331"){
                            jqueryMethod(JqueryMethod.refreshIndex,position.row);
                        }
                        /* 20181018 add by chenyl for 设置表的列宽*/
                        else if(msg==="20332"){
                            jqueryMethod(JqueryMethod.columnWidth,position.row);
                        }
                        /* 20181021 add by chenyl for 显示表格*/
                        else if(msg==="20333"){
                            jqueryMethod(JqueryMethod.showTable,position.row);
                        }
                        /* 20181018 add by chenyl for 隐藏表格*/
                        else if(msg==="20334"){
                            jqueryMethod(JqueryMethod.hideTable,position.row);
                        }
                        
                        else if(msg==='3001'){
                        	jqueryMethod(JqueryMethod.multiselect,position.row);
                        	
                        }
                        else if(msg==='3002'){
                        	jqueryMethod(JqueryMethod.multiselectdestroy,position.row);
                        	
                        }
                        else if(msg==='3003'){
                        	jqueryMethod(JqueryMethod.multiselectrefresh,position.row);
                        	
                        }
                        else if(msg==='3004'){
                        	jqueryMethod(JqueryMethod.multiselectrebuild,position.row);
                        	
                        }
                        else if(msg==='3005'){
                        	jqueryMethod(JqueryMethod.multiselectsetArray,position.row);
                        	
                        }
                        else if(msg==='3006'){
                        	jqueryMethod(JqueryMethod.multideselectsetArray,position.row);
                        	
                        }
                        else if(msg==='3007'){
                        	jqueryMethod(JqueryMethod.multiselectselectAll,position.row);
                        	
                        }
                        else if(msg==='3008'){
                        	jqueryMethod(JqueryMethod.multiselectdeselectAll,position.row);
                        	
                        }
                        else if(msg==='3009'){
                        	jqueryMethod(JqueryMethod.multiselectupdateButtonText,position.row);
                        	
                        }
                        else if(msg==='30010'){
                        	jqueryMethod(JqueryMethod.multiselectsetOptions,position.row);
                        	
                        }
                        else if(msg==='30011'){
                        	jqueryMethod(JqueryMethod.multiselectdisable,position.row);
                        	
                        } 
                        else if(msg==='30012'){
                        	jqueryMethod(JqueryMethod.multiselectenable,position.row);
                        	
                        }
                        else if(msg==='30013'){
                        	jqueryMethod(JqueryMethod.multiselectdataprovider,position.row);
                        	
                        }
                        
                        else if(msg==='4001'){
                        	jqueryMethod(JqueryMethod.enableClickableOptGroups,position.row);
                        }
                        else if(msg==='4002'){
                        	jqueryMethod(JqueryMethod.enableCollapsibleOptGroups,position.row);
                        }
                        else if(msg==='4003'){
                        	jqueryMethod(JqueryMethod.disableIfEmpty,position.row);
                        }
                        else if(msg==='4004'){
                        	jqueryMethod(JqueryMethod.disabledText,position.row);
                        }
                        else if(msg==='4005'){
                        	jqueryMethod(JqueryMethod.buttonWidth,position.row);
                        }
                        else if(msg==='4006'){
                        	jqueryMethod(JqueryMethod.dropRight,position.row);
                        }
                        else if(msg==='4007'){
                        	jqueryMethod(JqueryMethod.dropUp,position.row);
                        }
                        else if(msg==='4008'){
                        	jqueryMethod(JqueryMethod.maxHeight,position.row);
                        }
                        else if(msg==='4009'){
                        	jqueryMethod(JqueryMethod.buttonClass,position.row);
                        }
                        else if(msg==='40010'){
                        	jqueryMethod(JqueryMethod.inheritClass,position.row);
                        }
                        else if(msg==='40011'){
                        	jqueryMethod(JqueryMethod.nonSelectedText,position.row);
                        }
                        else if(msg==='40012'){
                        	jqueryMethod(JqueryMethod.numberDisplayed,position.row);
                        }
                        else if(msg==='40013'){
                        	jqueryMethod(JqueryMethod.nSelectedText,position.row);
                        }
                        else if(msg==='40014'){
                        	jqueryMethod(JqueryMethod.allSelectedText,position.row);
                        }
                        else if(msg==='40015'){
                        	jqueryMethod(JqueryMethod.delimiterText,position.row);
                        }
                        else if(msg==='40016'){
                        	jqueryMethod(JqueryMethod.selectedClass,position.row);
                        }
                        else if(msg==='40017'){
                        	jqueryMethod(JqueryMethod.enableFiltering,position.row);
                        }
                        else if(msg==='40018'){
                        	jqueryMethod(JqueryMethod.enableCaseInsensitiveFiltering,position.row);
                        }
                        else if(msg==='40019'){
                        	jqueryMethod(JqueryMethod.enableFullValueFiltering,position.row);
                        }
                        else if(msg==='40020'){
                        	jqueryMethod(JqueryMethod.filterBehavior,position.row);
                        }
                        else if(msg==='40021'){
                        	jqueryMethod(JqueryMethod.filterPlaceholder,position.row);
                        }
                        else if(msg==='40022'){
                        	jqueryMethod(JqueryMethod.includeSelectAllOption,position.row);
                        }
                        else if(msg==='40023'){
                        	jqueryMethod(JqueryMethod.selectAllJustVisible,position.row);
                        }
                        else if(msg==='40024'){
                        	jqueryMethod(JqueryMethod.selectAllText,position.row);
                        }
                        else if(msg==='40025'){
                        	jqueryMethod(JqueryMethod.selectAllValue,position.row);
                        }
                        else if(msg==='40026'){
                        	jqueryMethod(JqueryMethod.selectAllName,position.row);
                        }
                        else if(msg==='5001'){
                        	jqueryMethod(JqueryMethod.multiselectonChange,position.row);
                        }
                        else if(msg==='5002'){
                        	jqueryMethod(JqueryMethod.multiselectonDropdownShow,position.row);
                        }
                        else if(msg==='5003'){
                        	jqueryMethod(JqueryMethod.multiselectonDropdownShown,position.row);
                        }
                        else if(msg==='5004'){
                        	jqueryMethod(JqueryMethod.multiselectonDropdownHidden,position.row);
                        }
                        
                        else if(msg==='5005'){
                        	jqueryMethod(JqueryMethod.multiselectonbuttonText,position.row);
                        }
                        else if(msg==='5006'){
                        	jqueryMethod(JqueryMethod.multiselectbuttonTitle,position.row);
                        }
                        else if(msg==='5007'){
                        	jqueryMethod(JqueryMethod.multiselectoptionLabel,position.row);
                        }
                        else if(msg==='5008'){
                        	jqueryMethod(JqueryMethod.multiselectoptionClass,position.row);
                        }
                        else if(msg==='5009'){
                        	jqueryMethod(JqueryMethod.multiselectonSelectAll,position.row);
                        }
                        else if(msg==='50010'){
                        	jqueryMethod(JqueryMethod.multiselectonInitialized,position.row);
                        }
                        /*20181021 add by chenyl for 新增设置下拉框选项不可编辑*/
                        else if(msg==='50011'){
                            jqueryMethod(JqueryMethod.multiselectReadOnly,position.row);
                        }
                        else if(msg==="2201"){
                        	jqueryMethod(JqueryMethod.bootstrpValidator,position.row);
                        }
                        else if(msg==="2202"){
                        	jqueryMethod(JqueryMethod.proof,position.row);
                        }
                        //表格初始化
                        else if(msg==="202"){
                        	jqueryMethod(JqueryMethod.bootstrpTableInit,position.row);
                        }
                        //表格事件(Option 选项)
                        else if(msg==="2051"){
                        	jqueryMethod(JqueryMethod.onAll1,position.row);
                        }
                        else if(msg==="2052"){
                        	jqueryMethod(JqueryMethod.onClickRow1,position.row);
                        }
                        else if(msg==="2053"){
                        	jqueryMethod(JqueryMethod.onDblClickRow1,position.row);
                        }
                        else if(msg==="2054"){
                        	jqueryMethod(JqueryMethod.onClickCell1,position.row);
                        }
                        else if(msg==="2055"){
                        	jqueryMethod(JqueryMethod.onDblClickCell1,position.row);
                        }
                        else if(msg==="2056"){
                        	jqueryMethod(JqueryMethod.onSort1,position.row);
                        }
                        else if(msg==="2057"){
                        	jqueryMethod(JqueryMethod.onCheck1,position.row);
                        }
                        else if(msg==="2058"){
                        	jqueryMethod(JqueryMethod.onUncheck1,position.row);
                        }
                        else if(msg==="2059"){
                        	jqueryMethod(JqueryMethod.onCheckAll1,position.row);
                        }
                        else if(msg==="20510"){
                        	jqueryMethod(JqueryMethod.onUncheckAll1,position.row);
                        }
                        else if(msg==="20511"){
                        	jqueryMethod(JqueryMethod.onCheckSome1,position.row);
                        }
                        else if(msg==="20512"){
                        	jqueryMethod(JqueryMethod.onUncheckSome1,position.row);
                        }
                        else if(msg==="20513"){
                        	jqueryMethod(JqueryMethod.onLoadSuccess1,position.row);
                        }
                        else if(msg==="20514"){
                        	jqueryMethod(JqueryMethod.onLoadError1,position.row);
                        }
                        else if(msg==="20515"){
                        	jqueryMethod(JqueryMethod.onColumnSwitch1,position.row);
                        }
                        else if(msg==="20516"){
                        	jqueryMethod(JqueryMethod.onColumnSearch1,position.row);
                        }
                        else if(msg==="20517"){
                        	jqueryMethod(JqueryMethod.onPageChange1,position.row);
                        }
                        else if(msg==="20518"){
                        	jqueryMethod(JqueryMethod.onSearch1,position.row);
                        }
                        else if(msg==="20519"){
                        	jqueryMethod(JqueryMethod.onToggle1,position.row);
                        }
                        else if(msg==="20520"){
                        	jqueryMethod(JqueryMethod.onPreBody1,position.row);
                        }
                        else if(msg==="20521"){
                        	jqueryMethod(JqueryMethod.onPostBody1,position.row);
                        }
                        else if(msg==="20522"){
                        	jqueryMethod(JqueryMethod.onPostHeader1,position.row);
                        }
                        else if(msg==="20523"){
                        	jqueryMethod(JqueryMethod.onExpandRow1,position.row);
                        }
                        else if(msg==="20524"){
                        	jqueryMethod(JqueryMethod.onCollapseRow1,position.row);
                        }
                        else if(msg==="20525"){
                        	jqueryMethod(JqueryMethod.onRefreshOptions1,position.row);
                        }
                        else if(msg==="20526"){
                        	jqueryMethod(JqueryMethod.onResetView1,position.row);
                        }
                        //表格属性
                        else if(msg==='2041'){
                        	jqueryMethod(JqueryMethod.classes,position.row);
                        }
                        else if(msg==='2042'){
                        	jqueryMethod(JqueryMethod.height,position.row);
                        }
                        else if(msg==='2043'){
                        	jqueryMethod(JqueryMethod.undefinedText,position.row);
                        }
                        else if(msg==='2044'){
                        	jqueryMethod(JqueryMethod.striped,position.row);
                        }
                        else if(msg==='2045'){
                        	jqueryMethod(JqueryMethod.sortName,position.row);
                        }
                        else if(msg==='2046'){
                        	jqueryMethod(JqueryMethod.sortOrder,position.row);
                        }
                        else if(msg==='2047'){
                        	jqueryMethod(JqueryMethod.iconsPrefix,position.row);
                        }
                        else if(msg==='2048'){
                        	jqueryMethod(JqueryMethod.iconSize,position.row);
                        }
                        else if(msg==='2049'){
                        	jqueryMethod(JqueryMethod.icons,position.row);
                        }
                        else if(msg==='20410'){
                        	jqueryMethod(JqueryMethod.columns,position.row);
                        }
                        else if(msg==='20411'){
                        	jqueryMethod(JqueryMethod.data,position.row);
                        }
                        else if(msg==='20412'){
                        	jqueryMethod(JqueryMethod.dataField,position.row);
                        }
                        else if(msg==='20413'){
                        	jqueryMethod(JqueryMethod.ajax,position.row);
                        }
                        else if(msg==='20414'){
                        	jqueryMethod(JqueryMethod.method,position.row);
                        }
                        else if(msg==='20415'){
                        	jqueryMethod(JqueryMethod.url,position.row);
                        }
                        else if(msg==='20416'){
                        	jqueryMethod(JqueryMethod.cache,position.row);
                        }
                        else if(msg==='20417'){
                        	jqueryMethod(JqueryMethod.contentType,position.row);
                        }
                        else if(msg==='20418'){
                        	jqueryMethod(JqueryMethod.dataType,position.row);
                        }
                        else if(msg==='20419'){
                        	jqueryMethod(JqueryMethod.ajaxOptions,position.row);
                        }
                        else if(msg==='20420'){
                        	jqueryMethod(JqueryMethod.queryParams1,position.row);
                        }
                        else if(msg==='20421'){
                        	jqueryMethod(JqueryMethod.queryParamsType,position.row);
                        }
                        else if(msg==='20422'){
                        	jqueryMethod(JqueryMethod.responseHandler,position.row);
                        }
                        else if(msg==='20423'){
                        	jqueryMethod(JqueryMethod.pagination,position.row);
                        }
                        else if(msg==='20424'){
                        	jqueryMethod(JqueryMethod.onlyInfoPagination,position.row);
                        }
                        else if(msg==='20425'){
                        	jqueryMethod(JqueryMethod.sidePagination,position.row);
                        }
                        else if(msg==='20426'){
                        	jqueryMethod(JqueryMethod.pageNumber,position.row);
                        }
                        else if(msg==='20427'){
                        	jqueryMethod(JqueryMethod.pageSize,position.row);
                        }
                        else if(msg==='20428'){
                        	jqueryMethod(JqueryMethod.pageList,position.row);
                        }
                        else if(msg==='20429'){
                        	jqueryMethod(JqueryMethod.selectItemName,position.row);
                        }
                        else if(msg==='20430'){
                        	jqueryMethod(JqueryMethod.smartDisplay,position.row);
                        }
                        else if(msg==='20431'){
                        	jqueryMethod(JqueryMethod.search,position.row);
                        }
                        else if(msg==='20432'){
                        	jqueryMethod(JqueryMethod.strictSearch,position.row);
                        }
                        else if(msg==='20433'){
                        	jqueryMethod(JqueryMethod.searchText,position.row);
                        }
                        else if(msg==='20434'){
                        	jqueryMethod(JqueryMethod.searchTimeOut,position.row);
                        }
                        else if(msg==='20435'){
                        	jqueryMethod(JqueryMethod.trimOnSearch,position.row);
                        }
                        else if(msg==='20436'){
                        	jqueryMethod(JqueryMethod.showHeader,position.row);
                        }
                        else if(msg==='20437'){
                        	jqueryMethod(JqueryMethod.showFooter,position.row);
                        }
                        else if(msg==='20438'){
                        	jqueryMethod(JqueryMethod.showColumns,position.row);
                        }
                        else if(msg==='20439'){
                        	jqueryMethod(JqueryMethod.showRefresh,position.row);
                        }
                        else if(msg==='20440'){
                        	jqueryMethod(JqueryMethod.showToggle,position.row);
                        }
                        else if(msg==='20441'){
                        	jqueryMethod(JqueryMethod.showPaginationSwitch,position.row);
                        }
                        else if(msg==='20442'){
                        	jqueryMethod(JqueryMethod.minimumCountColumns,position.row);
                        }
                        else if(msg==='20443'){
                        	jqueryMethod(JqueryMethod.idField,position.row);
                        }
                        else if(msg==='20444'){
                        	jqueryMethod(JqueryMethod.uniqueId,position.row);
                        }
                        else if(msg==='20445'){
                        	jqueryMethod(JqueryMethod.cardView,position.row);
                        }
                        else if(msg==='20446'){
                        	jqueryMethod(JqueryMethod.detailView,position.row);
                        }
                        else if(msg==='20447'){
                        	jqueryMethod(JqueryMethod.detailFormatter,position.row);
                        }
                        else if(msg==='20448'){
                        	jqueryMethod(JqueryMethod.searchAlign,position.row);
                        }
                        else if(msg==='20449'){
                        	jqueryMethod(JqueryMethod.buttonsAlign,position.row);
                        }
                        else if(msg==='20450'){
                        	jqueryMethod(JqueryMethod.toolbarAlign,position.row);
                        }
                        else if(msg==='20451'){
                        	jqueryMethod(JqueryMethod.paginationVAlign,position.row);
                        }
                        else if(msg==='20452'){
                        	jqueryMethod(JqueryMethod.paginationHAlign,position.row);
                        }
                        else if(msg==='20453'){
                        	jqueryMethod(JqueryMethod.paginationDetailHAlign,position.row);
                        }
                        else if(msg==='20454'){
                        	jqueryMethod(JqueryMethod.paginationFirText,position.row);
                        }
                        else if(msg==='20455'){
                        	jqueryMethod(JqueryMethod.paginationPreText,position.row);
                        }
                        else if(msg==='20456'){
                        	jqueryMethod(JqueryMethod.paginationNextText,position.row);
                        }
                        else if(msg==='20457'){
                        	jqueryMethod(JqueryMethod.paginationLastText,position.row);
                        }
                        else if(msg==='20458'){
                        	jqueryMethod(JqueryMethod.clickToSelect,position.row);
                        }
                        else if(msg==='20459'){
                        	jqueryMethod(JqueryMethod.singleSelect,position.row);
                        }
                        else if(msg==='20460'){
                        	jqueryMethod(JqueryMethod.toolbar,position.row);
                        }
                        else if(msg==='20461'){
                        	jqueryMethod(JqueryMethod.checkboxHeader,position.row);
                        }
                        else if(msg==='20462'){
                        	jqueryMethod(JqueryMethod.maintainSelected,position.row);
                        }
                        else if(msg==='20463'){
                        	jqueryMethod(JqueryMethod.sortable,position.row);
                        }
                        else if(msg==='20464'){
                        	jqueryMethod(JqueryMethod.silentSort,position.row);
                        }
                        else if(msg==='20465'){
                        	jqueryMethod(JqueryMethod.rowStyle,position.row);
                        }
                        else if(msg==='20466'){
                        	jqueryMethod(JqueryMethod.rowAttributes,position.row);
                        }
                        else if(msg==='20467'){
                        	jqueryMethod(JqueryMethod.locale,position.row);
                        }
                        //表格列选项
                        else if(msg==='2061'){
                        	jqueryMethod(JqueryMethod.radio,position.row);
                        }
                        else if(msg==='2062'){
                        	jqueryMethod(JqueryMethod.checkbox,position.row);
                        }
                        else if(msg==='2063'){
                        	jqueryMethod(JqueryMethod.field,position.row);
                        }
                        else if(msg==='2064'){
                        	jqueryMethod(JqueryMethod.title,position.row);
                        }
                        else if(msg==='2065'){
                        	jqueryMethod(JqueryMethod.titleTooltip,position.row);
                        }
                        else if(msg==='2066'){
                        	jqueryMethod(JqueryMethod.class1,position.row);
                        }
                        else if(msg==='2067'){
                        	jqueryMethod(JqueryMethod.rowspan,position.row);
                        }
                        else if(msg==='2068'){
                        	jqueryMethod(JqueryMethod.colspan,position.row);
                        }
                        else if(msg==='2069'){
                        	jqueryMethod(JqueryMethod.align,position.row);
                        }
                        else if(msg==='20610'){
                        	jqueryMethod(JqueryMethod.halign,position.row);
                        }
                        else if(msg==='20611'){
                        	jqueryMethod(JqueryMethod.falign,position.row);
                        }
                        else if(msg==='20612'){
                        	jqueryMethod(JqueryMethod.valign,position.row);
                        }
                        else if(msg==='20613'){
                        	jqueryMethod(JqueryMethod.width,position.row);
                        }
                        else if(msg==='20614'){
                        	jqueryMethod(JqueryMethod.sortable,position.row);
                        }
                        else if(msg==='20615'){
                        	jqueryMethod(JqueryMethod.order,position.row);
                        }
                        else if(msg==='20616'){
                        	jqueryMethod(JqueryMethod.visible,position.row);
                        }
                        else if(msg==='20617'){
                        	jqueryMethod(JqueryMethod.cardVisible,position.row);
                        }
                        else if(msg==='20618'){
                        	jqueryMethod(JqueryMethod.switchable,position.row);
                        }
                        else if(msg==='20619'){
                        	jqueryMethod(JqueryMethod.clickToSelect,position.row);
                        }
                        else if(msg==='20620'){
                        	jqueryMethod(JqueryMethod.formatter,position.row);
                        }
                        else if(msg==='20621'){
                        	jqueryMethod(JqueryMethod.footerFormatter,position.row);
                        }
                        else if(msg==='20622'){
                        	jqueryMethod(JqueryMethod.events,position.row);
                        }
                        else if(msg==='20623'){
                        	jqueryMethod(JqueryMethod.sorter,position.row);
                        }
                        else if(msg==='20624'){
                        	jqueryMethod(JqueryMethod.sortName,position.row);
                        }
                        else if(msg==='20625'){
                        	jqueryMethod(JqueryMethod.cellStyle,position.row);
                        }
                        else if(msg==='20626'){
                        	jqueryMethod(JqueryMethod.searchable,position.row);
                        }
                        else if(msg==='20627'){
                        	jqueryMethod(JqueryMethod.searchFormatter,position.row);
                        }
                        //日期改变
                        else if(msg==='2301'){
                        	jqueryMethod(JqueryMethod.dateChange,position.row);
                        }
                        /*20181021 add by chenyl for 添加日期控件操作方法*/
                        else if(msg==='2302'){
                            jqueryMethod(JqueryMethod.dateGet,position.row);
                        }
                        else if(msg==='2303'){
                            jqueryMethod(JqueryMethod.dateSet,position.row);
                        }
                        /*20181021 add by chenyl for 添加单选按钮方法*/
                        else if(msg==='2501'){
                            jqueryMethod(JqueryMethod.setRadioVal,position.row);
                        }
                        else if(msg==='2502'){
                            jqueryMethod(JqueryMethod.getRadioVal,position.row);
                        }
                        else if(msg==='2503'){
                            jqueryMethod(JqueryMethod.getRadioLabel,position.row);
                        }
                        /*20181021 add by chenyl for 添加复选按钮方法*/
                        else if(msg==='2601'){
                            jqueryMethod(JqueryMethod.setCheckboxVal,position.row);
                        }
                        else if(msg==='2602'){
                            jqueryMethod(JqueryMethod.getCheckboxVal,position.row);
                        }
                        else if(msg==='2603'){
                            jqueryMethod(JqueryMethod.getCheckboxLabel,position.row);
                        }
                        /*20181021 add by chenyl for 添加设置图标选择器不可编辑*/
                        else if(msg==='2701'){
                            jqueryMethod(JqueryMethod.setIconSelectDisabled,position.row);
                        }
                        /*20181021 add by chenyl for 添加设置文件选择器不可编辑*/
                        else if(msg==='2801'){
                            jqueryMethod(JqueryMethod.setFileSelectDisabled,position.row);
                        }
                        /*富文本编辑器常用API*/
                        else if(msg==='2401'){
                        	jqueryMethod(JqueryMethod.getEditor,position.row);
                        }
                        else if(msg==='2402'){
                        	jqueryMethod(JqueryMethod.setContent,position.row);
                        }
                        else if(msg==='2403'){
                        	jqueryMethod(JqueryMethod.setContent2,position.row);
                        }
                        else if(msg==='2404'){
                        	jqueryMethod(JqueryMethod.getContent,position.row);
                        }
                        else if(msg==='2405'){
                        	jqueryMethod(JqueryMethod.getContentTxt,position.row);
                        }
                        else if(msg==='2406'){
                        	jqueryMethod(JqueryMethod.getPlainTxt,position.row);
                        }
                        else if(msg==='2407'){
                        	jqueryMethod(JqueryMethod.hasContents,position.row);
                        }
                        else if(msg==='2408'){
                        	jqueryMethod(JqueryMethod.ueFocus,position.row);
                        }
                        else if(msg==='2409'){
                        	jqueryMethod(JqueryMethod.ueBlur,position.row);
                        }
                        else if(msg==='24010'){
                        	jqueryMethod(JqueryMethod.isFocus,position.row);
                        }
                        else if(msg==='24011'){
                        	jqueryMethod(JqueryMethod.setDisabled,position.row);
                        }
                        else if(msg==='24012'){
                        	jqueryMethod(JqueryMethod.setEnabled,position.row);
                        }
                        else if(msg==='24013'){
                        	jqueryMethod(JqueryMethod.setHide,position.row);
                        }
                        else if(msg==='24014'){
                        	jqueryMethod(JqueryMethod.setShow,position.row);
                        }
                        else if(msg==='24015'){
                        	jqueryMethod(JqueryMethod.getText,position.row);
                        }
                        /*富文本编辑器常用命令*/
                        else if(msg==='2411'){
                        	jqueryMethod(JqueryMethod.inserthtml,position.row);
                        }
                        else if(msg==='2412'){
                        	jqueryMethod(JqueryMethod.setBold,position.row);
                        }
                        else if(msg==='2413'){
                        	jqueryMethod(JqueryMethod.setItalic,position.row);
                        }
                        else if(msg==='2414'){
                        	jqueryMethod(JqueryMethod.setSuperscript,position.row);
                        }
                        else if(msg==='2415'){
                        	jqueryMethod(JqueryMethod.setSupscript,position.row);
                        }
                        else if(msg==='2416'){
                        	jqueryMethod(JqueryMethod.setForecolor,position.row);
                        }
                        else if(msg==='2417'){
                        	jqueryMethod(JqueryMethod.setBackcolor,position.row);
                        }
                        else if(msg==='2418'){
                        	jqueryMethod(JqueryMethod.setUndo,position.row);
                        }
                        else if(msg==='2419'){
                        	jqueryMethod(JqueryMethod.setRedo,position.row);
                        }
                        else if(msg==='24110'){
                        	jqueryMethod(JqueryMethod.setSource,position.row);
                        }
                        else if(msg==='24111'){
                        	jqueryMethod(JqueryMethod.setSelectall,position.row);
                        }
                        else if(msg==='24112'){
                        	jqueryMethod(JqueryMethod.setCleardoc,position.row);
                        }
                        else if(msg==='24113'){
                        	jqueryMethod(JqueryMethod.setDrafts,position.row);
                        }
                        else if(msg==='24114'){
                        	jqueryMethod(JqueryMethod.setClearlocaldata,position.row);
                        }
                        
                        else if(msg ==='format'){
                        	format();
                        }else if(msg ==='save'){
                        	save();
                        }
                        
                    }
                    else {
                        if (msg.message === "JSURL") {
                            var path = msg.value;
                            console.info('---------path----------');
                            console.info(path);
                            setJspUrl(path);
                            getJScript(path, msg.numark, msg.quesbtn, msg.idname);
                            
                        }
                        else 
                            if (msg.message === "JSAVE") {
                            	save();
                            }
                            else 
                                if (msg.message === "SETURL") {
                                    console.info("修改之后的地址 " + msg.value);
                                    setJspUrl(msg.value);
                                }
                                else {
                                }
                    }
                   
                });
                messenger.addTarget(window.parent, 'Parent');
                
                //快捷键 CTRL+S 保存JS代码
                //var ctrloo = 0;
                $(document).keypress(function(e){
                	/* var position = editor.getCursorPosition(); */
                    if(e.altKey && e.shiftKey&&e.which ==74){
                    	e.preventDefault();
                        e.stopPropagation();
                        format();
                    }
                    else if (e.ctrlKey && e.which == 115) {
                        e.preventDefault();
                        e.stopPropagation();
                        save();
                    }
                });
                
                function format(){
                	if (jscriptUrl.endsWith(".jsp")) {
                        do_js_beautify(); 
                        var value = editor.getValue();
                        setJScript(value, jscriptUrl);
                    }
                    /* editor.gotoLine(position.row+1); */
                }
                
                function save() {
                	if (jscriptUrl.endsWith(".jsp") && isChange) {
                        var value = editor.getValue();
                        setJScript(value, jscriptUrl);
                   	 	isChange = false;
                    }
				}
                
                /* $('#dlg').dialog({    
                 title: '方法搜索',
                 width: 400,
                 top:130,
                 left:400,
                 height:200,
                 iconCls:'icon-search',
                 closed: true,
                 cache: false,
                 shadow : false,
                 collapsible : false,
                 resizable : true,
                 modal: false
                 });    */
                /* 	 $("#rainbow_keyword").keyup(function(){
                 
                 editor.find(/^[f]{1}unction[\s]+[\w]+\(\)$/,{
                 backwards: false,
                 wrap: false,
                 caseSensitive: false,
                 wholeWord: false,
                 regExp: false
                 });
                 
                 }); */
            });
            
            function jqueryMethod(JSMethod,p){
                var isdoc = findDoc();
                
                console.info("isdoc " + isdoc);
                if (isdoc == 1) {
                    console.info("位置 " + p);
                    editor.gotoLine(p+2);
                    //editor.selectLine();
                    // editor.gotoLine(ipos.row + 2);
                    editor.insert(JSMethod);
                    var position = editor.getCursorPosition();
                    var row=position.row;
                    editor.gotoLine(row);
                    
                }
                else {
                    editor.gotoLine(length + 1);
                    editor.insert(isdoc);
                    editor.gotoLine(editor.session.getLength() - 3);
                    var ipos = editor.getCursorPosition();
                    console.info("位置 " + ipos.row);
                    console.info(ipos.row + 1 + " opopop");
                    editor.gotoLine(ipos.row + 2);
                    editor.insert(JSMethod);
                }
            }
            
            function sendMessage(name, changes){
                messenger.targets[name].send(changes);
            }
            
            function setJScript(context, path){
            	 
            	
                $.ajax({
                    type: "POST",
                    data: {
                        jsContent: context,
                        path: path
                    },
                    url: ctxIde+'/ide/setJScript',
                    dataType: "json",
                    success: function(data){
                        if (data.msg === "success") {
                            sendMessage('Parent', 'savescs');
                        }
                        else {
                            sendMessage('Parent', 'saverr');
                        }
                    }
                });
                
            }
            
            function getJScript(path, numark, btn, idname){
                $.ajax({
                    type: "POST",
                    data: {
                        path: path
                    },
                    url: ctxIde+'/ide/getJScript',
                    dataType: "json",
                    success: function(data){
                        if (data.msg === "SCUESS") {
                            editor.setValue(data.jsContent);
                            var length = editor.session.getLength();
                            if (numark != undefined) {
                            
                                var triremark = findDoc();
                                if (triremark == 1) {
                                    console.info("标识!=未定义");
                                    var index = 0;
                                    index = editor.find('\\([\\s]*"[\\s]*#'+idname+'[\\s]*"[\\s]*\\)[\\s]*.[\\s]*'+btn+'[\\s]*\\([\\s]*function[\\s]*\\([\\s]*\\)[\\s]*{', {
                                        backwards: false,
                                        wrap: false,
                                        caseSensitive: false,
                                        wholeWord: false,
                                        regExp: true
                                    });
                                    
                                    if (index === undefined) {
                                        var ipos = editor.getCursorPosition();
                                        console.info("位置 " + ipos.row);
                                        editor.gotoLine(ipos.row + 1);
                                        //editor.selectLine();
                                        editor.gotoLine(ipos.row + 2);
                                        
                                        editor.insert(editorMethod(numark, btn, idname));
                                        //editor.gotoLine( editor.session.getLength()-3);
                                        var is = editor.getCursorPosition();
                                        
                                        editor.gotoLine(is.row-3);
                                    }
                                }
                                else {
                                    editor.gotoLine(length + 1);
                                    editor.insert(triremark);
                                    editor.gotoLine(editor.session.getLength() - 3);
                                    var ipos = editor.getCursorPosition();
                                    console.info("位置 " + ipos.row);
                                    console.info(ipos.row + 1 + " opopop");
                                    editor.gotoLine(ipos.row + 2);
                                    editor.insert(editorMethod(numark, btn, idname));
                                    editor.gotoLine(editor.session.getLength() - 6);
                                }
                                //else{
                                //	var ipos=editor.getCursorPosition();
                                //    console.info("位置 "+ipos.row);
                                //editor.gotoLine(ipos.row+1);
                                //editor.selectLine();
                            
                                //}
                            
                            }
                            else {
                                editor.gotoLine(length);
                                sendMessage('Parent', 'savescs');
                            }
                        }
                    }
                });
            }
            
            function editorMethod(v, b, i){
            
                if (v === 10001) {
                    if (b === "click" || b === "dblclick" || b === "blur"||b==="change"||b==="focus") {
                        return '\n\t$("#' + i + '").' + b +
                        '(function(){\n\t//默认生成方法...\n\n\t});\n';
                    }
                    else {
                        return '\n\t$("#' + i +'").bind("TYPE",function(){\n\t//TYPE: 含有一个或多个事件类型的字符串，由空格分\n\t//隔多个事件。比如"click"或"submit"。\n\t});\n';
                    }
                }
                else {
                    return "\nfunction RANBOW_FUCTION(){\n\t//默认生成方法...\n\n}\n";
                }
                
            }
            
            function findDoc(){
                var searchString = '$(document).ready(function()';
                var index = 0;
                index = editor.findAll(searchString, {
                    backwards: false,
                    wrap: false,
                    caseSensitive: false,
                    wholeWord: false,
                    regExp: false
                }); 
                if (index == 0) {
                
                    return '$(document).ready(function(){\n\n\n});';
                }
                else {
                	editor.find(searchString, {
                        backwards: false,
                        wrap: false,
                        caseSensitive: false,
                        wholeWord: false,
                        regExp: false
                    });
                    return 1;//存在该方法		
                }
            }
            
            function do_js_beautify() {
                var js_source = editor.getValue().replace(/^\s+/, '');
                if (js_source && js_source.charAt(0) === '<') {
                  editor.setValue(style_html(js_source, tabsize, tabchar, 80));
                } else {
                	editor.setValue(js_beautify(js_source));
                }
                return false;
              }
            
            var JqueryMethod = {
                post: '\n\t$.post("test.do", { name: "John", time: "2pm" },function(data){ \n\t\t\tif(data.returnCode!==undefined && "0000"!=data.returnCode){ \n\t\t\t var errMsg = "错误信息["+data.message+"]"; \n\t\t\t\tshowContent(errMsg,"error");	\n\t\t\t}else{\n\t\t\t\tfor(var i = 0 ; i < data.dataSetResult.length; i++){\n\t\t\t\t\tfor(var j = 0 ; j < data.dataSetResult[i].data.length; j++){\n\t\t\t\t\t\tvar jsonObj = data.dataSetResult[i].data[j];\n\t\t\t\t\t\tconsole.info(jsonObj);\n\t\t\t\t\t} \n\t\t\t\t}  \n\t\t\t} \n\t\t}, "json");\n',
                get: '\n\t$.get("test.do", { name: "John", time: "2pm" }, function(data){ \n\t\t\tif(data.returnCode!==undefined && "0000"!=data.returnCode){ \n\t\t\t var errMsg = "错误信息["+data.message+"]"; \n\t\t\t\tshowContent(errMsg,"error"); \n\t\t\t}else{\n\t\t\t\tfor(var i = 0 ; i < data.dataSetResult.length; i++){\n\t\t\t\t\tfor(var j = 0 ; j < data.dataSetResult[i].data.length; j++){\n\t\t\t\t\t\tvar jsonObj = data.dataSetResult[i].data[j];\n\t\t\t\t\t\tconsole.info(jsonObj);\n\t\t\t\t\t} \n\t\t\t\t}	\n\t\t\t} \n\t\t});\n',
                loadM:'\n\t$("selector").load("feeds.do", {limit: 25},function(){ \n\t\t\talert("The last 25 entries in the feed have been loaded"); \n\t\t});\n',
                ajaxM:'\n\t$.ajax({\n\t\ttype: "POST",\n\t\turl: "some.do", \n\t\tdata: "name=John&location=Boston", \n\t\tsuccess: function(msg){    \n\t\t\tif(msg.returnCode!==undefined && "0000"!=msg.returnCode){ \n\t\t\t var errMsg = "错误信息["+msg.message+"]"; \n\t\t\t\tshowContent(errMsg,"error");	\n\t\t\t}   \n\t\t\t} \n\t\t});\n',
                each :'\n\t$.each( { name: "John", lang: "JS" }, function(i, n){ \n\t\talert( "Name: " + i + ", Value: " + n );\n\t\t});\n',
                parseJSON:'\n\t//Begin\n\t//解析一个JSON字符串\n\t\tvar obj = $.parseJSON(json);\n\t\talert( obj.name === "John" );\n\t//End\n',
                browser:'\n\tif ($.browser.safari) { \n\t\talert("this is safari!");\n\t\t}\n',
                serializeObject:'\n\tvar formData = $("#qryForm").serializeObject();\n',
                cstCompnentRender:'\n\t/***\n\t* 自定义组件渲\n\t* 一般用于对CST文件中定义的UI组件渲染到当前页面指定的布局中的列中，在页面的装载成功后事件\n\t* sourceId：CST中定义的UI组件的DIV标签的id\n\t* destId：页面中布局中列的id，或DIV标签有column样式修饰的id\n\t* \n\t*/\n\tSmartWeb.swJS.render.renderTo("sourceId", "destId");\n',
                sessionAdd:'\n\t/*设置session数据*/\n\t$.session.set("key", "value");\n',
                sessionGet:'\n\t/*获取session数据*/\n\t$.session.get("key");\n',
                sessionRemove:'\n\t/*删除指定session数据*/\n\t$.session.remove("key");\n',
                sessionClear:'\n\t/*清理所有session数据*/\n\t$.session.clear();\n',
                openMenu:'\n\t/*打开指定菜单页,(参数：固定的三层菜单,以->进行分割)*/\n\topenMenu("系统管理->系统设置->角色管理");\n',
                setEnterKeyEvent:'\n\t/**\n\t* 设置组件的回车事件\n\t* @param id \n\t*           组件ID\n\t* @param callBack\n\t*          回调函数\n\t* @returns\n\t*/\n\tsetEnterKeyEvent("id",callBack);\n',
                clearForm:'\n\t/*清除form表单控件的值,(参数：表单的id)*/\n\tclearForm("formId");\n',
                loadJspByDiv:'\n\t/**\n\t* 异步装载页面到指定的div\n\t* @param id    DIV的id\n\t* @param url  需要装载的jsp路径\n\t* @param param    请求参数\n\t* @returns\n\t*/\n\tloadJspByDiv("divId", "url", param);\n',
                download:'\n\t/**\n\t* 文件下载，支持多文件下载\n\t* @param url     请求下载的url(必输)\n\t* @param fileIds    要下载的文件id列表，多个文件以逗号分隔(必输)\n\t* @param downloadFileName   下载后显示的文件名称，如果多文件下载的为.zip结尾(非必输)\n\t* @returns\n\t*/\n\tdownload(url, fileIds, downloadFileName);\n',
                importData:'\t\n/**\t\n* 数据导入\t\n* @param importUrl     导入数据请求路径(必输)\t\n* @param templateUrl    导入模板下载路径(必输)\t\n* @param title          导入弹出窗口标题，默认：导入数据\t\n* @param bottomText 导入弹出窗口底部备注信息，默认：导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！\t\n* @param cb      回调函数\t\n* @returns\t\n*/\t\nimportData(importUrl, templateUrl, title, bottomText, cb);\n',
                exportData:'\t\n/** 数据导出\t\n* @param exportUrl        数据导出请求的url(必输)\t\n* @param title            数据导出弹出窗口标题，默认：确认要导出数据吗？\t\n* @param alertMsg        数据导出弹出窗提示信息，默认：系统提示\t\n* @returns\t\n*/\t\nexportData(exportUrl, title, alertMsg);\n',
                done:'\n\t$.get("test.do").done(function() { \n\t\t alert("$.get succeeded");\n\t\t });\n',
                fail:'\n\t$.get("test.do").done(function(){\n\t\t alert("$.get succeeded"); })//延迟成功\n\t\t.fail(function(){ alert("$.get failed!"); });//延迟失败\n',
                add:'\t//Begin\n\tvar foo = function( value ){\n\t\tconsole.info( "foo:" + value );\n\t}\n\tvar bar = function( value ){\n\t\tconsole.info( "bar:" + value );\n\t}\n\tvar callbacks = $.Callbacks();\n\tcallbacks.add( foo );\n\tcallbacks.fire( "hello" );\n\tcallbacks.add( bar );\n\tcallbacks.fire( "world" );\n\t//End\n',
                disable:'\t//Begin\n\tvar foo = function( value ){\n\t\tconsole.info( value );\n\t}\n\tvar callbacks = $.Callbacks();\n\tcallbacks.add( foo );\n\tcallbacks.fire( "foo" );\n\tcallbacks.disable();\n\tcallbacks.fire( "foobar" );\n\t//End\n',
                empty:'\t//Begin\n\tvar foo = function( value1, value2 ){\n\t\t console.info( "foo:" + value1 + "," + value2 );\n\t\t}\n\tvar bar = function( value1, value2 ){\n\t\tconsole.info( "bar:" + value1 + "," + value2 );\n\t\t}\n\tvar callbacks = $.Callbacks();\n\tcallbacks.add( foo );\n\tcallbacks.add( bar );\n\tcallbacks.empty();\n\tconsole.info( callbacks.has( foo ) );\n\tconsole.info( callbacks.has( bar ) );\n//End\n',
                remove:'\t//Begin\n\tvar foo = function( value ){\n\t\tconsole.info( "foo:" + value );\n\t\t}\n\tvar callbacks = $.Callbacks();\n\tcallbacks.add( foo );\n\tcallbacks.fire( "hello" );\n\tcallbacks.remove( foo );\n\tcallbacks.fire( "world" );\n\t//End\n',
                edefault:'\n\te.preventDefault();//通知浏览器不要执行与事件关联的默认动作。\n',
                estop:'\n\te.stopPropagation();//不再派发事件\n',
                on:'\n\t$("p").on("click", function(){\n\t\t\talert( $(this).text() );\n\t\t});\n',
                bind:'\n\t$("button").bind({\n\t\tclick:function(){$("p").slideToggle();},\n\t\tmouseover:function(){$("body").css("background-color","red");},\n\t\tmouseout:function(){$("body").css("background-color","#FFFFFF");}\n\t});\n',
                unbind:'\n\t$("p").unbind( "click" );\n',
                keydown:'\n\t$(window).keydown(function(event){\n\t\tswitch(event.keyCode) {\n\t\t// ...\n\t\t// 不同的按键可以做不同的事情\n\t\t// 不同的浏览器的keycode不同\n\t\t// ...\n\t\t}\n\t});\n',
                //表单
                bootstrpValidator:'\t$("#formselector").bootstrapValidator({\n\t\texcluded : [":disabled"],\n\t\tfeedbackIcons: {\n\t\t\tvalid: \'glyphicon glyphicon-ok\',\n\t\t\tinvalid: \'glyphicon glyphicon-remove\',\n\t\t\tvalidating: \'glyphicon glyphicon-refresh\'\n\t\t}\n\t});',
                proof:'\t$("#saveBtn").click(function(){\n\t\t/*使用新校验,校验通过则返回true,否则返回false*/\n\t\tif(proof()){\n\t\t\tsave();\n\t\t};\n\t});\n',
                //表格初始化
                bootstrpTableInit:'\n\t//表格初始化\n\t$("#table").bootstrapTable({\n\t});\n',
                //表格属性
				 classes:'\t\t//table的类名称\n\t\tclasses:\'table table-hover\',\n',
				 height:'\t\t//表的高度\n\t\theight:undefined,\n',
				 undefinedText:'\t\t//指定未定义文本默认显示内容\n\t\tundefinedText:\'-\',\n',
				 striped:'\t\t//指定行是否显示条纹\n\t\tstriped:false,\n',
				 sortName:'\t\t//指定某列能够排序\n\t\tsortName:undefined,\n',
				 sortOrder:'\t\t//定义的列的排序顺序，只能“升序”或“降序”。\n\t\tsortOrder:\'asc\',\n',
				 iconsPrefix:'\t\t//定义图标集名称(\'glyphicon\' or \'fa\' for FontAwesome).默认使用\'glyphicon\'\n\t\ticonsPrefix:\'glyphicon\',\n',
				 iconSize:'\t\t// undefined => btn  xs => btn-xs    sm => btn-sm    lg => btn-lg\n\t\ticonSize:undefined,\n',
				 icons:'\t\t//设定图标\n\t\ticons:{\n\t\t\tpaginationSwitchDown: \'glyphicon-collapse-down icon-chevron-down\',\n\t\t\tpaginationSwitchUp: \'glyphicon-collapse-up icon-chevron-up\',\n\t\t\trefresh: \'glyphicon-refresh icon-refresh\', \n\t\t\ttoggle: \'glyphicon-list-alt icon-list-alt\',\n\t\t\tcolumns: \'glyphicon-th icon-th\',detailOpen: \'glyphicon-plus icon-plus\',\n\t\t\tdetailClose: \'glyphicon-minus icon-minus\'\n\t\t},\n',
				 columns:'\t\t//表列配置对象。\n\t\tcolumns:[[\n\t\t\t{\n\t\t\t\tfield:undefined,\n\t\t\t\ttitle:undefined,\n\t\t\t\twidth:undefined\n\t\t\t}\n\n\t\t]],\n',
				 data:'\t\t//The data to be loaded.\n\t\tdata:[\n\n\t\t],\n',
				 dataField:'\t\t//在导入json数据表中包含的行的关键字.\n\t\tdataField:\'rows\',\n',
				 ajax:'\t\t//一个替代的Ajax调用的方法。要实现相同的API作为jQuery的Ajax方法\n\t\tajax:undefined,\n',
				 method:'\t\t//请求远程数据的方法类型。\n\t\tmethod:\'get\',\n',
				 url:'\t\t//一个URL从远程站点请求数据。\n\t\turl:undefined,\n',
				 cache:'\t\t//False则表示当ajax requests是禁用缓存.\n\t\tcache:true,\n',
				 contentType:'\t\t//请求远程数据的内容类型。\n\t\tcontentType:\'application/json\',\n',
				 dataType:'\t\t//从服务器返回的数据类型。\n\t\tdataType:\'json\',\n',
				 ajaxOptions:'\t\t//附加选项提交Ajax请求。\n\t\tajaxOptions:{\n\n\t\t},\n',
				 queryParams1:'\t\t//当请求远程数据，你可以通过修改queryparams发送额外的参数。\n\t\tqueryParams:function(params) {\n\t\t\treturn params;\n\t\t},\n',
				 queryParamsType:'\t\t//设置限制发送查询参数宽度RESTful式。\n\t\tqueryParamsType:\'limit\',\n',
				 responseHandler:'\t\t//在加载远程数据之前，处理程序响应数据格式，所包含的参数对象：既有响应数据。\n\t\tresponseHandler:function(res) {\n\t\t\treturn res;\n\t\t},\n',
				 pagination:'\t\t//当设置为true时候在table底部显示分页工具栏。\n\t\tpagination:false,\n',
				 onlyInfoPagination:'\t\t//真实显示在表中显示的数据的数量。它需要分页表选项设置为true\n\t\tonlyInfoPagination:false,\n',
				 sidePagination:'\t\t//定义表格边分页。\n\t\tsidePagination:\'client\',\n',
				 pageNumber:'\t\t//在设置分页属性的时候初始化页码。\n\t\tpageNumber:1,\n',
				 pageSize:'\t\t//在设置分页属性的时候初始化页面大小。\n\t\tpageSize:10,\n',
				 pageList:'\t\t//在设置分页属性的时候 初始化页面大小选择列表。\n\t\tpageList:[10, 25, 50, 100, All],\n',
				 selectItemName:'\t\t//The name of radio or checkbox input.\n\t\tselectItemName:\'btSelectItem\',\n',
				 smartDisplay:'\t\t//是否敏捷显示分页或视图\n\t\tsmartDisplay:true,\n',
				 search:'\t\t//是否显示search输入框\n\t\tsearch:false,\n',
				 strictSearch:'\t\t//是否显示精确的查询\n\t\tstrictSearch:false,\n',
				 searchText:'\t\t//当设置search属性是，文本框初始化内容\n\t\tsearchText:",\n',
				 searchTimeOut:'\t\t//设置查询失败超时时间\n\t\tsearchTimeOut:500,\n',
				 trimOnSearch:'\t\t//搜索字段中的空格\n\t\ttrimOnSearch:true,\n',
				 showHeader:'\t\t//是否显示表头\n\t\tshowHeader:true,\n',
				 showFooter:'\t\t//是否显示总结行\n\t\tshowFooter:false,\n',
				 showColumns:'\t\t//是否显示列下拉列表，可选择列显示\n\t\tshowColumns:false,\n',
				 showRefresh:'\t\t//是否显示刷新按钮\n\t\tshowRefresh:false,\n',
				 showToggle:'\t\t//是否显示切换按钮，用来切换table和card视图\n\t\tshowToggle:false,\n',
				 showPaginationSwitch:'\t\t//是否显示分页按钮开关\n\t\tshowPaginationSwitch:false,\n',
				 minimumCountColumns:'\t\t//列的最小数目，以隐藏列下拉列表\n\t\tminimumCountColumns:1,\n',
				 idField:'\t\t//指明哪一个字段是标识字段。\n\t\tidField:undefined,\n',
				 uniqueId:'\t\t//为每行表示一个唯一的标识符。\n\t\tuniqueId:undefined,\n',
				 cardView:'\t\t//是否显示card视图。\n\t\tcardView:false,\n',
				 detailView:'\t\t//是否显示详细视图表。\n\t\tdetailView:false,\n',
				 detailFormatter:'\t\t//格式化你的详细视图时，设置真实DetailView。\n\t\tdetailFormatter:function(index, row) {\n\t\t\treturn \'\';\n\t\t},\n',
				 searchAlign:'\t\t//指示如何对齐搜索输入。“left”，“right”可以使用。\n\t\tsearchAlign:\'right\',\n',
				 buttonsAlign:'\t\t//指定如何对齐按钮组。“left”，“right”可以使用。\n\t\tbuttonsAlign:\'right\',\n',
				 toolbarAlign:'\t\t//指定如何对齐toolbar。“left”，“right”可以使用。\n\t\ttoolbarAlign:\'left\',\n',
				 paginationVAlign:'\t\t//指定如何对齐分页。\'top\', \'bottom\',\'both\' 可以使用。\n\t\tpaginationVAlign:\'bottom\',\n',
				 paginationHAlign:'\t\t//指定如何对齐分页。\'left\', \'right\'可以使用。\n\t\tpaginationHAlign:\'right\',\n',
				 paginationDetailHAlign:'\t\t//如何指定分页描述。\'left\', \'right\'可以使用。\n\t\tpaginationDetailHAlign:\'left\',\n',
				 paginationFirText:'\t\t//首页\n\t\tpaginationFirText:\'<<\',\n',
				 paginationPreText:'\t\t//前一页\n\t\tpaginationPreText:\'>\',\n',
				 paginationNextText:'\t\t//后一页\n\t\tpaginationNextText:\'>\',\n',
				 paginationLastText:'\t\t//尾页\n\t\tpaginationLastText:\'>>\',\n',
				 clickToSelect:'\t\t//是否单击行时选择checkbox or radiobox\n\t\tclickToSelect:false,\n',
				 singleSelect:'\t\t//如果为true，则只允许选择一行。\n\t\tsingleSelect:false,\n',
				 toolbar:'\t\t//设置data-toolbar\n\t\ttoolbar:undefined,\n',
				 checkboxHeader:'\t\t//如果为false，则隐藏全选checkbox\n\t\tcheckboxHeader:true,\n',
				 maintainSelected:'\t\t//在更改页和搜索上保持选定的行\n\t\tmaintainSelected:false,\n',
				 sortable:'\t\t//如果为false，则禁用可排序的所有列。\n\t\tsortable:true,\n',
				 silentSort:'\t\t//The name of radio or checkbox input.\n\t\tsilentSort:true,\n',
				 rowStyle:'\t\t//行格式化，参数为row,index。\n\t\trowStyle:{},\n',
				 rowAttributes:'\t\t//自定义行属性\n\t\trowAttributes:{},\n',
				 locale:'\t\t//表格类\n\t\tlocale:undefined,\n',
				//表格列属性
				 radio:'\t\t\t\tradio:false,\n',
				 checkbox:'\t\t\t\tcheckbox:false,\n',
				 field:'\t\t\t\tfield:undefined,\n',
				 title:'\t\t\t\ttitle:undefined,\n',
				 titleTooltip:'\t\t\t\ttitleTooltip:undefined,\n',
				 class1:'\t\t\t\tclass:undefined,\n',
				 rowspan:'\t\t\t\trowspan:undefined,\n',
				 colspan:'\t\t\t\tcolspan:undefined,\n',
				 align:'\t\t\t\talign:undefined,\n',
				 halign:'\t\t\t\thalign:undefined,\n',
				 falign:'\t\t\t\tfalign:undefined,\n',
				 valign:'\t\t\t\tvalign:undefined,\n',
				 width:'\t\t\t\twidth:undefined,\n',
				 sortable:'\t\t\t\tsortable:false,\n',
				 order:'\t\t\t\torder:\'asc\',\n',
				 visible:'\t\t\t\tvisible:true,\n',
				 cardVisible:'\t\t\t\tcardVisible:true,\n',
				 switchable:'\t\t\t\tswitchable:true,\n',
				 clickToSelect:'\t\t\t\tclickToSelect:true,\n',
				 formatter:'\t\t\t\tformatter:undefined,\n',
				 footerFormatter:'\t\t\t\tfooterFormatter:undefined,\n',
				 events:'\t\t\t\tevents:undefined,\n',
				 sorter:'\t\t\t\tsorter:undefined,\n',
				 sortName:'\t\t\t\tsortName:undefined,\n',
				 cellStyle:'\t\t\t\tcellStyle:undefined,\n',
				 searchable:'\t\t\t\tsearchable:true,\n',
				 searchFormatter:'\t\t\t\tsearchFormatter:true,\n',
                //表格事件(Option选项)
                onAll1:'\t\t//全部事件\n\t\tonAll:function (name, args) {\n\n\t\t},\n',
				onClickRow1:'\t\t//单击事件\n\tonClickRow:function (row, $element) {\n\n\t\t},\n',
                onDblClickRow1:'\t\t//双击事件\n\tonDblClickRow:function (row, $element) {\n\n\t\t},\n',
               	onClickCell1:'\t\t//单击单元格事件\n\tonClickCell:function (field, value, row, $element) {\n\n\t\t},\n',
               	onDblClickCell1:'\t\t//双击单元格事件\n\tonDblClickCell:function (field, value, row, $element) {\n\n\t\t},\n',
               	onSort1:'\t\t//列排序事件\n\tonSort:function (name, order) {\n\n\t\t},\n',
               	onCheck1:'\t\t//行选择事件\n\tonCheck:function (row, $element) {\n\n\t\t},\n',
               	onUncheck1:'\t\t//行取消选择事件\n\tonUncheck:function (row, $element) {\n\n\t\t},\n',
               	onCheckAll1:'\t\t//全选事件\n\tonCheckAll:function (rows) {\n\n\t\t},\n',
               	onUncheckAll1:'\t\t//取消全选事件\n\tonUncheckAll:function (rows) {\n\n\t\t},\n',
               	onCheckSome1:'\t\t//部分行选择事件\n\tonCheckSome:function (rows) {\n\n\t\t},\n',
               	onUncheckSome1:'\t\t//对部分行取消选择事件\n\tonUncheckSome:function (rows) {\n\n\t\t},\n',
               	onLoadSuccess1:'\t\t//加载成功监听事件\n\tonLoadSuccess:function (data) {\n\n\t\t},\n',
               	onLoadError1:'\t\t//加载失败监听事件\n\tonLoadError:function (status, res) {\n\n\t\t},\n',
               	onColumnSwitch1:'\t\t//切换列可视监听事件\n\tonColumnSwitch:function (field, checked) {\n\n\t\t},);\n',
				onColumnSearch1:'\t\t//列查询监听事件\n\tonColumnSearch:function (field, text) {\n\n\t\t},\n',
				onPageChange1:'\t\t//PageNumber/pageSize改变监听事件\n\tonPageChange:function (number, size) {\n\n\t\t},\n',
				onSearch1:'\t\t//表格查询监听事件\n\tonSearch:function (text) {\n\n\t\t},\n',
				onToggle1:'\t\t//切换视图监听事件\n\tonToggle:function (cardView) {\n\n\t\t},\n',
				onPreBody1:'\t\t//表格呈现前监听事件\n\tonPreBody:function (data) {\n\n\t\t},\n',
				onPostBody1:'\t\t//表格呈现后并在DOM中可用事件\n\tonPostBody:function (none) {\n\n\t\t},\n',
				onPostHeader1:'\t\t//表格头部呈现后并在DOM中可用事件\n\tonPostHeader:function (none) {\n\n\t\t},\n',
				onExpandRow1:'\t\t//单击详情图标展开细节监听事件\n\tonExpandRow:function (index, row, $detail) {\n\n\t\t},\n',
				onCollapseRow1:'\t\t//列单击详情图标收起细节监听事件\n\tonCollapseRow:function (index, row) {\n\n\t\t},\n',
				onRefreshOptions1:'\t\t//刷新、摧毁表格选项前初始化表格监听事件\n\tonRefreshOptions:function (options) {\n\n\t\t},\n',
				onResetView1:'\t\t//重置表格视图监听事件\n\tonResetView:function (Boolean) {\n\n\t\t},\n',
                //表格事件(Jquery选项)
                onAll:'\t//全部事件\n\t$("#table").on(\'all.bs.table\', function (evnet, name, args) {\n\n\t},\n',
                onClickRow:'\t//单击事件\n\t$("#table").on(\'click-row.bs.table\', function (evnet, row, $element) {\n\n\t});\n',
                onDblClickRow:'\t//双击事件\n\t$("#table").on(\'dbl-click-row.bs.table\', function (evnet, row, $element) {\n\n\t});\n',
               	onClickCell:'\t//单击单元格事件\n\t$("#table").on(\'click-cell.bs.table\', function (evnet, field, value, row, $element) {\n\n\t});\n',
               	onDblClickCell:'\t//双击单元格事件\n\t$("#table").on(\'dbl-click-cell.bs.table\', function (evnet, field, value, row, $element) {\n\n\t});\n',
               	onSort:'\t//列排序事件\n\t$("#table").on(\'sort.bs.table\', function (evnet, name, order) {\n\n\t});\n',
               	onCheck:'\t//行选择事件\n\t$("#table").on(\'check.bs.table\', function (evnet, row, $element) {\n\n\t});\n',
               	onUncheck:'\t//行取消选择事件\n\t$("#table").on(\'uncheck.bs.table\', function (evnet, row, $element) {\n\n\t});\n',
               	onCheckAll:'\t//全选事件\n\t$("#table").on(\'check-all.bs.table\', function (evnet, rows) {\n\n\t});\n',
               	onUncheckAll:'\t//取消全选事件\n\t$("#table").on(\'uncheck-all.bs.table\', function (evnet, rows) {\n\n\t});\n',
               	onCheckSome:'\t//部分行选择事件\n\t$("#table").on(\'check-some.bs.table\', function (evnet, rows) {\n\n\t});\n',
               	onUncheckSome:'\t//对部分行取消选择事件\n\t$("#table").on(\'uncheck-some.bs.table\', function (evnet, rows) {\n\n\t});\n',
               	onLoadSuccess:'\t//加载成功监听事件\n\t$("#table").on(\'load-success.bs.table\', function (evnet, data) {\n\n\t});\n',
               	onLoadError:'\t//加载失败监听事件\n\t$("#table").on(\'load-error.bs.table\', function (evnet, status, res) {\n\n\t});\n',
               	onColumnSwitch:'\t//切换列可视监听事件\n\t$("#table").on(\'column-switch.bs.table\', function (evnet, field, checked) {\n\n\t});\n',
				onColumnSearch:'\t//列查询监听事件\n\t$("#table").on(\'column-search.bs.table\', function (evnet, field, text) {\n\n\t});\n',
				onPageChange:'\t//PageNumber/pageSize改变监听事件\n\t$("#table").on(\'page-change.bs.table\', function (evnet, number, size) {\n\n\t});\n',
				onSearch:'\t//表格查询监听事件\n\t$("#table").on(\'search.bs.table\', function (evnet, text) {\n\n\t});\n',
				onToggle:'\t//切换视图监听事件\n\t$("#table").on(\'toggle.bs.table\', function (evnet, cardView) {\n\n\t});\n',
				onPreBody:'\t//表格呈现前监听事件\n\t$("#table").on(\'pre-body.bs.table\', function (evnet, data) {\n\n\t});\n',
				onPostBody:'\t//表格呈现后并在DOM中可用事件\n\t$("#table").on(\'post-body.bs.table\', function (evnet, none) {\n\n\t});\n',
				onPostHeader:'\t//表格头部呈现后并在DOM中可用事件\n\t$("#table").on(\'post-header.bs.table\', function (evnet, none) {\n\n\t});\n',
				onExpandRow:'\t//单击详情图标展开细节监听事件\n\t$("#table").on(\'expand-row.bs.table\', function (evnet, index, row, $detail) {\n\n\t});\n',
				onCollapseRow:'\t//列单击详情图标收起细节监听事件\n\t$("#table").on(\'collapse-row.bs.table\', function (evnet, index, row) {\n\n\t});\n',
				onRefreshOptions:'\n\t//刷新、摧毁表格选项前初始化表格监听事件\n\t$("#table").on(\'refresh-options.bs.table\', function (evnet, options) {\n\n\t});\n',
				onResetView:'\t//重置表格视图监听事件\n\t$("#table").on(\'reset-view.bs.table\', function (evnet, Boolean) {\n\n\t});\n',
                //表格常用模版
               	queryParams:'\nfunction queryParams(params){\n return {\n\t\tpgside : \'client\',//客户分页\n\t\tpageSize : params.pageSize,\n\t\tstart : params.offset+1,\n\t\tpageNo : params.pageNumber,\n\t\tsort : params.sortName,\n\t\torder : params.order\n\t};\n}\n',
               	queryParamsForLimit:'\nfunction queryParams(params){\n\tvar formData = $("#qryForm").serializeObject(); \n\tvar paramList = { \n\t\tpgside : \'server\',//服务器分页\n\t\tpageSize : params.limit,\n\t\tstart : params.offset+1,\n\t\tpageNo : getPage(params),\n\t\tsort : params.sort,\n\t\torder : params.order,\n\t\tBUSI_NO : formData.BUSI_NO\n\t}; \n\treturn paramList; \n}\nfunction getPage(params) {\n\tif (!isNaN(params.offset) || !isNaN(params.limit)) {\n\t\t return params.offset / params.limit + 1;\n\t}\n}\n',
               	rowStyle:'\nfunction rowStyle(row, index) {\n var classes = [ \'active\', \'success\', \'info\', \'warning\', \'danger\' ];\n\tif (index % 2 === 0 && index / 2 < classes.length) {\n\t\treturn {\n\t\t\tclasses : classes[index / 2]\n\t\t};\n\t}\n\treturn {};\n}\n',
               	cellStyle:'\nfunction cellStyle(value, row, index) {\n var classes = [ \'active\', \'success\', \'info\', \'warning\', \'danger\' ];\n\tif (index % 2 === 0 && index / 2 < classes.length) {\n\t\treturn {\n\t\t\tclasses : classes[index / 2]\n\t\t};\n\t}\n\treturn {};\n}\n',
               	//表格方法
           		getOptions:'\t$("#table").bootstrapTable(\'getOptions\');\n',
           		getSelections:'\t$("#table").bootstrapTable(\'getSelections\');\n',
           		getAllSelections:'\t$("#table").bootstrapTable(\'getAllSelections\');\n',
           		getData:'\t$("#table").bootstrapTable(\'getData\');\n',
           		getRowByUniqueId:'\t$("#table").bootstrapTable(\'getRowByUniqueId\', id);\n',
           		load:'\t$("#table").bootstrapTable(\'load\', data);\n',
           		append:'\t$("#table").bootstrapTable(\'append\', data);\n',
           		prepend:'\t$("#table").bootstrapTable(\'prepend\', data);\n',
           		remove:'\t$("#table").bootstrapTable(\'remove\', {field: \'field\', values: value});\n',
           		removeAll:'\t$("#table").bootstrapTable(\'removeAll\');\n',
           		removeByUniqueId:'\t$("#table").bootstrapTable(\'removeByUniqueId\', id);\n',
           		insertRow:'\t$("#table").bootstrapTable(\'insertRow\', {index: index, row: row});\n',
           		updateRow:'\t$("#table").bootstrapTable(\'updateRow\', {index: index, row: row});\n',
           		updateByUniqueId:'\t$("#table").bootstrapTable(\'updateByUniqueId\', {id: id, row: row});\n',
           		showRow:'\t$("#table").bootstrapTable(\'showRow\', {index:index});\n',
           		hideRow:'\t$("#table").bootstrapTable(\'hideRow\', {index:index});\n',
           		refresh:'\t$("#table").bootstrapTable(\'refresh\');\n',
           		refreshOptions:'\t$("#table").bootstrapTable(\'refreshOptions\', {\n\t\tshowColumns: true, \n\t\tsearch: true, \n\t\tsearch: true, \n\t\tshowRefresh: true, \n\t\turl: \'../json/data1.json\'\n\t});\n',
           		resetView:'\t$("#table").bootstrapTable(\'resetView\');\n',
           		mergeCells:'\t$("#table").bootstrapTable(\'mergeCells\', {index: 1, field: \'name\', colspan: 2, rowspan: 3});\n',
           		checkAll:'\t$("#table").bootstrapTable(\'checkAll\');\n',
           		uncheckAll:'\t$("#table").bootstrapTable(\'uncheckAll\');\n',
           		check:'\t$("#table").bootstrapTable(\'check\', index);\n',
           		uncheck:'\t$("#table").bootstrapTable(\'uncheck\', index);\n',
           		checkBy:'\t$("#table").bootstrapTable(\'checkBy\', {field:\'id\', values:[1, 2, 3]});\n',
           		uncheckBy:'\t$("#table").bootstrapTable(\'uncheckBy\', {field:\'id\', values:[1, 2, 3]});\n',
           		showColumn:'\t$("#table").bootstrapTable(\'showColumn\', \'name\');\n',
           		hideCoulumn:'\t$("#table").bootstrapTable(\'hideCoulumn\', \'name\');\n',
           		expandRow:'\t$("#table").bootstrapTable(\'expandRow\', 1);\n',
           		collapseRow:'\t$("#table").bootstrapTable(\'collapseRow\', 1);\n',
           		/*2018101017 add by chenyl for 重置搜索下标*/
                refreshIndex:'\n\t\t/*重置搜索下标，跳转到第一页*/\n\t\tvar $preClick = $("#table").parent().parent().find(".page-pre");\n\t\tif($preClick.siblings().length>1){\n\t\t\t$preClick.next().click();\n\t\t}\n',
                /*2018101017 add by chenyl for 设置表的列宽*/
                columnWidth:'\n\t\t/*\n\t\t* 在表格成功后事件调用，其中id为对应表格的ID\n\t\t* 设置表的列显示固定字符长度，如果table标签中存在open-click="true"属性时则点击单元格格式化为输入框\n\t\t* 设置某些列显示的显示长度：SmartWeb.swJS.index.init({id:"table"},{len:20,column:0},{len:3,column:2},{len:10,column:1});\n\t\t* 设置所有列显示的固定显示长度：SmartWeb.swJS.index.init({id:"table"},{dftlen:15});\n\t\t* 设置所有列显示的固定显示长度：SmartWeb.swJS.index.init({id:"table"},{dftlen:15});\n\t\t* 设置所有列显示的默认显示长度10个字符：SmartWeb.swJS.index.init({id:"table"}); \n\t\t* */\n\t\tSmartWeb.swJS.index.init({id:"table"});\n',
                showTable:'\n\t/*显示bootstrap-table,(参数：表格ID)*/\n\tshowTable("tableId");\n',
                hideTable:'\n\t/*隐藏bootstrap-table,(参数：表格ID)*/\n\thideTable("tableId");\n',

           		//multiselect BEG
           		multiselect:'\t$selector.multiselect({\n\t\t\n\t\t\n\t});\n',
           	    enableClickableOptGroups:'\t\t//选项组可点击\n\t\tenableClickableOptGroups: true,\n',
           	    enableCollapsibleOptGroups:'\t\t//选项组可折叠\n\t\tenableCollapsibleOptGroups: true,\n',
            	disableIfEmpty:'\t\t//没用选项时按钮禁用\n\t\tdisableIfEmpty: true,\n',
	           	disabledText:'\t\t//禁用时,按钮上的显示的文本\n\t\tdisabledText: "Disabled ...",\n',
	           	buttonWidth:'\t\t//设定下拉按钮宽度\n\t\tbuttonWidth: "400px",\n',
	           	dropRight:'\t\t//下拉框靠右显示\n\t\tdropRight: true,\n',
	           	dropUp:'\t\t//下拉框向上显示\n\t\tdropUp: true,\n',
	           	maxHeight:'\t\t//设定下拉框高度\n\t\tmaxHeight: 200,\n',
	           	buttonClass:'\t\t//设定下拉按钮样式\n\t\tbuttonClass: "btn btn-link",\n',
	           	inheritClass:'\t\t//继承按钮初始样式\n\t\tinheritClass: true,\n',
	           	nonSelectedText:'\t\t//没有选项时，按钮显示文本\n\t\tnonSelectedText: "Check an option!",\n',
	           	numberDisplayed:'\t\t//选项被选超过1个，下拉按钮将显示特定文字，常和nSelectedText属性连用\n\t\tnumberDisplayed: 1,\n',
	           	nSelectedText:'\t\t//设置超过指定个数(numberDisplayed)时，下拉按钮显示的文本\n\t\tnSelectedText: " - Too many options selected!",\n',
	           	allSelectedText:'\t\t//设定所有选项被选时，下拉的显示的文本\n\t\tallSelectedText:"No option left ...",\n',
	           	delimiterText:'\t\t//为选中的选项设置分隔符，默认为 , \n\t\tdelimiterText:"; " ,\n',
	           	selectedClass:'\t\t//设定选项被选择时的样式\n\t\tselectedClass: "multiselect-selected",\n',
	           	enableFiltering:'\t\t//启用搜索功能\n\t\tenableFiltering: true,\n',
	           	enableCaseInsensitiveFiltering:'\t\t//忽略大小写搜索\n\t\tenableCaseInsensitiveFiltering: true,\n',
	           	enableFullValueFiltering:'\t\t//前缀模糊搜索\n\t\tenableFullValueFiltering: true,\n',
	           	filterBehavior:'\t\t//根据选项的value值进行搜索\n\t\tfilterBehavior: "value",\n',
	           	filterPlaceholder:'\t\t//设置搜索框的提示语\n\t\tfilterPlaceholder: "Search for something...",\n',
	           	includeSelectAllOption:'\t\t//启用全选\n\t\tincludeSelectAllOption:true,\n',
	           	selectAllJustVisible:'\t\t//与enableFiltering和includeSelectAllOption连用选择所用可见选项\n\t\tselectAllJustVisible: false,\n',
	           	selectAllText:'\t\t//设定全选的文本与includeSelectAllOption连用\n\t\tselectAllText: "Check all!",\n',
	           	selectAllValue:'\t\t//设定全选的value与includeSelectAllOption连用\n\t\tselectAllValue: "select-all-value",\n',
	           	selectAllName:'\t\t//设定全选的name与includeSelectAllOption连用\n\t\tselectAllName: "select-all-name",\n',
	           	multiselectonChange:'\t\t//改变选项时，触发\n\t\tonChange: function(option, checked, select) {\n\t\t\talert("Changed option " + $(option).val() + ".");\n\t\t},\n',
	           	multiselectonDropdownShow:'\t\t//下拉时触发\n\t\tonDropdownShow: function(event) {\n\t\t\talert("Dropdown shown.");\n\t\t},\n',
	           	multiselectonDropdownShown:'\t\t//显示出下拉框时触发\n\t\tonDropdownShown: function(event) {\n\t\t\talert("Dropdown closed.");\n\t\t},\n',
	           	multiselectonDropdownHidden:'\t\t//下拉框隐藏后触发\n\t\tonDropdownHidden: function(event) {\n\t\t\talert("Dropdown closed.");\n\t\t},\n',
	            multiselectonbuttonText:'\t\t//根据选项的多少，设定按钮上的文字显示\n\t\tbuttonText: function(options, select) { \n\t\t\tif (options.length === 0) {\n\t\t\t\treturn "No option selected ...";\n\t\t\t }\n\t\t\telse if (options.length > 3) {return "More than 3 options selected!";\n\t\t\t}\n\t\t\telse {\n\t\t\t\tvar labels = [];\n\t\t\t\toptions.each(function() {\n\t\t\tif ($(this).attr("label") !== undefined) {\n\t\t\t\tlabels.push($(this).attr("label"));\n\t\t\t}\n\t\t\telse {\n\t\t\t\tlabels.push($(this).html());\n\t\t\t}\n\t\t\t});\n\t\t\t\treturn labels.join(", ") + "";\n\t\t\t }\n\t\t},\n',
	            multiselectbuttonTitle:'\t\t//修改按钮标题title\n\t\tbuttonTitle: function(options, select) {\n\t\t\t  var labels = [];\n\t\t\t  options.each(function () {\n\t\t\t\tlabels.push($(this).text());\n\t\t\t });\n\t\t\t return labels.join(" - ");\n\t\t},\n',
	            multiselectoptionLabel:'\t\t//定义标签选项值\n\t\toptionLabel: function(element) {\n\t\t\t\t return $(element).html() + "(" + $(element).val() + ")";\n\t\t},\n',
	            multiselectoptionClass:'\t\t//设定下拉列表li元素的样式\n\t\toptionClass: function(element) {\n\t\t\t\t var value = $(element).val();\n\t\t\t\t if (value%2 == 0) {\n\t\t\t\t\t return "even";\n\t\t\t\t }\n\t\t\t\t else {\n\t\t\t\t\t  return "odd";\n\t\t\t\t }\n\t\t},\n',
	            multiselectonSelectAll:'\t\t//所有的选项被选择时回调\n\t\tonSelectAll: function() {\n\t\t\t\talert("onSelectAll triggered.");\n\t\t},\n',
	            multiselectonInitialized:'\t\t//绑定点击事件，点击按钮初始化组件时回调\n\t\tonInitialized: function(select, container) {\n\t\t\t\t alert("Initialized.");\n\t\t},\n',
	            multiselectdestroy:'\t\t//销毁multiselect\n\t\t$selector.multiselect("destroy");\n',
	            multiselectrefresh:'\t\t//刷新multiselect\n\t\t$selector.multiselect("refresh");\n',
	            multiselectrebuild:'\t\t//重构下拉列表\n\t\t$selector.multiselect("rebuild");\n',
	            multiselectsetArray:'\t\t//设定选项，["1", "2", "4"]为value值\n\t\t$selector.multiselect("select", ["1", "2", "4"]);\n',
	            multideselectsetArray:'\t\t//取消选项，["1", "2", "4"]为value值\n\t\t$selector.multiselect("deselect", ["1", "2", "4"]);\n',
	            multiselectselectAll:'\t\t//选择所有可见选项\n\t\t$selector.multiselect("selectAll", true);\n',
	            multiselectdeselectAll:'\t\t//取消所有可见被选选项\n\t\t$selector.multiselect("deselectAll", true);\n',
	            multiselectupdateButtonText:'\t\t//更新按钮文本\n\t\t$selector.multiselect("updateButtonText", true);\n'	,
	            multiselectsetOptions:'\t\t//改变Options配置\n\t\t//var firstConfigurationSet = {\n\t\t\t//  includeSelectAllOption: false,\n\t\t\t//  enableFiltering: false\n\t\t// };\n\t\t$selector.multiselect("setOptions", options);\n',
	            multiselectdisable:'\t\t//禁用下拉列表\n\t\t$selector.multiselect("disable");\n',
	            multiselectenable:'\t\t//启用下拉列表\n\t\t$selector.multiselect("enable");\n',
	            multiselectdataprovider:'\t\t//数组创建Multiselect\n\t\t// var options = [\n\t\t\t//{label: "Option 1", title: "Option 1", value: "1", selected: true},\n\t\t\t//{label: "Option 2", title: "Option 2", value: "2"},\n\t\t\t//{label: "Option 3", title: "Option 3", value: "3", selected: true},\n\t\t\t//{label: "Option 4", title: "Option 4", value: "4"},\n\t\t\t//{label: "Option 5", title: "Option 5", value: "5"},\n\t\t\t//{label: "Option 6", title: "Option 6", value: "6", disabled: true}\n\t\t// ];\n\t\t$selector.multiselect("dataprovider", options);\n',
	            multiselectReadOnly:'\n\t/*设置下拉框选择不可编辑,(参数：下拉框组件ID)*/\n\tmultiselectReadOnly("id");\n',
                //multiselect END
	            //日期改变
	            dateChange:'\t//timeSelector:日期时间选择器,formSelector:表单选择器,timeField:日期时间域\n\t$(\'#timeSelector\').datetimepicker().on(\'changeDate show\',function(e) {\n\t\t$(\'#formSelector\').data(\'bootstrapValidator\').updateStatus(\'timeField\', \'NOT_VALIDATED\', null).validateField(\'timeField\');\n\t});\n',
	            dateGet:'\n\t/*获取日期控件的值,dateId为日期控件ID*/\n\tgetDateValue("dateId");\n',
	            dateSet:'\n\t/*设置日期控件的值,(参数：日期控件id、日期真实值)*/\n\tsetDateValue("dateId","dateVal");\n',
	            /*单选按钮*/
	            setRadioVal:'\n\t/*设置单选按钮的值,(参数：单选按钮的name、单选按钮的值)*/\n\tsetRadioVal("name", "val");\n',
	            getRadioVal:'\n\t/*获取单选按钮选中的值,(参数：单选按钮的name)*/\n\tgetRadioVal("name");\n',
	            getRadioLabel:'\n\t/*获取单选按钮选中的标签值,(参数：单选按钮的name)*/\n\tgetRadioLabel("name");\n',
	            /*复选按钮*/
                setCheckboxVal:'\n\t/*根据值设置复选框选中状态,默认分隔符为",",(参数：复选按钮的name,设置值列表val,值列表的分隔符splitor)*/\n\tsetCheckboxVal("name");\n',
                getCheckboxVal:'\n\t/*获取复选框选中的值,(参数：复选按钮的name)*/\n\tgetCheckboxVal("name");\n',
                getCheckboxLabel:'\n\t/*获取复选框选中的标签,(参数：复选按钮的name)*/\n\tgetCheckboxLabel("name");\n',
                /*图标选择器*/
                setIconSelectDisabled:'\n\t/*设置图标选择器不可编辑,(参数：图标组件ID)*/\n\tsetIconSelectDisabled("iconId");\n',
                /*文件选择器*/
                setFileSelectDisabled:'\n\t/*设置文件选择器不可编辑,(参数：文件选择器ID)*/\n\tsetFileSelectDisabled("fileId");\n',

                /*富文本编辑器常用API*/
	            getEditor:'/*实例化编辑器到id为 container 的 dom 容器上*/\nvar ue = UE.getEditor(\'container\');\n',
	            setContent:'/*实例后在ready方(组件渲染后事件)中设置内容*/\nue.ready(function() {\n\tue.setContent(\'<p>hello!</p>\');\n});\n',
	            setContent2:'/*实例后在ready方(组件渲染后事件)中设置内容,第二个参数为true是追加*/\nue.ready(function() {\n\tue.setContent(\'<p>hello!</p>\', true);\n});\n',
	            getContent:'/*获取编辑器html内容*/\nue.ready(function() {\n\tvar html = ue.getContent();\n});\n',
	            getContentTxt:'/*获取纯文本内容*/\nue.ready(function() {\n\tvar txt = ue.getContentTxt();\n});\n',
	            getPlainTxt:'/*获取保留格式的文本内容*/\nue.ready(function() {\n\tvar txt = ue.getPlainTxt();\n});\n',
	            hasContents:'/*判断编辑器是否有内容*/\nue.hasContents();\n',
	            ueFocus:'/*让编辑器获得焦点*/\nue.focus();\n',
	            ueBlur:'/*让编辑器失去焦点*/\nue.blur();\n',
	            isFocus:'/*判断编辑器是否获得焦点*/\nue.isFocus();\n',
	            setDisabled:'/*设置当前编辑区域不可编辑*/\nue.setDisabled();\n',
	            setEnabled:'/*设置当前编辑区域可编辑*/\nue.setEnabled();\n',
	            setHide:'/*隐藏编辑器*/\nue.setHide();\n',
	            setShow:'/*显示编辑器*/\nue.setShow();\n',
	            getText:'/*获得当前选中的文本*/\nue.selection.getText();\n',
	            /*富文本编辑器常用命令*/
	            inserthtml:'/*在当前光标位置插入html内容*/\nue.execCommand(\'inserthtml\', \'<span>hello!</span>\');\n',
	            setBold:'/*加粗*/\nue.execCommand(\'bold\');\n',
	            setItalic:'/*加斜线*/\nue.execCommand(\'italic\');\n',
	            setSuperscript:'/*设置上标*/\nue.execCommand(\'superscript\');\n',
	            setSubscript:'/*设置下标*/\nue.execCommand(\'subscript\');\n',
	            setForecolor:'/*设置字体颜色*/\nue.execCommand(\'forecolor\', \'#FF0000\');\n',
	            setBackcolor:'/*设置字体背景颜色*/\nue.execCommand(\'backcolor\', \'#0000FF\');\n',
	            setUndo:'/*回退编辑器内容*/\nue.execCommand(\'undo\');\n',
	            setRedo:'/*撤销回退编辑器内容*/\nue.execCommand(\'redo\');\n',
	            setSource:'/*切换源码和可视化编辑模式*/\nue.execCommand(\'source\');\n',
	            setSelectall:'/*选中所有内容*/\nue.execCommand(\'selectall\');\n',
	            setCleardoc:'/*清空内容*/\nue.execCommand(\'cleardoc\');\n',
	            setDrafts:'/*读取草稿箱*/\nue.execCommand(\'drafts\');\n',
	            setClearlocaldata:'/*清空草稿箱*/\nue.execCommand(\'clearlocaldata\');\n'
            }
        </script>
    </body>
</html>
