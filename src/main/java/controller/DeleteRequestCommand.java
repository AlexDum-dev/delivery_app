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
	private Request request;
	private int pickup;
	private int delivery;
	
	public DeleteRequestCommand(Plan plan, Tour tour, Request r) {
		this.tour = tour;
		this.plan = plan;
		this.request = r;
	}

	@Override
	public void doCommand() {
		int index = request.getIndex();
		int i = 0;
		for(CheckPoint c : tour.getCheckPoints()) {
			if(c.equals(request.getPickup())){
				pickup = i;
			}
			if(c.equals(request.getDelivery())){
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
