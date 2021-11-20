package controller;

import delivery.model.Plan;

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
