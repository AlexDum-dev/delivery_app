package delivery.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an intersection of the XML File
 * 
 * @author 4IF Group H4144
 * @version 1.0 17 Nov 2021
 */
public class Intersection {
	private String id;
	private double latitude;
	private double longitude;
	
	public Intersection(String id, double latitude, double longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public static double getMaxLatitude(Map<String,Intersection> listInter) {
		
		double max = 0;
		Set<String> setInterId = listInter.keySet();
		for(String id : setInterId) {
			if(listInter.get(id).latitude > max) max = listInter.get(id).latitude;
		}
		
		return max;
	}
	
	public static double getMaxLongitude(Map<String,Intersection> listInter) {
		
		double max = 0;
		Set<String> setInterId = listInter.keySet();
		for(String id : setInterId) {
			if(listInter.get(id).longitude > max) max = listInter.get(id).longitude;
		}
		
		return max;
	}
	
	public static double getMinLongitude(Map<String,Intersection> listInter) {
		
		double min = Double.MAX_VALUE;
		Set<String> setInterId = listInter.keySet();
		for(String id : setInterId) {
			if(listInter.get(id).longitude < min) min = listInter.get(id).longitude;
		}
		
		return min;
	}
	
	public static double getMinLatitude(Map<String,Intersection> listInter) {
		
		double min = Double.MAX_VALUE;
		Set<String> setInterId = listInter.keySet();
		for(String id : setInterId) {
			if(listInter.get(id).latitude < min) min = listInter.get(id).latitude;
		}
		
		return min;
	}

}
