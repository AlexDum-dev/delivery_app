package controller;

import delivery.model.Plan;

/**
 * State interface, represents one state of the application
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public interface State {
	default void loadMap(Controller c, Plan p) {}
	default void loadRequest(Controller c, Plan p) {}
	default void computeTour(Controller c) {}
	default void addRequest(Controller c) {}
	default void modifyRequest(Controller c) {}
	default void deleteRequest(Controller c) {}
	default void undo(Controller c) {}
	default void redo(Controller c) {}
}
