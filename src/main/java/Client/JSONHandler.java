package Client;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Client.SensorData.GeoSensor;
import Client.SensorData.Sensor;

public class JSONHandler {

	public void execute() throws JSONException {
		  Sensor sensor = new GeoSensor(20, 20, 20, 20, 20);

			// Put into JSON-file
			JSONObject json = new JSONObject();
			json.put("latitude", "23");
			json.put("longitude", "24");
			JSONArray jsonA = new JSONArray();
			jsonA.put(sensor);
			
			json.put("Sensors", sensor);
			// TODO: change absolute with relative path 
			try (FileWriter writer = new FileWriter("D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\webapp\\api\\sensors.json", true)){
				writer.write(json.toString());
				writer.flush();
				writer.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			System.out.println(json);
	}
}
