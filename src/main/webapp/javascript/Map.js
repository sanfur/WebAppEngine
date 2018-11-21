	var mymap = L.map('mapid').setView([46.84986, 9.53287], 13);	
	L.control.scale().addTo(mymap);
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox.streets',
    accessToken: 'pk.eyJ1Ijoic2FuZnVyIiwiYSI6ImNqbmsxazd2czExM3ozcHBtanB6ZGg2MHkifQ.LrKOZy_Xx4HKSK7CviX7nA'
	}).addTo(mymap);
		
	$.ajax({
		url: 'api/sensors.json',
		dataType: 'json',
		type: 'get',
		cache: false,
		success: function(data){
			$(data.sensors).each(function(index, value){
				var marker = L.marker([value.latitude, value.longitude]).addTo(mymap);
			});
		}
	});
	
//	function addMarkersToMap(){
//	    $.get("https://heatmap-219120.appspot.com/api/sensors", function(data, status){
//	        for(var i = 0; i < data.length; i++){
//	            var marker = L.marker([data[i].location.latitude, data[i].location.longitude]).addTo(mymap);
//	            marker.bindPopup();
//	            marker.id = data[i].id;
//	            marker.on('click', onMarkerClick );
//	            function onMarkerClick(e) {
//	                var popup = e.target.getPopup();
//	                var chartId = "chart" + e.target.id;
//	                popup.setContent(
//	                    '<canvas id="' + chartId + '" width="400" height="400"><p>ERROR: Could not load data!</p></canvas>'
//	                );
//	                drawChart(document.getElementById(chartId), e.target.id);
//	            }
//	        }
//	    });
//	}