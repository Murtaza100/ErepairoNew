package com.stackroute.ServiceProviderService.model;

public class SlotTime {

	private String time;
	private AvailabilityStatus slotStatus;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public AvailabilityStatus getSlotStatus() {
		return slotStatus;
	}
	public void setSlotStatus(AvailabilityStatus slotStatus) {
		this.slotStatus = slotStatus;
	}
	
	
}
