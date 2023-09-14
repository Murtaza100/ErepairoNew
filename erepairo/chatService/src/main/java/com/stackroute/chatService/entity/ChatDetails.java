package com.stackroute.chatService.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class ChatDetails.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_details")
public class ChatDetails {

	@Id
	private String chatId;
	private List<Message> messages;
	private int senderId;
	private int receiverId;

}
