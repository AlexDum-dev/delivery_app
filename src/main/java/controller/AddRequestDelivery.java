package controller;

import model.CheckPoint;
import model.Intersection;
import model.Plan;
import model.Tour;
import view.Window;

/**
 * AddRequestDelivery State
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
		Intersection delivery = p.getNearestIntersection(lat, lon);

		CheckPoint pickup = w.getMapView().getPickupToAdd();
		Integer duration = CommonActions.inputNumber(w, 
				"Delivery Duration", "Enter delivery duration");

		if (duration!=null) {
			w.getMapView().setPickupToAdd(null);
			listOfCommands.add(new AddRequestCommand(t, p, 
					pickup.getAddress().getId(), delivery.getId(), 
					pickup.getDuration(), duration));
			c.setCurrentState(TourComputed.getInstance());
			w.getMessage().setText("Request added!");
			w.setDeleteButtonEnabled(true);
			w.setModifyButtonsEnabled(true);
		}
	}
}
