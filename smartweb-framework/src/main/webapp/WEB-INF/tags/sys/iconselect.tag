<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值"%>
<%@ attribute name="url" type="java.lang.String" required="false" description="图标数据地址"%>
<%@ attribute name="icon_css" type="java.lang.String" required="false" description="图标自定义CSS"%>
<%@ attribute name="icon_required" type="java.lang.Boolean" required="false" description="是否必输"%>
<i id="${id}Icon" class="${icon_css} icon-${not empty value?value:'hide'}"></i>&nbsp;<label id="${id}IconLabel">${not empty value?value:'无'}</label>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value}" icon_url="${url}" <c:if test="${icon_required}">check-empty="true"</c:if> /><a id="${id}Button" href="javascript:" class="btn">选择</a>&nbsp;&nbsp;
<script type="text/javascript">
	$("#${id}Button").click(function(){
		var openUrl = "${ctx}/tag/iconselect";
		if($("#${id}").attr("icon_url")!=undefined && $("#${id}").attr("icon_url")!=''){
			openUrl = $("#${id}").attr("icon_url");
		}
		top.$.jBox.open("iframe:"+openUrl+"?value="+$("#${id}").val(), "选择图标", 700, $(top.document).height()-180, {
            buttons:{"确定":"ok", "清除":"clear", "关闭":true}, submit:function(v, h, f){
            	var iconClass = $("#${id}Icon").attr('class');
            	var iconCss = '';
            	if(undefined!=iconClass){
            		var split=iconClass.split(' ');
            		for(var i=0;i<split.length-1;i++){
            			iconCss = iconCss + split[i];
            		}
            		iconCss = iconCss + ' ';
            	}
                if (v=="ok"){
                	var icon = $.trim(h.find("iframe")[0].contentWindow.$("#icon").val());
                	$("#${id}Icon").attr("class", iconCss + icon);
	                $("#${id}IconLabel").text(icon);
	                $("#${id}").val(icon.substring(icon.indexOf('icon-')+5,icon.length));
                }else if (v=="clear"){
	                $("#${id}Icon").attr("class", iconCss + "icon-hide");
	                $("#${id}IconLabel").text("无");
	                $("#${id}").val("");
                }
                try{showProof();}catch(e){}
            }, loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            }
        });
	});
	/*回显图片*/
	function ${id}IconPreview(){
		var iconClass = $("#${id}Icon").attr('class');
    	var iconCss = '';
    	if(undefined!=iconClass){
    		var split=iconClass.split(' ');
    		for(var i=0;i<split.length-1;i++){
    			iconCss = iconCss + split[i];
    		}
    		iconCss = iconCss + ' ';
    	}
    	var icon = $("#${id}").val();
    	if(icon!=''){
    		icon = 'icon-' + icon;
    		$("#${id}Icon").attr("class", iconCss + icon);
            $("#${id}IconLabel").text(icon);
            $("#${id}").val(icon.substring(icon.indexOf('icon-')+5,icon.length));
    	} else {
    		$("#${id}Icon").attr("class", iconCss + "icon-hide");
            $("#${id}IconLabel").text("无");
            $("#${id}").val("");
    	}   	
    	try{showProof();}catch(e){}
	}
	${id}IconPreview();
</script>