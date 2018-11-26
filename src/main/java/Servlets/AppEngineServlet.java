package Servlets;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import DataAcquisition.*;
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
	    SensorHandler sensorHandler = new SensorHandler();
	    sensorHandler.execute();	        
	    
	    // Data Storage
	    DataStoreHandler dataHandler = new DataStoreHandler(sensorHandler.getListOfGeoSensors());
	    dataHandler.saveToDataStore();
	    
	    // Data Visualization	  
	    JSONHandler json = new JSONHandler(dataHandler.getFromDataStore());	    
	    try {
			json.execute();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    // Clean up
	    dataHandler.deleteDataStore();
//	    json.deleteJSONObject();
 	}
}