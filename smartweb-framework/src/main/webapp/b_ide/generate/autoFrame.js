/* ========================================================================
 * Rainbow: autoFrame.js v1.0.0 
 * Author: pangzhe
 * Time: 20170110
 * Copyright 2017-2020 Adtec, Inc.
 * 
 * ======================================================================== */

//
var editor = undefined;
var editorPo = undefined;
var editorAction = undefined;
var editorHtml = undefined;
var editorXml = undefined;
var isInitModule = false;	//判断是否需要初始化模板需要的js及相关配置页面

$(function () { 
	init_ace();
	init_ace_po();
	init_ace_action();
	init_ace_html();
	init_ace_xml();
	init_frame();
	
 });

function init_ace(){
	editor = ace.edit("editor");
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
    /*editor.setReadOnly(true);*/
    editor.$blockScrolling = Infinity;
    
    var value = editor.getValue().trim();
	console.info('editor='+value);
	if(value!==''){
		return;
	}
}

function init_ace_po(){
	editorPo = ace.edit("editorPo");
	editorPo.setTheme("ace/theme/xcode");// cobalt  merbivore_soft sqlserver terminal textmate tomorrow_night_blue tomorrow_night_bright tomorrow_night'tomorrow vibrant_ink'xcode
    //3.0设置程序语言模式
	editorPo.session.setMode("ace/mode/java");
    //4.0设置代码折叠:
	editorPo.getSession().setUseWrapMode(true);
    //5.0设置高亮:
	editorPo.setHighlightActiveLine(true);
    //6.0设置、获取内容:
    var value = editorPo.getValue();
    /* editor.setValue('-------------'); */
    //7.0获取总行数:
    var length = editorPo.session.getLength();
    //8.0跳转到行:
    editorPo.gotoLine();
    //9.0使用软标签:
    editorPo.getSession().setUseSoftTabs(true);
    editorPo.getSession().setTabSize(4);
    editorPo.setReadOnly(true);
    editorPo.$blockScrolling = Infinity;
    
    var value = editorPo.getValue().trim();
	console.info('editorPo='+value);
	if(value!==''){
		return;
	}
}



function init_ace_html(){
	editorHtml = ace.edit("editorHtml");
	editorHtml.setTheme("ace/theme/xcode");// cobalt  merbivore_soft sqlserver terminal textmate tomorrow_night_blue tomorrow_night_bright tomorrow_night'tomorrow vibrant_ink'xcode
    //3.0设置程序语言模式
	editorHtml.session.setMode("ace/mode/html");
    //4.0设置代码折叠:
	editorHtml.getSession().setUseWrapMode(true);
    //5.0设置高亮:
	editorHtml.setHighlightActiveLine(true);
    //6.0设置、获取内容:
    var value = editorHtml.getValue();
    /* editor.setValue('-------------'); */
    //7.0获取总行数:
    var length = editorHtml.session.getLength();
    //8.0跳转到行:
    editorHtml.gotoLine();
    //9.0使用软标签:
    editorHtml.getSession().setUseSoftTabs(true);
    editorHtml.getSession().setTabSize(4);
    editorHtml.setReadOnly(true);
    editorHtml.$blockScrolling = Infinity;
    
    var value = editorHtml.getValue().trim();
	console.info('editorHtml='+value);
	if(value!==''){
		return;
	}
}

function init_ace_action(){
	editorAction = ace.edit("editorAction");
	editorAction.setTheme("ace/theme/xcode");// cobalt  merbivore_soft sqlserver terminal textmate tomorrow_night_blue tomorrow_night_bright tomorrow_night'tomorrow vibrant_ink'xcode
    //3.0设置程序语言模式
	editorAction.session.setMode("ace/mode/java");
    //4.0设置代码折叠:
	editorAction.getSession().setUseWrapMode(true);
    //5.0设置高亮:
	editorAction.setHighlightActiveLine(true);
    //6.0设置、获取内容:
    var value = editorAction.getValue();
    /* editor.setValue('-------------'); */
    //7.0获取总行数:
    var length = editorAction.session.getLength();
    //8.0跳转到行:
    editorAction.gotoLine();
    //9.0使用软标签:
    editorAction.getSession().setUseSoftTabs(true);
    editorAction.getSession().setTabSize(4);
    editorAction.setReadOnly(true);
    editorAction.$blockScrolling = Infinity;
    
    var value = editorAction.getValue().trim();
	console.info('editorAction='+value);
	if(value!==''){
		return;
	}
}

/*初始化xml编辑器*/
function init_ace_xml(){
	editorXml = ace.edit("editorXml");
	editorXml.setTheme("ace/theme/xcode");// cobalt  merbivore_soft sqlserver terminal textmate tomorrow_night_blue tomorrow_night_bright tomorrow_night'tomorrow vibrant_ink'xcode
    //3.0设置程序语言模式
	editorXml.session.setMode("ace/mode/xml");
    //4.0设置代码折叠:
	editorXml.getSession().setUseWrapMode(true);
    //5.0设置高亮:
	editorXml.setHighlightActiveLine(true);
    //6.0设置、获取内容:
    var value = editorXml.getValue();
    /* editor.setValue('-------------'); */
    //7.0获取总行数:
    var length = editorXml.session.getLength();
    //8.0跳转到行:
    editorXml.gotoLine();
    //9.0使用软标签:
    editorXml.getSession().setUseSoftTabs(true);
    editorXml.getSession().setTabSize(4);
    editorXml.setReadOnly(true);
    editorXml.$blockScrolling = Infinity;
    
    var value = editorXml.getValue().trim();
	console.info('editorXml='+value);
	if(value!==''){
		return;
	}
}

function init_frame() {
	/*
	 * 单选框 多选框可点击label选择
	 */
	function changeState(el) {
        if (el.readOnly) el.checked=el.readOnly=false;
        else if (!el.checked) el.readOnly=el.indeterminate=true;
    }
	
	/*
	 * 界面预览
	 */
	$("#btnId_293329").click(function(){
		handler(formateJsons());
	});
	
	/*
	 * 
	 */
	$("#btnId_save").click(function(){
		$('#saveModal').modal('show');
		/*save();*/
	});
	
	
	
	/*
	 * JS内容预览
	 */
	$("#btnId_ace").click(function(){
		$('#aceModal').modal('show');
		formateJsons();
		handler_datagrid_events();
		handler_js();
	});
	
	/*
	 * PO内容预览
	 */
	$("#btnId_ace_po").click(function(){
		$('#acePoModal').modal('show');
		//TODO 根据接口生成po
		handler_po();
	});
	
	/*
	 * Action内容预览
	 */
	$("#btnId_ace_action").click(function(){
		$('#aceActionModal').modal('show');
		//TODO 根据接口生成Action
		handler_action();
	});

	/*
	 * Html预览
	 */
	$("#btnId_html").click(function(){
		$('#htmlModal').modal('show');
		var html = getHtml();
		
		editorHtml.setValue(html);
	});
	
	//根据jQuery选择器找到需要加载ystep的容器
    //loadStep 方法可以初始化ystep
    $(".ystep1").loadStep({
      //ystep的外观大小
      //可选值：small,large
      size: "large",
      //ystep配色方案
      //可选值：green,blue
      color: "lightblue",
      //ystep中包含的步骤
      steps: [{
          //步骤名称
          title: "接口选择",
          stepIcon:'glyphicon glyphicon-align-justify'
          //步骤内容(鼠标移动到本步骤节点时，会提示该内容)
        },{
        //步骤名称
        title: "模板选择",
        stepIcon:'glyphicon glyphicon-leaf'
        //步骤内容(鼠标移动到本步骤节点时，会提示该内容)
      },{
	    title: "查询条件",
	    stepIcon:'glyphicon glyphicon-search'
      },{
        title: "查询表格",
        stepIcon:'glyphicon glyphicon-list'
      },{
        title: "表单生成",
    	stepIcon:'glyphicon glyphicon-list-alt'
      },{
        title: "保存",
    	stepIcon:'glyphicon glyphicon-floppy-save'
      }]
    });
//    $(".ystep1").setStep(2);
    $(".ystep1").bindTabs('tabs-505781');
    $("#nextstep").bind("click",function(){
    	$(".ystep1").nextStep();
    });
    $("#prestep").bind("click",function(){
    	$(".ystep1").prevStep();
    });
}


//动态加载js脚本文件
function loadScript(url) {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = url;
    document.body.appendChild(script);
    console.info('动态装载js：'+url);
}

//动态移除js脚本文件
function removeScript(url){
	var allScripts = document.getElementsByTagName("script");
	for (var i=allScripts.length; i>=0; i--){
		if (allScripts[i] && allScripts[i].getAttribute("src")!=null && allScripts[i].getAttribute("src").indexOf(url)!=-1){
			console.info('动态移除js：'+allScripts[i].getAttribute("src"));
			allScripts[i].parentNode.removeChild(allScripts[i]);
		}
	}
}

//装载需要的模板块,初始化所有模板需要的js文件及对应的配置页面
function loadModuleSub(){
	//移除其余模板的js文件
	removeScript('./modules_js/');
	//removeScript('b_base/bootstrap-3.4.1/js/bootstrap.js');
	//查询子模块
	$('#panel-883310').load('./modules_sub/'+selModule+'_query.jsp',function(){
		//列表子模块
		$('#panel-371915').load('./modules_sub/'+selModule+'_datagrid.jsp',function(){
			//表单子模块
			$('#panel-405592').load('./modules_sub/'+selModule+'_form.jsp',function(){
				//loadScript('../../b_base/bootstrap-3.4.1/js/bootstrap.js');	//查询模板js
				loadScript('./modules_js/'+selModule+'_query.js');	//查询模板js
				loadScript('./modules_js/'+selModule+'_datagrid.js');	//数据列表模板js
				loadScript('./modules_js/'+selModule+'_form.js');	//表单模板js
				loadScript('./modules_js/'+selModule+'_handler.js');	//处理模板js
//				$("select[data-role='multiselect']").each(function(i) {
//					var $this = $(this);
//					$this.multiselect("rebuild");
//				});
//				$("select[data-role='multiselect']").multiselect('destroy');
//				$("select[data-role='multiselect']").multiselect();
			}); //ajax加载页面			
		}); //ajax加载页面
	}); //ajax加载页面
	
}

/*首字母大写*/
function ucfirst(str) {
	str = str.replace(/\b\w+\b/g, function(word){
	    return word.substring(0,1).toUpperCase()+word.substring(1);
	});
	return str;
}
