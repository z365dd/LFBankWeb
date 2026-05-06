<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="name" type="java.lang.String" required="false"%>
<%@ attribute name="value" type="java.lang.String" required="false"%>
<%@ attribute name="tabindex" type="java.lang.Integer" required="false"%>
<%@ attribute name="title" type="java.lang.String"  required="false"  description="弹出窗口的标题,默认是：选择数据" %>
<%@ attribute name="width" type="java.lang.String"  required="false"  description="弹出窗口的宽度" %>
<%@ attribute name="height" type="java.lang.String"  required="false"  description="弹出窗口的高度" %>
<%@ attribute name="data_url" type="java.lang.String"  required="true" description="查询数据的接口"  %>
<%@ attribute name="columnNames" type="java.lang.String"  required="true" description="窗口显示接口返回的列名+标题,示例:列名:标题,列名:标题" %>
<%@ attribute name="callbackFunction" type="java.lang.String"  required="false" description="自定义处理窗口中信息的函数 示例:fun(v,h,f); 要分号" %>
<%@ attribute name="placeholder" type="java.lang.String"  required="true" description="显示的界面上的字段值" %>
<%@ attribute name="sendColumnData" type="java.lang.String"  required="true" description="传入后台的字段值" %>
<%@ attribute name="searchParaFunction" type="java.lang.String"  required="false" description="搜索时需要额外传送的参数的处理函数，封装成对象返回 不要分号" %>
<div class="input-append" >

	<input id="adtec_tag_random_id_234934131${id}" tabindex='<c:if test="${tabindex != \"\"}">${tabindex}</c:if>' value="" placeholder = "${value}" type="text" class="form-control input-small" />
		
	<input id="${id}" name="${name}" type="hidden" class="form-control input-small"
		value="${value}" />
	
</div>

<script type="text/javascript">
    
    
	$("#adtec_tag_random_id_234934131${id}").focus(function() {

		if ($("#${id}").hasClass("disabled")) {
			return true;
		}
		
		var title = '${title}';	if(title.length == 0) title = "选择数据";
		var width , height;
		if('${width}' === '') {width = '450';} else {width = '${width}';}
		if('${height}' === '') {height = '490';} else {height = '${height}';}
		
		var ajaxData = {
				data_url : '${data_url}',
				columnNames : '${columnNames}'
			}
		if(!('${searchParaFunction}' === '') ){
			var temp = JSON.stringify(${searchParaFunction});
			temp = temp.replace(/\"/g,'@');   
			ajaxData.searchPara = temp;
		}
	 
		

		top.$.jBox.open("iframe:${ctx}/tag/tagSelectDataWindow/",title, parseInt(width), parseInt(height), {
			ajaxData : ajaxData,
			buttons:{"确定":"ok", "清除":"clear", "关闭":true},
			loaded : function(h) {
				$(".jbox-content", top.document).css("overflow-y", "hidden");
			},
			submit:function(v, h, f){
				if('${callbackFunction}' === '' ){
					if(v === 'ok'){
					var val =  JSON.parse(h.find("iframe")[0].contentWindow.$("#adtec_tag_tempValue_123288985").val());
						$("#${id}").val(val.${sendColumnData});
						$("#adtec_tag_random_id_234934131${id}").attr("placeholder",val.${placeholder})
					}else if(v === 'clear'){
						$("#${id}").val("");
						$("#adtec_tag_random_id_234934131${id}").attr("placeholder","")
					}
					
				}else{
					${callbackFunction}
				}
				
				
			}

		})

	});
</script>