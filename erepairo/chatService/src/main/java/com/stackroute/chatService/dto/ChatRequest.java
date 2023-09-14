package com.stackroute.chatService.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class ChatRequest.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

	private String messageBody;
	private String from;
	private String to;
	private int senderId;
	private int receiverId;
	private MultipartFile file;
}
