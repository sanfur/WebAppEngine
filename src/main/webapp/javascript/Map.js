	var tempData = [];

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
			    var dpsB = [{x: 1,y: 10}];
			    
			    var i = 1;

			    var marker = L.marker([value.latitude, value.longitude])
								.addTo(mymap)
								.bindPopup(div);

				marker.on("click", function(element) {
				    
					$.ajax({
						url: 'api/temperature.json',
						dataType: 'json',
						type: 'get',
						cache: false,
						success: function(measure){

							$(measure.temperature).each(function(indexTemp, valueTemp){
							  
								var yValue = valueTemp.temp;
								var xValue = (dpsB.length + 1);
							    dpsB.push({x: xValue,y: yValue});
							    console.log("X: " + xValue + " ; " + "Y: " + yValue);
							    console.log("DPS B: " + dpsB);
							    console.log("DPS A: " + dps);
							    chart.render();
							});	
						}

					});

				    var chart = new CanvasJS.Chart("chartID",{

				    	title :{
				    		text: "Measurements"
				    	},
				    	axisX: {						
				    		title: "Timestamps"
				    	},
				    	axisY: {						
				    		title: "Temperature"
				    	},
				    	data: [{
				    		type: "line",
				    		dataPoints : dpsB
				    	}]
				    });
				     					
				    var updateInterval = 3000;
				    
					
				    var dps = [{x: 1, y: 10}, {x: 2, y: 10}, {x: 3, y: 10}, {x: 4, y: 10}, {x: 5, y: 10}];   //dataPoints. 
				    var yVal = updateTempValue(1);	
				    var updateInterval = 1000;
					   
				    setInterval(function(){updateChart(dpsB.length + 1, yVal, dpsB, chart)}, updateInterval);
				    
					marker.openPopup();
			    });	
			});
		}
	});
	
	function updateTempValue(index){
		$.ajax({
			url: 'api/temperature.json',
			dataType: 'json',
			type: 'get',
			cache: false,
			success: function(measure){

				$(measure.temperature).each(function(indexTemp, valueTemp){
				  
					var yValue = valueTemp.temp;
					var xValue = (dpsB.length + 1);
				    dpsB.push({x: xValue,y: yValue});
				    console.log("X: " + xValue + " ; " + "Y: " + yValue);
				    console.log("DPS B: " + dpsB);
				    console.log("DPS A: " + dps);
				    chart.render();
				    
				});	
			}

		});
	}
	
	function updateChart (xVal, yVal, dps, chart) {
    	
    	
		yVal = yVal +  Math.round(5 + Math.random() *(-5-5));
		dps.push({x: xVal,y: yVal,});
		
		xVal++;
	
	chart.render();	
 
	// update chart after specified time. 
 
	};
	



















