package Servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataVisualization.JSONGenerator;

@WebServlet(
		urlPatterns = {"/getCSV"}
)

public class ExportCSV extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	private String coordinatesJSONFile = "/api/coordinates.json";
	private String temperaturesJSONFile = "/api/temperature.json";
	
	private JSONGenerator json;
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);	
	    json = new JSONGenerator();
	    
	    //TODO: Local path
        coordinatesJSONFile = getServletContext().getRealPath(coordinatesJSONFile);		
        temperaturesJSONFile = getServletContext().getRealPath(temperaturesJSONFile);
}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=Sensors.csv");
	    response.setCharacterEncoding("UTF-8");
		
	    String file = json.createCSVContent(coordinatesJSONFile, temperaturesJSONFile);
	    
	    response.getWriter().print(file);
	}
}
