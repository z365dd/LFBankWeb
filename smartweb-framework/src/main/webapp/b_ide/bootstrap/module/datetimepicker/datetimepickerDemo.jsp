<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <!-- JSTL  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!-- JQUERY -->
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script><!-- /JQUERY -->

<!-- BOOTSTRAP -->
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap-theme.min.css"/>
<!-- css -->
 <link href="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/css/bootstrap.min.css" rel="stylesheet" media="screen"/>
 <link href="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>
<!-- js -->
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<!-- <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/datetimepicker/js/bootstrap-datetimepicker.de.js" charset="UTF-8"></script> -->
<title>Datetimepicker</title>
</head>
<body>
<div class="container">
    <form action="" class="form-horizontal"  role="form">
        <fieldset>
            <legend>DateTimePicker Test</legend>
            <div class="form-group">
                <label for="dtp_input1" class="col-md-5 control-label">年/月/日/时/分</label>
                <!-- <div class="input-group date form_datetime col-md-3" data-date-format="yyyy MM dd" data-link-field="dtp_input1" data-date="2015-10-23T05:25:07Z"> -->
                <div class='col-md-5'>
                    <div class="input-group date form_datetime" data-link-field="dtp_input1"  data-date-format="yyyy MM dd - HH:ii p" data-link-format="yyyy-mm-dd - HH:ii p" data-date=""><!--  -->  
                        <input class="form-control" size="16" type="text" value="" readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                    </div>
				    <input type="hidden" id="dtp_input1" value="" />
                </div>
            </div>
			<div class="form-group">
                <label for="dtp_input2" class="col-md-5 control-label">年/月/日</label>
                <div class='col-md-5'>
                     <div class="input-group date form_date" data-link-field="dtp_input2" data-date-format="yyyy MM dd" data-link-format="yyyy-mm-dd" data-date=""><!--  -->
                        <input class="form-control" size="16" type="text" value="" readonly>
	                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                     </div>
                     <input type="hidden" id="dtp_input2" value="" />
                </div>
            </div>
			<div class="form-group">
                <label for="dtp_input3" class="col-md-5 control-label">时/分</label>
                <div class='col-md-5'>
	                <div class="input-group date form_time" data-link-field="dtp_input3" data-date-format="hh:ii" data-link-format="hh:ii" data-date=""><!--  -->
	                    <input class="form-control" size="16" type="text" value="" readonly>
	                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
						<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
	                </div>
					<input type="hidden" id="dtp_input3" value="" />
			    </div>
            </div>
            <div>
                 <input type="button" value='获取年月日时分' id='dtp_1' style="margin: 10px 10% 0px 10%"/>
                 <input type="button" value='获取年月日' id='dtp_2' style="margin: 10px 5% 0px 5%"/>
                 <input type="button" value='获取时分' id='dtp_3' style="margin: 10px 5% 0px 5%"/>
                 <input type='text' value='' id='date_time' style="margin: 10px 5% 0px 10%"/>
            </div><br/>
            <div class="form-group">
                <label for="dtp_input4" class="col-md-3 control-label">开始时间</label>
                <div id='start' class="input-group date col-md-8" data-date-format="yyyy MM dd" data-link-field="dtp_input4" data-date="">
                    <input class="form-control" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
				<input type="hidden" id="dtp_input4" value="" /><br/>
            </div>
            <div class="form-group">
                <label for="dtp_input5" class="col-md-3 control-label">结束时间</label>
                <div id='end' class="input-group date col-md-8" data-date-format="yyyy MM dd" data-link-field="dtp_input5" data-date="">
                    <input class="form-control" size="16" type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
				<input type="hidden" id="dtp_input5" value="" /><br/>
            </div>
            <div>
               <!--  <div class="input-append date" id="datetimepicker1" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
                  <input class="span2" size="16" type="text" value="12-02-2012">
                  <span class="add-on"><i class="glyphicon icon-th"></i></span>
                </div>   
                <div class="input-append date" id="datetimepicker" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
                  <input class="span2" size="16" type="text" value="12-02-2012">
                  <span class="input-group-addon"><i class="glyphicon icon-remove"></i></span>
                  <span class="input-group-addon"><i class="glyphicon icon-th"></i></span>
                </div>    -->   
                <!-- <input type="text" value="2012-05-15 21:05" id="datetimepicker"> -->               
            </div>
        </fieldset>
    </form>
</div> 
<script type="text/javascript">
    $('.form_datetime').datetimepicker({
    	//format:'yyyy/dd/mm',        // 格式化时间
        language:  'zh-CN',         // 设置语言（中文）
        weekStart: 0,               // 设置一周从哪一天开始（0（星期日）到6（星期六））
//      startDate:'2015-9-23',      // 设置开始时间
//		endDate:'2015-10-23',       // 设置结束时间
//		daysOfWeekDisabled:[0,6],   // 禁用时间（周末）(数组0代表周日6代表周六)
		autoclose:true,             // 当选择一个日期之后是否立即关闭此日期时间选择器。  
		startView: 2,               // 日期时间选择器打开之后首先显示的视图。（0选择分钟1代表小时2日期3月份4年份）
		minView:0,                  // 最小的视图 参数同上
        todayBtn:'linked',          // Boolean, "linked". 默认值: false 如果此值为true 或 "linked"
                                    // 则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。如果是true的话，"Today" 
                                    // 按钮仅仅将视图转到当天的日期，如果是"linked"，当天日期将会被选中。
        todayHighlight:true,        // true 高亮当前日期
        keyboardNavigation:false,   // 是否允许通过方向键改变日期
		autoclose: 1,               //
		todayHighlight: 1,          //
		forceParse: 0,              //
        showMeridian: 1             //
    });
    $('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
	$('.form_time').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 2,
		forceParse: 0
    }); 
    $(function(){
	    	$('#dtp_1').click(function(){
	    		var dateTime = $('#dtp_input1').val();
				if(dateTime.trim()!=''){
					$('#date_time').val(dateTime);
				}else{
					alert('请选择时间！');
				}
	    	});
	    	$('#dtp_2').click(function(){
	    		var dateTime = $('#dtp_input2').val();
				if(dateTime.trim()!=''){
					$('#date_time').val(dateTime);
				}else{
					alert('请选择时间！');
				}
	    	});
	    	$('#dtp_3').click(function(){
	    		var dateTime = $('#dtp_input3').val();
				if(dateTime.trim()!=''){
					$('#date_time').val(dateTime);
				}else{
					alert('请选择时间！');
				}
	    	});
	    }); 
    
    $('#start').datetimepicker({
    	format:'yyyy/mm/dd',
        language:  'zh-CN',
        weekStart: 0,
		autoclose:true,
		startView: 2,
		minView:2,
        todayBtn:'linked',
        todayHighlight:true,
        keyboardNavigation:false,
		autoclose: 1,
		todayHighlight: 1,
		forceParse: 0,
        showMeridian: 1
    });
	$('#end').datetimepicker({
    	format:'yyyy/mm/dd',
        language:  'zh-CN',
        weekStart: 0,
		autoclose:true,
		startView: 2,
		minView:2,
        todayBtn:'linked',
        todayHighlight:true,
        keyboardNavigation:false,
		autoclose: 1,
		todayHighlight: 1,
		forceParse: 0,
        showMeridian: 1
    });
   $('#start').datetimepicker().on('changeDate', function(ev){ console.info(ev); alert(ev.date); $('#end').datetimepicker('setStartDate', ev.date);});
   $('#end').datetimepicker().on('changeDate', function(ev){ console.info(ev); alert(ev.date); $('#start').datetimepicker('setEndDate', ev.date);});
</script>
</body>
</html>
