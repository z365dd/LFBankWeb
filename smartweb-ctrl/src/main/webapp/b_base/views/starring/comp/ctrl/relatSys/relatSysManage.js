$(function(){
	//循环 id以tab_开头的都要调用一个方法:
	$("a[href^='#tab_']").each(function(i){
		console.info("choose tab");
		$(this).click(function(e){
			showTabs($(this).attr("href"),$(this).attr("url"));//参数为 tab的href(对应iframe的id) 和url
		    e.preventDefault();
		});
	});
	$("a[href^='#tab_list']").click();		
})

/* tab1点击执行函数 */
function tab1() {
	$("a[href^='#tab_list']").click();	
	freshTable('table');
}

//控制顶部tab 显 隐	  		此页面 全显示 不用控制
function showTabs(tabsId,url) {
	console.info('tabId:' + tabsId);	//获取iframe的id
	console.info('url:' + url);			//页面路径url
    var $tabContent = $(tabsId);		//定义对象  --> 当前被点击tab对应的iframe对象
    $("a[href='"+tabsId+"']").tab('show');		//把被点击的顶部tab显示出来
    if($("a[href='"+tabsId+"']").attr("isLoad")!="true") {		//不太明白
    	$tabContent.find("iframe").attr("src",url);
    }
    $tabContent.siblings().each(function(i){
    	$(this).find("iframe").hide();		
    });
    if(tabsId=="#tab_list"){			//如果被点击的tab是tab_list 则只显示tab_list 其他隐藏
    	$("a[href='#tab_list']").show();
    	$("a[href='#tab_add']").show();
    	$("a[href='#tab_mod']").hide();
    	$("a[href='#tab_det']").hide();
    }else if(tabsId=="#tab_det"){	//如果被点击的tab是tab_detail 则都显示
    	$("a[href='#tab_list']").show();
    	$("a[href='#tab_add']").show();
    	$("a[href='#tab_det']").show();
    	$("a[href='#tab_mod']").hide();
    }else{								//如果被点击的是其他的 ,则只显示被点击的 和 tab_list
    	$("a[href='"+tabsId+"']").show();
        $("a[href='"+tabsId+"']").parent().siblings().each(function(i){
        	if($(this).find("a").attr("href")!="#tab_list"){
        		$(this).find("a").hide();
       	}; 
        }); 
    };
}