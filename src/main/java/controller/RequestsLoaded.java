package controller;

public class RequestsLoaded implements State {
	private static RequestsLoaded instance = null;
	
	private RequestsLoaded() {
		
	}
	
	protected static RequestsLoaded getInstance() {
		if (instance==null) {
			instance = new RequestsLoaded();
		}
		return instance;
	}

	public void loadMap() {
		//TODO: Load the map
	}
	
	public void loadRequest() {
		//TODO: Load the requests
	}
	
	public void computeTour() {
		//TODO: Compute the tour
	}
}
