package controller;

import javax.swing.JOptionPane;

import model.CheckPoint;
import model.CheckPointType;
import model.Intersection;
import model.Plan;
import model.Request;
import model.Tour;
import view.Window;

/**
 * TourComputing State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class AddRequestDelivery implements State {
	private static AddRequestDelivery instance = null;
	
	private AddRequestDelivery() {
		
	}
	
	protected static AddRequestDelivery getInstance() {
		if (instance==null) {
			instance = new AddRequestDelivery();
		}
		return instance;
	}
	
	@Override
	public void clickOnMap(Controller c, ListOfCommands listOfCommands, 
			Plan p, Tour t, double lat, double lon, Window w) {
		System.out.println("Click on map");
		System.out.println("lat = "+lat+"   lon = "+lon); 
		
		Intersection delivery = p.getNearestIntersection(lat, lon);
		
		CheckPoint pickup = w.getMapView().getPickupToAdd();
		Integer duration = CommonActions.inputNumber(w, 
				"Delivery Duration", "Enter delivery duration");
		
		if (duration!=null) {
			w.getMapView().setPickupToAdd(null);
			listOfCommands.add(new AddRequestCommand(t, p, 
					pickup.getAddress().getId(), delivery.getId(), 
					pickup.getDuration(), duration, w));
			c.setCurrentState(TourComputed.getInstance());
		}
	}
}
