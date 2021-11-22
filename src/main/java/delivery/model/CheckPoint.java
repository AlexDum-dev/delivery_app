package delivery.model;

import java.time.LocalTime;

/**
 * Represents a checkpoint in the request
 * 
 * @author 4IF Group H4144
 * @version 1.0 17 Nov 2021
 */
public class CheckPoint {
	private CheckPointType type; // type of checkpoint
	private Intersection address;
	private LocalTime time;
	private int duration; // duration in seconds

	public CheckPoint(CheckPointType type, Intersection address, LocalTime time) {
		this.type = type;
		this.address = address;
		this.time = time;
		this.duration = 0;
	}
	
	public CheckPoint(CheckPointType type, Intersection address, int duration) {
		this.type = type;
		this.address = address;
		this.time = null;
		this.duration = duration;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public CheckPointType getType() {
		return type;
	}

	public Intersection getAddress() {
		return address;
	}

	public int getDuration() {
		return duration;
	}
	
}
