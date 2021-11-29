package controller;

import java.awt.Component;


import delivery.model.Plan;
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
	default void addRequest(Controller c) {}
	default void modifyRequest(Controller c) {}
	default void deleteRequest(Controller c) {}
	default void undo(Controller c) {}
	default void redo(Controller c) {}
}
