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
    <script src="${ctx}/b_base/views/starring/pay/offline/school_register/add.js"></script>
    <link rel="stylesheet" href="${ctx}/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
</head>
<body>
<form id="from" class="form-horizontal" style="margin:20px;">
    <input type="hidden" id="provinceName" name="provinceName"/>
    <input type="hidden" id="officeName" name="officeName" value="${office.name}"/>
    <div class="form-group"><label class="col-sm-2 control-label">业务编号</label><div class="col-sm-4"><input id="busiNo" name="busiNo" class="form-control"/></div><div id="busiNoErrTip" style="display:none;color:red"></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">业务名称</label><div class="col-sm-4"><input id="busiName" name="busiName" class="form-control"/></div><div id="busiNameErrTip" style="display:none;color:red"></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">机构号</label><div class="col-sm-4"><input id="officeId" name="officeId" class="form-control" value="${office.id}"/></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">清算账户</label><div class="col-sm-4"><input id="payAcct" name="payAcct" class="form-control"/></div><div id="acctErrTip" style="display:none;color:red"></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">账户名称</label><div class="col-sm-4"><input id="payAcctName" name="payAcctName" class="form-control"/></div><div id="acctNameErrTip" style="display:none;color:red"></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">清算周期</label><div class="col-sm-4"><select id="clrCycle" name="clrCycle" class="form-control"><option value="Y">T1清算</option><option value="N">D1清算</option></select></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">是否拆分</label><div class="col-sm-4"><select id="sepaFlg" name="sepaFlg" class="form-control"><option value="N">否</option><option value="Y">是</option></select></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">省</label><div class="col-sm-2"><select id="province" name="province" class="form-control"></select></div><label class="col-sm-1 control-label">市</label><div class="col-sm-2"><select id="city" name="city" class="form-control"></select></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">详细地址</label><div class="col-sm-4"><input id="detailedAddress" name="detailedAddress" class="form-control"/></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">咨询电话</label><div class="col-sm-4"><input id="phoneNo" name="phoneNo" class="form-control"/></div><div id="phoneNoErrTip" style="display:none;color:red"></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">联系人</label><div class="col-sm-4"><input id="name" name="name" class="form-control"/></div><div id="nameErrTip" style="display:none;color:red"></div></div>
    <div class="form-group"><label class="col-sm-2 control-label">商户状态</label><div class="col-sm-4"><select id="openStat" name="openStat" class="form-control"><option value="Y">上架</option><option value="N">下架</option></select></div></div>
    <div class="form-group"><div class="col-sm-offset-2 col-sm-1"><button id="save" type="submit" class="btn btn-info">提交</button></div><div class="col-sm-1"><button id="back" type="button" class="btn btn-default">返回</button></div></div>
</form>
</body>
</html>
