package controller;

import delivery.model.Plan;

/**
 * Controller class
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
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
	
	public void loadMap(Controller c, Plan plan) {
		CommonActions.loadMap(c, plan);
	}
}
