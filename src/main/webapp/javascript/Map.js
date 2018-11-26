	var mymap = L.map('mapid').setView([46.84986, 9.53287], 13);	
	L.control.scale().addTo(mymap);
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox.streets',
    accessToken: 'pk.eyJ1Ijoic2FuZnVyIiwiYSI6ImNqbmsxazd2czExM3ozcHBtanB6ZGg2MHkifQ.LrKOZy_Xx4HKSK7CviX7nA'
	}).addTo(mymap);
		
	$.ajax({
		url: 'api/sensorObjects.json',
		dataType: 'json',
		type: 'get',
		cache: false,
		success: function(data){
			$(data.sensors).each(function(index, value){
				var marker = L.marker([value.latitude, value.longitude])
								.addTo(mymap)
								.bindPopup("dfsfdgdfgfdgfdggsgfdfs");
			});
		}
	});