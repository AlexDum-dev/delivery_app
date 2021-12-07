package controller;

import java.awt.Component;

import model.Plan;
import model.Request;
import model.Tour;
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
	default void computeTour(Controller c, Plan p, Tour t, Window w, ListOfCommands l) {}
	default void stopTour(Controller c) {}
	default void clickOnMap(Controller c, ListOfCommands listOfCommands, 
			Plan p, Tour t, double lat, double lon, Window window) {}
	default void modifyRequest(ListOfCommands l, Plan plan, Tour tour, int indexCheckPoint, int newIndexCheckPoint, Window w) {}
	default void deleteRequest(ListOfCommands l, Plan plan, Tour tour, String idPickup, String idDelivery) {}
	default void undo(ListOfCommands l) {}
	default void redo(ListOfCommands l) {}
	default void addRequest(Controller c, Window w) {}
}
