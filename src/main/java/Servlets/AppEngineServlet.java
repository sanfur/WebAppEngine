package Servlets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import DataAcquisition.*;
import DataAcquisition.SensorData.*;
import DataAcquisition.SensorData.TemperatureData.*;
import DataStorage.*;
import DataVisualization.*;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class AppEngineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

 	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	      throws IOException {
	
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print("Hello App Engine!\r\n");
	    
	    // Data Acquisition
	    
	    //	Set Coordinate List
	    ArrayList<Coordinates> coordinateList = new ArrayList<Coordinates>();

	    String atHomeFolderPath = "D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\input\\";
	    String atSchoolFolderPathPath = "D:\\CloudComputing\\WebAppEngine\\src\\main\\input\\";
	    String DBFile = "DB.txt";
	    String testFile = "testLocation.txt";
	    Locator locator = new Locator();
	    coordinateList = locator.getCoordinates(atHomeFolderPath + testFile);
		System.out.println("AppEngineServlet: Added Coordinates to list: " + coordinateList.size());

	    //	Set TimeAndTempData List
	    ArrayList<TemperatureData> tempDataList = new ArrayList<TemperatureData>();
	    
	    TempHandler tempHandler = new TempHandler();
	    TimeStampCollector timeStampCollector = new TimeStampCollector();
	    for(int i = 0; i < coordinateList.size(); i++) {
    		TemperatureData tempData = new TemperatureData(timeStampCollector.getTimeStamp(), tempHandler.getRandomTemp());
    		tempDataList.add(tempData);
	    }
		System.out.println("AppEngineServlet: Added TemperatureData to list: " + tempDataList.size());

	    
	    //	Set Sensor List
	    ArrayList<GeoSensor> sensorList = new ArrayList<GeoSensor>();
	    SensorHandler sensorHandler = new SensorHandler();	    	    
	    for(int i = 0; i < coordinateList.size(); i++) {
    		sensorList.add(sensorHandler.getGeoSensor(i + 1, tempDataList.get(i), coordinateList.get(i)));
	    }
		System.out.println("AppEngineServlet: Added Sensors to list: " + sensorList.size());
	    
	    // Data Storage
	    DataStoreHandler dataHandler = new DataStoreHandler(sensorList);
	    dataHandler.saveCoordinatesToDataStore();
	   
	    int numberOfMeasurements = 2;
	    
    	for(int i = 0; i < numberOfMeasurements; i ++) {
    		for(GeoSensor sensor : sensorList) {	  	    			    		
			    dataHandler.saveTemperaturesToDataStore(sensor, (i + 1));
    		}
    		try {
    			int timeToSleepInSeconds = 1;
    			System.out.println("AppEngineServlet: Started Sleep: " + timeToSleepInSeconds + " Seconds");
				TimeUnit.SECONDS.sleep(timeToSleepInSeconds);
				System.out.println("AppEngineServlet: Ended Sleep");
			} catch (InterruptedException e) { e.printStackTrace(); }
	    }
    	// TODO: Check if Datastore has sensors in it
    	boolean dataStoreHasSensors = true;
	    
	    // Data Visualization	  
	    String atHomeFolderPathJSON = "D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\webapp\\api\\";
	    String atSchoolFolderPathJSON = "D:\\CloudComputing\\WebAppEngine\\src\\main\\webapp\\api\\";
	    String coordinatesJSONFile = "coordinates.json";
	    String temperaturesJSONFile = "temperature.json";
	    JSONHandler json = new JSONHandler(dataHandler.getCoordinatesFromDataStore(), dataHandler.getTemperaturesFromDataStore());	    
	    try {
	    	if(dataStoreHasSensors) {
	    		json.createCoordinatesObject(atHomeFolderPathJSON + coordinatesJSONFile);
				json.createTemperaturesObject(atHomeFolderPathJSON + temperaturesJSONFile, numberOfMeasurements);	
	    	}
	    	else
	    	{
	    		System.out.println("FAILED AppEngineServlet: Datastore is empty, no JSONObject made");
	    	}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    // Clean up
//	    dataHandler.deleteDataStore();
//	    json.deleteJSONObject(atSchoolFolderPathJSON + JSONFile);
 	}
}