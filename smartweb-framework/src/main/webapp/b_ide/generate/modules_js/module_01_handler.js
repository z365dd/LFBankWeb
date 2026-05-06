/*
 * 格式化JSON
 */
function formateJsons(){
		//TODO 表格 查询
		formateJson_query();
		formateJson_datagrid();
		formateJson_fome();
	}

/*
 * 根据格式化的JSON生成DOM  也可以用于预览
 */
function handler() {
	/*$('table').bootstrapTable('updateCell',{
		index:'0',
		field:'c_row',
		value:'hello'
	});*/
	
	$('#domContent').empty();
	
	var $queryForm = handler_query();
	
	var mm_query=$('<div>',{
		'ravo':"rainbow_fx_layout",
		'class':"row clearfix",
		'id':'toolbar'
	}).append($('<div>',{
		'class':"col-md-12 column"
	}).append($queryForm));
	
	handler_query_button($queryForm);
	
	var $datagrid = handler_datagrid();
	var mm_datagrid=$('<div>',{
		'ravo':"rainbow_fx_layout",
		'class':"row clearfix"
	}).append($('<div>',{
		'class':"col-md-12 column"
	}).append($datagrid));
	
	
	var $form = handler_form();
	var $form_button = handler_form_button();
	$form.append($form_button);
	

	var $tabContent1 = $('<div>',{
		'class':'tab-pane active',
		'id':'myMainTab_tab1'
	}).append(mm_query).append(mm_datagrid);
	
	var $tabContent2 = $('<div>',{
		'class':'tab-pane',
		'id':'myMainTab_tab2'
	}).append($form);
	
	var $tabModule = $('<div>',{
		'ravo':"rainbow_fx_layout",
		'class':"row clearfix"
	}).append($('<div>',{
		'class':'col-md-12 column'
	}).append($('<div>',{
		'class':'clearfix'
	})).append($('<div>',{
		'ravo':"rainbow_fx_layout_tab",
		'class':"tabbable",
		'id':'navigations'
		}).append($('<ul>',{
					'class':'nav nav-tabs',
					'data-toggle':'tabs',
					'id':'myMainTab'
			}).append($('<li>',{
						'class':'active'	
					}).append($('<a>',{
							'id':'myMainTab_li1',
							'href':'#myMainTab_tab1',
							'data-toggle':'tab',
							'contenteditable':'true',
							'aria-expanded':'true',
							'title':'信息查询列表'
					}).append('信息查询列表'))
			 ).append($('<li>',{
						'class':''	
					}).append($('<a>',{
						'id':'myMainTab_li2',
						'href':'#myMainTab_tab2',
						'data-toggle':'tab',
						'contenteditable':'true',
						'aria-expanded':'true',
						'title':'信息查询添加'
					}).append('信息查询添加'))
			)
		).append($('<div>',{
			'class':'tab-content'
			}).append($tabContent1).append($tabContent2)
		)
	)
	);
	
	$('#domContent').append($tabModule);
	
	$('#myModal').modal('show');
	
	loadScript(ctxIde+'/bootstrap/module/bootstrap-table/src/bootstrap-table.js');
	loadScript(ctxIde+'/bootstrap/module/bootstrap-table/src/extensions/export/bootstrap-table-export.js');
	loadScript(ctxIde+'/bootstrap/module/bootstrap-table/src/locale/bootstrap-table-zh-CN.js');
	loadScript(ctxIde+'/bootstrap/module/bootstrap-table/src/extensions/export/rgwgit-tableExport.js');
	loadScript(ctxIde+'/bootstrap/module/bootstrap-table/src/extensions/editable/bootstrap-table-editable.js');
	loadScript(ctxIde+'/bootstrap/module/bootstrap-table/src/extensions/editable/bootstrap-editable.js');
	
	loadScript(ctxIde+'/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js');//多选框
	//日期框
	loadScript(ctxIde+'/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.min.js');
	loadScript(ctxIde+'/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js');
}



function editor_insert_down(content,wz,_row,_column){
	var index = editor.findAll(wz, {
		backwards: false,
        wrap: false,
        caseSensitive: false,
        wholeWord: false,
        regExp: false
    });
	if(index===0){
		return;
	}
	var c = editor.selection.getCursor();
	if(_row!=undefined && _column!=undefined){
		c.row = c.row+_row;
		c.column = c.column+_column;
	}
	editor.moveCursorToPosition(c)
	editor.clearSelection();
	
	editor.insert('\n'+content);
}

function editor_insert_up(content,wz){
	var index = editor.findAll(wz, {
		backwards: false,
        wrap: false,
        caseSensitive: false,
        wholeWord: false,
        regExp: false
    });
	
	var c = editor.selection.getCursor();
	/*editor.moveCursorToPosition(c)*/
	editor.moveCursorTo(c.row-1,c.column);
	editor.clearSelection();
	
	editor.insert('\n'+content);
}

function editor_insert_last(content){
	var l =	editor.session.getLength();
	editor.gotoLine(l+2);
	editor.insert(content);
}

function editor_findAll(content){
	var index = editor.findAll(content, {
		backwards: false,
        wrap: false,
        caseSensitive: false,
        wholeWord: false,
        regExp: false
    });
	if(index>0){
		return true;
	}else{
		return false;
	}
}



function handler_js(){
	
	//设置获取页码
	var $getPage = '\n function getPage(params) {'+
	'\n if (!isNaN(params.offset) || !isNaN(params.limit)) {'+
		'\n return params.offset / params.limit + 1;'+
	'\n }'+
	'\n }';
	editor_insert_up($getPage,'function init_datagrid() {');
	
	/*
	 * 添加增删查改方法
	 */
	for(var i=0;i<formButtonArrayList.size();i++){
		var butName = formButtonArrayList.get(i);
		var butId = butName+'Btn';
		if(butName ==='save'){//2.2 保存
			//1.0 添加
			
			editor_insert_down('var bootstrapValidator;','var $datagrid_table;');
			editor_insert_down("var FORM_ID='editForm';",'var $datagrid_table;');
			
			editor_insert_down('init_validator();','$(document).ready(function() {');
			
			
			
			var c1 = 
				'\n$("#saveBtn").click(function() {'+
			    	'\nbutton_submit();'+
			    '\n});'
	    	editor_insert_down(c1,'init_datagrid();');
			
			
			var c2 =
				'//auto::表单生成->表单按钮->保存按钮\n'+
				'function button_submit(){\n'+
					'var $btn = $("#saveBtn").button("loading"); \n'+
					'bootstrapValidator.validate();\n'+
					'var b = bootstrapValidator.isValid();\n'+
					'if (!b) {\n'+
						'$btn.button("reset");\n'+
						'return;\n'+
					'}\n'+
					'var url = ctx; \n'+
					'var oprType = $(\'#oprType\').val();\n'+
					'if(oprType===\'0\'){\n'+
						'url = url + \''+getReqUrlByInterface('add')+'\';\n'+
	        		'}else{\n'+
	        			'url = url + \''+getReqUrlByInterface('update')+'\';\n'+
	        		'}var formData = $("#"+FORM_ID).serializeObject();\n'+
	        		'$.post(url, formData,function(data){\n'+
	        	        'if(data.returnCode!==undefined && "0000"!=data.returnCode){\n'+
	        			    'var errMsg = "错误信息["+data.message+"]"; \n'+
	        				'showContent(errMsg,"error");	\n'+
	        			'}else{'+
	        				'$("#"+FORM_ID)[0].reset();/*清空表单数据*/\n'+
	        				'$(\'#oprType\').val("0");\n'+
	        			    '$(\'#myMainTab a[href="#myMainTab_tab1"]\').tab(\'show\');\n'+
	        			    '$("#queryTable").bootstrapTable(\'refresh\');\n'+
	        			'}\n'+	        		    
	        		'}, "json");\n'+
				'}\n';
			editor_insert_up(c2,'function init_datagrid() {');
			
			var c3 =
			'\n//auto::表单生成->表单校验'+	
			'\nfunction init_validator(){'+
			'\n	$("#"+FORM_ID).bootstrapValidator({'+
			'\n    	excluded : [],//[":disabled", ":hidden", ":not(:visible)"] //设置隐藏组件可验证'+
			'\n        feedbackIcons: {'+
			'\n            /*valid: "glyphicon glyphicon-ok",*/'+
			'\n            invalid: "glyphicon glyphicon-remove",'+
			'\n            validating: "glyphicon glyphicon-refresh"'+
			'\n        },'+
			'\n        fields: {'+
			'\n        }'+
			'\n    });'+
			'\n    bootstrapValidator = $("#"+FORM_ID).data("bootstrapValidator");'+
			'\n}';
			editor_insert_up(c3,'function init_datagrid() {');
		}
	}
		
	//返回按钮
	var c4 = 
		'\n$("#backBtn").click(function() {'+
			'\n$(\'#myMainTab a[href="#myMainTab_tab1"]\').tab(\'show\');'+
			'\n$("#editForm")[0].reset();//清空表单数据'+
			'\n$(\'#oprType\').val("0");'+
		'\n});'
	editor_insert_down(c4,'$(document).ready(function() {');
		
	for(var i=0;i<queryButtonArrayList.size();i++){
		var butName = queryButtonArrayList.get(i);
		var butId = butName+'Btn';
		if(butName ==='query'){
			//查询
			var c =
			"\n$('#"+butId+"').click(function () {"+
			"\n	button_query();"+
			"\n});"
			editor_insert_down(c,'$(document).ready(function() {');
			
			c=
			"\nfunction button_query(){"+
			"\n	$datagrid_table.bootstrapTable('refreshOptions', {pageNumber: 1});"+
			"}";
			editor_insert_last(c);		
			
		}
				
		if(butName ==='delete'){
			//删除
			var c =
				"\n$('#"+butId+"').click(function () {"+
				"\n	button_delete();"+
				"\n});"
				editor_insert_down(c,'$(document).ready(function() {');
				
				c=
				'\nfunction button_delete(){'+
					'var selData = $("#queryTable").bootstrapTable(\'getSelections\');'+
					'if(selData.length===0){'+
			        	'showTip(\'请选择要删除的记录！\',\'error\');'+
			        	'return;'+
			    	'}'+
			    	'var reqData = \'\';'+
					'var reqElem = ['+getQueryDeleteElement("delete")+'];'+
					'for(var j=0;j<reqElem.length;j++){'+
						'reqData += \'"\'+reqElem[j]+\'":"\';'+
						'var val = \'\';'+
						'for(var i=0;i<selData.length;i++){'+
							'val += selData[i].reqElem[j];'+
							'if(i<selData.length-1){'+
								'val += \',\';'+
							'}'+
						'}'+
						'reqData += val + \'"\';'+
						'if(j<reqElem.length-1){'+
							'reqData += \',\';'+
						'}'+
					'}'+
					'reqData = (reqData);'+
			    	'$.post(ctx+"'+getReqUrlByBtn("delete")+'", reqData, function(data){'+
        	        'if(data.returnCode!==undefined && "0000"!=data.returnCode){'+
        			    'var errMsg = "错误信息["+data.message+"]"; '+
        				'showContent(errMsg,"error");	'+
        			'}else{'+
        				'showTip(\'删除交易成功！\',\'success\');'+
        			    '$("#queryTable").bootstrapTable(\'refresh\');'+
        			'}'+	        		    
        			'}, "json");'+
				'}';
				editor_insert_last(c);		
		}
		
		if(butName ==='update'){
			//修改			
			var c =
			"\n$('#"+butId+"').click(function () {"+
			"\n	button_update();"+
			"\n});"
			editor_insert_down(c,'$(document).ready(function() {');
			
			c=
			'\nfunction button_update(){'+
				'var selData = $("#queryTable").bootstrapTable(\'getSelections\');'+
				'if(selData.length!=1){'+
		        	'showTip(\'请选择一条记录！\',\'error\');'+
		        	'return;'+
		    	'}'+
		    	'$(\'#myMainTab_li2\').attr(\'title\',\'信息查询修改\');'+
				'$(\'#myMainTab_li2\').html(\'信息查询修改\');'+
		    	'$(\'#editForm\')[0].reset();'+
		    	'$(\'#oprType\').val("1");'+
		    	'var rec = selData[0];'+
		    	'$(\'#myMainTab a[href="#myMainTab_tab2"]\').tab(\'show\');'+
		    	'qryDetail(rec);'+
			'}';
			editor_insert_last(c);		
		}
	}
	
	/*生成明细*/
	var detailC = '\nfunction qryDetail(rec){'+
		'\n//查询查询明细'+
		'\n$.post(ctx+"'+getReqUrlByInterface("detail")+'", { '+getQueryDetailJson('0')+' },'+
		'\nfunction(data){ '+
			'\nif(data.returnCode!==undefined && "0000"!=data.returnCode){ '+
				'\nvar errMsg = "错误信息["+data.message+"]"; '+
				'\nshowContent(errMsg,"error");	'+
			'\n}else{'+
				'\nvar ds = data.dataSetResult[0].data[0];'+
				'\n'+getQueryDetailJson('1')+
			'\n}'+
			'\n}, "json");'+
	'}';
	editor_insert_last(detailC);
	
	/*生成tab页的点击绑定事件*/
	var tabC = '\n$(\'#myMainTab_li1\').click(function() {\n'+
			'$(\'#myMainTab_li2\').attr(\'title\',\'信息查询添加\');\n'+
			'$(\'#myMainTab_li2\').html(\'信息查询添加\');\n'+
			'$(\'#editForm\')[0].reset();\n'+
	    	'$(\'#oprType\').val("0");\n'+
		'});\n'+
	    '\n$(\'#myMainTab_li2\').click(function() {\n'+
			'$(\'#myMainTab_li2\').attr(\'title\',\'信息查询添加\');\n'+
			'$(\'#myMainTab_li2\').html(\'信息查询添加\');\n'+
			'$(\'#editForm\')[0].reset();\n'+
	    	'$(\'#oprType\').val("0");\n'+
		'});\n';
	editor_insert_down(tabC,'$(document).ready(function() {');
	

	//表单
	var m_form_arrayList = formateJson.get('m_form');
	handler_js_form(m_form_arrayList);
	//查询表单
	var m_form_query_arrayList = formateJson.get('m_query');
	handler_js_form(m_form_query_arrayList);
	
	var js_source = editor.getValue();
	editor.setValue(js_beautify(js_source));
}


function handler_js_form(m_form_arrayList){//
	for(var it=m_form_arrayList.iterator();it.hasNext();){
		var as = it.next();//获取 field的属性
		if(as.containsKey('c_type') && as.get('c_type')==='datatime'){//检查表单是否有日期框 
			if(editor_findAll('function init_datetime(){')){
				var c6 =
					'\n$("#'+as.get('id')+'").datetimepicker().on("changeDate", function(ev){'+
					'\n	//手动验证隐藏的input组件'+
					'\n	bootstrapValidator.updateStatus("'+as.get('a_data_name')+'", "NOT_VALIDATED").validateField("'+as.get('a_data_name')+'");'+
					'\n});'
					editor_insert_down(c6,'function init_datetime(){');
			}else{
				var c5 = 
					'\nfunction init_datetime(){'+
					'\n$("#'+as.get('id')+'").datetimepicker().on("changeDate", function(ev){'+
					'\n	//手动验证隐藏的input组件'+
					'\n	bootstrapValidator.updateStatus("'+as.get('a_data_name')+'", "NOT_VALIDATED").validateField("'+as.get('a_data_name')+'");'+
					'\n});'+
					'\n}';
				editor_insert_up(c5,'function init_datagrid() {');
				
				editor_insert_up('init_datetime();','init_datagrid();');
			}
			
			//show
			if(as.containsKey('show') && 'true'==as.get('show')){ 
				var c =
					'\n$("#'+as.get('id')+'").datetimepicker().on("show", function(ev){'+
					'\n});'
				editor_insert_down(c,'function init_datetime(){');
			}
			//hide
			if(as.containsKey('hide') && 'true'==as.get('hide')){
				var c =
					'\n$("#'+as.get('id')+'").datetimepicker().on("hide", function(ev){'+
					'\n});'
				editor_insert_down(c,'function init_datetime(){');
			}
			
			//changeYear
			if(as.containsKey('changeYear') && 'true'==as.get('changeYear')){
				var c =
					'\n$("#'+as.get('id')+'").datetimepicker().on("changeYear", function(ev){'+
					'\n});'
				editor_insert_down(c,'function init_datetime(){');
			}
			//changeMonth
			if(as.containsKey('changeMonth') && 'true'==as.get('changeMonth')){
				var c =
					'\n$("#'+as.get('id')+'").datetimepicker().on("changeMonth", function(ev){'+
					'\n});'
				editor_insert_down(c,'function init_datetime(){');
			}
			//outOfRange
			if(as.containsKey('outOfRange') && 'true'==as.get('outOfRange')){
				var c =
					'\n$("#'+as.get('id')+'").datetimepicker().on("outOfRange", function(ev){'+
					'\n});'
				editor_insert_down(c,'function init_datetime(){');
			}
			
		}else if(as.containsKey('c_type') && as.get('c_type')==='fileInput'){//检查表单是否有文件上传组件
			/*function init_fileInput(){*/
			var c7 =
			'\nfunction init_fileInput(){'+
			'\n}';
			
			var c8 = 
				'\n$("#'+as.get('id')+'").fileinput({'+
				'\n	language : "zh",//设置语言'+
				'\nuploadUrl: "${pageContext.request.contextPath}/fileUpload/UploadHandleServlet",//上传地址'+
				'\nuploadAsync: true,//同步还是异步 true：异步-- false：同步'+
				'\nshowCaption:false,//是否显示标题'+
				'\nshowUpload: true,//是否显示上传按钮'+
				'\nbrowseClass: "btn btn-primary", //按钮样式 '+
				'\nallowedFileExtensions : ["jpg", "png","gif","xls","xlsx"],//接收的文件后缀'+
				'\nallowedFileTypes: ["image", "video", "flash","object"],//接收的文件类型["image", "html", "text", "video", "audio", "flash","object"]'+
				'\nmaxFileCount: 6,//最大上传文件数限制'+
				'\nmaxFileSize: 1000, '+
				"\nmsgFilesTooMany: '选择上传的文件数量({n}) 超过允许的最大数值{m}！',"+
				"\npreviewFileIcon: '<i class="+'"'+"glyphicon glyphicon-file"+'"'+"></i>', "+
				"\nenctype: 'multipart/form-data',"+
				'\npreviewFileIconSettings: {'+
				"\n     'docx': '<i class="+'"'+"glyphicon glyphicon-file"+'"'+"></i>',"+
				"\n     'xlsx': '<i class="+'"'+"glyphicon glyphicon-file"+'"'+"></i>',"+
				"\n     'pptx': '<i class="+'"'+"glyphicon glyphicon-file"+'"'+"></i>',"+
				"\n     'jpg': '<i class="+'"'+"glyphicon glyphicon-picture"+'"'+"></i>',"+
				"\n     'pdf': '<i class="+'"'+"glyphicon glyphicon-file"+'"'+"></i>',"+
				"\n     'zip': '<i class="+'"'+"glyphicon glyphicon-file"+'"'+"></i>'"+
				'\n  },'+
				'\n uploadExtraData:{//本插件能够想你的服务器发送额外的数据。这个能够通过设置uploadExtraData来完成'+
				'\n    	/*id:"kv-1"*/'+
				'\n  }, '+
				'\n slugCallback: function(filename) {//这是文件名替换'+
				'\n     return filename.replace("(", "_").replace("]", "_");'+
				'\n  } '+
				'\n});'+
		    
				'\n//这是提交完成后的回调函数'+  
				'\n$("#f_'+as.get('a_data_name')+'").on("fileuploaded", function(event, data, previewId, index) {'+
				'\n});';
			
			if(!editor_findAll('function init_fileInput(){')){
				editor_insert_up(c7,'function init_datagrid() {');
				editor_insert_up('init_fileInput();','init_datagrid();');
			}
			editor_insert_down(c8,'function init_fileInput(){');
			
		}else if(as.containsKey('c_type') && as.get('c_type')==='multiselect'){//检查是否有下拉框
			//TODO 1.0各种事件 
			
			if(editor_findAll('init_multiselect();')){
				//TODO
			}else{
				editor_insert_down('init_multiselect();','$(document).ready(function() {');
				var c3 =
					'\nfunction init_multiselect(){'+
					'\n}';
				editor_insert_up(c3,'function init_datagrid() {');
			}
			
			
			if(as.containsKey('onChange')
					||as.containsKey('onInitialized')
					||as.containsKey('onDropdownHide')
					||as.containsKey('onDropdownShown')
					||as.containsKey('onDropdownHidden')
					||as.containsKey('onSelectAll')){
				var c = 
					"\n$('#"+as.get('id')+"').multiselect({"+
					"\n});"
					
				editor_insert_down(c,'function init_multiselect(){');
			}
			
			if(as.containsKey('onChange')){
	           var c = 
				'\nonChange: function(option, checked) {'+
				'\n//更改选项时函数被触发' +
	            '\n},';
				editor_insert_down(c,"\n$('#"+as.get('id')+"').multiselect({");
			}
			
			if(as.containsKey('onInitialized')){
				var c = 
					'\nonInitialized: function(option, checked) {'+
					'\n//初始化后出发' +
					'\n},';
				editor_insert_down(c,"\n$('#"+as.get('id')+"').multiselect({");
			}
			
			if(as.containsKey('onDropdownShow')){
				var c = 
					'\nonDropdownShow: function(option, checked) {'+
					'\n//当列表下拉列表打开前，触发此函数' +
					'\n},';
				editor_insert_down(c,"\n$('#"+as.get('id')+"').multiselect({");
			}
			
			if(as.containsKey('onDropdownHide')){
				var c = 
					'\nonDropdownHide: function(option, checked) {'+
					'\n//当下拉列表关闭前，触发此函数' +
					'\n},';
				editor_insert_down(c,"\n$('#"+as.get('id')+"').multiselect({");
			}
			
			if(as.containsKey('onDropdownShown')){
				var c = 
					'\nonDropdownShown: function(option, checked) {'+
					'\n//当下拉列表打开后，触发此函数' +
					'\n},';
				editor_insert_down(c,"\n$('#"+as.get('id')+"').multiselect({");
			}
			
			if(as.containsKey('onDropdownHidden')){
				var c = 
					'\nonDropdownHidden: function(option, checked) {'+
					'\n//当下拉列表关闭后，触发此函数' +
					'\n},';
				editor_insert_down(c,"\n$('#"+as.get('id')+"').multiselect({");
			}
			
			if(as.containsKey('onSelectAll')){
				var c = 
					'\nonSelectAll: function(option, checked) {'+
					'\n//全选后' +
					'\n},';
				editor_insert_down(c,"\n$('#"+as.get('id')+"').multiselect({");
			}
			
			//TODO 2.0url 获取值，直接通过标签设置了data-url获取
//			if(as.containsKey('url')){
//				var c = 
//					"\n$.ajax({"+
//					"\n      url : "+as.get('url')+","+
//					"\n      type : 'POST',"+
//					"\n      dataType : 'json',"+
//					"\n      success : function(data) {"+
//					"\n          if (data.success) {"+
//					"\n               $('#"+as.get('id')+"').multiselect('dataprovider', data);"+     
//					"\n          }"+
//					"\n      }"+
//					"\n  });";
//					editor_insert_down(c,'function init_multiselect(){');
//			}
			
		}else if(as.containsKey('c_type') && as.get('c_type')==='check'){
			if(as.containsKey('notEmpty') && 'true'===as.get('notEmpty')){
				var c9 =
					'\n'+as.get('a_data_name')+': {'+
					'\n	validators: {'+
					'\n notEmpty: {'+
                        '\nmessage: "内容不为空"'+
                    '\n}'+
					'\n	}'+
					'\n},';
				editor_insert_down(c9,'fields: {');
			}
		}else if(as.containsKey('c_type') && as.get('c_type')==='radio'){
			if(as.containsKey('notEmpty') && 'true'===as.get('notEmpty')){
				var c9 =
					'\n'+as.get('a_data_name')+': {'+
					'\n	validators: {'+
					'\n notEmpty: {'+
                        '\nmessage: "内容不为空"'+
                    '\n}'+
					'\n	}'+
					'\n},';
				editor_insert_down(c9,'fields: {');
			}
		}
		
		//判断组件是否定义了校验
		if(as.containsKey('c_validator')){//文本框 密码框 
			var c9 =
				'\n'+as.get('a_data_name')+': {'+
				'\n	validators: {'+
				'\n	}'+
				'\n},';
			editor_insert_down(c9,'fields: {');
				
			var vs = as.get('c_validator');
			
			//不为空
			if(vs.containsKey('notEmpty')){
				var c10= 
					'\nnotEmpty: {'+
					'\n	message: "内容不为空"'+
					'\n},';
				editor_insert_down(c10,as.get('a_data_name')+': {',1,10);
			}
			//整形
			if(vs.containsKey('integer')){
				var c11= 
					'\ninteger: {'+
					'\n	message: "请输入整形数字"'+
					'\n},';
				editor_insert_down(c11,as.get('a_data_name')+': {',1,10);
			}
			//金额amount
			if(vs.containsKey('amount')){
				var c13= 
					'\nregexp: {'+
					'\nregexp: /^(?!0+(?:\\.0+)?$)(?:[1-9]\\d*|0)(?:\\.\\d{1,2})?$/,'+
					'\n	message: "金额格式有误"'+
					'\n},';
				editor_insert_down(c13,as.get('a_data_name')+': {',1,10);
			}
			
			//身份证card
			if(vs.containsKey('card')){
				var c13= 
					'\nregexp: {'+
					'\nregexp: /^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$/,'+
					'\n	message: "身份证格式有误"'+
					'\n},';
				editor_insert_down(c13,as.get('a_data_name')+': {',1,10);
			}
			//手机号tel
			if(vs.containsKey('tel')){
				var c14= 
					'\nregexp: {'+
					'\nregexp: /^1[34578]\\d{9}$/,'+
					'\n	message: "手机号格式有误"'+
					'\n},';
				editor_insert_down(c14,as.get('a_data_name')+': {',1,10);
			}
			
			//邮箱
			if(vs.containsKey('mail')){
				var c12= 
					'\nemailAddress: {'+
					'\n	message: "邮箱格式不正确"'+
					'\n},';
				editor_insert_down(c12,as.get('a_data_name')+': {',1,10);
			}
			//length
			if(vs.containsKey('length_min')&&vs.containsKey('length_max')){
				var c15= 
					'\n stringLength: {'+
					'\n	 min:'+vs.get('length_min')+ ','+
					'\n  max:'+vs.get('length_max')+ ','+
					'\n  message:"输入必须大于'+vs.get('length_min')+'且小于'+vs.get('length_max')+'个字节长度"'+
					'\n },';
				editor_insert_down(c15,as.get('a_data_name')+': {',1,10);
			}
			//choile
			if(vs.containsKey('choile_min')&&vs.containsKey('choile_max')){
				var c16= 
					'\n choice: {'+
					'\n	 min:'+vs.get('choile_min')+ ','+
					'\n  max:'+vs.get('choile_max')+ ','+
					'\n  message:"请选择'+vs.get('choile_min')+'-'+vs.get('choile_max')+'范围内容"'+
					'\n },';
				editor_insert_down(c16,as.get('a_data_name')+': {',1,10);
			}
			//regexp
			if(vs.containsKey('regexp_regexp')&&vs.containsKey('regexp_message')){
				var c17= 
					'\n regexp: {'+
					'\n	 regexp:'+vs.get('regexp_regexp')+ ','+
					'\n  message:"'+vs.get('regexp_message')+'"'+
					'\n },';
				editor_insert_down(c17,as.get('a_data_name')+': {',1,10);
			}
			//different
			if(vs.containsKey('different_field')&&vs.containsKey('different_message')){
				var c18= 
					'\n different: {'+
					'\n	 field:"'+vs.get('different_field')+ '",'+
					'\n  message:"'+vs.get('different_message')+'"'+
					'\n },';
				editor_insert_down(c18,as.get('a_data_name')+': {',1,10);
			}
			//date
			if(vs.containsKey('date_formate')&&vs.containsKey('date_message')){
				var c19= 
					'\n date: {'+
					'\n	 format:"'+vs.get('date_formate')+ '",'+
					'\n  message:"'+vs.get('date_message')+'"'+
					'\n },';
				editor_insert_down(c19,as.get('a_data_name')+': {',1,10);
			}
		}
	}
}

function handler_po(){
	var $table = $query_data.trans.table;
	var $interfaces = $table.interface;
	var $package = $table.package;
	var Name = $table.name;
	var $controllerPath = '';
	var $queryClass = undefined;
	var $pageName = window.parent.savePath.substring(window.parent.savePath.lastIndexOf('/')+1,window.parent.savePath.indexOf('.jsp'));
	console.info('window.parent.savePath='+window.parent.savePath);
	var sysDate = new Date();
	
	editorPo.setValue('');//首先清空
	editorPo.navigateTo(0, 0);
	
	var ipos = editorPo.getCursorPosition();
	editorPo.navigateTo(ipos.row, ipos.column);
	
	for(var i=0;i<$interfaces.length;i++){
		var $interface = $interfaces[i];
		var $reqClassName = Name+ucfirst($interface.id)+'_REQ';
		var $resClassName = Name+ucfirst($interface.id)+'_RES';
		var $reqClassListName = '';
		var $resClassListName = '';
		var $getSetFunc = '';
		//请求报文体PO类
		editorPo.insert('/**PO START**/\n');
		
		var $desc = '/**\n'+
		 '* 系统名称: 金融交易云管理平台\n'+
		 '* 模块名称: 报文格式\n'+
		 '* 功能描述: '+$interface.name+'请求报文体\n'+
		 '* 类 名 称  : '+$reqClassName+'.java\n'+
		 '* 软件版权: 北京先进数通信息技术股份公司\n'+
		 '* 开发人员: chenyl <br>\n'+
		 '* 开发时间: '+sysDate.getFullYear()+'年'+(sysDate.getMonth()+1)+'月'+sysDate.getDate()+'日 '+sysDate.getHours()+':'+sysDate.getMinutes()+':'+sysDate.getSeconds()+'<br>\n'+
		 '* 系统版本: V1.0.0<br>\n'+
		 '** 修改记录:\n'+
		 '* 修改日期                            修改人员          修改说明 <br>\n'+
		 '* ========     ======  ============================================\n'+
		 '* \n'+
		 '* ========     ======  ============================================\n'+
		 '*/\n';

		editorPo.insert($desc);
		editorPo.insert('package '+$package+'.entity; \n\n');
		
		if($interface.request.list){
			/*存在循环体*/
			editorPo.insert('import java.util.ArrayList; \n\n');
		}
		
		editorPo.insert('public class '+$reqClassName+' { \n');
		//临时保存get/set方法
		$getSetFunc = '';
		//属性定义
		if($interface.request.element){
			for(var j=0;j<$interface.request.element.length;j++){
				var $ele = $interface.request.element[j];
				editorPo.insert('	/*'+$ele.name+'*/ \n');
				editorPo.insert('	private '+$ele.data_type+' '+$ele.id+'; \n');
				
				$getSetFunc +=  
					'	public '+$ele.data_type+' get'+ucfirst($ele.id)+'() { \n'+
					'		return '+$ele.id+'; \n'+
					'	} \n'+
					'	public void set'+ucfirst($ele.id)+'('+$ele.data_type+' '+$ele.id+') { \n'+
					'		this.'+$ele.id+' = '+$ele.id+'; \n'+
					'	} \n';
			}
		}
		//存在循环属性
		if($interface.request.list){
			var $list = $interface.request.list;
			$reqClassListName = Name+ucfirst($interface.id)+'_REQ_'+$list.id;
			editorPo.insert('	/*'+$list.name+'*/ \n');
			editorPo.insert('	private ArrayList<'+$reqClassListName+'> '+$list.id+' = new ArrayList<'+$reqClassListName+'>(); \n');
			$getSetFunc +=  
				'	public ArrayList<'+$reqClassListName+'> get'+ucfirst($list.id)+'() { \n'+
				'		return '+$list.id+'; \n'+
				'	} \n'+
				'	public void set'+ucfirst($list.id)+'(ArrayList<'+$reqClassListName+'> '+$list.id+') { \n'+
				'		this.'+$list.id+' = '+$list.id+'; \n'+
				'	} \n';
		}
		//写入无参构造方法
		editorPo.insert('	public '+$reqClassName+'(){ }\n');
		//写入get/set方法
		editorPo.insert( $getSetFunc+' \n');		
		editorPo.insert('} \n');		
		editorPo.insert('/**PO END**/\n\n');
		
		//请求报文体循环体PO类
		if($interface.request.list){
			var $list = $interface.request.list;
			$reqClassListName = Name+ucfirst($interface.id)+'_REQ_'+$list.id;
			
			editorPo.insert('/**PO START**/\n');
			
			var $desc = '/**\n'+
			 '* 系统名称: 金融交易云管理平台\n'+
			 '* 模块名称: 报文格式\n'+
			 '* 功能描述: '+$list.name+'报文体\n'+
			 '* 类 名 称  : '+$reqClassListName+'.java\n'+
			 '* 软件版权: 北京先进数通信息技术股份公司\n'+
			 '* 开发人员: chenyl <br>\n'+
			 '* 开发时间: '+sysDate.getFullYear()+'年'+(sysDate.getMonth()+1)+'月'+sysDate.getDate()+'日 '+sysDate.getHours()+':'+sysDate.getMinutes()+':'+sysDate.getSeconds()+'<br>\n'+
			 '* 系统版本: V1.0.0<br>\n'+
			 '** 修改记录:\n'+
			 '* 修改日期                            修改人员          修改说明 <br>\n'+
			 '* ========     ======  ============================================\n'+
			 '* \n'+
			 '* ========     ======  ============================================\n'+
			 '*/\n';

			editorPo.insert($desc);
			editorPo.insert('package '+$package+'.entity; \n\n');
			editorPo.insert('public class '+$reqClassListName+' { \n');
			
			//临时保存get/set方法
			$getSetFunc = '';
			//属性定义
			if($list.element){
				for(var j=0;j<$list.element.length;j++){
					var $ele = $list.element[j];
					editorPo.insert('	/*'+$ele.name+'*/ \n');
					editorPo.insert('	private '+$ele.data_type+' '+$ele.id+'; \n');
					
					$getSetFunc +=  
						'	public '+$ele.data_type+' get'+ucfirst($ele.id)+'() { \n'+
						'		return '+$ele.id+'; \n'+
						'	} \n'+
						'	public void set'+ucfirst($ele.id)+'('+$ele.data_type+' '+$ele.id+') { \n'+
						'		this.'+$ele.id+' = '+$ele.id+'; \n'+
						'	} \n';
				}
			}
			//写入无参构造方法
			editorPo.insert('	public '+$reqClassListName+'(){ }\n');
			//写入get/set方法
			editorPo.insert( $getSetFunc+' \n');		
			editorPo.insert('} \n');		
			editorPo.insert('/**PO END**/\n\n');
		}

		//响应报文体PO类
		editorPo.insert('/**PO START**/\n');
		
		var $desc = '/**\n'+
		 '* 系统名称: 金融交易云管理平台\n'+
		 '* 模块名称: 报文格式\n'+
		 '* 功能描述: '+$interface.name+'响应报文体\n'+
		 '* 类 名 称  : '+$resClassName+'.java\n'+
		 '* 软件版权: 北京先进数通信息技术股份公司\n'+
		 '* 开发人员: chenyl <br>\n'+
		 '* 开发时间: '+sysDate.getFullYear()+'年'+(sysDate.getMonth()+1)+'月'+sysDate.getDate()+'日 '+sysDate.getHours()+':'+sysDate.getMinutes()+':'+sysDate.getSeconds()+'<br>\n'+
		 '* 系统版本: V1.0.0<br>\n'+
		 '** 修改记录:\n'+
		 '* 修改日期                            修改人员          修改说明 <br>\n'+
		 '* ========     ======  ============================================\n'+
		 '* \n'+
		 '* ========     ======  ============================================\n'+
		 '*/\n';

		editorPo.insert($desc);
		editorPo.insert('package '+$package+'.entity; \n\n');
		
		if($interface.response.list){
			/*存在循环体*/
			editorPo.insert('import java.util.ArrayList; \n\n');
		}
		
		editorPo.insert('public class '+$resClassName+' { \n');
		//临时保存get/set方法
		$getSetFunc = '';
		//属性定义
		if($interface.response.element){
			for(var j=0;j<$interface.response.element.length;j++){
				var $ele = $interface.response.element[j];
				editorPo.insert('	/*'+$ele.name+'*/ \n');
				editorPo.insert('	private '+$ele.data_type+' '+$ele.id+'; \n');
				
				$getSetFunc +=  
					'	public '+$ele.data_type+' get'+ucfirst($ele.id)+'() { \n'+
					'		return '+$ele.id+'; \n'+
					'	} \n'+
					'	public void set'+ucfirst($ele.id)+'('+$ele.data_type+' '+$ele.id+') { \n'+
					'		this.'+$ele.id+' = '+$ele.id+'; \n'+
					'	} \n';
			}
		}		
		//存在循环属性
		if($interface.response.list){
			var $list = $interface.response.list;
			$resClassListName = Name+ucfirst($interface.id)+'_RES_'+$list.id;
			editorPo.insert('	/*'+$list.name+'*/ \n');
			editorPo.insert('	private ArrayList<'+$resClassListName+'> '+$list.id+' = new ArrayList<'+$resClassListName+'>(); \n');
			$getSetFunc +=  
				'	public ArrayList<'+$resClassListName+'> get'+ucfirst($list.id)+'() { \n'+
				'		return '+$list.id+'; \n'+
				'	} \n'+
				'	public void set'+ucfirst($list.id)+'(ArrayList<'+$resClassListName+'> '+$list.id+') { \n'+
				'		this.'+$list.id+' = '+$list.id+'; \n'+
				'	} \n';
		}
		//写入无参构造方法
		editorPo.insert('	public '+$resClassName+'(){ }\n');
		//写入get/set方法
		editorPo.insert( $getSetFunc+' \n');		
		editorPo.insert('} \n');		
		editorPo.insert('/**PO END**/\n\n');
		
		//请求报文体循环体PO类
		if($interface.response.list){
			var $list = $interface.response.list;
			$resClassListName = Name+ucfirst($interface.id)+'_RES_'+$list.id;
			
			editorPo.insert('/**PO START**/\n');
			
			var $desc = '/**\n'+
			 '* 系统名称: 金融交易云管理平台\n'+
			 '* 模块名称: 报文格式\n'+
			 '* 功能描述: '+$list.name+'报文体\n'+
			 '* 类 名 称  : '+$resClassListName+'.java\n'+
			 '* 软件版权: 北京先进数通信息技术股份公司\n'+
			 '* 开发人员: chenyl <br>\n'+
			 '* 开发时间: '+sysDate.getFullYear()+'年'+(sysDate.getMonth()+1)+'月'+sysDate.getDate()+'日 '+sysDate.getHours()+':'+sysDate.getMinutes()+':'+sysDate.getSeconds()+'<br>\n'+
			 '* 系统版本: V1.0.0<br>\n'+
			 '** 修改记录:\n'+
			 '* 修改日期                            修改人员          修改说明 <br>\n'+
			 '* ========     ======  ============================================\n'+
			 '* \n'+
			 '* ========     ======  ============================================\n'+
			 '*/\n';

			editorPo.insert($desc);
			editorPo.insert('package '+$package+'.entity; \n\n');
			editorPo.insert('public class '+$resClassListName+' { \n');
			
			//临时保存get/set方法
			$getSetFunc = '';
			//属性定义
			if($list.element){
				for(var j=0;j<$list.element.length;j++){
					var $ele = $list.element[j];
					editorPo.insert('	/*'+$ele.name+'*/ \n');
					editorPo.insert('	private '+$ele.data_type+' '+$ele.id+'; \n');
					
					$getSetFunc +=  
						'	public '+$ele.data_type+' get'+ucfirst($ele.id)+'() { \n'+
						'		return '+$ele.id+'; \n'+
						'	} \n'+
						'	public void set'+ucfirst($ele.id)+'('+$ele.data_type+' '+$ele.id+') { \n'+
						'		this.'+$ele.id+' = '+$ele.id+'; \n'+
						'	} \n';
				}
			}
			//写入无参构造方法
			editorPo.insert('	public '+$resClassListName+'(){ }\n');
			//写入get/set方法
			editorPo.insert( $getSetFunc+' \n');		
			editorPo.insert('} \n');		
			editorPo.insert('/**PO END**/\n\n');
		}
	}
	
}

function handler_action(){
	var $table = $query_data.trans.table;
	var $interfaces = $table.interface;
	var $package = $table.package;
	var Name = $table.name;
	var $controllerPath = '';
	var $queryClass = undefined;
	var $pageName = window.parent.savePath.substring(window.parent.savePath.lastIndexOf('/')+1,window.parent.savePath.indexOf('.jsp'));
	console.info('window.parent.savePath='+window.parent.savePath);
	var sysDate = new Date();
	var $desc = '/**\n'+
				 '* 系统名称: 金融交易云管理平台\n'+
				 '* 模块名称: '+$table.desc+'模块\n'+
				 '* 功能描述: '+$table.desc+'\n'+
				 '* 类 名 称  : '+Name+'Controller.java\n'+
				 '* 软件版权: 北京先进数通信息技术股份公司\n'+
				 '* 开发人员: chenyl <br>\n'+
				 '* 开发时间: '+sysDate.getFullYear()+'年'+(sysDate.getMonth()+1)+'月'+sysDate.getDate()+'日 '+sysDate.getHours()+':'+sysDate.getMinutes()+':'+sysDate.getSeconds()+'<br>\n'+
				 '* 系统版本: V1.0.0<br>\n'+
				 '** 修改记录:\n'+
				 '* 修改日期                            修改人员          修改说明 <br>\n'+
				 '* ========     ======  ============================================\n'+
				 '* \n'+
				 '* ========     ======  ============================================\n'+
				 '*/\n';
	
	editorAction.setValue('');//首先清空
	editorAction.navigateTo(0, 0);
	
	var ipos = editorAction.getCursorPosition();
	editorAction.navigateTo(ipos.row, ipos.column);
	editorAction.insert($desc);
	editorAction.insert('package '+$package+'.web;\n\n');
	/*引入用到的包类*/
	editorAction.insert('import java.util.ArrayList;\n');	
	editorAction.insert('import javax.servlet.http.HttpServletRequest;\n');
	editorAction.insert('import javax.servlet.http.HttpServletResponse;\n');
	editorAction.insert('import org.apache.shiro.authz.annotation.RequiresPermissions;\n');
	editorAction.insert('import org.springframework.stereotype.Controller;\n');
	editorAction.insert('import org.springframework.ui.Model;\n');
	editorAction.insert('import org.springframework.web.bind.annotation.RequestMapping;\n');
	editorAction.insert('import org.springframework.web.bind.annotation.ResponseBody;\n');
	editorAction.insert('import com.adtec.framework.common.share.dataset.DatasetService;\n');
	editorAction.insert('import com.adtec.framework.interfaces.share.dataset.DatasetColumnType;\n');
	editorAction.insert('import com.adtec.framework.interfaces.share.dataset.IDataset;\n');
	editorAction.insert('import com.adtec.httpjson.HttpJsonFactory;\n');
	editorAction.insert('import com.adtec.sys.common.exception.BaseException;\n');
	editorAction.insert('import com.adtec.sys.common.exception.SysErr;\n');
	editorAction.insert('import com.adtec.sys.common.web.BaseController;\n');
	editorAction.insert('import com.adtec.sys.constant.IErrMsg;\n');
	editorAction.insert('import com.adtec.sys.modules.sys.utils.DictUtils;\n');
	editorAction.insert('import com.adtec.framework.common.util.DataUtil;\n');
	editorAction.insert('import com.adtec.framework.common.util.ParamUtil;\n');
	/*报文头对象*/
	editorAction.insert('import com.adtec.httpjson.bean.pub.MBC_RES;\n');
	editorAction.insert('import com.adtec.httpjson.bean.pub.MBC_RES_APP_HEAD;\n');
	editorAction.insert('import com.adtec.httpjson.bean.pub.MBC_RES_SYS_HEAD;\n');
	editorAction.insert('import com.adtec.httpjson.bean.pub.MBC_RES_SYS_HEAD_RET;\n');
	/*报文体对象*/
	for(var i=0;i<$interfaces.length;i++){
		var $interface = $interfaces[i];
		editorAction.insert('import '+$package+'.entity.'+Name+ucfirst($interface.id)+'_REQ;\n');
		editorAction.insert('import '+$package+'.entity.'+Name+ucfirst($interface.id)+'_RES;\n');
		if($interface.request.list){
			/*存在循环体*/
			editorAction.insert('import '+$package+'.entity.'+Name+ucfirst($interface.id)+'_REQ_'+$interface.request.list.id+';\n');
		}
		if($interface.response.list){
			/*存在循环体*/
			editorAction.insert('import '+$package+'.entity.'+Name+ucfirst($interface.id)+'_RES_'+$interface.response.list.id+';\n');
		}
		if(i==0){
			//设置拦截器的拦截路径
			$controllerPath = $interface.url.substring(0,$interface.url.lastIndexOf('/'));
		}
		if('query'===$interface.id){
			//列表查询类作为controller的model类
			$queryClass = Name+ucfirst($interface.id);
		}
	}
	
	editorAction.insert('\n\n');
	editorAction.insert('@Controller\n');
	editorAction.insert('@RequestMapping(value = "${adminPath}'+$controllerPath+'")\n');
	editorAction.insert('public class '+Name+'Controller extends BaseController {\n\n');
	//初始化页面方法
	editorAction.insert('	@RequiresPermissions("user")\n');
	editorAction.insert('	@RequestMapping(value ={"index", ""})\n');
	editorAction.insert('	public String index('+$queryClass+'_REQ req, HttpServletRequest request, HttpServletResponse response, Model model) {\n');
	editorAction.insert('		model.addAttribute("'+$pageName+'", req);\n');
	editorAction.insert('		String path = ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1]+"'+window.parent.savePath+'".replace(ParamUtil.getConfig("web.view.suffix"),"");\n');
	editorAction.insert('		return path;\n');
	editorAction.insert('	}\n');
	
	for(var i=0;i<$interfaces.length;i++){
		var $interface = $interfaces[i];
		var $requestElements = $interface.request.element;
		var $urlMapper = $interface.url.substring($interface.url.lastIndexOf('/')+1, $interface.url.length);
		if('query'===$interface.id){
			//列表查询方法
			editorAction.insert('	/**\n');
			editorAction.insert('	* 列表查询\n');
			editorAction.insert('	* @param request\n');
			editorAction.insert('	* @param response\n');
			editorAction.insert('	*/\n');
			editorAction.insert('	@ResponseBody\n');
			editorAction.insert('	@RequiresPermissions("user")\n');
			editorAction.insert('	@RequestMapping(value="'+$urlMapper+'")\n');
			editorAction.insert('	public void query(HttpServletRequest request, HttpServletResponse response){\n');
			editorAction.insert('		IDataset reqDs = DatasetService.getInstace().getDataset(request);\n');
			editorAction.insert('		'+Name+ucfirst($interface.id)+'_REQ req = DatasetService.getInstace().getMBCObject(reqDs, '+Name+ucfirst($interface.id)+'_REQ.class); //设置请求报文体\n');
			for(var j=0;j<$requestElements.length;j++){
				var $ele = $requestElements[j];
				var $dataType = $ele.data_type;
				var $eleId = $ele.id;
				var $eleName = $ele.name;
				//设置常量
				if($ele.isconstant==='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+$ele.default_value+'; /* 常量：'+$eleName+',默认值：'+$ele.default_value+' */ \n');
					editorAction.insert('		req.set'+ucfirst($eleId)+'('+$eleId+'); \n');
				}
				//检查必输项
				if(undefined==$ele.isconstant && $ele.required=='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+'reqDs.getString("'+$eleId+'"); \n');
					editorAction.insert('		if(DataUtil.isNullStr('+$eleId+')){ \n');
					editorAction.insert('			throw new BaseException("0003", "'+$eleName+'"); \n');
					editorAction.insert('		} \n');
				}	
			}
			//获取分页起始记录数和每页最大记录数
			editorAction.insert('		int start = reqDs.getInt("start"); \n');
			editorAction.insert('		int limit = reqDs.getInt("pageSize"); \n');
			editorAction.insert('		logger.debug("pageSize="+start+",pageNo="+limit); \n');

			editorAction.insert('		MBC_RES res = HttpJsonFactory.getInstance().callMBC("'+Name+ucfirst($interface.id)+'", req, '+Name+ucfirst($interface.id)+'_RES.class, start, limit); \n');
			editorAction.insert('		if(null==res){ \n');
			editorAction.insert('			logger.error("获取返回报文失败！"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"获取返回报文失败！"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		MBC_RES_SYS_HEAD resSysHead = res.getSYS_HEAD(); \n');
			editorAction.insert('		String txStat = resSysHead.getTX_STAT(); \n');
			editorAction.insert('		if(!"S".equals(txStat)){ \n');
			editorAction.insert('			MBC_RES_SYS_HEAD_RET txRet = resSysHead.getTX_RET().get(0); \n');
			editorAction.insert('			String errCode = txRet.getRET_CODE(); \n');
			editorAction.insert('			String errMsg = txRet.getRET_MSG(); \n');
			editorAction.insert('			logger.error("调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		MBC_RES_APP_HEAD resAppHead = res.getAPP_HEAD(); \n');
			editorAction.insert('		String TOT_NUM = resAppHead.getTOT_NUM(); \n');
			editorAction.insert('		if(DataUtil.isNullStr(TOT_NUM)){ \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"返回记录数为空字符串!"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		'+Name+ucfirst($interface.id)+'_RES resBody = ('+Name+ucfirst($interface.id)+'_RES)res.getBODY(); \n');
			editorAction.insert('		if(null==resBody){ \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"后台返回空记录!"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		ArrayList<'+Name+ucfirst($interface.id)+'_RES_'+$interface.response.list.id+'> list = resBody.get'+ucfirst($interface.response.list.id)+'(); \n');
			editorAction.insert('		//把结果集转换成DataSet并返回前台显示 \n');
			editorAction.insert('		IDataset resDs = DatasetService.getInstace().getDataset(list, '+Name+ucfirst($interface.id)+'_RES_'+$interface.response.list.id+'.class); \n');
			editorAction.insert('		resDs.setTotalCount(Integer.parseInt(TOT_NUM));	//设置总记录数 \n');
			editorAction.insert('		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "'+$interface.name+'成功！");\n');
			editorAction.insert('	} \n');	
			
		}
		
		if('detail'===$interface.id){
			//明细查询交易
			editorAction.insert('	/**\n');
			editorAction.insert('	* 明细查询\n');
			editorAction.insert('	* @param request\n');
			editorAction.insert('	* @param response\n');
			editorAction.insert('	*/\n');
			editorAction.insert('	@ResponseBody\n');
			editorAction.insert('	@RequiresPermissions("user")\n');
			editorAction.insert('	@RequestMapping(value="'+$urlMapper+'")\n');
			editorAction.insert('	public void detail(HttpServletRequest request, HttpServletResponse response){\n');
			editorAction.insert('		IDataset reqDs = DatasetService.getInstace().getDataset(request);\n');
			editorAction.insert('		'+Name+ucfirst($interface.id)+'_REQ req = DatasetService.getInstace().getMBCObject(reqDs, '+Name+ucfirst($interface.id)+'_REQ.class); //设置请求报文体\n');
			for(var j=0;j<$requestElements.length;j++){
				var $ele = $requestElements[j];
				var $dataType = $ele.data_type;
				var $eleId = $ele.id;
				var $eleName = $ele.name;
				//设置常量
				if($ele.isconstant==='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+$ele.default_value+'; /* 常量：'+$eleName+',默认值：'+$ele.default_value+' */ \n');
					editorAction.insert('		req.set'+ucfirst($eleId)+'('+$eleId+'); \n');
				}
				//检查必输项
				if(undefined==$ele.isconstant && $ele.required=='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+'reqDs.getString("'+$eleId+'"); \n');
					editorAction.insert('		if(DataUtil.isNullStr('+$eleId+')){ \n');
					editorAction.insert('			throw new BaseException("0003", "'+$eleName+'"); \n');
					editorAction.insert('		} \n');
				}	
			}

			editorAction.insert('		MBC_RES res = HttpJsonFactory.getInstance().callMBC("'+Name+ucfirst($interface.id)+'", req, '+Name+ucfirst($interface.id)+'_RES.class); \n');
			editorAction.insert('		if(null==res){ \n');
			editorAction.insert('			logger.error("获取返回报文失败！"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"获取返回报文失败！"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		MBC_RES_SYS_HEAD resSysHead = res.getSYS_HEAD(); \n');
			editorAction.insert('		String txStat = resSysHead.getTX_STAT(); \n');
			editorAction.insert('		if(!"S".equals(txStat)){ \n');
			editorAction.insert('			MBC_RES_SYS_HEAD_RET txRet = resSysHead.getTX_RET().get(0); \n');
			editorAction.insert('			String errCode = txRet.getRET_CODE(); \n');
			editorAction.insert('			String errMsg = txRet.getRET_MSG(); \n');
			editorAction.insert('			logger.error("调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		'+Name+ucfirst($interface.id)+'_RES resBody = ('+Name+ucfirst($interface.id)+'_RES)res.getBODY(); \n');
			editorAction.insert('		if(null==resBody){ \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"后台返回空记录!"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		//把结果集转换成DataSet并返回前台显示 \n');
			editorAction.insert('		IDataset resDs = DatasetService.getInstace().getDataset(resBody, '+Name+ucfirst($interface.id)+'_RES'+'.class); \n');
			editorAction.insert('		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "'+$interface.name+'成功！");\n');
			editorAction.insert('	} \n');	
			
		}
		
		if('add'===$interface.id){
			//新增交易
			editorAction.insert('	/**\n');
			editorAction.insert('	* 新增交易\n');
			editorAction.insert('	* @param request\n');
			editorAction.insert('	* @param response\n');
			editorAction.insert('	*/\n');
			editorAction.insert('	@ResponseBody\n');
			editorAction.insert('	@RequiresPermissions("user")\n');
			editorAction.insert('	@RequestMapping(value="'+$urlMapper+'")\n');
			editorAction.insert('	public void add(HttpServletRequest request, HttpServletResponse response){\n');
			editorAction.insert('		IDataset reqDs = DatasetService.getInstace().getDataset(request);\n');
			editorAction.insert('		'+Name+ucfirst($interface.id)+'_REQ req = DatasetService.getInstace().getMBCObject(reqDs, '+Name+ucfirst($interface.id)+'_REQ.class); //设置请求报文体\n');
			for(var j=0;j<$requestElements.length;j++){
				var $ele = $requestElements[j];
				var $dataType = $ele.data_type;
				var $eleId = $ele.id;
				var $eleName = $ele.name;
				//设置常量
				if($ele.isconstant==='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+$ele.default_value+'; /* 常量：'+$eleName+',默认值：'+$ele.default_value+' */ \n');
					editorAction.insert('		req.set'+ucfirst($eleId)+'('+$eleId+'); \n');
				}
				//检查必输项
				if(undefined==$ele.isconstant && $ele.required=='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+'reqDs.getString("'+$eleId+'"); \n');
					editorAction.insert('		if(DataUtil.isNullStr('+$eleId+')){ \n');
					editorAction.insert('			throw new BaseException("0003", "'+$eleName+'"); \n');
					editorAction.insert('		} \n');
				}	
			}

			editorAction.insert('		MBC_RES res = HttpJsonFactory.getInstance().callMBC("'+Name+ucfirst($interface.id)+'", req, '+Name+ucfirst($interface.id)+'_RES.class); \n');
			editorAction.insert('		if(null==res){ \n');
			editorAction.insert('			logger.error("获取返回报文失败！"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"获取返回报文失败！"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		MBC_RES_SYS_HEAD resSysHead = res.getSYS_HEAD(); \n');
			editorAction.insert('		String txStat = resSysHead.getTX_STAT(); \n');
			editorAction.insert('		if(!"S".equals(txStat)){ \n');
			editorAction.insert('			MBC_RES_SYS_HEAD_RET txRet = resSysHead.getTX_RET().get(0); \n');
			editorAction.insert('			String errCode = txRet.getRET_CODE(); \n');
			editorAction.insert('			String errMsg = txRet.getRET_MSG(); \n');
			editorAction.insert('			logger.error("调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		//把结果集转换成DataSet并返回前台显示 \n');
			editorAction.insert('		IDataset resDs = DatasetService.getInstace().getDataset(); \n');
			if($interface.response.element || $interface.response.list){
				editorAction.insert('		'+Name+ucfirst($interface.id)+'_RES resBody = ('+Name+ucfirst($interface.id)+'_RES)res.getBODY(); \n');
				editorAction.insert('		if(null==resBody){ \n');
				editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"后台返回空记录!"); \n');
				editorAction.insert('		} \n');
				editorAction.insert('		resDs = DatasetService.getInstace().getDataset(resBody, '+Name+ucfirst($interface.id)+'_RES'+'.class); \n');
			}
			editorAction.insert('		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "'+$interface.name+'成功！");\n');
			editorAction.insert('	} \n');	
			
		}
		
		if('update'===$interface.id){
			//新增交易
			editorAction.insert('	/**\n');
			editorAction.insert('	* 修改交易\n');
			editorAction.insert('	* @param request\n');
			editorAction.insert('	* @param response\n');
			editorAction.insert('	*/\n');
			editorAction.insert('	@ResponseBody\n');
			editorAction.insert('	@RequiresPermissions("user")\n');
			editorAction.insert('	@RequestMapping(value="'+$urlMapper+'")\n');
			editorAction.insert('	public void update(HttpServletRequest request, HttpServletResponse response){\n');
			editorAction.insert('		IDataset reqDs = DatasetService.getInstace().getDataset(request);\n');
			editorAction.insert('		'+Name+ucfirst($interface.id)+'_REQ req = DatasetService.getInstace().getMBCObject(reqDs, '+Name+ucfirst($interface.id)+'_REQ.class); //设置请求报文体\n');
			for(var j=0;j<$requestElements.length;j++){
				var $ele = $requestElements[j];
				var $dataType = $ele.data_type;
				var $eleId = $ele.id;
				var $eleName = $ele.name;
				//设置常量
				if($ele.isconstant==='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+$ele.default_value+'; /* 常量：'+$eleName+',默认值：'+$ele.default_value+' */ \n');
					editorAction.insert('		req.set'+ucfirst($eleId)+'('+$eleId+'); \n');
				}
				//检查必输项
				if(undefined==$ele.isconstant && $ele.required=='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+'reqDs.getString("'+$eleId+'"); \n');
					editorAction.insert('		if(DataUtil.isNullStr('+$eleId+')){ \n');
					editorAction.insert('			throw new BaseException("0003", "'+$eleName+'"); \n');
					editorAction.insert('		} \n');
				}	
			}

			editorAction.insert('		MBC_RES res = HttpJsonFactory.getInstance().callMBC("'+Name+ucfirst($interface.id)+'", req, '+Name+ucfirst($interface.id)+'_RES.class); \n');
			editorAction.insert('		if(null==res){ \n');
			editorAction.insert('			logger.error("获取返回报文失败！"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"获取返回报文失败！"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		MBC_RES_SYS_HEAD resSysHead = res.getSYS_HEAD(); \n');
			editorAction.insert('		String txStat = resSysHead.getTX_STAT(); \n');
			editorAction.insert('		if(!"S".equals(txStat)){ \n');
			editorAction.insert('			MBC_RES_SYS_HEAD_RET txRet = resSysHead.getTX_RET().get(0); \n');
			editorAction.insert('			String errCode = txRet.getRET_CODE(); \n');
			editorAction.insert('			String errMsg = txRet.getRET_MSG(); \n');
			editorAction.insert('			logger.error("调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		//把结果集转换成DataSet并返回前台显示 \n');
			editorAction.insert('		IDataset resDs = DatasetService.getInstace().getDataset(); \n');
			if($interface.response.element || $interface.response.list){
				editorAction.insert('		'+Name+ucfirst($interface.id)+'_RES resBody = ('+Name+ucfirst($interface.id)+'_RES)res.getBODY(); \n');
				editorAction.insert('		if(null==resBody){ \n');
				editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"后台返回空记录!"); \n');
				editorAction.insert('		} \n');
				editorAction.insert('		resDs = DatasetService.getInstace().getDataset(resBody, '+Name+ucfirst($interface.id)+'_RES'+'.class); \n');
			}
			editorAction.insert('		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "'+$interface.name+'成功！");\n');
			editorAction.insert('	} \n');	
			
		}
		
		if('delete'===$interface.id){
			//新增交易
			editorAction.insert('	/**\n');
			editorAction.insert('	* 删除交易\n');
			editorAction.insert('	* @param request\n');
			editorAction.insert('	* @param response\n');
			editorAction.insert('	*/\n');
			editorAction.insert('	@ResponseBody\n');
			editorAction.insert('	@RequiresPermissions("user")\n');
			editorAction.insert('	@RequestMapping(value="'+$urlMapper+'")\n');
			editorAction.insert('	public void delete(HttpServletRequest request, HttpServletResponse response){\n');
			editorAction.insert('		IDataset reqDs = DatasetService.getInstace().getDataset(request);\n');
			editorAction.insert('		'+Name+ucfirst($interface.id)+'_REQ req = DatasetService.getInstace().getMBCObject(reqDs, '+Name+ucfirst($interface.id)+'_REQ.class); //设置请求报文体\n');
			for(var j=0;j<$requestElements.length;j++){
				var $ele = $requestElements[j];
				var $dataType = $ele.data_type;
				var $eleId = $ele.id;
				var $eleName = $ele.name;
				//设置常量
				if($ele.isconstant==='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+$ele.default_value+'; /* 常量：'+$eleName+',默认值：'+$ele.default_value+' */ \n');
					editorAction.insert('		req.set'+ucfirst($eleId)+'('+$eleId+'); \n');
				}
				//检查必输项
				if(undefined==$ele.isconstant && $ele.required=='true'){
					editorAction.insert('		'+ucfirst($dataType)+' '+$eleId+' = '+'reqDs.getString("'+$eleId+'"); \n');
					editorAction.insert('		if(DataUtil.isNullStr('+$eleId+')){ \n');
					editorAction.insert('			throw new BaseException("0003", "'+$eleName+'"); \n');
					editorAction.insert('		} \n');
				}	
			}

			editorAction.insert('		MBC_RES res = HttpJsonFactory.getInstance().callMBC("'+Name+ucfirst($interface.id)+'", req, '+Name+ucfirst($interface.id)+'_RES.class); \n');
			editorAction.insert('		if(null==res){ \n');
			editorAction.insert('			logger.error("获取返回报文失败！"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"获取返回报文失败！"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		MBC_RES_SYS_HEAD resSysHead = res.getSYS_HEAD(); \n');
			editorAction.insert('		String txStat = resSysHead.getTX_STAT(); \n');
			editorAction.insert('		if(!"S".equals(txStat)){ \n');
			editorAction.insert('			MBC_RES_SYS_HEAD_RET txRet = resSysHead.getTX_RET().get(0); \n');
			editorAction.insert('			String errCode = txRet.getRET_CODE(); \n');
			editorAction.insert('			String errMsg = txRet.getRET_MSG(); \n');
			editorAction.insert('			logger.error("调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"调用'+Name+ucfirst($interface.id)+'交易失败["+errCode+":"+errMsg+"]"); \n');
			editorAction.insert('		} \n');
			editorAction.insert('		//把结果集转换成DataSet并返回前台显示 \n');
			editorAction.insert('		IDataset resDs = DatasetService.getInstace().getDataset(); \n');
			if($interface.response.element || $interface.response.list){
				editorAction.insert('		'+Name+ucfirst($interface.id)+'_RES resBody = ('+Name+ucfirst($interface.id)+'_RES)res.getBODY(); \n');
				editorAction.insert('		if(null==resBody){ \n');
				editorAction.insert('			throw new BaseException(SysErr.E_MESSAGE,"后台返回空记录!"); \n');
				editorAction.insert('		} \n');
				editorAction.insert('		resDs = DatasetService.getInstace().getDataset(resBody, '+Name+ucfirst($interface.id)+'_RES'+'.class); \n');
			}
			editorAction.insert('		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "'+$interface.name+'成功！");\n');
			editorAction.insert('	} \n');	
			
		}
	}
	
	
	
	
		
	//2.0 获取formateJson
//	formateJsons();
//	if(!formateJson.containsKey('m_query')){//2.1 查询
//		return;
//	}else{
//		var ipos = editorAction.getCursorPosition();
//		editorAction.navigateTo(ipos.row, 4);
//		editorAction.insert('	public void query() throws Exception {\n');
//		
//		editorAction.insert('	}\n\n');
//		
//		var q = formateJson.get('m_query');
//	}
//	
//	for(var i=0;i<formButtonArrayList.size();i++){
//		var butName = formButtonArrayList.get(i);
//		var butId = "btnId_"+butName;
//		if(butName ==='add'){//2.2 新增
//			editorAction.insert('	public void add() throws Exception {\n');
//			
//			editorAction.insert('	}\n\n');
//		}else if(butName ==='remove'){//2.3 删除
//			editorAction.insert('	public void remove() throws Exception {\n');
//			
//			editorAction.insert('	}\n\n');
//		}else if(butName ==='update'){//2.4 修改
//			editorAction.insert('	public void update() throws Exception {\n');
//			
//			editorAction.insert('	}\n\n');
//		}
//	}
	//3.0
	//TODO
	editorAction.insert('}');
}

function getHtml(){
	formateJsons();
	
	$('#domsave').empty();
	
	//TODO
	var $queryForm = handler_query();
	
	
	
	var mm_query=$('<div>',{
		'ravo':"rainbow_fx_layout",
		'class':"row clearfix",
		'id':'toolbar'
	}).append($('<div>',{
		'class':"col-md-12 column"
	}).append($queryForm));
	
	handler_query_button($queryForm);
	var $datagrid = handler_datagrid();
	var mm_datagrid=$('<div>',{
		'ravo':"rainbow_fx_layout",
		'class':"row clearfix"
	}).append($('<div>',{
		'class':"col-md-12 column"
	}).append($datagrid));
	
	var $form = handler_form();
	var $form_button = handler_form_button();
	$form.append($form_button);
	
	var $tabContent1 = $('<div>',{
		'class':'tab-pane active',
		'id':'myMainTab_tab1'
	}).append(mm_query).append(mm_datagrid);
	
	var $tabContent2 = $('<div>',{
		'class':'tab-pane',
		'id':'myMainTab_tab2'
	}).append($form);
	
	var $tabModule = $('<div>',{
		'ravo':"rainbow_fx_layout",
		'class':"row clearfix"
	}).append($('<div>',{
		'class':'col-md-12 column'
	}).append($('<div>',{
		'class':'clearfix'
	})).append($('<div>',{
		'ravo':"rainbow_fx_layout_tab",
		'class':"tabbable",
		'id':'navigations'
		}).append($('<ul>',{
					'class':'nav nav-tabs',
					'data-toggle':'tabs',
					'id':'myMainTab'
			}).append($('<li>',{
						'class':'active'	
					}).append($('<a>',{
							'id':'myMainTab_li1',
							'href':'#myMainTab_tab1',
							'data-toggle':'tab',
							'contenteditable':'true',
							'aria-expanded':'true',
							'title':'信息查询列表'
					}).append('信息查询列表'))
			 ).append($('<li>',{
						'class':''	
					}).append($('<a>',{
						'id':'myMainTab_li2',
						'href':'#myMainTab_tab2',
						'data-toggle':'tab',
						'contenteditable':'true',
						'aria-expanded':'true',
						'title':'信息查询添加'
					}).append('信息查询添加'))
			)
		).append($('<div>',{
			'class':'tab-content'
			}).append($tabContent1).append($tabContent2)
		)
	)
	);
	
	var $container = $('<div>',{
		"class":"container-fluid"
	});

	$('#domsave').append($container.append($tabModule));
	var html = beautify($("#domsave").html());
	return html;
}




function htmlspecialchars(str){
	var str1 = '&quot;';
	var str0 = '&nbsp;';
	var str2 = '';
	var result = str.replace(eval("/"+str1+"/gi"),str2);
	result = result.replace(eval("/"+str0+"/gi"),' ');
	result=encodeURI(result);
	return result;
}

function beautify(source) {
    var js_source = source.replace(/^\s+/, '');
    var tabsize = 1;
    var tabchar = '';
    //tabsize = document.getElementById('tabsize').value;
    if (tabsize == 1) {
      tabchar = '\t';
    }
    if (js_source && js_source.charAt(0) === '<') {
      return  style_html(js_source, tabsize, tabchar, 80);
    } else {
      return js_beautify(js_source, tabsize, tabchar);
    }
  }

/*-------------------------------1.0 form-------------------------------*/

function handler_query_button($queryForm){
	var arr = queryButtonArrayList.toArray();
	
	var $col = $('<div>',{
		'ravo':'rainbow_fx_layout',
		'class':'row clearfix'
	});
	
	for(var j=0;j<(4-queryButtonArrayList.size());j++){
		$col.append($('<div>',{
			'class':'column col-md-3'
		}));
	}
	
	for(var i=0;i<queryButtonArrayList.size();i++){
		var butName = queryButtonArrayList.get(i);
		var butId = butName+'Btn';
		if(butName ==='query'){
			var $but = '<shiro:hasPermission name="anno">'+
			'<button id="'+butId+'" ravo="rainbow_fx" type="button" class="btn btn-info">查询</button>'
			+'</shiro:hasPermission>';
			$col.append($('<div>',{
				'class':'column col-md-3'
			}).append($but));
		}
		if(butName ==='delete'){
			var $but = '<shiro:hasPermission name="anno">'+
			'<button id="'+butId+'" ravo="rainbow_fx" type="button" class="btn btn-warning">删除</button>'
			+'</shiro:hasPermission>';
			$col.append($('<div>',{
				'class':'column col-md-3'
			}).append($but));
		}
		if(butName ==='update'){
			var $but = '<shiro:hasPermission name="anno">'+
			'<button id="'+butId+'" ravo="rainbow_fx" type="button" class="btn btn-warning">修改</button>'
			+'</shiro:hasPermission>';
			$col.append($('<div>',{
				'class':'column col-md-3'
			}).append($but));
		}
	}
	
	var $row = $('<div>',{
		'ravo':'rainbow_fx_layout',
		'class':'row clearfix'
	}).append($('<div>',{
		'class':'column col-md-7'
	})).append($('<div>',{
		'class':'column col-md-5'
	}).append($col));
	$queryForm.append($row);
}

function handler_form_button(){
	var arr = formButtonArrayList.toArray();
	
	var $col = $('<div>',{
		'ravo':'rainbow_fx_layout',
		'class':'row clearfix'
	});
	
	for(var i=0;i<formButtonArrayList.size();i++){
		var butName = formButtonArrayList.get(i);
		var butId = butName+'Btn';
		if(butName ==='save'){
			var $but = '<shiro:hasPermission name="anno">'+
			'<button id="'+butId+'" ravo="rainbow_fx" type="button" class="btn btn-info">保存</button>'
			+'</shiro:hasPermission>';
			$col.append($('<div>',{
				'class':'column col-md-3'
			}).append($but));
		}
	}
	
	//隐藏域，操作类型
	var $oprType = $('<input>',{
		"id":"oprType",
		"type":"hidden",
		"value":""
	});
	
	var $backBtn = '<shiro:hasPermission name="anno">'+
	'<button id="backBtn" ravo="rainbow_fx" type="button" class="btn btn-default">返回</button>'
	+'</shiro:hasPermission>';
	$col.append($('<div>',{
		'class':'column col-md-3'
	}).append($backBtn).append($oprType));
	
	for(var j=0;j<(3-formButtonArrayList.size());j++){
		$col.append($('<div>',{
			'class':'column col-md-3'
		}));
	}

	var $row = $('<div>',{
		'ravo':'rainbow_fx_layout',
		'class':'row clearfix'
	}).append($('<div>',{
		'class':'column col-md-7'
	})).append($('<div>',{
		'class':'column col-md-5'
	}).append($col));
	
	return $row;
}

function handler_form_horizontal(_form,key){
	
	var m_form_arrayList = formateJson.get(key);
	var idPre = '';
	var $formAttrMap = undefined;
	if('m_query'!==key){
		idPre = 'EDIT_';//表单组件id的前缀
		$formAttrMap = PrivateAttrFormMap;
	}else{
		$formAttrMap = PrivateAttrQueryMap;
	}
	
	/*排序beg*/
	var objectList2 = new Array();
	for(var it=m_form_arrayList.iterator();it.hasNext();){
		var as = it.next();//获取 field的属性
		objectList2.push(as);
	}
	objectList2.sort(function(a,b){
        return a.get('c_row')-b.get('c_row');
    });
	/*排序eng*/
	
	var memoryRowsMap = new Hashtable();
	for(var j=0;j<objectList2.length;j++){
		var as = objectList2[j];
		var row = as.get('c_row');
		if(memoryRowsMap.containsKey(row)){
			memoryRowsMap.get(row).add(as);
		}else{
			var _l = new ArrayList();
			_l.add(as);
			memoryRowsMap.add(row,_l);
		}
		//TODO
	}
	
	for(var it=memoryRowsMap.keys().iterator();it.hasNext();){
		var key = it.next();
		var row = memoryRowsMap.get(key);
		
		var _row = $('<div>',{
			"ravo":"rainbow_fx_layout",
			"class":"row clearfix"
		});
		_form.append(_row);
		
		//获取列数
		var col_num = row.size();
		
		for(var _it=row.iterator();_it.hasNext();){
			var as = _it.next();
			if($formAttrMap.get('colNum')<=col_num){
				var $col = $('<div>',{
					"class":"col-md-"+ parseInt(12/col_num)+" column"
				});
			}else{
				var $col = $('<div>',{
					"class":"col-md-"+ parseInt(12/$formAttrMap.get('colNum'))+" column"
				});
			}
			_row.append($col);
			
			/*---*/
			var _group = $('<div>',{
				"ravo":"rainbow_fx",
				"class":"form-group"
			});
			var $label =$('<label>',{
				 "for":"inputEmail3",
				 "class":"col-sm-"+$formAttrMap.get('leftOcc')+" control-label"
				}).text(as.get('c_label'));
			
			if(as.containsKey('c_must') && as.get('c_must')){
				$label.prepend($('<span>',{
					"style":"color: #a94442; font-size: 10px ;padding-right: 2px",
					"class":"glyphicon glyphicon-asterisk"
				}));
			}
			$col.append(_group.append($label));
			
			if((!as.containsKey('c_type')) || as.get('c_type')==='text'||as.get('c_type')==='password'){//文本框
				var _div = $('<div>',{
					"class":"col-sm-"+$formAttrMap.get('rightOcc')
				});
				_group.append(_div);
				
				var input = $('<input>',{
					'id':idPre+as.get('a_data_name'),
					'name':as.get('a_data_name'),
					'class':"form-control "+$formAttrMap.get('size'), 
					'type':as.get('c_type')
				});
				//设置input属性
				if(as.containsKey('c_readonly') && as.get('c_readonly')){
					input.attr('readonly','readonly');
				}
				if(as.containsKey('c_disable') && as.get('c_disable')){
					input.attr('disabled','disabled');
				}
				
				if(as.containsKey('t_placeholder') && as.get('t_placeholder')!==''){
					input.attr('placeholder',as.get('t_placeholder'));
				}
				
				if(as.containsKey('t_id') && as.get('t_id')!==''){
					input.attr('id',as.get('t_id'));
				}
				
				if(as.containsKey('t_size') && as.get('t_size')!==''){
					/*input.attr('id',as.get('t_id'));*/
					_group.addClass(as.get('t_size'));
				}
				
				_div.append(input);
			}else if(as.containsKey('c_type') && as.get('c_type')==='multiselect'){//下拉框
				//TODO
				var _div = $('<div>',{
					"class":"col-sm-"+$formAttrMap.get('rightOcc')
				});
				
				var $select = $('<select>',{
					'id':idPre+as.get('a_data_name'),
					"name":as.get('a_data_name'),
					"data-role":"multiselect"
				});
				
				/*if(as.containsKey('id') && as.get('id')!==''){
					$select.attr('id',as.get('t_placeholder'));
				}*/
				
				if(as.containsKey('content') && as.get('content')!==''){
					var content = as.get('content');
					var files = content.split(/\r?\n/);
					var $group = undefined;
					files.forEach(function(e){  
					    if(file!==''){
					    	var file = e.trim();
					    	var kns = file.split('&');
					    	if(file.indexOf("&") != -1){//file
					    		var $option = $('<option>',{
					    			"value":kns[1]
					    		}).text(kns[0]);
					    		if($group===undefined){
					    			$select.append($option);
					    		}else{
					    			$group.append($option);
					    		}
					    	}else{//group
					    		$group = $('<optgroup>',{
					    			'label':file,
					    			'class':"group-1"
					    		})
					    		$select.append($group);
					    	}
					    }
					})
				}
				if(as.containsKey('url') && as.get('url')!==''){
					$select.attr('data-url',as.get('url'));
				}
				
				if(as.containsKey('notEmpty') && as.get('notEmpty')!==''){
					$select.attr('required','required');
				}
				
				if(as.containsKey('multiple') && as.get('multiple')!==''){
					$select.attr('multiple','multiple');
				}
				
				if(as.containsKey('id') && as.get('id')!==''){
					$select.attr('id',as.get('id'));
				}
				
				if(as.containsKey('buttonWidth') && as.get('buttonWidth')!==''){
					$select.attr('data-button-width',as.get('buttonWidth'));
				}
				
				if(as.containsKey('dropUp') && as.get('dropUp')!==''){
					$select.attr('data-drop-up',true);
				}
				
				if(as.containsKey('dropRight') && as.get('dropRight')!==''){
					$select.attr('data-drop-right',true);
				}
				
				if(as.containsKey('dropRight') && as.get('dropRight')!==''){
					$select.attr('data-drop-right',true);
				}
				
				if(as.containsKey('enableClickableOptGroups') && as.get('enableClickableOptGroups')!==''){
					$select.attr('data-enable-clickable-opt-groups',true);
				}
				if(as.containsKey('enableCollapsibleOptGroups') && as.get('enableCollapsibleOptGroups')!==''){
					$select.attr('data-enable-collapsible-opt-groups',true);
				}
				
				if(as.containsKey('delimiterText') && as.get('delimiterText')!==''){
					$select.attr('data-delimiter-text',as.get('delimiterText'));
				}
				if(as.containsKey('includeSelectAllOption') && as.get('includeSelectAllOption')!==''){
					$select.attr('data-include-select-all-option',true);
				}
				if(as.containsKey('allSelectedText') && as.get('allSelectedText')!==''){
					$select.attr('data-all-selected-text',as.get('allSelectedText'));
				}
				
				if(as.containsKey('enableFiltering') && as.get('enableFiltering')!==''){
					$select.attr('data-enable-filtering',true);
				}
				if(as.containsKey('enableFullValueFiltering') && as.get('enableFullValueFiltering')!==''){
					$select.attr('data-enable-full-value-filtering',true);
				}
				
				if(as.containsKey('disableIfEmpty') && as.get('disableIfEmpty')!==''){
					$select.attr('data-disable-if-empty',true);
				}
				
				if(as.containsKey('disabledText') && as.get('disabledText')!==''){
					$select.attr('data-disabled-text',as.get('disabledText'));
				}
				if(as.containsKey('nonSelectedText') && as.get('nonSelectedText')!==''){
					$select.attr('data-non-selected-text',as.get('nonSelectedText'));
				}
				
				_div.append($select);
				_group.append(_div);
				//设置input属性BEG
				
				//设置input属性END
			}else if(as.containsKey('c_type') && as.get('c_type')==='textarea'){//多行文本框
				var _div = $('<div>',{
					"class":"col-sm-"+$formAttrMap.get('rightOcc')
				});
				_group.append(_div);
				
				var textarea = $('<textarea>',{
					'id':idPre+as.get('a_data_name'),
					'name':as.get('a_data_name'),
					'class':"form-control "+$formAttrMap.get('size')
				});
				
				//设置input属性BEG
				
				//END
				_div.append(textarea);
			}else if(as.containsKey('c_type') && as.get('c_type')==='check'){//多选框
				
				var _div = $('<div>',{
					"class":"col-sm-"+$formAttrMap.get('rightOcc')
				});
				_group.append(_div);
				
				if(as.containsKey('content') && as.get('content')!==''){
					var content = as.get('content');
					var files = content.split(/\r?\n/);
					var $group = undefined;
					var i=0;
					files.forEach(function(e){  
					    if(file!==''){
					    	var file = e.trim();
					    	var $checkbox = $('<div>',{
					    			'class':'checkbox checkbox-info'
					    		});
					    	var $label = $('<label>',{
					    		'for':as.get('a_data_name')+i
					    	}).text(file);
					    	
					    	var $input = $('<input>',{
					    		'id':idPre+as.get('a_data_name')+i,
					    		'name':as.get('a_data_name'),
					    		'type':'checkbox'
					    	});
					    	
					    	if(as.containsKey('inline') && 'true' ===as.get('inline')){
					    		$checkbox.addClass('checkbox-inline');
					    	}
					    	
					    	if(as.containsKey('circle') && 'true' ===as.get('circle')){
					    		$checkbox.addClass('checkbox-circle');
					    	}
					    	
					    	if(as.containsKey('colors')){
					    		$checkbox.removeClass('checkbox-info');
					    		$checkbox.addClass(as.get('colors'));
					    	}
					    	
					    	$checkbox.append($input).append($label);
					    	_div.append($checkbox);
					    	
					    	i=i+1;
					    }
					})
				}
			}else if(as.containsKey('c_type') && as.get('c_type')==='radio'){// 单选框
				var _div = $('<div>',{
					"class":"col-sm-"+$formAttrMap.get('rightOcc')
				});
				_group.append(_div);
				
				if(as.containsKey('content') && as.get('content')!==''){
					var content = as.get('content');
					var files = content.split(/\r?\n/);
					var $group = undefined;
					var i=0;
					files.forEach(function(e){  
					    if(file!==''){
					    	var file = e.trim();
					    	var $radio = $('<div>',{
					    			'class':'radio radio-info'
					    		});
					    	var $label = $('<label>',{
					    		'for':as.get('a_data_name')+i
					    	}).text(file);
					    	
					    	var $input = $('<input>',{
					    		'id':idPre+as.get('a_data_name')+i,
					    		'name':as.get('a_data_name'),
					    		'type':'radio'
					    	});
					    	
					    	if(as.containsKey('inline') && 'true' ===as.get('inline')){
					    		$radio.addClass('radio-inline');
					    	}
					    	
					    	/*if(as.containsKey('circle') && 'true' ===as.get('circle')){
					    		$radio.addClass('checkbox');
					    	}*/
					    	
					    	if(as.containsKey('colors')){
					    		$radio.removeClass('radio-info');
					    		$radio.addClass(as.get('colors'));
					    	}
					    	
					    	if(as.containsKey('c_disable') && as.get('c_disable')){
					    		$input.attr('disabled','disabled');
							}
					    	
					    	$radio.append($input).append($label);
					    	_div.append($radio);
					    	
					    	i=i+1;
					    }
					})
				}
				
			}else if(as.containsKey('c_type') && as.get('c_type')==='datatime'){//日期框
				var _div = $('<div>',{
					"class":"col-sm-"+$formAttrMap.get('rightOcc')
				});
				_group.append(_div);
				
				var $datetime = $('<div>',{
					"id":idPre+as.get('a_data_name'),
					"class":"input-group date form_datetime", 
					"data-toggle":"datatimepiker",
					"data-date-language":"zh-CN", 
					"data-date-weekstart":"0", 
					"data-date-autoclose":"true",
					"data-start-view":"2", 
					"data-date-today-btn":"linked", 
					"data-date-format":"yyyy-mm-dd HH:ii:ss",
					"data-date-today-highlight":"true",
					"data-link-field":"d_i_"+idPre+as.get('a_data_name'), 
					"data-picker-position":"bottom-left"
				}).append($('<input>',{
					"class":"form-control rainbow-select",
					"size":"16",
					"readonly":"readonly"
				})).append($('<span>',{
					"class":"input-group-addon"
				}).append($('<span>',{
					"class":"glyphicon glyphicon-remove"
				}))).append($('<span>',{
					"class":"input-group-addon"
				}).append($('<span>',{
					"class":"glyphicon glyphicon-th"
				})));
				
				var $input = $('<input>',{
					"id":"d_i_"+idPre+as.get('a_data_name'),
					"name":as.get('a_data_name'),
					"type":"hidden"
				});
				
				//设置datetime属性BEG
				
				//格式化
				if(as.containsKey('format') && as.get('format')!==''){
					$datetime.attr('data-date-format',as.get('format'));
				}
				//首显视图
				if(as.containsKey('startView') && as.get('startView')!==''){
					$datetime.attr('data-start-view',as.get('startView'));
				}
				//显示范围最小
				if(as.containsKey('minView') && as.get('minView')!==''){
					$datetime.attr('data-min-view',as.get('minView'));
				}
				
				//显示范围最大
				if(as.containsKey('maxView') && as.get('maxView')!==''){
					$datetime.attr('data-max-view',as.get('maxView'));
				}
				
				//日期选择器位置
				if(as.containsKey('pickerPosition') && as.get('pickerPosition')!==''){
					$datetime.attr('data-picker-position',as.get('pickerPosition'));
				}
				//可选开始日期
				if(as.containsKey('startDate') && as.get('startDate')!==''){
					$datetime.attr('data-date-startdate',as.get('startDate'));
				}
				//可选结束日期
				if(as.containsKey('endDate') && as.get('endDate')!==''){
					$datetime.attr('data-date-enddate',as.get('endDate'));
				}
				//分钟选择范围值
				if(as.containsKey('minuteStep') && as.get('minuteStep')!==''){
					$datetime.attr('data-minute-step',as.get('minuteStep'));
				}
				//每周开始日期
				if(as.containsKey('weekStart') && as.get('weekStart')!==''){
					$datetime.attr('data-date-weekstart',as.get('weekStart'));
				}
				//禁用每周的某天
				
				//选择后关闭日期选择器
				if(as.containsKey('autoclose') && as.get('autoclose')!==''){
					$datetime.attr('data-date-autoclose',as.get('autoclose'));
				}
				
				//显示今日按钮
				if(as.containsKey('todayBtn') && as.get('todayBtn')!==''){
					$datetime.attr('data-date-today-btn',as.get('todayBtn'));
				}
				//小时视图显示上、下午
				if(as.containsKey('showMeridian') && as.get('showMeridian')!==''){
					$datetime.attr('data-show-meridian',as.get('showMeridian'));
				}
				//不为空
				if(as.containsKey('notEmpty') && as.get('notEmpty')!==''){
					if('true'===as.get('notEmpty')){
						$input.attr('data-bv-notempty','true');
						$input.attr('data-bv-notempty-message','输入不为空!');
					}else{
						$input.removeAttr('data-bv-notempty');
						$input.removeAttr('data-bv-notempty-message');
					}
				}
				
				//END
				_div.append($datetime).append($input);
				
			}else if(as.containsKey('c_type') && as.get('c_type')==='fileInput'){//文件上传
				var _div = $('<div>',{
					"class":"col-sm-"+$formAttrMap.get('rightOcc')
				});
				_group.append(_div);
				
				var $fileInput = $('<input>',{
					'id':idPre+as.get('a_data_name'),
					'name':as.get('a_data_name'),
					'class':"file "+$formAttrMap.get('size'), 
					'type':"file",
					'multiple':'',
					'data-show-preview':'true',
					'data-role':'fileInput'
				});
				
				_div.append($fileInput);
			}
		}
	}
}

function handler_form_inline($form,key){
	var $groups = $form.find('.form-group');
	$form.empty().append($groups);
	
	$groups.each(function(i){
		$(this).children('label').removeClass('col-sm-1 col-sm-2 col-sm-3 col-sm-4 col-sm-5 col-sm-6 col-sm-7 col-sm-8 col-sm-9 col-sm-10 col-sm-11 control-label');
		$(this).children('div').attr('sign','');
		$(this).append($(this).children('div').children());
		$(this).children('div[sign]').remove();
		
		/*label变成 <div class="input-group-addon">
							bbb
						</div>/*/ 
	})
	
}

/*
 * 返回query_form
 */
function handler_query(){
	//获取表单属性
	if(!formateJson.containsKey('m_query')){
		return;
	}
	var isToolbar = PrivateAttrQueryMap.get('toolbar');
	var rankType = PrivateAttrQueryMap.get('rankType');
	
		var queryFormId = 'queryForm';
		var form = $('<form>',{
			'id':queryFormId,
			'ravo':'rainbow_fx_layout_bd',
			'class':rankType+' breadcrumb'
		});
		handler_form_horizontal(form,'m_query');
		if('form-inline'==rankType){
			handler_form_inline(form,'m_query');
		}		
		
		return form;
}

function handler_form(){
	//获取表单属性
	if(!formateJson.containsKey('m_form')){
		return;
	}
	
	//新增表单
	var _form = $('<form>',{
		'id':'editForm',
		'ravo':'rainbow_fx_layout_bd',
		'class':PrivateAttrFormMap.get('rankType')
	});
	
	handler_form_horizontal(_form,'m_form');
	if(PrivateAttrFormMap.get('rankType')=='form-horizontal'){
		
	}else{
		handler_form_inline(_form);
	}
	
	return _form;
}


/*-------------------------------2.0 datagrid-------------------------------*/

function handler_datagrid(){//获取表单属性
	if((!formateJson.containsKey('m_datagrid'))||(formateJson.get('m_datagrid').size()==0)){
		return;
	}
	//1.0 table包装
	var $row = $('<div>',{
		"ravo":"rainbow_fx_layout",
		"class":"row clearfix"
	});
	var $col = $('<div>',{
		"class":"col-md-12 column"
	});
	
	//2.0 table生成
	var $table = $('<table>',{
		'class':'table table-hover',
		'id':'queryTable', 
		'data-side-pagination':"server",
		'data-toggle':'table',
		'data-url':'${ctx}'+getReqUrlByBtn('query'),
		'ravo':'rainbow_fx_bj'
	});
	$row.append($col.append($table));
	
	/*--开始遍历表格属性BEG--*/
	formateJson_datagrid_attr();
	if(PrivateAttrDatagridMap.containsKey('height')){
		$table.attr('data-height',PrivateAttrDatagridMap.get('height'));
	}
	
	if(PrivateAttrDatagridMap.containsKey('undefinedText')){
		$table.attr('data-undefined-text',PrivateAttrDatagridMap.get('undefinedText'));
	}
	
	/*-------可选显示组件------*/
	if(PrivateAttrDatagridMap.containsKey('pagination')){
		$table.attr('data-pagination',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('search')){
		$table.attr('data-search',true);
	}
	
	if(!PrivateAttrDatagridMap.containsKey('showHeader')){
		$table.attr('data-show-header',false);
	}
	
	if(PrivateAttrDatagridMap.containsKey('showColumns')){
		$table.attr('data-show-columns',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('showRefresh')){
		$table.attr('data-show-refresh',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('showToggle')){
		$table.attr('data-show-toggle',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('showPaginationSwitch')){
		$table.attr('data-show-pagination-switch',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('cardView')){
		$table.attr('data-card-view',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('detailView')){
		$table.attr('data-detail-view',true);
	}
	
	/*----------基本设置------------*/
	if(!PrivateAttrDatagridMap.containsKey('classes')){
		$table.attr('data-classes','table table-hover table-no-bordered');
	}
	
	if(PrivateAttrDatagridMap.containsKey('striped')){
		$table.attr('data-striped',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('clickToSelect')){
		$table.attr('data-click-to-select',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('singleSelect')){
		$table.attr('data-single-select',true);
	}
	
	if(PrivateAttrDatagridMap.containsKey('toolbar')){
		$table.attr('data-toolbar','#toolbar');
	}
	
	if(!PrivateAttrDatagridMap.containsKey('checkboxHeader')){
		$table.attr('data-checkbox-header',false);
	}
	
	
	/*------------分页设置-----------*/
	if(PrivateAttrDatagridMap.containsKey('pageNumber')&&PrivateAttrDatagridMap.get('pageNumber')!==''){
		$table.attr('data-page-number',PrivateAttrDatagridMap.get('pageNumber'));
	}
	
	if(PrivateAttrDatagridMap.containsKey('pageSize')&&PrivateAttrDatagridMap.get('pageSize')!==''){
		$table.attr('data-page-size',PrivateAttrDatagridMap.get('pageSize'));
	}
	
	if(PrivateAttrDatagridMap.containsKey('pageList')&&PrivateAttrDatagridMap.get('pageList')!==''){
		$table.attr('data-page-list',PrivateAttrDatagridMap.get('pageList'));
	}
	
	if(PrivateAttrDatagridMap.containsKey('paginationPreText')&&PrivateAttrDatagridMap.get('paginationPreText')!==''){
		$table.attr('data-pagination-pre-text',PrivateAttrDatagridMap.get('paginationPreText'));
	}
	
	if(PrivateAttrDatagridMap.containsKey('paginationNextText')&&PrivateAttrDatagridMap.get('paginationNextText')!==''){
		$table.attr('data-pagination-next-text',PrivateAttrDatagridMap.get('paginationNextText'));
	}
	
	/*-------------组件显示位置--------------*/
	if(PrivateAttrDatagridMap.containsKey('searchAlign')){
		var v = PrivateAttrDatagridMap.get('searchAlign');
		if(v ==='left'){
			$table.attr('data-search-align',v);
		}
	}
	
	if(PrivateAttrDatagridMap.containsKey('buttonsAlign')){
		var v = PrivateAttrDatagridMap.get('buttonsAlign');
		if(v ==='left'){
			$table.attr('data-buttons-align',v);
		}
	}
	
	if(PrivateAttrDatagridMap.containsKey('toolbarAlign')){
		var v = PrivateAttrDatagridMap.get('toolbarAlign');
		if(v ==='right'){
			$table.attr('data-toolbar-align',v);
		}
	}
	
	if(PrivateAttrDatagridMap.containsKey('paginationVAlign')){
		var v = PrivateAttrDatagridMap.get('paginationVAlign');
		if(v !=='bottom'){
			$table.attr('data-pagination-v-align',v);
		}
	}
	
	if(PrivateAttrDatagridMap.containsKey('paginationHAlign')){
		var v = PrivateAttrDatagridMap.get('paginationHAlign');
		if(v ==='left'){
			$table.attr('data-pagination-h-align',v);
		}
	}
	
	if(PrivateAttrDatagridMap.containsKey('paginationDetailHAlign')){
		var v = PrivateAttrDatagridMap.get('paginationDetailHAlign');
		if(v ==='right'){
			$table.attr('data-pagination-detail-h-align',v);
		}
	}
	
	/*--开始遍历表格属性END--*/
	
	var $thead = $('<thead>');
	$table.append($thead);
	
	var $tr = $('<tr>');
	
	
	var m_datagrid_arrayList = formateJson.get('m_datagrid');
	
	if(PrivateAttrDatagridMap.containsKey('firstColCheckbox')){
		var $th0 = $('<th>',{
			"data-checkbox":"true",
			"data-field":"state"
		});
		$tr.append($th0);
	}
	
	if(PrivateAttrDatagridMap.containsKey('firstColRadio')){
		var $th0 = $('<th>',{
			"data-radio":"true",
			"data-field":"state"
		});
		$tr.append($th0);
	}
	
	for(var it=m_datagrid_arrayList.iterator();it.hasNext();){
		var as = it.next();//获取 field的属性
		
		var $th = $('<th>',{});
		if(as.containsKey('a_data_name')){
			var c = as.get('a_data_name');
			$th.attr('data-field',c);
		}
		
		if(as.containsKey('c_label')){
			var c = as.get('c_label');
			$th.attr('data-title',c);
			$th.text(c);
		}
		
		if(as.containsKey('data-checkbox')){
			var c = as.get('data-checkbox');
			$th.attr('data-checkbox',c);
		}
		
		if(as.containsKey('data-sortable')){
			var c = as.get('data-sortable');
			$th.attr('data-sortable',c);
		}
		
		if(as.containsKey('data-sortable')){
			var c = as.get('data-sortable');
			$th.attr('data-sortable',c);
		}
		
		if(as.containsKey('data-order')){
			var c = as.get('data-order');
			if(c){//默认为升序，选中为降序
				$th.attr('data-order','desc');
			}
		}
		
		if(as.containsKey('data-editable')){
			var c = as.get('data-editable');
			if(c){
				$th.attr('data-editable',c+'');
			}
		}
		
		if(as.containsKey('data-formatter')){
			var c = as.get('data-formatter');
			if(c){
				$th.attr('data-formatter','formatter_'+as.get('a_data_name'));
			}
		}
		
		if(as.containsKey('data-visible')){
			var c = as.get('data-visible');
			if(c){//默认为升序，选中为降序
				$th.attr('data-visible',false);
			}
		}
		
		/*
		 *  列宽
		 */
		if(as.containsKey('data-width')){
			var c = as.get('data-width');
			if(c!==undefined && c!==''){//默认为升序，选中为降序
				$th.attr('data-width',c);
			}
		}
		
		/*
		 * 列对齐列数据
		 */
		if(as.containsKey('data-align')){
			var c = as.get('data-align');
			$th.attr('data-align',c);
		}
		
		if(as.containsKey('data-valign')){
			var c = as.get('data-valign');
			$th.attr('data-valign',c);
		}
		
		if(as.containsKey('data-halign')){
			var c = as.get('data-halign');
			$th.attr('data-halign',c);
		}
		
		if(as.containsKey('data-falign')){
			var c = as.get('data-falign');
			$th.attr('data-falign',c);
		}
		
		$tr.append($th);
	}
	
	$thead.append($tr);
	
	//table初始化
	
	return $row;
}

function handler_datagrid_events() {
	//TODO
	console.info('------------handler_datagrid_events---------------');
	formateJson_datagrid_events();
	
	/*if(PrivateAttrDatagridEventsMap.isEmpty()){
		return;
	}*/
	//1.0 添加$(function(){})
	var h = 
		'\n/*auto::表格查询->选择表格列属性*/'+
		'$(document).ready(function() {'+
			 	'init_datagrid();'+
		 	'});'
			 	
 	editor.setValue(h);
	var initF = 
		'\nfunction init_datagrid(){'+
		'\n/*auto::表格查询->选择表格列属性*/'+
			'$datagrid_table = $("#queryTable");\n'+//查询表格字段属性定义表
			 '$datagrid_table.bootstrapTable({\n'+
			 
			 '\n/*auto::查询条件参数*/'+
			 '\nqueryParams:function(params){'+
			 	'\nvar formData = $("#queryForm").serializeObject();'+
			 	'\nvar paramList = {'+
			 			'\n'+getQueryFormJson()+
			 			'\npgside : "server",// 服务器分页'+
			 			'\npageSize : params.limit,'+
			 			'\nstart : params.offset+1,'+
			 			'\npageNo :  getPage(params),'+
			 			'\nsort : params.sort,'+
			 			'\norder : params.order'+
			 		'\n};'+
			 	'\nreturn paramList;'+
			 '\n}'+ 
			 '\n});'+
		 '}\n';

			 			
	//获取总行数 插入到最后
	var l =	editor.session.getLength();
	//设置光标
	editor.navigateTo(l+1, 1);
	editor.insert(initF);
	
	editor.navigateTo(0, 0);
	editor.insert('var $datagrid_table;');
	
	var o = editor.findAll('$datagrid_table.bootstrapTable({',{
		backwards: false,
		wrap: false,
		caseSensitive: false,
		wholeWord: false,
		regExp: false
	});
	var ipos = editor.getCursorPosition();
	if(PrivateAttrDatagridEventsMap.containsKey('onClickRow')){
		editor.navigateTo(ipos.row, ipos.column);
		var c =
			'\n /*auto::表格查询->表格事件->单击一行事件*/'+
			'\nonClickRow: function(row, $element) {'+
	        '\n    /*--单击一行事件--*/'+
	    	'\n	for(var name  in row){'+
	    	"\n		$('#f_'+name).val(row[name]);"+
	    	'\n	}'+
	        '\n},\n'
		editor.insert(c);
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onDblClickRow')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->双击一行事件*/'+
				'\n onDblClickRow:function(row,$element){'+
				'\n/*--双击一行事件--*/'+
				'\nconsole.info("--双击一行事件--");'+
				'\n$(\'#editForm\')[0].reset();'+
				'\n$(\'#oprType\').val("1");'+
				'\n$(\'#myMainTab a[href="#myMainTab_tab2"]\').tab(\'show\');'+
				'\nqryDetail(row);'+
				'},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onClickCell')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->单击单元格事件*/\n onClickCell:function(field, value, row, $element){/*--单击单元格事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onDblClickCell')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->双击单元格事件*/\n onDblClickCell:function(field, value, row, $element){/*--双击单元格事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onExpandRow')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->单击展开细节监听事件*/\n onExpandRow:function(index, row, $detail){/*--单击展开细节监听事件--*/},\n');
	}
	if(PrivateAttrDatagridEventsMap.containsKey('onCollapseRow')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->单击收起细节监听事件*/\n onCollapseRow:function(index, row){/*--单击收起细节监听事件--*/},\n');
	}
	//----
	if(PrivateAttrDatagridEventsMap.containsKey('onCheck')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->行选择事件*/\n onCheck:function(row, $element){/*--行选择事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onUncheck')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->取消行选择事件*/\n onUncheck:function(row,$element){/*--取消行选择事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onCheckAll')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->全选事件*/\n onCheckAll:function(rows){/*--全选事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onUncheckAll')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->取消全选事件*/\n onUncheckAll:function(rows){/*--取消全选事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onCheckSome')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->部分行选择事件*/\n onCheckSome:function(rows){/*--部分行选择事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onUncheckSome')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->取消部分行选择事件*/\n onUncheckSome:function(rows){/*--取消部分行选择事件--*/},\n');
	}
	
	//-----
	
	if(PrivateAttrDatagridEventsMap.containsKey('onLoadSuccess')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表加载成功事件*/\n onLoadSuccess:function(data){/*--表加载成功事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onLoadError')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表加载失败事件*/\n onLoadError:function(status, res){/*--表加载失败事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onPreBody')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表呈现前事件*/\n onPreBody:function(data){/*--表呈现前事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onPostBody')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表呈现后事件*/\n onPostBody:function(){/*--表呈现后事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onResetView')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表重置事件*/\n onResetView:function(){/*--表重置事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onRefreshOptions')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表刷新时在真正摧毁之前的事件*/\n onRefreshOptions:function(options){/*--表刷新时在真正摧毁之前的事件--*/},\n');
	}
	//----
	if(PrivateAttrDatagridEventsMap.containsKey('onColumnSearch')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->列查询监听事件*/\n onColumnSearch:function(field, text){/*--列查询监听事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onPageChange')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表切换分页事件*/\n onPageChange:function(number, size){/*--表切换分页事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onSearch')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表查询事件*/\n onSearch:function(text){/*--表查询事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onToggle')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->表切换视图事件*/\n onToggle:function(cardView){/*--表切换视图事件--*/},\n');
	}
	
	if(PrivateAttrDatagridEventsMap.containsKey('onSort')){
		editor.navigateTo(ipos.row, ipos.column);
		editor.insert('/*auto::表格查询->表格事件->列排序事件*/\n onSort:function(name, order){/*--列排序事件--*/},\n');
	}
	
	/*
	 * 表格列格式化
	 */
	var fl = PrivateAttrDatagridEventsMap.get('colFormatter');
	if(fl!=null && fl!=undefined){
		for(var it=fl.iterator();it.hasNext();){
			var as = it.next();//获取 field的属性
			var l =	editor.session.getLength();
			var f = 'function '+as+' (value, row, index) {}\n'
			editor.navigateTo(l, 1);
			editor.insert(f);
		}
	}
	
	/*
	 * 列格式化后响应的事件
	 */
	var el = PrivateAttrDatagridEventsMap.get('colEvents');
	if(el!=null && el!=undefined){
		for(var it=el.iterator();it.hasNext();){
			var as = it.next();//获取 field的属性
			var l =	editor.session.getLength();
			var f=
				'\n/*auto::表格查询->表格列属性->格式化后事件*/\n'+
				'window.'+as+' = {\n'+
			'"click input[auto-data-sortable]": function (e, value, row, index) {\n'+
			'/*---------列格式化后触发的事件---------*/\n'+
			'}\n'+
			'};'
			
			editor.navigateTo(l, 1);
			editor.insert(f);
		}
	}
	
	
	//格式化代码
    var js_source = editor.getValue();
    //console.info('js code : '+js_beautify(js_source));
	editor.setValue(js_beautify(js_source));
	
}

/*获取查询表单的请求内容*/
function getQueryFormJson(){
	var $json = '';
	if(queryButtonAttrMap.containsKey('query')){
		var $queryBtnAttr = queryButtonAttrMap.get('query');
		if(undefined!=$queryBtnAttr && $queryBtnAttr.containsKey('interface')){
			var $interface = $queryBtnAttr.get('interface');
			var $element = $interface.request.element;	//请求报文体
			for(var i=0;i<$element.length;i++){
				var $ele = $element[i];
				if(undefined!=$ele.isconstant){
					continue;
				}else{
					$json += $ele.id+' : formData.'+$ele.id+',';
				}
			}			
		}
	}
	return $json;
}

/*根据按钮ID获取对应绑定的url*/
function getReqUrlByBtn(btnId){
	var $url = '';
	if(btnId!=undefined){
		if(queryButtonAttrMap.containsKey(btnId)){
			var $queryBtnAttr = queryButtonAttrMap.get(btnId);
			if(undefined!=$queryBtnAttr && $queryBtnAttr.containsKey('interface')){
				var $interface = $queryBtnAttr.get('interface');
				$url = $interface.url;
			}
		}
	}
	
	return $url;
}

/*根据接口ID获取对应的url*/
function getReqUrlByInterface(ifc){
	var $url = '';
	var $interface = undefined;
	for(var i=0;i<$query_data.trans.table.interface.length;i++){
		if($query_data.trans.table.interface[i].id==ifc){
			$interface = $query_data.trans.table.interface[i];
			$url = $interface.url;
			break;
		}
	}
	return $url;
}

/*获取明细查询的0-请求/1-响应内容*/
function getQueryDetailJson(t){
	var $json = '';
	var $detailInterface = undefined;
	for(var i=0;i<$query_data.trans.table.interface.length;i++){
		if($query_data.trans.table.interface[i].id=='detail'){
			$detailInterface = $query_data.trans.table.interface[i];
			break;
		}
	}
	if($detailInterface){
			var $interface = $detailInterface;
			var $element = undefined;
			var m_form_arrayList = formateJson.get('m_form');
			if(t=='0'){
				$element = $interface.request.element;	//请求报文体
				for(var i=0;i<$element.length;i++){
					var $ele = $element[i];
					if(undefined!=$ele.isconstant){
						continue;
					}else{
						$json += $ele.id+' : rec.'+$ele.id+',';
					}
				}
				$json = $json.substring(0,$json.length-1);
			}else{
				$element = $interface.response.element;	//响应报文体
				for(var i=0;i<$element.length;i++){
					var $ele = $element[i];
					if(undefined!=$ele.isconstant){
						continue;
					}else{
						for(var it=m_form_arrayList.iterator();it.hasNext();){
							var as = it.next();//获取 field的属性
							var as_name = as.get('a_data_name');
							if(as_name==$ele.id){
								if(as.containsKey('c_type') && as.get('c_type')==='multiselect'){
									//下拉框
									$json += '$(\'#EDIT_'+$ele.id+'\').multiselect("select", [ds.'+$ele.id+']);\n';
								}else{
									$json += '$(\'#EDIT_'+$ele.id+'\').val(ds.'+$ele.id+');\n';
								}
							}
						}
					}
				}	
			}		
	}
	return $json;
}

/*获取按钮绑定的接口的请求内容*/
function getQueryDeleteElement(btnId){
	var $json = '';
	if(btnId!=undefined){
		if(queryButtonAttrMap.containsKey(btnId)){
			var $queryBtnAttr = queryButtonAttrMap.get(btnId);
			if(undefined!=$queryBtnAttr && $queryBtnAttr.containsKey('interface')){
				var $interface = $queryBtnAttr.get('interface');
				if($interface){
					var $element = undefined;
					$element = $interface.request.element;	//请求报文体
					for(var i=0;i<$element.length;i++){
						var $ele = $element[i];
						if(undefined!=$ele.isconstant){
							continue;
						}else{
							$json += '"'+$ele.id+'",';
						}
					}	
					$json = $json.substring(0,$json.length-1);		
				}
			}
		}
	}
	

	return $json;
}