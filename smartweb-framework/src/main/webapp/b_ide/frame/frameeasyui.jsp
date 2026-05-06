<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String webapp = request.getContextPath();
String basePath = webapp;
%>

<!-- http://localhost:8080/eide/jsp/frame/frameeasyui.jsp -->

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>FACEUI</title>
	<!-- jQuery引入 -->
	<script type="text/javascript" src="<%=basePath%>/b_base/jquery-1.8.3.min.js" charset="utf-8"> </script>
	
	<!-- easyUI引入 -->
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/default/easyui.css" />
	<script type="text/javascript" src="<%=basePath%>/b_base/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
	
	
	<!-- messenger -->
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/messenger.js" charset="utf-8"></script>
	
	<!-- backspace key handler -->
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/backspace.js" charset="utf-8"> </script>
	<style type="">
		body {
		    background-image: url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/bc4.jpg");
		}
		
		#tran80{
    		 background-image: url("<%=basePath %>/b_base/jquery-easyui-1.4.2/themes/frame/images/80.png");/**/
		}
		
		#tran90{
    		 background-image: url("<%=basePath %>/b_base/jquery-easyui-1.4.2/themes/frame/images/90.png");/**/
		}
	</style> 
</head>

<body id="layout" class="">

    <div id="tran80" data-options="region:'north',split:true,border:false" style="height:30px;">
    	<button type="button" class="btn btn-default btn-block" onclick="sendMessage('ide','hello');">send to ide</button>
    </div>
    <div  data-options="region:'west',split:true ,border:false" style="width:200px;border-bottom-left-radius:6px;border-bottom-right-radius:6px;border-top-left-radius:6px;border-top-right-radius:6px;" id="westid">
       	<div id="idcenter" class="" data-options="fit:true">   
            <div id="tran80" data-options="region:'center',border:false" style="border-bottom-left-radius:6px;border-bottom-right-radius:6px;border-top-left-radius:6px;border-top-right-radius:6px;">
            </div>   
            <div id="tran80" data-options="region:'south',split:true,border:false" style="height:300px; border-bottom-left-radius:6px;border-bottom-right-radius:6px;border-top-left-radius:6px;border-top-right-radius:6px;">
	            <table id="dataGrid" class="easyui-propertygrid">
				</table>
            </div>      
        </div>  
    </div>   
    <div data-options="region:'center',border:false" style="padding:0px; border-bottom-left-radius:6px;border-bottom-right-radius:6px;border-top-left-radius:6px;border-top-right-radius:6px;">
       	<iframe id="ide" name="hello" src="../../jsp/bootstrap/ide.jsp" style="border: 0; width: 100%; height: 100%;"></iframe>
    </div>
    
<script>

	var messenger;
	
	$(function(){
	    messenger = new Messenger('parent', 'faceui');
	    var ide = document.getElementById('ide');
	    /* var iframe2 = document.getElementById('iframe2'); */
	
	    messenger.listen(function (msg) {
	        /* var newline = '\n';
	        var text = document.createTextNode(msg + newline);
	        document.getElementById('output').appendChild(text); */
	        console.info('form to frame message:'+msg);
	        if(msg=='button'){
	        	console.info('frame button');
	        	$('#dataGrid').propertygrid({    
	    			url: 'propertygrid_data1.json',
	    			method: 'get',
	    			showGroup: true,
	    			striped: true,
	    			border: false,
	    			fitColumns:true
	    		});
	        	
	        }else{
		        /* $("#layout").layout("collapse", "west"); */
	        }
	        
	       	$('#dataGrid').datagrid({
	       		onAfterEdit:function(index, row, changes){
	   				if(changes.value==undefined){
	   					return;
	   				}
	   				alert("send:"+changes.value);
	   				sendMessage('ide',changes.value);
	   			} 
	   		});
	       /*  $("#layout").layout("collapse", "north"); */
	    });
	    console.info('===============');
	    console.info($('#ide').contentWindow);
	    console.info('===============');
	    messenger.addTarget(ide.contentWindow, 'ide');
	    
	    /* messenger.addTarget(iframe2.contentWindow, 'iframe2'); */
	    /* var panel = $('#layout').layout('panel','west'); */
	   
	    $('#westid').panel({
	    	onResize:function(width, height){
				setTimeout( "$('#dataGrid').datagrid('resize', { })", 200 );
	    	}
	    });
			
	});
	
	
    function sendMessage(name,changes) {
        messenger.targets[name].send(changes);
    }

    function sendAll() {
        messenger.send("message from parent: to all");
    }
    
    $('#layout').addClass("easyui-layout");
	$('#idcenter').addClass("easyui-layout");
	


</script>
      
</body>
</html>
