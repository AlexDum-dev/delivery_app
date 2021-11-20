package delivery.model;

import java.time.LocalTime;

/**
 * Represents the depot (starting and ending point of the tour)
 * 
 * @author 4IF Group H4144
 * @version 1.0 17 Nov 2021
 */
public class Depot {
	private Intersection address;
	private LocalTime time;
	
	public Depot(Intersection address, LocalTime time) {
		this.address = address;
		this.time = time;
	}

	public Intersection getAddress() {
		return address;
	}

	public LocalTime getTime() {
		return time;
	}
	
	public Boolean equals(Depot d) {
		if (d.getAddress().equals(this.getAddress()) && d.getTime().equals(this.getTime())){
			return true;
		}
		return false;
	}
}
