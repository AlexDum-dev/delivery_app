package controller;

import java.util.List;

import algorithm.Dijkstra;
import model.CheckPoint;
import model.Intersection;
import model.Path;
import model.Plan;
import model.Tour;
import view.Window;

/**
 * Class to implement the modify tour command (change the order)
 * @author alex
 *
 */

public class ModifyTourCommand implements Command {
	
	private int indexCheckpoint;
	private int newIndexCheckPoint;
	private Tour tour;
	private Plan plan;
	private ModifyTourCommand invertCommand;
	
	
	public ModifyTourCommand(Tour tour, Plan plan, int indexCheckPoint, int newIndexCheckPoint) {
		this(tour, plan, indexCheckPoint, newIndexCheckPoint, true);
	}
	
	private ModifyTourCommand(Tour tour, Plan plan, int indexCheckPoint, int newIndexCheckPoint, boolean invert) {
		this.indexCheckpoint = indexCheckPoint;
		this.newIndexCheckPoint = newIndexCheckPoint;
		this.tour = tour;
		this.plan = plan;
		if (invert) {
			invertCommand = new ModifyTourCommand(tour, plan, newIndexCheckPoint, indexCheckPoint, false);
		}
	}
	@Override
	public void doCommand() {

		for(int i =  0; i < this.tour.getPath().size();i++) {
			System.out.println(this.tour.getPath().get(i).getPath().get(0).getOrigin().getId());
		}
		//nonsense to put newIndexCheckPoint + 1 = indexCheckPoint
		//Get all intersections we need : 
		Intersection intersectionCheckPointMoved = this.tour.getIntersectionCheckPointByIndex(indexCheckpoint);
		Intersection intersectionBeforeCheckPoint = this.tour.getIntersectionCheckPointByIndex(indexCheckpoint - 1);
		Intersection intersectionAfterCheckPoint = this.tour.getIntersectionCheckPointByIndex(indexCheckpoint + 1);
		
		//Arrange the list of checkpoint : 
		CheckPoint tmp = this.tour.getCheckPoint().get(indexCheckpoint);
		this.tour.getCheckPoint().remove(indexCheckpoint);
		this.tour.getCheckPoint().add(newIndexCheckPoint, tmp);
		
		Intersection newIntersectionBeforeCheckPoint = this.tour.getIntersectionCheckPointByIndex(newIndexCheckPoint-1);
		Intersection newIntersectionAfterCheckPoint = this.tour.getIntersectionCheckPointByIndex(newIndexCheckPoint+1);
		
		
		//Delete all path that going to be replaced : 
		this.tour.removeConnectedPath(indexCheckpoint);
		int decalage = 1;
		int decalagePlacement = 0;
		
		//this.tour.removeConnectedPath(newIndexCheckPoint);
		this.tour.getPath().remove(newIndexCheckPoint-decalage);
		this.tour.getPath().add(newIndexCheckPoint-decalage, new Path());
		this.tour.getPath().add(newIndexCheckPoint-decalage, new Path());
		
		
		//Reput paths : 
		
		//Path between ancient before and ancient after : 
		int index = tour.getIndexInListCheckPoint(intersectionBeforeCheckPoint);
		List<Integer> pedecesor =  Dijkstra.dijkstra(plan.getIntersections(),intersectionBeforeCheckPoint);
		
		//Create path between the 
		Path pathToReconnect = Dijkstra.createPath(plan.getIntersections(), pedecesor, intersectionBeforeCheckPoint.getIndex(),
				intersectionAfterCheckPoint.getIndex());
		this.tour.getPath().set(index, pathToReconnect);
		
		//Path between the checkpoint and the new Before : 
		int indexCheckPointMoved = tour.getIndexInListCheckPoint(intersectionCheckPointMoved);
		
		//Launch djikstra for the the checkpoint before
		List<Integer> predecesorCheckPointMoved = Dijkstra.dijkstra(plan.getIntersections(), 
				intersectionCheckPointMoved);
		//Constuct the path between the checkpoint we moved and the checkpoint after
		
		Path pathCheckPointMovedToAfter = Dijkstra.createPath(plan.getIntersections(), 
		predecesorCheckPointMoved, intersectionCheckPointMoved.getIndex(),
				newIntersectionAfterCheckPoint.getIndex());
				
		this.tour.getPath().set(indexCheckPointMoved-decalagePlacement, pathCheckPointMovedToAfter);
		
		
		//Path between the new before and the checkpoint : 
		int indexNewBefore = tour.getIndexInListCheckPoint(newIntersectionBeforeCheckPoint);
		
		//launch djikstra between from the new before checkpoint : 
		List<Integer> predecesorNewBeforeCheckPoint = Dijkstra.dijkstra(plan.getIntersections(),newIntersectionBeforeCheckPoint);
				
		//construct the path between the new before checkpoint and the checkpoint we moved :
		Path pathNewBeforeCheckPointToCheckPointMoved = Dijkstra.createPath(plan.getIntersections(), predecesorNewBeforeCheckPoint, newIntersectionBeforeCheckPoint.getIndex(),
						intersectionCheckPointMoved.getIndex());
		this.tour.getPath().set(indexNewBefore-decalagePlacement, pathNewBeforeCheckPointToCheckPointMoved);
		
		this.tour.actualizeTime();
		
		this.tour.notifyObservers();
		
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		invertCommand.doCommand();
		this.tour.notifyObservers();
	}

}
	