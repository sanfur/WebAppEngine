package Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@WebServlet(
		urlPatterns = {"/deleteData"}
)
public class DeleteData extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DatastoreService datastoreService;
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);		    
        datastoreService = DatastoreServiceFactory.getDatastoreService();
}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Delete Coordiantes		
		Query queryCoordinates = new Query("Coordinates");
		PreparedQuery preparedQueryCoordinates = datastoreService.prepare(queryCoordinates);
		List<Entity> coordinates = preparedQueryCoordinates.asList(FetchOptions.Builder.withDefaults());
		coordinates.forEach(entity -> {datastoreService.delete(entity.getKey());});
		
		// Delete Measurements		
		Query queryMeasurements = new Query("Measurements");
		PreparedQuery aredQueryMeasurements = datastoreService.prepare(queryMeasurements);
		List<Entity> measurements = aredQueryMeasurements.asList(FetchOptions.Builder.withDefaults());
		measurements.forEach(entity -> {datastoreService.delete(entity.getKey());});
		
		// Delete JSON		
		Query queryJSON = new Query("JSON");
		PreparedQuery preparedQueryJSON = datastoreService.prepare(queryJSON);
		List<Entity> JSONentity = preparedQueryJSON.asList(FetchOptions.Builder.withDefaults());
		JSONentity.forEach(entity -> {datastoreService.delete(entity.getKey());});
		
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print("Data deleted!\r\n");
	}
}
