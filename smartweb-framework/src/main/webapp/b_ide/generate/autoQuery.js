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

/*--------------1.0 界面组件定义和初始化-----------------*/
var $table_query = undefined;
var m_query ;

var queryButtonArrayList= new ArrayList();

/*
 * auto.jsp 组件定义
 */
var $form_query_toolbar = undefined;//是否放在toolbar的位置
var $form_query_rank = undefined;//排列方式form_query_rank
var $form_query_colNum = undefined;//列数form_query_colNum
var $form_query_leftOcc = undefined;//左占form_query_leftOcc
var $form_query_rightOcc = undefined;//右占form_query_rightOcc
var $form_query_size = undefined;//尺寸form_query_size


/*
 * PrivateAttrQueryFiledMap
 * 暂时存储字段的私有属性
 * 添加方法
 * def_pub_attr();
 * onExpand_text_cc
 * 
 * 公共操作方法 addAttr
 * 
 * 
 */
var PrivateAttrQueryFiledMap = new Hashtable();
var PrivateAttrQueryMap = new Hashtable();

 $(function () {
	 init_query();
	 
	 formateJson_query_attr();
 });
 
 function init_query(){
	 console.info('init_query='+selModule);
	 /*
	  * 表单属性
	  */
	 $form_query_toolbar = $('#form_query_toolbar');
	 $form_query_colNum = $('#form_query_colNum');
	 $form_query_rank = $('#form_query_rank');
	 $form_query_leftOcc = $('#form_query_leftOcc');
	 $form_query_rightOcc = $('#form_query_rightOcc');
	 $form_query_size = $('#form_query_size');
	 /*
	  * 表单字段属性 table
	  */
	 
	/*
	 * 表单提交按钮选择框初始化
	 */
	$('#query_buttons').multiselect({
		 onChange: function(option,checked,select) {
			 var v = $(option).val();
			 if(checked){
				 queryButtonArrayList.add(v);
			 }else{
				 queryButtonArrayList.remove(v);
			 }
		 }
	 });
	
	//table 初始化
	 $table_query = $('#table_query');
	 $table_query.bootstrapTable({
		 onDblClickRow:function(row,$element){
		 },
		 onEditableSave:function(field, row, oldValue, $el){
			 $table_query.bootstrapTable('resetView', {
	         });
		 },
		 onCheck:function(row,$element){
			 query_table_onCheck(row,$element);
		 },
		 onCheckAll:function(rows){
			 query_table_onCheckAll(rows);
		 },
		 onUncheck:function(row,$element){
			 query_table_onUncheck(row,$element);
		 },
		 onUncheckAll:function(rows){
			 query_table_onUncheckAll(rows);
		 },
		 onExpandRow:function(index, row, $detail){
			 
			 var tr= $detail.parent().prev();
			 
			 var attr = getAttr(row.ID,'c_type','query');
			 
			 if(attr===undefined){
				 onExpand_text(index, row, $detail,'query');
			 }else if(attr==='text'){//文本框
				 onExpand_text(index, row, $detail,'query');
			 }else if(attr==='password'){//密码框
				 onExpand_text(index, row, $detail,'query');
			 }else if(attr ==='emil'){//邮件文本框
				 onExpand_text(index, row, $detail,'query');
			 }else if(attr ==='multiselect'){//选择框
				 onExpand_multiselect(index, row, $detail,'query');
				 //TODO
			 }else if(attr ==='textarea'){//多行文本框
				 /*onExpand_textarea(index, row, $detail);*/
				 onExpand_text(index, row, $detail,'query');
			 }else if(attr==='check'){//多选框
				 onExpand_checkbox(index, row, $detail,'query');
			 }else if(attr==='radio'){//单选框
				 onExpand_radio(index, row, $detail,'query');
			 }else if(attr ==='datatime'){//日期框
				 onExpand_datatime(index, row, $detail,'query');
			 }else if(attr ==='fileInput'){//文件上传
				//TODO
			 }
		 },
		 onCollapseRow:function(index, row){
		 },
		 onPostBody:function(){
			 def_pub_attr_query();
		 }
	 });
}

 /*--------------2.0 formate datagrid attr Json 表属性 数据存储及格式化---------*/

	/*
	 * 获取格式化数据  表单字段属性
	 */
	function formateJson_query(){
		m_query = new ArrayList();
		var selects = $table_query.bootstrapTable('getAllSelections');
		for(var i=0;i<selects.length;i++){
			var field = new Hashtable();
			for (var key in selects[i]) {
				
				field.add(key,selects[i][key]);
			}
			
			//添加组件ID
			field.add('id','qf_'+selects[i]['a_data_name']);
			
			if(PrivateAttrQueryFiledMap.containsKey(selects[i].ID)){
				var attr = PrivateAttrQueryFiledMap.get(selects[i].ID);
				var keys = attr.keys();
				for(var it=keys.iterator();it.hasNext();){
					var key = it.next();
					field.add(key,attr.get(key));
				}
			}
			
			m_query.add(field);
		}
		formateJson.add('m_query',m_query);
		
		return formateJson;
	}

/*
* 格式化 PrivateAttrQueryMap = m_query_attr 表单属性
*/
function formateJson_query_attr(){
		PrivateAttrQueryMap.add('toolbar','true');
		PrivateAttrQueryMap.add('colNum',parseInt(2));
		PrivateAttrQueryMap.add('leftOcc',4);
		PrivateAttrQueryMap.add('rightOcc',8);
		PrivateAttrQueryMap.add('size','');
		PrivateAttrQueryMap.add('rankType','form-horizontal');//默认水平表单
		
		//是否以toolbar的方式展现
		$form_query_toolbar.multiselect({
			 onChange: function(option,checked,select) {
				 var colNum =parseInt($(option).val()) ;
				 PrivateAttrQueryMap.add('toolbar',colNum);
			 }
		 });
		
		//表单默认列数
		$form_query_colNum.multiselect({
			 onChange: function(option,checked,select) {
				 var colNum =parseInt($(option).val()) ;
				 PrivateAttrQueryMap.add('colNum',colNum);
				 setRowAndColNumQuery();
			 }
		 });
		//左占
		$form_query_leftOcc.multiselect({
			 onChange: function(option,checked,select) {
				 var leftOcc =parseInt($(option).val()) ;
				 PrivateAttrQueryMap.add('leftOcc',leftOcc);
			 }
		 });
		
		//右占
		$form_query_rightOcc.multiselect({
			 onChange: function(option,checked,select) {
				 var rightOcc =parseInt($(option).val()) ;
				 PrivateAttrQueryMap.add('rightOcc',rightOcc);
			 }
		 });
		
		//尺寸
		$form_query_size.multiselect({
			 onChange: function(option,checked,select) {
				 var size =$(option).val() ;
				 PrivateAttrQueryMap.add('size',size);
			 }
		 });
		
		//排列方式
		$form_query_rank.multiselect({
			 onChange: function(option,checked,select) {
				 var rankType =$(option).val() ;
				 PrivateAttrQueryMap.add('rankType',rankType);
			 }
		 });
		
		//排列方式
}
 


 /*-------------3.0 公共属性-----------------------------*/
 	/*-----3.1公共属性格式化-----*/
	//组件类型
	function formatterQueryType(value, row, index) {
		 var _id = "c-type-query"+row.ID;
		 return [
			 '<select id="'+_id+'" auto-c-type-query="true" auto-index="'+row.ID+'"  data-role="multiselect" >',
				'<option value="text">',
					'文本框',
				'</option>',
				'<option value="password">',
					'密码框',
				'</option>',
				'<option value="emil">',
					'电子邮件',
				'</option>',
				'<option value="multiselect">',
					'下拉框',
				'</option>',
				'<option value="textarea">',
					'多行文本框',
				'</option>',
				'<option value="check">',
					'多选框',
				'</option>',
				'<option value="radio">',
					'单选框',
				'</option>',
				'<option value="datatime">',
					'日期框',
				'</option>',			
				'<option value="fileInput">',
					'文件上传',
				'</option>',				
			'</select>'
	   ].join('');
	}
	//只读
	 function formatterQueryReadOnly(value, row, index){
			var _id = "c-readOnly-query"+row.ID;
			 return [
						'<input class="checkbox" auto-c-readOnly-query="true" id="'+_id+'" value="" type="checkbox">'
		    ].join('');
		} 
	 
	 //禁用
	function formatterQueryDisabled(value, row, index){
		var _id = "c-disable-query"+row.ID;
		return [
			'<input class="checkbox" auto-c-disable-query="true" id="'+_id+'" value="" type="checkbox">'
			].join('');
	} 

	//排列
	function formatterQueryRank(value, row, index){
		return [
	        '<a class="like up" href="javascript:void(0)" title="Like">',
	        '<i class="glyphicon glyphicon-chevron-down"></i>',
	        '</a>  ',
	        '<a class="remove down" href="javascript:void(0)" title="Remove">',
	        '<i class="glyphicon glyphicon-chevron-up"></i>',
	        '</a>'
	    ].join('');
	}
	 /*-----3.2公共属属性格式化后绑定事件-----*/
	 
	 function def_pub_attr_query(){
	 	 //TODO 切换组件类型的时候需要个性化属性面板 
	 	
	 	 //1.0组件类型
	 	 $('select[auto-c-type-query]').multiselect({
	 		 onChange: function(option,checked,select) {
	 			 $table_query.bootstrapTable('resetView');
	 			 var tr = this.$select.parent().parent().parent();
	 			 var row = getRowByIndex_query(tr.data('index'));
	 			 
	 			 addAttr_query(row.ID,'c_type',$(option).val());
	 		 }
	 	 });
	 	 //2.0 只读
	 	 $("input[auto-c-readOnly-query]").on("click", function(){
	 		 var tr = $(this).parent().parent();
	 		 var row = getRowByIndex_query(tr.data('index'));
	 		 
	 		 addAttr_query(row.ID,'c_readonly',this.checked);
	 	 });
	 	 
	 	 //3.0 禁用
	 	 $("input[auto-c-disable-query]").on("click", function(){
	 		 var tr = $(this).parent().parent();
	 		 var row = getRowByIndex_query(tr.data('index'));
	 		 
	 		 addAttr_query(row.ID,'c_disable',this.checked);
	 	 });
	  }	 
	 	//排列
		window.eventQueryRank = {
		        'click .like': function (e, value, row, index) {
		        	$table_query.bootstrapTable('remove', {field: 'ID', values: row.ID});
		        	$table_query.bootstrapTable('insertRow', {index:index+1, row:row});
		        	def_pub_attr_fx_query();
		        },
		        'click .remove': function (e, value, row, index) {
		        	if(index<1){
		        		return;
		        	}
		        	$table_query.bootstrapTable('remove', {field: 'ID', values: row.ID});
		        	$table_query.bootstrapTable('insertRow', {index:index-1, row:row});
		        	def_pub_attr_fx_query();
		        }
		    };
	 
	 /*
	  * 反显自定义公共属性
	  */

	 function def_pub_attr_fx_query(){
	 	//1.0 遍历自定义属性
	 	var keys = PrivateAttrQueryFiledMap.keys();
	 	if(keys.isEmpty()){
	 		return;
	 	}
	 	for(var i=0;i<keys.size();i++){
	 		var ID = keys.get(i);
	 		var attr = PrivateAttrQueryFiledMap.get(ID);

	 		if(attr.containsKey('c_type')){
	 			$('#c-type-'+ID).multiselect('select', attr.get('c_type'));
	 		}
	 		
	 		if(attr.containsKey('c_readonly')){
	 			$('#c-readOnly-'+ID).attr("checked",attr.get('c_readonly'));
	 		}
	 		
	 		if(attr.containsKey('c_disable')){
	 			$('#c-disable-'+ID).attr("checked",attr.get('c_disable'));
	 		}
	 	}
	 }
	 
	 
 /*
  * 选择行的时候出发默认第几行第几列
  */
 function query_table_onCheck(row,$element){
	 setRowAndColNumQuery();
 }	
 
 function query_table_onCheckAll(rows){
	 setRowAndColNumQuery();
 }
 
 function query_table_onUncheck(row,$element){
	 setRowAndColNumQuery();
 }
 
 function query_table_onUncheckAll(rows){
	 setRowAndColNumQuery();
 }
 
 /* 自动定义行列 */
 function setRowAndColNumQuery(){
	 //首先清空
	 var rows =$table_query.bootstrapTable('getData');
	 for(var i=0;i<rows.length;i++){
		 var row = rows[i];
		 row.c_row='0';
		 $table_query.bootstrapTable('updateByUniqueId',{
			 id:row.ID,
			 row:row
		 });
	 }
	 //设置
	 var colNum = PrivateAttrQueryMap.get('colNum');
	 var selRows =$table_query.bootstrapTable('getAllSelections');
	 for(var i=0;i<selRows.length;i++){
		 var row = selRows[i];
		 row.c_row=parseInt(i/colNum)+1;
		 
		 $table_query.bootstrapTable('updateByUniqueId',{
			 id:row.ID,
			 row:row
		 });
	 }
	 
	 def_pub_attr_fx_query();
 }
 
 
 /*-------------4.0 私有属性-----------------------------*/
 /*function onExpand_text_query(index, row, $detail){
	 var id = "select_query"+row.ID;
	 $detail.html(
			 '<form ravo="rainbow_fx_layout_bd" class="form-horizontal"pourl="">'+
				'<div ravo="rainbow_fx_layout" class="row clearfix">'+
					'<div class="col-md-4 column">'+
						'<div ravo="rainbow_fx" class="form-group">'+
							'<label for="inputEmail3" class="col-sm-4 control-label">'+
								'校验：'+
							'</label>'+
							'<select multiple="multiple" id="'+id+'" data-role="multiselect"'+
							'class="col-sm-7">'+
								'<option value="notempty">'+
									'不为空'+
								'</option>'+
								'<option value="integer">'+
									'整数'+
								'</option>'+
								'<option value="3">'+
									'option 3'+
								'</option>'+
							'</select>'+
						'</div>'+
					'</div>'+
					'<div class="col-md-4 column">'+
						'<div ravo="rainbow_fx" class="form-group">'+
							'<label for="inputEmail3" class="col-sm-4 control-label rainbow-select">'+
								'min'+
							'</label>'+
							'<div class="col-sm-7">'+
								'<input class="form-control" placeholder="Rainbow" type="text">'+
							'</div>'+
						'</div>'+
					'</div>'+
					'<div class="col-md-4 column">'+
						'<div ravo="rainbow_fx" class="form-group">'+
							'<label for="inputEmail3" class="col-sm-4 control-label">'+
								'max'+
							'</label>'+
							'<div class="col-sm-7">'+
								'<input class="form-control" placeholder="Rainbow" type="text">'+
							'</div>'+
						'</div>'+
					'</div>'+
				'</div>'+
			'</form>'
	 );
	 onExpand_text_cc_query(index, row, $detail);
	 onExpand_text_fx_query(index, row, $detail);
 }
 
 function onExpand_text_cc_query(index, row, $detail){
	 var ID = row.ID;
	 $('#select_query'+row.ID).multiselect({
		 onChange:function(option,checked,select){
			 var val = $(option).val();
			 
			 var vs = getAttr_query(ID,'c_validator');
			 if(vs==undefined){
				 vs = new Hashtable();
				 addAttr_query(ID,'c_validator',vs);
			 }
			 
			 if(checked){
				 vs.add(val,val);
			 }else{
				 vs.remove(val);
			 }
		 }
	 });
	 //TODO
 }
 
 function onExpand_text_fx_query(index, row, $detail){
	//TODO 反显
	 var id = "select_query"+row.ID;
	 
	 var vs = getAttr_query(row.ID,'c_validator');
	 
	 if(vs===undefined){
		 return;
	 }
	 if(vs.containsKey('notempty')){
		 $('#'+id).multiselect('select', ['notempty']);
	 }
	 if(vs.containsKey('integer')){
		 $('#'+id).multiselect('select', ['integer']);
	 }
	 //TODO
 }*/

 /*-------------4.0 初始化 PrivateAttrQueryFiledMap--------*/
 /*
  *公共方法 table 方法补充  通过index获取row数据
  */
 
 function getRowByIndex_query(index){
	 var ID = $table_query.find('tbody').children('tr').eq(index).children('td').eq(2).text();
	 return $table_query.bootstrapTable('getRowByUniqueId',ID);
 }
 
/*
 * 公共方法 存放到PrivateAttrQueryFiledMap
 */ 
 
function addAttr_query(ID,attrKey,attrValue){
	var attribute = undefined;
	 if(PrivateAttrQueryFiledMap.containsKey(ID)){
		 attribute = PrivateAttrQueryFiledMap.get(ID);
	 }else{
		attribute = new Hashtable();
		PrivateAttrQueryFiledMap.add(ID,attribute);
	 }
	 attribute.add(attrKey,attrValue);
}

function getAttr_query(ID,attrKey){
	if(PrivateAttrQueryFiledMap.containsKey(ID)){
		if(PrivateAttrQueryFiledMap.get(ID).containsKey(attrKey)){
			return PrivateAttrQueryFiledMap.get(ID).get(attrKey);
		}else{
			return undefined;
		}
	}else{
		return undefined;
	}
}

/* function getHeight() {
     return $(window).height() - $('h1').outerHeight(true)-300;
 }*/