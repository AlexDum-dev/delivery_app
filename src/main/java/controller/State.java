package controller;

import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.Tour;

/**
 * State interface, represents one state of the application
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public interface State {
	default void loadMap(Controller c, Plan p, Tour t) {}
	default void loadRequest(Controller c, Plan p, Tour t) {}
	default void computeTour(Controller c, Plan p, Tour t) {}
	default void addRequest(Controller c) {}
	default void modifyRequest(Controller c) {}
	default void deleteRequest(Controller c) {}
	default void undo(Controller c) {}
	default void redo(Controller c) {}
	default void addRequest(Controller c, Plan plan, Tour tour, Request req) {}
}
