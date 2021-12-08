package controller;

/**
 * Command interface
 * 
 * @author Christine SOLNON (from PlaCo sources)
 * @version 1.0
 */
public interface Command {
	
	/**
	 * Execute the command this
	 */
	void doCommand();
	
	/**
	 * Execute the reverse command of this
	 */
	void undoCommand();
}