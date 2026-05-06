/* ========================================================================
 * Rainbow: autoQuery.js v1.0.0 
 * Author: pangzhe
 * Time: 20170110
 * Copyright 2017-2020 Adtec, Inc.
 * 
 * 简介：
 * 1、数据初始化
 * 2、界面事件获取
 * 3、内存数据存储功能
 * ======================================================================== */

var $saveFlag = false;
var $finish = '0000';	/*保存完成标志第1~4位分别代表jsp、js、po、action*/
var $interval = undefined;
	$(function () { 
		/*
		 * 保存
		 */
		$("#btnId_754957").click(function(){
			$saveFlag = false;
			$finish = '0000';
			if($('#saveHtml').prop('checked')){
				saveJsp();
			}
			if($('#saveJs').prop('checked')){
				saveJs();
			}
			if($('#savePo').prop('checked')){
				savePo();
			}
			if($('#saveAction').prop('checked')){
				saveAction();
			}
			
			//检查是否所有相关的已经保存成功,每100毫秒执行一次
			$interval = setInterval(chkSaveFinish,100);
		});
	});
	
	function chkSaveFinish(){
		var $result = ($('#saveHtml').prop('checked')?'1':'0') + ($('#saveJs').prop('checked')?'1':'0')
					+ ($('#savePo').prop('checked')?'1':'0') + ($('#saveAction').prop('checked')?'1':'0');
		console.info('$result='+$result+' , $finish='+$finish);
		if($finish===$result){
			clearInterval($interval);
			if($saveFlag){
				alert("保存成功");
			}else{
				alert("保存失败");
			}
		}
		
	}
	
	function save() {
		saveJsp();
		saveJs();
		/*savePo();*/
		/*saveAction();*/
	}
	
	/*
	 * 保存JSP
	 */
	function saveJsp(){
		
		
		var $formSave = $('#formId_save');
		var fs = $formSave.serializeArray();
		
		$.ajax({
			   type: "POST",
			   data: 'jspfile='+htmlspecialchars(getHtml())+'&path='+window.parent.savePath,
			   url: ctxIde+'/ideAuto/jspSave',
			   success: function(data){
				   if(data.obj="SUCCESS"){
					   $saveFlag = true;
				   }else{
					   $saveFlag = false;
				   }
				   $finish = '1'+$finish.substring(1,$finish.length);
				   console.info('saveJsp='+$finish);
			   }
		});
	}



	/**
	 * @保存JS
	 */
	function saveJs(){
		var context = $.trim(editor.getValue());
		if(context.length==0){
			formateJsons();
			handler_datagrid_events();
			handler_js();
			context = $.trim(editor.getValue());
		}
		console.info('saveJs : '+context);
	    $.ajax({
	        type: "POST",
	        data: {
	            jsContent: context,
	            path: window.parent.savePath
	        },
	        url: ctxIde+'/ide/setJScript',
	        dataType: "json",
	        success: function(data){
	        	if(data.obj="SUCCESS"){
					$saveFlag = true;
				}else{
					$saveFlag = false;
				}
	        	$finish = $finish.substring(0,1)+'1'+$finish.substring(2,$finish.length);
				console.info('saveJs='+$finish);
	        }
	    });
	}

	/*
	 * 保存PO
	 */
	function savePo(){
		var context = editorPo.getValue();
	    $.ajax({
	        type: "POST",
	        data: {
	            poContent: context,
	            /*path: window.parent.savePath*/
	            path: 'Temp'
	        },
	        url: ctxIde+'/ideAuto/savePo',
	        dataType: "json",
	        success: function(data){
	        	if(data.obj="SUCCESS"){
					$saveFlag = true;
				}else{
					$saveFlag = false;
				}
	        	$finish = $finish.substring(0,2)+'1'+$finish.substring(3,$finish.length);
				console.info('savePo='+$finish);
	        }
	    });
	}
	/*
	 * 保存ACTION
	 */
	function saveAction(){
		//TODO
		handler_action();
		
		var context = editorAction.getValue();
	    $.ajax({
	        type: "POST",
	        data: {
	            poContent: context,
	            /*path: window.parent.savePath*/
	            path: 'Temp'
	        },
	        url: ctxIde+'/ideAuto/saveAction',
	        dataType: "json",
	        success: function(data){
	        	if(data.obj="SUCCESS"){
					$saveFlag = true;
				}else{
					$saveFlag = false;
				}
	        	$finish = $finish.substring(0,3)+'1';
				console.info('saveAction='+$finish);
	        }
	    });
	}