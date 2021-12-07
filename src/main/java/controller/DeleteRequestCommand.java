package controller;

import java.util.List;

import algorithm.Dijkstra;
import model.CheckPointType;
import model.Path;
import model.Plan;
import model.Request;
import model.Tour;
import view.Window;

public class DeleteRequestCommand implements Command {
	private Tour tour;
	private Plan plan;
	private String idPickup;
	private String idDelivery;
	private int indexRequest;
	private Window window;
	
	public DeleteRequestCommand(Plan plan, Tour tour, String idPickup, String idDelivery, Window w) {
		this.tour = tour;
		this.plan = plan;
		this.idPickup = idPickup;
		this.idDelivery = idDelivery;
		this.window = w;
	}

	@Override
	public void doCommand() {
		int index = 0;
		for(int i = 1; i < tour.getCheckPoint().size() ; i++) {
			if(tour.getCheckPoint().get(i).getAddress().getId().equals(this.idPickup)){
				index = tour.getCheckPoint().get(i).getIndex();
				this.indexRequest = index;
				tour.actualizeTour(tour.getCheckPoint().get(i).getIndex());
			}
			
		}
		
		plan.getRequests().remove(index); //delete the request in the plan
		plan.actualizeRequestsIndex(); //actualize the index of the requests
		
		for(int i=0; i<tour.getPath().size();i++) {
			if(tour.getPath().get(i).getLength() == -1) {
				List<Integer> predecesorCheckpoint =  Dijkstra.dijkstra(plan.getIntersections(), 
						tour.getCheckPoint().get(i).getAddress());
				Path pathFromBeforeCheckPointtoNextCheckPoint = null;
				if(i+1 == tour.getPath().size()) {
					pathFromBeforeCheckPointtoNextCheckPoint = Dijkstra.createPath(plan.getIntersections(),predecesorCheckpoint, 
																tour.getCheckPoint().get(i).getAddress().getIndex(), 
																plan.getDepot().getAddress().getIndex());
				} else {
					pathFromBeforeCheckPointtoNextCheckPoint = Dijkstra.createPath(plan.getIntersections(),predecesorCheckpoint,
																tour.getCheckPoint().get(i).getAddress().getIndex(), 
																tour.getCheckPoint().get(i+1).getAddress().getIndex());
				}
				tour.getPath().remove(i);
				tour.getPath().add(i, pathFromBeforeCheckPointtoNextCheckPoint);
			}
		}

		this.tour.actualizeTime();
		
		plan.notifyObservers();
		tour.notifyObservers();

	}

	@Override
	public void undoCommand() {
		int durationPickup = 0;
		int durationDelivery = 0;
		for(int i = 1; i < tour.getCheckPoint().size() ; i++) {
			if(tour.getCheckPoint().get(i).getAddress().getId().equals(this.idPickup)){
				if(tour.getCheckPoint().get(i).getType() == CheckPointType.PICKUP) {
					durationPickup = tour.getCheckPoint().get(i).getDuration();
				}
				
				if(tour.getCheckPoint().get(i).getType() == CheckPointType.DELIVERY) {
					durationDelivery = tour.getCheckPoint().get(i).getDuration();
				}
				
			}
		}
		
		AddRequestCommand addCommand = new AddRequestCommand(tour, plan, this.idPickup, this.idDelivery,
										durationPickup, durationDelivery, this.window);
		addCommand.doCommand();
		ModifyTourCommand modifyCommand = new ModifyTourCommand(tour, plan, tour.getPath().size()-1, this.indexRequest);
		modifyCommand.doCommand();
	}

}
