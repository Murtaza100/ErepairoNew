package com.stackroute.chatService.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.stackroute.chatService.dto.ChatRequest;
import com.stackroute.chatService.dto.ChatResponse;
import com.stackroute.chatService.dto.MessageResponse;
import com.stackroute.chatService.dto.SendMessageRequest;
import com.stackroute.chatService.entity.ChatDetails;
import com.stackroute.chatService.entity.File;
import com.stackroute.chatService.entity.Message;
import com.stackroute.chatService.exception.ChatAlreadyExistException;
import com.stackroute.chatService.exception.IdNotFoundException;
import com.stackroute.chatService.exception.InvalidChatRequestException;
import com.stackroute.chatService.repository.ChatRepository;
import com.stackroute.chatService.util.AppConstants;

/**
 * The Class ChatServiceImpl.
 *
 * @author sushanth
 */
@Service
public class ChatServiceImpl implements ChatService {

	/** The log. */
	Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

	/** The chat repository. */
	@Autowired
	ChatRepository chatRepository;

	/** The sequence generator service. */
	@Autowired
	SequenceGeneratorServiceImpl sequenceGeneratorService;

	@Autowired
	private GridFsTemplate template;

	@Autowired
	private GridFsOperations operations;

	/**
	 * Initiate chat.
	 *
	 * @param chatRequest the chat request
	 * @return the chat response
	 */
	@Override
	public ChatResponse initiateChat(ChatRequest chatRequest) {
		log.info("Inside ChatServiceImpl.initiateChat");
		validateInitiateChatRequest(chatRequest);
		validateSenderIdAndReceiverId(chatRequest.getSenderId(), chatRequest.getReceiverId());
		ChatDetails chatDetails = new ChatDetails();
		chatDetails.setSenderId(chatRequest.getSenderId());
		chatDetails.setReceiverId(chatRequest.getReceiverId());
		List<Message> messages = new ArrayList<>();
		Message message = new Message();
		message.setMessageId(sequenceGeneratorService.generateSequence(AppConstants.SEQUENCE_NAME));
		message.setMessageBody(chatRequest.getMessageBody());
		message.setSentTime(LocalDateTime.now());
		message.setFrom(chatRequest.getFrom());
		message.setTo(chatRequest.getTo());
		if (null != chatRequest.getFile()) {
			message.setAttachmenId(addFile(chatRequest.getFile()));
		}
		messages.add(message);
		chatDetails.setMessages(messages);
		return convertEntityToDto(chatRepository.save(chatDetails));
	}

	/**
	 * Adds the file.
	 *
	 * @param file the file
	 * @return the string
	 */
	public String addFile(MultipartFile file) {
		log.info("Inside ChatServiceImpl.addFile");
		DBObject metadata = new BasicDBObject();
		metadata.put("fileSize", file.getSize());

		Object fileID = null;
		try {
			fileID = template.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
		} catch (IOException e) {
			throw new InvalidChatRequestException(e.getMessage());
		}
		return fileID.toString();
	}

	/**
	 * Gets the file.
	 *
	 * @param id the id
	 * @return the file
	 */
	public File getFile(String id) {
		log.info("Inside ChatServiceImpl.viewFile");
		GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));

		File file = new File();

		if (gridFSFile != null && gridFSFile.getMetadata() != null) {
			try {
				file.setFilename(gridFSFile.getFilename());

				file.setFileType(gridFSFile.getMetadata().get("_contentType").toString());

				file.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());

				file.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
				throw new InvalidChatRequestException(
						AppConstants.CONVERT_BYTE_ARRAY_UNKNOWN_EXCEPTION + e.getMessage());
			}
		} else {
			throw new InvalidChatRequestException(AppConstants.CONVERT_BYTE_ARRAY_EXCEPTION);
		}
		return file;
	}

	/**
	 * Send message.
	 *
	 * @param sendMessageRequest the send message request
	 * @return the chat response
	 */
	@Override
	public ChatResponse sendMessage(SendMessageRequest sendMessageRequest) {
		log.info("Inside ChatServiceImpl.sendMessage");
		if (StringUtils.isEmpty(sendMessageRequest.getChatId())) {
			throw new InvalidChatRequestException(AppConstants.EMPTY_CHAT_ID_EXCEPTION);
		}
		String chatId = sendMessageRequest.getChatId();
		Optional<ChatDetails> optionalChatDetails = chatRepository.findById(chatId);
		if (optionalChatDetails.isPresent()) {
			validateSendMessageRequest(sendMessageRequest);
			ChatDetails chatDetail = optionalChatDetails.get();
			Message message = new Message();
			message.setMessageBody(sendMessageRequest.getMessageBody());
			message.setMessageId(sequenceGeneratorService.generateSequence(AppConstants.SEQUENCE_NAME));
			message.setSentTime(LocalDateTime.now());
			message.setFrom(sendMessageRequest.getFrom());
			message.setTo(sendMessageRequest.getTo());
			if (null != sendMessageRequest.getFile()) {
				message.setAttachmenId(addFile(sendMessageRequest.getFile()));
			}
			List<Message> messages = chatDetail.getMessages();
			messages.add(message);
			chatDetail.setMessages(messages);
			return convertEntityToDto(chatRepository.save(chatDetail));
		} else {
			log.error(String.format(AppConstants.INITIATE_CHAT_EXCEPTION, chatId));
			throw new IdNotFoundException(String.format(AppConstants.INITIATE_CHAT_EXCEPTION, chatId));
		}
	}

	/**
	 * View chat by id.
	 *
	 * @param chatId the chat id
	 * @return the chat response
	 */
	@Override
	public ChatResponse viewChatById(String chatId) {
		log.info("Inside ChatServiceImpl.viewChatById");
		ChatDetails chatDetails = chatRepository.findById(chatId).orElseThrow(
				() -> new IdNotFoundException(String.format(AppConstants.INVALID_CHAT_ID_EXCEPTION, chatId)));
		return convertEntityToDto(chatDetails);
	}

	/**
	 * View all chat by sender.
	 *
	 * @param senderId the sender id
	 * @return the list
	 */
	@Override
	public List<ChatResponse> viewAllChatBySender(int senderId) {
		log.info("Inside ChatServiceImpl.viewAllChatBySender");
		List<ChatDetails> chatDetails = chatRepository.findBySenderId(senderId);
		if (CollectionUtils.isEmpty(chatDetails)) {
			throw new IdNotFoundException(String.format(AppConstants.INVALID_SENDER_ID_EXCEPTION, senderId));
		}
		List<ChatResponse> chatResponses = new ArrayList<>();
		for (ChatDetails chatDetail : chatDetails) {
			chatResponses.add(convertEntityToDto(chatDetail));
		}
		return chatResponses;
	}

	/**
	 * View chat by sender and receiver.
	 *
	 * @param senderId   the sender id
	 * @param receiverId the receiver id
	 * @return the chat response
	 */
	@Override
	public ChatResponse viewChatBySenderAndReceiver(int senderId, int receiverId) {
		log.info("Inside ChatServiceImpl.viewChatBySenderAndReceiver");
		ChatDetails chatDetails = chatRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElseThrow(
				() -> new IdNotFoundException(String.format(AppConstants.INVALID_SENDER_RECEIVER_ID_EXCEPTION,
						String.valueOf(senderId), String.valueOf(receiverId))));
		return convertEntityToDto(chatDetails);
	}

	/**
	 * Delete message.
	 *
	 * @param chatId    the chat id
	 * @param messageId the message id
	 * @return the chat response
	 */
	@Override
	public ChatResponse deleteMessage(String chatId, int messageId) {
		log.info("Inside ChatServiceImpl.deleteMessage");
		ChatDetails chatDetails = chatRepository.findByChatIdAndMessages_MessageId(chatId, messageId)
				.orElseThrow(() -> new IdNotFoundException(String.format(AppConstants.INVALID_CHAT_MESSAGE_ID_EXCEPTION,
						chatId, String.valueOf(messageId))));
		List<Message> messages = chatDetails.getMessages();
		for (Iterator<Message> iterator = messages.iterator(); iterator.hasNext();) {
			Message value = iterator.next();
			if (value.getMessageId() == messageId) {
				iterator.remove();
			}
		}
		chatDetails.setMessages(messages);
		return convertEntityToDto(chatRepository.save(chatDetails));
	}

	/**
	 * Clear chat.
	 *
	 * @param chatId the chat id
	 * @return the chat response
	 */
	@Override
	public ChatResponse clearChat(String chatId) {
		log.info("Inside ChatServiceImpl.clearChat");
		ChatDetails chatDetails = chatRepository.findById(chatId).orElseThrow(
				() -> new IdNotFoundException(String.format(AppConstants.INVALID_CHAT_ID_EXCEPTION, chatId)));
		chatDetails.setMessages(new ArrayList<>());
		return convertEntityToDto(chatRepository.save(chatDetails));
	}

	/**
	 * Delete chat.
	 *
	 * @param chatId the chat id
	 * @return the string
	 */
	@Override
	public String deleteChat(String chatId) {
		log.info("Inside ChatServiceImpl.deleteChat");
		chatRepository.findById(chatId).orElseThrow(
				() -> new IdNotFoundException(String.format(AppConstants.INVALID_CHAT_ID_EXCEPTION, chatId)));
		chatRepository.deleteById(chatId);
		log.info(String.format(AppConstants.DELETE_CHAT_SUCCESS, chatId));
		return String.format(AppConstants.DELETE_CHAT_SUCCESS, chatId);
	}

	/**
	 * View message.
	 *
	 * @param chatId    the chat id
	 * @param messageId the message id
	 * @return the byte array resource
	 */
	@Override
	public File viewMessage(String chatId, int messageId) {
		ChatDetails chatDetails = chatRepository.findByChatIdAndMessages_MessageId(chatId, messageId)
				.orElseThrow(() -> new IdNotFoundException(
						String.format(AppConstants.INVALID_CHAT_MESSAGE_ID_EXCEPTION, chatId, messageId)));
		List<Message> messages = chatDetails.getMessages();
		String fileId = "";
		for (Iterator<Message> iterator = messages.iterator(); iterator.hasNext();) {
			Message value = iterator.next();
			if (value.getMessageId() == messageId) {
				fileId = value.getAttachmenId();
			}
		}
		return getFile(fileId);
	}

	/**
	 * Convert entity to dto.
	 *
	 * @param chatDetails the chat details
	 * @return the chat response
	 */
	private ChatResponse convertEntityToDto(ChatDetails chatDetails) {
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setChatId(chatDetails.getChatId());
		chatResponse.setReceiverId(chatDetails.getReceiverId());
		chatResponse.setSenderId(chatDetails.getSenderId());
		List<MessageResponse> messageResponses = new ArrayList<>();
		if (!CollectionUtils.isEmpty(chatDetails.getMessages())) {
			chatDetails.getMessages().forEach(message -> {
				MessageResponse messageResponse = new MessageResponse();
				messageResponse.setMessageId(message.getMessageId());
				messageResponse.setFrom(message.getFrom());
				messageResponse.setTo(message.getTo());
				messageResponse.setMessageBody(message.getMessageBody());
				messageResponse.setSentTime(message.getSentTime());
				if (null != message.getAttachmenId()) {
					File file = getFile(message.getAttachmenId());
					messageResponse.setFile(file);
				}
				messageResponses.add(messageResponse);
			});
		}
		chatResponse.setMessages(messageResponses);
		return chatResponse;

	}

	/**
	 * Validate initiate chat request.
	 *
	 * @param chatRequest the chat request
	 * @return true, if successful
	 */
	private boolean validateInitiateChatRequest(ChatRequest chatRequest) {
		log.info("Inside ChatServiceImpl.validateInitiateChatRequest");
		if (StringUtils.isEmpty(chatRequest.getFrom()) || StringUtils.isEmpty(chatRequest.getTo())
				|| StringUtils.isEmpty(chatRequest.getMessageBody()) || chatRequest.getSenderId() <= 0
				|| chatRequest.getReceiverId() <= 0) {
			log.error(AppConstants.INVALID_INITIATE_CHAT_EXCEPTION);
			throw new InvalidChatRequestException(AppConstants.INVALID_INITIATE_CHAT_EXCEPTION);
		}
		return true;
	}

	/**
	 * Validate sender id and receiver id.
	 *
	 * @param senderId   the sender id
	 * @param receiverId the receiver id
	 * @return true, if successful
	 */
	private boolean validateSenderIdAndReceiverId(int senderId, int receiverId) {
		log.info("Inside ChatServiceImpl.validateSenderIdAndReceiverId");
		Optional<ChatDetails> chatDetails = chatRepository.findBySenderIdAndReceiverId(senderId, receiverId);
		if (chatDetails.isPresent()) {
			log.error(String.format(AppConstants.SENDER_RECEIVER_ID_ALREADY_EXISTS_EXCEPTION, senderId, receiverId));
			throw new ChatAlreadyExistException(
					String.format(AppConstants.SENDER_RECEIVER_ID_ALREADY_EXISTS_EXCEPTION, senderId, receiverId));
		}
		return true;

	}

	/**
	 * Validate send message request.
	 *
	 * @param sendMessageRequest the send message request
	 * @return true, if successful
	 */
	private boolean validateSendMessageRequest(SendMessageRequest sendMessageRequest) {
		log.info("Inside ChatServiceImpl.validateSendMessageRequest");
		if (StringUtils.isEmpty(sendMessageRequest.getFrom()) || StringUtils.isEmpty(sendMessageRequest.getTo())
				|| StringUtils.isEmpty(sendMessageRequest.getMessageBody())) {
			log.error(AppConstants.INVALID_SEND_MESSAGE_EXCEPTION);
			throw new InvalidChatRequestException(AppConstants.INVALID_SEND_MESSAGE_EXCEPTION);
		}
		return true;
	}

}
