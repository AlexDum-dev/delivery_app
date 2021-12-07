package controller;

import java.util.List;
import algorithm.Dijkstra;
import model.CheckPoint;
import model.CheckPointType;
import model.Path;
import model.Plan;
import model.Request;
import model.Tour;
import view.Window;

/**
 * Class to implement the add request command
 * @author alex
 *
 */

public class AddRequestCommand implements Command {
	
	private Tour tour;
	private Plan plan;
	private String idPickup;
	private String idDelivery;
	private int durationPickup;
	private int durationDelivery;
	private Request request;
	private Window window;
	
	public AddRequestCommand(Tour tour, Plan plan, String idPickup, String idDelivery, int durationPickup, int durationDelivery, Window w) {
		super();
		this.tour = tour;
		this.plan = plan;
		this.idPickup = idPickup;
		this.idDelivery = idDelivery;
		this.durationPickup = durationPickup;
		this.durationDelivery = durationDelivery;
		this.window = w;
	}
	
	public AddRequestCommand(Plan plan, Tour tour, String idPickup, String idDelivery, Window w) {
		this.tour = tour;
		this.plan = plan;
		this.idPickup = idPickup;
		this.idDelivery = idDelivery;
		this.window = w;
	}
	

	@Override
	public void doCommand() {
		CheckPoint newPickup = new CheckPoint(CheckPointType.PICKUP, plan.getIntersection(idPickup), durationPickup);
		CheckPoint newDelivery = new CheckPoint(CheckPointType.DELIVERY, plan.getIntersection(idDelivery), durationDelivery);
		Request r = new Request(newPickup, newDelivery);
		plan.addRequest(r);
		
		tour.insertCheckPoint(tour.getCheckPoint().size(), r.getPickup(), plan.getIntersections());
		tour.insertCheckPoint(tour.getCheckPoint().size(), r.getDelivery(), plan.getIntersections());
		
		tour.actualizeTime();
		
		plan.notifyObservers();
		tour.notifyObservers();
		window.getMessage().setText("Request added!");
		window.setDeleteButtonEnabled(true);
		window.setModifyButtonsEnabled(true);
	}

	@Override
	public void undoCommand() {

		tour.deleteCheckPoint(tour.getCheckPoint().size()-1, plan.getIntersections());
		tour.deleteCheckPoint(tour.getCheckPoint().size()-1, plan.getIntersections());
		tour.actualizeTime();
		
		List<Request> r = plan.getRequests();
		r.remove(r.size()-1);

		plan.notifyObservers();
		tour.notifyObservers();
	}

}
