<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="label_name" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="label_value" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true"%>
<%@ attribute name="ext_id" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="is_all" type="java.lang.Boolean" required="false" description="是否列出全部数据，设置true则不进行数据权限过滤（目前仅对Office有效）"%>
<%@ attribute name="not_allow_select_root" type="java.lang.Boolean" required="false" description="不允许选择根节点"%>
<%@ attribute name="not_allow_select_parent" type="java.lang.Boolean" required="false" description="不允许选择父节点"%>
<%@ attribute name="module" type="java.lang.String" required="false" description="过滤栏目模型（只显示指定模型，仅针对CMS的Category树）"%>
<%@ attribute name="select_scope_module" type="java.lang.Boolean" required="false" description="选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）"%>
<%@ attribute name="allow_clear" type="java.lang.Boolean" required="false" description="是否允许清除"%>
<%@ attribute name="allow_input" type="java.lang.Boolean" required="false" description="文本框可填写"%>
<%@ attribute name="css_class" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="css_style" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="small_btn" type="java.lang.Boolean" required="false" description="缩小按钮显示"%>
<%@ attribute name="hide_btn" type="java.lang.Boolean" required="false" description="是否显示按钮"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="data_msg_required" type="java.lang.String" required="false" description=""%>
<%@ attribute name="validators" type="java.lang.String" required="false" description="校验"%>
<%@ attribute name="treesearch_required" type="java.lang.Boolean" required="false" description="是否必输"%>
<%@ attribute name="win_height" type="java.lang.String" required="false" description="窗口高度"%>
<%@ attribute name="win_width" type="java.lang.String" required="false" description="窗口宽度"%>
<div class="input-append col-sm-6">
	<input id="${id}Id" name="${name}" class="${css_class}" type="hidden" value="${value}" <c:if test="${treesearch_required}">check-empty="true"</c:if>/>
	<input input_type="treesearch" search_title="${title}" placeholder="请输入${title }" search_url="${url}" id="${id}Name" name="${label_name}" ${allow_input?'':'readonly="readonly"'} type="text" value="${label_value}" data-msg-required="${data_msg_required}" 
		class="${css_class}" style="${css_style}" checked="${checked}" ext_id="${ext_id}" is_all="${is_all}" not_allow_select_root="${not_allow_select_root}" not_allow_select_parent="${not_allow_select_parent}" 
		module="${module}" select_scope_module="${select_scope_module}" allow_clear="${allow_clear}" win_height="${win_height}" win_width="${win_width}"
		<c:if test="${not empty validators}">${fns:gernValidators(validators)}</c:if> /><a id="${id}Button" href="javascript:" class="btn ${disabled} ${hide_btn ? 'hide' : ''}" style="${small_btn?'padding:4px 2px;':''}">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
</div>
<script type="text/javascript">	
	$("#${id}Button, #${id}Name").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		if($("#${id}Name").attr("search_url")==""){
			return false;
		}
		var win_height = 420;
		var win_width = 300;
		console.info("${id}Name.win_height="+$("#${id}Name").attr("win_height")+" , top.${id}Name.win_height="+top.$("#${id}Name").attr("win_height"));
		if(undefined!=$("#${id}Name").attr("win_height") && ""!=$("#${id}Name").attr("win_height")){
			win_height = parseInt($("#${id}Name").attr("win_height"));
		}
		if(undefined!=$("#${id}Name").attr("win_width") && ""!=$("#${id}Name").attr("win_width")){
			win_width = parseInt($("#${id}Name").attr("win_width"));
		}
		/* 正常打开	*/
		top.$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent($("#${id}Name").attr("search_url"))+"&module=${module}&checked=${checked}&extId="+$("#${id}Name").attr("ext_id")+"&isAll="+$("#${id}Name").attr("is_all"), "选择"+$("#${id}Name").attr("search_title"), win_width, win_height, {
			ajaxData:{selectIds: $("#${id}Id").val()},buttons:{"确定":"ok", ${allow_clear?"\"清除\":\"clear\", ":""}"关闭":true}, submit:function(v, h, f){
				console.info("${id}Name.win_height="+$("#${id}Name").attr("win_height"));
				if (v=="ok"){
					var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
					var ids = [], names = [], nodes = [];
					if ($("#${id}Name").attr("checked") == "true"){
						nodes = tree.getCheckedNodes(true);
					}else{
						nodes = tree.getSelectedNodes();
					}
					for(var i=0; i<nodes.length; i++) {
						if (nodes[i].isParent && ("true"==$("#${id}Name").attr("checked") && "true"==$("#${id}Name").attr("not_allow_select_parent"))){
							continue; /* 如果为复选框选择，则过滤掉父节点 */
						}

						if (nodes[i].level == 0 && $("#${id}Name").attr("not_allow_select_root")=="true"){
							/* 不允许选择根节点 */
							top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。","error");
							return false;
						}
						
						if (nodes[i].isParent  && $("#${id}Name").attr("not_allow_select_parent")=="true"){
							/* 不允许选择父节点 */
							top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。","error");
							return false;
						}
						
						if(undefined!=$("#${id}Name").attr("module") && ""!=$("#${id}Name").attr("module") && "true"==$("#${id}Name").attr("select_scope_module")){
							/*启用过滤栏目且过滤栏目模型不为空*/
							if (nodes[i].module == ""){
								top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。","error");
								return false;
							}else if (nodes[i].module != "${module}"){
								top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。","error");
								return false;
							}	
						}
						
						ids.push(nodes[i].id);
						names.push(nodes[i].name);
						if("true"!=$("#${id}Name").attr("checked")){
							/*如果为非复选框选择，则返回第一个选择*/
							break;
						}
					}
					$("#${id}Id").val(ids.join(",").replace(/u_/ig,""));
					$("#${id}Name").val(names.join(","));
					
					if(ids.length>0){
						openLeftMenu(nodes[i].pId,nodes[i].pName);
					}
					
				}
				else if (v=="clear"){
					console.info('allow_clear='+$("#${id}Name").attr("allow_clear"));
					if($("#${id}Name").attr("allow_clear")=="true"){
						$("#${id}Id").val("");
						$("#${id}Name").val("");
					}
                }
				if(typeof ${id}TreeselectCallBack == 'function'){
					${id}TreeselectCallBack(v, h, f);
				}
				try{showProof();}catch(e){}
			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	});
</script>