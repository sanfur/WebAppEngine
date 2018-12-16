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
				var temperatures = [];
				var marker = L.marker([value.latitude, value.longitude])
								.addTo(mymap)

				marker.on("click", function(element) {
					
					var wrapper = document.getElementById("wrapper");
					wrapper.style.display = "block";
					
				    var chart = new CanvasJS.Chart("chart",{

				    	title :{
				    		text: "Measurements",
				    		fontFamily: "Arial Rounded MT Bold",
				    	},
				    	
				    	axisX: [{						
				    		title: "Timestamps",
				            lineColor: "#2394bc",
				            minimum: 1,
				    	},
				    	{
				            lineColor: "#2394bc",
				            minimum: 1,
				    	}],
				    	
				    	axisY: {						
				    		title: "Temperature"
				    	},
				    	
				    	data: [
			    		{
				    		type: "spline",
				    		axisIndex: 0,
				    		dataPoints : [{}]
				    	},
				    	{
				    		type: "line",
				    		axisXIndex: 1,
				    		dataPoints : [{}]
				    	}]
				    });
					
					$.ajax({
						url: 'api/temperature.json',
						dataType: 'json',
						type: 'get',
						cache: false,
						success: function(data){
							$(data.temperature).each(function(index, valueTemp){
								if(parseInt(value.sensorID) == parseInt(valueTemp.sensorID)){
									var i = 0;
									temperatures[i] = valueTemp;
									i++;
									var yValue = parseInt(valueTemp.temp, 10);
									var xValue = parseInt(valueTemp.time, 10);
									var length = chart.options.data[0].dataPoints.length;
									chart.options.data[0].dataPoints.push({y: yValue});
									chart.options.data[1].dataPoints.push({label: xValue,});
									chart.render();									
								}
							});	
							document.getElementById("averageWeekly").innerHTML = "Weekly Average: " + calculateAverage(temperatures, 604800000) + " &#8451";
							document.getElementById("averageDaily").innerHTML = "Daily Average: " + calculateAverage(temperatures, 86400000) + " &#8451";
							document.getElementById("averageHourly").innerHTML = "Hourly Average: " + calculateAverage(temperatures, 3600000) + " &#8451";
							chart.render();
						}
					});
			    });	
			});
		}
	});
	
	function calculateAverage(temperatures, milliSeconds){

		var lastTimeStamp = parseInt(temperatures[(temperatures.length - 1)].time);
		var firstTimeStamp = lastTimeStamp - milliSeconds;
		
		var average = 0;
		var i;
		for(i = 0; i < temperatures.length; i++){
			if(parseInt(temperatures[i].time) >= firstTimeStamp){
				average += parseInt(temperatures[i].temp);
			}
		}
		average /= temperatures.length;
		return parseInt(average);
	}



