package observer;

/**
 * Observer interface
 * 
 * @author Christine SOLNON (from PlaCo sources)
 * @version 1.0
 */
public interface Observer {
	public void update(Observable observed, Object arg);
}
