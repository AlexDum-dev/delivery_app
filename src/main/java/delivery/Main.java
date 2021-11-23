package delivery;
import controller.Controller;
import delivery.model.Plan;
import delivery.model.Tour;

/**
 * Main class of the Delivery application
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Plan p = new Plan();
		Tour t = new Tour();
		Controller c = new Controller(p, t);
	}

}
