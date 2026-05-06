/*!
 * 
 * 
 * 主框架窗口大小调整
 * 
 * @version 2013-11-09
 */

$("#left-index").width(leftWidth);
$("#transverter").click(function(){
	if($(this).children().hasClass("fold")){
		$(this).children().removeClass("fold");
		$(this).children().addClass("unfold");
		$("#left-index").animate({width:0,opacity:"hide"});
		/*$("#right").css("left","8px");*/
		$("#transverter").animate({left:0});
		$("#right").animate({left:8,width:$("#content").width()-$("#transverter").width()},function(){
			if(typeof openCloseClickCallBack == 'function'){
				openCloseClickCallBack(true);
			}
		});
		/*$("#footer-div").animate({width:$("#right").width()+leftWidth});*/
	}else{
		$(this).children().addClass("fold");
		$(this).children().removeClass("unfold");
		/*$("#right").css("left","165px");*/
		$("#left-index").animate({width:leftWidth,opacity:"show"});
		/*$("#footer-div").animate({width:$("#content").width()-165});*/
		$("#transverter").animate({left:leftWidth});
		$("#right").animate({width:$("#content").width()-$("#transverter").width()-leftWidth,left:leftWidth+8},function(){
			if(typeof openCloseClickCallBack == 'function'){
				openCloseClickCallBack(true);
			}
		});
		/*$("#right").width($("#right").width-157);*/
		
	}
});
if(!Array.prototype.map)
	Array.prototype.map = function(fn,scope) {
	var result = [],ri = 0;
	for (var i = 0,n = this.length; i < n; i++){
	  if(i in this){
	    result[ri++]  = fn.call(scope ,this[i],i,this);
	  }
	}
	return result;
};
var getWindowSize = function(){
	return ["Height","Width"].map(function(name){
	  return window["inner"+name] ||
		document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ];
	});
};
$(window).resize(function(){
	wSize();
});
wSize(); // 在主窗体中定义，设置调整目标