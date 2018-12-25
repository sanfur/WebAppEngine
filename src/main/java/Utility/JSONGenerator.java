package Utility;

import java.io.IOException;
import java.util.List;
import org.json.*;
import com.google.appengine.api.datastore.Entity;

public class JSONGenerator {
	
	public String createCoordsJSONString(List<Entity> coordinateEntities) throws JSONException, IOException {
		
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < coordinateEntities.size(); i++) {
			JSONObject sensObj = new JSONObject();
			sensObj.put("longitude", coordinateEntities.get(i).getProperty("Longitude").toString());
			sensObj.put("latitude", coordinateEntities.get(i).getProperty("Latitude").toString());
			sensObj.put("sensorID", coordinateEntities.get(i).getProperty("SensorID").toString());
			jsonArray.put(sensObj);
		}
		jsonObj.put("coordinates", jsonArray);

		return jsonObj.toString();
	}
	
	public String createMeasureJSONString(List<Entity> temperatureEntities) throws JSONException, IOException {
		
		JSONObject jsonObj = new JSONObject();
		JSONArray tempArray = new JSONArray();
		for(int i = 0; i < temperatureEntities.size(); i++) {
			JSONObject tempObj = new JSONObject();
			tempObj.put("numberOfMeasurements", temperatureEntities.get(i).getProperty("NumberOfMeasurements").toString());
			tempObj.put("sensorID", temperatureEntities.get(i).getProperty("SensorID").toString());
			tempObj.put("time", temperatureEntities.get(i).getProperty("Timestamp").toString());
			tempObj.put("temp", temperatureEntities.get(i).getProperty("Temperature").toString());
			tempArray.put(tempObj);
		}
		jsonObj.put("temperature", tempArray);

		return jsonObj.toString();			
	}
	
	public String convertToCSV(String coordinates, String measurements) {
	
		String file = "";

		// Combine JSON Objects to one String
        JSONObject outputFromCoordinates;
        JSONObject outputFromMeasurements;
        try {
            outputFromCoordinates = new JSONObject(coordinates);
            outputFromMeasurements = new JSONObject(measurements);

            JSONArray coordinatesDocs = outputFromCoordinates.getJSONArray("coordinates");
            JSONArray measurementsDocs = outputFromMeasurements.getJSONArray("temperature");
           
            String coordinateCSV = CDL.toString(coordinatesDocs);
            String measurementCSV = CDL.toString(measurementsDocs);
            
            file = "\n\nSensors\n" + coordinateCSV + "\n\nMeasurements\n" + measurementCSV;
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return file;
	}
}
