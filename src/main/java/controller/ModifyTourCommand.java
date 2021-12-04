package controller;

import java.util.List;

import algorithm.Dijkstra;
import model.Intersection;
import model.Path;
import model.Plan;
import model.Tour;
import view.Window;

public class ModifyTourCommand implements Command {
	
	private int indexCheckpoint;
	private int newIndexCheckPoint;
	private Tour tour;
	private Plan plan;
	private Window window;
	
	public ModifyTourCommand(Tour tour, Plan plan, int indexCheckPoint, int newIndexCheckPoint, Window w) {
		this.indexCheckpoint = indexCheckPoint;
		this.newIndexCheckPoint = newIndexCheckPoint;
		this.tour = tour;
		this.plan = plan;
		this.window = w;
	}
	
	@Override
	public void doCommand() {
		for(int i =  0; i < this.tour.getPath().size();i++) {
			System.out.println(this.tour.getPath().get(i).getPath().get(0).getOrigin().getId());
		}
		//Delete the two path connected to the checkpoint that we want to modify
		//Connect the two checkpoint that was connected to the checkpoint
		Intersection intersectionBeforeCheckpoint = this.tour.getPath().get(indexCheckpoint - 1).getPath().get(0).getOrigin();
		Intersection intersectionNextCheckPoint = null;
		if(this.indexCheckpoint+1 == this.tour.getPath().size()){ //case where it's the last checkpoint before pickup
			intersectionNextCheckPoint = this.plan.getDepot().getAddress();
		} else {
			intersectionNextCheckPoint = this.tour.getPath().get(indexCheckpoint + 1).getPath().get(0).getOrigin();
		}
		Intersection intersectionCheckPointMoved = this.tour.getPath().get(indexCheckpoint).getPath().get(0).getOrigin();
		
		tour.removeConnectedPath(this.indexCheckpoint); //remove path with the checkpoint moved as origin
		
		List<Integer> pedecesor =  Dijkstra.dijkstra(plan.getIntersections(),intersectionBeforeCheckpoint);
		
		//Create path between the 
		Path pathToReconnect = Dijkstra.createPath(plan.getIntersections(), pedecesor, intersectionBeforeCheckpoint.getIndex(),
											intersectionNextCheckPoint.getIndex());
		this.tour.getPath().set(indexCheckpoint-1, pathToReconnect);

		//get the checkpoint before the new place and after and delete the path between both
		Intersection intersectionNewAfterCheckPoint = this.tour.getPath().get(newIndexCheckPoint).getPath().get(0).getOrigin();
		Intersection intersectionNewBeforeCheckPoint = this.tour.getPath().get(newIndexCheckPoint - 1).getPath().get(0).getOrigin();
		
		
		//Launch djikstra for the the checkpoint before
		List<Integer> predecesorCheckPointMoved = Dijkstra.dijkstra(plan.getIntersections(), intersectionCheckPointMoved);
		
		//Constuct the path between the checkpoint we moved and the checkpoint after
		Path pathCheckPointMovedToAfter = Dijkstra.createPath(plan.getIntersections(), predecesorCheckPointMoved, intersectionCheckPointMoved.getIndex(),
											intersectionNewAfterCheckPoint.getIndex());
		this.tour.getPath().set(newIndexCheckPoint-1, pathCheckPointMovedToAfter);
		
		//launch djikstra between from the new before checkpoint : 
		List<Integer> predecesorNewBeforeCheckPoint = Dijkstra.dijkstra(plan.getIntersections(),intersectionNewBeforeCheckPoint);
		
		//construct the path between the new before checkpoint and the checkpoint we moved :
		Path pathNewBeforeCheckPointToCheckPointMoved = Dijkstra.createPath(plan.getIntersections(), predecesorNewBeforeCheckPoint, intersectionNewBeforeCheckPoint.getIndex(),
				intersectionCheckPointMoved.getIndex());
		this.tour.getPath().add(newIndexCheckPoint-1, pathNewBeforeCheckPointToCheckPointMoved);
		
		
		//actualize time
		this.tour.actualizeTime();
		
		this.plan.notifyObservers();
		this.tour.notifyObservers();
		//TODO : do I have to update the index because i changed the list of path ?
		
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub

	}

}
	