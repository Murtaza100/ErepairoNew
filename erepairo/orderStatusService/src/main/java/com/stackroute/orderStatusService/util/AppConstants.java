package com.stackroute.orderStatusService.util;

/**
 * The Interface AppConstants.
 *
 * @author sushanth
 */
public interface AppConstants {

	String INVALID_BOOKING_ID_EXCEPTION = "No tracklist present for the booningId: %s";
	String INVALID_SERVICE_CENTER_EXCEPTION = "No tracklist present for the Service center name: %s";
	String BOOKING_ID_ALREADY_PRESENT = "Booking Id %s is already present. Please update if needed";

	String EMPLTY_BOOKING_ID_EXCEPTION = "Booking Id cannot be null or empty";
	String EMPLTY_TRACK_STATUS = "Track status cannot be null or empty";
	String EMPLTY_BOOKING_STATUS = "Booking/Order status cannot be null or empty";
	String INVALID_COST = "Calculated cost cannot be less than or equal to 0";
	String EMPLTY_SERVICE_CENTER_NAME = "Service center name cannot be null or empty";
	String EMPTY_CUSTOMER_EMAIL = "Customer email cannot be null or empty";
	
	String STATUS_QUEUE = "status_queue";
	String STATUS_EXCHANGE = "status_exchange";
	String STATUS_ROUTING_KEY = "status_routing_key";

}
