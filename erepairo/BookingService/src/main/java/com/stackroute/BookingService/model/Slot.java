package com.stackroute.BookingService.model;

import java.time.LocalDate;
import java.util.List;

public class Slot {

	private String date;
	private List<SlotTime> timings;
	
	public String getDate() {
		return date.toString();
	}
	public void setDate(LocalDate date) {
		if(null != date) {
			this.date = date.toString();
		}
	}
	public List<SlotTime> getTimings() {
		return timings;
	}
	public void setTimings(List<SlotTime> timings) {
		this.timings = timings;
	}
	
	

}
