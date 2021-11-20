package controller;

public interface State {
	default void loadMap() {}
	default void loadRequest() {}
	default void computeTour() {}
	default void addRequest() {}
	default void modifyRequest() {}
	default void deleteRequest() {}
	default void undo() {}
	default void redo() {}
}
