/* ========================================================================
 * Bootstrap: tabs.js v3.3.5 pangzhe
 * ======================================================================== */

+function ($) {
  'use strict';

  // TAB CLASS DEFINITION
  // ====================
  
  var WinReszier = (function(){
		var registered = [];
		var inited = false;
		var timer;
		var resize = function(ev) {
			clearTimeout(timer);
			timer = setTimeout(notify, 100);
		};
		var notify = function() {
			for(var i=0, cnt=registered.length; i<cnt; i++) {
				registered[i].apply();
			}
		};
		return {
			register: function(fn) {
				registered.push(fn);
				if (inited === false) {
					$(window).bind('resize', resize);
					inited = true;
				}
			},
			unregister: function(fn) {
				for(var i=0, cnt=registered.length; i<cnt; i++) {
					if (registered[i] == fn) {
						delete registered[i];
						break;
					}
				}
			}
		}
	}());
  
  
  
  var Tabs = function (element,options) {
	
	var that = this;  
	this.options = options;  
    this.$element = $(element);
    this.width = options.width;
    this.height = options.height;
    
    this.tab = undefined;
    this.pane = undefined;
    
    if(!this.options.showHeader){
    	this.hideHeader();
    }
    
    //是否添加删除按钮
    if(this.options.closeable){
    	this.$element.children('li:not(.dropdown)').each(function () {
    		if($(this).has('i').size()===0){
    			$(this).children('a').append('<i class="close-tab glyphicon glyphicon-remove"></i>');
    		}
    		$(this).on('click', '.close-tab', function (e) { 
    			 if(e) e.preventDefault();//先阻止冒泡行为
    			 _close(this,$(element),options);
    			 that.layout();
    		});
		});
    }
    
    if(typeof this.width ==='number' && this.width!==0){
    	this.$element.css({width:this.width+'px'});
    	this.$element.next().css({width:this.width+'px'});
    }else if(this.width !== 'auto' || this.width===0){
    	this.$element.css({width:''});
    	this.$element.next().css({width:''});
    }
    
    if(typeof this.height ==='number' && this.height!==0){
    	this.$element.next().css({height:this.height+'px',overflow:'auto'});
    }else if(this.height !=='auto'|| this.height!==0){
    	this.$element.next().css({height:''});
    }
    
    //add tabs drop
    /*dropdown pull-right tabdrop active*/
    //如果已经有就没必要加了
    if(!this.$element.children('li:first').is('.dropdown')){
    	this.dropdown = $('<li class="dropdown hide pull-right tabdrop"><a  padding="10px 10px 10px 10px" class="dropdown-toggle" data-toggle="dropdown" href="#">'+'<i class="glyphicon glyphicon-align-justify"></i>'+' <b class="caret"></b></a><ul class="dropdown-menu"></ul></li>')
    	.prependTo(this.$element);
    }else{
    	this.dropdown = this.$element.children('li:first');
    }
    /*WinReszier.register($.proxy(this.layout, this));
    this.layout();*/
    
    this.select(this.options.selected);
  }
  
  
  Tabs.prototype.removerClose= function () {
	  this.closeable= false;
	  this.$element.find('i.close-tab').remove();
  }
  
  Tabs.prototype.addClose = function () {
	  this.closeable = true;
	  this.$element.children('li:not(.dropdown)').each(function () {
		if($(this).has('i').size()===0){
			$(this).children('a').append('<i class="close-tab glyphicon glyphicon-remove"></i>');
		}
		$(this).on('click', '.close-tab', function (e) { 
			 if(e) e.preventDefault();//先阻止冒泡行为
			 _close(this,$(element),options);
			 that.layout();
		});
	  });
  }
  
  Tabs.prototype.show = function () {
    var $this    = this.$element
    var $ul      = $this.closest('ul:not(.dropdown-menu)')
    var selector = $this.data('target')

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    }

    if ($this.parent('li').hasClass('active')) return

    var $previous = $ul.find('.active:last a')
    var hideEvent = $.Event('hide.bs.tab', {
      relatedTarget: $this[0]
    })
    var showEvent = $.Event('show.bs.tab', {
      relatedTarget: $previous[0]
    })

    $previous.trigger(hideEvent)
    $this.trigger(showEvent)

    if (showEvent.isDefaultPrevented() || hideEvent.isDefaultPrevented()) return

    var $target = $(selector)

    this.activate($this.closest('li'), $ul)
    this.activate($target, $target.parent(), function () {
      $previous.trigger({
        type: 'hidden.bs.tab',
        relatedTarget: $this[0]
      })
      $this.trigger({
        type: 'shown.bs.tab',
        relatedTarget: $previous[0]
      })
    })
    $this.parent().parent().data('bs.tabs')['onSelect']($this);
  }
  
  Tabs.prototype.activate = function (element, container, callback) {
	    var $active    = container.find('> .active')
	    var transition = callback
	      && $.support.transition
	      && ($active.length && $active.hasClass('fade') || !!container.find('> .fade').length)

	    function next() {
	      $active
	        .removeClass('active')
	        .find('> .dropdown-menu > .active')
	          .removeClass('active')
	        .end()
	        .find('[data-toggle="tab"]')
	          .attr('aria-expanded', false)

	      element
	        .addClass('active')
	        .find('[data-toggle="tab"]')
	          .attr('aria-expanded', true)

	      if (transition) {
	        element[0].offsetWidth // reflow for transition
	        element.addClass('in')
	      } else {
	        element.removeClass('fade')
	      }

	      if (element.parent('.dropdown-menu').length) {
	        element
	          .closest('li.dropdown')
	            .addClass('active')
	          .end()
	          .find('[data-toggle="tab"]')
	            .attr('aria-expanded', true)
	      }

	      callback && callback()
	    }

	    $active.length && transition ?
	      $active
	        .one('bsTransitionEnd', next)
	        .emulateTransitionEnd(Tab.TRANSITION_DURATION) :
	      next()

	    $active.removeClass('in')
	  }
  
  Tabs.prototype.layout = function () {
	  	/*var collection = [];
		this.dropdown.removeClass('hide');
		this.$element
			.append(this.dropdown.find('li'))
			.find('>li')
			.not('.tabdrop')
			.each(function(){
				if(this.offsetTop > 0) {
					collection.push(this);
				}
			});
		if (collection.length > 0) {
			collection = $(collection);
			this.dropdown
				.find('ul')
				.empty()
				.append(collection);
			if (this.dropdown.find('.active').length == 1) {
				this.dropdown.addClass('active');
			} else {
				this.dropdown.removeClass('active');
			}
		} else {
			this.dropdown.addClass('hide');
		}*/
  }
  
  function _close(that,$element,options) {
  	/*$that = $(that);*/
  	  var $that = $(that);
  	  var $content = $element.siblings();
  	  var e  = $.Event('close.bs.tabs', { relatedTarget: options });
	  $element.trigger(e);// 触发add.bs.tabs事件
	  if (e.isDefaultPrevented()) return
	  
	  //1.0判断onBeforeClose是否存在 2.0 判断onBeforeClose是否是方法 3.0 判断 onBeforeClose 返回true 或者false 4.0传参数
	  if(options.onBeforeClose && typeof options.onadd ==='function') {
		  var ret = options.onBeforeClose($that.parent().text().trim(),$content.find($that.parent().attr('href')).prevAll(':not(.dropdown)').size());
		  if(ret===false) return;
	  }
	  
	  var activeTab = $element.find('li.active:not(.dropdown) a').attr('href');//获取当前激活的tabID
	  var b = $element.find('li.active:not(.dropdown)').parent().is('ul.dropdown-menu');
	  var id = $that.parent().attr('href');
	  
	  $that.parent().parent().prev().has('div').remove();
	 	
	  //如果关闭的是当前激活的TAB，激活他的前一个TAB
	  //获取当前激活tab的ID
	  if(activeTab === id){
		  var tabs_size = $that.parent().parent().nextAll('li:not(.dropdown) > a').size();
		  if(!b){
			  $that.parent().parent().prev().addClass('active');
		  }else{
			  if(tabs_size >0){
				  $that.parent().parent().next().addClass('active');
			  }else{
				  $element.children('li:not(.dropdown):last').addClass('active');
			  }
		  }
		  $content.find(id).prev().addClass('active');
	  }
	 	
	  //获取前一个tab
	  $that.parent().parent().remove();
	  $content.find(id).remove();
  }
  
  Tabs.DEFAULTS = {
		    width:'auto',
		    height:'auto',
		    showHeader:true,//设置为true时，显示标签页标题,默认设置true
		    selected:0,	//mody by chenyl 20170602 for 修改1->0，
		    closeable:false,
		    fit: false,// 默认配置,要背景div(单击触发hide方法)
		    fitMarginBottom:0,
		    tabPosition:'left'
		  }
  
//返回所有选项卡面板
  Tabs.prototype.tabs = function(){
	  return this.$element.find('li:not(.dropdown) > a');
  }
  
  
  Tabs.prototype.onSelect = function (_relatedTarget) {
	  console.info('onSelect...');
	  var that = this;
	  if(that.options.onSelect && typeof that.options.onSelect ==='function' && _relatedTarget.attr('href')!=undefined) {
		  var ret = that.options.onSelect(_relatedTarget.text().trim(),_relatedTarget.attr('name'),_relatedTarget.attr('url'),_relatedTarget.attr('href'));
		  if(ret===false) return;
	  }
  }
  
  //选择一个选项卡面板,"which"参数可以是选项卡面板标题或者索引
  Tabs.prototype.select = function(_relatedTarget){
	  //判断参数类型
	  var that = this;
	  if(that.exists(_relatedTarget)){
		  this.getTab(_relatedTarget);
		  
		  if(that.options.onSelect && typeof that.options.onSelect ==='function') {
			  var ret = that.options.onSelect(this.tab.text(),_relatedTarget);
			  if(ret===false) return;
		  }
		  
		  //获取已选择的tab
		  var selectedTab  = that.$element.children('.active');
		  //mody by chenyl 20170605 for 屏蔽以下两行代码防止初始化进入界面时选择tab与选择tabcontent不一致的问题
		  /*
		  var _len = selectedTab.prevAll('li:not(.dropdown)').has('a').size();
		  if(_len === _relatedTarget) return;
		  */
		  
		  //去掉当前的active
		  selectedTab.removeClass('active');
		  that.$element.next('.tab-content').children('.active').removeClass('active');
		  
		  that.tab.addClass('active');
		  that.pane.addClass('active');
		  that.pane.addClass('in');
		  
		  this.layout();
		  
		  return that.tab;
	  }
  }
  
  //取消选择一个选项卡面板 ,"which"参数可以是选项卡面板标题或者索引
  Tabs.prototype.unselect = function(_relatedTarget){
	  
  }  
  
  Tabs.prototype.exists = function (_relatedTarget) {
	  var ret = false;
	  
	  var that = this;
	  var lis = that.$element.children('li:not(.dropdown)').has('a');
	  
	  var _lis = that.$element.children('li:first').find('ul > li');
	  if(typeof _relatedTarget == 'number' && _relatedTarget<=lis.size()+_lis.size()-1 && _relatedTarget>=0){
		  ret= true;
	  }else if(typeof _relatedTarget === 'string'){
		  lis.each(function () {
			 if($(this).text().trim() === _relatedTarget ){
				 ret = true;
			 }
		  });
		  _lis.each(function () {
			 if($(this).text().trim() === _relatedTarget ){
				 ret = true;
			 }
		  });
		  
	  }
	  return ret;
  }
  
  //显示选项卡的标签头
  Tabs.prototype.showHeader = function () {
	  this.$element.show();
  }
  
  //隐藏选项卡的标签头
  Tabs.prototype.hideHeader = function(){
	  this.$element.hide();
  }
  
  Tabs.prototype.getTab = function(_relatedTarget){
	  var that = this;
	  var lis = that.$element.children('li:not(.dropdown)').has('a');
	  var _lis = that.$element.children('li:first').find('ul > li');
	  if(that.exists(_relatedTarget)){
		  if(typeof _relatedTarget === 'number'){
			  
			  var selectTab = undefined;
			  if(lis.size()-1>=_relatedTarget){
				  selectTab  =  lis.eq(_relatedTarget);
			  }else{
				  selectTab = _lis.eq(_lis.size()-(_relatedTarget-lis.size())-1);
			  }
			  var panId = selectTab.find('a:first').attr('href');
			  
			  this.tab = selectTab;
			  this.pane = that.$element.next('.tab-content').find(panId);
			  return selectTab;
		  }else if(typeof _relatedTarget === 'string'){
			  var b = false;
			  lis.each(function () {
				 if($(this).text().trim() === _relatedTarget ){
					 var _n = $(this).prevAll('li:not(.dropdown)').has('a').size();
					 that.getTab(_n);
					 b = true;
				 }
			  });
			  if(!b){
				  _lis.each(function () {
					  if($(this).text().trim() === _relatedTarget ){
						  var _n = $(this).nextAll('li:not(.dropdown)').has('a').size();
						  that.getTab(lis.size()+_n);
					  }
				  });
			  }
		  }
	  }
  }
  
  Tabs.prototype.setWidth = function(_relatedTarget){
	  if(typeof _relatedTarget ==='number' && _relatedTarget!==0){
		  this.width = _relatedTarget;
    	  this.$element.css({width:this.width+'px'});
	      this.$element.next().css({width:this.width+'px'});
	  }else if(typeof _relatedTarget ==='number' && _relatedTarget===0){
		  this.$element.css({width:''});
	      this.$element.next().css({width:''});
	  }
	  this.layout();
  }
  
  Tabs.prototype.setHeight = function(_relatedTarget){
	  if(typeof _relatedTarget ==='number' && _relatedTarget!==0){
		  this.height = _relatedTarget;
	      this.$element.next().css({height:this.height+'px',overflow:'auto'});
	  }else if(typeof _relatedTarget ==='number' && _relatedTarget===0){
		  this.$element.next().css({height:''});
	  }
  }
  
  //关闭选项卡
  Tabs.prototype.close = function(_relatedTarget){
	  var that = this;
	  if(that.exists(_relatedTarget)){
		  this.getTab(_relatedTarget);
		  if(that.options.onBeforeClose && typeof this.options.onBeforeClose ==='function') {
			  var ret = this.options.onBeforeClose(this.tab.text().trim(),_relatedTarget);
			  if(ret===false) return;
		  }
		  
		  var activeTab = this.$element.find('li.active:not(.dropdown) a').attr('href');//获取当前激活的tabID
		  var b = this.$element.find('li.active:not(.dropdown)').parent().is('ul.dropdown-menu');
		  var id = this.tab.children('a:first').attr('href');
		 	
		  //如果关闭的是当前激活的TAB，激活他的前一个TAB
		  //获取当前激活tab的ID
		  if(activeTab === id){
			  var tabs_size = this.tab.nextAll('li:not(.dropdown)').has('a').size();
			  if(!b){
				  this.tab.prev().addClass('active');
			  }else{
				  if(tabs_size >0){
					  this.tab.next().addClass('active');
				  }else{
					  this.$element.children('li:not(.dropdown):last').addClass('active');
				  }
			  }
			  this.$element.siblings().find(id).prev().addClass('active');
		  }
		  //删除split
		  this.tab.prev().has('div').remove();
		  
		  this.tab.remove();
		  this.pane.remove();
		  this.layout();
	  }
  }
  
  Tabs.prototype.closeAll = function(){
	  var $this = this.$element;
	  var $content = $this.next();
	  $this.children('li:not(.dropdown)').remove();
	  $content.children('div').remove();
  }
  
  Tabs.prototype.addContent = function (_relatedTarget) {
	  var href = _relatedTarget.href;
	  if(href==undefined){
		  return;
	  }
	  var $this = this.$element;
	  var $content = $this.next();
	  
	  //TODO
	  if (!href) {
	      href = href && href.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
	  }
	  if($content.children().is(_relatedTarget.href)==true){
		  return;
	  }
	  if(_relatedTarget.content!=undefined && _relatedTarget.content!=''){
		  var content = $('<div id="'+href+'">'+_relatedTarget.content+'</div>');
		  $content.append(content);
		  $content.children('.active').removeClass('active');
		  content.addClass('tab-pane active');
	  }
	  if(this.options.fit){
		  
		  $content.find('iframe').load(function () {
			  var iframe = this;
			  console.info(iframe.contentWindow.document.body);
			  iframe.height = 0;
			  var bHeight = iframe.contentWindow.document.body.scrollHeight;
			  console.info(bHeight);
			  var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			  var height = Math.max(bHeight, dHeight);
			  iframe.height =  height+20;
		  });
		  
	  }
  }
  
  //添加一个新选项卡面板
  Tabs.prototype.add = function(_relatedTarget){
	  var that = this;
	  var $this = this.$element;
	  //添加新增前事件
	  var e    = $.Event('add.bs.tabs', { relatedTarget: _relatedTarget });
	  this.$element.trigger(e);
	  if(this.options.onAdd && typeof this.options.onAdd ==='function') this.options.onAdd(_relatedTarget.title,_relatedTarget.index);//另外一种方式实现事件
	  
	  // 如果已经显示了, 或者上面事件回调中调用了e.preventDefault 就 直接不处理,直接返回
	  if (e.isDefaultPrevented()) return
	  
	  var $content = $this.siblings();
	  var options = this.options;
	  
	  var href = 'panel-'+ generateMixed(6);
	  var title = $('<li> <a href="#'+href+'" data-toggle="tab" url="'+_relatedTarget.url+'" name="'+_relatedTarget.name+'" contenteditable="true">'+_relatedTarget.title+'</a> </li>');
	  
	  if(this.options.closeable){//是否添加close
		  title.children('a').append('<i class="close-tab glyphicon glyphicon-remove"></i>');
	  }
	  
	  //添加分隔符
	  var split = '<li> <div> </div> </li>';
	  
	  $this.children('li:not(.dropdown):last').parent().append(split);
	  
	  $this.append(title);
	  if(_relatedTarget.content!=undefined && _relatedTarget.content!=''){
		  var content = $('<div id="'+href+'">'+_relatedTarget.content+'</div>');
		  $content.append(content);
		  content.addClass('tab-pane');
	  }
	  
	  title.on('click', '.close-tab', function (e) { //删除按钮事件
		  if(e) e.preventDefault();//先阻止冒泡行为
		  _close(this,$this,options);
		  that.layout();
	  });
	  
	  //fit
	  if(this.options.fit){
		  $content.find('iframe').css('height', ($(document).height()-this.options.fitMarginBottom)+'px');
	  }
	  
	  
	  //添加新增后事件
	  var e    = $.Event('addn.bs.tabs', { relatedTarget: _relatedTarget });
	  this.$element.trigger(e);
	  
	  //是否显示标签页标题
	  if(!this.options.showHeader){
		  title.children('a').hide();
	  }
	  
	  this.layout();
  };
  
  Tabs.VERSION = '3.3.5';

  Tabs.TRANSITION_DURATION = 150;
  
// TAB PLUGIN DEFINITION
// =====================
  
  var logError = function (message) {
		if (window.console) {
			window.console.error(message);
		}
	};
	
	/*function Plugin(option) {
	    return this.each(function () {
	      var $this = $(this)
	      var data  = $this.data('bs.tabs')
	      var options = $.extend({}, Tabs.DEFAULTS, $this.data(), typeof option == 'object' && option)//合并配置参数
	      if (!data) $this.data('bs.tabs', (data = new Tabs(this)))
	      if (typeof option == 'string') data[options]()
	    })
	 }*/

  function Plugin(option,_relatedTarget) {
	var ret ;
    this.each(function () {
	    var $this = $(this);//提示当前对象的dom节点名称,这里的this关键字都指向一个不同的DOM
	    var data  = $this.data('bs.tabs');//取缓存tabs对象
	    var options = $.extend({}, Tabs.DEFAULTS, $this.data(), typeof option == 'object' && option);//合并配置参数
	    //add by chenyl 20170602 for 新增判断是否tab组件才缓存
	    if($this.hasClass('nav-tabs')){
	    	if(!data){
	    		$this.data('bs.tabs', (data = new Tabs(this,options)));
	    	}else{
	    		//20170815 add by chenyl for 当再次初始化时，且option为object
	    		if(typeof option == 'object' && option){
	    			$this.data('bs.tabs', (data = new Tabs(this,options)));
	    		}
	    	}
		    if (typeof option == 'string'){
		    	if(!$.isFunction(data[option]) || option.charAt(0) === '_'){
		    		logError('No such method : ' + options);
		    	}else{
		    		ret =  data[option](_relatedTarget);
		    	}
		    }
	    }
    })
    return ret;
}

/*var old = $.fn.tabs;
$.fn.tabs.Constructor = Tabs;*/

var old = $.fn.tabs

$.fn.tabs  = Plugin
$.fn.tabs.Constructor = Tabs


// TAB NO CONFLICT
// ===============

$.fn.tabs.noConflict = function () {
  $.fn.tabs = old;
  return this;
}

var jschars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += jschars[id];
    }
    return res;
}

 var clickHandler = function (e) {
	    e.preventDefault()
	    Plugin.call($(this), 'show')
	  }

	  $(document)
	    .on('click.bs.tab.data-api', '[data-toggle="tab"]', clickHandler)
	    .on('click.bs.tab.data-api', '[data-toggle="pill"]', clickHandler)
	    .on('mousedown','[data-toggle="tab"]',clickHandler)
	    .on('mousedown','[data-toggle="pill"]',clickHandler);

}(jQuery);

/* ========================================================================
 * Bootstrap: linklist.js v3.3.5 pangzhe 链接列表
 * ======================================================================== */

!function ($) {
  'use strict';

  // TAB CLASS DEFINITION
  // ====================

var Alable = function (element,options) {
	this.options = options;  
    this.$element = $(element);
};
  
Alable.prototype.click = function (e) {
	var elementName = undefined;
	var ul = this.$element.parentsUntil('ul').last().parent();
	var ulParent = ul.parent();
	if(ul.hasClass('navbar-nav')){
		elementName = 'navbar';//导航栏
	}else if(ulParent.hasClass('whitebgmid')){
	    elementName='linkList';//链接列表
	}else if(ul.hasClass('nav-tabs')){
		elementName='tabs';
		//add by chenyl 20170605 for 设置选择的tabcontent为active
		ul.next('.tab-content').children('.active').removeClass('active');
		var _tabContent = ul.next('.tab-content').find(this.$element.attr('href'));
		_tabContent.addClass('active');
		_tabContent.addClass('in');
	}
	  
	if(this.options.onClick && typeof this.options.onClick ==='function') {
		var ret = this.options.onClick(this.$element.text(),elementName,window.location.href,this.$element.attr('href'));
		if(ret===false) return;
	}
};

Alable.VERSION = '3.3.5';

// TAB PLUGIN DEFINITION

function Plugin(options,_relatedTarget) {
    this.each(function () {
    	var $this = $(this);
        var data  = $this.data('bs.alable');

        if (!data) $this.data('bs.alable', (data = new Alable(this,options)));
        if (typeof options == 'string') data[options](_relatedTarget);
    });
}


$.fn.alable  = Plugin;
$.fn.alable.Constructor = Alable;


// TAB NO CONFLICT
// ===============


var clickHandler = function (e) {
    Plugin.call($(this), 'click');
};
 
$(document)
  .on('click','a',clickHandler);
}(jQuery);
