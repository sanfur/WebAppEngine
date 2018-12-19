package Servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataStorage.DataStoreHandler;

@WebServlet(
		urlPatterns = {"/deleteData"}
)
public class DeleteData extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DataStoreHandler dataStoreHandler;
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);		    
	    dataStoreHandler = new DataStoreHandler();
}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print("Data deleted!\r\n");
	    
	    dataStoreHandler.deleteDataStore();

	}
}
