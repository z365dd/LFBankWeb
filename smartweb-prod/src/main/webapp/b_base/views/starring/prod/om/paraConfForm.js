var startHtml = "<div ravo='rainbow_fx_layout' class='row clearfix' style='margin-top:20px'>"+
				"	<div class='col-md-5 column'>";
var endHtml = "</div>";

$(function() {
	parent.window.$("#iframe_list").show();
	$("#model_0").hide();
	resetHeight();
	getParaConfInfo();
});


function getParaConfInfo() {
	
	$.post(ctx + "/prod/om/freshPara/getParaConfInfo", {
		
	}, function(data) {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			var divObj = $("#model_0").html();
//			console.log(data);
			for(var i = 0 ; i < data.dataSetResult.length; i++){
//				console.info(data.dataSetResult[i]);
				var jsonObj = data.dataSetResult[i];
				copyHtml(jsonObj, divObj);
				chgHeight(50);
			}
		}
	}, "json");
}


function copyHtml(jsonObj, divObj) {
	if (jsonObj.data == "[]") {
		return;
	}
	console.log(jsonObj.dataSetName);
	var last = $("div[id^='model_']:last").attr("id").split("_")[1];
	last++;
	var x = last; 
	var html = "<div id='model_"+x+"'>" + divObj + "</div>";
	var modelId = "model_"+x;
	var $lastDiv = $("div[id^='model_']:last");
	$("div[id^='model_']:last").after(html);
	$("div[id^='model_']:last").find("a").html(jsonObj.dataSetName);
	$("div[id^='model_']:last").find("a").attr("href", "#collapse_"+x);
	$("div[id^='model_']:last").find("div[id='collapse']").attr("id", "collapse_"+x);
	
	var numFlag = true;
	for (var i=0; i<jsonObj.data.length; i++) {
		var contentsHtml = "";
		var tmpObj = $("div[id^='model_']:last").find("div[id^='collapse']").find(".panel-body").find("#tmp");
		/*
		if (numFlag) { // 基数
			tmpObj = $("div[id^='model_']:last").find("div[id^='collapse']").find(".panel-body").find("#tmp");
			numFlag = false;
		} else {
			tmpObj = $("div[id^='model_']:last").find("div[id^='collapse']").find(".panel-body").find("#tmp1");
			numFlag = true;
		}
		*/
		var keyData = jsonObj.data[i];
		var keyName = keyData.keyName;
		var keyValue = keyData.midRmrk;
		var keyNo = keyData.keyNo;
		var kv = keyData.kv;
		var keyDesc = keyData.keyDesc;
		var vLen = keyData.longRmrk;
		if (i==0) {
			$("div[id^='model_']:last").find("div[id^='collapse']").find(".panel-body").find("#tmp").find("input[id='compNo']").val(keyData.compNo);
			$("div[id^='model_']:last").find("div[id^='collapse']").find(".panel-body").find("button[id^='saveBtn']").attr('id', 'saveBtn_'+keyData.compNo);
			$("div[id^='model_']:last").find("div[id^='collapse']").find(".panel-body").find("button[id^='resetBtn']").attr('id', 'resetBtn_'+keyData.compNo);
			$("#saveBtn_"+keyData.compNo).on('click', function(){
				save($(this));
			});
		}
		var keyHtml = "<label for='inputEmail3' class='col-sm-4 control-label'> "+
							keyName +
					  "</label>";
		var descHtml = "";
			descHtml = "<div class='col-md-7 column'>" +
							"<label for='inputEmail3' class='col-sm-7 control-label'> "+
								keyDesc +
							"</label>" +
					   "</div>";
//		var keyDesc = "";
		
		if (keyData.keyTp == "02") { // 通用属性
			if (keyData.shortRmrk == "01") { // 输入框
				keyHtml +=
				  "<div class='col-sm-5'ravo='rainbow_fx'>" +
				  		"<input type='text' class='form-control' placeholder='' id='kv_"+keyNo+"' name='"+keyNo+"' value='"+kv+"' check-minlength='1' maxlength='"+vLen+"' />" +
				  "</div>";
				
				contentsHtml = startHtml + keyHtml + endHtml + descHtml + endHtml + endHtml;
				
				tmpObj.after(contentsHtml);
				
			} else {
				
				keyHtml += "<select data-role='multiselect' id='kv_"+keyNo+"' class='' name='"+keyNo+"'></select>";
				contentsHtml = startHtml + keyHtml + endHtml + descHtml + endHtml + endHtml;
				tmpObj.after(contentsHtml);
				
				var defaultKv = keyValue.split(";@;");
				var datas = [];
				for (var j=0;j<defaultKv.length;j++){
					var tmp = defaultKv[j];
					if (tmp.indexOf("!@#") != -1){
						datas.push({
				            label: tmp.split("!@#")[0],
				            value: tmp.split("!@#")[1]
				        });
					} else {
						datas.push({
				            label: tmp,
				            value: tmp
				        });
					}
				}
				var $select = $("div[id='"+modelId+"']").find("div[id^='collapse']").find(".panel-body").find("select[id='kv_"+keyNo+"']");
				if(keyData.shortRmrk == "03"){
					$select.attr('multiple', "multiple");
				}
				$select.multiselect('dataprovider', datas).multiselect('rebuild').multiselect('refresh');
				$select.multiselect('select', kv).multiselect('refresh');
			}
		} else if (keyData.keyTp == "03"){ // 关联属性
			var id = "kv_"+keyNo;
			var url = keyData.kv.indexOf("http://") != -1 ? keyData.kv : ctx + keyData.kv;
			keyHtml +=
					  "<div class='col-sm-4'>" +
					  		"<a href='javascript:;' class='btn btn-xs btn-default' data-toggle='tooltip' data-placement='top' id='kv_"+keyNo+"' name='"+keyNo+"' onclick=\"jump('"+id+"')\">" +
			  						"<span class='glyphicon glyphicon-share' style='margin-top:5px; width:100px'>跳转</span>" +
			  						"<input id='url' value='"+url+"' type='hidden'>" +
		  					"</a>"+
					  "</div>";
			contentsHtml = startHtml + keyHtml + endHtml + descHtml + endHtml + endHtml;
			
			tmpObj.after(contentsHtml);
		}
	}
}


function save (obj) {
	var json = ""
	var parentDiv = obj.parents("div[id^='collapse_']");
	var compNo = parentDiv.find("input[id='compNo']").val();
	var collapseId = parentDiv.attr("id");
	var _input = $("#"+collapseId).find("input[id^='kv_']");
	// var tipVal = tipVal+'请输入'+_min+"至"+_maxlength+"长度之间";
	var inputIsNull = false;
	_input.each(function(){
		var _min = $(this).attr("check-minlength");
		var _maxlength = $(this).attr("maxlength");
		
		var inputId = $(this).attr("id");
		if ($(this).val() == "") {
			inputIsNull = true;
			var tipVal = '请输入'+_min+"至"+_maxlength+"长度之间";
			lengtPrompt (tipVal , collapseId, inputId);
		} else {
			$("#"+ collapseId).find("input[id='"+inputId+"']").parent().next("span.warnBlock").remove();
		}
		
	});
	
	if (!inputIsNull) {
		
		var _children = parentDiv.find("input[id^='kv_'], select[id^='kv_']");
		_children.each(function(){
			$this = $(this);
			var content = "{\"compNo\""+":\""+compNo+"\",\"keyNo\":\""+$this.attr('name')+"\",\"kv\":\""+$this.val()+"\"}";
			json += content + ",";
		});
		
		var _childrenA = parentDiv.find("a[id^='kv_']");
		_childrenA.each(function(){
			$this = $(this);
			var content = "{\"compNo\""+":\""+compNo+"\",\"keyNo\":\""+$this.attr('name')+"\",\"kv\":\""+$this.attr('href')+"\"}";
			json += content + ",";
		});
		
		
		var contents = "[" + json.substring(0, json.length-1) + "]";
		
//		console.log(contents);
		
		$.post(ctx + "/prod/om/freshPara/saveParaConfInfo", {contents : contents},
			function(data){ 
				if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
				    var errMsg = "错误信息["+data.message+"]"; 
					showTip(errMsg,"error");
					return '0';
				}else if(data.msg_type == "success"){
					var successMsg = "技术参数生效["+data.message+"]"; 
					showTip(successMsg,"success");
				}
		}, "json");
		
		
	}
	
}


function lengtPrompt (tipVal , collapseId, inputId){
	var script = tipVal;
	$("#"+ collapseId).find("input[id='"+inputId+"']").parent().next("span.warnBlock").remove();
	if($("#"+ collapseId).find("input[id='"+inputId+"']").parent().next("span.warnBlock").length == 0){
		var tag= "<span class='warnBlock'>"+script+"</span>";
		$("#"+ collapseId).find("input[id='"+inputId+"']").parent().after(tag);
		$("#"+ collapseId).find("input[id='"+inputId+"']").removeClass('trueInput');
	};
}


/*跳转明细页面*/
function jump(aId){
	console.info(aId);
	var url = $("a[id='"+aId+"']").find("input[id=url]").val();
	console.info("open detail tab:"+ url);
	if (url.indexOf("?id") != -1) {
		var id = url.substring("=");
		$.session.set('id',id);
	}
	parent.window.$("a[href^='#tab_add']").attr("url", url);
	parent.window.$("a[href^='#tab_add']").click();
}


/*改变内容高度*/
function chgHeight(h){
	var Height =$(document.body).height() + h;
	$(window.parent.document).find("#tab_list").find('iframe').height(Height);
}

/*恢复原高度*/
function resetHeight(){
	$(window.parent.document).find("#tab_add").find('iframe').height(1000);
}
