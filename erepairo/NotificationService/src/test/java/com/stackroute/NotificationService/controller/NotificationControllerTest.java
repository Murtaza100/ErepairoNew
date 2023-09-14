package com.stackroute.NotificationService.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stackroute.NotificationService.dto.NotificationDto;
import com.stackroute.NotificationService.service.NotificationService;
import com.stackroute.NotificationService.util.AppConstants;

@SpringBootTest
public class NotificationControllerTest {

	@MockBean
	NotificationService notificationService;

	@Autowired
	NotificationController notificationController;

	@Test
	void sendMailTest() {
		when(notificationService.sendEmail(any())).thenReturn(AppConstants.SUCCESS_MESSAGE);
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setEmail("sushanth@globallogic@gmail.com");
		notificationDto.setMailBody("This is mail body");
		notificationDto.setMailSubject("This is mail subject");
		assertNotNull(notificationController.sendEmail(notificationDto));
	}

	@Test
	void sendComonMailTest() {
		when(notificationService.sendCommonEmail(any())).thenReturn(AppConstants.SUCCESS_MESSAGE);
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMailBody("This is mail body");
		notificationDto.setMailSubject("This is mail subject");
		assertNotNull(notificationController.sendCommonEmail(notificationDto));
	}

}
