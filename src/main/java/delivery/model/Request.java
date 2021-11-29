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
	private int index;
	private boolean active;
	
	public Request(CheckPoint pickup, CheckPoint delivery) {
		this.delivery = delivery;
		this.pickup = pickup;
		this.index = -1;
		this.active = false;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.delivery.setActive(active);
		this.pickup.setActive(active);
		this.active = active;
	}

	public CheckPoint getDelivery() {
		return delivery;
	}

	public CheckPoint getPickup() {
		return pickup;
	}
	
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.pickup.setIndex(index);
		this.delivery.setIndex(index);
		this.index = index;
	}

	public Boolean equals(Request r) {
		if (r.getDelivery().equals(this.getDelivery()) && r.getPickup().equals(this.getPickup())){
			return true;
		}
		return false;
	}	
	
}
