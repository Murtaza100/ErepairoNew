package com.stackroute.chatService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.chatService.dto.ChatRequest;
import com.stackroute.chatService.dto.ChatResponse;
import com.stackroute.chatService.dto.SendMessageRequest;
import com.stackroute.chatService.entity.File;
import com.stackroute.chatService.service.ChatService;

/**
 * The Class ChatController.
 *
 * @author sushanth
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

	/** The log. */
	Logger log = LoggerFactory.getLogger(ChatController.class);

	/** The chat service. */
	@Autowired
	ChatService chatService;

	/**
	 * Initiate chat.
	 *
	 * @param chatRequest the chat request
	 * @return the response entity
	 */
	@PostMapping(value = "/initiate", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ChatResponse> initiateChat(@ModelAttribute ChatRequest chatRequest) {
		log.info("Inside ChatController.initiateChat");
		return new ResponseEntity<>(chatService.initiateChat(chatRequest), HttpStatus.ACCEPTED);
	}

	/**
	 * Send message.
	 *
	 * @param chatRequest the chat request
	 * @param chatId      the chat id
	 * @return the response entity
	 */
	@PostMapping(value = "/send", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ChatResponse> sendMessage(@ModelAttribute SendMessageRequest sendMessageRequest) {
		log.info("Inside ChatController.sendMessage");
		return new ResponseEntity<>(chatService.sendMessage(sendMessageRequest), HttpStatus.ACCEPTED);
	}

	/**
	 * View chat by id.
	 *
	 * @param chatId the chat id
	 * @return the response entity
	 */
	@GetMapping("/view/{chatId}")
	public ResponseEntity<ChatResponse> viewChatById(@PathVariable String chatId) {
		log.info("Inside ChatController.viewChatById");
		return new ResponseEntity<>(chatService.viewChatById(chatId), HttpStatus.OK);
	}

	/**
	 * View all chat by sender.
	 *
	 * @param senderId the sender id
	 * @return the response entity
	 */
	@GetMapping("/view/sender/{senderId}")
	public ResponseEntity<List<ChatResponse>> viewAllChatBySender(@PathVariable int senderId) {
		log.info("Inside ChatController.viewAllChatBySender");
		return new ResponseEntity<>(chatService.viewAllChatBySender(senderId), HttpStatus.OK);
	}

	/**
	 * View chat by sender and receiver.
	 *
	 * @param senderId   the sender id
	 * @param receiverId the receiver id
	 * @return the response entity
	 */
	@GetMapping("/view/sender/{senderId}/receiver/{receiverId}")
	public ResponseEntity<ChatResponse> viewChatBySenderAndReceiver(@PathVariable int senderId,
			@PathVariable int receiverId) {
		log.info("Inside ChatController.viewChatBySenderAndReceiver");
		return new ResponseEntity<>(chatService.viewChatBySenderAndReceiver(senderId, receiverId), HttpStatus.OK);
	}

	/**
	 * Delete message.
	 *
	 * @param chatId    the chat id
	 * @param messageId the message id
	 * @return the response entity
	 */
	@DeleteMapping("/message/{chatId}/message/{messageId}")
	public ResponseEntity<ChatResponse> deleteMessage(@PathVariable String chatId, @PathVariable int messageId) {
		log.info("Inside ChatController.deleteMessage");
		return new ResponseEntity<>(chatService.deleteMessage(chatId, messageId), HttpStatus.OK);
	}

	/**
	 * Clear chat.
	 *
	 * @param chatId the chat id
	 * @return the response entity
	 */
	@DeleteMapping("/clear/{chatId}")
	public ResponseEntity<ChatResponse> clearChat(@PathVariable String chatId) {
		log.info("Inside ChatController.clearChat");
		return new ResponseEntity<>(chatService.clearChat(chatId), HttpStatus.OK);
	}

	/**
	 * Delete chat.
	 *
	 * @param chatId the chat id
	 * @return the response entity
	 */
	@DeleteMapping("/message/{chatId}")
	public ResponseEntity<String> deleteChat(@PathVariable String chatId) {
		log.info("Inside ChatController.deleteChat");
		return new ResponseEntity<>(chatService.deleteChat(chatId), HttpStatus.OK);
	}

	/**
	 * Download file.
	 *
	 * @param chatId    the chat id
	 * @param messageId the message id
	 * @return the response entity
	 */
	@GetMapping(value = "file/{chatId}/message/{messageId}", produces = "application/png")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String chatId, @PathVariable int messageId) {
		log.info("Inside ChatController.viewChatBySenderAndReceiver");
		File response = chatService.viewMessage(chatId, messageId);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(response.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getFilename() + "\"")
				.body(new ByteArrayResource(response.getFile()));
	}

}
