import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GetFromDataStore {

	public void execute() {
		Key bobKey = KeyFactory.createKey("Person", "Bob");
		Key aliceKey = KeyFactory.createKey("Person", "Alice");
		 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity alice, bob;
		 
		try {
		    alice = datastore.get(aliceKey);
		    bob = datastore.get(bobKey);
		 
		    Long aliceAge = (Long) alice.getProperty("age");
		    Long bobAge = (Long) bob.getProperty("age");
		    System.out.println("Alice’s age: " + aliceAge);
		    System.out.println("Bob’s age: " + bobAge);
		} catch (EntityNotFoundException e) {
		    // Alice or Bob doesn't exist!
		}
	}
}
