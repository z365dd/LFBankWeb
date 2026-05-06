$(document).ready(function() {
	// var pieData = [ {
	// value : 300,
	// color : "#F7464A",
	// highlight : "#FF5A5E",
	// label : "Red"
	// }, {
	// value : 50,
	// color : "#46BFBD",
	// highlight : "#5AD3D1",
	// label : "Green"
	// }, {
	// value : 100,
	// color : "#FDB45C",
	// highlight : "#FFC870",
	// label : "Yellow"
	// }, {
	// value : 40,
	// color : "#949FB1",
	// highlight : "#A8B3C5",
	// label : "Grey"
	// }, {
	// value : 120,
	// color : "#4D5360",
	// highlight : "#616774",
	// label : "Dark Grey"
	// }
	//
	// ];
	//
	// var doughnutData = [ {
	// value : 1,
	// label : "One"
	// }, {
	// value : 2,
	// label : "Two"
	// }, {
	// value : 3,
	// label : "Three"
	// }, {
	// value : 4,
	// label : "Four"
	// }, {
	// value : 5,
	// label : "Five"
	// }
	//
	// ];
	//
	// var polarData = [ {
	// value : 300,
	// color : "#F7464A",
	// highlight : "#FF5A5E",
	// label : "Red"
	// }, {
	// value : 50,
	// color : "#46BFBD",
	// highlight : "#5AD3D1",
	// label : "Green"
	// }, {
	// value : 100,
	// color : "#FDB45C",
	// highlight : "#FFC870",
	// label : "Yellow"
	// }, {
	// value : 40,
	// color : "#949FB1",
	// highlight : "#A8B3C5",
	// label : "Grey"
	// }, {
	// value : 120,
	// color : "#4D5360",
	// highlight : "#616774",
	// label : "Dark Grey"
	// }
	//
	// ];

	// var lineChartData = {
	// labels : [ "January", "February", "March", "April", "May",
	// "June", "July" ],
	// datasets : [
	// {
	// label : "My First dataset",
	// fillColor : "rgba(220,220,220,0.2)",
	// strokeColor : "rgba(220,220,220,1)",
	// pointColor : "rgba(220,220,220,1)",
	// pointStrokeColor : "#fff",
	// pointHighlightFill : "#fff",
	// pointHighlightStroke : "rgba(220,220,220,1)",
	// data : [ randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor() ]
	// },
	// {
	// label : "My Second dataset",
	// fillColor : "rgba(151,187,205,0.2)",
	// strokeColor : "rgba(151,187,205,1)",
	// pointColor : "rgba(151,187,205,1)",
	// pointStrokeColor : "#fff",
	// pointHighlightFill : "#fff",
	// pointHighlightStroke : "rgba(151,187,205,1)",
	// data : [ randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor() ]
	// } ]
	//
	// };

	// var radarChartData = {
	// labels : [ "Eating", "Drinking", "Sleeping", "Designing",
	// "Coding", "Cycling", "Running" ],
	// datasets : [ {
	// label : "My First dataset",
	// fillColor : "rgba(220,220,220,0.2)",
	// strokeColor : "rgba(220,220,220,1)",
	// pointColor : "rgba(220,220,220,1)",
	// pointStrokeColor : "#fff",
	// pointHighlightFill : "#fff",
	// pointHighlightStroke : "rgba(220,220,220,1)",
	// data : [ 65, 59, 90, 81, 56, 55, 40 ]
	// }, {
	// label : "My Second dataset",
	// fillColor : "rgba(151,187,205,0.2)",
	// strokeColor : "rgba(151,187,205,1)",
	// pointColor : "rgba(151,187,205,1)",
	// pointStrokeColor : "#fff",
	// pointHighlightFill : "#fff",
	// pointHighlightStroke : "rgba(151,187,205,1)",
	// data : [ 28, 48, 40, 19, 96, 27, 100 ]
	// } ]
	// };
	// var radarChartData = {
	// labels : [ "Eating", "Drinking", "Sleeping", "Designing",
	// "Coding", "Cycling", "Running" ],
	// datasets : [ {
	// label : "My First dataset",
	// fillColor : "rgba(220,220,220,0.2)",
	// strokeColor : "rgba(220,220,220,1)",
	// pointColor : "rgba(220,220,220,1)",
	// pointStrokeColor : "#fff",
	// pointHighlightFill : "#fff",
	// pointHighlightStroke : "rgba(220,220,220,1)",
	// data : [ 65, 59, 90, 81, 56, 55, 40 ]
	// }, {
	// label : "My Second dataset",
	// fillColor : "rgba(151,187,205,0.2)",
	// strokeColor : "rgba(151,187,205,1)",
	// pointColor : "rgba(151,187,205,1)",
	// pointStrokeColor : "#fff",
	// pointHighlightFill : "#fff",
	// pointHighlightStroke : "rgba(151,187,205,1)",
	// data : [ 28, 48, 40, 19, 96, 27, 100 ]
	// } ]
	// };
	// var randomScalingFactor = function() {
	// return Math.round(Math.random() * 100);
	// };
	// var barChartData = {
	// labels : [ "January", "February", "March", "April", "May",
	// "June", "July" ],
	// datasets : [
	// {
	// fillColor : "rgba(220,220,220,0.5)",
	// strokeColor : "rgba(220,220,220,0.8)",
	// highlightFill : "rgba(220,220,220,0.75)",
	// highlightStroke : "rgba(220,220,220,1)",
	// data : [ randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor() ]
	// },
	// {
	// fillColor : "rgba(151,187,205,0.5)",
	// strokeColor : "rgba(151,187,205,0.8)",
	// highlightFill : "rgba(151,187,205,0.75)",
	// highlightStroke : "rgba(151,187,205,1)",
	// data : [ randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor(),
	// randomScalingFactor() ]
	// } ]
	//
	// }
	$("canvas[data-role='pchartcustom']").each(function(i) {
		var ulval = $(this).attr("action");
		var url = "../../" + ulval;
		var ctx = $(this).get(0).getContext("2d");
		$.post(url, {
			type : "label"
		}, function(data) {
			var pieData = data;
			window.myPie = new Chart(ctx).Pie(pieData);
		}, "json");
	});

	$("canvas[data-role='nchartcustom']").each(function(i) {
		var ulval = $(this).attr("action");
		var url = "../../" + ulval;
		var ctx = $(this).get(0).getContext("2d");
		$.post(url, {
			type : "label"
		}, function(data) {
			var doughnutData = data;
			window.myDoughnut = new Chart(ctx).Doughnut(doughnutData);
		}, "json");
	});
	$("canvas[data-role='dchartcustom']").each(function(i) {
		var ulval = $(this).attr("action");
		var url = "../../" + ulval;
		var ctx = $(this).get(0).getContext("2d");
		$.post(url, {
			type : "label"
		}, function(data) {
			var polarData = data;
			window.myPolarArea = new Chart(ctx).PolarArea(polarData);
		}, "json");
	});
	$("canvas[data-role='qchartcustom']").each(function(i) {
		var ulval = $(this).attr("action");
		var url = "../../" + ulval;
		var ctx = $(this).get(0).getContext("2d");
		$.post(url, {
			type : "label"
		}, function(data) {
			if (data.retCode === "0000") {
				var lineChartData = data.lineChartData;
				window.myLine = new Chart(ctx).Line(lineChartData);
			}
		}, "json");
	});
	$("canvas[data-role='lchartcustom']").each(function(i) {
		var ulval = $(this).attr("action");
		var url = "../../" + ulval;
		var ctx = $(this).get(0).getContext("2d");
		$.post(url, {
			type : "label"
		}, function(data) {
			if (data.retCode === "0000") {
				var radarChartData = data.lineChartData;
				window.myRadar = new Chart(ctx).Radar(radarChartData);
			}
		}, "json");
	});
	$("canvas[data-role='zchartcustom']").each(function(i) {
		var ulval = $(this).attr("action");
		var url = "../../" + ulval;
		var ctx = $(this).get(0).getContext("2d");
		$.post(url, {
			type : "label"
		}, function(data) {
			if (data.retCode === "0000") {
				var barChartData = data.lineChartData;
				window.myBar = new Chart(ctx).Bar(barChartData);
			}
		}, "json");
	});

});
