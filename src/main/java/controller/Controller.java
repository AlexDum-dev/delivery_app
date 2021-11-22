package controller;

import delivery.model.Plan;
import view.Window;

/**
 * Controller class
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class Controller {
	private Plan plan;
	private Window window;
	private State currentState;

	public Controller(Plan plan) {
		this.plan = plan;
		this.currentState = InitialState.getInstance();
		window = new Window(plan, this);
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
	public void loadMap(Plan p) {
		currentState.loadMap(this, p);
	}
	
	public void loadRequest(Plan p) {
		currentState.loadRequest(this, p);
	}
	public void computeTour() {
		currentState.computeTour(this);
	}
	public void addRequest() {
		currentState.addRequest(this);
	}
	public void modifyRequest() {
		currentState.modifyRequest(this);
	}
	public void deleteRequest() {
		currentState.deleteRequest(this);
	}
	public void undo() {
		currentState.undo(this);
	}
	public void redo() {
		currentState.redo(this);
	}
}
