	var mymap = L.map('mapid').setView([46.84986, 9.53287], 13);	
	L.control.scale().addTo(mymap);
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox.streets',
    accessToken: 'pk.eyJ1Ijoic2FuZnVyIiwiYSI6ImNqbmsxazd2czExM3ozcHBtanB6ZGg2MHkifQ.LrKOZy_Xx4HKSK7CviX7nA'
	}).addTo(mymap);	
	
	$.ajax({
		url: 'api/coordinates.json',
		dataType: 'json',
		type: 'get',
		cache: false,
		success: function(data){
			$(data.coordinates).each(function(index, value){
			    
				var div = $('<div id="chartID"; style="width: 300px; height: 200px;"></div>')[0];
			    var marker = L.marker([value.latitude, value.longitude])
								.addTo(mymap)
								.bindPopup(div);

				marker.on("click", function(element) {

				    var dpsB = [{y: 1}];
					 
				    var chart = new CanvasJS.Chart("chartID",{
				    	
				    	title :{
				    		text: "Measurements"
				    	},
				    	axisX: [{						
				    		title: "Timestamps"
				    	},
				    	{
				            lineColor: "#C24642"
				    	}],
				    	axisY: {						
				    		title: "Temperature"
				    	},
						type: "spline",
				    	data: [{
				    		type: "line",
				    		axisIndex: 0,
				    		dataPoints : [{}]
				    	},
				    	{
				    		type: "line",
				    		axisIndex: 1,
				    		dataPoints : [{}]
				    	}]
				    });
					
					$.ajax({
						url: 'api/temperature.json',
						dataType: 'json',
						type: 'get',
						cache: false,
						success: function(data){
							$(data.temperature).each(function(index, value){
								var yValue = parseInt(value.temp, 10);
								var xValue = parseInt(value.time, 10);
								var length = chart.options.data[0].dataPoints.length;
								chart.options.data[0].dataPoints.push({y: yValue});
								chart.axisX[1].push({x: xValue});
							    console.log("DPS B: " + dpsB);
							    chart.render();
							});	
						}
					});
				    	
//				    var updateInterval = 1000;
//				    setInterval(function(){updateChart(dpsB.length + 1, yVal, dpsB, chart)}, updateInterval);
				    
					chart.render();
					marker.openPopup();
			    });	
			});
		}
	});

	function updateChart (xVal, yVal, dps, chart) {
		yVal = yVal +  Math.round(5 + Math.random() *(-5-5));
		dps.push({x: xVal,y: yVal,});
		xVal++;
	chart.render();	
	// update chart after specified time. 
	};
	



















