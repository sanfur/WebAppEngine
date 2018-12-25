package Utility;

import com.google.appengine.api.datastore.DatastoreService;

public class DataStoreHandler {
	
	private DatastoreService datastoreService;
	
	public DataStoreHandler(DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
	}
	
	public DatastoreService getDatastore() {
		return datastoreService;
	}
	
}
