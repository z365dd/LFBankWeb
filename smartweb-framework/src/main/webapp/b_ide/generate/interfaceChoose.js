/* ========================================================================
 * SmartWeb: interfaceChoose.js v1.0.0 
 * Author: chenyl
 * Time: 20170516
 * Copyright 2017-2020 Adtec, Inc.
 * 
 * ======================================================================== */

//
var interface_url = ctxIde+"/ideAuto/getAllInterface";
var selInterface = undefined;

$(function () {
	console.info('--------------auto_getAllInterface-------------');
	init_interface_tree();	
 });

function init_interface_tree() {
	//treeView初始化方法
	$('#interfaceTree').treeview({
		levels: 20,
		url:interface_url,
		//节点选择事件
		onNodeSelected: function(event, data) {
			console.info('node='+data);
			console.info('parent='+$('#interfaceTree').treeview('getParent',data));
			//处理逻辑,只处理叶子节点
			if(data.nodes==undefined){
				console.info('叶子节点：'+data.nodeId+':'+data.text);
				var fullPath = getInterfaceNodePath('interfaceTree',data);
				$('#interfaceTitle').html('接口XML文件('+fullPath+')预览');
				//获取对应的接口文件进行预览
				$.ajax({
					   type: "GET",
					   url: ctxIde+'/generate/'+fullPath,
					   success: function(data){
//						   console.info(XMLtoString(data));
						   editorXml.setValue(XMLtoString(data));
					   }
				});
				selInterface = fullPath;
				if(undefined!=selModule && undefined!=selInterface){
			    	isInitModule = true;
			    	//装载需要的模板块
			    	loadModuleSub();
			    	//初始化需要的模板块的js
//			    	initModuleJs();
			    }else{
			    	isInitModule = false;
			    }
			}else{
				console.info('非叶子节点：'+data.nodeId+':'+data.text);
			}
		}
	});

}

/*获取接口的全路径包*/
function getInterfaceNodePath(treeId, treeNode) {
	var path = '';
	var p = $('#'+treeId).treeview('getParent',treeNode);
	while(null!=p && undefined!=p.nodes){
		path = p.text + '/' + path;
		p = $('#'+treeId).treeview('getParent',p);
	}
	return path+treeNode.text;
};

/*xml转成字符串*/
function XMLtoString(elem){  
    var serialized;  
    try {  
        // XMLSerializer exists in current Mozilla browsers                                                                              
        serializer = new XMLSerializer();                                                                                                
        serialized = serializer.serializeToString(elem);                                                                                 
    }                                                                                                                                    
    catch (e) {  
        // Internet Explorer has a different approach to serializing XML                                                                 
        serialized = elem.xml;                                                                                                           
    }      
    return serialized;                                                                                                                   
} 