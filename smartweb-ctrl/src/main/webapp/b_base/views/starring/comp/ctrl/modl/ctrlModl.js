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
	$(document).scrollTop(0);
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
    	$("#tab2").text("组件新增");
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
		$("#tab2").text("组件修改");
	}else{
		$("#tab2").text("组件新增");
	}
}

