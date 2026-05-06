<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div ravo="rainbow_fx_layout" class="row clearfix">
	<div class='col-md-12 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='busiDesc' class='col-sm-2 control-label'>备注</label>
			<div class='col-sm-4'>
				<textarea class='form-control' style='resize:none;' rows='3' name='busiDesc' id='busiDesc' check-empty='true' maxlength='360'></textarea>
			</div>
		</div>
	</div>
</div>
<div ravo="rainbow_fx_layout" class="row clearfix" style="display:none">
	<div class='col-md-6 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='openGrpChnlNo' class='col-sm-4 control-label'>业务开通渠道</label>
			<div class='col-sm-4'>
				<select data-role="multiselect" id="openGrpChnlNo" class="" name="openGrpChnlNo" check-empty="false" data-max-height="300"  data-button-width="300"
						data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true"
						data-filter-placeholder="搜索" multiple="multiple" data-select-all-text="全部选择" data-include-select-all-option="true" data-non-selected-text="请选择"
						data-all-selected-text="已选全部" data-n-selected-text="个已选"
						data-number-displayed="2" multiple="multiple">
				</select>
			</div>
		</div>
	</div>
	<div class='col-md-6 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='openGrpBrch' class='col-sm-4 control-label'>业务开通机构</label>
			<div class='col-sm-4'>
				<select data-role="multiselect" id="openGrpBrch" class="" name="openGrpBrch" check-empty=""false"" data-max-height="300"  data-button-width="300"
						data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true"
						data-filter-placeholder="搜索" multiple="multiple" data-select-all-text="全部选择" data-include-select-all-option="true" data-non-selected-text="请选择"
						data-all-selected-text="已选全部" data-n-selected-text="个已选"
						data-number-displayed="2" multiple="multiple">
				</select>
			</div>
		</div>
	</div>
</div>
<div ravo="rainbow_fx_layout" class="row clearfix">
	<div class='col-md-12 column'>
		<div ravo="rainbow_fx" class="form-group">
			<label for="brchId" class="col-sm-2 control-label">清算机构</label>
			<div class="col-sm-4">
				<sys:treeselect id="brchId" name="brchId" value="brchIdValue"
								label_name="brchIdName" label_value="" title="机构" url="/sys/office/treeData"
								allow_clear="true" css_class="form-control input-small" treesearch_required="true">
				</sys:treeselect>
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='clrTp' class='col-sm-2 control-label'>清算模式</label>
			<div class='col-sm-8'>
<%--				<select data-role="multiselect" id="clrTp" class=""--%>
<%--					name="clrTp">--%>
<%--				</select>--%>
				<input type="radio" id="clrTp" name="clrTp" value="00" checked="checked" />商户清算模式-无需清算(账务交易直接与清算账户完成账务处理)
				<br>
				<input type="radio" id="clrTp" name="clrTp" value="01" />商户清算模式-使用过渡户
				<br>
				<input type="radio" id="clrTp" name="clrTp" value="02" />其他模式(选择该选项，则需要跳转到清算组件的配置页面进行参数配置)
			</div>
		</div>
	</div>
</div>

<div ravo="rainbow_fx_layout" class="row clearfix" id="showClrTp">
	<div class="col-md-1 column"></div>
	<div class='col-md-5 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='intrmAcct' class='col-sm-4 control-label'>过渡账户</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="intrmAcct" name="intrmAcct">
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='intrmAcctName' class='col-sm-4 control-label'>过渡账户名</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="intrmAcctName" name="intrmAcctName">
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='inOutBankFlg' class='col-sm-4 control-label'>账号跨行标志</label>
			<div class='col-sm-4'>
<%--				<select data-role="multiselect" id="inOutBankFlg" class="" name="inOutBankFlg">--%>
<%--				</select>--%>
				<select data-role="multiselect" id="inOutBankFlg" name="inOutBankFlg" data-max-height="300" blank-item="true"
						data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="inOutBankFlg">
				</select>
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='intrmAcctOpenInst' class='col-sm-4 control-label'>过渡账户开户机构</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="intrmAcctOpenInst" name="intrmAcctOpenInst">
			</div>
		</div>
		<!-- <div ravo='rainbow_fx' class='form-group'>
			<label for='isClrFlg' class='col-sm-4 control-label'>是否清算</label>
			<div class='col-sm-4'>
				<select data-role="multiselect" id="isClrFlg" class="" name="isClrFlg">
				</select>
			</div>
		</div> -->
	</div>
	<div class='col-md-5 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='entrAcct' class='col-sm-4 control-label'>清算账户/客户账号</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="entrAcct" name="entrAcct" check-empty="true">
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='entrAcctName' class='col-sm-4 control-label'>清算账户名/客户账号名</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="entrAcctName" name="entrAcctName" check-empty="true">
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='entrAcctBank' class='col-sm-4 control-label'>客户账号开户机构</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="entrAcctBank" name="entrAcctBank" >
			</div>
		</div>
	</div>
</div>
<div ravo="rainbow_fx_layout" class="row clearfix">
	<div class='col-md-12 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='signPat' class='col-sm-2 control-label'>签约检查</label>
			<div class='col-sm-8'>
<%--				<select data-role="multiselect" id="signPat" class=""--%>
<%--					name="signPat">--%>
<%--				</select>--%>
				<input type="radio" id="signPat" name="signPat" value="00" checked="checked" />不检查(如果没有需要校验签约的服务，则不检查)
				<br>
				<input type="radio" id="signPat" name="signPat" value="01" />检查(支持一个业务只对应一个签约类型ID的情况)
				<br>
				<input type="radio" id="signPat" name="signPat" value="02" />其他模式(选择该选择，则需要跳转到签约组件的配置页面进行参数配置)
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='chkPat' class='col-sm-2 control-label'>对账模式</label>
			<div class='col-sm-8'>
<%--				<select data-role="multiselect" id="chkPat" class=""--%>
<%--					name="chkPat">--%>
<%--				</select>--%>
				<input type="radio" id="chkPat" name="chkPat" value="00" checked="checked" />不对账
				<br>
				<input type="radio" id="chkPat" name="chkPat" value="01" />两方对账(使用对应模型默认的对账参数进行对账处理，该参数作为技术参数，在模型生效时配置)
				<br>
				<input type="radio" id="chkPat" name="chkPat" value="02" />三方对账(需要跳转到三方对账的配置页面，进行三方对账参数配置)
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='clrFeeTp' class='col-sm-2 control-label'>清算手续费模式</label>
			<div class='col-sm-8'>
<%--				<select data-role="multiselect" id="clrFeeTp" class=""--%>
<%--					name="clrFeeTp">--%>
<%--				</select>--%>
				<input type="radio" id="clrFeeTp" name="clrFeeTp" value="00" checked="checked" />商户清算模式-不收取手续费
				<br>
				<input type="radio" id="clrFeeTp" name="clrFeeTp" value="01" />商户清算模式-按成功金额比例收取
				<br>
				<input type="radio" id="clrFeeTp" name="clrFeeTp" value="02" />商户清算模式-按成功笔数收取
				<br>
				<input type="radio" id="clrFeeTp" name="clrFeeTp" value="03" />其他模式
			</div>
		</div>
	</div>
</div>
<div ravo="rainbow_fx_layout" class="row clearfix hide" id="showClrFeeTp">
	<div class="col-md-2 column"></div>
	<div class='col-md-5 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='defFrt' class='col-sm-4 control-label'>手续费费率</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="defFrt" name="defFrt" >
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='feeTfOutAcct' class='col-sm-4 control-label'>手续费付款账号</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="feeTfOutAcct" name="feeTfOutAcct" >
			</div>
		</div>
	</div>
	
	<div class='col-md-5 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='amt' class='col-sm-4 control-label'>每笔金额</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="amt" name="amt" >
			</div>
		</div>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='feeTfInAcct' class='col-sm-4 control-label'>手续费收款账号</label>
			<div class='col-sm-4'>
				<input type="text" class="form-control" placeholder="" id="feeTfInAcct" name="feeTfInAcct" >
			</div>
		</div>
	</div>
</div>
<div ravo="rainbow_fx_layout" class="row clearfix">
	<div class='col-md-12 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='busiTp' class='col-sm-2 control-label'>联机业务</label>
			<div class='col-sm-8'>
<%--				<select data-role="multiselect" id="busiTp" class=""--%>
<%--					name="busiTp">--%>
<%--				</select>--%>
				<input type="radio" id="busiTp" name="busiTp" value="01" checked="checked" />非联网
				<br>
				<input type="radio" id="busiTp" name="busiTp" value="00" />联网(需要输入关联系统编号，该编号由技术人员提供)
			</div>
		</div>
	</div>
</div>
<div ravo="rainbow_fx_layout" class="row clearfix hide" id="show_busiTp">
	<div class="col-md-2 column"></div>
	<div class='col-md-5 column'>
		<div ravo='rainbow_fx' class='form-group'>
			<label for='relatSysNo' class='col-sm-4 control-label'>关联系统编号</label>
			<div class='col-sm-4'>
				<select data-role="multiselect" id="relatSysNo" class="" name="relatSysNo" data-max-height="300"
						data-enable-filtering="true" data-enable-full-value-filtering="false" data-enable-case-insensitive-filtering="true" data-filter-placeholder="搜索" checkbtn="relatSysNo">
					<option value="">
						请选择
					</option>
				</select>
			</div>
		</div>
	</div>
</div>
