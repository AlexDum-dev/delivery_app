package controller;

import java.awt.Component;

import model.Plan;
import model.Tour;
import view.Window;

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

	@Override
	public void loadMap(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadMap(c, plan, tour, frame, w);
	}
}
