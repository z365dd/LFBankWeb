<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值"%>

<i id="${id}ImageI" style="display:${not empty value?'block':'none'};"><img id="${id}Image" src="${ctxStatic}/mainframe/img/first_menu/${value}" /></i>&nbsp;<label id="${id}ImageLabel">${not empty value?value:'无'}</label>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value}"/><a id="${id}Button" href="javascript:" class="btn">选择</a>&nbsp;&nbsp;
<script type="text/javascript">
	$("#${id}Button").click(function(){
		top.$.jBox.open("iframe:${ctx}/tag/imgselect?value="+$("#${id}").val(), "选择图片", 700, $(top.document).height()-180, {
            buttons:{"确定":"ok", "清除":"clear", "关闭":true}, submit:function(v, h, f){
                if (v=="ok"){
                	var image = $.trim(h.find("iframe")[0].contentWindow.$("#image").val());
                	$("#${id}ImageI").attr("style", "display:block;");
                	$("#${id}Image").attr("src", "${ctxStatic}/mainframe/img/first_menu/"+image);
	                $("#${id}ImageLabel").text(image);
	                $("#${id}").val(image);
                }else if (v=="clear"){
	                $("#${id}ImageI").attr("style", "display:none;");
	                $("#${id}ImageLabel").text("无");
	                $("#${id}").val("");
                }
            }, loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            }
        });
	});
</script>