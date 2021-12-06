package controller;

import model.CheckPoint;
import model.CheckPointType;
import model.Intersection;
import model.Plan;
import model.Tour;
import view.Window;

/**
 * TourComputing State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class AddRequestPickup implements State {
	private static AddRequestPickup instance = null;
	
	private AddRequestPickup() {
		
	}
	
	protected static AddRequestPickup getInstance() {
		if (instance==null) {
			instance = new AddRequestPickup();
		}
		return instance;
	}

	@Override
	public void clickOnMap(Controller c, ListOfCommands listOfCommands, 
			Plan p, Tour t, double lat, double lon, Window w) {
		System.out.println("Click on map");
		System.out.println("lat = "+lat+"   lon = "+lon); 
		
		Intersection pickup = p.getNearestIntersection(lat, lon);

		Integer duration = CommonActions.inputNumber(w, 
				"Pickup Duration", "Enter pickup duration");
		
		if (duration!=null) {
			w.getMapView().setPickupToAdd(
					new CheckPoint(CheckPointType.PICKUP, pickup, duration));
			c.setCurrentState(AddRequestDelivery.getInstance());
			w.getMessage().setText("Please select a delivery location.");
		}
		p.notifyObservers();
	}
}
