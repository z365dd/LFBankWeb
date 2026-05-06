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
    	$("a[href='#tab_save']").hide();
//    	$("#tab2").text("新增模型组件");
    }else{
    	$("a[href='"+tabsId+"']").show();
        $("a[href='"+tabsId+"']").parent().siblings().each(function(i){
        	if($(this).find("a").attr("href")!="#tab_list"){
//       		$(this).find("a").hide();
        	}
        });
    }
}

//获取到的案例回显到 测试案例页面
function tab1(Json){
	$("#tab1").attr("isLoad","false");
	$("#tab1").click();
	//调用子页面的setVal方法
	$("#tab_list").find('iframe').load(function(){
		$("#tab_list").find('iframe')[0].contentWindow.setVal(Json);
		$("#tab_list").find('iframe').off("load");
	});
	changeName("tab2");
}

//点击 测试案例 tab 选项(根据参数是否重新加载页面 Y-加载  N-不加载)
function tab11(isLoad){
	if(isLoad=="N"){
		$("#tab1").attr("isLoad","true");
	}else{
		$("#tab1").attr("isLoad","false");
	}
	$("#tab1").click();
	changeName("tab2");
	goTop();
}

//载入案例
function tab2(){
	$("#tab2").click();
	goTop();
	$("a[href='#tab_save']").hide();
}

//另存为案例
function tab3(jsonStr){
	//调用子页面的saveNewBtnFun方法
	$("#tab_save").find('iframe').load(function(){
		$("#tab_save").find('iframe')[0].contentWindow.setSaveVal(jsonStr);
	});
	goTop();
}

function changeName(tab){
	if(tab == "tab3"){
		$("#tab2").text("另存为案例");
	}else{
		$("#tab2").text("载入案例");
	}
}

//返回顶页
function goTop(){
	$(document).scrollTop(0);
}