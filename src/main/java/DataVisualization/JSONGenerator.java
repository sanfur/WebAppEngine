package DataVisualization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.io.FileUtils;
import org.json.*;

import com.google.appengine.api.datastore.Entity;

import DataAcquisition.SensorData.Sensor;

public class JSONGenerator {
	
	public void createCoordinatesObject(String filePath, List<Entity> coordinateEntities) throws JSONException, IOException {
			
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

		try (FileWriter file = new FileWriter(filePath)) {
			file.write(jsonObj.toString());
			System.out.println("SUCCESS JSONHandler: Copied JSON Coordinates Object to File, / Object: " + jsonObj);
		}
		catch(IOException e) {
		e.printStackTrace();
		}			
	}
	
	public void createMeasurementObject(String filePath, List<Entity> temperatureEntities) throws JSONException, IOException {
		
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

		try (FileWriter file = new FileWriter(filePath)) {
			file.write(jsonObj.toString());
			System.out.println("SUCCESS JSONHandler: Copied JSON Temperature Object to File, / Object: " + jsonObj);
		}
		catch(IOException e) {
		e.printStackTrace();
		}			
	}
	
	public ArrayList<Sensor> getSensors(String filePath, String fileName) throws JSONException{
		
		ArrayList<Sensor> jsonList = new ArrayList<Sensor>();
        
		 String line = "";

		    try {
		        BufferedReader bufferreader = new BufferedReader(new FileReader(filePath + fileName));
		        line = bufferreader.readLine();
		        bufferreader.close();
		    } catch (FileNotFoundException ex) {
		        ex.printStackTrace();
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
			System.out.println("JSONHandler: line: " + line);

		    JSONObject jsonObject = new JSONObject(line);
			System.out.println("JSONHandler: jsonObject: " + jsonObject);

	    	System.out.println("Generated JSONObject: " + jsonObject);
            JSONArray array = jsonObject.getJSONArray("coordinates");
			System.out.println("JSONHandler: array: " + array);

            for(int i = 0; i < array.length(); i++) {

            	JSONObject sensorObject = (JSONObject) array.get(i);
            	System.out.println("JSONHandler: arras.get(i): " + sensorObject);
    	    	String lat = (String) sensorObject.get("latitude");
    	    	
    	    	System.out.println("Coordinates: "+ i + " Latitude: " + lat);
    	    	String lon = (String) sensorObject.get("longitude");
    	    	
    	    	System.out.println("Coordinates: "+ i + " Longitude: " + lon);
    	    	String id = (String) sensorObject.get("sensorID");
    	    	
    	    	System.out.println("Coordinates: "+ i + " SensorID: " + id);
    	    	Sensor sensor = new Sensor(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
    	    	jsonList.add(sensor);
    	    	System.out.println("Sensor: "+ sensor.getSensorID() + " , " + sensor.getLat() + " , " + sensor.getLong());
            }
		
		return jsonList;
	}
	
	public ArrayList<Sensor> getMeasurements(String filePath, String fileName) throws JSONException{
		
		ArrayList<Sensor> jsonList = new ArrayList<Sensor>();
        
		 String line = "";
		    try {
		        BufferedReader bufferreader = new BufferedReader(new FileReader(filePath + fileName));
		        line = bufferreader.readLine();
		        bufferreader.close();
		    } catch (FileNotFoundException ex) {
		        ex.printStackTrace();
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
			System.out.println("JSONHandler: line: " + line);

		    JSONObject jsonObject = new JSONObject(line);
			System.out.println("JSONHandler: jsonObject: " + jsonObject);

	    	System.out.println("Generated JSONObject: " + jsonObject);
            JSONArray array = jsonObject.getJSONArray("coordinates");
			System.out.println("JSONHandler: array: " + array);

            for(int i = 0; i < array.length(); i++) {

            	JSONObject sensorObject = (JSONObject) array.get(i);
            	System.out.println("JSONHandler: arras.get(i): " + sensorObject);
    	    	String lat = (String) sensorObject.get("latitude");
    	    	
    	    	System.out.println("Coordinates: "+ i + " Latitude: " + lat);
    	    	String lon = (String) sensorObject.get("longitude");
    	    	
    	    	System.out.println("Coordinates: "+ i + " Longitude: " + lon);
    	    	String id = (String) sensorObject.get("sensorID");
    	    	
    	    	System.out.println("Coordinates: "+ i + " SensorID: " + id);
    	    	Sensor sensor = new Sensor(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
    	    	jsonList.add(sensor);
    	    	System.out.println("Sensor: "+ sensor.getSensorID() + " , " + sensor.getLat() + " , " + sensor.getLong());
            }
		
		return jsonList;
	}
	
	public String createCSVContent(String coordinatesFileName, String measurementsFileName) {
		String file = "";
		String coordinates = "";
		String measurements = "";
	    
		// Read JSON Objects
		try {
	        BufferedReader bufferedReaderForCoordinates = new BufferedReader(new FileReader(coordinatesFileName));
	        BufferedReader bufferReaderForMeasurements = new BufferedReader(new FileReader(measurementsFileName));
	        coordinates = bufferedReaderForCoordinates.readLine();
	        measurements = bufferReaderForMeasurements.readLine();
	        bufferedReaderForCoordinates.close();
	        bufferReaderForMeasurements.close();
	    } catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
		
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
