document.write("<script src='js/util.js' type='text/javascript'></script>");

function addStyle(key, value) {
	if (key == 'undefined' || key == '') {
		return;
	}
	console.info(key + "==" + value);
	var styMap = new Hashtable();

	var style = presentElement.attr('style');
	if (style != undefined) {
		var ss = style.split(';');
		for (var i = 0; i < ss.length; i++) {
			var s = ss[i].split(':');
			if (s[0] == '' || s[0] == undefined) {
				continue;
			}
			styMap.add(s[0], s[1]);
		}
	}

	if (value == '' || value == 'undefined') {
		styMap.remove(key);
	} else {
		styMap.add(key, value);
	}

	return styMap.toStr(':');
}

var presentElement;// 当前组件
var scriptStr = '';// 脚本储存
var eleType = '';
function grid_attrs(presentElement) {
	eleType = 'grid';
	var cl = presentElement.attr('class');
	var cls = cl.split(' ');
	var _id = presentElement.attr("id");
	var style = presentElement.attr('style');
	var _margin = "", _background = "", _borderStyle = "", _borderWidth = "", _boderColor = "", _padding = "", _textAlign = "";
	var _area = presentElement.attr('data-area-text');
	if (presentElement.hasClass("text-left")) {
		_textAlign = "text-left";
	} else if (presentElement.hasClass("text-center")) {
		_textAlign = "text-center";
	} else if (presentElement.hasClass("text-right")) {
		_textAlign = "text-right";
	}
	if (style != undefined && style !== '') {
		var s = style.split(';');
		for (var i = 0; i < s.length - 1; i++) {
			var ss = s[i].split(':');
			var _name = ss[0].trim();
			var _value = "";
			for (var j = 1; j < ss.length - 1; j++) {
				_value += ss[j].trim() + ":";
			}
			_value += ss[ss.length - 1].trim();
			if (_name == "margin") {
				_margin = _value;
			}
			if (_name == "padding") {
				_padding = _value;
			}
			if (_name == "background-color") {
				if (_value != '') {
					_background = _value;
				}
			}
			if (_name == "background-image") {
				if (_value != 'none') {
					var _start = _value.indexOf("(");
					var _end = _value.indexOf(")");
					var _bg = _value.substring(_start + 1, _end);
					_background = _bg.replace(/\"/g, "");
				}
			}
			if (_name == "border-width") {
				_borderWidth = _value;
			}
			if (_name == "border-style") {
				_borderStyle = _value;
			}
			if (_name == "border-color") {
				_boderColor = _value;
			}
		}
	}
	// 正则表达式 列布局
	// 列偏移
	var regex_col_xs = buildRegex('col-xs-[\\d]{1,2}', 'gi');
	var regex_col_sm = buildRegex('col-sm-[\\d]{1,2}', 'gi');
	var regex_col_md = buildRegex('col-md-[\\d]{1,2}', 'gi');
	var regex_col_lg = buildRegex('col-lg-[\\d]{1,2}', 'gi');
	// 列隐藏
	var regex_col_xs_offset = buildRegex('col-xs-offset-[\\d]{1,2}', 'gi');
	var regex_col_sm_offset = buildRegex('col-sm-offset-[\\d]{1,2}', 'gi');
	var regex_col_md_offset = buildRegex('col-md-offset-[\\d]{1,2}', 'gi');
	var regex_col_lg_offset = buildRegex('col-lg-offset-[\\d]{1,2}', 'gi');

	// 列布局
	var col_xs = '';
	var col_sm = '';
	var col_md = '';
	var col_lg = '';
	// 列偏移
	var col_xs_offset = '';
	var col_sm_offset = '';
	var col_md_offset = '';
	var col_lg_offset = '';
	// 列隐藏
	var hidden_xs = '';
	var hidden_sm = '';
	var hidden_md = '';
	var hidden_lg = '';
	// 列显示
	var visible_xs_block = '';
	var visible_sm_block = '';
	var visible_md_block = '';
	var visible_lg_block = '';
	// 清除列浮动
	var clearfix_visible_xs = '';
	var clearfix_visible_sm = '';
	var clearfix_visible_md = '';
	var clearfix_visible_lg = '';

	for (var i = 0; i < cls.length; i++) {
		var _c = cls[i];
		if (_c.match(regex_col_xs) !== null
				&& _c.match(regex_col_xs).length >= 1) {// 匹配
			col_xs = _c;
		} else if (_c.match(regex_col_sm) !== null
				&& _c.match(regex_col_sm).length >= 1) {
			col_sm = _c;
		} else if (_c.match(regex_col_md) !== null
				&& _c.match(regex_col_md).length >= 1) {
			col_md = _c;
		} else if (_c.match(regex_col_lg) !== null
				&& _c.match(regex_col_lg).length >= 1) {
			col_lg = _c;
		} else if (_c.match(regex_col_xs_offset) !== null
				&& _c.match(regex_col_xs_offset).length >= 1) {
			col_xs_offset = _c;
		} else if (_c.match(regex_col_sm_offset) !== null
				&& _c.match(regex_col_sm_offset).length >= 1) {
			col_sm_offset = _c;
		} else if (_c.match(regex_col_md_offset) !== null
				&& _c.match(regex_col_md_offset).length >= 1) {
			col_md_offset = _c;
		} else if (_c.match(regex_col_lg_offset) != null
				&& _c.match(regex_col_lg_offset).length >= 1) {
			col_lg_offset = _c;
		} else if (_c === 'hidden-xs') {
			hidden_xs = 'hidden-xs';
		} else if (_c === 'hidden-sm') {
			hidden_sm = 'hidden-sm';
		} else if (_c === 'hidden-md') {
			hidden_md = 'hidden-md';
		} else if (_c === 'hidden-lg') {
			hidden_lg = 'hidden-lg';
		} else if (_c === 'visible-xs-block') {
			visible_xs_block = 'visible-xs-block';
		} else if (_c === 'visible-sm-block') {
			visible_sm_block = 'visible-sm-block';
		} else if (_c === 'visible-md-block') {
			visible_md_block = 'visible-md-block';
		} else if (_c === 'visible-lg-block') {
			visible_lg_block = 'visible-lg-block';
		}
	}

	// 清除列浮动
	var clea = presentElement.prev('.clearfix');

	if (clea.length > 0) {
		var clea_cl = clea.attr('class');
		var clea_cls = clea_cl.split(' ');
		for (var j = 0; j < clea_cls.length; j++) {
			if (clea_cls[j] === 'visible-xs-block') {
				clearfix_visible_xs = 'visible-xs-block';
			} else if (clea_cls[j] === 'visible-sm-block') {
				clearfix_visible_sm = 'visible-sm-block';
			} else if (clea_cls[j] === 'visible-md-block') {
				clearfix_visible_md = 'visible-md-block';
			} else if (clea_cls[j] === 'visible-lg-block') {
				clearfix_visible_lg = 'visible-lg-block';
			}
		}
	}

	var attrs = "eleType=" + eleType + "@#!id=" + _id + "@#!style=" + style
			+ "@#!padding=" + _padding + "@#!域名=" + _area + "@#!margin="
			+ _margin + "@#!background=" + _background + "@#!borderStyle="
			+ _borderStyle + "@#!borderWidth=" + _borderWidth
			+ "@#!borderColor=" + _boderColor + "@#!textAlign=" + _textAlign
			+ "@#!col_xs=" + col_xs + "@#!col_sm=" + col_sm + "@#!col_md="
			+ col_md + "@#!col_lg=" + col_lg + "@#!col_xs_offset="
			+ col_xs_offset + "@#!col_sm_offset=" + col_sm_offset
			+ "@#!col_md_offset=" + col_md_offset + "@#!col_lg_offset="
			+ col_lg_offset + "@#!hidden-xs=" + hidden_xs + "@#!hidden-sm="
			+ hidden_sm + "@#!hidden-md=" + hidden_md + "@#!hidden-lg="
			+ hidden_lg + "@#!visible-xs-block=" + visible_xs_block
			+ "@#!visible-sm-block=" + visible_sm_block
			+ "@#!visible-md-block=" + visible_md_block
			+ "@#!visible-lg-block=" + visible_lg_block
			+ "@#!clearfix_visible_xs=" + clearfix_visible_xs
			+ "@#!clearfix_visible_sm=" + clearfix_visible_sm
			+ "@#!clearfix_visible_md=" + clearfix_visible_md
			+ "@#!clearfix_visible_lg=" + clearfix_visible_lg;
	return attrs;
}
function select_attr(presentElement) {
	eleType = 'select';
	var _info = '';
	var _left = '';
	var _right = '';
	var _form_group = '';
	var _isMultiple = false;
	var _Multiple = false, _enableClickableOptGroups = false, _enableCollapsibleOptGroups = false, _disableIfEmpty = false, _disabledText = "", _dropRight = false, _dropUp = false, _maxHeight = "", _buttonClass = "", _inheritClass = false, _buttonWidth = "", _nonSelectedText = "", _nSelectedText = "", _allSelectedText = "", _numberDisplayed = "", _delimiterText = "", _selectedClass = "", _includeSelectAllOption = false, _selectAllJustVisible = true, _selectAllText = "", _selectAllValue = "", _selectAllName = "", _selectAllNumber = "", _enableFiltering = false, _enableCaseInsensitiveFiltering = false, _enableFullValueFiltering = false, _filterBehavior = "", _filterPlaceholder = "";
	var _hidden = presentElement.closest('div.view').children('div').children(
			'label').attr('style');
	var _poSrc = presentElement.closest("form").attr("pourl");
	var _reverse = reverse(presentElement);
	// 无url报错
	// _poSrc="";
	var _id = presentElement.attr("id");
	var _name = presentElement.attr("name");
	var _value = presentElement.find("option:selected").attr('value');
	var _style = presentElement.attr("style");
	var _url = presentElement.attr("data-url");
	/* 20180623 add by chenyl for 新增multiselect同步加载功能*/
	var _async = presentElement.attr("data-async");
	console.info("_url mes =" + _url);
	// 20160929 add by chenyl 新增下拉选项空选项的处理
	var _blank_item = presentElement.attr("blank-item");
	var _blank_text = presentElement.attr("blank-text");
	var _blank_value = presentElement.attr("blank-value");
	console.info("_blank_item = " + _blank_item + " , _blank_text = "
			+ _blank_text);
	/* 20180313 add by chenyl for 新增新校验属性处理*/
	var _checkbtn = presentElement.attr("checkbtn");
	
	var _hidden = presentElement.closest('div.view').children('div').children(
			'label').attr('style');
	var _cl = presentElement.closest('div.view').children('div').attr('class')
			.split(" ");
	var Multiple2 = presentElement.attr("multiple");
	if (Multiple2 != undefined && Multiple2 == "multiple") {
		_Multiple = true;
	}
	_enableClickableOptGroups = presentElement
			.attr("data-enable-clickable-opt-groups");
	_enableCollapsibleOptGroups = presentElement
			.attr("data-enable-collapsible-opt-groups");
	_disableIfEmpty = presentElement.attr("data-disable-if-empty");
	_disabledText = presentElement.attr("data-disabled-text");
	_dropRight = presentElement.attr("data-drop-right");
	_dropUp = presentElement.attr("data-drop-up");
	_maxHeight = presentElement.attr("data-max-height");
	_buttonClass = presentElement.attr("data-button-class");
	_inheritClass = presentElement.attr("data-inherit-class");
	_buttonWidth = presentElement.attr("data-button-width");
	_nonSelectedText = presentElement.attr("data-non-selected-text");
	_nSelectedText = presentElement.attr("data-n-selected-text");
	_allSelectedText = presentElement.attr("data-all-selected-text");
	_numberDisplayed = presentElement.attr("data-number-displayed");
	_delimiterText = presentElement.attr("data-delimiter-text");
	_selectedClass = presentElement.attr("data-selected-class");
	_includeSelectAllOption = presentElement
			.attr("data-include-select-all-option");
	_selectAllJustVisible = presentElement.attr("data-select-all-just-visible");
	_selectAllText = presentElement.attr("data-select-all-text");
	_selectAllValue = presentElement.attr("data-select-all-value");
	_selectAllName = presentElement.attr("data-select-all-name");
	_selectAllNumber = presentElement.attr("data-select-all-number");
	_enableFiltering = presentElement.attr("data-enable-filtering");
	_enableCaseInsensitiveFiltering = presentElement
			.attr("data-enable-case-insensitive-filtering");
	_enableFullValueFiltering = presentElement
			.attr("data-enable-full-value-filtering");
	_filterBehavior = presentElement.attr("data-filter-behavior");
	_filterPlaceholder = presentElement.attr("data-filter-placeholder");
	for (var i = 0; i < _cl.length; i++) {
		if (_cl[i] === 'form-group-lg' || _cl[i] === 'form-group-sm') {
			_form_group = _cl[i];
		}
	}
	var info = presentElement.children();
	/*
	 * for(var i =0;i<info.length;i++){ _info +=
	 * info.eq(i).text()+"#&"+info.eq(i).attr("value")+"\n"; }
	 */
	for (var i = 0; i < info.length; i++) {
		if (info[i].tagName == "OPTGROUP") {
			var oCld = $(info[i]).children();
			_info += "----" + "\n";
			for (var j = 0; j < oCld.length; j++) {
				_info += $.trim(oCld.eq(j).text()) + "#&"
						+ $.trim(oCld.eq(j).attr("value")) + "\n";
			}
		} else if (info[i].tagName == "OPTION") {
			_info += $.trim(info.eq(i).text()) + "#&"
					+ $.trim(info.eq(i).attr("value")) + "\n";
		}
	}

	var _formType = presentElement.closest('form').hasClass('form-horizontal');
	if (_formType) {
		var _cla = presentElement.closest('div.view').children('div').children(
				'label').attr('class').split(" ");
		for (var i = 0; i < _cla.length; i++) {
			if (_cla[i].trim() === 'col-sm-1' || _cla[i].trim() === 'col-sm-2'
					|| _cla[i].trim() === 'col-sm-3'
					|| _cla[i].trim() === 'col-sm-4'
					|| _cla[i].trim() === 'col-sm-5'
					|| _cla[i].trim() === 'col-sm-6'
					|| _cla[i].trim() === 'col-sm-7'
					|| _cla[i].trim() === 'col-sm-8'
					|| _cla[i].trim() === 'col-sm-9'
					|| _cla[i].trim() === 'col-sm-10'
					|| _cla[i].trim() === 'col-sm-11'
					|| _cla[i].trim() === 'col-sm-12') {
				_left = _cla[i] + " control-label";
			}
		}
		var _clas = presentElement.attr("class").split(" ");
		for (var i = 0; i < _clas.length; i++) {
			if (_clas[i].trim() == "col-sm-1" || _clas[i].trim() == "col-sm-2"
					|| _clas[i].trim() == "col-sm-3"
					|| _clas[i].trim() == "col-sm-4"
					|| _clas[i].trim() == "col-sm-5"
					|| _clas[i].trim() == "col-sm-6"
					|| _clas[i].trim() == "col-sm-7"
					|| _clas[i].trim() == "col-sm-8"
					|| _clas[i].trim() == "col-sm-9"
					|| _clas[i].trim() == "col-sm-10"
					|| _clas[i].trim() == "col-sm-11"
					|| _clas[i].trim() == "col-sm-12") {
				_right = "btn-group " + _clas[i];
			}

		}
		attrs = "左占=" + _left + "@#!右占=" + _right + "@#!尺寸=" + _form_group
				+ "@#!id=" + _id + "@#!reverse=" + _reverse + "@#!name="
				+ _name + "@#!value=" + _value + "@#!style=" + _style
				+ "@#!eleType=select" + "@#!内容=" + _info + "@#!隐藏=" + _hidden
				+ "@#!formtype=" + _formType + "@#!poSrc=" + _poSrc + "@#!url="
				+ _url + "@#!Multipl=" + _Multiple
				/* 20180313 add by chenyl for 新增新校验属性处理*/
				+ "@#!是否同步加载=" + _async
				+ "@#!enableClickableOptGroups=" + _enableClickableOptGroups
				+ "@#!enableCollapsibleOptGroups="
				+ _enableCollapsibleOptGroups + "@#!disableIfEmpty="
				+ _disableIfEmpty + "@#!disabledText=" + _disabledText
				+ "@#!dropRight=" + _dropRight + "@#!dropUp=" + _dropUp
				+ "@#!maxHeight=" + _maxHeight + "@#!buttonClass="
				+ _buttonClass + "@#!inheritClass=" + _inheritClass
				+ "@#!buttonWidth=" + _buttonWidth + "@#!nonSelectedText="
				+ _nonSelectedText + "@#!nSelectedText=" + _nSelectedText
				+ "@#!allSelectedText=" + _allSelectedText
				+ "@#!numberDisplayed=" + _numberDisplayed
				+ "@#!delimiterText=" + _delimiterText + "@#!selectedClass="
				+ _selectedClass + "@#!includeSelectAllOption="
				+ _includeSelectAllOption + "@#!selectAllJustVisible="
				+ _selectAllJustVisible + "@#!selectAllText=" + _selectAllText
				+ "@#!selectAllValue=" + _selectAllValue + "@#!selectAllName="
				+ _selectAllName + "@#!selectAllNumber=" + _selectAllNumber
				+ "@#!enableFiltering=" + _enableFiltering
				+ "@#!enableCaseInsensitiveFiltering="
				+ _enableCaseInsensitiveFiltering
				+ "@#!enableFullValueFiltering=" + _enableFullValueFiltering
				+ "@#!filterBehavior=" + _filterBehavior
				+ "@#!filterPlaceholder=" + _filterPlaceholder + "@#!是否显示空选项="
				+ _blank_item + "@#!空选项标签值=" + _blank_text + "@#!空选项值="
				+ _blank_value
				/* 20180313 add by chenyl for 新增新校验属性处理*/
				+ "@#!_checkbtn="
				+ _checkbtn;

	} else {
		attrs = "id=" + _id + "@#!reverse=" + _reverse + "@#!name=" + _name
				+ "@#!value=" + _value + "@#!style=" + _style
				+ "@#!eleType=select" + "@#!内容=" + _info + "@#!隐藏=" + _hidden
				+ "@#!formtype=" + _formType + "@#!poSrc=" + _poSrc + "@#!url="
				+ _url + "@#!eleType=select" + "@#!内容=" + _info + "@#!隐藏="
				+ _hidden + "@#!formtype=" + _formType + "@#!poSrc=" + _poSrc
				+ "@#!url=" + _url + "@#!Multipl=" + _Multiple
				/* 20180313 add by chenyl for 新增新校验属性处理*/
				+ "@#!是否同步加载=" + _async
				+ "@#!enableClickableOptGroups=" + _enableClickableOptGroups
				+ "@#!enableCollapsibleOptGroups="
				+ _enableCollapsibleOptGroups + "@#!disableIfEmpty="
				+ _disableIfEmpty + "@#!disabledText=" + _disabledText
				+ "@#!dropRight=" + _dropRight + "@#!dropUp=" + _dropUp
				+ "@#!maxHeight=" + _maxHeight + "@#!buttonClass="
				+ _buttonClass + "@#!inheritClass=" + _inheritClass
				+ "@#!buttonWidth=" + _buttonWidth + "@#!nonSelectedText="
				+ _nonSelectedText + "@#!nSelectedText=" + _nSelectedText
				+ "@#!allSelectedText=" + _allSelectedText
				+ "@#!numberDisplayed=" + _numberDisplayed
				+ "@#!delimiterText=" + _delimiterText + "@#!selectedClass="
				+ _selectedClass + "@#!includeSelectAllOption="
				+ _includeSelectAllOption + "@#!selectAllJustVisible="
				+ _selectAllJustVisible + "@#!selectAllText=" + _selectAllText
				+ "@#!selectAllValue=" + _selectAllValue + "@#!selectAllName="
				+ _selectAllName + "@#!selectAllNumber=" + _selectAllNumber
				+ "@#!enableFiltering=" + _enableFiltering
				+ "@#!enableCaseInsensitiveFiltering="
				+ _enableCaseInsensitiveFiltering
				+ "@#!enableFullValueFiltering=" + _enableFullValueFiltering
				+ "@#!filterBehavior=" + _filterBehavior
				+ "@#!filterPlaceholder=" + _filterPlaceholder + "@#!是否显示空选项="
				+ _blank_item + "@#!空选项标签值=" + _blank_text + "@#!空选项值="
				+ _blank_value
				/* 20180313 add by chenyl for 新增新校验属性处理*/
				+ "@#!_checkbtn="
				+ _checkbtn;
	}
	return attrs;
}
function row_attr(presentElement) {
	eleType = 'row';
	var _id = presentElement.attr("id");
	var style = presentElement.attr('style');
	var _margin = "", _background = "", _borderStyle = "", _borderWidth = "", _boderColor = "";
	if (style != undefined && style !== '') {
		var s = style.split(';');
		for (var i = 0; i < s.length - 1; i++) {
			var ss = s[i].split(':');
			var _name = ss[0].trim();
			var _value = "";
			for (var j = 1; j < ss.length - 1; j++) {
				_value += ss[j].trim() + ":";
			}
			_value += ss[ss.length - 1].trim();
			if (_name == "margin") {
				_margin = _value;
			}
			if (_name == "background-color") {
				if (_value != '') {
					_background = _value;
				}
			}
			if (_name == "background-image") {
				if (_value != 'none') {
					var _start = _value.indexOf("(");
					var _end = _value.indexOf(")");
					var _bg = _value.substring(_start + 1, _end);
					_background = _bg.replace(/\"/g, "");
				}
			}
			if (_name == "border-width") {
				_borderWidth = _value;
			}
			if (_name == "border-style") {
				_borderStyle = _value;
			}
			if (_name == "border-color") {
				_boderColor = _value;
			}
		}
	}
	// var
	// attrs="eleType="+eleType+",,id="+_id+",,margin="+_margin+",,background="+_background+",,borderStyle="+_borderStyle+",,borderWidth="+_borderWidth+",,borderColor="+_boderColor;
	var attrs = "eleType=" + eleType + "@#!id=" + _id + "@#!margin=" + _margin
			+ "@#!background=" + _background + "@#!borderStyle=" + _borderStyle
			+ "@#!borderWidth=" + _borderWidth + "@#!borderColor="
			+ _boderColor;
	return attrs;
}
function button_attr(presentElement) {
	eleType = 'button';
	// add by chenyl
	var _permission = presentElement.parent().attr("name");
	var _id = presentElement.attr("id");
	var _value = presentElement.attr("value");
	var _type = presentElement.attr("type");
	// 20160930 add by chenyl 提交、重置按钮特殊处理
	var _btn_type = presentElement.attr("btn-type");
	var _class = presentElement.attr("class");
	var cld = presentElement.children();// 子元素
	var _name = presentElement.attr("name");
	var _style = presentElement.attr("style");
	var _wenben = presentElement.html();
	var _float = "", _btnStyle = "", _btnSize = "", _btnBlock = "", _src = "", _badge = "", _imgOption = "right";
	var _inlineType = "";
	var regex = buildRegex('<[ ]*.+"[ ]*>[\s]*</.+>');
	_wenben = _wenben.replace(regex, '');
	regex = buildRegex('<[ ]*.+"[ ]*>');
	_wenben = _wenben.replace(regex, '');
	regex = buildRegex('[\\t\\n]', 'gi');
	_wenben = _wenben.replace(regex, '').trim();
	var arrFloat = new Array("pull-left", "pull-right");
	var arrStyle = new Array("btn-default", "btn-primary", "btn-info",
			"btn-success", "btn-danger", "btn-warning", "btn-link",
			"btn-inverse");
	var arrSize = new Array("btn-lg", "btn-sm", "btn-xs");
	var _edit = "";
	if (_class != undefined) {
		for (var i = 0; i < arrFloat.length; i++) {
			if (_class.indexOf(arrFloat[i]) >= 0) {
				_float = arrFloat[i];
			}
		}
		for (var i = 0; i < arrStyle.length; i++) {
			if (_class.indexOf(arrStyle[i]) >= 0) {
				_btnStyle = arrStyle[i];
			}
		}
		for (var i = 0; i < arrSize.length; i++) {
			if (_class.indexOf(arrSize[i]) >= 0) {
				_btnSize = arrSize[i];
			}
		}
		if (_class.indexOf("btn-block") >= 0) {
			_btnBlock = "btn-block";
		}
	}
	// 如果button存在子元素
	if (cld.length > 0) {
		var cldCls = cld.attr("class");
		if (cld[0].tagName == "SPAN") {
			// 如果是下拉菜单
			if (cldCls != undefined && cldCls.indexOf("caret") >= 0) {
				_inlineType = "dropmenu";
				presentElement.next().find("li").each(function() {
					var cls = $(this).attr("class");
					if (cls == "divider") {
						_edit += "----" + "\n";
					} else {
						_edit += $(this).children().text() + "\n";
					}
				});
			} else if (cldCls != undefined && cldCls.indexOf("badge") >= 0) {
				_inlineType = "badge";
				_badge = cld.text();
			}
		} else if (cld[0].tagName == "I") {
			_inlineType = "i";
			if (cldCls != undefined) {
				var split = cldCls.split(" ");
				for (var i = 0; i < split.length; i++) {
					if (split.indexOf('glyphicon') >= 0) {
						_src += split[i] + " ";
					}
				}
			}
			var _text = presentElement.text();
			var _html = presentElement.html();
			if (_html.endWith(_text)) {
				_imgOption = "left";
			}
		}
	}
	// var
	// attrs="id="+_id+","+"type="+_type+","+"btn-type="+_btn_type+","+"class="+_class+","+"name="+_name+",图片位置="+_imgOption+","+"wenben="+_wenben+",尺寸="+_btnSize+","+"eleType=button"+",style="+_style+",inlineType="+_inlineType+",edit="+_edit+",浮动="+_float+",value="+_value+",样式="+_btnStyle+",通栏="+_btnBlock+",src="+_src+",badge="+_badge+",设置权限="+_permission;
	var attrs = "id=" + _id + "@#!" + "type=" + _type + "@#!" + "btn-type="
			+ _btn_type + "@#!" + "class=" + _class + "@#!" + "name=" + _name
			+ "@#!图片位置=" + _imgOption + "@#!" + "wenben=" + _wenben + "@#!尺寸="
			+ _btnSize + "@#!" + "eleType=button" + "@#!style=" + _style
			+ "@#!inlineType=" + _inlineType + "@#!edit=" + _edit + "@#!浮动="
			+ _float + "@#!value=" + _value + "@#!样式=" + _btnStyle + "@#!通栏="
			+ _btnBlock + "@#!src=" + _src + "@#!badge=" + _badge + "@#!设置权限="
			+ _permission;
	return attrs;
}
function a_attr(presentElement) {
	eleType = 'a';
	var _id = presentElement.attr("id");
	var _name = presentElement.attr("name");
	var _rel = presentElement.attr("rel");
	var _title = presentElement.attr("title");
	var _inlineType = "", _aType = "";
	var _style = presentElement.attr("style");
	var _href = presentElement.attr("href");
	var _text = presentElement.text().trim();
	var p = presentElement.parent();// a父节点
	var pp = p.parent();// a父节点的父节点
	var cld = presentElement.children();
	var _class = p.attr('class');
	var _aClass = presentElement.attr('class');
	var _ulClass = "", _active = "", _disabled = "", _float = "", _ulFloat = "", _edit = "", _ulEdit = "", _src = "", _badge = "";
	var _aActive = "", _aFloat = "", _aDisabled = "";
	var _isNavbar = false, _preIsLi = false;
	_isBlank = false;// 定义两个元素判断是否为导航，a标签父类是否为li
	var floatArr = new Array("pull-left", "pull-right");
	var ulFloatArr = new Array("navbar-left", "navbar-right");
	var btnType = new Array("btn-default", "btn-primary", "btn-success",
			"btn-info", "btn-warning", "btn-danger", "btn-link");
	var btnSize = new Array("btn-lg", "btn-sm", "btn-xs");
	var labelType = new Array("label-default", "label-primary",
			"label-success", "label-info", "label_warning", "label_danger");
	var _btnType = "", _btnSize = "", _btnBlock = "", _btnActive = "", _btnDisabled = "";
	var _labelType = "";
	var _imgAlign = "";
	// 判断a标签父类是否为li
	if (p[0].tagName == "LI") {
		_preIsLi = true;
	}
	// mody by chenyl 20170421
	if (presentElement.attr("target") != undefined
			&& presentElement.attr("target") == "_blank") {
		_isBlank = true;
	}
	// 获得<a>标签父节点的属性用于反显
	if (_class != undefined) {
		// 如果a标签父类为li
		if (p[0].tagName == "LI") {
			if (_class.indexOf("active") >= 0) {
				_active = "active";
			}
			if (_class.indexOf("disabled") >= 0) {
				_disabled = "disabled";
			}
			// 循环遍历判断_class是否含有数组中的元素，若有则赋值
			for (var i = 0; i < floatArr.length; i++) {
				if (_class.indexOf(floatArr[i]) >= 0) {
					_float = floatArr[i];
				}
			}
		}
	}
	// 获得<a>标签属性用于反显
	if (_aClass != undefined) {
		// 反显a浮动
		$.each(floatArr, function(n, value) {
			if (_aClass.indexOf(value) >= 0) {
				_aFloat = value;
			}
		});
		// 反显a活动
		if (_aClass.indexOf("active") >= 0) {
			_aActive = "active";
		}
		// 反显a禁用
		if (_aClass.indexOf("disabled") >= 0) {
			_aDisabled = "disabled";
		}
		$.each(btnType, function(n, value) {
			if (_aClass.indexOf(value) >= 0) {
				_btnType = value;
			}
		});
		$.each(btnSize, function(n, value) {
			if (_aClass.indexOf(value) >= 0) {
				_btnSize = value;
			}
		});
		$.each(labelType, function(n, value) {
			if (_aClass.indexOf(value) >= 0) {
				_labelType = value;
			}
		});
		if (_aClass.indexOf("btn-block") >= 0) {
			_btnBlock = "btn-block";
		}
		if (_aClass.indexOf("active") >= 0) {
			_btnActive = "active";
		}
		if (_aClass.indexOf("disabled") >= 0) {
			_btnDisabled = "disabled";
		}
		// 判断<a>标签类型：button,label
		if (_aClass.indexOf("btn") >= 0) {
			_aType = "button";
		} else if (_aClass.indexOf("label") >= 0) {
			_aType = "label";
		}
	}
	// 如果<a>祖先节点组件为导航栏(navbar),frame中则显示ul浮动,否则不显示
	if (presentElement.closest(".view").attr("id") == "navbar") {
		_isNavbar = true;
	} else {
		_isNavbar = false;
	}
	// 获得<a>标签祖先节点Ul的属性用于反显
	if (pp[0].tagName == "UL") {
		_ulClass = pp.attr("class");
		if (_ulClass != undefined) {
			for (var i = 0; i < ulFloatArr.length; i++) {
				if (_ulClass.indexOf(ulFloatArr[i]) >= 0) {
					_ulFloat = ulFloatArr[i];
				}
			}
		}
		// 找到UL的直系子节点<a>获取文本值
		pp.find(">li >a").each(function() {
			_ulEdit += $(this).text().trim() + "\n";
		});
	}
	var cld = presentElement.children("img,i,span");
	// 若<a>标签含子节点
	if (cld.length > 0) {
		if (cld[0].tagName == "IMG" || cld[0].tagName == "I") {
			var cls = cld.attr("class");
			_inlineType = "image";
			$.each(floatArr, function(n, value) {
				if (cls != undefined && cls.indexOf(value) >= 0) {
					_imgAlign = value;
				}
			});
			if (cld[0].tagName == "IMG") {
				_src = cld.attr('src');
			} else if (cld[0].tagName == "I") {
				if (cls != undefined) {
					var split = cls.split(" ");
					for (var i = 0; i < split.length; i++) {
						if (split.indexOf('glyphicon') >= 0) {
							_src += split[i] + " ";
						}
					}
				}
			}
		} else if (cld[0].tagName == "SPAN") {// 判断是否为徽章（即判断class为badge的span是否存在）
			var _cls = cld.attr("class");
			if (_cls != undefined) {
				if (_cls.indexOf("badge") >= 0) {// 徽章
					_inlineType = "badge";
					_badge = cld.text();
				} else if (_cls.indexOf("caret") >= 0) {// 下拉菜单
					_inlineType = "dropdown";
					/* 下拉菜单读取 */
					$(this).next().find("li").each(function() {
						var cls = $(this).attr("class");
						if (cls == "divider") {
							_edit += "----" + "\n";
						} else {
							_edit += $(this).children().text() + "\n";
						}
					});
				}
			}
		}
	}
	// var
	// attrs="eleType="+eleType+",id="+_id+",name="+_name+",title="+_title+",isBlank="+_isBlank+",a浮动="+_aFloat+",a活动="+_aActive+",a禁用"+_aDisabled+",rel="+_rel+",style="+_style+",btnType="+_btnType+",btnSize="+_btnSize+",btnBlock="+_btnBlock+",btnActive="+_btnActive+",btnDisabled="+_btnDisabled+",labelType="+_labelType+",活动/取消活动="+_active+",禁用/取消禁用="+_disabled+",浮动="+_float+",inlineType="+_inlineType+",aType="+_aType+",edit="+_edit+",ulFloat="+_ulFloat+",图片位置="+_imgAlign+",src="+_src+",ulEdit="+_ulEdit+",靠左/靠右="+_ulFloat+",isNavbar="+_isNavbar+",_preIsLi="+_preIsLi+",href="+_href+",文本="+_text+",badge="+_badge;
	var attrs = "eleType=" + eleType + "@#!id=" + _id + "@#!name=" + _name
			+ "@#!title=" + _title + "@#!isBlank=" + _isBlank + "@#!a浮动="
			+ _aFloat + "@#!a活动=" + _aActive + "@#!a禁用" + _aDisabled
			+ "@#!rel=" + _rel + "@#!style=" + _style + "@#!btnType="
			+ _btnType + "@#!btnSize=" + _btnSize + "@#!btnBlock=" + _btnBlock
			+ "@#!btnActive=" + _btnActive + "@#!btnDisabled=" + _btnDisabled
			+ "@#!labelType=" + _labelType + "@#!活动/取消活动=" + _active
			+ "@#!禁用/取消禁用=" + _disabled + "@#!浮动=" + _float + "@#!inlineType="
			+ _inlineType + "@#!aType=" + _aType + "@#!edit=" + _edit
			+ "@#!ulFloat=" + _ulFloat + "@#!图片位置=" + _imgAlign + "@#!src="
			+ _src + "@#!ulEdit=" + _ulEdit + "@#!靠左/靠右=" + _ulFloat
			+ "@#!isNavbar=" + _isNavbar + "@#!_preIsLi=" + _preIsLi
			+ "@#!href=" + _href + "@#!文本=" + _text + "@#!badge=" + _badge;
	return attrs;
}

/**
 * 获取要修改的组件
 */
var IDISNULL = 0;// 0 无值
var IDVALUE = null;
var clickEle = null;
function addSelectCls(presentElement) {
	if (clickEle != null) {
		clickEle.removeClass("rainbow-select");
	}
	clickEle = presentElement;
	clickEle.addClass("rainbow-select");
}
$(document)
		.ready(
				function() {
					$(".container").delegate(
							".ui-sortable",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								eleType = 'container';
								addSelectCls(presentElement);
								var downLayout = $('#download-layout')
										.children().first();
								var _class = downLayout.attr('class');
								if (downLayout.hasClass("container")) {
									_class = "container";
								} else if (downLayout
										.hasClass("container-fluid")) {
									_class = "container-fluid";
								}
								var attrs = "eleType=" + eleType + ",class="
										+ _class;
								sendMessage('parent', attrs);
							});
					$(".ui-sortable").delegate(
							"button",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								var $test = $(this).closest(".btn-group").prev(
										"select");
								var testid1 = $test.length;
								if (testid1 == 1) {
									console.info(testid1
											+ " trdy 匹配到了select 元素");
									presentElement = $test;
									addSelectCls(presentElement);
									var attrs = select_attr(presentElement);
									sendMessage('parent', attrs);
								} else {
									presentElement = $(this);
									addSelectCls(presentElement);
									var attrs = button_attr(presentElement);
									console.info(" button 按钮： " + attrs);
									sendMessage('parent', attrs);
								}
							});

					$(".ui-sortable").delegate(
							"canvas",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								addSelectCls(presentElement);
								var action = presentElement.attr("action");
								var beg = presentElement.attr("beg");
								var end = presentElement.attr("end");
								eleType = presentElement.closest('div.view')
										.attr('id');
								attrs = "eleType=" + eleType + ",action="
										+ action + ",起始ID=" + beg + ",结束ID="
										+ end;
								sendMessage('parent', attrs);

							});

					$('.ui-sortable').bind('contextmenu', function() {
						return false;
					});
					$(".ui-sortable")
							.delegate(
									"input[type='checkbox'],input[type='radio'],input[type='text'],button,a,select",
									"mousedown",
									function(e) {
										e.preventDefault();
										e.stopPropagation();
										var _this = $(this);
										if (3 == e.which) {
											var testselect = _this.closest(
													".btn-group")
													.prev("select").length;
											if (testselect == 1) {
												var idselect = _this.closest(
														".btn-group").prev(
														"select").attr("id");
												_this.attr("id", idselect);
											}
											if (!$(".popover a").length) {
												console
														.info(" button 按钮： 移动事件");
												var map = {};
												map.value = 10001;// 10001 标识
																	// 按钮 Button
												var attrs = button_attr(_this);
												var Arr = attrs.split("@#!")[0];
												var ArrId = Arr.split("=");
												console.info(ArrId[0]
														+ " ==id?");
												IDVALUE = ArrId[1];
												console.info(IDVALUE
														+ "  idval");
												console.info(e.which + "鼠标事件");

												_this
														.popover(
																{
																	trigger : 'manual',
																	placement : 'right',
																	title : " JS  ",
																	html : true,
																	animation : true,
																	content : '<a  id="a1" href="javascript:void(0);"  onmouseover="" onclick="startjseditor('
																			+ map.value
																			+ ',this.id);" style="text-decoration: none;">click</a><br/><a id="a2" href="javascript:void(0);"  onmouseover="" onclick="startjseditor('
																			+ map.value
																			+ ',this.id);" style="text-decoration: none;">blur</a><br/><a id="a3" href="javascript:void(0);"  onmouseover="" onclick="startjseditor('
																			+ map.value
																			+ ',this.id);" style="text-decoration: none;">dblclick</a><br/><a  id="a5" href="javascript:void(0);"  onmouseover="" onclick="startjseditor('
																			+ map.value
																			+ ',this.id);" style="text-decoration: none;">change</a><br/><a  id="a6" href="javascript:void(0);"  onmouseover="" onclick="startjseditor('
																			+ map.value
																			+ ',this.id);" style="text-decoration: none;">focus</a><br/><a id="a4" href="javascript:void(0);"  onmouseover="" onclick="startjseditor('
																			+ map.value
																			+ ',this.id);" style="text-decoration: none;">Custom</a><br/>'
																})
														.popover("show")
														.on(
																"mouseleave",
																function() {
																	// var _this
																	// = this;
																	setTimeout(
																			function() {
																				if (!$(".popover:hover").length) {
																					$(
																							_this)
																							.popover(
																									"hide");
																				}
																				$(
																						".popover")
																						.on(
																								"mouseleave",
																								function() {
																									_this
																											.popover('hide');
																								});

																			},
																			100)
																});
											}

										}

									});

					$(".ui-sortable")
							.delegate(
									"input",
									"dblclick",
									function(e) {
										e.preventDefault();
										e.stopPropagation();
										presentElement = $(this);
										addSelectCls(presentElement);
										var _date = presentElement
												.closest('div.date');
										/* 20181225 add by chenyl for 隐藏域组件 */
										var _input_hidden = presentElement
												.closest('[input_type="hidden"]');
										/* 20180620 add by chenyl for 文件管理组件 */
										var _ckfinder = presentElement
												.closest('[input_type="sys:ckfinder"]');
										/* 20180612 add by chenyl for 图标选择组件 */
										var _iconselect = presentElement
												.closest('[input_type="sys:iconselect"]');
										console.info("treeselect="+_iconselect);
										/* 20161019 add by chenyl for 搜索树组件 */
										var _treesearch = presentElement
												.closest('[input_type="sys:treeselect"]');
										/* 20160809 add by chenyl for 日期时间组件 */
										var _datetime = presentElement
												.closest('input.Wdate');
										if (_date.size() > 0) {
											eleType = 'time';
											var _form_group = '';
											var _left = '', _right = '';
											var _id = '', _name = '', _style = '', _format = '', _weekStart = '', _startDate = '', _endDate = '', _daysOfWeekDisabled = '', _autoclose = false, _startView = '', _minView = '', _maxView = '', _todayBtn = false, _todayHighlight = false, _keyboardNavigation = '', _forceParse = '', _pickerPosition = '', _showMeridian = '';
											var _hidden = presentElement
													.closest('div.view label')
													.attr('style');
											var _poSrc = $(this)
													.closest("form").attr(
															"pourl");
											var _cl = presentElement.closest(
													'div.view').children('div')
													.attr('class').split(" ");
											var _reverse = reverse(presentElement);
											for (var i = 0; i < _cl.length; i++) {
												if (_cl[i] === 'form-group-lg'
														|| _cl[i] === 'form-group-sm'
														|| _cl[i] === 'form-group') {
													_form_group = _cl[i];
												}
											}
											var _name = presentElement.closest(
													'div.view').find(
													'input:hidden')
													.attr('name');
											var _formType = presentElement
													.closest('form').hasClass(
															'form-horizontal');
											var _timepic = presentElement
													.closest('[data-toggle="datatimepiker"]');
											_id = _timepic.next().first().attr(
													"id");
											_name = _timepic.next().first()
													.attr("name");
											_style = presentElement
													.attr("style");
											_format = _timepic
													.attr("data-date-format");
											_weekStart = _timepic
													.attr("data-date-weekstart");
											_startDate = _timepic
													.attr("data-date-startdate");
											_endDate = _timepic
													.attr("data-date-enddate");
											_daysOfWeekDisabled = _timepic
													.attr("data-date-days-of-week-disabled");
											_autoclose = _timepic
													.attr("data-date-autoclose");
											_startView = _timepic
													.attr("data-start-view");
											_minView = _timepic
													.attr("data-min-view");
											_maxView = _timepic
													.attr("data-max-view");
											_todayBtn = _timepic
													.attr("data-date-today-btn");
											_todayHighlight = _timepic
													.attr("data-date-today-highlight");
											_keyboardNavigation = _timepic
													.attr("data-date-keyboard-navigation");
											_forceParse = _timepic
													.attr("data-date-force-parse");
											_pickerPosition = _timepic
													.attr("data-picker-position");
											_showMeridian = _timepic
													.attr("data-show-meridian");
											if (_formType) {
												var _cla = presentElement
														.closest('div.view')
														.children('div')
														.children('label')
														.attr('class').split(
																" ");
												for (var i = 0; i < _cla.length; i++) {
													if (_cla[i].trim() === 'col-sm-1'
															|| _cla[i].trim() === 'col-sm-2'
															|| _cla[i].trim() === 'col-sm-3'
															|| _cla[i].trim() === 'col-sm-4'
															|| _cla[i].trim() === 'col-sm-5'
															|| _cla[i].trim() === 'col-sm-6'
															|| _cla[i].trim() === 'col-sm-7'
															|| _cla[i].trim() === 'col-sm-8'
															|| _cla[i].trim() === 'col-sm-9'
															|| _cla[i].trim() === 'col-sm-10'
															|| _cla[i].trim() === 'col-sm-11'
															|| _cla[i].trim() === 'col-sm-12') {
														_left = _cla[i];
													}
												}
												var _clas = presentElement
														.closest('div.view')
														.children('div')
														.children('div').attr(
																'class').split(
																" ");
												for (var i = 0; i < _clas.length; i++) {
													if (_clas[i].trim() == "col-sm-1"
															|| _clas[i].trim() == "col-sm-2"
															|| _clas[i].trim() == "col-sm-3"
															|| _clas[i].trim() == "col-sm-4"
															|| _clas[i].trim() == "col-sm-5"
															|| _clas[i].trim() == "col-sm-6"
															|| _clas[i].trim() == "col-sm-7"
															|| _clas[i].trim() == "col-sm-8"
															|| _clas[i].trim() == "col-sm-9"
															|| _clas[i].trim() == "col-sm-10"
															|| _clas[i].trim() == "col-sm-11"
															|| _clas[i].trim() == "col-sm-12") {
														_right = _clas[i];
													}

												}
												attrs = "左占="
														+ _left
														+ "@#!poSrc="
														+ _poSrc
														+ "@#!reverse="
														+ _reverse
														+ "@#!右占="
														+ _right
														+ "@#!尺寸="
														+ _form_group
														+ "@#!id="
														+ _id
														+ "@#!name="
														+ _name
														+ "@#!style="
														+ _style
														+ "@#!eleType="
														+ eleType
														+ "@#!format="
														+ _format
														+ "@#!weekStart="
														+ _weekStart
														+ "@#!startDate="
														+ _startDate
														+ "@#!endDate="
														+ _endDate
														+ "@#!daysOfWeekDisabled="
														+ _daysOfWeekDisabled
														+ "@#!autoclose="
														+ _autoclose
														+ "@#!startView="
														+ _startView
														+ "@#!minView="
														+ _minView
														+ "@#!maxView="
														+ _maxView
														+ "@#!todayBtn="
														+ _todayBtn
														+ "@#!todayHighlight="
														+ _todayHighlight
														+ "@#!keyboardNavigation="
														+ _keyboardNavigation
														+ "@#!forceParse="
														+ _forceParse
														+ "@#!pickerPosition="
														+ _pickerPosition
														+ "@#!showMeridian="
														+ _showMeridian
														+ "@#!隐藏=" + _hidden
														+ "@#!formtype="
														+ _formType;
											} else {
												attrs = "尺寸="
														+ _form_group
														+ "@#!id="
														+ _id
														+ "@#!reverse="
														+ _reverse
														+ "@#!name="
														+ _name
														+ "@#!style="
														+ _style
														+ "@#!poSrc="
														+ _poSrc
														+ "@#!eleType="
														+ eleType
														+ "@#!format="
														+ _format
														+ "@#!weekStart="
														+ _weekStart
														+ "@#!startDate="
														+ _startDate
														+ "@#!endDate="
														+ _endDate
														+ "@#!daysOfWeekDisabled="
														+ _daysOfWeekDisabled
														+ "@#!autoclose="
														+ _autoclose
														+ "@#!startView="
														+ _startView
														+ "@#!minView="
														+ _minView
														+ "@#!maxView="
														+ _maxView
														+ "@#!todayBtn="
														+ _todayBtn
														+ "@#!todayHighlight="
														+ _todayHighlight
														+ "@#!keyboardNavigation="
														+ _keyboardNavigation
														+ "@#!forceParse="
														+ _forceParse
														+ "@#!pickerPosition="
														+ _pickerPosition
														+ "@#!showMeridian="
														+ _showMeridian
														+ "@#!隐藏=" + _hidden
														+ "@#!formtype="
														+ _formType;
											}
											;
											sendMessage('parent', attrs);

										}
										/* 20160809 add by chenyl for 日期时间组件 */
										else if (_datetime.size() > 0) {
											eleType = 'datetime';
											console.info("编辑日期时间组件属性");
											var _id = '';
											var _form_group = '';
											var _left = '';
											var _right = '';
											var _skin = "";
											var _dateFmt = "";
											var _minDate = "";
											var _maxDate = "";
											var _showClear = "";
											var _showWeek = "";
											var _showToday = "";
											var _defaultVal = "";
											var _valueFmt = "";
											var _valHiddenId = "";
											var _style = '';
											var _class = '';

											// 表单绑定的后台对象
											var _poSrc = $(this)
													.closest("form").attr(
															"pourl");
											// 是否隐藏标签
											var _hidden = presentElement
													.closest('div.view')
													.children('div').children(
															'label').attr(
															'style');
											var _cl = presentElement.closest(
													'div.view').children('div')
													.attr('class').split(" ");
											// 校验信息
											var _reverse = reverse(presentElement
													.parent()
													.find(
															'input[type="hidden"]'));
											for (var i = 0; i < _cl.length; i++) {
												if (_cl[i] === 'form-group-lg'
														|| _cl[i] === 'form-group-sm'
														|| _cl[i] === 'form-group') {
													// 布局样式
													_form_group = _cl[i];
												}
											}
											// 日期值隐藏域绑定的后台属性值名称
											var _name = presentElement.closest(
													'div.view').find(
													'input:hidden')
													.attr('name');
											// 整个日期组件自定义样式
											_style = presentElement.closest(
													'div.view').children('div')
													.children('div').attr(
															'style');
											// 组件id
											_id = presentElement.closest(
													'div.view').children('div')
													.children('div').children(
															'input.Wdate')
													.attr('id');
											// 日期组件皮肤
											_skin = presentElement.closest(
													'div.view').children('div')
													.children('div').children(
															'input.Wdate')
													.attr('datetime-skin');
											// 日期显示格式
											_dateFmt = presentElement.closest(
													'div.view').children('div')
													.children('div').children(
															'input.Wdate')
													.attr('datetime-date-fmt');
											// 最小值
											_minDate = presentElement.closest(
													'div.view').children('div')
													.children('div').children(
															'input.Wdate')
													.attr('datetime-min-date');
											// 最大值
											_maxDate = presentElement.closest(
													'div.view').children('div')
													.children('div').children(
															'input.Wdate')
													.attr('datetime-max-date');
											// 是否显示清除按钮
											_showClear = presentElement
													.closest('div.view')
													.children('div')
													.children('div')
													.children('input.Wdate')
													.attr(
															'datetime-is-show-clear');
											// 是否显示星期
											_showWeek = presentElement
													.closest('div.view')
													.children('div')
													.children('div')
													.children('input.Wdate')
													.attr(
															'datetime-is-show-week');
											// 是否显示今天按钮
											_showToday = presentElement
													.closest('div.view')
													.children('div')
													.children('div')
													.children('input.Wdate')
													.attr(
															'datetime-is-show-today');
											// 默认值
											_defaultVal = presentElement
													.closest('div.view')
													.children('div')
													.children('div')
													.children('input.Wdate')
													.attr(
															'datetime-default-value');
											// 日期值格式
											_valueFmt = presentElement.closest(
													'div.view').children('div')
													.children('div').children(
															'input.Wdate')
													.attr('datetime-value-fmt');
											// 保存日期隐藏域的id
											_valHiddenId = presentElement
													.closest('div.view')
													.children('div').children(
															'div').children(
															'input.Wdate')
													.attr('data-link-field');
											// 日期输入框的样式
											_class = presentElement.closest(
													'div.view').children('div')
													.children('div').children(
															'input.Wdate')
													.attr('class');
											// 表单类型
											var _formType = presentElement
													.closest('form').hasClass(
															'form-horizontal');
											
											/* 20180313 add by chenyl for 新增新校验属性回显 */
											/*不为空*/
											var _check_time_empty = presentElement
													.closest('div.view')
													.children('div').children(
															'div').children(
															'input.Wdate')
													.attr('check-time-empty');
											/*正常开始时间*/
											var _check_starttime_one = presentElement
													.closest('div.view')
													.children('div').children(
															'div').children(
															'input.Wdate')
													.attr('check-starttime-one');
											/*正常结束时间*/
											var _check_endtime_one = presentElement
													.closest('div.view')
													.children('div').children(
															'div').children(
															'input.Wdate')
													.attr('check-endtime-one');
											/*修改页面开始时间*/
											var _check_starttime_two = presentElement
													.closest('div.view')
													.children('div').children(
															'div').children(
															'input.Wdate')
													.attr('check-starttime-two');
											/*修改页面结束时间*/
											var _check_endtime_two = presentElement
													.closest('div.view')
													.children('div').children(
															'div').children(
															'input.Wdate')
													.attr('check-endtime-two');


											attrs = "eleType=" + eleType
													+ ",poSrc=" + _poSrc
													+ ",隐藏=" + _hidden
													+ ",reverse=" + _reverse
													+ ",尺寸=" + _form_group
													+ ",name=" + _name
													+ ",style=" + _style
													+ ",id=" + _id + ",skin="
													+ _skin + ",dateFmt="
													+ _dateFmt + ",minDate="
													+ _minDate + ",maxDate="
													+ _maxDate + ",showClear="
													+ _showClear + ",showWeek="
													+ _showWeek + ",showToday="
													+ _showToday
													+ ",defaultVal="
													+ _defaultVal
													+ ",valueFmt=" + _valueFmt
													+ ",dtclass=" + _class
													+ ",dtclass=" + _class
													+ ",formtype=" + _formType
													/* 20180313 add by chenyl for 新增新校验属性回显 */
													+ ",_check_time_empty=" + _check_time_empty
													+ ",_check_starttime_one=" + _check_starttime_one
													+ ",_check_endtime_one=" + _check_endtime_one
													+ ",_check_starttime_two=" + _check_starttime_two
													+ ",_check_endtime_two=" + _check_endtime_two;
											if (_formType) {
												// 标签占的阑珊数，左占多少
												var _cla = presentElement
														.closest('div.view')
														.children('div')
														.children('label')
														.attr('class').split(
																" ");
												for (var i = 0; i < _cla.length; i++) {
													if (_cla[i].trim() === 'col-sm-1'
															|| _cla[i].trim() === 'col-sm-2'
															|| _cla[i].trim() === 'col-sm-3'
															|| _cla[i].trim() === 'col-sm-4'
															|| _cla[i].trim() === 'col-sm-5'
															|| _cla[i].trim() === 'col-sm-6'
															|| _cla[i].trim() === 'col-sm-7'
															|| _cla[i].trim() === 'col-sm-8'
															|| _cla[i].trim() === 'col-sm-9'
															|| _cla[i].trim() === 'col-sm-10'
															|| _cla[i].trim() === 'col-sm-11'
															|| _cla[i].trim() === 'col-sm-12') {
														_left = _cla[i];
													}
												}
												// 日期输入框占的阑珊数，右占多少
												var _clas = presentElement
														.closest('div.view')
														.children('div')
														.children('div').attr(
																'class').split(
																" ");
												for (var i = 0; i < _clas.length; i++) {
													if (_clas[i].trim() == "col-sm-1"
															|| _clas[i].trim() == "col-sm-2"
															|| _clas[i].trim() == "col-sm-3"
															|| _clas[i].trim() == "col-sm-4"
															|| _clas[i].trim() == "col-sm-5"
															|| _clas[i].trim() == "col-sm-6"
															|| _clas[i].trim() == "col-sm-7"
															|| _clas[i].trim() == "col-sm-8"
															|| _clas[i].trim() == "col-sm-9"
															|| _clas[i].trim() == "col-sm-10"
															|| _clas[i].trim() == "col-sm-11"
															|| _clas[i].trim() == "col-sm-12") {
														_right = _clas[i];
													}

												}
												attrs = attrs + ",左占=" + _left
														+ ",右占=" + _right;
											}
											sendMessage('parent', attrs);

										}
										/* 20161019 add by chenyl for 搜索树组件 */
										else if (_treesearch.size() > 0) {
											eleType = 'treesearch';
											console.info("搜索树组属性");
											var _id = '';
											var _name = '';
											var _value = '';
											var _label_name = '';
											var _label_value = '';
											var _title = '';
											var _url = '';
											var _checked = '';
											var _ext_id = '';
											var _is_all = '';
											var _not_allow_select_root = '';
											var _not_allow_select_parent = '';
											var _allow_clear = '';
											var _allow_input = '';
											var _css_style = '';
											var _small_btn = '';
											var _hide_btn = '';
											var _disabled = '';
											var _form_group = '';
											var _left = '';
											var _right = '';
											var _class = '';
											var _treesearch_required = '';
											var _win_height = '';
											var _win_width = '';

											// 搜索树输入框
											var $sys = presentElement
													.closest('div.view')
													.children('div')
													.children('div')
													.children(
															'[input_type="sys:treeselect"]');
											// 表单绑定的后台对象
											var _poSrc = $(this)
													.closest("form").attr(
															"pourl");
											// 是否隐藏标签
											var _hidden = presentElement
													.closest('div.view')
													.children('div').children(
															'label').attr(
															'style');
											var _cl = presentElement.closest(
													'div.view').children('div')
													.attr('class').split(" ");
											// 校验信息
											var _reverse = $sys
													.attr('validators');
											for (var i = 0; i < _cl.length; i++) {
												if (_cl[i] === 'form-group-lg'
														|| _cl[i] === 'form-group-sm'
														|| _cl[i] === 'form-group') {
													// 布局样式
													_form_group = _cl[i];
												}
											}

											// 组件id
											_id = $sys.attr('id');
											// 隐藏域名称（ID）
											_name = $sys.attr('name');
											// 隐藏域值（ID）
											_value = $sys.attr('value');
											// 输入框名称（Name）
											_label_name = $sys
													.attr('label_name');
											// 输入框值（Name）
											_label_value = $sys
													.attr('label_value');
											// 选择框标题
											_title = $sys.attr('title');
											// 树结构数据地址
											_url = $sys.attr('url');
											// 是否显示复选框
											_checked = $sys.attr('checked') == 'checked' ? 'true'
													: 'false';
											// 排除掉的编号
											_ext_id = $sys.attr('ext_id');
											// 是否列出全部数据
											_is_all = $sys.attr('is_all');
											// 不允许选择根节点
											_not_allow_select_root = $sys
													.attr('not_allow_select_root');
											// 不允许选择父节点
											_not_allow_select_parent = $sys
													.attr('not_allow_select_parent');
											// 是否允许清除
											_allow_clear = $sys
													.attr('allow_clear');
											// 文本框可填写
											_allow_input = $sys
													.attr('allow_input');
											// css样式
											_css_style = $sys.attr('css_style');
											// 缩小按钮显示
											_small_btn = $sys.attr('small_btn');
											// 是否显示按钮
											_hide_btn = $sys.attr('hide_btn');
											// 是否限制选择
											_disabled = $sys.attr('disabled');
											// 是否必输
											_treesearch_required = $sys.attr('treesearch_required');
											// 窗体高度
											_win_height = $sys.attr('win_height');
											// 窗体宽度
											_win_width = $sys.attr('win_width');

											// 表单类型
											var _formType = presentElement
													.closest('form').hasClass(
															'form-horizontal'); 

											attrs = "eleType="
													+ eleType
													+ "$$poSrc="
													+ _poSrc
													+ "$$隐藏="
													+ _hidden
													+ "$$reverse="
													+ _reverse
													+ "$$尺寸="
													+ _form_group
													+ "$$id="
													+ _id
													+ "$$name="
													+ _name
													+ "$$value="
													+ _value
													+ "$$label_name="
													+ _label_name
													+ "$$label_value="
													+ _label_value
													+ "$$title="
													+ _title
													+ "$$url="
													+ _url
													+ "$$checked="
													+ _checked
													+ "$$treesearch_required="
													+ _treesearch_required
													+ "$$ext_id="
													+ _ext_id
													+ "$$is_all="
													+ _is_all
													+ "$$not_allow_select_root="
													+ _not_allow_select_root
													+ "$$not_allow_select_parent="
													+ _not_allow_select_parent
													+ "$$allow_clear="
													+ _allow_clear
													+ "$$allow_input="
													+ _allow_input
													+ "$$css_style="
													+ _css_style
													+ "$$small_btn="
													+ _small_btn
													+ "$$hide_btn=" + _hide_btn
													+ "$$disabled=" + _disabled
													+ "$$formtype=" + _formType
													+ "$$win_height=" + _win_height
													+ "$$win_width=" + _win_width;
											
											if (_formType) {
												// 标签占的阑珊数，左占多少
												var _cla = presentElement
														.closest('div.view')
														.children('div')
														.children('label')
														.attr('class').split(
																" ");
												for (var i = 0; i < _cla.length; i++) {
													if (_cla[i].trim() === 'col-sm-1'
															|| _cla[i].trim() === 'col-sm-2'
															|| _cla[i].trim() === 'col-sm-3'
															|| _cla[i].trim() === 'col-sm-4'
															|| _cla[i].trim() === 'col-sm-5'
															|| _cla[i].trim() === 'col-sm-6'
															|| _cla[i].trim() === 'col-sm-7'
															|| _cla[i].trim() === 'col-sm-8'
															|| _cla[i].trim() === 'col-sm-9'
															|| _cla[i].trim() === 'col-sm-10'
															|| _cla[i].trim() === 'col-sm-11'
															|| _cla[i].trim() === 'col-sm-12') {
														_left = _cla[i];
													}
												}
												// 日期输入框占的阑珊数，右占多少
												var _clas = presentElement
														.closest('div.view')
														.children('div')
														.children('div').attr(
																'class').split(
																" ");
												for (var i = 0; i < _clas.length; i++) {
													if (_clas[i].trim() == "col-sm-1"
															|| _clas[i].trim() == "col-sm-2"
															|| _clas[i].trim() == "col-sm-3"
															|| _clas[i].trim() == "col-sm-4"
															|| _clas[i].trim() == "col-sm-5"
															|| _clas[i].trim() == "col-sm-6"
															|| _clas[i].trim() == "col-sm-7"
															|| _clas[i].trim() == "col-sm-8"
															|| _clas[i].trim() == "col-sm-9"
															|| _clas[i].trim() == "col-sm-10"
															|| _clas[i].trim() == "col-sm-11"
															|| _clas[i].trim() == "col-sm-12") {
														_right = _clas[i];
													}

												}
												attrs = attrs + "$$左占=" + _left
														+ "$$右占=" + _right;
											}
											// console.info("treesearch
											// atrr="+attrs);
											sendMessage('parent', attrs);

										}
										/* 20180612 add by chenyl for 图标选择组件属性回显 */
										else if (_iconselect.size() > 0) {
											eleType = 'iconselect';
											console.info("图标选择组件属性回显");
											var _id = '';
											var _name = '';
											var _value = '';
											var _url = '';
											var _icon_required = '';
											var _form_group = '';
											var _icon_css = '';

											// 图标选择组件按钮
											var $sys = presentElement
													.closest('div.view')
													.children('div')
													.children('div')
													.children(
															'[input_type="sys:iconselect"]');
											// 表单绑定的后台对象
											var _poSrc = $(this)
													.closest("form").attr(
															"pourl");
											// 是否隐藏标签
											var _hidden = presentElement
													.closest('div.view')
													.children('div').children(
															'label').attr(
															'style');
											var _cl = presentElement.closest(
													'div.view').children('div')
													.attr('class').split(" ");
											for (var i = 0; i < _cl.length; i++) {
												if (_cl[i] === 'form-group-lg'
														|| _cl[i] === 'form-group-sm'
														|| _cl[i] === 'form-group') {
													// 布局样式
													_form_group = _cl[i];
												}
											}

											// 组件id
											_id = $sys.attr('icon_id');
											// 隐藏域名称（ID）
											_name = $sys.attr('icon_name');
											// 隐藏域值（ID）
											_value = $sys.attr('icon_value');
											// 树结构数据地址
											_url = $sys.attr('icon_url');
											// 自定义CSS
											_icon_css = $sys.attr('icon_css');
											// 是否必输
											_icon_required = $sys.attr('icon_required') == 'true' ? 'true' : 'false';
											
											// 表单类型
											var _formType = presentElement
													.closest('form').hasClass(
															'form-horizontal'); 

											attrs = "eleType="
													+ eleType
													+ "$$poSrc="
													+ _poSrc
													+ "$$隐藏="
													+ _hidden
													+ "$$尺寸="
													+ _form_group
													+ "$$id="
													+ _id
													+ "$$name="
													+ _name
													+ "$$value="
													+ _value
													+ "$$url="
													+ _url
													+ "$$自定义CSS="
													+ _icon_css
													+ "$$icon_required="
													+ _icon_required
													+ "$$formtype=" + _formType;
											
											if (_formType) {
												// 标签占的阑珊数，左占多少
												var _cla = presentElement
														.closest('div.view')
														.children('div')
														.children('label')
														.attr('class').split(
																" ");
												for (var i = 0; i < _cla.length; i++) {
													if (_cla[i].trim() === 'col-sm-1'
															|| _cla[i].trim() === 'col-sm-2'
															|| _cla[i].trim() === 'col-sm-3'
															|| _cla[i].trim() === 'col-sm-4'
															|| _cla[i].trim() === 'col-sm-5'
															|| _cla[i].trim() === 'col-sm-6'
															|| _cla[i].trim() === 'col-sm-7'
															|| _cla[i].trim() === 'col-sm-8'
															|| _cla[i].trim() === 'col-sm-9'
															|| _cla[i].trim() === 'col-sm-10'
															|| _cla[i].trim() === 'col-sm-11'
															|| _cla[i].trim() === 'col-sm-12') {
														_left = _cla[i];
													}
												}
												// 图标选择组件框占的阑珊数，右占多少
												var _clas = presentElement
														.closest('div.view')
														.children('div')
														.children('div').attr(
																'class').split(
																" ");
												for (var i = 0; i < _clas.length; i++) {
													if (_clas[i].trim() == "col-sm-1"
															|| _clas[i].trim() == "col-sm-2"
															|| _clas[i].trim() == "col-sm-3"
															|| _clas[i].trim() == "col-sm-4"
															|| _clas[i].trim() == "col-sm-5"
															|| _clas[i].trim() == "col-sm-6"
															|| _clas[i].trim() == "col-sm-7"
															|| _clas[i].trim() == "col-sm-8"
															|| _clas[i].trim() == "col-sm-9"
															|| _clas[i].trim() == "col-sm-10"
															|| _clas[i].trim() == "col-sm-11"
															|| _clas[i].trim() == "col-sm-12") {
														_right = _clas[i];
													}

												}
												attrs = attrs + "$$左占=" + _left
														+ "$$右占=" + _right;
											}
											 console.info("iconselect atrr="+attrs);
											sendMessage('parent', attrs);

										}
										/* 20180620 add by chenyl for 文件管理组件属性回显 */
										else if (_ckfinder.size() > 0) {
											eleType = 'ckfinder';
											console.info("文件管理组件回显");
											var _id = '';
											var _name = '';
											var _value = '';
											var _type = '';
											var _uploadPath = '';
											var _yearPath = '';
											var _monthPath = '';
											var _isAllUser = '';
											var _required = '';
											var _selectMultiple = '';
											var _readonly = '';
											var _maxWidth = '';
											var _maxHeight = '';
											var _form_group = '';

											// 图标选择组件按钮
											var $sys = presentElement
													.closest('div.view')
													.children('div')
													.children('div')
													.children(
															'[input_type="sys:ckfinder"]');
											// 表单绑定的后台对象
											var _poSrc = $(this)
													.closest("form").attr(
															"pourl");
											// 是否隐藏标签
											var _hidden = presentElement
													.closest('div.view')
													.children('div').children(
															'label').attr(
															'style');
											var _cl = presentElement.closest(
													'div.view').children('div')
													.attr('class').split(" ");
											for (var i = 0; i < _cl.length; i++) {
												if (_cl[i] === 'form-group-lg'
														|| _cl[i] === 'form-group-sm'
														|| _cl[i] === 'form-group') {
													// 布局样式
													_form_group = _cl[i];
												}
											}

											// 组件id
											_id = $sys.attr('ckfinder_id');
											// 隐藏域名称（ID）
											_name = $sys.attr('ckfinder_name');
											// 隐藏域值（ID）
											_value = $sys.attr('ckfinder_value');
											// 文件类型
											_type = $sys.attr('ckfinder_type');
											// 打开文件管理的上传路径
											_uploadPath = $sys.attr('ckfinder_upload_path');
											// 是否生成年份路径
											_yearPath = $sys.attr('ckfinder_year_path') == 'true' ? 'true' : 'false';
											// 是否生成月份路径
											_monthPath = $sys.attr('ckfinder_month_path') == 'true' ? 'true' : 'false';
											// 是否所有用户可见
											_isAllUser = $sys.attr('ckfinder_is_all_user') == 'true' ? 'true' : 'false';
											// 是否必输
											_required = $sys.attr('ckfinder_required') == 'true' ? 'true' : 'false';
											// 是否可以多选
											_selectMultiple = $sys.attr('ckfinder_select_multiple') == 'true' ? 'true' : 'false';
											// 是否查看模式
											_readonly = $sys.attr('ckfinder_readonly') == 'true' ? 'true' : 'false';
											// 最大宽度
											_maxWidth = $sys.attr('ckfinder_max_width');
											// 最大高度
											_maxHeight = $sys.attr('ckfinder_max_height');
											
											// 表单类型
											var _formType = presentElement
													.closest('form').hasClass(
															'form-horizontal'); 

											attrs = "eleType="
													+ eleType
													+ "$$poSrc="
													+ _poSrc
													+ "$$隐藏="
													+ _hidden
													+ "$$尺寸="
													+ _form_group
													+ "$$id="
													+ _id
													+ "$$name="
													+ _name
													+ "$$value="
													+ _value
													+ "$$文件类型="
													+ _type
													+ "$$打开文件管理的上传路径="
													+ _uploadPath
													+ "$$是否生成年份路径="
													+ _yearPath
													+ "$$是否生成月份路径="
													+ _monthPath
													+ "$$是否所有用户可见="
													+ _isAllUser
													+ "$$不为空="
													+ _required
													+ "$$是否可以多选="
													+ _selectMultiple
													+ "$$是否查看模式="
													+ _readonly
													+ "$$最大宽度="
													+ _maxWidth
													+ "$$最大高度="
													+ _maxHeight
													+ "$$formtype=" + _formType;
											
											if (_formType) {
												// 标签占的阑珊数，左占多少
												var _cla = presentElement
														.closest('div.view')
														.children('div')
														.children('label')
														.attr('class').split(
																" ");
												for (var i = 0; i < _cla.length; i++) {
													if (_cla[i].trim() === 'col-sm-1'
															|| _cla[i].trim() === 'col-sm-2'
															|| _cla[i].trim() === 'col-sm-3'
															|| _cla[i].trim() === 'col-sm-4'
															|| _cla[i].trim() === 'col-sm-5'
															|| _cla[i].trim() === 'col-sm-6'
															|| _cla[i].trim() === 'col-sm-7'
															|| _cla[i].trim() === 'col-sm-8'
															|| _cla[i].trim() === 'col-sm-9'
															|| _cla[i].trim() === 'col-sm-10'
															|| _cla[i].trim() === 'col-sm-11'
															|| _cla[i].trim() === 'col-sm-12') {
														_left = _cla[i];
													}
												}
												// 图标选择组件框占的阑珊数，右占多少
												var _clas = presentElement
														.closest('div.view')
														.children('div')
														.children('div').attr(
																'class').split(
																" ");
												for (var i = 0; i < _clas.length; i++) {
													if (_clas[i].trim() == "col-sm-1"
															|| _clas[i].trim() == "col-sm-2"
															|| _clas[i].trim() == "col-sm-3"
															|| _clas[i].trim() == "col-sm-4"
															|| _clas[i].trim() == "col-sm-5"
															|| _clas[i].trim() == "col-sm-6"
															|| _clas[i].trim() == "col-sm-7"
															|| _clas[i].trim() == "col-sm-8"
															|| _clas[i].trim() == "col-sm-9"
															|| _clas[i].trim() == "col-sm-10"
															|| _clas[i].trim() == "col-sm-11"
															|| _clas[i].trim() == "col-sm-12") {
														_right = _clas[i];
													}

												}
												attrs = attrs + "$$左占=" + _left
														+ "$$右占=" + _right;
											}
											 console.info("ckfinder atrr="+attrs);
											sendMessage('parent', attrs);

										}
										/* 201801225 add by chenyl for 隐藏域组件属性回显 */
										else if (_input_hidden.size() > 0) {
											eleType = 'input_hidden';
											console.info("隐藏域组件回显");
											var _id = '';
											var _name = '';
											var _value = '';
											var _required = '';

											// 图标选择组件按钮
											var $sys = presentElement
													.closest('div.view').children('[input_type="hidden"]');

											// 组件id
											_id = $sys.attr('hidden_id');
											// 隐藏域名称（ID）
											_name = $sys.attr('hidden_name');
											// 隐藏域值（ID）
											_value = $sys.attr('hidden_value');
											// 是否必输
											_required = $sys.attr('hidden_required') == 'true' ? 'true' : 'false';

											attrs = "eleType="
													+ eleType
													+ "$$id="
													+ _id
													+ "$$name="
													+ _name
													+ "$$value="
													+ _value
													+ "$$不为空="
													+ _required;
											
											 console.info("hidden atrr="+attrs);
											sendMessage('parent', attrs);

										} else {
											eleType = 'input';
											var _id = $(this).attr("id");
											var _type = $(this).attr("type");
											var _btn_type = $(this).attr(
													"btn-type"); // 20160929
																	// add by
																	// chenyl
																	// 按钮类型特殊处理
											var _class = $(this).attr("class");
											var _name = $(this).attr("name");
											var _style = $(this).attr("style");
											var _value = $(this).attr("value");
											var _poSrc = $(this)
													.closest("form").attr(
															"pourl");
											var _hidden = $(this).parents(
													'div.view').children('div')
													.children('label').attr(
															'style');
											// 禁用
											var _forbidden = $(this).attr(
													"disabled");
											// 只读
											var _readonly = $(this).attr(
													"readonly");
											// 注解
											var _hint = $(this).attr(
													"placeholder");
											// 前裹类型
											var _front = $(this).attr(
													'prevtype');
											var _t = $(this).prev().children();
											var _frontText = '';
											var _reverse = reverse(presentElement);
											if (_front === 'dropmenu'
													|| _front === 'splitdropmenu') {
												var _tw = $(this).prev().find(
														'ul').children();
												for (var i = 0; i < _tw.length; i++) {
													_frontText += _tw.eq(i)
															.text()
															+ '\n';
												}
											} else {
												for (var i = 0; i < _t.length; i++) {
													_frontText += _t.eq(i)
															.text()
															+ '\n';
												}
											}
											// 后裹类型
											var _behind = $(this).attr(
													'nexttype');
											var _d = $(this).next().children();
											var _behindText = '';
											if (_behind === 'dropmenu'
													|| _behind === 'splitdropmenu') {
												var _dw = $(this).next().find(
														'ul').children();
												for (var i = 0; i < _dw.length; i++) {
													_behindText += _dw.eq(i)
															.text()
															+ '\n';
												}
											} else {
												for (var i = 0; i < _d.length; i++) {
													_behindText += _d.eq(i)
															.text()
															+ '\n';
												}
											}
											var _form_group = '';
											var _clas = $(this).closest(
													'div.view').children('div')
													.attr('class').split(" ");
											for (var i = 0; i < _clas.length; i++) {
												if (_clas[i] === 'form-group') {
													_form_group = _clas[i];
												}
											}
											var _col_sm_input = undefined;
											var _col_sm_label = undefined;
											var _formType = $(this).closest(
													'form').hasClass(
													'form-horizontal');
											/* 20180313 add by chenyl for 回显示新校验设置属性 */
											/*不为空*/
											var _check_empty = $(this).attr("check-empty");
											/*最大长度*/
											var _maxlength = $(this).attr("maxlength");
											/*最小长度*/
											var _check_minlength = $(this).attr("check-minlength");
											/*最小范围*/
											var _min = $(this).attr("min");
											/*最大范围*/
											var _max = $(this).attr("max");
											/*电话号码*/
											var _check_telephone = $(this).attr("check-telephone");
											/*身份证号码*/
											var _check_idcard = $(this).attr("check-idcard");
											/*中文*/
											var _check_chinese = $(this).attr("check-chinese");
											/*IP地址*/
											var _check_ipaddress = $(this).attr("check-ipaddress");
											/*版本号*/
											var _check_edition = $(this).attr("check-edition");
											/*币种，只能输入英文*/
											var _check_english = $(this).attr("check-english");
											/*英文、数字、特殊字符*/
											var _check_character = $(this).attr("check-character");
											/*密码*/
											var _check_password = $(this).attr("check-password");
											/*英文数字下划线*/
											var _check_alphanumericsymbols = $(this).attr("check-alphanumericsymbols");
											/*中文数字横线中括号*/
											var _check_chinesedigitalsymbols = $(this).attr("check-chinesedigitalsymbols");
											/*单选框属性/复选框属性*/
											var _checkbtn = $(this).attr("checkbtn");
											
											var attrs = '';
											if (_formType) {
												var cla = $(this).closest(
														'div.view').children(
														'div').children('div')
														.attr('class').split(
																" ");
												for (var i = 0; i < cla.length; i++) {
													if (cla[i] === 'col-sm-1'
															|| cla[i] === 'col-sm-2'
															|| cla[i] === 'col-sm-3'
															|| cla[i] === 'col-sm-4'
															|| cla[i] === 'col-sm-5'
															|| cla[i] === 'col-sm-6'
															|| cla[i] === 'col-sm-7'
															|| cla[i] === 'col-sm-8'
															|| cla[i] === 'col-sm-9'
															|| cla[i] === 'col-sm-10'
															|| cla[i] === 'col-sm-11'
															|| cla[i] === 'col-sm-12') {
														_col_sm_input = cla[i];
													}
												}
												var _cla = $(this).closest(
														'div.view').children(
														'div')
														.children('label')
														.attr('class').split(
																" ");
												for (var i = 0; i < _cla.length; i++) {
													if (_cla[i] === 'col-sm-1'
															|| _cla[i] === 'col-sm-2'
															|| _cla[i] === 'col-sm-3'
															|| _cla[i] === 'col-sm-4'
															|| _cla[i] === 'col-sm-5'
															|| _cla[i] === 'col-sm-6'
															|| _cla[i] === 'col-sm-7'
															|| _cla[i] === 'col-sm-8'
															|| _cla[i] === 'col-sm-9'
															|| _cla[i] === 'col-sm-10'
															|| _cla[i] === 'col-sm-11'
															|| _cla[i] === 'col-sm-12') {
														_col_sm_label = _cla[i]
																+ " control-label";
													}
												}
												attrs = "id=" + _id + ","
														+ "type=" + _type + ","
														+ "btn-type="
														+ _btn_type
														+ ",reverse="
														+ _reverse + ","
														+ "class=" + _class
														+ ",poSrc=" + _poSrc
														+ "," + "name=" + _name
														+ "," + "eleType=input"
														+ ",style=" + _style
														+ ",右占="
														+ _col_sm_input
														+ ",左占="
														+ _col_sm_label
														+ ",尺寸=" + _form_group
														+ ",value=" + _value
														+ ",禁用=" + _forbidden
														+ ",只读=" + _readonly
														+ ",注解=" + _hint
														+ ",前裹类型=" + _front
														+ ",后裹类型=" + _behind
														+ ",隐藏=" + _hidden
														+ ",前裹内容=" + _frontText
														+ ",后裹内容="
														+ _behindText
														+ ",formtype="
														+ _formType
														/*20180313 add by chenyl for 获取新增新校验属性*/
														+ ",_check_empty="
														+ _check_empty
														+ ",_maxlength="
														+ _maxlength
														+ ",_check_minlength="
														+ _check_minlength
														+ ",_min="
														+ _min
														+ ",_max="
														+ _max
														+ ",_check_telephone="
														+ _check_telephone
														+ ",_check_idcard="
														+ _check_idcard
														+ ",_check_chinese="
														+ _check_chinese
														+ ",_check_ipaddress="
														+ _check_ipaddress
														+ ",_check_edition="
														+ _check_edition
														+ ",_check_english="
														+ _check_english
														+ ",_check_character="
														+ _check_character
														+ ",_check_password="
														+ _check_password
														+ ",_check_alphanumericsymbols="
														+ _check_alphanumericsymbols
														+ ",_check_chinesedigitalsymbols="
														+ _check_chinesedigitalsymbols
														+ ",_checkbtn="
														+ _checkbtn;
											} else {
												attrs = "id=" + _id + ","
														+ "type=" + _type + ","
														+ "btn-type="
														+ _btn_type
														+ ",reverse="
														+ _reverse + ","
														+ "class=" + _class
														+ ",poSrc=" + _poSrc
														+ "," + "name=" + _name
														+ "," + "eleType=input"
														+ ",style=" + _style
														+ ",尺寸=" + _form_group
														+ ",value=" + _value
														+ ",禁用=" + _forbidden
														+ ",只读=" + _readonly
														+ ",注解=" + _hint
														+ ",前裹类型=" + _front
														+ ",后裹类型=" + _behind
														+ ",隐藏=" + _hidden
														+ ",前裹内容=" + _frontText
														+ ",后裹内容="
														+ _behindText
														+ ",formtype="
														+ _formType
														/*20180313 add by chenyl for 获取新增新校验属性*/
														+ ",_check_empty="
														+ _check_empty
														+ ",_maxlength="
														+ _maxlength
														+ ",_check_minlength="
														+ _check_minlength
														+ ",_min="
														+ _min
														+ ",_max="
														+ _max
														+ ",_check_telephone="
														+ _check_telephone
														+ ",_check_idcard="
														+ _check_idcard
														+ ",_check_chinese="
														+ _check_chinese
														+ ",_check_ipaddress="
														+ _check_ipaddress
														+ ",_check_edition="
														+ _check_edition
														+ ",_check_english="
														+ _check_english
														+ ",_check_character="
														+ _check_character
														+ ",_check_password="
														+ _check_password
														+ ",_check_alphanumericsymbols="
														+ _check_alphanumericsymbols
														+ ",_check_chinesedigitalsymbols="
														+ _check_chinesedigitalsymbols
														+ ",_checkbtn="
														+ _checkbtn;
											}
											sendMessage('parent', attrs);
										}

									});

					$(".ui-sortable")
							.delegate(
									"label",
									"dblclick",
									function(e) {
										e.preventDefault();
										e.stopPropagation();
										presentElement = $(this);
										addSelectCls(presentElement);
										var _id = $(this).attr("id");
										var _class = $(this).attr("class");
										var _name = $(this).attr("name");
										var _style = $(this).attr("style");
										var _wenben = $(this).html();
										var _value = $(this).attr("value");
										var regex = buildRegex('<[ ]*.+"[ ]*>',
												'gi');

										/*
										 * <[ ]*\w+[ ]+.+[ ]*> 匹配：<input
										 * type="radio" name="optionsRadios"
										 * id="optionsRadios1" value="option1"
										 * checked >
										 * 
										 *  <[ ]*\w+[ ]+.+"[ ]*>[ ]*</.+> 匹配：<
										 * i class=" glyphicon
										 * glyphicon-align-left " > </ i >
										 */
										var s = _wenben.replace(regex, '');
										regex = buildRegex('[\\t\\n]', 'gi');
										_wenben = s.replace(regex, '').trim();

										if (presentElement.find('input').length > 0) {
											eleType = 'radioCheckbox';
											var _hidden = _style
													.indexOf('visibility:hidden') > -1 ? _hidden = "visibility:hidden"
													: _hidden = "visibility:visible";
											var _poSrc = $(this)
													.closest("form").attr(
															"pourl");
											var attr = "id=" + _id + ",name="
													+ _name + ",poSrc="
													+ _poSrc + ",value="
													+ _value + ",style="
													+ _style + ",eleType="
													+ eleType + ",文本="
													+ _wenben + ",隐藏="
													+ _hidden;
											sendMessage('parent', attr);
										} else {
											eleType = 'label';
											var attrs = "id=" + _id + ","
													+ "class=" + _class + ","
													+ "name=" + _name + ","
													+ "eleType=" + eleType
													+ ",style=" + _style
													+ ",wenben=" + _wenben;
											sendMessage('parent', attrs);
										}

									});

					//	
					// $(".ui-sortable").delegate("label", "dblclick",
					// function(e){
					// e.preventDefault();
					// e.stopPropagation();
					// presentElement = $(this);
					// var _id=$(this).attr("id");
					// var _class=$(this).attr("class");
					// var _name=$(this).attr("name");
					// var _style=$(this).attr("style");
					// var _wenben=$(this).html();
					// var _value = $(this).attr("value");
					// var regex = buildRegex('<[ ]*.+"[ ]*>','gi');
					//		
					// /*
					// * <[ ]*\w+[ ]+.+[ ]*>
					// * 匹配：<input type="radio" name="optionsRadios"
					// id="optionsRadios1" value="option1" checked >
					// *
					// *
					// * <[ ]*\w+[ ]+.+"[ ]*>[ ]*</.+>
					// * 匹配：< i class=" glyphicon glyphicon-align-left " > </ i
					// >
					// */
					// var s = _wenben.replace(regex, '');
					// regex = buildRegex('[\\t\\n]','gi');
					// _wenben = s.replace(regex, '').trim();
					//	
					// if(presentElement.find('input').length>0){
					// eleType = 'radioCheckbox';
					// var _hidden = _style.contains('visibility:hidden')?
					// _hidden="visibility:hidden":_hidden="visibility:visible";
					// var _poSrc=$(this).closest("form").attr("pourl");
					// var attr =
					// "id="+_id+",name="+_name+",poSrc="+_poSrc+",value="+_value+",style="+_style+",eleType="+eleType+",文本="+_wenben+",隐藏="+_hidden;
					// sendMessage('parent',attr);
					// }else{
					// eleType = 'label';
					// var
					// attrs="id="+_id+","+"class="+_class+","+"name="+_name+","+"eleType="+eleType+",style="+_style+",wenben="+_wenben;
					// sendMessage('parent',attrs);
					// }
					//		
					// });

					$(".ui-sortable")
							.delegate(
									"textarea",
									"dblclick",
									function(e) {
										e.preventDefault();
										e.stopPropagation();
										presentElement = $(this);
										addSelectCls(presentElement);
										eleType = 'textarea';
										var _hidden = $(this).closest(
												'div.view').children('div')
												.children('label')
												.attr('style');
										// 尺寸
										var _form_group = "";
										var _poSrc = $(this).closest("form")
												.attr("pourl");
										var _cl = $(this).closest('div.view')
												.children('div').attr('class')
												.split(" ");
										for (var i = 0; i < _cl.length; i++) {
											if (_cl[i] === 'form-group-lg'
													|| _cl[i] === 'form-group-sm') {
												_form_group = _cl[i];
											}
										}
										var _id = $(this).attr("id");
										var _name = $(this).attr("name");
										var _style = $(this).attr("style");
										var _value = $(this).attr("value");
										var _class = $(this).attr("class");
										// 行数
										var _rows = $(this).attr("rows");
										// 注解
										var _placeholder = $(this).attr(
												"placeholder");
										// 禁用
										var _forbidden = $(this).attr(
												"disabled");
										// 只读
										var _readonly = $(this)
												.attr("readonly");
										var _formType = $(this).closest('form')
												.hasClass('form-horizontal');
										var attrs = '';
										var _reverse = reverse(presentElement);
										if (_formType) {
											// 右占
											var _right = "";
											var _cla = $(this).closest(
													'div.view').children('div')
													.children('label').attr(
															'class').split(" ");
											for (var i = 0; i < _cla.length; i++) {
												if (_cla[i] === 'col-sm-1'
														|| _cla[i] === 'col-sm-2'
														|| _cla[i] === 'col-sm-3'
														|| _cla[i] === 'col-sm-4'
														|| _cla[i] === 'col-sm-5'
														|| _cla[i] === 'col-sm-6'
														|| _cla[i] === 'col-sm-7'
														|| _cla[i] === 'col-sm-8'
														|| _cla[i] === 'col-sm-9'
														|| _cla[i] === 'col-sm-10'
														|| _cla[i] === 'col-sm-11'
														|| _cla[i] === 'col-sm-12') {
													_right = _cla[i];
												}
											}
											// 左占
											var _left = "";
											var _clas = $(this).closest(
													'div.view').children('div')
													.children('div').attr(
															'class').split(" ");
											for (var i = 0; i < _clas.length; i++) {
												if (_clas[i] === 'col-sm-1'
														|| _clas[i] === 'col-sm-2'
														|| _clas[i] === 'col-sm-3'
														|| _clas[i] === 'col-sm-4'
														|| _clas[i] === 'col-sm-5'
														|| _clas[i] === 'col-sm-6'
														|| _clas[i] === 'col-sm-7'
														|| _clas[i] === 'col-sm-8'
														|| _clas[i] === 'col-sm-9'
														|| _clas[i] === 'col-sm-10'
														|| _clas[i] === 'col-sm-11'
														|| _clas[i] === 'col-sm-12') {
													_left = _clas[i]
															+ ' control-label';
												}
											}
											attrs = "左占=" + _left + ",右占="
													+ _right + ",尺寸="
													+ _form_group + ",id="
													+ _id + ",name=" + _name
													+ ",reverse=" + _reverse
													+ ",poSrc=" + _poSrc
													+ ",style=" + _style
													+ ",value=" + _value
													+ ",class=" + _class
													+ ",行数=" + _rows + ",注解="
													+ _placeholder + ",禁用="
													+ _forbidden + ",只读="
													+ _readonly
													+ ",eleType=textarea"
													+ ",隐藏=" + _hidden
													+ ",formtype=" + _formType;

										} else {
											attrs = "尺寸=" + _form_group
													+ ",id=" + _id + ",name="
													+ _name + ",reverse="
													+ _reverse + ",poSrc="
													+ _poSrc + ",style="
													+ _style + ",value="
													+ _value + ",class="
													+ _class + ",行数=" + _rows
													+ ",注解=" + _placeholder
													+ ",禁用=" + _forbidden
													+ ",只读=" + _readonly
													+ ",eleType=textarea"
													+ ",隐藏=" + _hidden
													+ ",formtype=" + _formType;
										}
										sendMessage('parent', attrs);
									});

					$(".ui-sortable").delegate("select", "click", function(e) {
						e.preventDefault();
						e.stopPropagation();
						presentElement = $(this);
						addSelectCls(presentElement);
						addSelectCls(presentElement);
						// TODO
						var attrs = select_attr(presentElement);
						sendMessage('parent', attrs);
					});

					$(".ui-sortable").delegate(
							":header",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								addSelectCls(presentElement);
								eleType = 'h3';
								var _class = $(this).attr("class");
								var _sign = "";
								var _align = "";
								var _controlcase = "";
								var _style = $(this).attr("style");
								var arrSign = new Array("text-muted",
										"text-success", "text-error",
										"text-info", "text-warning",
										"text-danger");
								var arrAlign = new Array("text-left",
										"text-center", "text-right");
								var arrCase = new Array("text-lowercase",
										"text-uppercase", "text-capitalize");
								if (_class != undefined) {
									for (var i = 0; i < arrSign.length; i++) {
										if (_class.indexOf(arrSign[i]) >= 0) {
											_sign = arrSign[i];
										}
									}
								}
								if (_class != undefined) {
									for (var i = 0; i < arrAlign.length; i++) {
										if (_class.indexOf(arrAlign[i]) >= 0) {
											_align = arrAlign[i];
										}
									}
								}
								if (_class != undefined) {
									for (var i = 0; i < arrCase.length; i++) {
										if (_class.indexOf(arrCase[i]) >= 0) {
											_controlcase = arrCase[i];
										}
									}
								}
								var attrs = "eleType=" + eleType + ",style="
										+ _style + ",sign=" + _sign + ",align="
										+ _align + ",controlcase="
										+ _controlcase;
								sendMessage('parent', attrs);
							});

					$(".ui-sortable")
							.delegate(
									"p",
									"dblclick",
									function(e) {
										e.preventDefault();
										e.stopPropagation();
										presentElement = $(this);
										addSelectCls(presentElement);
										var attrs = '';
										var _type = presentElement.parents(
												"div.view").attr('id');
										if (_type === 'slidel') {
											eleType = 'imgp';
											var _eidt = presentElement.text();
											attrs = "eleType=" + eleType
													+ ",,编辑=" + _eidt;
										} else {
											eleType = 'p';
											var _class = $(this).attr("class");
											var _sign = "";
											var _align = "";
											var _controlcase = "";
											var _leadd = "";
											var _style = $(this).attr("style");
											var arrSign = new Array(
													"text-muted",
													"text-success",
													"text-error", "text-info",
													"text-warning",
													"text-danger");
											var arrAlign = new Array(
													"text-left", "text-center",
													"text-right");
											var arrCase = new Array(
													"text-lowercase",
													"text-uppercase",
													"text-capitalize");
											if (_class != undefined) {
												for (var i = 0; i < arrSign.length; i++) {
													if (_class
															.indexOf(arrSign[i]) >= 0) {
														_sign = arrSign[i];
													}
												}
												for (var i = 0; i < arrAlign.length; i++) {
													if (_class
															.indexOf(arrAlign[i]) >= 0) {
														_align = arrAlign[i];
													}
												}
												for (var i = 0; i < arrCase.length; i++) {
													if (_class
															.indexOf(arrCase[i]) >= 0) {
														_controlcase = arrCase[i];
													}
												}
												if (_class.indexOf("lead") >= 0) {
													_leadd = "lead";
												}
											}
											attrs = "eleType=" + eleType
													+ ",style=" + _style
													+ ",sign=" + _sign
													+ ",align=" + _align
													+ ",controlcase="
													+ _controlcase + ",leadd="
													+ _leadd;
										}
										sendMessage('parent', attrs);
									});

					$(".ui-sortable").delegate(
							"address",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								addSelectCls(presentElement);
								eleType = 'address';
								var _class = $(this).attr("class");
								var _align = "";
								var arrAlign = new Array("text-left",
										"text-center", "text-right");
								var _style = $(this).attr("style");
								if (_class != undefined) {
									for (var i = 0; i < arrAlign.length; i++) {
										if (_class.indexOf(arrAlign[i]) >= 0) {
											_align = arrAlign[i];
										}
									}
								}
								var attrs = "eleType=" + eleType + ",style="
										+ _style + ",align=" + _align;
								sendMessage('parent', attrs);
							});
					$(".ui-sortable").delegate(
							"a",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								addSelectCls(presentElement);
								var attrs = a_attr(presentElement);
								sendMessage('parent', attrs);
								console.info('------------a------------'
										+ $(this).text());
							});
					$(".ui-sortable").delegate(
							"dl",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								addSelectCls(presentElement);
								eleType = 'dl';
								var _class = $(this).attr("class");
								var _align = "";
								var arrAlign = new Array("text-left",
										"text-center", "text-right");
								var _style = $(this).attr("style");
								if (_class != undefined) {
									for (var i = 0; i < arrAlign.length; i++) {
										if (_class.indexOf(arrAlign[i]) >= 0) {
											_align = arrAlign[i];
										}
									}
								}
								var attrs = "eleType=" + eleType + ",style="
										+ _style + ",align=" + _align;
								sendMessage('parent', attrs);
							});

					$(".ui-sortable").delegate(
							"span",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								addSelectCls(presentElement);
								eleType = 'span';
								var _class = $(this).attr("class");
								var _sign = "";
								var _float = "";
								var _style = $(this).attr("style");
								var arrSign = new Array("label-default",
										"label-primary", "label-success",
										"label-info", "label-warning",
										"label-danger");
								var arrFloat = new Array("pull-left",
										"pull-right");
								if (_class != undefined) {
									for (var i = 0; i < arrSign.length; i++) {
										if (_class.indexOf(arrSign[i]) >= 0) {
											_sign = arrSign[i];
										}
									}
									for (var i = 0; i < arrFloat.length; i++) {
										if (_class.indexOf(arrFloat[i]) >= 0) {
											_float = arrFloat[i];
										}
									}
								}
								var attrs = "eleType=" + eleType + ",style="
										+ _style + ",sign=" + _sign + ",float="
										+ _float;
								sendMessage('parent', attrs);
							});

					$(".ui-sortable").delegate(
							"img",
							"dblclick",
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								addSelectCls(presentElement);
								var attrs = '';
								/* 20180618 add by chenyl for 新增处理富文本编辑器属性回显 */
								var _ueditor = presentElement.closest('[input_type="ueditor"]');
								if(_ueditor.size()>0){
									eleType = 'ueditor';
									console.info("富文本编辑器组件属性回显");
									var _id = '';
									var _name = '';
									var _value = '';
									var _style = '';
									var _form_group = '';
									
									// 图标选择组件按钮
									var $sys = presentElement
											.closest('div.view')
											.children('div')
											.children('div')
											.children(
													'[input_type="ueditor"]');
									// 表单绑定的后台对象
									var _poSrc = $(this).closest("form").attr("pourl");
									// 是否隐藏标签
									var _hidden = presentElement.closest('div.view').children('div').children('label').attr('style');
									var _cl = presentElement.closest('div.view').children('div').attr('class').split(" ");
									for (var i = 0; i < _cl.length; i++) {
										if (_cl[i] === 'form-group-lg'
												|| _cl[i] === 'form-group-sm'
												|| _cl[i] === 'form-group') {
											// 布局样式
											_form_group = _cl[i];
										}
									}

									// 组件id
									_id = $sys.attr('ue_id');
									// 隐藏域名称（ID）
									_name = $sys.attr('ue_name');
									// 隐藏域值（ID）
									_value = $sys.attr('ue_value');
									// 树结构数据地址
									_style = $sys.attr('ue_style');
									
									// 表单类型
									var _formType = presentElement
											.closest('form').hasClass(
													'form-horizontal'); 

									attrs = "eleType="
											+ eleType
											+ "$$poSrc="
											+ _poSrc
											+ "$$隐藏="
											+ _hidden
											+ "$$尺寸="
											+ _form_group
											+ "$$id="
											+ _id
											+ "$$name="
											+ _name
											+ "$$value="
											+ _value
											+ "$$style="
											+ _style
											+ "$$formtype=" + _formType;
									
									if (_formType) {
										// 标签占的阑珊数，左占多少
										var _cla = presentElement.closest('div.view').children('div').children('label').attr('class').split(" ");
										for (var i = 0; i < _cla.length; i++) {
											if (_cla[i].trim() === 'col-sm-1'
													|| _cla[i].trim() === 'col-sm-2'
													|| _cla[i].trim() === 'col-sm-3'
													|| _cla[i].trim() === 'col-sm-4'
													|| _cla[i].trim() === 'col-sm-5'
													|| _cla[i].trim() === 'col-sm-6'
													|| _cla[i].trim() === 'col-sm-7'
													|| _cla[i].trim() === 'col-sm-8'
													|| _cla[i].trim() === 'col-sm-9'
													|| _cla[i].trim() === 'col-sm-10'
													|| _cla[i].trim() === 'col-sm-11'
													|| _cla[i].trim() === 'col-sm-12') {
												_left = _cla[i];
											}
										}
										// 图标选择组件框占的阑珊数，右占多少
										var _clas = presentElement
												.closest('div.view')
												.children('div')
												.children('div').attr(
														'class').split(
														" ");
										for (var i = 0; i < _clas.length; i++) {
											if (_clas[i].trim() == "col-sm-1"
													|| _clas[i].trim() == "col-sm-2"
													|| _clas[i].trim() == "col-sm-3"
													|| _clas[i].trim() == "col-sm-4"
													|| _clas[i].trim() == "col-sm-5"
													|| _clas[i].trim() == "col-sm-6"
													|| _clas[i].trim() == "col-sm-7"
													|| _clas[i].trim() == "col-sm-8"
													|| _clas[i].trim() == "col-sm-9"
													|| _clas[i].trim() == "col-sm-10"
													|| _clas[i].trim() == "col-sm-11"
													|| _clas[i].trim() == "col-sm-12") {
												_right = _clas[i];
											}

										}
										attrs = attrs + "$$左占=" + _left
												+ "$$右占=" + _right;
									}
									console.info("ueditor atrr="+attrs);
								}else{
									eleType = 'img';
									var _class = $(this).attr("class");
									var _sign = "", _float = "";
									var _imgType = "";
									var _src = $(this).attr("src");
									var arrSign = new Array("img-rounded",
											"img-circle", "img-thumbnail");
									var arrFloat = new Array("pull-left",
											"center-block", "pull-right");
									var _style = $(this).attr("style");
									if (_class != undefined) {
										for (var i = 0; i < arrSign.length; i++) {
											if (_class.indexOf(arrSign[i]) >= 0) {
												_sign = arrSign[i];
											}
										}
										if (_class.indexOf("img-responsive") >= 0) {
											_imgType = "img-responsive";
										}
										for (var i = 0; i < arrFloat.length; i++) {
											if (_class.indexOf(arrFloat[i]) >= 0) {
												_float = arrFloat[i];
											}
										}
									}
									attrs = "src=" + _src + "," + "eleType="
									+ eleType + ",style=" + _style
									+ ",sign=" + _sign + ",imgType="
									+ _imgType + ",float=" + _float;
								}
								sendMessage('parent', attrs);
							});
					$(".ui-sortable").delegate(
							".table thead tr th",
							"dblclick",
							function(e) {
								// 表格中的columns添加双击事件
								e.preventDefault();
								e.stopPropagation();
								presentElement = $(this);
								addSelectCls(presentElement);
								eleType = 'tabCol';
								var _radio = presentElement.attr('data-radio');
								var _checkbox = presentElement
										.attr('data-checkbox');
								var _field = presentElement.attr('data-field');
								var _title = presentElement.attr('data-title');
								var _tooltip = presentElement
										.attr('data-title-tooltip');
								var _class = presentElement.attr('data-class');
								var _rowspan = presentElement
										.attr('data-rowspan');
								var _colspan = presentElement
										.attr('data-colspan');
								var _align = presentElement.attr('data-align');
								var _hAlign = presentElement
										.attr('data-halign');
								var _fAlign = presentElement
										.attr('data-falign');
								var _vAlign = presentElement
										.attr('data-valign');
								var _width = presentElement.attr('data-width');
								var _sortable = presentElement
										.attr('data-sortable');
								var _order = presentElement.attr('data-order');
								var _visible = presentElement
										.attr('data-visible');
								var _cardVis = presentElement
										.attr('data-card-visible');
								var _sAbl = presentElement
										.attr('data-switchable');
								var _cToSel = presentElement
										.attr('data-click-to-select');
								var _format = presentElement
										.attr('data-formatter');
								var _fFormat = presentElement
										.attr('data-footer-formatter');
								var _events = presentElement
										.attr('data-events');
								var _sorter = presentElement
										.attr('data-sorter');
								var _sortName = presentElement
										.attr('data-sort-name');
								var _cellSty = presentElement
										.attr('data-cell-style');
								var _searchable = presentElement
										.attr('data-searchable');
								var _sFormat = presentElement
										.attr('data-search-formatter');
								var attrs = "eleType=" + eleType + ",sFormat="
										+ _sFormat + ",searchable="
										+ _searchable + ",cellSty=" + _cellSty
										+ ",sortName=" + _sortName + ",sorter="
										+ _sorter + ",events=" + _events
										+ ",fFormat=" + _fFormat + ",format="
										+ _format + ",cToSel=" + _cToSel
										+ ",sAbl=" + _sAbl + ",cardVis="
										+ _cardVis + ",visible=" + _visible
										+ ",order=" + _order + ",sortable="
										+ _sortable + ",width=" + _width
										+ ",align=" + _align + ",hAlign="
										+ _hAlign + ",fAlign=" + _fAlign
										+ ",vAlign=" + _vAlign + ",title="
										+ _title + ",radio=" + _radio
										+ ",checkbox=" + _checkbox + ",field="
										+ _field + ",tooltip=" + _tooltip
										+ ",class=" + _class + ",rowspan="
										+ _rowspan + ",colspan=" + _colspan;
								sendMessage('parent', attrs);
							});
					/*
					 * 联合组件获取
					 */
					$(".ui-sortable")
							.delegate(
									"div .view",
									"dblclick",
									function(e) {
										e.preventDefault();
										e.stopPropagation();
										presentElement = $(this);
										addSelectCls(presentElement);
										// 判断组件类型
										eleType = presentElement.attr('id');
										console
												.info('-----------------eletype-----------');
										console.info(eleType);
										if (eleType === 'btn_group') {
											btnGroup();
										} else if (eleType === 'down_menu') {
											// TODO 下拉菜单
											downMenu();
										} else if (eleType === 'navbar') {
											navbar();// 导航栏
										} else if (eleType == 'navigations') {
											// TODO 导航
											navigation();
										} else if (eleType == 'path_navigation') {
											// TODO 路径导航
											pathNavigation();
										} else if (eleType == 'paging') {
											// TODO 分页
											paging();
										} else if (eleType === 'btn_toolbar') {
											btnToolbar();
										} else if (eleType === 'turnPage') {
											// 翻页
											turnPage();
										} else if (eleType === 'progressbar') {
											// 进度条
											progressBar();
										} else if (eleType === 'checkbox'
												|| eleType === 'radio') { // 多选框
																			// 或者
																			// 单选框
											radioAndCheckboxBoard();
										} else if (eleType === 'panel') {
											// 面板
											panel();
										} else if (eleType === 'biaoge') {
											// 表格
											table();
										} else if (eleType === 'thumbnail') {
											// 缩略图
											thumbnail();
										} else if (eleType === 'listGroup') {
											// 列表组
											listGroup();
										} else if (eleType === 'media') {
											// 媒体
											media();
										} else if (eleType === 'jumbotron') {
											// 巨幕
											jumbotron();
										} else if (eleType === 'hint') { // 提示框
											hint();
										} else if (eleType === 'fold') { // 手风琴
											fold();
										} else if (eleType === 'slidel') { // 幻灯片
											slidel();
										} else if (eleType === 'tab') {
											// 选项卡
											tab();
										}
										/*
										 * else if(eleType ==='time'){ // 选项卡
										 * time(); }
										 */
										else if (eleType === 'fileInput') {
											fileInput();
										}
									});

					// 图片上传组
					function fileInput() {
						eleType = "fileInput";
						var t = presentElement.find(".file");

						var _showPreview = t.attr("data-show-preview");
						var _showUpload = t.attr("data-show-upload");
						var _showRemove = t.attr("data-show-remove");
						var _showCancel = t.attr("data-show-cancel");
						var _showCaption = t.attr("data-show-caption");
						var _readonly = t.attr("readonly");
						var _disabled = t.attr("disabled");
						var _minFileCount = t.attr("data-min-file-count");
						var _maxFileCount = t.attr("data-max-file-count");
						var _allowedFile = t
								.attr("data-allowed-file-extensions");
						var _allowedFileType = t
								.attr("data-allowed-file-types");
						var _allowedPriview = t
								.attr("date-allowed-preview-types");
						attrs = "eleType=" + eleType + ",是否预览=" + _showPreview
								+ ",是否显示删除=" + _showRemove + ",是否显示取消="
								+ _showCancel + ",是否显示上传=" + _showUpload
								+ ",是否显示标题=" + _showCaption + ",只读="
								+ _readonly + ",不可用=" + _disabled + ",最小上传数量="
								+ _minFileCount + ",最大上传数量=" + _maxFileCount
								+ ",接收的文件后缀=" + _allowedFile + ",接收的文件类型="
								+ _allowedFileType + ",预览文件类型="
								+ _allowedPriview;
						sendMessage('parent', attrs);
					}

					/*
					 * 栅格系统
					 * 
					 */
					$(".ui-sortable")
							.delegate(
									".column",
									"dblclick",
									function(e) {
										console
												.info('--------------.column-----------');
										e.preventDefault();
										e.stopPropagation();
										presentElement = $(this);
										addSelectCls(presentElement);
										var attrs = grid_attrs(presentElement);
										sendMessage('parent', attrs);
									});

					$(".ui-sortable")
							.delegate(
									".row",
									"dblclick",
									function(e) {
										e.preventDefault();
										e.stopPropagation();
										eleType = 'grid';
										presentElement = $(this);
										addSelectCls(presentElement);
										var cl = presentElement.attr('class');
										var cls = cl.split(' ');

										// 正则表达式 列布局
										// 列偏移
										var regex_col_xs = buildRegex(
												'col-xs-[\\d]{1,2}', 'gi');
										var regex_col_sm = buildRegex(
												'col-sm-[\\d]{1,2}', 'gi');
										var regex_col_md = buildRegex(
												'col-md-[\\d]{1,2}', 'gi');
										var regex_col_lg = buildRegex(
												'col-lg-[\\d]{1,2}', 'gi');
										// 列隐藏
										var regex_col_xs_offset = buildRegex(
												'col-xs-offset-[\\d]{1,2}',
												'gi');
										var regex_col_sm_offset = buildRegex(
												'col-sm-offset-[\\d]{1,2}',
												'gi');
										var regex_col_md_offset = buildRegex(
												'col-md-offset-[\\d]{1,2}',
												'gi');
										var regex_col_lg_offset = buildRegex(
												'col-lg-offset-[\\d]{1,2}',
												'gi');

										// 列布局
										var col_xs = '';
										var col_sm = '';
										var col_md = '';
										var col_lg = '';
										// 列偏移
										var col_xs_offset = '';
										var col_sm_offset = '';
										var col_md_offset = '';
										var col_lg_offset = '';
										// 列隐藏
										var hidden_xs = '';
										var hidden_sm = '';
										var hidden_md = '';
										var hidden_lg = '';
										// 列显示
										var visible_xs_block = '';
										var visible_sm_block = '';
										var visible_md_block = '';
										var visible_lg_block = '';
										// 清除列浮动
										var clearfix_visible_xs = '';
										var clearfix_visible_sm = '';
										var clearfix_visible_md = '';
										var clearfix_visible_lg = '';

										for (var i = 0; i < cls.length; i++) {
											var _c = cls[i];
											if (_c.match(regex_col_xs) !== null
													&& _c.match(regex_col_xs).length >= 1) {// 匹配
												col_xs = _c;
											} else if (_c.match(regex_col_sm) !== null
													&& _c.match(regex_col_sm).length >= 1) {
												col_sm = _c;
											} else if (_c.match(regex_col_md) !== null
													&& _c.match(regex_col_md).length >= 1) {
												col_md = _c;
											} else if (_c.match(regex_col_lg) !== null
													&& _c.match(regex_col_lg).length >= 1) {
												col_lg = _c;
											} else if (_c
													.match(regex_col_xs_offset) !== null
													&& _c
															.match(regex_col_xs_offset).length >= 1) {
												col_xs_offset = _c;
											} else if (_c
													.match(regex_col_sm_offset) !== null
													&& _c
															.match(regex_col_sm_offset).length >= 1) {
												col_sm_offset = _c;
											} else if (_c
													.match(regex_col_md_offset) !== null
													&& _c
															.match(regex_col_md_offset).length >= 1) {
												col_md_offset = _c;
											} else if (_c
													.match(regex_col_lg_offset) != null
													&& _c
															.match(regex_col_lg_offset).length >= 1) {
												col_lg_offset = _c;
											} else if (_c === 'hidden-xs') {
												hidden_xs = 'hidden-xs';
											} else if (_c === 'hidden-sm') {
												hidden_sm = 'hidden-sm';
											} else if (_c === 'hidden-md') {
												hidden_md = 'hidden-md';
											} else if (_c === 'hidden-lg') {
												hidden_lg = 'hidden-lg';
											} else if (_c === 'visible-xs-block') {
												visible_xs_block = 'visible-xs-block';
											} else if (_c === 'visible-sm-block') {
												visible_sm_block = 'visible-sm-block';
											} else if (_c === 'visible-md-block') {
												visible_md_block = 'visible-md-block';
											} else if (_c === 'visible-lg-block') {
												visible_lg_block = 'visible-lg-block';
											}
										}

										// 清除列浮动
										var clea = presentElement
												.prev('.clearfix');

										if (clea.length > 0) {
											var clea_cl = clea.attr('class');
											var clea_cls = clea_cl.split(' ');
											for (var j = 0; j < clea_cls.length; j++) {
												if (clea_cls[j] === 'visible-xs-block') {
													clearfix_visible_xs = 'visible-xs-block';
												} else if (clea_cls[j] === 'visible-sm-block') {
													clearfix_visible_sm = 'visible-sm-block';
												} else if (clea_cls[j] === 'visible-md-block') {
													clearfix_visible_md = 'visible-md-block';
												} else if (clea_cls[j] === 'visible-lg-block') {
													clearfix_visible_lg = 'visible-lg-block';
												}
											}
										}

										var attrs = "eleType=" + eleType
												+ "@#!col_xs=" + col_xs
												+ "@#!col_sm=" + col_sm
												+ "@#!col_md=" + col_md
												+ "@#!col_lg=" + col_lg
												+ "@#!col_xs_offset="
												+ col_xs_offset
												+ "@#!col_sm_offset="
												+ col_sm_offset
												+ "@#!col_md_offset="
												+ col_md_offset
												+ "@#!col_lg_offset="
												+ col_lg_offset
												+ "@#!hidden-xs=" + hidden_xs
												+ "@#!hidden-sm=" + hidden_sm
												+ "@#!hidden-md=" + hidden_md
												+ "@#!hidden-lg=" + hidden_lg
												+ "@#!visible-xs-block="
												+ visible_xs_block
												+ "@#!visible-sm-block="
												+ visible_sm_block
												+ "@#!visible-md-block="
												+ visible_md_block
												+ "@#!visible-lg-block="
												+ visible_lg_block
												+ "@#!clearfix_visible_xs="
												+ clearfix_visible_xs
												+ "@#!clearfix_visible_sm="
												+ clearfix_visible_sm
												+ "@#!clearfix_visible_md="
												+ clearfix_visible_md
												+ "@#!clearfix_visible_lg="
												+ clearfix_visible_lg;
										sendMessage('parent', attrs);
									});

					// 布局 表单 模态框
					$(".ui-sortable").delegate('.row', "dblclick", function(e) {
						e.preventDefault();
						e.stopPropagation();
						presentElement = $(this);
						addSelectCls(presentElement);

						console.info('------div-------');
						console.info(presentElement.parent('form'));
						console.info(presentElement.parent('form').length);

						var ravo = presentElement.attr('ravo');
						console.info(ravo);

						if (presentElement.parent('form').length === 1) {// 表单
							eleType = 'form';
							form();
						} else if ('rainbow_fx_layout_modal' === ravo) {// 模态框
							eleType = 'modal';
							modal();
						} else {// 布局
							var attrs = row_attr(presentElement);
							sendMessage('parent', attrs);
						}
					});
				});

/* ------------表单组件---------------------- */

function modal() {
	var _id = presentElement.attr('modal-id');
	var _isShow = presentElement.attr('modal-header-show');
	var _isHeaderClose = presentElement.attr('modal-header-close');
	var _headerTitle = presentElement.attr('modal-header-title');
	var _width = presentElement.attr('modal-width');

	var attrs = "eleType=" + eleType + ",id=" + _id + ",isShow=" + _isShow
			+ ",isHeaderClose=" + _isHeaderClose + ",headerTitle="
			+ _headerTitle + ",width=" + _width;
	sendMessage('parent', attrs);
}

function form() {

	var form = presentElement.parent();
	var _css = form.attr('class').trim();
	var _id = form.attr('id');
	var _poSrc = form.attr("pourl");
	var _size = "";
	var arrSize = new Array("form-group-lg", "form-group-sm");
	var formGrp = form.find(".form-group").first();// 第一个form-group的div
	for (var i = 0; i < arrSize.length; i++) {
		if (formGrp.size() > 0) {
			if (formGrp.hasClass(arrSize[i])) {
				_size = arrSize[i];
			}
		}
	}
	var attrs = "eleType=" + eleType + ",id=" + _id + ",排列方式=" + _css
			+ ",poSrc=" + _poSrc + ",尺寸=" + _size;
	sendMessage('parent', attrs);
}

/* ------------联合组件BEG ----------------- */
function navbar() {
	var collapse = '';// 标签
	var nav_ys = '';// 样式
	var nav_lcnb = '';// 两侧内补
	var nav_bodynb = '';// body内补
	var nav_fz = ''// 反转

	var m_wb = '';// 导航＿眉＿文本
	var m_img = '';// 导航_眉_品牌图标

	// coolapse
	var uls = presentElement.find('ul.nav.navbar-nav');
	for (var i = 0; i < uls.length; i++) {
		var as = uls.eq(i).children('li');
		for (var j = 0; j < as.length; j++) {
			console.info(as.eq(j).children('a').text().trim());
			collapse = collapse + as.eq(j).children('a').text().trim() + '\n';
		}
		if (i < (uls.length - 1)) {
			collapse = collapse + '----\n';
		}
	}
	// 样式
	var _nav = presentElement.find('nav.navbar');
	if (_nav.hasClass('navbar-static-top')) {
		nav_ys = 'navbar-static-top';
	} else if (_nav.hasClass('navbar-fixed-top')) {
		nav_ys = 'navbar-fixed-top';
	} else if (_nav.hasClass('navbar-fixed-bottom')) {
		nav_ys = 'navbar-fixed-bottom';
	}

	// 反转
	if (_nav.hasClass('navbar-inverse')) {
		nav_fz = 'navbar-inverse';
	}

	// 两侧内补
	if (presentElement.find('.container').length !== 0) {
		nav_lcnb = 'container';
	} else if (presentElement.find('.container-fluid').length !== 0) {
		nav_lcnb = 'container-fluid';
	}

	//

	console.info(collapse);
	var attrs = "eleType=" + eleType + ",collapse=" + collapse + ",nav_ys="
			+ nav_ys + ",nav_fz=" + nav_fz + ",nav_lcnb=" + nav_lcnb;
	sendMessage('parent', attrs);
}
// 处理多选框和单选框
function radioAndCheckboxBoard() {
	// 获得尺寸
	var _s = presentElement.find(">.form-group").attr('class');
	var size = _s.split(' ');
	var _size = '';
	var _poSrc = presentElement.closest("form").attr("pourl");
	for (var i = 0; i < size.length; i++) {
		if (size[i].trim() === 'form-group-lg'
				|| size[i].trim() === 'form-group-sm') {
			_size = "form-group " + size[i].trim();
		} else {
			_size = "form-group";
		}
	}// 获得排列方式
	var _form = presentElement.find('input').parent('label').parent('div')
			.attr("class");
	_form = _form.split(' ');
	for (var i = 0; i < _form.length; i++) {
		if (_form[i].trim() === 'checkbox-horizontal'
				|| _form[i].trim() === 'checkbox-inline'
				|| _form[i].trim() === 'checkbox'
				|| _form[i].trim() === 'radio-horizontal'
				|| _form[i].trim() === 'radio-inline'
				|| _form[i].trim() === 'radio') {
			_form = _form[i].trim();
		}
	}
	var _eleType = 'radioAndCheckbox';
	// 获取选择框文本
	var bs = presentElement.children('div').children('div').find('label');
	var _text = '';
	for (var i = 0; i < bs.length; i++) {
		_text = _text + bs.eq(i).text().trim() + '\n';
	}
	;// 隐藏label
	var yincang = presentElement.children('div').children('label')
			.attr('style');
	// 判断表单是否是水平
	var _formType = presentElement.closest('form').hasClass('form-horizontal');
	var _reverse = reverse(presentElement.find('input'));
	if (_formType) {// 添加左占右占
		var _left = '';
		var _cla = presentElement.children('div').children('label').attr(
				"class").split(' ');
		for (var i = 0; i < _cla.length; i++) {
			if (_cla[i] === 'col-sm-1' || _cla[i] === 'col-sm-2'
					|| _cla[i] === 'col-sm-3' || _cla[i] === 'col-sm-4'
					|| _cla[i] === 'col-sm-5' || _cla[i] === 'col-sm-6'
					|| _cla[i] === 'col-sm-7' || _cla[i] === 'col-sm-8'
					|| _cla[i] === 'col-sm-9' || _cla[i] === 'col-sm-10'
					|| _cla[i] === 'col-sm-11' || _cla[i] === 'col-sm-12') {
				_left += _cla[i] + " control-label";
			}
		}
		var _right = '';
		var _clas = presentElement.children('div').children('div')
				.attr("class").split(' ');
		for (var i = 0; i < _clas.length; i++) {
			if (_clas[i] === 'col-sm-1' || _clas[i] === 'col-sm-2'
					|| _clas[i] === 'col-sm-3' || _clas[i] === 'col-sm-4'
					|| _clas[i] === 'col-sm-5' || _clas[i] === 'col-sm-6'
					|| _clas[i] === 'col-sm-7' || _clas[i] === 'col-sm-8'
					|| _clas[i] === 'col-sm-9' || _clas[i] === 'col-sm-10'
					|| _clas[i] === 'col-sm-11' || _clas[i] === 'col-sm-12') {
				_right += _clas[i];
			}
		}

		attrs = "eleType=" + _eleType + ",reverse=" + _reverse + ",poSrc="
				+ _poSrc + ",左占=" + _left + ",右占=" + _right + ",尺寸=" + _size
				+ ",排列方式=" + _form + ",编辑=" + _text + ",formtype=" + _formType
				+ ",隐藏label=" + yincang;

	} else {
		attrs = "eleType=" + _eleType + ",reverse=" + _reverse + ",poSrc="
				+ _poSrc + ",尺寸=" + _size + ",排列方式=" + _form + ",编辑=" + _text
				+ ",formtype=" + _formType + ",隐藏label=" + yincang;
	}
	sendMessage('parent', attrs);
}

function btnGroup() {
	var t = presentElement.find(">.btn-group");
	var _bstext = '';
	var _id = t.attr('id');
	var _name = t.attr('name');
	var _style = t.attr('style');
	var firstCld = t.find("button").first();// 获得按钮组第一个button子元素
	var _class = t.attr('class');
	var _cldCls = firstCld.attr('class');// 获得第一个子元素class
	var bs = presentElement.find('button');
	var _float = "", _btnStyle = "", _size = "", _derection = "", _justified = "";
	var arrFloat = new Array("pull-left", "pull-right");
	var arrStyle = new Array("btn-default", "btn-primary", "btn-info",
			"btn-success", "btn-danger", "btn-warning", "btn-link");
	var arrSize = new Array("btn-group-lg", "btn-group-md", "btn-group-sm",
			"btn-group-xs");
	// 反显按钮组信息
	if (_class != undefined) {
		for (var i = 0; i < arrFloat.length; i++) {
			if (_class.indexOf(arrFloat[i]) >= 0) {
				_float = arrFloat[i];
			}
		}
		for (var i = 0; i < arrSize.length; i++) {
			if (_class.indexOf(arrSize[i]) >= 0) {
				_size = arrSize[i];
			}
		}
		if (_class.indexOf("btn-group-vertical") >= 0) {
			_derection = "btn-group-vertical";
		}
		if (_class.indexOf("btn-group-justified") >= 0) {
			_justified = "btn-group-justified";
		}
	}
	// 反显按钮组第一个按钮样式信息
	if (_cldCls != undefined) {
		for (var i = 0; i < arrStyle.length; i++) {
			if (_cldCls.indexOf(arrStyle[i]) >= 0) {
				_btnStyle = arrStyle[i];
			}
		}
	}
	for (var i = 0; i < bs.length; i++) {
		_bstext = _bstext + bs.eq(i).text().trim() + '\n';
	}
	console.info(_bstext);
	attrs = "eleType=" + eleType + ",bsText=" + _bstext + ",id=" + _id
			+ ",name=" + _name + ",style=" + _style + ",浮动=" + _float + ",样式="
			+ _btnStyle + ",尺寸=" + _size + ",方向=" + _derection + ",两端对齐="
			+ _justified;
	sendMessage('parent', attrs);
}

function downMenu() {
	var t = presentElement.find(">.btn-group");
	var _id = t.attr('id');
	var _name = t.attr('name');
	var _style = t.attr('style');
	var btnCld = t.find('>button');
	var child = t.children();// 获得按钮组第一个子元素
	var _class = t.attr('class');
	var _cldCls = child.first().attr('class');// 获得第一个子元素class
	var _float = "", _btnStyle = "", _size = "", _derection = "", _isSingle = false;
	var arrFloat = new Array("pull-left", "pull-right");
	var arrStyle = new Array("btn-default", "btn-primary", "btn-info",
			"btn-success", "btn-danger", "btn-warning", "btn-link",
			"btn-inverse");
	var arrSize = new Array("btn-lg", "btn-sm", "btn-xs");
	// 反显下拉菜单信息
	if (_class != undefined) {
		for (var i = 0; i < arrFloat.length; i++) {
			if (_class.indexOf(arrFloat[i]) >= 0) {
				_float = arrFloat[i];
			}
		}
		if (_class.indexOf("dropup") >= 0) {
			_derection = "dropup";
		}
	}
	// 若有两个button则为分裂式下拉菜单，一个为单按钮式下拉菜单
	if (btnCld.length == 2) {
		_isSingle = false;
	} else if (btnCld.length < 2) {
		_isSingle = true;
	}
	// 反显下拉菜单第一个按钮样式信息
	if (_cldCls != undefined) {
		for (var i = 0; i < arrStyle.length; i++) {
			if (_cldCls.indexOf(arrStyle[i]) >= 0) {
				_btnStyle = arrStyle[i];
			}
		}
		for (var i = 0; i < arrSize.length; i++) {
			if (_cldCls.indexOf(arrSize[i]) >= 0) {
				_size = arrSize[i];
			}
		}
	}

	attrs = "eleType=" + eleType + ",id=" + _id + ",name=" + _name + ",style="
			+ _style + ",浮动=" + _float + ",单按钮式下拉菜单=" + _isSingle + ",样式="
			+ _btnStyle + ",尺寸=" + _size + ",方向=" + _derection;
	sendMessage('parent', attrs);
}
// 导航
function navigation() {
	var t = presentElement.find("ul.nav");
	var _class = t.attr("class");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _style = t.attr("style");
	var _edit = "", _float = "";
	var attrs = '';
	var arrFloat = new Array("pull-left", "pull-right");
	var arrType = new Array("nav-tabs", "nav-pills");
	var arrAlign = new Array("nav-stacked", "nav-justified");
	var _navType = "";
	var _navAlign = "";
	if (_class != undefined) {
		for (var i = 0; i < arrFloat.length; i++) {
			if (_class.indexOf(arrFloat[i]) >= 0) {
				_float = arrFloat[i];
			}
		}
		for (var i = 0; i < arrType.length; i++) {
			if (_class.indexOf(arrType[i]) >= 0) {
				_navType = arrType[i];
			}
		}
		for (var i = 0; i < arrAlign.length; i++) {
			if (_class.indexOf(arrAlign[i]) >= 0) {
				_navAlign = arrAlign[i];
			}
		}
	}
	presentElement.find("ul.nav >li >a").each(function() {
		_edit += $(this).text().trim() + "\n";
	});
	attrs = "eleType=" + eleType + ",style=" + _style + ",navType=" + _navType
			+ ",navAlign=" + _navAlign + ",edit=" + _edit + ",id=" + _id
			+ ",name=" + _name + ",浮动=" + _float;
	sendMessage('parent', attrs);
}
// 路径导航
function pathNavigation() {
	var t = presentElement.find("ul.breadcrumb");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _class = t.attr("class");
	var _style = t.attr("style");
	var _float = "";
	var arrFloat = new Array("pull-left", "pull-right");
	var _edit = "";
	if (_class != undefined) {
		for (var i = 0; i < arrFloat.length; i++) {
			if (_class.indexOf(arrFloat[i]) >= 0) {
				_float = arrFloat[i];
			}
		}
	}

	presentElement.find("ul.breadcrumb >li").each(function() {
		_edit += $(this).text().trim() + "\n";
	});
	attrs = "eleType=" + eleType + ",style=" + _style + ",edit=" + _edit
			+ ",浮动=" + _float + ",id=" + _id + ",name=" + _name;
	sendMessage('parent', attrs);
}
// 分页
function paging() {
	var t = presentElement.find("ul.pagination");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _class = t.attr("class");
	var _style = t.attr("style");
	var _float = "";
	var _size = "";
	var arrFloat = new Array("pull-left", "pull-right");
	var arrSize = new Array("pagination-lg", "pagination-sm");
	var _edit = "";
	if (_class != undefined) {
		for (var i = 0; i < arrFloat.length; i++) {
			if (_class.indexOf(arrFloat[i]) >= 0) {
				_float = arrFloat[i];
			}
		}
		for (var i = 0; i < arrSize.length; i++) {
			if (_class.indexOf(arrSize[i]) >= 0) {
				_size = arrSize[i];
			}
		}
	}
	presentElement.find("ul.pagination >li >a").each(function() {
		_edit += $(this).text().trim() + "\n";
	});
	attrs = "eleType=" + eleType + ",style=" + _style + ",edit=" + _edit
			+ ",浮动=" + _float + ",id=" + _id + ",name=" + _name + ",尺寸="
			+ _size;
	sendMessage('parent', attrs);
}
// 按钮工具栏
function btnToolbar() {
	var t = presentElement.find("div.btn-toolbar");
	var btnCld = t.find('>div >button').first();
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _class = t.attr("class");
	var _btnCls = btnCld.attr("class");
	var _style = t.attr("style");
	var _float = "", _btnStyle = "";
	var arrFloat = new Array("pull-left", "pull-right");
	var arrStyle = new Array("btn-default", "btn-primary", "btn-info",
			"btn-success", "btn-danger", "btn-warning", "btn-link");
	var _edit = "";
	if (_class != undefined) {
		for (var i = 0; i < arrFloat.length; i++) {
			if (_class.indexOf(arrFloat[i]) >= 0) {
				_float = arrFloat[i];
			}
		}
	}
	if (_btnCls != undefined) {
		for (var i = 0; i < arrStyle.length; i++) {
			if (_btnCls.indexOf(arrStyle[i]) >= 0) {
				_btnStyle = arrStyle[i];
			}
		}
	}
	t.find(">.btn-group").each(function() {
		$(this).find("button").each(function() {
			_edit += $(this).text().trim() + "\n";
		});
		_edit += "----" + "\n";
	});
	attrs = "eleType=" + eleType + ",style=" + _style + ",edit=" + _edit
			+ ",浮动=" + _float + ",id=" + _id + ",name=" + _name + ",样式="
			+ _btnStyle;
	sendMessage('parent', attrs);
}
// 翻页
function turnPage() {
	var t = presentElement.find("nav");
	var _li = presentElement.find("li");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _class = t.attr("class");
	var _CldCls = _li.first().attr("class");
	var _style = t.attr("style");
	var _float = "", _justify = "";
	var arrFloat = new Array("pull-left", "pull-right");
	if (_class != undefined) {
		for (var i = 0; i < arrFloat.length; i++) {
			if (_class.indexOf(arrFloat[i]) >= 0) {
				_float = arrFloat[i];
			}
		}
	}
	// 反显两端对齐栏
	if (_CldCls != undefined) {
		if (_CldCls.indexOf('previous') >= 0) {
			_justify = "previous next";
		}
	}
	attrs = "eleType=" + eleType + ",style=" + _style + ",浮动=" + _float
			+ ",id=" + _id + ",name=" + _name + ",两端对齐=" + _justify;
	sendMessage('parent', attrs);
}
// 进度条
function progressBar() {
	var t = presentElement.find("div.progress");
	var tt = presentElement.find("div.progress div.progress-bar");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _style = t.attr("style");
	var _class = tt.attr("class");
	var _width = tt.css("width");
	var _minWidth = tt.css("min-width");
	var arrStyle = new Array("progress-bar-success", "progress-bar-info",
			"progress-bar-warning", "progress-bar-danger");
	var _isShow = false, _barStyle = "", _striped = "", _active;
	if (_class != undefined) {
		for (var i = 0; i < arrStyle.length; i++) {
			if (_class.indexOf(arrStyle[i]) >= 0) {
				_barStyle = arrStyle[i];
			}
		}
		if (_class.indexOf("progress-bar-striped") >= 0) {
			_striped = "progress-bar-striped";
		}
		if (_class.indexOf("active") >= 0) {
			_active = "active";
		}
	}
	tt.each(function() {
		var _span = $(this).find("span.sr-only");
		// 不存在span
		if (_span.length <= 0) {
			_isShow = true;
		}
	});
	attrs = "eleType=" + eleType + ",style=" + _style + ",id=" + _id + ",name="
			+ _name + ",提示=" + _isShow + ",编辑提示=" + _width + ",最小百分比="
			+ _minWidth + ",样式=" + _barStyle + ",条纹=" + _striped + ",动画="
			+ _active;
	sendMessage('parent', attrs);
}
// 面板
function panel() {
	var t = presentElement.find("div.panel");
	var _head = t.find("div.panel-heading");
	var _footer = t.find("div.panel-footer");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _style = t.attr("style");
	var _class = t.attr("class");
	var arrStyle = new Array("panel-primary", "panel-success", "panel-info",
			"panel-warning", "panel-danger");
	var _title = false, _tyle = "", _foot = false;
	if (_class != undefined) {
		for (var i = 0; i < arrStyle.length; i++) {
			if (_class.indexOf(arrStyle[i]) >= 0) {
				_tyle = arrStyle[i];
			}
		}
	}
	// 面板有标题
	if (_head.length > 0) {
		_title = "true";
	}
	// 面板有脚注
	if (_footer.length > 0) {
		_foot = "true";
	}
	attrs = "eleType=" + eleType + ",style=" + _style + ",id=" + _id + ",name="
			+ _name + ",样式=" + _tyle + ",标题=" + _title + ",脚注=" + _foot;
	sendMessage('parent', attrs);
}
// 缩略图
function thumbnail() {
	var t = presentElement.find(">div");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _style = t.attr("style");
	var _setting = "";
	var _row = t.find(">div.row");// 找到行
	_row.each(function() {
		$(this).find(">div").each(function() {
			var _cls = $(this).attr("class");
			var _sCls = _cls.split(" ");
			for (var i = 0; i < _sCls.length; i++) {
				if (_sCls[i].indexOf("col") >= 0) {
					_setting += _sCls[i] + "\n";
				}
			}
		});
		_setting += "----" + "\n";
	});
	attrs = "eleType=" + eleType + ",style=" + _style + ",id=" + _id + ",name="
			+ _name + ",设置=" + _setting;
	sendMessage('parent', attrs);
}
// 列表组
function listGroup() {
	var t = presentElement.find(".list-group");
	var _cld = t.find(".list-group-item");
	var _badge = t.find(".list-group-item span.badge");
	var item = t.find(".list-group-item").first();
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _style = t.attr("style");
	var _class = item.attr("class");
	var arrType = new Array("list-group-item-success", "list-group-item-info",
			"list-group-item-warning", "list-group-item-danger");
	var _type = "", _inlineType = "", _badgeText = "", _listType = "";
	if (_class != undefined) {
		for (var i = 0; i < arrType.length; i++) {
			if (_class.indexOf(arrType[i]) >= 0) {
				_type = arrType[i];
			}
		}
	}
	// 判断内嵌类型
	if (_badge.length > 0) {
		_inlineType = "badge";
		_badgeText = _badge.first().text();
	}
	// 判断类型
	if (_cld.length > 0) {
		if (_cld[0].tagName == "A") {
			_listType = 'a';
		} else if (_cld[0].tagName == "LI") {
			_listType = 'li';
		}
		if (_cld[0].tagName == "BUTTON") {
			_listType = 'button';
		}
	}
	attrs = "eleType=" + eleType + ",style=" + _style + ",id=" + _id + ",name="
			+ _name + ",样式=" + _type + ",inlineType=" + _inlineType + ",设置徽章="
			+ _badgeText + ",类型=" + _listType;
	sendMessage('parent', attrs);
}
// 媒体
function media() {
	var t = presentElement.find(">div");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _style = t.attr("style");
	var _list = t.find(".media-list");
	var _isMediaList = "";
	if (_list.length > 0) {
		_isMediaList = "media-list";
	}
	// var _class=item.attr("class");
	attrs = "eleType=" + eleType + ",style=" + _style + ",id=" + _id + ",name="
			+ _name + ",媒体列表=" + _isMediaList;
	sendMessage('parent', attrs);
}
// 巨幕
function jumbotron() {
	var t = presentElement.find(".jumbotron");
	var _id = t.attr("id");
	var _name = t.attr("name");
	var _style = t.attr("style");
	var _class = t.attr("class");
	var _isWell = "";
	if (_class != undefined) {
		if (_class.indexOf("well") >= 0) {
			_isWell = "well";
		}
	}
	attrs = "eleType=" + eleType + ",style=" + _style + ",id=" + _id + ",name="
			+ _name + ",well=" + _isWell;
	sendMessage('parent', attrs);
}

function hint() {
	var _hint = presentElement.find("div").attr("class");
	var _eleType = 'hint';
	var attrs = "";
	attrs = "提示框样式=" + _hint + ",eleType=" + _eleType;
	sendMessage('parent', attrs);
}
// 表格
function table() {
	var t = presentElement.find(".table");
	var _id = t.attr("id");
	var _style = t.attr("style");
	var _edit = "";
	var attrs = '';
	var _classes = t.attr("data-classes");
	var _height = t.attr("data-height");
	var _undefT = t.attr("data-undefined-text");
	var _striped = t.attr("data-striped");
	var _rowstyle = t.attr("data-row-style");
	var _mothod = t.attr("data-method");
	var _url = t.attr("data-url");
	var _pagi = t.attr("data-pagination");
	var _pagiInf = t.attr("data-only-info-pagination");
	var _pagiSide = t.attr("data-side-pagination");
	var _search = t.attr("data-search");
	var _sTime = t.attr("data-search-time-out");
	var _showH = t.attr("data-show-header");
	var _showC = t.attr("data-show-columns");
	var _showR = t.attr("data-show-refresh");
	var _showT = t.attr("data-show-toggle");
	var _sPagS = t.attr("data-show-pagination-switch");
	var _sCard = t.attr("data-card-view");
	var _sAlg = t.attr("data-search-align");
	var _bAlg = t.attr("data-buttons-align");
	var _tAlg = t.attr("data-toolbar-align");
	var _pAlgV = t.attr("data-pagination-v-align");
	var _pAlgh = t.attr("data-pagination-h-align");
	var _pDAlg = t.attr("data-pagination-detail-h-align");
	var _pFir = t.attr("data-pagination-first-text");
	var _pPre = t.attr("data-pagination-pre-text");
	var _pNext = t.attr("data-pagination-next-text");
	var _pLast = t.attr("data-pagination-last-text");
	var _cTS = t.attr("data-click-to-select");
	var _sinSle = t.attr("data-single-select");
	var _tBar = t.attr("data-toolbar");
	var _sCH = t.attr("data-checkbox-header");
	var _params = t.attr("data-query-params");
	var _pType = t.attr("data-query-params-type");
	var _sortable = t.attr('data-sortable');
	var _order = t.attr('data-order');
	var _sortName = t.attr('data-sort-name');
	var _export = t.attr('data-show-export');
	var _poUrl = t.attr("poUrl");
	var _xmlPath = t.attr("xmlPath");
	var _pageNum = t.attr("data-page-number");
	var _pageSize = t.attr("data-page-size");
	var _pageList = t.attr("data-page-list");
	/* add by chenyl 20170423 for 新增是否首次自动装载数据 */
	var _isAutoLoad = t.attr("data-first-load");
	if (undefined == _isAutoLoad) {
		_isAutoLoad = 'false';
	}
	t.find('thead tr th').each(
			function() {
				_edit += $(this).attr("data-field") + "#&"
						+ $(this).text().trim() + "\n";
			});
	attrs = "id=" + _id + ",,pageList=" + _pageList + ",,pageSize=" + _pageSize
			+ ",,pageNum=" + _pageNum + ",,xmlPath=" + _xmlPath + ",,poUrl="
			+ _poUrl + ",,rowstyle=" + _rowstyle + ",,export=" + _export
			+ ",,sortName=" + _sortName + ",,order=" + _order + ",,sortable="
			+ _sortable + ",,params=" + _params + ",,pType=" + _pType
			+ ",,edit=" + _edit + ",,class=" + _classes + ",,eleType=table"
			+ ",,style=" + _style + ",,height=" + _height + ",,undefT="
			+ _undefT + ",,striped=" + _striped + ",,pFir=" + _pFir
			+ ",,pDAlg=" + _pDAlg + ",,pAlgh=" + _pAlgh + ",,pAlgV=" + _pAlgV
			+ ",,tAlg=" + _tAlg + ",,bAlg=" + _bAlg + ",,sAlg=" + _sAlg
			+ ",,sCard=" + _sCard + ",,sPagS=" + _sPagS + ",,showT=" + _showT
			+ ",,showR=" + _showR + ",,mothod=" + _mothod + ",,url=" + _url
			+ ",,pagi=" + _pagi + ",,pagiInf=" + _pagiInf + ",,pagiSide="
			+ _pagiSide + ",,search=" + _search + ",,sTime=" + _sTime
			+ ",,showH=" + _showH + ",,showC=" + _showC + ",,pPre=" + _pPre
			+ ",,pNext=" + _pNext + ",,pLast=" + _pLast + ",,cTS=" + _cTS
			+ ",,sinSle=" + _sinSle + ",,tBar=" + _tBar + ",,sCH=" + _sCH
			+ ",,isAutoLoad=" + _isAutoLoad;
	sendMessage('parent', attrs);
}
/* ------------联合组件END ----------------- */

function onMatch(str, reg) {
	var regex = buildRegex(reg, 'gi');
	var result = str.match(regex);
	console.info('---------onMatch----------');
	console.info(result);
	if (null == result || 0 == result.length) {
		// 没有匹配
		return false;
	}
	/*
	 * if (document.getElementById("optionGlobal").checked) { var strResult =
	 * "共找到 " + result.length + " 处匹配：\r\n"; for (var i=0;i <
	 * result.length;++i)strResult = strResult + result[i] + "\r\n";
	 * document.getElementById("textMatchResult").value = strResult; }
	 */
	/*
	 * else { document.getElementById("textMatchResult").value= "匹配位置：" +
	 * regex.lastIndex + "\r\n匹配结果：" + result[0]; }
	 */
	return true;
}

/*
 * 构建REGEX key = 'g' 全局搜索 key = 'i' 忽略大小写 key = 'gi' 全局搜索+忽略大小写 key = '' 都不支持
 */

function buildRegex(value, g) {
	return new RegExp(value, g);
}

/*
 * 替换
 * 
 */
function replace(value, g, replacement) {
	var regex = buildRegex('<[ ]*.+"[ ]*>', 'gi');
	var s = value.replace(regex, '');
	var regex = buildRegex('[\\t\\n]', 'gi');
	return s.replace(regex, '').trim();
}

var savePath = '';
/*
 * 获取修改后的属性信息,更新HTML
 */
function msghandler(msg) {
	console.info('--------------ide receive frame sendMessage------------');
	console.info(msg);
	if (msg.eleType === 'jspCon') {
		// 反显处理
		$('.edit .demo').attr('data-text', msg.filePath);
		// 反显处理
		fanxian_handler(msg);
	} else if (eleType == 'button') {
		button_handler(msg);
	} else if (eleType == 'input') {
		input_handler(msg);
	} else if (eleType == 'label') {
		label_handler(msg);
	} else if (eleType == 'textarea') {
		textarea_handler(msg);
	} else if (eleType == 'select') {
		select_handler(msg);
	} else if (eleType == 'biaoge') {
		table_handler(msg);
	} else if (eleType == 'tabCol') {
		tableCol_handler(msg);
	} else if (eleType == 'h3') {
		h_handler(msg);
	} else if (eleType == 'p') {
		p_handler(msg);
	} else if (eleType == 'address') {
		address_handler(msg);
	} else if (eleType == 'ul') {
		ul_handler(msg);
	} else if (eleType == 'ol') {
		ol_handler(msg);
	} else if (eleType == 'dl') {
		dl_handler(msg);
	} else if (eleType == 'span') {
		span_handler(msg);
	} else if (eleType == 'img') {
		img_handler(msg);
	} else if (eleType === 'btn_group') {
		btngroup_handler(msg);
	} else if (eleType === 'down_menu') {
		downMenu_handler(msg);
	} else if (eleType === 'navbar') {
		navbar_handler(msg);
	} else if (eleType === 'navigations') {
		navigation_handler(msg);
	} else if (eleType == 'a') {
		a_handler(msg);
	} else if (eleType === 'path_navigation') {
		pathNav_handler(msg);
	} else if (eleType === 'paging') {
		paging_handler(msg);
	} else if (eleType === 'form') {
		form_handler(msg);
	} else if (eleType === 'btn_toolbar') {
		btnToolbar_handler(msg);
	} else if (eleType === 'turnPage') {
		turnPage_handler(msg);
	} else if (eleType === 'grid') {
		grid_handler(msg);
	} else if (eleType === 'checkbox' || eleType === 'radio') {
		radioAndCheckboxBoard_handler(msg);
	} else if (eleType === 'radioCheckbox') {
		radioCheckbox_handler(msg);
	} else if (eleType === 'row') {
		row_handler(msg);
	} else if (eleType === 'progressbar') {
		progressbar_handler(msg);
	} else if (eleType === 'panel') {
		panel_handler(msg);
	} else if (eleType === 'thumbnail') {
		thumbnail_handler(msg);
	} else if (eleType === 'listGroup') {
		listGroup_handler(msg);
	} else if (eleType === 'media') {
		media_handler(msg);
	} else if (eleType === 'hint') {
		hint_handler(msg);
	} else if (eleType === 'fold') {
		fold_handler(msg);
	} else if (eleType === 'slidel') {
		slidel_handler(msg);
	} else if (eleType === 'tab') {
		tab_handler(msg);
	} else if (eleType === 'jumbotron') {
		jumbotron_handler(msg);
	} else if (eleType === 'container') {
		container_handler(msg);
	} else if (eleType === 'imgp') {
		imgp_handler(msg);
	} else if (eleType === 'time') {
		time_handler(msg);
	} else if (eleType == 'canvas_bing' || eleType == 'canvas_huan'
			|| eleType == 'canvas_diji' || eleType == 'canvas_quxian'
			|| eleType == 'canvas_leida' || eleType == 'canvas_zhu') {
		chartPie_handler(msg);
	} else if (eleType === 'fileInput') {
		fileInput_handler(msg);
	} else if (eleType === 'modal') {
		modal_handler(msg);
	}
	/* 20160817 add by chenyl 新增保存日期时间组件的属性修改值操作 */
	else if (eleType === 'datetime') {
		datetime_handler(msg);
	}
	/* 20161019 add by chenyl 新增保存搜索树组件的属性修改值操作 */
	else if (eleType === 'treesearch') {
		treesearch_handler(msg);
	}
	/* 20180612 add by chenyl 新增保存图标选择组件的属性修改值操作 */
	else if (eleType === 'iconselect') {
		iconselect_handler(msg);
	}
	/* 20180618 add by chenyl 新增保存富文本编辑器组件的属性修改值操作 */
	else if (eleType === 'ueditor') {
		ueditor_handler(msg);
	}
	/* 20180620 add by chenyl 新增保存文件管理组件的属性修改值操作 */
	else if (eleType === 'ckfinder') {
		ckfinder_handler(msg);
	}
	/* 20181225 add by chenyl 新增隐藏域的属性修改值操作 */
	else if (eleType === 'input_hidden') {
		hidden_handler(msg);
	}
	else{
		console.info("未定义的类型："+msg);
	}
}

function comClass(msg) {
	var data = msg.editor.options.data;
	var length = data.length;
	var r = '';
	for (var i = 0; i < length; i++) {
		r += data[i].value + ' ';
	}
	return r;
}

function modal_handler(msg) {
	if (msg.name === 'id') {
		presentElement.attr("modal-id", msg.value.trim());
	} else if (msg.name === '宽度') {
		presentElement.attr("modal-width", msg.value.trim());
	} else if (msg.name === '标题') {
		if (msg.value === '隐藏') {
			presentElement.attr("modal-header-show", 'false');
		} else if (msg.value === '显示') {
			presentElement.attr("modal-header-show", 'true');
		}
	} else if (msg.name === '关闭') {
		if (msg.value === '隐藏') {
			presentElement.attr("modal-header-close", 'false');
		} else if (msg.value === '显示') {
			presentElement.attr("modal-header-close", 'true');
		}
	} else if (msg.name === '标题内容') {
		presentElement.attr("modal-header-title", msg.value.trim());
	}
}

function row_handler(msg) {
	if (msg.name === 'id') {
		presentElement.attr("id", msg.value.trim());
	} else if (msg.name === 'margin') {
		presentElement.css('margin', msg.value);
	} else if (msg.name === 'background') {
		var _background = msg.value.trim();
		if (_background.endWith(".bmp") || _background.endWith(".gif")
				|| _background.endWith(".jpeg") || _background.endWith(".png")
				|| _background.endWith(".jpg")) {
			var _url = 'url(' + _background + ')';
			presentElement.css('background-color', "");
			presentElement.css('background-image', _url);
		} else {
			presentElement.css('background-image', "none");
			presentElement.css('background-color', _background);
		}
	} else if (msg.name === '边框样式') {
		if (msg.value == "") {
			presentElement.css("border-style", "");
			presentElement.css("border-width", "");
			presentElement.css("border-color", "");
			var attrs = row_attr(presentElement);
			sendMessage('parent', attrs);
		} else {
			presentElement.css("border-style", msg.value.trim());
		}
	} else if (msg.name === '边框宽度') {
		presentElement.css("border-width", msg.value.trim());
	} else if (msg.name === '边框颜色') {
		presentElement.css("border-color", msg.value.trim());
	}
}

function cleanHtml(e) {
	$(e).parent().append($(e).children().html());
}

function activeDrag() {
	/* -BEG- =反显后可拖拽= */
	$(".demo, .demo .column").sortable({
		connectWith : ".column",
		opacity : .35,
		handle : ".drag"
	});

	$(".sidebar-nav .lyrow").draggable({
		connectToSortable : ".demo",
		helper : "clone",
		handle : ".drag",
		drag : function(e, t) {
			t.helper.width(400);
		},
		stop : function(e, t) {
			console.info('-----------');
			$(".demo .column").sortable({
				opacity : .35,
				connectWith : ".column"
			});
			var type = $(this).find('div.view').attr('id');
			if (type === 'form') {
				var _form = $(".demo").find("div.view form");
				_form.each(function() {
					var _id = $(this).attr("id");
					if (_id == undefined || _id == null || _id == "") {
						var _formId = "formId_" + randomNumber();
						$(this).attr("id", _formId);
					}
				});
			}
		}
	});

	$(".sidebar-nav .box")
			.draggable(
					{
						connectToSortable : ".column",
						helper : "clone",
						handle : ".drag",
						drag : function(e, t) {
							t.helper.width(400);
						},
						stop : function() {
							handleJsIds();
							var type = $(this).find('div.view').attr('id');
							console.info("当前拖放的组件类型：" + type);
							if (type === 'select') {
								$(".demo")
										.find(
												"div .view select[data-role='multiselect']")
										.each(
												function() {
													var _id = $(this)
															.attr("id");
													if (_id == undefined
															|| _id == null
															|| _id == "") {
														var _selectId = "selectId_"
																+ randomNumber();
														$(this).attr("id",
																_selectId);
													}
												});
							}

							if (type === 'button') {
								var _btn = $(".demo").find("div.view button");
								_btn.each(function() {
									var _id = $(this).attr("id");
									if (_id == undefined || _id == null
											|| _id == "") {
										var _btnId = "btnId_" + randomNumber();
										$(this).attr("id", _btnId);
									}
								});
							}
							/*
							 * else if(type === 'time'){ var demo = $(".demo");
							 * var chi = demo.find('div.view'); for(var i=0;i<chi.length;i++){
							 * if(chi.eq(i).attr('id') === 'time'){
							 * if(chi.eq(i).children('div').children('div').children('div').attr('class')==='input-group
							 * date form_datetime'){ scriptStr =
							 * '$("#time'+i+'").datetimepicker({language:"zh-CN",weekStart:
							 * 0,todayBtn:"1",autoclose: 1,todayHighlight:
							 * 1,forceParse: 0,showMeridian: 1});';
							 * chi.eq(i).children('div').children('div').children('div').attr('id','time'+i); //
							 * chi.eq(i).children('div').children('div').children('input').attr('id','time'+i);
							 * $(".form_datetime").datetimepicker({ language:
							 * 'zh-CN', // 设置语言（中文） weekStart: 0, //
							 * 设置一周从哪一天开始（0（星期日）到6（星期六）） startView: 2, //
							 * 日期时间选择器打开之后首先显示的视图。（0选择分钟1代表小时2日期3月份4年份）
							 * minView:0, // 最小的视图 参数同上 todayBtn:'linked', //
							 * Boolean, "linked". 默认值: false 如果此值为true 或
							 * "linked" // 则在日期时间选择器组件的底部显示一个 "Today"
							 * 按钮用以选择当前日期。如果是true的话，"Today" //
							 * 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。 autoclose:
							 * 1, // todayHighlight: 1, // forceParse: 0, //
							 * showMeridian: 1 }); }else
							 * if(chi.eq(i).children('div').children('div').children('div').attr('class')==='input-group
							 * date form_date'){ scriptStr =
							 * '$("#time'+i+'").datetimepicker({language:"zh-CN",weekStart:
							 * 1,startView: 2,minView: 2,todayBtn:
							 * 1,todayHighlight: 1,autoclose: 1,forceParse:
							 * 0});';
							 * chi.eq(i).children('div').children('div').children('div').attr('id','time'+i);
							 * $('.form_date').datetimepicker({ language:
							 * 'zh-CN', weekStart: 1, todayBtn: 1, autoclose: 1,
							 * todayHighlight: 1, startView: 2, minView: 2,
							 * forceParse: 0 }); }else
							 * if(chi.eq(i).children('div').children('div').children('div').attr('class')==='input-group
							 * date form_time'){ scriptStr =
							 * '$("#time'+i+'").datetimepicker({language:"zh-CN",weekStart:
							 * 1,startView: 1,minView: 0,maxView: 2,todayBtn:
							 * 1,todayHighlight: 1,autoclose: 1,forceParse:
							 * 0});';
							 * chi.eq(i).children('div').children('div').children('div').attr('id','time'+i);
							 * $('.form_time').datetimepicker({ language:
							 * 'zh-CN', weekStart: 1, todayBtn: 1, autoclose: 1,
							 * todayHighlight: 1, startView: 1, minView: 0,
							 * maxView: 2, forceParse: 0 }); }
							 * 
							 *  }
							 *  } }
							 */
							/* 20160808 add by chenyl 日期时间组件 beg */
							else if (type === 'datetime') {
								console.info("datetime start ...");
								var demo = $(".demo");
								var chi = demo.find('div.view');
								for (var i = 0; i < chi.length; i++) {
									if (chi.eq(i).attr('id') === 'datetime') {
										var _id = chi.eq(i).children('div')
												.children('div').children(
														'.Wdate').attr('id');
										var _hid = chi.eq(i).children('div')
												.children('div').children(
														'input[type=hidden]')
												.attr('id');
										console.info("datetime id =" + _id
												+ " value id =" + _hid);
										if (_id == undefined || _id == null
												|| _id == "") {
											var rand = randomNumber();
											_id = "datetime_" + rand;
											_hid = "val_" + _id;
											chi.eq(i).children('div').children(
													'div').children('.Wdate')
													.attr('id', _id);// 初始化默认id
											chi.eq(i).children('div').children(
													'div').children(
													'input[type=hidden]').attr(
													'id', _hid);
											console.info("new datetime:" + _id
													+ " , datetime hidden ="
													+ _hid);
										}
										// 重新设置默认值的id
										$("#" + _id).attr('data-link-field',
												_hid);
									}

								}
								console.info("datetime end ...");
							}
							/* 20160808 add by chenyl 日期时间组件 end */
							else if (type === "canvas_bing"
									|| type === "canvas_huan"
									|| type === "canvas_diji") {
								var pieData = [ {
									value : 300,
									color : "#F7464A",
									highlight : "#FF5A5E",
									label : "Red"
								}, {
									value : 50,
									color : "#46BFBD",
									highlight : "#5AD3D1",
									label : "Green"
								}, {
									value : 100,
									color : "#FDB45C",
									highlight : "#FFC870",
									label : "Yellow"
								}, {
									value : 40,
									color : "#949FB1",
									highlight : "#A8B3C5",
									label : "Grey"
								}, {
									value : 120,
									color : "#4D5360",
									highlight : "#616774",
									label : "Dark Grey"
								} ];
								var demo = $(".demo");

								var chi = demo.find('div.view');
								for (var i = 0; i < chi.length; i++) {
									if (chi.eq(i).attr('id') === 'canvas_bing') {
										chi.eq(i).find('canvas').attr('id',
												'canvas' + i);
										var can = $("#canvas" + i + "").get(0)
												.getContext("2d");
										window.myPie = new Chart(can)
												.Pie(pieData);
									} else if (chi.eq(i).attr('id') === 'canvas_huan') {
										chi.eq(i).find('canvas').attr('id',
												'canvas' + i);
										var can = $("#canvas" + i + "").get(0)
												.getContext("2d");
										window.myPie = new Chart(can)
												.Doughnut(pieData);
									} else if (chi.eq(i).attr('id') === 'canvas_diji') {
										chi.eq(i).find('canvas').attr('id',
												'canvas' + i);
										var can = $("#canvas" + i + "").get(0)
												.getContext("2d");
										window.myPie = new Chart(can)
												.PolarArea(pieData);
									}

								}
							} else if (type === "canvas_quxian"
									|| type === "canvas_leida"
									|| type === "canvas_zhu") {
								var radarChartData = {
									labels : [ "Eating", "Drinking",
											"Sleeping", "Designing", "Coding",
											"Cycling", "Running" ],
									datasets : [
											{
												label : "My First dataset",
												fillColor : "rgba(220,220,220,0.2)",
												strokeColor : "rgba(220,220,220,1)",
												pointColor : "rgba(220,220,220,1)",
												pointStrokeColor : "#fff",
												pointHighlightFill : "#fff",
												pointHighlightStroke : "rgba(220,220,220,1)",
												data : [ 65, 59, 90, 81, 56,
														55, 40 ]
											},
											{
												label : "My Second dataset",
												fillColor : "rgba(151,187,205,0.2)",
												strokeColor : "rgba(151,187,205,1)",
												pointColor : "rgba(151,187,205,1)",
												pointStrokeColor : "#fff",
												pointHighlightFill : "#fff",
												pointHighlightStroke : "rgba(151,187,205,1)",
												data : [ 28, 48, 40, 19, 96,
														27, 100 ]
											} ]
								};
								var demo = $(".demo");

								var chi = demo.find('div.view');
								for (var i = 0; i < chi.length; i++) {
									if (chi.eq(i).attr('id') === 'canvas_quxian') {
										chi.eq(i).find('canvas').attr('id',
												'canvas' + i);
										var can = $("#canvas" + i + "").get(0)
												.getContext("2d");
										window.myPie = new Chart(can)
												.Line(radarChartData);
									} else if (chi.eq(i).attr('id') === 'canvas_leida') {
										chi.eq(i).find('canvas').attr('id',
												'canvas' + i);
										var can = $("#canvas" + i + "").get(0)
												.getContext("2d");
										window.myPie = new Chart(can)
												.Radar(radarChartData);
									} else if (chi.eq(i).attr('id') === 'canvas_zhu') {
										chi.eq(i).find('canvas').attr('id',
												'canvas' + i);
										var can = $("#canvas" + i + "").get(0)
												.getContext("2d");
										window.myPie = new Chart(can)
												.Bar(radarChartData);
									}

								}
							}
							sendMessage('parent', 'savePath');
						}
					});
	/* -END- =反显后可拖拽= */
}
// 保存 脚本代码
function saveScript(path) {
	if (scriptStr != '') {
		// var d = beautify(scriptStr);//格式化代码
		$.post(ctxIde+'/ide/saveScript', {
			path : path,
			scriptStr : scriptStr
		}, function(data) {
		});
		scriptStr = '';
	}
	//	
}
function fanxian_handler(msg) {
	$(".demo").empty();
	$(".demo").html(msg.value);
	var demo = $('.demo');
	// console.info("反显的html="+demo.html());

	var container = demo.children().first();
	// 修改downLayout class
	var downLayout = $('#download-layout').children().first();
	downLayout.removeClass('container  container-fluid');
	downLayout.addClass(container.attr('class'));// 添加新属性
	downLayout.removeClass("input-group  table-welcome path");

	var chileren = container.html();
	container.parent().append(chileren);
	container.remove();

	/*
	 * 模态框格式化BEG...
	 */
	demo
			.find('.modal-content .modal-body')
			.each(
					function(i) {
						var $this = $(this);
						var modal_content = $this.parent();
						var modal_dialog = $this.parent().parent();
						var modal = $this.parent().parent().parent();

						var modal_id = '';
						var modal_width = '';
						var modal_header_show = '';
						var modal_header_close = '';
						var modal_header_title = '';

						modal_id = modal.attr('id');
						modal_width = modal_dialog.attr('style');
						var modal_header = modal_content.find('.modal-header');
						if (modal_header.length > 0) {
							modal_header_show = 'true';
							var modal_close = modal_content
									.find('.modal-header .close');
							if (modal_close.length > 0) {
								modal_header_close = 'true';
							} else {
								modal_header_close = 'false';
							}
							var modal_title = modal_content
									.find('.modal-header .modal-title');
							modal_header_title = modal_title.text();
						} else {
							modal_header_show = 'false';
							modal_header_close = 'false';
							modal_header_title = '';
						}

						$this.wrapInner('<div class="col-md-12 column"></div>');
						$this
								.wrapInner('<div ravo="rainbow_fx_layout_modal" class="row clearfix" modal-header-show="'
										+ modal_header_show
										+ '" modal-header-close="'
										+ modal_header_close
										+ '" modal-header-title="'
										+ modal_header_title
										+ '" modal-id="'
										+ modal_id
										+ '" modal-width="'
										+ modal_width + '" ></div>');

						$this.parent().parent().parent().parent().append(
								$(this).children());
						$this.parent().parent().parent().remove();
					});
	/*
	 * 模态框格式化END...
	 */
	/*
	 * 选项卡格式化BEG...
	 * 
	 */
	demo.find('[data-toggle="tabs"]').each(function() {
		// 获取data属性
		$this = $(this);
		var data = $this.data();
		$(this).tabs(data);
	});

	/*
	 * 
	 * 选项卡格式化END...
	 */

	demo.find(
			"" + "[ravo='rainbow_fx_layout'],"
					+ "[ravo='rainbow_fx_layout_bd'],"
					+ "[ravo='rainbow_fx_layout_panel'],"
					+ "[ravo='rainbow_fx_layout_tab'],"
					+ "[ravo='rainbow_fx_layout_modal'],"
					+ "[ravo='rainbow_fx_layout_fold']").wrap(
			"<div class='lyrow ui-draggable'><div class='view'> </div></div>");
	demo
			.find(
					"[ravo='rainbow_fx_anz']," + "[ravo='rainbow_fx_xlcd'],"
							+ "[ravo='rainbow_fx_bj_slt'],"
							+ "[ravo='rainbow_fx_bj'],"
							+ "[ravo='rainbow_fx_checkbox'],"
							+ "[ravo='rainbow_fx_radio'],"
							+ "[ravo='rainbow_fx_slidel'],"
							+ "[ravo='rainbow_fx_bj_hint'],"
							+ "[ravo='rainbow_fx_bj_media'],"
							+ "[ravo = 'rainbow_fx_bj_listGroup']")
			.wrap(
					"<div class='box box-element ui-draggable'><div class='view'> </div></div>");
	// add by chenyl 按钮特殊处理,把权限设置包裹进去
	demo
			.find("[ravo='rainbow_fx']")
			.each(
					function() {
						var $this = $(this);
						// console.info('rainbow_fx='+$this.html());
						// add by chenyl 提交、重置按钮的特殊处理
						if ($this.attr("type") != undefined
								&& ($this.attr("type") == "submit" || $this
										.attr("type") == "reset")) {
							$this.attr("type", "button");
						}
						if ($this.attr("type") != undefined
								&& $this.attr("type") == "button") {
							var shiro = $this.parent().find(
									'shiro\\:haspermission');
							// console.info("shiro="+shiro.length);
							// console.info("parent html="+shiro.html());
							if (shiro != undefined) {
								$this
										.parent()
										.wrap(
												"<div class='box box-element ui-draggable'><div class='view'> </div></div>");
								// if(shiro.find('shiro\\:haspermission')!=undefined
								// && shiro.html()!=undefined &&
								// shiro.html().indexOf('shiro:haspermission')>-1){
								// //已经存在权限标签包裹的情况
								// $this.parent().wrap("<div class='box
								// box-element ui-draggable'><div class='view'>
								// </div></div>");
								// }else{
								// //按钮没有父母节点div包着的情况
								// $this.wrap("<div class='box box-element
								// ui-draggable'><div
								// class='view'><shiro:hasPermission
								// name='anno'>
								// </shiro:hasPermission></div></div>");
								// }
							} else {
								$this
										.wrap("<div class='box box-element ui-draggable'><div class='view'><shiro:hasPermission name='anno'> </shiro:hasPermission></div></div>");
							}
							// console.info("this html="+$this.parent().html());
						} 
						/*20181225 add by chenyl for 对于隐藏域反显的处理，改为按钮方式显示*/
						else if ($this.attr("type") != undefined
								&& $this.attr("type") == "hidden") {
							if ($this.attr('id') != undefined) {
								$this.attr("hidden_id", $this.attr('id'));
							} else {
								$this.attr("hidden_id", 'hid_' + randomNumber());
							}
							if ($this.attr('name') != undefined) {
								$this.attr("hidden_name", $this.attr('name'));
							} else {
								$this.attr("hidden_name", 'hid');
							}
							if ($this.attr('value') != undefined) {
								$this.attr("hidden_value", $this.attr('value'));
							} else {
								$this.attr("hidden_value", '');
							}
							if ($this.attr('check-empty') != undefined) {
								$this.attr("hidden_required", $this.attr('check-empty'));
							} else {
								$this.attr("hidden_required", 'false');
							}
							
							$this.attr("type", "button");
							$this.attr("input_type", "hidden");
							$this.attr("value", "隐藏域");
							$this.attr("class", "disabled");
							$this.attr("readOnly", "true");
							
							/*移除不必要的属性*/
							$this.removeAttr('check-empty');
							$this.wrap("<div class='box box-element ui-draggable'><div class='view' id='input_hidden'> </div></div>");
							console.info("反显后的hidden="+$this.html());
						} else {
							$this
									.wrap("<div class='box box-element ui-draggable'><div class='view'> </div></div>");
						}

						// 20180620 add by chenyl for 文件管理组件反显处理
						if ($this.html().indexOf('<sys:ckfinder') > -1) {
							// console.info("size="+$this.find("div").size());
							$this
									.find("div")
									.each(
											function() {
												var $div = $(this)
												var $sys = $div.children();
												var ckfinder = '无&nbsp;<input type="button" value="文件管理" class="disabled" input_type="sys:ckfinder" readOnly="true"';
												// 编号
												if ($sys.attr('input') != undefined) {
													ckfinder = ckfinder
															+ ' ckfinder_id="'
															+ $sys.attr('input')
															+ '"';
												} else {
													ckfinder = ckfinder
															+ ' ckfinder_id="ckfinder_'
															+ randomNumber()
															+ '"';
												}
												// 隐藏域名称（ID）
												if ($sys.attr('name') != undefined) {
													ckfinder = ckfinder
															+ ' ckfinder_name="'
															+ $sys.attr('name')
															+ '"';
												} else {
													ckfinder = ckfinder
															+ ' ckfinder_name="ckfinder"';
												}
												// 隐藏域值（ID）
												if ($sys.attr('value') != undefined) {
													ckfinder = ckfinder
															+ ' ckfinder_value="'
															+ $sys
																	.attr('value')
															+ '"';
												} else {
													ckfinder = ckfinder
															+ ' ckfinder_value=""';
												}
												// 文件类型
												if ($sys.attr('type') != undefined) {
													ckfinder = ckfinder
															+ ' ckfinder_type="'
															+ $sys.attr('type')
															+ '"';
												} else {
													ckfinder = ckfinder
															+ ' ckfinder_type="files"';
												}
												// 打开文件管理的上传路径
												if ($sys.attr('upload_path') != undefined) {
													ckfinder = ckfinder
															+ ' ckfinder_upload_path="'
															+ $sys.attr('upload_path')
															+ '"';
												} else {
													ckfinder = ckfinder
															+ ' ckfinder_upload_path="/custom"';
												}
												// 是否生成年份路径
												if($sys.attr('year_path')!=undefined){
													ckfinder = ckfinder + ' ckfinder_year_path="'+$sys.attr('year_path')+'"';
												}else{
													ckfinder = ckfinder + ' ckfinder_year_path="false"';
												}
												// 是否生成月份路径
												if($sys.attr('month_path')!=undefined){
													ckfinder = ckfinder + ' ckfinder_month_path="'+$sys.attr('month_path')+'"';
												}else{
													ckfinder = ckfinder + ' ckfinder_month_path="false"';
												}
												// 是否所有用户可见
												if($sys.attr('is_all_user')!=undefined){
													ckfinder = ckfinder + ' ckfinder_is_all_user="'+$sys.attr('is_all_user')+'"';
												}else{
													ckfinder = ckfinder + ' ckfinder_is_all_user="false"';
												}
												//是否必选
												if($sys.attr('ckfinder_required')!=undefined){
													ckfinder = ckfinder + ' ckfinder_required="'+$sys.attr('ckfinder_required')+'"';
												}else{
													ckfinder = ckfinder + ' ckfinder_required="false"';
												}
												// 是否可以多选
												if($sys.attr('select_multiple')!=undefined){
													ckfinder = ckfinder + ' ckfinder_select_multiple="'+$sys.attr('select_multiple')+'"';
												}else{
													ckfinder = ckfinder + ' ckfinder_select_multiple="false"';
												}
												// 是否查看模式
												if($sys.attr('readonly')!=undefined){
													ckfinder = ckfinder + ' ckfinder_readonly="'+$sys.attr('readonly')+'"';
												}else{
													ckfinder = ckfinder + ' ckfinder_readonly="false"';
												}
												// 最大宽度
												if($sys.attr('max_width')!=undefined){
													ckfinder = ckfinder + ' ckfinder_max_width="'+$sys.attr('max_width')+'"';
												}else{
													ckfinder = ckfinder + ' ckfinder_max_width=""';
												}
												// 最大高度
												if($sys.attr('max_height')!=undefined){
													ckfinder = ckfinder + ' ckfinder_max_height="'+$sys.attr('max_height')+'"';
												}else{
													ckfinder = ckfinder + ' ckfinder_max_height=""';
												}

												ckfinder = ckfinder + ' />';
												console.info("反显后的ckfinder="+ckfinder);
												$div.html(ckfinder);
											});
						}
						
						// 20180618 add by chenyl for 富文本编辑器组件反显
						if ($this.html().indexOf('type="text/plain"') > -1) {
							$this.find("div").each(
											function() {
												var $div = $(this)
												var $sys = $div.children();
												console.info("反显："+$sys);
												var ueditor = '<img input_type="ueditor" alt="140x140" src="./file/ueditor.png" ';
												// 编号
												if ($sys.attr('id') != undefined) {
													ueditor = ueditor + ' ue_id="' + $sys.attr('id') + '"';
												} else {
													ueditor = ueditor + ' ue_id="ueditor_' + randomNumber() + '"';
												}
												// 隐藏域名称（ID）
												if ($sys.attr('name') != undefined) {
													ueditor = ueditor + ' ue_name="' + $sys.attr('name') + '"';
												} else {
													ueditor = ueditor + ' ue_name="ueditor"';
												}
												// 默认值
												if ($sys.text() != undefined) {
													ueditor = ueditor + ' ue_value="' + $sys.text() + '"';
												} else {
													ueditor = ueditor + ' ue_value=""';
												}
												// 自定义style
												if ($sys.attr('style') != undefined) {
													ueditor = ueditor + ' ue_style="' + $sys.attr('style') + '"';
												} else {
													ueditor = ueditor + ' ue_style=""';
												}

												ueditor = ueditor + ' />';
												console.info("反显后的ueditor="+ueditor);
												$div.html(ueditor);
											});
						}
						
						// 20180612 add by chenyl for 图标选择组件反显处理
						if ($this.html().indexOf('<sys:iconselect') > -1) {
							// console.info("size="+$this.find("div").size());
							$this
									.find("div")
									.each(
											function() {
												var $div = $(this)
												var $sys = $div.children();
												var iconselect = '无&nbsp;<input type="button" value="选择" class="disabled" input_type="sys:iconselect" readOnly="true"';
												// 编号
												if ($sys.attr('id') != undefined) {
													iconselect = iconselect
															+ ' icon_id="'
															+ $sys.attr('id')
															+ '"';
												} else {
													iconselect = iconselect
															+ ' icon_id="iconselect_'
															+ randomNumber()
															+ '"';
												}
												// 隐藏域名称（ID）
												if ($sys.attr('name') != undefined) {
													iconselect = iconselect
															+ ' icon_name="'
															+ $sys.attr('name')
															+ '"';
												} else {
													iconselect = iconselect
															+ ' icon_name="icon"';
												}
												// 隐藏域值（ID）
												if ($sys.attr('value') != undefined) {
													iconselect = iconselect
															+ ' icon_value="'
															+ $sys
																	.attr('value')
															+ '"';
												} else {
													iconselect = iconselect
															+ ' icon_value=""';
												}
												// 图标数据地址
												if ($sys.attr('url') != undefined) {
													iconselect = iconselect
															+ ' icon_url="'
															+ $sys.attr('url')
															+ '"';
												} else {
													iconselect = iconselect
															+ ' icon_url=""';
												}
												// 自定义CSS
												if ($sys.attr('icon_css') != undefined) {
													iconselect = iconselect
															+ ' icon_css="'
															+ $sys.attr('icon_css')
															+ '"';
												} else {
													iconselect = iconselect
															+ ' icon_css=""';
												}
												//是否必选
												if($sys.attr('icon_required')!=undefined){
													iconselect = iconselect + ' icon_required="'+$sys.attr('icon_required')+'"';
												}else{
													iconselect = iconselect + ' icon_required="false"';
												}

												iconselect = iconselect + ' />';
												console.info("反显后的iconselect="+iconselect);
												$div.html(iconselect);
											});
						}
						
						// 20161018 add by chenyl for 搜索树组件
						if ($this.html().indexOf('<sys:treeselect') > -1) {
							// console.info("size="+$this.find("div").size());
							$this
									.find("div")
									.each(
											function() {
												var $div = $(this)
												var $sys = $div.children();
												var treesearch = '<input input_type="sys:treeselect" readOnly="true"';
												// console.info("编号:"+$sys.attr('id')+",
												// 输入框名称（Name）:"+$sys.attr('label_name'));
												// 编号
												if ($sys.attr('id') != undefined) {
													treesearch = treesearch
															+ ' id="'
															+ $sys.attr('id')
															+ '"';
												} else {
													treesearch = treesearch
															+ ' id="treesearch_'
															+ randomNumber()
															+ '"';
												}
												// 隐藏域名称（ID）
												if ($sys.attr('name') != undefined) {
													treesearch = treesearch
															+ ' name="'
															+ $sys.attr('name')
															+ '"';
												} else {
													treesearch = treesearch
															+ ' name="treesearch.name"';
												}
												// 隐藏域值（ID）
												if ($sys.attr('value') != undefined) {
													treesearch = treesearch
															+ ' value="'
															+ $sys
																	.attr('value')
															+ '"';
												} else {
													treesearch = treesearch
															+ ' value="treesearch.value"';
												}
												// 输入框名称（Name）
												if ($sys.attr('label_name') != undefined) {
													treesearch = treesearch
															+ ' label_name="'
															+ $sys
																	.attr('label_name')
															+ '"';
												} else {
													treesearch = treesearch
															+ ' label_name="treesearch.label_name"';
												}
												// 输入框值（Name）
												if ($sys.attr('label_value') != undefined) {
													treesearch = treesearch
															+ ' label_value="'
															+ $sys
																	.attr('label_value')
															+ '"';
												} else {
													treesearch = treesearch
															+ ' label_value="treesearch.label_value"';
												}
												// 选择框标题
												if ($sys.attr('title') != undefined) {
													treesearch = treesearch
															+ ' title="'
															+ $sys
																	.attr('title')
															+ '"';
												} else {
													treesearch = treesearch
															+ ' title="搜索树标题"';
												}
												// 树结构数据地址
												if ($sys.attr('url') != undefined) {
													treesearch = treesearch
															+ ' url="'
															+ $sys.attr('url')
															+ '"';
												} else {
													treesearch = treesearch
															+ ' url="data2.json"';
												}
												// 是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true
												if ($sys.attr('checked') != undefined) {
													var _checked = $sys
															.attr('checked');
													if (_checked == 'checked') {
														treesearch = treesearch
																+ ' checked="true"';
													} else {
														treesearch = treesearch
																+ ' checked="false"';
													}

												}
												// 排除掉的编号（不能选择的编号）
												if ($sys.attr('ext_id') != undefined) {
													treesearch = treesearch
															+ ' ext_id="'
															+ $sys
																	.attr('ext_id')
															+ '"';
												}
												// 是否列出全部数据，设置true则不进行数据权限过滤（目前仅对Office有效）
												if ($sys.attr('ext_id') != undefined) {
													treesearch = treesearch
															+ ' is_all="'
															+ $sys
																	.attr('ext_id')
															+ '"';
												}
												// 不允许选择根节点
												if ($sys
														.attr('not_allow_select_root') != undefined) {
													treesearch = treesearch
															+ ' not_allow_select_root="'
															+ $sys
																	.attr('not_allow_select_root')
															+ '"';
												}
												// 不允许选择父节点
												if ($sys
														.attr('not_allow_select_root') != undefined) {
													treesearch = treesearch
															+ ' not_allow_select_parent="'
															+ $sys
																	.attr('not_allow_select_root')
															+ '"';
												}
												// 过滤栏目模型（只显示指定模型，仅针对CMS的Category树）
												if ($sys.attr('module') != undefined) {
													treesearch = treesearch
															+ ' module="'
															+ $sys
																	.attr('module')
															+ '"';
												}
												// 选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）
												if ($sys
														.attr('select_scope_module') != undefined) {
													treesearch = treesearch
															+ ' select_scope_module="'
															+ $sys
																	.attr('select_scope_module')
															+ '"';
												}
												// 是否允许清除
												if ($sys.attr('allow_clear') != undefined) {
													treesearch = treesearch
															+ ' allow_clear="'
															+ $sys
																	.attr('allow_clear')
															+ '"';
												}
												// 文本框可填写
												if ($sys.attr('allow_input') != undefined) {
													treesearch = treesearch
															+ ' allow_input="'
															+ $sys
																	.attr('allow_input')
															+ '"';
												}
												// css样式
												if ($sys.attr('css_class') != undefined) {
													treesearch = treesearch
															+ ' css_class="'
															+ $sys
																	.attr('css_class')
															+ '"';
												}
												// css扩展样式
												if ($sys.attr('css_style') != undefined) {
													treesearch = treesearch
															+ ' css_style="'
															+ $sys
																	.attr('css_style')
															+ '"';
												}
												// 缩小按钮显示
												if ($sys.attr('small_btn') != undefined) {
													treesearch = treesearch
															+ ' small_btn="'
															+ $sys
																	.attr('small_btn')
															+ '"';
												}
												// 是否显示按钮
												if ($sys.attr('hide_btn') != undefined) {
													treesearch = treesearch
															+ ' hide_btn="'
															+ $sys
																	.attr('hide_btn')
															+ '"';
												}
												// 是否限制选择，如果限制，设置为disabled
												if ($sys.attr('disabled') != undefined) {
													treesearch = treesearch
															+ ' disabled="'
															+ $sys
																	.attr('disabled')
															+ '"';
												}
												// 是否限制选择，如果限制，设置为disabled
												if ($sys
														.attr('data_msg_required') != undefined) {
													treesearch = treesearch
															+ ' data_msg_required="'
															+ $sys
																	.attr('data_msg_required')
															+ '"';
												}

												var _validator = $sys
														.attr('validators');
												// console.info('反显validators='+_validator);
												if (_validator != undefined
														&& _validator != null) {
													treesearch = treesearch
															+ ' validators="'
															+ _validator + '"';
												}
												
												//是否必选
												if($sys.attr('treesearch_required')!=undefined){
													treesearch = treesearch + ' treesearch_required="'+$sys.attr('treesearch_required')+'"';
												}else{
													treesearch = treesearch + ' treesearch_required="false"';
												}

												treesearch = treesearch + ' />';
												// console.info("treesearch="+treesearch);
												$div.html(treesearch);
											});
						}

					});

	demo
			.find('.ui-draggable')
			.prepend(
					"<a href='javascript:void(0);' class='remove label label-danger'> <i class='glyphicon-remove glyphicon'></i> 删除 </a> "
							+ "<span class='drag label label-default'><i class='glyphicon glyphicon-move'></i>拖动</span>");

	// 添加编辑

	demo
			.find(
					".view [ravo='rainbow_fx_bj'],"
							+ ".view [ravo='rainbow_fx_bj_slt'],"
							+ ".view [ravo='rainbow_fx_bj_hint'],"
							+ ".view [ravo='rainbow_fx_bj_media'],"
							+ ".view [ravo='rainbow_fx_bj_listGroup']")
			.parent()
			.before(
					"<span class='configuration'>"
							+ "<button class='btn btn-xs btn-default'  role='button'  data-target='#editorModal' data-toggle='modal'>编辑</button></span>");

	// 添加ID
	// 20180620 add by chenyl for 文件管理组件
	demo.find(".view [input_type='sys:ckfinder']").parent().attr('id', "ckfinder");
	// 20180618 add by chenyl for 图标选择器组件
	demo.find(".view [input_type='ueditor']").parent().attr('id', "ueditor");
	// 20180618 add by chenyl for 图标选择器组件
	demo.find(".view [input_type='sys:iconselect']").parent().attr('id', "iconselect");
	// 20161018 add by chenyl for 搜索树组件
	demo.find(".view [input-type='sys:treeselect']").parent().attr('id', "treesearch");
	// 表格
	demo.find('.view table').parent().attr('id', "biaoge");
	// 按钮组
	demo.find(".view [ravo='rainbow_fx_anz']").parent().attr('id', "btn_group");
	// 按钮工具栏
	demo.find('.view .btn-toolbar').parent().attr('id', "btn_toolbar");
	// 下拉菜单
	demo.find(".view [ravo='rainbow_fx_xlcd']").parent()
			.attr('id', "down_menu");
	// 导航
	demo.find('.view .nav-tabs').parent().attr('id', "navigations");
	// 路径导航
	demo.find('.view .breadcrumb').parent().attr('id', "path_navigation");
	// 分页
	demo.find('.view .pagination').parent().attr('id', "paging");
	// 翻页
	demo.find('.view nav').parent().attr('id', "turnPage");
	// 进度条
	demo.find('.view .progress').parent().attr('id', "progressbar");

	// 表单
	demo.find(".view [ravo='rainbow_fx_layout_bd']").each(function() {
		$(this).parent().attr('id', 'form');
		var v = $(this).parent().parent().children().first();
		v.text('表单');
		v.prepend('<i class="glyphicon-remove glyphicon"></i>');
	});

	// 多选框
	demo.find(".view [ravo='rainbow_fx_checkbox']").parent().attr('id',
			"checkbox");

	// 单选框
	demo.find(".view [ravo='rainbow_fx_radio']").parent().attr('id', "radio");

	// 缩略图
	demo.find(".view [ravo='rainbow_fx_bj_slt']").parent().attr('id',
			"thumbnail");

	// 面板
	demo.find(".view [ravo='rainbow_fx_layout_panel']").each(function() {
		$(this).parent().attr('id', 'panel');
		var v = $(this).parent().parent().children().first();
		v.text('面板');
		v.prepend('<i class="glyphicon-remove glyphicon"></i>');
	});

	// 手风琴
	demo.find(".view [ravo='rainbow_fx_layout_fold']").parent().attr('id',
			"fold");

	// 幻灯片
	demo.find(".view [ravo='rainbow_fx_slidel']").parent().attr('id', "slidel");

	// 提示框
	demo.find(".view [ravo='rainbow_fx_bj_hint']").parent().attr('id', "hint");

	// 选项卡
	demo.find(".view [ravo='rainbow_fx_layout_tab']").parent()
			.attr('id', "tab");

	// 列表组
	demo.find(".view [ravo='rainbow_fx_bj_listGroup']").parent().attr('id',
			"listGroup");

	// 媒体
	demo.find(".view [ravo='rainbow_fx_bj_media']").parent()
			.attr('id', "media");

	// 模态框
	demo.find(".view [ravo='rainbow_fx_layout_modal']").each(function() {
		$(this).parent().attr('id', 'modal');
		var v = $(this).parent().parent().children().first();
		v.text('模态');
		v.prepend('<i class="glyphicon-remove glyphicon"></i>');
	});

	// 布局
	demo.find(".view [ravo='rainbow_fx_layout']").each(function() {
		var v = $(this).parent().parent().children().first();
		v.text('布局');
		v.prepend('<i class="glyphicon-remove glyphicon"></i>');
	});

	// 文件上传,mody by chenyl
	// demo.find(".view [ravo='rainbow_fx']").parent().attr('id', "fileInput");
	demo.find(".view [ravo='rainbow_fx']").each(function() {
		var type = $(this).find('div.fileInput');
		// console.info(type.length);
		if (type.length > 0) {
			console.info("添加文件上传属性");
			$(this).parent().attr('id', "fileInput");
		}
	});

	// 面板可拖拽编辑

	demo
			.find(
					".panel-body,.panel-heading,.panel-footer,.tab-content .tab-pane,form")
			.each(
					function() {
						var t = $(this);
						var childs = t.children();
						if (childs.length === 0) {
							t
									.append("<div ravo='rainbow_fx_remove' class='row clearfix'><div class='col-md-12 column'> </div></div>");
						} else {
							t.empty();
							t
									.append("<div ravo='rainbow_fx_remove' class='row clearfix'><div class='col-md-12 column'> </div></div>");
							t.children().first().children().first().append(
									childs);
						}
					});

	demo.find('.column').addClass("ui-sortable");

	activeDrag();

	/**
	 * var jsElem = document.createElement('script'); jsElem.src='<%=basePath%>/b_ide/bootstrap/module/bootstrap-table/src/bootstrap-table.js';
	 * document.getElementsByTagName('head')[0].appendChild(jsElem);
	 */
	// 下拉列表反显设置
	if (msg.selectinfo === 1) {
		// $("select[data-role='multiselect']").multiselect('destroy');
		$("select[data-role='multiselect']").multiselect();
	}

	/* 20170615 add by chenyl for 把可编辑的设置为contenteditable=true */
	demo.find('[contenteditable=false]').each(function(i) {
		var $this = $(this);
		$this.attr('contenteditable', true);
	});

	// console.info("格式化的html="+demo.html());
}

function container_handler(msg) {
	if (msg.name === '容器') {
		var downLayout = $('#download-layout').children().first();
		downLayout.removeClass('container  container-fluid');
		downLayout.addClass(msg.value);// 添加新属性
	}
}

function grid_handler(msg) {
	if (msg.group === '列布局' || msg.group === '列偏移') {
		presentElement.removeClass(comClass(msg));
		presentElement.addClass(msg.value);// 添加新属性
	} else if (msg.group === '清除列浮动') {
		/* <div class="clearfix visible-xs-block"></div> */
		var div_clearfix = presentElement.prev('.clearfix');
		console.info(div_clearfix);
		if (msg.value !== '') {
			if (div_clearfix.length !== 0) {
				div_clearfix.addClass(msg.value);
			} else {
				if (msg.name === 'clearfix visible-xs') {
					presentElement
							.before('<div class="clearfix visible-xs-block"></div>');
				} else if (msg.name === 'clearfix visible-sm') {
					presentElement
							.before('<div class="clearfix visible-sm-block"></div>');
				} else if (msg.name === 'clearfix visible-md') {
					presentElement
							.before('<div class="clearfix visible-md-block"></div>');
				} else if (msg.name === 'clearfix visible-lg') {
					presentElement
							.before('<div class="clearfix visible-lg-block"></div>');
				}
			}
		} else {
			var div_fix_css = div_clearfix.attr('class').split(' ');
			console.info('---------div_fix_css_length--------');
			console.info(div_fix_css.length);
			if (div_fix_css.length === 2) {
				div_clearfix.remove();
			} else {
				if (msg.name === 'clearfix visible-xs') {
					div_clearfix.removeClass('visible-xs-block');
				} else if (msg.name === 'clearfix visible-sm') {
					div_clearfix.removeClass('visible-sm-block');
				} else if (msg.name === 'clearfix visible-md') {
					div_clearfix.removeClass('visible-md-block');
				} else if (msg.name === 'clearfix visible-lg') {
					div_clearfix.removeClass('visible-lg-block');
				}
			}
		}
	} else if (msg.group === '列隐藏' || msg.group === '列显示') {
		if (msg.value !== '') {
			presentElement.addClass(msg.value);// 添加新属性
		} else if (msg.name === 'hidden-xs') {
			presentElement.removeClass('hidden-xs');
		} else if (msg.name === 'hidden-sm') {
			presentElement.removeClass('hidden-sm');
		} else if (msg.name === 'hidden-md') {
			presentElement.removeClass('hidden-md');
		} else if (msg.name === 'hidden-lg') {
			presentElement.removeClass('hidden-lg');
		} else if (msg.name === 'visible-xs-block') {
			presentElement.removeClass('visible-xs-block');
		} else if (msg.name === 'visible-sm-block') {
			presentElement.removeClass('visible-sm-block');
		} else if (msg.name === 'visible-md-block') {
			presentElement.removeClass('visible-md-block');
		} else if (msg.name === 'visible-lg-block') {
			presentElement.removeClass('visible-lg-block');
		}
	} else if (msg.group == "列基本属性") {
		if (msg.name == "margin") {
			presentElement.css("margin", msg.value.trim());
		} else if (msg.name == "padding") {
			presentElement.css("padding", msg.value.trim());
		} else if (msg.name == "id") {
			presentElement.attr("id", msg.value.trim());
		} else if (msg.name == "style") {
			presentElement.attr("style", msg.value.trim());
		} else if (msg.name === 'background') {
			var _background = msg.value.trim();
			if (_background.endWith(".bmp") || _background.endWith(".gif")
					|| _background.endWith(".jpeg")
					|| _background.endWith(".png")
					|| _background.endWith(".jpg")) {
				var _url = 'url(' + _background + ')';
				presentElement.css('background-color', "");
				presentElement.css('background-image', _url);
			} else {
				presentElement.css('background-image', "none");
				presentElement.css('background-color', _background);
			}
		} else if (msg.name === '边框样式') {
			if (msg.value == "") {
				presentElement.css("border-style", "");
				presentElement.css("border-width", "");
				presentElement.css("border-color", "");
				var attrs = row_attr(presentElement);
				sendMessage('parent', attrs);
			} else {
				presentElement.css("border-style", msg.value.trim());
			}
		} else if (msg.name === '边框宽度') {
			presentElement.css("border-width", msg.value.trim());
		} else if (msg.name === '边框颜色') {
			presentElement.css("border-color", msg.value.trim());
		} else if (msg.name === "文本对齐方式") {
			var data = msg.editor.options.data;
			var length = data.length;
			var r = '';
			for (var i = 0; i < length; i++) {
				r += data[i].value + ' ';
			}
			presentElement.removeClass(r);
			presentElement.addClass(msg.value);
		} else if (msg.name === '域名') {
			var _text = msg.value.trim();
			if (_text != null && _text != "" && _text != undefined) {
				presentElement.attr("data-area", true);
				presentElement.attr("data-area-text", msg.value.trim());
				presentElement.attr("data-area-con", "area_" + randomNumber());
			} else {
				presentElement.attr("data-area", false);
				presentElement.attr("data-area-text", "");
				presentElement.attr("data-area-con", "");
			}
		}
		var attrs = grid_attrs(presentElement);
		sendMessage('parent', attrs);
	}

}

function form_handler(msg) {
	var value = msg.value;
	var form = presentElement.parent('form');
	if (msg.name === 'id') {
		form.attr('id', msg.value);
	} else if (msg.name === '排列方式') {
		form.removeClass(comClass(msg));
		form.addClass(msg.value);// 添加新属性
		if (value === 'form-inline' || value === '') {// 内联
			// 删除groups中label的class 和删除div
			presentElement.find('.form-group').children('label').removeClass();
			var groups = presentElement.find('.form-group');
			for (var i = 0; i < groups.length; i++) {
				var group = groups.eq(i);
				var group_div = group
						.children(
								'div:not(.input-group,.checkbox,.checkbox-horizontal,.checkbox-inline,.radio,.radio-horizontal,.radio-inline)')
						.eq(0);
				var con = group_div.children();
				group_div.remove();
				group.append(con);
			}
		} else if (value === 'form-horizontal') {// 水平
			// 补
			presentElement.find('.form-group').children('label').addClass(
					'col-sm-4 control-label');

			var groups = presentElement.find('.form-group');
			for (var i = 0; i < groups.length; i++) {
				var group = groups.eq(i);
				var con = group.children('input,div,select,textarea');
				con.wrapAll("<div class='col-sm-8'></div>");
			}
		}
	} else if (msg.name === '左占') {
		presentElement.find('.form-group').children('label').removeClass(
				comClass(msg));
		presentElement.find('.form-group').children('label')
				.addClass(msg.value);
	} else if (msg.name === '右占') {
		presentElement.find('.form-group').children('div').removeClass(
				comClass(msg));
		presentElement.find('.form-group').children('div').addClass(msg.value);
	} else if (msg.name === '尺寸') {
		presentElement.find('.form-group').removeClass(comClass(msg));
		presentElement.find('.form-group').addClass(msg.value);
	} else if (msg.name == 'poSrc') {
		form.attr('pourl', msg.value.trim());
	}
}

function navbar_handler(msg) {
	if (msg.name === '标签') {
		presentElement.find('.collapse.navbar-collapse').empty();
		var _ul_a = '<ul class="nav navbar-nav">';
		var _ul_b = '</ul>';

		var _ul = '';

		if (msg.value.trim() !== '') {
			var _as = msg.value.split('----');
			for (var i = 0; i < _as.length; i++) {
				if (_as[i].trim() !== '') {
					_ul = _ul + _ul_a;
					var a = _as[i].split('\n');
					for (var j = 0; j < a.length; j++) {
						if (a[j].trim() !== '') {
							_ul = _ul + '<li>'
									+ '<a href="javascript:void(0);">'
									+ a[j].trim() + '</a>' + '</li>';
						}
					}
					_ul = _ul + _ul_b;
				}
			}
		}
		presentElement.find('.collapse.navbar-collapse').append(_ul);
	} else if (msg.name === '文本') {
		presentElement.find('.navbar-brand').text(msg.value);
	} else if (msg.name === '两侧内补') {
		// 判断是否已经内补
		if (presentElement.find('.container,.container-fluid').length !== 0) {
			presentElement.find('.collapse.navbar-collapse').unwrap();
		}
		presentElement.find('nav.navbar').wrapInner(
				'<div class="' + msg.value + '"></div>');
	} else if (msg.editor.type == 'combobox') {
		if (msg.name === '样式' || msg.name === '反转') {
			var data = msg.editor.options.data;
			var length = data.length;
			var r = '';
			for (var i = 0; i < length; i++) {
				r += data[i].value + ' ';
			}
			presentElement.find('nav.navbar').removeClass(r);
			presentElement.find('nav.navbar').addClass(msg.value);// 添加新属性
			// body内补
			$('body').css('padding-top', '70px');

			if (msg.name === '样式'
					&& (msg.value === '' || msg.value === 'navbar-static-top')) {
				// 去除container
				if (presentElement.find('.container,.container-fluid').length !== 0) {
					presentElement.find('.collapse.navbar-collapse').unwrap();
				}
			}
		}
	} else if (msg.name === '文本') {
		presentElement.find('a.navbar-brand').text(msg.value);
	} else if (msg.name === '品牌图标') {
		if (msg.value !== '') {
			presentElement.find('a.navbar-brand').prepend(
					'<img alt="Brand" src="' + msg.value + '">');
		} else {
			console.info('----remove----');
			presentElement.find('a.navbar-brand').children('img').remove();
		}
	}

	/**/
}

function downMenu_handler(msg) {
	var _bg = presentElement.find('>.btn-group');
	if (msg.editor.type == 'combobox') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		if (msg.name == '样式' || msg.name == '尺寸') {
			_bg.find('>button').each(function() {
				$(this).removeClass(r);
				$(this).addClass(msg.value);
			});
		} else {
			_bg.removeClass(r);
			_bg.addClass(msg.value);// 添加新属性
		}
	} else if (msg.editor == 'text') {
		if (msg.name == 'id') {
			_bg.attr('id', msg.value);
		} else if (msg.name == 'name') {
			_bg.attr('name', msg.value);
		} else if (msg.name == 'style') {
			_bg.attr('style', msg.value);
		}
	} else if (msg.editor.type == 'checkbox') {
		if (msg.name == '单按钮式下拉菜单') {
			var button = _bg.find(">.dropdown-toggle");
			var btnClass = button.attr("class");
			var ch = button.children();
			if (msg.value == "true") {
				_bg.children().first().remove();
				button.text('帮助');
				if (ch.length !== 0) {
					button.append(ch);
				}
			} else if (msg.value == 'false') {
				button.text("");
				if (ch.length !== 0) {
					button.append(ch);
				}
				if (btnClass != undefined) {
					// var s=btnClass.split('');
					var repCls = btnClass.replace("dropdown-toggle", "");
					_bg.prepend('<button class="' + repCls
							+ '" contenteditable="true">帮助</button>');
				}

			}
		}
	}
}

function btngroup_handler(msg) {
	console.info(presentElement);
	var _bg = presentElement.find('>.btn-group');
	var _cld = _bg.find("button").first();
	var _cldCls = _cld.attr("class");
	var arrSize = new Array("btn-group-lg", "btn-group-md", "btn-group-sm",
			"btn-group-xs");
	var cls = _bg.attr('class');
	if (msg.editor.type == 'combobox') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		if (msg.name === '样式') {
			_bg.find('button').each(function() {
				$(this).removeClass(r);
				$(this).addClass(msg.value);
			});
		} else {
			_bg.removeClass(r);
			_bg.addClass(msg.value);// 添加新属性
			if (msg.name == "尺寸") {
				_bg.find('div').each(function() {
					$(this).removeClass(r);
					$(this).addClass(msg.value);
				});
			}
		}
	} else if (msg.editor == 'textarea') {
		var htm = '';
		_bg.empty();
		var bsT = msg.value.split('\n');
		for (var i = 0; i < bsT.length; i++) {
			var bss = bsT[i].trim();
			if (bss === '') {
				continue;
			}
			if (cls != undefined) {
				// 如果为两端对齐
				if (cls.indexOf('btn-group-justified') >= 0) {
					htm = htm
							+ '<div class="btn-group" role="group"><button type="button">'
							+ bss + '</button></div>';
				} else {
					htm = htm + '<button type="button">' + bss + '</button>';
				}
			}
		}
		_bg.append(htm);
		if (_cldCls != undefined) {
			_bg.find("button").each(function() {
				$(this).addClass(_cldCls);
				$(this).removeClass("dropdown-toggle");
			});
		}
		// 尺寸
		if (cls != undefined) {
			for (var i = 0; i < arrSize.length; i++) {
				if (cls.indexOf(arrSize[i]) >= 0) {
					_bg.find("div").each(function() {
						$(this).addClass(arrSize[i]);
					});
				}
			}
		}
		/*
		 * //重置.btn-group的class _bg.attr("class","btn-group");
		 */
	} else if (msg.editor == 'text') {
		if (msg.name == 'id') {
			_bg.attr('id', msg.value);
		} else if (msg.name == "name") {
			_bg.attr('name', msg.value);
		} else if (msg.name == "style") {
			_bg.attr('style', msg.value);
		}
	} else if (msg.editor.type == 'checkbox') {
		if (msg.name == '两端对齐') {
			var s = msg.editor.options.on + " " + msg.editor.options.off;
			_bg
					.find("button")
					.each(
							function() {
								var _btncls = $(this).attr("class");
								if (_btncls != undefined) {
									// 下拉菜单不处理
									if (_btncls.indexOf("dropdown-toggle") < 0) {
										if (msg.value.trim() == 'btn-group-justified') {
											$(this)
													.wrap(
															'<div class="btn-group" role="group"></div');
										} else {
											$(this).unwrap();
										}
									}
								}
							});
			_bg.removeClass(s);
			_bg.addClass(msg.value);
			// 尺寸
			if (cls != undefined) {
				for (var i = 0; i < arrSize.length; i++) {
					if (cls.indexOf(arrSize[i]) >= 0) {
						_bg.find("div").each(function() {
							$(this).addClass(arrSize[i]);
						});
					}
				}
			}
		}
	}
}

function navigation_handler(msg) {
	var t = presentElement.find('ul.nav');
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == '标签类型' || msg.name == '标签样式' || msg.name == '浮动') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		t.removeClass(r);
		t.addClass(msg.value);// 添加新属性
	} else if (msg.name == '编辑') {
		var s = msg.value.split("\n");
		var length = s.length;
		t.empty();
		var str = "";
		for (var i = 0; i < length; i++) {
			if (s[i].trim() == "") {
				continue;
			}
			str = '<li> <a href="javascript:void(0);">' + s[i] + '</a></li>';
			t.append(str);
		}
	}
}
function pathNav_handler(msg) {
	var t = presentElement.find('ul.breadcrumb');
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == '浮动') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		t.removeClass(r);
		t.addClass(msg.value);// 添加新属性
	} else if (msg.name == '编辑') {
		var s = msg.value.split("\n");
		var flag = true;
		var length = s.length;
		t.empty();
		var str = "";
		for (var i = length - 1; i >= 0; i--) {
			if (s[i].trim() == "") {
				continue;
			}
			if (flag) {
				str = '<li class="active">' + s[i] + '</li>';
				flag = false;
			} else {
				str = '<li> <a href="javascript:void(0);">' + s[i]
						+ '</a></li>';
			}
			t.prepend(str);
		}
	}
}
// 分页
function paging_handler(msg) {
	var t = presentElement.find('ul.pagination');
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == '浮动' || msg.name == "尺寸") {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		t.removeClass(r);
		t.addClass(msg.value);// 添加新属性
	} else if (msg.name == '编辑') {
		var s = msg.value.split("\n");
		var length = s.length;
		t.empty();
		var str = "";
		for (var i = 0; i < length; i++) {
			if (s[i].trim() == "") {
				continue;
			}
			str = '<li> <a href="javascript:void(0);">' + s[i] + '</a></li>';
			t.append(str);
		}
	}
}
// 按钮工具栏
function btnToolbar_handler(msg) {
	var t = presentElement.find('.btn-toolbar');
	var arrStyle = new Array("btn-default", "btn-primary", "btn-info",
			"btn-success", "btn-danger", "btn-warning", "btn-link");
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == '浮动' || msg.name == '样式') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		if (msg.name == '样式') {
			t.find("button").each(function() {
				$(this).removeClass(r);
				$(this).addClass(msg.value);// 添加新属性
			});
		} else {
			t.removeClass(r);
			t.addClass(msg.value);// 添加新属性
		}
	} else if (msg.name == '编辑') {
		var str = "";
		var _cls = t.find(".btn").first().attr('class');
		var btnCls = "";
		if (_cls != undefined) {
			for (var i = 0; i < arrStyle.length; i++) {
				if (_cls.indexOf(arrStyle[i]) >= 0) {
					btnCls = arrStyle[i];
				}
			}
		}
		t.empty();
		t.append('<div class="btn-group"></div>');
		var s = msg.value.split("\n");
		for (var i = 0; i < s.length; i++) {
			if (s[i].trim() == "") {
				continue;
			}
			if (s[i].trim() === "----") {
				t.append('<div class="btn-group"></div>');
			} else {
				var _div = t.find(">div");
				str = '<button class="btn ' + btnCls + '" type="button">'
						+ s[i] + '</button>';
				_div.last().append(str);
			}
		}
	}
}
// 翻页
function turnPage_handler(msg) {
	var t = presentElement.find('nav');
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == '浮动') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		t.removeClass(r);
		t.addClass(msg.value);// 添加新属性
	} else if (msg.name == '两端对齐') {
		var _msg = msg.editor.options.on;
		var s = _msg.split(" ");
		var _li = t.find(".pager >li");
		if (msg.value.trim() != "") {
			_li.first().addClass(s[0]);
			_li.last().addClass(s[1]);
		} else {
			_li.first().removeClass(s[0]);
			_li.last().removeClass(s[1]);
		}
	}
}
// 进度条
function progressbar_handler(msg) {
	var t = presentElement.find('div.progress');
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == '样式') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		if (msg.name == '样式') {
			// 给每个进度条添加样式
			t.find("div.progress-bar").each(function() {
				$(this).removeClass(r);
				$(this).addClass(msg.value);
			});
		} else {
			t.removeClass(r);
			t.addClass(msg.value);// 添加新属性
		}
	} else if (msg.name == '提示') {
		if (msg.value.trim() == 'true') {// 提示
			var _span = t.find("span.sr-only");
			var length = _span.length;
			// 若含.sr_only的span则去掉span
			if (length > 0) {
				var _text = _span.text().trim();
				_span.parent().text(_text);
				_span.remove();

			}
		} else if (msg.value.trim() == 'false') {// 不提示
			var _span = t.find("span.sr-only");
			var length = _span.length;
			// 若不含.sr_only的span添加span
			if (length <= 0) {
				t.find("div.progress-bar").each(
						function() {
							var _text = $(this).text();
							$(this).text("");
							$(this).append(
									'<span class="sr-only">' + _text
											+ '</span>');
						});
			}
		}
	} else if (msg.name == '编辑提示') {
		t.find("div.progress-bar").each(function() {
			var _cld = $(this).children();
			var _span = $(this).find("span.sr-only");// 如果存在span改变span的text
			if (_span.length > 0) {
				_span.text(msg.value);
			} else {
				$(this).text(msg.value);
				$(this).append(_cld);
			}
			$(this).css("width", msg.value);
		});
	} else if (msg.name == '最小百分比') {
		t.find("div.progress-bar").each(function() {
			$(this).css("min-width", msg.value);
		});
	} else if (msg.name == '条纹' || msg.name == '动画') {
		var _msg = msg.editor.options.on;
		t.find("div.progress-bar").each(function() {
			if (msg.value.trim() != "") {
				$(this).removeClass(_msg);
				$(this).addClass(_msg);
			} else {
				$(this).removeClass(_msg);
			}
		});
	}
}
// 面板
function panel_handler(msg) {
	var t = presentElement.find('.panel').first();
	var _body = t.find("div.panel-body");
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == '样式') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		t.removeClass(r);
		t.addClass(msg.value);// 添加新属性
	} else if (msg.name == '显示标题') {
		var _head = t.find("div.panel-heading");
		if (msg.value == 'true') {
			if (_head.length <= 0) {
				_body
						.before('<div class="panel-heading"> <div class="row clearfix"><div class="col-md-12 column">	</div></div></div>');
			}
			activeDrag();
		} else {
			if (_head.length > 0) {
				_head.remove();
			}
		}

	} else if (msg.name == '显示脚注') {
		var _footer = t.find("div.panel-footer");
		if (msg.value == 'true') {
			if (_footer.length <= 0) {
				_body
						.after('<div class="panel-footer"> <div class="row clearfix"><div class="col-md-12 column">	</div></div></div>');
			}
			activeDrag();
		} else {
			if (_footer.length > 0) {
				_footer.remove();
			}
		}
	}
}
// 缩略图
function thumbnail_handler(msg) {
	var t = presentElement.find('>div');
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == '设置') {
		var sRow = msg.value.split('----');
		t.empty();
		for (var i = 0; i < sRow.length; i++) {
			alert(sRow[i]);
			var sMsg = sRow[i].split('\n');
			if (sRow[i].trim() == '') {
				continue;
			}
			t.append('<div class="row"></div>');
			for (var j = 0; j < sMsg.length; j++) {
				if (sMsg[j].trim() == '') {
					continue;
				}
				t
						.find("div.row")
						.last()
						.append(
								'<div class="'
										+ sMsg[j]
										+ '"><div class="thumbnail">	<img alt="300x200" src="./file/default(1).jpg"><div class="caption" contenteditable="true">	<h3>标题...</h3><p>						内容...						</p>						<p>							<a class="btn btn-primary" href="javascript:void(0);">Action</a>							<a class="btn" href="javascript:void(0);">Action</a>						</p>					</div>				</div> </div>');
			}
		}
	}
}
// 列表组
function listGroup_handler(msg) {
	var t = presentElement.find('.list-group');
	var _listItem = t.find('.list-group-item');
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == "样式") {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		// 添加新属性
		t.find(".list-group-item").each(function() {
			$(this).removeClass(r);
			$(this).addClass(msg.value);
		});
	} else if (msg.name == '类型') {
		var str = "";
		var _id = t.attr('id');
		var _name = t.attr('name');
		var _style = t.attr('style');
		var _cldcls = _listItem.first().attr("class");
		t.remove();
		if (msg.value == 'li') {
			str = '<ul class="list-group">  <li class="list-group-item">Cras justo odio</li>  <li class="list-group-item">Dapibus ac acilisis in</li>  <li class="list-group-item">Morbi leo risus</li>  <li class="list-group-item">Porta ac consectetur ac</li>  <li class="list-group-item">Vestibulum at eros</li></ul>';
		} else if (msg.value == 'button') {
			str = '<div class="list-group">  <button type="button" class="list-group-item">Cras justo odio</button>  <button type="button" class="list-group-item">Dapibus ac facilisis in</button>  <button type="button" class="list-group-item">Morbi leo risus</button>  <button type="button" class="list-group-item">Porta ac consectetur ac</button>  <button type="button" class="list-group-item">Vestibulum at eros</button></div>';
		} else {
			str = '<div class="list-group">  <a href="#" class="list-group-item">    Cras justo odio  </a>  <a href="#" class="list-group-item">Dapibus ac facilisis in</a>  <a href="#" class="list-group-item">Morbi leo risus</a>  <a href="#" class="list-group-item">Porta ac consectetur ac</a>  <a href="#" class="list-group-item" >Vestibulum at eros</a> </div>';
		}
		presentElement.append(str);
		t = presentElement.find(".list-group");
		t.attr('id', _id);
		t.attr('name', _name);
		t.attr('style', _style);
		var _item = t.find(".list-group-item");
		_item.removeClass("list-group-item");
		_item.addClass(_cldcls);
		listGroup();
	} else if (msg.name == '内嵌') {
		// 清除子类
		t.find('.list-group-item').each(function() {
			var cld = $(this).children();
			if (cld.length > 0) {
				var cls = t.attr("class");
				// 下拉菜单处理
				if (cls.indexOf("dropdown-toggle") >= 0) {
					$(this).parent().removeClass("dropdown");
					$(this).removeClass("dropdown-toggle");
					$(this).removeAttr("data-toggle");
					$(this).next().remove();
				}
			}
			cld.remove();
		});
		if (msg.value == "badge") {
			t.find('.list-group-item').each(function() {
				$(this).append('<span class="badge">1</span>');
			});
		}
		listGroup();
	} else if (msg.name == "设置徽章") {
		t.find(".list-group-item span.badge").text(msg.value);
	} else if (msg.name == "增加行") {
		var _msg = msg.value.trim();
		var length = _listItem.length + 1;
		if (_listItem.length > 0) {
			if (_msg < 1 || _msg > length) {
				alert('请输入1-' + length + '之间的数字！');
			} else {
				// 如果.list-group无子元素.list-group-item则新增
				var _preElement = _listItem.eq(_msg - 1);// 找到第_msg-1行,eq从0开始
				if (_msg == length) {
					_listItem.last().clone(true).insertAfter(_listItem.last());
				} else {
					_preElement.clone(true).insertBefore(_preElement);
				}
			}
		}
	} else if (msg.name == "删除行") {
		var _msg = msg.value.trim();
		var length = _listItem.length;
		if (length > 1) {
			if (_msg < 1 || _msg > length) {
				alert('请输入1-' + length + '之间的数字！');
			} else {
				var _preElement = _listItem.eq(_msg - 1);// 找到第_msg-1行,eq从0开始
				_preElement.remove();
			}
		} else if (length == 1) {
			alert('不能全部删除！');
		}
	}
}
// 媒体
function media_handler(msg) {
	var t = presentElement.children();
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == "媒体列表") {
		var _list = t.find(">.media-list");
		if (_list.length > 0) {
			if (msg.value.trim() == "") {
				var st = t.find(".media-list >.media");
				_list.remove();
				st.each(function() {
					var cld = $(this).children();
					var cls = $(this).attr("class");
					$(this).remove();
					t.append('<div class="' + cls + '"></div>');
					var s = t.find(">.media");
					s.append(cld);
				});
			}
		} else {
			var st = t.find('>.media');
			if (msg.value.trim() != "") {
				st.wrapAll('<ul class="' + msg.value + '"></ul>');
				st = presentElement.find('.media-list');
				st.find(">.media").each(function() {
					var cld = $(this).children();
					var cls = $(this).attr("class");
					$(this).remove();
					st.last().append('<li class="' + cls + '"></li>');
					var s = st.find(">.media");
					s.append(cld);
				});
			}
		}
	}
}
// 巨幕
function jumbotron_handler(msg) {
	var t = presentElement.find(".jumbotron");
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'name') {
		t.attr('name', msg.value);
	} else if (msg.name == 'style') {
		t.attr('style', msg.value);
	} else if (msg.name == 'well') {
		var str = msg.editor.options.on + " " + msg.editor.options.off;
		t.removeClass(str);
		t.addClass(msg.value);
	}
}
// a标签
function a_handler(msg) {
	var cls = presentElement.attr('class');
	var _tabsA = presentElement.closest("#tab.view");// 找到匹配的标签页
	var arrFloat = new Array("pull-left", "pull-right");
	if (msg.name == 'id') {
		presentElement.attr('id', msg.value);
	} else if (msg.name == 'name') {
		presentElement.attr('name', msg.value);
	} else if (msg.name == '文本') {
		var ch = presentElement.children();
		presentElement.text(msg.value);
		if (ch.length !== 0) {
			presentElement.append(ch);
		}
	}
	if (msg.name == 'rel') {
		presentElement.attr('rel', msg.value);
	} else if (msg.name == 'title') {
		presentElement.attr('title', msg.value);
	} else if (msg.name == 'href') {
		presentElement.attr('href', msg.value);
	} else if (msg.name == '新窗口打开') {
		var isBlank = msg.value;
		if (isBlank == "true") {
			presentElement.attr('target', '_blank');
		} else if (isBlank == "false") {
			if (presentElement.attr("target") != undefined
					&& presentElement.attr("target") == '_blank') {
				presentElement.removeAttr('target');
			}
		}
	} else if (msg.name == 'style') {
		presentElement.attr('style', msg.value);
	} else if (msg.name == '内嵌') {
		var _id = 'downmenu' + randomNumber();// 随机生成id号
		var _href = 'tabs' + randomNumber();// 随机标签页生成href中id
		var _aId = "";
		// 判断当前对象class不为undefined
		if (cls != undefined) {
			// 如果内嵌下拉菜单清除属性
			if (cls.indexOf("dropdown-toggle") >= 0) {
				var _next = presentElement.next();
				presentElement.parent().removeClass("dropdown");
				presentElement.removeClass("dropdown-toggle open");
				presentElement.removeAttr("data-toggle");
				presentElement.removeAttr("id");
				_next.remove();
				// 如果为标签页，做处理
				if (_tabsA.length > 0) {
					var firstId = "";
					presentElement.children().remove();
					presentElement.attr("href", "#" + _href);
					presentElement.attr("data-toggle", 'tab');
					_tabsA
							.find('.tab-content')
							.append(
									'<div class="tab-pane" id="'
											+ _href
											+ '">	<div ravo="rainbow_fx_remove" class="row clearfix"><div class="col-md-12 column"></div></div></div>');
					activeDrag();
					_next
							.find('li >a')
							.each(
									function() {
										var ahref = $(this).attr('href');
										_tabsA
												.find(".tab-content .tab-pane")
												.each(
														function() {
															var panelId = $(
																	this).attr(
																	"id");
															if (ahref != undefined
																	&& ahref
																			.indexOf(panelId) >= 0) {
																$(this)
																		.remove();
															}
														});
									});
					// 设置第一个选项卡为active
					_tabsA.find("div.tab-content div.tab-pane").removeClass(
							"active");
					_tabsA.find("ul.nav-tabs li").removeClass("active");
					var firstLi = _tabsA.find("ul.nav-tabs >li").first();
					// li子元素存在li
					var _li = firstLi.find("li");
					if (firstLi.length > 0) {
						firstLi.addClass("active");
						if (_li.length > 0) {
							_li.first().addClass("active");
							var _a = _li.first().find(">a");
							if (_a.length > 0) {
								firstId = _a.attr("href");
							}
						} else {
							firstId = firstLi.find(">a").attr("href");
						}
					}
					_tabsA.find(".tab-content .tab-pane").each(
							function() {
								var panelId = $(this).attr("id");
								if (firstId != undefined
										&& firstId.indexOf(panelId) >= 0) {
									$(this).addClass("active");
								}
							});
				}
			}
		}
		// 清除内嵌属性
		var _chileren = "";
		if (presentElement.hasClass("list-group-item")) {
			_chileren = presentElement.children(".list-group-item-heading");
		}
		presentElement.children().remove();
		// 添加元素
		if (_chileren != "") {
			presentElement.append(_chileren);
		}
		if (msg.value == "image") {
			presentElement.append('<img></img');
		} else if (msg.value == "badge") {
			presentElement.append('<span class="badge">1</span>');
		} else if (msg.value == "dropdown") {
			// 若是标签页下的a标签
			if (_tabsA.length > 0) {
				_aId = presentElement.attr("href");
				presentElement.removeAttr("href");
				_tabsA.find(".tab-content .tab-pane").each(function() {
					var paneId = $(this).attr("id");
					if (_aId.indexOf(paneId) >= 0) {
						$(this).remove();
					}
				});

			}
			presentElement.parent().addClass("dropdown");
			presentElement.attr("data-toggle", "dropdown");
			presentElement.attr("id", _id);
			presentElement.addClass("dropdown-toggle");
			presentElement.append('<span class="caret"></span>');
			presentElement.after('<ul class="dropdown-menu" aria-labelledby="'
					+ _id + '"></ul>');
		}
		var attrs = a_attr(presentElement);
		sendMessage('parent', attrs);
	} else if (msg.name == 'aType') {
		var cls = presentElement.attr("class");
		if (cls != undefined) {
			var arr = cls.split(" ");
			// 清除含btn及label的class
			$.each(arr, function(n, value) {
				if (value.indexOf("btn") >= 0 || value.indexOf("label") >= 0) {
					presentElement.removeClass(value);
				}
			});
		}
		if (msg.value == "button") {
			presentElement.addClass("btn btn-default");
		} else if (msg.value == "label") {
			presentElement.addClass("label label-default");
		}
		var attrs = a_attr(presentElement);
		sendMessage('parent', attrs);
	} else if (msg.name == 'src') {
		var t = presentElement.children("img,i");
		var str = msg.value.trim();
		var strr = str.toLowerCase();
		if (strr.endWith(".bmp") || strr.endWith(".gif")
				|| strr.endWith(".jpeg") || strr.endWith(".png")
				|| strr.endWith(".jpg")) {
			if (t.length > 0) {
				if (t[0].tagName == "IMG") {
					t.attr("src", msg.value);
				} else if (t[0].tagName == "I") {
					var _cls = t.attr('class');
					var _float = "";
					if (_cls != undefined) {
						for (var i = 0; i < arrFloat.length; i++) {
							if (_cls.indexOf(arrFloat[i]) >= 0) {
								_float = arrFloat[i];
							}
						}
					}
					t.replaceWith("<img></img>");
					t = presentElement.find(">img");
					t.attr("src", msg.value);
					t.addClass(_float);
				}
			}
		} else {
			var _cls = t.attr('class');
			if (t[0].tagName == "I") {
				var str = "";
				if (_cls != undefined) {
					var spliCls = _cls.split(" ");
					for (var i = 0; i < spliCls.length; i++) {
						if (spliCls[i].indexOf("glyphicon") >= 0) {
							str += spliCls[i] + " ";
						}
					}
				}
				t.removeClass(str);
				t.addClass(msg.value);
			} else if (t[0].tagName == "IMG") {
				var _float = "";
				if (_cls != undefined) {
					for (var i = 0; i < arrFloat.length; i++) {
						if (_cls.indexOf(arrFloat[i]) >= 0) {
							_float = arrFloat[i];
						}
					}

				}
				t.replaceWith('<i> </i>');
				t = presentElement.find(">i");
				t.addClass(msg.value);
				t.addClass(_float);
			}
		}
	} else if (msg.name == '图片位置') {
		// 判断是img或i元素
		var t1 = presentElement.find(">img");
		var t2 = presentElement.find(">i");
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		if (t1.length > 0) {
			t1.removeClass(r);
			t1.addClass(msg.value);
		} else if (t2.length > 0) {
			t2.removeClass(r);
			t2.addClass(msg.value);
		}
	} else if (msg.name == '下拉菜单编辑') {
		var dropdown = presentElement.next();
		var _tabs = "";
		// 下拉菜单编辑
		var s = msg.value.split("\n");
		var length = s.length;
		var firstId = "";
		dropdown.empty();
		var str = "";
		for (var i = 0; i < length; i++) {
			var _id = 'tabs' + randomNumber();// 随机生成id号
			if (s[i].trim() == '') {
				continue;
			}
			if (s[i] == "----") {
				str = '<li class="divider"></li>';
				dropdown.append(str);
			} else {
				// 选项卡中的a
				if (_tabsA.length > 0) {
					str = '<li> <a href="#' + _id + '" data-toggle="tab">'
							+ s[i] + '</a></li>';
					_tabs = '<div class="tab-pane" id="'
							+ _id
							+ '">	<div ravo="rainbow_fx_remove" class="row clearfix"><div class="col-md-12 column"></div></div></div>';
					_tabsA.find("div.tab-content").append(_tabs);
					dropdown.append(str);
					activeDrag();
					_tabsA.find("div.tab-content div.tab-pane").removeClass(
							"active");
					_tabsA.find("ul.nav-tabs li").removeClass("active");
					// 设置第一个选项卡为active
					var firstLi = _tabsA.find("ul.nav-tabs >li").first();
					// li子元素存在li
					var _li = firstLi.find("li");
					if (firstLi.length > 0) {
						firstLi.addClass("active");
						if (_li.length > 0) {
							_li.first().addClass("active");
							var _a = _li.first().find(">a");
							if (_a.length > 0) {
								firstId = _a.attr("href");
							}
						} else {
							firstId = firstLi.find(">a").attr("href");
						}
					}
					_tabsA.find(".tab-content div.tab-pane").each(
							function() {
								var panelId = $(this).attr("id");
								if (firstId != undefined
										&& firstId.indexOf(panelId) >= 0) {
									$(this).addClass("active");
								}
							});
				} else {
					str = '<li> <a href="javascript:void(0);">' + s[i]
							+ '</a></li>';
					dropdown.append(str);
				}
			}
		}
	} else if (msg.name == '设置徽章') {
		var t = presentElement.find(">span.badge");
		t.text(msg.value);
	} else if (msg.name == "靠左/靠右" || msg.name == '浮动') {
		var t = presentElement.parent().parent();
		var tt = presentElement.parent();
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		if (msg.name == "靠左/靠右") {
			t.removeClass(r);
			t.addClass(msg.value);// 添加新属性
		} else if (msg.name == '浮动') {
			tt.removeClass(r);
			tt.addClass(msg.value);// 添加新属性
		}
	} else if (msg.name == "ulEdit") {
		var t = presentElement.parent().parent();
		var s = msg.value.split("\n");
		var length = s.length;
		var str = "";
		t.empty();
		for (var i = 0; i < length; i++) {
			if (s[i].trim() == '') {
				continue;
			}
			str = '<li> <a href="javascript:void(0);">' + s[i] + '</a></li>';
			t.append(str);
		}
	} else if (msg.name == "btnType" || msg.name == "btnSize"
			|| msg.name == "label样式" || msg.name == "a浮动") {
		var parent = presentElement.parent();
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		presentElement.removeClass(r);
		presentElement.addClass(msg.value);// 添加新属性
		// a标签父类li为active,a标签设置button时的属性的效果不出来，所以去除父类的active
		if (msg.name != "a浮动") {
			if (parent[0].tagName == "LI") {
				parent.removeClass("active");
			}
		}
	} else if (msg.name == "btn通栏" || msg.name == "a活动" || msg.name == 'a禁用') {
		var t = presentElement.parent();
		var r = msg.editor.options.on + " " + msg.editor.options.off;
		presentElement.removeClass(r);
		if (msg.value.trim() != "") {
			presentElement.addClass(msg.value);
		}
		if (msg.name == "btn通栏") {
			if (t[0].tagName == "LI") {
				t.removeClass("active");
			}
		}
	} else if (msg.name == '活动/取消活动' || msg.name == '禁用/取消禁用') {
		var t = presentElement.parent();
		var r = msg.editor.options.on + " " + msg.editor.options.off;
		t.removeClass(r);
		if (msg.value.trim() != "") {
			t.addClass(msg.value);
		}
	}
}

// 单选框 多选框
function radioAndCheckboxBoard_handler(msg) {
	if (msg.name == '左占') {
		var _cla = presentElement.children('div').children('label').attr(
				"class");
		var _clas = _cla.replace('col-sm-1 control-label', "").replace(
				'col-sm-2 control-label', "").replace('col-sm-3 control-label',
				"").replace('col-sm-4 control-label', "").replace(
				'col-sm-5 control-label', "").replace('col-sm-6 control-label',
				"").replace('col-sm-7 control-label', "").replace(
				'col-sm-8 control-label', "").replace('col-sm-9 control-label',
				"").replace('col-sm-10 control-label', "").replace(
				'col-sm-11 control-label', "").replace(
				'col-sm-12 control-label', "");
		presentElement.children('div').children('label').attr("class",
				_clas + " " + msg.value);
	}
	if (msg.name == '右占') {
		var _cla = presentElement.children('div').children('div').attr("class");
		var _clas = _cla.replace('col-sm-1', "").replace('col-sm-2', "")
				.replace('col-sm-3', "").replace('col-sm-4', "").replace(
						'col-sm-5', "").replace('col-sm-6', "").replace(
						'col-sm-7', "").replace('col-sm-8', "").replace(
						'col-sm-9', "").replace('col-sm-10', "").replace(
						'col-sm-11', "").replace('col-sm-12', "");
		presentElement.children('div').children('div').attr("class",
				_clas + " " + msg.value);
	}
	if (msg.name == '尺寸') {
		var _cla = presentElement.children('div').attr("class");
		var _clas = _cla.replace('form-group-sm', "").replace('form-group-lg',
				"").replace('form-group', "");
		presentElement.children('div').attr('class', _clas + " " + msg.value);
	}
	if (msg.name == '隐藏label') {
		presentElement.children('div').children('label').attr("style",
				msg.value);
	}
	if (msg.name == '排列方式') {
		var _formType = presentElement.closest('form').hasClass(
				'form-horizontal');
		if (_formType) {
			if (msg.value.trim() === 'inline') {
				var clas = presentElement.children("div").children("div")
						.children("div").attr("class");
				var cla = clas.split('-');
				presentElement.children("div").children("div").children("div")
						.attr("class", cla[0] + '-inline');
			} else if (msg.value.trim() === 'default') {
				var clas = presentElement.children("div").children("div")
						.children("div").attr("class");
				var cla = clas.split('-');
				presentElement.children("div").children("div").children("div")
						.attr("class", cla[0]);
			} else if (msg.value.trim() === 'horizontal') {
				var clas = presentElement.children("div").children("div")
						.children("div").attr("class");
				var cla = clas.split('-');
				presentElement.children("div").children("div").children("div")
						.attr("class", cla[0] + "-horizontal");
			}
		} else {
			if (msg.value.trim() === 'inline') {
				var clas = presentElement.children("div").children("div").attr(
						"class");
				var cla = clas.split('-');
				presentElement.children("div").children("div").attr("class",
						cla[0] + '-inline');
			} else if (msg.value.trim() === 'default') {
				var clas = presentElement.children("div").children("div").attr(
						"class");
				var cla = clas.split('-');
				presentElement.children("div").children("div").attr("class",
						cla[0]);
			} else if (msg.value.trim() === 'horizontal') {
				var clas = presentElement.children("div").children("div").attr(
						"class");
				var cla = clas.split('-');
				presentElement.children("div").children("div").attr("class",
						cla[0] + "-horizontal");
			}
		}

	}
	if (msg.name == '编辑') {
		var _formType = presentElement.closest('form').hasClass(
				'form-horizontal');
		if (_formType) {
			var dropdown = presentElement.find('div.form-group').find('div');
			console.info(dropdown);
			var type = presentElement.find('input').attr('type');
			var _cla = presentElement.children("div").children("div").children(
					"div").attr("class");
			var _id = presentElement.find('input').attr('id');
			var _value = presentElement.find('input').attr('value');
			var _name = presentElement.find('input').attr('name');
			var s = msg.value.split("\n");
			var length = s.length;
			dropdown.empty();
			var str = "";
			if (type === 'checkbox') {
				for (var i = 0; i < length; i++) {
					if (s[i].trim() == '') {
						continue;
					}
					str = '<div class="' + _cla + '">'
							+ '<label style="visibility:visible">'
							+ '<input type="' + type + '" value=""/>' + s[i]
							+ '</lable></div>';
					dropdown.append(str);
				}
			} else if (type === 'radio') {
				for (var i = 0; i < length; i++) {
					if (s[i].trim() == '') {
						continue;
					}
					str = '<div class="' + _cla + '">'
							+ '<label style="visibility:visible">'
							+ '<input id = "' + _id.substring(0, 12) + i
							+ '" type="' + type + '" value="'
							+ _value.substring(0, 5) + '" name="' + _name
							+ '" />' + s[i] + '</lable></div>';
					dropdown.append(str);
				}
			}
		} else {
			var dropdown = presentElement.find('div.form-group').find('div');
			console.info(dropdown);
			var type = presentElement.find('input').attr('type');
			var _cla = presentElement.children("div").children("div").attr(
					"class");
			var _id = presentElement.find('input').attr('id');
			var _value = presentElement.find('input').attr('value');
			var _name = presentElement.find('input').attr('name');
			var s = msg.value.split("\n");
			var length = s.length;
			dropdown.remove();
			var div = presentElement.find('div.form-group');
			var str = "";
			if (type === 'checkbox') {
				for (var i = 0; i < length; i++) {
					if (s[i].trim() == '') {
						continue;
					}
					str = '<div class="' + _cla + '">'
							+ '<label style="visibility:visible">'
							+ '<input type="' + type + '" value=""/>' + s[i]
							+ '</lable></div>';
					div.append(str);
				}
			} else if (type === 'radio') {
				for (var i = 0; i < length; i++) {
					if (s[i].trim() == '') {
						continue;
					}
					str = '<div class="' + _cla + '">'
							+ '<label style="visibility:visible">'
							+ '<input id = "' + _id.substring(0, 12) + i
							+ '" type="' + type + '" value="'
							+ _value.substring(0, 5) + '" name="' + _name
							+ '" />' + s[i] + '</lable></div>';
					div.append(str);
				}
			}
		}
	} else if (msg.name == '校验') {
		presentElement.find('input').each(function() {
			validators($(this), msg.value.trim());
		});
	}
}
// 
function radioCheckbox_handler(msg) {
	if (msg.name == 'id') {
		presentElement.attr("id", msg.value);
	} else if (msg.name == 'name') {
		presentElement.attr("name", msg.value);
	} else if (msg.name == 'value') {
		presentElement.attr("value", msg.value);
	} else if (msg.name == 'style') {
		presentElement.attr("style", msg.value);
	} else if (msg.name == '文本') {
		var _input = presentElement.find("input");
		presentElement.text(msg.value);
		presentElement.prepend(_input);
	} else if (msg.name == '隐藏') {
		presentElement.parent().parent().prev().attr("style", msg.value);
	}
}

function label_handler(msg) {
	// 判断label类型
	if (msg.editor.type == 'combobox') {
		/*
		 * var data = msg.editor.options.data; var length = data.length; var r =
		 * ''; for(var i=0;i<length;i++){ r += data[i].value + ' '; }
		 * presentElement.removeClass(r);
		 * presentElement.addClass(msg.value);//添加新属性
		 */
	} else if (msg.editor == 'text') {
		if (msg.name == "name") {
			presentElement.attr('name', msg.value);
		} else if (msg.name == "wenben") {
			var regex = buildRegex('<[ ]*.+"[ ]*>', 'gi');
			var result = presentElement.html().match(regex);
			if (null == result || 0 == result.length) {
				presentElement[0].innerHTML = msg.value;
			} else {
				var html = '';
				for (var i = 0; i < result.length; i++) {
					html = html + result[i] + "\n";
				}
				html = html + msg.value;
				presentElement[0].innerHTML = html;
			}
		} else if (msg.name == "type") {
			presentElement[0].type = msg.value;
		} else if (msg.name == "id") {
			presentElement.attr('id', msg.value);
		} else if (msg.name == 'style') {
			presentElement.attr('style', msg.value);
		}
	}
}

/*
 * 输入框
 */
function input_handler(msg) {
	if (msg.name == 'type') {
		presentElement.attr('type', msg.value);
	} else if (msg.name == 'id') {
		presentElement.attr('id', msg.value);
	} else if (msg.name == 'name') {
		presentElement.attr('name', msg.value);
	} else if (msg.name == 'value') {
		presentElement.attr('value', msg.value);
	} else if (msg.name == 'style') {
		presentElement.attr('style', msg.value);
	} else if (msg.name == '注解') {
		presentElement.attr('placeholder', msg.value);
	} else if (msg.name == '校验') {
		validators(presentElement, msg.value.trim());
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class').split(' ');
			var _clas = '';
			for (var i = 0; i < _cla.length; i++) {
				if (_cla[i] === 'col-sm-1' || _cla[i] === 'col-sm-2'
						|| _cla[i] === 'col-sm-3' || _cla[i] === 'col-sm-4'
						|| _cla[i] === 'col-sm-5' || _cla[i] === 'col-sm-6'
						|| _cla[i] === 'col-sm-7' || _cla[i] === 'col-sm-8'
						|| _cla[i] === 'col-sm-9' || _cla[i] === 'col-sm-10'
						|| _cla[i] === 'col-sm-11' || _cla[i] === 'col-sm-12') {
					_clas += msg.value;
				} else {
					_clas += _cla[i];
				}
				presentElement.closest('div.view').children('div').children(
						'div').attr('class', _clas);
			}

		}
		// presentElement.closest('div.view').children('div').children('div').attr('class',msg.value);
	} else if (msg.name == '左占') {
		var _cla = presentElement.closest('div.view').children('div').children(
				'label').attr('class').split(' ');
		var _clas = '';
		for (var i = 0; i < _cla.length; i++) {
			if (_cla[i] === 'col-sm-1' || _cla[i] === 'col-sm-2'
					|| _cla[i] === 'col-sm-3' || _cla[i] === 'col-sm-4'
					|| _cla[i] === 'col-sm-5' || _cla[i] === 'col-sm-6'
					|| _cla[i] === 'col-sm-7' || _cla[i] === 'col-sm-8'
					|| _cla[i] === 'col-sm-9' || _cla[i] === 'col-sm-10'
					|| _cla[i] === 'col-sm-11' || _cla[i] === 'col-sm-12') {
				_clas += msg.value + ' control-label';
			} else if (_cla[i] === 'control-label') {

			} else {
				_clas += _cla[i];
			}
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', _clas);
		}
		// presentElement.closest('div.view').children('div').children('label').attr('class',msg.value);
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr('style', msg.value);
	} else if (msg.name == '尺寸') {
		if (msg.value != '') {
			var cla = presentElement.closest('div.view').children('div').attr(
					'class');
			var cls = cla.replace('form-group-lg', "").replace('form-group-sm',
					"");
			presentElement.closest('div.view').children('div').attr('class',
					cls + " " + msg.value);
		} else {
			var cla = presentElement.closest('div.view').children('div').attr(
					'class');
			var cls = cla.replace('form-group-lg', "").replace('form-group-sm',
					"");
			presentElement.closest('div.view').children('div').attr('class',
					cls);
		}
	} else if (msg.name == '禁用') {
		if (msg.value == 'true') {
			presentElement.attr('disabled', 'disabled');
		} else {
			presentElement.removeAttr("disabled");
		}

	}

	/* txc and 添加判断是否为校验 20171229 */
	else if (msg.effect == 'check') {
		if (msg.value == 'true') {
			presentElement.attr(msg.inputCheck, 'true');
		} else if (msg.value == 'false') {
			presentElement.removeAttr(msg.inputCheck);
		} else if (msg.name == '最大长度') {
			presentElement.attr('maxlength', msg.value);
		} else if (msg.name == '最小长度') {
			presentElement.attr('check-minlength', msg.value);
		} else if (msg.name == '定长校验') {
			presentElement.attr('check-fixedLength', msg.value);
		}else if (msg.name == '最小范围') {
			presentElement.attr('min', msg.value);
		} else if (msg.name == '最大范围') {
			presentElement.attr('max', msg.value);
		} else if (msg.name == '单选框属性') {
			presentElement.attr('checkbtn', msg.value);
		} else if (msg.name == '复选框属性') {
			presentElement.attr('checkbtn', msg.value);
		}

	} else if (msg.name == '只读') {
		if (msg.value == 'true') {
			presentElement.attr('readonly', 'readonly');
		} else {
			presentElement.removeAttr("readonly");
		}

	} else if (msg.name == '前裹类型') {
		if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
			var _type = presentElement.attr('prevtype');
			if (_type != msg.value) {
				console.info('----------------');
				console.info(presentElement.prev('div'));
				presentElement.prev('div').empty();
				presentElement.attr('prevtype', msg.value);
			}

		}

	} else if (msg.name == '前裹内容') {
		var prevtype = presentElement.attr('prevtype');
		if (prevtype === 'text') {// 文本
			if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
				if (presentElement.parents(".input-group").length > 0) {
					if (presentElement.prev('div').length > 0) {
						var span = "<span>"
								+ msg.value.trim().replace(/[\r\n]/g, "")
								+ "</span>";
						presentElement.prev('div').empty();
						presentElement.prev('div').append(span);
					} else {
						presentElement
								.before("<div class='input-group-addon'><span>"
										+ msg.value.trim().replace(/[\r\n]/g,
												"") + "</span></div>");
					}
				} else {
					presentElement.wrap("<div class='input-group'></div>");
					presentElement
							.before("<div class='input-group-addon'><span>"
									+ msg.value.trim().replace(/[\r\n]/g, "")
									+ "</span></div>");
				}
			} else {
				if (presentElement.next().length > 0) {
					presentElement.prev('div').remove();
				} else {
					var group = presentElement.parents(".input-group").parent();
					var input = presentElement;
					presentElement.parents(".input-group").remove();
					group.append(input);
				}
			}
		} else if (prevtype === 'checkbox') {// 多选框
			if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
				if (presentElement.parents(".input-group").length > 0) {
					if (presentElement.prev().length > 0) {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<span><input type = 'checkbox' name = 'prevchechbox'>"
									+ _val[i] + "</input></span>";
						}
						presentElement.prev().empty();
						presentElement.prev().append(input);
					} else {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<span><input type = 'checkbox' name = 'prevchechbox'>"
									+ _val[i] + "</input></span>";
						}
						presentElement
								.before("<span class='input-group-addon'>"
										+ input + "</div>");
					}
				} else {
					presentElement.wrap("<div class='input-group'></div>");
					var _val = msg.value.trim().split('\n');
					var input = '';
					for (var i = 0; i < _val.length; i++) {
						input += "<span><input type = 'checkbox' name = 'prevchechbox'>"
								+ _val[i] + "</input></span>";
					}
					presentElement.before("<div class='input-group-addon'>"
							+ input + "</div>");
				}
			} else {
				if (presentElement.next().length > 0) {
					presentElement.prev().remove();
				} else {
					var group = presentElement.parents(".input-group").parent();
					var input = presentElement;
					presentElement.parents(".input-group").remove();
					group.append(input);
				}
			}
		} else if (prevtype === 'radio') {// 单选框
			if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
				if (presentElement.parents(".input-group").length > 0) {
					if (presentElement.prev().length > 0) {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<span><input type = 'radio' name = 'prevradio'>"
									+ _val[i] + "</input></span>";
						}
						presentElement.prev().empty();
						presentElement.prev().append(input);
					} else {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<span><input type = 'radio' name = 'prevradio'>"
									+ _val[i] + "</input></span>";
						}
						presentElement
								.before("<span class='input-group-addon'>"
										+ input + "</div>");
					}
				} else {
					presentElement.wrap("<div class='input-group'></div>");
					var _val = msg.value.trim().split('\n');
					var input = '';
					for (var i = 0; i < _val.length; i++) {
						input += "<span><input type = 'radio' name = 'prevradio'>"
								+ _val[i] + "</input></span>";
					}
					presentElement.before("<div class='input-group-addon'>"
							+ input + "</div>");
				}
			} else {
				if (presentElement.next().length > 0) {
					presentElement.prev().remove();
				} else {
					var group = presentElement.parents(".input-group").parent();
					var input = presentElement;
					presentElement.parents(".input-group").remove();
					group.append(input);
				}
			}
		} else if (prevtype === 'button') {// 按钮
			if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
				if (presentElement.parents(".input-group").length > 0) {
					if (presentElement.prev().length > 0) {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<button type = 'button' >" + _val[i]
									+ "</button>";
						}
						presentElement.prev().empty();
						presentElement.prev().append(input);
					} else {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<button type = 'button' >" + _val[i]
									+ "</button>";
						}
						presentElement
								.before("<span class='input-group-addon'>"
										+ input + "</div>");
					}
				} else {
					presentElement.wrap("<div class='input-group'></div>");
					var _val = msg.value.trim().split('\n');
					var input = '';
					for (var i = 0; i < _val.length; i++) {
						input += "<button type = 'button' >" + _val[i]
								+ "</button>";
					}
					presentElement.before("<div class='input-group-addon'>"
							+ input + "</div>");
				}
			} else {
				if (presentElement.next().length > 0) {
					presentElement.prev().remove();
				} else {
					var group = presentElement.parents(".input-group").parent();
					var input = presentElement;
					presentElement.parents(".input-group").remove();
					group.append(input);
				}
			}
		}/*
			 * else if(prevtype === 'dropmenu'){//下拉菜单
			 * if(msg.value.trim()!='undefined' && msg.value.trim()!=""){ if (
			 * presentElement.parents(".input-group").length > 0 ){
			 * if(presentElement.prev().length > 0){ var _val =
			 * msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button" data-toggle="dropdown"
			 * aria-haspopup="true" aria-expanded="false"style="padding:auto">'+
			 * 'Action <span class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.prev().empty();
			 * presentElement.prev().append(input); }else{ var _val =
			 * msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button" data-toggle="dropdown"
			 * aria-haspopup="true" aria-expanded="false"style="padding:auto">'+
			 * 'Action <span class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.before("<span
			 * class='input-group-addon'>"+input+"</div>"); } }else{
			 * presentElement.wrap("<div class='input-group'></div>"); var
			 * _val = msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button" data-toggle="dropdown"
			 * aria-haspopup="true" aria-expanded="false"
			 * style="padding:auto">'+ 'Action <span class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.before("<div
			 * class='input-group-addon'>"+input+"</div>"); } }else{
			 * if(presentElement.next().length > 0){
			 * presentElement.prev().remove(); }else{ var group =
			 * presentElement.parents(".input-group").parent(); var input =
			 * presentElement; presentElement.parents(".input-group").remove();
			 * group.append(input); } } }else if(prevtype ===
			 * 'splitdropmenu'){//分列式下拉菜单 if(msg.value.trim()!='undefined' &&
			 * msg.value.trim()!=""){ if (
			 * presentElement.parents(".input-group").length > 0 ){
			 * if(presentElement.prev().length > 0){ var _val =
			 * msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button">Action</button><button
			 * type="button" data-toggle="dropdown" aria-haspopup="true"
			 * aria-expanded="false"style="padding:auto">'+ ' <span
			 * class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.prev().empty();
			 * presentElement.prev().append(input); }else{ var _val =
			 * msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button">Action</button><button
			 * type="button" data-toggle="dropdown" aria-haspopup="true"
			 * aria-expanded="false"style="padding:auto">'+ '<span
			 * class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.before("<span
			 * class='input-group-addon'>"+input+"</div>"); } }else{
			 * presentElement.wrap("<div class='input-group'></div>"); var
			 * _val = msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button">Action</button><button
			 * type="button" data-toggle="dropdown" aria-haspopup="true"
			 * aria-expanded="false" style="padding:auto">'+ ' <span
			 * class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.before("<div
			 * class='input-group-addon'>"+input+"</div>"); } }else{
			 * if(presentElement.next().length > 0){
			 * presentElement.prev().remove(); }else{ var group =
			 * presentElement.parents(".input-group").parent(); var input =
			 * presentElement; presentElement.parents(".input-group").remove();
			 * group.append(input); } } }
			 */
	} else if (msg.name == '后裹类型') {
		if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
			var _type = presentElement.attr('nexttype');
			if (_type != msg.value) {
				presentElement.next().empty();
				presentElement.attr('nexttype', msg.value);
			}
		}

	} else if (msg.name == '后裹内容') {
		var nexttype = presentElement.attr('nexttype');
		if (nexttype === 'text') {// 文本
			if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
				if (presentElement.parents(".input-group").length > 0) {
					if (presentElement.next().length > 0) {
						var span = "<span>"
								+ msg.value.trim().replace(/[\r\n]/g, "")
								+ "</span>";
						presentElement.next().empty();
						presentElement.next().append(span);
					} else {
						presentElement
								.after("<span class='input-group-addon'><span>"
										+ msg.value.trim().replace(/[\r\n]/g,
												"") + "</span></span>");
					}
				} else {
					presentElement.wrap("<div class='input-group'></div>");
					presentElement
							.after("<span class='input-group-addon'><span>"
									+ msg.value.trim().replace(/[\r\n]/g, "")
									+ "</span></span>");
				}
			} else {
				if (presentElement.prev().length > 0) {
					presentElement.next().remove();
				} else {
					var group = presentElement.parents(".input-group").parent();
					var input = presentElement;
					presentElement.parents(".input-group").remove();
					group.append(input);
				}
			}
		} else if (nexttype === 'checkbox') {// 多选框
			if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
				if (presentElement.parents(".input-group").length > 0) {
					if (presentElement.next().length > 0) {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<span><input type = 'checkbox' name = 'nextchechbox'>"
									+ _val[i] + "</input></span>";
						}
						presentElement.next().empty();
						presentElement.next().append(input);
					} else {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<span><input type = 'checkbox' name = 'nextchechbox'>"
									+ _val[i] + "</input></span>";
						}
						presentElement.after("<span class='input-group-addon'>"
								+ input + "</span>");
					}
				} else {
					presentElement.wrap("<div class='input-group'></div>");
					var _val = msg.value.trim().split('\n');
					var input = '';
					for (var i = 0; i < _val.length; i++) {
						input += "<span><input type = 'checkbox' name = 'nextchechbox'>"
								+ _val[i] + "</input></span>";
					}
					presentElement.after("<span class='input-group-addon'>"
							+ input + "</span>");
				}
			} else {
				if (presentElement.prev().length > 0) {
					presentElement.next().remove();
				} else {
					var group = presentElement.parents(".input-group").parent();
					var input = presentElement;
					presentElement.parents(".input-group").remove();
					group.append(input);
				}
			}
		} else if (nexttype === 'radio') {// 单选框
			if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
				if (presentElement.parents(".input-group").length > 0) {
					if (presentElement.next().length > 0) {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<span><input type = 'radio' name = 'nextradio'>"
									+ _val[i] + "</input></span>";
						}
						presentElement.next().empty();
						presentElement.next().append(input);
					} else {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<span><input type = 'radio' name = 'nextradio'>"
									+ _val[i] + "</input></span>";
						}
						presentElement.after("<span class='input-group-addon'>"
								+ input + "</span>");
					}
				} else {
					presentElement.wrap("<div class='input-group'></div>");
					var _val = msg.value.trim().split('\n');
					var input = '';
					for (var i = 0; i < _val.length; i++) {
						input += "<span><input type = 'radio' name = 'nextradio'>"
								+ _val[i] + "</input></span>";
					}
					presentElement.after("<span class='input-group-addon'>"
							+ input + "</span>");
				}
			} else {
				if (presentElement.prev().length > 0) {
					presentElement.next().remove();
				} else {
					var group = presentElement.parents(".input-group").parent();
					var input = presentElement;
					presentElement.parents(".input-group").remove();
					group.append(input);
				}
			}
		} else if (nexttype === 'button') {// 按钮
			if (msg.value.trim() != 'undefined' && msg.value.trim() != "") {
				if (presentElement.parents(".input-group").length > 0) {
					if (presentElement.next().length > 0) {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<button type = 'button' >" + _val[i]
									+ "</button>";
						}
						presentElement.next().empty();
						presentElement.next().append(input);
					} else {
						var _val = msg.value.trim().split('\n');
						var input = '';
						for (var i = 0; i < _val.length; i++) {
							input += "<button type = 'button' >" + _val[i]
									+ "</button>";
						}
						presentElement.after("<span class='input-group-addon'>"
								+ input + "</span>");
					}
				} else {
					presentElement.wrap("<div class='input-group'></div>");
					var _val = msg.value.trim().split('\n');
					var input = '';
					for (var i = 0; i < _val.length; i++) {
						input += "<button type = 'button' >" + _val[i]
								+ "</button>";
					}
					presentElement.after("<span class='input-group-addon'>"
							+ input + "</span>");
				}
			} else {
				if (presentElement.prev().length > 0) {
					presentElement.next().remove();
				} else {
					var group = presentElement.parents(".input-group").parent();
					var input = presentElement;
					presentElement.parents(".input-group").remove();
					group.append(input);
				}
			}
		}/*
			 * else if(nexttype === 'dropmenu'){//下拉菜单
			 * if(msg.value.trim()!='undefined'&&msg.value.trim()!=""){
			 * //value内容存在 if ( presentElement.parents(".input-group").length >
			 * 0 ){ //后裹内容存在 if(presentElement.next().length > 0){ //在原来后裹基础上 修改
			 * 或增加 var _val = msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button" data-toggle="dropdown"
			 * aria-haspopup="true" aria-expanded="false"style="padding:auto">'+
			 * 'Action <span class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } input +="</ul></div>";
			 * presentElement.next().empty();
			 * presentElement.next().append(input); }else{ // 在没有原来后裹的情况下增加后裹
			 * var _val = msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button" data-toggle="dropdown"
			 * aria-haspopup="true" aria-expanded="false"style="padding:auto">'+
			 * 'Action <span class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } input += "</ul></div>";
			 * presentElement.after("<span class='input-group-addon'></span>");
			 * presentElement.next().append(input); } }else{ // 不存在前裹或者后裹
			 * presentElement.wrap("<div class='input-group'></div>"); var
			 * _val = msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button" data-toggle="dropdown"
			 * aria-haspopup="true" aria-expanded="false"style="padding:auto">'+
			 * 'Action <span class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } input += "</ul></div>";
			 * presentElement.after("<span class='input-group-addon'></span>");
			 * presentElement.next().append(input); } }else{//value内容不存在
			 * if(presentElement.prev().length > 0){ //前裹内容存在
			 * presentElement.next().remove(); //只删除后裹内容 }else{//前裹内容不存在 删除div
			 * 重新插入该输入框 var group =
			 * presentElement.parents(".input-group").parent(); var input =
			 * presentElement; presentElement.parents(".input-group").remove();
			 * group.append(input); } } }else if(nexttype ===
			 * 'splitdropmenu'){//分列式下拉菜单
			 * if(msg.value.trim()!='undefined'&&msg.value.trim()!=""){ if (
			 * presentElement.parents(".input-group").length > 0 ){
			 * if(presentElement.next().length > 0){ var _val =
			 * msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button">Action</button><button
			 * type="button" data-toggle="dropdown" aria-haspopup="true"
			 * aria-expanded="false"style="padding:auto">'+ ' <span
			 * class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.next().empty();
			 * presentElement.next().append(input); }else{ var _val =
			 * msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button">Action</button><button
			 * type="button" data-toggle="dropdown" aria-haspopup="true"
			 * aria-expanded="false"style="padding:auto">'+ ' <span
			 * class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.after("<span
			 * class='input-group-addon'>"+input+"</span>"); } }else{
			 * presentElement.wrap("<div class='input-group'></div>"); var
			 * _val = msg.value.trim().split('\n'); var input = '<div
			 * class="input-group"><button type="button">Action</button><button
			 * type="button" data-toggle="dropdown" aria-haspopup="true"
			 * aria-expanded="false"style="padding:auto">'+ ' <span
			 * class="caret"></span></button><ul class="dropdown-menu">';
			 * for(var i=0;i<_val.length;i++){ input += "<li><a href='#'>
			 * "+_val[i]+"</a></li>"; } presentElement.after("<span
			 * class='input-group-addon'>"+input+"</span>"); } }else{
			 * if(presentElement.prev().length > 0){
			 * presentElement.next().remove(); }else{ var group =
			 * presentElement.parents(".input-group").parent(); var input =
			 * presentElement; presentElement.parents(".input-group").remove();
			 * group.append(input); } } }
			 */
	}
}

/*
 * 按钮
 */
function button_handler(msg) {
	console.info("btn_html=" + presentElement.html() + "\nbtn_parnet_html="
			+ presentElement.parent().html());
	var cld = presentElement.children();
	var arrFloat = new Array("pull-left", "pull-right");
	var _btngroup = presentElement.closest('.btn-group-justified');
	if (msg.editor.type == 'combobox') {
		if (msg.name == 'type') {
			// 20160929 add by chenyl 提交、重置按钮的特殊处理
			presentElement.attr("btn-type", msg.value);
			presentElement.attr("type", "button");
		} else if (msg.name == '内嵌') {
			// 如果button有子元素清空
			var _id = 'downmenu' + randomNumber();
			if (cld.length > 0) {
				var cls = presentElement.attr("class");
				// 下拉菜单处理
				if (cls.indexOf("dropdown-toggle") >= 0) {
					presentElement.unwrap();
					presentElement.parent().removeClass(
							"dropdown pull-left pull-right");
					presentElement.removeClass("dropdown-toggle");
					presentElement.removeAttr("data-toggle");
					presentElement.next().remove();
				}
			}
			cld.remove();
			// 添加元素
			if (msg.value == "badge") {
				presentElement.append('<span class="badge">1</span>');
			} else if (msg.value == "i") {
				presentElement.append('<i></i>');
			} else if (msg.value == "dropmenu") {
				// 若不为两端对齐添加用div包裹
				var _float = "";
				if (_btngroup.length <= 0) {
					for (var i = 0; i < arrFloat.length; i++) {
						if (presentElement.hasClass(arrFloat[i])) {
							_float = arrFloat[i];
						}
					}
					presentElement.wrap('<div class="btn-group ' + _float
							+ '" role="group"></div>');
				}
				presentElement.attr("data-toggle", "dropdown");
				presentElement.attr("id", _id);
				presentElement.addClass("dropdown-toggle");
				presentElement.append('<span class="caret"></span>');
				presentElement
						.after('<ul class="dropdown-menu" aria-labelledby="'
								+ _id + '" role="menu">  </ul>');
			}
			var attrs = button_attr(presentElement);
			sendMessage('parent', attrs);
		} else if (msg.name == "图片位置") {
			var t = presentElement.find(">i");
			if (t.size() > 0) {
				t.remove();
				if (msg.value == "left") {
					presentElement.prepend(t);
				} else if (msg.value == "right") {
					presentElement.append(t);
				}
			}
		} else {
			var data = msg.editor.options.data;
			var length = data.length;
			var r = '';
			for (var i = 0; i < length; i++) {
				r += data[i].value + ' ';
			}
			presentElement.removeClass(r);
			presentElement.addClass(msg.value);// 添加新属性
			if (msg.name == "浮动") {
				if (cld.length > 0) {
					var cls = presentElement.attr("class");
					// 判断下拉菜单ul增加属性
					if (cls.indexOf("dropdown-toggle") >= 0) {
						presentElement.parent().removeClass(r);// 父类div删除class
						presentElement.parent().addClass(msg.value);// 添加新属性
						presentElement.next().removeClass(r);
						presentElement.next().addClass(msg.value);// 添加新属性
					}
				}
			}
		}
	} else if (msg.editor == 'text') {
		if (msg.name == "name") {
			presentElement.attr('name', msg.value);
		} else if (msg.name == "wenben") {
			var ch = presentElement.children();
			presentElement.text(msg.value);
			if (ch.length !== 0) {
				presentElement.append(ch);
			}
		} else if (msg.name == "id") {
			presentElement.attr('id', msg.value);
		} else if (msg.name == 'style') {
			presentElement.attr('style', msg.value);
		} else if (msg.name == 'value') {
			presentElement.attr("value", msg.value);
		} else if (msg.name == 'src') {
			// 设置图片
			var t = presentElement.find(">i");
			if (t.size() > 0) {
				t.attr("class", msg.value);
			}
		} else if (msg.name == '设置徽章') {
			var t = presentElement.find(">span.badge");
			t.text(msg.value);
		} else if (msg.name == '设置权限') { // add by chenyl
			var t = presentElement.parent();
			if (msg.value == undefined || '' == msg.value) {
				t.attr('name', 'anno'); // 不设置权限
			} else {
				t.attr('name', msg.value);
			}
			presentElement = t;
			// presentElement.wrap("<shiro:hasPermission
			// name='"+msg.value+"'></shiro:hasPermission>");
			console.info('设置权限=' + presentElement.html());
		}
	} else if (msg.editor == 'textarea') {
		if (msg.name == '编辑') {
			var dropdown = presentElement.next();
			console.info(dropdown);
			// 下拉菜单编辑
			var s = msg.value.split("\n");
			var length = s.length;
			dropdown.empty();
			var str = "";
			for (var i = 0; i < length; i++) {
				if (s[i].trim() == '') {
					continue;
				}
				if (s[i] == "----") {
					str = '<li class="divider"></li>';
				} else {
					str = '<li> <a href="javascript:void(0);">' + s[i]
							+ '</a></li>';
				}
				dropdown.append(str);
			}
		}
	} else if (msg.editor.type == 'checkbox') {
		var r = msg.editor.options.on + " " + msg.editor.options.off;
		presentElement.removeClass(r);
		if (msg.value.trim() != "") {
			presentElement.addClass(msg.value);
		}
	}
}

/*
 * 文本域
 */
function textarea_handler(msg) {
	if (msg.name == '左占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var cla = presentElement.closest('div.view').children('div')
					.children('label').attr('class');
			var cls = cla.replace('col-sm-1 control-label', "").replace(
					'col-sm-2 control-label', "").replace(
					'col-sm-3 control-label', "").replace(
					'col-sm-4 control-label', "").replace(
					'col-sm-5 control-label', "").replace(
					'col-sm-6 control-label', "").replace(
					'col-sm-7 control-label', "").replace(
					'col-sm-8 control-label', "").replace(
					'col-sm-9 control-label', "").replace(
					'col-sm-10 control-label', "").replace(
					'col-sm-11 control-label', "").replace(
					'col-sm-12 control-label', "");
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', cls + ' ' + msg.value);
		}
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class');
			var cls = cla.replace('col-sm-1', "").replace('col-sm-2', "")
					.replace('col-sm-3', "").replace('col-sm-4', "").replace(
							'col-sm-5', "").replace('col-sm-6', "").replace(
							'col-sm-7', "").replace('col-sm-8', "").replace(
							'col-sm-9', "").replace('col-sm-10', "").replace(
							'col-sm-11', "").replace('col-sm-12', "");
			presentElement.closest('div.view').children('div').children('div')
					.attr('class', cls + ' ' + msg.value);
		}
	} else if (msg.name == '尺寸') {
		if (msg.value != '') {
			var cla = presentElement.closest('div.view').children('div').attr(
					'class');
			var cls = cla.replace('form-group-lg', "").replace('form-group-sm',
					"");
			presentElement.closest('div.view').children('div').attr('class',
					cls + " " + msg.value);
		} else {
			var cla = presentElement.closest('div.view').children('div').attr(
					'class');
			var cls = cla.replace('form-group-lg', "").replace('form-group-sm',
					"");
			presentElement.closest('div.view').children('div').attr('class',
					cls);
		}
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr("style", msg.value);
	} else if (msg.name == 'id') {
		presentElement.attr("id", msg.value);
	} else if (msg.name == 'name') {
		presentElement.attr("name", msg.value);
	} else if (msg.name == 'style') {
		presentElement.attr("style", msg.value);
	} else if (msg.name == 'value') {
		presentElement.attr("value", msg.value);
	} else if (msg.name == '禁用') {
		if (msg.value == 'true') {
			presentElement.attr('disabled', 'disabled');
		} else {
			presentElement.removeAttr("disabled");
		}
	} else if (msg.name == '只读') {
		if (msg.value == 'true') {
			presentElement.attr('readonly', 'readonly');
		} else {
			presentElement.removeAttr("readonly");
		}
	} else if (msg.name == '注解') {
		presentElement.attr('placeholder', msg.value);
	} else if (msg.name == '行数') {
		presentElement.attr('rows', msg.value);
	} else if (msg.name == '校验') {
		validators(presentElement, msg.value.trim());
	}/* 添加判断是否为校验 20171229 */
	else if (msg.effect == 'check') {
		console.log(111);
		if (msg.value == 'true') {
			presentElement.attr(msg.inputCheck, 'true');
		} else if (msg.value == 'false') {
			presentElement.removeAttr(msg.inputCheck);
		} else if (msg.name == '最大长度') {
			presentElement.attr('maxlength', msg.value);
		} else if (msg.name == '最小长度') {
			presentElement.attr('check-minlength', msg.value);
		}

	}
}
/*
 * 下拉列表
 */
var showMultiselect = 0;
function select_handler(msg) {
	console.info(msg + " 发送的是什么---");
	if (msg.name == '左占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var cla = presentElement.closest('div.view').children('div')
					.children('label').attr('class');
			var cls = cla.replace('col-sm-1 control-label', "").replace(
					'col-sm-2 control-label', "").replace(
					'col-sm-3 control-label', "").replace(
					'col-sm-4 control-label', "").replace(
					'col-sm-5 control-label', "").replace(
					'col-sm-6 control-label', "").replace(
					'col-sm-7 control-label', "").replace(
					'col-sm-8 control-label', "").replace(
					'col-sm-9 control-label', "").replace(
					'col-sm-10 control-label', "").replace(
					'col-sm-11 control-label', "").replace(
					'col-sm-12 control-label', "");
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', cls + ' ' + msg.value);
		}
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class');
			var cls = cla.replace('btn-group col-sm-1', "").replace(
					'btn-group col-sm-2', "").replace('btn-group col-sm-3', "")
					.replace('btn-group col-sm-4', "").replace(
							'btn-group col-sm-5', "").replace(
							'btn-group col-sm-6', "").replace(
							'btn-group col-sm-7', "").replace(
							'btn-group col-sm-8', "").replace(
							'btn-group col-sm-9', "").replace(
							'btn-group col-sm-10', "").replace(
							'btn-group col-sm-11', "").replace(
							'btn-group col-sm-12', "");
			presentElement.closest('div.view').children('div').children('div')
					.attr('class', cls + ' ' + msg.value);
		}
	} else if (msg.name == '尺寸') {
		if (msg.value != '') {
			var cla = presentElement.closest('div.view').children('div').attr(
					'class');
			var cls = cla.replace('form-group-lg', "").replace('form-group-sm',
					"");
			presentElement.closest('div.view').children('div').attr('class',
					cls + " " + msg.value);
		} else {
			var cla = presentElement.closest('div.view').children('div').attr(
					'class');
			var cls = cla.replace('form-group-lg', "").replace('form-group-sm',
					"");
			presentElement.closest('div.view').children('div').attr('class',
					cls);
		}
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr("style", msg.value);
	} else if (msg.name == 'id') {
		presentElement.attr("id", msg.value);
	} else if (msg.name == 'name') {
		presentElement.attr("name", msg.value);
	} else if (msg.name == 'value') {
		presentElement.find("option:selected").attr('value', msg.value);
	} else if (msg.name == 'style') {
		presentElement.attr("style", msg.value);
	} else if (msg.name == 'multiple') {

		var msgVal = msg.value;
		if (msgVal === "multiple") {
			presentElement.attr("multiple", msg.value);
		}
		// alert(msgVal+" cdcdcdcdc");
		if (msgVal === "nomultiple") {
			presentElement.removeAttr("multiple");
		}
	} else if (msg.name == 'url') {
		presentElement.attr("data-url", msg.value);
	}
	/* 20180623 add by chenyl for 新增multiselect同步加载功能*/
	 else if (msg.name == '是否同步加载') {
		presentElement.attr("data-async", msg.value);
	}
	/* txc and 20180117新增校验 */
	else if (msg.effect == 'check') {
		presentElement.attr('checkbtn', msg.value);
	} else if (msg.name == '是否显示空选项') {
		presentElement.attr("blank-item", msg.value);
	} else if (msg.name == '空选项标签值') {
		presentElement.attr("blank-text", msg.value);
	} else if (msg.name == '空选项值') {
		presentElement.attr("blank-value", msg.value);
	} else if (msg.name == '内容') {
		// 实现逻辑,先将行为"----"的行数保存至数组arr,数组包括第0个位置以及最后一个位置，然后根据保存的数组进行循环添加optgroup及option标签
		var dropdown = presentElement.next();
		console.info("heheh " + dropdown);
		// 下拉菜单编辑
		var s = msg.value.split("\n");
		var length = s.length;
		presentElement.empty();
		var str = "";
		var num = 1;
		for (var i = 0; i < length; i++) {
			if (s[i].trim() == '') {
				continue;
			}
			if (s[i].trim() == '----') {
				presentElement.append('<optgroup label="Group"' + num
						+ '></optgroup>');
				num++;
				continue;
			}
			if (s[i].split("#&")[1] == "") {
				str = '<option>' + s[i].split("#&")[0] + '</option>';
			} else {
				str = '<option value="' + s[i].split("#&")[1] + '">'
						+ s[i].split("#&")[0] + '</option>';
			}
			if (presentElement.children('optgroup').length > 0) {
				presentElement.children('optgroup').last().append(str);
			} else {
				presentElement.append(str);
			}
		}
	} else if (msg.name == '支持多选') {
		if (msg.value == "true") {
			presentElement.attr("multiple", "multiple");
		} else {
			presentElement.removeAttr("multiple");
		}
	} else if (msg.name == 'optgroup可点击') {
		presentElement.attr("data-enable-clickable-opt-groups", msg.value);
	} else if (msg.name == 'optgroup可折叠') {
		presentElement.attr("data-enable-collapsible-opt-groups", msg.value);
	} else if (msg.name == '无选项时下拉禁用') {
		presentElement.attr("data-disable-if-empty", msg.value);
	} else if (msg.name == '设置无选项下拉禁用时显示文本') {
		presentElement.attr("data-disabled-text", msg.value);
	} else if (msg.name == '下拉是否靠右') {
		presentElement.attr("data-drop-right", msg.value);
	} else if (msg.name == '下拉是否朝上') {
		presentElement.attr("data-drop-up", msg.value);
	} else if (msg.name == '设置下拉最大高度') {
		presentElement.attr("data-max-height", msg.value);
	} else if (msg.name == 'buttonClass') {
		presentElement.attr("data-button-class", msg.value);
	} else if (msg.name == '是否继承原始下拉中的button') {
		presentElement.attr("data-inherit-class", msg.value);
	} else if (msg.name == 'buttonContainer') {
		presentElement.attr("data-button-container", msg.value);
	} else if (msg.name == 'buttonWidth') {
		presentElement.attr("data-button-width", msg.value);
	} else if (msg.name == '没有选择选项时显示的文本') {
		presentElement.attr("data-non-selected-text", msg.value);
	} else if (msg.name == '选项超过numberDisplayed个数时显示的文本') {
		presentElement.attr("data-n-selected-text", msg.value);
	} else if (msg.name == '所有选项选中时显示的文本') {
		presentElement.attr("data-all-selected-text", msg.value);
	} else if (msg.name == '选中选项可视的最大个数') {
		presentElement.attr("data-number-displayed", msg.value);
	} else if (msg.name == '选中选项的分割符') {
		presentElement.attr("data-delimiter-text", msg.value);
	} else if (msg.name == '应用于选中选项的类(class)') {
		presentElement.attr("data-selected-class", msg.value);
	} else if (msg.name == '启用选择所有选项') {
		presentElement.attr("data-include-select-all-option", msg.value);
	} else if (msg.name == '选择所有选项总是只选择可视的选项') {
		presentElement.attr("data-select-all-just-visible", msg.value);
	} else if (msg.name == '设置selectAll的文本内容') {
		presentElement.attr("data-select-all-text", msg.value);
	} else if (msg.name == '设置selectAll的value') {
		presentElement.attr("data-select-all-value", msg.value);
	} else if (msg.name == '设置selectAll的name') {
		presentElement.attr("data-select-all-name", msg.value);
	} else if (msg.name == '是否显示selectAll的个数') {
		presentElement.attr("data-select-all-number", msg.value);
	} else if (msg.name == '启用过滤器') {
		presentElement.attr("data-enable-filtering", msg.value);
	} else if (msg.name == '过滤器不分大小写') {
		presentElement
				.attr("data-enable-case-insensitive-filtering", msg.value);
	} else if (msg.name == '全值过滤') {
		presentElement.attr("data-enable-full-value-filtering", msg.value);
	} else if (msg.name == '过滤依据') {
		presentElement.attr("data-filter-behavior", msg.value);
	} else if (msg.name == '过滤输入框placeholder') {
		presentElement.attr("data-filter-placeholder", msg.value);
	} else if (msg.name == '校验') {
		validators(presentElement, msg.value.trim());
	}
};

/*
 * 表格
 */
function table_handler(msg) {
	var t = presentElement.find(".table");
	if (msg.name == 'id') {
		t.attr('id', msg.value);
	} else if (msg.name == 'style') {
		t.attr("style", msg.value);
	} else if (msg.name == 'edit') {
		var s = msg.value.split("\n");
		var length = s.length;
		presentElement.find("thead tr").empty();
		for (var i = 0; i < length; i++) {
			if (s[i].trim() == '') {
				continue;
			}
			var _split = s[i].split("#&");

			presentElement.find("thead tr").append(
					'<th data-field="' + _split[0] + '">' + _split[1]
							+ '</th>\n');
		}
	} else if (msg.name == 'table类名称') {
		t.attr("data-classes", msg.value);
	} else if (msg.name == 'table高度') {
		t.attr("data-height", msg.value);
	} else if (msg.name == 'table未定义文本显示内容') {
		t.attr("data-undefined-text", msg.value);
	} else if (msg.name == '条纹') {
		t.attr("data-striped", msg.value);
	} else if (msg.name == '请求远程数据类型') {
		t.attr("data-method", msg.value);
	} else if (msg.name == 'rowstyle') {
		t.attr("data-row-style", msg.value);
	} else if (msg.name == '远程数据url') {
		var _msg = msg.value.trim();
		if (_msg != "" || _msg != null) {
			t.attr("data-url", _msg);
			t.attr("data-side-pagination", 'server');
			table();
		}
	} else if (msg.name == '显示分页') {
		t.attr("data-pagination", msg.value);
	} else if (msg.name == '表格真实数据数量') {
		t.attr("data-only-info-pagination", msg.value);
	} else if (msg.name == '表格边分页') {
		t.attr("data-side-pagination", msg.value);
	} else if (msg.name == '显示search输入框') {
		t.attr("data-search", msg.value);
	} else if (msg.name == '设置查询失败超时时间') {
		t.attr("data-search-time-out", msg.value);
	} else if (msg.name == '显示表头') {
		t.attr("data-show-header", msg.value);
	} else if (msg.name == '显示列下拉列表') {
		t.attr("data-show-columns", msg.value);
	} else if (msg.name == '显示刷新按钮') {
		t.attr("data-show-refresh", msg.value);
	} else if (msg.name == '显示切换按钮') {
		t.attr("data-show-toggle", msg.value);
	} else if (msg.name == '显示分页按钮开关') {
		t.attr("data-show-pagination-switch", msg.value);
	} else if (msg.name == '显示card视图') {
		t.attr("data-card-view", msg.value);
	} else if (msg.name == '对齐搜索输入') {
		t.attr("data-search-align", msg.value);
	} else if (msg.name == '对齐按钮组') {
		t.attr("data-buttons-align", msg.value);
	} else if (msg.name == '对齐toolbar') {
		t.attr("data-toolbar-align", msg.value);
	} else if (msg.name == '对齐分页(竖直)') {
		t.attr("data-pagination-v-align", msg.value);
	} else if (msg.name == '对齐分页(水平)') {
		t.attr("data-pagination-h-align", msg.value);
	} else if (msg.name == '对齐分页描述(水平)') {
		t.attr("data-pagination-detail-h-align", msg.value);
	} else if (msg.name == '首页') {
		t.attr("data-pagination-first-text", msg.value);
	} else if (msg.name == '前一页') {
		t.attr("data-pagination-pre-text", msg.value);
	} else if (msg.name == '后一页') {
		t.attr("data-pagination-next-text", msg.value);
	} else if (msg.name == '尾页') {
		t.attr("data-pagination-last-text", msg.value);
	} else if (msg.name == '单击行时选择checkbox/radiobox') {
		t.attr("data-click-to-select", msg.value);
	} else if (msg.name == '只允许选择一行') {
		t.attr("data-single-select", msg.value);
	} else if (msg.name == '设置toolbar') {
		t.attr("data-toolbar", msg.value);
	} else if (msg.name == 'checkbox全选') {
		t.attr("data-checkbox-header", msg.value);
	} else if (msg.name == '查询参数') {
		t.attr("data-query-params", msg.value);
	} else if (msg.name == '查询参数类型') {
		if (msg.value == "") {
			t.attr("data-query-params-type", "");
		} else {
			t.attr("data-query-params-type", msg.value);
		}
	} else if (msg.name == '是否排序') {
		t.attr('data-sortable', msg.value);
	} else if (msg.name == '排序方式') {
		t.attr('data-sort-order', msg.value);
	} else if (msg.name == '排序字段名(sortName)') {
		t.attr('data-sort-name', msg.value);
	} else if (msg.name == '绑定后台Po类') {
		var _msg = msg.value;
		if (_msg != null && _msg != "") {
			t.attr("poUrl", _msg);
			var reData = getResultJson(_msg);
			console.info(reData[0]);
			t.find("thead tr").empty();
			t.find("thead tr").append(
					'<th data-field="state" data-checkbox="true"></th>\n');
			for (var i = 0; i < reData.length; i++) {
				t.find("thead tr").append(
						'<th data-field="' + reData[i].text + '">'
								+ reData[i].value + '</th>\n');
			}
			table();
		}
	} else if (msg.name == '绑定后台structs文件') {
		var _path = msg.value.trim();
		if (_path != null && _path != "") {
			t.attr("xmlPath", _path);
			table();
		} else {
			t.removeAttr("xmlPath"); // add by chenyl
		}
	} else if (msg.name == "pageNumber") {
		t.attr('data-page-number', msg.value.trim());
	} else if (msg.name == "pageSize") {
		t.attr('data-page-size', msg.value.trim());
	} else if (msg.name == "pageList") {
		t.attr('data-page-list', msg.value.trim());
	} else if (msg.name == '显示export') {
		t.attr('data-show-export', msg.value);
	}
	/* add by chenyl 20170423 for 添加首次自动装载数据 */
	else if (msg.name == '是否首次自动装载数据') {
		t.attr('data-first-load', msg.value);
	}
}

function loadjscssfile(filename, filetype) {

	if (filetype == "js") {
		var fileref = document.createElement('script');
		fileref.setAttribute("type", "text/javascript");
		fileref.setAttribute("src", filename);
	} else if (filetype == "css") {

		var fileref = document.createElement('link');
		fileref.setAttribute("rel", "stylesheet");
		fileref.setAttribute("type", "text/css");
		fileref.setAttribute("href", filename);
	}
	if (typeof fileref != "undefined") {
		document.getElementsByTagName("head")[0].appendChild(fileref);
	}

}
/*
 * 表格columns
 */
function tableCol_handler(msg) {
	if (msg.name == 'radio') {
		presentElement.attr("data-radio", msg.value);
	} else if (msg.name == 'checkbox') {
		presentElement.attr("data-checkbox", msg.value);
	} else if (msg.name == 'field域') {
		presentElement.attr("data-field", msg.value);
	} else if (msg.name == 'title标题') {
		presentElement.attr('data-title', msg.value);
	} else if (msg.name == 'title标题提示') {
		presentElement.attr('data-title-tooltip', msg.value);
	} else if (msg.name == '列class') {
		presentElement.attr('data-class', msg.value);
	} else if (msg.name == '合并行') {
		presentElement.attr('data-rowspan', msg.value);
	} else if (msg.name == '合并列') {
		presentElement.attr('data-colspan', msg.value);
	} else if (msg.name == '列data对齐') {
		presentElement.attr('data-align', msg.value);
	} else if (msg.name == '列heading对齐') {
		presentElement.attr('data-halign', msg.value);
	} else if (msg.name == '列footer对齐') {
		presentElement.attr('data-falign', msg.value);
	} else if (msg.name == '列data对齐(竖直)') {
		presentElement.attr('data-valign', msg.value);
	} else if (msg.name == '列宽') {
		presentElement.attr('data-width', msg.value);
	} else if (msg.name == '是否排序') {
		presentElement.attr('data-sortable', msg.value);
	} else if (msg.name == '排序方式') {
		presentElement.attr('data-sort-order', msg.value);
	} else if (msg.name == '列可视') {
		presentElement.attr('data-visible', msg.value);
	} else if (msg.name == '列视图可视') {
		presentElement.attr('data-card-visible', msg.value);
	} else if (msg.name == '列开关') {
		presentElement.attr('data-switchable', msg.value);
	} else if (msg.name == '单击列时选择checkbox/radiobox') {
		presentElement.attr('data-click-to-select', msg.value);
	} else if (msg.name == '格式化(formatter)') {
		presentElement.attr('data-formatter', msg.value);
	} else if (msg.name == '脚格式化(footerFormatter)') {
		presentElement.attr('data-footer-formatter', msg.value);
	} else if (msg.name == '事件(events)') {
		presentElement.attr('data-events', msg.value);
	} else if (msg.name == '排序(sorterFunction)') {
		presentElement.attr('data-sorter', msg.value);
	} else if (msg.name == '排序字段名(sortName)') {
		presentElement.attr('data-sort-name', msg.value);
	} else if (msg.name == '单元格设计(cellStyle)') {
		presentElement.attr('data-cell-style', msg.value);
	} else if (msg.name == '可被查询') {
		presentElement.attr('data-searchable', msg.value);
	} else if (msg.name == '查询是否格式化') {
		presentElement.attr('data-search-formatter', msg.value);
	}
}

/*
 * p h标签
 */
function h_handler(msg) {
	if (msg.editor.type == 'combobox') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		presentElement.removeClass(r);
		presentElement.addClass(msg.value);// 添加新属性
	} else if (msg.editor == 'text') {
		if (msg.name == 'style') {
			presentElement.attr("style", msg.value);
		}
	}
}
function p_handler(msg) {
	if (msg.editor.type == 'combobox') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		presentElement.removeClass(r);
		presentElement.addClass(msg.value);// 添加新属性
	} else if (msg.editor == 'text') {
		if (msg.name == 'style') {
			presentElement.attr("style", msg.value);
		}
	}
}
/*
 * address标签
 */
function address_handler(msg) {
	if (msg.editor == 'text') {
		if (msg.name == 'style') {
			presentElement.attr("style", msg.value);
		}
	} else if (msg.editor.type == 'combobox') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		presentElement.removeClass(r);
		presentElement.addClass(msg.value);// 添加新属性
	}
}
/*
 * dl标签
 */
function dl_handler(msg) {
	if (msg.editor.type == 'combobox') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		presentElement.removeClass(r);
		presentElement.addClass(msg.value);// 添加新属性
	} else if (msg.editor == 'text') {
		if (msg.name == 'style') {
			presentElement.attr("style", msg.value);
		}
	}
}

/*
 * 标签
 */
function span_handler(msg) {
	if (msg.editor.type == 'combobox') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		presentElement.removeClass(r);
		presentElement.addClass(msg.value);// 添加新属性
	} else if (msg.editor == 'text') {
		if (msg.name == 'style') {
			presentElement.attr("style", msg.value);
		}
	}
}

/*
 * 图片标签
 */
function img_handler(msg) {
	if (msg.editor.type == 'combobox') {
		var data = msg.editor.options.data;
		var length = data.length;
		var r = '';
		for (var i = 0; i < length; i++) {
			r += data[i].value + ' ';
		}
		presentElement.removeClass(r);
		presentElement.addClass(msg.value);// 添加新属性
	} else if (msg.editor == 'text') {
		if (msg.name == "src") {
			presentElement.attr('src', msg.value);
		} else if (msg.name == 'style') {
			presentElement.attr("style", msg.value);
		}
	}
}

String.prototype.startWith = function(str) {
	var reg = new RegExp("^" + str);
	return reg.test(this);
};
String.prototype.endWith = function(str) {
	var reg = new RegExp(str + "$");
	return reg.test(this);
};

function hint_handler(msg) {
	if (msg.name == '提示框样式') {
		presentElement.find("div").attr('class', msg.value);
	}

}
// 随机数
function randomNumber() {
	return randomFromInterval(1, 1e6);
}

function randomFromInterval(e, t) {
	return Math.floor(Math.random() * (t - e + 1) + e);
}

function fold() {
	// 折叠展示样式
	var _foldStyle = presentElement.find('div').attr('class');
	// 新增面板
	var _add = presentElement.find('div#myAccordion').attr('class');
	// 删除面板
	var _delete = presentElement.find('div#myAccordion').attr('class');
	var eleType = 'fold';
	var attrs = '';
	attrs = "eleType=" + eleType + ",折叠展示样式=" + _foldStyle + ",增加面板=" + _add
			+ ",删除面板=" + _delete;
	sendMessage('parent', attrs);
};

function fold_handler(msg) {
	if (msg.name == '折叠展示样式') {
		if (msg.value === 'true') {
			var script = presentElement.find('div#script');
			script.remove();

		} else if (msg.value === 'false') {
			var aa = presentElement.find('div .panel-collapse');
			var _script = "<div id='script'><script type='text/javascript'>";
			for (var i = 0; i < aa.length; i++) {
				var id = aa.eq(i).attr('id');
				var _function = "$(function () { $('#" + id
						+ "').collapse('show')});\n";
				_script += _function;
			}
			_script += "</script></div>";
			presentElement.append(_script);
		}
	}
	if (msg.name == '新增面板') {
		var _add = presentElement.children();
		var _addId = presentElement.children().attr("id");
		var _a = presentElement.children().children();
		if (parseInt(msg.value) < parseInt((_a.length + 2))) {
			_add.empty();// 清空
			for (var i = 0; i < _a.length + 1; i++) {
				if (parseInt(msg.value - 1) != parseInt(i)) {
					if (parseInt(msg.value) > parseInt(i)) {
						_add.append(_a[(i)]);
					} else {
						_add.append(_a[(i - 1)]);
					}

				} else {
					var _acc = "<div class='panel panel-default'>"
							+ "<div class='panel-heading'>"
							+ "<a class='panel-title' data-toggle='collapse' data-parent='#"
							+ _addId
							+ "' href='#collapse"
							+ i
							+ "' contenteditable='true'>新增面板</a>"
							+ "</div>"
							+ "<div id='collapse"
							+ i
							+ "' class='panel-collapse collapse'>"
							+ "<div class='panel-body' contenteditable='true'>"
							+ "<div class='row clearfix' ravo='rainbow_fx_remove'>"
							+ "<div class='col-md-12 column'>" + "</div>"
							+ "</div>" + "</div>" + "</div>" + "</div>";
					_add.append(_acc);
				}
				;
			}

		} else {
			alert("此位置不存在！不能输入此数！");
		}
		;
		activeDrag();
	}
	if (msg.name == '删除面板') {
		var _add = presentElement.children();
		var _a = presentElement.children().children();
		if (parseInt(msg.value) < parseInt((_a.length + 1))) {
			_add.empty();
			for (var i = 1; i < (_a.length + 1); i++) {
				if (parseInt(msg.value) != parseInt(i)) {
					_add.append(_a[(i - 1)]);
					if (parseInt(i) > parseInt("1")) {
						_add.append(_a[(i - 1)]);
					}
				} else {
					continue;
				}
				;
			}
		}
		activeDrag();
	}

};

function slidel() {
	var _style = presentElement.children('div').attr('style');
	// data-ride 设置为页面加载时就运行"carousel"自动轮播
	var _dataRide = presentElement.children('div').attr('data-ride');
	// data-interval 设置为false 则不会自动轮播 设置为毫秒 则是轮播时间
	var _dataInterval = presentElement.children('div').attr('data-interval');
	// data-pause 设置为hover（默认为hover） 鼠标悬停 空或者 其他 不停
	var _dataPause = presentElement.children('div').attr('data-pause');
	// data-wrap 默认为true (循环播放)设置为false 则在最后一张停止
	var _dataWrap = presentElement.children('div').attr('data-wrap');
	var eleType = 'slidel';
	attrs = 'style=' + _style + ',eleType=' + eleType + ',自动轮播=' + _dataRide
			+ ',间隔时间=' + _dataInterval + ',鼠标悬停=' + _dataPause + ',循环播放='
			+ _dataWrap;
	sendMessage('parent', attrs);

};
function slidel_handler(msg) {
	if (msg.name === '自动轮播') {
		if (msg.value === 'true') {
			presentElement.children('div').attr('data-ride', 'carousel');
		} else if (msg.value === 'false') {
			presentElement.children('div').removeAttr('data-ride');
		}
	}
	if (msg.name === '循环播放') {
		if (msg.value === 'true') {
			presentElement.children('div').attr('data-wrap', 'true');
		} else if (msg.value === 'false') {
			presentElement.children('div').attr('data-wrap', 'false');
		}
	}
	if (msg.name === '鼠标悬停') {
		if (msg.value === 'true') {
			presentElement.children('div').attr('data-pause', 'hover');
		} else if (msg.value === 'false') {
			presentElement.children('div').removeAttr('data-pause');
		}
	}
	if (msg.name === 'style') {
		if (msg.value != 'undefined' && msg.value.trim() != '') {
			presentElement.children('div').attr('style', msg.value);
		}
	}
	if (msg.name === '间隔时间') {
		if (msg.value != 'undefined' && msg.value.trim() != '') {
			presentElement.children('div').attr('data-interval', msg.value);
		}
	}
	if (msg.name === '增加') {
		if (msg.value != 'undefined' && msg.value.trim() != '') {
			var _div = presentElement.children('div').children('div').children(
					'div');
			var _divHtml = '';
			for (var i = 0; i < _div.length; i++) {
				_divHtml += _div.eq(i).html() + "\n\n\n";
			}
			_divHtml = _divHtml.split('\n\n\n');
			// 增加节点的位置
			var ol = presentElement.children('div').children('ol');
			var div = presentElement.children('div').children('div');
			var _id = presentElement.children('div').attr('id');

			if (msg.value < (_div.length + 2)) {// 输入的值 最多能多余原有图片张数+1
				ol.empty();
				div.empty();
				for (var i = 0; i < (_div.length + 1); i++) { // 循环增加 图片
					if (i == 0) { // 处理第一张图片
						if (msg.value == 1) {// 增加的位置是第一个
							var li = "<li class='active' data-target='#" + _id
									+ "' data-slide-to='" + i + "'></li>";
							ol.append(li);
							var divItem = "<div class='item active'>"
									+ "<img src='./file/default(7).jpg'alt='default(7)'/>"
									+ "<div class='carousel-caption' >"
									+ "<span class='view'>"
									+ "<h4>幻灯片Label</h4>"
									+ "<p data-target='#editorModal' data-toggle='modal'>新增的幻灯片</p>"
									+ "</span>" + "</div>" + "</div>";
							div.append(divItem);
						} else {
							var li = "<li class='active' data-target='#" + _id
									+ "' data-slide-to='" + i + "'></li>";
							ol.append(li);
							var _d = "<div class='item active'>" + _divHtml[i]
									+ "</div>";
							div.append(_d);
						}
					} else { // 非第一张图片
						if ((i + 1) < msg.value) {
							ol.append("<li class='' data-target='#" + _id
									+ "' data-slide-to='" + i + "'></li>");
							var _d = "<div class='item'>" + _divHtml[i]
									+ "</div>";
							div.append(_d);
						} else if ((i + 1) == msg.value) {
							ol.append("<li class='' data-target='#" + _id
									+ "' data-slide-to='" + i + "'></li>");
							var _d = "<div class='item'>"
									+ "<img src='./file/default(7).jpg'alt=''/>"
									+ "<div class='carousel-caption' >"
									+ "<span class='view'>"
									+ "<h4>幻灯片Label</h4>"
									+ "<p data-target='#editorModal' data-toggle='modal'>新增的幻灯片</p>"
									+ "</span>" + "</div>" + "</div>";
							div.append(_d);
						} else if ((i + 1) > msg.value) {
							ol.append("<li class='' data-target='#" + _id
									+ "' data-slide-to='" + i + "'></li>");
							var _d = "<div class='item'>" + _divHtml[i - 1]
									+ "</div>";
							div.append(_d);
						}
					}
				}
			}
		}
		activeDrag();
	}
	if (msg.name === '删除') {
		if (msg.value != 'undefined' && msg.value.trim() != '') {
			var _div = presentElement.children('div').children('div').children(
					'div');
			var _divHtml = '';
			for (var i = 0; i < _div.length; i++) {
				_divHtml += _div.eq(i).html() + "\n\n\n";
			}
			_divHtml = _divHtml.split('\n\n\n');
			// 增加节点的位置
			var ol = presentElement.children('div').children('ol');
			var div = presentElement.children('div').children('div');
			var _id = presentElement.children('div').attr('id');

			if (msg.value < (_div.length + 1)) {// 输入的值 不能超过原有图片张数
				ol.empty();
				div.empty();
				for (var i = 0; i < _div.length; i++) { // 循环增加 图片
					if (i == 0) { // 处理第一张图片
						if (msg.value == 1) {// 删除的位置是第一个
							continue;
						} else {
							var li = "<li class='active' data-target='#" + _id
									+ "' data-slide-to='" + i + "'></li>";
							ol.append(li);
							var _d = "<div class='item active'>" + _divHtml[i]
									+ "</div>";
							div.append(_d);
						}
					} else { // 非第一张图片
						if ((i + 1) < msg.value) {
							ol.append("<li class='' data-target='#" + _id
									+ "' data-slide-to='" + i + "'></li>");
							var _d = "<div class='item'>" + _divHtml[i]
									+ "</div>";
							div.append(_d);
						} else if ((i + 1) == msg.value) {
							continue;
						} else if ((i + 1) > msg.value) {
							ol.append("<li class='' data-target='#" + _id
									+ "' data-slide-to='" + i + "'></li>");
							var _d = "<div class='item'>" + _divHtml[i]
									+ "</div>";
							div.append(_d);
						}
					}
				}
			}
			ol.children('li').attr('class', '');
			ol.children('li').first().attr('class', 'active');
			div.children('div').attr('class', 'item');
			div.children('div').first().attr('class', 'item active');
			activeDrag();
		}
	}
}

function tab() {
	var bs_tabs = presentElement.find('[data-toggle="tabs"]').data('bs.tabs');
	if (bs_tabs === undefined) {
		presentElement.find('[data-toggle="tabs"]').tabs();
		bs_tabs = presentElement.find('[data-toggle="tabs"]').data('bs.tabs');
	}

	var data = presentElement.find('[data-toggle="tabs"]').data();
	console.info('------tabs-------');
	console.info(data);

	var eleType = 'tab';
	// 标签名字
	var _a = presentElement.children('div').children('ul').find('a');
	var _aText = '';
	bs_tabs.tabs('tabs').each(function() {
		_aText += $(this).text().trim() + "\n";
	});
	// 标签样式
	var _style = presentElement.children().children('ul').attr('class');
	// 渐变动画
	// var _jianbian = presentElement.children().children('div').children();
	// var attrs = "编辑="+_aText+",eleType="+eleType+",样式="+_style;
	var attrs = "编辑=" + _aText + "@#!eleType=" + eleType + "@#!样式=" + _style
			+ "@#!显示关闭按钮=" + data.closeable + "@#!宽度=" + data.width + "@#!高度="
			+ data.height + "@#!默认选中页=" + data.selected + "@#!自适应=" + data.fit
			+ "@#!底部填充=" + data.fitMarginBottom + "@#!显示标题=" + data.showHeader;
	sendMessage('parent', attrs);
};

function tab_handler(msg) {
	var $tabs = presentElement.find('[data-toggle="tabs"]');
	if (msg.name === '样式') {
		presentElement.children().children('ul').attr('class', msg.value);
	}
	if (msg.name === '禁止渐变') {
		if (msg.value.trim() != '') {
			var a = presentElement.children('div').children('div').children(
					'div');
			for (var i = 0; i < a.length; i++) {
				var cl = a.eq(i).attr('class');
				if (cl.contains("active")) {
					a.eq(i).attr('class', msg.value + ' active in');
				} else {
					a.eq(i).attr('class', msg.value);
				}
			}
		}
	} else if (msg.name === '显示关闭按钮') {
		$tabs.attr("data-closeable", msg.value);
		if (msg.value === 'true') {
			$tabs.tabs('addClose');
		} else {
			$tabs.tabs('removerClose');
		}
	} else if (msg.name === '显示标题') {
		$tabs.attr("data-show-header", msg.value);
		if (msg.value === 'true') {
			$tabs.tabs('showHeader');
		} else {
			$tabs.tabs('hideHeader');
		}
	} else if (msg.name === '宽度') {
		$tabs.attr("data-width", msg.value);
		$tabs.tabs('setWidth', parseInt(msg.value));
	} else if (msg.name === '高度') {
		console.info(parseInt(msg.value));
		$tabs.attr("data-height", msg.value);
		$tabs.tabs('setHeight', parseInt(msg.value));
	}

	if (msg.name === '编辑') {
		var aText = msg.value.split("\n");
		// $tabs.tabs('closeAll'); //清理所有的tab
		for (var i = 0; i < aText.length; i++) {
			var _text = aText[i];
			var _b = $tabs.tabs('exists', _text);
			var _con = '<div ravo="rainbow_fx_remove" class="row clearfix"> <div class="col-md-12 column"> </div> </div>';
			if (!_b && _text.trim() !== '') {
				$tabs.tabs('add', {
					title : _text,
					content : _con
				});
			}
		}
		// $tabs.tabs({});//重新初始化
		activeDrag();
	} else if (msg.name === '默认选中页') {
		$tabs.attr("data-selected", msg.value);
	} else if (msg.name === '自适应') {
		$tabs.attr("data-fit", msg.value);
	} else if (msg.name === '底部填充') {
		$tabs.attr("data-fit-margin-bottom", msg.value);
	}

};
function imgp_handler(msg) {
	if (msg.name === '编辑') {
		presentElement.text(msg.value);
	}
};
/*
 * function select(){ eleType = 'select'; var _hidden =
 * presentElement.children('div').children('label').attr('style'); // 尺寸 var
 * _form_group = ''; var _cl =
 * presentElement.children('div').attr('class').split(" "); for(var i=0;i<_cl.length;i++){
 * if(_cl[i] === 'form-group-lg'|| _cl[i] === 'form-group-sm'){ _form_group =
 * _cl[i]; } } var _id= presentElement.find('select').attr("id"); var _name=
 * presentElement.find('select').attr("name"); var
 * _value=presentElement.find("option:selected").attr('value'); var
 * _style=presentElement.find('select').attr("style"); // 内容 var _info=""; var
 * info=presentElement.find("option"); for(var i =0;i<info.length;i++){ _info=
 * _info+info.eq(i).text().trim()+'\n'; } var
 * _class=presentElement.find('select').attr("class"); var _formType =
 * presentElement.closest('form').hasClass('form-horizontal'); var attrs = '';
 * if(_formType){ // 左占 var _left = ''; var _cla =
 * presentElement.children('div').children('label').attr('class').split(" ");
 * for(var i=0;i<_cla.length;i++){ if(_cla[i].trim() ===
 * 'col-sm-1'||_cla[i].trim() === 'col-sm-2'||_cla[i].trim() ===
 * 'col-sm-3'||_cla[i].trim() === 'col-sm-4' ||_cla[i].trim() ===
 * 'col-sm-5'||_cla[i].trim() === 'col-sm-6'||_cla[i].trim() ===
 * 'col-sm-7'||_cla[i].trim() === 'col-sm-8' ||_cla[i].trim() ===
 * 'col-sm-9'||_cla[i].trim() === 'col-sm-10'||_cla[i].trim() ===
 * 'col-sm-11'||_cla[i].trim() === 'col-sm-12'){ _left = _cla[i]+"
 * control-label"; } } // 右占 var _right = ''; var _clas =
 * presentElement.children('div').children('div').attr('class').split(" ");
 * for(var i=0;i<_clas.length;i++){ if(_clas[i].trim() ==
 * "col-sm-1"||_clas[i].trim() == "col-sm-2"||_clas[i].trim() ==
 * "col-sm-3"||_clas[i].trim() == "col-sm-4"|| _clas[i].trim() ==
 * "col-sm-5"||_clas[i].trim() == "col-sm-6"||_clas[i].trim() ==
 * "col-sm-7"||_clas[i].trim() == "col-sm-8"|| _clas[i].trim() ==
 * "col-sm-9"||_clas[i].trim() == "col-sm-10"||_clas[i].trim() ==
 * "col-sm-11"||_clas[i].trim() == "col-sm-12"){ _right = "btn-group "+_clas[i]; }
 *  }
 * attrs="左占="+_left+",右占="+_right+",尺寸="+_form_group+",id="+_id+",name="+_name+",value="+_value+",style="+_style+",eleType=select"+",内容="+_info+",class="+_class+",隐藏="+_hidden+",formtype="+_formType;
 * 
 * }else{
 * attrs="id="+_id+",name="+_name+",value="+_value+",style="+_style+",eleType=select"+",内容="+_info+",class="+_class+",隐藏="+_hidden+",formtype="+_formType; }
 * sendMessage('parent',attrs); }
 */
/*
 * function time(){ eleType = 'time'; // 尺寸 var _form_group = ''; // 左占 var
 * _left = ''; // 右占 var _right = ''; var _time_Type = ""; var _style = ''; var
 * _id = ''; var data = ''; var _cl =
 * presentElement.children('div').attr('class').split(" "); for(var i=0;i<_cl.length;i++){
 * if(_cl[i] === 'form-group-lg'|| _cl[i] === 'form-group-sm'|| _cl[i] ===
 * 'form-group'){ _form_group = _cl[i]; } } var _name =
 * presentElement.find('input:hidden').attr('name'); var _formType =
 * presentElement.closest('form').hasClass('form-horizontal'); if(_formType){
 * _style =
 * presentElement.children('div').children('div').children('div').attr('style');
 * _id =
 * presentElement.children('div').children('div').children('div').attr('id');
 * data =
 * presentElement.children('div').children('div').children('div').attr('data-date-format');
 * if(data === 'yyyy MM dd - HH:ii p'){ _time_Type = "日期时间"; }else if(data ===
 * 'yyyy MM dd'){ _time_Type = "日期"; }else if(data === 'hh:ii'){ _time_Type =
 * "时间"; } var _cla =
 * presentElement.children('div').children('label').attr('class').split(" ");
 * for(var i=0;i<_cla.length;i++){ if(_cla[i].trim() ===
 * 'col-sm-1'||_cla[i].trim() === 'col-sm-2'||_cla[i].trim() ===
 * 'col-sm-3'||_cla[i].trim() === 'col-sm-4' ||_cla[i].trim() ===
 * 'col-sm-5'||_cla[i].trim() === 'col-sm-6'||_cla[i].trim() ===
 * 'col-sm-7'||_cla[i].trim() === 'col-sm-8' ||_cla[i].trim() ===
 * 'col-sm-9'||_cla[i].trim() === 'col-sm-10'||_cla[i].trim() ===
 * 'col-sm-11'||_cla[i].trim() === 'col-sm-12'){ _left = _cla[i]; } } var _clas =
 * presentElement.children('div').children('div').attr('class').split(" ");
 * for(var i=0;i<_clas.length;i++){ if(_clas[i].trim() ==
 * "col-sm-1"||_clas[i].trim() == "col-sm-2"||_clas[i].trim() ==
 * "col-sm-3"||_clas[i].trim() == "col-sm-4"|| _clas[i].trim() ==
 * "col-sm-5"||_clas[i].trim() == "col-sm-6"||_clas[i].trim() ==
 * "col-sm-7"||_clas[i].trim() == "col-sm-8"|| _clas[i].trim() ==
 * "col-sm-9"||_clas[i].trim() == "col-sm-10"||_clas[i].trim() ==
 * "col-sm-11"||_clas[i].trim() == "col-sm-12"){ _right = _clas[i]; }
 *  }
 * attrs="左占="+_left+",右占="+_right+",尺寸="+_form_group+",id="+_id+",name="+_name+",时间类型="+_time_Type+",style="+_style+",eleType="+eleType+",formtype="+_formType;
 * }else{ _style = presentElement.children('div').children('div').attr('style');
 * _id = presentElement.children('div').children('div').attr('id'); data =
 * presentElement.children('div').children('div').attr('data-date-format');
 * if(data === 'yyyy MM dd - HH:ii p'){ _time_Type = "日期时间"; }else if(data ===
 * 'yyyy MM dd'){ _time_Type = "日期"; }else if(data === 'hh:ii'){ _time_Type =
 * "时间"; } if(data === 'yyyy MM dd - HH:ii p'){ _time_Type = "日期时间"; }else
 * if(data === 'yyyy MM dd'){ _time_Type = "日期"; }else if(data === 'hh:ii'){
 * _time_Type = "时间"; }
 * attrs="尺寸="+_form_group+",id="+_id+",name="+_name+",style="+_style+",时间类型="+_time_Type+",eleType="+eleType+",formtype="+_formType; };
 * sendMessage('parent',attrs);
 *  }
 */
function time_handler(msg) {
	var time = presentElement.closest('[data-toggle="datatimepiker"]');
	if (msg.name == '左占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('label').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div').children('div')
					.attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '尺寸') {
		if (msg.value.trim() != 'undefined') {
			if (msg.value != '') {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls + " " + msg.value);
			} else {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls);
			}
		}
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr('style', msg.value);
	} else if (msg.name == 'id') {
		time.attr('data-link-field', msg.value.trim());
		time.next().first().attr("id", msg.value.trim());
	} else if (msg.name == 'name') {
		time.next().first().attr("name", msg.value.trim());
	} else if (msg.name == 'style') {
		presentElement.attr("style", msg.value.trim());
	} else if (msg.name == '校验') {
		validators(presentElement, msg.value.trim());
	} else if (msg.name == '日期时间格式') {
		time.attr("data-date-format", msg.value);
		// time.attr("data-link-format",msg.value);
	} else if (msg.name == '每周开始日期') {
		time.attr("data-date-weekstart", msg.value);
	} else if (msg.name == '设置开始日期') {
		time.attr("data-date-startdate", msg.value);
	} else if (msg.name == '设置结束日期') {
		time.attr("data-date-enddate", msg.value);
	} else if (msg.name == '禁用每周的某天') {
		time.attr("data-date-days-of-week-disabled", msg.value);
	} else if (msg.name == '选择后关闭日期选择器') {
		time.attr("data-date-autoclose", msg.value);
	} else if (msg.name == '日期选择器打开后首显视图') {
		time.attr("data-start-view", msg.value);
	} else if (msg.name == '最精确的时间选择视图') {
		time.attr("data-min-view", msg.value);
	} else if (msg.name == '最高能展示的选择范围视图') {
		time.attr("data-max-view", msg.value);
	} else if (msg.name == '今日按钮') {
		time.attr("data-date-today-btn", msg.value);
	} else if (msg.name == '高亮今日按钮') {
		time.attr("data-date-today-highlight", msg.value);
	} else if (msg.name == '是否允许通过方向键改变日期') {
		time.attr("data-date-keyboard-navigation", msg.value);
	} else if (msg.name == '选择器关闭强制解析输入框值') {
		time.attr("data-date-force-parse", msg.value);
	} else if (msg.name == '日期选择器位置') {
		time.attr("data-picker-position", msg.value);
	} else if (msg.name == '启动day和hour视图的经络视图') {
		time.attr("data-show-meridian", msg.value);
	}
}
function chartPie_handler(msg) {
	alert("calling");
	if (msg.name === 'DataAction' && msg.value != 'undefied' && msg.value != '') {
		var data = '';
		var canvas_type = presentElement.closest('div.view').attr('id');
		scriptStr = "function getJsonData(url, type){var jsonData = ''; $.ajax({type : 'post',url:url,dataType : 'json',data : 'type='+type,"
				+ "async :false,success : function(data){jsonData = data;}});return jsonData;}$(function (){";
		
		scriptStr += "var data =getJsonData(" + msg.value + ","+ canvas_type + ");";
		data = getJsonData(msg.value, canvas_type);

		var id = presentElement.attr('id');
		var can = $("#" + id + "").get(0).getContext("2d");
		scriptStr += " var can = $('#'+'" + id + "').get(0).getContext('2d');";
		if (canvas_type === 'canvas_bing') {
			scriptStr += " window.myPie = new Chart(can).Pie(data);});";
			window.myPie = new Chart(can).Pie(data);
		} else if (canvas_type === 'canvas_huan') {
			scriptStr += " window.myDoughnut = new Chart(can).Doughnut(data);})";
			window.myDoughnut = new Chart(can).Doughnut(data);
		} else if (canvas_type === 'canvas_diji') {
			scriptStr += " window.myPolarArea = new Chart(can).PolarArea(data);}) ";
			window.myPolarArea = new Chart(can).PolarArea(data);
		} else if (canvas_type === 'canvas_quxian') {
			scriptStr += "window.myLine = new Chart(can).Line(data)});";
			window.myLine = new Chart(can).Line(data);
		} else if (canvas_type === 'canvas_leida') {
			scriptStr += " window.myRadar = new Chart(can).Radar(data)}); ";
			window.myRadar = new Chart(can).Radar(data);
		} else if (canvas_type === 'canvas_zhu') {
			scriptStr += " window.myBar = new Chart(can).myBar(data)});";
			window.myBar = new Chart(can).myBar(data);
		}
		presentElement.attr('action', msg.value);
		sendMessage('parent', 'savePath');
	} else if (msg.name === "起始ID") {
		presentElement.attr('beg', msg.value);
	} else if (msg.name === "结束ID") {
		presentElement.attr('end', msg.value);
	}

}
function getJsonData(url, type) {
	var jsonData = '';
	$.ajax({
		type : "post",
		url : url,
		dataType : "json",
		data : "type=" + type,
		async : false,// 同步
		success : function(data) {
			jsonData = data;
		}
	});
	return jsonData;
}

function fileInput_handler(msg) {
	var t = presentElement.find(".file");
	if (msg.name == '是否预览') {
		t.attr("data-show-preview", msg.value);
	} else if (msg.name == '是否显示上传') {
		t.attr("data-show-upload", msg.value);
	} else if (msg.name == '是否显示删除') {
		t.attr("data-show-remove", msg.value);
	} else if (msg.name == '是否显示取消') {
		t.attr("data-show-cancel", msg.value);
	} else if (msg.name == '是否显示标题') {
		t.attr("data-show-caption", msg.value);
	} else if (msg.name == '只读') {
		t.attr("readonly", msg.value);
	} else if (msg.name == '不可用') {
		t.attr("disabled", msg.value);
	} else if (msg.name == '最小上传数量') {
		t.attr("data-min-file-count", msg.value);
	} else if (msg.name == '最大上传数量') {
		t.attr("data-max-file-count", msg.value);
	} else if (msg.name == '接收的文件后缀') {
		t.attr("data-allowed-file-extensions", msg.value);
	} else if (msg.name == '接收的文件类型') {
		t.attr("data-allowed-file-types", msg.value);
	} else if (msg.name == '预览文件类型') {
		t.attr("date-allowed-preview-types", msg.value);
	}
}
function validators(presentElement, validator) {
	var str = "";
	var list = presentElement[0].attributes;
	for (var i = 0; i < list.length; i++) {
		var _name = list[i].name;
		if (_name.startWith("data-bv-")) {
			str += _name + ";";// 先得到属性中含有“data-bv-*”的属性以“;”隔开
		}
	}
	var s = str.split(";");
	for (var i = 0; i < s.length; i++) {
		presentElement.removeAttr(s[i]);// 清除属性
	}
	if (validator != null) {
		var _split = validator.split("#");
		presentElement.attr("data-bv-" + _split[0], "true");
		for (var i = 1; i < _split.length; i++) {
			var _split1 = _split[i].split("=");
			presentElement.attr("data-bv-" + _split[0] + "-" + _split1[0],
					_split1[1]);
		}
	}
}

function getResultJson(url) {
	var reData = "";
	var data = "path=" + url;
	$.ajax({
		type : "post",
		url : ctxIde+'/ide/getPoName',
		dataType : "json",
		data : data,
		async : false,// 同步
		success : function(data) {
			var msg = data.msg;
			if (msg[0] != null) {
				alert(msg[0]);
			}
			reData = data.obj;
		}
	});
	return reData;
}
// 反显校验
function reverse(presentElement) {
	var str = "";
	var list = presentElement[0].attributes;
	for (var i = 0; i < list.length; i++) {
		var _name = list[i].name;
		if (_name.startWith("data-bv-")) {
			var splN = _name.split('-');
			str = splN[2];
			break;
		}
	}
	return str;
}

/* 20160817 add by chenyl for 处理日期时间组件的属性值 */
function datetime_handler(msg) {
	if (msg.name == '左占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('label').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div').children('div')
					.attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '尺寸') {
		if (msg.value.trim() != 'undefined') {
			if (msg.value != '') {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls + " " + msg.value);
			} else {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls);
			}
		}
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr('style', msg.value);
	} else if (msg.name == 'id') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('id', msg.value);
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('data-link-field',
						'val_' + msg.value);
		presentElement.closest('div.view').children('div').children('div')
				.children("input[type='hidden']")
				.attr('id', 'val_' + msg.value);

	} else if (msg.name == 'name') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children("input[type='hidden']").attr('name', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children("input[type='hidden']").removeAttr('name');
		}
	} else if (msg.name == 'style') {
		presentElement.closest('div.view').children('div').children('div')
				.attr('style', msg.value);
	} else if (msg.name == '控件皮肤') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-skin', msg.value);
	} else if (msg.name == '日期显示格式') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-date-fmt', msg.value);
	} else if (msg.name == '日期值格式') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-value-fmt', msg.value);
	} else if (msg.name == '初始日期') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-default-value',
						msg.value);
	} else if (msg.name == '最小日期') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-min-date', msg.value);
	} else if (msg.name == '最大日期') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-max-date', msg.value);
	} else if (msg.name == '显示清除按钮') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-is-show-clear',
						msg.value);
	} else if (msg.name == '显示星期') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-is-show-week',
						msg.value);
	} else if (msg.name == '显示今天按钮') {
		presentElement.closest('div.view').children('div').children('div')
				.children('input.Wdate').attr('datetime-is-show-today',
						msg.value);
	} else if (msg.name == '校验') {
		validators(presentElement.parent().find('input[type="hidden"]'),
				msg.value.trim());
	}
	/* 通过datetime_handler方法让复选框给时间添加验证属性 20180102 */
	else if (msg.effect == 'check') {
		if (msg.value == 'true') {
			presentElement.attr(msg.inputCheck, 'true');
		} else {
			presentElement.removeAttr(msg.inputCheck);
		}
	}
}

/* 20161019 add by chenyl for 处理搜索树组件的属性值 */
function treesearch_handler(msg) {
	console.info("更新搜索树组件属性：" + msg.name);
	if (msg.name == '左占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('label').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div').children('div')
					.attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '尺寸') {
		if (msg.value.trim() != 'undefined') {
			if (msg.value != '') {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls + " " + msg.value);
			} else {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls);
			}
		}
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr('style', msg.value);
	} else if (msg.name == 'id') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('id',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('id',
							'treesearch_' + randomNumber());
		}
	} else if (msg.name == '隐藏域名称') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('name',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('name',
							'treesearch.name');
		}
	} else if (msg.name == '隐藏域值') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('value',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('value',
							'treesearch.value');
		}
	} else if (msg.name == '输入框名称') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'label_name', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'label_name', 'treesearch.label_name');
		}
	} else if (msg.name == '输入框值') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'label_value', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'label_value', 'treesearch.label_value');
		}
	} else if (msg.name == '选择框标题') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('title',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('title',
							'搜索树标题');
		}
	} else if (msg.name == '树结构数据地址') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('url',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('url',
							'/sys/office/treeData?type=1');
		}
	} else if (msg.name == '是否显示复选框') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			if (msg.value == 'true') {
				presentElement.closest('div.view').children('div').children(
						'div').children('[input_type="sys:treeselect"]').attr(
						'checked', 'true');
			} else {
				presentElement.closest('div.view').children('div').children(
						'div').children('[input_type="sys:treeselect"]')
						.removeAttr('checked');
			}
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'checked');
		}
	} else if (msg.name == '排除掉的编号') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('ext_id',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'ext_id');
		}
	} else if (msg.name == '是否列出全部数据') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('is_all',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'is_all');
		}
	} else if (msg.name == '不允许选择根节点') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'not_allow_select_root', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'not_allow_select_root');
		}
	} else if (msg.name == '不允许选择父节点') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'not_allow_select_parent', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'not_allow_select_parent');
		}
	}/*
		 * else if(msg.name == '过滤栏目模型'){ if(msg.value.trim()!='' &&
		 * msg.value.trim()!='undefined'){
		 * presentElement.closest('div.view').children('div').children('div').children('[input_type="sys:treeselect"]').attr('module',msg.value);
		 * }else{
		 * presentElement.closest('div.view').children('div').children('div').children('[input_type="sys:treeselect"]').removeAttr('module'); }
		 * }else if(msg.name == '选择范围内的模型'){ if(msg.value.trim()!='' &&
		 * msg.value.trim()!='undefined'){
		 * presentElement.closest('div.view').children('div').children('div').children('[input_type="sys:treeselect"]').attr('select_scope_module',msg.value);
		 * }else{
		 * presentElement.closest('div.view').children('div').children('div').children('[input_type="sys:treeselect"]').removeAttr('select_scope_module'); } }
		 */else if (msg.name == '是否允许清除') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'allow_clear', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'allow_clear');
		}
	} else if (msg.name == '文本框可填写') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'allow_input', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'allow_input');
		}
	} else if (msg.name == 'css样式') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'css_style', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'css_style');
		}
	} else if (msg.name == '缩小按钮显示') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'small_btn', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'small_btn');
		}
	} else if (msg.name == '是否显示按钮') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('hide_btn',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'hide_btn');
		}
	} else if (msg.name == '是否限制选择') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('disabled',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'disabled');
		}
	} else if (msg.name == '窗体宽度') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('win_width',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'win_width');
		}
	} else if (msg.name == '窗体高度') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('win_height',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'win_height');
		}
	} else if (msg.name == '校验') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			// console.info("校验value="+msg.value);
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr(
							'validators', msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'validators');
		}
	} else if (msg.name == '不为空') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').attr('treesearch_required',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:treeselect"]').removeAttr(
							'treesearch_required');
		}
	}
}

/* 20180612 add by chenyl for 处理图标选择组件的属性值 */
function iconselect_handler(msg) {
	console.info("更新图标选择组件属性：" + msg.name);
	if (msg.name == '左占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('label').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div').children('div')
					.attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '尺寸') {
		if (msg.value.trim() != 'undefined') {
			if (msg.value != '') {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls + " " + msg.value);
			} else {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls);
			}
		}
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr('style', msg.value);
	} else if (msg.name == 'id') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_id',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('id',
							'iconselect_' + randomNumber());
		}
	} else if (msg.name == 'name') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_name',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_name',
							'icon');
		}
	} else if (msg.name == 'value') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_value',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_value',
							'');
		}
	} else if (msg.name == '图标数据地址') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_url',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_url',
							'');
		}
	} else if (msg.name == '自定义CSS') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_css',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_css',
							'');
		}
	} else if (msg.name == '不为空') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_required',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
			.children('[input_type="sys:iconselect"]').attr('icon_required',
					'false');
		}
	}
}

/* 20180618 add by chenyl for 处理富文本编辑器组件的属性值 */
function ueditor_handler(msg) {
	console.info("更新富文本编辑器属性：" + msg.name);
	if (msg.name == '左占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('label').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div').children('div')
					.attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '尺寸') {
		if (msg.value.trim() != 'undefined') {
			if (msg.value != '') {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls + " " + msg.value);
			} else {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls);
			}
		}
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr('style', msg.value);
	} else if (msg.name == 'id') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="ueditor"]').attr('ue_id',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="ueditor"]').attr('ue_id',
							'ueditor_' + randomNumber());
		}
	} else if (msg.name == 'name') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="ueditor"]').attr('ue_name',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="ueditor"]').attr('ue_name',
							'ueditor');
		}
	} else if (msg.name == 'value') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="ueditor"]').attr('ue_value',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="ueditor"]').attr('ue_value',
							'');
		}
	} else if (msg.name == 'style') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="ueditor"]').attr('ue_style',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="ueditor"]').attr('ue_style',
							'');
		}
	}
}

/* 20180620 add by chenyl for 处理文件管理组件的属性值 */
function ckfinder_handler(msg) {
	console.info("更新文件管理属性：" + msg.name);
	if (msg.name == '左占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('label').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div')
					.children('label').attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '右占') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			var _cla = presentElement.closest('div.view').children('div')
					.children('div').attr('class');
			var _clas = _cla.replace('col-sm-1', '').replace('col-sm-2', '')
					.replace('col-sm-3', '').replace('col-sm-4', '').replace(
							'col-sm-5', '').replace('col-sm-6', '').replace(
							'col-sm-7', '').replace('col-sm-8', '').replace(
							'col-sm-9', '').replace('col-sm-10', '').replace(
							'col-sm-11', '').replace('col-sm-12', '');
			presentElement.closest('div.view').children('div').children('div')
					.attr('class', _clas + " " + msg.value);
		}
	} else if (msg.name == '尺寸') {
		if (msg.value.trim() != 'undefined') {
			if (msg.value != '') {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls + " " + msg.value);
			} else {
				var cla = presentElement.closest('div.view').children('div')
						.attr('class');
				var cls = cla.replace('form-group-lg', "").replace(
						'form-group-sm', "");
				presentElement.closest('div.view').children('div').attr(
						'class', cls);
			}
		}
	} else if (msg.name == '隐藏label') {
		presentElement.closest('div.view').children('div').children('label')
				.attr('style', msg.value);
	} else if (msg.name == 'id') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_id',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('id',
							'ckfinder_' + randomNumber());
		}
	} else if (msg.name == 'name') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_name',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:iconselect"]').attr('icon_name',
							'ckfinder');
		}
	} else if (msg.name == 'value') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_value',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_value',
							'');
		}
	} else if (msg.name == '文件类型') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_type',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_type',
							'files');
		}
	} else if (msg.name == '打开文件管理的上传路径') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_upload_path',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_upload_path',
							'/custom');
		}
	} else if (msg.name == '是否生成年份路径') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_year_path',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_year_path',
							'false');
		}
	} else if (msg.name == '是否生成月份路径') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_month_path',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_month_path',
							'false');
		}
	} else if (msg.name == '是否所有用户可见') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_is_all_user',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_is_all_user',
							'false');
		}
	} else if (msg.name == '不为空') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_required',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
			.children('[input_type="sys:ckfinder"]').attr('ckfinder_required',
					'false');
		}
	} else if (msg.name == '是否可以多选') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_select_multiple',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
			.children('[input_type="sys:ckfinder"]').attr('ckfinder_select_multiple',
					'false');
		}
	} else if (msg.name == '是否查看模式') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_readonly',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
			.children('[input_type="sys:ckfinder"]').attr('ckfinder_readonly',
					'false');
		}
	} else if (msg.name == '最大宽度') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_max_width',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
			.children('[input_type="sys:ckfinder"]').attr('ckfinder_max_width',
					'');
		}
	} else if (msg.name == '最大高度') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.closest('div.view').children('div').children('div')
					.children('[input_type="sys:ckfinder"]').attr('ckfinder_max_height',
							msg.value);
		} else {
			presentElement.closest('div.view').children('div').children('div')
			.children('[input_type="sys:ckfinder"]').attr('ckfinder_max_height',
					'');
		}
	}
}

/* 20181225 add by chenyl for 处理隐藏域组件的属性值 */
function hidden_handler(msg) {
	console.info("更新隐藏域属性：" + msg.name);
	if (msg.name == 'id') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.attr('hidden_id',msg.value);
		} else {
			presentElement.attr('hidden_id', 'hid_' + randomNumber());
		}
	} else if (msg.name == 'name') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.attr('hidden_name',msg.value);
		} else {
			presentElement.attr('hidden_name','hid');
		}
	} else if (msg.name == 'value') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.attr('hidden_value',msg.value);
		} else {
			presentElement.attr('hidden_value','');
		}
	} else if (msg.name == '不为空') {
		if (msg.value.trim() != '' && msg.value.trim() != 'undefined') {
			presentElement.attr('hidden_required',msg.value);
		} else {
			presentElement.attr('hidden_required','false');
		}
	}
}
