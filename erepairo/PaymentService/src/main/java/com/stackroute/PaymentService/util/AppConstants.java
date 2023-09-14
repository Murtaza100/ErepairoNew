package com.stackroute.PaymentService.util;

public interface AppConstants {

	// stripe constants
	String STRIPE_EXCEPTION = "An error occured during payment createtion";
	String STRIPE_SECRET_KEY = "${stripe.key.secret}";

	// constants
	String CURRENCY_INR = "INR";
	String PRODUCT_NAME = "Erepairo Payments";
	String PRODUCT_DESCRIPTION = "Service charges";

	// endpoints
	String COMMON_URL = "http://localhost:8080/payments";
	String SUCCESS_END_POINT = "/success";
	String CANCEL_END_POINT = "/cancel";

	// exceptions
	String UNKNOWN_EXCEPTION = "An error occured in payment service: ";
	String EMAIL_EXCEPTION = "Email cannot be null";
	String AMOUNT_EXCEPTION = "payment amount cannot be less than or equal to 0";

	String PAYMENT_QUEUE = "payment_queue";
	String PAYMENT_EXCHANGE = "payment_exchange";
	String PAYMENT_ROUTING_KEY = "payment_routing_key";

	// rabbitmq constants
	String NOTIFICATION_QUEUE = "notification_queue";
	String NOTIFICATION_EXCHANGE = "notification_exchange";
	String NOTIFICATION_ROUTING_KEY = "notification_routing_key";
	
	String COMPLETE_PAYMENT_NOTIFICATION_MESSAGE = "Complete Payment for your Booking with Erepairo!!";
	String COMPLETE_PAYMENT_NOTIFICATION_MESSAGE_BODY = "Click the link to complete your payment for %s: %s";
	String SEND_NOTIFICATION_EXCEPTION = "An error occured while sending notification";
}
