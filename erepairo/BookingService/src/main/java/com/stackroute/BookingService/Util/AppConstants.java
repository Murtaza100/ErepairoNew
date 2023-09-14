package com.stackroute.BookingService.Util;

public interface AppConstants {
    String SERVICE_ID_NOT_MESSAGE = "Service id not found,please provide valid id";
    String ID_NOT_FOUND_MESSAGE = "ID not found,please provide valid id";
    String EMAILID_NOT_FOUND_MESSAGE = "Email_ID not found,please provide valid email id";
    String MOBILE_NUMBER_NOT_FOUND_MESSAGE = "Mobile number not found,please provide valid number";
    String INVALID_DATE_TIME="Please select preferred date and time";
    String NO_SLOTS_AVAILABLE_ON_DATE_MESSAGE = "No slots available, please book for another date";
    String NOTIFICATION_QUEUE = "notification_queue";
    String NOTIFICATION_EXCHANGE = "notification_exchange";
    String NOTIFICATION_ROUTING_KEY = "notification_routing_key";
    
    String STATUS_QUEUE = "status_queue";
	String STATUS_EXCHANGE = "status_exchange";
	String STATUS_ROUTING_KEY = "status_routing_key";

    String BOOKING_QUEUE = "booking_queue";
   String BOOKING_EXCHANGE = "booking_exchange";
    String BOOKING_ROUTING_KEY = "booking_routing_key";
}

