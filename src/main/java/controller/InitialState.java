package controller;

public class InitialState implements State {
	private static InitialState instance = null;
	
	private InitialState() {
		
	}
	
	protected static InitialState getInstance() {
		if (instance==null) {
			instance = new InitialState();
		}
		return instance;
	}
	
	public void loadMap() {
		//TODO: Load the map
	}
}
