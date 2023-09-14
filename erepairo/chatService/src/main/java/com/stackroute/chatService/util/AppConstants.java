package com.stackroute.chatService.util;

/**
 * The Interface AppConstants.
 *
 * @author sushanth
 */
public interface AppConstants {

	String SEQUENCE_NAME = "message_sequence";
	String INVALID_INITIATE_CHAT_EXCEPTION = "From, To, Message body, sender id, receiver id fields cannot be null or empty";
	String SENDER_RECEIVER_ID_ALREADY_EXISTS_EXCEPTION = "Chat already exist for senderId: %s and receiverId: %d. Please use send message.";
	String INVALID_SEND_MESSAGE_EXCEPTION = "From, To, Message body cannot be null or empty";
	String INVALID_CHAT_ID_EXCEPTION = "No Chat available for chatId: %s";
	String INVALID_CHAT_MESSAGE_ID_EXCEPTION = "No message available for chatId: %s and messageId: %s";
	String INVALID_SENDER_RECEIVER_ID_EXCEPTION = "No Chat exists for senderId: %s and receiverId: %s";
	String INVALID_SENDER_ID_EXCEPTION = "No chats available for senderId: %d";
	String INITIATE_CHAT_EXCEPTION = "Chat Id %s not available in the database. Please initiate the chat";
	String CONVERT_BYTE_ARRAY_EXCEPTION = "No file available for the given message";
	String CONVERT_BYTE_ARRAY_UNKNOWN_EXCEPTION = "Error while converting to byte array: ";
	String EMPTY_CHAT_ID_EXCEPTION = "Chat Id cannot be null or enpty";

	String DELETE_CHAT_SUCCESS = "Chat %s deleted successfully";

}
