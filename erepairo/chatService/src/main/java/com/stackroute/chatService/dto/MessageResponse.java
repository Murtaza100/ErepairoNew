package com.stackroute.chatService.dto;

import java.time.LocalDateTime;

import com.stackroute.chatService.entity.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

	private int messageId;
	private String messageBody;
	private LocalDateTime sentTime;
	private String from;
	private String to;
	private File file; 
	
}
