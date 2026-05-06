<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String webapp = request.getContextPath();
	String basePath =  webapp;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>RAINBOW</title>
<!-- jQuery引入 -->
<script type="text/javascript"
	src="<%=basePath%>/b_base/jquery-1.8.3.min.js" charset="utf-8"> </script>


		<link rel="stylesheet" type="text/css" href="<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/default/easyui.css" />
		<script type="text/javascript" src="<%=basePath%>/b_base/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>

		<!-- messenger -->
		<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/messenger.js" charset="utf-8"></script>

		<!-- backspace key handler -->
		<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/backspace.js" charset="utf-8"> </script>

		<script type="text/javascript" src="./frame.js" charset="utf-8"> </script>

		<style type="">
body {
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/bc15.jpg");
}

#tran50 {
	background-image:	
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/50.png");
	/**/
}

#tran80 {
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/80.png");
	/**/
}

#westid{
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/80.png");
	/**/
}

#tran90 {
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/90.png");
	/**/
}
</style>
</head>

<body id="layout" class="">
	<div id="tran80" data-options="region:'north',border:false"
		style="width: 100%; height: 50px;">
		<iframe id="model" name="model" src="<%=basePath%>/b_ide/bootstrap/model.jsp"
			style="border: 0; width: 100%; height: 100%;"></iframe>
	</div>
	<!-- <div  data-options="region:'west',split:true ,border:false"
		style="width: 200px; border-bottom-left-radius: 6px; border-bottom-right-radius: 6px; border-top-left-radius: 6px; border-top-right-radius: 6px;"
		id="tran80">
		<ul id="treeDemo" class="ztree" style="over-flow: auto;"></ul>
	</div> -->
	<div id="centerID" data-options="region:'center',border:false"
		style="padding: 0px; border-bottom-left-radius: 6px; border-bottom-right-radius: 6px; border-top-left-radius: 6px; border-top-right-radius: 6px;">
		<iframe id="ide" name="hello" src="../../b_ide/bootstrap/ide.jsp"
			style="border: 0; width: 100%; height: 100%;"></iframe>
	</div>
	
	<div id="westid" data-options="region:'east',border:false,split:true " style="width:250px;border-bottom-left-radius: 6px; border-bottom-right-radius: 6px; border-top-left-radius: 6px; border-top-right-radius: 6px;">
		<table id="dataGrid" class="easyui-propertygrid">
		</table>
	</div>
	
	<div id="dialog" title="RAINBOW PREVIEW">
		<iframe id="preview" scrolling="yes" frameborder="0"
			src="<%=basePath%>/b_ide/frame/preview/preview.jsp"
			style="width: 100%; height: 98%;"></iframe>
	</div>

	<script type="text/javascript">
        $( "#dialog" ).dialog({
          width : 1300,  
          height : 580,  
          modal :  true,  
          minimizable : false,  
          maximizable : true,
          maximized : true,
          shadow : false,  
          cache : false,
          closed : true,  
          collapsible : false,  
          resizable : false, 

        });
    </script>

	<script type="text/javascript">

	var messenger;
	var savePath = '';
	
	$(function(){
		
		savePath = '${param.url}'; 
		
	    messenger = new Messenger('parent', 'faceui');
	    var ide = document.getElementById('ide');

	    var model = document.getElementById('model');
	    
	    var preview = document.getElementById('preview');
	    
	
	    messenger.listen(function (msg) {
	    	if (msg == "bigpc")
	    	{
	    		$("#centerID").css("width","100%"); 
	    	}else if (msg == "pc")
	    	{
	    		$("#centerID").css("width","970px"); 
	    	}else if (msg == "pad")
	    	{
	    		$("#centerID").css("width","750px");
	    	}else if (msg == "phone")
	    	{
	    		$("#centerID").css("width","580px");
	    	}else if(msg=="download")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="save")
	    	{
	    		var m = new Object();
	    		m.name = 'save';
	    		m.value = savePath;
	    		sendMessage('ide',m);
	    	}else if(msg=="modal")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="clear")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="edit")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="devpreview")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="sourcepreview")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="browserPreview")
	    	{
	    		sendMessage('ide',msg);
	    		
	    	}else if(msg=='open'){
	    		sendMessage('preview',msg);
	    		$('#dialog').panel('refresh').panel('open');
	    		
	    	}else{
	    		mesHandler(msg);
	    	}
	    });
	    //
	    messenger.addTarget(ide.contentWindow, 'ide');
	    messenger.addTarget(model.contentWindow, 'model');
	    messenger.addTarget(preview.contentWindow, 'preview');

	    
	    $('#westid').panel({
	    	onResize:function(width, height){
				setTimeout( "$('#dataGrid').datagrid('resize', { })", 200 );
				setTimeout( "$('#dataGrid2').datagrid('resize', { })", 200 );
				setTimeout( "$('#dataGrid3').datagrid('resize', { })", 200 );
	    	}
	    });
	    
	    //iframe完全加载完加载交易
	    document.getElementById("ide").onload=function(){  
		    openTrade();
	    };
	    
	});
	
	//反显资源
	
	function openTrade(){
		$.ajax({ 
			type: "POST",
			url:ctxIde+'/ide/jspGet',
			data: {"path":savePath},
			dataType : "json",
			success : function(data) {
				//传送到IDE.jsp
				var msg = new Object();
				msg.eleType = 'jspCon';
				msg.value = data.obj;
				sendMessage('ide',msg);
			}
		});
	}
	
	// 向单个子页面发送消息
    function sendMessage(name,changes) {
        messenger.targets[name].send(changes);
    }
    // 向全部子页面发送‘message from parent: to all’
    function sendAll() {
        messenger.send("message from parent: to all");
    }
    
    $('#layout').addClass("easyui-layout");
	$('#idcenter').addClass("easyui-layout");
	
    </script>
</body>
</html>
