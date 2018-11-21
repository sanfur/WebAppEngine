package Server;
import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import Client.*;


@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class AppEngineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");

    response.getWriter().print("Hello App Engine!\r\n");
    
//    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//    double time = timestamp.getTime();    
//    Sensor sensor = new Sensor(20, 46.84986, 9.53287, time);
    
//    SaveToDataStore save = new SaveToDataStore(sensor);
//    save.execute();
    
//    GetFromDataStore get = new GetFromDataStore();
//	get.execute();
	
//	JSONHandler json = new JSONHandler();
//	try {
//		json.execute();
//	} catch (JSONException e) {
//		e.printStackTrace();
//	}
  }
}