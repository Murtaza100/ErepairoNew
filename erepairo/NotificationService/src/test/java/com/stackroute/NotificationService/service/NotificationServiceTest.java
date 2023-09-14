package com.stackroute.NotificationService.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.stackroute.NotificationService.dto.NotificationDto;
import com.stackroute.NotificationService.entity.Notification;
import com.stackroute.NotificationService.exception.EmailException;
import com.stackroute.NotificationService.exception.InvalidNotificationRequestException;
import com.stackroute.NotificationService.repository.NotificationRepository;

@SpringBootTest
public class NotificationServiceTest {

	@MockBean
	NotificationRepository notificationRepository;

	@MockBean
	private JavaMailSender javaMailSender;

	@MockBean
	SequenceGeneratorServiceImpl sequenceGeneratorService;

	@Autowired
	NotificationService notificationService;

	@Test
	void sendMailTest() {
		when(notificationRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1l);
		Notification notification = new Notification();
		notification.setId(1l);
		notification.setEmail("sushanth@globallogic@gmail.com");
		when(notificationRepository.save(any())).thenReturn(notification);
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setEmail("sushanth@globallogic@gmail.com");
		notificationDto.setMailBody("This is mail body");
		notificationDto.setMailSubject("This is mail subject");
		String result = notificationService.sendEmail(notificationDto);
		assertNotNull(result);
	}

	@Test
	void sendMailTestThrowsExceptionEmailSubjectNull() {
		when(notificationRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1l);
		Notification notification = new Notification();
		notification.setId(1l);
		notification.setEmail("sushanth@globallogic@gmail.com");
		when(notificationRepository.save(any())).thenReturn(notification);
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setEmail("sushanth@globallogic@gmail.com");
		notificationDto.setMailBody("This is mail body");
		assertThrows(InvalidNotificationRequestException.class, () -> {
			notificationService.sendEmail(notificationDto);
		});
	}

	@Test
	void sendMailTestThrowsExceptionInvalidEmail() {
		Mockito.doThrow(new MailSendException("test exception")).when(javaMailSender)
				.send(Mockito.any(SimpleMailMessage.class));
		when(notificationRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1l);
		Notification notification = new Notification();
		notification.setId(1l);
		notification.setEmail("sushanth@globallogic@gmail.com");
		when(notificationRepository.save(any())).thenReturn(notification);
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setEmail("sushanth@globallogic@gmail.com");
		notificationDto.setMailBody("This is mail body");
		notificationDto.setMailSubject("This is mail subject");
		assertThrows(EmailException.class, () -> {
			notificationService.sendEmail(notificationDto);
		});
	}

	@Test
	void sendCommonMailTest() {
		List<Notification> notifications = new ArrayList<>();
		Notification notification1 = new Notification();
		notification1.setId(1l);
		notification1.setEmail("sushanth@globallogic@gmail.com");

		Notification notification2 = new Notification();
		notification2.setId(2l);
		notification2.setEmail("sushanth2@globallogic@gmail.com");
		notifications.add(notification1);
		notifications.add(notification2);

		when(notificationRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1l);
		Notification notification = new Notification();
		notification.setId(1l);
		notification.setEmail("sushanth@globallogic@gmail.com");
		when(notificationRepository.save(any())).thenReturn(notification);
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMailBody("This is mail body");
		notificationDto.setMailSubject("This is mail subject");
		String result = notificationService.sendCommonEmail(notificationDto);
		assertNotNull(result);
	}

	@Test
	void sendCommonMailTestThrowsExceptionEmptyMailSubject() {
		List<Notification> notifications = new ArrayList<>();
		Notification notification1 = new Notification();
		notification1.setId(1l);
		notification1.setEmail("sushanth@globallogic@gmail.com");

		Notification notification2 = new Notification();
		notification2.setId(2l);
		notification2.setEmail("sushanth2@globallogic@gmail.com");
		notifications.add(notification1);
		notifications.add(notification2);

		when(notificationRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(sequenceGeneratorService.generateSequence(anyString())).thenReturn(1l);
		Notification notification = new Notification();
		notification.setId(1l);
		notification.setEmail("sushanth@globallogic@gmail.com");
		when(notificationRepository.save(any())).thenReturn(notification);
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMailBody("This is mail body");
		assertThrows(InvalidNotificationRequestException.class, () -> {
			notificationService.sendCommonEmail(notificationDto);
		});
	}

}
