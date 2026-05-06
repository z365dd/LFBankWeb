<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String webapp = request.getContextPath();
	String basePath = webapp;
%>
<head>

  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Editor</title>
  <script type="text/javascript" src="<%=basePath%>/b_base/jquery-1.8.3.min.js" charset="utf-8"> </script>
  <script src="src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
  <script src="src-noconflict/ext-language_tools.js" type="text/javascript" charset="utf-8"></script>
  
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
    }
  </style>
</head>
<body>

<pre id="editor">

	function bbb(items) {
	    var i;
	    for (i = 0; i < items.length; i++) {
	        alert("Ace Rocks " + items[i]);
	    }
	}
	

	function foo(items) {
	    var i;
	    for (i = 0; i < items.length; i++) {
	        alert("Ace Rocks " + items[i]);
	    }
	} 
</pre>

<script>
    var editor = ace.edit("editor");
	/**
	  	1.0自动补全
	  	enableBasicAutocompletion：快捷键补全
	  	enableSnippets：判断补全
	  	enableLiveAutocompletion：自动补全
	*/
	var autoCompleteData = [{"meta":"function", "caption":"ajax", "value":"$.ajax({ url: 'test.html', context: document.body, success: function(){});","score":1}];
	var completions = [];
	completions.push({
        caption: 'ajax',
        snippet: "$.ajax({\n  type:\"POST\",\n  url: \"some.php\",\n  data: \"\",\n  success: function(msg){\n  }\n});",
        meta: "snippet",
        type: "snippet"
    }); 
	
	
	/*
	meta：显示在提示框的右边（如下图）。
	caption：显示在提示框的左边（如下图）。
	value：是实际插入的数据。
	score：表示优先级，高的排在前面。
	*/
	
	var tangideCompleter = {
		    getCompletions: function(editor, session, pos, prefix, callback) {
		        if (prefix.length === 0) {
		            return callback(null, []);
		        }else {
		            return callback(null, completions);
		        }
		    }
		}
	var langTools = ace.require("ace/ext/language_tools");
	editor.setOptions({enableBasicAutocompletion: true, enableSnippets: true, enableLiveAutocompletion: false}); 
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
    editor.setHighlightActiveLine(false);
	//6.0设置、获取内容:
    var value = editor.getValue();
    console.info(value);
    /* editor.setValue('-------------'); */
    
    //7.0获取总行数:
    var length = editor.session.getLength();
    console.info(length);
    //8.0跳转到行:
    editor.gotoLine(10);
    //9.0使用软标签:
    editor.getSession().setUseSoftTabs(true); 

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
    */
    editor.find('foo',{
        backwards: false,
        wrap: false,
        caseSensitive: false,
        wholeWord: false,
        regExp: false
    });
    
    
    /**
      13.0 监听改变事件
      
    */
    editor.getSession().on('change', function(e) {
        /* console.info(e); */
    });
    //设置字体大小
    document.getElementById('editor').style.fontSize='12px';
    
</script>

<script>
	//快捷键 CTRL+S 保存JS代码
	$(document).keypress(function(e) {
		if(e.ctrlKey && e.which == 115){
			e.preventDefault();
			e.stopPropagation();
			console.info('TODO---save')
			//TODO save js
		}
	})  
</script>

</body>
</html>
