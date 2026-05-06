console.log('prodSalePagePerForm.js');


$(function(){
	$("#addBtn").click(function(){
		if(proof()){
			console.log("提交成功");
			console.log(getI("keyT"));
		}
	});
});

//生成预览界面
function setPerPage(data,Id){
	//根据
	var setInput  = `<div ravo="rainbow_fx" class="form-group">
												<label for="inputEmail3" class="col-sm-3 control-label">
													单位证件号码</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" placeholder=""
														data-bv-="true" id="ENTR_CERT_NO" name="ENTR_CERT_NO"
														check-empty="true">
												</div>
											</div>`;
	var setSelect  = `<div ravo="rainbow_fx" class="form-group show">
											<label for="inputEmail3"
												class="col-sm-4 control-label control-label">
												允许缴费的账户类型 </label>
											<div class="col-sm-4">
												<select
													id="999301_Pay_Handle_ChkAcctType" class=""
													name="999301_Pay_Handle_ChkAcctType" multiple="multiple"
													data-include-select-all-option="true" >
													<option value="01">对公</option>
													<option value="02">对私</option>
													<option value="11">内部帐</option>
													<option value="21">借记卡</option>
													<option value="22">信用卡</option>
												</select>
											</div>
										</div>`;
	var setRadio = `<div ravo="rainbow_fx_radio" class="form-group">
											<label for="inputEmail3"
												class=" control-label  col-sm-5 control-label"
												style="visibility: visible">缴费客户签约 </label>
											<div class="col-sm-7">
												<div class="radio-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio0" type="radio" value="Y"
														name="999301_Busi_ChkCustSign" class=""
														checkbtn="999301_Busi_ChkCustSign">是
													</label>
												</div>
												<div class="radio-inline">
													<label style="visibility: visible"> <input
														id="optionsRadio1" type="radio" value="N"
														name="999301_Busi_ChkCustSign" class=""
														checkbtn="999301_Busi_ChkCustSign">否
													</label>
												</div>
											</div>
										</div>`;
	var setCheckbox = `<div ravo="rainbow_fx_checkbox" class="form-group">
											<label for="inputEmail3"
												class=" control-label  col-sm-5 control-label"
												style="visibility: visible">开通渠道</label>
											<div class="col-sm-6">
												<div class="checkbox">
													<label style="visibility: visible"> <input
														id="optionsRadio0" type="checkbox" value="0"
														name="999322_Busi_Pub_TranChnl" class=""
														checkbtn="999322_Busi_Pub_TranChnl">柜面
													</label>
												</div>
												<div class="checkbox">
													<label style="visibility: visible"> <input
														id="optionsRadio1" type="checkbox" value="0"
														name="999322_Busi_Pub_TranChnl" class=""
														checkbtn="999322_Busi_Pub_TranChnl">网银
													</label>
												</div>
												<div class="checkbox">
													<label style="visibility: visible"> <input
														id="optionsRadio1" type="checkbox" value="0"
														name="999322_Busi_Pub_TranChnl" class=""
														checkbtn="999322_Busi_Pub_TranChnl">第三方
													</label>
												</div>
											</div>
										</div>`;
	var setTime = `<div ravo="rainbow_fx" class="form-group">
											<label class="control-label col-sm-5 control-label">
												发起时间（起始） </label>
											<div class="input-group-sm  col-sm-4">
												<input type="text" readonly="readonly" maxlength="20"
													class="form-control input-mini Wdate" value=""
													datetime-skin="twoer" datetime-date-fmt="HH:mm:ss"
													datetime-min-date="" datetime-max-date=""
													datetime-is-show-clear="true" datetime-is-show-week="true"
													datetime-is-show-today="true" datetime-default-value=""
													datetime-value-fmt="HHmmss"
													data-link-field="val_999322_Busi_Pub_SndStrTime"
													id="999322_Busi_Pub_SndStrTime"
													onclick="WdatePicker({skin: 'twoer',dateFmt: 'HH:mm:ss',minDate: '1900-01-01 00:00:00',maxDate: '2099-12-31 23:59:59',isShowClear: true,isShowWeek: true,isShowToday: true,onpicked:function(dp){	var newVal = dp.cal.getNewDateStr();  console.info(newVal);	writeDateValue('999322_Busi_Pub_SndStrTime');}});"
													onchange="writeDateValue('999322_Busi_Pub_SndStrTime');">
												<input type="hidden" id="val_999322_Busi_Pub_SndStrTime"
													value="" name="999322_Busi_Pub_SndStrTime">
											</div>
										</div>`;
	/*$("#formId_286247").append(setInput).append(setSelect).append(setRadio).append(setCheckbox).append(setTime).find("select").multiselect('destroy').multiselect('rebuild')
	.multiselect('refresh');*/
	
	//可在数组指定位置插入，无内容取出为undefined
	/*var a=[];
	a[1]=1;
	console.log(a);
	console.log(a[0]);
	console.log(a.length);*/
	//html取出对象内容
	/*console.log($("#formId_286247").html());*/
	
	var arr = [
		{
			ATOM_PROD_CODE:"test",
			ATOM_PROD_DESC:"测试",
			KEY_LIST:[
				{
					KEY_NO:"key",
					KEY_NAME:"键",
					LINE_SER:"1",
					COL_SER:"1",
					FLG:"N",
					VAL_LEN:"11",
					KEY_TP:"01",
					INPUT_FLG:"01",
					CTRL_LIST:[
						{
							KEY_NO:"text.note",
							KV:"提示"
						},
						{
							KEY_NO:"text.check",
							KV:"telephone;integer-pos-neg"
						}
					],
				},
				{
					KEY_NO:"keyS",
					KEY_NAME:"键S",
					LINE_SER:"1",
					COL_SER:"2",
					FLG:"N",
					VAL_LEN:"6",
					KEY_TP:"02",
					INPUT_FLG:"01",
					CTRL_LIST:[
						{
							KEY_NO:"select.kv",
							KV:"测试1-test1;测试2-test2"
						}
					],
				},
				{
					KEY_NO:"keyR",
					KEY_NAME:"键R",
					LINE_SER:"1",
					COL_SER:"1",
					FLG:"N",
					VAL_LEN:"6",
					KEY_TP:"03",
					INPUT_FLG:"01",
					CTRL_LIST:[
						{
							KEY_NO:"radio.kv",
							KV:"测试1-test1;测试2-test2"
						},
						{
							KEY_NO:"radio.arrange",
							KV:"inline"
						}
					],
				},
				{
					KEY_NO:"keyC",
					KEY_NAME:"键C",
					LINE_SER:"1",
					COL_SER:"2",
					FLG:"N",
					VAL_LEN:"6",
					KEY_TP:"04",
					INPUT_FLG:"01",
					CTRL_LIST:[
						{
							KEY_NO:"checkbox.kv",
							KV:"测试1-test1;测试2-test2"
						},
						{
							KEY_NO:"checkbox.arrange",
							KV:"horizontal"
						}
					],
				},
				{
					KEY_NO:"keyT",
					KEY_NAME:"时间",
					LINE_SER:"2",
					COL_SER:"2",
					FLG:"N",
					VAL_LEN:"6",
					KEY_TP:"05",
					INPUT_FLG:"01",
					CTRL_LIST:[
						{
							KEY_NO:"datetime.datafmt",
							KV:"yyyy-MM-dd"
						},
						{
							KEY_NO:"datetime.valuefmt",
							KV:"yyyyMMdd"
						}
					],
				}
			]
		}
	];
	//createPage(arr,"formId_286247");
	
	createPage(data,$("#"+Id));
	ifrChildAut("panel3",100);
}



