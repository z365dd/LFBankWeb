$(document).ready(function(){
	parent.window.$("#iframe_init").show();

	$("#partBtn").click(function() {
		confirmx("是否确定增量初始化", function(){
			var url = ctx + "/sys/sysPermissionWeight/initPermission";
			//向后台发送参数
			$.post(url, {isAll:'false'},
				function(data){
					if(data.returnCode!==undefined && "0000"!=data.returnCode){
						var errMsg = "错误信息["+data.message+"]";
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						var successMsg = "初始化成功["+data.message+"]";
						showContent(successMsg,"success");
						parent.window.$("a[href^='#tab_init']").click();
					}
				}, "json");
		});
	});

	$("#allBtn").click(function() {
		confirmx("是否确定完全初始化", function(){
			var url = ctx + "/sys/sysPermissionWeight/initPermission";
			//向后台发送参数
			$.post(url, {isAll:'true'},
				function(data){
					if(data.returnCode!==undefined && "0000"!=data.returnCode){
						var errMsg = "错误信息["+data.message+"]";
						showContent(errMsg,"error");
						return '0';
					}else if(data.msg_type == "success"){
						var successMsg = "初始化成功["+data.message+"]";
						showContent(successMsg,"success");
						parent.window.$("a[href^='#tab_init']").click();
					}
				}, "json");
		});
	});

});
