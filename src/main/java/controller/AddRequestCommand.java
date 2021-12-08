package controller;

import java.util.List;

import model.CheckPoint;
import model.CheckPointType;
import model.Plan;
import model.Request;
import model.Tour;

/**
 * Command to add a request
 * 
 * @author 4IF Group H4144
 * @version 1.0 7 Dec 2021
 */
public class AddRequestCommand implements Command {

	private Tour tour;
	private Plan plan;
	private String idPickup;
	private String idDelivery;
	private int durationPickup;
	private int durationDelivery;
	private boolean invalid;

	public AddRequestCommand(Tour tour, Plan plan, String idPickup, 
			String idDelivery, int durationPickup, int durationDelivery) {
		super();
		this.tour = tour;
		this.plan = plan;
		this.idPickup = idPickup;
		this.idDelivery = idDelivery;
		this.durationPickup = durationPickup;
		this.durationDelivery = durationDelivery;
		this.invalid = false;
	}

	@Override
	public void doCommand() {
		invalid = false;
		
		CheckPoint newPickup = new CheckPoint(CheckPointType.PICKUP, 
				plan.getIntersection(idPickup), durationPickup);
		CheckPoint newDelivery = new CheckPoint(CheckPointType.DELIVERY, 
				plan.getIntersection(idDelivery), durationDelivery);
		Request r = new Request(newPickup, newDelivery);
		plan.addRequest(r);

		boolean ok;
		ok = tour.insertCheckPoint(tour.getCheckPoints().size(), 
				r.getPickup(), plan.getIntersections());
		ok &= tour.insertCheckPoint(tour.getCheckPoints().size(), 
				r.getDelivery(), plan.getIntersections());

		if (ok) {
			tour.actualizeTime();

			plan.notifyObservers();
			tour.notifyObservers();
		} else {
			undoCommand();
			invalid = true;
		}
	}

	@Override
	public void undoCommand() {
		if (!invalid) {
			tour.deleteCheckPoint(tour.getCheckPoints().size()-1, 
					plan.getIntersections());
			tour.deleteCheckPoint(tour.getCheckPoints().size()-1, 
					plan.getIntersections());
			tour.actualizeTime();
	
			List<Request> r = plan.getRequests();
			r.remove(r.size()-1);
	
			plan.notifyObservers();
			tour.notifyObservers();
		}
	}

}
