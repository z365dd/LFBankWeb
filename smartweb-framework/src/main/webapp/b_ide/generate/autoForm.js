/* ========================================================================
 * Rainbow: autoDatagrid.js v1.0.0 
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
var $table_form = undefined;

var selectRowsMap = new Hashtable();
var m_form ;

var formateJson = new Hashtable();
var formButtonArrayList= new ArrayList();//应该归类到formateJson


/*
 * auto.jsp 组件定义
 */
var $form_attr_rankType = undefined;//排列方式
var $form_attr_colNum = undefined;//列数
var $form_attr_leftOcc = undefined;//左占
var $form_attr_rightOcc = undefined;//右占
var $form_attr_size = undefined;//尺寸


/*
 * PrivateAttrFormFiledMap
 * 暂时存储字段的私有属性
 * 添加方法
 * def_pub_attr();
 * onExpand_text_cc
 * 
 * 公共操作方法 addAttr
 * 
 * 
 */
var PrivateAttrFormFiledMap = new Hashtable();
var PrivateAttrFormMap = new Hashtable();

 $(function () {
	 
	 init_form();
	 
	 formateJson_form_attr();
	 
 });
 
 function init_form(){
	 /*
	  * 表单属性
	  */
	 $form_attr_colNum = $('#selectId_form_attr_colNum');
	 $form_attr_rankType = $('#selectId_form_attr_rank');
	 $form_attr_leftOcc = $('#selectId_form_attr_leftOcc');
	 $form_attr_rightOcc = $('#selectId_form_attr_rightOcc');
	 $form_attr_size = $('#selectId_form_attr_size');
	 /*
	  * 表单字段属性 table
	  */
	 
	/*
	 * 表单提交按钮选择框初始化
	 */
	$('#selectId_906550').multiselect({
		 onChange: function(option,checked,select) {
			 var v = $(option).val();
			 if(checked){
				 formButtonArrayList.add(v);
			 }else{
				 formButtonArrayList.remove(v);
			 }
		 }
	 });
	
	//table 初始化
	 $table_form = $('#table_form');
	 $table_form.bootstrapTable({
		 onDblClickRow:function(row,$element){
		 },
		 onEditableSave:function(field, row, oldValue, $el){
			 $table_form.bootstrapTable('resetView', {
	         });
		 },
		 onCheck:function(row,$element){
			 form_table_onCheck(row,$element);
		 },
		 onCheckAll:function(rows){
			 form_table_onCheckAll(rows);
		 },
		 onUncheck:function(row,$element){
			 form_table_onUncheck(row,$element);
		 },
		 onUncheckAll:function(rows){
			 form_table_onUncheckAll(rows);
		 },
		 onExpandRow:function(index, row, $detail){
			 var tr= $detail.parent().prev();
			 /*var attr = tr.data('attr_type');//获取类型
*/			 
			 var attr = getAttr(row.ID,'c_type','form');
			 
			 if(attr===undefined){
				 onExpand_text(index, row, $detail,'form');
			 }else if(attr==='text'){//文本框
				 onExpand_text(index, row, $detail,'form');
			 }else if(attr==='password'){//密码框
				 onExpand_text(index, row, $detail,'form');
			 }else if(attr ==='emil'){//邮件文本框
				 onExpand_text(index, row, $detail,'form');
			 }else if(attr ==='multiselect'){//选择框
				 onExpand_multiselect(index, row, $detail,'form');
				 //TODO
			 }else if(attr ==='textarea'){//多行文本框
				 /*onExpand_textarea(index, row, $detail);*/
				 onExpand_text(index, row, $detail,'form');
			 }else if(attr==='check'){//多选框
				 onExpand_checkbox(index, row, $detail,'form');
			 }else if(attr==='radio'){//单选框
				 onExpand_radio(index, row, $detail,'form');
			 }else if(attr ==='datatime'){//日期框
				 onExpand_datatime(index, row, $detail,'form');
			 }else if(attr ==='fileInput'){//文件上传
				//TODO
			 }
		 },
		 onCollapseRow:function(index, row){
		 },
		 onPostBody:function(){
			 def_pub_attr();
		 }
	 });
}

 /*--------------2.0 formate datagrid attr Json 表属性 数据存储及格式化---------*/
	/*
	 * 获取格式化数据  表单字段属性
	 */
	function formateJson_fome(){
		m_form = new ArrayList();
		var selects = $table_form.bootstrapTable('getAllSelections');
		for(var i=0;i<selects.length;i++){
			var field = new Hashtable();
			for (var key in selects[i]) {
				field.add(key,selects[i][key]);
			}
			//添加组件ID
			field.add('id','f_'+selects[i]['a_data_name']);
			
			if(PrivateAttrFormFiledMap.containsKey(selects[i].ID)){
				var attr = PrivateAttrFormFiledMap.get(selects[i].ID);
				var keys = attr.keys();
				for(var it=keys.iterator();it.hasNext();){
					var key = it.next();
					field.add(key,attr.get(key));
				}
			}
			
			m_form.add(field);
			console.info(field);
		}
		formateJson.add('m_form',m_form);
		return formateJson;
	}

/*
* 格式化 PrivateAttrFormMap = m_form_attr 表单属性
*/
function formateJson_form_attr(){
		
		PrivateAttrFormMap.add('colNum',parseInt(2));
		PrivateAttrFormMap.add('leftOcc',4);
		PrivateAttrFormMap.add('rightOcc',8);
		PrivateAttrFormMap.add('size','');
		PrivateAttrFormMap.add('rankType','form-horizontal');//默认水平表单
		
		//表单默认列数
		$form_attr_colNum.multiselect({
			 onChange: function(option,checked,select) {
				 var colNum =parseInt($(option).val()) ;
				 PrivateAttrFormMap.add('colNum',colNum);
				 setRowAndColNum();
			 }
		 });
		//左占
		$form_attr_leftOcc.multiselect({
			 onChange: function(option,checked,select) {
				 var leftOcc =parseInt($(option).val()) ;
				 PrivateAttrFormMap.add('leftOcc',leftOcc);
			 }
		 });
		
		//右占
		$form_attr_rightOcc.multiselect({
			 onChange: function(option,checked,select) {
				 var rightOcc =parseInt($(option).val()) ;
				 PrivateAttrFormMap.add('rightOcc',rightOcc);
			 }
		 });
		
		//尺寸
		$form_attr_size.multiselect({
			 onChange: function(option,checked,select) {
				 var size =$(option).val() ;
				 PrivateAttrFormMap.add('size',size);
			 }
		 });
		
		//排列方式
		$form_attr_rankType.multiselect({
			 onChange: function(option,checked,select) {
				 var rankType =$(option).val() ;
				 PrivateAttrFormMap.add('rankType',rankType);
			 }
		 });
		
		//排列方式
}
 


 /*-------------3.0 公共属性-----------------------------*/
 	/*-----3.1公共属性格式化-----*/
	//组件类型
	function formatterType(value, row, index) {
		 var _id = "c-type-"+row.ID;
		 return [
			 '<select id="'+_id+'" auto-c-type="true" auto-index="'+row.ID+'"  data-role="multiselect" >',
				'<option value="text">',
					'文本框',
				'</option>',
				'<option value="password">',
					'密码框',
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
	 function formatterReadOnly(value, row, index){
			var _id = "c-readOnly-"+row.ID;
			 return [
						'<input class="checkbox" auto-c-readOnly="true" id="'+_id+'" value="" type="checkbox">'
		    ].join('');
		} 
	 
	 //禁用
	function formatterDisabled(value, row, index){
		var _id = "c-disable-"+row.ID;
		return [
			'<input class="checkbox" auto-c-disable="true" id="'+_id+'" value="" type="checkbox">'
			].join('');
	} 
	
	 //必输
	function formatterMust(value, row, index){
		var _id = "c-must-"+row.ID;
		return [
			'<input class="checkbox" auto-c-must="true" id="'+_id+'" value="" type="checkbox">'
			].join('');
	} 

	//排列
	function formatterRank(value, row, index){
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
	 
	 function def_pub_attr(){
	 	 //TODO 切换组件类型的时候需要个性化属性面板 
	 	
	 	 //1.0组件类型
	 	 $('select[auto-c-type]').multiselect({
	 		 onChange: function(option,checked,select) {
	 			 $table_form.bootstrapTable('resetView');
	 			 var tr = this.$select.parent().parent().parent();
	 			 var row = getRowByIndex(tr.data('index'));
	 			 addAttr(row.ID,'c_type',$(option).val());
	 			 
	 			 //TODO 切换展开内容
	 			 $table_form.bootstrapTable('collapseRow', tr.data('index'));
	 			 $table_form.bootstrapTable('expandRow', tr.data('index'));
	 		 }
	 	 });
	 	 //2.0 只读
	 	 $("input[auto-c-readOnly]").on("click", function(){
	 		 var tr = $(this).parent().parent();
	 		 var row = getRowByIndex(tr.data('index'));
	 		 
	 		 addAttr(row.ID,'c_readonly',this.checked);
	 	 });
	 	 
	 	 //3.0 禁用
	 	 $("input[auto-c-disable]").on("click", function(){
	 		 var tr = $(this).parent().parent();
	 		 var row = getRowByIndex(tr.data('index'));
	 		 
	 		 addAttr(row.ID,'c_disable',this.checked);
	 	 });
	 	 
	 	 //4.0 只读
	 	$("input[auto-c-must]").on("click", function(){
	 		 var tr = $(this).parent().parent();
	 		 var row = getRowByIndex(tr.data('index'));
	 		 
	 		 addAttr(row.ID,'c_must',this.checked);
	 	 });
	  }	 
	 	//排列
		window.eventRank = {
		        'click .like': function (e, value, row, index) {
		        	$table_form.bootstrapTable('remove', {field: 'ID', values: row.ID});
		        	$table_form.bootstrapTable('insertRow', {index:index+1, row:row});
		        	def_pub_attr_fx();
		        },
		        'click .remove': function (e, value, row, index) {
		        	if(index<1){
		        		return;
		        	}
		        	$table_form.bootstrapTable('remove', {field: 'ID', values: row.ID});
		        	$table_form.bootstrapTable('insertRow', {index:index-1, row:row});
		        	def_pub_attr_fx();
		        }
		    };
	 
	 /*
	  * 反显自定义公共属性
	  */

	 function def_pub_attr_fx(){
	 	//1.0 遍历自定义属性
	 	var keys = PrivateAttrFormFiledMap.keys();
	 	if(keys.isEmpty()){
	 		return;
	 	}
	 	for(var i=0;i<keys.size();i++){
	 		var ID = keys.get(i);
	 		var attr = PrivateAttrFormFiledMap.get(ID);

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
 function form_table_onCheck(row,$element){
	 setRowAndColNum();
 }	
 
 function form_table_onCheckAll(rows){
	 setRowAndColNum();
 }
 
 function form_table_onUncheck(row,$element){
	 setRowAndColNum();
 }
 
 function form_table_onUncheckAll(rows){
	 setRowAndColNum();
 }
 
 /* 自动定义行列 */
 function setRowAndColNum(){
	 console.info('=======================');
	 //首先清空
	 var rows =$table_form.bootstrapTable('getData');
	 for(var i=0;i<rows.length;i++){
		 var row = rows[i];
		 row.c_row='0';
		 $table_form.bootstrapTable('updateByUniqueId',{
			 id:row.ID,
			 row:row
		 });
	 }
	 //设置
	 var colNum = PrivateAttrFormMap.get('colNum');
	 var selRows =$table_form.bootstrapTable('getAllSelections');
	 for(var i=0;i<selRows.length;i++){
		 var row = selRows[i];
		 row.c_row=parseInt(i/colNum)+1;
		 
		 $table_form.bootstrapTable('updateByUniqueId',{
			 id:row.ID,
			 row:row
		 });
	 }
	 
	 def_pub_attr_fx();
 }
 
 
 /*-------------4.0 私有属性-----------------------------*/
 /*
  * 文本框
  */
 function onExpand_text(index, row, $detail,sign){
	 var id = "text_"+sign+'_'+row.ID;
	 var formId = 'form_'+sign+row.ID;
	 $detail.html(
			 '<div>'+
			 '<form id="'+formId+'"  class="form-horizontal"> '+
			 '	<!-- 第一列 --> '+
			 '	<div class="col-md-2">                 '+
			 '		<div class="thumbnail">              '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">  '+
			 '				标准校验  '+
			 '			</h5>       '+
			 '			<div class="caption">              '+
			 '				<!-- 第一列 -->                  '+
			 '				<div class="checkbox checkbox-info">'+
			 '				 <input id="'+id+'anotEmpty" name="notEmpty" type="checkbox">'+
			 '			      <label for="'+id+'anotEmpty">'+
			 '			        	 不为空                 '+
			 '			      </label>                     '+
			 '			    </div>  '+
			 '			            '+
			 '				<div class="checkbox checkbox-info" >          '+
			 '					<input id="'+id+'integer" name="integer" type="checkbox">'+
			 '			      <label for="'+id+'integer">'+
			 '			        	 整数                    '+
			 '			      </label>                     '+
			 '			    </div>  '+
			 '			            '+
			 '			    <div class="checkbox checkbox-info">         '+
			 '					<input id="'+id+'amount" name="amount" type="checkbox">'+
			 '			      <label for="'+id+'amount">'+
			 '			                 金额                     '+
			 '			      </label>                     '+
			 '			    </div>  '+
			 '			            '+
			 '			    <div class="checkbox checkbox-info">         '+
			 '					<input id="'+id+'card" name="card" type="checkbox">'+
			 '			      <label for="'+id+'card">'+
			 '			        	 身份证                     '+
			 '			      </label>                     '+
			 '			    </div>  '+
			 '			            '+
			 '			    <div class="checkbox checkbox-info" >        '+
			 '					<input id="'+id+'tel" name="tel" type="checkbox">'+
			 '			      <label for="'+id+'tel">'+
			 '			        	 手机号'+
			 '			      </label>                     '+
			 '			    </div>  '+
			 '			            '+
			 '			    <div class="checkbox checkbox-info" >        '+
			 '					<input id="'+id+'mail" name="mail" type="checkbox">'+
			 '			      <label for="'+id+'mail">'+
			 '			         邮件'+
			 '			      </label>                     '+
			 '			    </div>  '+
			 '			</div>      '+
			 '		</div>        '+
			 '	</div>          '+
			 '	<!-- 第二列 --> '+
			 '	<div class="col-md-6">                 '+
			 '		<div class="thumbnail">              '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">  '+
			 '				自定义校验'+
			 '			</h5>       '+
			 '			<div class="caption">              '+
			 '				<!-- String Length  -->          '+
			 '				<div  class="form-group form-group-sm">           '+
			 '					<div class="col-sm-2">         '+
			 '						<label for="inputEmail3" class="control-label">'+
			 '							length：                   '+
			 '						</label>                     '+
			 '					</div>  '+
			 '					        '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									min                    '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="length_min"  class="form-control" placeholder="2" type="text">     '+
			 '						</div>'+
			 '					</div>  '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									max                    '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="length_max"  class="form-control" placeholder="6" type="text">     '+
			 '						</div>'+
			 '					</div>  '+
			 '				</div>    '+
			 '				          '+
			 '				<!-- Choile  -->                 '+
			 '				<div  class="form-group form-group-sm">           '+
			 '					<div class="col-sm-2">         '+
			 '						<label for="inputEmail3" class="control-label">'+
			 '							choile：                   '+
			 '						</label>                     '+
			 '					</div>  '+
			 '					        '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									min                    '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="choile_min"  class="form-control" placeholder="2" type="text">     '+
			 '						</div>'+
			 '					</div>  '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									max                    '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="choile_max"  class="form-control" placeholder="6" type="text">     '+
			 '						</div>'+
			 '					</div>  '+
			 '				</div>    '+
			 '				          '+
			 '				<!-- Regexp  -->                 '+
			 '				<div  class="form-group form-group-sm">           '+
			 '					<div class="col-sm-2">         '+
			 '						<label for="inputEmail3" class="control-label">'+
			 '							regexp：                   '+
			 '						</label>                     '+
			 '					</div>  '+
			 '					        '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									regexp                 '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="regexp_regexp"  class="form-control" placeholder="/^[a-zA-Z0-9_\.]+$/" type="text">       '+
			 '						</div>'+
			 '					</div>  '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									message                '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="regexp_message"  class="form-control" placeholder="用户名只能包含字符、数字" type="text"> '+
			 '						</div>'+
			 '					</div>  '+
			 '				</div>    '+
			 '				          '+
			 '				<!-- different  -->              '+
			 '				<div  class="form-group form-group-sm">           '+
			 '					<div class="col-sm-2">         '+
			 '						<label for="inputEmail3" class="control-label">'+
			 '							different：                '+
			 '						</label>                     '+
			 '					</div>  '+
			 '					        '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									field                  '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="different_field"  class="form-control" placeholder="field-name" type="text">              '+
			 '						</div>'+
			 '					</div>  '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									message                '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="different_message"  class="form-control" placeholder="密码和用户名不能相同" type="text">  '+
			 '						</div>'+
			 '					</div>  '+
			 '				</div>    '+
			 '				          '+
			 '				<!-- date  -->                   '+
			 '				<div  class="form-group form-group-sm">           '+
			 '					<div class="col-sm-2">         '+
			 '						<label for="inputEmail3" class="control-label">'+
			 '							date：                     '+
			 '						</label>                     '+
			 '					</div>  '+
			 '					        '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									formate                '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="date_formate"  class="form-control" placeholder="YYYY/MM/DD" type="text">                 '+
			 '						</div>'+
			 '					</div>  '+
			 '					<div class="col-sm-5">         '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon"  style="width: 95px">               '+
			 '								<span>                   '+
			 '									message                '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input name="date_message"  class="form-control" placeholder="日期格式无效" type="text">               '+
			 '						</div>'+
			 '					</div>  '+
			 '				</div>    '+
			 '				          '+
			 '			</div>      '+
			 '		</div>        '+
			 '	</div>          '+
			 '	<!-- 第三列 -->	'+
			 '	<div class="col-md-4">                 '+
			 '		<div class="thumbnail">              '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">  '+
			 '				组件属性  '+
			 '			</h5>       '+
			 '			<div class="caption">              '+
			 '				<div  class="form-group form-group-sm">           '+
			 '					<div class="col-sm-10">        '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon" style="width: 95px"><!--pageNumber -->               '+
			 '								<span>                   '+
			 '									ID                     '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input  name="t_id" class="form-control" placeholder="" type="text">          '+
			 '						</div>'+
			 '					</div>  '+
			 '				</div>    '+
			 '				          '+
			 '				<div  class="form-group form-group-sm">           '+
			 '					<div class="col-sm-10">        '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon" style="width: 95px"><!--pageSize -->'+
			 '								<span>                   '+
			 '									注解                   '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<input  name="t_placeholder" class="form-control" placeholder="" type="text">            '+
			 '						</div>'+
			 '					</div>  '+
			 '				</div>    '+
			 '				          '+
			 '				<div  class="form-group form-group-sm">           '+
			 '					<div class="col-sm-10">        '+
			 '						<div class="input-group">    '+
			 '							<div class="input-group-addon" style="width: 95px"><!--[10, 25, 50, 100, All] -->   '+
			 '								<span>                   '+
			 '									尺寸                   '+
			 '								</span>                  '+
			 '							</div>                     '+
			 '							<!-- <input  name="pageList" class="form-control" placeholder="[10, 25, All]" type="text"> -->             '+
		     '						<div class="radio radio-info radio-inline">'+
		     '							<input id="'+id+'t_sizemr" type="radio" name="t_size" checked="checked"  value="">'+
			 '							<label for="'+id+'t_sizemr" style="margin-left: 5px">              '+
			 '							   默认       '+
			 '							</label>                   '+
			 '                      </div>               '+
		     '						<div class="radio radio-info radio-inline">'+		
		     '                          <input id="'+id+'t_sizelg" type="radio" name="t_size" value="form-group-lg">'+
			 '							<label for="'+id+'t_sizelg">                '+
			 '							   大        '+
			 '							</label>                   '+
			 '                      </div>               '+			 
		     '						<div  class="radio radio-info radio-inline">'+			
		     '							<input id="'+id+'t_sizesm" type="radio" name="t_size"  value="form-group-sm">'+
			 '							<label for="'+id+'t_sizesm">                '+
			 '							   小        '+
			 '							</label>                   '+
			 '                      </div>               '+	
			 '						</div>'+
			 '					</div>  '+
			 '				</div>    '+
			 '			</div>      '+
			 '		</div>        '+
			 '	</div>          '+
			 '</form>'+
			 '<div>'
	 );
	 onExpand_text_cc(index, row, $detail,sign);
	 onExpand_text_fx(index, row, $detail,sign);
	 
 }
 
 /*
  * 获取属性填充到formateJson中
  */
 function onExpand_text_cc(index, row, $detail,sign){
	 var ID = row.ID;
	 var formId = '#form_'+sign+row.ID;
	 /*
	  * 兼容IE
	  */
	 $(formId+' input:checkbox').click(function (obj) {
		 this.blur();
		 this.focus();
	 });
	 
	 $(formId+' input:checkbox').change(function (e) {
		 var name = $(this).attr('name');
		 
		 var vs = getAttr(ID,'c_validator',sign);
		 if(vs==undefined){
			 vs = new Hashtable();
			 addAttr(ID,'c_validator',vs,sign);
		 }
		 if($(this).prop('checked')){//选中
			 vs.add(name,name);
		 }else{
			 vs.remove(name);
		 }
	 });  
	 
	 $(formId+' input').bind('input propertychange', function(e) { 
		 //进行相关操作
		 var val = $(this).val();
		 var name = $(this).attr('name');
		 var vs = getAttr(ID,'c_validator',sign);
		 if(vs==undefined){
			 vs = new Hashtable();
			 addAttr(ID,'c_validator',vs,sign);
		 }
		 if(val!==''){
			 vs.add(name,val);
		 }else{
			 vs.remove(name);
		 }
	 });
	 
	 
	 $(formId+' input:radio').click(function (obj) {
		 this.blur();
		 this.focus();
	 });
	 
	 $(formId+' input:radio').change(function (e) {
		 var name = $(this).attr('name');
		 var val = $(this).val();
		 
		 addAttr(ID,name,val,sign);
	 });  
	 
	 $(formId+' input').bind('input propertychange', function(e) { 
		 //进行相关操作
		 var val = $(this).val();
		 var name = $(this).attr('name');
		 
		 addAttr(ID,name,val,sign);
	 });
	 
 }
 
 function onExpand_text_fx(index, row, $detail,sign){
	//TODO 反显
	 var id = "select_"+row.ID;
	 var formId = '#form_'+sign+row.ID;
	 
	 var vs = getAttr(row.ID,'c_validator',sign);
	 
	 if(vs===undefined){
		 return;
	 }
	 if(vs.containsKey('notEmpty')){
		 $(formId+" input[name='notEmpty']").prop('checked',true);
	 }
	 if(vs.containsKey('integer')){
		 $(formId+" input[name='integer']").prop('checked',true);
	 }
	 if(vs.containsKey('amount')){
		 $(formId+" input[name='amount']").prop('checked',true);
	 }
	 if(vs.containsKey('card')){
		 $(formId+" input[name='card']").prop('checked',true);
	 }
	 if(vs.containsKey('tel')){
		 $(formId+" input[name='tel']").prop('checked',true);
	 }
	 if(vs.containsKey('mail')){
		 $(formId+" input[name='mail']").prop('checked',true);
	 }
	 //length_min
	 if(vs.containsKey('length_min')){
		 $(formId+" input[name='length_min']").val(vs.get('length_min'));
	 }
	 //length_max
	 if(vs.containsKey('length_max')){
		 $(formId+" input[name='length_max']").val(vs.get('length_max'));
	 }
	 //choile_min
	 if(vs.containsKey('choile_min')){
		 $(formId+" input[name='choile_min']").val(vs.get('choile_min'));
	 }
	 //choile_max
	 if(vs.containsKey('choile_max')){
		 $(formId+" input[name='choile_max']").val(vs.get('choile_max'));
	 }
	 //regexp_regexp
	 if(vs.containsKey('regexp_regexp')){
		 $(formId+" input[name='regexp_regexp']").val(vs.get('regexp_regexp'));
	 }
	 //regexp_message
	 if(vs.containsKey('regexp_message')){
		 $(formId+" input[name='regexp_message']").val(vs.get('regexp_message'));
	 }
	 //different_field
	 if(vs.containsKey('different_field')){
		 $(formId+" input[name='different_field']").val(vs.get('different_field'));
	 }
	 //different_message
	 if(vs.containsKey('different_message')){
		 $(formId+" input[name='different_message']").val(vs.get('different_message'));
	 }
	 //date_formate
	 if(vs.containsKey('date_formate')){
		 $(formId+" input[name='date_formate']").val(vs.get('date_formate'));
	 }
	 //date_message
	 if(vs.containsKey('date_message')){
		 $(formId+" input[name='date_message']").val(vs.get('date_message'));
	 }
 }
 
 /*
  * 下拉框
  */
 function onExpand_multiselect(index, row, $detail,sign){
	 /*var id = "multiselect_"+row.ID;*/
	 var id = "text_"+sign+'_'+row.ID;
	 var formId = 'form_'+sign+row.ID;
	 $detail.html(
			 '<form id="'+formId+'"  class="form-horizontal"> '+
			 '	<div class="col-md-3">         '+
			 '		<div class="thumbnail">      '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">                 '+
			 '				基本属性'+
			 '			</h5>     '+
			 '			<div class="caption">      '+
			 '				<div class="form-group form-group-sm">          '+
			 '					<div class="col-sm-12">'+
			 '						<div class="input-group" >                  '+
			 '							<div class="input-group-addon" style="width: 95px">        '+
			 '								<span>           '+
			 '									宽度             '+
			 '								</span>          '+
			 '							</div>             '+
			 '							<input  name="buttonWidth" class="form-control" placeholder="100" type="text">            '+
			 '						</div>               '+
			 '					</div>'+
			 '				</div>  '+			 
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="input-group">                   '+
			 '							<div class="input-group-addon" style="width: 95px">        '+
			 '								<span>           '+
			 '									远程URL        '+
			 '								</span>          '+
			 '							</div>             '+
			 '							<input  name="url" class="form-control" placeholder="" type="text">           '+
			 '						</div>               '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-11">'+
			 '						<div class="input-group col-sm-11">         '+
			 '							<div class="input-group-addon" style="width: 95px">        '+
			 '								<span>           '+
			 '									下拉框内容     '+
			 '								</span>          '+
			 '							</div>             '+
			 '							<textarea class="form-control " style="height: 153px" name="content" >           '+
			 '							</textarea>        '+
			 '						</div>               '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '			</div>    '+
			 '		</div>      '+
			 '	</div>        '+
			 '	              '+
			 '	<div class="col-md-3">         '+
			 '		<div class="thumbnail">      '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">                 '+
			 '				组件属性'+
			 '			</h5>     '+
			 '			<div class="caption">      '+
			 '				<div class="form-group form-group-sm">         '+
			 '					      '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info">                     '+
			 '							<input id="'+id+'notEmpty" name="notEmpty" type="checkbox">'+
			 '		 			      <label for="'+id+'notEmpty">          '+
			 '		 			        	 不为空     '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'multiple" name="multiple" type="checkbox">'+
			 '		 			      <label for="'+id+'multiple">          '+
			 '		 			         多选           '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'dropRight" name="dropRight" type="checkbox">'+
			 '		 			      <label for="'+id+'dropRight">          '+
			 '		 			         下拉菜靠左显示'+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'dropUp" name="dropUp" type="checkbox">'+
			 '		 			      <label for="'+id+'dropUp">          '+
			 '		 			         	下拉菜单朝上     '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'enableClickableOptGroups" name="enableClickableOptGroups" type="checkbox">'+
			 '		 			      <label for="'+id+'enableClickableOptGroups">          '+
			 '		 			         点击组全选            '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'enableCollapsibleOptGroups" name="enableCollapsibleOptGroups" type="checkbox">'+
			 '		 			      <label for="'+id+'enableCollapsibleOptGroups">          '+
			 '		 			         组可折叠            '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="input-group">                   '+
			 '							<div class="input-group-addon" style="width: 95px">        '+
			 '								<span>           '+
			 '									分隔符         '+
			 '								</span>          '+
			 '							</div>             '+
			 '							<input  name="delimiterText" class="form-control" placeholder="," type="text">    '+
			 '						</div>               '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '			</div>    '+
			 '		</div>      '+
			 '	</div>        '+
			 '	              '+
			 '	<div class="col-md-3">         '+
			 '		<div class="thumbnail">      '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">                 '+
			 '				组件属性'+
			 '			</h5>     '+
			 '			<div class="caption">      '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'includeSelectAllOption" name="includeSelectAllOption" type="checkbox">'+
			 '		 			      <label for="'+id+'includeSelectAllOption">          '+
			 '		 			         开启全选                  '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="input-group">                   '+
			 '							<div class="input-group-addon" style="width: 95px">        '+
			 '								<span>           '+
			 '									全选后显示文本 '+
			 '								</span>          '+
			 '							</div>             '+
			 '							<input  name="allSelectedText" class="form-control" placeholder="" type="text">   '+
			 '						</div>               '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'enableFiltering" name="enableFiltering" type="checkbox">'+
			 '		 			      <label for="'+id+'enableFiltering">          '+
			 '		 			        	 开启过滤器                     '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'enableFullValueFiltering" name="enableFullValueFiltering" type="checkbox">'+
			 '		 			      <label for="'+id+'enableFullValueFiltering">          '+
			 '		 			        	 开启全文本过滤        '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				        '+
			 '				        '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'disableIfEmpty" name="disableIfEmpty" type="checkbox">'+
			 '		 			      <label for="'+id+'disableIfEmpty">          '+
			 '		 			        	 无选项时禁用                    '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="input-group">                   '+
			 '							<div class="input-group-addon" style="width: 95px">        '+
			 '								<span>           '+
			 '									无选项禁用时显示文本                  '+
			 '								</span>          '+
			 '							</div>             '+
			 '							<input  name="disabledText" class="form-control" placeholder="" type="text">      '+
			 '						</div>               '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="input-group">                   '+
			 '							<div class="input-group-addon" style="width: 95px">        '+
			 '								<span>           '+
			 '									没有选项时显示文本                    '+
			 '								</span>          '+
			 '							</div>             '+
			 '							<input  name="nonSelectedText" class="form-control" placeholder="" type="text">   '+
			 '						</div>               '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '			</div>    '+
			 '		</div>      '+
			 '	</div>        '+
			 '	              '+
			 '	<div class="col-md-3">         '+
			 '		<div class="thumbnail">      '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">                 '+
			 '				事件    '+
			 '			</h5>     '+
			 '			<div class="caption">      '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'onChange" name="onChange" type="checkbox">'+
			 '		 			      <label for="'+id+'onChange">          '+
			 '		 			        	 onChange       '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'onInitialized"  name="onInitialized" type="checkbox">'+
			 '		 			      <label for="'+id+'onInitialized">          '+
			 '		 			         onInitialized(初始化后)          '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'onDropdownShow" name="onDropdownShow" type="checkbox">'+
			 '		 			      <label for="'+id+'onDropdownShow">          '+
			 '		 			         onDropdownShow                  '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'onDropdownHide" name="onDropdownHide" type="checkbox">'+
			 '		 			      <label for="'+id+'onDropdownHide">          '+
			 '		 			         onDropdownHide                  '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'onDropdownShown" name="onDropdownShown" type="checkbox">'+
			 '		 			      <label for="'+id+'onDropdownShown">          '+
			 '		 			         onDropdownShown                '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div class="form-group form-group-sm">          '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'onDropdownHidden" name="onDropdownHidden" type="checkbox">'+
			 '		 			      <label for="'+id+'onDropdownHidden">          '+
			 '		 			         onDropdownHidden              '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '				<div  class="form-group form-group-sm">         '+
			 '					<div class="col-sm-10">'+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'onSelectAll" name="onSelectAll" type="checkbox"> '+
			 '		 			      <label for="'+id+'onSelectAll">          '+
			 '		 			        onSelectAll '+
			 '		 			      </label>         '+
			 '		 			    </div>             '+
			 '					</div>'+
			 '				</div>  '+
			 '				        '+
			 '			</div>    '+
			 '		</div>      '+
			 '	</div>        '+
			 '</form>        '
	 );
	 
	 onExpand_multiselect_cc(index, row, $detail,sign);
	 onExpand_multiselect_fx(index, row, $detail,sign);
 }
 
 /*
  * 获取属性填充到formateJson中
  */
 function onExpand_multiselect_cc(index, row, $detail,sign){
	 var ID = row.ID;
	 var formId = '#form_'+sign+row.ID;
	 
	 $(formId+' input:checkbox').click(function (obj) {
		 this.blur();
		 this.focus();
	 });
	 
	 $(formId+' input:checkbox').change(function (e) {
		 var name = $(this).attr('name');
		 var val = $(this).val();
		 
		 if($(this).prop('checked')){//选中
			 /*vs.add(name,name);*/
			 addAttr(ID,name,val,sign);
		 }else{
			 removeAttr(ID,name,sign);
		 }
		 
	 }); 
	 
	 $(formId+' input').bind('input propertychange', function(e) { 
		 //进行相关操作
		 var val = $(this).val();
		 var name = $(this).attr('name');
		 
		 if(val!==''){
			 addAttr(ID,name,val,sign);
		 }else{
			 removeAttr(ID,name,sign);
		 }
		 
	 });
	 
	 $(formId+' textarea').bind('input propertychange', function(e) { 
		 //进行相关操作
		 var val = $(this).val();
		 var name = $(this).attr('name');
		 if(val!==''){
			 addAttr(ID,name,val,sign);
		 }else{
			 removeAttr(ID,name,sign);
		 }
	 });
	 
 }
 
 
 function onExpand_multiselect_fx(index, row, $detail,sign){
	 var formId = '#form_'+sign+row.ID;
	 if(undefined!==getAttr(row.ID,'content',sign)){
		 $(formId+" textarea[name='content']").val(getAttr(row.ID,'content',sign));
	 }
	 
	 var texts = [
		 'buttonWidth',
		 'url',
		 'delimiterText',
		 'delimiterText',
		 'disabledText',
		 'nonSelectedText'
		 ];
	 onExpand_fx_text(row.ID,texts,sign,formId);
	 
	 var checkboxs = [
		 'notEmpty',
		 'multiple',
		 'dropRight',
		 'dropUp',
		 'enableClickableOptGroups',
		 'enableCollapsibleOptGroups',
		 'includeSelectAllOption',
		 'enableFiltering',
		 'enableFullValueFiltering',
		 'disableIfEmpty',
		 'onChange',
		 'onInitialized',
		 'onDropdownShow',
		 'onDropdownHide',
		 'onDropdownShown',
		 'onDropdownHidden',
		 'onSelectAll'
		 ];
	 onExpand_fx_checkbox(row.ID,checkboxs,sign,formId);
 }
 
 function onExpand_fx_text(id,texts,sign,formId){
	 for(i in texts){
		 var name = texts[i];
		 if(undefined!==getAttr(id,name,sign)){
			 $(formId+" input[name='"+name+"']").val(getAttr(id,name,sign));
		 }
	 }
 }
 
 function onExpand_fx_checkbox(id,checkboxs,sign,formId){
	 for(i in checkboxs){
		 var name = checkboxs[i];
		 if(undefined!==getAttr(id,name,sign)){
			 $(formId+" input[name='"+name+"']").prop('checked',true);
		 }
	 }
 };
 /*
  * 多行文本框
  */
 function onExpand_textarea(index, row, $detail){
	 var id = "select_"+row.ID;
	 $detail.html(
			 'onExpand_textarea'
	 );
 }
 
 /*
  * 日期框
  */
 
 function onExpand_datatime(index, row, $detail,sign){
	/* var id = "datatime_"+row.ID;*/
	 var id = "datatime_"+sign+'_'+row.ID;
	 var formId = 'form_'+sign+row.ID;
	 $detail.html(
			 '<form id="'+formId+'"  class="form-horizontal"> '+
			 '	<div class="col-md-3">           '+
			 '		<div class="thumbnail">        '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">             '+
			 '				基本属性                   '+
			 '			</h5> '+
			 '			<div class="caption">        '+
			 '				<div class="form-group form-group-sm">      '+
			 '					<div class="input-group" >                '+
			 '						<div class="input-group-addon" style="width: 125px">           '+
			 '							<span>               '+
			 '								日期格式化         '+
			 '							</span>              '+
			 '						</div>                 '+
			 '						<input  name="format" value="yyyy-mm-dd HH:ii:ss" class="form-control" placeholder="yyyy-mm-dd HH:ii:ss" type="text">          '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '				    <div class="input-group">               '+
			 '				       <div class="input-group-addon" style="width: 125px">首显视图</div>           '+
			 '			     	   <select name="startView" data-role="multiselect" class="rainbow-select">     '+
			 '							<option value="4">   '+
			 '								年                 '+
			 '							</option>            '+
			 '							<option value="3">   '+
			 '								月                 '+
			 '							</option>            '+
			 '							<option selected="selected" value="2">'+
			 '								天                 '+
			 '							</option>            '+
			 '							<option value="1">   '+
			 '								时                 '+
			 '							</option>            '+
			 '							<option value="0">   '+
			 '								分                 '+
			 '							</option>            '+
			 '						</select>              '+
			 '				    </div>                 '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '				    <div class="input-group">               '+
			 '				       <div class="input-group-addon" style="width: 125px">显示范围最小</div>       '+
			 '			     	   <select name="minView" data-role="multiselect" class="rainbow-select">       '+
			 '							<option value="4">   '+
			 '								年                 '+
			 '							</option>            '+
			 '							<option value="3">   '+
			 '								月                 '+
			 '							</option>            '+
			 '							<option selected="selected" value="2">'+
			 '								天                 '+
			 '							</option>            '+
			 '							<option value="1">   '+
			 '								时                 '+
			 '							</option>            '+
			 '							<option value="0">   '+
			 '								分                 '+
			 '							</option>            '+
			 '						</select>              '+
			 '				    </div>                 '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '				    <div class="input-group">               '+
			 '				       <div class="input-group-addon" style="width: 125px">显示范围最大</div>       '+
			 '			     	   <select name="maxView" data-role="multiselect" class="rainbow-select">       '+
			 '							<option value="4">   '+
			 '								年                 '+
			 '							</option>            '+
			 '							<option  value="3">  '+
			 '								月                 '+
			 '							</option>            '+
			 '							<option value="2">   '+
			 '								天                 '+
			 '							</option>            '+
			 '							<option value="1">   '+
			 '								时                 '+
			 '							</option>            '+
			 '							<option selected="selected" value="0">'+
			 '								分                 '+
			 '							</option>            '+
			 '						</select>              '+
			 '				    </div>                 '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '				    <div class="input-group">               '+
			 '				       <div class="input-group-addon" style="width: 125px">日期选择器位置</div>     '+
			 '			     	   <select name="pickerPosition" data-role="multiselect" class=" rainbow-select">'+
			 '							<option value="bottom-right">         '+
			 '								下左               '+
			 '							</option>            '+
			 '							<option value="bottom-left">          '+
			 '								下右               '+
			 '							</option>            '+
			 '							<option value="top-right">            '+
			 '								上左               '+
			 '							</option>            '+
			 '							<option value="top-left">             '+
			 '								上右               '+
			 '							</option>            '+
			 '						</select>              '+
			 '				    </div>                 '+
			 '				</div>                     '+
			 '				    '+
			 '			</div>'+
			 '		</div>  '+
			 '	</div>    '+
			 '	          '+
			 '	<div class="col-md-3">           '+
			 '		<div class="thumbnail">        '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">             '+
			 '				组件属性                   '+
			 '			</h5> '+
			 '			<div class="caption">        '+
			 '				<div class="form-group form-group-sm">      '+
			 '					<div class="input-group" >                '+
			 '						<div class="input-group-addon" style="width: 125px">           '+
			 '							<span>               '+
			 '								可选开始日期       '+
			 '							</span>              '+
			 '						</div>                 '+
			 '						<input  name="startDate" class="form-control" placeholder="例：2016-01-10" type="text">          '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '					<div class="input-group" >                '+
			 '						<div class="input-group-addon" style="width: 125px">           '+
			 '							<span>               '+
			 '								可选结束日期       '+
			 '							</span>              '+
			 '						</div>                 '+
			 '						<input  name="endDate" class="form-control" placeholder="例：2017-01-10" type="text">          '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '					<div class="input-group" >                '+
			 '						<div class="input-group-addon" style="width: 125px">           '+
			 '							<span>               '+
			 '								分钟选择范围值     '+
			 '							</span>              '+
			 '						</div>                 '+
			 '						<input  name="minuteStep" class="form-control" placeholder="默认值：5" type="text">          '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '				    <div class="input-group">               '+
			 '				       <div class="input-group-addon" style="width: 125px">每周开始日期</div>       '+
			 '			     	   <select name="weekStart" data-role="multiselect" class=" rainbow-select">               '+
										'<option value="0" selected="selected">'+
											'星期日'+
										'</option>'+
										'<option value="1">'+
											'星期一'+
										'</option>'+
										'<option value="2">'+
											'星期二'+
										'</option>'+
										'<option value="3">'+
											'星期三'+
										'</option>'+
										'<option value="4">'+
											'星期四'+
										'</option>'+
										'<option value="5">'+
											'星期五'+
										'</option>'+
										'<option value="6">'+
											'星期六'+
										'</option>'+
			 '						</select>              '+
			 '				    </div>                 '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '				    <div class="input-group">               '+
			 '				       <div class="input-group-addon" style="width: 125px">禁用每周的某天</div>     '+
			 '			     	   <select name="daysOfWeekDisabled" data-role="multiselect" class=" rainbow-select">               '+
										 '<option value="0" selected="selected">'+
											'星期日'+
										'</option>'+
										'<option value="1">'+
											'星期一'+
										'</option>'+
										'<option value="2">'+
											'星期二'+
										'</option>'+
										'<option value="3">'+
											'星期三'+
										'</option>'+
										'<option value="4">'+
											'星期四'+
										'</option>'+
										'<option value="5">'+
											'星期五'+
										'</option>'+
										'<option value="6">'+
											'星期六'+
										'</option>'+
			 '						</select>              '+
			 '				    </div>                 '+
			 '				</div>                     '+
			 '				    '+
			 '			</div>'+
			 '		</div>  '+
			 '	</div>    '+
			 '	          '+
			 '	<div class="col-md-3">           '+
			 '		<div class="thumbnail">        '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">             '+
			 '				组件属性                   '+
			 '			</h5> '+
			 '			<div class="caption">        '+
			 '				<div  class="form-group form-group-sm">     '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'autoclose" name="autoclose" checked="checked" type="checkbox">'+
			 '		 			      <label for="'+id+'autoclose">            '+
			 '		 			        	 选择后关闭日期选择器       '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				    '+
			 '				<div  class="form-group form-group-sm">     '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'todayBtn" name="todayBtn" checked="checked" type="checkbox">'+
			 '		 			      <label for="'+id+'todayBtn">            '+
			 '		 			        	 显示今日按钮      '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm"> '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'showMeridian" name="showMeridian" type="checkbox">'+
			 '		 			      <label for="'+id+'showMeridian">            '+
			 '		 			        	 小时视图显示上、下午        '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>  '+
			 '				<div class="form-group form-group-sm"> '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'notEmpty" name="notEmpty" type="checkbox">'+
			 '		 			      <label for="'+id+'notEmpty">            '+
			 '		 			        	 不为空   '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>  '+			 
			 '				    '+
			 '			</div>'+
			 '		</div>  '+
			 '	</div>    '+
			 '	          '+
			 '	<div class="col-md-3">           '+
			 '		<div class="thumbnail">        '+
			 '			<h5  class="rainbow-select text-center text-info" data-rainbow="caption">             '+
			 '				事件'+
			 '			</h5> '+
			 '			<div class="caption">        '+
			 '				<div  class="form-group form-group-sm">     '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'show" name="show" type="checkbox">'+
			 '		 			      <label for="'+id+'show">            '+
			 '		 			         show             '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				<div  class="form-group form-group-sm">     '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'hide" name="hide" type="checkbox">'+
			 '		 			      <label for="'+id+'hide">            '+
			 '		 			         hide        '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				    '+
			 '				<div  class="form-group form-group-sm">     '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'changeYear" name="changeYear" type="checkbox">'+
			 '		 			      <label for="'+id+'changeYear">            '+
			 '		 			         changeYear '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				<div  class="form-group form-group-sm">     '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'changeMonth" name="changeMonth" type="checkbox">'+
			 '		 			      <label for="'+id+'changeMonth">            '+
			 '		 			         changeMonth                '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '				<div class="form-group form-group-sm">      '+
			 '					<div class="col-sm-10">  '+
			 '						<div class="checkbox checkbox-info"> '+
			 '							<input id="'+id+'outOfRange" name="outOfRange" type="checkbox">'+
			 '		 			      <label for="'+id+'outOfRange">            '+
			 '		 			         outOfRange                '+
			 '		 			      </label>           '+
			 '		 			    </div>               '+
			 '					</div>                   '+
			 '				</div>                     '+
			 '				    '+
			 '			</div>'+
			 '		</div>  '+
			 '	</div>    '+
			 '</form>    '
			 );
	 loadScript(ctxIde+'/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js');//多选框
	 onExpand_datatime_cc(index, row, $detail,sign);
	 onExpand_datatime_fx(index, row, $detail,sign);
 }
 
 function onExpand_datatime_cc(index, row, $detail,sign){
	 var ID = row.ID;
	 var formId = '#form_'+sign+row.ID;
	 $(formId+' input:checkbox').click(function (obj) {
		 this.blur();
		 this.focus();
	 });
	 
	 $(formId+' input:checkbox').change(function (e) {
		 var name = $(this).attr('name');
		 var val = $(this).val();
		
		 if($(this).prop('checked')){//选中
			 /*vs.add(name,name);*/
			 addAttr(ID,name,"true",sign);
		 }else{
			 addAttr(ID,name,"false",sign);
		 }
		 
	 }); 
	 
	 $(formId+' input').bind('input propertychange', function(e) { 
		 //进行相关操作
		 var val = $(this).val();
		 var name = $(this).attr('name');
		 
		 if(val!==''){
			 addAttr(ID,name,val,sign);
		 }else{
			 removeAttr(ID,name,sign);
		 }
	 });
	 
	 
	 $(formId+' select').multiselect({
	        onChange: function(option, checked) {
	            //更改选项时函数被触发
	        	var val = option.val();
	        	var name = option.parent().attr('name');
	        	addAttr(ID,name,val,sign);
	        }
	    });
 }
 
 function onExpand_datatime_fx(index, row, $detail,sign){
	 var formId = '#form_'+sign+row.ID;
	 //事件反显-beg 04
	 if('true'===getAttr(row.ID,'show',sign)){
		 $(formId+" input[name='show']").prop('checked',true);
	 }
	 if('true'===getAttr(row.ID,'hide',sign)){
		 $(formId+" input[name='hide']").prop('checked',true);
	 }
	 if('true'===getAttr(row.ID,'changeYear',sign)){
		 $(formId+" input[name='changeYear']").prop('checked',true);
	 }
	 if('true'===getAttr(row.ID,'changeMonth',sign)){
		 $(formId+" input[name='changeMonth']").prop('checked',true);
	 }
	 if('true'===getAttr(row.ID,'outOfRange',sign)){
		 $(formId+" input[name='outOfRange']").prop('checked',true);
	 }
	 //03
	 
	 if('false'===getAttr(row.ID,'autoclose',sign)){
		 $(formId+" input[name='autoclose']").prop('checked',false);
	 }
	 
	 if('false'===getAttr(row.ID,'todayBtn',sign)){
		 $(formId+" input[name='todayBtn']").prop('checked',false);
	 }
	 
	 if('true'===getAttr(row.ID,'showMeridian',sign)){
		 $(formId+" input[name='showMeridian']").prop('checked',true);
	 }
	 
	 if('true'===getAttr(row.ID,'notEmpty',sign)){
		 $(formId+" input[name='notEmpty']").prop('checked',true);
	 }
	 //02
	 
	 if(undefined!==getAttr(row.ID,'startDate',sign)){
		 $(formId+" input[name='startDate']").val(getAttr(row.ID,'startDate',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'endDate',sign)){
		 $(formId+" input[name='endDate']").val(getAttr(row.ID,'endDate',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'minuteStep',sign)){
		 $(formId+" input[name='minuteStep']").val(getAttr(row.ID,'minuteStep',sign));
	 }
	 
	 
	 if(undefined!==getAttr(row.ID,'weekStart',sign)){
		 $(formId+" select[name='weekStart']").multiselect('select', getAttr(row.ID,'weekStart',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'daysOfWeekDisabled',sign)){
		 $(formId+" select[name='daysOfWeekDisabled']").multiselect('select', getAttr(row.ID,'daysOfWeekDisabled',sign));
	 }
	 
	 //01
	 if(undefined!==getAttr(row.ID,'format',sign)){
		 $(formId+" input[name='format']").val(getAttr(row.ID,'format',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'startView',sign)){
		 $(formId+" select[name='startView']").multiselect('select', getAttr(row.ID,'startView',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'minView',sign)){
		 $(formId+" select[name='minView']").multiselect('select', getAttr(row.ID,'minView',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'maxView',sign)){
		 $(formId+" select[name='maxView']").multiselect('select', getAttr(row.ID,'maxView',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'pickerPosition',sign)){
		 $(formId+" select[name='pickerPosition']").multiselect('select', getAttr(row.ID,'pickerPosition',sign));
	 }
	//事件反显-end
	 
	 /*$('#form_'+row.ID+" input[name='notEmpty']").prop('checked',true);*/
 }
 
 
 function onExpand_checkbox(index, row, $detail,sign){
	 var id = "checkbox_"+sign+'_'+row.ID;
	 var formId = 'form_'+sign+row.ID;
	 $detail.html(
			 '<form id="'+formId+'"  class="form-horizontal"> '+
			 '	<div class="col-md-3"> '+
			 '		<div class="thumbnail">                                                           '+
			 '			                                                                                '+
			 '			<div class="caption">                                                           '+
			 '				<div  class="form-group form-group-sm">                                       '+
			 ' 					<div class="col-sm-11">                                                   '+
			 ' 						<div class="input-group col-sm-11">                                     '+
			 ' 							<div class="input-group-addon" style="width: 95px">                   '+
			 ' 								<span>                                                              '+
			 ' 									内容                                                              '+
			 ' 								</span>                                                             '+
			 ' 							</div>                                                                '+
			 ' 							<textarea class="form-control " style="height: 123px" name="content" >'+
			 ' 							</textarea>                                                           '+
			 ' 						</div>                                                                  '+
			 ' 					</div>                                                                    '+
			 ' 				</div>                                                                      '+
			 '				                                                                              '+
			 '			</div>                                                                          '+
			 '		</div>                                                                            '+
			 '	</div>                                                                              '+
			 '	                                                                                    '+
			 '	<div class="col-md-3">                                                              '+
			 '		<div class="thumbnail">                                                           '+
			 '			<div class="caption">                                                           '+
			 '			                                                                                '+
			 '				<div class="form-group form-group-sm">                                        '+
			 '					<div class="col-sm-11">                                                     '+
			 '				    <div class="input-group">                                                 '+
			 '				       <div class="checkbox checkbox-info">                                   '+
			 '							<input id="'+id+'notEmpty" name="notEmpty" type="checkbox" >                  '+
			 '							<label for="'+id+'notEmpty">                                                  '+
			 '								不为空                                                                '+
			 '							</label>                                                                '+
			 '					    </div>                                                                  '+
			 '					    </div>                                                                  '+
			 '				    </div>                                                                    '+
			 '				</div>                                                                        '+
			 '				                                                                              '+
			 '				<div class="form-group form-group-sm">                                        '+
			 '					<div class="col-sm-11">                                                     '+
			 '				    <div class="input-group">                                                 '+
			 '				       <div class="checkbox checkbox-info">                                   '+
			 '							<input id="'+id+'circle" name="circle" type="checkbox">                       '+
			 '							<label for="'+id+'circle">                                                    '+
			 '								圆角                                                                  '+
			 '							</label>                                                                '+
			 '					    </div>                                                                  '+
			 '					    </div>                                                                  '+
			 '				    </div>                                                                    '+
			 '				</div>  '+
			 '				 <div class="form-group form-group-sm">'+
			'					<div class="col-sm-11">'+
			'				    <div class="input-group">'+
			'				       <div class="checkbox checkbox-info">'+
			'							<input id="'+id+'inline" name="inline" type="checkbox">'+
			'							<label for="'+id+'inline">'+
			'								内联'+
			'							</label>'+
			'					    </div>'+
			'					    </div>'+
			'				    </div>'+
			'				</div>'+
			 '			                                                                                '+
			 '				<div class="form-group form-group-sm">                                        '+
			 '					<div class="col-sm-11">                                                     '+
			 '				    <div class="input-group">                                                 '+
			 '				       <div class="input-group-addon" style="width: 125px">颜色</div>         '+
			 '			     	   <select  name="colors" data-role="multiselect" class="rainbow-select"> '+
			 '							<option value="">                                                       '+
			 '								Default                                                               '+
			 '							</option>                                                               '+
			 '							<option value="checkbox-primary">                                       '+
			 '								Primary                                                               '+
			 '							</option>                                                               '+
			 '							<option value="checkbox-success">                                       '+
			 '								Success                                                               '+
			 '							</option>                                                               '+
			 '							<option value="checkbox-info" selected="selected">                      '+
			 '								Info                                                                  '+
			 '							</option>                                                               '+
			 '							<option value="checkbox-warning">                                       '+
			 '								Warning                                                               '+
			 '							</option>                                                               '+
			 '							<option value="checkbox-danger">                                        '+
			 '								danger                                                                '+
			 '							</option>                                                               '+
			 '						</select>                                                                 '+
			 '				    </div>                                                                    '+
			 '				</div>                                                                        '+
			 '				</div>                                                                        '+
			 '			</div>                                                                          '+
			 '		</div>                                                                            '+
			 '	</div>                                                                              '+
			 '</form>                                                                              '
	 );
	 loadScript(ctxIde+'/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js');//多选框
	 onExpand_checkbox_cc(index, row, $detail,sign);
	 onExpand_checkbox_fx(index, row, $detail,sign);
 }
 
 function onExpand_checkbox_cc(index, row, $detail,sign){
	 var ID = row.ID;
	 var formId = '#form_'+sign+row.ID;
	 $(formId+' input:checkbox').click(function (obj) {
		 this.blur();
		 this.focus();
	 });
	 
	 $(formId+' input:checkbox').change(function (e) {
		 var name = $(this).attr('name');
		 var val = $(this).val();
		
		 if($(this).prop('checked')){//选中
			 /*vs.add(name,name);*/
			 addAttr(ID,name,"true",sign);
		 }else{
			 addAttr(ID,name,"false",sign);
		 }
		 
	 }); 
	 
	 $(formId+' textarea').bind('input propertychange', function(e) { 
		 //进行相关操作
		 var val = $(this).val();
		 var name = $(this).attr('name');
		 if(val!==''){
			 addAttr(ID,name,val,sign);
		 }else{
			 removeAttr(ID,name,sign);
		 }
	 });
	 
	 
	 $(formId+' select').multiselect({
	        onChange: function(option, checked) {
	            //更改选项时函数被触发
	        	var val = option.val();
	        	var name = option.parent().attr('name');
	        	addAttr(ID,name,val,sign);
	        }
	    });
 }
 
 function onExpand_checkbox_fx(index, row, $detail,sign){
	 var formId = '#form_'+sign+row.ID;
	 if(undefined!==getAttr(row.ID,'colors',sign)){
		 $(formId+" select[name='colors']").multiselect('select', getAttr(row.ID,'colors',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'content',sign)){
		 $(formId+" textarea[name='content']").val(getAttr(row.ID,'content',sign));
	 }
	 
	 var checkboxs = [
		 'notEmpty',
		 'circle',
		 'inline'
		 ];
	 onExpand_fx_checkbox(row.ID,checkboxs,sign,formId);
 }

 
 function onExpand_radio(index, row, $detail,sign){
	 /*var id = "radio_"+row.ID;*/
	 var id = "radio_"+sign+'_'+row.ID;
	 var formId = 'form_'+sign+row.ID;
	 $detail.html(
			 '<form id="'+formId+'"  class="form-horizontal"> '+
			 '	<div class="col-md-3">                                                              '+
			 '		<div class="thumbnail">                                                           '+
			 '			                                                                                '+
			 '			<div class="caption">                                                           '+
			 '				<div  class="form-group form-group-sm">                                       '+
			 ' 					<div class="col-sm-11">                                                   '+
			 ' 						<div class="input-group col-sm-11">                                     '+
			 ' 							<div class="input-group-addon" style="width: 95px">                   '+
			 ' 								<span>                                                              '+
			 ' 									内容                                                              '+
			 ' 								</span>                                                             '+
			 ' 							</div>                                                                '+
			 ' 							<textarea class="form-control " style="height: 123px" name="content" >'+
			 ' 							</textarea>                                                           '+
			 ' 						</div>                                                                  '+
			 ' 					</div>                                                                    '+
			 ' 				</div>                                                                      '+
			 '				                                                                              '+
			 '			</div>                                                                          '+
			 '		</div>                                                                            '+
			 '	</div>                                                                              '+
			 '	                                                                                    '+
			 '	<div class="col-md-3">                                                              '+
			 '		<div class="thumbnail">                                                           '+
			 '			<div class="caption">                                                           '+
			 '			                                                                                '+
			 '				<div class="form-group form-group-sm">                                        '+
			 '					<div class="col-sm-11">                                                     '+
			 '				    <div class="input-group">                                                 '+
			 '				       <div class="checkbox checkbox-info">                                   '+
			 '							<input id="'+id+'notEmpty" name="notEmpty" type="checkbox" >                  '+
			 '							<label for="'+id+'notEmpty">                                                  '+
			 '								不为空                                                                '+
			 '							</label>                                                                '+
			 '					    </div>                                                                  '+
			 '					    </div>                                                                  '+
			 '				    </div>                                                                    '+
			 '				</div>                                                                        '+
			 '				                                                                              '+

			 '				 <div class="form-group form-group-sm">'+
			'					<div class="col-sm-11">'+
			'				    <div class="input-group">'+
			'				       <div class="checkbox checkbox-info">'+
			'							<input id="'+id+'inline" name="inline" type="checkbox">'+
			'							<label for="'+id+'inline">'+
			'								内联'+
			'							</label>'+
			'					    </div>'+
			'					    </div>'+
			'				    </div>'+
			'				</div>'+
			 '			                                                                                '+
			 '				<div class="form-group form-group-sm">                                        '+
			 '					<div class="col-sm-11">                                                     '+
			 '				    <div class="input-group">                                                 '+
			 '				       <div class="input-group-addon" style="width: 125px">颜色</div>         '+
			 '			     	   <select  name="colors" data-role="multiselect" class="rainbow-select"> '+
			 '							<option value="">                                                       '+
			 '								Default                                                               '+
			 '							</option>                                                               '+
			 '							<option value="radio-primary">                                       '+
			 '								Primary                                                               '+
			 '							</option>                                                               '+
			 '							<option value="radio-success">                                       '+
			 '								Success                                                               '+
			 '							</option>                                                               '+
			 '							<option value="radio-info" selected="selected">                      '+
			 '								Info                                                                  '+
			 '							</option>                                                               '+
			 '							<option value="radio-warning">                                       '+
			 '								Warning                                                               '+
			 '							</option>                                                               '+
			 '							<option value="radio-danger">                                        '+
			 '								danger                                                                '+
			 '							</option>                                                               '+
			 '						</select>                                                                 '+
			 '				    </div>                                                                    '+
			 '				</div>                                                                        '+
			 '				</div>                                                                        '+
			 '			</div>                                                                          '+
			 '		</div>                                                                            '+
			 '	</div>                                                                              '+
			 '</form>                                                                              '
	 );
	 loadScript(ctxIde+'/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js');//多选框
	 onExpand_radio_cc(index, row, $detail,sign);
	 onExpand_radio_fx(index, row, $detail,sign);
 }
 
 
 function onExpand_radio_cc(index, row, $detail,sign){
	 var ID = row.ID;
	 var formId = '#form_'+sign+row.ID;
	 $(formId+' input:checkbox').click(function (obj) {
		 this.blur();
		 this.focus();
	 });
	 
	 $(formId+' input:checkbox').change(function (e) {
		 var name = $(this).attr('name');
		 var val = $(this).val();
		
		 if($(this).prop('checked')){//选中
			 /*vs.add(name,name);*/
			 addAttr(ID,name,"true",sign);
		 }else{
			 addAttr(ID,name,"false",sign);
		 }
		 
	 }); 
	 
	 $(formId+' textarea').bind('input propertychange', function(e) { 
		 //进行相关操作
		 var val = $(this).val();
		 var name = $(this).attr('name');
		 if(val!==''){
			 addAttr(ID,name,val,sign);
		 }else{
			 removeAttr(ID,name,sign);
		 }
	 });
	 
	 
	 $(formId+' select').multiselect({
	        onChange: function(option, checked) {
	            //更改选项时函数被触发
	        	var val = option.val();
	        	var name = option.parent().attr('name');
	        	addAttr(ID,name,val,sign);
	        }
	    });
 }
 
 function onExpand_radio_fx(index, row, $detail,sign){
	 var formId = '#form_'+sign+row.ID;
	 if(undefined!==getAttr(row.ID,'colors',sign)){
		 $(formId+" select[name='colors']").multiselect('select', getAttr(row.ID,'colors',sign));
	 }
	 
	 if(undefined!==getAttr(row.ID,'content',sign)){
		 $(formId+" textarea[name='content']").val(getAttr(row.ID,'content',sign));
	 }
	 
	 var checkboxs = [
		 'notEmpty',
		 'circle',
		 'inline'
		 ];
	 onExpand_fx_checkbox(row.ID,checkboxs,sign,formId);
 }
 
 /*-------------4.0 初始化 PrivateAttrFormFiledMap--------*/
 /*
  *公共方法 table 方法补充  通过index获取row数据
  */
 
 function getRowByIndex(index){
	 var ID = $table_form.find('tbody').children('tr[data-index][data-uniqueid]').eq(index).children('td').eq(2).text();
	 return $table_form.bootstrapTable('getRowByUniqueId',ID);
 }
 
/*
 * 公共方法 存放到PrivateAttrFormFiledMap
 */ 
 
function addAttr(ID,attrKey,attrValue,sign){
	var attribute = undefined;
	
	if(sign==='query'){
		if(PrivateAttrQueryFiledMap.containsKey(ID)){
			attribute = PrivateAttrQueryFiledMap.get(ID);
		}else{
			attribute = new Hashtable();
			PrivateAttrQueryFiledMap.add(ID,attribute);
		}
		attribute.add(attrKey,attrValue);
	}else{
		if(PrivateAttrFormFiledMap.containsKey(ID)){
			attribute = PrivateAttrFormFiledMap.get(ID);
		}else{
			attribute = new Hashtable();
			PrivateAttrFormFiledMap.add(ID,attribute);
		}
		attribute.add(attrKey,attrValue);
	}
	
}

function removeAttr(ID,attrKey){
	var attribute = undefined;
	 if(PrivateAttrFormFiledMap.containsKey(ID)){
		 attribute = PrivateAttrFormFiledMap.get(ID);
		 attribute.remove(attrKey);
	 }
}

function getAttr(ID,attrKey,sign){
	console.info('-------------------------------');
	console.info(ID);
	console.info(attrKey);
	console.info(sign);
	if(sign==='query'){
		if(PrivateAttrQueryFiledMap.containsKey(ID)){
			if(PrivateAttrQueryFiledMap.get(ID).containsKey(attrKey)){
				console.info(PrivateAttrQueryFiledMap.get(ID).get(attrKey));
				return PrivateAttrQueryFiledMap.get(ID).get(attrKey);
			}else{
				return undefined;
			}
		}else{
			return undefined;
		}
	}else if(sign=='form'){
		if(PrivateAttrFormFiledMap.containsKey(ID)){
			if(PrivateAttrFormFiledMap.get(ID).containsKey(attrKey)){
				console.info(PrivateAttrFormFiledMap.get(ID).get(attrKey));
				return PrivateAttrFormFiledMap.get(ID).get(attrKey);
			}else{
				return undefined;
			}
		}else{
			return undefined;
		}
	}
}

/* function getHeight() {
     return $(window).height() - $('h1').outerHeight(true)-300;
 }*/