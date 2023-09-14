package com.stackroute.userService.util;

public interface AppConstants {
    String API_DELETE_SUCCESS_MESSAGE= "record deleted successfully";
    String QUEUE = "message_queue";
    String EXCHANGE = "message_exchange";
    String ROUTING_KEY = "message_routingKey";
    String NOTIFICATION_QUEUE = "notification_queue";
    String NOTIFICATION_EXCHANGE = "notification_exchange";
    String NOTIFICATION_ROUTING_KEY = "notification_routing_key";
    String USER_UPDATED_MAIL_SUBJECT = "Profile updated successfully";
    String USER_UPDATED_MAIL_BODY = "Your profile have been updated successfully, " +
            "please verify your changes."+ "\n\n" +
            "If changes are not done by you, please change your credentials.";
    String PRODUCT_LIST_UPDATED_MAIL_SUBJECT = "Product list updated successfully";
    String PRODUCT_LIST_UPDATED_MAIL_BODY = "Your product list have been updated successfully, " +
            "please verify your changes."+ "\n\n" +
            "If changes are not done by you, please change your credentials.";
}
