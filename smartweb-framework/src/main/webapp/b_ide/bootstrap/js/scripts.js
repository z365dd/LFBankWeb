/// <reference path="/b_base/jquery-3.6.1.min.js"/>
var length = 0;// 用来计调用次数
function supportstorage() {
	if (typeof window.localStorage=='object') 
		return true;
	else
		return false;		
}

function handleSaveLayout(){
	var e=$(".demo").html();
	if(e!=window.demoHtml){
		saveLayout();
		window.demoHtml=e;
	};
}

function handleJsIds(){
	handleModalIds();
	handleAccordionIds();
	handleCarouselIds();
	handleTabsIds();
	}
// 手风琴切换
function handleAccordionIds(){
	var e=$(".demo #myAccordion");
	var t=randomNumber();
	var n="panel-"+t;
	var r;e.attr("id",n);e.find(".panel").each(function(e,t){
		r="panel-element-"+randomNumber();
		$(t).find(".panel-title").each(function(e,t){
			$(t).attr("data-parent","#"+n);
			$(t).attr("href","#"+r);
		});
		$(t).find(".panel-collapse").each(function(e,t){
			$(t).attr("id",r);
		});
	});
}
// 幻灯片
function handleCarouselIds(){
	var e=$(".demo #myCarousel");
	var t=randomNumber();
	var n="carousel-"+t;e.attr("id",n);
	e.find(".carousel-indicators li").each(function(e,t){
		$(t).attr("data-target","#"+n);
	});
	e.find(".left").attr("href","#"+n);
	e.find(".right").attr("href","#"+n);
}
// 遮罩窗体
function handleModalIds(){
	var e=$(".demo #myModalLink");
	var t=randomNumber();
	var n="modal-container-"+t;
	var r="modal-"+t;e.attr("id",r);
	e.attr("href","#"+n);
	e.next().attr("id",n);
	}
// 选项卡
function handleTabsIds(){
	var e=$(".demo #myTabs");
	var t=randomNumber();
	var n="tabs-"+t;e.attr("id",n);
	e.find(".tab-pane").each(function(e,t){
		var n=$(t).attr("id");
		var r="panel-"+randomNumber();$(t).attr("id",r);$(t).parent().parent().find("a[href=#"+n+"]").attr("href","#"+r);
	});
}

function randomNumber(){
	return randomFromInterval(1,1e6);
	}

function randomFromInterval(e,t){
	return Math.floor(Math.random()*(t-e+1)+e);
	}

function gridSystemGenerator(){
	$(".lyrow .preview input").bind("keyup",function(){
		var e=0;
		var t="";
		var n=false;
		var r=$(this).val().split(" ",12);
		$.each(r,function(r,i){
			if(!n){
				if(parseInt(i)<=0)n=true;
				e=e+parseInt(i);
				t+='<div class="col-md-'+i+' column"></div>';
				}
		});
		if(e==12&&!n){
			$(this).parent().next().children().html(t);
			$(this).parent().prev().show();
		}else{
			$(this).parent().prev().hide();
		}
	});
}

function configurationElm(e,t){
	$(".demo").delegate(".configuration > a","click",function(e){
		e.preventDefault();
		var t=$(this).parent().next().next().children();
		$(this).toggleClass("active");
		t.toggleClass($(this).attr("rel"));
	});
	$(".demo").delegate(".configuration .dropdown-menu a","click",function(e){
		e.preventDefault();
		var t=$(this).parent().parent();  //获取UL 列表
		var n=t.parent().parent().next().next().children(); //获取BUTTON
		t.find("li").removeClass("active");//去除UL的active
		$(this).parent().addClass("active");//添加UL的active
		var r="";
		t.find("a").each(function(){
			r+=$(this).attr("rel")+" ";//获取所有属性
		});
		t.parent().removeClass("open");
		n.removeClass(r);//去除Button的所有属性
		n.addClass($(this).attr("rel"));//添加新属性
	});
}
function removeElm(){
	$(".demo").delegate(".remove","click",function(e){
		e.preventDefault();
		$(this).parent().remove();
		if(!$(".demo .lyrow").length>0){
				clearDemo();
		}
	});
}
function clearDemo(){
	$(".demo").empty();
}

function removeMenuClasses(){
	$("#menu-layoutit li button").removeClass("active");
}

function changeStructure(e,t){
	$("#download-layout ."+e).removeClass(e).addClass(t);
	}

function cleanHtml(e){
	$(e).parent().append($(e).children().html());
}

function downloadLayoutSrc(){
	/* 下载 demo ui-sortable */
	$("#download-layout").children().html($(".demo").html());
	var t=$("#download-layout").children();
	
	/*20181225 add by chenyl for hidden隐藏域组件格式化处理*/
	t.find("[input_type='hidden']").each(function(i){
		$sys = $(this);
		$sys.attr('type', 'hidden');
		
		//ID
		if($sys.attr('hidden_id')!=undefined){
			$sys.attr('id', $sys.attr('hidden_id'));
		}else{
			$sys.attr('id', 'hid_'+randomNumber());
		}
		//隐藏域名称（NAME）
		if($sys.attr('hidden_name')!=undefined){
			$sys.attr('name', $sys.attr('hidden_name'));
		}else{
			$sys.attr('name', $sys.attr('hid'));
		}
		//隐藏域值（value）
		if($sys.attr('hidden_value')!=undefined){
			$sys.attr('value', $sys.attr('hidden_value'));
		}else{
			$sys.attr('value', $sys.attr(''));
		}		
		//是否必选
		if($sys.attr('hidden_required')!=undefined){
			$sys.attr('check-empty', $sys.attr('hidden_required'));
		}else{
			$sys.attr('check-empty', $sys.attr('false'));
		}
		
		/*移除不必要的属性*/
		$sys.removeAttr('hidden_id');
		$sys.removeAttr('hidden_name');
		$sys.removeAttr('hidden_value');
		$sys.removeAttr('hidden_required');
		$sys.removeAttr('input_type');
		$sys.removeAttr('class');
		$sys.removeAttr('readOnly');
		
		console.info("保存hiden="+$sys.html());
	});
	
	/*20180620 add by chenyl for ckfinder文件管理组件格式化处理*/
	t.find("[input_type='sys:ckfinder']").each(function(i){
		$sys = $(this);
		$div = $sys.parent();
		var ckfinder = '<sys:ckfinder ';
		//console.info("编号:"+$sys.attr('id')+", 输入框名称（Name）:"+$sys.attr('label_name'));
		//编号
		if($sys.attr('ckfinder_id')!=undefined){
			ckfinder = ckfinder + ' input="'+$sys.attr('ckfinder_id')+'"';
		}else{
			ckfinder = ckfinder + ' input="ckfinder_'+randomNumber()+'"';
		}
		//隐藏域名称（ID）
		if($sys.attr('ckfinder_name')!=undefined){
			ckfinder = ckfinder + ' name="'+$sys.attr('ckfinder_name')+'"';
		}else{
			ckfinder = ckfinder + ' name="ckfinder"';
		}
		//隐藏域值（ID）
		if($sys.attr('ckfinder_value')!=undefined){
			ckfinder = ckfinder + ' value="'+$sys.attr('ckfinder_value')+'"';
		}else{
			ckfinder = ckfinder + ' value=""';
		}		
		//文件类型
		if($sys.attr('ckfinder_type')!=undefined){
			ckfinder = ckfinder + ' type="'+$sys.attr('ckfinder_type')+'"';
		}else{
			ckfinder = ckfinder + ' type="files"';
		}
		//打开文件管理的上传路径
		if($sys.attr('ckfinder_upload_path')!=undefined){
			ckfinder = ckfinder + ' upload_path="'+$sys.attr('ckfinder_upload_path')+'"';
		}else{
			ckfinder = ckfinder + ' upload_path="/custom"';
		}
		//是否生成年份路径
		if($sys.attr('ckfinder_year_path')!=undefined){
			ckfinder = ckfinder + ' year_path="'+$sys.attr('ckfinder_year_path')+'"';
		}else{
			ckfinder = ckfinder + ' year_path="false"';
		}
		//是否生成月份路径
		if($sys.attr('ckfinder_month_path')!=undefined){
			ckfinder = ckfinder + ' month_path="'+$sys.attr('ckfinder_month_path')+'"';
		}else{
			ckfinder = ckfinder + ' month_path="false"';
		}
		//是否所有用户可见
		if($sys.attr('ckfinder_is_all_user')!=undefined){
			ckfinder = ckfinder + ' is_all_user="'+$sys.attr('ckfinder_is_all_user')+'"';
		}else{
			ckfinder = ckfinder + ' is_all_user="false"';
		}
		//是否必选
		if($sys.attr('ckfinder_required')!=undefined){
			ckfinder = ckfinder + ' ckfinder_required="'+$sys.attr('ckfinder_required')+'"';
		}else{
			ckfinder = ckfinder + ' ckfinder_required="false"';
		}
		//是否可以多选
		if($sys.attr('ckfinder_select_multiple')!=undefined){
			ckfinder = ckfinder + ' select_multiple="'+$sys.attr('ckfinder_select_multiple')+'"';
		}else{
			ckfinder = ckfinder + ' select_multiple="false"';
		}
		//是否查看模式
		if($sys.attr('ckfinder_readonly')!=undefined){
			ckfinder = ckfinder + ' readonly="'+$sys.attr('ckfinder_readonly')+'"';
		}else{
			ckfinder = ckfinder + ' readonly="false"';
		}
		//最大宽度
		if($sys.attr('ckfinder_max_width')!=undefined){
			ckfinder = ckfinder + ' max_width="'+$sys.attr('ckfinder_max_width')+'"';
		}else{
			ckfinder = ckfinder + ' max_width=""';
		}
		//最大高度
		if($sys.attr('ckfinder_max_height')!=undefined){
			ckfinder = ckfinder + ' max_height="'+$sys.attr('ckfinder_max_height')+'"';
		}else{
			ckfinder = ckfinder + ' max_height=""';
		}
		
		ckfinder = ckfinder + ' />';
		$div.html(ckfinder);
		console.info("保存ckfinder="+$div.html());
	});
	
	/*20180618 add by chenyl for ueditor富文本编辑器格式化处理*/
	t.find("[input_type='ueditor']").each(function(i){
		$sys = $(this);
		$div = $sys.parent();
		var ueditor = '<script type="text/plain" ';

		//编号
		if($sys.attr('ue_id')!=undefined){
			ueditor = ueditor + ' id="'+$sys.attr('ue_id')+'"';
		}else{
			ueditor = ueditor + ' id="ueditor_'+randomNumber()+'"';
		}
		//送值的name
		if($sys.attr('ue_name')!=undefined){
			ueditor = ueditor + ' name="'+$sys.attr('ue_name')+'"';
		}else{
			ueditor = ueditor + ' name="ueditor"';
		}
		//自定义style
		if($sys.attr('ue_style')!=undefined){
			ueditor = ueditor + ' style="'+$sys.attr('ue_style')+'"';
		}else{
			ueditor = ueditor + ' style=""';
		}		
		ueditor = ueditor + ' >';
		//设置的默认显示值
		if($sys.attr('ue_value')!=undefined){
			ueditor = ueditor + $sys.attr('ue_value');
		}
		
		ueditor = ueditor + '</script>';
		$div.html(ueditor);
		console.info("保存ueditor="+$div.html());
	});
	
	/*20180612 add by chenyl for iconselect图标选择组件格式化处理*/
	t.find("[input_type='sys:iconselect']").each(function(i){
		$sys = $(this);
		$div = $sys.parent();
		var inconselect = '<sys:iconselect ';
		//console.info("编号:"+$sys.attr('id')+", 输入框名称（Name）:"+$sys.attr('label_name'));
		//编号
		if($sys.attr('icon_id')!=undefined){
			inconselect = inconselect + ' id="'+$sys.attr('icon_id')+'"';
		}else{
			inconselect = inconselect + ' id="iconselect_'+randomNumber()+'"';
		}
		//隐藏域名称（ID）
		if($sys.attr('icon_name')!=undefined){
			inconselect = inconselect + ' name="'+$sys.attr('icon_name')+'"';
		}else{
			inconselect = inconselect + ' name="icon"';
		}
		//隐藏域值（ID）
		if($sys.attr('icon_value')!=undefined){
			inconselect = inconselect + ' value="'+$sys.attr('icon_value')+'"';
		}else{
			inconselect = inconselect + ' value=""';
		}		
		//图标数据地址
		if($sys.attr('icon_url')!=undefined){
			inconselect = inconselect + ' url="'+$sys.attr('icon_url')+'"';
		}else{
			inconselect = inconselect + ' url=""';
		}
		//自定义CSS
		if($sys.attr('icon_css')!=undefined){
			inconselect = inconselect + ' icon_css="'+$sys.attr('icon_css')+'"';
		}else{
			inconselect = inconselect + ' icon_css=""';
		}
		//是否必选
		if($sys.attr('icon_required')!=undefined){
			inconselect = inconselect + ' icon_required="'+$sys.attr('icon_required')+'"';
		}else{
			inconselect = inconselect + ' icon_required="false"';
		}
		
		inconselect = inconselect + ' />';
		$div.html(inconselect);
		console.info("保存inconselect="+$div.html());
	});
	
	/*20161018 add by chenyl for treesearch搜索树组件格式化处理*/
	t.find("[input_type='sys:treeselect']").each(function(i){
		$sys = $(this);
		$div = $sys.parent();
		var treesearch = '<sys:treeselect ';
		//console.info("编号:"+$sys.attr('id')+", 输入框名称（Name）:"+$sys.attr('label_name'));
		//编号
		if($sys.attr('id')!=undefined){
			treesearch = treesearch + ' id="'+$sys.attr('id')+'"';
		}else{
			treesearch = treesearch + ' id="treesearch_'+randomNumber()+'"';
		}
		//隐藏域名称（ID）
		if($sys.attr('name')!=undefined){
			treesearch = treesearch + ' name="'+$sys.attr('name')+'"';
		}else{
			treesearch = treesearch + ' name="treesearch.name"';
		}
		//隐藏域值（ID）
		if($sys.attr('value')!=undefined){
			treesearch = treesearch + ' value="'+$sys.attr('value')+'"';
		}else{
			treesearch = treesearch + ' value="treesearch.value"';
		}
		//输入框名称（Name）
		if($sys.attr('label_name')!=undefined){
			treesearch = treesearch + ' label_name="'+$sys.attr('label_name')+'"';
		}else{
			treesearch = treesearch + ' label_name="treesearch.label_name"';
		}
		//输入框值（Name）
		if($sys.attr('label_value')!=undefined){
			treesearch = treesearch + ' label_value="'+$sys.attr('label_value')+'"';
		}else{
			treesearch = treesearch + ' label_value="treesearch.label_value"';
		}
		//选择框标题
		if($sys.attr('title')!=undefined){
			treesearch = treesearch + ' title="'+$sys.attr('title')+'"';
		}else{
			treesearch = treesearch + ' title="搜索树标题"';
		}
		//树结构数据地址
		if($sys.attr('url')!=undefined){
			treesearch = treesearch + ' url="'+$sys.attr('url')+'"';
		}else{
			treesearch = treesearch + ' url="data2.json"';
		}
		//是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true
		if($sys.attr('checked')!=undefined){
			treesearch = treesearch + ' checked="true"';
		}
		//排除掉的编号（不能选择的编号）
		if($sys.attr('ext_id')!=undefined){
			treesearch = treesearch + ' ext_id="'+$sys.attr('ext_id')+'"';
		}
		//是否列出全部数据，设置true则不进行数据权限过滤（目前仅对Office有效）
		if($sys.attr('is_all')!=undefined){
			treesearch = treesearch + ' is_all="'+$sys.attr('is_all')+'"';
		}
		//不允许选择根节点
		if($sys.attr('not_allow_select_root')!=undefined){
			treesearch = treesearch + ' not_allow_select_root="'+$sys.attr('not_allow_select_root')+'"';
		}
		//不允许选择父节点
		if($sys.attr('not_allow_select_parent')!=undefined){
			treesearch = treesearch + ' not_allow_select_parent="'+$sys.attr('not_allow_select_parent')+'"';
		}
		//过滤栏目模型（只显示指定模型，仅针对CMS的Category树）
		if($sys.attr('module')!=undefined){
			treesearch = treesearch + ' module="'+$sys.attr('module')+'"';
		}
		//选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）
		if($sys.attr('select_scope_module')!=undefined){
			treesearch = treesearch + ' select_scope_module="'+$sys.attr('select_scope_module')+'"';
		}
		//是否允许清除
		if($sys.attr('allow_clear')!=undefined){
			treesearch = treesearch + ' allow_clear="'+$sys.attr('allow_clear')+'"';
		}
		//文本框可填写
		if($sys.attr('allow_input')!=undefined){
			treesearch = treesearch + ' allow_input="'+$sys.attr('allow_input')+'"';
		}
		//css样式
		if($sys.attr('css_class')!=undefined){
			treesearch = treesearch + ' css_class="'+$sys.attr('css_class')+'"';
		}
		//css扩展样式
		if($sys.attr('css_style')!=undefined){
			treesearch = treesearch + ' css_style="'+$sys.attr('css_style')+'"';
		}
		//缩小按钮显示
		if($sys.attr('small_btn')!=undefined){
			treesearch = treesearch + ' small_btn="'+$sys.attr('small_btn')+'"';
		}
		//是否显示按钮
		if($sys.attr('hide_btn')!=undefined){
			treesearch = treesearch + ' hide_btn="'+$sys.attr('hide_btn')+'"';
		}
		//是否限制选择，如果限制，设置为disabled
		if($sys.attr('disabled')!=undefined){
			treesearch = treesearch + ' disabled="'+$sys.attr('disabled')+'"';
		}
		//是否限制选择，如果限制，设置为disabled
		if($sys.attr('data_msg_required')!=undefined){
			treesearch = treesearch + ' data_msg_required="'+$sys.attr('data_msg_required')+'"';
		}
		//窗体高度
		if($sys.attr('win_height')!=undefined && ''!=$sys.attr('win_height')){
			treesearch = treesearch + ' win_height="'+$sys.attr('win_height')+'"';
		}
		//窗体宽度
		if($sys.attr('win_width')!=undefined && ''!=$sys.attr('win_width')){
			treesearch = treesearch + ' win_width="'+$sys.attr('win_width')+'"';
		}
		//校验信息
		if($sys.attr('validators')!=undefined){
			treesearch = treesearch + ' validators="'+$sys.attr('validators')+'"';
		}
		//是否必选
		if($sys.attr('treesearch_required')!=undefined){
			treesearch = treesearch + ' treesearch_required="'+$sys.attr('treesearch_required')+'"';
		}else{
			treesearch = treesearch + ' treesearch_required="false"';
		}
		
		treesearch = treesearch + ' />';
		$div.html(treesearch);
		//console.info("保存treesearch="+$div.html());
	});
	
	/*20160818 add by chenyl for datetime日期时间组件格式化处理 */
	t.find("input.Wdate").each(function(i){
		var $this = $(this);
		var _id = $this.attr('id');
		var _hid = $this.attr('data-link-field');
		var skin = $this.attr('datetime-skin');
		var dateFmt = $this.attr('datetime-date-fmt');
		var minDate = $this.attr('datetime-min-date');
		var maxDate = $this.attr('datetime-max-date');
		var isShowClear = $this.attr('datetime-is-show-clear');
		var isShowWeek = $this.attr('datetime-is-show-week');
		var isShowToday = $this.attr('datetime-is-show-today');
		var defaultValue = $this.attr('datetime-default-value');
		var valueFmt = $this.attr('datetime-value-fmt');
						
		console.info("组件id="+_id+",皮肤="+skin+",日期格式="+dateFmt+",值格式="+valueFmt);
		if(skin==undefined||skin==null||skin==""){
			skin = 'default'; //默认皮肤default
			$this.attr('datetime-skin','default');
		}
		if(dateFmt==undefined||dateFmt==null||dateFmt==""){
			dateFmt = 'yyyy-MM-dd HH:mm:ss'; //默认日期时间显示格式：yyyy-MM-dd HH:mm:ss
			$this.attr('datetime-date-fmt','yyyy-MM-dd HH:mm:ss');
		}
		if(valueFmt==undefined||valueFmt==null||valueFmt==""){
			valueFmt = 'yyyyMMddHHmmss'; //默认日期时间值格式：yyyyMMddHHmmss
			$this.attr('datetime-date-fmt','yyyyMMddHHmmss');
		}
		var params = "skin: '"+skin+"'"+",dateFmt: '"+dateFmt+"'";
		if(minDate!=undefined && minDate!=null && minDate!=""){
			params = params + ",minDate: '"+minDate+"'";
		}else{
			params = params + ",minDate: '1900-01-01 00:00:00'";
		}
		if(maxDate!=undefined && maxDate!=null && maxDate!=""){
			params = params + ",maxDate: '"+maxDate+"'";
		}else{
			params = params + ",maxDate: '2099-12-31 23:59:59'";
		}
		if(defaultValue!=undefined && defaultValue!=null && defaultValue!=""){
			params = params + ",startDate: '"+defaultValue+"'";
		}
		if(isShowClear!=undefined && isShowClear!=null && isShowClear!=""){
			params = params + ",isShowClear: "+isShowClear;
		}
		if(isShowWeek!=undefined && isShowWeek!=null && isShowWeek!=""){
			params = params + ",isShowWeek: "+isShowWeek;
		}
		if(isShowToday!=undefined && isShowToday!=null && isShowToday!=""){
			params = params + ",isShowToday: "+isShowToday;
		}
		/*生产控件触发事件*/
		$this.attr("onclick","WdatePicker({"
						 		+ params
						 		+",onpicked:function(dp){"
						 		+"	var newVal = dp.cal.getNewDateStr();"
						 		+"  console.info(newVal);"
						 		+"	writeDateValue('"+_id+"');"
						 		+"}"
						 	+"});");
		/*对控件添加change事件*/
		$this.attr("onchange","writeDateValue('"+_id+"');");
	});
	
	//20160929 add by chenyl 提交、重置按钮的特殊处理
	t.find("button[type='button']").each(function(i){
		var $this = $(this);
		var btnType = $this.attr("btn-type");
		console.info("处理按钮类型："+btnType);
		if(btnType!=undefined && btnType!=''){
			$this.attr('type', btnType);
		}else{
			//设置默认为button
			$this.attr('type', "button");
		}
	});
	
	//TODO 模态框格式化
    t.find("div[ravo='rainbow_fx_layout_modal']").each(function (i) {
    	
    	var $this = $(this);
    	/*modal-header-show="true" modal-header-close="true" modal-header-title="hello" modal-id="modalId_001" modal-width=""*/
    	//0.0获取参数
    	var modal_id = $this.attr('modal-id');
		var modal_width = $this.attr('modal-width');
		var modal_header_show = $this.attr('modal-header-show');
		var modal_header_close = $this.attr('modal-header-close');
		var modal_header_title = $this.attr('modal-header-title');
		
    	
    	//1.0获取model父节点
    	var modal_parent = $(this).parent();
    	//2.0 获取modal content
    	var modal_content = $(this).children('.column').first().children();
    	//2.0 删除自身节点
    	$(this).remove();
    	//3.0 替换成modal组件
    	modal_parent.append('<div class="modal" id="">'+
								'<div class="modal-dialog" style="">'+
									'<div class="modal-content">'+
										'<div class="modal-header">'+
											'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"> × </button>'+
											'<h4 class="modal-title" id="myModalLabel" contenteditable="true">标题</h4>'+
										'</div>'+
										'<div class="modal-body" ></div>'+
								'</div>'+
								'</div></div>'
								);
    	//4.0设置参数
    	modal_parent.find('.modal').attr('id',modal_id);
    	modal_parent.find('.modal-dialog').attr('style',modal_width);
    	if(modal_header_show==='true'){
    		if(modal_header_close==='false'){
    			modal_parent.find('.modal-header .close').remove();
    		}
    		modal_parent.find('.modal-header .modal-title').text(modal_header_title);
    	}else{
    		modal_parent.find('.modal-header').remove();
    	}
    	
    	//获取modal-body并填充内容
    	var modal_body = modal_parent.find('.modal-body').last();
    	modal_body.append(modal_content);
	});
	
	t.find(".preview, .configuration, .drag, .remove").remove();
	t.find(".lyrow").addClass("removeClean");
	t.find(".box-element").addClass("removeClean");
	
	t.find(".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean").each(function(){
	    cleanHtml(this);
	});
	
	t.find(".lyrow .lyrow .lyrow .lyrow .lyrow .removeClean").each(function(){
		    cleanHtml(this);
	});
	t.find(".lyrow .lyrow .lyrow .lyrow .removeClean").each(
		function(){
			cleanHtml(this);
		});
	t.find(".lyrow .lyrow .lyrow .removeClean").each(
		function(){
			cleanHtml(this);
		});
	t.find(".lyrow .lyrow .removeClean").each(
		function(){
			cleanHtml(this);
		});
	t.find(".lyrow .removeClean").each(
		function(){
			cleanHtml(this);
		});
	t.find(".removeClean").each(
		function(){
			cleanHtml(this);
		});
	t.find(".removeClean").remove();
	
	/*  */
	t.find("[ravo='rainbow_fx_remove']").addClass("ravoremoveClean");
	t.find(".ravoremoveClean .ravoremoveClean .ravoremoveClean .ravoremoveClean").each(
			function(){ 
				cleanHtml(this);
			});
	t.find(".ravoremoveClean .ravoremoveClean .ravoremoveClean").each(
			function(){ 
				cleanHtml(this);
			});
	t.find(".ravoremoveClean .ravoremoveClean").each(
			function(){ 
				cleanHtml(this);
			});
	t.find(".ravoremoveClean").each(
			function(){ 
				cleanHtml(this);
			});
	t.find(".ravoremoveClean").remove();
	t.find(".rainbow-select").removeClass("rainbow-select");
	
	/*
	 * table格式化BEG
	 */
	
	t.find('.bootstrap-table').each(function () {
		var bootstrap_table_parent = $(this).parent();
		
		var table = $(this).find('.fixed-table-body table');
		
		var thead = table.find('thead');
		
		thead.find('th').each(function name() {
			var input = $(this).find('.th-inner input');
			if(input!==null && input!==undefined){
				var _type = input.attr('type');
				if(_type ==='checkbox'){
					$(this).attr('data-checkbox','true');
				}
			}
			$(this).removeAttr('tabindex class style');
		});
		
		thead.find('.fht-cell').remove();
		
		thead.find('.th-inner').each(function(){
			var text = $(this).text();
			$(this).parent().empty().text(text);
		});
		
		table.empty();
		table.append(thead); 
		
		$(this).remove();
		bootstrap_table_parent.append(table); 
	});
	
	/* 
	 * table格式化END
	*/
	
	/* 
	 * Tabs格式化BEG...
	 */
	t.find('[data-toggle="tabs"]').each(function () {
		//获取data属性
		$this = $(this);
		var data = $this.data();
		$this.find('li:not(.dropdown )>a>i').remove();
		$this.next().css({height:'',overflow:''});
		$this.css({width:''});
		$this.next().css({width:''});
	});
	/*
	 * Tabs格式化END...
	 */
	
	
	/**
	 * 图片格式化BEG
	 */
	t.find(".fileInput").each(function(){
	/*	if($(this).has("span") !=""){
			$(this).find(".file-preview").remove();
			$(this).find(".hide").remove();
			$(this).find(".input-group").children().first().remove();
			$(this).find(".input-group").children().find("button[type='button']").remove();
			$(this).find(".input-group").children().find("button[type='submit']").remove();
			$("input").unwrap();
			$("input").unwrap();
			$("input").unwrap();
			$("input").unwrap();
			$(this).find(".glyphicon-folder-open").remove();
		}*/
		$(this).children().find(".file").clone().prependTo($(this));
		$(this).find("span").remove();
		/*$(this).find(".input-group").children().each(function(){
			$(this).first().remove();
//			$(this).find("button[type='button']").remove();
//			$(this).find("button[type='submit']").remove();
//			$("input").unwrap();
		});*/
		//$(this).children().remove();
		//$(this).append("<input id='file' class='file' multiple  type='file'>");
	});
	
	
	/* 
	 *  下拉框格式化 BEG
	 */
    t.find("select[data-role='multiselect']").each(function (i) {
    	$(this).removeAttr("style");
	    $(this).next().remove();
	    $(this).unwrap();
	});
	/*
	 *  下拉框格式化END
	 */
    
    /*
     * 模态框格式化BEG
     * <div class="view" id="model">
								<div ravo="rainbow_fx_layout_model" class="row clearfix" modal-header-show="true" modal-header-close="true" modal-header-title="hello" >
									<div class="col-md-12 column"></div>
								</div>
							</div>
							
		<div class="modal show" id="myModalContainer" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							hello
						</div>
						<div class="modal-body" >内容...</div>
					</div>
				</div>
		</div>
     */
    
    /*20170615 add by chenyl for 把可编辑的设置为contenteditable=false*/
	t.find('[contenteditable=true]').each(function(i){
		var $this = $(this);
		$this.attr('contenteditable', false);
	});
	
	$("#download-layout .column").removeClass("ui-sortable");
	$("#download-layout .row-fluid").removeClass("clearfix").children().removeClass("column");
	if($("#download-layout .container").length>0){
		changeStructure("row-fluid","row");
	}
	//formatSrc=$.htmlClean($("#download-layout").html(),{format:true,allowedAttributes:[["id"],["name"],["value"],["style"],["class"],["type"],["data-interval"],["data-ride"],["ravo"],["data-toggle"],["data-target"],["data-parent"],["role"],["data-dismiss"],["aria-labelledby"],["aria-hidden"],["data-slide-to"],["data-slide"],["placeholder"],["input-group-addon"],["prevtype"],["nexttype"]]});
	/*alert($("#download-layout").html());
	alert(formatSrc);*/
	//console.info("download-layout:"+$("#download-layout").html());
	formatSrc = beautify($("#download-layout").html());
	//console.info("formatSrc:"+formatSrc);
	$("#download-layout").html(formatSrc);
	$("#downloadModal textarea").empty();
	$("#downloadModal textarea").val(formatSrc);
	
	return formatSrc;
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

function htmlspecialchars(str){
	var str0 = '&nbsp;';
	var str1 = '&quot;';
	var str2 = '';
	var result = str.replace(eval("/"+str1+"/gi"),str2);
	
	/*20181225 add by chenyl for 替换html的空格转移字符&nbsp;为空格*/
	result = result.replace(eval("/"+str0+"/gi"),' ');
	
	result=encodeURI(result);
	return result;
}

var currentDocument=null;
var timerSave=2e3;
var demoHtml=$(".demo").html();

$(window).resize(function(){
	$("body").css("min-height",$(window).height()-90);
	$(".demo").css("min-height",$(window).height()-160);
});

function saveJspFile(msg){

	var formatSrc = htmlspecialchars(downloadLayoutSrc());
	$.ajax({
		   type: "POST",
		   data: 'jspfile='+formatSrc+'&path='+msg.value,
		   url: ctxIde+'/ide/ideJspSave',
		   success: function(data){
			   if(data.obj="SUCCESS"){
				   alert("保存成功");
			   }else{
				   alert("保存失败");
			   }
		   }
    });
	

}

function saveLayoutSrc(msg){
	
	var formatSrc = htmlspecialchars(downloadLayoutSrc());
	
	$.ajax({
		   type: "POST",
		   data: 'jspfile='+formatSrc+'&path='+msg.value,
		   url: ctxIde+'/ide/ideSave',
		   dataType:"json",
		   success: function(data){
			   if(data.obj="SUCCESS"){
				   alert("保存成功");
			   }else{
				   alert("保存失败");
			   }
		   }
	   });
}

var presentElement;//当前组件

var currenteditor = null;

function restoreData(){
	if (supportstorage()) {
		layouthistory = JSON.parse(localStorage.getItem("layoutdata"));
		if (!layouthistory) return false;
		window.demoHtml = layouthistory.list[layouthistory.count-1];
		if (window.demoHtml) $(".demo").html(window.demoHtml);
	}
}

$(document).ready(function(){
	
	CKEDITOR.disableAutoInline = true;
	restoreData();
	var contenthandle = CKEDITOR.replace( 'contenteditor' ,{
		language: 'zh-cn',
		contentsCss: ['css/bootstrap.min.css'],
		allowedContent: true
	});
	
	$("body").css("min-height",$(window).height()-90);
	
	$(".demo").css("min-height",$(window).height()-160);
	
	$(".demo, .demo .column").sortable({
		connectWith:".column",
		opacity:.35,
		handle:".drag"});
	
	$(".sidebar-nav .lyrow").draggable({
		connectToSortable:".demo",
		helper:"clone",handle:".drag",
		drag:function(e,t){
			t.helper.width(400);
		},
		stop:function(e,t){
			console.info("===========script.js up ===========");
			$(".demo .column").sortable({opacity:.35,connectWith:".column"});
			var type = $(this).find('div.view').attr('id');
			if(type==='form'){
				  var _form=$(".demo").find("div.view form");
				  _form.each(function(){
					  var _id=$(this).attr("id");
					  if(_id==undefined||_id==null||_id==""){
						  var _formId="formId_"+randomNumber();
						  $(this).attr("id",_formId);
					  }
				  });
		    }
		
		}
	});
	
	$(".sidebar-nav .box").draggable({
		connectToSortable:".column",
		helper:"clone",
		handle:".drag",
		drag:function(e,t){
			t.helper.width(400);
		},
	stop:function(e,t){
			console.info("===========script.js down ===========");
			length ++;
			handleJsIds();
			var type = $(this).find('div.view').attr('id');
			console.info(type);
			if(type ==='select'){
				$(".demo").find("div .view select[data-role='multiselect']").each(function(){
				     var _id=$(this).attr("id");
				     if(_id==undefined||_id==null||_id==""){
						 var _selectId="selectId_"+randomNumber();
					     $(this).attr("id",_selectId);
					  }
			     });
		      }  
		    if(type =='button'){
				 var _btn=$(".demo").find("div.view button");
					_btn.each(function(){
						  var _id=$(this).attr("id");
						  if(_id==undefined||_id==null||_id==""){
							  var _btnId="btnId_"+randomNumber();
							  $(this).attr("id",_btnId);
							  console.info("new btn:"+_btnId);
						  }
					 });
			  }
			  /*20160815 add by chenyl for 新增日期时间组件*/
			  else  if(type === 'datetime'){
				var demo = $(".demo");
				var chi = demo.find('div.view');
				for(var i=0;i<chi.length;i++){
					if(chi.eq(i).attr('id') === 'datetime'){
						var _id='datetime_'+randomNumber();
						console.info("datetime id ="+_id);
						chi.eq(i).children('div').children('div').children('.Wdate').attr('id',_id);
						console.info("datetime parent ="+$("#"+_id).parent());
						 $("#"+_id).on('click',function(){
						 	alert('注册了点击时间');
						 });						
					}
				
				}	
			}
			  /*else  if(type === 'time'){
				var demo = $(".demo");
				var chi = demo.find('div.view');
				for(var i=0;i<chi.length;i++){
					if(chi.eq(i).attr('id') === 'time'){
						chi.eq(i).children('div').children('div').children('div').attr('id','time'+i);
						 $("#time"+i+"").datetimepicker({
						        language:  'zh-CN',
						        weekStart: 1,
						        todayBtn:  1,
								autoclose: 1,
								todayHighlight: 1,
								startView: 2,
								minView: 2,
								forceParse: 0
						    });	
						
					}
				
				}	
			}*/
			else if(type === "canvas_bing" || type === "canvas_huan" || type === "canvas_diji"){
				var pieData = [{value: 300,
								color:"#F7464A",
								highlight: "#FF5A5E",
								label: "Red"},{
								value: 50,
								color: "#46BFBD",
								highlight: "#5AD3D1",
								label: "Green"},{
								value: 100,
								color: "#FDB45C",
								highlight: "#FFC870",
								label: "Yellow"},{
								value: 40,
								color: "#949FB1",
								highlight: "#A8B3C5",
								label: "Grey"},{
								value: 120,
								color: "#4D5360",
								highlight: "#616774",
								label: "Dark Grey"}];
				var demo = $(".demo");
				
				var chi = demo.find('div.view');
				for(var i=0;i<chi.length;i++){
					if(chi.eq(i).attr('id') === 'canvas_bing'){
						chi.eq(i).find('canvas').attr('id','canvas'+i);
						 var can = $("#canvas"+i+"").get(0).getContext("2d");
						 window.myPie = new Chart(can).Pie(pieData);
					}else if(chi.eq(i).attr('id') === 'canvas_huan'){
						chi.eq(i).find('canvas').attr('id','canvas'+i);
						 var can = $("#canvas"+i+"").get(0).getContext("2d");
						 window.myPie = new Chart(can).Doughnut(pieData);
					}else if(chi.eq(i).attr('id') === 'canvas_diji'){
						chi.eq(i).find('canvas').attr('id','canvas'+i);
						 var can = $("#canvas"+i+"").get(0).getContext("2d");
						 window.myPie = new Chart(can).PolarArea(pieData);
					}
				
				}	
			}else if(type === "canvas_quxian" || type === "canvas_leida" || type === "canvas_zhu"){
				var radarChartData = {
						labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],
						datasets: [
							{
								label: "My First dataset",
								fillColor: "rgba(220,220,220,0.2)",
								strokeColor: "rgba(220,220,220,1)",
								pointColor: "rgba(220,220,220,1)",
								pointStrokeColor: "#fff",
								pointHighlightFill: "#fff",
								pointHighlightStroke: "rgba(220,220,220,1)",
								data: [65,59,90,81,56,55,40]
							},
							{
								label: "My Second dataset",
								fillColor: "rgba(151,187,205,0.2)",
								strokeColor: "rgba(151,187,205,1)",
								pointColor: "rgba(151,187,205,1)",
								pointStrokeColor: "#fff",
								pointHighlightFill: "#fff",
								pointHighlightStroke: "rgba(151,187,205,1)",
								data: [28,48,40,19,96,27,100]
							}
						]
					};
			var demo = $(".demo");
			
			var chi = demo.find('div.view');
			for(var i=0;i<chi.length;i++){
				if(chi.eq(i).attr('id') === 'canvas_quxian'){
					chi.eq(i).find('canvas').attr('id','canvas'+i);
					 var can = $("#canvas"+i+"").get(0).getContext("2d");
					 window.myPie = new Chart(can).Line(radarChartData);
				}else if(chi.eq(i).attr('id') === 'canvas_leida'){
					chi.eq(i).find('canvas').attr('id','canvas'+i);
					 var can = $("#canvas"+i+"").get(0).getContext("2d");
					 window.myPie = new Chart(can).Radar(radarChartData);
				}else if(chi.eq(i).attr('id') === 'canvas_zhu'){
					chi.eq(i).find('canvas').attr('id','canvas'+i);
					 var can = $("#canvas"+i+"").get(0).getContext("2d");
					 window.myPie = new Chart(can).Bar(radarChartData);
				}
			
			}	
		  }
	   }
	});
	
	/* -download- =下载按钮= */
	$("[data-target=#downloadModal]").click(function(e){
		e.preventDefault();
		downloadLayoutSrc();
	});
	
	/* -save- =保存按钮= */
	/*$("[data-target=#saveModal]").click(function(e){
		console.info(e);
		e.preventDefault();
		//saveLayoutSrc();
	});*/
	/* 浏览器预览 */
	/*$("#browserPreview").click(function(e){
		e.preventDefault();
		saveLayoutSrc();
		window.open("http://localhost:8080/eide/jsp/bootstrap/preview/preview.jsp");   
	});*/
	
	$("#download").click(function(){
		downloadLayout();
		return false;
	});
	
	$("#downloadhtml").click(function(){
		downloadHtmlLayout();
		return false;
	});
	/* -edit- =编辑按钮= */
	$("#edit").click(function(){
		$("body").removeClass("devpreview sourcepreview");
		$("body").addClass("edit");
		removeMenuClasses();
		$(this).addClass("active");
		return false;
	});
	/* -clear- =清空按钮=*/
	$("#clear").click(function(e){
		e.preventDefault();
		clearDemo();
	});
	/* -devpreview- =开发按钮= */
	$("#devpreview").click(function(){
		$("body").removeClass("edit sourcepreview");
		$("body").addClass("devpreview");removeMenuClasses();
		$(this).addClass("active");
		return false;
	});
	/* -sourcepreview- =预览按钮= */
	$("#sourcepreview").click(function(){
		$("body").removeClass("edit");
		$("body").addClass("devpreview sourcepreview");removeMenuClasses();
		$(this).addClass("active");
		return false;
	});
	$(".nav-header").click(function(){
		$(".sidebar-nav .boxes, .sidebar-nav .rows").hide();
		$(this).next().slideDown();
	});
	
	$('body.edit .demo').on("click","[data-target=#editorModal]",function(e) {
		e.preventDefault();
		currenteditor = $(this).parent().parent().find('.view');
		var eText = currenteditor.html();
		contenthandle.setData(eText);
	});
	
	$("#savecontent").click(function(e) {
		e.preventDefault();
		currenteditor.html(contenthandle.getData());
	});
	
	
	removeElm();
	configurationElm();
	gridSystemGenerator();
	setInterval(function(){
		handleSaveLayout();
	},timerSave);
});
