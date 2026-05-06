$(function(){
	$("a[href^='#tab_']").each(function(i){
		console.info("choose tab");
		$(this).click(function(e){
			showTabs($(this).attr("href"),$(this).attr("url"));
		    e.preventDefault();
		});
	});
	$("a[href^='#tab_list']").click();
});
function showTabs(tabsId,url) {
	console.info('tabId:' + tabsId);
	console.info('url:' + url);
    var $tabContent = $(tabsId);
    $("a[href='"+tabsId+"']").tab('show');
    if($("a[href='"+tabsId+"']").attr("isLoad")!="true") {
    	$tabContent.find("iframe").attr("src",url);
    }
    $tabContent.find("iframe").show();
    $tabContent.siblings().each(function(i){
    	$(this).find("iframe").hide();
    });
    if(tabsId=="#tab_list"){
    	$("a[href='#tab_list']").show();
    	$("a[href='#tab_add']").show();
    	$("a[href='#tab_update']").hide();
    	$("#tab2").text("服务码新增");
    }else{
    	$("a[href='"+tabsId+"']").show();
        $("a[href='"+tabsId+"']").parent().siblings().each(function(i){
        	if($(this).find("a").attr("href")!="#tab_list"){
//       		$(this).find("a").hide();
        	}
        });
    }
}

//返回主页(N-不重新加载  Y-重新加载)
function tab1(isLoad){
	if("N"==isLoad){
		$("a[href='#tab_list']").attr('isLoad','true');
	}else{
		$("a[href='#tab_list']").attr('isLoad','false');
	}
	$("#tab1").click();
}

function changeName(tab){
	if(tab == "update"){
		$("#tab2").text("服务码修改");
	}else{
		$("#tab2").text("服务码新增");
	}
}

//调用修改页面 数据回显的方法 setVal()
function setModData(Data){
	//返回主页顶部，table列表过长时点修改操作跳到修改页面 保证位于修改页面顶部
	$(document).scrollTop(0);
	$("#tab_add").find('iframe').load(function(){
		$("#tab_add").find('iframe')[0].contentWindow.setVal(Data);
		$("#tab_add").find('iframe').off("load");
	});
}
