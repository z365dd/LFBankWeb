$(document).ready(function(){
	//获取快捷入口信息
	getQuickEntry();
	//获取公告信息
	getNoticeList();
	//获取流程信息
	getFlowList();
	//获取用户信息
	getUserInfo();
	//为快捷入口添加绑定事件
	//addEventForQuickEntry();
	//获取最新流程信息
	getLatestFlowInfo();
	//获取当前日期信息
	getCurrentDateInfo();
	//大屏请求跳转页面
	screenReqJump();

});

function getCurrentDateInfo(){
	console.log("======================================123=====================================");
	var curDateTime = GetCurrentDate();
	var lunDateTime = GetLunarDate();
	$("div .k6-1-2").text(curDateTime.substring(8,10));
	$("div .k6-1-3").text(curDateTime.substring(21,24));
	$("div .k6-1-4").text(curDateTime.substring(0,8));
	/*
	 * var arr = lunDateTime.split(/\s+/g); if(arr.length > 1){ $("div
	 * .k6-1-5").text("农历"+arr[1]); }
	 */
	$("div .k6-1-5").text("农历" + lunDateTime);
}

function moreInfo(flag){
	var path="";
	if(flag=="moreTask"){
		path="平台管理->流程管理->流程实例"; /*根据审批流查询对应的一条记录*/
	}else if(flag=="moreNitoces"){
		path="平台管理->系统设置->公告消息";
	}else if(flag=="moreFlows"){
		path="平台管理->流程管理->流程实例";
	}else{
		//
	}
	parent.window.openMenu(path);
	
}

//为快捷入口添加绑定事件
/*function addEventForQuickEntry(){
	$("li[class='r1-1']").click(function(){
		var path = $(this).children('input').val();
		if(path!=undefined){
			openMenu(path);
		}
	});	
	//去掉默认的contextmenu事件，否则会和右键事件同时出现。
	document.oncontextmenu = function(){
		return false
	};    
    var res = document.getElementById('delMenu');
    $("li[class='r1-1']").contextmenu(function(e){
    	//如果button=1（鼠标左键），button=2（鼠标右键），button=0（鼠标中间键）
    	var path = $(this).children('input').val();
    	var id = $(this).children('input').attr("id");
    	var name = $(this).children('div').children('div .y2').text();
		if(path!=undefined){
		    if(e.button===2){
		    	//打印参数
			    console.log(e);
			    //鼠标点击时给div定位Y轴
			    res.style.top = e.clientY+'px';
			    res.style.left = e.clientX+'px';
			    res.style.display = 'block';
          		$("#delId").val(id);
          		$("#delName").val(name);
		    }else{
		    	res.style.display = 'none';
		    }
		}
    });
    //点击隐藏删除按钮
	document.onclick = function() { 
		res.style.display = 'none';
	}
}*/

//保存新增快捷入口方法
function save(){
	var menuId = $("#addQuickEntryId").val();
	var menuName = $("#addQuickEntryName").val();
	var pId = $("#pId").val();
	var pName = $("#pName").val();
	var len = $("#addMore").siblings().length;
	if(len > 8){
		var msg = "快捷入口不能超过9个"; 
		top.$.jBox.tip(msg,"error",{persistent:true,opacity:0});
		return false;
	}
	//flag快捷入口是否重复标志
	var flag = false;
	$("#addMore").siblings().each(function(i){
		var id = $(this).children('.r1-1-3').children('.y2').attr("id");
		if(menuId==id){
			flag = true;
		}
	});
	if(flag){
		var errMsg = "快捷入口["+menuName+"]已经存在，请不要重复添加"; 
		top.$.jBox.tip(errMsg,"error",{persistent:true,opacity:0});
		return false;
	};
	$.ajax({
		url:ctx +'/sys/modules/sysQuickEntry/addQuickEntry', 
		type:"POST",
		dataType:"json",
		data:{menuId:menuId,menuName:menuName,pId:pId,pName:pName,img:"r"+GetRandomNum(1,3)+".png"}, 
		async:true,
		success:function(data) {
			console.log("==save=="+data);
			if(data){
				var msg = "新增快捷入口["+menuName+"]成功"; 
				top.$.jBox.tip(msg,"success",{persistent:true,opacity:0});
				//showContent1(msg,"success");
				$("#addMore").siblings().remove();
				getQuickEntry();
				//addEventForQuickEntry();
			}else{
				var msg = "新增快捷入口["+menuName+"]失败"; 
				top.$.jBox.tip(msg,"error",{persistent:true,opacity:0});
			}
		}
	});
}

//弹出菜单选择树
function addQuickEntry(){
	$("#addQuickEntryName").click();
}

//删除快捷入口
function delMenu(id,name){
	confirmx("是否确定删除该快捷入口", function(){
		$.ajax({
			url:ctx +'/sys/modules/sysQuickEntry/delQuickEntry', 
			type:"POST",
			dataType:"json",
			data:{id:id}, 
			async:true,
			success:function(data) {
				console.log("==delMenu=="+data);
				if(data){
					var successMsg = "删除快捷入口["+name+"]成功"; 
					top.$.jBox.tip(successMsg,"success",{persistent:true,opacity:0});
					$("#addMore").siblings().remove();
					getQuickEntry();
					//addEventForQuickEntry();
				}else{
					var msg = "删除快捷入口["+name+"]失败"; 
					top.$.jBox.tip(msg,"error",{persistent:true,opacity:0});
				}
			}
		});
	});	
}

//进入页面加载快捷入口数据
function getQuickEntry() {
	$.ajax({
	url:ctx +'/sys/modules/sysQuickEntry/getQuickEntry', 
	type:"POST",
	dataType:"json",
	data:{}, 
	async:false,
	success:function(data) {
		console.log("==getQuickEntry=="+data);
		for(var i=0; i<data.length; i++) {
    		var img = data[i].img;
    		var imgCss = "";
    		if("r1.png"==img){
    			imgCss = "1";
    		}else if("r2.png"==img){
    			imgCss = "4";
    		
    		}else if("r3.png"==img){
    			imgCss = "5";
    			
    		}else{
    			imgCss = "1";
    		}
	    	var str = "<li class=\"r1-1\" id=\""+i+"\"><input type=\"hidden\" id=\""+data[i].id+"\" value=\""+data[i].path+"\" \\><div class=\"r1-1-"+imgCss+"\"></div><div class=\"r1-1-2\">"+data[i].pmenuName+"</div><div class=\"r1-1-3\"><div class=\"y1\"></div><div id=\""+data[i].menuId+"\" class=\"y2\">"+data[i].menuName+"</div><div class=\"y3\">&#10006</div></div></li>"
	    	$("#addMore").before(str);
		}
		hoverCloseImg();
	}});	
}

//为快捷入口绑定事件
function hoverCloseImg(){
	//快捷入口添加点击事件
	$("li[class='r1-1']").click(function(){
		var path = $(this).children('input').val();
		if(path!=undefined){
			parent.window.openMenu(path);
		}
	});	
	//快捷入口添加hover事件显示和隐藏删除图标
	$(".r1-1").hover(function(){
		$(this).children("div").children("div .y3").show();
	},function(){
		$(this).children("div").children("div .y3").hide();
	});
	//删除图标点击方法
	$(".y3").click(function(event){
		var id=$(this).parent().siblings("input").attr("id");
		var name=$(this).siblings("div .y2").text();
		delMenu(id,name);
		event.stopPropagation();
	});
}

//进入页面加载第新一条流程信息
function getLatestFlowInfo() {
	$.ajax({
	url:ctx +'/sys/flow/flow/getLatestFlowInfo', 
	type:"POST",
	dataType:"json",
	data:{}, 
	async:true,
	success:function(data) {
		console.log("==getLatestFlowInfo=="+data);
		var str = "";
		//最后一步的颜色标志
		var colorFlag = false;
		if(data.length == 0){
			var noData="<div class='no-data-1'></div>"
			$("#myTask").append(noData);
		}
		for(var i=0; i<data.length; i++) {
			var stat = data[i].stat;
			var title = "";
			var finishFlag = "";
			if(stat=="03"||stat=="05"||stat=="07"){
				//审核不通过
				title = "审核不通过";
				finishFlag = "已完成";
			}else if(stat=="02"||stat=="04"||stat=="06"){
				//审核通过
				title = "审核通过";
				finishFlag = "已完成";
			}else{
				//审核中
				title = "审核中";
				finishFlag = "未完成";
			}
			var begTime = data[i].begTime.trim();
			var endTime = data[i].endTime.trim();
			var times;
			if(endTime.length==0){
				endTime = new Date();
				times = endTime.getTime() - new Date(begTime).getTime();
			}else{
				times = new Date(endTime).getTime() - new Date(begTime).getTime();
			}
        	var dayNum=Math.floor(times/(24*3600*1000));
        	var leave=times%(24*3600*1000);    //计算天数后剩余的毫秒数
			var hours=Math.floor(leave/(3600*1000));
			var timeInfo = dayNum+"天"+hours+"小时";
			if(i==0){
    			str += "<li class=\"step-start step-done\"><div class=\"ui-step-line\"></div><div class=\"ui-step-cont\"><span onmouseover=\"over('div1')\" onmouseleave=\"leave('div1')\" class=\"ui-step-cont-number\">1</span><span class=\"ui-step-cont-text\">发起流程</span></div><div class=\"s1\">"+data[i].name+"<br>"+data[i].begTime+"</div><div class=\"w1\" style=\"display:none\"><div class=\"w1-1\"><div class=\"w1-2\">"+data[i].name+"</div><div class=\"w1-3\"><div class=\"d1\"></div>"+finishFlag+"</div><div class=\"w1-4\">耗时："+timeInfo+"</div></div></div></li>";
    		}else if(i==1){
    			str += "<li class=\"step-active\"><div class=\"ui-step-line\"></div><div class=\"ui-step-cont\"><span onmouseover=\"over('div2')\" onmouseleave=\"leave('div2')\" class=\"ui-step-cont-number\">2</span><span class=\"ui-step-cont-text\">"+title+"</span></div><div title=\""+data[i].name+"\" class=\"s2\">"+data[i].name+"<br>"+data[i].endTime+"</div><div class=\"w2\" style=\"display: none;\"><div class=\"w1-1\"><div class=\"w1-2\">"+data[i].name+"</div><div class=\"w1-3\"><div class=\"d1\"></div>"+finishFlag+"</div><div class=\"w1-4\">耗时："+timeInfo+"</div></div></div></li>";
    		}else if(i==2){
    			if(stat!="00"&&stat!="01"){
    				colorFlag=true;
    			}
    			str += "<li class=\"step-end\"><div class=\"ui-step-line\"></div><div class=\"ui-step-cont\"><span onmouseover=\"over('div3')\" onmouseleave=\"leave('div3')\" class=\"ui-step-cont-number\">3</span><span class=\"ui-step-cont-text\">流程完成</span></div><div title=\""+data[i].name+"\" class=\"s3\">"+data[i].name+"<br>"+data[i].endTime+"</div><div class=\"w3\" style=\"display:none\"><div class=\"w1-1\"><div class=\"w1-2\">"+data[i].name+"</div><div class=\"w1-3\"><div class=\"d1\"></div>"+finishFlag+"</div><div class=\"w1-4\">耗时："+timeInfo+"</div></div></div></li>";
    		}
		}
		$("#myTask").append(str);
		if(colorFlag){
			var backgroundColor = "#e0e0e0";
			if(ctxTheme=="green"){
				backgroundColor = "#02c171";
			}else if(ctxTheme=="tech"){
				backgroundColor = "#199df3";
			}
			$(".step-end .ui-step-cont-number").css({"background-color": backgroundColor});
			$(".step-active .ui-step-line").css({"background-color": backgroundColor});
		}
	}});	
}

//进入页面加载公告信息数据
function getNoticeList() {
	$.ajax({
	url:ctx +'/sys/modules/sysNotice/getNoticeList', 
	type:"POST",
	dataType:"json",
	data:{}, 
	async:true,
	success:function(data) {
		console.log("==getNoticeList=="+data);
		var now = new Date();
		var newFlag = "";
		if(data.length == 0){
			var noData="<div class='no-data-2'></div>";
	    	$("#noticeData").append(noData);
		}
		for(var i=0; i<data.length; i++) {
			var id = data[i].id;
			var date = data[i].date;
			var title = getShortTitle(data[i].title,40);
			var dayNum = DateMinus(date,now);
			var dataDate = data[i].date.substring(5,10)
			//new图标
			if(dayNum<8){
				newFlag = "x1-3";
			}else{
				newFlag = "";
			}
	    	var str = "<li class=\"x1\"><div class=\"x1-1\"></div><div class=\"x1-2\"><a href=\"JavaScript:void(0);\" onclick=\"noticeDetail('"+id+"')\">"+title+"</a></div><div class=\""+newFlag+"\"></div><div class=\"x1-4\">"+dataDate+"</div></li>"
	    	$("#noticeData").append(str);
		}
	}});	
}

//查看公告信息详情
function noticeDetail(id){
	$.session.set('id',id);
	parent.window.openMenu("平台管理->系统设置->公告消息");
}

//获取流程列表
function getFlowList(){
	$.ajax({
	url:ctx +'/sys/flow/flow/list', 
	type:"POST",
	dataType:"json",
	data:{start:1,pageSize:10}, 
	async:true,
	success:function(data) {
		console.log("==getFlowList=="+data);
		if(data.returnCode!==undefined && "0000"!=data.returnCode){ 
			console.log("==getFlowList error==");
		}else {
			var now = new Date();
			var topFlag="";
			for(var i = 0 ; i < data.dataSetResult.length; i++){
				if(data.dataSetResult[i].totalCount == 0){
					var noData="<div class='no-data-3'></div>"
	    			$("#flowData").append(noData);
				}
				if(data.dataSetResult[i].data != "[]"){
					for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
						var flow = data.dataSetResult[i].data[j];
						var id = flow.id;
						var stat = flow.flowStat;
						var title = getShortTitle(flow.infoTitle,35);
						var crtTime = flow.crtTime.substring(5,10);
						var action = flow.action;
						var css="";
						var flowTitle="";
						if(stat=="03"||stat=="05"||stat=="07"){
							//审批不通过
							css = "sz3";
							flowTitle = "【不通过】";
						}else if(stat=="02"||stat=="04"||stat=="06"){
							//审批通过
							css = "sz2";
							flowTitle = "【已通过】";
						}else{
							//审批中
							css = "sz1";
							flowTitle = "【待审批】";
						}
						//top图标
						var date = flow.crtTime;
						var days = DateMinus(date,now);
						if(days<8){
							topFlag = "l1-3";
						}else{
							topFlag = "";
						}
						/*20220307 mod by chenyl for 修复流程待审批页面跳转处理*/
						if(action.indexOf('审核')==-1){
							// 不存在审核按钮默认为详情
							stat = '02';
						}
						var str = "<li class=\"l1\"><div class=\"l1-1\"><span class=\""+css+"\">"+flowTitle+"</span></div><div class=\"l1-2\"><a href=\"JavaScript:void(0);\" onclick=\"flowDetail('"+id+"','"+stat+"')\">"+title+"</a></div><div class=\""+topFlag+"\"></div><div class=\"l1-4\">"+crtTime+"</div></li>"
						$("#flowData").append(str);
					}
				}
			}
		}
	}});	
}

//查看流程信息详情
function flowDetail(id, flowStat){
	$.session.set('id',id);
	$.session.set('flowStat', flowStat)
	parent.window.openMenu("平台管理->流程管理->流程实例");
}

//进入页面加载当前用户信息
function getUserInfo() {
	$.ajax({
	url:ctx +'/sys/modules/sysNotice/getUserInfo', 
	type:"POST",
	dataType:"json",
	data:{}, 
	async:true,
	success:function(data) {
		console.log("==getUserInfo=="+data);
		var now = new Date();
		var newFlag = "";
		for(var i=0; i<data.length; i++) {
			var id = data[i].id;
			var name = data[i].name;
			var roleName = data[i].roleName;
			var userPhoto = data[i].userPhoto;
			var brchName = data[i].brchName;
			var tntName = data[i].tntName;
			var legaName = data[i].legaName;
			var loginIp = data[i].loginIp;
			var loginDate = data[i].loginDate;
			if(userPhoto.length > 0){
				$("#userPhoto").attr("src",userPhoto);
			}
			$("#userName").html("用户名称&nbsp;&nbsp;&nbsp;"+name);
			$("#roleName").attr("title",roleName).html("权&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限&nbsp;&nbsp;&nbsp;"+roleName);
			$("#brchName").html("归属部门&nbsp;&nbsp;&nbsp;"+brchName);
			$("#tntName").html("归属租户&nbsp;&nbsp;&nbsp;"+tntName);
			$("#legaName").html("归属法人&nbsp;&nbsp;&nbsp;"+legaName);
			$("#loginIp").html("登录&nbsp;&nbsp;&nbsp;IP&nbsp;&nbsp;&nbsp;"+loginIp);
			$("#loginDate").html("登录时间&nbsp;&nbsp;&nbsp;"+loginDate);
		}
	}});	
}

//获取min到max之间的整数
function GetRandomNum(min, max) {
    var Range = max - min;
    var Rand = Math.random();
    return(min + Math.round(Rand * Range));
}

//鼠标悬停方法
function over(obj){
	if(obj=="div3"){
		$(".w3").show();
	}else if(obj=="div2"){
		$(".w2").show();
	}else{
		$(".w1").show();
	}
}

//鼠标离开方法
function leave(obj){
	if(obj=="div3"){
		$(".w3").hide();
	}else if(obj=="div2"){
		$(".w2").hide();
	}else{
		$(".w1").hide();
	}	
}

//获取2个日期相差多少天
function DateMinus(beginDate,endDate){
	var beginDate = new Date(beginDate); 
  　　var endDate = new Date(endDate); 
  　　var dayNum = endDate.getTime() - beginDate.getTime(); 
  　　var day = parseInt(dayNum / (1000 * 60 * 60 * 24)); 
  　　return day; 
}

//截取制定长度的字符串，中文算2，字母等算1
function getShortTitle(obj,length){
	var len = 0;
	var temp = 0;
	if(!obj){
		return;
	}
	for(var i=0;i<obj.length;i++){
		var s = obj.charAt(i);
		if(s.match(/[^\x00-\xff]/ig) != null){
			len += 2;
		}else{
			len += 1;
		}
		if(len > length){
			temp = i;
			break;
		}
	}
	if(temp > 0){
		return obj.substring(0,temp)+"...";
	}else{
		return obj;
	}
}

function GetCurrentDate() {
 	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var date = d.getDate();
  	var week = d.getDay();
  	var hours = d.getHours();
  	var minutes = d.getMinutes();
	var seconds = d.getSeconds();
	var ms = d.getMilliseconds();
	var curDateTime = year;
	if (month > 9)
    	curDateTime = curDateTime + "年" + month;
    else
    	curDateTime = curDateTime + "年0" + month;
    if (date > 9)
    	curDateTime = curDateTime + "月" + date + "日";
    else
    	curDateTime = curDateTime + "月0" + date + "日";
    if (hours > 9)
    	curDateTime = curDateTime + " " + hours;
    else
    	curDateTime = curDateTime + " 0" + hours;
    if (minutes > 9)
    	curDateTime = curDateTime + ":" + minutes;
    else
    	curDateTime = curDateTime + ":0" + minutes;
  	if (seconds > 9)
    	curDateTime = curDateTime + ":" + seconds;
  	else
    	curDateTime = curDateTime + ":0" + seconds;
  	var weekday = "";
  	if (week == 0)
    	weekday = "星期日";
  	else if (week == 1)
    	weekday = "星期一";
  	else if (week == 2)
    	weekday = "星期二";
  	else if (week == 3)
   	 	weekday = "星期三";
  	else if (week == 4)
    	weekday = "星期四";
  	else if (week == 5)
   		weekday = "星期五";
 	else if (week == 6)
    	weekday = "星期六";
  	curDateTime = curDateTime + " " + weekday;
  	return curDateTime;
}

/*var CalendarData=new Array(20);
var madd=new Array(12);
var TheDate=new Date();
var tgString="甲乙丙丁戊己庚辛壬癸";
var dzString="子丑寅卯辰巳午未申酉戌亥";
var numString="一二三四五六七八九十";
var monString="正二三四五六七八九十冬腊";
var weekString="日一二三四五六";
var sx="鼠牛虎兔龙蛇马羊猴鸡狗猪";
var cYear;
var cMonth;
var cDay;
var cHour;
var cDateString;
var DateString;
//var Browser=navigator.appName;

function init(){
	CalendarData[0]=0x41A95;
    CalendarData[1]=0xD4A;
    CalendarData[2]=0xDA5;
    CalendarData[3]=0x20B55;
    CalendarData[4]=0x56A;
    CalendarData[5]=0x7155B;
    CalendarData[6]=0x25D;
    CalendarData[7]=0x92D;
    CalendarData[8]=0x5192B;
    CalendarData[9]=0xA95;
    CalendarData[10]=0xB4A;
    CalendarData[11]=0x416AA;
    CalendarData[12]=0xAD5;
    CalendarData[13]=0x90AB5;
    CalendarData[14]=0x4BA;
    CalendarData[15]=0xA5B;
    CalendarData[16]=0x60A57;
    CalendarData[17]=0x52B;
    CalendarData[18]=0xA93;
    CalendarData[19]=0x40E95;
    madd[0]=0;
    madd[1]=31;
    madd[2]=59;
    madd[3]=90;
    madd[4]=120;
    madd[5]=151;
    madd[6]=181;
    madd[7]=212;
    madd[8]=243;
    madd[9]=273;
    madd[10]=304;
    madd[11]=334;
}

function GetBit(m,n){
	return (m>>n)&1;
}

function e2c(){
    var totalmnk;
    var isEnd=false;
    var tmp=TheDate.getYear();
    if (tmp<1900)   tmp+=1900;
    total=(tmp-2001)*365
      +Math.floor((tmp-2001)/4)
      +madd[TheDate.getMonth()]
      +TheDate.getDate()
      -23;
    if (TheDate.getYear()%4==0&&TheDate.getMonth()>1)
    	total++;
    for(m=0;;m++){
    	k=(CalendarData[m]<0xfff)?11:12;
	    for(n=k;n>=0;n--){
	    	if(total<=29+GetBit(CalendarData[m],n)){
		        isEnd=true;
		        break;
	      	}
	      	total=total-29-GetBit(CalendarData[m],n);
	    }
	    if(isEnd)break;
    }
    cYear=2001 + m;
    cMonth=k-n+1;
    cDay=total;
    if(k==12){
	    if(cMonth==Math.floor(CalendarData[m]/0x10000)+1)
	    	cMonth=1-cMonth;
	    if(cMonth>Math.floor(CalendarData[m]/0x10000)+1)
	      	cMonth--;
    }
    cHour=Math.floor((TheDate.getHours()+3)/2);
}*/

function GetLunarDate() {
	console.log("--GetLunarDate--");
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var date = d.getDate();
	var dd = calendarFormatter.solar2lunar(year, month, date)
	return dd;
}

/**
 * 大屏请求跳转
 */
function screenReqJump() {
	// var screenReqFlag = document.getElementById('screenReqFlag').value;
	let screenReqFlag=$("#screenReqFlag").val();
	console.log("screenReqFlag:"+ screenReqFlag);
	if (screenReqFlag === 'true') {
		var startTime = document.getElementById('startTime').value;
		var endTime = document.getElementById('endTime').value;
		var glbl_biz_swfno = document.getElementById('glbl_biz_swfno').value;
		var prvpt_cenmd_no = document.getElementById('prvpt_cenmd_no').value;
		var prvpt_mcrsv_no = document.getElementById('prvpt_mcrsv_no').value;
		var cnsmr_mcrsv_swfno = document.getElementById('cnsmr_mcrsv_swfno').value;
		var transTmMin = document.getElementById('transTmMin').value;
		var transTmMax = document.getElementById('transTmMax').value;
		var mcrsv_fnct_intfc_ecd = document.getElementById('mcrsv_fnct_intfc_ecd').value;
		var trd_dlwth_retn_cd = document.getElementById('trd_dlwth_retn_cd').value;
		var scene_idcd = document.getElementById('scene_idcd').value;
		var biz_lunch_org_ecd = document.getElementById('biz_lunch_org_ecd').value;
		var chnl_typ_cd = document.getElementById('chnl_typ_cd').value;
		$.session.set("screenReqFlag",screenReqFlag);
		$.session.set("startTime",startTime);
		$.session.set("endTime",endTime);
		$.session.set("glbl_biz_swfno",glbl_biz_swfno);
		$.session.set("prvpt_cenmd_no",prvpt_cenmd_no);
		$.session.set("prvpt_mcrsv_no",prvpt_mcrsv_no);
		$.session.set("cnsmr_mcrsv_swfno",cnsmr_mcrsv_swfno);
		$.session.set("transTmMin",transTmMin);
		$.session.set("transTmMax",transTmMax);
		$.session.set("mcrsv_fnct_intfc_ecd",mcrsv_fnct_intfc_ecd);
		$.session.set("trd_dlwth_retn_cd",trd_dlwth_retn_cd);
		$.session.set("scene_idcd",scene_idcd);
		$.session.set("biz_lunch_org_ecd",biz_lunch_org_ecd);
		$.session.set("chnl_typ_cd",chnl_typ_cd);
		openMenu("日志监控运营->数据查询->交易数据查询");
	}
}

/*function GetLunarDate(){
	var tmp="";
	tmp+=tgString.charAt((cYear-4)%10);   //年干
	tmp+=dzString.charAt((cYear-4)%12);   //年支
	tmp+="年(";
	tmp+=sx.charAt((cYear-4)%12);
	tmp+=") ";
	if(cMonth<1){
		tmp+="闰";
		tmp+=monString.charAt(-cMonth-1);
	}else
		tmp+=monString.charAt(cMonth-1);
		tmp+="月";
		tmp+=(cDay<11)?"初":((cDay<20)?"十":((cDay<30)?"廿":"卅"));
	if(cDay%10!=0||cDay==10)
		tmp+=numString.charAt((cDay-1)%10);
		tmp+=" ";
	if(cHour==13)tmp+="夜";
		tmp+=dzString.charAt((cHour-1)%12);
		tmp+="时";
	    cDateString=tmp;
    return tmp;
}
init();
e2c();*/

/**
 * 
 * 农历1900-2100的润大小信息表
 * 
 * @Array Of Property
 * @return Hex
 */
var lunarInfo = [
		0x04bd8,
		0x04ae0,
		0x0a570,
		0x054d5,
		0x0d260,
		0x0d950,
		0x16554,
		0x056a0,
		0x09ad0,
		0x055d2, // 1900-1909
		0x04ae0,
		0x0a5b6,
		0x0a4d0,
		0x0d250,
		0x1d255,
		0x0b540,
		0x0d6a0,
		0x0ada2,
		0x095b0,
		0x14977, // 1910-1919
		0x04970,
		0x0a4b0,
		0x0b4b5,
		0x06a50,
		0x06d40,
		0x1ab54,
		0x02b60,
		0x09570,
		0x052f2,
		0x04970, // 1920-1929
		0x06566,
		0x0d4a0,
		0x0ea50,
		0x06e95,
		0x05ad0,
		0x02b60,
		0x186e3,
		0x092e0,
		0x1c8d7,
		0x0c950, // 1930-1939
		0x0d4a0,
		0x1d8a6,
		0x0b550,
		0x056a0,
		0x1a5b4,
		0x025d0,
		0x092d0,
		0x0d2b2,
		0x0a950,
		0x0b557, // 1940-1949
		0x06ca0,
		0x0b550,
		0x15355,
		0x04da0,
		0x0a5b0,
		0x14573,
		0x052b0,
		0x0a9a8,
		0x0e950,
		0x06aa0, // 1950-1959
		0x0aea6,
		0x0ab50,
		0x04b60,
		0x0aae4,
		0x0a570,
		0x05260,
		0x0f263,
		0x0d950,
		0x05b57,
		0x056a0, // 1960-1969
		0x096d0,
		0x04dd5,
		0x04ad0,
		0x0a4d0,
		0x0d4d4,
		0x0d250,
		0x0d558,
		0x0b540,
		0x0b6a0,
		0x195a6, // 1970-1979
		0x095b0,
		0x049b0,
		0x0a974,
		0x0a4b0,
		0x0b27a,
		0x06a50,
		0x06d40,
		0x0af46,
		0x0ab60,
		0x09570, // 1980-1989
		0x04af5,
		0x04970,
		0x064b0,
		0x074a3,
		0x0ea50,
		0x06b58,
		0x05ac0,
		0x0ab60,
		0x096d5,
		0x092e0, // 1990-1999
		0x0c960,
		0x0d954,
		0x0d4a0,
		0x0da50,
		0x07552,
		0x056a0,
		0x0abb7,
		0x025d0,
		0x092d0,
		0x0cab5, // 2000-2009
		0x0a950,
		0x0b4a0,
		0x0baa4,
		0x0ad50,
		0x055d9,
		0x04ba0,
		0x0a5b0,
		0x15176,
		0x052b0,
		0x0a930, // 2010-2019
		0x07954, 0x06aa0,
		0x0ad50,
		0x05b52,
		0x04b60,
		0x0a6e6,
		0x0a4e0,
		0x0d260,
		0x0ea65,
		0x0d530, // 2020-2029
		0x05aa0, 0x076a3, 0x096d0,
		0x04afb,
		0x04ad0,
		0x0a4d0,
		0x1d0b6,
		0x0d250,
		0x0d520,
		0x0dd45, // 2030-2039
		0x0b5a0, 0x056d0, 0x055b2, 0x049b0,
		0x0a577,
		0x0a4b0,
		0x0aa50,
		0x1b255,
		0x06d20,
		0x0ada0, // 2040-2049
		0x14b63, 0x09370, 0x049f8, 0x04970, 0x064b0,
		0x168a6,
		0x0ea50,
		0x06b20,
		0x1a6c4,
		0x0aae0, // 2050-2059
		0x0a2e0, 0x0d2e3, 0x0c960, 0x0d557, 0x0d4a0, 0x0da50,
		0x05d55,
		0x056a0,
		0x0a6d0,
		0x055d4, // 2060-2069
		0x052d0, 0x0a9b8, 0x0a950, 0x0b4a0, 0x0b6a6, 0x0ad50, 0x055a0,
		0x0aba4,
		0x0a5b0,
		0x052b0, // 2070-2079
		0x0b273, 0x06930, 0x07337, 0x06aa0, 0x0ad50, 0x14b55, 0x04b60, 0x0a570,
		0x054e4,
		0x0d160, // 2080-2089
		0x0e968, 0x0d520, 0x0daa0, 0x16aa6, 0x056d0, 0x04ae0, 0x0a9d4, 0x0a2d0,
		0x0d150, 0x0f252, // 2090-2099
		0x0d520]; // 2100
var solarMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
/**
 * 天干地支之天干速查表
 * @Array Of Property trans['甲','乙','丙','丁','戊','己','庚','辛','壬','癸']
 * @return Cn string
 */
var Gan = ['\u7532', '\u4e59', '\u4e19', '\u4e01', '\u620a', '\u5df1',
		'\u5e9a', '\u8f9b', '\u58ec', '\u7678']
/**
 * 天干地支之地支速查表
 * @Array Of Property
 * @trans['子','丑','寅','卯','辰','巳','午','未','申','酉','戌','亥']
 * @return Cn string
 */
var Zhi = ['\u5b50', '\u4e11', '\u5bc5', '\u536f', '\u8fb0', '\u5df3',
		'\u5348', '\u672a', '\u7533', '\u9149', '\u620c', '\u4ea5']
/**
 * 24节气速查表
 * @Array Of Property
 * @trans['小寒','大寒','立春','雨水','惊蛰','春分','清明','谷雨','立夏','小满','芒种','夏至','小暑','大暑','立秋','处暑','白露','秋分','寒露','霜降','立冬','小雪','大雪','冬至']
 * @return Cn string
 */
var solarTerm = ['\u5c0f\u5bd2', '\u5927\u5bd2', '\u7acb\u6625',
		'\u96e8\u6c34', '\u60ca\u86f0', '\u6625\u5206', '\u6e05\u660e',
		'\u8c37\u96e8', '\u7acb\u590f', '\u5c0f\u6ee1', '\u8292\u79cd',
		'\u590f\u81f3', '\u5c0f\u6691', '\u5927\u6691', '\u7acb\u79cb',
		'\u5904\u6691', '\u767d\u9732', '\u79cb\u5206', '\u5bd2\u9732',
		'\u971c\u964d', '\u7acb\u51ac', '\u5c0f\u96ea', '\u5927\u96ea',
		'\u51ac\u81f3']
/**
 * 1900-2100各年的24节气日期速查表
 * @Array Of Property
 * @return 0x string For splice
 */
var sTermInfo = ['9778397bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e', '97bcf97c3598082c95f8c965cc920f',
		'97bd0b06bdb0722c965ce1cfcc920f', 'b027097bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e',
		'97bcf97c359801ec95f8c965cc920f', '97bd0b06bdb0722c965ce1cfcc920f',
		'b027097bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e', '97bcf97c359801ec95f8c965cc920f',
		'97bd0b06bdb0722c965ce1cfcc920f',
		'b027097bd097c36b0b6fc9274c91aa', '9778397bd19801ec9210c965cc920e',
		'97b6b97bd19801ec95f8c965cc920f',
		'97bd09801d98082c95f8e1cfcc920f', '97bd097bd097c36b0b6fc9210c8dc2',
		'9778397bd197c36c9210c9274c91aa',
		'97b6b97bd19801ec95f8c965cc920e', '97bd09801d98082c95f8e1cfcc920f',
		'97bd097bd097c36b0b6fc9210c8dc2',
		'9778397bd097c36c9210c9274c91aa', '97b6b97bd19801ec95f8c965cc920e',
		'97bcf97c3598082c95f8e1cfcc920f',
		'97bd097bd097c36b0b6fc9210c8dc2', '9778397bd097c36c9210c9274c91aa',
		'97b6b97bd19801ec9210c965cc920e',
		'97bcf97c3598082c95f8c965cc920f', '97bd097bd097c35b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e', '97bcf97c3598082c95f8c965cc920f',
		'97bd097bd097c35b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa', '97b6b97bd19801ec9210c965cc920e',
		'97bcf97c359801ec95f8c965cc920f',
		'97bd097bd097c35b0b6fc920fb0722', '9778397bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e',
		'97bcf97c359801ec95f8c965cc920f', '97bd097bd097c35b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e', '97bcf97c359801ec95f8c965cc920f',
		'97bd097bd07f595b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9210c8dc2', '9778397bd19801ec9210c9274c920e',
		'97b6b97bd19801ec95f8c965cc920f',
		'97bd07f5307f595b0b0bc920fb0722', '7f0e397bd097c36b0b6fc9210c8dc2',
		'9778397bd097c36c9210c9274c920e',
		'97b6b97bd19801ec95f8c965cc920f', '97bd07f5307f595b0b0bc920fb0722',
		'7f0e397bd097c36b0b6fc9210c8dc2',
		'9778397bd097c36c9210c9274c91aa', '97b6b97bd19801ec9210c965cc920e',
		'97bd07f1487f595b0b0bc920fb0722',
		'7f0e397bd097c36b0b6fc9210c8dc2', '9778397bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e',
		'97bcf7f1487f595b0b0bb0b6fb0722', '7f0e397bd097c35b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e', '97bcf7f1487f595b0b0bb0b6fb0722',
		'7f0e397bd097c35b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa', '97b6b97bd19801ec9210c965cc920e',
		'97bcf7f1487f531b0b0bb0b6fb0722',
		'7f0e397bd097c35b0b6fc920fb0722', '9778397bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c965cc920e',
		'97bcf7f1487f531b0b0bb0b6fb0722', '7f0e397bd07f595b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa',
		'97b6b97bd19801ec9210c9274c920e', '97bcf7f0e47f531b0b0bb0b6fb0722',
		'7f0e397bd07f595b0b0bc920fb0722',
		'9778397bd097c36b0b6fc9210c91aa', '97b6b97bd197c36c9210c9274c920e',
		'97bcf7f0e47f531b0b0bb0b6fb0722',
		'7f0e397bd07f595b0b0bc920fb0722', '9778397bd097c36b0b6fc9210c8dc2',
		'9778397bd097c36c9210c9274c920e',
		'97b6b7f0e47f531b0723b0b6fb0722', '7f0e37f5307f595b0b0bc920fb0722',
		'7f0e397bd097c36b0b6fc9210c8dc2',
		'9778397bd097c36b0b70c9274c91aa', '97b6b7f0e47f531b0723b0b6fb0721',
		'7f0e37f1487f595b0b0bb0b6fb0722',
		'7f0e397bd097c35b0b6fc9210c8dc2', '9778397bd097c36b0b6fc9274c91aa',
		'97b6b7f0e47f531b0723b0b6fb0721',
		'7f0e27f1487f595b0b0bb0b6fb0722', '7f0e397bd097c35b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa',
		'97b6b7f0e47f531b0723b0b6fb0721', '7f0e27f1487f531b0b0bb0b6fb0722',
		'7f0e397bd097c35b0b6fc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa', '97b6b7f0e47f531b0723b0b6fb0721',
		'7f0e27f1487f531b0b0bb0b6fb0722',
		'7f0e397bd097c35b0b6fc920fb0722', '9778397bd097c36b0b6fc9274c91aa',
		'97b6b7f0e47f531b0723b0b6fb0721',
		'7f0e27f1487f531b0b0bb0b6fb0722', '7f0e397bd07f595b0b0bc920fb0722',
		'9778397bd097c36b0b6fc9274c91aa',
		'97b6b7f0e47f531b0723b0787b0721', '7f0e27f0e47f531b0b0bb0b6fb0722',
		'7f0e397bd07f595b0b0bc920fb0722',
		'9778397bd097c36b0b6fc9210c91aa', '97b6b7f0e47f149b0723b0787b0721',
		'7f0e27f0e47f531b0723b0b6fb0722',
		'7f0e397bd07f595b0b0bc920fb0722', '9778397bd097c36b0b6fc9210c8dc2',
		'977837f0e37f149b0723b0787b0721',
		'7f07e7f0e47f531b0723b0b6fb0722', '7f0e37f5307f595b0b0bc920fb0722',
		'7f0e397bd097c35b0b6fc9210c8dc2',
		'977837f0e37f14998082b0787b0721', '7f07e7f0e47f531b0723b0b6fb0721',
		'7f0e37f1487f595b0b0bb0b6fb0722',
		'7f0e397bd097c35b0b6fc9210c8dc2', '977837f0e37f14998082b0787b06bd',
		'7f07e7f0e47f531b0723b0b6fb0721',
		'7f0e27f1487f531b0b0bb0b6fb0722', '7f0e397bd097c35b0b6fc920fb0722',
		'977837f0e37f14998082b0787b06bd',
		'7f07e7f0e47f531b0723b0b6fb0721', '7f0e27f1487f531b0b0bb0b6fb0722',
		'7f0e397bd097c35b0b6fc920fb0722',
		'977837f0e37f14998082b0787b06bd', '7f07e7f0e47f531b0723b0b6fb0721',
		'7f0e27f1487f531b0b0bb0b6fb0722',
		'7f0e397bd07f595b0b0bc920fb0722', '977837f0e37f14998082b0787b06bd',
		'7f07e7f0e47f531b0723b0b6fb0721',
		'7f0e27f1487f531b0b0bb0b6fb0722', '7f0e397bd07f595b0b0bc920fb0722',
		'977837f0e37f14998082b0787b06bd',
		'7f07e7f0e47f149b0723b0787b0721', '7f0e27f0e47f531b0b0bb0b6fb0722',
		'7f0e397bd07f595b0b0bc920fb0722',
		'977837f0e37f14998082b0723b06bd', '7f07e7f0e37f149b0723b0787b0721',
		'7f0e27f0e47f531b0723b0b6fb0722',
		'7f0e397bd07f595b0b0bc920fb0722', '977837f0e37f14898082b0723b02d5',
		'7ec967f0e37f14998082b0787b0721',
		'7f07e7f0e47f531b0723b0b6fb0722', '7f0e37f1487f595b0b0bb0b6fb0722',
		'7f0e37f0e37f14898082b0723b02d5',
		'7ec967f0e37f14998082b0787b0721', '7f07e7f0e47f531b0723b0b6fb0722',
		'7f0e37f1487f531b0b0bb0b6fb0722',
		'7f0e37f0e37f14898082b0723b02d5', '7ec967f0e37f14998082b0787b06bd',
		'7f07e7f0e47f531b0723b0b6fb0721',
		'7f0e37f1487f531b0b0bb0b6fb0722', '7f0e37f0e37f14898082b072297c35',
		'7ec967f0e37f14998082b0787b06bd',
		'7f07e7f0e47f531b0723b0b6fb0721', '7f0e27f1487f531b0b0bb0b6fb0722',
		'7f0e37f0e37f14898082b072297c35',
		'7ec967f0e37f14998082b0787b06bd', '7f07e7f0e47f531b0723b0b6fb0721',
		'7f0e27f1487f531b0b0bb0b6fb0722',
		'7f0e37f0e366aa89801eb072297c35', '7ec967f0e37f14998082b0787b06bd',
		'7f07e7f0e47f149b0723b0787b0721',
		'7f0e27f1487f531b0b0bb0b6fb0722', '7f0e37f0e366aa89801eb072297c35',
		'7ec967f0e37f14998082b0723b06bd',
		'7f07e7f0e47f149b0723b0787b0721', '7f0e27f0e47f531b0723b0b6fb0722',
		'7f0e37f0e366aa89801eb072297c35',
		'7ec967f0e37f14998082b0723b06bd', '7f07e7f0e37f14998083b0787b0721',
		'7f0e27f0e47f531b0723b0b6fb0722',
		'7f0e37f0e366aa89801eb072297c35', '7ec967f0e37f14898082b0723b02d5',
		'7f07e7f0e37f14998082b0787b0721',
		'7f07e7f0e47f531b0723b0b6fb0722', '7f0e36665b66aa89801e9808297c35',
		'665f67f0e37f14898082b0723b02d5',
		'7ec967f0e37f14998082b0787b0721', '7f07e7f0e47f531b0723b0b6fb0722',
		'7f0e36665b66a449801e9808297c35',
		'665f67f0e37f14898082b0723b02d5', '7ec967f0e37f14998082b0787b06bd',
		'7f07e7f0e47f531b0723b0b6fb0721',
		'7f0e36665b66a449801e9808297c35', '665f67f0e37f14898082b072297c35',
		'7ec967f0e37f14998082b0787b06bd',
		'7f07e7f0e47f531b0723b0b6fb0721', '7f0e26665b66a449801e9808297c35',
		'665f67f0e37f1489801eb072297c35',
		'7ec967f0e37f14998082b0787b06bd', '7f07e7f0e47f531b0723b0b6fb0721',
		'7f0e27f1487f531b0b0bb0b6fb0722']
/**
 * 数字转中文速查表
 * @Array Of Property
 * @trans ['日','一','二','三','四','五','六','七','八','九','十']
 * @return Cn string
 */
var nStr1 = ['\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94',
		'\u516d', '\u4e03', '\u516b', '\u4e5d', '\u5341']
/**
 * 日期转农历称呼速查表
 * @Array Of Property
 * @trans ['初','十','廿','卅']
 * @return Cn string
 */
var nStr2 = ['\u521d', '\u5341', '\u5eff', '\u5345']
/**
 * 月份转农历称呼速查表
 * @Array Of Property
 * @trans ['正','一','二','三','四','五','六','七','八','九','十','冬','腊']
 * @return Cn string
 */
var nStr3 = ['\u6b63', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d',
		'\u4e03', '\u516b', '\u4e5d', '\u5341', '\u51ac', '\u814a']
/**
 * 返回农历y年一整年的总天数
 * @param lunar
 *            Year
 * @return Number
 * @eg:var count = calendar.lYearDays(1987) ;//count=387
 */
function lYearDays(y) {
	var i
	var sum = 348
	for (i = 0x8000; i > 0x8; i >>= 1) {
		sum += (lunarInfo[y - 1900] & i) ? 1 : 0
	}
	return (sum + leapDays(y))
}

/**
 * 返回农历y年闰月是哪个月；若y年没有闰月 则返回0
 * @param lunar
 *            Year
 * @return Number (0-12)
 * @eg:var leapMonth = calendar.leapMonth(1987) ;//leapMonth=6
 */

function leapMonth(y) { // 闰字编码 \u95f0
	return (lunarInfo[y - 1900] & 0xf)
}

/**
 * 返回农历y年闰月的天数 若该年没有闰月则返回0
 * @param lunar
 *            Year
 * @return Number (0、29、30)
 * @eg:var leapMonthDay = calendar.leapDays(1987) ;//leapMonthDay=29
 */
function leapDays(y) {
	if (leapMonth(y)) {
		return ((lunarInfo[y - 1900] & 0x10000) ? 30 : 29)
	}
	return (0)
}

/**
 * 
 * 返回农历y年m月（非闰月）的总天数，计算m为闰月时的天数请使用leapDays方法
 * 
 * @param lunar
 *            Year
 * 
 * @return Number (-1、29、30)
 * 
 * @eg:var MonthDay = calendar.monthDays(1987,9) ;//MonthDay=29
 * 
 */

function monthDays(y, m) {
	if (m > 12 || m < 1) {
		return -1
	}// 月份参数从1至12，参数错误返回-1
	return ((lunarInfo[y - 1900] & (0x10000 >> m)) ? 30 : 29)

}

/**
 * 返回公历(!)y年m月的天数
 * @param solar
 *            Year
 * @return Number (-1、28、29、30、31)
 * @eg:var solarMonthDay = calendar.leapDays(1987) ;//solarMonthDay=30
 */

function solarDays(y, m) {
	if (m > 12 || m < 1) {
		return -1
	} // 若参数错误 返回-1
	var ms = m - 1
	if (ms === 1) { // 2月份的闰平规律测算后确认返回28或29
		return (((y % 4 === 0) && (y % 100 !== 0) || (y % 400 === 0)) ? 29 : 28)
	} else {
		return (solarMonth[ms])
	}
}

/**
 * 农历年份转换为干支纪年
 * @param lYear
 *            农历年的年份数
 * @return Cn string
 */

function toGanZhiYear(lYear) {
	var ganKey = (lYear - 3) % 10
	var zhiKey = (lYear - 3) % 12
	if (ganKey === 0)
		ganKey = 10 // 如果余数为0则为最后一个天干
	if (zhiKey === 0)
		zhiKey = 12 // 如果余数为0则为最后一个地支
	return Gan[ganKey - 1] + Zhi[zhiKey - 1]
}

/**
 * 传入offset偏移量返回干支
 * @param offset
 *            相对甲子的偏移量
 * @return Cn string
 */

function toGanZhi(offset) {
	return Gan[offset % 10] + Zhi[offset % 12]
}

/**
 * 传入公历(!)y年获得该年第n个节气的公历日期
 * @param y公历年(1900-2100)；n二十四节气中的第几个节气(1~24)；从n=1(小寒)算起
 * @return day Number
 * @eg:var _24 = calendar.getTerm(1987,3) ;//_24=4;意即1987年2月4日立春
 */

function getTerm(y, n) {
	if (y < 1900 || y > 2100) {
		return -1
	}
	if (n < 1 || n > 24) {
		return -1
	}
	var _table = sTermInfo[y - 1900]
	var _info = [
			parseInt('0x' + _table.substr(0, 5)).toString(),
			parseInt('0x' + _table.substr(5, 5)).toString(),
			parseInt('0x' + _table.substr(10, 5)).toString(),
			parseInt('0x' + _table.substr(15, 5)).toString(),
			parseInt('0x' + _table.substr(20, 5)).toString(),
			parseInt('0x' + _table.substr(25, 5)).toString()
	]
	var _calday = [
			_info[0].substr(0, 1),
			_info[0].substr(1, 2),
			_info[0].substr(3, 1),
			_info[0].substr(4, 2),
			_info[1].substr(0, 1),
			_info[1].substr(1, 2),
			_info[1].substr(3, 1),
			_info[1].substr(4, 2),
			_info[2].substr(0, 1),
			_info[2].substr(1, 2),
			_info[2].substr(3, 1),
			_info[2].substr(4, 2),
			_info[3].substr(0, 1),
			_info[3].substr(1, 2),
			_info[3].substr(3, 1),
			_info[3].substr(4, 2),
			_info[4].substr(0, 1),
			_info[4].substr(1, 2),
			_info[4].substr(3, 1),
			_info[4].substr(4, 2),
			_info[5].substr(0, 1),
			_info[5].substr(1, 2),
			_info[5].substr(3, 1),
			_info[5].substr(4, 2)
	]
	return parseInt(_calday[n - 1])
}

/**
 * 传入农历数字月份返回汉语通俗表示法
 * @param lunar
 *            month
 * @return Cn string
 * @eg:var cnMonth = calendar.toChinaMonth(12) ;//cnMonth='腊月'
 * 
 */
function toChinaMonth(m) { // 月 => \u6708
	if (m > 12 || m < 1) {
		return -1
	} // 若参数错误 返回-1
	var s = nStr3[m - 1]
	s += '\u6708' // 加上月字
	return s
}

/**
 * 传入农历日期数字返回汉字表示法
 * @param lunar
 *            day
 * @return Cn string
 * @eg:var cnDay = calendar.toChinaDay(21) ;//cnMonth='廿一'
 */

function toChinaDay(d) { // 日 => \u65e5
	var s
	switch (d) {
		case 10 :
			s = '\u521d\u5341'
			break
		case 20 :
			s = '\u4e8c\u5341'
			break
		case 30 :
			s = '\u4e09\u5341'
			break
		default :
			s = nStr2[Math.floor(d / 10)]
			s += nStr1[d % 10]
	}
	return (s)
}

/**
 * 
 * 传入阳历年月日获得详细的公历、农历object信息 <=>JSON
 * @param y
 *            solar year
 * @param m
 *            solar month
 * @param d
 *            solar day
 * @return JSON object
 * @eg:console.log(calendar.solar2lunar(1987,11,01));
 * 
 */
function solar2lunar(y, m, d) { // 参数区间1900.1.31~2100.12.31
	// 年份限定、上限
	if (y < 1900 || y > 2100) {
		return -1 // undefined转换为数字变为NaN
	}
	// 公历传参最下限
	if (y === 1900 && m === 1 && d < 31) {
		return -1
	}
	// 未传参 获得当天
	var objDate = null
	if (!y) {
		objDate = new Date()
	} else {
		objDate = new Date(y, parseInt(m) - 1, d)
	}
	var i
	var leap = 0
	var temp = 0
	// 修正ymd参数
	y = objDate.getFullYear()
	m = objDate.getMonth() + 1
	d = objDate.getDate()
	var offset = (Date.UTC(objDate.getFullYear(), objDate.getMonth(), objDate
					.getDate()) - Date.UTC(1900, 0, 31))
			/ 86400000
	for (i = 1900; i < 2101 && offset > 0; i++) {
		temp = lYearDays(i)
		offset -= temp
	}
	if (offset < 0) {
		offset += temp;
		i--
	}
	// 是否今天
	var isTodayObj = new Date()
	var isToday = false
	if (isTodayObj.getFullYear() === y && isTodayObj.getMonth() + 1 === m
			&& isTodayObj.getDate() === d) {
		isToday = true
	}
	// 星期几
	var nWeek = objDate.getDay()
	var cWeek = nStr1[nWeek]
	// 数字表示周几顺应天朝周一开始的惯例
	if (nWeek === 0) {
		nWeek = 7
	}
	// 农历年
	var year = i
	leap = leapMonth(i) // 闰哪个月
	var isLeap = false
	// 效验闰月
	for (i = 1; i < 13 && offset > 0; i++) {
		// 闰月
		if (leap > 0 && i === (leap + 1) && isLeap === false) {
			--i
			isLeap = true;
			temp = leapDays(year) // 计算农历闰月天数
		} else {
			temp = monthDays(year, i)// 计算农历普通月天数
		}
		// 解除闰月
		if (isLeap === true && i === (leap + 1)) {
			isLeap = false
		}
		offset -= temp
	}
	// 闰月导致数组下标重叠取反
	if (offset === 0 && leap > 0 && i === leap + 1) {
		if (isLeap) {
			isLeap = false

		} else {
			isLeap = true;
			--i
		}
	}
	if (offset < 0) {
		offset += temp;
		--i
	}
	// 农历月
	var month = i
	// 农历日
	var day = offset + 1
	// 天干地支处理
	var sm = m - 1
	var gzY = toGanZhiYear(year)
	// 当月的两个节气
	// bugfix-2017-7-24 11:03:38 use lunar Year Param `y` Not `year`
	var firstNode = getTerm(y, (m * 2 - 1)) // 返回当月「节」为几日开始
	var secondNode = getTerm(y, (m * 2)) // 返回当月「节」为几日开始
	// 依据12节气修正干支月
	var gzM = toGanZhi((y - 1900) * 12 + m + 11)
	if (d >= firstNode) {
		gzM = toGanZhi((y - 1900) * 12 + m + 12)
	}
	// 传入的日期的节气与否
	var isTerm = false
	var Term = null
	if (firstNode === d) {
		isTerm = true
		Term = solarTerm[m * 2 - 2]
	}
	if (secondNode === d) {
		isTerm = true
		Term = solarTerm[m * 2 - 1]
	}
	// 日柱 当月一日与 1900/1/1 相差天数
	var dayCyclical = Date.UTC(y, sm, 1, 0, 0, 0, 0) / 86400000 + 25567 + 10
	var gzD = toGanZhi(dayCyclical + d - 1)
	var CnDate = (isLeap ? '\u95f0' : '') + toChinaMonth(month)
			+ toChinaDay(day);
	return CnDate;
}

var calendarFormatter = {
	// 传入阳历年月日获得详细的公历、农历object信息 <=>JSON
	solar2lunar : function(y, m, d) { // 参数区间1900.1.31~2100.12.31
		return solar2lunar(y, m, d)
	},

	/**
	 * 
	 * 传入农历年月日以及传入的月份是否闰月获得详细的公历、农历object信息 <=>JSON
	 * 
	 * @param y
	 *            lunar year
	 * 
	 * @param m
	 *            lunar month
	 * 
	 * @param d
	 *            lunar day
	 * 
	 * @param isLeapMonth
	 *            lunar month is leap or not.[如果是农历闰月第四个参数赋值true即可]
	 * 
	 * @return JSON object
	 * 
	 * @eg:console.log(calendar.lunar2solar(1987,9,10));
	 * 
	 */
	lunar2solar : function(y, m, d, isLeapMonth) { // 参数区间1900.1.31~2100.12.1
		isLeapMonth = !!isLeapMonth
		if (isLeapMonth && (leapMonth !== m)) {
			return -1
		}// 传参要求计算该闰月公历 但该年得出的闰月与传参的月份并不同
		if (y === 2100 && m === 12 && d > 1 || y === 1900 && m === 1 && d < 31) {
			return -1
		} // 超出了最大极限值
		var day = monthDays(y, m)
		var _day = day
		// bugFix 2016-9-25
		// if month is leap, _day use leapDays method
		if (isLeapMonth) {
			_day = leapDays(y, m)
		}
		if (y < 1900 || y > 2100 || d > _day) {
			return -1
		}// 参数合法性效验
		// 计算农历的时间差
		var offset = 0
		for (var i = 1900; i < y; i++) {
			offset += lYearDays(i)
		}
		var leap = 0
		var isAdd = false
		for (i = 1; i < m; i++) {
			leap = leapMonth(y)
			if (!isAdd) { // 处理闰月
				if (leap <= i && leap > 0) {
					offset += leapDays(y);
					isAdd = true
				}
			}
			offset += monthDays(y, i)
		}
		// 转换闰月农历 需补充该年闰月的前一个月的时差
		if (isLeapMonth) {
			offset += day
		}
		// 1900年农历正月一日的公历时间为1900年1月30日0时0分0秒(该时间也是本农历的最开始起始点)
		var stmap = Date.UTC(1900, 1, 30, 0, 0, 0);
		var calObj = new Date((offset + d - 31) * 86400000 + stmap);
		var cY = calObj.getUTCFullYear();
		var cM = calObj.getUTCMonth() + 1;
		var cD = calObj.getUTCDate();
		return solar2lunar(cY, cM, cD);
	}
}
