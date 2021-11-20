package controller;

public class Controller {
	private State currentState;

	public Controller() {
		this.currentState = InitialState.getInstance();
	}
	
	public void loadMap() {
		currentState.loadMap();
	}
	public void loadRequest() {
		currentState.loadRequest();
	}
	public void computeTour() {
		currentState.computeTour();
	}
	public void addRequest() {
		currentState.addRequest();
	}
	public void modifyRequest() {
		currentState.modifyRequest();
	}
	public void deleteRequest() {
		currentState.deleteRequest();
	}
	public void undo() {
		currentState.undo();
	}
	public void redo() {
		currentState.redo();
	}
}
