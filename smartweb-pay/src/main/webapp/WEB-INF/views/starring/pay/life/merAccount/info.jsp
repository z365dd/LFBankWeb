<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!-- JSTL -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = path;
%>
<!DOCTYPE HTML>
<html lang="zh-cn">


<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width,initial-scale=1.0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <title>商户入账明细</title>
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
    <link rel="stylesheet" href="<%=basePath%>/b_ide/bootstrap/treegrid/jquery.treegrid.min.css">
    <%--    其他引用--%>
</head>
<script src="<%=basePath%>/b_base/views/bootstrap/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript"
        src="<%=basePath%>/b_base/views/bootstrap/bootstrap-table.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="<%=basePath%>/b_base/views/bootstrap/bootstrap-table-treegrid.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="<%=basePath%>/b_base/views/bootstrap/jquery.treegrid.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="<%=basePath%>/b_base/views/starring/pay/com/com.js"
        charset="utf-8"></script>
<table id="table"></table>

<script type="text/javascript">
    var $table = $('#table');
    $(function() {
        parent.window.$('#iframe_detail').show();
        var text = window.parent.document.getElementById('comDiv').innerText;
        console.info(text);
        var data = JSON.parse(text);
        //控制台输出一下数据
        console.log(data);
        channelMap = chnlMap();
        payTypeMap = payTpMap();
        $table.bootstrapTable({
            data:data,
            idField: 'id',
            dataType:'jsonp',
            columns: [
                { field:'busi_NAME',title:'业务名称'},
                { field:'clr_DATE',title:'入账日期'},
                { field:'chnl_NO',title:'渠道名称',formatter:function (value,row){
                        const chnlNo = row.chnl_NO;
                        return getOrDefaltOfMap(channelMap,chnlNo);

                    }},
                { field:'pay_TP',title:'支付方式',formatter:function (value,row){
                        const payType = row.pay_TP;
                        if(payType == '160'){
                            return '批量扣款';
                        }
                        return getOrDefaltOfMap(payTypeMap, payType);
                    }},
                { field:'tot_NUM',title:'缴费笔数'},
                { field:'tot_AMT',title:'缴费金额'},
                { field:'clr_AMT',title:'清算金额'},
                { field:'dct_MERT_AMT',title:'商户补贴'},
                { field:'dct_BANK_AMT',title:'银行补贴'},
                { field:'fee_AMT',title:'手续费'},
                {field: 'bat_AMT', title: '批量金额',formatter: (value, row) => {
                        if(row.bat_AMT == undefined || row.bat_AMT == null){
                            return 0
                        }else{
                            return  row.bat_AMT;
                        }
                    }},
            ],

            // bootstrap-table-treegrid.js 插件配置 -- start

            //在哪一列展开树形
            treeShowField: 'busi_NAME',
            //指定父id列
            parentIdField: 'pid',

            onResetView: function(data) {
                //console.log('load');
                $table.treegrid({
                    initialState: 'collapsed',// 所有节点都折叠
                    // initialState: 'expanded',// 所有节点都展开，默认展开
                    treeColumn: 0,
                    // expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
                    // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                    onChange: function() {
                        $table.bootstrapTable('resetWidth');
                    }
                });

                //只展开树形的第一级节点
                // $table.treegrid('getRootNodes').treegrid('collapsed');

            },

            // bootstrap-table-treetreegrid.js 插件配置 -- end
        });
    });



    function back() {
        parent.window.$('a[href^=\'#tab_list\']').click();
    }


</script>

</html>
