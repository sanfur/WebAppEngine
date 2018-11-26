package DataVisualization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.google.appengine.api.datastore.Entity;

import DataAcquisition.SensorData.GeoSensor;
import DataAcquisition.SensorData.Sensor;

public class JSONHandler {
	
	private List<Entity> entities;

	public JSONHandler(List<Entity> entities) {
		this.entities = entities;
	}

	public void execute() throws JSONException, IOException {
			
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		

		for(int i = 0; i < entities.size(); i++) {

			JSONObject sensObj = new JSONObject();
			
			sensObj.put("longitude", entities.get(i).getProperty("Longitude").toString());
			sensObj.put("latitude", entities.get(i).getProperty("Latitude").toString());
			sensObj.put("temperature", entities.get(i).getProperty("Temperature").toString());
			sensObj.put("time", entities.get(i).getProperty("Time").toString());
			sensObj.put("id", entities.get(i).getProperty("ID").toString());

			jsonArray.put(sensObj);
		}
		
		jsonObj.put("sensors", jsonArray);

		//TODO: change absolute to relative path
		try (FileWriter file = new FileWriter("D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\webapp\\api\\sensorObjects.json")) {
			file.write(jsonObj.toString());
			System.out.println("SUCCESS JSONHandler: Copied JSON Object to File, / Object: " + jsonObj);
		}
		catch(IOException e) {
		e.printStackTrace();
		}			
	}
	
	public void deleteJSONObject() {
		File file = new File("D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\webapp\\api\\sensorObjects.json");
		if(file.delete()) {
			System.out.println("SUCCESS JSONHandler: File sensorObjects.json deleted.");
		}
		else {
			System.out.println("FAILED JSONHandler: File sensorObjects.json doesn't exist.");
		}
	}
}
