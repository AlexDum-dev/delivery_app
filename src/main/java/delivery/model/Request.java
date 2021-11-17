package delivery.model;

/**
 * Represents a checkpoint in the request
 * 
 * @author 4IF Group H4144
 * @version 1.0 17 Nov 2021
 */
public class Request {
	private CheckPoint delivery;
	private CheckPoint pickup;
	
	public Request(CheckPoint pickup, CheckPoint delivery) {
		this.delivery = delivery;
		this.pickup = pickup;
	}

	public CheckPoint getDelivery() {
		return delivery;
	}

	public CheckPoint getPickup() {
		return pickup;
	}
	
	
}
