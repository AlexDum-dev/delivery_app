package controller;

import model.CheckPoint;
import model.Plan;
import model.Request;
import model.Tour;

/**
 * Command to delete a request
 * 
 * @author 4IF Group H4144
 * @version 1.0 7 Dec 2021
 */
public class DeleteRequestCommand implements Command {
	private Tour tour;
	private Plan plan;
	private String idPickup;
	private String idDelivery;
	private Request request;
	private int pickup;
	private int delivery;
	
	public DeleteRequestCommand(Plan plan, Tour tour, 
			String idPickup, String idDelivery) {
		this.tour = tour;
		this.plan = plan;
		this.idPickup = idPickup;
		this.idDelivery = idDelivery;
	}

	@Override
	public void doCommand() {
		int index = -1;
		int i = 0;
		for(CheckPoint c : tour.getCheckPoints()) {
			if(c.getAddress().getId().equals(this.idPickup)){
				index = c.getIndex();
				pickup = i;
			}
			if(c.getAddress().getId().equals(this.idDelivery)){
				index = c.getIndex();
				delivery = i;
			}
			++i;
		}
		
		if (index!=-1) {
			if (pickup<delivery) {
				tour.deleteCheckPoint(delivery, plan.getIntersections());
				tour.deleteCheckPoint(pickup, plan.getIntersections());
			} else{
				tour.deleteCheckPoint(pickup, plan.getIntersections());
				tour.deleteCheckPoint(delivery, plan.getIntersections());
			}
			request = plan.getRequests().get(index); //get request in the plan
			plan.getRequests().remove(index); //delete request in the plan
			plan.actualizeRequestsIndex(); //actualize the index of requests
			
			request.setActive(false);
			
			this.tour.actualizeTime();

			plan.notifyObservers();
			tour.notifyObservers();
		}
	}

	@Override
	public void undoCommand() {
		plan.getRequests().add(request.getIndex(), request);
		plan.actualizeRequestsIndex();
		
		if (pickup<delivery) {
			tour.insertCheckPoint(pickup, request.getPickup(), 
					plan.getIntersections());
			tour.insertCheckPoint(delivery, request.getDelivery(), 
					plan.getIntersections());
		} else{
			tour.insertCheckPoint(delivery, request.getDelivery(), 
					plan.getIntersections());
			tour.insertCheckPoint(pickup, request.getPickup(), 
					plan.getIntersections());
		}
		
		plan.notifyObservers();
		tour.notifyObservers();
	}

}
