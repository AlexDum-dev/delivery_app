package controller;

import java.util.List;

import algorithm.Dijkstra;
import delivery.model.CheckPoint;
import delivery.model.CheckPointType;
import delivery.model.Path;
import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.Tour;

public class AddRequestCommand implements Command {
	
	private Tour tour;
	private Plan plan;
	private String idPickup;
	private String idDelivery;
	private int durationPickup;
	private int durationDelivery;
	
	
	public AddRequestCommand(Tour tour, Plan plan, String idPickup, String idDelivery, int durationPickup, int durationDelivery) {
		super();
		this.tour = tour;
		this.plan = plan;
		this.idPickup = idPickup;
		this.idDelivery = idDelivery;
		this.durationPickup = durationPickup;
		this.durationDelivery = durationDelivery;	
	}
	
	public AddRequestCommand(Plan plan, Tour tour, String idPickup, String idDelivery) {
		this.tour = tour;
		this.plan = plan;
		this.idPickup = idPickup;
		this.idDelivery = idDelivery;
		
	}
	

	@Override
	public void doCommand() {
		CheckPoint newPickup = new CheckPoint(CheckPointType.PICKUP, plan.getIntersection(idPickup), durationPickup);
		CheckPoint newDelivery = new CheckPoint(CheckPointType.DELIVERY, plan.getIntersection(idDelivery), durationDelivery);
		Request r = new Request(newPickup, newDelivery);
		plan.addRequest(r);
		plan.notifyObservers();
		
		Request req = plan.getRequests().get(plan.getRequests().size() - 1);
		System.out.println("*****************[addRequest] id du pickup : "+req.getPickup().getAddress().getId()+" id du deliv: "+req.getDelivery().getAddress().getId());
		tour.removeLastPath();
		CheckPoint lastCheckPoint = tour.removeLastCheckPoint();
		
		//launch dijkstra for the the pickup and the delivery and the last checkpoint
		List<Integer> predecesorPickupDeparture =  Dijkstra.dijkstra(plan.getIntersections(),req.getPickup().getAddress());
		List<Integer> predecesorDeliveryDeparture = Dijkstra.dijkstra(plan.getIntersections(),req.getDelivery().getAddress());
		List<Integer> predecesorLastIntersectionTour = Dijkstra.dijkstra(plan.getIntersections(), lastCheckPoint.getAddress());
		
		
		Path pathFromLastPontToNewPickup = Dijkstra.createPath(plan.getIntersections(), predecesorLastIntersectionTour, lastCheckPoint.getAddress().getIndex() , req.getPickup().getAddress().getIndex());
		Path pathFromPickupToDelivery = Dijkstra.createPath(plan.getIntersections(), predecesorPickupDeparture, req.getPickup().getAddress().getIndex(), req.getDelivery().getIndex());
		Path pathFromDeliveryToDepot = Dijkstra.createPath(plan.getIntersections(), predecesorDeliveryDeparture, req.getDelivery().getAddress().getIndex(), plan.getDepot().getAddress().getIndex());
		//Create path between last Checkpoint of the last path and the pickup
		//Create path between the pickup and the delivery
		//create path between the delivery and the depot
		//add all the paths
		//update to vew
		
		tour.addPath(pathFromLastPontToNewPickup, lastCheckPoint );
		tour.addPath(pathFromPickupToDelivery, req.getPickup());
		tour.addPath(pathFromDeliveryToDepot, req.getDelivery());
		
		tour.actualizeTime();
		
		tour.notifyObservers();
	}

	@Override
	public void undoCommand() {
		System.out.println("Dans le undo command");
		//Get checkpoint avant pickup et after et reconnect
		//same for delivery
		CheckPoint beforePickup = null;
		CheckPoint beforeDelivery = null;
		CheckPoint afterPickup = null;
		CheckPoint afterDelivery = null;
		int pathPickupIndex = 0;
		int pathDeliveryIndex = 0;
		for(int i = 1; i < tour.getCheckPoint().size() ; i++) {
			System.out.println("CheckPoint : "+tour.getCheckPoint().get(i).getAddress().getId());
			if(tour.getCheckPoint().get(i).getAddress().getId().equals(this.idPickup)){
				beforePickup = tour.getCheckPoint().get(i-1);
				afterPickup = tour.getCheckPoint().get(i+1);
				pathPickupIndex = i-1;
			}
			
			if(tour.getCheckPoint().get(i).getAddress().getId().equals(this.idDelivery)){
				beforeDelivery = tour.getCheckPoint().get(i-1);
				
				if(i == tour.getCheckPoint().size() - 1) {
					afterDelivery = this.plan.getDepot();
				} else {
					afterDelivery = tour.getCheckPoint().get(i+1);
				}
				
	
				pathDeliveryIndex = i-1;
			}
		}
		tour.getCheckPoint().remove(pathPickupIndex+1);
		tour.getCheckPoint().remove(pathDeliveryIndex);
		
		tour.getPath().remove(pathPickupIndex);
		tour.getPath().remove(pathPickupIndex);
		
		tour.getPath().remove(pathDeliveryIndex-2);
		tour.getPath().remove(pathDeliveryIndex-2);
		
		//TODO Update all the index of request and path in the plan
		//just have to put the index = the position in the list
		
		System.out.println("Before pickup : "+beforePickup.getAddress().getId()+ " after pickup "+beforePickup.getAddress().getId());
		System.out.println("Before deliv : "+beforeDelivery.getAddress().getId()+ "after deliv "+afterDelivery.getAddress().getId());
		
		
		List<Integer> predecesorBeforePickupDeparture =  Dijkstra.dijkstra(plan.getIntersections(),beforePickup.getAddress());
		
		List<Integer> predecesorBeforeDeliveryDeparture =  Dijkstra.dijkstra(plan.getIntersections(),beforeDelivery.getAddress());
		
		Path pathFromBeforePickupToAfterPickup = Dijkstra.createPath(plan.getIntersections(),predecesorBeforePickupDeparture, beforePickup.getIndex(), afterPickup.getIndex());
		Path pathFromBeforeDeliverytToAfterDelivery = Dijkstra.createPath(plan.getIntersections(),predecesorBeforePickupDeparture, beforeDelivery.getIndex(), afterDelivery.getIndex());
		
		plan.getRequests().remove(this.idPickup);
		
		tour.getPath().add(pathPickupIndex, pathFromBeforePickupToAfterPickup);
		tour.getPath().add(pathDeliveryIndex, pathFromBeforeDeliverytToAfterDelivery);
		
		
		
		plan.notifyObservers();
		tour.notifyObservers();
		
		

	}

}
