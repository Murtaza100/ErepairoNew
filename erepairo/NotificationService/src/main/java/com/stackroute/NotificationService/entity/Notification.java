package com.stackroute.NotificationService.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Notification.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notification")
public class Notification {

	@Id
	private long id;
	private String email;

}
