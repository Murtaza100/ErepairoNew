package com.stackroute.chatService.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Message.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

	@Id
	private int messageId;
	private String messageBody;
	private LocalDateTime sentTime;
	private String from;
	private String to;
	private String attachmenId;

}
