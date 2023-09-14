package com.stackroute.chatService.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.stackroute.chatService.dto.ChatRequest;
import com.stackroute.chatService.dto.SendMessageRequest;
import com.stackroute.chatService.entity.ChatDetails;
import com.stackroute.chatService.entity.Message;
import com.stackroute.chatService.exception.ChatAlreadyExistException;
import com.stackroute.chatService.exception.IdNotFoundException;
import com.stackroute.chatService.exception.InvalidChatRequestException;
import com.stackroute.chatService.repository.ChatRepository;

@SpringBootTest
class ChatServiceTest {

	@MockBean
	ChatRepository chatRepository;

	@MockBean
	SequenceGeneratorServiceImpl sequenceGeneratorService;

	@Mock
	private GridFsTemplate template;

	@Mock
	private GridFsOperations operations;

	@Autowired
	ChatService chatService;

	ChatDetails getChatDetails() {
		ChatDetails chatDetails = new ChatDetails();
		chatDetails.setChatId("111");
		List<Message> messages = new ArrayList<>();
		Message message = new Message();
		message.setFrom("sender");
		message.setTo("receiver");
		message.setMessageBody("Message");
		message.setSentTime(LocalDateTime.now());
		messages.add(message);
		chatDetails.setMessages(messages);
		return chatDetails;
	}

	@Test
	void testInitiateChat() throws IOException {
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1);
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		ChatRequest chatRequest = new ChatRequest();
		chatRequest.setFrom("sender");
		chatRequest.setTo("receiver");
		chatRequest.setMessageBody("Message");
		chatRequest.setSenderId(1);
		chatRequest.setReceiverId(2);
		assertNotNull(chatService.initiateChat(chatRequest));
	}

	@Test
	void testInitiateChatThrowsException() {
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1);
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		
		ChatRequest chatRequest = new ChatRequest();
		chatRequest.setTo("receiver");
		chatRequest.setMessageBody("Message");
		chatRequest.setSenderId(1);
		chatRequest.setReceiverId(2);
		assertThrows(InvalidChatRequestException.class, ()->{
			chatService.initiateChat(chatRequest);
		});
	}

	@Test
	void testInitiateChatThrowsValidationException() {
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1);
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		when(chatRepository.findBySenderIdAndReceiverId(anyInt(), anyInt())).thenReturn(Optional.of(getChatDetails()));
		
		ChatRequest chatRequest = new ChatRequest();
		chatRequest.setFrom("sender");
		chatRequest.setTo("receiver");
		chatRequest.setMessageBody("Message");
		chatRequest.setSenderId(1);
		chatRequest.setReceiverId(2);
		assertThrows(ChatAlreadyExistException.class, ()->{
			chatService.initiateChat(chatRequest);
		});
	}

	@Test
	void testSendMessage() throws IOException {
		when(chatRepository.findById(any())).thenReturn(Optional.of(getChatDetails()));
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setChatId("101");
		sendMessageRequest.setFrom("sender");
		sendMessageRequest.setTo("receiver");
		sendMessageRequest.setMessageBody("Message");
		assertNotNull(chatService.sendMessage(sendMessageRequest));
	}

	@Test
	void testSendMessageThrowsException() throws IOException {
		when(chatRepository.findById(any())).thenReturn(Optional.empty());
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setChatId("101");
		sendMessageRequest.setFrom("sender");
		sendMessageRequest.setTo("receiver");
		sendMessageRequest.setMessageBody("Message");
		assertThrows(IdNotFoundException.class, ()->{
			chatService.sendMessage(sendMessageRequest);
		});
	}

	@Test
	void testSendMessageThrowsValidationException() throws IOException {
		when(chatRepository.findById(any())).thenReturn(Optional.of(getChatDetails()));
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setChatId("101");
		sendMessageRequest.setTo("receiver");
		sendMessageRequest.setMessageBody("Message");
		assertThrows(InvalidChatRequestException.class, ()->{
			chatService.sendMessage(sendMessageRequest);
		});
	}

	@Test
	void testViewChatById() {
		when(chatRepository.findById(any())).thenReturn(Optional.of(getChatDetails()));
		assertNotNull(chatService.viewChatById("101"));
	}

	@Test
	void testViewChatByIdThrowsException() {
		when(chatRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(IdNotFoundException.class, ()->{
			chatService.viewChatById("101");
		});
	}

	@Test
	void testViewAllChatBySender() {
		when(chatRepository.findBySenderId(anyInt())).thenReturn(Arrays.asList(getChatDetails()));
		assertNotNull(chatService.viewAllChatBySender(1));
	}

	@Test
	void testViewAllChatBySenderThrowsException() {
		when(chatRepository.findBySenderId(anyInt())).thenReturn(new ArrayList<>());
		assertThrows(IdNotFoundException.class, ()->{
			chatService.viewAllChatBySender(1);
		});
	}

	@Test
	void testViewChatBySenderAndReceiver() {
		when(chatRepository.findBySenderIdAndReceiverId(anyInt(), anyInt())).thenReturn(Optional.of(getChatDetails()));
		assertNotNull(chatService.viewChatBySenderAndReceiver(1,2));
	}

	@Test
	void testViewChatBySenderAndReceiverThrowsException() {
		when(chatRepository.findBySenderIdAndReceiverId(anyInt(), anyInt())).thenReturn(Optional.empty());
		assertThrows(IdNotFoundException.class, ()->{
			chatService.viewChatBySenderAndReceiver(1,2);
		});
	}

	@Test
	void testDeleteMessage() {
		when(chatRepository.findByChatIdAndMessages_MessageId(anyString(), anyInt())).thenReturn(Optional.of(getChatDetails()));
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		assertNotNull(chatService.deleteMessage("101",2));
	}

	@Test
	void testDeleteMessageThrowsException() {
		when(chatRepository.findByChatIdAndMessages_MessageId(anyString(), anyInt())).thenReturn(Optional.empty());
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		assertThrows(IdNotFoundException.class, ()->{
			chatService.deleteMessage("101",2);
		});
	}

	@Test
	void testClearChat() {
		when(chatRepository.findById(any())).thenReturn(Optional.of(getChatDetails()));
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		assertNotNull(chatService.clearChat("101"));
	}

	@Test
	void testClearChatThrowsException() {
		when(chatRepository.findById(any())).thenReturn(Optional.empty());
		when(chatRepository.save(any())).thenReturn(getChatDetails());
		assertThrows(IdNotFoundException.class, ()->{
			chatService.clearChat("101");
		});
	}

	@Test
	void testDeleteChat() {
		when(chatRepository.findById(any())).thenReturn(Optional.of(getChatDetails()));
		Mockito.doNothing().doThrow(new IdNotFoundException("test exception")).when(chatRepository)
		.deleteById(anyString());
		assertNotNull(chatService.deleteChat("101"));
	}

	@Test
	void testDeleteChatThrowsException() {
		when(chatRepository.findById(any())).thenReturn(Optional.empty());
		Mockito.doNothing().doThrow(new IdNotFoundException("test exception")).when(chatRepository)
		.deleteById(anyString());
		assertThrows(IdNotFoundException.class, ()->{
			chatService.deleteChat("101");
		});
	}

}
