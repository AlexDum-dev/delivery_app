package controller;

import delivery.model.Plan;
import delivery.model.Tour;
import view.Window;

/**
 * Controller class
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class Controller {
	private Plan plan;
	private Tour tour;
	private Window window;
	private State currentState;
	private ListOfCommands listOfCommands;

	public Controller(Plan plan, Tour tour) {
		this.plan = plan;
		this.tour = tour;
		this.currentState = InitialState.getInstance();
		this.window = new Window(plan, tour, this);
		this.listOfCommands = new ListOfCommands();
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
	public void loadMap() {
		currentState.loadMap(this, plan, tour, window.getFrame());
	}
	
	public void loadRequest() {
		currentState.loadRequest(this, plan, tour, window.getFrame());
	}
	public void computeTour() {
		currentState.computeTour(this, plan, tour);
	}
	public void addRequest(String idPickup, String idDelivery, int durationPickup, int durationDelivery) {
		currentState.addRequest(listOfCommands, plan, tour, idPickup, idDelivery, durationPickup, durationDelivery);
	}
	public void modifyRequest() {
		currentState.modifyRequest(this);
	}
	public void deleteRequest() {
		currentState.deleteRequest(this);
	}
	public void undo() {
		currentState.undo(this.listOfCommands);
	}
	public void redo() {
		currentState.redo(this.listOfCommands);
	}
}
