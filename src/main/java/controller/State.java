package controller;

import java.awt.Component;


import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.Tour;
import view.Window;
/**
 * State interface, represents one state of the application
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public interface State {
	default void loadMap(Controller c, Plan p, Tour t, Component frame, Window w) {}
	default void loadRequest(Controller c, Plan p, Tour t, Component frame, Window w) {}
	default void computeTour(Controller c, Plan p, Tour t, Window w) {}
	default void stopTour(Controller c) {}
	default void modifyRequest(Controller c) {}
	default void deleteRequest(ListOfCommands l, Plan plan, Tour tour, Request request, Window w) {}
	default void undo(ListOfCommands l) {}
	default void redo(ListOfCommands l) {}
	default void addRequest(ListOfCommands l, Plan plan, Tour tour, String idPickup, String idDelivery,
			int durationPickup, int durationDelivery, Window w) {}
}
