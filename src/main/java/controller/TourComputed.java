package controller;

import model.Plan;
import model.Request;
import model.Tour;
import view.Window;

/**
 * TourComputed State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class TourComputed implements State {
	private static TourComputed instance = null;
	
	private TourComputed() {
		
	}
	
	protected static TourComputed getInstance() {
		if (instance==null) {
			instance = new TourComputed();
		}
		return instance;
	}

	@Override
	public void loadMap(Controller c, Plan plan, Tour tour, Window w) {
		CommonActions.loadMap(c, plan, tour, w);
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour, Window w) {
		CommonActions.loadRequest(c, plan, tour, w);
	}
	
	@Override
	public void addRequest(Controller c, Window w) {
		w.getMessage().setText("Please select a pickup location.");
		c.setCurrentState(AddRequestPickup.getInstance());
	}
	
	@Override
	public void deleteRequest(ListOfCommands l, Plan plan, Tour tour, 
			Request request) {
		l.add(new DeleteRequestCommand(plan, tour, request));
	}
	
	@Override
	public void modifyRequest(ListOfCommands l, Plan plan, Tour tour, 
			int oldCheckPoint, int newCheckPoint) {
		l.add(new ModifyTourCommand(tour, plan, oldCheckPoint, newCheckPoint));
	}
	
	@Override
	public void undo(ListOfCommands l) {
		l.undo();
	}
	
	@Override
	public void redo(ListOfCommands l) {
		l.redo();
	}
}
