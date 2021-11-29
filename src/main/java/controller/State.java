package controller;

import java.awt.Component;

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
	default void loadMap(Controller c, Plan p, Tour t, Component frame) {}
	default void loadRequest(Controller c, Plan p, Tour t, Component frame) {}
	default void computeTour(Controller c, Plan p, Tour t) {}
	default void modifyRequest(Controller c) {}
	default void deleteRequest(Controller c) {}
	default void undo(ListOfCommands l) {}
	default void redo(ListOfCommands l) {}
	default void addRequest(ListOfCommands l, Plan plan, Tour tour, String idPickup, String idDelivery,
			int durationPickup, int durationDelivery) {}
}
