document.write("<script src='../bootstrap/js/util.js' type='text/javascript'></script>");

/*扩展combo多选下拉列表*/
$.extend($.fn.datagrid.defaults.editors, {
	   combo: {
	       init: function(container, options){
	           var input = $('<select id="cc"></select>').appendTo(container);
	           input.combo(options);
	           var html = "";
	           html+='<div id="sp">';
	           html+='<div style="color:#99BBE8;background:#fafafa;padding:5px;">'+options.title+'</div>';
	           for(var i=0;i<options.data.length;i++){
	           html+='<input type="checkbox" name="lang" value="'+options.data[i].id+'"><span>'+options.data[i].name+'</span><br/>';
	       }
	       html+='</div>';
	       $(html).appendTo($('#cc').combo('panel'));
	       $('#sp input').click(function(){
	           var _value="";
	           var _text="";
	           $("[name=lang]:input:checked").each(function(){
	               _value+=$(this).val()+",";
	               _text+=$(this).next("span").text()+",";
	           });
	           if(_value.length>0){
	               _value=_value.substring(0,_value.length-1);
	           }
	           if(_text.length>0){
	               _text=_text.substring(0,_text.length-1);
	           }
	               $('#cc').combo('setValue',_value).combo('setText', _text);
	           });
	           return input;
	       },
	       destroy: function(target){
	           $(target).combo('destroy');
	       },
	       getValue: function(target){
	       return $(target).combo('getValue');
	       },
	       setValue: function(target, value){
	           $(target).combo('setValue', value);
	       },
	       resize: function(target, width){
	           $(target).combo('resize',width);
	       }
	   }
	});

$(document).ready(function(){

});

$.extend($.fn.propertygrid.defaults.columns[0][1],{
	formatter:function(value,rowData,rowIndex){
		if(rowData.name==="容器"){//如果是"性格属性"
			return value=="container"?"固定宽度":"100%宽度";
		}else if(rowData.name==="标记"){
			if(value ===''){
				return '默认';
			}else if(value ==='text-muted'){
				return '禁用';
			}else if(value === 'text-warning'){
				return '警告';
			}else if(value ==='text-danger'){
				return '危险';
			}else if(value ==='text-error'){
				return '错误';
			}else if(value === 'text-info'){
				return '提示';
			}else if(value ==='text-success'){
				return '成功';
			}
		}else if(rowData.name==="对齐"){
			if(value ===''){
				return '默认';
			}else if(value ==='text-left'){
				return '靠左';
			}else if(value ==='text-right'){
				return '靠右';
			}else if(value ==='text-center'){
				return '居中';
			}else if(value === 'dl-horizontal'){
				return '竖向对齐';
			}else if(value ==='text-justify'){
				return '文本对齐';
			}
		}else if(rowData.name==="浮动"||rowData.name==="a浮动"){
			if(value ===''){
				return '默认';
			}else if(value ==='pull-left'){
				return '靠左';
			}else if(value ==='center-block'){
				return '居中';
			}else if(value ==='pull-right'){
				return '靠右';
			}
		}else if(rowData.name==="图片位置"){
			if(value ===''){
				return '默认';
			}else if(value ==='pull-left'){
				return '靠左';
			}else if(value ==='pull-right'){
				return '靠右';
			}else if(value ==='left'){
				return '左';
			}else if(value ==='right'){
				return '右(默认)';
			}
		}else if(rowData.name==="靠左/靠右"){
			if(value ===''){
				return '默认';
			}else if(value ==='navbar-left'){
				return '靠左';
			}else if(value ==='navbar-right'){
				return '靠右';
			}
		}else if(rowData.name==="对齐搜索输入"||rowData.name==="对齐按钮组"||rowData.name==="对齐toolbar"||rowData.name==="对齐分页(水平)"||rowData.name==="对齐分页描述(水平)"){
				if(value ==='left'){
					return '左';
				}else if(value ==='right'){
					return '右';
				}
		}else if(rowData.name==="列data对齐(竖直)"){
			if(value ===''){
				return '无';
			}else if(value ==='bottom'){
				return '底';
			}else if(value ==='middle'){
				return '中';
			}else if(value === 'top'){
				return '顶';
			}
		}else if(rowData.name==="对齐分页(竖直)"){
			if(value ==='top'){
				return '顶部';
			}else if(value ==='bottom'){
				return '底部';
			}else if(value === 'both'){
				return '顶部和底部';
			}
		}else if(rowData.name==="时间类型"){
			if(value ==='datetime'){
				return '日期时间';
			}else if(value ==='date'){
				return '日期';
			}else if(value === 'time'){
				return '时间';
			}
		}else if(rowData.name==="列footer对齐"||rowData.name==="列heading对齐"||rowData.name==="列data对齐"){
			if(value ===''){
				return '无';
			}else if(value ==='left'){
				return '左';
			}else if(value ==='center'){
				return '中';
			}else if(value ==='right'){
				return '右';
			}
		}else if(rowData.name==="table类名称"){
			if(value ==='table table-hover'){
				return '默认(hover)';
			}else if(value === 'table-no-bordered'){
				return '移除表格边框';
			}
		}else if(rowData.name==="排序方式"){
			if(value ==='asc'){
				return '升序';
			}else if(value === 'desc'){
				return '降序';
			}
		}else if(rowData.name==="改变大小写"){
			if(value ===''){
				return '默认';
			}else if(value ==='text-capitalize'){
				return '首字母大写';
			}else if(value === 'text-lowercase'){
				return '小写';
			}else if(value ==='text-uppercase'){
				return '大写';
			}
		}else if(rowData.name==="Lead"){
			if(value ===''){
				return '默认';
			}else if(value ==='lead'){
				return '突出显示';
			}
		}else if(rowData.name==="样式"||rowData.name==="label样式"||rowData.name==="btnType"){
			if(value ==='table-default'||value ==='label-default'||value ==='btn-default'||value ===''||value==='panel-default'){
				return '默认';
			}else if(value ==='table-striped'){
				return '条纹状表格';
			}else if(value ==='table-bordered'){
				return '带边框的表格';
			}else if(value ==='label-primary'||value ==='btn-primary'||value ==='panel-primary'){
				return '原生';
			}else if(value ==='label-success'||value ==='btn-success'||value ==='panel-success'||value ==='progress-bar-success'||value ==='list-group-item-success'){
				return '成功';
			}else if(value ==='label-info'){
				return '提示';
			}else if(value ==='btn-info'||value ==='progress-bar-info'||value ==='list-group-item-info'||value ==='panel-info'){
				return '信息';
			}else if(value ==='btn-warning'||value ==='progress-bar-warning'||value ==='list-group-item-warning'||value ==='panel-warning'){
				return '提醒';
			}else if(value ==='btn-inverse'){
				return '反转';
			}else if(value ==='btn-link'){
				return '链接';
			}else if(value ==='label-warning'||value ==='progress-bar-danger'){
				return '警告';
			}else if(value ==='label-danger'||value ==='btn-danger'||value ==='list-group-item-danger'||value ==='panel-danger'){
				return '危险';
			}else if(value ==='img-rounded'){
				return '圆角';
			}else if(value ==='img-circle'){
				return '圆圈';
			}else if(value ==='img-thumbnail'){
				return '相框';
			}else if(value ==='navbar-static-top'){
				return '静止顶部';
			}else if(value ==='navbar-fixed-top'){
				return '固定顶部';
			}else if(value ==='navbar-fixed-bottom'){
				return '固定底部';
			}else if(value ==='nav nav-tabs'){
				return '标签样式';
			}else if(value ==='nav nav-pills'){
				return '胶囊样式';
			}else if(value ==='nav nav-pills nav-stacked'){
				return '垂直胶囊式';
			}else if(value ==='nav nav-pills nav-justified'){
				return '两端对齐';
			}
		}else if(rowData.name ==="排列方式"){
			if(value === ''||value ==='default'||value ==='checkbox'||value ==='radio'){
				return '默认';
			}else if(value ==='form-inline'||value ==='inline'||value ==='checkbox-inline'||value ==='radio-inline'){
				return '内联';
			}else if(value ==='form-horizontal'||value ==='horizontal'||value ==='checkbox-horizontal'||value ==='radio-horizontal'){
				return '水平';
			}
		}else if(rowData.name==='左占'){
			 if(value ==='col-sm-1 control-label'||value ==='col-sm-1'){
				return 'col-sm-1';
			}else if(value ==='col-sm-2 control-label'||value ==='col-sm-2'){
				return 'col-sm-2';
			}else if(value ==='col-sm-3 control-label'||value ==='col-sm-3'){
				return 'col-sm-3';
			}else if(value ==='col-sm-4 control-label'||value ==='col-sm-4'){
				return 'col-sm-4';
			}else if(value ==='col-sm-5 control-label'||value ==='col-sm-5'){
				return 'col-sm-5';
			}else if(value ==='col-sm-6 control-label'||value ==='col-sm-6'){
				return 'col-sm-6';
			}else if(value ==='col-sm-7 control-label'||value ==='col-sm-7'){
				return 'col-sm-7';
			}else if(value ==='col-sm-8 control-label'||value ==='col-sm-8'){
				return 'col-sm-8';
			}else if(value ==='col-sm-9 control-label'||value ==='col-sm-9'){
				return 'col-sm-9';
			}else if(value ==='col-sm-10 control-label'||value ==='col-sm-10'){
				return 'col-sm-10';
			}else if(value ==='col-sm-11 control-label'||value ==='col-sm-11'){
				return 'col-sm-11';
			}else if(value ==='col-sm-12 control-label'||value ==='col-sm-12'){
				return 'col-sm-12';
				}
 			}else if(rowData.name==='右占'){
				 if(value ==='btn-group col-sm-1'||value ==='col-sm-1'){
					return 'col-sm-1';
				}else if(value ==='btn-group col-sm-2'||value ==='col-sm-2'){
					return 'col-sm-2';
				}else if(value ==='btn-group col-sm-3'||value ==='col-sm-3'){
					return 'col-sm-3';
				}else if(value ==='btn-group col-sm-4'||value ==='col-sm-4'){
					return 'col-sm-4';
				}else if(value ==='btn-group col-sm-5'||value ==='col-sm-5'){
					return 'col-sm-5';
				}else if(value ==='btn-group col-sm-6'||value ==='col-sm-6'){
					return 'col-sm-6';
				}else if(value ==='btn-group col-sm-7'||value ==='col-sm-7'){
					return 'col-sm-7';
				}else if(value ==='btn-group col-sm-8'||value ==='col-sm-8'){
					return 'col-sm-8';
				}else if(value ==='btn-group col-sm-9'||value ==='col-sm-9'){
					return 'col-sm-9';
				}else if(value ==='btn-group col-sm-10'||value ==='col-sm-10'){
					return 'col-sm-10';
				}else if(value ==='btn-group col-sm-11'||value ==='col-sm-11'){
					return 'col-sm-11';
				}else if(value ==='btn-group col-sm-12'||value ==='col-sm-12'){
					return 'col-sm-12';
				}
		}else if(rowData.name ==="提示框样式"){
			if(value === 'alert alert-success alert-dismissable'){
				return 'Success';
			}else if(value ==='alert alert-info alert-dismissable'){
				return 'Info';
			}else if(value ==='alert alert-warning alert-dismissable'){
				return 'warning';
			}else if(value ==='alert alert-danger alert-dismissable'){
				return 'Danger';
		}
		}else if(rowData.name ==="自动轮播"){
			if(value ==='undefined'){
				return '否';
			}else if(value === 'carousel'){
				return '是';
			}
		}else if(rowData.name ==="鼠标悬停"){
			if(value === 'hover' || value === 'undefined'|| value === 'table-hover'){
				return '是';
			}else{
				return '否';
			}
		}else if(rowData.name ==="循环播放"){
			if(value === 'true' || value === 'undefined'){
				return '是';
			}else{
				return '否';
			}
		}else if(rowData.name ==="折叠展示样式"){
			if(value === 'true'){
				return '只允许打开一个';
			}else if(value ==='false'){
				return '允许打开多个';
			}
		}else if(rowData.name ==="紧缩表格"){
				if(value === ''){
					return '默认';
				}else if(value ==='table-condensed'){
					return '紧缩表格';
			}
		}else if(rowData.name ==="禁止渐变"){
			if(value ==='tab-pane'){
				return '是';
			}else if(value ==='tab-pane fade'){
				return '否';
		}
		}else if(rowData.name ==="禁用"||rowData.name ==="只读"){
			if(value === 'true'){
				return '是';
			}else if(value ==='false'){
				return '否';
		}
		}else if(rowData.name ==="前裹类型"){
			if(value === 'undefined'){
				return '无内容';
			}else if(value === 'text'){
				return '文本框';
			}else if(value ==='checkbox'){
				return '多选框';
			}else if(value ==='radio'){
				return '单选框';
			}else if(value ==='button'){
				return '按钮';
			}else if(value ==='dropmenu'){
				return '下拉菜单';
			}else if(value ==='splitdropmenu'){
				return '分列式下拉菜单';
		}
		}else if(rowData.name ==="后裹类型"){
			if(value === 'undefined'){
				return '无';
			}else if(value === 'text'){
				return '文本框';
			}else if(value ==='checkbox'){
				return '多选框';
			}else if(value ==='radio'){
				return '单选框';
			}else if(value ==='button'){
				return '按钮';
			}else if(value ==='dropmenu'){
				return '下拉菜单';
			}else if(value ==='splitdropmenu'){
				return '分列式下拉菜单';
		}
		}else if(rowData.name ==="两侧内补"){
			if(value === ''){
				return '默认';
			}else if(value ==='container'){
				return '固定宽度';
			}else if(value ==='container-fluid'){
				return '百分比宽度';
		}
		}else if(rowData.name ==="反转"){
			if(value === ''){
				return '默认';
			}else if(value ==='navbar-inverse'){
				return '黑白';
		}
		}else if(rowData.name ==="响应式"){
				if(value === ''){
					return '默认';
				}else if(value ==='table-responsive'){
					return '响应式表格';
				}else if(value ==='img-responsive'){
					return '响应式图片';

			}
		}else if(rowData.name==='type'){
			if(value ==='button'){
				return '默认';
			}else if(value ==='submit'){
				return 'submit';
			}else if(value === 'reset'){
				return 'reset';
			}else if(value ==='email'){
				return 'email';
			}else if(value === 'password'){
				return 'password';
			}else if(value ==='text'){
					return 'text';
			}
		}else if(rowData.name==='aType'){
			if(value ==='button'){
				return 'button按钮';
			}else if(value ==='label'){
				return 'label标签';
			}else if(value ===''){
					return '默认';
			}
		}else if(rowData.name==='朝向'){
			if(value ===''){
				return '默认';
			}else if(value ==='dropup'){
				return '向上';
			}
		}else if(rowData.name==='隐藏label'||rowData.name==='隐藏'){
			if(value === ''){
				return '否';
			}else if(value ==='visibility:hidden'){
				return '是';
			}else if(value ==='visibility:visible'){
				return '否';
			}
		}else if(rowData.name==='尺寸'){
			if(value ===''||value ==='form-group'){
				return '默认';
			}else if(value ==='btn-lg'||value ==='btn-group-lg'||value ==='pagination-lg'||value ==='form-group-lg'||value ==='form-group form-group-lg'){
				return '大';
			}else if(value === 'btn-sm'||value ==='btn-group-sm'||value ==='pagination-sm'||value ==='form-group-sm'||value ==='form-group form-group-sm'){
				return '小';
			}else if(value ==='btn-group-md'){
				return '中';
			}else if(value ==='btn-group-xs'||value ==='btn-xs'){
				return '微型';
			}
		}else if(rowData.name==='btnSize'){
			if(value ===''){
				return '默认';
			}else if(value ==='btn-lg'){
				return '大按钮';
			}else if(value === 'btn-sm'){
				return '小按钮';
			}else if(value ==='btn-xs'){
				return '微型按钮';
			}
		}else if(rowData.name==='内嵌'){
			if(value ===''){
				return '默认';
			}else if(value ==='dropmenu'||value ==='dropdown'){
				return '下拉菜单';
			}else if(value === 'badge'){
				return '徽章';
			}else if(value === 'i'||value === 'image'){
				return '图片';
			}
		}else if(rowData.name==='类型'){
			if(value ==='a'){
				return 'a(默认)';
			}else if(value ==='li'){
				return 'li';
			}else if(value ==='button'){
				return 'button';
			}
		}else if(rowData.name==='方向'){
			if(value ===''){
				return '横向';
			}else if(value ==='btn-group-vertical'){
				return '纵向';
			}
		}else if(rowData.name==='下拉方向'){
			if(value ===''){
				return '向下（默认）';
			}else if(value ==='dropup'){
				return '向上';
			}
		}else if(rowData.name==='标签类型'){
			if(value ==='nav-tabs'){
				return '标签页';
			}else if(value ==='nav-pills'){
				return '胶囊式标签页';
			}
		}else if(rowData.name==='标签样式'){
			if(value ===''){
				return '默认';
			}else if(value ==='nav-stacked'){
				return '竖直排列';
			}else if(value ==='nav-justified'){
				return '两端对齐';
			}
		}else if(rowData.name==='禁用'){
			if(value ==='undefined'){
				return '否';
			}else if(value ==='disabled'){
				return '是';
			}
		}else if(rowData.name==='只读'){
			if(value ==='undefined'){
				return '否';
			}else if(value ==='readonly'){
				return '是';
			}
		}else if(rowData.name==='边框样式'){
			if(value ===''){
				return '默认';
			}else if(value ==='solid'){
				return '实线边框';
			}else if(value ==='dashed'){
				return '虚线边框';
			}
		}else{
			return value;
		}
	}
});

function getImgpArrMap(msg){
	var _map = new Hashtable();
	var atts=msg.split(",,");
	for(var i=0;i<atts.length;i++){
		var s = atts[i].split('=');
		_map.add(s[0], s[1]);
	}
	return _map;
}

/*分隔个组件的属性的分隔符为@#!*/
function getArrMap3(msg) {
	console.info('========================= get attr map by @#! ==========================');
	console.info(msg);
	var _map = new Hashtable();
	var atts=msg.split("@#!");
	for(var i=0;i<atts.length;i++){
		var s = atts[i].split('=');
		_map.add(s[0], s[1]);
	}
	return _map;
}

function getArrMap(msg) {
	console.info('=========================get attr map==========================');
	console.info(msg);
	var _map = new Hashtable();
	var atts=msg.split(",");
	for(var i=0;i<atts.length;i++){
		var s = atts[i].split('=');
		_map.add(s[0], s[1]);
	}
	return _map;
}

/*分隔校验的属性*/
function getArrMap2(msg) {
	console.info('=========================get attr map==========================');
	console.info(msg);
	var _map = new Hashtable();
	var atts=msg.split("$$");
	for(var i=0;i<atts.length;i++){
		var s = atts[i].split('=');
		_map.add(s[0], s[1]);
	}
	return _map;
}

function updataRow(index,val) {
	$('#dataGrid').datagrid('updateRow',{
		index: index,
		row: {
			value:val
		}
	});
}


function deleteRow(index) {
	$('#dataGrid').datagrid('deleteRow',index);
}

function mesHandler(msg) {
	var attMap = getArrMap(msg);
	var attMap2 = getArrMap2(msg);
	var attMap3 = getArrMap3(msg);
    if(attMap3.get('eleType')=='button'){
    	_button(attMap3);
    }else if(attMap.get('eleType')=='input'){
    	_input(attMap);
    }else if(attMap.get('eleType')=='label'){
    	_label(attMap);
    }else if(attMap.get('eleType')=='textarea'){
    	_textarea(attMap);
    }else if(attMap3.get('eleType')=='select'){
    	_select(attMap3);
    }else if(attMap.get('eleType')=='table'){
    	 attMap = getImgpArrMap(msg);
    	_table(attMap);
    }else if(attMap.get('eleType')== 'tabCol'){
    	_tableCol(attMap);
    }else if(attMap.get('eleType')=='h3'){
    	_h(attMap);
    }else if(attMap.get('eleType')=='p'){
    	_p(attMap);
    }else if(attMap.get('eleType')==='address'){
    	_address(attMap);
    }else if(attMap.get('eleType')==='btn_group'){
    	_btnGroup(attMap);
    }else if(attMap.get('eleType')==='down_menu'){
    	_downMenu(attMap);
    }else if(attMap.get('eleType')==='dl'){
    	_dl(attMap);
    }else if(attMap.get('eleType')==='span'){
    	_span(attMap);
    }else if(attMap.get('eleType')==='img'){
    	_img(attMap);
    }else if(attMap.get('eleType')=='navigations'){
    	_navigation(attMap);
    }else if(attMap3.get('eleType')=='a'){
    	_a(attMap3);
    }else if(attMap.get('eleType')==='navbar'){
    	_navbar(attMap);
    }else if(attMap.get('eleType')=='path_navigation'){
    	_pathNav(attMap);
    }else if(attMap.get('eleType')=='paging'){
    	_paging(attMap);
    }else if(attMap.get('eleType')==='form'){
    	
    	_form(attMap);
    }else if(attMap.get('eleType')==='btn_toolbar'){
    	
    	_btnToolbar(attMap);
    }else if(attMap3.get('eleType')==='grid'){
    	_grid(attMap3);
    }else if(attMap.get('eleType')==='turnPage'){
    	
    	_turnPage(attMap);
    }else if(attMap.get('eleType')==='radioAndCheckbox')
    {
    	_radioAndCheckboxBoard(attMap);
    }else if(attMap.get('eleType')==="radioCheckbox")
    {
    	_radioCheckbox(attMap);
    }else if(attMap.get('eleType')==='progressbar'){
    	
    	_progressBar(attMap);
    }else if(attMap.get('eleType')==='panel'){
    	
    	_panel(attMap);
    }else if(attMap3.get('eleType')==='row'){
    	_row(attMap3);
    }else if(attMap.get('eleType')==='thumbnail'){
    	
    	_thumbnail(attMap);
    }else if(attMap.get('eleType')==='listGroup'){
    	
    	_listGroup(attMap);
    }else if(attMap.get('eleType')==='media'){
    	_media(attMap);
    }else if(attMap.get('eleType')==='hint'){
    	_hint(attMap);
    }else if(attMap.get('eleType')==='fold')
    {
    	_fold(attMap);
    }else if(attMap.get('eleType')==='slidel')
    {
    	_slidel(attMap);
    }else if(attMap3.get('eleType')==='tab')
    {
    	_tab(attMap3);
    }else if(attMap.get('eleType')==='jumbotron'){
    	_jumbotron(attMap);
    }else if(attMap.get('eleType')==='container'){
    	_container(attMap);
    }else if(attMap.get('eleType')==='imgp'){
//    	attMap = getImgpArrMap(msg);
    	_imgp(attMap);
    }else if(attMap3.get('eleType')==='time'){
    	_time(attMap3);
    }else if(attMap.get('eleType')==='canvas_bing'||attMap.get('eleType')==='canvas_huan'||attMap.get('eleType')==='canvas_diji'){
    	_charPie(attMap);
    }else if(attMap.get('eleType')==='canvas_zhu'||attMap.get('eleType')==='canvas_leida'||attMap.get('eleType')==='canvas_quxian'){
    	_charBar(attMap);
    }else if(attMap.get('eleType')==='fileInput'){
    	_fileInput(attMap);
    }else if(attMap.get('eleType')==='modal'){
    	console.info('------------frame modal---------');
    	_modal(attMap);
    }
    /*20160817 add by chenyl 日期时间组件属性值处理*/
    else if(attMap.get('eleType')==='datetime'){
    	console.info('------------获取到datetime属性值---------');
    	_datetime(attMap);
    }
    
    /*20161019 add by chenyl 搜索树组件属性值处理*/
    if(attMap2.get('eleType')==='treesearch'){
    	console.info('------------获取到treesearch属性值---------');
    	_treesearch(attMap2);
    }
    
    /*20180612 add by chenyl 图标选择组件属性值处理*/
    if(attMap2.get('eleType')==='iconselect'){
    	console.info('------------获取到iconselect属性值---------');
    	_iconselect(attMap2);
    }
    
    /*20180618 add by chenyl 富文本编辑器组件属性值处理*/
    if(attMap2.get('eleType')==='ueditor'){
    	console.info('------------获取到ueditor属性值---------');
    	_ueditor(attMap2);
    }
    
    /*20180620 add by chenyl 文件管理组件属性值处理*/
    if(attMap2.get('eleType')==='ckfinder'){
    	console.info('------------获取到ckfinder属性值---------');
    	_ckfinder(attMap2);
    }
   	
    /*20180620 add by chenyl 文件管理组件属性值处理*/
    if(attMap2.get('eleType')==='input_hidden'){
    	console.info('------------获取到hidden属性值---------');
    	_hidden(attMap2);
    }
}

function _modal(attMap) {
	console.info('----------attMap----------');
	console.info(attMap);
	console.info(attMap.get('id'));
	var reData="";
	$('#dataGrid').propertygrid({
		url: 'Modal.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			
			if(attMap.get('width')!='undefined'){
				updataRow(1,attMap.get('width'));
			}
			if(attMap.get('isShow')!='undefined'){
				if(attMap.get('isShow')==='true'){
					updataRow(2,'显示');
				}else{
					updataRow(2,'隐藏');
				}
			}
			if(attMap.get('isHeaderClose')!='undefined'){
				if(attMap.get('isHeaderClose')==='true'){
					updataRow(3,'显示');
				}else{
					updataRow(3,'隐藏');
				}
			}
			if(attMap.get('headerTitle')!='undefined'){
				var t = attMap.get('headerTitle').trim();
				updataRow(4,t);
			}

			reData= attMap.rows;
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			sendMessage('ide',row);
		}
	});
	return reData;
	
}


function containsRow(name) {
	var rows = $('#dataGrid').datagrid('getRows');
	for(var i =0;i<rows.length;i++){
		if(rows[i].name ===name){
			return true;
		}
	}
	return false;
}

function getRowValue(rownum){
	var rows = $('#dataGrid').datagrid('getRows');
	return rows[rownum].value;
}

function _container(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'container.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			
			if(attMap.get('class')!='undefined'){
				updataRow(0,attMap.get('class'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			sendMessage('ide',row);
		}
	});
}

function _row(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'row.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('margin')!='undefined'){
				updataRow(1,attMap.get('margin'));
			}
			if(attMap.get('background')!='undefined'){
				updataRow(2,attMap.get('background'));
			}
			if(attMap.get('borderStyle')!='undefined'){
				updataRow(3,attMap.get('borderStyle'));
			}
			if(attMap.get('borderWidth')!='undefined'){
				updataRow(4,attMap.get('borderWidth'));
			}
			if(attMap.get('borderColor')!='undefined'){
				updataRow(5,attMap.get('borderColor'));
			}
			
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			sendMessage('ide',row);
		}
	});
}

function _grid(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'grid.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(1,attMap.get('style'));
			}
			if(attMap.get('margin')!='undefined'){
				updataRow(2,attMap.get('margin'));
			}
			if(attMap.get('padding')!='undefined'){
				updataRow(3,attMap.get('padding'));
			}
			if(attMap.get('background')!='undefined'){
				updataRow(4,attMap.get('background'));
			}
			if(attMap.get('域名')!='undefined'){
				updataRow(5,attMap.get('域名'));
			}
			if(attMap.get('borderStyle')!='undefined'){
				updataRow(6,attMap.get('borderStyle'));
			}
			if(attMap.get('borderWidth')!='undefined'){
				updataRow(7,attMap.get('borderWidth'));
			}
			if(attMap.get('borderColor')!='undefined'){
				updataRow(8,attMap.get('borderColor'));
			}
			if(attMap.get('textAlign')!='undefined'){
				updataRow(9,attMap.get('textAlign'));
			}
			if(attMap.get('col_xs')!='undefined'){
				updataRow(10,attMap.get('col_xs'));
			}
			if(attMap.get('col_sm')!='undefined'){
				updataRow(11,attMap.get('col_sm'));
			}
			if(attMap.get('col_md')!='undefined'){
				updataRow(12,attMap.get('col_md'));
			}
			if(attMap.get('col_lg')!='undefined'){
				updataRow(13,attMap.get('col_lg'));
			}
			
			if(attMap.get('col_xs_offset')!='undefined'){
				updataRow(14,attMap.get('col_xs_offset'));
			}
			if(attMap.get('col_sm_offset')!='undefined'){
				updataRow(15,attMap.get('col_sm_offset'));
			}	
			if(attMap.get('col_md_offset')!='undefined'){
				updataRow(16,attMap.get('col_md_offset'));
			}	
			if(attMap.get('col_lg_offset')!='undefined'){
				updataRow(17,attMap.get('col_lg_offset'));
			}
			
			if(attMap.get('hidden-xs')!='undefined'){
				updataRow(18,attMap.get('hidden-xs'));
			}
			if(attMap.get('hidden-sm')!='undefined'){
				updataRow(19,attMap.get('hidden-sm'));
			}
			if(attMap.get('hidden-md')!='undefined'){
				updataRow(20,attMap.get('hidden-md'));
			}
			if(attMap.get('hidden-lg')!='undefined'){
				updataRow(21,attMap.get('hidden-lg'));
			}
			
			if(attMap.get('visible-xs-block')!='undefined'){
				updataRow(22,attMap.get('visible-xs-block'));
			}
			if(attMap.get('visible-sm-block')!='undefined'){
				updataRow(23,attMap.get('visible-sm-block'));
			}
			if(attMap.get('visible-md-block')!='undefined'){
				updataRow(24,attMap.get('visible-md-block'));
			}
			if(attMap.get('visible-lg-block')!='undefined'){
				updataRow(25,attMap.get('visible-lg-block'));
			}
				
			if(attMap.get('clearfix_visible_xs')!='undefined'){
				updataRow(26,attMap.get('clearfix_visible_xs'));
			}
			if(attMap.get('clearfix_visible_sm')!='undefined'){
				updataRow(27,attMap.get('clearfix_visible_sm'));
			}
			if(attMap.get('clearfix_visible_md')!='undefined'){
				updataRow(28,attMap.get('clearfix_visible_md'));
			}
			if(attMap.get('clearfix_visible_lg')!='undefined'){
				updataRow(29,attMap.get('clearfix_visible_lg'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			sendMessage('ide',row);
		}
	});
}

function _form(attMap){
	$('#dataGrid').propertygrid({    
		url: 'form.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('poSrc')!='undefined'){
				updataRow(0,attMap.get('poSrc'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(1,attMap.get('id'));
			}
			if(attMap.get('排列方式')!='undefined'){
				
				updataRow(2,attMap.get('排列方式'));
				//如果不是水平排列则删除占左占右行
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(5,attMap.get('尺寸'));
			}
			if(attMap.get('排列方式')!='undefined'&&attMap.get('排列方式')!=='form-horizontal'){
				deleteRow(4);
				deleteRow(3);
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			if(row.name ==='排列方式'){
				if(row.value!=='form-horizontal' && containsRow('左占')){
					deleteRow(4);
					deleteRow(3);
				}
				if(row.value==='form-horizontal' && !containsRow('左占')){
					$('#dataGrid').propertygrid('insertRow',{
						index: 2,	// 索引从0开始
						row: {
							name:'左占',    
							value:'col-sm-4',    
							group:'FROM表单',    
							editor:{
								"type":"combobox",
								"options":{
								"data":
									[{"value":"col-sm-1","text":"col-sm-1"},
							   		{"value":"col-sm-2","text":"col-sm-2"},
							   		{"value":"col-sm-3","text":"col-sm-3"},
							   		{"value":"col-sm-4","text":"col-sm-4"},
							   		{"value":"col-sm-5","text":"col-sm-5"},
							   		{"value":"col-sm-6","text":"col-sm-6"},
							   		{"value":"col-sm-7","text":"col-sm-7"},
							   		{"value":"col-sm-8","text":"col-sm-8"},
							   		{"value":"col-sm-9","text":"col-sm-9"},
							   		{"value":"col-sm-10","text":"col-sm-10"},
							   		{"value":"col-sm-11","text":"col-sm-11"},
							   		{"value":"col-sm-12","text":"col-sm-12"}],
					       	   		"panelHeight":"auto"
								}
							}   
						}
					});
					$('#dataGrid').propertygrid('insertRow',{
						index: 3,	// 索引从0开始
						row: {
							name:'右占',    
							value:'col-sm-8',    
							group:'FROM表单',    
							editor:{
								"type":"combobox",
								"options":{
								"data":
									[{"value":"col-sm-1","text":"col-sm-1"},
							   		{"value":"col-sm-2","text":"col-sm-2"},
							   		{"value":"col-sm-3","text":"col-sm-3"},
							   		{"value":"col-sm-4","text":"col-sm-4"},
							   		{"value":"col-sm-5","text":"col-sm-5"},
							   		{"value":"col-sm-6","text":"col-sm-6"},
							   		{"value":"col-sm-7","text":"col-sm-7"},
							   		{"value":"col-sm-8","text":"col-sm-8"},
							   		{"value":"col-sm-9","text":"col-sm-9"},
							   		{"value":"col-sm-10","text":"col-sm-10"},
							   		{"value":"col-sm-11","text":"col-sm-11"},
							   		{"value":"col-sm-12","text":"col-sm-12"}],
					       	   		"panelHeight":"auto"
								}
							}   
						}
					});
				}
			}
			sendMessage('ide',row);
		}
	});
}


function _navbar(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'navbar.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('collapse')!='undefined'){
				updataRow(5,attMap.get('collapse'));
			}
			if(attMap.get('nav_ys')!='undefined'){
				updataRow(0,attMap.get('nav_ys'));
			}
			
			if(attMap.get('nav_lcnb')!='undefined'){
				updataRow(1,attMap.get('nav_lcnb'));
			}
			
			if(attMap.get('nav_fz')!='undefined'){
				updataRow(2,attMap.get('nav_fz'));
			}
			
			if(attMap.get('nav_ys')!='undefined'){
				//如果样式是默认或者静止顶部 则隐藏两侧内补和body内补
				if(attMap.get('nav_ys')===''||attMap.get('nav_ys')==='navbar-static-top'){
					deleteRow(1);
				}
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
					return;
			}
   			console.info('-----------frame changes ------------');
   			console.info(row);
   			
   			console.info($('#dataGrid').datagrid('getRows'));
   			
   			if(row.name==='样式'){
   				//如果是固定顶部和固定底部
   				if(row.value==='navbar-fixed-top'||row.value==='navbar-fixed-bottom'){
   					if(!containsRow('两侧内补')){
   						$('#dataGrid').propertygrid('insertRow',{
   							index: 1,	// 索引从0开始
   							row: {
   								name:'两侧内补',    
   								value:'',    
   								group:'导航栏',    
   								editor:{
   									"type":"combobox",
   									"options":{
   										"data":
   											[{"value":"","text":"默认"},
   											 {"value":"container","text":"固定宽度"},
   											 {"value":"container-fluid","text":"百分比宽度"}],
   											 "panelHeight":"auto"
   									}
   								}   
   							}
   						});
   	   				}
   				}else{
   					if(containsRow('两侧内补')){
   						deleteRow(1);
   					}
   				}
   			}
			sendMessage('ide',row);
		}
	});
}

function _downMenu(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'downmenu.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(3,attMap.get('浮动'));
			}
			if(attMap.get('单按钮式下拉菜单')!='undefined'){
				updataRow(4,attMap.get('单按钮式下拉菜单'));
			}
			if(attMap.get('样式')!='undefined'){
				updataRow(5,attMap.get('样式'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(6,attMap.get('尺寸'));
			}
			if(attMap.get('方向')!='undefined'){
				updataRow(7,attMap.get('方向'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}

function _btnGroup(attMap){
	$('#dataGrid').propertygrid({    
		url: 'btngroup.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(3,attMap.get('浮动'));
			}
			if(attMap.get('样式')!='undefined'){
				updataRow(4,attMap.get('样式'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(5,attMap.get('尺寸'));
			}
			if(attMap.get('方向')!='undefined'){
				updataRow(6,attMap.get('方向'));
			}
			if(attMap.get('两端对齐')!='undefined'){
				updataRow(7,attMap.get('两端对齐'));
			}		
			if(attMap.get('bsText')!='undefined' && attMap.get('bsText')!==''){
				updataRow(8,attMap.get('bsText'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
//导航
function _navigation(attMap){
	$('#dataGrid').propertygrid({    
		url: 'Navigation.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(3,attMap.get('浮动'));
			}
			if(attMap.get('navType')!='undefined'){
				updataRow(4,attMap.get('navType'));
			}
			if(attMap.get('navAlign')!='undefined'){
				updataRow(5,attMap.get('navAlign'));
			}
			if(attMap.get('edit')!='undefined'){
				updataRow(6,attMap.get('edit'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
	  }
	});
}
//路径导航
function _pathNav(attMap){
	$('#dataGrid').propertygrid({    
		url: 'pathNavigation.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(3,attMap.get('浮动'));
			}
			if(attMap.get('edit')!='undefined'){
				updataRow(4,attMap.get('edit'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
	  }
	});
}
//分页
function _paging(attMap){
	$('#dataGrid').propertygrid({    
		url: 'paging.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(3,attMap.get('浮动'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(4,attMap.get('尺寸'));
			}
			if(attMap.get('edit')!='undefined'){
				updataRow(5,attMap.get('edit'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
//按钮工具栏
function _btnToolbar(attMap){
	$('#dataGrid').propertygrid({    
		url: 'btnToolbar.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(3,attMap.get('浮动'));
			}
			if(attMap.get('样式')!='undefined'){
				updataRow(4,attMap.get('样式'));
			}
			if(attMap.get('edit')!='undefined'){
				updataRow(5,attMap.get('edit'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
//翻页
function _turnPage(attMap){
	$('#dataGrid').propertygrid({    
		url: 'turningPage.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(3,attMap.get('浮动'));
			}
			if(attMap.get('两端对齐')!='undefined'){
				updataRow(4,attMap.get('两端对齐'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
//进度条
function _progressBar(attMap){
	$('#dataGrid').propertygrid({    
		url: 'progressBar.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('提示')!='undefined'){
				updataRow(3,attMap.get('提示'));
			}
			if(attMap.get('编辑提示')!='undefined'){
				updataRow(4,attMap.get('编辑提示'));
			}
			if(attMap.get('最小百分比')!='undefined'){
				updataRow(5,attMap.get('最小百分比'));
			}
			if(attMap.get('样式')!='undefined'){
				updataRow(6,attMap.get('样式'));
			}
			if(attMap.get('条纹')!='undefined'){
				updataRow(7,attMap.get('条纹'));
			}
			if(attMap.get('动画')!='undefined'){
				updataRow(8,attMap.get('动画'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
//进度条
function _panel(attMap){
	$('#dataGrid').propertygrid({    
		url: 'panel.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('样式')!='undefined'){
				updataRow(3,attMap.get('样式'));
			}
			if(attMap.get('标题')!='undefined'){
				updataRow(4,attMap.get('标题'));
			}
			if(attMap.get('脚注')!='undefined'){
				updataRow(5,attMap.get('脚注'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
//缩略图
function _thumbnail(attMap){
	$('#dataGrid').propertygrid({    
		url: 'thumbnail.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('设置')!='undefined'){
				updataRow(3,attMap.get('设置'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}

//列表组
function _listGroup(attMap){
	var _inlineType=attMap.get('inlineType');
	$('#dataGrid').propertygrid({    
		url: 'listGroup.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('样式')!='undefined'){
				updataRow(3,attMap.get('样式'));
			}
			if(attMap.get('类型')!='undefined'){
				updataRow(4,attMap.get('类型'));
			}
			if(attMap.get('设置徽章')!='undefined'){
				updataRow(8,attMap.get('设置徽章'));
			}
			if(_inlineType!='undefined'){ 
				if(_inlineType==""){
					deleteRow(8);
				}else if(_inlineType=='badge'){
				    
				}
				updataRow(7,_inlineType);
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
			if(row.name=="增加行"){
				updataRow(5,"");
			}
			if(row.name== "删除行"){
				updataRow(6,"");
			}
		}
	});
}
//媒体
function _media(attMap){
	$('#dataGrid').propertygrid({    
		url: 'media.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('媒体列表')!='undefined'){
				updataRow(3,attMap.get('媒体列表'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
//巨幕
function _jumbotron(attMap){
	$('#dataGrid').propertygrid({    
		url: 'jumbotron.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
			if(attMap.get('well')!='undefined'){
				updataRow(3,attMap.get('well'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}

// 处理多选框单选框
function _radioAndCheckboxBoard(attMap)
{
	$('#dataGrid').propertygrid({    
		url: 'radioAndCheckboxBoard.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined')
			{
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined')
			{
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined')
			{
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏label')!='undefined')
			{
				updataRow(3,attMap.get('隐藏label'));
			}
			if(attMap.get('排列方式')!='undefined')
			{
				updataRow(4,attMap.get('排列方式'));
			}
			if(attMap.get('编辑')!='undefined')
			{
				updataRow(5,attMap.get('编辑'));
			}
			if(attMap.get('reverse')!='undefined')
			{
				updataRow(6,returnReverse(attMap.get('reverse'),getJson('choiceValidator.json')));
			}
			updataRowForCombox(6,getJson('choiceValidator.json'));
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
// 单选框
function _radioCheckbox(attMap)
{
	var _posrc=attMap.get("poSrc");	
	$('#dataGrid').propertygrid({    
		url: 'radioAndCheckbox.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined')
			{
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined')
			{
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('value')!='undefined')
			{
				updataRow(2,attMap.get('value'));
			}
			if(attMap.get('style')!='undefined')
			{
				updataRow(3,attMap.get('style'));
			}
			if(attMap.get('文本')!='undefined')
			{
				updataRow(4,attMap.get('文本'));
			}
			
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);deleteRow(0);
				}
			}
			if(_posrc!='undefined'&&_posrc!=''){
				var reData=getResultJson(_posrc);
				updataRowForCombox(1,reData);
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
function _a(attMap){
	var inlineType=attMap.get('inlineType');
	var aType=attMap.get('aType');
	var preIsLi=attMap.get('_preIsLi');
	$('#dataGrid').propertygrid({    
		url: 'a.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('文本')!='undefined'){
				updataRow(2,attMap.get('文本'));
			}
			if(attMap.get('rel')!='undefined'){
				updataRow(3,attMap.get('rel'));
			}
			if(attMap.get('title')!='undefined'){
				updataRow(4,attMap.get('title'));
			}
			if(attMap.get('href')!='undefined'){
				updataRow(5,attMap.get('href'));
			}
			if(attMap.get('isBlank')!='undefined'){
				updataRow(6,attMap.get('isBlank'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(7,attMap.get('style'));
			}
			if(attMap.get('a浮动')!='undefined'){
				updataRow(8,attMap.get('a浮动'));
			}
			if(attMap.get('a活动')!='undefined'){
				updataRow(9,attMap.get('a活动'));
			}
			if(attMap.get('a禁用')!='undefined'){
				updataRow(10,attMap.get('a禁用'));
			}
			if(attMap.get('aTyle')!='undefined'){
				updataRow(11,attMap.get('aType'));
			}
			if(attMap.get('btnType')!='undefined'){
				updataRow(12,attMap.get('btnType'));
			}
			if(attMap.get('btnSize')!='undefined'){
				updataRow(13,attMap.get('btnSize'));
			}
			if(attMap.get('btnBlock')!='undefined'){
				updataRow(14,attMap.get('btnBlock'));
			}
			if(attMap.get('labelType')!='undefined'){
				updataRow(15,attMap.get('labelType'));
			}
			if(attMap.get('src')!='undefined'){
				updataRow(17,attMap.get('src'));
			}
			if(attMap.get('图片位置')!='undefined'){
				updataRow(18,attMap.get('图片位置'));
			}
			if(attMap.get('edit')!='undefined'){
				updataRow(19,attMap.get('edit'));
			}
			if(attMap.get('badge')!='undefined'){
				updataRow(20,attMap.get('badge'));
			}
			if(attMap.get('活动/取消活动')!='undefined'){
				updataRow(21,attMap.get('活动/取消活动'));
			}
			if(attMap.get('禁用/取消禁用')!='undefined'){
				updataRow(22,attMap.get('禁用/取消禁用'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(23,attMap.get('浮动'));
			}
			if(attMap.get('靠左/靠右')!='undefined'){
				updataRow(24,attMap.get('靠左/靠右'));
			}
			if(attMap.get('ulEdit')!='undefined'){
				updataRow(25,attMap.get('ulEdit'));
			}
			//如果不是导航栏则删除ul属性
			if(attMap.get('isNavbar')=='false'){
				deleteRow(25);
				deleteRow(24);
			}
			//若a父类不为li,则删除li属性栏
			if(preIsLi=='false'){
				deleteRow(23);
				deleteRow(22);
				deleteRow(21);
			}
			if(inlineType!='undefined'){ 
				if(inlineType==''){
					deleteRow(20);
					deleteRow(19);
					deleteRow(18);
					deleteRow(17);
				}else if(inlineType=='image'){
					deleteRow(20);
					deleteRow(19);
				}else if(inlineType=='dropdown'){
					deleteRow(20);
					deleteRow(18);
					deleteRow(17);
				}else if(inlineType== 'badge'){
					deleteRow(19);
					deleteRow(18);
					deleteRow(17);
				}
				updataRow(16,inlineType);
			}
			if(aType!='undefined'){ 
				if(aType==''){
					deleteRow(15);
					deleteRow(14);
					deleteRow(13);
					deleteRow(12);
				}else if(aType=='button'){
					deleteRow(15);
				}else if(aType=='label'){
					deleteRow(14);
					deleteRow(13);
					deleteRow(12);
				}
				updataRow(11,aType);
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
			}
   			console.info('-----------frame changes ------------');
   			console.info(row);
			sendMessage('ide',row);
		}
	});
}


function _label(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'label.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('wenben')!='undefined'){
				updataRow(2,attMap.get('wenben'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(3,attMap.get('style'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}

function _button(attMap) {
	var inlineType=attMap.get('inlineType');
	$('#dataGrid').propertygrid({    
		url: 'button.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('wenben')!='undefined'){
				updataRow(2,attMap.get('wenben'));
			}
			if(attMap.get('value')!='undefined'){
				updataRow(3,attMap.get('value'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(4,attMap.get('style'));
			}
			if(attMap.get('浮动')!='undefined'){
				updataRow(5,attMap.get('浮动'));
			}	
			if(attMap.get('type')!='undefined'){
				//20160929 add by chenyl 提交、重置按钮的特殊处理
				if(attMap.get('btn-type')!='undefined'){
					updataRow(6,attMap.get('btn-type'));
				}else{
					updataRow(6,attMap.get('type'));
				}
			}	
			if(attMap.get('样式')!='undefined'){
				updataRow(7,attMap.get('样式'));
			}	
			if(attMap.get('尺寸')!='undefined'){
				updataRow(8,attMap.get('尺寸'));
			}			
			if(attMap.get('通栏')!='undefined'){
				updataRow(9,attMap.get('通栏'));
			}	
			if(attMap.get('活动')!='undefined'){
				updataRow(10,attMap.get('活动'));
			}	
			if(attMap.get('禁用')!='undefined'){
				updataRow(11,attMap.get('禁用'));
			}
			//add by chenyl
			if(attMap.get('设置权限')!='undefined'){
				updataRow(12,attMap.get('设置权限'));
			}	
			if(attMap.get('edit')!='undefined'){
				updataRow(14,attMap.get('edit'));
			}	
			if(attMap.get('src')!='undefined'){
				updataRow(15,attMap.get('src'));
			}	
			if(attMap.get('图片位置')!='undefined'){
				updataRow(16,attMap.get('图片位置'));
			}	
			if(attMap.get('badge')!='undefined'){
				updataRow(17,attMap.get('badge'));
			}	
			if(inlineType!='undefined'){ 
				if(inlineType==""){
					deleteRow(17);
					deleteRow(16);
					deleteRow(15);
					deleteRow(14);
				}else if(inlineType=='badge'){
					deleteRow(16);
					deleteRow(15);
					deleteRow(14);
				}else if(inlineType=='i'){
					deleteRow(17);
					deleteRow(14);
				}else if(inlineType=='dropmenu'){
					deleteRow(17);
					deleteRow(16);
					deleteRow(15);
				}
				updataRow(13,inlineType);
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info('row:'+row+' , value:'+changes.value);
			sendMessage('ide',row);
		}
	});
}
function updataRowForCombox(index,data) {
	$('#dataGrid').propertygrid('updateRow',{
		index: index,
		row:{
			editor:{ 'type': 'combobox',
                'options': { 'data':data , "panelHeight": "auto"}
            }
		}
	});
}
function _input(attMap) {
	var _posrc=attMap.get("poSrc");	
	$('#dataGrid').propertygrid({    
		url: 'input.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			
			if(attMap.get('尺寸')!='undefined')
			{
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined')
			{
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			
			if(attMap.get('type').trim()=='radio' || attMap.get('type').trim()=='checkbox'||attMap.get('type').trim()!='undefined'||attMap.get('type').trim()!=''){
				updataRow(6,attMap.get('type').trim());
			}
			
			if(attMap.get('value')!='undefined'){
				updataRow(7,attMap.get('value'));
			}
			
			if(attMap.get('style')!='undefined'){
				updataRow(8,attMap.get('style'));
			}
			
			if(attMap.get('禁用')!='undefined')
			{
				updataRow(9,attMap.get('禁用'));
			}else{
				var a = '否 ';
				updataRow(9,a);
			}
			
			if(attMap.get('只读')!='undefined')
			{
			     updataRow(10,attMap.get('只读'));
		    }else{
		    	var a = '否 ';
			    updataRow(10,a);
		     }
			
			if(attMap.get('注解')!='undefined')
			{
				updataRow(11,attMap.get('注解'));
			}
			
			if(attMap.get('前裹类型')!='undefined')
			{
				updataRow(12,attMap.get('前裹类型'));
			}else{
				updataRow(12,'无内容');
			}
			if(attMap.get('前裹内容')!='undefined')
			{
				updataRow(13,attMap.get('前裹内容'));
			}
			
			if(attMap.get('后裹类型')!='undefined')
			{
				updataRow(14,attMap.get('后裹类型'));
			}else{
				updataRow(14,'无内容');
			}
			if(attMap.get('后裹内容')!='undefined')
			{
				updataRow(15,attMap.get('后裹内容'));
			}
			if(attMap.get('reverse')!='undefined')
			{
				updataRow(16,returnReverse(attMap.get('reverse'),getJson('inputValidator.json')));
			}
			updataRowForCombox(16,getJson('inputValidator.json'));
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(3,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			}
			/*20180313 add by chenyl for 新增新校验属性设置的回显*/
			/*不为空*/
			if(attMap.get('_check_empty')!='undefined')
			{
				updataRow(17,attMap.get('_check_empty'));
			}
			/*最大长度*/
			if(attMap.get('_maxlength')!='undefined')
			{
				updataRow(18,attMap.get('_maxlength'));
			}
			/*最小长度*/
			if(attMap.get('_check_minlength')!='undefined')
			{
				updataRow(19,attMap.get('_check_minlength'));
			}
			/*最小范围*/
			if(attMap.get('_min')!='undefined')
			{
				updataRow(20,attMap.get('_min'));
			}
			/*最大范围*/
			if(attMap.get('_max')!='undefined')
			{
				updataRow(21,attMap.get('_max'));
			}
			/*电话号码*/
			if(attMap.get('_check_telephone')!='undefined')
			{
				updataRow(22,attMap.get('_check_telephone'));
			}
			/*身份证号码*/
			if(attMap.get('_check_idcard')!='undefined')
			{
				updataRow(23,attMap.get('_check_idcard'));
			}
			/*中文*/
			if(attMap.get('_check_chinese')!='undefined')
			{
				updataRow(24,attMap.get('_check_chinese'));
			}
			/*IP地址*/
			if(attMap.get('_check_ipaddress')!='undefined')
			{
				updataRow(25,attMap.get('_check_ipaddress'));
			}
			/*版本号*/
			if(attMap.get('_check_edition')!='undefined')
			{
				updataRow(26,attMap.get('_check_edition'));
			}
			/*币种，只能输入英文*/
			if(attMap.get('_check_english')!='undefined')
			{
				updataRow(27,attMap.get('_check_english'));
			}
			/*英文、数字、特殊字符*/
			if(attMap.get('_check_character')!='undefined')
			{
				updataRow(28,attMap.get('_check_character'));
			}
			/*密码*/
			if(attMap.get('_check_password')!='undefined')
			{
				updataRow(29,attMap.get('_check_password'));
			}
			/*英文数字下划线*/
			if(attMap.get('_check_alphanumericsymbols')!='undefined')
			{
				updataRow(30,attMap.get('_check_alphanumericsymbols'));
			}
			/*中文数字横线中括号*/
			if(attMap.get('_check_chinesedigitalsymbols')!='undefined')
			{
				updataRow(31,attMap.get('_check_chinesedigitalsymbols'));
			}
			var _check_chinesedigitalsymbols = $(this).attr("check-chinesedigitalsymbols");
			/*单选框属性/复选框属性*/
			if(attMap.get('_checkbtn')!='undefined')
			{
				if(attMap.get('type').trim()=='radio'){
					updataRow(32,attMap.get('_checkbtn'));
				}else if(attMap.get('type').trim()=='checkbox'){
					updataRow(33,attMap.get('_checkbtn'));
				}
			}
			
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------input changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
	
}
function _textarea(attMap) {
	var _posrc=attMap.get("poSrc");	
	$('#dataGrid').propertygrid({    
		url: 'textarea.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined'){
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(6,attMap.get('style'));
			}
			if(attMap.get('value')!='undefined'){
				updataRow(7,attMap.get('value'));
			}else{
			}
			if(attMap.get('禁用')!='undefined'){
				updataRow(8,attMap.get('禁用'));
			}else{
			}
			if(attMap.get('只读')!='undefined'){
				updataRow(9,attMap.get('只读'));
			}else{
			}
			if(attMap.get('注解')!='undefined'){
				updataRow(10,attMap.get('注解'));
			}
			if(attMap.get('行数')!='undefined'){
				updataRow(11,attMap.get('行数'));
			}
			if(attMap.get('reverse')!='undefined')
			{
				updataRow(12,returnReverse(attMap.get('reverse'),getJson('textareaValidator.json')));
			}
			updataRowForCombox(12,getJson('textareaValidator.json'));
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(3,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			}
			
	},
			
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
	
}
function _select(attMap){
	var _posrc=attMap.get("poSrc");	
	var _isMultiple=attMap.get("isMultiple");
	$('#dataGrid').propertygrid({    
		url: 'select.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined'){
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(6,attMap.get('style'));
			}
			if(attMap.get('内容')!='undefined'){
				updataRow(7,attMap.get('内容'));
			}
			if(attMap.get('Multipl')!='undefined'){
				updataRow(8,attMap.get('Multipl'));
			}
			if(attMap.get('enableClickableOptGroups')!='undefined'){
				updataRow(9,attMap.get('enableClickableOptGroups'));
			}
			if(attMap.get('enableCollapsibleOptGroups')!='undefined'){
				updataRow(10,attMap.get('enableCollapsibleOptGroups'));
			}
			if(attMap.get('disableIfEmpty')!='undefined'){
				updataRow(11,attMap.get('disableIfEmpty'));
			}
			if(attMap.get('disabledText')!='undefined'){
				updataRow(12,attMap.get('disabledText'));
			}
			if(attMap.get('dropRight')!='undefined'){
				updataRow(13,attMap.get('dropRight'));
			}
			if(attMap.get('dropUp')!='undefined'){
				updataRow(14,attMap.get('dropUp'));
			}
			if(attMap.get('maxHeight')!='undefined'){
				updataRow(15,attMap.get('maxHeight'));
			}
			if(attMap.get('buttonClass')!='undefined'){
				updataRow(16,attMap.get('buttonClass'));
			}
			if(attMap.get('inheritClass')!='undefined'){
				updataRow(17,attMap.get('inheritClass'));
			}
			if(attMap.get('buttonWidth')!='undefined'){
				updataRow(18,attMap.get('buttonWidth'));
			}
			if(attMap.get('nonSelectedText')!='undefined'){
				updataRow(19,attMap.get('nonSelectedText'));
			}
			if(attMap.get('nSelectedText')!='undefined'){
				updataRow(20,attMap.get('nSelectedText'));
			}
			if(attMap.get('allSelectedText')!='undefined'){
				updataRow(21,attMap.get('allSelectedText'));
			}
			if(attMap.get('numberDisplayed')!='undefined'){
				updataRow(22,attMap.get('numberDisplayed'));
			}
			if(attMap.get('delimiterText')!='undefined'){
				updataRow(23,attMap.get('delimiterText'));
			}
			if(attMap.get('selectedClass')!='undefined'){
				updataRow(24,attMap.get('selectedClass'));
			}
			if(attMap.get('includeSelectAllOption')!='undefined'){
				updataRow(25,attMap.get('includeSelectAllOption'));
			}
			if(attMap.get('selectAllJustVisible')!='undefined'){
				updataRow(26,attMap.get('selectAllJustVisible'));
			}
			if(attMap.get('selectAllText')!='undefined'){
				updataRow(27,attMap.get('selectAllText'));
			}
			if(attMap.get('selectAllValue')!='undefined'){
				updataRow(28,attMap.get('selectAllValue'));
			}
			if(attMap.get('selectAllName')!='undefined'){
				updataRow(29,attMap.get('selectAllName'));
			}
			if(attMap.get('selectAllNumber')!='undefined'){
				updataRow(30,attMap.get('selectAllNumber'));
			}
			if(attMap.get('enableFiltering')!='undefined'){
				updataRow(31,attMap.get('enableFiltering'));
			}
			if(attMap.get('enableCaseInsensitiveFiltering')!='undefined'){
				updataRow(32,attMap.get('enableCaseInsensitiveFiltering'));
			}
			if(attMap.get('enableFullValueFiltering')!='undefined'){
				updataRow(33,attMap.get('enableFullValueFiltering'));
			}
			if(attMap.get('filterBehavior')!='undefined'){
				updataRow(34,attMap.get('filterBehavior'));
			}
			if(attMap.get('filterPlaceholder')!='undefined'){
				updataRow(35,attMap.get('filterPlaceholder'));
			}
			if(attMap.get('reverse')!='undefined')
			{
				updataRow(36,returnReverse(attMap.get('reverse'),getJson('choiceValidator.json')));
			}
			updataRowForCombox(36,getJson('choiceValidator.json'));
			
			/* 20180313 add by chenyl for 新增新校验属性设置,并对应后面的更新下标+1*/
			if(attMap.get('_checkbtn')!='undefined'){
				updataRow(37,attMap.get('_checkbtn'));
			}
			
			//struts文件绑定不处理,第38行
			if(attMap.get("url")!='undefined'){
				console.info("select url="+attMap.get("url"));
				updataRow(39,attMap.get("url"));//add by chenyl
			}
			/* 20180623 add by chenyl for 新增multiselect同步加载功能*/
			if(attMap.get("是否同步加载")!='undefined'){
				updataRow(40,attMap.get("是否同步加载"));
			}
			if(attMap.get("是否显示空选项")!='undefined'){
				updataRow(41,attMap.get("是否显示空选项"));//add by chenyl
			}
			if(attMap.get("空选项标签值")!='undefined'){
				updataRow(42,attMap.get("空选项标签值"));//add by chenyl
			}
			if(attMap.get("空选项值")!='undefined'){
				updataRow(43,attMap.get("空选项值"));//add by chenyl
			}
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(3,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			}
			
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------select changes ------------');
			console.info(row);
//			var rowid = $(this).datagrid('getData').rows[4];
//			console.info(rowid.value);
//			row.rowidval=rowid;
			sendMessage('ide',row);
		}
	});
	
}
function _table(attMap){
	$('#dataGrid').propertygrid({    
		url: 'table.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('poUrl')!='undefined'){
				updataRow(0,attMap.get('poUrl'));
			}
			if(attMap.get('xmlPath')!='undefined'){
				updataRow(1,attMap.get('xmlPath'));
				if(attMap.get('xmlPath')!=''){
					updataRowForCombox(18,getXmlAction(attMap.get('xmlPath')));
				}
			}
			if(attMap.get('id')!='undefined'){
				updataRow(2,attMap.get('id'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(3,attMap.get('style'));
			}
			if(attMap.get('edit')!='undefined'){
				updataRow(4,attMap.get('edit'));
			}
			if(attMap.get('class')!='undefined'){
				updataRow(5,attMap.get('class'));
			}
			if(attMap.get('height')!='undefined'){
				updataRow(6,attMap.get('height'));
			}
			if(attMap.get('undefT')!='undefined'){
				updataRow(7,attMap.get('undefT'));
			}
			if(attMap.get('striped')!='undefined'){
				updataRow(8,attMap.get('striped'));
			}
			if(attMap.get('sinSle')!='undefined'){
				updataRow(9,attMap.get('sinSle'));
			}
			if(attMap.get('cTS')!='undefined'){
				updataRow(10,attMap.get('cTS'));
			}
			if(attMap.get('tBar')!='undefined'){
				updataRow(11,attMap.get('tBar'));
			}
			if(attMap.get('sCH')!='undefined'){
				updataRow(12,attMap.get('sCH'));
			}
			if(attMap.get('rowstyle')!='undefined'){
				updataRow(13,attMap.get('rowstyle'));
			}
			if(attMap.get('sorter')!='undefined'){
				updataRow(14,attMap.get('sorter'));
			}
			if(attMap.get('sortName')!='undefined'){
				updataRow(15,attMap.get('sortName'));
			}
			if(attMap.get('order')!='undefined'){
				updataRow(16,attMap.get('order'));
			}
			/*add by chenyl 20170423 for 新增是否首次自动装载数据*/
			if(attMap.get('isAutoLoad')!='undefined'){
				updataRow(17,attMap.get('isAutoLoad'));
			}
			if(attMap.get('mothod')!='undefined'){
				updataRow(18,attMap.get('mothod'));
			}
			if(attMap.get('url')!='undefined'){
				updataRow(19,attMap.get('url'));
			}
			
			if(attMap.get('params')!='undefined'){
				updataRow(20,attMap.get('params'));
			}
			if(attMap.get('pType')!='undefined'){
				updataRow(21,attMap.get('pType'));
			}
			if(attMap.get('pagi')!='undefined'){
				updataRow(22,attMap.get('pagi'));
			}
			if(attMap.get('search')!='undefined'){
				updataRow(23,attMap.get('search'));
			}
			if(attMap.get('showH')!='undefined'){
				updataRow(24,attMap.get('showH'));
			}
			if(attMap.get('showC')!='undefined'){
				updataRow(25,attMap.get('showC'));
			}
			if(attMap.get('showR')!='undefined'){
				updataRow(26,attMap.get('showR'));
			}
			if(attMap.get('showT')!='undefined'){
				updataRow(27,attMap.get('showT'));
			}
			if(attMap.get('sPagS')!='undefined'){
				updataRow(28,attMap.get('sPagS'));
			}
			if(attMap.get('sCard')!='undefined'){
				updataRow(29,attMap.get('sCard'));
			}
			if(attMap.get('export')!='undefined'){
				updataRow(30,attMap.get('export'));
			}
			if(attMap.get('sAlg')!='undefined'){
				updataRow(31,attMap.get('sAlg'));
			}
			if(attMap.get('bAlg')!='undefined'){
				updataRow(32,attMap.get('bAlg'));
			}
			if(attMap.get('tAlg')!='undefined'){
				updataRow(33,attMap.get('tAlg'));
			}
			if(attMap.get('pAlgV')!='undefined'){
				updataRow(34,attMap.get('pAlgV'));
			}
			if(attMap.get('pAlgh')!='undefined'){
				updataRow(35,attMap.get('pAlgh'));
			}
			if(attMap.get('pDAlg')!='undefined'){
				updataRow(36,attMap.get('pDAlg'));
			}
			if(attMap.get('sTime')!='undefined'){
				updataRow(37,attMap.get('sTime'));
			}
			if(attMap.get('pageNum')!='undefined'){
				updataRow(38,attMap.get('pageNum'));
			}
			if(attMap.get('pageSize')!='undefined'){
				updataRow(39,attMap.get('pageSize'));
			}
			if(attMap.get('pageList')!='undefined'){
				updataRow(40,attMap.get('pageList'));
			}
			if(attMap.get('pagiInf')!='undefined'){
				updataRow(41,attMap.get('pagiInf'));
			}
			if(attMap.get('pagiSide')!='undefined'){
				updataRow(42,attMap.get('pagiSide'));
			}
			if(attMap.get('pFir')!='undefined'){
				updataRow(43,attMap.get('pFir'));
			}
			if(attMap.get('pPre')!='undefined'){
				updataRow(44,attMap.get('pPre'));
			}
			if(attMap.get('pNext')!='undefined'){
				updataRow(45,attMap.get('pNext'));
			}
			if(attMap.get('pLast')!='undefined'){
				updataRow(46,attMap.get('pLast'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}
function _tableCol(attMap){
	$('#dataGrid').propertygrid({
		url: 'tabClm.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('radio')!='undefined'){
				updataRow(0,attMap.get('radio'));
			}
			if(attMap.get('checkbox')!='undefined'){
				updataRow(1,attMap.get('checkbox'));
			}
			if(attMap.get('field')!='undefined'){
				updataRow(2,attMap.get('field'));
			}
			if(attMap.get('title')!='undefined'){
				updataRow(3,attMap.get('title'));
			}
			if(attMap.get('tooltip')!='undefined'){
				updataRow(4,attMap.get('tooltip'));
			}
			if(attMap.get('class')!='undefined'){
				updataRow(5,attMap.get('class'));
			}
			
			if(attMap.get('width')!='undefined'){
				updataRow(6,attMap.get('width'));
			}
			if(attMap.get('visible')!='undefined'){
				updataRow(7,attMap.get('visible'));
			}
/*			if(attMap.get('cardVis')!='undefined'){
				updataRow(8,attMap.get('cardVis'));
			}
*/			if(attMap.get('sAbl')!='undefined'){
				updataRow(8,attMap.get('sAbl'));
			}
			/*if(attMap.get('cToSel')!='undefined'){
				updataRow(9,attMap.get('cToSel'));
			}*/
			if(attMap.get('cellSty')!='undefined'){
				updataRow(9,attMap.get('cellSty'));
			}
			if(attMap.get('format')!='undefined'){
				updataRow(10,attMap.get('format'));
			}
/*			if(attMap.get('fFormat')!='undefined'){
				updataRow(15,attMap.get('fFormat'));
			}
*/			if(attMap.get('sortable')!='undefined'){
				updataRow(11,attMap.get('sortable'));
			}
			if(attMap.get('sortName')!='undefined'){
				updataRow(12,attMap.get('sortName'));
			}
			if(attMap.get('order')!='undefined'){
				updataRow(13,attMap.get('order'));
			}
			if(attMap.get('sorter')!='undefined'){
				updataRow(14,attMap.get('sorter'));
			}
			if(attMap.get('align')!='undefined'){
				updataRow(15,attMap.get('align'));
			}
			if(attMap.get('hAlign')!='undefined'){
				updataRow(16,attMap.get('hAlign'));
			}
			if(attMap.get('fAlign')!='undefined'){
				updataRow(17,attMap.get('fAlign'));
			}
			if(attMap.get('vAlign')!='undefined'){
				updataRow(18,attMap.get('vAlign'));
			}
		},
		onAfterEdit:function(index,row,changes){
			if(changes.value==undefined ||changes.value==null){
				return;
			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}
function _h(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'BiaoTi.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('sign')!='undefined'){
				updataRow(0,attMap.get('sign'));
			}
			if(attMap.get('align')!='undefined'){
				updataRow(1,attMap.get('align'));
			}
			if(attMap.get('controlcase')!='undefined'){
				updataRow(2,attMap.get('controlcase'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(3,attMap.get('style'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}

function _p(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'DuanLuo.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('sign')!='undefined'){
				updataRow(0,attMap.get('sign'));
			}
			if(attMap.get('align')!='undefined'){
				updataRow(1,attMap.get('align'));
			}
			if(attMap.get('controlcase')!='undefined'){
				updataRow(2,attMap.get('controlcase'));
			}
			if(attMap.get('leadd')!='undefined'){
				updataRow(3,attMap.get('leadd'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(4,attMap.get('style'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}

function _address(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'Address.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('align')!='undefined'){
				updataRow(0,attMap.get('align'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(1,attMap.get('style'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}
function _dl(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'XiMiaoShu.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('align')!='undefined'){
				updataRow(0,attMap.get('align'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(1,attMap.get('style'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}

function _span(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'BiaoQian.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('sign')!='undefined'){
				updataRow(0,attMap.get('sign'));
			}
			if(attMap.get('float')!='undefined'){
				updataRow(1,attMap.get('float'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(2,attMap.get('style'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}

function _img(attMap) {
	$('#dataGrid').propertygrid({    
		url: 'TuPian.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('src')!='undefined'){
				updataRow(0,attMap.get('src'));
			}
			if(attMap.get('sign')!='undefined'){
				updataRow(1,attMap.get('sign'));
			}
			if(attMap.get('float')!='undefined'){
				updataRow(2,attMap.get('float'));
			}
			if(attMap.get('imgType')!='undefined'){
				updataRow(3,attMap.get('imgType'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(4,attMap.get('style'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
}

function _hint(attMap){
	$('#dataGrid').propertygrid({    
		url: 'Hint.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('提示框样式')!='undefined'){
				updataRow(0,attMap.get('提示框样式'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
};

function _fold(attMap){
	$('#dataGrid').propertygrid({    
		url: 'Fold.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('折叠展示样式')!='undefined'){
				updataRow(0,attMap.get('折叠展示样式'));
			}
			/*if(attMap.get('增加面板')!='undefined'){
				updataRow(1,attMap.get('增加面板'));
			}
			if(attMap.get('删除面板')!='undefined'){
				updataRow(2,attMap.get('删除面板'));
			}*/
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
			if(row.name === '新增面板'){
				updataRow(1,"");
			}else if(row.name === '删除面板'){
				updataRow(2,"");
			}
		}
	});
};
function _slidel(attMap){
	$('#dataGrid').propertygrid({    
		url: 'Slidel.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('自动轮播')!='undefined'){
				updataRow(0,attMap.get('自动轮播'));
			}if(attMap.get('循环轮播')!='undefined'){
				updataRow(1,attMap.get('循环轮播'));
			}if(attMap.get('鼠标悬停')!='undefined'){
				updataRow(0,attMap.get('鼠标悬停'));
			}if(attMap.get('style')!='undefined'){
				updataRow(0,attMap.get('style'));
			}if(attMap.get('间隔时间')!='undefined'){
				updataRow(0,attMap.get('间隔时间'));
			}
			
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
			if(row.name === '增加'){
				updataRow(5,"");
			}else if(row.name === '删除'){
				updataRow(6,"");
			}
		}
	});
};

function _tab(attMap){
	$('#dataGrid').propertygrid({    
		url: 'Tab.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('样式')!='undefined'){
				updataRow(0,attMap.get('样式'));
			}
			if(attMap.get('编辑')!='undefined'){
				updataRow(2,attMap.get('编辑'));
			}
			if(attMap.get('显示标题')!='undefined'){
				updataRow(3,attMap.get('显示标题'));
			}
			if(attMap.get('显示关闭按钮')!='undefined'){
				updataRow(4,attMap.get('显示关闭按钮'));
			}
			if(attMap.get('宽度')!='undefined'){
				updataRow(5,attMap.get('宽度'));
			}
			if(attMap.get('高度')!='undefined'){
				updataRow(6,attMap.get('高度'));
			}
			if(attMap.get('默认选中页')!='undefined'){
				updataRow(7,attMap.get('默认选中页'));ee
			}
			if(attMap.get('自适应')!='undefined'){
				updataRow(8,attMap.get('自适应'));
			}
			if(attMap.get('底部填充')!='undefined'){
				updataRow(9,attMap.get('底部填充'));
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
};

function _imgp(attMap){
	$('#dataGrid').propertygrid({
		url: 'imgp.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('编辑')!='undefined'){
				updataRow(0,attMap.get('编辑').replace(/^\s+|\s+$/g, ""));
			}
			
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
function _time(attMap){
	var _posrc=attMap.get("poSrc");
	$('#dataGrid').propertygrid({
		url: 'dateAndtime.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined'){
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(6,attMap.get('style'));
			}
			if(attMap.get('format')!='undefined'){
				updataRow(7,attMap.get('format'));
			}
			if(attMap.get('weekStart')!='undefined'){
				updataRow(8,attMap.get('weekStart'));
			}
			if(attMap.get('startDate')!='undefined'){
				updataRow(9,attMap.get('startDate'));
			}
			if(attMap.get('endDate')!='undefined'){
				updataRow(10,attMap.get('endDate'));
			}
			if(attMap.get('daysOfWeekDisabled')!='undefined'){
				updataRow(11,attMap.get('daysOfWeekDisabled'));
			}
			if(attMap.get('autoclose')!='undefined'){
				updataRow(12,attMap.get('autoclose'));
			}
			if(attMap.get('startView')!='undefined'){
				updataRow(13,attMap.get('startView'));
			}
			if(attMap.get('minView')!='undefined'){
				updataRow(14,attMap.get('minView'));
			}
			if(attMap.get('maxView')!='undefined'){
				updataRow(15,attMap.get('maxView'));
			}
			if(attMap.get('todayBtn')!='undefined'){
				updataRow(16,attMap.get('todayBtn'));
			}
			if(attMap.get('todayHighlight')!='undefined'){
				updataRow(17,attMap.get('todayHighlight'));
			}
			if(attMap.get('keyboardNavigation')!='undefined'){
				updataRow(18,attMap.get('keyboardNavigation'));
			}
			if(attMap.get('forceParse')!='undefined'){
				updataRow(19,attMap.get('forceParse'));
			}
			if(attMap.get('pickerPosition')!='undefined'){
				updataRow(20,attMap.get('pickerPosition'));
			}
			if(attMap.get('showMeridian')!='undefined'){
				updataRow(21,attMap.get('showMeridian'));
			}
			var _vdata=getJson('inputValidator.json');
			if(attMap.get('reverse')!='undefined')
			{
				updataRow(22,returnReverse(attMap.get('reverse'),_vdata));
			}
			updataRowForCombox(22,_vdata);
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(3,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			}
		},
		onAfterEdit:function(index,row,changes){
			console.info('-----------frame changes ------------');
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			sendMessage('ide',row);
		}
	});
}

/*20160817 add by chenyl 新增日期时间组件属性值的回显*/
function _datetime(attMap){
	var _posrc=attMap.get("poSrc");
	$('#dataGrid').propertygrid({
		url: 'datetime.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined'){
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(6,attMap.get('style'));
			}
			if(attMap.get('skin')!='undefined'){
				updataRow(7,attMap.get('skin'));
			}
			if(attMap.get('dateFmt')!='undefined'){
				updataRow(8,attMap.get('dateFmt'));
			}
			if(attMap.get('valueFmt')!='undefined'){
				updataRow(9,attMap.get('valueFmt'));
			}
			if(attMap.get('defaultVal')!='undefined'){
				updataRow(10,attMap.get('defaultVal'));
			}
			if(attMap.get('minDate')!='undefined'){
				updataRow(11,attMap.get('minDate'));
			}
			if(attMap.get('maxDate')!='undefined'){
				updataRow(12,attMap.get('maxDate'));
			}
			if(attMap.get('showClear')!='undefined'){
				updataRow(13,attMap.get('showClear'));
			}
			if(attMap.get('showWeek')!='undefined'){
				updataRow(14,attMap.get('showWeek'));
			}
			if(attMap.get('showToday')!='undefined'){
				updataRow(15,attMap.get('showToday'));
			}			
			
			var _vdata=getJson('inputValidator.json');
			if(attMap.get('reverse')!='undefined')
			{
				updataRow(16,returnReverse(attMap.get('reverse'),_vdata));
			}
			updataRowForCombox(16,_vdata);
			
			/* 20180928 add by chenyl for 对日期时间组件必输项的回显设置*/
			if(attMap.get('_check_time_empty')!='undefined'){
				updataRow(17,attMap.get('_check_time_empty'));
			}
			if(attMap.get('_check_starttime_one')!='undefined'){
				updataRow(18,attMap.get('_check_starttime_one'));
			}
			if(attMap.get('_check_endtime_one')!='undefined'){
				updataRow(19,attMap.get('_check_endtime_one'));
			}
			if(attMap.get('_check_starttime_two')!='undefined'){
				updataRow(20,attMap.get('_check_starttime_two'));
			}
			if(attMap.get('_check_endtime_two')!='undefined'){
				updataRow(21,attMap.get('_check_endtime_two'));
			}
			
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(7,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			} 
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------datetime changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}

/*20161019 add by chenyl 新增搜索树组件属性值的回显*/
function _treesearch(attMap){
	var _posrc=attMap.get("poSrc");
	$('#dataGrid').propertygrid({
		url: 'treesearch.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined'){
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			if(attMap.get('value')!='undefined'){
				updataRow(6,attMap.get('value'));
			}
			if(attMap.get('label_name')!='undefined'){
				updataRow(7,attMap.get('label_name'));
			}
			if(attMap.get('label_value')!='undefined'){
				updataRow(8,attMap.get('label_value'));
			}
			if(attMap.get('title')!='undefined'){
				updataRow(9,attMap.get('title'));
			}
			if(attMap.get('url')!='undefined'){
				updataRow(10,attMap.get('url'));
			}
			if(attMap.get('checked')!='undefined'){
				updataRow(11,attMap.get('checked'));
			}
			if(attMap.get('ext_id')!='undefined'){
				updataRow(12,attMap.get('ext_id'));
			}
			if(attMap.get('is_all')!='undefined'){
				updataRow(13,attMap.get('is_all'));
			}
			if(attMap.get('not_allow_select_root')!='undefined'){
				updataRow(14,attMap.get('not_allow_select_root'));
			}
			if(attMap.get('not_allow_select_parent')!='undefined'){
				updataRow(15,attMap.get('not_allow_select_parent'));
			}
			if(attMap.get('allow_clear')!='undefined'){
				updataRow(16,attMap.get('allow_clear'));
			}
			if(attMap.get('allow_input')!='undefined'){
				updataRow(17,attMap.get('allow_input'));
			}
			if(attMap.get('css_style')!='undefined'){
				updataRow(18,attMap.get('css_style'));
			}
			if(attMap.get('small_btn')!='undefined'){
				updataRow(19,attMap.get('small_btn'));
			}
			if(attMap.get('hide_btn')!='undefined'){
				updataRow(20,attMap.get('hide_btn'));
			}
			if(attMap.get('disabled')!='undefined'){
				updataRow(21,attMap.get('disabled'));
			}		
			if(attMap.get('win_width')!='undefined' && attMap.get('win_width')!=''){
				updataRow(22,attMap.get('win_width'));
			}
			if(attMap.get('win_height')!='undefined' && attMap.get('win_height')!=''){
				updataRow(23,attMap.get('win_height'));
			}
			
			var _vdata=getJson('inputValidator.json');
			if(attMap.get('reverse')!='undefined')
			{
				updataRow(24,returnReverse(attMap.get('reverse').split('#')[0].toLowerCase(),_vdata));
			}
			updataRowForCombox(24,_vdata);
			if(attMap.get('treesearch_required')!='undefined'){
				updataRow(25,attMap.get('treesearch_required'));
			}
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(3,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------treesearch changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}

/*2080612 add by chenyl 新增图标选择组件属性值的回显*/
function _iconselect(attMap){
	var _posrc=attMap.get("poSrc");
	$('#dataGrid').propertygrid({
		url: 'iconselect.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined'){
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			if(attMap.get('value')!='undefined'){
				updataRow(6,attMap.get('value'));
			}
			if(attMap.get('url')!='undefined'){
				updataRow(7,attMap.get('url'));
			}
			if(attMap.get('自定义CSS')!='undefined'){
				updataRow(8,attMap.get('自定义CSS'));
			}
			if(attMap.get('icon_required')!='undefined'){
				updataRow(9,attMap.get('icon_required'));
			}
			console.info('iconselect');
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(7,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------iconselect changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}

/*2080618 add by chenyl 新增富文本编辑器组件属性值的回显*/
function _ueditor(attMap){
	var _posrc=attMap.get("poSrc");
	$('#dataGrid').propertygrid({
		url: 'ueditor.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined'){
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			if(attMap.get('value')!='undefined'){
				updataRow(6,attMap.get('value'));
			}
			if(attMap.get('style')!='undefined'){
				updataRow(7,attMap.get('style'));
			}
			
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(7,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------ueditor changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}

/*20180620 add by chenyl 新增文件管理组件属性值的回显*/
function _ckfinder(attMap){
	var _posrc=attMap.get("poSrc");
	$('#dataGrid').propertygrid({
		url: 'ckfinder.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('左占')!='undefined'){
				updataRow(0,attMap.get('左占'));
			}
			if(attMap.get('右占')!='undefined'){
				updataRow(1,attMap.get('右占'));
			}
			if(attMap.get('尺寸')!='undefined'){
				updataRow(2,attMap.get('尺寸'));
			}
			if(attMap.get('隐藏')!='undefined'){
				updataRow(3,attMap.get('隐藏'));
			}
			if(attMap.get('id')!='undefined'){
				updataRow(4,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(5,attMap.get('name'));
			}
			if(attMap.get('value')!='undefined'){
				updataRow(6,attMap.get('value'));
			}
			if(attMap.get('文件类型')!='undefined'){
				updataRow(7,attMap.get('文件类型'));
			}
			if(attMap.get('打开文件管理的上传路径')!='undefined'){
				updataRow(8,attMap.get('打开文件管理的上传路径'));
			}
			if(attMap.get('是否生成年份路径')!='undefined'){
				updataRow(9,attMap.get('是否生成年份路径'));
			}
			if(attMap.get('是否生成月份路径')!='undefined'){
				updataRow(10,attMap.get('是否生成月份路径'));
			}
			if(attMap.get('是否所有用户可见')!='undefined'){
				updataRow(11,attMap.get('是否所有用户可见'));
			}
			if(attMap.get('是否可以多选')!='undefined'){
				updataRow(12,attMap.get('是否可以多选'));
			}
			if(attMap.get('是否查看模式')!='undefined'){
				updataRow(13,attMap.get('是否查看模式'));
			}
			if(attMap.get('最大宽度')!='undefined'){
				updataRow(14,attMap.get('最大宽度'));
			}
			if(attMap.get('最大高度')!='undefined'){
				updataRow(15,attMap.get('最大高度'));
			}
			if(attMap.get('不为空')!='undefined'){
				updataRow(16,attMap.get('不为空'));
			}
			console.info('ckfinder');
			if(attMap.get("formtype")!='undefined'){
				if(attMap.get("formtype") === 'false'){
					deleteRow(1);
					deleteRow(0);
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(7,reData);
					}
				}else{
					if(_posrc!='undefined'&&_posrc!=''){
						var reData=getResultJson(_posrc);
						updataRowForCombox(5,reData);
					}	
				}
			}
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------treesearch changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}

/*20181225 add by chenyl 新增隐藏域组件属性值的回显*/
function _hidden(attMap){
	var _posrc=attMap.get("poSrc");
	$('#dataGrid').propertygrid({
		url: 'hidden.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('id')!='undefined'){
				updataRow(0,attMap.get('id'));
			}
			if(attMap.get('name')!='undefined'){
				updataRow(1,attMap.get('name'));
			}
			if(attMap.get('value')!='undefined'){
				updataRow(2,attMap.get('value'));
			}
			if(attMap.get('不为空')!='undefined'){
				updataRow(3,attMap.get('不为空'));
			}
			console.info('hidden');
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------treesearch changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}

function getJson(url){
	var reData="";
	$.ajax({
		url:url,
		dataType: 'json',
		async :false,//同步
		success : function(data) {
			reData= data.rows;
		}
	});
	return reData;
}
function returnReverse(reverse,data){
	console.info('returnReverse='+reverse+' , '+data);
	var str="";
	var leg=data.length;
	if(reverse==""){
		str=null;
	}else{
		for(var i=0;i<leg;i++){
			var _d=data[i].value.toLowerCase();
			var split=_d.split('#');
			if(split[0]==reverse){
				str=data[i].value;
			}
		}
	}
	console.info('returnReverse='+str);
	return str;
}
function getResultJson(url){
	var reData="";
	var data ="path="+url;
	$.ajax({
		type : "post",
		url:ctxIde+'/ide/getPoName',
		dataType : "json",
		data : data,
		async :false,//同步
		success : function(data) {
			var msg=data.msg;
			console.info(msg[0]);
			if(msg[0]!=null){
				alert(msg[0]);
			}
			reData= data.obj;
		}
	});
	return reData;
}
function _charPie(attMap){
	$('#dataGrid').propertygrid({
		url: 'chart.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('action')!='undefined'&&attMap.get('action')!=''){
				updataRow(2,attMap.get('action'));
			}
			deleteRow(1);
			deleteRow(0);
 	    },
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
function _charBar(attMap){
	$('#dataGrid').propertygrid({
		url: 'chart.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			if(attMap.get('起始ID')!='undefined'&&attMap.get('启始ID')!=''){
				updataRow(0,attMap.get('起始ID'));
			}if(attMap.get('结束ID')!='undefined'&&attMap.get('结束ID')!=''){
				updataRow(1,attMap.get('结束ID'));
			}
			if(attMap.get('action')!='undefined'){
				updataRow(2,attMap.get('action'));
			}
	    },
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			sendMessage('ide',row);
		}
	});
}
function _fileInput(attMap){
	var reData="";
	$('#dataGrid').propertygrid({
		url: 'fileInput.json',
		method: 'get',
		showGroup: true,
		striped: true,
		border: false,
		fitColumns:true,
		onLoadSuccess:function(data){
			console.info(attMap);
			if(attMap.get('是否预览')!='undefined'){
				updataRow(0,attMap.get('是否预览'));
			}
			if(attMap.get('是否显示删除')!='undefined'){
				updataRow(1,attMap.get('是否显示删除'));
			}
			if(attMap.get('是否显示取消')!='undefined'){
				updataRow(2,attMap.get('是否显示取消'));
			}
			if(attMap.get('是否显示上传')!='undefined'){
				updataRow(3,attMap.get('是否显示上传'));
			}
			if(attMap.get('是否显示标题')!='undefined'){
				updataRow(4,attMap.get('是否显示标题'));
			}
			if(attMap.get('只读')!='undefined'){
				updataRow(5,attMap.get('只读'));
			}
			if(attMap.get('不可用')!='undefined'){
				updataRow(6,attMap.get('不可用'));
			}
			if(attMap.get('最小上传数量')!='undefined'){
				updataRow(7,attMap.get('最小上传数量'));
			}
			if(attMap.get('最大上传数量')!='undefined'){
				updataRow(8,attMap.get('最大上传数量'));
			}
			if(attMap.get('接收的文件后缀')!='undefined'){
				updataRow(9,attMap.get('接收的文件后缀'));
			}
			if(attMap.get('接收的文件类型')!='undefined'){
				updataRow(10,attMap.get('接收的文件类型'));
			}
			if(attMap.get('预览文件类型')!='undefined'){
				updataRow(11,attMap.get('预览文件类型'));
			}
			
		},
		onAfterEdit:function(index,row,changes){
   			if(changes.value==undefined ||changes.value==null){
				return;
   			}
			console.info('-----------frame changes ------------');
			console.info(row);
			
			sendMessage('ide',row);
		}
	});
	return reData;
}
function getXmlAction(path){
	var reData="";
	var data ="xmlName="+path;
	$.ajax({
		type : "post",
		url:ctxIde+'/ide/getActionName',
		dataType : "json",
		data : data,
		async :false,//同步
		success : function(data) {
			var msg=data.msg;
			console.info(msg[0]);
			if(msg[0]!=null){
				alert(msg[0]);
			}
			reData= data.obj;
		}
	});
	return reData;
}
