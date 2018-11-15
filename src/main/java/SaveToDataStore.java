import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class SaveToDataStore {
	
	public void execute() {
    	
		Entity alice = new Entity("Alice", "Person");
    	alice.setProperty("gender", "female");
    	alice.setProperty("age", 20);
    	 
    	Key bobKey = KeyFactory.createKey("Person", "Bob");
    	Entity bob = new Entity(bobKey);
    	bob.setProperty("gender", "male");
    	bob.setProperty("age", "23");
    	
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	datastore.put(alice);
    	datastore.put(bob);
	}
}
