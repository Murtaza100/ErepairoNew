package com.stackroute.NotificationService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.NotificationService.dto.NotificationDto;
import com.stackroute.NotificationService.service.NotificationService;

/**
 * The Class NotificationController.
 * 
 * @author sushanth
 *
 */
@RestController
@RequestMapping("/erepairo/notifications")
public class NotificationController {

	Logger log = LoggerFactory.getLogger(NotificationController.class);

	/** The notification service. */
	@Autowired
	NotificationService notificationService;

	/**
	 * Send email.
	 *
	 * @param notificationRequest the notification request
	 * @return the response entity
	 */
	@PostMapping("/email")
	public ResponseEntity<String> sendEmail(@RequestBody NotificationDto notificationRequest) {
		log.info("Starting to execute NotificationController.sendEmail");
		String successMessage = notificationService.sendEmail(notificationRequest);
		return new ResponseEntity<String>(successMessage, HttpStatus.OK);
	}
	
	
	/**
	 * Send common email.
	 *
	 * @param notificationDto the notification dto
	 * @return the response entity
	 */
	@PostMapping("/email/common")
	public ResponseEntity<String> sendCommonEmail(@RequestBody NotificationDto notificationDto) {
		log.info("Starting to execute NotificationController.sendCommonEmail");
		String successMessage = notificationService.sendCommonEmail(notificationDto);
		return new ResponseEntity<String>(successMessage, HttpStatus.OK);
	}
}
