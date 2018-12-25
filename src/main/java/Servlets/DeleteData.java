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

import Utility.DataStoreHandler;

@WebServlet(
		urlPatterns = {"/deleteData"}
)
public class DeleteData extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DatastoreService datastoreService;
	private DataStoreHandler datastoreHandler;
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);		    
        datastoreService = DatastoreServiceFactory.getDatastoreService();
        datastoreHandler = new DataStoreHandler();
}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    datastoreHandler.deleteEntitiesOfKind("Coordinates", datastoreService);
	    datastoreHandler.deleteEntitiesOfKind("Measurements", datastoreService);
	    datastoreHandler.deleteEntitiesOfKind("JSON", datastoreService);

	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print("Data deleted!\r\n");
	}
}
