package com.stackroute.chatService.service;

import java.util.List;

import com.stackroute.chatService.dto.ChatRequest;
import com.stackroute.chatService.dto.ChatResponse;
import com.stackroute.chatService.dto.SendMessageRequest;
import com.stackroute.chatService.entity.File;

/**
 * The Interface ChatService.
 *
 * @author sushanth
 */
public interface ChatService {

	/**
	 * Initiate chat.
	 *
	 * @param chatRequest the chat request
	 * @return the chat response
	 */
	ChatResponse initiateChat(ChatRequest chatRequest);

	/**
	 * Send message.
	 *
	 * @param sendMessageRequest the send message request
	 * @return the chat response
	 */
	ChatResponse sendMessage(SendMessageRequest sendMessageRequest);

	/**
	 * View chat by id.
	 *
	 * @param chatId the chat id
	 * @return the chat response
	 */
	ChatResponse viewChatById(String chatId);

	/**
	 * View all chat by sender.
	 *
	 * @param senderId the sender id
	 * @return the list
	 */
	List<ChatResponse> viewAllChatBySender(int senderId);

	/**
	 * View chat by sender and receiver.
	 *
	 * @param senderId   the sender id
	 * @param receiverId the receiver id
	 * @return the chat response
	 */
	ChatResponse viewChatBySenderAndReceiver(int senderId, int receiverId);

	/**
	 * Delete message.
	 *
	 * @param chatId    the chat id
	 * @param messageId the message id
	 * @return the chat response
	 */
	ChatResponse deleteMessage(String chatId, int messageId);

	/**
	 * Clear chat.
	 *
	 * @param chatId the chat id
	 * @return the chat response
	 */
	ChatResponse clearChat(String chatId);

	/**
	 * Delete chat.
	 *
	 * @param chatId the chat id
	 * @return the string
	 */
	String deleteChat(String chatId);

	/**
	 * View message.
	 *
	 * @param chatId    the chat id
	 * @param messageId the message id
	 * @return the byte[]
	 */
	File viewMessage(String chatId, int messageId);

}
