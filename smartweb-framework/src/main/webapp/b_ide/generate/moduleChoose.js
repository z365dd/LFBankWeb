/* ========================================================================
 * SmartWeb: moduleChoose.js v1.0.0 
 * Author: chenyl
 * Time: 20170516
 * Copyright 2017-2020 Adtec, Inc.
 * 
 * ======================================================================== */

//
var module_url = ctxIde+"/ideAuto/getAllModuleName";
var selModule = undefined;
var moduleList = undefined;

$(function () {
	console.info('--------------auto_getAllModuleName-------------');
	init_module_tab();	
 });

function init_module_tab(){
	var $moduleChooseTab = $('#moduleChooseTab');
	var $moduleChooseTabContent = $('#moduleChooseTabContent');
	//查询模板列表
	$.post(module_url, {},
	     function(result){ 
			var data = result.data;
			var tabList = '';
			var contentList = '';
			if(data!=undefined){
				moduleList = data;
				for(var i=0;i<data.length;i++){
					var mName = data[i].name;
					var mJsp = data[i].jspFile;
					if(i==0){
						tabList += '<li class="active">'
								+ '<a href="#'+mName+'" data-toggle="tab" contenteditable="true" aria-expanded="true">'+mName+'</a></li>';					
						contentList += '<div class="tab-pane active" id="'+mName+'"></div>';	
						selModule = mName;
					}else{
						tabList += '<li>'
							+'<a href="#'+mName+'" data-toggle="tab" contenteditable="true" aria-expanded="false">'+mName+'</a></li>';
						contentList += '<div class="tab-pane" id="'+mName+'"></div>';
					}
				}
				$moduleChooseTab.append(tabList);
				$moduleChooseTabContent.append(contentList);
				console.info("moduleChooseTab html: "+$moduleChooseTab.html());
				//遍历json数组,循环添加a标签click事件:
				$(moduleList).each(function(){
				    console.info(this.name + "--->" + this.jspFile);
				    var $name = this.name;
				    var $jspFile = './modules/'+this.jspFile;
				    $("a[href='#"+$name+"']").click(function(e) {
				        showTabs($name,$jspFile);
				        e.preventDefault();
				    });
				});
				 $("a[href='#"+selModule+"']").click();
			}
	}, "json");
}

function showTabs(tabId, url){
	console.info('页面加载：'+tabId + "--->" + url);
	$("a[href='#"+tabId+"']").tab('show');
    var $tabContent = $('#'+tabId);
    //页面内容已存在则不重新装载
    if($tabContent.length < 100) {
    	$tabContent.load(url); //ajax加载页面
    }
    selModule = tabId;
    if(undefined!=selModule && undefined!=selInterface){
    	isInitModule = true;
    	//装载需要的模板块
    	loadModuleSub();
    }else{
    	isInitModule = false;
    }
    console.info('selModule='+selModule);
}
