/**
 * 全局变量：默认不属于弹出窗口
 */
var IS_OPEN_WINDOW = false;

/**
 * --------------------------------------------------
 *
 * @模块描述：全局命名空间
 * @负责人信息：陈应龙
 * @描述： SmartWeb全局命名空间，其他模块的命名空间可以通过(SmartWeb.二级命名空间名)进行扩展定义,各个模块均可以在此命名空间里定义域
 *      --------------------------------------------------
 */
var SmartWeb = {};
SmartWeb.namespace = function(str){
	var arr = str.split("."), o = SmartWeb;
	for(i=(arr[0] == "SmartWeb")?1:0 ; i<arr.length; i++){
		o[arr[i]] = o[arr[i]] || {};
		o = o[arr[i]];
	}
}

/* 可以在此定义全局变量 */
SmartWeb.IS_OPEN_WINDOW = false;
SmartWeb.FORM_SET_DIV = undefined;
SmartWeb.FORM_GET_DIV = undefined;

/**
 * --------------------------------------------------
 *
 * @模块描述：定义当前smartweb.js文件的命名空间为SmartWeb.swJS
 * @负责人信息：陈应龙
 * @描述： 把当前js文件的定义归到swJS中 --------------------------------------------------
 */
SmartWeb.swJS = new function(){
	/* ========== 用于指向当前对象 ========== */
	var self = this;
	/* ========== 私有属性域 ========== */
	var _taskName = "Task";

	/**
	 * 方法的功能描述
	 */
	function _findCoreTask(){
		alert("不对外暴露方法");
	}

	/* 对外暴露方法 */
	self.findTask = function (taskName) {
		alert("私有方法：" + taskName);
		_findCoreTask();
	};

	/*20191004 add by chenyl for 添加去掉字符串的左边所有空格*/
	self.ltrim = function (str) { //删除左边的空格
		return str.replace(/(^\s*)/g, "");
	}

	/*20191004 add by chenyl for 添加去掉字符串的右边所有空格*/
	self.rtrim = function (str) { //删除右边的空格
		return str.replace(/(\s*$)/g, "");
	}

	/*20191004 add by chenyl for 添加去掉字符串的左边和右边所有空格*/
	self.lrtrim = function (str) { //删除左右两端的空格
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}

	/**
	 * 设置表的列显示固定字符长度，如果table标签中存在open-click="true"属性时则点击单元格格式化为输入框
	 * 设置某些列显示的显示长度：SmartWeb.swJS.index.init({id:'table'},{len:20,column:0},{len:3,column:2},{len:10,column:1});
	 * 设置所有列显示的固定显示长度：SmartWeb.swJS.index.init({id:'table'},{dftlen:15});
	 * 设置所有列显示的固定显示长度：SmartWeb.swJS.index.init({id:'table'},{dftlen:15});
	 * 设置所有列显示的默认显示长度10个字符：SmartWeb.swJS.index.init({id:'table'});
	 *
	 * 一般在表格装载数据后事件调用： $("#table").on('load-success.bs.table', function (data)
	 * {SmartWeb.swJS.index.init({id:'table'});});
	 */
	self.index = {
		init:function(){
			var me = this;
			var parameter = arguments.length;
			var bol = arguments[0].id == undefined;
			if (bol) {
				console.log("表格无id");
			}
			me.render(arguments[0]);
			if(2==parameter && arguments[1].dftlen){
				/* 进行全局设置列的宽度 */
				me._whole(arguments[1], 1);
			} else if(1==parameter){
				/* 设置全局列默认宽度10个字符 */
				me._whole(arguments[0], 0);
			} else {
				/* 进行指定列的宽度进行设置 */
				for (var i = 1; i < parameter; i++) {
					me._maxLength(arguments[i]);

				}
			}
			me.bind(arguments[0]);
		},
		render:function(e){
			// console.log(e);
			var me = this;
			me.test = $("#"+e.id+" td");
			me.tr =  $("#"+e.id+" tr");
		},
		bind:function(e){
			var me = this;
			console.log(e);
			if($("#"+e.id).attr('open-click')=='true'){
				me.test.on('click',$.proxy(me["_do"],this));
			}
            /*20200521 add by chenyl for 修复表格中动态选择组件时无法正常使用的bug，当表格中的组件存在open-blur=true属性时开启重写blur事件 */
            me.tr.on('blur',"input[open-blur^='false']",function(){
				var input = me.tr.find("input");
				var inputVal = me.tr.find("input").val();
				var js_len = me.tr.find("input").parent().attr("js-len") || 10;;
				var tdStr = inputVal.substring(0, js_len);
				if(input.parent("td").hasClass("js-option")) {
					if(inputVal.length > js_len){
						var _tdStr = tdStr+"...";
						$("#opt").text(_tdStr);
						$("#opt").attr("title", inputVal);
					}else{
						$("#opt").text(inputVal);
						$("#opt").attr("title", inputVal);
					}
				}else{
					$("#opt").text(inputVal);
				}
				/* 20181121 add by tangxch 修復复选框和单选框失去焦点会消失的bug */
				for (var i = 0; i<input.length;i++) {
					console.log(input[i].type);
					if (input[i].type != 'checkbox' && input[i].type != 'radio') {
						input.remove();
					}
				}
				$("#opt").removeAttr("id");
			});
		},
		_do:function(e){
			var wid = wid =  $(e.target).width();
			var pad = 0;
			if(NaN!=parseInt($(e.target).css("padding-left"))){
				pad += parseInt($(e.target).css("padding-left"));
			}
			if(NaN!=parseInt($(e.target).css("padding-right"))){
				pad += parseInt($(e.target).css("padding-right"));
			}

			var bor = 0;
			if(NaN!=parseInt($(e.target).css("border-left"))){
				bor += parseInt($(e.target).css("border-left"));
			}
			if(NaN!=parseInt($(e.target).css("border-right"))){
				bor += parseInt($(e.target).css("border-right"));
			}

			var m = $(e.target).attr("title") || $(e.target).text();
			// console.log(m);
			var s = '<input type="text" class="form-control" style="border:0px;padding:0px;height:auto" value = "'+m+'">'
			$(e.target).html(s);
			$(e.target).css("width",(wid+pad+bor));
			$(e.target).attr("id","opt")
			$(e.target).find("input").focus();
		},
		_maxLength:function(e){
			// console.log(e);
			var me = this;
			me.tr.each(function() {
				// console.log($(this));
				var _slefText = $(this).children('td:eq('+e.column+')');
				_slefText.attr("class","js-option");
				_slefText.attr("js-len",e.len);
				var _tdText = _slefText.attr("title") ||_slefText.text();
				if(_tdText.length > e.len){
					var tdStr = _tdText.substring(0, e.len);
					var _tdStr = tdStr+"...";
	        		//add bu chenyl 修改表格数据有空格时替换去掉了空格问题
	        		var _slefHtml = _slefText.html();
	        		_slefHtml = _slefHtml.replace(_tdText, _tdStr);
	        		_slefText.html(_slefHtml);
					_slefText.attr("title", _tdText);
				};
			});
		},
		_whole:function(e,i){
			// console.log(e);
			var me = this;
			me.test.each(function() {
				var _slefText = $(this);
				_slefText.attr("class","js-option");
				_slefText.attr("js-len",e.dftlen);
				var _tdText = _slefText.text();
				if (i != 0) {
					_tdText = _slefText.attr("title") ||_slefText.text();
				}
				var leng = e.dftlen || 10;
				if(_tdText.length > leng){
					var tdStr = _tdText.substring(0, leng);
					var _tdStr = tdStr+"...";
					_slefText.text(_tdStr);
					_slefText.attr("title", _tdText);
				}
			});
		}
	};

	/***************************************************************************
	 * 页面渲染器 一般用于对CST文件中定义的UI组件渲染到当前页面指定的布局中的列中，在页面的装载成功后事件
	 * sourceId：CST中定义的UI组件的DIV标签的id destId：页面中布局中列的id，或DIV标签有column样式修饰的id
	 *
	 * 用法： $(document).ready(function(){
	 * SmartWeb.swJS.render.renderTo('sourceId', 'destId'); });
	 */
	self.render = {
		renderTo:function(sourceId, destId){
			try{
				console.info(sourceId+'->'+destId);
				var _sourceIdDiv = $('#'+sourceId);
				var _sourceIdTagName = _sourceIdDiv[0].tagName.toLowerCase();
				console.info(sourceId+'的标签：'+_sourceIdTagName);
				var _destIdDiv = $('#'+destId);
				var _destIdTagName = _destIdDiv[0].tagName.toLowerCase();
				console.info(destId+'的标签：'+_destIdTagName);
				if('div'==_sourceIdTagName && 'div'==_destIdTagName && _destIdDiv.hasClass('column')){
					/* 备份源组件 */
					var _sourceIdHtml = _sourceIdDiv.html();
					var _sourceIdClass = _sourceIdDiv.attr('class');
					var _sourceIdStyle = _sourceIdDiv.attr('style');
					/* 移除源组件 */
					_sourceIdDiv.remove();
					/* 清空目标标签内容 */
					_destIdDiv.empty();
					/* 在目标DIV下创建新的DIV */
					var _sourceIdDivSub = $('<div></div>');
					_sourceIdDivSub.attr('id', sourceId);
					_sourceIdDivSub.attr('class', _sourceIdClass);
					_sourceIdDivSub.attr('style', _sourceIdStyle);
					_sourceIdDivSub.html(_sourceIdHtml);
					_sourceIdDivSub.appendTo(_destIdDiv);
				}
			}catch(e){}
		}
	};
};

$(document).ready(function() {
	try{
		/* 20181206 add by chenyl for 动态引入加密js文件 */
		loadJs('jsencrypt', ctxStatic+'/jsencrypt.min.js');
		//20190919 add by zhouxina for 动态添加新样式css文件
		// if(ctxTheme=="tech" || ctxTheme=="green"){
			loadCss("styleCss",ctxStatic+"/mainframe/css_"+ctxTheme+"/style.css");
			loadCss("zujianCss",ctxStatic+"/mainframe/css_"+ctxTheme+"/zujian.css");

		// }

		/* 默认阻止点击回车按钮事件触发页面跳转，如需要单个页面使用这个事件可以重写 */
		$(document).keydown(function(event){
			/* 20181224 mod by chenyl for 对于按钮、文本域这些控件的回车事件不阻止 */
			var srcElement = event.target||event.srcElement;
			var tagType = srcElement.type;
			var tagName = srcElement.nodeName
			var _ReadOnly = srcElement.readOnly;
			var _disabled = srcElement.disabled;
			if (event.keyCode == 13 && undefined != srcElement) {
				if ('textarea' == tagType || 'button' == tagType || 'submit' == tagType || 'PRE' === tagName) {
					console.info(srcElement.type + ',不阻止回车');
				} else {
					event.stopPropagation();
					event.preventDefault();
					return false;
				}
			}
			/* 2019-02-26 add by lijunbin 阻止IE浏览器退格删除键返回上一页的事件 */
			if (event.keyCode === 8) {
				if (!(('INPUT' === tagName || 'TEXTAREA' === tagName || 'PRE' === tagName) && !_ReadOnly)) {
					console.info(tagName + ':' + tagType + ':' + _ReadOnly + ',阻止退格删除键');
					event.stopPropagation();
					event.preventDefault();
					return false;
				}
			}
		});

		/* 链接去掉虚框 */
		$("a").bind("focus",function() {
			if(this.blur) {this.blur()};
		});

		/* 20170811 add by chenyl for 对textarea初始化时全空格的trim掉 */
		$("textarea").each(function(){
			var $this = $(this);
			if(undefined!=$this.val() && $.trim($this.val()).length===0){
				$this.val("");
			}
		});

		/* 20170823 add by chenyl for 对tab组件的默认初始化 */
		$('[data-toggle="tabs"]').each(function () {
			$(this).tabs();
			/* 20181224 add by chenyl for 对于tab组件已经是active状态的再点击阻止触发事件 */
			var $this = $(this);
			$this.find('li a').each(function(){
				var $a = $(this);
				var $li = $a.parent();
				var clickBak = $a.click;
				$li.data('init', 'true');
				$a.click({'li':$li, 'a':$a, 'clickBak':clickBak}, function(event){
					/*
                        * 20190108 add by chenyl for
                        * 对于tab组件ul中添加了reload属性为true时，每次点击都重新装载
                        */
					var $ul = event.data.li.parent();
					var __reload = $ul.attr('reload');
					console.info('重新绑定点击事件');
					if ( event.data.li.hasClass("active") && event.data.li.data('init')!='true' && 'true'!=__reload){
						console.info(event.data.a.text() + '，已激活');
						event.data.a.attr('isLoad', 'true');
						event.stopPropagation();
						event.preventDefault();
						return false;
					}else{
						event.data.a.removeAttr('isLoad');
						event.data.li.data('init', 'false');
						event.data.clickBak;
					}
				});
			});
		});

		/* 20180618 add by chenyl for 对富文本编辑器初始化 */
		$('script[type="text/plain"]').each(function(){
			var $this = $(this);
			var _id = $this.attr("id");
			if(_id!=undefined){
				UE.getEditor(_id);
				uParse(_id, {
					rootPath: '../'
				});
			}
		});

		/* 20180306 add by chenyl for 写入ajax请求时的屏蔽层html */
		if(undefined==document.getElementById('#fountainTextG')){
			$(document.body).append('<div id="fountainTextG"><div style="width:260px;heigth:20px;position:fixed;left:50%;top:50%;margin-top:-10px;margin-left:-130px "><div id="fountainTextG_1" class="fountainTextG">L</div><div id="fountainTextG_2" class="fountainTextG">o</div><div id="fountainTextG_3" class="fountainTextG">a</div><div id="fountainTextG_4" class="fountainTextG">d</div><div id="fountainTextG_5" class="fountainTextG">i</div><div id="fountainTextG_6" class="fountainTextG">n</div><div id="fountainTextG_7" class="fountainTextG">g</div><div id="fountainTextG_8" class="fountainTextG">.</div><div id="fountainTextG_9" class="fountainTextG">.</div><div id="fountainTextG_10" class="fountainTextG">.</div></div></div>');
		}
		$('#fountainTextG').hide();

		/* 20181003 add by chenyl for 设置表格的表头对齐 */
		$('[data-toggle="table"]').each(function(){
			var $this = $(this);
			$this.css({"table-layout":"fixed !important"});
			/*20220219 add by chenyl for 点击一行记录背景颜色高亮*/
			$this.on('click-row.bs.table', function (e,row,$element) {
                $('.changeColor').removeClass('changeColor');
                $($element).addClass('changeColor');
            });
		});
	}catch(e){
		// blank
	}

	/* 20180306 add by chenyl for ajax请求时显示屏蔽层，请求结束时关闭屏蔽层 */
	// .ajaxError事件定位到document对象，文档内所有元素发生ajax请求异常，都将冒泡到document对象的ajaxError事件执行处理
	$(document).ajaxSend(function(evt, request, settings){
        //添加公共请求头参数
        request.setRequestHeader("authorization", authorization);
	}).ajaxStart(function(evt, request, settings){
		//alert("123");
		console.info("ajaxstart...");
		showLoading();
	}).ajaxStop(function(event,request, settings){
		console.info("ajaxstop...");
		hideLoading();
	}).ajaxError(

		// 所有ajax请求异常的统一处理函数，处理
		function(event,xhr,options,exc ){
			console.info("ajaxError...");
			top.$('#fountainTextG').hide();
			if(xhr.status == 'undefined'){
				return;
			}
			switch(xhr.status){
				// 20170822 add by chenyl 统一处理session超时的ajax请求
				case 401:
					alert('未登录或登录超时。请重新登录，谢谢！');
					top.location = ctx;
					break;
				case 403:
					// 未授权异常
					// alert("系统拒绝：您没有访问权限。");
					showContent("系统拒绝：您没有访问权限。","error");
					break;
				case 404:
					// alert("您访问的资源不存在。");
					showContent("您访问的资源不存在。","error");
					break;
				case 500:
					// alert("系统异常。");
					var msg = "系统异常。"+xhr.responseText;
					showContent(msg,"error");
					break;
			}
		}
	);

	chkWindowOpen();

	//20190909 add by zhouxina for tips提示的动态效果
	$(".div-tips").click(function(){
		$(this).toggle(500);
	});

});

/**
 * 显示加载层锁屏
 * @returns
 */
function showLoading(){
	console.info("$HIDE_LOADING:"+$.session.get('$HIDE_LOADING'));
	if('true'!=$.session.get('$HIDE_LOADING')){
		top.$('#fountainTextG').show();
	}
}

/**
 * 隐藏加载层锁屏
 * @returns
 */
function hideLoading(){
	top.$('#fountainTextG').hide();
}

/* 20181003 add by chenyl for 当窗口进行缩放时，自动调整table的视图 */
$(window).resize(function () {
	$('[data-toggle="table"]').each(function(){
		var $this = $(this);
		$this.bootstrapTable('resetView');
	});
});

function strTrans(oldStr,type){
	var newStr = "";
	if("1"==type){
		//全大写
		newStr = oldStr.toUpperCase();
	}else if("2"==type){
		//大驼峰
		newStr = oldStr.toLowerCase().replace(/\_(\w)/g, (all,letter)=>letter.toUpperCase());	
		newStr = newStr.substring(0,1).toUpperCase() + newStr.substring(1);
	}else if("3"==type){
		//小驼峰
		newStr = oldStr.toLowerCase().replace(/\_(\w)/g, (all,letter)=>letter.toUpperCase());
	}else if("4"==type){
		//全小写
		newStr = oldStr.toLowerCase();		
	}else{
		//默认
		newStr = oldStr;
	}
	return newStr;
}

/* 检查当前页面是否为弹出窗口 */
function chkWindowOpen(){
	var winUrl = window.location.href;
	var isOpenWindow = getQueryString('IS_OPEN_WINDOW',winUrl);
	console.info("url="+winUrl+" , isOpenWindow="+isOpenWindow);
	if(null!==isOpenWindow){
		IS_OPEN_WINDOW = true;
	}else{
		IS_OPEN_WINDOW = false;
	}
	return IS_OPEN_WINDOW;
}

/**
 * JquerySession是一个基于jquery的用来处理session的库，使用它可以简化我们的工作。在使用之前需要引入jquery。
 * 注意：页面缓存使用的是document.cookie实现的，因此设置的value值种不能包含分号（;）、逗号（,）、等号（=）以及空格
 *
 * 添加数据 $.session.set('key', 'value')
 *
 * 删除数据 $.session.remove('key');
 *
 * 获取数据 $.session.get('key');
 *
 * 清除数据 $.session.clear();
 */
(function($) {
	$.session = {
		_id: null,
		_cookieCache: undefined,
		_init: function() {
			if (!window.name) {
				window.name = Math.random();
			}
			this._id = window.name;
			this._initCache();
			var matches = (new RegExp(this._generatePrefix() + "=([^;]+);")).exec(document.cookie);
			if (matches && document.location.protocol !== matches[1]) {
				this._clearSession();
				for (var key in this._cookieCache) {
					try {
						window.sessionStorage.setItem(key, this._cookieCache[key]);
					} catch(e) {};
				}
			}
			document.cookie = this._generatePrefix() + "=" + document.location.protocol + ';path=/;expires=' + (new Date((new Date).getTime() + 120000)).toUTCString();
		},
		_generatePrefix: function() {
			return '__session:' + this._id + ':';
		},
		_initCache: function() {
			var cookies = document.cookie.split(';');
			this._cookieCache = {};
			for (var i in cookies) {
				var kv = cookies[i].split('=');
				if ((new RegExp(this._generatePrefix() + '.+')).test(kv[0]) && kv[1]) {
					this._cookieCache[kv[0].split(':', 3)[2]] = kv[1];
				}
			}
		},
		_setFallback: function(key, value, onceOnly) {
			var cookie = this._generatePrefix() + key + "=" + value + "; path=/";
			if (onceOnly) {
				cookie += "; expires=" + (new Date(Date.now() + 120000)).toUTCString();
			}
			document.cookie = cookie;
			this._cookieCache[key] = value;
			return this;
		},
		_getFallback: function(key) {
			if (!this._cookieCache) {
				this._initCache();
			}
			return this._cookieCache[key];
		},
		_clearFallback: function() {
			for (var i in this._cookieCache) {
				document.cookie = this._generatePrefix() + i + '=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
			}
			this._cookieCache = {};
		},
		_deleteFallback: function(key) {
			document.cookie = this._generatePrefix() + key + '=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
			delete this._cookieCache[key];
		},
		get: function(key) {
			return window.sessionStorage.getItem(key) || this._getFallback(key);
		},
		set: function(key, value, onceOnly) {
			try {
				window.sessionStorage.setItem(key, value);
			} catch(e) {}
			this._setFallback(key, value, onceOnly || false);
			return this;
		},
		'delete': function(key) {
			return this.remove(key);
		},
		remove: function(key) {
			try {
				window.sessionStorage.removeItem(key);
			} catch(e) {};
			this._deleteFallback(key);
			return this;
		},
		_clearSession: function() {
			try {
				window.sessionStorage.clear();
			} catch(e) {
				for (var i in window.sessionStorage) {
					window.sessionStorage.removeItem(i);
				}
			}
		},
		clear: function() {
			this._clearSession();
			this._clearFallback();
			return this;
		}
	};
	$.session._init();
})(jQuery);

(function($){
	if(typeof($.fn.initSwitch) != 'undefined') {return false;} // prevent dmultiple scripts inits	
	$.fn.initSwitch = function() {
		// destruct
		$.fn.swithc_destroy = function() {			
			$(this).each(function() {
                var $wrap = $(this).parents('.swithc_wrap');				
				$wrap.children().not('input').remove();
				$(this).unwrap();
            });		
			return true;
		};	
		
		// set to ON
		$.fn.swithc_on = function() {			
			$(this).each(function() {
                var $wrap = $(this).parents('.swithc_wrap');
				var $input = $wrap.find('input');			
				if(typeof($.fn.prop) == 'function') {
					$wrap.find('input').prop('checked', true);
				} else {
					$wrap.find('input').attr('checked', true);
				}				
				$wrap.find('input').trigger('lcs-on');
				$wrap.find('input').trigger('lcs-statuschange');
				$wrap.find('.swithc').removeClass('swithc_off').addClass('swithc_on');	
				$wrap.find('.swithc').css("background",$(this).prev().attr("onColor"));	
				$(this).prev().val("Y");				
				// if radio - disable other ones 
				if( $wrap.find('.swithc').hasClass('swithc_radio_switch') ) {
					var f_name = $input.attr('name');
					$wrap.parents('form').find('input[name='+f_name+']').not($input).swithc_off();	
				}
            });			
			return true;
		};	
				
		// set to OFF
		$.fn.swithc_off = function() {			
			$(this).each(function() {
                var $wrap = $(this).parents('.swithc_wrap');
				if(typeof($.fn.prop) == 'function') {
					$wrap.find('input').prop('checked', false);
				} else {
					$wrap.find('input').attr('checked', false);
				}				
				$wrap.find('input').trigger('lcs-off');
				$wrap.find('input').trigger('lcs-statuschange');
				$wrap.find('.swithc').removeClass('swithc_on').addClass('swithc_off');
				$wrap.find('.swithc').css("background",$(this).prev().attr("offColor"));
				$(this).prev().val("N");	
            });			
			return true;
		};	
				
		// construct
		return this.each(function(){			
			// check against double init
			if( !$(this).parent().hasClass('swithc_wrap') ) {			
				// default texts and colors
				var onText = $(this).attr("onText");
				var offText = $(this).attr("offText");
				var onColor = $(this).attr("onColor");
				var offColor = $(this).attr("offColor");
				var ckd_on_txt = (typeof(onText) == 'undefined' || onText == '') ? 'ON' : $(this).attr("onText");
				var ckd_off_txt = (typeof($(this).attr("offText")) == 'undefined' || offText == '') ? 'OFF' : $(this).attr("offText");
				var ckd_on_color = (typeof($(this).attr("onColor")) == 'undefined' || onColor == '') ? '' : $(this).attr("onColor");
				var ckd_off_color = (typeof($(this).attr("offColor")) == 'undefined' || offColor == '') ? '' : $(this).attr("offColor");				
			    //alert($(this).attr("test-attr"));
			    // labels structure
				var on_label = (ckd_on_txt) ? '<div class="swithc_label swithc_label_on">'+ ckd_on_txt +'</div>' : '';
				var off_label = (ckd_off_txt) ? '<div class="swithc_label swithc_label_off">'+ ckd_off_txt +'</div>' : '';							
				// default states
				var disabled 	= ($(this).is(':disabled')) ? true: false;
				var active 		= ($(this).is(':checked')) ? true : false;				
				var status_classes = '';
				status_classes += (active) ? ' swithc_on' : ' swithc_off'; 
				if(disabled) {status_classes += ' swithc_disabled';} 			   			   
				// wrap and append
				var color = "";
				if(active){
					color = ckd_on_color;
				}else{
					color = ckd_off_color;
				}
				var structure = 
				'<div class="swithc '+status_classes+'" style="background:'+color+'">' +
					'<div class="swithc_cursor"></div>' +
					on_label + off_label +
				'</div>';			   
				if( $(this).is(':input') && ($(this).attr('type') == 'checkbox' || $(this).attr('type') == 'radio') ) {					
					$(this).wrap('<div class="swithc_wrap"></div>');
					$(this).parent().append(structure);					
					$(this).parent().find('.swithc').addClass('swithc_'+ $(this).attr('type') +'_switch');
				}
			}
        });
	};	
		
	// handlers
	$(document).ready(function() {		
		// on click
		$(document).delegate('.swithc:not(.swithc_disabled)', 'click tap', function(e) {
			if( $(this).hasClass('swithc_on') ) {
				if( !$(this).hasClass('swithc_radio_switch') ) { // not for radio
					$(this).swithc_off();
				}
			} else {
				$(this).swithc_on();	
			}
		});
				
		// on checkbox status change
		$(document).delegate('.swithc_wrap input', 'change', function() {
			if( $(this).is(':checked') ) {
				$(this).swithc_on();
			} else {
				$(this).swithc_off();	
			}	
		});		
	});	
})(jQuery);



// 引入js和css文件,此方法会把整个页面重写
function include(id, path, file){
	if (document.getElementById(id)==null){
		var files = typeof file == "string" ? [file] : file;
		for (var i = 0; i < files.length; i++){
			var name = files[i].replace(/^\s|\s$/g, "");
			var att = name.split('.');
			var ext = att[att.length - 1].toLowerCase();
			var isCSS = ext == "css";
			var tag = isCSS ? "link" : "script";
			var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
			var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
			document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
		}
	}
}

/**
 * 动态装载css文件
 *
 * @param id
 *            引入CSS文件的id
 * @param url
 *            CSS文件路径
 * @param callback
 *            引入CSS文件后的回调函数
 * @returns
 */
function loadCss(id, url, callback){
	if (document.getElementById(id)==null){
		var head = document.getElementsByTagName('head')[0];
		if(null==head || undefined==head){
			head = document.createElement('head');
			document.appendChild(head);
		}
		var script=document.createElement('link');
		script.type="text/css";
		script.rel="stylesheet";
		script.id = id;
		script.href=url;
		if(typeof(callback)!="undefined"){
			if(script.readyState){
				script.onreadystatechange=function(){
					if(script.readyState == "loaded" || script.readyState == "complete"){
						script.onreadystatechange=null;
						callback();
					}
				}
			}else{
				script.onload=function(){
					callback();
				}
			}
		}
		head.appendChild(script);
	}
}

/**
 * 动态装载js文件
 *
 * @param id
 *            引入js文件的id
 * @param url
 *            js文件路径
 * @param callback
 *            引入js文件后的回调函数
 * @returns
 */
function loadJs(id, url, callback){
	if (document.getElementById(id)==null){
		var head = document.getElementsByTagName('head')[0];
		if(null==head || undefined==head){
			head = document.createElement('head');
			document.appendChild(head);
		}
		var script=document.createElement('script');
		script.type="text/javascript";
		script.id = id;
		script.src=url;
		if(typeof(callback)!="undefined"){
			if(script.readyState){
				script.onreadystatechange=function(){
					if(script.readyState == "loaded" || script.readyState == "complete"){
						script.onreadystatechange=null;
						callback();
					}
				}
			}else{
				script.onload=function(){
					callback();
				}
			}
		}
		head.appendChild(script);
	}
}

// 获取URL地址参数
function getQueryString(name, url) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	if (!url || url == ""){
		url = window.location.search;
	}else{
		url = url.substring(url.indexOf("?"));
	}
	r = url.substr(1).match(reg)
	if (r != null) return unescape(r[2]); return null;
}

// 获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

/**
 * 打开个对话窗口
 *
 * @param url
 *            请求路径
 * @param reqObj
 *            请求参数的数据对象
 * @param width
 *            窗口的宽度
 * @param height
 *            窗口的高度
 * @returns 返回弹出窗口的返回值
 */
/*
 * function openDialog(url, reqObj, width, height){ var
 * top=parseInt((window.screen.height-height)/2,10); var
 * left=parseInt((window.screen.width-width)/2,10); var
 * options="scroll=yes;status=yes;"+"dialogWidth="+width+";dialogHeight="+height+";dialogTop="+top+";dialogLeft="+left+";";
 * if(url.indexOf('?')!=-1){ //原始路径存在参数 url = url+"&IS_OPEN_WINDOW=true"; }else{
 * //原始路径不存在参数 url = url+"?IS_OPEN_WINDOW=true"; } var resObj =
 * window.showModalDialog(url, reqObj, options); return resObj;
 *  }
 */

/**
 *
 * @param url
 *            请求路径
 * @param name
 *            窗口名称
 * @param reqSrcElement
 *            输入参数的源DOM名称，以#!分割(父窗口)
 * @param reqDestElement
 *            输入参数的目的DOM名称，以#!分割(子窗口)
 * @param resSrcElement
 *            返回参数的源DOM名称，以#!分割(子窗口)
 * @param resDestElement
 *            返回参数的目的DOM名称，以#!分割(父窗口)
 * @param width
 *            口的宽度
 * @param height
 *            窗口的高度
 * @returns
 */
function openDialog(url, name, width, height, reqSrcElement, reqDestElement, resSrcElement, resDestElement){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
			"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	if(url.indexOf('?')!=-1){
		// 原始路径存在参数
		url = url+"&IS_OPEN_WINDOW=true&REQ_SRC_ELEMENT="+reqSrcElement+"&REQ_DEST_ELEMENT="+reqDestElement+"&RES_SRC_ELEMENT="+resSrcElement+"&RES_DEST_ELEMENT="+resDestElement;
	}else{
		// 原始路径不存在参数
		url = url+"?IS_OPEN_WINDOW=true&REQ_SRC_ELEMENT="+reqSrcElement+"&REQ_DEST_ELEMENT="+reqDestElement+"&RES_SRC_ELEMENT="+resSrcElement+"&RES_DEST_ELEMENT="+resDestElement;
	}
	var result = window.open(url ,name , options);
	window.onfocus=function(){
		result.focus();
	}
	window.onclick=function(){
		result.focus();
	}
}

/**
 * 设置弹出窗口的输入参数，在子窗口调用
 *
 * @returns
 */
function setDialogArguments(){
	var winUrl = window.location.href;
	var srcElement = getQueryString('REQ_SRC_ELEMENT',winUrl);
	var destElement = getQueryString('REQ_DEST_ELEMENT',winUrl);
	console.info("设置输入参数：srcElement="+srcElement+" , destElement="+destElement);
	var srcArray = new Array();
	var destArray = new Array();
	srcArray = srcElement.split('#!');
	destArray = destElement.split('#!');
	for(i=0;i<srcArray.length;i++){
		try{
			var srcName = srcArray[i];
			var destName = destArray[i];
			if(srcName=='' || destName==''){
				continue;
			}
			document.getElementById(destName).value = window.opener.document.getElementById(srcName).value;
		}catch(e){
			console.info("设置第"+(i+1)+"参数时发生异常："+e);
		}
	}
}

/**
 * 设置弹出窗口的返回值，在子窗口关闭时调用
 *
 * @returns
 */
function setDialogReturnValue(){
	var winUrl = window.location.href;
	var srcElement = getQueryString('RES_SRC_ELEMENT',winUrl);
	var destElement = getQueryString('RES_DEST_ELEMENT',winUrl);
	console.info("设置返回值参数：srcElement="+srcElement+" , destElement="+destElement);
	var srcArray = new Array();
	var destArray = new Array();
	srcArray = srcElement.split('#!');
	destArray = destElement.split('#!');
	for(i=0;i<srcArray.length;i++){
		try{
			var srcName = srcArray[i];
			var destName = destArray[i];
			if(srcName=='' || destName==''){
				continue;
			}
			window.opener.document.getElementById(destName).value = document.getElementById(srcName).value;
		}catch(e){
			console.info("设置第"+(i+1)+"参数时发生异常："+e);
		}
	}
}

/**
 * 判断当前浏览器是否为IE
 *
 * @returns
 */
function isIE(){
	if(window.ActiveXObject || "ActiveXObject" in window){
		return true;
	}else{
		return false;
	}
}

/**
 * 获取弹出窗口的参数
 *
 * @returns
 */
function getDialogArguments(){
	var parent;
	// 谷歌情况下
	if(window.opener != undefined){
		parent = window.opener;
	}else{
		parent = window.dialogArguments;
	}
	return parent;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
			"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	if(url.indexOf('?')!=-1){
		// 原始路径存在参数
		url = url+"&IS_OPEN_WINDOW=true";
	}else{
		// 原始路径不存在参数
		url = url+"?IS_OPEN_WINDOW=true";
	}
	window.open(url ,name , options);
}

// 恢复提示框显示
function resetTip(){
	if(IS_OPEN_WINDOW){
		$.jBox.tip.mess = null;
	}else{
		top.$.jBox.tip.mess = null;
	}
}

// 关闭提示框
function closeTip(){
	if(IS_OPEN_WINDOW){
		$.jBox.closeTip();
	}else{
		top.$.jBox.closeTip();
	}
}

// 显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		if(IS_OPEN_WINDOW){
			$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0,
				timeout:  timeout == undefined ? 2000 : timeout});
		}else{
			top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0,
				timeout:  timeout == undefined ? 2000 : timeout});
		}
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在提交，请稍等...";
	}
	resetTip();
	if(IS_OPEN_WINDOW){
		$.jBox.tip(mess,'loading',{opacity:0});
	}else{
		top.$.jBox.tip(mess,'loading',{opacity:0});
	}
}

// 警告对话框
function alertx(mess, closed){
	if(IS_OPEN_WINDOW){
		$.jBox.info(mess, '提示', {closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
		$('.jbox-body .jbox-icon').css('top','55px');
	}else{
		top.$.jBox.info(mess, '提示', {closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
		top.$('.jbox-body .jbox-icon').css('top','55px');
	}
}

// 确认对话框
function confirmx(mess, href, closed){
	if(IS_OPEN_WINDOW){
		$.jBox.confirm(mess,'系统提示',function(v,h,f){
			if(v=='ok'){
				if (typeof href == 'function') {
					href();
				}else{
					resetTip(); // loading();
					location = href;
				}
			}
		},{buttonsFocus:1, closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
		$('.jbox-body .jbox-icon').css('top','55px');
	}else{
		top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
			if(v=='ok'){
				if (typeof href == 'function') {
					href();
				}else{
					resetTip(); // loading();
					location = href;
				}
			}
		},{buttonsFocus:1, closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
		top.$('.jbox-body .jbox-icon').css('top','55px');
	}
	return false;
}

// 提示输入对话框
function promptx(title, lable, href, closed){
	if(IS_OPEN_WINDOW){
		$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable + "：<input type='text' id='txt' name='txt'/></div>", {
			title: title, submit: function (v, h, f){
				if (f.txt == '') {
					$.jBox.tip("请输入" + lable + "。", 'error');
					return false;
				}
				if (typeof href == 'function') {
					href();
				}else{
					resetTip(); // loading();
					location = href + encodeURIComponent(f.txt);
				}
			},closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
	}else{
		top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable + "：<input type='text' id='txt' name='txt'/></div>", {
			title: title, submit: function (v, h, f){
				if (f.txt == '') {
					top.$.jBox.tip("请输入" + lable + "。", 'error');
					return false;
				}
				if (typeof href == 'function') {
					href();
				}else{
					resetTip(); // loading();
					location = href + encodeURIComponent(f.txt);
				}
			},closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
	}
	return false;
}

// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	top.$.fn.jerichoTab.addTab({
		tabFirer: $this,
		title: title,
		closeable: closeable == undefined,
		data: {
			dataType: 'iframe',
			dataLink: url
		}
	}).loadData(refresh != undefined);
}

// cookie操作
function cookie(name, value, options) {
	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires
			// attribute,
			// max-age is not
			// supported by IE
		}
		var path = options.path ? '; path=' + options.path : '';
		var domain = options.domain ? '; domain=' + options.domain : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
}

// 数值前补零
function pad(num, n) {
	var len = num.toString().length;
	while(len < n) {
		num = "0" + num;
		len++;
	}
	return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);
}

// 截取字符串，区别汉字和英文
function abbr(name, maxLength){
	if(!maxLength){
		maxLength = 20;
	}
	if(name==null||name.length<1){
		return "";
	}
	var w = 0;// 字符串长度，一个汉字长度为2
	var s = 0;// 汉字个数
	var p = false;// 判断字符串当前循环的前一个字符是否为汉字
	var b = false;// 判断字符串当前循环的字符是否为汉字
	var nameSub;
	for (var i=0; i<name.length; i++) {
		if(i>1 && b==false){
			p = false;
		}
		if(i>1 && b==true){
			p = true;
		}
		var c = name.charCodeAt(i);
		// 单字节加1
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
			w++;
			b = false;
		}else {
			w+=2;
			s++;
			b = true;
		}
		if(w>maxLength && i<=name.length-1){
			if(b==true && p==true){
				nameSub = name.substring(0,i-2)+"...";
			}
			if(b==false && p==false){
				nameSub = name.substring(0,i-3)+"...";
			}
			if(b==true && p==false){
				nameSub = name.substring(0,i-2)+"...";
			}
			if(p==true){
				nameSub = name.substring(0,i-2)+"...";
			}
			break;
		}
	}
	if(w<=maxLength){
		return name;
	}
	return nameSub;
}

/**
 * form表单系列化并返回对应的json对象 调用方式：$('#formId').serializeObject();
 */
$.fn.serializeObject = function(){
	var o = {};
	var a = this.serializeArray();
	$.each(a,function(){
		if(o[this.name]){
			if(!o[this.name].push){
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		}else{
			o[this.name] = this.value || '';
		}
	});
	return o;
};

/**
 * 显示操作结果信息 content:消息内容 type:消息类型(info、success、warning、error、loading)
 */
function showContent(content,type){
	try{
		console.info("showContent type="+type);
		if(type==undefined || type==null){
			if(content!=null && content.indexOf('失败')>-1){
				type = 'error';
			}else{
				type = 'success';
			}
		}
		console.info($('#messageBox').html());
		if($('#messageBox').length == 0){
			$('body').prepend('<div id="messageBox" class="alert alert-'+type+'"><button data-dismiss="alert" class="close">×</button><span id="messageContent"></span></div>');
			$('#messageContent').text(content);
		}else{
			$('#messageBox').removeClass().addClass('alert alert-'+type);
			$('#messageContent').text(content);
		}
		console.info($('#messageBox').html());
		console.info(top.$.jBox.tip.mess);
		// if(!top.$.jBox.tip.mess){
		// top.$.jBox.tip.mess=1;
		$("#messageBox").show();
		//jbox输出html标签需要转义
		top.$.jBox.tip(content.replace(/</g, '&lt;').replace(/>/g, '&gt;'),type,{persistent:true,opacity:0});
		// }
	}catch(e){}
}

function showContent_bak(content,type){
	try{
		console.info("showContent type="+type);
		if(type==undefined || type==null){
			if(content!=null && content.indexOf('失败')>-1){
				type = 'error';
			}else{
				type = 'success';
			}
		}
		console.info($('#messageBox').html());
		var tips = "";
		if(type=="success"){
			tips = "成功";
		}else if(type=="error"){
			tips = "失败";
		}else{
			tips = "注意";
		}
		if($('#messageBox').length == 0){
			$('body').prepend('<div id=\"messageBox\" class=\"div-tips tips-'+type+'\"><div class=\"toClose\">&#10006</div><dl><dt id=\"messageTips\">'+tips+'</dt><dd id=\"messageContent\">'+content+'</dd></dl></div>');
		}else{
			$('#messageBox').remove();
			$('body').prepend('<div id=\"messageBox\" class=\"div-tips tips-'+type+'\"><div class=\"toClose\">&#10006</div><dl><dt id=\"messageTips\">'+tips+'</dt><dd id=\"messageContent\">'+content+'</dd></dl></div>');
		}
		$(".div-tips").click(function(){
			$(this).toggle(500);
		});
		console.info($('#messageBox').html());
		console.info(top.$.jBox.tip.mess);
		$("#messageBox").show();
	}catch(e){}
}

/**
 * 重写日期格式化方法 对Date的扩展，将 Date 转化为指定格式的String *
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符 * 年(y)可以用 1-4
 * 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new Date()).pattern("yyyy-MM-dd
 * hh:mm:ss.S")==> 2006-07-02 08:09:04.423 (new Date()).pattern("yyyy-MM-dd E
 * HH:mm:ss") ==> 2009-03-10 二 20:09:04 (new Date()).pattern("yyyy-MM-dd EE
 * hh:mm:ss") ==> 2009-03-10 周二 08:09:04 (new Date()).pattern("yyyy-MM-dd EEE
 * hh:mm:ss") ==> 2009-03-10 星期二 08:09:04 (new Date()).pattern("yyyy-M-d
 * h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.pattern=function(fmt) {
	var o = {
		"M+" : this.getMonth()+1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth()+3)/3), // 季度
		"S" : this.getMilliseconds() // 毫秒
	};
	var week = {
		"0" : "/u65e5",
		"1" : "/u4e00",
		"2" : "/u4e8c",
		"3" : "/u4e09",
		"4" : "/u56db",
		"5" : "/u4e94",
		"6" : "/u516d"
	};
	if(/(y+)/.test(fmt)){
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}
	if(/(E+)/.test(fmt)){
		fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
	}
	for(var k in o){
		if(new RegExp("("+ k +")").test(fmt)){
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
		}
	}
	return fmt;
}

/* 获取日期控件的值 */
function getDateValue(dateId){
	console.log(dateId+"<<----");
	// 20190110 add by qiurx for判断某div内部取值
	var obj;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		obj = $('#'+dateId);
	}else{
		obj = $("#"+SmartWeb.FORM_GET_DIV).find('#' + dateId);
	}
	var val = obj.val();
	var datefmt = obj.attr('datetime-date-fmt');
	var valuefmt = obj.attr('datetime-value-fmt');

	var dateobj=StringToDate(val,datefmt);
	if(valuefmt==undefined){
		valuefmt== datefmt;
	}

	if(typeof dateobj  === 'undefined'){
		return dateobj;
	}
	// alert(valuefmt);

	return dateobj.format(valuefmt);
}

/* 设置My97DatetimePicker的日期(参数：日期控件id、日期真实值) */
function setDateValue(dateId,dateVal){
	console.log(dateId+"---->>"+dateVal);
	// 20190110 add by qiurx for 需要对某div内部元素设置值
	var obj;
	var objHid;
	if(null==SmartWeb.FORM_SET_DIV || undefined==SmartWeb.FORM_SET_DIV ||""==SmartWeb.FORM_SET_DIV){
		obj = $('#'+dateId);
		objHid = $('#val_'+dateId);
	}else{
		obj =$('#' + SmartWeb.FORM_SET_DIV).find("#"+dateId);
		objHid = $('#' + SmartWeb.FORM_SET_DIV).find('#val_'+dateId);
	}
	if(obj==undefined || objHid==undefined){
		return;
	}
	var val = dateVal;
	var datefmt = obj.attr('datetime-date-fmt');
	var valuefmt = obj.attr('datetime-value-fmt');
	if(valuefmt==undefined){
		valuefmt== datefmt;
	}
	var dateobj=StringToDate(val,valuefmt);

	if(typeof dateobj  === 'undefined'){
		obj.val("");
		objHid.val("");
		return;
	}
	// alert(valuefmt);

	obj.val(dateobj.format(datefmt));
	objHid.val(dateVal);
}


function StringToDate(dateString, formatString){
	var reg = validateDate(dateString, formatString);
	if(reg!=undefined) {
		var now = new Date();
		var vals = reg.exec(dateString);
		var index = validateIndex(formatString);
		var year = index[0]>=0?vals[index[0] + 1]:now.getFullYear();
		var month = index[1]>=0?(vals[index[1] + 1]-1):now.getMonth();
		var day = index[2]>=0?vals[index[2] + 1]:now.getDate();
		var hour = index[3]>=0?vals[index[3] + 1]:"";
		var minute = index[4]>=0?vals[index[4] + 1]:"";
		var second = index[5]>=0?vals[index[5] + 1]:"";

		var validate;
		console.info('yyyy:'+year);
		if (hour == "")
			validate = new Date(year, month, day);
		else
			validate = new Date(year, month, day, hour, minute, second);

		if(validate.getDate()==day) return validate;

	}
	// alert("wrong date");
}

function validateDate(dateString, formatString){
	var dateString = trim(dateString);
	if(dateString==""||dateString===undefined) return;
	var reg = formatString;
	reg = reg.replace(/yyyy/, "([0-9]{4})");
	reg = reg.replace(/yy/, "([0-9]{2})");
	reg = reg.replace(/MM/, "(0[1-9]|1[0-2])");
	reg = reg.replace(/M/, "([1-9]|1[0-2])");
	reg = reg.replace(/dd/, "(0[1-9]|[1-2][0-9]|30|31)");
	reg = reg.replace(/d/,  "([1-9]|[1-2][0-9]|30|31)");
	reg = reg.replace(/HH/,  "([0-1][0-9]|20|21|22|23)");
	reg = reg.replace(/H/, "([0-9]|1[0-9]|20|21|22|23)");
	reg = reg.replace(/mm/, "([0-5][0-9])");
	reg = reg.replace(/m/, "([0-9]|[1-5][0-9])");
	reg = reg.replace(/ss/, "([0-5][0-9])");
	reg = reg.replace(/s/, "([0-9]|[1-5][0-9])");
	reg = new RegExp("^"+reg+"$");
	// regexp = reg;
	if(reg.test(dateString))
		return reg;
	return undefined;
}

function validateIndex(formatString){
	var ia = new Array();
	var i = 0;
	var yi=-1,Mi=-1,di=-1,mi=-1,Hi=-1,si=-1;
	yi = formatString.search(/yyyy/);
	if ( yi < 0 ) yi = formatString.search(/yy/);
	if (yi >= 0) {
		ia[i] = yi;
		i++;
	}
	Mi = formatString.search(/MM/);
	if ( Mi < 0 ) Mi = formatString.search(/M/);
	if (Mi >= 0) {
		ia[i] = Mi;
		i++;
	}
	di = formatString.search(/dd/);
	if ( di < 0 ) di = formatString.search(/d/);
	if (di >= 0) {
		ia[i] = di;
		i++;
	}
	Hi = formatString.search(/HH/);
	if ( Hi < 0 ) Hi = formatString.search(/H/);
	if (Hi >= 0) {
		ia[i] = Hi;
		i++;
	}
	mi = formatString.search(/mm/);
	if ( mi < 0 ) mi = formatString.search(/m/);
	if (mi >= 0) {
		ia[i] = mi;
		i++;
	}
	si = formatString.search(/ss/);
	if ( si < 0 ) si = formatString.search(/s/);
	if (si >= 0) {
		ia[i] = si;
		i++;
	}
	var ia2 = new Array(yi, Mi, di, Hi, mi, si);
	for(i=0; i<ia.length-1; i++)
		for(j=0;j<ia.length-1-i;j++)
			if(ia[j]>ia[j+1]) {
				temp=ia[j];
				ia[j]=ia[j+1];
				ia[j+1]=temp;
			}
	for (i=0; i<ia.length ; i++)
		for (j=0; j<ia2.length; j++)
			if(ia[i]==ia2[j]) {
				ia2[j] = i;
			}
	return ia2;
}

function trim(str){
	if(str == undefined){
		return ;
	}else{
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
}

function DateToString(date,formatString){
	var dateString = date.format(formatString);
	var parsedDate = Date.parseString(todayString);
}

Date.prototype.format = function(formatString)
{
	if(!formatString || formatString == "")
	{
		formatString = "yyyy-MM-dd";
	}
	var year = this.getFullYear().toString();
	var month = (this.getMonth() + 1).toString();
	var day = this.getDate().toString();
	var hour = this.getHours().toString();
	var minute = this.getMinutes().toString();
	var sec = this.getSeconds().toString();
	var yearMarker = formatString.replace(/[^y|Y]/g,'');
	if(yearMarker.length == 2){
		year = year.substring(2,4);
	}else if(yearMarker.length< 1){
		year = "";
	}
	console.info(yearMarker+':'+year);
	var monthMarker = formatString.replace(/[^M]/g,'');
	if(monthMarker.length > 1&&month.length == 1)
		month = "0" + month;
	else
	if(monthMarker.length<1)
		month ="";
	var dayMarker = formatString.replace(/[^d]/g,'');
	if(dayMarker.length > 1&&day.length == 1)
		day = "0" + day;
	else
	if(dayMarker.length<1)
		day ="";
	var hourMarker = formatString.replace(/[^h|H]/g,'');
	if(hourMarker.length>1&&hour.length == 1)
		hour = "0" + hour;
	else
	if(hourMarker.length<1)
		hour = "";
	var minuteMarker = formatString.replace(/[^m]/g,'');
	if(minuteMarker.length>1&&minute.length==1)
		minute = "0"+minute;
	else
	if(minuteMarker.length<1)
		minute = "";
	var secMarker = formatString.replace(/[^s|S]/g,'');

	if(secMarker.length>1&&sec.length==1)
		sec = "0"+sec;
	else
	if(secMarker.length<1)
		sec = "";
	return formatString.replace(yearMarker,year).replace(monthMarker,month).replace(dayMarker,day).replace(hourMarker,hour).replace(minuteMarker,minute).replace(secMarker,sec);

}

Date.parseString = function(dateString,formatString)
{
	var today = new Date();
	if(!dateString || dateString == "")
		return today;
	if(!formatString || formatString == "")
		formatString = "yyyy-MM-dd HH:mm:ss";
	var yearMarker = formatString.replace(/[^y|Y]/g,'');
	var monthMarker = formatString.replace(/[^M]/g,'');
	var dayMarker = formatString.replace(/[^d]/g,'');
	var hourMarker = formatString.replace();
	var yearPosition = formatString.indexOf(yearMarker);
	var yearLength = yearMarker.length;
	var year = dateString.substring(yearPosition ,yearPosition + yearLength) * 1;
	if( yearLength == 2)
		if(year < 50 )
			year += 2000;
		else
			year += 1900;
	var monthPosition = formatString.indexOf(monthMarker);
	var month = dateString.substring(monthPosition,monthPosition + monthMarker.length) * 1 - 1;
	var dayPosition = formatString.indexOf(dayMarker);
	var day = dateString.substring( dayPosition,dayPosition + dayMarker.length )* 1;
	return new Date(year,month,day);
}

/**
 * 日期时间控件回填值方法 sId:源控件id
 */
function writeDateValue(sId){
	var value = getDateValue(sId);
	$('#val_'+sId).val(value); // 设置隐藏域的值
	// 由于input为hidden，验证会出现一些bug，此处手动验证隐藏的input组件
	var $form = $('#val_'+sId).closest('form[ravo="rainbow_fx_layout_bd"]');
	var $field = $('#val_'+sId).attr('name');
	if($form.data('bootstrapValidator')){
		try{
			$form.data('bootstrapValidator').updateStatus($field,  "NOT_VALIDATED",  null );
			$form.data('bootstrapValidator').validateField($field);
		}catch(e){}
	}

	console.info('dateVal='+$('#val_'+sId).val());
}

/* 根据值设置单选按钮选中状态 */
function setRadioVal(name, val){
	$("input[type='radio'][name='"+name+"'][value!='"+val+"']").removeAttr("checked");
	$("input[type='radio'][name='"+name+"'][value='"+val+"']").prop("checked", true);
}

/* 根据值设置复选框选中状态,默认分隔符为"," */
function setCheckboxVal(name, val, splitor){
	if(undefined==val){
		return;
	}
	if(undefined==splitor){
		splitor = ',';
	}
	$("input[type='checkbox'][name='"+name+"']").prop("checked", false);
	var v = val.split(splitor);
	for(var i=0;i<v.length;i++){
		$("input[type='checkbox'][name='"+name+"'][value='"+v[i]+"']").prop("checked", true);
		$("input[type='checkbox'][name='"+name+"'][value='"+v[i]+"']").trigger('change');
	}
}

/* 根据值设置多选框选项选中状态,默认分隔符为"," add by weizhijie 20170828 */
function setMultipleSelectVal(id, val, splitor){
	if(undefined==val){
		return;
	}
	if(undefined==splitor){
		splitor = ',';
	}
	var v = val.split(splitor);
	var valArr = new Array();
	for(var i=0;i<v.length;i++){
		valArr.push(v[i]);
	}
	$('#' + id).multiselect("select", valArr).multiselect('rebuild');
}

/* 根据值设置单选框中单选选项的选中状态 add by weizhijie 20170828 */
function setRadioSelectVal(id, val){
	$('#' + id).find("input[type='radio'][value='"+val+"']").prop("checked", true);
	$('#' + id).find("input[type='radio'][value='"+val+"']").trigger('change');
}

/* 获取单选按钮选中的值 */
function getRadioVal(name){
	var $id = 'input[name="'+name+'"]:checked';
	return $($id).val();
}

/* 获取单选按钮选中的标签值 */
function getRadioLabel(name){
	var $id = 'input[name="'+name+'"]:checked';
	return $($id).closest('label').text();
}

/* 获取复选框选中的值 */
function getCheckboxVal(name){
	var chk_value =[];
	var $id = 'input[name="'+name+'"]:checked';
	$($id).each(function(){
		chk_value.push($(this).val());
	});
	if(chk_value.length===0){
		return '';
	}else{
		return chk_value.join(',');
	}
}

/* 获取复选框选中的标签 */
function getCheckboxLabel(name){
	var chk_value =[];
	var $id = 'input[name="'+name+'"]:checked';
	$($id).each(function(){
		chk_value.push($(this).closest('label').text());
	});
	if(chk_value.length===0){
		return '';
	}else{
		return chk_value.join(';');
	}
}

function getCheckboxLabel2(name){
	var chk_value =[];
	var $id = 'input[name="'+name+'"]:checked';
	$($id).each(function(){
		chk_value.push($(this).next().text());
	});
	if(chk_value.length===0){
		return '';
	}else{
		return chk_value.join(',');
	}
}

/* 获取下拉框选中的标签 */
function getSelectLabel(id){
	var chk_value =[];
	var $id = $("#"+id).find("option:selected");
	$($id).each(function(){
		chk_value.push($(this).text());
	});
	if(chk_value.length===0){
		return '';
	}else{
		return chk_value.join(';');
	}
}

function getSL(id,separator){
	var chk_value =[];
	var $id = $("#"+id).find("option:selected");
	$($id).each(function(){
		chk_value.push($(this).text());
	});
	if(chk_value.length===0){
		return '';
	}else{
		if(undefined == separator){
			return chk_value.join(',');
		}else{
			return chk_value.join(separator);
		}
	}
}

/* 获取下拉框选中的值 默认逗号分隔 */
function getSelectDataVal(id,separator) {
	var chk_val = [];
	var $id = $("#" + id).find('option:selected');
	$id.each(function() {
		chk_val.push($(this).val());
	});
	if (chk_val.length === 0) {
		return '';
	} else {
		if(undefined == separator){
			return chk_val.join(';');
		}else{
			return chk_val.join(separator);
		}
	}
}

/* 获取下拉框所有的标签 */
function getAllLabel(id,separator){
	var chk_value =[];
	var $id = $("#"+id).find("option");
	$($id).each(function(){
		chk_value.push($(this).text());
	});
	if(chk_value.length===0){
		return '';
	}else{
		if(undefined == separator){
			return chk_value.join(';');
		}else{
			return chk_value.join(separator);
		}
	}
}

/* 获取下拉框所有的值 默认逗号分隔 */
function getAllValue(id,separator) {
	var chk_val = [];
	var $id = $("#" + id).find('option');
	$id.each(function() {
		chk_val.push($(this).val());
	});
	if (chk_val.length === 0) {
		return '';
	} else {
		if(undefined == separator){
			return chk_val.join(';');
		}else{
			return chk_val.join(separator);
		}
	}
}

/* 清除form表单控件的值 */
function clearForm(id){
	$('#'+id)[0].reset();
	// 下拉框特殊处理
	$('#'+id).find('select[data-role="multiselect"]').each(function(i){
		var $this = $(this);
		$this.multiselect('rebuild');
	});
	// 日期组件的特殊处理
	$('#'+id).find('input.Wdate').each(function(i){
		var $this = $(this);
		if($this.attr('data-link-field')){
			$('#'+$this.attr('data-link-field')).val('');
		}
	});
	// 单选特殊处理
	$('#' + id).find('input[type="radio"]').each(function(){
		var $this = $(this);
		if($this.attr('checked')){
			$this.removeAttr("checked");
		}
	});
}

/**
 * 设置组件的回车事件
 *
 * @param id
 *            组件ID
 * @param callBack
 *            回调函数
 * @returns
 */
function setEnterKeyEvent(id,callBack){
	try{
		$('#'+id).keydown(function(event){
			var lKeyCode = (navigator.appname=="Netscape")?event.which:event.keyCode;
			if ( lKeyCode == 13 ){
				callBack
			}
		});
	}catch(e){
		console.info('设置组件的回车事件异常：' + e);
	}
}

// 打开指定菜单页
// menuPath格式如下 —— 系统管理->系统设置->角色管理
function openMenu(menuPath){
	console.info('click menuPath='+menuPath+' , theme='+ctxTheme);
	// if('tech'===ctxTheme || 'green'===ctxTheme){
		var $subMenu,$thirdMenu,$secondInterval,$thirdInterval;
		var maxLoop = 20,st1 = 0,st2 = 0;
		var $menuStr = menuPath.split('->');
		if($menuStr.length!=3){
			return;
		}
		// 获取第一层
		top.$(".firstMenu-link").each(function(i){
			var $this = $(this);
			if($.trim($this.text())!=undefined && $menuStr[0]==$.trim($this.text())){
				// $this.parent().parent().click();
				// 一级菜单焦点
				//top.$("#menu li.menu").removeClass("active");
				//$this.parent().parent().addClass("active");
				// $subMenu = '#menu-'+$this.parent().attr('data-id');
				// 显示左侧菜单
				var menuId = "#menu-" + $this.attr("data-id");
				var len = top.$(menuId).length;
				console.log("menuId:" + menuId + ",len:" + len);
				if (len > 0){
					top.$("#left-index .menuHeader").hide();
					top.$(menuId).show();
					// 获取第二层
					top.$('div[data-parent="'+menuId+'"]').each(function(i){
						var text = $.trim($(this).children().eq(0).text());
						if(text!=undefined && $menuStr[1]==text){
							// 当改二级菜单没有展开则出发点击事件进行展开
							$thirdMenu = $(this).attr('data-href');
							if(!top.$(this).children().eq(1).hasClass('icon-change-down')){
								top.$(this).click();
							}

							// 获取第三层
							top.$($thirdMenu).find('a').each(function(i){
								if($.trim($(this).attr("title"))!=undefined && $menuStr[2]==$.trim($(this).attr("title"))){
									//modify dongbin 同级跳转问题解决
									//$(this).click();
									top.$(this).click();
									// 20170829 add by chenyl for
									// 根据session，$reload来判断是否重新装在页面
									var $tabName = $.trim(top.$(this).text());
									if($.session.get('$reload') && $.session.get('$reload')=='true'){
										console.info('$reload='+$.session.get('$reload'));
										var $tabId = undefined;
										top.$(".jericho_tab .tab_selected").each(function(i){
											var $this = $(this);
											// 20180419 mody by chenyl for
											// 防止因为标题过长显示省略号时匹配不上
											if(($this.text() && $tabName==$.trim($this.text())) || ($this.attr('name') && $tabName==$.trim($this.attr('name')))){
												$tabId = $this.attr('id');
												return false;
											}
										});
										if($tabId){
											top.$("#"+$tabId).loadData(true);
										}

										$.session.remove('$reload');
									}

									return false;
								}
							});
							return false;
						}
					});

				}else{
					// 获取二级菜单数据
					$.get($this.attr("data-href"), function(data){
						top.$("#left-index .menuHeader").hide();
						top.$("#left-index").append(data);
						// 链接去掉虚框
						top.$(menuId + " a").bind("focus",function() {
							if(this.blur) {this.blur()};
						});

						//一级菜单过长时自动省略后面一部分
						top.$(menuId).find(".c2-2").each(function(){
							var menuReg = new RegExp("[\u4E00-\u9FA5]|[\u3002|\uff1f|\uff01|\uff0c|\u3001|" +
								"\uff1b|\uff1a|\u201c|\u201d|\u2018|\u2019|\uff08|\uff09|\u300a|\u300b|" +
								"\u3008|\u3009|\u3010|\u3011|\u300e|\u300f|\u300c|\u300d|\ufe43|\ufe44|" +
								"\u3014|\u3015|\u2026|\u2014|\uff5e|\ufe4f|\uffe5]");
							var menuText = $(this).text().trim();
							var menuTextLength = menuText.length;
							var charLength = 0;
							for(var i=0;i<menuTextLength;i++){
								if(menuReg.test(menuText.substring(i,i+1))){
									charLength+=2;
								}else{
									charLength+=1;
								}
							}
							if(charLength>16){
								$(this).text(menuText.substring(0,7)+"...");
							}
						});

						top.$(menuId).find('a').each(function(){
							var thirdMenuText =  $.trim($(this).text());
							if(thirdMenuText.length>6){
								$(this).text(thirdMenuText.substring(0,6)+"...");
							}
						});

						//一级菜单点击事件
						top.$(menuId+" .menuTree").click(function(){
							if(top.$(".menuda").hasClass("show")){
								top.$(".firstIndex").toggle();
								top.$("#menuModel,.tab_pages,.tab_content").toggle();
							}else{
								top.$("#menuModel,.tab_pages,.tab_content").toggle();
								top.$(".firstIndex").toggle();
								if(top.$(".firstIndex").html().trim()==""){
									$.get(ctx+"/sys/menu/mainMenu",function(data){
										if (data.indexOf("id=\"loginForm\"") != -1){
											alert('未登录或登录超时。请重新登录，谢谢！');
											top.location = ctx;
											return false;
										}
										top.$(".firstIndex").append(data);
										top.$(".menuTarget").mouseup(function(){
											top.$(".firstIndex").hide();
											top.$("#menuModel,.tab_pages,.tab_content").show();
											var menuPath = $(this).attr("menuPath");
											openMenu(menuPath);
										})
									})
								}else{
									top.$(".firstIndex").show();
								}
							}
						});

						// 二级标题
						/*top.$(menuId + " .menu2").click(function(){
							// 关闭所有.accordion-body展开
							$(menuId + " .menu2 div").each(function(i){
								$(this).removeClass('in');
							});
							top.$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
							if(!$($(this).attr('data-href')).hasClass('in')){
								$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
							}
						});*/
						/*// 二级内容
						top.$(menuId + " .accordion-body a").click(function(){
							top.$(menuId + " li").removeClass("active");
							top.$(menuId + " li i").removeClass("icon-white");
							$(this).parent().addClass("active");
							$(this).children("i").addClass("icon-white");
						});*/

						//二级菜单点击事件
						top.$(menuId+" .menu2").click(function(){
							top.$(menuId+" .icon-change-down").removeClass("icon-change-down").addClass("icon-change-right");
							$(this).children().eq(1).removeClass("icon-change-right").addClass("icon-change-down");
							top.$(menuId+" .list-show").removeClass("list-show").addClass("list-hidden");
							$(this).next().removeClass("list-hidden").addClass("list-show");
						});

						// 展现三级
						/*top.$(menuId + " .accordion-inner a").click(function(){
							var href = $(this).attr("data-href");
							if($(href).length > 0){
								$(href).toggle().parent().toggle();
								return false;
							}
							return top.addTab($(this));
						});*/

						top.$(menuId+" a").click(function(){
							top.$(".firstIndex").hide();
							top.$("#menuModel,.tab_pages,.tab_content").show();
							top.$(".menu3-list-focus").removeClass("menu3-list-focus").addClass("menu3-list-blur");
							$(this).removeClass("menu3-list-blur").addClass("menu3-list-focus");
							top.$(".menu3-circle-focus").removeClass("menu3-circle-focus").addClass("menu3-circle-blur");
							$(this).parent().prev().removeClass("menu3-circle-blur").addClass("menu3-circle-focus");
							var href = $(this).attr("data-href");
							if($(href).length > 0){
								$(href).toggle().parent().toggle();
								return false;
							}
							// <c:if test="${tabmode eq '1'}"> 打开显示页签
							return top.addTab($(this),true); // </c:if>
						});

						// 获取第二层
						top.$('div[data-parent="'+menuId+'"]').each(function(i){
							var text = $.trim($(this).children().eq(0).text());
							if(text!=undefined && $menuStr[1]==text){
								// 当改二级菜单没有展开则出发点击事件进行展开
								$thirdMenu = $(this).attr('data-href');
								if(!$(this).children().eq(1).hasClass('icon-change-down')){
									$(this).click();
								}

								// 获取第三层
								top.$($thirdMenu).find('a').each(function(i){

									if($.trim($(this).attr("title"))!=undefined && $menuStr[2]==$.trim($(this).attr("title"))){
										top.$(this).click();
										// 20170829 add by chenyl for
										// 根据session，$reload来判断是否重新装在页面
										var $tabName = $.trim($(this).attr("title"));
										if($.session.get('$reload') && $.session.get('$reload')=='true'){
											console.info('$reload='+$.session.get('$reload'));
											var $tabId = undefined;
											top.$(".jericho_tab .tab_selected").each(function(i){
												var $this = $(this);
												// 20180419 mody by chenyl for
												// 防止因为标题过长显示省略号时匹配不上
												if(($this.text() && $tabName==$.trim($this.text())) || ($this.attr('name') && $tabName==$.trim($this.attr('name')))){
													$tabId = $this.attr('id');
													return false;
												}
											});
											if($tabId){
												top.$("#"+$tabId).loadData(true);
											}

											$.session.remove('$reload');
										}

										return false;
									}
								});
								return false;
							}
						});
					});
					return false;
				}
				return false;
			}
		});
	// }else{
	// 	var $subMenu,$thirdMenu,$secondInterval,$thirdInterval;
	// 	var maxLoop = 20,st1 = 0,st2 = 0;
	// 	var $menuStr = menuPath.split('->');
	// 	if($menuStr.length!=3){
	// 		return;
	// 	}
	// 	// 获取第一层
	// 	top.$("#menu a.menu span").each(function(i){
	// 		var $this = $(this);
	// 		if($.trim($this.text())!=undefined && $menuStr[0]==$.trim($this.text())){
	// 			// $this.parent().parent().click();
	// 			// 一级菜单焦点
	// 			top.$("#menu li.menu").removeClass("active");
	// 			$this.parent().parent().addClass("active");
	// 			// $subMenu = '#menu-'+$this.parent().attr('data-id');
	// 			// 显示二级菜单
	// 			var menuId = "#menu-" + $this.parent().attr("data-id");
	// 			if (top.$(menuId).length > 0){
	// 				top.$("#left .accordion").hide();
	// 				top.$(menuId).show();
	//
	// 				// 获取第二层
	// 				top.$('a[data-parent="'+menuId+'"]').each(function(i){
	// 					if($.trim($(this).text())!=undefined && $menuStr[1]==$.trim($(this).text())){
	// 						// 当改二级菜单没有展开则出发点击事件进行展开
	// 						$thirdMenu = $(this).attr('data-href');
	// 						if(!top.$($thirdMenu).hasClass('in')){
	// 							top.$(this).click();
	// 						}
	//
	// 						// 获取第三层
	// 						top.$($thirdMenu).find('a').each(function(i){
	// 							if($.trim($(this).text())!=undefined && $menuStr[2]==$.trim($(this).text())){
	// 								top.$(this).click();
	// 								// 20170829 add by chenyl for
	// 								// 根据session，$reload来判断是否重新装在页面
	// 								var $tabName = $.trim(top.$(this).text());
	// 								if($.session.get('$reload') && $.session.get('$reload')=='true'){
	// 									console.info('$reload='+$.session.get('$reload'));
	// 									var $tabId = undefined;
	// 									top.$(".jericho_tab .tab_selected").each(function(i){
	// 										var $this = $(this);
	// 										// 20180419 mody by chenyl for
	// 										// 防止因为标题过长显示省略号时匹配不上
	// 										if(($this.text() && $tabName==$.trim($this.text())) || ($this.attr('name') && $tabName==$.trim($this.attr('name')))){
	// 											$tabId = $this.attr('id');
	// 											return false;
	// 										}
	// 									});
	// 									if($tabId){
	// 										top.$("#"+$tabId).loadData(true);
	// 									}
	//
	// 									$.session.remove('$reload');
	// 								}
	//
	// 								return false;
	// 							}
	// 						});
	// 						return false;
	// 					}
	// 				});
	//
	// 			}else{
	// 				// 获取二级菜单数据
	// 				$.get($this.parent().attr("data-href"), function(data){
	// 					top.$("#left .accordion").hide();
	// 					top.$("#left").append(data);
	// 					// 链接去掉虚框
	// 					top.$(menuId + " a").bind("focus",function() {
	// 						if(this.blur) {this.blur()};
	// 					});
	// 					// 二级标题
	// 					top.$(menuId + " .accordion-heading a").click(function(){
	// 						// 关闭所有.accordion-body展开
	// 						$(menuId + " .accordion-body").each(function(i){
	// 							$(this).removeClass('in');
	// 						});
	// 						top.$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
	// 						if(!$($(this).attr('data-href')).hasClass('in')){
	// 							$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
	// 						}
	// 					});
	// 					// 二级内容
	// 					top.$(menuId + " .accordion-body a").click(function(){
	// 						top.$(menuId + " li").removeClass("active");
	// 						top.$(menuId + " li i").removeClass("icon-white");
	// 						$(this).parent().addClass("active");
	// 						$(this).children("i").addClass("icon-white");
	// 					});
	// 					// 展现三级
	// 					top.$(menuId + " .accordion-inner a").click(function(){
	// 						var href = $(this).attr("data-href");
	// 						if($(href).length > 0){
	// 							$(href).toggle().parent().toggle();
	// 							return false;
	// 						}
	// 						return top.addTab($(this));
	// 					});
	//
	// 					// 获取第二层
	// 					top.$('a[data-parent="'+menuId+'"]').each(function(i){
	// 						if($.trim($(this).text())!=undefined && $menuStr[1]==$.trim($(this).text())){
	// 							// $(this).click();
	// 							$thirdMenu = $(this).attr('data-href');
	// 							// 关闭所有.accordion-body展开
	// 							top.$(menuId + " .accordion-body").each(function(i){
	// 								$(this).removeClass('in');
	// 							});
	// 							// 展开当前的二级菜单
	// 							top.$($thirdMenu).addClass('in');
	// 							top.$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
	// 							if(!$($thirdMenu).hasClass('in')){
	// 								$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
	// 							}
	// 							// 获取第三层
	// 							top.$($thirdMenu).find('a').each(function(i){
	// 								if($.trim($(this).text())!=undefined && $menuStr[2]==$.trim($(this).text())){
	// 									top.$(menuId + " li").removeClass("active");
	// 									top.$(menuId + " li i").removeClass("icon-white");
	// 									$(this).parent().addClass("active");
	// 									$(this).children("i").addClass("icon-white");
	// 									var href = $(this).attr("data-href");
	// 									if($(href).length > 0){
	// 										$(href).toggle().parent().toggle();
	// 										return false;
	// 									}
	// 									top.addTab($(this));
	// 									// $(this).click();
	// 									return false;
	// 								}
	// 							});
	// 							return false;
	// 						}
	// 					});
	// 				});
	// 				return false;
	// 			}
	// 			return true;
	// 		}
	// 	});
	// }

}


/**
 * 异步装载页面到指定的div
 *
 * @param id
 *            DIV的id
 * @param url
 *            需要装载的jsp路径
 * @param param
 *            请求参数
 * @returns
 */
function loadJspByDiv(id, url, param){
	console.info("loadJspByDiv:[ "+id+" , "+url+" , "+param+" ]");
	if(param==undefined){
		param = {};
	}
	$.get(url, param, function(data){
		var start = data.indexOf('<!-- view start -->');
		var end = data.indexOf('<!-- view end -->') + '<!-- view end -->'.length;
		var newHtml = data.substring(start, end);
		// console.info("返回的数据："+newHtml);
		$('#'+id).html('');
		$('#'+id).html(newHtml);
		// 下拉框初始化
		initMultiSelect();
		// 对textarea初始化时全空格的trim掉
		$("textarea").each(function(){
			var $this = $(this);
			if(undefined!=$this.val() && $.trim($this.val()).length===0){
				$this.val("");
			}
		});
		console.info('装载新页面成功');
	});
}

/**
 * 在指定的div中生成checkbox复选框(生成后不可拖拉的)
 *
 * @param divId
 *            DIV的id
 * @param id
 *            生成checkbox的id前缀
 * @param name
 *            生成checkbox中的选项的name
 * @param url
 *            获取选项数据的远程路径，返回格式为：list:[{label:'标签1',value:'1'},{label:'标签2',value:'2'}]
 * @param required
 *            是否必输：true-必输、false-非必输
 * @param chkList
 *            默认选中列表值数组
 * @param params
 *            请求时需要传输的参数
 * @returns
 */
function genCheckboxByUrl(divId, id, name, url, required, chkList, params){
	if(undefined==divId || undefined==url || undefined==id || undefined==name){
		console.info('divId,id,name,url为必输项！');
		return;
	}
	if(undefined==params){
		params = {};
	}
	/*20220110 mod by chenyl for 调整为同步模式获取后台数据*/
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        data: params,
        async: false,
        success: function (data) {
    		if(data.retCode==="0000"){
    			var $div = $("#"+divId);
    			var clazz = '';
    			if(undefined!=required && required){
    				clazz = 'required';
    			}
    			$div.html('');
    			/*20220110 add by chenyl for 对返回结果进行非空判断*/
    			if(undefined!=data.list && null!=data.list){
    				$.each(data.list, function (index, option) {
    					var $tag,$checked=false;
    					if(undefined!=chkList){
    						for(var i=0;i<chkList.length;i++){
    							if(option.value==chkList[i]){
    								$checked = true;
    								break;
    							}
    						}
    					}
    					if(undefined!=option.checked && option.checked == "true"){
    						$checked = true;
    					}
    					$tag = $('<span>').append($('<input>',{
    						'id':id+index,
    						'name':name,
    						'class':clazz,
    						'type':'checkbox',
    						'value':option.value,
    						'label':option.label,
    						'checked':$checked
    					})).append($('<label>',{
    						'for':id+index
    					}).append(option.label));
    					$div.append($tag);
    				});
    				if(clazz=='required'){
    					$div.append($('<span>',{
    						"class":"help-inline"
    					}).append($('<font>',{
    						"color":"red"
    					}).append('*')));
    				}
    			}
    		}
        }
    });
}

/**
 * 文件下载，支持多文件下载
 *
 * @param url
 *            请求下载的url(必输)
 * @param fileIds
 *            要下载的文件id列表，多个文件以逗号分隔(必输)
 * @param downloadFileName
 *            下载后显示的文件名称，如果多文件下载的为.zip结尾(非必输)
 * @returns
 */
function download(url, fileIds, downloadFileName){
	var form=$("<form>");// 定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action",url);// 请求url
	var input1=$("<input>");
	input1.attr("type","hidden");
	input1.attr("name","__FILE_IDS");// 设置属性的名字(文件id列表)
	input1.attr("value",fileIds);// 设置属性的值
	var input2=$("<input>");
	input2.attr("type","hidden");
	input2.attr("name","__DOWNLOAD_FILENAME");// 设置属性的名字(文件id列表)
	input2.attr("value",downloadFileName);// 设置属性的值
	$("body").append(form);// 将表单放置在web中
	form.append(input1);
	form.append(input2);
	form.submit();// 表单提交
	showTip('正在导出，请稍等...', 3000);
}

/**
 * 数据导入
 *
 * @param importUrl
 *            导入数据请求路径
 * @param templateUrl
 *            导入模板下载路径
 * @param title
 *            导入弹出窗口标题，默认：导入数据
 * @param bottomText
 *            导入弹出窗口底部备注信息，默认：导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！
 * @param cb
 *            回调函数
 * @returns
 */
function importData(importUrl, templateUrl, title, bottomText, cb){
	if($('#importBox')!=undefined){
		$('#importBox').remove(); /* 首先移除已有的下载框 */
	}
	if(title==undefined || ''==title){
		title = '导入数据';
	}
	if(bottomText==undefined || ''==bottomText){
		bottomText = '导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！';
	}
	var $importBox = $("<div>");
	$importBox.attr("id", "importBox");
	$importBox.attr("class", "hide");
	var $form=$("<form>");// 定义一个form表单
	$form.attr("name","importDataForm");
	$form.attr("action",importUrl);
	$form.attr("method","post");
	$form.attr("enctype","multipart/form-data");
	$form.attr("class","form-import");
	$form.attr("style","padding-left:20px;text-align:center;");
	$form.attr("onsubmit","loading('正在导入，请稍等...');");
	var $br = $("<br>");
	$form.append($br);
	var $input1=$("<input>");
	$input1.attr("type","file");
	$input1.attr("id","__IMPORT_DATA_FILE");
	$input1.attr("name","file");/* 文件上传传送值的name */
	$input1.attr("style","width:330px");
	$form.append($input1);
	var $br1 = $("<br>");
	$form.append($br1);
	var $br2 = $("<br>");
	$form.append($br2);
	var $input2=$("<input>");
	$input2.attr("type","button");
	$input2.attr("class","btn btn-primary importBtn");/* 文件上传传送值的name */
	$input2.attr("value","   导    入   ");
	$input2.attr("onclick","_importDataSubmit('"+importUrl+"', "+cb+");");
	$form.append($input2);
	var $a=$("<a>");
	$a.attr("href",templateUrl);
	$a.append("下载模板");
	$form.append($a);
	$importBox.append($form); /* 将form添加到div中 */
	/* 弹出下载窗口 */
	$.jBox($importBox.html(), {title:title, buttons:{"关闭":true}, bottomText:bottomText});
}

/**
 * 数据导出
 *
 * @param exportUrl
 *            数据导出请求的url(必输)
 * @param title
 *            数据导出弹出窗口标题，默认：确认要导出数据吗？
 * @param alertMsg
 *            数据导出弹出窗提示信息，默认：系统提示
 * @returns
 */
function exportData(exportUrl, title, alertMsg){
	if(title==undefined || ''==title){
		title = '确认要导出数据吗？';
	}
	if(alertMsg==undefined || ''==alertMsg){
		alertMsg = '系统提示';
	}
	if($('#exportDataForm')!=undefined){
		$('#exportDataForm').remove();
	}
	top.$.jBox.confirm(title , alertMsg, function(v,h,f){
		if(v=="ok"){
			var form=$("<form>");// 定义一个form表单
			form.attr("id","exportDataForm");
			form.attr("style","display:none");
			form.attr("target","");
			form.attr("method","post");
			form.attr("action",exportUrl);// 请求url
			$("body").append(form);// 将表单放置在web中
			form.submit();// 表单提交
		}
	},{buttonsFocus:1});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}

/**
 * 数据导入方法的内部异步请求
 *
 * @param importUrl
 *            数据导入请求URL
 * @param callBack
 *            数据导入成功后回调方法
 * @returns
 */
function _importDataSubmit(importUrl, cb){
	if(''==$('#__IMPORT_DATA_FILE').val() || undefined==$('#__IMPORT_DATA_FILE').val()){
		top.showContent("导入文件不能空！","error");
		return '0';
	}
	var form = $("form[name=importDataForm]");
	var options  = {
		url:importUrl,
		type:'post',
		success:function(data)
		{
			try{
				data = eval('(' + data + ')');
				if(data.returnCode!==undefined && "0000"!=data.returnCode){
					var errMsg = "交易失败["+data.message+"]";
					top.showContent(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "交易成功["+data.message+"]";
					top.showContent(successMsg,"success");
					try{
						console.info("导入数据后执行回调...");
						$('a.jbox-close').click();
						if(typeof cb == 'function'){
							cb();
						}
					}catch(e){}
				}
			}catch(e){
				top.showContent("文件导入提交失败,请技术人员协查","error");
			}
		}
	};
	form.ajaxSubmit(options);
}

/**
 * 显示bootstrap-table
 *
 * @param tableId
 *            表格ID
 * @returns
 */
function showTable(tableId){
	$(document).find('div.bootstrap-table').each(function(){
		var $this = $(this);
		if($this.find('table[id="'+tableId+'"]').length>0){
			$this.show();
			return false;
		}
	});
}

/**
 * 隐藏bootstrap-table
 *
 * @param tableId
 *            表格ID
 * @returns
 */
function hideTable(tableId){
	$(document).find('div.bootstrap-table').each(function(){
		var $this = $(this);
		if($this.find('table[id="'+tableId+'"]').length>0){
			$this.hide();
			return false;
		}
	});
}

/**
 * 设置图标选择器不可编辑
 *
 * @param iconId
 * @returns
 */
function setIconSelectDisabled(iconId){
	var $id = iconId+'Button';
	$('#'+$id).hide();
}

/**
 * 设置文件选择器不可编辑
 *
 * @param fileId
 * @returns
 */
function setFileSelectDisabled(fileId){
	var $id = fileId+'Preview';
	$('#'+$id+' li').each(function(){
		$this = $(this).find("a:last-child");
		$this.hide();
	});
	$('#'+$id).nextAll('a.btn').each(function(){
		$this = $(this);
		$this.hide();
	});
}

/* 将下拉多选框变成只读 */
function multiselectReadOnly(id){
	var parent = $("#"+id).parent();
	parent.find('.btn-group ul li input').each(function(){
		var $this = $(this);
		$this.attr("disabled", "true");
	});
}


/**
 * 进行RSA算法加密
 *
 * @param plainTxt
 *            明文
 * @returns 密文
 */
function encryptRSA(plainTxt){
	if(undefined==plainTxt || ''==plainTxt){
		console.info("明文为空，不执行加密");
		return '';
	}
	var public_key = '';
	var url = ctx.substring(0,ctx.lastIndexOf("/"))+'/publicKey';
	console.info('url='+url)
	$.session.set('$HIDE_LOADING', 'true');
	/* 获取公钥 */
	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
		data:{},
		async:false,
		success:function(data){
			if(data.returnCode!==undefined && "0000"!=data.returnCode){
				var errMsg = "错误信息["+data.message+"]";
				showContent(errMsg,"error");
				return '0';
			}else{
				for(var i = 0 ; i < data.dataSetResult.length; i++){
					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
						var jsonObj = data.dataSetResult[i].data[j];
						console.info('公钥='+jsonObj.key);
						public_key = jsonObj.key;
					}
				}
			}
		}
	});
	$.session.set('$HIDE_LOADING', 'false');
	var encrypt = new JSEncrypt();
	encrypt.setPublicKey(public_key);
	return encrypt.encrypt(plainTxt);
}

/**
 * bootstrap table动态生成工具
 * @author lijunbin
 * 用法：
 * 1、在页面上定义一个 <table id="demo"></table>
 * 2、然后调用以下方法
 * var config = SmartWeb.bootstrapTable.constructor('#demo', columns, url, queryParams, renderSubtable, subtableColumns, subtableUrl, subTableQueryParams);
 * SmartWeb.bootstrapTable.init(config);
 */
SmartWeb.bootstrapTable = new function(){
	var self = this;
	self.tableSelect = '';
	self.columns = [];
	self.subtableColumns = [];
	self.url =  '';
	self.subtableUrl = '';
	self.queryParams = {};
	self.subTableQueryParams = {};

	self.config = {};


	/**
	 * 构造函数
	 * 普通表格只需传入tableSelect,columns,url,queryParams
	 * 带子表表格需传入tableSelect,columns,url,queryParams,subtableColumns,subtableUrl,subTableQueryParams
	 * 本工具自带了一个默认的自动调用的初始化子表的私有方法_initSubtable，若想覆盖可传入renderSubtable参数，传入该方法后，subtableColumns,subtableUrl,subTableQueryParams不需传入构造方法，而要在该方法中设置
	 * @param tableSelect 选择器 如'#table'
	 * @param columns 主表列参数
	 * @param url 主表请求url
	 * @param queryParams 主表请求参数
	 * @param renderSubtable 初始化副表的方法 覆盖默认的_initSubtable方法。若传入该方法，则subtableColumns,subtableUrl,subTableQueryParams不需传入构造方法，而要在该方法中设置
	 * @param subtableColumns 副表列参数
	 * @param subtableUrl 副表请求url
	 * @param subTableQueryParams 副表请求参数
	 * @param showPlus 是否显示行前面得+号
	 * @return config 配置
	 *
	 */
	self.constructor = function(tableSelect, columns, url, queryParams, renderSubtable, subtableColumns, subtableUrl, subTableQueryParams,showPlus) {
		self.tableSelect = tableSelect;
		self.columns = columns;
		self.url = url;
		self.subtableColumns = subtableColumns;
		self.subtableUrl = subtableUrl;
		self.queryParams = queryParams;
		self.subTableQueryParams = subTableQueryParams;
		self.config = _defaultConfig();
		if(undefined != renderSubtable && '' != renderSubtable) {
			var subtableConfig = _subtableConfig(renderSubtable, showPlus);
			self.config = $.extend(self.config, subtableConfig);
		} else if(undefined != subtableColumns && '' != subtableColumns) {
			var subtableConfig = _subtableConfig();
			self.config = $.extend(self.config, subtableConfig);
		}
		return self.config;
	};

	/**
	 * 初始化table, 调用前先调用构造方法constructor
	 * @param config bootstrap table 配置
	 * 用法：SmartWeb.bootstrapTable.init(config);
	 */
	self.init = function(config) {
		$(self.tableSelect).bootstrapTable('destroy').bootstrapTable(config);
	};

	/**
	 * 刷新表格
	 * 用法：SmartWeb.bootstrapTable.refresh('#table');
	 */
	self.refresh = function(tableSelect) {
		$(tableSelect).bootstrapTable('refresh');
	};

	/**
	 * 扩展/覆盖 config配置
	 * @param config 调用构造函数得到的config配置
	 * @param custom 自定义配置 形如 {url:"/xxx", method:"post"}
	 * 用法：
	 * var custom = {height:500, method:"post"}
	 * var config = SmartWeb.bootstrapTable.constructor(tableSelect, columns, url, queryParams, renderSubtable, subtableColumns, subtableUrl, subTableQueryParams);
	 * config = SmartWeb.bootstrapTable.extendConfig(config, custom);
	 */
	self.extendConfig = function(config, custom) {
		return $.extend(config, custom);
	};

	self.hidePlusSign = function() {
		$(".detail").hide();
		$(self.tableSelect + " tbody tr td:first-child").each(function(){
			$(this).hide();
		});
	};

	/**
	 * 展开子表按钮
	 * {field: 'action',title: '操作',formatter:function(value, row, index){
	 *		return SmartWeb.bootstrapTable.colspanBtn(value, row, index, '展开', '收起');
	 * } }
	 */
	self.colspanBtn = function(value, row, index, openText, closeText) {
		if (value == undefined) {
			value = '';
		}
		return value + ' <a href="#" class="subtable ' + row.id + '" onClick="SmartWeb.bootstrapTable.viewDetail(\'' + index + '\',\'' + openText + '\',\'' + closeText + '\')">' + openText + '</a>';
	};

	/**
	 * 展开子表，colspanBtn按钮中使用，无需手动调用
	 */
	self.viewDetail = function(index, openText, closeText){
		var text = $('.subtable ').eq(index).html();
		if(text == openText){
			$('.subtable ').eq(index).html(closeText);
			$(self.tableSelect).bootstrapTable('expandRow', index);
		} else {
			$('.subtable ').eq(index).html(openText);
			$(self.tableSelect).bootstrapTable('collapseRow', index);
		}
		event.stopPropagation();
	};

	/**
	 * 格式化列表按钮add by zhouxin 用法如下
	 * $("#tableId").bootstrapTable({
	 * 	  onLoadSuccess: function(data){
	 *		  if(ctxTheme=="tech"){
	 *			  SmartWeb.bootstrapTable.formatAction(tableId);
	 *		  }
	 * 	  }
	 * });
	 */
	self.formatAction = function(tableId){
		var FBchineseNum = 2;
		var chineseNum = 0;
	    $('#'+tableId).find("tbody").find("tr").each(function(){
	        var td = $(this).children("td:last-child");
	        var arr = formatterAction(td.html());
	        if($.isArray(arr)) {
				// 当为数组时调整按钮
				td.html(arr[0]);
				//20200227 add by chenyl for 处理IE浏览器时列表中第一个按钮的a标签的click无效问题
				if (window.ActiveXObject || "ActiveXObject" in window) {
					td.find('.lb1').each(function () {
						var $this = $(this);
						var alist = $this.children('a');
						if (null != alist) {
							var btnClick = null;
							for (var i = 0; i < alist.length; i++) {
								var $a = alist[i];
								if (undefined != $a.onclick) {
									btnClick = $a.onclick;
									$this.on('click', btnClick);
								}
							}
						}
					});
				}
				//绑定事件
				bindHover();
				//根据按钮的汉字数设置宽度
				var num = arr[1];
				if(chineseNum < num){
					chineseNum = num;
				}
				//默认的100px可以显示7个汉字，每多一个汉字+17px
				if(chineseNum > 7){
					var px = (chineseNum-7)*17;
					var width = px + 100;
					$(".lb4li").width(width+"px");
					$(".lb4").width(width+"px");
				}
				//根据第一个按钮的汉字数设置宽度
				var FBnum = arr[2];
				if (FBchineseNum < FBnum) {
					FBchineseNum = FBnum;
				}
				if (FBchineseNum > 2) {
					var px = (FBchineseNum - 2) * 12;
					var width = px + 36;
					$(".lb1").width(width + "px");
				}
			}
		})
	};


	/**
	 * bootstrap table默认配置
	 */
	function _defaultConfig(){
		return {
			url: self.url,						//请求后台的URL（*）
			method: 'post',						//请求方式（*）
			toolbar: "#SwToolbar",				//工具按钮DIV的id 形如'#toolbar'
			toolbarAlign: 'left',				//工具条位置
			striped: true,						//是否显示行间隔色
			cache: false,						//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,					//是否显示分页（*）
			sortable: true,						//是否启用排序
			sortOrder: "asc",					//排序方式
			queryParams: self.queryParams,		//传递参数（*），这里应该返回一个object，即形如{param1:val1,param2:val2}
			sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
			pageNumber:1,						//初始化加载第一页，默认第一页
			pageSize: 20,						//每页的记录行数（*）
			/*modify by dongbin 20220411 增加页数10*/
			pageList: [10, 20, 50, 100],			//可供选择的每页的行数（*）
			search: false,						//是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch: false,
			showColumns: false,					//是否显示所有的列
			showRefresh: false,					//是否显示刷新按钮
			minimumCountColumns: 2,				//最少允许的列数
			clickToSelect: false,				//是否启用点击选中行
			//height: 500,						//行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID",						//每一行的唯一标识，一般为主键列
			showToggle:false,					//是否显示详细视图和列表视图的切换按钮
			cardView: false,					//是否显示详细视图
			detailView: false,					//是否显示父子表
			columns: self.columns
		}
	}

	function _subtableConfig(renderSubtable,flg) {
		var onLoadSuccess = flg ? function() {

		} : function() {
			self.hidePlusSign();
		}

		if(undefined != renderSubtable && '' != renderSubtable) {
			return {detailView : true,onExpandRow:renderSubtable,onLoadSuccess: onLoadSuccess}
		}
		return {detailView : true,onExpandRow:_initSubtable,onLoadSuccess: onLoadSuccess}
	}

	function _initSubtable(index, row, $detail) {
		var parentid = row.MENU_ID;
		// 注意这个'table'不是一个id，他在任何情况下不需要改变
		var cur_table = $detail.html('<table class=\'subtable\'></table>').find('table');
		$(cur_table).bootstrapTable({
			url:self.subtableUrl,
			method:'post',
			queryParams:self.subTableQueryParams,
			//ajaxOptions:{strParentID:parentid},
			pagination: false,
			clickToSelect:true,
			detailView:false,
			uniqueId:"MENU_ID",
			striped: false,
			sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
			pageNumber:1,						//初始化加载第一页，默认第一页
			pageSize: 10,						//每页的记录行数（*）
			/*modify by dongbin 20220411 增加页数10*/
			pageList: [10, 20, 50, 100],			//可供选择的每页的行数（*）
			columns:self.subtableColumns
		});
	}

	function _assembleColumns(fields, titles, useCheckbox) {
		if(fields.length!=titles.length)
			return null;
		var arr = [];
		if(useCheckbox) {
			var objc = {};
			objc.checkbox = true;
			arr.push(objc);
		}
		for(var i = 0;i<fields.length;i++) {
			var obj = {};
			obj.field = fields[i];
			obj.title = titles[i];
			arr.push(obj);
		}
		return arr;
	}

	function formatterAction(value){
		// 20201230 mod by chenyl for 存在unformatter属性时不执行转换，默认转换
        if(undefined!=value && (!(value.indexOf('<a')>-1 || value.indexOf('<button')>-1) || value.trim().length==0 || value.indexOf('unformatter')>-1)){
            //没有找到匹配的记录
            return value;
        }
		var first="";
		var more="";
		//用"> <"进行分割，中间可能有多个空格
		value = SmartWeb.swJS.lrtrim(value);
		var arr = value.split(/>\s*</g);
		var len = arr.length;
		var FBnum = 0;
		var flag = true;
		var num = 0;
		var btnNum = 0;
		for(var i=0;i<len;i++){
			if(arr[i].indexOf('<shiro:haspermission')>-1){
				var contentLen = arr[i].length;
				if(btnNum>1){
					more += arr[i];
					if(arr[i].lastIndexOf('>')!=(contentLen-1)){
						// 判断最后一位是否有结束符，没有则补位
						more += '>';
					}
				}else{
					first += arr[i];
					if(arr[i].lastIndexOf('>')!=(contentLen-1)){
						// 判断最后一位是否有结束符，没有则补位
						first += '>';
					}
				}
			}else if(arr[i].indexOf('/shiro:haspermission')>-1){
				var contentLen = arr[i].length;
				if(btnNum>1){
					if(arr[i].indexOf('<')!=0){
						// 判断第一位是否有开始标签符，没有则补位
						more += '<';
					}
					more += arr[i];
					if(arr[i].lastIndexOf('>')!=(contentLen-1)){
						// 判断第一位是否有开始标签符，没有则补位
						more += '>';
					}
				}else{
					if(arr[i].indexOf('<')!=0){
						// 判断第一位是否有开始标签符，没有则补位
						first += '<';
					}
					first += +arr[i];
					if(arr[i].lastIndexOf('>')!=(contentLen-1)){
						// 判断第一位是否有开始标签符，没有则补位
						first += '>';
					}
				}
			}else if(arr[i].indexOf('<input')>-1){
				//某些页面在拼按钮的时候拼了一些input的隐藏域，这种不做处理
			}else{
				//20191010 mod by chenyl 只处理指定的标签开头的数据
				var chineseNum = calculateChinese(arr[i]);
				//alert(chineseNum+"="+arr[i]);
				if(chineseNum > 0 && flag){
					//只获取第一个按钮的汉字数目
					FBnum = chineseNum;
					flag = false;
				}
				if(num < chineseNum){
					num = chineseNum;
				}
				if(arr[i].indexOf('a')==0 || arr[i].indexOf('<a')==0 || arr[i].indexOf('button')==0 || arr[i].indexOf('<button')==0){
					var contentLen = arr[i].length;
					if(0==btnNum){
						// 拼接首个按钮
						first += "<button class=\"lb1\">";
						if(arr[i].indexOf('<')!=0){
							// 判断第一位是否有开始标签符，没有则补位
							first += '<';
						}
						first += arr[i];
						if(arr[i].lastIndexOf('>')!=(contentLen-1)){
							// 判断最后一位是否有结束符，没有则补位
							first += '>';
						}
						first += "</button>";
					}else{
						// 拼接更多按钮
						more += "<li class=\"lb4li\">";
						if (arr[i].indexOf('<') != 0) {
							// 判断第一位是否有开始标签符，没有则补位
							more += '<';
						}
						more += arr[i];
						if (arr[i].lastIndexOf('>') != (contentLen - 1)) {
							// 判断最后一位是否有结束符，没有则补位
							more += '>';
						}
						more += "</li>";
					}
					btnNum++;
				}
			}
		}
		//有多个按钮时才拼更多按钮
		if (more.length > 0) {
			more = "<div class=\"lb2\">更多<button class=\"lb3\"><img src=\"" + ctxStatic + "/mainframe/img/" + ctxTheme + "/lb4.png\"></button><div class=\"lb4\" style=\"display:none;position:absolute;z-index:999;\"><ul>" + more + "</ul></div></div>";
		}
		var array = new Array()
		array[0] = first + more;
		array[1] = num;
		array[2] = FBnum;
		return array;
	}

	function calculateChinese(obj){
		var num = 0;
		if (obj.indexOf(">") != -1) {
			obj = obj.substring(obj.indexOf(">"));
		}
		if(obj.indexOf("<") != -1){
			obj = obj.substring(0,obj.indexOf("<"));
		}
		// 只计算"><"中间包含的字符
		//var str = obj.match(/(?<=>).*?(?=<)/);
		//匹配中英文
		var reg = /[\u4e00-\u9fa5_a-zA-Z0-9_]/g;
		var notMatchStr = obj.replace(reg,"");
		num = obj.length-notMatchStr.length;
		//num = str[0].length;
		return num;
	}

	function bindHover() {
		$(".lb2").hover(function () {
			$(this).children("div").show();
		}, function () {
			$(this).children("div").hide();
		});
	}
};

/**
 * 模态框动态生成工具
 * @author lijunbin
 */
SmartWeb.modal = new function(){
	var self = this;

	/**
	 * 页面引入模态框
	 * 用法:SmartWeb.modal.createModal();
	 */
	self.createModal = function() {
		var modalDiv = "<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\"\n" +
			"\taria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
			"<div class=\"modal-dialog\">\n" +
			"<div class=\"modal-content\">\n" +
			"<div class=\"modal-header\">\n" +
			"<button type=\"button\" class=\"close\" data-dismiss=\"modal\"\n" +
			"taria-hidden=\"true\">&times;</button>\n" +
			"<h4 class=\"modal-title\" id=\"modalTitle\"></h4>\n" +
			"</div>\n" +
			"<div class=\"modal-body\" id=\"myModalBody\">\n" +
			"</div>\n" +
			"<div class=\"modal-footer\">\n" +
			"<button type=\"button\" class=\"btn btn-primary btnSubmit\">提交</button>\n" +
			"<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">取消</button>\n" +
			"</div>\n" +
			"</div>\n" +
			"</div>\n" +
			"</div>";
		$('body').append(modalDiv);
		return $('#myModal');
	};

	/**
	 * 模态框生成器，执行后需执行$('#myModal').modal('show')才能显示模态框
	 * @param modalTitle 标题
	 * @param modalSelect 模态框ID选择器 如'#myModal'
	 * @param modalData modalData模态框内容 js拼装的html代码
	 * @param width 模态框宽度 如 '500'或'50%'
	 * @param height 模态框高度 如 '500'或'50%'
	 * @param useSubmitBtn 是否显示提交按钮 提交按钮事件使用 $('.btnSubmit').click(function(){});
	 * 用法：SmartWeb.modal.moadlDataControl("标题", "#myModal", '<div>...</div>', "80%", "350", false);
	 */
	self.init = function(modalTitle, modalSelect, modalData, width, height, useSubmitBtn) {
		$("#modalTitle").text(modalTitle);
		if("" != width) {
			$(".modal-dialog").width(width);
		}
		if("" != height) {
			$(".modal-body").height(height);
		}
		/* 初始化模态框首页数据 */
		$(modalSelect + " " + ".modal-body").html(modalData);
		/* 是否显示提交按钮 */
		if(useSubmitBtn != undefined && !useSubmitBtn) {
			$(modalSelect + " " + ".btnSubmit").hide();
		} else {
			$(modalSelect + " " + ".btnSubmit").show();
		}
		/* 关闭模态框时,清空数据 */
		$(modalSelect).on('hidden.bs.modal', function () {
			$(modalSelect + " " + ".modal-body").empty();
			$(modalSelect).css("display","none");
		});
	};

	/**
	 * 显示模态框
	 * @param modalSelect 模态框ID选择器 如'#myModal'
	 */
	self.show = function (modalSelect) {
		$(modalSelect).modal('show');
	};

	/**
	 * 隐藏模态框 模态框ID选择器 如'#myModal'
	 * @param modalSelect
	 */
	self.hide = function (modalSelect) {
		$(modalSelect).modal('hide');
	}
};

/**
 * 获取某个cookie对应的cookie
 * @param name
 * @returns {string|null}
 * @author lijunbin
 */
function getCookie(name) {
	var pattren = "(^| )" + name + "=([^;]*)(;|$)"
	var arr = document.cookie.match(new RegExp(pattren));
	if(arr != null) {
		return unescape(arr[2]);
	}
	return null;
}

var stompClient = (function () {

})();

function moveData(tableId,type, event) {
	var $tr = $(event.target).parents("tr");
	var len = $('#'+tableId).bootstrapTable('getData').length;
	var idx = $tr.index();
	var next_idx = 0;
	var array = $('#'+tableId).bootstrapTable('getData');
	$tr.fadeOut().fadeIn();
	if (type == 'up') {
		if ($tr.index() == 0) {
			top.$.jBox.tip("首行数据不可上移!","error",{persistent:true,opacity:0});
			return;
		}
		next_idx = idx - 1;
		$tr.prev().before($tr);
		//交换元素
	    var temp = array[idx];
	    array[idx] = array[next_idx];
	    array[next_idx] = temp;
	} else if (type == 'down') {
		if ($tr.index() == len - 1) {
			top.$.jBox.tip("尾行数据不可下移!", "error", {persistent: true, opacity: 0});
			return;
		}
		next_idx = idx + 1;
		$tr.next().after($tr);
		//交换元素
		var temp = array[idx];
		array[idx] = array[next_idx];
		array[next_idx] = temp;
	} else if (type == 'top') {
		if ($tr.index() == 0) {
			top.$.jBox.tip("此行数据已经置顶!","error",{persistent:true,opacity:0});
			return;
		}
		$("#"+tableId).prepend($tr);
		//交换元素
	    var temp = array[idx];
	    array.unshift(temp);//添加到数组开头
		array.splice(idx+1,1);
	} else if (type == 'bottom') {
		if ($tr.index() == len - 1) {
			top.$.jBox.tip("此行数据已经置底!", "error", {persistent: true, opacity: 0});
			return;
		}
		$("#" + tableId).append($tr);
		//交换元素
		var temp = array[idx];
		array.push(temp);//添加到数组末尾
		array.splice(idx, 1);
	}
}

//格式化金额
function amtfill(obj) {
	var reg = /^.*$/;
	obj.value = obj.value.replace(reg, function ($0, $1) {
		if (/^\d.*\.$/.test($0)) {
			return $0 + "00";
		} else if (/^\d+$/.test($0)) {
			return $0 + ".00";
		} else if (/^\d+\.\d$/.test($0)) {
			return $0 + "0";
		} else {
			return $0;
		}
	});
}

function usePost(url, data) {
	// Default options are marked with *
	return fetch(url, {
		body: JSON.stringify(data), // must match 'Content-Type' header
		cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
		credentials: 'same-origin', // include, same-origin, *omit
		headers: {
			'user-agent': 'Mozilla/4.0 MDN Example',
			'content-type': 'application/json'
		},
		method: 'POST', // *GET, POST, PUT, DELETE, etc.
		mode: 'cors', // no-cors, cors, *same-origin
		redirect: 'follow', // manual, *follow, error
		referrer: 'no-referrer', // *client, no-referrer
	}).then(response => response.json()) // parses response to JSON
}

/**
 * 去掉html标签
 * */
function removeHtmlTab(tab) {
	 return tab.replace(/<[^<>]+?>/g,'');//删除所有HTML标签
}

/**
 * 普通字符转换成转意符
 * @param sHtml
 * @returns
 */
function html2Escape(sHtml) {
	 return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
}

/**
 * 转意符换成普通字符
 * @param str
 * @returns
 */
function escape2Html(str) {
	 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
	 return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
}

/**
 * &nbsp;转成空格
 * @param str
 * @returns
 */
function nbsp2Space(str) {
	 var arrEntities = {'nbsp' : ' '};
	 return str.replace(/&(nbsp);/ig, function(all, t){return arrEntities[t]})
}

/**
 * 回车转为br标签
 * @param str
 * @returns
 */
function return2Br(str) {
	 return str.replace(/\r?\n/g,"<br />");
}

function getDatesAfter(date,days) {
	var dd = new Date(date);
	dd.setDate(dd.getDate() + days);// 获取days天后的日期
	var y = dd.getFullYear();
	var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd
			.getMonth() + 1);// 获取当前月份的日期，不足10补0
	var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();// 获取当前几号，不足10补0
	return y + "-" + m + "-" + d;
}

function bindActionHover() {
	$(".lb2").hover(function () {
		$(this).children("div").show();
	}, function () {
		$(this).children("div").hide();
	});
}
