package com.stackroute.NotificationService.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.stackroute.NotificationService.dto.NotificationDto;
import com.stackroute.NotificationService.entity.Notification;
import com.stackroute.NotificationService.exception.EmailException;
import com.stackroute.NotificationService.exception.InvalidNotificationRequestException;
import com.stackroute.NotificationService.repository.NotificationRepository;
import com.stackroute.NotificationService.util.AppConstants;

/**
 * The Class NotificationServiceImpl.
 *
 * @author sushanth
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	/** The java mail sender. */
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	SequenceGeneratorServiceImpl sequenceGeneratorService;

	@Autowired
	private NotificationRepository notificationRepository;

	/**
	 * Send email.
	 *
	 * @param notificationDto the notification dto
	 * @return the string
	 */
	@Override
	public String sendEmail(NotificationDto notificationDto) {
		log.info("Starting to execute NotificationServiceImpl.sendEmail");
		validateRequestForSendMail(notificationDto);
		String email = notificationDto.getEmail();
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(notificationDto.getEmail());
		mail.setFrom(notificationDto.getEmail());
		mail.setSubject(notificationDto.getMailSubject());
		mail.setText(notificationDto.getMailBody());
		try {
			javaMailSender.send(mail);
			Optional<Notification> optionalValue = notificationRepository.findByEmail(email);
			if (optionalValue.isPresent()) {
				log.info("emailId already present in the database. Hence we are skipping the insertion");
			} else {
				Notification notification = new Notification();
				notification.setId(sequenceGeneratorService.generateSequence(AppConstants.SEQUENCE_NAME));
				notification.setEmail(email);
				notificationRepository.save(notification);
				log.info("emailId saved in the database successfully");
			}
		} catch (MailException exception) {
			log.error(AppConstants.MAIL_ERROR_MESSAGE, exception.getMessage());
			throw new EmailException(exception.getMessage());
		} catch (Exception exception) {
			log.error(AppConstants.UNKNOWN_ERROR_MESSAGE, exception.getMessage());
			throw new EmailException(exception.getMessage());
		}
		log.info(String.format(AppConstants.SUCCESS_MESSAGE, notificationDto.getEmail()));
		return String.format(AppConstants.SUCCESS_MESSAGE, notificationDto.getEmail());
	}

	/**
	 * Validate request for send mail.
	 *
	 * @param notificationDto the notification dto
	 * @return true, if successful
	 */
	private boolean validateRequestForSendMail(NotificationDto notificationDto) {
		if (StringUtils.isBlank(notificationDto.getEmail()) || StringUtils.isBlank(notificationDto.getMailBody())
				|| StringUtils.isBlank(notificationDto.getMailSubject())) {
			log.error(AppConstants.INVALID_REQUEST_MESSAGE);
			throw new InvalidNotificationRequestException(AppConstants.INVALID_REQUEST_MESSAGE);
		}
		return true;
	}

	/**
	 * Send common email.
	 *
	 * @param notificationDto the notification dto
	 * @return the string
	 */
	@Override
	public String sendCommonEmail(NotificationDto notificationDto) {
		List<Notification> notifications = notificationRepository.findAll();
		if (StringUtils.isBlank(notificationDto.getMailBody())
				|| StringUtils.isBlank(notificationDto.getMailSubject())) {
			log.error(AppConstants.INVALID_REQUEST_MESSAGE);
			throw new InvalidNotificationRequestException(AppConstants.INVALID_REQUEST_MESSAGE);
		}
		for (Notification notification : notifications) {
			notificationDto.setEmail(notification.getEmail());
			sendEmail(notificationDto);
		}
		return AppConstants.SUCCESS_COMMON_MESSAGE;
	}
}
