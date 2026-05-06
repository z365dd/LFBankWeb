<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/comm-js.jsp"%>
</head>
<body>
	<table id="${bootstrapTableId}" data-toggle="table"
		data-url="${data_url}" data-click-to-select="true"
		data-show-export="false" data-show-refresh="false"
		data-show-toggle="false" data-show-columns="false"
		data-pagination="true" data-search="true"
		data-query-params="queryParams" data-method="post"
		data-undefined-text="**" data-height="350"
		data-content-type="application/x-www-form-urlencoded"
		ravo="rainbow_fx_bj" class="table table-hover"
		data-show-header="true" data-side-pagination="server"
		data-pagination-h-align="left">
		<thead>

			<tr>
				<th data-field="state" data-radio="true"></th>
				<c:forEach items="${columnNames}" var="column" >
					<th data-field="<c:out value='${column[0]}'></c:out>" data-title="<c:out value='${column[1]}'></c:out>"></th>
				</c:forEach>
			</tr>
		</thead>
	</table>
	<input id="adtec_tag_tempValue_123288985" type="hidden" /> 
	<script type="text/javascript">
		$("#${bootstrapTableId}").bootstrapTable({
			onClickCell : function(field, value, row, $element) {
				$("#adtec_tag_tempValue_123288985").val(JSON.stringify(row));  //需要先转为普通字符串再传递
			}
		});

		function queryParams(params) {
			var paramsList = {
				pgside : 'server',
				pageSize : params.limit,
				start : params.offset + 1,
				pageNo : getPage(params),
				sort : params.sort,
				//order : params.order,
				search : $(".fixed-table-toolbar input")[0].value //获取搜索条件
			};
	    if(!('${searchPara}' === '')){
	    	var tempPara = '${searchPara}';
	    	tempPara = tempPara.replace(/@/g,"\"");
	    	tempPara = JSON.parse(tempPara);
	    	for(var v in tempPara){
	    		console.log(v+":"+tempPara[v]);
	    		paramsList[v] = tempPara[v];
	    	}
	    	console.log(paramsList);
	    }
			return paramsList;

		}
		function getPage(params) {
			if (!isNaN(params.offset) || !isNaN(params.limit)) {
				return params.offset / params.limit + 1;
			}
		}
	</script>
</body>
</html>