$(document).ready(function() {
});

function update(formData) {

	/*向后台发送参数*/
	$.post(ctx + "/sys/modules/sysUserAcct/update", formData, data => {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
			return '0';
		} else if (data.msg_type == "success") {
			var successMsg = "修改交易[" + data.message + "]";
			showContent(successMsg, "success");
			console.info("修改交易成功");
			refreshTable();
		}
	}, "json");
}

function btnAddClick() {
	bootstrapModal.create('createAcctModal');

	const modalDataArray = [
		{
			title: '新增账号',
			height: 200,
			width: 600,
			onReady: () => {
				loadSelectUnder('#createAcctModal');
			},
			htmlString: getAddFromStr
		}
	];
	bootstrapModal.modalDataControl('#createAcctModal', modalDataArray, () => {
		const formData = $("#addForm").serializeObject();
		formData['userId'] = $('#userId').val();
		/*const pwd = formData['pwd'];
		formData['pwd'] = encryptRSA(pwd);*/
		save(formData);
	});
	bootstrapModal.show('#createAcctModal');
}

function btnUpdateClick(id, userAcctTp, loginName) {
	bootstrapModal.create('updateAcctModal');

	const modalDataArray = [
		{
			title: '修改密码',
			height: 100,
			width: 600,
			onReady: () => {
				loadSelectUnder('#updateAcctModal');
			},
			htmlString: getUpdateFromStr
		}
	];
	bootstrapModal.modalDataControl('#updateAcctModal', modalDataArray, () => {
		const formData = $("#updateForm").serializeObject();
		formData['userId'] = $('#userId').val();
		formData['userAcctTp'] = userAcctTp;
		formData['loginName'] = loginName;
		formData['id'] = id;
		/*const pwd = formData['pwd'];
		formData['pwd'] = encryptRSA(pwd);*/
		update(formData);
	});
	bootstrapModal.show('#updateAcctModal');
}

const save = formData => {
	/*向后台发送参数*/
	$.post(ctx + "/sys/modules/sysUserAcct/insert", formData,
		data => {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
				return '0';
			} else if (data.msg_type == "success") {
				var successMsg = "新增交易[" + data.message + "]";
				showContent(successMsg, "success");
				console.info("新增交易成功");
				refreshTable();
			}
		}, "json");

}

function del(id) {
	confirmx("是否确定删除该用户账号信息", function() {
		var url = ctx + "/sys/modules/sysUserAcct/delete";
		//向后台发送参数
		const params = {
			id: id
		};
		$.post(url, params, function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
				return '0';
			} else if (data.msg_type == "success") {
				var successMsg = "删除信息[" + data.message + "]";
				showContent(successMsg, "success");
				refreshTable();
			}
		}, "json");
	});
}

const refreshTable = () => {
	const $table = $('#acct-table');
	const $preClick = $table.parent().parent().find(".page-pre");
	if ($preClick.siblings().length > 1) {
		$preClick.next().click();
	}
	$table.bootstrapTable('refresh');
}

const getAddFromStr = () => {
	return `
		<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
			id="addForm" style="margin-top:10px">
			<div class="clearfix"></div>
			<div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-1 column"></div>
				<div class="col-md-10 column">
					<div ravo="rainbow_fx" class="form-group">
						<label for="inputEmail3" class="col-sm-4 control-label">
							账号类型 </label>
						<div class="col-sm-7">
							<select data-role="multiselect" id="userAcctTp" class=""
								data-url="${ctx}/sys/dict/selectData?type=USER_ACCT_TP"
								data-async="false" blank-item="true" name="userAcctTp"
								data-max-height="300" data-bv-notempty="true" checkbtn="userAcctTp"
								data-bv-notempty-message="选项不能为空!"></select>
						</div>
					</div>
				</div>
			</div>
			<div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-1 column"></div>
				<div class="col-md-10 column">
					<div ravo="rainbow_fx" class="form-group">
						<label for="inputEmail3" class="col-sm-4 control-label">
							登录账号 </label>
						<div class="col-sm-6">
							<input type="text" class="form-control" placeholder="请输入登录账号"
								name="loginName" id="loginName" check-empty="true" />
						</div>
					</div>
				</div>
			</div>
			<div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-1 column"></div>
				<div class="col-md-10 column">
					<div ravo="rainbow_fx" class="form-group">
						<label for="inputEmail3" class="col-sm-4 control-label">
							登录密码 </label>
						<div class="col-sm-6">
							<input type="password" class="form-control"
								placeholder="请输入登录密码" id="pwd" name="pwd" check-empty="true" />
						</div>
					</div>
				</div>
			</div>
		</form>
	`;
}

const getUpdateFromStr = () => {
	return `
		<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
			id="updateForm" style="margin-top:10px">
			<div class="clearfix"></div>
			<div ravo="rainbow_fx_layout" class="row clearfix">
				<div class="col-md-1 column"></div>
				<div class="col-md-10 column">
					<div ravo="rainbow_fx" class="form-group">
						<label for="inputEmail3" class="col-sm-4 control-label">
							登录密码 </label>
						<div class="col-sm-6">
							<input type="password" class="form-control"
								placeholder="请输入登录密码" id="pwd" name="pwd" check-empty="true" />
						</div>
					</div>
				</div>
			</div>
		</form>
	`;
}

/*分页查询传参方法*/
function queryParams(params) {
	var formData = $("#listForm").serializeObject();
	var paramList = {
		pgside: 'server',/*服务器分页*/
		pageSize: params.limit,
		start: params.offset + 1,
		pageNo: getPage(params),
		sort: params.sort,
		order: params.order
	};
	return paramList;
}

function getPage(params) {
	if (!isNaN(params.offset) || !isNaN(params.limit)) {
		return params.offset / params.limit + 1;
	}
}