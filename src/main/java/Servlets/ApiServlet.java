package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import Utility.JSONGenerator;

@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private DatastoreService datastoreService;
	private JSONGenerator jsonGenerator;
    public ApiServlet() {
        super();
        datastoreService = DatastoreServiceFactory.getDatastoreService();
        jsonGenerator = new JSONGenerator();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Request: " + request.getRequestURI());
		String obj = "";

		if(request.getRequestURI().contains("coordinates.json")) {
			Key jsonKey = KeyFactory.createKey("JSON", 1);
			try {
				Entity entity = datastoreService.get(jsonKey);
				obj = entity.getProperty("object").toString();
				System.out.println("ApiServlet GET: " + obj);
			} catch (EntityNotFoundException e) {e.printStackTrace();}
			response.getWriter().append(obj);
		}
		else if(request.getRequestURI().contains("temperature.json")) {
			Key jsonKey = KeyFactory.createKey("JSON", 2);
			try {
				Entity entity = datastoreService.get(jsonKey);
				obj = entity.getProperty("object").toString();
				System.out.println("ApiServlet GET: " + obj);
			} catch (EntityNotFoundException e) {e.printStackTrace();}
			response.getWriter().append(obj);
		}
		else if(request.getRequestURI().contains("CSV")) {

		    Key coordsJsonKey = KeyFactory.createKey("JSON", 1);
			Key measureJsonKey = KeyFactory.createKey("JSON", 2);
			String coords = "";
			String measure = "";
			try {
				Entity coordsEntity = datastoreService.get(coordsJsonKey);
				Entity measureEntity = datastoreService.get(measureJsonKey);
				coords = coordsEntity.getProperty("object").toString();
				measure = measureEntity.getProperty("object").toString();
				System.out.println("ApiServlet GET: " + coords);
				System.out.println("ApiServlet GET: " + measure);
			} catch (EntityNotFoundException e) {e.printStackTrace();}
		    
		    String file = jsonGenerator.convertToCSV(coords, measure);

		    response.setContentType("text/csv");
		    response.setHeader("Content-Disposition", "attachment;filename=Sensors.csv");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().print(file);
		}
	}
}
