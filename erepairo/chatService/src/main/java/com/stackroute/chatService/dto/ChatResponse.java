package com.stackroute.chatService.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class ChatResponse.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

	private String chatId;
	private List<MessageResponse> messages;
	private int senderId;
	private int receiverId;

}
