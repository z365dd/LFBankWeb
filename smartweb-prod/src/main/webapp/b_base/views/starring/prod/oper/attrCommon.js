console.log('attrCommon.js');



/*获取详细数据,打开tab2传入数据*/
//属性，详细/修改，加载div，选择内容是否可以修改(true可修改)，选择设置根据此值设置
function getDetail(KEY_NO,tp,commonDiv,ifSelect,ENUM_VAL_STR){
	$.ajax({
		url:ctx + "/prod/oper/prodAttrDict/getDetail",
		type:"GET",
		dataType:"json",
		data:{
			KEY_NO:KEY_NO
		},
		async:false,
		success:function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data);
				var successMsg = "获取数据["+data.message+"]"; 
				showTip(successMsg,"success");
				
				var Data = data.dataSetResult[0].data[0];
				// Data = JSON.parse(Data);
				if(commonDiv!=undefined){
					ifr(commonDiv,'prod/oper/prodAttrDict/prodAttrDictForm',600);
					$("#"+commonDiv).find('iframe').load(function() {
						$("#"+commonDiv).find('iframe')[0].contentWindow.setData(Data,tp,ifSelect,ENUM_VAL_STR);
					});
				}else{
					$('#tab2').click();
					if(tp=="detail"){
						$('#tab2').text('属性详细');
						SAVE_OR_REV = "detail";
					}else if(tp=="revice"){
						$('#tab2').text('属性修改');
						SAVE_OR_REV = "revice";
					}
					$("#panel2").find('iframe').load(function() {
						$("#panel2").find('iframe')[0].contentWindow.setData(Data,tp);
					});
				}
				
			}
		}
	});
}

//控件类型转换
function changeKeyTp(keyTp){
	var keyTpC = "";
	if(keyTp=="01"){
		keyTpC="输入框";
	}else if(keyTp=="02"){
		keyTpC="下拉框";
	}else if(keyTp=="03"){
		keyTpC="单选框";
	}else if(keyTp=="04"){
		keyTpC="多选框";
	}else if(keyTp=="05"){
		keyTpC="日期时间组件";
	}
	return keyTpC;
}

//获取预览数据，tp判断不同操作
//1，打开tab3预览加载页面
//2，获取数据返回数据，改成同步
function getPerData(SALE_PROD_CODE,tp){
	var ifAsync = true;
	if(tp=="2"){
		ifAsync=false;
		var returnArr=[];
	}
	$.ajax({
		url:ctx+"/prod/oper/prodSalePage/perPage",
		type:"POST",
		dataType:"json",
		data:{
			SALE_PROD_CODE:SALE_PROD_CODE
		},
		async:ifAsync,
		success:function(data) {
			console.log("----------查询可售产品包装的属性----------"+SALE_PROD_CODE);
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var successMsg = "获取页面数据["+data.message+"]"; 
				showContent(successMsg,"success");
				
				var Data = data.dataSetResult[0].data[0];
				if(Data.FProdSaleProdPageParaViewRes!="" && Data.FProdSaleProdPageParaViewRes!=undefined){
					Data = JSON.parse(Data.FProdSaleProdPageParaViewRes);
				}else{
					Data = {};
				}
				
				
				var arr=[];
				if(Data.LIST!=undefined){
					arr = Data.LIST;
				}
				
				if(tp=="1"){
					$("#tab3").show().click();
					goTop();
					ifr('panel3','prod/oper/prodSalePage/prodSalePagePerForm',600);
					$("#panel3").find('iframe').on("load",function(){
						$("#panel3").find('iframe')[0].contentWindow.setPerPage(arr,"formId_286247");
					});
				}else if(tp=="2"){
					returnArr= arr;
				}
			}
		}
	});
	
	return returnArr;
}

//根据预览数据生成各块的html页面
function createPage(arr,obj){
	//存储原子产品信息key-原子产品名称+"!@#"+编码，val为json（已配置行列信息，待合并信息，未配置行列信息）
	var atomData={};
	//最大列数列，用于判断整个页面的格式
	var maxCol = 0;
	//玄幻所有数据处理成atomData存储
	for(var a=0;a<arr.length;a++){
		//第一层原子产品
		var data = arr[a];
		//键list
		var keyList = data.KEY_LIST;
		console.log("------keyList.length------"+keyList.length);
		//原子产品名称
		var atomProdDesc = data.ATOM_PROD_DESC;
		//原子产品编码
		var atomProdCode = data.ATOM_PROD_CODE;
		//行arr，里面放入每一行的数据（数据以arr显示每一列），数组位置即为第几行（可能为undefined）
		var lineArrRes = [];
		//待合并属性信息数组
		var waitMergeArr = [];
		//未配置行列信息数组
		var noLineArr = [];
		if(keyList!=undefined){
			for(var b=0;b<keyList.length;b++){// 24
				console.log("------keyList[b]------"+keyList[b]);
				//页面配置数据
				var keyData = keyList[b];
				//行
				var line  = keyData.LINE_SER;
				//列
				var col = keyData.COL_SER;
				//键标志
				var keyFlg = keyData.KEY_FLG;
				//列arr，放同行中同列的数据
				var colArr = [];
				//往行总arr中加入行列数据
				if(col!=undefined && line!=undefined){
					if(keyFlg=="01"){
						//信息待合并，加入到待合并数组
						waitMergeArr.push(keyData);
					}else{
						//已配置过合并或者不需要合并
						if(col==0 || line==0){
							//行列信息配置不完整，加入未配置行列信息数组
							noLineArr.push(keyData);
						}else{
							//行列信息完整，进行行列信息数据处理
							//判断页面最大列
							if(col>maxCol){
								maxCol = col;
							}
							maxCol = col;
							//数组从0开始
							line-=1;
							col-=1;
							if(lineArrRes[line]==undefined){
								//确定增加第line行数据arr
								lineArrRes[line] = [];
							}
							if(lineArrRes[line][col]==undefined){
								//确定增加第line行第col列数据arr
								lineArrRes[line][col] = [];
							}
							//把配置信息加入到信息配置数组
							lineArrRes[line][col].push(keyData);
						}
					}
				}else{
					//TODO
				}
			}
		}
		//加入存储原子产品预览信息
		atomData[atomProdDesc+"!@#"+atomProdCode] = {
				//行列信息数组
				lineArrRes:lineArrRes,
				//待合并信息数组
				waitMergeArr:waitMergeArr,
				//没行列信息数据数组
				noLineArr:noLineArr
		};
	}
	//每列占位（根据最大列数确定）
	if(maxCol==0){
		//全部未配置
		maxCol = 2;
	}
	//var colLength = 12/maxCol;
	var colLength = 0.99999999;
	
	//根据处理好的数据生成页面
	for(atomKey in atomData){	
		//当前原子产品名称
		var atomProdDesc = atomKey.split('!@#')[0];
		//页面分块主题结构
		var titleHtml = `<div ravo="rainbow_fx_layout_panel" class="panel panel-default">
							<div class="panel-heading">
								<div ravo="rainbow_fx_bj">
									<h4 contenteditable="false">`+atomProdDesc+`</h4>
								</div>
							</div>
							<div class="panel-body" contenteditable="false">
							</div>
						</div>`;
		var $titleHtml = $(titleHtml);
		//原子产品行列信息数组
		var lineArr = atomData[atomKey]['lineArrRes'];
		//待合并信息数组
		var waitMergeArr = atomData[atomKey]['waitMergeArr'];
		//没行列数据信息数组
		var noLineArr = atomData[atomKey]['noLineArr'];
		//总行数
		var lineNum = lineArr.length;
		//行列信息操作，循环每一行数据（每一行数据是个arr）
		for(var c=0;c<lineNum;c++){
			//每行数据数组
			var lineDataArr = lineArr[c];
			//行基础结构
			var lineHtml =`<div ravo="rainbow_fx_layout" class="row clearfix">
				</div>` ;
			var $lineHtml = $(lineHtml);
			if(lineDataArr!=undefined){
				//行中列数（按整个页面最大列数确定）
				//var colNum = lineDataArr.length;
				//循环最大列数，取列数据对应的值组成列
				for(var d=0;d<maxCol;d++){
					//行中每列数据（数组）
					var colDataArr = lineDataArr[d];
					//列模板
					var colHtml = `<div class="col-md-`+colLength+` column"></div>`;
					var $colHtml = $(colHtml);
					if(colDataArr!=undefined){
						//当前列有数据
						//当前列元素个数
						var eleNum = colDataArr.length;
						//同一列的数据
						for(var l=0;l<eleNum;l++){
							//每一个列元素页面数据
							var colData = colDataArr[l];
							//生成页面加入此列
							$colHtml.append(createPageHtml(colData));
						}
					}else{
						//当列不存在，取其他数据占位
						if(noLineArr.length>0){
							//未配置列存在取第一个
							var colData = noLineArr[0];
							//生成页面加入此列
							$colHtml.append(createPageHtml(colData));
							//删除未配置数组第一个
							noLineArr.shift();
						}
					}
					//行中加入列
					$lineHtml.append($colHtml);
				}
			}else{
				//TODO
				//当前行没有数据，此行不操作
			}
			//动态内容区加入行
			$titleHtml.find(".panel-body").append($lineHtml);
		}
		//未被使用的未配置元素操作
		while(noLineArr.length>0){
			//行基础结构
			var lineHtml =`<div ravo="rainbow_fx_layout" class="row clearfix">
				</div>` ;
			var $lineHtml = $(lineHtml);
			for(var n=0;n<maxCol;n++){
				//未配置行列数据第一条
				var noLineData = noLineArr[0];
				//列模板
				var colHtml = `<div class="col-md-`+colLength+` column"></div>`;
				var $colHtml = $(colHtml);
				//生成页面加入此列
				$colHtml.append(createPageHtml(noLineData));
				//行中加入列
				$lineHtml.append($colHtml);
				//删除未配置数组第一个
				noLineArr.shift();
				if(noLineArr.length==0){
					break;
				}
			} 
			//动态内容区加入行
			$titleHtml.find(".panel-body").append($lineHtml);
		}
		//未作是否合并操作的数据
		while(waitMergeArr.length>0){
			//行基础结构
			var lineHtml =`<div ravo="rainbow_fx_layout" class="row clearfix">
				</div>` ;
			var $lineHtml = $(lineHtml);
			for(var n=0;n<maxCol;n++){
				//未配置行列数据第一条
				var waitMergeData = waitMergeArr[0];
				var keyName = waitMergeData.KEY_NAME;
				//列模板
				var colHtml = `<div class="col-md-`+colLength+` column"></div>`;
				var $colHtml = $(colHtml);
				//未配置内容提示
				var waitMergeHtml = `<div ravo="rainbow_fx" class="form-group">
						<label class="col-sm-4 control-label">
							`+keyName+`</label>
						<label class="col-sm-4 control-label" style="
							text-align: left;color: red;">
							未确认是否合并！
						</label>
					</div>`;
				//生成页面加入此列
				$colHtml.append(waitMergeHtml);
				//行中加入列
				$lineHtml.append($colHtml);
				//删除未配置数组第一个
				waitMergeArr.shift();
				if(waitMergeArr.length==0){
					break;
				}
			} 
			//动态内容区加入行
			$titleHtml.find(".panel-body").append($lineHtml);
		}
		
		//进行初始化下拉框插件的操作
		$titleHtml.find("select").multiselect('destroy').multiselect('rebuild').multiselect('refresh');
		
		//往页面加入此块动态界面
		obj.append($titleHtml);
	}
	
}

//根据页面配置生成页面数据对象
function createPageHtml(colData){
	//左占
	/*var leftCol;
	//右占
	var rightCol;
	if(maxCol==0){
		//全都未配置行列信息，默认分两列
		leftCol =  
	}*/
	//长度
	var valLen = (colData.VAL_LEN>0)?("maxlength=\""+colData.VAL_LEN+"\""):"";
	//隐藏标志
	var flg = (colData.FLG=="Y")?"hide":"show";
	//输入标志
	var inputFlg  =(colData.INPUT_FLG=='01')?"true":"false";
	//键
	var keyNo = colData.KEY_NO.replace(/[\.]/g,"-_-");
	//名
	var keyName  =colData.KEY_NAME;
	//控制属性列表
	var ctrlList  =colData.CTRL_LIST;
	//键类型  colData.defv_val
	var keyTp = colData.KEY_TP;
	if(keyTp=="01"){
		//TODO 输入框
		var inputHtml = `<div ravo="rainbow_fx" class="form-group `+flg+`">
						<label class="col-sm-4 control-label">
							`+keyName+`</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" placeholder=""
								data-bv-="true" id="`+keyNo+`" name="`+keyNo+`" check-empty="`+inputFlg+`" `+valLen+`>
						</div>
					</div>`;
		var $inputHtml = $(inputHtml);
		if(colData.FLG=="Y") {
			$("#"+keyNo).val(colData.DEFA_KV);
		}else{
			if(ctrlList!=undefined){
				for (var e=0;e<ctrlList.length;e++){
					var ctrlData = ctrlList[e];
					if(ctrlData.KEY_NO=="text.check"){
						//校验
						// var checkArr = ctrlData.KV.split(";");
						// for(var f=0;f<checkArr.length;f++){
						// 	var checkTp = checkArr[f];
						// 	$inputHtml.find("input").attr("check-"+checkTp,"true");
						// }
					}else if(ctrlData.KEY_NO=="text.len"){
						//TODO 长度？
					}else if(ctrlData.KEY_NO=="text.note"){
						//提示
						$inputHtml.find("input").attr("placeholder",ctrlData.KV);
					}
				}
			}
		}
		//$colHtml.append($inputHtml);
		return $inputHtml;
	}else if(keyTp=="02"){
		//TODO 下拉框
		inputFlg = (inputFlg=="true")?("checkbtn=\""+keyNo+"\""):"";
		var selectHtml = `<div ravo="rainbow_fx" class="form-group `+flg+`">
					<label class="col-sm-4 control-label">
						`+keyName+`</label>
					<div class="col-sm-7">
						<select id="`+keyNo+`" class=""
							name="`+keyNo+`" `+inputFlg+`>
						</select>
					</div>
				</div>`;
		var $selectHtml = $(selectHtml);
		if(colData.FLG=="Y") {
			$("#"+keyNo).val(colData.DEFA_KV);
		}else{
			//var ctrlList  =colData.CTRL_LIST;
			if(ctrlList!=undefined){
				var ifMultiple = false;
				var kvArr = [];
				for (var e=0;e<ctrlList.length;e++){
					var ctrlData = ctrlList[e];
					if(ctrlData.KEY_NO=="select.kv"){
						//下拉框键值
						kvArr = ctrlData.KV.split(";@;");
					}else if(ctrlData.KEY_NO=="if.multiple"){
						//下拉设置（单复选）
						if(ctrlData.KV=="Y"){
							//复选
							ifMultiple = true;
						}else{
							//单选
							$selectHtml.find("select").prepend('<option value="">请选择</option>');
						}
					}
				}
				//设置单选复选
//				$selectHtml.find("select").attr("data-include-select-all-option",ifMultiple);
//				$selectHtml.find("select").attr("multiple",ifMultiple);
				//加入选项
				for(var f=0;f<kvArr.length;f++){
					var keyValArr = kvArr[f].split("!@#");
					var optStr  =`<option value="`+keyValArr[1]+`">`+keyValArr[0]+`</option>`;
					$selectHtml.find("select").append(optStr);
				}
			}
		}
		
		//$colHtml.append($selectHtml);
		return $selectHtml;
	}else if(keyTp=="03"){
		/*20200119 modify by zengxj 控件类型03改为 多选下拉框 */

		//TODO 下拉框
		inputFlg = (inputFlg=="true")?("checkbtn=\""+keyNo+"\""):"";
		var selectHtml = `<div ravo="rainbow_fx" class="form-group `+flg+`">
					<label class="col-sm-4 control-label">
						`+keyName+`</label>
					<div class="col-sm-7">
						<select id="`+keyNo+`" class=""
							name="`+keyNo+`" `+inputFlg+`>
						</select>
					</div>
				</div>`;
		var $selectHtml = $(selectHtml);
		if(colData.FLG=="Y") {
			$("#"+keyNo).val(colData.DEFA_KV);
		}else{
			if(ctrlList!=undefined){
				var ifMultiple = false;
				var kvArr = [];
				for (var e=0;e<ctrlList.length;e++){
					var ctrlData = ctrlList[e];
					if(ctrlData.KEY_NO=="checkbox.kv"){
						//下拉框键值
						kvArr = ctrlData.KV.split(";@;");
					}else if(ctrlData.KEY_NO=="if.multiple"){
						//下拉设置（单复选）
						if(ctrlData.KV=="Y"){
							//复选
							ifMultiple = true;
						}else{
							//单选
							$selectHtml.find("select").prepend('<option value="">请选择</option>');
						}
					}
				}
				//设置单选复选
//				$selectHtml.find("select").prepend('<option value="">请选择</option>');
				//data-select-all-text="全部选择" 
				$selectHtml.find("select").attr("data-select-all-text", "全部选择");
				$selectHtml.find("select").attr("data-non-selected-text", "请选择");
				
				$selectHtml.find("select").attr("data-n-selected-text", "个已选");
				$selectHtml.find("select").attr("data-number-displayed", "5");
				$selectHtml.find("select").attr("data-include-select-all-option", true);
				$selectHtml.find("select").attr("multiple","multiple");
				//加入选项
				for(var f=0;f<kvArr.length;f++){
					var keyValArr = kvArr[f].split("!@#");
					var optStr  =`<option value="`+keyValArr[1]+`">`+keyValArr[0]+`</option>`;
					$selectHtml.find("select").append(optStr);
				}
			}
		}
		//var ctrlList  =colData.CTRL_LIST;
		
		//$colHtml.append($selectHtml);
		return $selectHtml;
		
		/* 
		//TODO 单选框（按钮）
		inputFlg = (inputFlg=="true")?("checkbtn=\""+keyNo+"\""):"";
		var radioHtml = `<div ravo="rainbow_fx_radio" class="form-group `+flg+`">
					<label class="col-sm-4 control-label">`+keyName+`</label>
					<div class="col-sm-7">
					</div>
				</div>`;
		var $radioHtml = $(radioHtml);
		//var ctrlList  =colData.CTRL_LIST;
		if(ctrlList!=undefined){
			//键值字符串
			var kvArr =[];
			//排列方式
			var arrange = "";
			for (var e=0;e<ctrlList.length;e++){
				var ctrlData = ctrlList[e];
				if(ctrlData.KEY_NO=="radio.kv"){
					//单选按钮键值
					kvArr = ctrlData.KV.split(";@;");
				}else if(ctrlData.KEY_NO=="radio.arrange"){
					//默认横排列
					arrange = (ctrlData.KV=="horizontal")?"radio":"radio-inline";
				}
			}
			if(kvArr!=undefined){
				for(var f=0;f<kvArr.length;f++){
					var keyValArr = kvArr[f].split("!@#");
					var radioStr  =`<div class="`+arrange+`">
							<label style="visibility: visible"> <input
								type="radio" value="`+keyValArr[1]+`"
								name="`+keyNo+`" class=""
								`+inputFlg+`>`+keyValArr[0]+`
							</label>
						</div>`;
					$radioHtml.find(".col-sm-7").append(radioStr);
				}
			}
		}
		//$colHtml.append($radioHtml);
		return $radioHtml;
		*/
	}else if(keyTp=="04"){
		/*20200119 modify by zengxj 控件类型03改为 多选下拉框 */
		/* 
		//TODO 多选框（按钮）
		inputFlg = (inputFlg=="true")?("checkbtn=\""+keyNo+"\""):"";
		var checkboxHtml = `<div ravo="rainbow_fx_checkbox" class="form-group `+flg+`">
					<label class="col-sm-4 control-label">`+keyName+`</label>
					<div class="col-sm-7">
					</div>
				</div>`;
		var $checkboxHtml = $(checkboxHtml);
		//var ctrlList  =colData.CTRL_LIST;
		if(ctrlList!=undefined){
			//键值字符串
			var kvArr =[];
			//排列方式
			var arrange = "";
			for (var e=0;e<ctrlList.length;e++){
				var ctrlData = ctrlList[e];
				if(ctrlData.KEY_NO=="checkbox.kv"){
					//复选按钮键值
					kvArr = ctrlData.KV.split(";@;");
				}else if(ctrlData.KEY_NO=="checkbox.arrange"){
					//默认横排列
					arrange = (ctrlData.KV=="horizontal")?"checkbox":"checkbox-inline";
				}
			}
			if(kvArr!=undefined){
				for(var f=0;f<kvArr.length;f++){
					var keyValArr = kvArr[f].split("!@#");
					var checkboxStr  =`<div class="`+arrange+`">
							<label style="visibility: visible"> <input
								type="checkbox" value="`+keyValArr[1]+`"
								name="`+keyNo+`" class=""
								`+inputFlg+`>`+keyValArr[0]+`
							</label>
						</div>`;
					$checkboxHtml.find(".col-sm-7").append(checkboxStr);
				}
			}
		}
		//$colHtml.append($checkboxHtml);
		return $checkboxHtml;
		*/
		
	}else if(keyTp=="05"){
		//TODO 日期时间组件
		var timeHtml = `<div ravo="rainbow_fx" class="form-group `+flg+`">
					<label class="control-label col-sm-4 control-label">
						`+keyName+`</label>
					<div class="input-group-sm col-sm-4">
						<input type="text" readonly="readonly" maxlength="20"
							class="form-control input-mini Wdate" value=""
							datetime-skin="twoer"
							datetime-min-date="" datetime-max-date=""
							datetime-is-show-clear="true" datetime-is-show-week="true"
							datetime-is-show-today="true" datetime-default-value=""
							data-link-field="val_`+keyNo+`"
							id="`+keyNo+`"
							onchange="writeDateValue('`+keyNo+`');" `+inputFlg+`>
						<input type="hidden" id="val_`+keyNo+`"
							value="" name="`+keyNo+`">
					</div>
				</div>`;
		var $timeHtml = $(timeHtml);
		//var ctrlList  =colData.CTRL_LIST;
		if(ctrlList!=undefined){
			inputFlg = (inputFlg=="true")?("check-time-empty=\"true\""):"";
			//显示格式
			var dataFmt ="";
			//值格式
			var valueFmt = "";
			for (var e=0;e<ctrlList.length;e++){
				var ctrlData = ctrlList[e];
				if(ctrlData.KEY_NO=="datetime.datafmt"){
					//显示格式
					dataFmt = changeTimeTp(ctrlData.KV);
				}else if(ctrlData.KEY_NO=="datetime.valuefmt"){
					//值格式
					valueFmt = changeTimeTp(ctrlData.KV);
				}
			}
			$timeHtml.find("[type='text']").attr('datetime-date-fmt',dataFmt);
			$timeHtml.find("[type='text']").attr('datetime-value-fmt',valueFmt);
			$timeHtml.find("[type='text']").attr('onclick',`WdatePicker({skin: 'twoer',dateFmt:'`+dataFmt+`',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('`+keyNo+`');}});`);
		}
		//$colHtml.append($timeHtml);
		return $timeHtml;
	}
}

//日期转换
function changeTimeTp(tp){
	if(tp=="YYYY-MM-DD"){
		return "yyyy-MM-dd";
	}else if(tp=="DD-MM-YYYY"){
		return "dd-MM-yyyy";
	}else if(tp=="YYYY-MM-DD HH:mm:ss"){
		return "yyyy-MM-dd HH:mm:ss";
	}else if(tp=="YYYYMMDD"){
		return "yyyyMMdd";
	}else if(tp=="DDMMYYYY"){
		return "ddMMyyyy";
	}else if(tp=="YYYYMMDDHHmmss"){
		return "yyyyMMddHHmmss";
	}else{
		return tp;
	}
}

