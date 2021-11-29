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
	
	public Request(CheckPoint pickup, CheckPoint delivery) {
		this.delivery = delivery;
		this.pickup = pickup;
		this.index = -1;
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

	public boolean equals(Object r) {
		// If the object is compared with itself then return true 
        if (r == this) {
            return true;
        }
        
        if (!(r instanceof Request)) {
            return false;
        }
         
        Request tmpRequest = (Request) r;
		if (tmpRequest.getDelivery().equals(this.getDelivery()) 
				&& tmpRequest.getPickup().equals(this.getPickup())){
			return true;
		}
		return false;
	}	
	
}
