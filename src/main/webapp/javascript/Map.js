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
				
				marker.on("click", function() {
				      marker.openPopup();
			    });	
				
				$(div).CanvasJSChart({ 
					 width: 300,
					 height: 200,
					 data: [
					{
						type: "splineArea",
						dataPoints: [
							{ x: 10, y: 10 },
							{ x: 20, y: 14 },
							{ x: 30, y: 18 },
							{ x: 40, y: 22 },
							{ x: 50, y: 18 },
							{ x: 60, y: 28 }
						]
					}]
				});
			});
		}
	});