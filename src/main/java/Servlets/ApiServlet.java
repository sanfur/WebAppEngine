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

import Utility.DataStoreHandler;
import Utility.JSONGenerator;

@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private DatastoreService datastoreService;
	private JSONGenerator jsonGenerator;
	private DataStoreHandler datastoreHandler;
	
    public ApiServlet() {
        super();
        datastoreService = DatastoreServiceFactory.getDatastoreService();
        jsonGenerator = new JSONGenerator();
        datastoreHandler = new DataStoreHandler();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String obj = "";

		if(request.getRequestURI().contains("sensors")) {
			try {
				
				obj = datastoreHandler.getJsonFromDatastore(1, datastoreService);
				
			} catch (EntityNotFoundException e) {e.printStackTrace();}
		    response.setContentType("application/json");
			response.getWriter().append(obj);
		}
		else if(request.getRequestURI().contains("measurements")) {
			try {
				
				obj = datastoreHandler.getJsonFromDatastore(2, datastoreService);
				
			} catch (EntityNotFoundException e) {e.printStackTrace();}
		    response.setContentType("application/json");
			response.getWriter().append(obj);
		}
		else if(request.getRequestURI().contains("CSV")) {
			String coords = "";
			String measure = "";
			try {
				
				coords = datastoreHandler.getJsonFromDatastore(1, datastoreService);
				measure = datastoreHandler.getJsonFromDatastore(2, datastoreService);
				
			} catch (EntityNotFoundException e) {e.printStackTrace();}
		    
		    String file = jsonGenerator.convertToCSV(coords, measure);

		    response.setContentType("text/csv");
		    response.setHeader("Content-Disposition", "attachment;filename=Sensors.csv");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().print(file);
		}
	}
}
