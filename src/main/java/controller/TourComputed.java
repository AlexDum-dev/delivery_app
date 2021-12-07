package controller;

import java.awt.Component;

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
	public void loadMap(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadMap(c, plan, tour, frame, w);
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadRequest(c, plan, tour, frame, w);
	}
	
	@Override
	public void addRequest(Controller c, Window w) {
		w.getMessage().setText("Please select a pickup location.");
		c.setCurrentState(AddRequestPickup.getInstance());
	}
	
	@Override
	public void deleteRequest(ListOfCommands l, Plan plan, Tour tour, String idPickup, String idDelivery) {
		l.add(new DeleteRequestCommand(plan, tour, idPickup, idDelivery));
	}
	
	@Override
	public void modifyRequest(ListOfCommands l, Plan plan, Tour tour, int indexCheckPoint, int newIndexCheckPoint, Window w) {
		l.add(new ModifyTourCommand(tour, plan, indexCheckPoint, newIndexCheckPoint));
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
