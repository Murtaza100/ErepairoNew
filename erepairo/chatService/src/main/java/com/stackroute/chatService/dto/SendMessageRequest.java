package com.stackroute.chatService.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
	private String chatId;
	private String messageBody;
	private String from;
	private String to;
	private MultipartFile file;
}
