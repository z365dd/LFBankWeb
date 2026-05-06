/*revice by chenyl for
给未来动态生成元素加校验事件
有校验属性的当前对象绑定事件，不用id绑定事件（避免同一个页面的重复id）*/

/* 
	time:20171219
	author:
	Effect:获取属性是deta-key input的val
*/


console.log("checkReviceObj");

/*获取当前时间*/

$(this).hover(function(){
		$("[data-toggle='tooltip']").tooltip();
})

function hong(){
	$("input").keyup(function(){
		if($(this).attr("data-toggle") == "tooltip"){
			if($(this).val().length != "0"){
				$("input[data-toggle='tooltip']").css('border-color','#9EBED7');
			}
		}
});
}





function inputcheck (self){
	var _self = self;
	/* 获取属性值 */
	input_check ={};
			/* 不为空 */
			input_check.notEmpty = $(_self).val(),
			/* 电话号码 */
			input_check.telephone = $(_self).val(),
			/* 固定电话号码 */
			input_check.telephone1 = $(_self).val(),
			/* 身份证 */
			input_check.idCard = $(_self).val(),
			/* 中文 */
			input_check.chinese = $(_self).val(),
			/* IP地址 */
			input_check.ipAddress = $(_self).val(),
			/* 版本 */
			input_check.edition = $(_self).val(),
			/* 英文 */
			input_check.english = $(_self).val(),
			/* 邮件 */
			input_check.mail = $(_self).val(),
			/* 数字和英文 */
			input_check.NAL = $(_self).val(),
			/* 数字和英文还有字符 */
			input_check.character = $(_self).val(),
			/* 密码 */
			input_check.password = $(_self).val(),
			/* 空格 */
			input_check.blank = $(_self).val(),
			/* 整数 */
			input_check.integer = $(_self).val(),
			/* 正常开始时间验证 */
			input_check.startTime_one = $(_self).val(),
			/* 结束时间验证 */
			input_check.endTime_one = $(_self).val()
			
}

$(function (){
	
	
	
	/* 添加红字提示 */
	/*revice by qiu 把id改为obj，避免页面重复校验出错*/
	function Prompt (tipVal ,thisObject){
		var script = tipVal;
		if($(thisObject).parent().next("span.warnBlock").length == 0){
			var tag= "<span class='warnBlock'>"+script+"</span>";
			$(thisObject).parent().after(tag);
			$(thisObject).removeClass('trueInput');
		};
	}
	/*revice by qiu 把id改为obj，避免页面重复校验出错*/
	function lengtPrompt (tipVal ,thisObject){
		var script = tipVal;
		$(thisObject).parent().next("span.warnBlock").remove();
		if($(thisObject).parent().next("span.warnBlock").length == 0){
			var tag= "<span class='warnBlock'>"+script+"</span>";
			$(thisObject).parent().after(tag);
			$(thisObject).removeClass('trueInput');
		};
	}
	
	
	/*revice by qiu obj*/
	/* 添加灰色提示 */
	var tag;
	function greyHints(tipVal,thisObject){
		var script = tipVal;
		if(tag == "<span class='promptScript'>输入不能为空</span>"){
			 tag= "<span class='promptScript'>"+script+' '+"</span>";
			 $(thisObject).parent().after(tag);
		}else{
			if($(thisObject).parent().next("span.promptScript").length == 0){
				 tag= "<span class='promptScript'>"+script+"</span>";
				 $(thisObject).parent().after(tag);
			};
		}
		
	}
	/*revice by qiu obj*/
	function lengthHints(tipVal,thisObject){
		var script = tipVal;
		$(thisObject).parent().next("span.promptScript").remove();
		if($(thisObject).parent().next("span.promptScript").length == 0){
			var tag= "<span class='promptScript'>"+script+"</span>";
			$(thisObject).parent().after(tag);
		};
	}
	
	/*revice by qiu去掉id参数，self对象里面包含了所有信息了呀?为什么还要id？*/
	function cue(self){
		console.log("校验代码读取");
		var tipVal;
		
		/* 验证input是否为空 */
		if($(self).attr("check-empty") == "true" ){
			var tipVal = '输入不能为空'
			greyHints(tipVal,self);
		};
		
		/* 判断电话号码 */
		if($(self).attr("check-telephone") == "true" ){
			var tipVal = '请输入正确的电话号码'
			greyHints(tipVal,self);
			
		};
		/* 判断固定电话号码 */
		if($(self).attr("check-telephone1") == "true" ){
			var tipVal = '请输入正确的电话号码'
				greyHints(tipVal,self);
			
		};
		
		/* 身份证号码 */
		if($(self).attr("check-idCard") == "true" ){
			var tipVal = '请输入正确的身份证号码'
			greyHints(tipVal,self);
		};
		/* 验证中文 */
		if($(self).attr("check-chinese") == "true" ){
			var tipVal = '请输入中文'
			greyHints(tipVal,self);
		};
		/* 验证IP地址 */
		if($(self).attr("check-ipAddress") == "true" ){
			var tipVal = '请输入正确的ip地址'
			greyHints(tipVal,self);
		};
		/* 验证版本 */
		if($(self).attr("check-edition") == "true" ){
			var tipVal = '请输入类似1.0或1.0.1的版本格式'
			greyHints(tipVal,self);
		};
		/* 验证英文 金额币种 */
		if($(self).attr("check-english") == "true" ){
			tipVal= "只能输入大写英文";
			greyHints(tipVal,self);
		};
		/* 验证邮件 */
		if($(self).attr("check-mail") == "true" ){
			var tipVal = '请输入正确的邮箱'
			greyHints(tipVal,self);
		};
		/* 验证数字和英文 */
		if($(self).attr("check-NAL") == "true" ){
			var tipVal = '只能输入数字和大小写字母'
			greyHints(tipVal,self);
		};
		/* 验证英文、数字、特殊字符 */
		if($(self).attr("check-character") == "true" ){
			var tipVal = '只能输入数字和字母和部分特殊符号'
			greyHints(tipVal,self);
		};
		/* 验证密码 */
		if($(self).attr("check-password") == "true" ){
			var tipVal = '请输入非中文字符,长度在6~18之间'
			greyHints(tipVal,self);
		};
		/* 验证空格 */
		if($(self).attr("check-blank") == "true" ){
			var tipVal = '不能输入空格'
			greyHints(tipVal,self);
		};
		/* 验证非零的正整数 */
		if($(self).attr("check-integer") == "true" ){
			var tipVal = '请输入非零的正整数'
			greyHints(tipVal,self);
		};
		
		/* 验证请输入3到10*/
		if($(self).attr("check-TtoT") == "true" ){
			var tipVal = '请输入3到10的数字'
			greyHints(tipVal,self);
		};
		
		/* 验证请输入3到10 */
		if($(self).attr("check-OtoF") == "true" ){
			var tipVal = '请输入1-5的数字'
			greyHints(tipVal,self);
		};
		
		/* 验证请输入1到5 */
		if($(self).attr("check-TtoT") == "true" ){
			var tipVal = '请输入3到10'
			greyHints(tipVal,self);
		};
		
		/* 验证正常开始时间 */
		if($(self).attr("check-startTime-one") == "true" ){
			var tipVal = '开始时间要小于结束时间'
			greyHints(tipVal,self);
		};
		/* 验证结束时间 */
		if($(self).attr("check-endTime-one") == "true" ){
			var tipVal = '开始时间要小于结束时间'
			greyHints(tipVal,self);
		};
		
		/* 验证正常开始时间   开始时间大于当前时间  add qiu*/
		/*if($(self).attr("check-startTime") == "true" ){*/
		if($(self).is("[check-startTime]")){
			var tipVal = '时间要大于当前时间'
			greyHints(tipVal,self);
		};
		/* 验证结束时间  结束时间不小于开始时间   add qiu   */
		/*if($(self).attr("check-endTime") == "true" ){*/
		if($(self).is("[check-endTime]")){
			var attrVal = $(self).attr('check-endTime');
			if($("[check-startTime='"+attrVal+"']").val()==""){
				var tipVal = '请先选择开始时间'
				greyHints(tipVal,self);
			}else{
				var tipVal = '结束时间不小于开始时间'
				greyHints(tipVal,self);
			}
			
		};
		
		/* 验证中文数字横线中括号小括号 */
		if($(self).attr("check-chineseDigitalSymbols") == "true" ){
			var tipVal = '只能输入中文、数字、横线、中括号、小括号'
			greyHints(tipVal,self);
		};
		
		/* 验证英文数字下划线 */
		if($(self).attr("check-alphanumericSymbols") == "true" ){
			var tipVal = '只能输入英文、数字、下划线'
			greyHints(tipVal,self);
		};
		/* 验证部分特殊符号 */
		if($(self).attr("check-partCharacter") == "true" ){
			var tipVal = '请勿输入单引、双引号、以及斜线'
			greyHints(tipVal,self);
		};
		
		/* 验证时间不能为空 */
		if($(self).attr("check-time-empty") == "true" ){
			var tipVal = '不能为空'
			greyHints(tipVal,self);
		};
		
		/* 校验整数 正负和0  add qiu */
		if($(self).attr("check-integer-pos-neg") == "true" ){
			var tipVal = '请输入整数'
			greyHints(tipVal,self);
		};
		
		/* 校验金额 add qiu */
		if($(self).attr("check-money") == "true" ){
			var tipVal = '请输入金额'
			greyHints(tipVal,self);
		};
		return tipVal;
	}
	
	
	

	/* 获得焦点 */
	//$("input,textarea").focus(function(){
	$(document).on("focus","input,textarea",function(){
		var self = this;
		/*避免用id，页面重复（copy页面元素）revice by qiu*/
		//var nameId = $(self).attr("id");
	  	/*最大长度*/
	  	var _maxlength = $(self).attr("maxlength");
	  	/*最小长度*/
		var _min = $(self).attr("check-minlength");
		inputcheck(self);
		$(self).removeClass('trueInput');
		$(self).parent().siblings('span.warnBlock').remove();
		
		if(_maxlength != undefined || _min != undefined){
			var tipVal =  cue(self);
			/*判断tipVal是否为空*/
			if(tipVal != undefined){
				var tipVal = tipVal+'，';
			}else{
				var tipVal = '';
			};
			/* 最大长度提示 */
			if(_maxlength != undefined ){
				if(_min != undefined){
					var tipVal = tipVal+'请输入'+_min+"至"+_maxlength+"长度之间";
					lengthHints(tipVal,self);
				}else{
					var tipVal =  tipVal+'不超过'+_maxlength+"个字符";
					lengthHints(tipVal,self);
				}
			}else
			/* 最小长度提示 */
			if( _min != undefined ){
				if(_maxlength != undefined){
					var tipVal =  tipVal+'请输入'+_min+"--"+_maxlength+"长度之间";
					lengthHints(tipVal,self);
				}else{
					var tipVal =  tipVal+'最小长度'+_min+"个字符";
					lengthHints(tipVal,self);
				}
			}
		}else{
			cue(self);
			
		}
	})

	/* 失去焦点 */
	//$("input,textarea").blur(function  (){
	$(document).on("blur","input,textarea",function(){
		/* 调整指针 */
	  	var self = this;
	  	/* 获取点击input的id */
	  	/*避免用id，页面重复（copy页面元素）revice by qiu*/
	  	//var nameId = $(self).attr("id");
	  	/* 获取点击input的val */
	  	var _val = $(self).val().length;
	  	/*最大长度*/
	  	var _maxlength = $(self).attr("maxlength");
	  	/*最小长度*/
		var _min = $(self).attr("check-minlength");
	  	/* 失去焦点删除灰色提示 */
	  	$(self).parent().siblings('span.promptScript').remove();
	  	/* 获取val值 */
	  	inputcheck(self);
		
	  	//revice by qiu 去掉nameId参数，信息已经包含在self不需要
	  	function redPrompt(self){
	  		var tipVal;
			if($(self).is(":visible")){
				if ($(self).attr("disabled") != "disabled") {
					if ($(self).attr("readonly") == undefined ) {
						/* 验证input是否为空 */
					if($(self).attr("check-empty") == "true" ){
						if($.trim(input_check.notEmpty) == ""){
							$(self).removeClass('trueInput');
							var tipVal = '输入不能为空'
							Prompt(tipVal,self);
					  	}else{
					  		$(self).parent().siblings('span.warnBlock').remove();
					  		$(self).addClass('trueInput');
					  		
					  	}
					};
					
					/* 验证电话号码 */
					if($(self).attr("check-telephone") == "true" ){
						var reg=/^[1][3,4,5,6,7,8,9][0-9]{9}$/ ;
						if(_val != 0 && !reg.test(input_check.telephone)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入正确的电话号码'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					};
					
					/* 验证固定电话号码 */
					if($(self).attr("check-telephone1") == "true" ){
						var reg=/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/ ;
						if(_val != 0 && !reg.test(input_check.telephone1)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入正确的电话号码'
								Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).addClass('trueInput');
								return
							}
						}
					};
					/* 验证身份证号码 */
					if($(self).attr("check-idCard") == "true"){
						var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
						if(_val != 0 && !reg.test(input_check.idCard)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入正确的身份证号码'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								
								$(self).addClass('trueInput');
						  		return
							}
					  	}
		
					}
					
					/* 验证最大取值范围,和最小取值范围 */
					if($(self).attr("min") || $(self).attr("max") ){
						var _minVal = Number($(self).attr("min"));
						var _maxVal = Number($(self).attr("max"));
						/*add qiu*/ 
						/*把双字节转换成两个字节内容，再作判断*/
						var inputVal =  Number($(self).val().replace(/[^\x00-xff]/g, "xx"));
						/*输入值满足：_minVal <= inputVal <= _maxVal 成立*/
						if(inputVal >= _minVal && inputVal <= _maxVal){
							if(_val != 0){
								$(self).addClass('trueInput');
						  		return
							}
						}else{
							$(self).removeClass('trueInput');
							var tipVal = '请输入'+_minVal+"-"+_maxVal+"之间的数";
							Prompt(tipVal,self);
					  	}
					}
					
					/* 验证中文 */
					if($(self).attr("check-chinese") == "true"){
						var reg = /[\u4e00-\u9fa5]/;
						if(_val != 0 && !reg.test(input_check.chinese)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入中文'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).parent().siblings('span.warnBlock').remove();
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					}
		
					/* 验证IP地址 */
					if($(self).attr("check-ipAddress") == "true"){
						var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
						if(_val != 0 && !reg.test(input_check.ipAddress)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入正确的ip地址'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								
								$("#"+nameId).addClass('trueInput');
						  		return
							}
					  	}
					}
		
					/* 验证版本 */
					if($(self).attr("check-edition") == "true"){
						var reg = /^\d(\.\d){1,2}$/;
						if(_val != 0 && !reg.test(input_check.edition)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入类似1.0或1.0.1的版本格式'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					}
		
					/* 验证英文 金额币种 */
					if($(self).attr("check-english") == "true"){
						var reg = /^[A-Za-z]+$/;
						if(_val != 0 && !reg.test(input_check.english)){
							$(self).removeClass('trueInput');
							/*var tipVal = $(self).parent().siblings("label").text();*/
							tipVal= "只能输入大小写英文";
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).parent().siblings('span.warnBlock').remove();
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					}
		
					/* 验证邮件 */
					if($(self).attr("check-mail") == "true"){
						var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
						if(_val != 0 && !reg.test(input_check.mail)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入正确的邮箱'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					}
		
					/* 验证数字和英文 */
					if($(self).attr("check-NAL") == "true"){
						var reg = /^[A-Za-z0-9]+$/;
						if(_val != 0 && !reg.test(input_check.NAL)){
							$(self).removeClass('trueInput');
							var tipVal = '只能输入数字和大小写字母'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).parent().siblings('span.warnBlock').remove();
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					}
		
					/* 验证英文、数字、特殊字符 */
					if($(self).attr("check-character") == "true"){
						var reg = /^[a-zA-Z0-9\s!@#$%^&*(){}<>?]+$/;
						if(_val != 0 && !reg.test(input_check.character)){
							$(self).removeClass('trueInput');
							var tipVal = '只能输入数字和字母和部分特殊符号'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).parent().siblings('span.warnBlock').remove();
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					}
		
					/* 验证密码 */
					if($(self).attr("check-password") == "true"){
						var reg = /[\u4e00-\u9fa5]/;
						if(_val != 0 && reg.test(input_check.password)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入非中文字符,长度在6~18之间'
							Prompt(tipVal,self);
						}else{
							if(_val>=6 && _val<=18){
								$(self).addClass('trueInput');
						  		return
							}else{
								$(self).removeClass('trueInput');
								var tipVal = '请输入非中文字符,长度在6~18之间'
								Prompt(tipVal,self);
							}
					  	}
					}
		
					
					
					/* 验证英文数字下划线 */
					if($(self).attr("check-alphanumericSymbols") == "true"){
						var reg = /^[A-Za-z0-9_]+$/;
						if(_val != 0 && !reg.test(input_check.blank)){
							$(self).removeClass('trueInput');
							var tipVal = '只能输入英文、数字、下划线'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).parent().siblings('span.warnBlock').remove();
								$(self).addClass('trueInput');
						  		return
							}
					  		
					  	}
					}
					
					
					
					
					/* 验证数字3-10 */
					if($(self).attr("check-TtoT") == "true"){
						var reg = /^[0-9]+$/;
						if(3<=input_check.blank && input_check.blank<=10){
							if(_val != 0 && !reg.test(input_check.blank)){
									$(self).removeClass('trueInput');
									var tipVal = '只能输入3-10的数字'
									Prompt(tipVal,self);
							}else{
								if(_val != 0){
									$(self).parent().siblings('span.warnBlock').remove();
									$(self).addClass('trueInput');
							  		return
								}
						  	}
						}else{
							$(self).removeClass('trueInput');
							var tipVal = '只能输入3-10的数字'
							Prompt(tipVal,self);
						}
					}
					
					/* 验证数字1-5 */
					if($(self).attr("check-OtoF") == "true"){
						var reg = /^[1-5]+$/;
						if(1<=input_check.blank && input_check.blank<=5){
							if(_val != 0 && !reg.test(input_check.blank)){
									$(self).removeClass('trueInput');
									var tipVal = '只能输入1-5的数字'
									Prompt(tipVal,self);
							}else{
								if(_val != 0){
									$(self).parent().siblings('span.warnBlock').remove();
									$(self).addClass('trueInput');
							  		return
								}
						  	}
						}else{
							$(self).removeClass('trueInput');
							var tipVal = '只能输入1-5的数字'
							Prompt(tipVal,self);
						}	
					}
					/* 验证中文数字横线中括号小括号 */
					if($(self).attr("check-chineseDigitalSymbols") == "true"){
						var reg = /^[\u4e00-\u9fa5\0-9_()[\]（）【】-]+$/;
						var str = input_check.blank.replace(/[ ]/g, "")
						$(self).val(str);
							if(_val != 0 && !reg.test(str)){
								$(self).removeClass('trueInput');
								var tipVal = '只能输入中文、数字、下划线、中括号、小括号'
								Prompt(tipVal,self);
							}else{
								if(_val != 0){
									$(self).parent().siblings('span.warnBlock').remove();
									$(self).addClass('trueInput');
							  		return
								}
						  	}
					}
					
					
		
					/* 验证非零的正整数 */
					if($(self).attr("check-integer") == "true"){
						var reg = /^[1-9]\d*$/;
						if(_val != 0 && !reg.test(input_check.integer)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入正整数'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					};
					
					/* 验证部分特殊符号 */
					if($(self).attr("check-partCharacter") == "true"){
						var reg =/[''""\\\/]/;
						if(_val != 0 && reg.test(input_check.integer)){
							$(self).removeClass('trueInput');
							var tipVal = '请勿输入单引、双引号、以及正反斜线';
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).parent().siblings('span.warnBlock').remove();
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					};
					};
					
					/* 校验长度提示 */
					/*if(_val<_min){
						$("#"+ nameId).removeClass('trueInput');
						var tipVal = $(self).parent().siblings("label").text();
						tipVal = '请输入'+tipVal+'，最小'+_min+"个字符";
						Prompt(tipVal,nameId);
					}*/
					
					
					/* 验证整数  包括正负和0 add qiu*/
					if($(self).attr("check-integer-pos-neg") == "true"){
						var reg = /^(0|[1-9][0-9]*|-[1-9][0-9]*)$/g;
						if(_val != 0 && !reg.test(input_check.integer)){
							$(self).removeClass('trueInput');
							var tipVal = '请输入整数'
							Prompt(tipVal,self);
						}else{
							if(_val != 0){
								$(self).addClass('trueInput');
						  		return
							}
					  	}
					};
					
					/* 校验金额  add qiu */
					/*允许格式："0" "0." "0.1" 会自动把前面没用的0去掉*/
					/*如果需要确定小数数位  decimal-num 加上小数数位*/
					if($(self).attr("check-money") == "true" ){
						if(_val != 0){
							/*1、判断数字和点*/
							var reg = /[^\d | \.]/g;
							if(reg.test(input_check.notEmpty)){
								$(self).removeClass('trueInput');
								var tipVal = '请输入正确金额';
								Prompt(tipVal,self);
								return;
							}
							/*2、判断只有一个点或没有*/
							var reg1 = /[\.]/g;
							/*if((reg1.exec(input_check.notEmpty)) !=null &&(reg1.exec(input_check.notEmpty)).length>1){*/
							if(input_check.notEmpty.match(reg1)!=null && input_check.notEmpty.match(reg1).length>1){
								$(self).removeClass('trueInput');
								var tipVal = '请输入正确金额';
								Prompt(tipVal,self);
								return;
							}
							/*3、去掉开头无用的0或者加上0*/
							var reg2 = /^0*/g;
							var $manageList  = $(self).val().split(".");
							$manageList[0] = $manageList[0].replace(reg2,"");
							if($manageList[0]==""){
								$manageList[0] = 0;
							}
							var $manageVal_new = $manageList.join(".");
							$(self).val($manageVal_new);
							
							/*验证通过*/
							$(self).parent().siblings('span.warnBlock').remove();
							$(self).addClass('trueInput');
							
							
							/*如果还需要确定小数和整数位数处理*/
							/*整数超长报错，小数自动处理*/
							if($(self).attr("decimal-num") != undefined){
								var $num0 = ""; /*小数数位*/								
								var $manageList_new = $manageVal_new.split(".");  /*值分隔成数组*/
								
								if($(self).attr("decimal-num").match(",")){
									/*整数小数都要*/
									$num0 = $(self).attr("decimal-num").split(",")[1];
									
									$numInteger = $(self).attr("decimal-num").split(",")[0];
									if($manageList_new[0].length>$numInteger){
										$("#"+ nameId).removeClass('trueInput');
										var tipVal = '金额整数位超长';
										Prompt(tipVal,nameId);
										return;
									}
									
								}else{
									/*只要小数*/
									$num0 = $(self).attr("decimal-num");
								}
								
								
								var str0 ="";
								for(var num0 =0;num0<$num0;num0++){
									str0+="0";
								}
								
								if($manageList_new.length == 1){
									/*没有小数内容*/
									$(self).val($manageList_new[0]+"."+str0);
								}else{
									/*有小数内容进行转化*/
									$manageList_new[1] = ($manageList_new[1]+str0).substr(0,$num0);
									$(self).val($manageList_new.join("."));
								}
								
							}
							
					  		return;
						}else{
							$(self).removeClass('trueInput');
							var tipVal = '请输入金额';
							Prompt(tipVal,self);
							return;
						}
						
						
					}
				};
			
				/* 开始时间不能大于结束时间 */
				function addTime(){
					var MyDate=new Date();
					var startTime_one = Date.parse($("[check-startTime-one]").val());
					var endTime_one = Date.parse($("[check-endTime-one]").val());
					/*年月日*/
					var year=MyDate.getFullYear();
					var month=MyDate.getMonth()+1;
					var ri=MyDate.getDate();
					var slash = "/";
					var my_Date = year+slash+month+slash+ri;
					var myDate = Date.parse(my_Date);
					if(myDate<=startTime_one){
						if(startTime_one > endTime_one){
							var tipVal = '开始时间要小于结束时间';
							var nameId = $("[check-startTime-one]").attr("id");
							Prompt(tipVal,self);
						}else{
							var startTimeId = $("[check-startTime-two]").attr("id");
							var endTimeId = $("[check-endTime-two]").attr("id");
							$("#"+startTimeId).addClass('trueInput');
							$("#"+startTimeId).parent().siblings('span.warnBlock').remove();
							$("#"+endTimeId).parent().siblings('span.warnBlock').remove();
							
						}
					}else{
						var tipVal = '开始时间要大于当前时间';
						var nameId = $("[check-startTime-one]").attr("id");
						$(self).removeClass('trueInput');
						Prompt(tipVal,self);
					}
				}
				
				function unTime(){
					var startTime_one = Date.parse($("[check-startTime-two]").val());
					var endTime_one = Date.parse($("[check-endTime-two]").val());
						if(startTime_one > endTime_one){
							var tipVal = '开始时间要小于结束时间';
							var nameId = $("[check-startTime-two]").attr("id");
							$("#"+ nameId).removeClass('trueInput')
							Prompt(tipVal,self);
						}else{
							var startTimeId = $("[check-startTime-two]").attr("id");
							var endTimeId = $("[check-endTime-two]").attr("id");
							$("#"+startTimeId).addClass('trueInput');
							$("#"+startTimeId).parent().siblings('span.warnBlock').remove();
							$("#"+endTimeId).parent().siblings('span.warnBlock').remove();
							
						}
					
				}
				
				
				/*开头结尾不能要空格*/
				if($(self).attr("check-space") == "true"){
					var str = input_check.blank
					var spaceStr = str.replace(/(^\s*)|(\s*$)/g, ""); 
					$(self).val(spaceStr);
					inputcheck (self);
				}
		
				/*判断时间不为空*/
				if($(self).attr("check-time-empty") == "true"){
					if (input_check.endTime_one != "") {
						$(self).parent().siblings('span.warnBlock').remove();
						$(self).addClass('trueInput');
					}else{
						var tipVal = '输入不能为空'
						Prompt(tipVal,self);
					}
				}
				
				/* 验证开始时间  不小于当前时间  add qiu */
				/*if($(self).attr("check-startTime") == "true"){*/
				if($(self).is("[check-startTime]")){
					var dastartTimeValte = $(self).val();
					if (_val >0) {
						var today = new Date();
						var todayUTC = today.getTime() - 1000 * 60 * 60 * 24;
						var y = dastartTimeValte.slice(0, 4);
						var m = dastartTimeValte.slice(5, 7);
						var d = dastartTimeValte.slice(8, 10);
						var newDate = m + '/' + d + '/' + y;
						var setDay = Date.parse(newDate);
						if (setDay >= todayUTC) {
							$(self).addClass('trueInput');
							$(self).parent().siblings('span.warnBlock').remove();
						} else {
							var tipVal = '时间要大于当前时间';
							$(self).removeClass('trueInput');
							Prompt(tipVal,self);
						}
					}else{
						var tipVal = '时间不能为空';
						Prompt(tipVal,self);
					}
				}
				/* 验证开始时间  不小于当前时间  add qiu */
				/*if($(self).attr("check-endTime") == "true"){*/
				if($(self).is("[check-endTime]")){
					var attrVal = $(self).attr("check-endTime");
					var startDay = self.parent(".tab-pane").eq(0).find("[check-startTime='"+attrVal+"']").val();
					var endDay = $(self).val();
					if(endDay !=""){
						if(startDay!=""){
							var y = startDay.slice(0, 4);
							var m = startDay.slice(5, 7);
							var d = startDay.slice(8, 10);
							startDay = m + '/' + d + '/' + y;
							startDay = Date.parse(startDay);
							
							
							var y = endDay.slice(0, 4);
							var m = endDay.slice(5, 7);
							var d = endDay.slice(8, 10);
							endDay = m + '/' + d + '/' + y;
							endDay = Date.parse(endDay);
							if (startDay <= endDay) {
								$(self).addClass('trueInput');
								$(self).parent().siblings('span.warnBlock').remove();
							} else {
								var tipVal = '结束时间不小于开始时间';
								$(self).removeClass('trueInput');
								Prompt(tipVal,self);
							}
						}else{
							/*清空结束时间*/
							$(self).val("");
						}
						
					}else{
						var tipVal = '时间不能为空';
						Prompt(tipVal,self);
					}
				}
				
				
				
				/* 验证正常开始时间 */
				if($(self).attr("check-startTime-one") == "true"){
					if (input_check.startTime_one != "") {
						$(self).addClass('trueInput');
						addTime()
					}else{
						var tipVal = '输入不能为空'
						Prompt(tipVal,self);
					}
				}
		
				/* 验证结束开始时间 */
				if($(self).attr("check-endTime-one") == "true"){
					if (input_check.endTime_one != "") {
						$(self).addClass('trueInput');
						addTime()
					}else{
						var tipVal = '输入不能为空'
						Prompt(tipVal,self);
					}
				}
				
				/* 验证修改正常开始时间 */
				if($(self).attr("check-startTime-two") == "true"){
					if (input_check.startTime_one != "") {
						$(self).addClass('trueInput');
						unTime()
					}else{
						var tipVal = '输入不能为空'
						Prompt(tipVal,self);
					}
				}
		
				/* 验证修改结束开始时间 */
				if($(self).attr("check-endTime-two") == "true"){
					if (input_check.endTime_one != "") {
						$(self).addClass('trueInput');
						unTime()
					}else{
						var tipVal = '输入不能为空'
						Prompt(tipVal,self);
					}
				}
			};
			return tipVal;
	  	};
	  	if($(self).is(":visible")){
	  	if(_maxlength != undefined || _min != undefined){
			var tipVal =  redPrompt(self);
			/*判断tipVal是否为空*/
			if(tipVal != undefined){
				var tipVal = tipVal+'，';
			}else{
				var tipVal = '';
			};
			/* 最大长度提示 */
			if(_val<_min){
				if(_maxlength != undefined ){
					if(_min != undefined){
						var tipVal = tipVal+'，请输入'+_min+"至"+_maxlength+"长度之间";
						lengtPrompt(tipVal,self);
					}else{
						var tipVal =  '请输入'+tipVal+'，不超过'+_maxlength+"个字符";
						lengtPrompt(tipVal,self);
					}
				}else
				/* 最大小度提示 */
				if( _min != undefined ){
					if(_maxlength != undefined){
						var tipVal =  tipVal+'，请输入'+_min+"--"+_maxlength+"长度之间";
						lengtPrompt(tipVal,self);
					}else{
						if(tipVal == undefined){
							var tipVal =  '最小长度'+_min+"个字符";
							lengtPrompt(tipVal,self);
						}else{
							var tipVal =  tipVal+'，最小长度'+_min+"个字符";
							lengtPrompt(tipVal,self);
						}
					}
				}
		  	}else{
		  		redPrompt(self);
		  	}
	  	}else{
	  		redPrompt(self);
	  	}
	  	}
		
	});
});



/*
 * add qiu
 * 验真jBox输入框
 * 一点击就弹出jBox框，直接触发了blur
 * 不可以用blur验证（此时为空），在点击下一步时才做验证
 */
function checkJBox(ID){
	if(ID != undefined){
		$("#"+ID).find("[check-jBox-empty]").each(function(){
			if($(this).attr("check-jBox-empty")==true || $(this).attr("check-jBox-empty")=="true"){
				if($(this).val()==""){
					var tipVal = '输入不能为空'
					Prompt(tipVal,self);
				}
			}
		});
	}else{
		$("[check-jBox-empty]").each(function(){
			if($(this).attr("check-jBox-empty")==true || $(this).attr("check-jBox-empty")=="true"){
				if($(this).val()==""){
					var tipVal = '输入不能为空'
					Prompt(tipVal,self);
				}
			}
			
		});
	}
	
}





/*
 * 页面加载完,自动判断是否要加readonly
 */$(document).ready(function() {
	var myDate = new Date;
	var myTime = Date.parse(myDate);
	var startTime_one =  $("[check-startTime-ones]").val();
	if( startTime_one != ""){
		var startTimeOne = Date.parse(startTime_one);
		if (myTime>startTimeOne) {
			$("[check-startTime-ones]").attr("readonly","readonly");
		}
	}else{
		return
	}
	
}); 
/*普通验证方法 */
 /*revice by qiu改为对象*/
function Prompt (tipVal ,self){
		var script = tipVal;
		if($(self).parent().next("span.warnBlock").length == 0){
			var tag= "<span class='warnBlock'>"+script+"</span>";
			$(self).parent().after(tag);
			$(self).removeClass('trueInput');
		};
	}


 /*单选复选验证方法 */
/*revice by qiu改为对象*/
function Prompts (tipVal ,thisObj){
		var script = tipVal;
		if($(thisObj).parent().parent().next("span.warnBlock").length == 0){
			var tag= "<span class='warnBlock warnBlock2'>"+script+"</span>";
			$(thisObj).parent().parent().after(tag);
		};
	}

/* 添加下拉框红字提示 */
/*revice by qiu改为对象*/
function selectPrompt (tipVal ,thisObj){
	var script = tipVal;
	if($(thisObj).parent().next("span.warnBlock").length == 0){
		var tag= "<span class='warnBlock warnBlock2'>"+script+"</span>";
		$(thisObj).parent().after(tag);
		$(thisObj).removeClass('trueInput');
	};
}


  /*判断是否选中单选 */
function unChecked () {
	if ($("input[type='radio']").is(":visible")) {
		var radioCheckbtn = [];
		var i = 0;var j = 0;	
		$("input[type='radio']").each(function(){
			radioCheckbtn.push ($("input[type='radio']").eq(i).attr("checkbtn"));
			i++;
		});
		var arrCheckbtn = $.unique(radioCheckbtn);
		var tipVal = '请选择一项';
		$(arrCheckbtn).each(function(){
			var Checkbtn = arrCheckbtn[j];
			if ($("input[checkbtn="+Checkbtn+"]").is(":visible")) {
					if ($("input[checkbtn="+Checkbtn+"]").is(":checked")) {
						return j++;
					}else{
						//revice by qiu改为obj
						Prompts(tipVal,$("input[checkbtn="+Checkbtn+"]"));
					}
			};
			j++;
		});
	};
}
/*判断部分是否选中单选 */
function portionChecked (ID) {
	if ($("#"+ID+" "+"input[type='radio']").is(":visible")) {
		var radioCheckbtn = [];
		var i = 0;var j = 0;	
		$("#"+ID+" "+"input[type='radio']").each(function(){
			radioCheckbtn.push ($(this).attr("checkbtn"));
		});
		var arrCheckbtn = $.unique(radioCheckbtn);
		var tipVal = '请选择一项';
		$(arrCheckbtn).each(function(){
			var Checkbtn = arrCheckbtn[j];
			if ($("#"+ID+" "+"input[checkbtn="+Checkbtn+"]").is(":visible")) {
					if ($("#"+ID+" "+"input[checkbtn="+Checkbtn+"]").is(":checked")) {
						return j++;
					}else{
						Prompts(tipVal,$("#"+ID+" "+"input[checkbtn="+Checkbtn+"]").last());
					}
			};
			j++;
		});
	};
}

 /*判断是否选中复选框*/ 
function unCheckBox() {
		var checkboxCheckbtn = [];
		var i = 0;var j = 0;
		$("input[type='checkbox']").each(function(){
			checkboxCheckbtn.push ($("input[type='checkbox']").eq(i).attr("checkbtn"));
			i++;
		});
		var arrCheckbtn = $.unique(checkboxCheckbtn);
		var tipVal = '请选择一项';
		$(arrCheckbtn).each(function(){
			var Checkbtn = arrCheckbtn[j]
			if ($("input[checkbtn="+Checkbtn+"]").is(":visible")) {
				if ($("input[checkbtn="+Checkbtn+"]").is(":checked")) {
					return j++;
				}else{
						Prompts(tipVal,$("input[checkbtn="+Checkbtn+"]"));
					}
			};
			j++;
		});
}

/*部分判断是否选中复选框*/
function portionCheckBox(ID) {
	var checkboxCheckbtn = [];
	var i = 0;var j = 0;
	$("#"+ID+" "+"input[type='checkbox']").each(function(){
		checkboxCheckbtn.push ($(this).attr("checkbtn"));
		i++;
	});
	var arrCheckbtn = $.unique(checkboxCheckbtn);
	var tipVal = '请选择一项';
	$(arrCheckbtn).each(function(){
		var Checkbtn = arrCheckbtn[j]
		if ($("#"+ID+" "+"input[checkbtn="+Checkbtn+"]").is(":visible")) {
			if ($("#"+ID+" "+"input[checkbtn="+Checkbtn+"]").is(":checked")) {
				return j++;
			}else{
				Prompts(tipVal,$("#"+ID+" "+"input[checkbtn="+Checkbtn+"]").last());
			}
		};
		j++;
	});
}


/* 下拉框验证 */
function selects (){
		var selectsId = [];
		var i = 0;var j = 0;
		$("select").each(function(){
			selectsId.push ($("select").eq(i).attr("checkbtn"));
			i++;
		});
		var tipVal = '请选择一项';
		$(selectsId).each(function(){
			var Checkbtn = selectsId[j]
			if ($("select[checkbtn="+Checkbtn+"]").is(":visible")) {
				var coun = $("select[checkbtn="+Checkbtn+"]").val();
				if(coun == "") {
					selectPrompt(tipVal,$("select[checkbtn="+Checkbtn+"]"));
	    		}else {
	  				$("select[checkbtn="+Checkbtn+"]").parent().siblings('span.warnBlock').remove();
	    		}
			};
			j++;
		});
}
/* 部分下拉框验证 */
function portionSelects (ID){
	var tipVal = '请选择一项';
	//revice by qiu需要抓取id内的下拉框，不然全部后面抓取id内元素会出错且冗余,直接当作对象数组全部处理
	$("#"+ID+" "+"select").each(function(){
		if ($(this).is(":visible") && $(this).attr("checkbtn")!=null && $(this).attr("checkbtn")!=undefined && $(this).attr("checkbtn")!="") {
			var coun = $(this).val();
			if(coun == "") {
				selectPrompt(tipVal,this);
    		}else {
  				$(this).parent().siblings('span.warnBlock').remove();
    		}
		};
	});
}




/* 调用校验方法名 */
function proofTest(ID){
	/*add qiu用于部分验证
	否则部分外面有warnBlock也返回false*/	
	if(ID){
		if ($("#"+ID+" span.warnBlock:visible").length == 0) {
			return true;
		}else{
			return false;
		}
	}
	if ($("span.warnBlock:visible").length == 0) {
		return true;
	}else{
		return false;
	}
}

/*表格验证*/
function cTableLine1(tableId, classList) {
				if ($('#' + tableId).is(":visible")) {
					/*判断table至少有一行*/
					var  nextState = true;
					var index = $('#' + tableId).bootstrapTable('getSelections').length;
					if (index == 0) {
						if (nextState) {
							nextState = false;
							showContent("保存时至少选择一条地址信息", "error");
						}
					} else {
						 /*被选择的条内容必输*/
						for (var a = 0; a < classList.length; a++) {
							$('#' + tableId).find('tbody tr[class="selected"]').find("input[class=" + classList[a] + "]").each(function() {
									var blank = /^\s*|\s*$/g;
									var singleQuotes = /['']/;
									$(this).val($(this).val().replace(blank, ''));
									if ($(this).val() == '') {
										$(this).css('border-color','#f9918f');
										nextState = false;
										hong();
									}else if(singleQuotes.test($(this).val())){
										$(this).css('border-color','#f9918f');
										nextState = false;
										showContent("请勿输入单引号", "error");
										hong();
									}
								});
						}
					}
					return nextState
				}
}


function  hint(tableId, classList){
	var  cue ;
	if(cTableLine1(tableId, classList) ==true){
			cue = cTableLine1(tableId, classList);
			return cue;
		}
}


function  proof(){
	checkJBox();   /*jBox输入框 add qiu*/
	$("input,textarea:visible").trigger("blur");
	showProof();
	unChecked();
	unCheckBox();
	selects();
	/*var _proofArr = [];
	_proofArr[0]=tiShi();
	_proofArr[1]=proofTest();*/
	var _proofArr=proofTest();
	return _proofArr;
}

/* input */
function showProof() {
//	var nameId = $("input[type=hidden]").attr("id");
//	for(var i=0;i<name.length;i++){
	
//		
//	}
//	if($(nameId).attr("check-empty") == "true" ){
//		if($.trim(input_check.notEmpty) == ""){
//			$("#"+ nameId).removeClass('trueInput');
//			var tipVal = '输入不能为空'
//			Prompt(tipVal,nameId);
//	  	}else{
//	  		$("#"+nameId).parent().siblings('span.warnBlock').remove();
//	  	}  
//	};
	$('input[type="hidden"]').each(function(){
		var $this = $(this);
		var $checkEmpty = $this.attr('check-empty');
		var $value = $this.val();
		if(undefined!=$checkEmpty && $checkEmpty=='true'){
			if(undefined!=$value && $value!=''){
				// 关闭错误信息
				$this.parent().siblings('span.warnBlock').remove();
			}else{
				// 显示错误信息
				$this.removeClass('trueInput');
				var tipVal = '输入不能为空';
				var nameId  = $this.attr('id')
				Prompt(tipVal,this);
			}
		}
	});
}

/*部分校验*/
function  portion(ID){
	showProof();
	checkJBox(ID);   /*jBox输入框校验 add qiu*/	
	$("#"+ID+" "+"input,textarea:visible").trigger("blur");
	portionChecked(ID);
	portionCheckBox(ID);
	portionSelects(ID);
	/*var _proofArr = [];
	_proofArr[0]=tiShi();
	_proofArr[1]=proofTest();*/
	var _proofArr=proofTest(ID);
	return _proofArr;
}



/* 页面加载完,自动判断是否要加readonly */
$(document).ready(function() {
	/*$("#saveBtn").click(function(){
			
		if(proof()){
			
		};
	});*/
	//revice by qiu for 给动态生成元素加事件
	/* 点击复选之后取消红字提示 */
	$(document).on("click","input[type='checkbox']",function(){
		var self = this;
		$(self).parent().parent().siblings('span.warnBlock').remove();
	})
	/* 点击单选之后取消红字提示 */
	$(document).on("click","input[type='radio']",function(){
		var self = this;
		$(self).parent().parent().siblings('span.warnBlock').remove();
	})
	/* 点击下拉框之后取消红字提示 */
	$(document).on("change","select",function(){
		var self = this;
			$(self).parent().siblings('span.warnBlock').remove();
		});
});
