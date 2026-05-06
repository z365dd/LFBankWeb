<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <script type="text/javascript">
        var ctx = '${ctx}', ctxStatic = '${ctxStatic}';
    </script>
    <script src="${ctx}/b_base/jquery-3.6.1.min.js"></script>
    <script src="${ctx}/b_base/bootstrap-3.4.1/js/bootstrap.js"></script>
    <script src="${ctx}/b_base/common/smartweb.js"></script>
    <script src="${ctx}/b_base/views/starring/pay/com/com.js"></script>
    <script src="${ctx}/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js"></script>
    <script src="${ctx}/b_ide/bootstrap/module/bootstrap-table/dist/locale/bootstrap-table-zh-CN.js"></script>
    <script src="${ctx}/b_ide/bootstrap/module/bootstrap-table/src/extensions/export/jspdf/libs/base64.js"></script>
    <script src="${ctx}/b_base/views/starring/pay/offline/school_register/list.js"></script>
    <link rel="stylesheet" href="${ctx}/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
    <link rel="stylesheet" href="${ctx}/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.css"/>
</head>
<body>
<form class="form-horizontal" id="from" style="margin: 20px">
    <div class="form-group">
        <label class="col-sm-1 control-label">业务编号</label>
        <div class="col-sm-2"><input id="busiNo" name="busiNo" class="form-control"/></div>
        <label class="col-sm-1 control-label">业务名称</label>
        <div class="col-sm-2"><input id="busiName" name="busiName" class="form-control"/></div>
        <label class="col-sm-1 control-label">机构号</label>
        <div class="col-sm-2"><input id="officeId" name="officeId" class="form-control" value="${office.id}"/></div>
        <div class="col-sm-1"><button type="button" class="btn btn-info" id="qryBtn">查询</button></div>
        <div class="col-sm-1"><button type="button" class="btn btn-info" id="export">导出</button></div>
    </div>
</form>
<table id="merTable"></table>
</body>
</html>
