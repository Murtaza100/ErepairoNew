package com.stackroute.ServiceProviderService.model;

import java.time.LocalDate;
import java.util.List;

public class Slot {

	private LocalDate date;
	private List<SlotTime> timings;
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public List<SlotTime> getTimings() {
		return timings;
	}
	public void setTimings(List<SlotTime> timings) {
		this.timings = timings;
	}
	
	

}
