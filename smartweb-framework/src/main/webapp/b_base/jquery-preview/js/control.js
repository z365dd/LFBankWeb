$(function() {
	$('.preTitle1').mouseover(function() {
		if($(this).attr('State') == 1) {
			//图标下拉变十字
			$(this).find('.preDown').stop().fadeOut(100);
			$(this).find('.preCross').stop().fadeIn(100);
		} else {
			//小标题框外发光
			$(this).addClass('preTitle2');
		}
	});
	$('.preTitle1').mouseout(function() {
		if($(this).attr('State') == 1) {
			$(this).find('.preDown').stop().fadeIn(100);
			$(this).find('.preCross').stop().fadeOut(100);
		} else {
			$(this).removeClass('preTitle2');
		}
	});

	$('.preTitle1').click(function() {
		if($(this).attr('State') == 1) {
			$(this).find('.preCross').rotate({
				angle: 0,
				animateTo: 360
			});
			$(this).next('.preContent1').stop().animate({
				'height': '0px',
				'border-width': '0px'
			}, 300);
			$(this).next('.preContent1').css("visibility","hidden");
			$(this).parent('.preSmallBox').stop().delay(350).animate({
				'width': '70%'
			}, 300);
			$(this).attr('State', 0);
		} else {
			$(this).find('.preCross').rotate({
				angle: 0,
				animateTo: -360
			});
			$(this).parent('.preSmallBox').stop().animate({
				'width': '100%'
			}, 300);
			$(this).next('.preContent1').stop().delay(350).animate({
				/*'height': '200px',*/
				'height': "100%",
				'border-width': '2px'
			}, 300);
			$(this).next('.preContent1').css("visibility","visible");
			$(this).attr('State', 1);
			
			$(this).next('.preContent1').find("table[class='table table-hover table-striped']").css("width","100%"); 
		}
	});
})