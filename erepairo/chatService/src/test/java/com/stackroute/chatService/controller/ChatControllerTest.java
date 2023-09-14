package com.stackroute.chatService.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stackroute.chatService.dto.ChatRequest;
import com.stackroute.chatService.dto.ChatResponse;
import com.stackroute.chatService.dto.MessageResponse;
import com.stackroute.chatService.dto.SendMessageRequest;
import com.stackroute.chatService.service.ChatService;

@SpringBootTest
public class ChatControllerTest {

	@MockBean
	ChatService chatService;
	
	@Autowired
	ChatController chatController;
	
	ChatResponse getChatResponse() {
		ChatResponse chatDetails = new ChatResponse();
		chatDetails.setChatId("111");
		List<MessageResponse> messages = new ArrayList<>();
		MessageResponse message = new MessageResponse();
		message.setFrom("sender");
		message.setTo("receiver");
		message.setMessageBody("Message");
		message.setSentTime(LocalDateTime.now());
		messages.add(message);
		chatDetails.setMessages(messages);
		return chatDetails;
	}
	
	@Test
	void testInitiateChat() {
		when(chatService.initiateChat(any())).thenReturn(getChatResponse());
		ChatRequest chatRequest = new ChatRequest();
		chatRequest.setFrom("sender");
		chatRequest.setTo("receiver");
		chatRequest.setMessageBody("Message");
		chatRequest.setSenderId(1);
		chatRequest.setReceiverId(2);
		assertNotNull(chatController.initiateChat(chatRequest));
	}
	
	@Test
	void testSendMessage() {
		when(chatService.sendMessage(any())).thenReturn(getChatResponse());
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setChatId("101");
		sendMessageRequest.setFrom("sender");
		sendMessageRequest.setTo("receiver");
		sendMessageRequest.setMessageBody("Message");
		assertNotNull(chatController.sendMessage(sendMessageRequest));
	}
	
	@Test
	void testViewChatById() {
		when(chatService.viewChatById(any())).thenReturn(getChatResponse());
		assertNotNull(chatController.viewChatById("101"));
	}
	
	@Test
	void testViewChatBySenderId() {
		when(chatService.viewAllChatBySender(anyInt())).thenReturn(Arrays.asList(getChatResponse()));
		assertNotNull(chatController.viewAllChatBySender(1));
	}
	
	@Test
	void testViewChatBySenderAndReceiverId() {
		when(chatService.viewChatBySenderAndReceiver(anyInt(), anyInt())).thenReturn(getChatResponse());
		assertNotNull(chatController.viewChatBySenderAndReceiver(1, 2));
	}
	
	@Test
	void testDeleteMessage() {
		when(chatService.deleteMessage(anyString(), anyInt())).thenReturn(getChatResponse());
		assertNotNull(chatController.deleteMessage("101", 1));
	}
	
	@Test
	void testClearChat() {
		when(chatService.clearChat(anyString())).thenReturn(getChatResponse());
		assertNotNull(chatController.clearChat("101"));
	}
	
	@Test
	void testDeleteChat() {
		when(chatService.deleteChat(anyString())).thenReturn("Success");
		assertNotNull(chatController.deleteChat("101"));
	}
}
