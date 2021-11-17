package view;

import javax.swing.*;

public class LineRequestView {
	String pickupTextGrid;
	String deliveryTextGrid;
	String  pickupTimeTextGrid;
	String deliveryTimeTextGrid;
	
	
	public LineRequestView() {
		super();
	}
	public LineRequestView(String pickupTextGrid, String deliveryTextGrid, String pickupTimeTextGrid,
			String deliveryTimeTextGrid) {
		super();
		this.pickupTextGrid = pickupTextGrid;
		this.deliveryTextGrid = deliveryTextGrid;
		this.pickupTimeTextGrid = pickupTimeTextGrid;
		this.deliveryTimeTextGrid = deliveryTimeTextGrid;
	}
	
	public String getPickupTextGrid() {
		return pickupTextGrid;
	}
	public void setPickupTextGrid(String pickupTextGrid) {
		this.pickupTextGrid = pickupTextGrid;
	}
	public String getDeliveryTextGrid() {
		return deliveryTextGrid;
	}
	public void setDeliveryTextGrid(String deliveryTextGrid) {
		this.deliveryTextGrid = deliveryTextGrid;
	}
	public String getPickupTimeTextGrid() {
		return pickupTimeTextGrid;
	}
	public void setPickupTimeTextGrid(String pickupTimeTextGrid) {
		this.pickupTimeTextGrid = pickupTimeTextGrid;
	}
	public String getDeliveryTimeTextGrid() {
		return deliveryTimeTextGrid;
	}
	public void setDeliveryTimeTextGrid(String deliveryTimeTextGrid) {
		this.deliveryTimeTextGrid = deliveryTimeTextGrid;
	}
	
	
}