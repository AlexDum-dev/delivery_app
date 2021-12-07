package delivery;
import controller.Controller;

import model.Plan;
import model.Tour;

/**
 * Main class of the Delivery application
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class Main {

	public static void main(String[] args) {
		Plan p = new Plan();
		Tour t = new Tour();
		
		Controller c = new Controller(p, t);
	}

}
