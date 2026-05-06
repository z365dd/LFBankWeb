console.log("fsvrFileDownload.js");
$(function() {
	// 提交
	$('#submitBtn').on('click', fileDownload);
	/* 关闭按钮 */
	$('#cancelBtn').on('click', closeBtnToDo);
})

// 文件下载
function fileDownload() {
	if (proof()) {
		$('#table').bootstrapTable('removeAll');
		$.get(ctx + "/comp/fsvr/test/fileDownload", {
			FILE_TRANS_TP : getS("FILE_TRANS_TP"),
			FILE_SET_SEQ : getI("FILE_SET_SEQ"),
			PUB_FILE_PATH : getI("PUB_FILE_PATH"),
		}, function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "文件下载[" + data.message + "]";
				showContent(successMsg, "success");
				var dataVal = data.dataSetResult[0].data[0];
				// 设置值
				setVal(dataVal);
			}
		}, "json");
	}

}

// 同步对账提交成功返回设置值
function setVal(Data) {
	var dataArr = [];
	if (Data.lIST != undefined || Data.fILE_TP_NUM > 0) {
		dataArr = Data.lIST;
		for (var a = 0; a < dataArr.length; a++) {
			/* dataArr[a].action = "<a onclick='downLoad(this)'>下载</a>"; */
			dataArr[a].action = "<a href=" + ctx
					+ "/comp/fsvr/test/downLoadPub?filePath="
					+ encodeURIComponent(dataArr[a].sRC_SUB_FILE_PATH+"/"+dataArr[a].sRC_FILE_NAME) + "&aliasFileName="+encodeURIComponent(dataArr[a].aLIAS_FILE_NAME)+">下载</a>";
		}
		$('#table').bootstrapTable('load', dataArr);
	}
}

// 结果文件下载
function downLoad(obj) {
	/* 获取元素对象所在table的数据 */
	$table = $(obj).parents('table');
	// 此函数的对象所在行的序号
	var $rowIndex = $(obj).parents('tbody tr').index();
	// 获取table所有数据返回json数组
	var dataArr = $table.bootstrapTable('getData');
	// 返回此行的数据
	Data = dataArr[$rowIndex];
	// 下载文件
	var fileId = Data.ALIAS_FILE_NAME;
	console.info("下载文件" + fileId);
	if (fileId != "" && fileId != undefined) {
		window
				.open(ctx + "/comp/fchk/test/fChkVoaTest/downLoad?file="
						+ fileId);
	} else {
		return;
	}
}
