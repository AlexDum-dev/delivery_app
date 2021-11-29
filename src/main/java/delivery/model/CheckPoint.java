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
	private int index;
	
	/**
	 * Constructor for the depot
	 * @param type
	 * @param address
	 * @param time
	 */
	public CheckPoint(CheckPointType type, Intersection address, LocalTime time) {
		
		this.type = type;
		this.address = address;
		this.time = time;
		this.duration = 0;
		this.index = -1;
	}
	
	/**
	 * Constructor for ther pickup and the delivery
	 * @param type
	 * @param address
	 * @param duration
	 */
	public CheckPoint(CheckPointType type, Intersection address, int duration) {
		this.type = type;
		this.address = address;
		this.time = null;
		this.duration = duration;
		this.index = -1;
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
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean equals(Object c) {
		// If the object is compared with itself then return true 
        if (c == this) {
            return true;
        }
        
        if (!(c instanceof CheckPoint)) {
            return false;
        }
         
        CheckPoint tmpCheckpoint = (CheckPoint) c;
		
		if (tmpCheckpoint.getType().equals(this.getType()) 
				&& tmpCheckpoint.getAddress().equals(this.getAddress()) 
				&& ((tmpCheckpoint.getTime() == null && this.getTime() == null) || tmpCheckpoint.getTime().equals(this.getTime()))
				&& tmpCheckpoint.getDuration() == this.getDuration()
				&& tmpCheckpoint.getIndex() == this.getIndex()){
			return true;
		}
		return false;
	}
	
}
