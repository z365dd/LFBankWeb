/**
 *
 * Version : 1.0
 *
 * Created by pangzhe 2016-2-4.
 */

+function ($) {
	  'use strict';

	  // TAB CLASS DEFINITION
	  // ====================
	  var Tabs = function (element,options) {
		    var that = this;
		  	this.obj = $(element);
		  	this.options = options;
		  	
		  	this.obj.on('click', '.close-tab', function () {
		        var id = $(this).prev("a").attr("aria-controls");
		        that.close(id);
		    });

		    this.obj.on('mouseover','li',function() {
		        $(this).find('.close-tab').show();
		    });

		    this.obj.on('mouseleave','li',function() {
		        $(this).find('.close-tab').hide();
		    });
		    
		    if(!this.options.showHeader){
		    	this.hideHeader();
		    }
	
		    $(window).resize(function () {
		        obj.find('iframe').attr('height', $(document).height()-this.options.fitMarginBottom);
		    	that.drop();
		    });
		    
		    
	  }
	  
	  //显示选项卡的标签头
	  Tabs.prototype.showHeader = function () {
		  this.obj.children('ul').show();
	  }
	  
	  //隐藏选项卡的标签头
	  Tabs.prototype.hideHeader = function(){
		  this.obj.children('ul').hide();
	  }
	  
	Tabs.prototype.add = function (opts) {
        var id = 'tab_' + opts.id;
        //obj.find('.active').removeClass('active');
       /* this.obj.children('')*/
        $('li[role = "presentation"].active').removeClass('active'); 
        $('div[role = "tabpanel"].active').removeClass('active');
        //如果TAB不存在，创建一个新的TAB
        if (!$("#" + id)[0]) {
            //创建新TAB的title

            var title = $('<li>', {
                'role': 'presentation',
                'id': 'tab_' + id
            }).append(
                $('<a>', {
                    'href': '#' + id,
                    'aria-controls': id,
                    'role': 'tab',
                    'data-toggle': 'tab'
                }).html(opts.title)
            );
            //是否允许关闭
            if (this.options.closeable) {
                title.append(
                    $('<i>',{'class':'close-tab glyphicon glyphicon-remove'})
                );
            }
            //创建新TAB的内容
            var content = $('<div>', {
                'class': 'tab-pane',
                'id': id,
                'role': 'tabpanel'
            });

            //是否指定TAB内容
            if (opts.content) {
                content.append(opts.content);
            } else if (!opts.ajax) {//没有内容，使用IFRAME打开链接
                content.append(
                    $('<iframe>', {
                        'class': 'iframeClass',
                        'frameborder': "no",
                        'height': $(document).height()-this.options.fitMarginBottom,
                        'border': "0",
                        'src': opts.url
                    })
                );
            } else {
                $.get(opts.url, function (data) {
                    content.append(data);
                });
            }
            //加入TABS
            this.obj.children('.nav-tabs').append(title);
            this.obj.children(".tab-content").append(content);
        }

        //激活TAB
        $("#tab_" + id).addClass('active');
        $("#" + id).addClass("active");
        this.drop();
	}
	
	Tabs.prototype.close= function(id) {
		if (this.obj.find("li.active").attr('id') == "tab_" + id) {
            $("#tab_" + id).prev().addClass('active');
            $("#" + id).prev().addClass('active');
        }
        //关闭TAB
        $("#tab_" + id).remove();
        $("#" + id).remove();
        this.drop();
        /*this.options.callback();*/
	}
	
	Tabs.prototype.drop = function(opts) {
        var element = this.obj.find('.nav-tabs');
        //创建下拉标签
        var dropdown = $('<li>', {
            'class': 'dropdown pull-right hide tabdrop'
        }).append(
            $('<a>', {
                'class': 'dropdown-toggle',
                'data-toggle': 'dropdown',
                'href': '#'
            }).append(
                $('<i>', {'class': "glyphicon glyphicon-align-justify"})
            ).append(
                $('<b>', {'class': 'caret'})
            )
        ).append(
            $('<ul>', {'class': "dropdown-menu"})
        )

        //检测是否已增加
        if (!$('.tabdrop').html()) {
            dropdown.prependTo(element);
        } else {
            dropdown = element.find('.tabdrop');
        }
        //检测是否有下拉样式
        if (element.parent().is('.tabs-below')) {
            dropdown.addClass('dropup');
        }
        var collection = 0;

        //检查超过一行的标签页
        element.append(dropdown.find('li'))
            .find('>li')
            .not('.tabdrop')
            .each(function () {
                if (this.offsetTop > 0 || element.width() - $(this).position().left - $(this).width() < 53) {
                    dropdown.find('ul').append($(this));
                    collection++;
                }
            });

        //如果有超出的，显示下拉标签
        if (collection > 0) {
            dropdown.removeClass('hide');
            if (dropdown.find('.active').length == 1) {
                dropdown.addClass('active');
            } else {
                dropdown.removeClass('active');
            }
        } else {
            dropdown.addClass('hide');
        }
    }
	  
	Tabs.DEFAULTS = {
		  content: '', //直接指定所有页面TABS内容
		  closeable: true, //是否可以关闭
		  fitMarginBottom:0,
		  showHeader:true,
		  tabPosition:'top'
	}
	  
	  Tabs.VERSION = '3.3.5';
	  
	  var logError = function (message) {
			if (window.console) {
				window.console.error(message);
			}
	  };

	  function Plugin(option,_relatedTarget) {
		var ret ;
		
	    this.each(function () {
		    var $this = $(this)//提示当前对象的dom节点名称,这里的this关键字都指向一个不同的DOM
		    var data  = $this.data('bs.tabs')//取缓存tabs对象
		    var options = $.extend({}, Tabs.DEFAULTS, $this.data(), typeof option == 'object' && option)//合并配置参数
		    if(!data){
	    		$this.data('bs.tabs', (data = new Tabs(this,options)));
	    	}
		    if (typeof option == 'string'){
		    	if(!$.isFunction(data[option]) || option.charAt(0) === '_'){
		    		logError('No such method : ' + options);
		    	}else{
		    		ret =  data[option](_relatedTarget);
		    	}
		    }
	    })
	    return ret;
	}

	var old = $.fn.tabs

	$.fn.tabs  = Plugin
	$.fn.tabs.Constructor = Tabs
	// TAB NO CONFLICT
	// ===============

	$.fn.tabs.noConflict = function () {
	  $.fn.tabs = old;
	  return this;
	}
	
	/*$(document).on('click.bs.modal.data-api', '[data-toggle="modal"]', function (e) {
	    var $this   = $(this)
	    var href    = $this.attr('href')
	    var $target = $($this.attr('data-target') || (href && href.replace(/.*(?=#[^\s]+$)/, ''))) // strip for ie7
	    var option  = $target.data('bs.modal') ? 'toggle' : $.extend({ remote: !/#/.test(href) && href }, $target.data(), $this.data())

	    if ($this.is('a')) e.preventDefault()

	    $target.one('show.bs.modal', function (showEvent) {
	      if (showEvent.isDefaultPrevented()) return // only register focus restorer if modal will actually get shown
	      $target.one('hidden.bs.modal', function () {
	        $this.is(':visible') && $this.trigger('focus')
	      })
	    })
	    Plugin.call($target, option, this)
	  })*/
	/*$(document)
	bootstrap-tabs*/

	}(jQuery);





