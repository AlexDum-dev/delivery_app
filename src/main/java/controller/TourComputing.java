package controller;

/**
 * TourComputing State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class TourComputing implements State {
	private static TourComputing instance = null;

	private TourComputing() {

	}

	protected static TourComputing getInstance() {
		if (instance==null) {
			instance = new TourComputing();
		}
		return instance;
	}

	@Override
	public void stopTour(Controller c) {
		c.getThread().getTsp().setStop(true);
	}
}
