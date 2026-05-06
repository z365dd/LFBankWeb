$(document).ready(function(){
	$("a[href^='#tab_']").each(function(i){
		console.info("choose tab");
		$(this).click(function(e){
			showTabs($(this).attr("href"),$(this).attr("url"));
		    e.preventDefault();
		});
	});
	var id = $.session.get('id');
	var flowStat = $.session.get('flowStat');
	if(id!=undefined){
		if(flowStat!=undefined && (flowStat=='00' || flowStat=='01')){
			/*状态为00-开始、01-处理中的则直接调转到审核页面*/
			$("a[href^='#tab_apply']").click();
		}else{
			/*其他状态跳转到详情页面*/
			$("a[href^='#tab_detail']").click();
		}
	}else{
		$("a[href^='#tab_list']").click();
	}
});	

function showTabs(tabsId,url) {
	console.info('tabId:' + tabsId);
	console.info('url:' + url);
    var $tabContent = $(tabsId);
    $("a[href='"+tabsId+"']").tab('show');
    if($("a[href='"+tabsId+"']").attr("isLoad")!="true") {
    	$tabContent.find("iframe").attr("src",url);
    }
    $tabContent.siblings().each(function(i){
    	$(this).find("iframe").hide();
    });
    if(tabsId=="#tab_list"){
    	$("a[href='#tab_list']").show();
    	$("a[href='#tab_add']").show();
    	$("a[href='#tab_update']").hide();
    	$("a[href='#tab_detail']").hide();
    	$("a[href='#tab_apply']").hide();
    }else{
    	$("a[href='"+tabsId+"']").show();
        $("a[href='"+tabsId+"']").parent().siblings().each(function(i){
        	if($(this).find("a").attr("href")!="#tab_list"){
        		$(this).find("a").hide();
        	}
        });
    }
}