package controller;

/**
 * TourComputed State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class TourComputed implements State {
	private static TourComputed instance = null;
	
	private TourComputed() {
		
	}
	
	protected static TourComputed getInstance() {
		if (instance==null) {
			instance = new TourComputed();
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
