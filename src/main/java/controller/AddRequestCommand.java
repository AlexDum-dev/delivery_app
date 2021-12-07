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
		plan.notifyObservers();
		
		Request req = plan.getRequests().get(plan.getRequests().size() - 1);
		tour.removeLastPath();
		CheckPoint lastCheckPoint = tour.removeLastCheckPoint();
		
		//launch dijkstra for the the pickup and the delivery and the all the other checkpoints
		List<Integer> predecesorPickupDeparture =  Dijkstra.dijkstra(plan.getIntersections(),req.getPickup().getAddress());
		List<Integer> predecesorDeliveryDeparture = Dijkstra.dijkstra(plan.getIntersections(),req.getDelivery().getAddress());
		List<Integer> predecesorLastIntersectionTour = Dijkstra.dijkstra(plan.getIntersections(), lastCheckPoint.getAddress());
		
		
		Path pathFromLastPontToNewPickup = Dijkstra.createPath(plan.getIntersections(), predecesorLastIntersectionTour, lastCheckPoint.getAddress().getIndex() , req.getPickup().getAddress().getIndex());
		Path pathFromPickupToDelivery = Dijkstra.createPath(plan.getIntersections(), predecesorPickupDeparture, req.getPickup().getAddress().getIndex(), req.getDelivery().getAddress().getIndex());
		Path pathFromDeliveryToDepot = Dijkstra.createPath(plan.getIntersections(), predecesorDeliveryDeparture, req.getDelivery().getAddress().getIndex(), plan.getDepot().getAddress().getIndex());
		//Create path between last Checkpoint of the last path and the pickup
		//Create path between the pickup and the delivery
		//create path between the delivery and the depot
		//add all the paths
		//update to view
		
		tour.addPath(pathFromLastPontToNewPickup, lastCheckPoint );
		tour.addPath(pathFromPickupToDelivery, req.getPickup());
		tour.addPath(pathFromDeliveryToDepot, req.getDelivery());
		
		tour.actualizeTime();
		
		tour.notifyObservers();
		window.getMessage().setText("Request added!");
		window.setDeleteButtonEnabled(true);
		window.setModifyButtonsEnabled(true);
	}

	@Override
	public void undoCommand() {
		
		DeleteRequestCommand deleteCommand = new DeleteRequestCommand(plan, tour, this.idPickup, this.idDelivery, this.window);
		deleteCommand.doCommand();
		plan.notifyObservers();
		tour.notifyObservers();
		
	}

}
