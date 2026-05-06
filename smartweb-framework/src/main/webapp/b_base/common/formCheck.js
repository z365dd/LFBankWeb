//时间输入框取值，返回值，判断值都用id
//前台name隐藏域看到值？
//input相关都使用id

console.log('formCheck');


//要对某个div里面的值进行赋值操作
/*var SmartWeb.FORM_SET_DIV = undefined;
//要对某个div里进行取值操作
var SmartWeb.FORM_GET_DIV = undefined;*/

// 验证复选框未选中 返回真
var unChecked = function(Name) {
	if ($("input[name=" + Name + "]").is(":checked")) {
		return false;
	} else {
		return true;
	}
}

// 获取下拉选择框的值 逗号隔开
var getSelectVal = function(Name) {
	var a = [];
	$("select[name=" + Name + "]").find('option').filter(':selected').each(
			function() {
				if ($(this).val() != '') {
					a.push($(this).val());
				}
			});
	if (a.length == 0) {
		return false;
	} else {
		return true;
	}
}

// 获取单选/复选按钮的值 连在一起
var getCheckVal = function(Name) {
	var a = [];
	$("input[name=" + Name + "]").filter(':checked').each(function() {
		a.push($(this).val());
	});
	if (a.length == 0) {
		return '';
	} else {
		return a.join('');
	}
}

// 验证字母 不符合为空
var checkNumAsc = function(Name) {
	var a = /\W/g;
	if (a.test($("input[name=" + Name + "]").val())) {
		return true;
	} else {
		return false;
	}
}

// 验证不小于1的数 只能有一个小数点 不符合为真
var checkNumThan1 = function(Name) {
	var a = /^[1-9]+\d*[\.]?\d{0,3}$/g;
	if (a.test($("input[name=" + Name + "]").val())) {
		return false;
	} else {
		return true;
	}
}

// 验证日期 小于 开始时间 返回真
var checkEndDate = function(Name1, Name2) {
	var startDay = $("#" + Name2).val();
	var y = startDay.slice(0, 4);
	var m = startDay.slice(5, 7);
	var d = startDay.slice(8, 10);
	startDay = m + '/' + d + '/' + y;
	startDay = Date.parse(startDay);
	var endDay = $("#" + Name1).val();
	var y = endDay.slice(0, 4);
	var m = endDay.slice(5, 7);
	var d = endDay.slice(8, 10);
	endDay = m + '/' + d + '/' + y;
	endDay = Date.parse(endDay);
	if (startDay < endDay) {
		return false;
	} else {
		return true;
	}
}
// 验证日期 不小于 开始时间 返回真
var checkEndDate1 = function(Name1, Name2) {
	var startDay = $("#" + Name2).val();
	var y = startDay.slice(0, 4);
	var m = startDay.slice(5, 7);
	var d = startDay.slice(8, 10);
	startDay = m + '/' + d + '/' + y;
	startDay = Date.parse(startDay);
	var endDay = $("#" + Name1).val();
	var y = endDay.slice(0, 4);
	var m = endDay.slice(5, 7);
	var d = endDay.slice(8, 10);
	endDay = m + '/' + d + '/' + y;
	endDay = Date.parse(endDay);
	if (startDay <= endDay) {
		return false;
	} else {
		return true;
	}
}

// 验证日期 小于 当前时间 返回真
var checkDate = function(Name) {
	var today = new Date();
	var todayUTC = today.getTime() - 1000 * 60 * 60 * 24;
	var date = $("#" + Name).val();
	var y = date.slice(0, 4);
	var m = date.slice(5, 7);
	var d = date.slice(8, 10);
	var newDate = m + '/' + d + '/' + y;
	var setDay = Date.parse(newDate);
	if (setDay >= todayUTC) {
		return false;
	} else {
		return true;
	}
}

// 检验输入值 为 空 返回真
var checkNull = function(Name) {
	if ($("#" + Name).val() == "") {
		return true;
	} else {
		return false;
	}
}
// 检验输入值 为 空 返回真
var checkNull1 = function(Name) {
	if ($("#" + Name).val() == "") {
		return true;
	} else {
		return false;
	}
}

// 验证输入字节长度 不符合 返回真
var checkLength = function(min, max, Name) {
	var byte = $("#" + Name).val().replace(/[^\x00-xff]/g, "xx");
	if (byte.length < min || byte.length > max) {
		return true;
	} else {
		return false;
	}
}
// 验证最小到最大之间
var checkMinAndMax = function(min, max, Name) {
	var value = $("#" + Name).val();
	if (value >= min && value <= max) {
		return true;
	} else {
		return false;
	}
}
var checkVersion = function(Name) {
	var value = $("#" + Name).val();
	if (/^\d(\.\d){1,2}$/.test(value)) {
		return true;
	} else {
		return false;
	}
}
// 验证 不为 数字 返回真
var checkNumber = function(Name) {
	var num = /\D/g;
	if (num.test($("input[name=" + Name + "]").val())) {
		return true;
	} else {
		return false;
	}
}

// 验证 不为 数字和小数点 返回真
var checkNumberPoint = function(Name) {
	var numP = /^[0-9]+[.]?[0-9]*/g;
	if (numP.test($("input[name=" + Name + "]").val())) {
		return true;
	} else {
		return false;
	}
}

/*
 * //无法保存和下一步/完成触发的事件 //回到顶部，return var fail = function(){
 * $(document).scrollTop(0); return false; }
 */

// 下一步/完成状态初始化
// 初始化nextState
var startNext = function() {
	$("#" + nextBtnState).click(function() {
		nextState = 0;
	});
}

// 暂存状态初始化
// 初始化saveState
var startSave = function() {
	$("#" + saveBtnState).click(function() {
		saveState = 0;
	});
}

// 确定当前页面的btn
// 下一步/完成按钮，完成函数，取消按钮，暂存按钮，暂存函数
// 不需要传0/null
var startJudge = function(nextBtnId, cancelBtnId, saveBtnId) {
	nextBtnState = nextBtnId || 0;
	if (nextBtnState) {
		startNext();
	}
	cancelBtnState = cancelBtnId || 0;
	saveBtnState = saveBtnId || 0;
	if (saveBtnState) {
		startSave();
	}
}

// 下一步/完成状态判断
// 判断nextState，为真停止
var endNext = function(fun1) {
	$("#" + nextBtnState).click(function() {
		if (nextState) {
			$(document).scrollTop(0);
			$("#" + nextBtnState).off('click', fun1);
		} else {
			$("#" + nextBtnState).on('click', fun1());
		}
	});
}

// 暂存状态判断
// 判断nextState，为真停止
var endSave = function(fun2) {
	$("#" + saveBtnState).click(function() {
		if (saveState) {
			$(document).scrollTop(0);
			$("#" + saveBtnState).off('click', fun2);
		} else {
			$("#" + saveBtnState).on('click', fun2());
		}
	});
}

// 初始化页面按钮btn，下一步/完成判断
// 初始化btnState，以下一个页面可以使用
// 验证下一步/完成状态，可否执行
var endJudge = function(fun1, fun2) {
	if (nextBtnState) {
		endNext(fun1);
	}
	if (saveBtnState) {
		endSave(fun2);
	}
	nextBtnState = 0;
	cancelBtnState = 0;
	saveBtnState = 0;

}

// 012 取消/关闭按钮单击事件(input输入)
// 移除ooxx和节点
var cancel012 = function(Name) {
	var state = Name + 'State';
	$("#" + cancelBtnState).click(
			function() {
				// if($("#"+Name).attr('readonly')==false){
				$("#" + Name).removeClass('warnInput trueInput').parent().next(
						'span').remove();
				eval(state + "=1");
				// }
			});
}

// 01 取消/关闭按钮单击事件(input选择,select)
// 移除节点
var cancel01 = function(Name) {
	var a = $("select[name=" + Name + "]")[0] || $("input[id=" + Name + "]")[0];
	$("#" + cancelBtnState).click(function() {
		$(a).parent().next('span').remove();
	});
}

// 01 取消/关闭按钮单击事件(单选，复选按钮)
// 移除节点，复选框还原值
var cancel01Checked = function(Name) {
	$("#" + cancelBtnState).click(
			function() {
				$("input[name=" + Name + "]").parent().parent().parent().find(
						'span').remove();
				if ($("input[name=" + Name + "]").attr('type') == 'checkbox') {
					$("input[name=" + Name + "]").each(function() {
						$(this).val("0");
					})
				}
			});
}

// 012 下一步/完成按钮单击事件(input输入)
// 判断状态值，判断为1提示空,2不改变
// 1、2改变nextState下一步/完成状态值
var next012 = function(Name) {
	var state = Name + 'State';
	$("#" + nextBtnState).click(function() {
		if ($("#" + Name).is(":visible")) {
			if (eval(state + "==1")) {
				$("#" + Name).parent().next('span').remove();
				var script = '输入不能为空';
				var tag = "<span class='warnBlock'>" + script + "</span>";
				$("#" + Name).parent().after(tag);
				nextState = 1;
			} else if (eval(state + "==2")) {
				nextState = 1;
			}
		}
	});
}

// 012Date 下一步/完成按钮单击事件(input输入)
// 判断状态值，判断为1提示空,2不改变
// 1、2改变nextState下一步/完成状态值
var next012Date = function(Name) {
	var state = Name + 'State';
	$("#" + nextBtnState).click(function() {
		if ($('#' + Name).is(":visible")) {
			if (eval(state + "==1")) {
				$('#' + Name).parent().next('span').remove();
				var script = '时间不能为空';
				var tag = "<span class='warnBlock'>" + script + "</span>";
				$('#' + Name).parent().after(tag);
				nextState = 1;
			} else if (eval(state + "==2")) {
				nextState = 1;
			}
		}
	});
}

// 01 下一步/完成按钮单击事件(input选择,select)
// 判断是否为空，判断空给出提示
// 为空改变nextState
var next01 = function(Name) {
	if ($("input[id=" + Name + "]")[0]) {
		$("#" + nextBtnState).click(function() {
			if ($("input[id=" + Name + "]").is(":visible")) {
				if (checkNull(Name)) {
					$("input[id=" + Name + "]").parent().next('span').remove();
					var script = '输入不能为空';
					var tag = "<span class='warnBlock'>" + script + "</span>";
					$("input[id=" + Name + "]").parent().after(tag);
					nextState = 1;
				}
			}
		});
	} else if ($("select[name=" + Name + "]")[0]) {
		$("#" + nextBtnState).click(
				function() {
					if ($("select[name=" + Name + "]").is(":visible")) {
						if (getSelectVal(Name) == '') {
							$("select[name=" + Name + "]").parent()
									.next('span').remove();
							var script = '选择不能为空';
							var tag = "<span class='warnBlock warnBlock2'>"
									+ script + "</span>";
							$("select[name=" + Name + "]").parent().after(tag);
							nextState = 1;
						}
					}
				});
	}
}

// 01 下一步/完成按钮单击事件(单选，复选按钮)
// 判断选择是否为空，判断空给出提示
// 为空改变nextState
var next01Checked = function(Name) {
	$("#" + nextBtnState).click(
			function() {
				if ($("input[name=" + Name + "]").is(":visible")) {
					if (unChecked(Name)) {
						$("input[name=" + Name + "]").parent().parent()
								.parent().find('span').remove();
						var script = '选择不能为空';
						var tag = "<span class='warnBlock warnBlock2'>"
								+ script + "</span>";
						$("input[name=" + Name + "]").parent().parent()
								.parent().append(tag);
						nextState = 1;
					}
				}
			});
}

// 012 暂存按钮单击事件(01不需要)
// 判断状态值为2时，保存失败
var save012 = function(Name) {
	var state = Name + 'State';
	$("#" + saveBtnState).click(function() {
		if ($("#" + Name).is(":visible")) {
			if (eval(state + "==2")) {
				saveState = 1;
			}
		}
	});
}

// 结束大于开始时间
function endTime(Name1, Name2) {
	var state1 = Name1 + 'State';
	var state2 = Name2 + 'State';
	if (eval(state2 + "==0")) {
		$("#" + Name1).removeClass('warnInput trueInput').parent().next('span')
				.remove();
		var script = '时间要大于开始时间';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name1 + "]").parent().after(tag);
	} else {
		$("#" + Name1).removeClass('warnInput trueInput').parent().next('span')
				.remove();
		var script = '请先确定开始时间';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name1 + "]").parent().after(tag);
	}
}

// 判断长度(长度为字)，任意输入
// focus，任意输入，不超过max个字
var fLength = function(Name, max) {
	$("#" + Name).removeClass('warnInput trueInput').parent().next('span')
			.remove();
	var script = '任意输入，不超过' + max + '个字';
	var tag = "<span class='promptScript'>" + script + "</span>";
	$("#" + Name).parent().after(tag);
}

// 判断长度(长度为字)，任意输入
// blur，判断空，长度（1个字两字节），改变状态值
var bLength = function(Name, max, or) {
	var max1 = max * 2;
	var state = Name + 'State';
	var blank = /^\s*|\s*$/g;
	$("#" + Name).val($("#" + Name).val().replace(blank, ''));
	var or1 = or || 0;
	if (checkNull(Name)) {
		if (or1) {
			$("#" + Name).parent().next('span').remove();
			eval(state + "=0");
		} else {
			$("#" + Name).parent().next('span').remove();
			eval(state + "=1");
		}
	} else {
		if (checkLength(0, max1, Name)) {
			var script1 = '输入过长，不超过' + max + '个字';
			$("#" + Name).addClass('warnInput').parent().next().removeClass(
					'promptScript').addClass('warnScript').text(script1);
			eval(state + "=2");
		} else {
			$("#" + Name).addClass('trueInput').parent().next('span').remove();
			eval(state + "=0");
		}
	}
}

// 判断长度(长度为字)，任意输入
// foucus,blur事件
var fbLength = function(Name, max, or) {
	$("#" + Name).focus(function() {
		fLength(Name, max);
	}).blur(function() {
		bLength(Name, max, or)
	});
}

// 判断长度，数字
// focus，有min，请输入min位数字
// focus，无min，请输入数字，不超过max位
var fLengthNum = function(Name, max, min) {
	if (min) {
		$("input[name=" + Name + "]").removeClass('warnInput trueInput')
				.parent().next('span').remove();
		var script = '请输入' + min + '位数字';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name + "]").parent().after(tag);
	} else {
		$("input[name=" + Name + "]").removeClass('warnInput trueInput')
				.parent().next('span').remove();
		var script = '请输入数字，不超过' + max + '位';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name + "]").parent().after(tag);
	}
}
/**
 * 判断最小到最大之间
 */
var fMinAndMax = function(Name, min, max) {
	$("input[name=" + Name + "]").removeClass('warnInput trueInput').parent()
			.next('span').remove();
	var script = '请输入' + min + "至" + max + '之间的数字';
	var tag = "<span class='promptScript'>" + script + "</span>";
	$("input[name=" + Name + "]").parent().after(tag);
}
var fCheckVersion = function(Name) {
	$("input[name=" + Name + "]").removeClass('warnInput trueInput').parent()
			.next('span').remove();
	var script = '请输入类似1.0或1.0.1的版本格式';
	var tag = "<span class='promptScript'>" + script + "</span>";
	$("input[name=" + Name + "]").parent().after(tag);
}
// 判断长度，数字
// blur，有min，判断空、长度、固定几位数字，改变状态值
// blur,无min，空、长度、不超过几位数字，改变状态值
var bLengthNum = function(Name, max, min) {
	var state = Name + 'State';
	var blank = /^\s*|\s*$/g;
	$("input[name=" + Name + "]").val(
			$("input[name=" + Name + "]").val().replace(blank, ''));
	if (min) {
		if (checkNull(Name)) {
			$("input[name=" + Name + "]").parent().next('span').remove();
			eval(state + "=1");
		} else {
			if (checkLength(min, min, Name)) {
				var script1 = '长度错误，请输入' + min + '位数字';
				$("input[name=" + Name + "]").addClass('warnInput').parent()
						.next().removeClass('promptScript').addClass(
								'warnScript').text(script1);
				eval(state + "=2");
			} else {
				if (checkNumber(Name)) {
					$("input[name=" + Name + "]").addClass('warnInput')
							.parent().next().removeClass('promptScript')
							.addClass('warnScript').text('请输入数字');
					eval(state + "=2");
				} else {
					$("input[name=" + Name + "]").addClass('trueInput')
							.parent().next('span').remove();
					eval(state + "=0");
				}
			}
		}
	} else {
		if (checkNull(Name)) {
			$("input[name=" + Name + "]").parent().next('span').remove();
			eval(state + "=1");
		} else {
			if (checkLength(0, max, Name)) {
				var script1 = '输入过长，不超过' + max + '位数';
				$("input[name=" + Name + "]").addClass('warnInput').parent()
						.next().removeClass('promptScript').addClass(
								'warnScript').text(script1);
				eval(state + "=2");
			} else {
				if (checkNumber(Name)) {
					$("input[name=" + Name + "]").addClass('warnInput')
							.parent().next().removeClass('promptScript')
							.addClass('warnScript').text('请输入数字');
					eval(state + "=2");
				} else {
					$("input[name=" + Name + "]").addClass('trueInput')
							.parent().next('span').remove();
					eval(state + "=0");
				}
			}
		}
	}

}
/**
 * 判断最小到最大之间
 */
var bMinAndMax = function(Name, min, max) {
	var state = Name + 'State';
	var blank = /^\s*|\s*$/g;
	$("input[name=" + Name + "]").val(
			$("input[name=" + Name + "]").val().replace(blank, ''));
	if (checkNull(Name)) {
		$("input[name=" + Name + "]").parent().next('span').remove();
		eval(state + "=1");
	} else {
		if (!checkNumber(Name)) {
			if (checkMinAndMax(min, max, Name)) {
				$("input[name=" + Name + "]").addClass('trueInput').parent()
						.next('span').remove();
				eval(state + "=0");
			} else {
				var script1 = '输入错误，请输入' + min + '-' + max + '之间的数字';
				$("input[name=" + Name + "]").addClass('warnInput').parent()
						.next().removeClass('promptScript').addClass(
								'warnScript').text(script1);
				eval(state + "=2");
			}
		} else {
			var script1 = '输入错误，请输入' + min + '-' + max + '之间的数字';
			$("input[name=" + Name + "]").addClass('warnInput').parent().next()
					.removeClass('promptScript').addClass('warnScript').text(
							script1);
			eval(state + "=2");
		}
	}

}
/**
 * 
 */
var bCheckVersion = function(Name) {
	var state = Name + 'State';
	var blank = /^\s*|\s*$/g;
	$("input[name=" + Name + "]").val(
			$("input[name=" + Name + "]").val().replace(blank, ''));
	if (checkNull(Name)) {
		$("input[name=" + Name + "]").parent().next('span').remove();
		eval(state + "=1");
	} else {
		if (checkVersion(Name)) {
			$("input[name=" + Name + "]").addClass('trueInput').parent().next(
					'span').remove();
			eval(state + "=0");
		} else {
			var script1 = '输入错误,请输入类似1.0或1.0.1的版本格式';
			$("input[name=" + Name + "]").addClass('warnInput').parent().next()
					.removeClass('promptScript').addClass('warnScript').text(
							script1);
			eval(state + "=2");
		}
	}

}
// 判断长度，数字
// foucus,blur事件
var fbLengthNum = function(Name, max, min) {
	$("input[name=" + Name + "]").focus(function() {
		fLengthNum(Name, max, min);
	}).blur(function() {
		bLengthNum(Name, max, min)
	});
}
/**
 * 判断是否是最小到最大之间
 */
var fbMinAndMax = function(Name, min, max) {
	$("input[name=" + Name + "]").focus(function() {
		fMinAndMax(Name, min, max);
	}).blur(function() {
		bMinAndMax(Name, min, max);
	});
}
var fbCheckVersion = function(Name) {
	$("input[name=" + Name + "]").focus(function() {
		fCheckVersion(Name);
	}).blur(function() {
		bCheckVersion(Name);
	});
}
// 判断长度，数字或字母
// focus，有min，请输入min位数字或字母
// focus，无min，请输入数字或字母，不超过max位
var fLengthNumAsc = function(Name, max, min) {
	if (min) {
		$("input[name=" + Name + "]").removeClass('warnInput trueInput')
				.parent().next('span').remove();
		var script = '请输入' + min + '位数字、字母';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name + "]").parent().after(tag);
	} else {
		$("input[name=" + Name + "]").removeClass('warnInput trueInput')
				.parent().next('span').remove();
		var script = '请输入数字或字母，不超过' + max + '位';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name + "]").parent().after(tag);
	}

}
// 判断长度，数字或字母
// blur,有min，空、长度、固定几位数字字母，改变状态值
// blur,无min，空、长度、不超过几位数字字母，改变状态值
var bLengthNumAsc = function(Name, max, min) {
	var state = Name + 'State';
	var blank = /^\s*|\s*$/g;
	$("input[name=" + Name + "]").val(
			$("input[name=" + Name + "]").val().replace(blank, ''));
	if (min) {
		if (checkNull(Name)) {
			$("input[name=" + Name + "]").parent().next('span').remove();
			eval(state + "=1");
		} else {
			if (checkLength(min, min, Name)) {
				var script1 = '长度错误，请输入' + min + '位数字、字母';
				$("input[name=" + Name + "]").addClass('warnInput').parent()
						.next('span').removeClass('promptScript').addClass(
								'warnScript').text(script1);
				eval(state + "=2");
			} else {
				if (checkNumAsc(Name)) {
					$("input[name=" + Name + "]").addClass('warnInput')
							.parent().next('span').removeClass('promptScript')
							.addClass('warnScript').text('请输入数字、字母');
					eval(state + "=2");
				} else {
					$("input[name=" + Name + "]").addClass('trueInput')
							.parent().next('span').remove();
					eval(state + "=0");
				}
			}
		}
	} else {
		if (checkNull(Name)) {
			$("input[name=" + Name + "]").parent().next('span').remove();
			eval(state + "=1");
		} else {
			if (checkLength(0, max, Name)) {
				var script1 = '输入过长，不超过' + max + '位数字、字母';
				$("input[name=" + Name + "]").addClass('warnInput').parent()
						.next('span').removeClass('promptScript').addClass(
								'warnScript').text(script1);
				eval(state + "=2");
			} else {
				if (checkNumAsc(Name)) {
					$("input[name=" + Name + "]").addClass('warnInput')
							.parent().next('span').removeClass('promptScript')
							.addClass('warnScript').text('请输入数字、字母');
					eval(state + "=2");
				} else {
					$("input[name=" + Name + "]").addClass('trueInput')
							.parent().next('span').remove();
					eval(state + "=0");
				}
			}
		}
	}
}

// 判断长度,数字或字母
// foucus,blur事件
var fbLengthNumAsc = function(Name, max, min) {
	$("input[name=" + Name + "]").focus(function() {
		fLengthNumAsc(Name, max, min);
	}).blur(function() {
		bLengthNumAsc(Name, max, min)
	});
}

// 判断长度，大于1的数，包含小数点
// 请输入不小于1的数，不超过'+max+'位
var fLengthNumThan1 = function(Name, max) {
	$("input[name=" + Name + "]").removeClass('warnInput trueInput').parent()
			.next('span').remove();
	var script = '请输入不小于1的数，不超过' + max + '位';
	var tag = "<span class='promptScript'>" + script + "</span>";
	$("input[name=" + Name + "]").parent().after(tag);
}
// 判断长度，数字或字母
// 空、长度、不超过几位数字字母，改变状态值
var bLengthNumThan1 = function(Name, max) {
	var state = Name + 'State';
	var blank = /^\s*|\s*$/g;
	$("input[name=" + Name + "]").val(
			$("input[name=" + Name + "]").val().replace(blank, ''));
	if (checkNull(Name)) {
		$("input[name=" + Name + "]").parent().next('span').remove();
		eval(state + "=1");
	} else {
		if (checkLength(0, max, Name)) {
			var script1 = '输入过长，不超过' + max + '位数';
			$("input[name=" + Name + "]").addClass('warnInput').parent().next(
					'span').removeClass('promptScript').addClass('warnScript')
					.text(script1);
			eval(state + "=2");
		} else {
			if (checkNumThan1(Name)) {
				$("input[name=" + Name + "]").addClass('warnInput').parent()
						.next('span').removeClass('promptScript').addClass(
								'warnScript').text('请输入不小于1的数,最多三位小数');
				eval(state + "=2");
			} else {
				$("input[name=" + Name + "]").addClass('trueInput').parent()
						.next('span').remove();
				eval(state + "=0");
			}
		}
	}
}

// 判断长度，大于1的数，包含小数点
// foucus,blur事件
var fbLengthNumThan1 = function(Name, max) {
	$("input[name=" + Name + "]").focus(function() {
		fLengthNumThan1(Name, max);
	}).blur(function() {
		bLengthNumThan1(Name, max)
	});
}

// 判断结束时间
// focus，开始时间state为0，时间不小于开始时间
// focus，开始时间state为1/2，请先确定开始时间
var fEndDate = function(Name1, Name2) {
	var state1 = Name1 + 'State';
	var state2 = Name2 + 'State';
	if (eval(state2 + "==0")) {
		$("#" + Name1).removeClass('warnInput trueInput').parent().next('span')
				.remove();
		var script = '时间要大于开始时间';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name1 + "]").parent().after(tag);
	} else {
		$("#" + Name1).removeClass('warnInput trueInput').parent().next('span')
				.remove();
		var script = '请先确定开始时间';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name1 + "]").parent().after(tag);
	}
}

// 判断结束时间
// focus，开始时间state为0，时间不小于开始时间
// focus，开始时间state为1/2，请先确定开始时间
var fEndDate1 = function(Name1, Name2) {
	var state1 = Name1 + 'State';
	var state2 = Name2 + 'State';
	if (eval(state2 + "==0")) {
		$("#" + Name1).removeClass('warnInput trueInput').parent().next('span')
				.remove();
		var script = '时间不小于开始时间';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name1 + "]").parent().after(tag);
	} else {
		$("#" + Name1).removeClass('warnInput trueInput').parent().next('span')
				.remove();
		var script = '请先确定开始时间';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("input[name=" + Name1 + "]").parent().after(tag);
	}
}

// 判断结束时间
// blur，开始时间state为0，判断空、时间，改变状态值
// blur，开始时间state为1/2，清除提示
var bEndDate = function(Name1, Name2) {
	var state1 = Name1 + 'State';
	var state2 = Name2 + 'State';
	if (eval(state2 + "==0")) {
		if (checkNull1(Name1)) {
			$("#" + Name1).parent().next('span').remove();
			eval(state1 + "=1");
		} else {
			if (checkEndDate(Name1, Name2)) {
				var script1 = '时间要大于开始时间';
				$("#" + Name1).addClass('warnInput').parent().next('span')
						.removeClass('promptScript').addClass('warnScript')
						.text(script1);
				eval(state1 + "=2");
			} else {
				$("#" + Name1).addClass('trueInput').parent().next('span')
						.remove();
				eval(state1 + "=0");
			}
		}
	} else {
		$("#" + Name1).val('');
		$("#" + Name1).parent().next('span').remove();
	}
}

// 判断结束时间
// blur，开始时间state为0，判断空、时间，改变状态值
// blur，开始时间state为1/2，清除提示
var bEndDate1 = function(Name1, Name2) {
	var state1 = Name1 + 'State';
	var state2 = Name2 + 'State';
	if (eval(state2 + "==0")) {
		if (checkNull1(Name1)) {
			$("#" + Name1).parent().next('span').remove();
			eval(state1 + "=1");
		} else {
			if (checkEndDate1(Name1, Name2)) {
				var script1 = '时间不小于开始时间';
				$("#" + Name1).addClass('warnInput').parent().next('span')
						.removeClass('promptScript').addClass('warnScript')
						.text(script1);
				eval(state1 + "=2");
			} else {
				$("#" + Name1).addClass('trueInput').parent().next('span')
						.remove();
				eval(state1 + "=0");
			}
		}
	} else {
		$("#" + Name1).val('');
		$("#" + Name1).parent().next('span').remove();
	}
}

// 判断结束时间
// foucus,blur事件
var fbEndDate = function(Name1, Name2) {
	$("#" + Name1).focus(function() {
		fEndDate(Name1, Name2);
	}).blur(function() {
		bEndDate(Name1, Name2);
	});
	// $("#"+Name1).focus();
}

// 判断结束时间
// foucus,blur事件
var fbEndDate1 = function(Name1, Name2) {
	$("#" + Name1).focus(function() {
		fEndDate1(Name1, Name2);
	}).blur(function() {
		bEndDate1(Name1, Name2);
	});
	// $("#"+Name1).focus();
}

// 判断时间
// focus，不小于当前时间
var fDate = function(Name, Name2) {
	$("#" + Name).removeClass('warnInput trueInput').parent().next('span')
			.remove();
	var script = '时间不小于当前时间';
	var tag = "<span class='promptScript'>" + script + "</span>";
	$("#" + Name).parent().after(tag);
	if (Name2) {
		if ($("#" + Name2).val() != "" && checkEndDate(Name2, Name)) {
			var state = Name2 + 'State';
			$("#" + Name2).val('');
			$("#" + Name2).removeClass('warnInput trueInput').parent().next(
					'span').remove();
			eval(state + "=1");
		}
	}
}
/**
 * 生效日期获取焦点触发事件，effectFlag是否生效标识，0--未生效，1--已生效
 */
var fEffectDate = function(efectDate, invalidDate, effectFlag) {
	if (effectFlag == 0) {
		$("#" + efectDate).removeClass('warnInput trueInput').parent().next(
				'span').remove();
		var script = '时间不小于当前时间';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$("#" + efectDate).parent().after(tag);
	}
	if (invalidDate) {
		if ($("#" + invalidDate).val() != ""
				&& checkEndDate(invalidDate, efectDate)) {
			var state = invalidDate + 'State';
			$("#" + invalidDate).val('');
			$("#" + invalidDate).removeClass('warnInput trueInput').parent()
					.next('span').remove();
			eval(state + "=1");
		}
	}
}
// 判断时间
// blur函数判断空、时间，改变状态值
var bDate = function(Name) {
	var state = Name + 'State';
	if (checkNull1(Name)) {
		$("#" + Name).parent().next('span').remove();
		eval(state + "=1");
	} else {
		if (checkDate(Name)) {
			var script1 = '时间不能小于当前时间';
			$("#" + Name).addClass('warnInput').parent().next('span')
					.removeClass('promptScript').addClass('warnScript').text(
							script1);
			eval(state + "=2");
		} else {
			$("#" + Name).addClass('trueInput').parent().next('span').remove();
			eval(state + "=0");
		}
	}
}
var bEffectDate = function(efectDate, effectFlag) {
	var state = efectDate + 'State';
	if (checkNull1(efectDate)) {
		$("#" + efectDate).parent().next('span').remove();
		eval(state + "=1");
	} else {
		if (checkDate(efectDate) && effectFlag == 0) {
			var script1 = '时间不能小于当前时间';
			$("#" + efectDate).addClass('warnInput').parent().next('span')
					.removeClass('promptScript').addClass('warnScript').text(
							script1);
			eval(state + "=2");
		} else {
			$("#" + efectDate).addClass('trueInput').parent().next('span')
					.remove();
			eval(state + "=0");
		}
	}
}
/*
 * var bDate2 = function(Name,Name2){ var state = Name+'State';
 * if(checkNull1(Name)){ $("#"+Name).parent().next('span').remove();
 * eval(state+"=1"); }else{ if(checkDate(Name)){ var script1 = '时间不能小于当前时间';
 * $("#"+Name).addClass('warnInput').parent().next().removeClass('promptScript').addClass('warnScript').text(script1);
 * eval(state+"=2"); }else{ if(checkEndDate(Name2,Name)){
 * $("#"+Name2).addClass('warnInput').parent().next().removeClass('promptScript').addClass('warnScript').text(script1);
 * }else{ $("#"+Name).addClass('trueInput').parent().next('span').remove();
 * eval(state+"=0"); } } } }
 */
// 判断时间
// foucus,blur事件
var fbDate = function(Name, Name2) {
	$("#" + Name).focus(function() {
		fDate(Name, Name2);
	}).blur(function() {
		bDate(Name);
	});
}
var fbEffectDate = function(efectDate, invalidDate, effectFlag) {
	$("#" + efectDate).focus(function() {
		fEffectDate(efectDate, invalidDate, effectFlag);
	}).blur(function() {
		bEffectDate(efectDate, effectFlag);
	});
}
// 单选，复选按钮
// focus函数
var fChecked = function(Name) {
	$("input[name=" + Name + "]").parent().parent().parent().find('span')
			.remove();
}

// 复选按钮
// blur函数
var bChecked = function(Name,val1,val2) {
	$("input[name=" + Name + "]").each(function() {
		if(val1==undefined){
			if ($(this).is(":checked")) {
				$(this).val('1');
			} else {
				$(this).val('0');
			}
		}else{
			if ($(this).is(":checked")) {
				$(this).val(val1);
			} else {
				$(this).val(val2);
			}
		}
		
	});
}

// 复选按钮单独的blur事件
// 选中改变值，不用判断，不是必输
var cCheckBox = function(Name) {
	$("input[name=" + Name + "]").blur(function() {
		bChecked(Name);
	});
}

// 单选，复选按钮
// focus，blur事件
var fbChecked = function(Name) {
	$("input[name=" + Name + "]").focus(function() {
		fChecked(Name);
	});
	if ($("input[name=" + Name + "]").attr('type') == 'checkbox') {
		$("input[name=" + Name + "]").blur(function() {
			bChecked(Name);
		});
	}
}

// 01 清除空提示
// input选择（iframe）单击事件（ifarme框为新窗口，blur，change直接触发没用）
// select下拉选择框 改变事件函数（坑：click，blur事件没用）
var cleanNull01 = function(Name) {
	if ($("input[id=" + Name + "]")[0]) {
		$("input[id=" + Name + "]").click(function() {
			$(this).parent().next('span').remove();
		});
	} else if ($("select[name=" + Name + "]")[0]) {
		$("select[name=" + Name + "]").change(
				function() {
					if ($(this).parent().next('span').length == 0) {
						$(this).parents("[class*='col-sm']").find(
								'span[class*="warnBlock"]').remove();
					} else {
						$(this).parent().next('span').remove();
					}

				});
	}
}

// 完整，判断长度
// 参数：name，最大长度
var cLength = function(Name, max, or) {
	var state = Name + 'State';
	if (or) {
		eval(state + "=0");
	} else {
		eval(state + "=1");
	}
	fbLength(Name, max, or);
	if (nextBtnState) {
		next012(Name);
	}
	if (cancelBtnState) {
		cancel012(Name);
	}
	if (saveBtnState) {
		save012(Name);
	}
}

// 完整，判断长度和数字
// 参数：name，最大长度
var cLengthNum = function(Name, max, min) {
	var state = Name + 'State';
	eval(state + "=1");
	fbLengthNum(Name, max, min);
	if (nextBtnState) {
		next012(Name);
	}
	if (cancelBtnState) {
		cancel012(Name);
	}
	if (saveBtnState) {
		save012(Name);
	}
}

// 完整，判断不小于1的数字
// 参数：name，最大长度
var cLengthNumThan1 = function(Name, max) {
	var state = Name + 'State';
	eval(state + "=1");
	fbLengthNumThan1(Name, max);
	if (nextBtnState) {
		next012(Name);
	}
	if (cancelBtnState) {
		cancel012(Name);
	}
	if (saveBtnState) {
		save012(Name);
	}
}
/**
 * 只能输入min到max之间的数字 chenyl 2017-11-21
 */
var minAndMax = function(Name, min, max) {
	var state = Name + 'State';
	eval(state + "=1");
	fbMinAndMax(Name, min, max);
	if (nextBtnState) {
		next012(Name);
	}
	if (cancelBtnState) {
		cancel012(Name);
	}
	if (saveBtnState) {
		save012(Name);
	}
}
/**
 * 版本验证，只允许输入（数字(.数字){n}）
 */
var cVersion = function(Name) {
	var state = Name + 'State';
	eval(state + "=1");
	fbCheckVersion(Name);
	if (nextBtnState) {
		next012(Name);
	}
	if (cancelBtnState) {
		cancel012(Name);
	}
	if (saveBtnState) {
		save012(Name);
	}
}
// 完整，判断长度和数字字母
// 参数：name，最大长度，[固定长度]
var cLengthNumAsc = function(Name, max, min) {
	var state = Name + 'State';
	eval(state + "=1");
	fbLengthNumAsc(Name, max, min);
	if (nextBtnState) {
		next012(Name);
	}
	if (cancelBtnState) {
		cancel012(Name);
	}
	if (saveBtnState) {
		save012(Name);
	}
}

// 完整，判断时间
// 参数：name
var cDate = function(Name, Name2) {
	var state = Name + 'State';
	eval(state + "=1");
	fbDate(Name, Name2);
	if (nextBtnState) {
		next012Date(Name);
	}
	if (cancelBtnState) {
		cancel012(Name);
	}
	if (saveBtnState) {
		save012(Name);
	}
}
var cEffectDate = function(efectDate, invalidDate, effectFlag) {
	var state = efectDate + 'State';
	eval(state + "=1");
	fbEffectDate(efectDate, invalidDate, effectFlag);
	if (nextBtnState) {
		next012Date(efectDate);
	}
	if (cancelBtnState) {
		cancel012(efectDate);
	}
	if (saveBtnState) {
		save012(efectDate);
	}
}
// 完整，判断结束时间
// 参数：name
var cEndDate = function(Name1, Name2) {
	var state1 = Name1 + 'State';
	eval(state1 + "=1");
	var state2 = Name2 + 'State';
	eval(state2 + "=1");
	fbEndDate(Name1, Name2);
	if (nextBtnState) {
		next012Date(Name1);
	}
	if (cancelBtnState) {
		cancel012(Name1);
	}
	if (saveBtnState) {
		save012(Name1);
	}
}

// 完整，判断结束时间
// 参数：name
var cEndDate1 = function(Name1, Name2) {
	var state1 = Name1 + 'State';
	eval(state1 + "=1");
	var state2 = Name2 + 'State';
	eval(state2 + "=1");
	fbEndDate1(Name1, Name2);
	if (nextBtnState) {
		next012Date(Name1);
	}
	if (cancelBtnState) {
		cancel012(Name1);
	}
	if (saveBtnState) {
		save012(Name1);
	}
}

// 完整，判断input(iframe)选择框/select
// 参数：name
var cOpt = function(Name) {
	cleanNull01(Name);
	if (nextBtnState) {
		next01(Name);
	}
	if (cancelBtnState) {
		cancel01(Name);
	}
}

// 完整，单选，复选按钮
var cChecked = function(Name) {
	fbChecked(Name);
	if (nextBtnState) {
		next01Checked(Name);
	}
	if (cancelBtnState) {
		cancel01Checked(Name);
	}
}

// 取空格
function delectBlank(Name) {
	var blank = /^\s*|\s*$/g;
	$("input[name=" + Name + "]").val(
			$("input[name=" + Name + "]").val().replace(blank, ''));
}

// 完整，自动回显
// 自动回显input提示
var cAutomatic = function(Name) {
	$("input[name=" + Name + "]").prop('readonly', 'true').focus(function() {
		var script = '自动回显，不需要输入';
		var tag = "<span class='promptScript'>" + script + "</span>";
		$(this).parent().after(tag);
	}).blur(function() {
		$(this).parent().next('span').remove();
	});
}

// 清空值，状态值重置,清除节点
var resetInput = function(Name,tp) {
	if(tp == undefined){
		var state = Name + 'State';
		eval(state + "=1");
	}
	
	$("#" + Name).val('').removeClass('warnInput trueInput').parent().next(
			'span').remove();
}



// 下一步table验证 有新增行 输入不为空 输入不重复
function nextTable(tableId, toolbarId, classList) {
	$("#" + nextBtnState)
			.click(
					function() {
						if ($('#' + tableId).is(":visible")) {
							// 判断table至少有一行
							var index = $('#' + tableId).bootstrapTable(
									'getData').length;
							if (index == 0) {
								var prompt = "<span class='warnBlock warnBlock2'>至少新增一条节点信息</span>";
								$("#" + toolbarId).after(prompt);
								nextState = 1;
							} else {
								// table是否全输入
								var ifTable = 0;
								// 添加行的input.class不为空
								for (var a = 0; a < classList.length; a++) {
									$('input.' + classList[a]).each(
											function() {
												var blank = /^\s*|\s*$/g;
												$(this).val(
														$(this).val().replace(
																blank, ''));
												if ($(this).val() == '') {
													$(this).css('border-color',
															'#f9918f');
													nextState = 1;
													ifTable = 1;
												}
											});
								}
								// table内容全输入
								if (ifTable == 0) {
									var a = [];
									$('input.' + classList[0]).each(function() {
										a.push($(this).val());
									});
									var portArr = [];
									$('input.' + classList[1]).each(function() {
										portArr.push($(this).val());
									});
									// 输入格式
									var ifIp = 0;
									var testIp = /^([1-9]|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/g;
									for (var b = 0; b < a.length; b++) {
										if (testIp.test(a[b])) {
											// 匹配
											testIp.lastIndex = 0;
										} else {
											// 不匹配
											$('input.' + classList[0]).eq(b)
													.css('border-color',
															'#f9918f');
											nextState = 1;
											ifIp = 1;
										}
									}
									if (ifIp == 1) {
										showContent("Ip输入格式错误", "error");
									}

									if (ifIp == 0) {
										var ifPort = 0;
										var testPort = /\D/g;
										for (var b = 0; b < portArr.length; b++) {
											if (testPort.test(portArr[b])) {
												// 匹配
												$('input.' + classList[1])
														.eq(b).css(
																'border-color',
																'#f9918f');
												nextState = 1;
												ifPort = 1;
											} else {
												// 不匹配
												testPort.lastIndex = 0;
											}
										}
										if (ifPort == 1) {
											showContent("端口只能输入数字", "error");
										}
									}

									// ip输入格式正确才执行
									if (ifPort == 0) {
										// table是否有重复
										var ifRepeat = 0;
										for (var b = 0; b < a.length; b++) {
											for (var c = b + 1; c < a.length; c++) {
												if (a[b] == a[c]) {
													if ($(
															'input.'
																	+ classList[1])
															.eq(b).val() == $(
															'input.'
																	+ classList[1])
															.eq(c).val()) {
														$(
																'input.'
																		+ classList[0])
																.eq(b)
																.css(
																		'border-color',
																		'#f9918f');
														$(
																'input.'
																		+ classList[0])
																.eq(c)
																.css(
																		'border-color',
																		'#f9918f');
														$(
																'input.'
																		+ classList[1])
																.eq(b)
																.css(
																		'border-color',
																		'#f9918f');
														$(
																'input.'
																		+ classList[1])
																.eq(c)
																.css(
																		'border-color',
																		'#f9918f');
														nextState = 1;
														ifRepeat = 1;
													}
												}
											}
										}
										if (ifRepeat == 1) {
											showContent("Ip、端口重复，请重新输入",
													"error");
										}
									}

								}
							}

						}

					});
}
// table focus改回原来颜色
function changTIColor(classList) {
	for (var a = 0; a < classList.length; a++) {
		$(document).on('focus', 'input.' + classList[a], function() {
			$(this).css('border-color', '#9ebed7');
		});
	}
}
// 点击新增清空提示
function cleanTNP(toolbarId) {
	$('#' + toolbarId).find("button").click(function() {
		$("#" + toolbarId).next('span').remove();
	});
}
// 完整 验证table有新增行 且不为空
function cTableLine(tableId, toolbarId, classList) {
	cleanTNP(toolbarId);
	changTIColor(classList);
	nextTable(tableId, toolbarId, classList);
}

// table取消选择行时清除空提示
function cleanTIColor(tableId, classList) {
	$('#' + tableId).on(
			'uncheck.bs.table',
			function(e, row, ele) {
				$('#' + tableId).find('tbody tr').eq(row.index).find('input')
						.css('border-color', '#9ebed7');
			});
}

// 下一步table验证 至少选择一行 选择行list中类的input不为空
function nextTable1(tableId, classList) {
	$("#" + nextBtnState).click(
			function() {
				if ($('#' + tableId).is(":visible")) {
					// 判断table至少有一行

					var index = $('#' + tableId)
							.bootstrapTable('getSelections').length;
					if (index == 0) {
						if (nextState == 0) {
							nextState = 1;
							showContent("保存时至少选择一条地址信息", "error");
						}
					} else {
						// 被选择的条内容必输
						for (var a = 0; a < classList.length; a++) {
							$('#' + tableId).find('tbody tr[class="selected"]')
									.find("input[class=" + classList[a] + "]")
									.each(
											function() {
												var blank = /^\s*|\s*$/g;
												$(this).val(
														$(this).val().replace(
																blank, ''));
												if ($(this).val() == '') {
													$(this).css('border-color',
															'#f9918f');
													nextState = 1;
												}
											});
						}

					}

				}
			});
}

// 完整 验证table至少选择一行 且不为空
function cTableLine1(tableId, classList) {
	cleanTIColor(tableId, classList);
	changTIColor(classList);
	nextTable1(tableId, classList);
}

// 重置table内容
var resetTable = function(Name, btnId) {
	// $("#"+Name).find("[type='text']").val('');
	$("#" + Name).bootstrapTable('removeAll');
	if (btnId) {
		$('#' + btnId).next('span').remove();
	}
}
// 隐藏table
var hideTable = function(Name) {
	$("#" + Name).parents(".bootstrap-table").parent().parent().hide();
	//cIfrHeight();
}
// 显示table
var showTable = function(Name) {
	$("#" + Name).parents(".bootstrap-table").parent().parent().show();
	//cIfrHeight();
}

// 隐藏复选按钮
var hideCheckbox = function(Name) {
	$("input[Name='" + Name + "'").parents('.form-group').eq(0).hide();
	//cIfrHeight();
}
// 显示复选按钮
var showCheckbox = function(Name) {
	$("input[Name='" + Name + "'").parents('.form-group').eq(0).show();
	//cIfrHeight();
}
// 重置复选框
var resetCheckbox = function(Name) {
	$("input[Name='" + Name + "'").each(function() {
		$(this).removeAttr("checked");
	});
}

// 隐藏输入框
var hideInput = function(Name) {
	$("#" + Name).parent().parent().hide();
	//cIfrHeight();
}
// 显示输入框
var showInput = function(Name) {
	$("#" + Name).parent().parent().show();
	//cIfrHeight();
}

// 清空值，状态值重置,清除节点
var resetSelect = function(Name) {
	$("select[name=" + Name + "]").parent().next('span').remove();
	$("select[name=" + Name + "]").multiselect('select',
			[ $("select[name=" + Name + "]").find('option').eq(0).val() ])
			.multiselect('refresh');
}
// 隐藏下拉框
var hideSelect = function(Name) {
	$("select[name=" + Name + "]").parent().parent().parent().hide();
	//cIfrHeight();
}
// 显示下拉框
var showSelect = function(Name) {
	$("select[name=" + Name + "]").parent().parent().parent().show();
	//cIfrHeight();
}

// 下拉选择框一个选项控制下拉选择框必输
// Num为第几个选项，从1开始数
var selectSelect1 = function(sName1, sName2, Num) {
	var Num1 = Num - 1;
	$("select[name=" + sName1 + "]").change(function() {
		if ($(this).find('option').eq(Num1).is(':selected')) {
			showSelect(sName2);
		} else {
			resetSelect(sName2);
			hideSelect(sName2);
			$("select[name=" + sName2 + "]").change();
		}
	});
	$("select[name=" + sName1 + "]").change();
}

// 下拉选择框两个选项控制下拉选择框必输
// Num1，2为第几个选项，从1开始数
var selectSelect2 = function(sName1, sName2, Num1, Num2) {
	var Num11 = Num1 - 1;
	var Num22 = Num2 - 1;
	$("select[name=" + sName1 + "]").change(
			function() {
				if ($(this).find('option').eq(Num11).is(':selected')
						|| $(this).find('option').eq(Num22).is(':selected')) {
					showSelect(sName2);
				} else {
					resetSelect(sName2);
					hideSelect(sName2);
					$("select[name=" + sName2 + "]").change();
				}
			});
	$("select[name=" + sName1 + "]").change();
}

// 下拉选择框控制下拉选择框的值，并且不可修改
// NumList1为控制框的值list，NumList2为被控制框的值
// (原为根据第几个判断，现改为根据值来判断)
var selectSelect12 = function(sName1, sName2, NumList1, NumList2) {
	// var Num11=Num1-1;
	// var Num22=Num2-1;
	$("select[name=" + sName1 + "]")
			.change(
					function() {
						if ($(this).find('option:selected').val() != '') {
							var ifCheck = false;
							for (var a = 0; a < NumList1.length; a++) {
								if ($(this).find('option:selected').val() == NumList1[a]) {
									ifCheck = true;
									break;
								}
							}
							if (ifCheck == true) {
								// 刷新，确定准确值
								$("select[name=" + sName2 + "]").multiselect(
										'select', NumList2.slice(0, 1))
										.multiselect('disable').multiselect(
												'refresh');
								// 如果有控制输入框的change事件
								$("select[name=" + sName2 + "]").change();
							} else {
								$("select[name=" + sName2 + "]").multiselect(
										'select', NumList2.slice(1, 2))
										.multiselect('disable').multiselect(
												'refresh');
								$("select[name=" + sName2 + "]").change();
								// $("select[name="+sName2+"]").multiselect('enable');
							}
						} else {
							$("select[name=" + sName2 + "]").multiselect(
									'enable');
						}

						/*
						 * if($(this).find('option').eq(Num11).is(':selected')){
						 * //操作select框没用？？？喵喵喵 //
						 * $("select[name="+sName2+"]").prop('disabled',true).find('option').eq(Num22).prop('selected',true);
						 * //select插件实际上式radio/checkbox控制,不会完全控制 //
						 * $("select[name="+sName2+"]").parent().find("input[type='radio']").eq(Num22).prop('checked',true);
						 * 
						 * //查找rainbowAPI调用插件方法 //设置值 //禁用下拉框 //刷新，确定准确值
						 * $("select[name="+sName2+"]").multiselect('select',
						 * [$("select[name="+sName2+"]").find('option').eq(Num22).val()]).multiselect('disable').multiselect('refresh');
						 * //如果有控制输入框的change事件
						 * $("select[name="+sName2+"]").change(); }else{
						 * //普通方法对插件无效 //
						 * $("select[name="+sName2+"]").prop('readonly',false).find('option').eq(0).prop('selected',true).sibling().prop('selected',false);
						 * 
						 * //查找rainbowAPI调用插件方法 //启用下拉框
						 * $("select[name="+sName2+"]").multiselect('enable'); }
						 */
					});
}

// 下拉选择框控制table显隐
var selectTable = function(sName, tName, Num, btnId) {
	var Num1 = Num - 1;
	$("select[name=" + sName + "]").change(function() {
		if ($(this).find('option').eq(Num1).is(':selected')) {
			showTable(tName);
		} else {
			hideTable(tName);
			resetTable(tName, btnId);
		}
	});
	$("select[name=" + sName + "]").change();
	if (cancelBtnState) {
		$("#" + cancelBtnState).click(function() {
			hideTable(tName);
			resetTable(tName);
		});
	}
}

// 下拉选择框控制input
// Num为第几个选项，1开始算
var selectInput = function(sName, iName, Num) {
	var Num1 = Num - 1;
	$("select[name=" + sName + "]").change(function() {
		if ($(this).find('option').eq(Num1).is(':selected')) {
			showInput(iName);
		} else {
			hideInput(iName);
			resetInput(iName);
		}
	});
	$("select[name=" + sName + "]").change();
	if (undefined != cancelBtnState) {
		$("#" + cancelBtnState).click(function() {
			hideInput(iName);
			resetInput(iName);
		});
	}
}

// 单选按钮控制select
// Num为单选按钮的值
var radioSelect = function(rName, iName, Num) {
	// var Num1 = Num-1;
	$("input[name=" + rName + "]").change(function() {
		if ($("input[name=" + rName + "]:checked").val() == Num) {
			showSelect(iName);
		} else {
			resetSelect(iName);
			hideSelect(iName);
			$("select[name=" + iName + "]").change();
		}
	});
	$("input[name=" + rName + "]").change();
	if (cancelBtnState) {
		$("#" + cancelBtnState).click(function() {
			resetSelect(iName);
			hideSelect(iName);
		});
	}
}

// 单选按钮控制input
// Num为第几个单选按钮，1开始算
function radioInput(rName, iName, Num ,tp) {
	var Num1 = Num - 1;
	$("input[name=" + rName + "]").change(function() {
		if ($("input[name=" + rName + "]").eq(Num1).is(":checked")) {
			showInput(iName);
		} else {
			hideInput(iName);
			if(tp==undefined){
				resetInput(iName);
			}else{
				resetInput(iName,1);
			}
			
		}
	});
	$("input[name=" + rName + "]").change();
	
	if(tp==undefined){
		if (cancelBtnState) {
			$("#" + cancelBtnState).click(function() {
				hideInput(iName);
				resetInput(iName);
			});
		}
	}
	
}

// 单选按钮控制checkbox
// Num为第几个单选按钮，1开始算
function radioCheckbox(rName, iName, Num,tp) {
	var Num1 = Num - 1;
	$("input[name=" + rName + "]").change(function() {
		if ($("input[name=" + rName + "]").eq(Num1).is(":checked")) {
			showCheckbox(iName);
		} else {
			hideCheckbox(iName);
			resetCheckbox(iName);
			$("input[name=" + iName + "]").change();
		}
	});
	$("input[name=" + rName + "]").change();
	if(tp==undefined){
		if (cancelBtnState) {
			$("#" + cancelBtnState).click(function() {
				hideCheckbox(iName);
				resetCheckbox(iName);
			});
		}
	}
	
}

// 复选按钮控制input
// 只要有被选择就显示必填
var checkboxInput = function(cName, iName) {
	$("input[name=" + cName + "]").change(function() {
		if ($("input[name=" + cName + "]").is(":checked")) {
			showInput(iName);
		} else {
			hideInput(iName);
			resetInput(iName);
		}
	});
	$("input[name=" + cName + "]").change();
	if (cancelBtnState) {
		$("#" + cancelBtnState).click(function() {
			hideInput(iName);
		});
	}
}

//div内元素取值开始，设置为全局变量
function startFormGet(Id){
	SmartWeb.FORM_GET_DIV = Id;
	//SmartWeb.FORM_GET_DIV = Id;
}


// 获取input框的值
var getI = function(Name) {
	var $I = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$I = $('#' + Name);
	}else{
		$I = $("#"+SmartWeb.FORM_GET_DIV).find('#' + Name);
	}
	var a = $I.val();
	
	return a;
}

// 获取单选按钮的值
var getR = function(Name) {
	var $R = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$R =  $("input[name=" + Name + "]:checked");
	}else{
		$R = $("#"+SmartWeb.FORM_GET_DIV).find("input[name=" + Name + "]:checked");
	}
	var a = $R.val();
	
	if (a == undefined) {
		a = '';
	}
	return a;
}

// 获取复按钮的值
var getC = function(Name) {
	var a = [];
	var $C = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$C = $("input[name=" + Name + "]");
	}else{
		$C = $("#"+SmartWeb.FORM_GET_DIV).find("input[name=" + Name + "]");
	}
	$C.each(function() {
		a.push($(this).val());
	});
	
	return a.join('');
}

//获取按钮选项的值
//分号隔开
var getB = function(Name,separator) {
	var valArr = [];
	var $B = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$B = $("input[name=" + Name + "]");
	}else{
		$B = $("#"+SmartWeb.FORM_GET_DIV).find("input[name=" + Name + "]");
	}
	$B.filter(':checked').each(
			function() {
				valArr.push($(this).val());
			});
	
	if (valArr.length === 0) {
		return '';
	} else {
		if(undefined == separator){
			return valArr.join(';');
		}else{
			return valArr.join(separator);
		}
		
	}
}

//获取按钮选项的显示标签
//分号隔开
var getBT = function(Name,separator) {
	var valArr = [];
	var $BT = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$BT = $("input[name=" + Name + "]");
	}else{
		$BT = $("#"+SmartWeb.FORM_GET_DIV).find("input[name=" + Name + "]");
	}
	$BT.filter(':checked').each(
			function() {
				valArr.push($(this).parent("label").text());
			});
	
	if (valArr.length === 0) {
		return '';
	} else {
		if(undefined == separator){
			return valArr.join(';');
		}else{
			return valArr.join(separator);
		}
		
	}
}

// 获取下拉框的值
// 获取下拉选择框的值 分号隔开
var getS = function(Name,separator) {
	var valArr = [];
	var $S = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$S = $("select[name=" + Name + "]");
	}else{
		$S = $("#"+SmartWeb.FORM_GET_DIV).find("select[name=" + Name + "]");
	}
	$S.find('option').filter(':selected').each(
			function() {
				valArr.push($(this).val());
			});
	
	if (valArr.length === 0) {
		return '';
	} else {
		if(undefined == separator){
			return valArr.join(';');
		}else{
			return valArr.join(separator);
		}
		
	}

}

//获取下拉框的标签内容
//获取下拉选择框的值 逗号隔开
var getST = function(Name,separator) {
	var valArr = [];
	var $ST = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$ST = $("select[name=" + Name + "]");
	}else{
		$ST = $("#"+SmartWeb.FORM_GET_DIV).find("select[name=" + Name + "]");
	}
	$ST.find('option').filter(':selected')
	.each(function() {
		valArr.push($(this).text());
	});
	

	if (valArr.length === 0) {
		return '';
	} else {
		if(undefined == separator){
			return valArr.join(';');
		}else{
			return valArr.join(separator);
		}
	}
}

// 获取下拉框的标签 val-name中name的内容
// 获取下拉选择框的值 逗号隔开
var getSTN = function(Name,separator) {
	var valArr = [];
	var $ST = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$ST = $("select[name=" + Name + "]");
	}else{
		$ST = $("#"+SmartWeb.FORM_GET_DIV).find("select[name=" + Name + "]");
	}
	$ST.find('option').filter(':selected')
	.each(function() {
		var labStr = $(this).text();
		var index = labStr.indexOf("-") + 1;
		valArr.push(labStr.slice(index));
	});
	

	if (valArr.length === 0) {
		return '';
	} else {
		if(undefined == separator){
			return valArr.join(';');
		}else{
			return valArr.join(separator);
		}
	}
}


//获取下拉框的标签 val-name中name的内容
//获取下拉选择框的值 逗号隔开
var getSTN = function(Name,separator) {
	var valArr = [];
	var $ST = undefined;
	if(null==SmartWeb.FORM_GET_DIV || ""==SmartWeb.FORM_GET_DIV || undefined==SmartWeb.FORM_GET_DIV){
		$ST = $("select[name=" + Name + "]");
	}else{
		$ST = $("#"+SmartWeb.FORM_GET_DIV).find("select[name=" + Name + "]");
	}
	$ST.find('option').filter(':selected')
	.each(function() {
		var labStr = $(this).text();
		var index = labStr.indexOf("-") + 1;
		valArr.push(labStr.slice(index));
	});
	

	if (valArr.length === 0) {
		return '';
	} else {
		if(undefined == separator){
			return valArr.join(';');
		}else{
			return valArr.join(separator);
		}
	}
}


//div内元素取值结束
function endFormGet(){
	SmartWeb.FORM_GET_DIV = undefined;
}

/*
 * 清空div内input框的值，重置select、单选复选按钮、table、输入域 div的id 是否判断readonly false为全部，空和true忽略
 * 是否判断iframe true判断 ifram内是否判断readonly false不判断
 */
var resetForm = function(Id, ifR, tp) {
	$('#' + Id).find(".warnBlock,.warnBlock2").remove();
	$('#' + Id).find(".trueInput").removeClass("trueInput");
	
	if (ifR == false) {
		$('#' + Id).find(':input').not(
				":button, :submit, :reset, :checkbox, :radio").val("").removeClass('warnInput trueInput');
	} else if (ifR == true || undefined == ifR) {
		$('#' + Id).find(':input').not(
				":button, :submit, :reset, :checkbox, :radio").not(
				":input[readonly='readonly']").not(":disabled").val("").removeClass('warnInput trueInput');
	} else if (ifR == "noDisabled") {
		$('#' + Id).find(':input').not(
				":button, :submit, :reset, :checkbox, :radio").not(":disabled")
				.val("").removeClass('warnInput trueInput');
	} else if (ifR == "noReadonly") {
		$('#' + Id).find(':input').not(
				":button, :submit, :reset, :checkbox, :radio").not(
				":input[readonly='readonly']").val("").removeClass('warnInput trueInput');
	}

	$('#' + Id).find(':checkbox,:radio').removeAttr("checked");
	
	$('#' + Id).find('select').not(":disabled").each(function() {
		if($(this).attr("multiple")=="multiple"){
			//多选
			$(this).multiselect('deselectAll', true).multiselect('refresh');
		}else{
			var firstVal = $(this).find('option').eq(0).val();
			$(this).multiselect('select', firstVal).multiselect('refresh');
		}
		
	});
	$('#' + Id).find('textarea').val("").removeClass('warnInput trueInput');
	$('#' + Id).find('table[data-toggle="table"]').bootstrapTable('removeAll');

	if (tp == true) {
		// 重新加载ifr
		$('#' + Id).find('iframe').attr('src',
				$('#' + Id).find('iframe').attr('src'));
	}

	/*
	 * if (tp == true) { if(ifR==false){ $('#' +
	 * Id).find('iframe').contents().find(':input').not( ":button, :submit,
	 * :reset, :checkbox, :radio").val(""); }else{ $('#' +
	 * Id).find('iframe').contents().find(':input').not( ":button, :submit,
	 * :reset, :checkbox, :radio").not( ":input[readonly='readonly']").val(""); }
	 * 
	 * $('#' +
	 * Id).find('iframe').contents().find(':checkbox,:radio').removeAttr("checked");
	 * $('#' + Id).find('iframe').contents().find('select').each(function() {
	 * var firstVal = $(this).find('option').eq(0).val(); var resetArr = [];
	 * resetArr.push(firstVal); $(this).multiselect('select',
	 * resetArr).multiselect('refresh'); }); $('#' +
	 * Id).find('iframe').contents().find('textarea').val("");
	 * iframe里的table清除数据不生效？ $('#' +
	 * Id).find('iframe').contents().find('table').bootstrapTable('removeAll'); }
	 */
}


//div内元素设置值开始
function startFormSet(Id){
	SmartWeb.FORM_SET_DIV = Id;
	//SmartWeb.FORM_SET_DIV = Id;
}

// 设置input框的值
function setI(Name, Val) {
	var $I;
	if(null==SmartWeb.FORM_SET_DIV || undefined==SmartWeb.FORM_SET_DIV ||""==SmartWeb.FORM_SET_DIV){
		$I = $('#' + Name);
	}else{
		$I = $('#' + SmartWeb.FORM_SET_DIV).find("#"+Name);
	}
	$I.val(Val);
}

// 设置单选按钮（根据实际值）
function setR(Name, Val) {
	var $R;
	if(null==SmartWeb.FORM_SET_DIV || undefined==SmartWeb.FORM_SET_DIV ||""==SmartWeb.FORM_SET_DIV){
		$R = $("input[name=" + Name + "][value=" + Val + "]");
	}else{
		$R = $('#' + SmartWeb.FORM_SET_DIV).find("input[name=" + Name + "][value=" + Val + "]");
	}
	$R.prop('checked', true);
}

// 设置复选按钮（选中改变值，最终取值为全部拼接，默认选中变1）
function setC(Name, Val , checkedVal) {
	if(Val=="" || Val==undefined){
		return;
	}
	
	var $C;
	if(null==SmartWeb.FORM_SET_DIV || undefined==SmartWeb.FORM_SET_DIV ||""==SmartWeb.FORM_SET_DIV){
		$C = $("input[name=" + Name + "]");
	}else{
		$C = $('#' + SmartWeb.FORM_SET_DIV).find("input[name=" + Name + "]");
	}
	
	for (a = 0; a < Val.length; a++) {
		if(checkedVal == undefined){
			if (Val[a] == 1) {
				$C.eq(a).prop('checked', true).val('1');
			}
		}else{
			if (Val[a] == checkedVal) {
				$C.eq(a).prop('checked', true).val(checkedVal);
			}
		}
	}
}

//根据值设置按钮选择
function setB(Name,Val,separator){
	if ( undefined == Val || null == Val) {
		return;
	}
	
	if ( undefined != separator) {
		Val = Val.split(separator);
	}else{
		Val = Val.split(";");
	}
	
	var $B;
	if(null==SmartWeb.FORM_SET_DIV || undefined==SmartWeb.FORM_SET_DIV ||""==SmartWeb.FORM_SET_DIV){
		$B = $("input[name=" + Name + "]");
	}else{
		$B = $('#' + SmartWeb.FORM_SET_DIV).find("input[name=" + Name + "]");
	}
	
	$B.each(function(){
		$(this).removeAttr("checked");
	});
	
	for(var a=0;a<Val.length;a++){
		$B.filter("[value='"+Val[a]+"']").prop('checked', true);
	}
}

// 设置下拉框
function setS(Name, Val, separator) {
	if ( undefined == Val || null == Val) {
		return;
	}
	
	if (undefined != separator) {
		var valArr = (Val+"").split(separator);
	}else{
		var valArr = (Val+"").split(";");
	}
	
	var $S;
	if(null==SmartWeb.FORM_SET_DIV || undefined==SmartWeb.FORM_SET_DIV ||""==SmartWeb.FORM_SET_DIV){
		$S = $("select[name=" + Name + "]");
	}else{
		$S =$('#' + SmartWeb.FORM_SET_DIV).find("select[name=" + Name + "]");
	}
	
	if($S.next().attr("class")!=undefined){
		if($S.next().attr("class").match("btn-group")){
			if($S.attr("multiple")=="multiple"){
				//$S.next().find(':checkbox').removeAttr("checked");
				//$S.multiselect('deselectAll', true).multiselect('refresh');
				$S.val(valArr);
				$S.multiselect('refresh');
			}else{
				$S.multiselect('select', valArr).multiselect('refresh');
			}
			
		}else{
			$S.val(Val);
		}
	}else{
		$S.val(Val);
	}
}

//div内元素设置值结束
function endFormSet(){
	SmartWeb.FORM_SET_DIV = undefined;
}

// 子页面控制iframe高度自适应
//TODO这个方法有bug，相应地方先注释掉了
function cIfrHeight() {
	$(window.parent.document).find('iframe').height($('body').height() + 230);
}

// 父页面增加iframe节点
function ifrPoint(Id, Src) {
	$("#" + Id).find('iframe').remove();
	var tag = "<iframe style='width: 100%;height:100%;' frameborder='0' scrolling='no'></iframe>";
	$("#" + Id).append(tag).find('iframe').attr('src', ctx + "/" + Src);
}

// 父页面iframe自适应
function ifrHeight(Id) {
	$("#" + Id).find('iframe').load(function() {
		$(this).height($(this).contents().find('body').height() + 200);
	});
}
function ifrHeight1(Id) {
	$("#" + Id).find('iframe').load(function() {
		$(this).height($(this).contents().find('body').height() + 100);
	});
}

function setIfrHeight1(Id, num) {
	/*$("#" + Id).find('iframe').load(function() {
		$(this).height($(this).contents().find('body').height() + num);
	});*/
	$("#" + Id).find('iframe').on('load',function() {
		$(this).height($(this).contents().find('body').height() + num);
	});
}

// 父页面增加iframe节点并自适应
function ifr(Id, Src,num) {
	ifrPoint(Id, Src);
	if(undefined!=num && null !=num){
		setIfrHeight1(Id, num);
	}else{
		ifrHeight(Id);
	}
}

// 父页面增加iframe节点并自适应不加
function ifr1(Id, Src ,num) {
	ifrPoint(Id, Src);
	if(undefined!=num && null !=num){
		setIfrHeight1(Id, num);
	}else{
		ifrHeight1(Id);
	}
}

//ifr子页面变动改变高度
function ifrAutH(Id) {
	$("#" + Id).find('iframe').height(
			$("#" + Id).find('iframe').contents().find('body').height() + 200);
	$("#" + Id).height(
			$("#" + Id).find('iframe').contents().find('body').height() + 200);
}
function cIfrAutH(Id) {
	parent.ifrAutH(Id);
}

// ifr子自适应
function ifrChildAut(Id,Num) {
	var num =200;
	if(undefined!=Num){
		num =Num;
	}
	$(window.parent.document).find("#" + Id).find('iframe').height(
			$(window.parent.document).find("#" + Id).find('iframe').contents()
					.find('body').height() + num);
	$(window.parent.document).scrollTop(0);
}



// 父页面设置iframe中值时先调用
// 确定增加iframe标签的父级标签
function startIfrSet(Id) {
	ifrSetState = Id;
}

// 设置iframe中input框的值
function ifrSetI(Name, Val) {
	$("#" + ifrSetState).find('iframe').contents().find('#' + Name).val(Val);
}

// 设置iframe中下拉选择框的值
function ifrSetS(Name, Val) {
	/*
	 * $("#"+ifrSetState).find('iframe').contents().find("select[name="+Name+"]").multiselect('select',Val).multiselect('refresh');
	 * //不知为什么执行后会新增节点？手动去掉,change事件消失了？
	 * $("#"+ifrSetState).find('iframe').contents().find("select[name="+Name+"]").parent().next('.btn-group').remove();
	 */

	// 查看元素控制全部
	// 设置下拉选择框的值（真实值）
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").find('option[value=' + Val + ']')
			.prop('selected', true);
	// 插件由单选按钮控制，设置单选按钮的值，去除默认值
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").parent().find(":radio").eq(0).prop(
			'checked', false);
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").parent().find(
			":radio[value=" + Val + "]").prop('checked', true);
	// 设置button显示title值，里面span标签的值
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").parent().find("button").prop(
			'title',
			$("#" + ifrSetState).find('iframe').contents().find(
					"select[name=" + Name + "]").find(
					'option[value=' + Val + ']').text());
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").parent().find("button>span").text(
			$("#" + ifrSetState).find('iframe').contents().find(
					"select[name=" + Name + "]").find(
					'option[value=' + Val + ']').text());

}
// 根据标签内容 设置iframe中下拉选择框的值
function ifrSetST(Name, Text) {
	/*
	 * $("#"+ifrSetState).find('iframe').contents().find("select[name="+Name+"]").multiselect('select',Val).multiselect('refresh');
	 * //不知为什么执行后会新增节点？手动去掉,change事件消失了？
	 * $("#"+ifrSetState).find('iframe').contents().find("select[name="+Name+"]").parent().next('.btn-group').remove();
	 */
	var Val = $("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").find('option[label=' + Text + ']')
			.val();

	// 查看元素控制全部
	// 设置下拉选择框的值（真实值）
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").find('option[value=' + Val + ']')
			.prop('selected', true);
	// 插件由单选按钮控制，设置单选按钮的值，去除默认值
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").parent().find(":radio").eq(0).prop(
			'checked', false);
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").parent().find(
			":radio[value=" + Val + "]").prop('checked', true);
	// 设置button显示title值，里面span标签的值
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").parent().find("button").prop(
			'title',
			$("#" + ifrSetState).find('iframe').contents().find(
					"select[name=" + Name + "]").find(
					'option[value=' + Val + ']').text());
	$("#" + ifrSetState).find('iframe').contents().find(
			"select[name=" + Name + "]").parent().find("button>span").text(
			$("#" + ifrSetState).find('iframe').contents().find(
					"select[name=" + Name + "]").find(
					'option[value=' + Val + ']').text());

}

// 设置iframe中单选按钮
function ifrSetR(Name, Val) {
	$("#" + ifrSetState).find('iframe').contents().find(
			"input[name=" + Name + "][value=" + Val + "]")
			.prop('checked', true);
}

// 设置iframe中复选按钮
// 注意：复选按钮值格式是0000形式，选中值为1
function ifrSetC(Name, Val) {
	for (var a = 0; a < Val.length; a++) {
		if (Val[a] == 1) {
			$("#" + ifrSetState).find('iframe').contents().find(
					"input[name=" + Name + "]").eq(a).prop('checked', true)
					.val('1');
		}
	}
}

// 刷新table内容
function freshTable(Id) {
	$("#"+Id).bootstrapTable('removeAll');
	$("#" + Id).bootstrapTable('refreshOptions', {
		pageNumber : 1,
		pageSize : 10
	});
	// $("#"+Id).bootstrapTable('selectPage', 0).bootstrapTable('refresh');
	// $("#"+Id).bootstrapTable('destroy');

}

// 获取table中input值成数组
// '|'分割成字符串
function getTableI(Class) {
	var obj = $("input[class=" + Class + "]");
	var classArr = [];
	for (var a = 0; a < obj.length; a++) {
		classArr[a] = obj.eq(a).val();
	}
	// alert(classArr.join('|'));
	return classArr.join('|');
}
// 获取table中被选中的input值成数组
// '|'分割成字符串
function getTableSI(Class, tableId) {
	var obj = $('#' + tableId).find('tbody tr[class="selected"]').find(
			"input[class=" + Class + "]");
	var classArr = [];
	for (var a = 0; a < obj.length; a++) {
		classArr[a] = obj.eq(a).val();
	}
	// alert(classArr.join('|'));
	return classArr.join('|');
}

// 获取table中被选中列值成数组
// '|'分割成字符串
// 参数为那一列file域
function getTableS(Class, tableId) {
	// 获取被选中的列形成json数组
	// 数组转换为单列值的数组
	var jsonRow = "row." + Class;
	var a = $.map($('#' + tableId).bootstrapTable('getSelections'), function(
			row) {
		return eval(jsonRow);
	});
	return a.join('|');
}

// 两数组去重后合并返回新数组
// 以第一个数组为准
function uniqueArr(arr1, arr2) {
	for (var a = 0; a < arr1.length; a++) {
		for (var b = 0; b < arr2.length; b++) {
			if (arr1[a] == arr2[b]) {
				arr2.splice(b, 1);
			}
		}
	}
	return arr1.concat(arr2);
}

// 提示框
// 组件封装
(function($) {
	window.Ewin = function() {
		var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">'
				+ '<div class="modal-dialog modal-sm">'
				+ '<div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
				+ '<h4 class="modal-title" id="modalLabel">[Title]</h4>'
				+ '</div>'
				+ '<div class="modal-body">'
				+ '<p>[Message]</p>'
				+ '</div>'
				+ '<div class="modal-footer">'
				+ '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>'
				+ '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>'
				+ '</div>' + '</div>' + '</div>' + '</div>';

		var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">'
				+ '<div class="modal-dialog">'
				+ '<div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
				+ '<h4 class="modal-title" id="modalLabel">[Title]</h4>'
				+ '</div>'
				+ '<div class="modal-body">'
				+ '</div>'
				+ '</div>'
				+ '</div>' + '</div>';
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		var generateId = function() {
			var date = new Date();
			return 'mdl' + date.valueOf();
		}
		var init = function(options) {
			options = $.extend({}, {
				title : "操作提示",
				message : "提示内容",
				btnok : "确定",
				btncl : "取消",
				width : 200,
				auto : false
			}, options || {});
			var modalId = generateId();
			var content = html.replace(reg, function(node, key) {
				return {
					Id : modalId,
					Title : options.title,
					Message : options.message,
					BtnOk : options.btnok,
					BtnCancel : options.btncl
				}[key];
			});
			$('body').append(content);
			$('#' + modalId).modal({
				width : options.width,
				backdrop : 'static'
			});
			$('#' + modalId).on('hide.bs.modal', function(e) {
				$('body').find('#' + modalId).remove();
			});
			return modalId;
		}

		return {
			alert : function(options) {
				if (typeof options == 'string') {
					options = {
						message : options
					};
				}
				var id = init(options);
				var modal = $('#' + id);
				modal.find('.ok').removeClass('btn-success').addClass(
						'btn-primary');
				modal.find('.cancel').hide();

				return {
					id : id,
					on : function(callback) {
						if (callback && callback instanceof Function) {
							modal.find('.ok').click(function() {
								callback(true);
							});
						}
					},
					hide : function(callback) {
						if (callback && callback instanceof Function) {
							modal.on('hide.bs.modal', function(e) {
								callback(e);
							});
						}
					}
				};
			},
			confirm : function(options) {
				var id = init(options);
				var modal = $('#' + id);
				modal.find('.ok').removeClass('btn-primary').addClass(
						'btn-success');
				modal.find('.cancel').show();
				return {
					id : id,
					on : function(callback) {
						if (callback && callback instanceof Function) {
							modal.find('.ok').click(function() {
								callback(true);
							});
							modal.find('.cancel').click(function() {
								callback(false);
							});
						}
					},
					hide : function(callback) {
						if (callback && callback instanceof Function) {
							modal.on('hide.bs.modal', function(e) {
								callback(e);
							});
						}
					}
				};
			},
			dialog : function(options) {
				options = $.extend({}, {
					title : 'title',
					url : '',
					width : 800,
					height : 550,
					onReady : function() {
					},
					onShown : function(e) {
					}
				}, options || {});
				var modalId = generateId();

				var content = dialogdHtml.replace(reg, function(node, key) {
					return {
						Id : modalId,
						Title : options.title
					}[key];
				});
				$('body').append(content);
				var target = $('#' + modalId);
				target.find('.modal-body').load(options.url);
				if (options.onReady())
					options.onReady.call(target);
				target.modal();
				target.on('shown.bs.modal', function(e) {
					if (options.onReady(e))
						options.onReady.call(target, e);
				});
				target.on('hide.bs.modal', function(e) {
					$('body').find(target).remove();
				});
			}
		}
	}();
})(jQuery);

(function($) {
	window.Ewin1 = function() {
		var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">'
				+ '<div class="modal-dialog modal-sm">'
				+ '<div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
				+ '<h4 class="modal-title" id="modalLabel">[Title]</h4>'
				+ '</div>'
				+ '<div class="modal-body">'
				+ '<p>[Message]</p>'
				+ '</div>'
				+ '<div class="modal-footer">'
				+ '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>'
				+ '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>'
				+ '</div>' + '</div>' + '</div>' + '</div>';

		var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">'
				+ '<div class="modal-dialog">'
				+ '<div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
				+ '<h4 class="modal-title" id="modalLabel">[Title]</h4>'
				+ '</div>'
				+ '<div class="modal-body">'
				+ '</div>'
				+ '</div>'
				+ '</div>' + '</div>';
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		var generateId = function() {
			var date = new Date();
			return 'mdl' + date.valueOf();
		}
		var init = function(options) {
			options = $.extend({}, {
				title : "操作提示",
				message : "提示内容",
				btnok : "是",
				btncl : "否",
				width : 200,
				auto : false
			}, options || {});
			var modalId = generateId();
			var content = html.replace(reg, function(node, key) {
				return {
					Id : modalId,
					Title : options.title,
					Message : options.message,
					BtnOk : options.btnok,
					BtnCancel : options.btncl
				}[key];
			});
			$('body').append(content);
			$('#' + modalId).modal({
				width : options.width,
				backdrop : 'static'
			});
			$('#' + modalId).on('hide.bs.modal', function(e) {
				$('body').find('#' + modalId).remove();
			});
			return modalId;
		}

		return {
			alert : function(options) {
				if (typeof options == 'string') {
					options = {
						message : options
					};
				}
				var id = init(options);
				var modal = $('#' + id);
				modal.find('.ok').removeClass('btn-success').addClass(
						'btn-primary');
				modal.find('.cancel').hide();

				return {
					id : id,
					on : function(callback) {
						if (callback && callback instanceof Function) {
							modal.find('.ok').click(function() {
								callback(true);
							});
						}
					},
					hide : function(callback) {
						if (callback && callback instanceof Function) {
							modal.on('hide.bs.modal', function(e) {
								callback(e);
							});
						}
					}
				};
			},
			confirm : function(options) {
				var id = init(options);
				var modal = $('#' + id);
				modal.find('.ok').removeClass('btn-primary').addClass(
						'btn-success');
				modal.find('.cancel').show();
				return {
					id : id,
					on : function(callback) {
						if (callback && callback instanceof Function) {
							modal.find('.ok').click(function() {
								callback(true);
							});
							modal.find('.cancel').click(function() {
								callback(false);
							});
						}
					},
					hide : function(callback) {
						if (callback && callback instanceof Function) {
							modal.on('hide.bs.modal', function(e) {
								callback(e);
							});
						}
					}
				};
			},
			dialog : function(options) {
				options = $.extend({}, {
					title : 'title',
					url : '',
					width : 800,
					height : 550,
					onReady : function() {
					},
					onShown : function(e) {
					}
				}, options || {});
				var modalId = generateId();

				var content = dialogdHtml.replace(reg, function(node, key) {
					return {
						Id : modalId,
						Title : options.title
					}[key];
				});
				$('body').append(content);
				var target = $('#' + modalId);
				target.find('.modal-body').load(options.url);
				if (options.onReady())
					options.onReady.call(target);
				target.modal();
				target.on('shown.bs.modal', function(e) {
					if (options.onReady(e))
						options.onReady.call(target, e);
				});
				target.on('hide.bs.modal', function(e) {
					$('body').find(target).remove();
				});
			}
		}
	}();
})(jQuery);

function getIfrI(Id, ID) {
	var a = $("#" + Id).find('iframe').contents().find('#' + ID).val();
	return a;
}

// 设置子页面ID不可输
function readonlyIfrI(Id, ID) {
	var a = $("#" + Id).find('iframe').contents().find('#' + ID).attr(
			'disabled', 'true');
}

// 设置子页面ID隐藏
function hideIfrID(Id, ID) {
	$("#" + Id).find('iframe').contents().find('#' + ID).addClass('hide');
}

// 本页面隐藏id
function hideID(ID) {
	$("#" + ID).addClass('hide');
}

/* 删除下拉框选项留下请选择 */
function resetS(Name) {
	var pleaseS = [ {
		label : '请选择',
		value : ''
	} ];
	$("select[name=" + Name + "]").multiselect('dataprovider', pleaseS);
}

/* json数组去重 */
function uniqueJsonArr(jsonArr, ValName, PreName) {
	if(null!=jsonArr && undefined!=jsonArr && jsonArr.length>0){
		for (var a = 0; a < jsonArr.length; a++) {
			var preName = jsonArr[a][PreName];
			var valName = jsonArr[a][ValName];
			for (var b = a + 1; b < jsonArr.length; b++) {
				if (jsonArr[b][PreName] == preName
						&& jsonArr[b][ValName] == valName) {
					jsonArr.splice(b, 1);
					b--;
				}
			}
		}
	}
}

/*
 * 刚进页面直接调接口加载下拉框，不需要传值 下拉框Name Controller地址Url 下拉框值字段名ValName 下拉框显示字段名PreName Name输入参数是否为Id
 */
function setSysSelectData(Name, Url, ValName, PreName, ifUnique, ifAsync,ifSelect, isId) {
	if(isId){
		var pleaseS = [ {
			label : '请选择',
			value : ''
		} ];
		$("select[id=" + Name + "]").multiselect('dataprovider', pleaseS);
	}else{
		resetS(Name);
	}
	var $ifasync = true;
	if (ifAsync == false) {
		$ifasync = false;
	}
	$.ajax({
		url : ctx + Url,
		type : "GET",
		dataType : "json",
		data : {},
		async : $ifasync,
		success : function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var valData = data.list;
				if(null==valData || undefined==valData){
					valData = '[]';
				}
				valData = eval(valData);
				// 是否需要去重
				if (ifUnique == true) {
					uniqueJsonArr(valData, ValName, PreName);
				}

				var dataArr = [];
				if (valData.length != 0) {
					for (var a = 0; a < valData.length; a++) {
						var jsonVal = valData[a];
						dataArr[a] = {
							label : jsonVal[PreName],
							value : jsonVal[ValName]
						}
					}
				}
				if(ifSelect!=true){
					dataArr.unshift({
						label : '请选择',
						value : ''
					});
				}
				
				if(isId){
					$("select[id=" + Name + "]").multiselect('dataprovider',
							dataArr).multiselect('rebuild').multiselect('refresh');
				}else{
					$("select[name=" + Name + "]").multiselect('dataprovider',
							dataArr).multiselect('rebuild').multiselect('refresh');
				}

			}
		}
	});

}

/*
 * 刚进页面直接调接口加载下拉框，不需要传值 下拉框Name Controller地址Url 下拉框值字段名ValName 下拉框显示字段名PreName
 */
function setSelectData(Name, Url, ValName, PreName, ifUnique, ifAsync,ifSelect) {
	resetS(Name);
	var $ifasync = true;
	if (ifAsync == false) {
		$ifasync = false;
	}
	$.ajax({
		url : ctx + Url,
		type : "GET",
		dataType : "json",
		data : {},
		async : $ifasync,
		success : function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var valData = data.dataSetResult[0].data;
				valData = eval(valData);
				// 是否需要去重
				if (ifUnique == true) {
					uniqueJsonArr(valData, ValName, PreName);
				}

				var dataArr = [];
				if (valData.length != 0) {
					for (var a = 0; a < valData.length; a++) {
						var jsonVal = valData[a];
						dataArr[a] = {
							label : jsonVal[ValName] + '-' + jsonVal[PreName],
							value : jsonVal[ValName]
						}
					}
				}
				if(ifSelect!=true){
					dataArr.unshift({
						label : '请选择',
						value : ''
					});
				}
				
				$("select[name=" + Name + "]").multiselect('dataprovider',
						dataArr).multiselect('rebuild').multiselect('refresh');

			}
		}
	});

}

/*
 * 刚进页面直接调接口加载下拉框，不需要传值 下拉框Name Controller地址Url 下拉框值字段名ValName 下拉框显示字段名PreName
 */
function setSelect1(Name, Url, ValName, PreName, ifUnique, ifAsync,ifSelect) {
	resetS(Name);
	var $ifasync = true;
	if (ifAsync == false) {
		$ifasync = false;
	}
	$.ajax({
		url : ctx + Url,
		type : "GET",
		dataType : "json",
		data : {},
		async : $ifasync,
		success : function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var valData = data.dataSetResult[0].data;
				valData = eval(valData);
				// 是否需要去重
				if (ifUnique == true) {
					uniqueJsonArr(valData, ValName, PreName);
				}

				var dataArr = [];
				if (valData.length != 0) {
					for (var a = 0; a < valData.length; a++) {
						var jsonVal = valData[a];
						dataArr[a] = {
							label : jsonVal[PreName],
							value : jsonVal[ValName]
						}
					}
				}
				if(ifSelect!=true){
					dataArr.unshift({
						label : '请选择',
						value : ''
					});
				}
				
				$("select[name=" + Name + "]").multiselect('dataprovider',
						dataArr).multiselect('rebuild').multiselect('refresh');

			}
		}
	});

	/*
	 * $.get(ctx + Url, { // TODO }, function(data) { if (data.returnCode !==
	 * undefined && "0000" != data.returnCode) { var errMsg = "错误信息[" +
	 * data.message + "]"; showContent(errMsg, "error"); } else {
	 * console.log(data.message); var valData = data.dataSetResult[0].data;
	 * valData = eval(valData); // 是否需要去重 if (ifUnique == true) {
	 * uniqueJsonArr(valData, ValName, PreName); }
	 * 
	 * var dataArr = []; if (valData.length != 0) { for (var a = 0; a <
	 * valData.length; a++) { var jsonVal = valData[a]; dataArr[a] = { label :
	 * jsonVal[PreName], value : jsonVal[ValName] } } } dataArr.unshift({ label :
	 * '请选择', value : '' }); $("select[name=" + Name +
	 * "]").multiselect('dataprovider', dataArr)
	 * .multiselect('rebuild').multiselect('refresh'); } }, "json");
	 */
}

/*
 * 级联下拉框，传入data往后台获取值加载下拉框 下拉框Name Controller地址Url 下拉框值字段名ValName
 * 下拉框显示字段名PreName 传入后台的json数据Data 控制此下拉框的值ValControll(值为空则清空此下拉框，否则向后台查询加载加载)
 */
function setSelect2(Name, Url, ValName, PreName, Data, ValControll, ifUnique,
		ifAsync,ifSelect) {
	addLoad(Name);
	/*
	 * $("select[name=" + Name + "]").multiselect('destroy')
	 * .multiselect('rebuild');
	 */
	if (ValControll == "") {
		resetS(Name);
		removeLoad(Name);
		return;
	}
	var $ifasync = true;
	if (ifAsync == false) {
		$ifasync = false;
	}
	$
			.ajax({
				url : ctx + Url,
				type : "GET",
				dataType : "json",
				data : Data,
				async : $ifasync,
				success : function(data) {
					if (data.returnCode !== undefined
							&& "0000" != data.returnCode) {
						var errMsg = "错误信息[" + data.message + "]";
						showContent(errMsg, "error");
					} else {
						console.log(data.message);
						var valData = data.dataSetResult[0].data;
						valData = eval(valData);
						// 是否需要去重
						if (ifUnique == true) {
							uniqueJsonArr(valData, ValName, PreName);
						}
						var dataArr = [];
						if (valData.length != 0) {
							for (var a = 0; a < valData.length; a++) {
								var jsonVal = valData[a];
								dataArr[a] = {
									label : jsonVal[PreName],
									value : jsonVal[ValName]
								}
							}
						}
						if ($("select[name=" + Name + "]").attr('multiple') == "multiple") {

						} else {
							if(ifSelect!=true){
								dataArr.unshift({
									label : '请选择',
									value : ''
								});
							}
							
						}

						$("select[name=" + Name + "]").multiselect(
								'dataprovider', dataArr);

						$("select[name=" + Name + "]").multiselect('rebuild');

					}
					removeLoad(Name);
				}
			});

	/*
	 * $.get(ctx + Url, Data, function(data) { if (data.returnCode !== undefined &&
	 * "0000" != data.returnCode) { var errMsg = "错误信息[" + data.message + "]";
	 * showContent(errMsg, "error"); } else { console.log(data.message); var
	 * valData = data.dataSetResult[0].data; valData = eval(valData); // 是否需要去重
	 * if (ifUnique == true) { uniqueJsonArr(valData, ValName, PreName); } var
	 * dataArr = []; if (valData.length != 0) { for (var a = 0; a <
	 * valData.length; a++) { var jsonVal = valData[a]; dataArr[a] = { label :
	 * jsonVal[PreName], value : jsonVal[ValName] } } } if ($("select[name=" +
	 * Name + "]").attr('multiple') == "multiple") { } else { dataArr.unshift({
	 * label : '请选择', value : '' }); }
	 * 
	 * $("select[name=" + Name + "]").multiselect( 'dataprovider', dataArr);
	 * 
	 * $("select[name=" + Name + "]").multiselect( 'rebuild'); }
	 * removeLoad(Name); }, "json");
	 */
}

/*
 * 刚进页面直接调接口加载下拉框，不需要传值 下拉框Name Controller地址Url 下拉框值字段名ValName 下拉框显示字段名PreName
 * 下拉框label值为 ValName-PreName
 */
function setSelect3(Name, Url, ValName, PreName, ifUnique, ifAsync,ifSelect) {
	resetS(Name);
	var $ifasync = true;
	if (ifAsync == false) {
		$ifasync = false;
	}
	$.ajax({
		url : ctx + Url,
		type : "GET",
		dataType : "json",
		data : {},
		async : $ifasync,
		success : function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var valData = data.dataSetResult[0].data;
				valData = eval(valData);
				// 是否需要去重
				if (ifUnique == true) {
					uniqueJsonArr(valData, ValName, PreName);
				}

				var dataArr = [];
				if (valData.length != 0) {
					for (var a = 0; a < valData.length; a++) {
						var jsonVal = valData[a];
						dataArr[a] = {
							label : jsonVal[ValName] + "-" + jsonVal[PreName],
							value : jsonVal[ValName]
						}
					}
				}
				if(ifSelect!=true){
					dataArr.unshift({
						label : '请选择',
						value : ''
					});
				}
				
				$("select[name=" + Name + "]").multiselect('dataprovider',
						dataArr).multiselect('rebuild').multiselect('refresh');

			}
		}
	});
}

/*
 * 级联下拉框，传入data往后台获取值加载下拉框 下拉框Name Controller地址Url 下拉框值字段名ValName
 * 下拉框显示字段名ValName-PreName 传入后台的json数据Data 控制此下拉框的值ValControll(值为空则清空此下拉框，否则向后台查询加载加载)
 */
function setSelect4(Name, Url, ValName, PreName, Data, ValControll, ifUnique,
		ifAsync,ifSelect) {
	addLoad(Name);
	/*
	 * $("select[name=" + Name + "]").multiselect('destroy')
	 * .multiselect('rebuild');
	 */
	if (ValControll == "") {
		resetS(Name);
		removeLoad(Name);
		return;
	}
	var $ifasync = true;
	if (ifAsync == false) {
		$ifasync = false;
	}
	$.ajax({
		url : ctx + Url,
		type : "GET",
		dataType : "json",
		data : Data,
		async : $ifasync,
		success : function(data) {
			if (data.returnCode !== undefined
					&& "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
			} else {
				console.log(data.message);
				var valData = data.dataSetResult[0].data;
				valData = eval(valData);
				// 是否需要去重
				if (ifUnique == true) {
					uniqueJsonArr(valData, ValName, PreName);
				}
				var dataArr = [];
				if (valData.length != 0) {
					for (var a = 0; a < valData.length; a++) {
						var jsonVal = valData[a];
						dataArr[a] = {
							label : jsonVal[ValName] + "-" + jsonVal[PreName],
							value : jsonVal[ValName]
						}
					}
				}
				if ($("select[name=" + Name + "]").attr('multiple') == "multiple") {

				} else {
					if(ifSelect!=true){
						dataArr.unshift({
							label : '请选择',
							value : ''
						});
					}
					
				}

				$("select[name=" + Name + "]").multiselect(
						'dataprovider', dataArr);

				$("select[name=" + Name + "]").multiselect('rebuild');

			}
			removeLoad(Name);
		}
	});

	/*
	 * $.get(ctx + Url, Data, function(data) { if (data.returnCode !== undefined &&
	 * "0000" != data.returnCode) { var errMsg = "错误信息[" + data.message + "]";
	 * showContent(errMsg, "error"); } else { console.log(data.message); var
	 * valData = data.dataSetResult[0].data; valData = eval(valData); // 是否需要去重
	 * if (ifUnique == true) { uniqueJsonArr(valData, ValName, PreName); } var
	 * dataArr = []; if (valData.length != 0) { for (var a = 0; a <
	 * valData.length; a++) { var jsonVal = valData[a]; dataArr[a] = { label :
	 * jsonVal[PreName], value : jsonVal[ValName] } } } if ($("select[name=" +
	 * Name + "]").attr('multiple') == "multiple") { } else { dataArr.unshift({
	 * label : '请选择', value : '' }); }
	 * 
	 * $("select[name=" + Name + "]").multiselect( 'dataprovider', dataArr);
	 * 
	 * $("select[name=" + Name + "]").multiselect( 'rebuild'); }
	 * removeLoad(Name); }, "json");
	 */
}

/*
 * 刚进页面直接调接口加载下拉框，不需要传值 原生下拉框 下拉框Name Controller地址Url 下拉框值字段名ValName
 * 下拉框显示字段名PreName
 */
function setSelectJ1(Name, Url, ValName, PreName, ifUnique) {
	var selectTip = "<option value=''>请选择</option>"
	$("select[name=" + Name + "]").empty();
	$("select[name=" + Name + "]").append(selectTip);
	$.get(ctx + Url, {
	// TODO

	}, function(data) {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			var valData = data.dataSetResult[0].data;
			valData = eval(valData);
			// 是否需要去重
			if (ifUnique == true) {
				uniqueJsonArr(valData, ValName, PreName);
			}
			var dataArr = [];
			if (valData.length != 0) {
				for (var a = 0; a < valData.length; a++) {
					var jsonVal = valData[a];
					var optionAdd = "<option value='" + jsonVal[ValName] + "'>"
							+ jsonVal[PreName] + "</option>";
					$("select[name=" + Name + "]").append(optionAdd);
				}
			}

		}
	}, "json");
}

/*
 * 级联下拉框，传入data往后台获取值加载下拉框 原生下拉框 下拉框Name Controller地址Url 下拉框值字段名ValName
 * 下拉框显示字段名PreName 传入后台的json数据Data 控制此下拉框的值ValControll(值为空则清空此下拉框，否则向后台查询加载加载)
 */
function setSelectJ2(obj, Url, ValName, PreName, Data, ValControll, ifUnique) {
	var selectTip = "<option value=''>请选择</option>"
	obj.empty().append(selectTip);
	if (ValControll == "") {
		return;
	}

	$.get(ctx + Url, Data, function(data) {
		if (data.returnCode !== undefined && "0000" != data.returnCode) {
			var errMsg = "错误信息[" + data.message + "]";
			showContent(errMsg, "error");
		} else {
			console.log(data.message);
			var valData = data.dataSetResult[0].data;
			valData = eval(valData);
			// 是否需要去重
			if (ifUnique == true) {
				uniqueJsonArr(valData, ValName, PreName);
			}
			var dataArr = [];
			if (valData.length != 0) {
				for (var a = 0; a < valData.length; a++) {
					var jsonVal = valData[a];
					var optionAdd = "<option value='" + jsonVal[ValName] + "'>"
							+ jsonVal[PreName] + "</option>";
					obj.append(optionAdd);
				}
			}

		}
	}, "json");
}

// 设置input不可输
function disabledI(Id) {
	$("#" + Id).attr('disabled', 'true');
}

// 设置下拉框不可输
function disabledS(name) {
	$("select[name=" + name + "]").multiselect('disable')
			.multiselect('refresh');
}

// 设置input可输入
function enableI(Id) {
	$("#" + Id).removeAttr('disabled');
}

// 设置下拉框可输
function enableS(name) {
	$("select[name=" + name + "]").multiselect('enable');
}

// 设置一个id内的所有input和下拉不可用
function disDiv(Id) {
	$('#' + Id).find(':input').not(":button, :submit, :reset").attr('disabled',
			'true');
	$('#' + Id).find("select[data-role='multiselect']").multiselect('disable').multiselect('refresh');
	$('#' + Id).find('textarea').attr('disabled', 'true');
	$('#' + Id).find('select').attr('disabled', 'true');
}

// 日期去-
function dateDelete(dateVal) {
	if (dateVal == "" || undefined==dateVal) {
		return dateVal;
	} else {
		return $.trim(dateVal.split('-').join(''));
	}

}

// 日期加-
function dateAdd(dateVal) {
	if (dateVal == "" || undefined==dateVal) {
		return dateVal;
	} else {
		return dateVal.slice(0, 4) + "-" + dateVal.slice(4, 6) + "-"
				+ dateVal.slice(6, 8);
	}

}
// 时间去:
function timeDelete(dateVal) {
	if (dateVal == "" || undefined==dateVal) {
		return dateVal;
	} else {
		return dateVal.split(':').join('');
	}

}

// 时间加:
function timeAdd(dateVal) {
	if (dateVal == "" || undefined==dateVal) {
		return dateVal;
	} else {
		return dateVal.slice(0, 2) + ":" + dateVal.slice(2, 4) + ":"
				+ dateVal.slice(4, 6);
	}

}



// 给元素加加载标志
function addLoad(ID) {
	var loadImg = '<div class="imgDiv"></div>';
	$('#' + ID).parent().after(loadImg);
}
// 去除元素身上的加载标志
function removeLoad(ID) {
	$('#' + ID).parents("[class*='col-sm']").find('[class="imgDiv"]').remove();
}

// 页面取消异步
function cancelAsync() {
	$.ajaxSetup({
		async : false
	// 取消异步
	});
}
// 改为异步
function setAsync() {
	$.ajaxSetup({
		async : false
	// 取消异步
	});
}
/*
 * $.ajaxSetup({ async : false //取消异步 });
 */

// 下拉框控制div显隐，并且重置里面的内容
function selectDiv(sId, val, divId) {
	$('#' + sId).change(function() {
		if (getS(sId) == val) {
			$('#' + divId).show();
		} else {
			$('#' + divId).hide();
			resetForm(divId);
		}
	});
	$('#' + sId).change();
}

// 预览界面文字变蓝
function previewPage(Id) {
	/* reviceby qiu */
	// 预览部分span内容有分号的变蓝
	$('#' + Id)
			.find('span')
			.each(
					function() {
						var $this = $(this);
						var text = $this.text();
						var blank = /\s/g;
						text = text.replace(blank, '');

						if (text.split("：").length > 1
								&& text.split("：")[1] != "" && text.split("：")[1] != "请选择" && text.split("：")[1] != "--请选择--") {
							var title = text.split("：")[0];
							var content = text.split("：")[1];

							var newTemp = '<a  type="button" contenteditable="false"  style="text-decoration: none;">'
									+ content + '</a>';
							$this.html("<label '>" + title + "：</label>"
									+ newTemp);
						} else {
							$this.parent('div').addClass('hide');
						}
					});
}

/**
 * table新增一行 传入数据json
 */
function tableAddRow(tableId, data, serName) {
	console.info('新增行');
	var index = $('#' + tableId).bootstrapTable('getData').length;
	
	if (serName!=null &&serName!=undefined) {
		data[serName] = index+1;
	}
	$('#' + tableId).bootstrapTable('insertRow', {
		index : index,
		row : data
	});
	$('#' + tableId).bootstrapTable('resetView');
	//showTip("新增成功!", "success");
}

/**
 * table修改一行 传入数据json
 */
function tableReviceRow(tableId, data, serName) {
	console.info('修改行');
	var $table = $('#' + tableId);
	// 所选择的行序号
	var $sRowIndex = getSRowIndex(tableId);

	if ($sRowIndex != -1) {
		// 获取table所有数据返回json数组
		var dataArr = $table.bootstrapTable('getData');
		

		if (serName!=null &&serName!=undefined) {
			/*for (var a = 0; a < dateArr.length; a++) {
				dataArr[a][serName] = a + 1;
			}*/
			data[serName]=dataArr[$sRowIndex][serName];
		}
		// 修改此行对应数据
		dataArr[$sRowIndex] = data;

		// 重新加载table
		$table.bootstrapTable('load', dataArr);
		$table.find('tbody tr').eq($sRowIndex).click();
		showTip("修改成功!", "success");
		
		return true;
	}
}

/**
 * 初始化table table点击行变色 给此行增加selected属性,只能用来上移下移
 * 注意：用事件捕获来添加事件，方便通过添加的属性来做内部元素的事件操作?如何给未来元素加？
 */
function tableClick(tableId) {
	/*
	 * var $tr = $('#'+tableId);
	 * document.getElementsByTagName("tr").addEventListener("click",function(){
	 * $(this).addClass('Selected').siblings().removeClass('Selected');
	 * $(this).siblings().find('td').removeClass(' tableTrC');
	 * $(this).find('td').addClass('tableTrC'); },true);
	 */
	$('#' + tableId).on('click', "tbody tr", function() {
		$(this).addClass('Selected').siblings().removeClass('Selected');
		$(this).siblings().find('td').removeClass('tableTrC');
		$(this).find('td').addClass('tableTrC');
	});
}

/**
 * 获取选中行的行序号
 */
function getSRowIndex(tableId) {
	// 被选中的行
	var $SRow = $('#' + tableId).find('tr.Selected');
	if ($SRow) {
		// 选中行在所有行中的序号
		var $SRowIndex = $SRow.index();
		return $SRowIndex;
	} else {
		return -1;
	}

}

/**
 * 获取选中行的数据json
 */
function getSRowData(tableId) {
	// 选中行的序号
	var $SRowIndex = getSRowIndex(tableId);

	if ($SRowIndex == -1) {
		return {};
	} else {
		// 获取table所有数据返回json数组
		var dataArr = $('#' + tableId).bootstrapTable('getData');
		// 返回此行的数据
		return dataArr[$SRowIndex];
	}
}

/**
 * 删除 删除元素对象所在table的数据 删除此行
 */
function deleteRow(obj,callBackFunc,serName) {
	$table = $(obj).parents('table');
	// 此函数的对象所在行的序号
	var $rowIndex = $(obj).parents('tbody tr').index();
	// 获取table所有数据返回json数组
	var dataArr = $table.bootstrapTable('getData');
	// 从数组中去除此行数据
	dataArr.splice($rowIndex, 1);
	
	if (serName!=null && serName!=undefined) {
		// 需要重排序
		/* 全部数据重新设置序号 */
		for (var a = 0; a < dataArr.length; a++) {
			dataArr[a][serName] = a + 1;
		}
	}
	
	// 重新加载此table数据
	$table.bootstrapTable('load', dataArr);

	showTip("删除成功!", "success");
	if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
		callBackFunc();
	}
	
	/* add by weizhj 20190718 */
	if(dataArr.length > 0){
		return true;
	}else {
		return false;
	}
}

/**
 * 修改 修改元素对象所在table的数据 返回此行的数据
 */
function reviceRowData(obj) {
	$table = $(obj).parents('table');
	// 此函数的对象所在行的序号
	var $rowIndex = $(obj).parents('tbody tr').index();
	// 获取table所有数据返回json数组
	var dataArr = $table.bootstrapTable('getData');
	// 返回此行的数据
	return dataArr[$rowIndex];
}

/**
 * 上移 所选择的行 取出全部数据 先删除再插入加载回去
 */
function tableUpSort(tableId, serName ,callBackFunc) {
	console.info('上移');
	var $table = $('#' + tableId);
	// 所选择的行序号
	var $sRowIndex = getSRowIndex(tableId);

	if ($sRowIndex != 0 && $sRowIndex != -1) {
		// 所选择行的数据
		var $sRowData = getSRowData(tableId);
		// 获取table所有数据返回json数组
		var dataArr = $table.bootstrapTable('getData');

		// 先删除，再插入
		dataArr.splice($sRowIndex, 1);
		dataArr.splice($sRowIndex - 1, 0, $sRowData);

		if (null != serName && undefined != serName) {
			// 需要重排序
			/* 全部数据重新设置序号 */
			/*
			 * for(var a=0;a<dataArr.length;a++){ dataArr[a][serName] = a+1; }
			 */
			// 上移此位置内容
			var upVal = dataArr[$sRowIndex - 1][serName];
			// 被替换位置内容
			var upValB = dataArr[$sRowIndex][serName];
			// 交换
			dataArr[$sRowIndex - 1][serName] = upValB;
			dataArr[$sRowIndex][serName] = upVal;
		}

		// 重新加载table
		$table.bootstrapTable('load', dataArr);
		$table.find('tbody tr').eq($sRowIndex - 1).click();
		showTip("上移成功!", "success");
		
		if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
			callBackFunc();
		}
	}
}

/**
 * 下移 所选择的行 取出全部数据 先删除再插入加载回去
 */
function tableDownSort(tableId, serName ,callBackFunc) {
	console.info('下移');
	var $table = $('#' + tableId);
	// 所选择的行序号
	var $sRowIndex = getSRowIndex(tableId);

	if ($sRowIndex != index - 1 && $sRowIndex != -1) {
		// 所选择行的数据
		var $sRowData = getSRowData(tableId);
		// 获取table所有数据返回json数组
		var dataArr = $table.bootstrapTable('getData');

		// table总条数
		var index = $table.bootstrapTable('getData').length;

		// 先删除，再插入
		dataArr.splice($sRowIndex, 1);
		dataArr.splice($sRowIndex + 1, 0, $sRowData);

		if (null != serName && undefined != serName) {
			// 需要重排序
			/* 全部数据重新设置序号 */
			// 下移此位置内容
			var downVal = dataArr[$sRowIndex + 1][serName];
			// 被替换位置内容
			var downValB = dataArr[$sRowIndex][serName];
			// 交换
			dataArr[$sRowIndex + 1][serName] = downValB;
			dataArr[$sRowIndex][serName] = downVal;
		}

		// 重新加载table
		$table.bootstrapTable('load', dataArr);
		$table.find('tbody tr').eq($sRowIndex + 1).click();
		showTip("下移成功!", "success");
		
		if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
			callBackFunc();
		}
	}
}

// 下拉选择框控制下拉框
// Num为第几个选项，从1开始数
function selectSelectVal(sName1, sName2, val) {
	$("select[name=" + sName1 + "]").change(function() {
		if (getS(sName1) == val) {
			showSelect(sName2);
		} else {
			resetSelect(sName2);
			hideSelect(sName2);
			$("select[name=" + sName2 + "]").change();
		}
	});
	$("select[name=" + sName1 + "]").change();
}

// 下拉选择框控制下拉框
// Num为第几个选项，从1开始数
function selectInputVal(sName1, iName2, val) {
	$("select[name=" + sName1 + "]").change(function() {
		if (getS(sName1) == val) {
			showInput(iName2);
		} else {
			hideInput(iName2);
			resetInput(iName2);
		}
	});
	$("select[name=" + sName1 + "]").change();
}

// 页面回到顶部
function goTop() {
	$(document).scrollTop(0);
}

//打开jBox 业务编号
function busiClick(busiNo, busiName, entrNo, callBackFunc) {
	// 正常打开
	top.$.jBox
			.open(
					"iframe:"+ctx+"/tag/treeselect?url="
							+ encodeURIComponent($("#" + busiNo).attr(
									"search_url")),
					"选择业务编号",
					300,
					420,
					{
						ajaxData : {
							selectIds : ""
						},
						buttons : {
							"确定" : "ok",
							"清除" : "clear",
							"关闭" : true
						},
						submit : function(v, h, f) {
							if (v == "ok") {
								var tree = h.find("iframe")[0].contentWindow.tree;// h.find("iframe").contents();
								var ids = [], names = [], nodes = [];
								if ("" == "true") {
									nodes = tree.getCheckedNodes(true);
								} else {
									nodes = tree.getSelectedNodes();
								}
								for (var i = 0; i < nodes.length; i++) {//
									ids.push(nodes[i].id1);
									names.push(nodes[i].name);//
									names.push(nodes[i].name3);//
									break; // 如果为非复选框选择，则返回第一个选择
								}

								$("#" + busiNo).val(nodes[0].BUSI_NO);
								if (undefined != busiName && null != busiName) {
									$("#" + busiName).val(nodes[0].BUSI_NAME);
								}

								if (undefined != entrNo && null != entrNo) {
									if($("#"+entrNo).next().length>0 && $("#"+entrNo).next().attr("class").match("btn-group")){
										//产品创新平台修改了接口url
										//setSelect2(entrNo, "/comp/prod/oper/entrManage/entrSignData",  "ENTR_NO", "ENTR_NAME", {BUSI_NO:nodes[0].BUSI_NO}, nodes[0].BUSI_NO, false,false);
										setSelect2(entrNo, "/prod/oper/busiDemo/entrSignData",  "ENTR_NO", "ENTR_NAME", {BUSI_NO:nodes[0].BUSI_NO}, nodes[0].BUSI_NO, false,false);
									}else{
										//setSelectJ2($("#"+entrNo), "/comp/prod/oper/entrManage/entrSignData", "ENTR_NO", "ENTR_NAME", {BUSI_NO:nodes[0].BUSI_NO}, nodes[0].BUSI_NO, false);
										setSelectJ2($("#"+entrNo), "/prod/oper/busiDemo/entrSignData", "ENTR_NO", "ENTR_NAME", {BUSI_NO:nodes[0].BUSI_NO}, nodes[0].BUSI_NO, false);
									}
									//getEntrNo(nodes[0].BUSI_NO,entrNo)
								}
								
								if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
									callBackFunc();
								}

							} else if (v == "clear") {
								$("#" + busiNo).val("");
								if (undefined != busiName && null != busiName) {
									$("#" + busiName).val("");
								}
								if (undefined != entrNo && null != entrNo) {
									resetS(entrNo);
								}
								if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
									callBackFunc();
								}
							}
							if (typeof companyTreeselectCallBack == 'function') {
								companyTreeselectCallBack(v, h, f);
							}
						},
						loaded : function(h) {
							$(".jbox-content", top.document).css("overflow-y",
									"hidden");

						}
					});
}


//根据业务编号获取代理单位编号加载
function getEntrNo(busiNo,entrNo){
	addLoad(entrNo);
	if (busiNo == "") {
		resetS(entrNo);
		removeLoad(entrNo);
		return;
	}
	// 调用后台交易获取单位编号
	$
			.ajax({
				type : "Post",
				url : ctx
						+ "/comp/sign/pub/entrSignData",
				timeout : 10000,
				async : false,
				data : {
					busiNo : busiNo,
					stat : "0"
				},
				dataType : "json",// 表示后台返回的数据是json对象
				success : function(data) {
					if (data.returnCode == "0500") {
						showContent("代理单位获取"
								+ data.message,
								"error");
					} else {
						var dataArr = [];
						if (data.length != 0) {
							for (var a = 0; a < data.length; a++) {
								var jsonVal = data[a];
								dataArr[a] = {
									label : jsonVal["ENTR_NO"] + "-"
											+ jsonVal["ENTR_NAME"],
									value : jsonVal["ENTR_NO"]
								}
							}
						}
						if ($(
								"select[name="
										+ entrNo
										+ "]")
								.attr(
										'multiple') == "multiple") {

						} else {
							dataArr.unshift({
								label : '请选择',
								value : ''
							});
						}

						$(
								"select[name="
										+ entrNo
										+ "]")
								.multiselect(
										'dataprovider',
										dataArr);
					}
					removeLoad(entrNo);
				},
				error : function(error) {
					alert("error=" + error);
				}
			});
}


//自动为金额输入框补上.00
function changeNum(num){  
     num += '';  
     num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符  
     //去除前面0
     num = num.replace(/^0*/, '');  
     
     if(!/\./.test(num)){
    	 //没有.为整数
    	 num += '.00'; 
     }
     if(/^\./.test(num)){
    	 //最前面为.
    	 num = '0' + num;  
     } 
     //在字符串末尾补零  
     num += '00';       
     //去掉多余的尾数
     num = num.match(/\d+\.\d{2}/)[0];  
     return num;
}

//打开jBox 单位编号
function entrClick(entrNo, entrName, callBackFunc) {
	// 正常打开
	top.$.jBox
			.open(
					"iframe:"+ctx+"/a/tag/treeselect?url="
							+ encodeURIComponent($("#" + entrNo).attr(
									"search_url")),
					"选择单位编号",
					300,
					420,
					{
						ajaxData : {
							selectIds : ""
						},
						buttons : {
							"确定" : "ok",
							"清除" : "clear",
							"关闭" : true
						},
						submit : function(v, h, f) {
							if (v == "ok") {
								var tree = h.find("iframe")[0].contentWindow.tree;// h.find("iframe").contents();
								var ids = [], names = [], nodes = [];
								if ("" == "true") {
									nodes = tree.getCheckedNodes(true);
								} else {
									nodes = tree.getSelectedNodes();
								}
								for (var i = 0; i < nodes.length; i++) {//
									ids.push(nodes[i].id1);
									names.push(nodes[i].name);//
									names.push(nodes[i].name3);//
									break; // 如果为非复选框选择，则返回第一个选择
								}

								$("#" + entrNo).val(nodes[0].ENTR_NO);
								if (undefined != entrName) {
									$("#" + entrName).val(nodes[0].ENTR_NAME);
								}

								if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
									callBackFunc();
								}

							} else if (v == "clear") {
								$("#" + entrNo).val("");
								if (undefined != entrName) {
									$("#" + entrName).val("");
								}
								
								if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
									callBackFunc();
								}
							}
							if (typeof companyTreeselectCallBack == 'function') {
								companyTreeselectCallBack(v, h, f);
							}
						},
						loaded : function(h) {
							$(".jbox-content", top.document).css("overflow-y",
									"hidden");
						}
					});
}



//根据val和name写死下拉框选项
function setSD(Name,name,val) {
	resetS(Name);
	var dataArr = [];
	dataArr[0] = {
			label : name,
			value : val
		}
	$("select[name=" + Name + "]").multiselect(
			'dataprovider', dataArr);
	setS(Name,val);
	disabledS(Name);
}



//公共弹出框组件
function showJboxView(id, nameId, callBackFunc) {
	// 正常打开
	top.$.jBox.open("iframe:"+ctx+"/a/tag/treeselect?url="
			+ encodeURIComponent($("#" + id).attr("search_url")), "选择信息",
			300, 420, {
				ajaxData : {
					selectIds : ""
				},
				buttons : {
					"确定" : "ok",
					"清除" : "clear",
					"关闭" : true
				},
				submit : function(v, h, f) {
					if (v == "ok") {
						var tree = h.find("iframe")[0].contentWindow.tree;// h.find("iframe").contents();
						var ids = [], names = [], nodes = [];
						if ("" == "true") {
							nodes = tree.getCheckedNodes(true);
						} else {
							nodes = tree.getSelectedNodes();
						}
						for (var i = 0; i < nodes.length; i++) {//
							ids.push(nodes[i].id1);
							names.push(nodes[i].name);//
							names.push(nodes[i].name3);//
							break; // 如果为非复选框选择，则返回第一个选择
						}

						$("#" + id).val(nodes[0].KEY_VALUE);
						if (undefined != nameId) {
							$("#" + nameId).val(nodes[0].KEY_DESC);
						}

						if (undefined != callBackFunc && null != callBackFunc
								&& typeof (eval(callBackFunc)) == "function") {
							callBackFunc();
						}

					} else if (v == "clear") {
						$("#" + id).val("");
						if (undefined != nameId) {
							$("#" + nameId).val("");
						}

						if (undefined != callBackFunc && null != callBackFunc
								&& typeof (eval(callBackFunc)) == "function") {
							callBackFunc();
						}
					}
					if (typeof companyTreeselectCallBack == 'function') {
						companyTreeselectCallBack(v, h, f);
					}
				},
				loaded : function(h) {
					$(".jbox-content", top.document)
							.css("overflow-y", "hidden");
				}
			});
}

// 关闭当前页面
function closeBtnToDo() {
	Ewin.confirm({
		title : "操作提示",
		message : "数据会清空，确定关闭吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		top.$.fn.jerichoTab.closeCurrentTab();
	});
}


// 下拉选择框控制input
// 下拉框name 被控制input id 下拉框值数组
function sCi(sName, iId, valList) {
	$("select[name=" + sName + "]").change(function() {
		for(var a=0;a<valList.length;a++){
			if (getS(sName)==valList[a]) {
				showInput(iId);
				break;
			}
			if(a==valList.length-1){
				hideInput(iId);
				resetInput(iId,1);
			}
			
		}
	});
	$("select[name=" + sName + "]").change();
}

//下拉选择框控制下拉框
//下拉框name 被控制下拉框 name 下拉框值数组
function sCs(sName1, sName2, valList) {
	$("select[name=" + sName1 + "]").change(function() {
		for(var a=0;a<valList.length;a++){
			if (getS(sName1)==valList[a]) {
				showSelect(sName2);
				break;
			}
			if(a==valList.length-1){
				resetSelect(sName2);
				hideSelect(sName2);
				$("select[name=" + sName2 + "]").change();
			}
			
		}
	});
	$("select[name=" + sName1 + "]").change();
}

//下拉框控制div
//val为值
function sCd(sName1, dId, valList) {
	$("select[name=" + sName1 + "]").change(function() {
		for(var a=0;a<valList.length;a++){
			if (getS(sName1)==valList[a]) {
				$("#"+dId).show();
				break;
			}
			if(a==valList.length-1){
				$("#"+dId).hide();
				resetForm(dId);
				$("#"+dId).find("input[datetime-skin='twoer']").val("");
			}
		}
	});
	$("select[name=" + sName1 + "]").change();
}

//单选按钮控制div
//val为值
function rCd(rName, dId, valList) {
	$("input[name=" + rName + "]").change(function() {
		if(getB(rName) == ""){
			$("#"+dId).hide();
			resetForm(dId,"noReadonly");
			$("#"+dId).find("input[datetime-skin='twoer']").val("");
			return;
		}
		for(var a=0;a<valList.length;a++){
			if (getB(rName)==valList[a]) {
				$("#"+dId).show();
				break;
			}
			if(a==valList.length-1){
				$("#"+dId).hide();
				resetForm(dId,"noReadonly");
				$("#"+dId).find("input[datetime-skin='twoer']").val("");
			}
		}
	});
	$("input[name=" + rName + "]").change();
}

function rCr(rName, iName, valList) {
	$("input[name=" + rName + "]").change(function() {
		if(getB(rName) == ""){
			hideCheckbox(iName);
			resetCheckbox(iName);
			$("input[name=" + iName + "]").change();
			return;
		}
		for(var a=0;a<valList.length;a++){
			if (getB(rName)==valList[a]) {
				showCheckbox(iName);
				break;
			}
			if(a==valList.length-1){
				hideCheckbox(iName);
				resetCheckbox(iName);
				$("input[name=" + iName + "]").change();
			}
		}
	});
	$("input[name=" + rName + "]").change();
}


//单选按钮控制input
//val为值
function rCi(rName, iName, Val) {
	$("input[name=" + rName + "]").change(function() {
		if ($("input[name=" + rName + "]").is(":checked") && $("input[name=" + rName + "]:checked").val()==Val) {
			showInput(iName);
		} else {
			hideInput(iName);
			resetInput(iName,1);
		}
	});
	$("input[name=" + rName + "]").change();
}


//单选按钮控制div
//val为值
/*function rCd(rName, dId, Val) {
	$("input[name=" + rName + "]").change(function() {
		if ($("input[name=" + rName + "]").is(":checked") && $("input[name=" + rName + "]:checked").val()==Val) {
			$("#"+dId).show();
		} else {
			$("#"+dId).hide();
			resetForm(dId,"noDisabled");
		}
	});
	$("input[name=" + rName + "]").change();
}*/


//选中下拉框第一个，没有则选择第一个
/*function setSF(Name){
	var optArr = $("select[name=" + Name + "]").find('option');
	for()
	
	$("select[name=" + Name + "]").multiselect('select', Val).multiselect(
	'refresh');
}*/


/*
 * 刚进页面加载input框
 */
function setInput(Id, Url, ValName,ifAsync,Data,callBackFunc) {
	resetInput(Id,"1");
	var $ifasync = true;
	if (ifAsync == false) {
		$ifasync = false;
	}
	
	
	if(undefined!=Data){
		for(var a in Data){
			if(Data[a]==""){
				return;
			}
		}
	}else{
		Data = {};
	}
	
	$.ajax({
		url : ctx + Url,
		type : "GET",
		dataType : "json",
		data : Data,
		async : $ifasync,
		success : function(data) {
			if (data.returnCode !== undefined && "0000" != data.returnCode) {
				var errMsg = "错误信息[" + data.message + "]";
				showContent(errMsg, "error");
				
				if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
					callBackFunc();
				}
				
			} else {
				console.log(data.message);
				var valArr = eval(data.dataSetResult[0].data);
				if(valArr.length>0){
					valData = eval(valArr[0]);
					setI(Id,valData[ValName]);
				
					if(undefined != callBackFunc && null != callBackFunc && typeof(eval(callBackFunc)) == "function"){
						callBackFunc();
					}
				}
				
			}
		}
	});
	
	
}


/*zTree部分*/
//ztree参数设置
var setting = {
		view: {
			selectedMulti: false,
			/*fontCss:{
				"font-size":"17px"   //样式只加到span的上层标签<a>中，span负责继承，全局.tree*优先级更高
			}*/                      
			/*addHoverDom: addHoverDom,   //鼠标移到节点上时，显示用户自定义控件
			removeHoverDom: removeHoverDom,   //移除节点事件
			*/		
		},
		edit: {
			enable: true,
			showRemoveBtn: showRemoveBtn,
			showRenameBtn: showRenameBtn,
			removeTitle: "删除节点",
			renameTitle: "回显数据" 
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeDrag: false,   //拖拽前事件
			beforeRemove: beforeRemove,   //删除前事件
			onRemove: onRemove, 		//删除后事件
			
			beforeEditName: beforeEditName,    //编辑前事件，进入编辑状态之前
			beforeRename: beforeRename,   //重命名前事件，blur之后
			onRename: onRename				//重命名后事件
		}
	};

//是否显示移除按钮
function showRemoveBtn(treeId, treeNode) {
	if(treeNode.ifRoot==true){
		return false;
	}else{
		return true;
	}
}
//是否显示编辑回显按钮
function showRenameBtn(treeId, treeNode) {
	if(treeNode.ifRoot==true){
		return false;
	}else{
		return true;
	}
}

//删除前事件
//给出提示确认是否删除
function beforeRemove(treeId, treeNode) {
	if(typeof goTop === "function"){
		goTop();
	}
	if(typeof parent.goTop === "function"){
		parent.goTop();
	}
	
	Ewin.confirm({
		title : "操作提示",
		message : "确定删除节点吗？"
	}).on(function(e) {
		if (!e) {
			return;
		}
		//ztree对象
		var zTree = $.fn.zTree.getZTreeObj(treeId);   
		//删除此节点
		zTree.removeNode(treeNode);
		showTip("删除成功!", "success");
		
	});
	return false;   //次函数会先执行这里，为什么？
	/*return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");*/
}
function onRemove(e, treeId, treeNode) {
	return false;
}

//进入编辑状态前事件
function beforeEditName(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);   
	zTree.selectNode(treeNode);   //选中指定节点，参数：节点数据，是否追加选中，默认false选中滚到可视区域
	setTimeout(function() {
		/*if (confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？")) {
			setTimeout(function() {
				zTree.editName(treeNode);   //设置某点处于可编辑状态
			}, 0);
		}*/
		//修改设置值的函数（在对应页面自己写）
		setReviceNodeVal(treeNode);
		showTip("数据回显成功!", "success");
	}, 0);
	return false;
}
//编辑后根据返回值判断编辑是否生效
function beforeRename(treeId, treeNode, newName, isCancel) {
	/*if (newName.length == 0) {
		setTimeout(function() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.cancelEditName();    //取消节点编辑状态还原名称
			alert("节点名称不能为空.");
		}, 0);
		return false;
	}*/
	return true;
}
//编辑后事件
function onRename(e, treeId, treeNode, isCancel) {
	showTip("数据回显成功!", "success");
}


//节点增加事件函数
function addNode(id,data,ifRoot) {
	var zTree = $.fn.zTree.getZTreeObj(id),
	/*isParent = e.data.isParent,*/    //默认新的是父节点
	nodes = zTree.getSelectedNodes(),    //获取当前被选中的节点数据集合
	treeNode = nodes[0];
	
	//节点样式
	var commonData={
		iconSkin:"pIcon01"
	}
	$.extend(commonData,data);
	
	if (treeNode) {
		console.log(treeNode.tId);
		console.log(treeNode.level);
		console.log(treeNode.parentTId);
		console.log(treeNode.id);
		data.pId = treeNode.id;
		//增加节点
		treeNode = zTree.addNodes(treeNode, commonData);
	} else {
		if(ifRoot==true){
			//有初始化根节点
			showTip("请选择节点进行增加!", "success");
			return;
		}else{
			data.pId = null;
			treeNode = zTree.addNodes(null, commonData);  //增加节点，参数：父节点（null为根），[插入位置]，节点json数据，[增加节点后是否展开父节点]
		}
		
	}
	showTip("增加成功!", "success");
};

//节点修改事件函数
function reviceNode(id,data,ifRoot){
	var zTree = $.fn.zTree.getZTreeObj(id),
	nodes = zTree.getSelectedNodes(),    //获取当前被选中的节点数据集合
	treeNode = nodes[0];
	
	/*treeNode.id=getI("NO");
	treeNode.name=getI("NAME");
	treeNode.money=getI("MONEY");*/
	for(var a in data){
		treeNode[a] = data[a];
	}
	
	if(treeNode){
		if(ifRoot==true){
			//是否存在根节点
			if(treeNode.ifRoot==true){
				showTip("根节点不可修改!", "success");
				return;
			}
		}
		zTree.updateNode(treeNode);
		showTip("修改成功!", "success");
	}else{
		showTip("请先选择数据!", "success");
	}
}

//初始化ztree
//ztree初始域id，加载数据，是否有初始根节点
function intialZtree(id,data,ifRoot){
	if(undefined==data || null == data){
		var data = [];
	}else{
		data = eval(JSON.stringify(data));
		for(var a=0;a<data.length;a++){
			//加上图标样式
			data[a].iconSkin="pIcon01";
		}
	}
	
	if(ifRoot==true){
		//根节点
		var rootData=[{
			name:"",
			id:"ROOT",
			pId:null,
			iconSkin:"pIcon01",
			ifRoot:true
		}];
		data = data.concat(rootData);
	}
	
	$.fn.zTree.init($("#"+id), setting,data);
	
}


//加载组件号
function setCompNoS(Id,IfAsync,IfAll){
	var ifAsync = true;
	if(IfAsync==false){
		ifAsync = false;
	}
	if(IfAll == true){
		setSelect2(Id, "/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME", {Flg:"All"}, "All", null,ifAsync);
	}else{
		setSelect1(Id,"/comp/ctrl/oper/svccode/getModlNo","COMP_NO","COMP_NAME",null,ifAsync);
	}
}

//组件号change加载服务码
function setSvcCodeS(Id1,Id2,IfAsync){
	var ifAsync = true;
	if(IfAsync==false){
		ifAsync =false
	}
	$('#'+Id1).change(function(){
		var Data={
				COMP_NO:getS(Id1)
			}
		setSelect2(Id2,"/comp/ctrl/oper/svccode/getSvcCode","SVC_CODE","SVC_DESC",Data,Data.COMP_NO,null,ifAsync);
		$('#'+Id2).change();
	});
}

/**
 * 校验只可输入数字
 * @param value
 * @returns {*}
 */
function regexInt(value) {
	value = value.replace(/[^\d]/g,'');
	return value;
}