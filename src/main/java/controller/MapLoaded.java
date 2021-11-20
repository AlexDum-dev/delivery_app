package controller;

public class MapLoaded implements State {
	private static MapLoaded instance = null;
	
	private MapLoaded() {
		
	}
	
	protected static MapLoaded getInstance() {
		if (instance==null) {
			instance = new MapLoaded();
		}
		return instance;
	}

	public void loadMap() {
		//TODO: Load the map
	}
	
	public void loadRequest() {
		//TODO: Load the requests
	}
}
