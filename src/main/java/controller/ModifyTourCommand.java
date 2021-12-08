package controller;

import model.CheckPoint;
import model.Plan;
import model.Tour;

/**
 * Command to modify the tour
 * Swaps 2 checkpoints in the tour order
 * 
 * @author 4IF Group H4144
 * @version 1.0 7 Dec 2021
 */
public class ModifyTourCommand implements Command {
	
	private int oldCheckPoint;
	private int newCheckPoint;
	private Tour tour;
	private Plan plan;
	
	public ModifyTourCommand(Tour tour, Plan plan, 
			int oldCheckPoint, int newCheckPoint) {
		this.oldCheckPoint = oldCheckPoint;
		this.newCheckPoint = newCheckPoint;
		this.tour = tour;
		this.plan = plan;
	}
	
	@Override
	public void doCommand() {
		CheckPoint c = tour.deleteCheckPoint(oldCheckPoint, 
				plan.getIntersections());
		tour.insertCheckPoint(newCheckPoint, c, plan.getIntersections());
		
		this.tour.actualizeTime();
		this.tour.notifyObservers();
		
	}

	@Override
	public void undoCommand() {
		CheckPoint c = tour.deleteCheckPoint(newCheckPoint, 
				plan.getIntersections());
		tour.insertCheckPoint(oldCheckPoint, c, plan.getIntersections());
		
		this.tour.actualizeTime();
		this.tour.notifyObservers();
	}

}
	