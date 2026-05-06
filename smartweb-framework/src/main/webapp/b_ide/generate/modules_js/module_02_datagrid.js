/* ========================================================================
 * Rainbow: module_01_datagrid.js v1.0.0 
 * Author: chenyl
 * Time: 20170517
 * Copyright 2017-2020 Adtec, Inc.
 * 
 * 简介：
 * 1、数据初始化
 * 2、界面事件获取
 * 3、内存数据存储功能
 * ======================================================================== */


/*--------------1.0 界面组件定义和初始化-----------------*/
var $datagrid_height= undefined;

var $datagrid_table = undefined;

var m_datagrid;
 
 $(function () {
	 console.info('datagrid isInitModule='+isInitModule);
	 if(isInitModule){
		 init_datagrid(); 
	 }
	 
 });
 
 function init_datagrid(){
	 
	 //测试按钮
	 $('#btnId_ces').click(function () {
		formateJson_datagrid_attr();
	 });
	 
	 
	 $datagrid_height=$('#datagrid_height');//表高度
	 
	 $datagrid_table = $('#datagrid_table');//查询表格字段属性定义表
	 $datagrid_table.bootstrapTable({
		 
		 onDblClickRow:function(row,$element){
		 },
		 onEditableSave:function(field, row, oldValue, $el){
			 
		 },
		 onCheck:function(row,$element){
			 
		 },
		 onCheckAll:function(rows){
			 
		 },
		 onUncheck:function(row,$element){
			 
		 },
		 onUncheckAll:function(rows){
			 
		 },
		 onExpandRow:function(index, row, $detail){
			 
		 },onPostBody:function(){
			 //表呈现后，对公共属性进行初始化
			 /*def_pub_attr_datagrid();*/
		 }
	 });
	 
	 $.ajax({
		   type: "POST",
		   data: 'interfaceXmlfile='+selInterface,
		   url: ctxIde+'/ideAuto/getInterfaceXml',
		   success: function(data){
			   console.info('--------------');
			   
			   console.info(data.trans);
			   $datagrid_table.bootstrapTable('load',data.trans.table.field);
		   }
	});
 }
 
 
 
 /*--------------2.0 formate datagrid attr Json 表属性 数据存储及格式化---------*/
 var PrivateAttrDatagridMap = new Hashtable();//表属性
 var PrivateAttrDatagridFieldMap = new Hashtable();//表列属性
 
 /*
  * 格式化 PrivateAttrDatagridMap = m_datagird_attr
  */
 function formateJson_datagrid_attr(){
	 PrivateAttrDatagridMap.clear();
	 
	 var $form = $('#formId_datagrid_attr');
	 var fs = $form.serializeArray();
	 for(var i=0;i<fs.length;i++){
		 PrivateAttrDatagridMap.add(fs[i].name,fs[i].value);
	 }
 }
 
 /*
  * 格式化PrivateAttrDatagridFieldMap->m_datagrid
  */
 function formateJson_datagrid(){
	m_datagrid = new ArrayList();
	var selects = $datagrid_table.bootstrapTable('getAllSelections');
	for(var i=0;i<selects.length;i++){
		var field = new Hashtable();
		for (var key in selects[i]) {
			field.add(key,selects[i][key]);
		}
		if(PrivateAttrDatagridFiledMap.containsKey(selects[i].ID)){
			var attr = PrivateAttrDatagridFiledMap.get(selects[i].ID);
			var keys = attr.keys();
			for(var it=keys.iterator();it.hasNext();){
				var key = it.next();
				field.add(key,attr.get(key));
			}
		}
		m_datagrid.add(field);
	}
	formateJson.add('m_datagrid',m_datagrid);
 }

 /*--------------3.0 formate datagrid events Json 表事件  数据存储及格式化---------*/
 var PrivateAttrDatagridEventsMap = new Hashtable();//表事件属性
 
 /*
  * 格式化 PrivateAttrDatagridEventsMap = m_datagird_events
  */
 function formateJson_datagrid_events(){
	 var l = PrivateAttrDatagridEventsMap.get('colFormatter');
	 var ll = PrivateAttrDatagridEventsMap.get('colEvents');
	 PrivateAttrDatagridEventsMap.clear();
	 
	 var $formEvents = $('#formId_datagrid_events');
	 var fs = $formEvents.serializeArray();
	 for(var i=0;i<fs.length;i++){
		 PrivateAttrDatagridEventsMap.add(fs[i].name,fs[i].value);
	 }
	 PrivateAttrDatagridEventsMap.add('colFormatter',l);
	 PrivateAttrDatagridEventsMap.add('colEvents',ll);
 }
 
 
 /*-------------4.0 界面事件-----------------------------*/
 
 
 /*-----4.1公共属性初始化DOM-----*/
 var PrivateAttrDatagridFiledMap = new Hashtable();//表事件属性
 //初始化表格格式化
 function formatterTableCheckbox(value, row, index) {
	 var _id = "data-checkbox-"+row.ID;
	 return [
				'<input class="checkbox" style="display: inline-block" auto-data-checkbox="true" id="'+_id+'" value="" type="checkbox">'
    ].join('');
}
 
window.formatterTableCheckboxEvent = {
		    'click input[auto-data-checkbox]': function (e, value, row, index) {
		    	addAttrDatagrid(row.ID,'data-checkbox',this.checked);
		    }
		};

/*
 * 可排序
 */

function formatterTableSortable(value, row, index) {
	 var _id = "data-sortable-"+row.ID;
	 return [
				'<input class="checkbox" style="display: inline-block" auto-data-sortable="true" id="'+_id+'" value="" type="checkbox">'
   ].join('');
}

window.formatterTableSortableEvent = {
	    'click input[auto-data-sortable]': function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-sortable',this.checked);
	    }
	};

/*
 * 选中为降序排序 ，默认升序排序
 */


function formatterTableOrder(value, row, index) {
	 var _id = "data-order-"+row.ID;
	 return [
				'<input class="checkbox" style="display: inline-block" auto-data-order="true" id="'+_id+'" value="" type="checkbox">'
  ].join('');
}

window.formatterTableOrderEvent = {
	    'click input[auto-data-order]': function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-order',this.checked);
	    }
	};

/*
 * 可编辑
 */
function formatterTableEditable(value, row, index) {
	 var _id = "data-editable-"+row.ID;
	 return [
				'<input class="checkbox" style="display: inline-block" auto-data-editable="true" id="'+_id+'" value="" type="checkbox">'
 ].join('');
}

window.formatterTableEditableEvent = {
	    'click input[auto-data-editable]': function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-editable',this.checked);
	    }
	};

/*
 * 可格式化
 */

function formatterTableFormatter(value, row, index) {
	 var _id = "data-formatter-"+row.ID;
	 return [
				'<input class="checkbox" style="display: inline-block" auto-data-formatter="true" id="'+_id+'" value="" type="checkbox">'
			].join('');
}

window.formatterTableFormatterEvent = {
	    'click input[auto-data-formatter]': function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-formatter',this.checked);
	    	var l;
	    	if(PrivateAttrDatagridEventsMap.containsKey('colFormatter')){
	    		l = PrivateAttrDatagridEventsMap.get('colFormatter');
	    	}else{
	    		l = new ArrayList();
	    		PrivateAttrDatagridEventsMap.add('colFormatter',l);
	    	}
	    	if(this.checked){
	    		l.add('formatter_'+row.a_data_name);
	    	}else{
	    		l.remove('formatter_'+row.a_data_name);
	    	}
	    }
	};

/*
 * 格式化显示后响应的事件
 */

function formatterTableEvents(value, row, index) {
	 var _id = "data-events-"+row.ID;
	 return [
				'<input class="checkbox" style="display: inline-block" auto-data-events="true" id="'+_id+'" value="" type="checkbox">'
			].join('');
}

window.formatterTableEventsEvent = {
	    'click input[auto-data-events]': function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-events',this.checked);
	    	var l;
	    	l = PrivateAttrDatagridEventsMap.get('colEvents');
	    	
	    	if(l===null||l===undefined){
	    		l = new ArrayList();
	    		PrivateAttrDatagridEventsMap.add('colEvents',l);
	    	}
	    	if(this.checked){
	    		l.add('events_'+row.a_data_name);
	    	}else{
	    		l.remove('events_'+row.a_data_name);
	    	}
	    }
	}

/*
 * 列隐藏
 */
function formatterTableVisible(value, row, index) {
	 var _id = "data-visible-"+row.ID;
	 return [
				'<input class="checkbox" style="display: inline-block" auto-data-visible="true" id="'+_id+'" value="" type="checkbox">'
			].join('');
}

window.formatterTableVisibleEvent = {
	    'click input[auto-data-visible]': function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-visible',this.checked);
	    }
	};

/*
 * 列水平对齐
 */
function formatterAlign(value, row, index) {
	 var _id = "data-align-"+row.ID;
	 return [
		 '<label class="radio-inline" style="padding-left:0px">',
		 '<input type="radio" name="dataAlign"  value="left" style="position:relative"> 左',
		'</label>',
		'<label class="radio-inline" style="padding-left:0px">',
		  '<input type="radio" name="dataAlign"  value="center" style="position:relative"> 中',
		'</label>',
		'<label class="radio-inline" style="padding-left:0px">',
		  '<input type="radio" name="dataAlign"  value="right" style="position:relative"> 右',
		'</label>'
			].join('');
}

window.eventsAlign = {
	    "click input[name='dataAlign']": function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-align',$(this).val());
	    }
	};

/*
 * 列垂直对齐
 */
function formatterValign(value, row, index) {
	 /*var _id = "data-valign-"+row.ID;*/
	 return [
		 '<label class="radio-inline" style="padding-left:0px">',
		 '<input type="radio" name="dataValign"  value="top" style="position:relative"> 上',
		'</label>',
		'<label class="radio-inline" style="padding-left:0px">',
		  '<input type="radio" name="dataValign"  value="middle" style="position:relative"> 中',
		'</label>',
		'<label class="radio-inline" style="padding-left:0px">',
		  '<input type="radio" name="dataValign"  value="bottom" style="position:relative"> 下',
		'</label>'
			].join('');
}

window.eventsValign = {
	    'click input[name="dataValign"]': function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-valign',$(this).val());
	    }
	};

/*对齐表格头部*/
function formatterHalign(value, row, index) {
	 var _id = "data-align-"+row.ID;
	return [
		 '<label class="radio-inline" style="padding-left:0px">',
		 '<input type="radio" name="dataHalign"  value="left" style="position:relative"> 左',
		'</label>',
		'<label class="radio-inline" style="padding-left:0px">',
		  '<input type="radio" name="dataHalign"  value="center" style="position:relative"> 中',
		'</label>',
		'<label class="radio-inline" style="padding-left:0px">',
		  '<input type="radio" name="dataHalign"  value="right" style="position:relative"> 右',
		'</label>'
			].join('');
}
window.eventsHalign = {
	    "click input[name='dataHalign']": function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-halign',$(this).val());
	    }
	};
/*
 * 表脚水平对齐
 */
function formatterFalign(value, row, index) {
	 var _id = "data-align-"+row.ID;
	return [
		 '<label class="radio-inline" style="padding-left:0px">',
		 '<input type="radio" name="dataFalign"  value="left" style="position:relative"> 左',
		'</label>',
		'<label class="radio-inline" style="padding-left:0px">',
		  '<input type="radio" name="dataFalign"  value="center" style="position:relative"> 中',
		'</label>',
		'<label class="radio-inline" style="padding-left:0px">',
		  '<input type="radio" name="dataFalign"  value="right" style="position:relative"> 右',
		'</label>'
			].join('');
}
window.eventsFalign = {
	    "click input[name='dataFalign']": function (e, value, row, index) {
	    	addAttrDatagrid(row.ID,'data-falign',$(this).val());
	    }
	};


 function addAttrDatagrid(ID,attrKey,attrValue){
	var attribute = undefined;
	 if(PrivateAttrDatagridFiledMap.containsKey(ID)){
		 attribute = PrivateAttrDatagridFiledMap.get(ID);
	 }else{
		attribute = new Hashtable();
		PrivateAttrDatagridFiledMap.add(ID,attribute);
	 }
	 attribute.add(attrKey,attrValue);
}

function getAttrDatagrid(ID,attrKey){
	if(PrivateAttrDatagridFiledMap.containsKey(ID)){
		if(PrivateAttrDatagridFiledMap.get(ID).containsKey(attrKey)){
			return PrivateAttrDatagridFiledMap.get(ID).get(attrKey);
		}else{
			return undefined;
		}
	}else{
		return undefined;
	}
}
 
 //4.2公共属性初始化后的事件初始化
/* function def_pub_attr_datagrid() {
	//2.0 只读
 	 $("input[auto-c-checkbox]").on("click", function(){
 		 var tr = $(this).parent().parent();
 		 var row = getRowByIndex(tr.data('index'));
 		 
 		 addAttr(row.ID,'c_readonly',this.checked);
 	 });
	
};*/
 
 
 /*-----4.1表格事件END-----*/
 
 
 
 
 /*-------------5.0 初始化 PrivateAttrDatagridFieldMap---*/
 
 //TODO

 
 