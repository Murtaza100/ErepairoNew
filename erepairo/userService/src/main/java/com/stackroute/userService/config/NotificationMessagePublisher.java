package com.stackroute.userService.config;

import com.stackroute.userService.dto.NotificationDto;
import com.stackroute.userService.util.AppConstants;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationMessagePublisher {
    org.slf4j.Logger logger = LoggerFactory.getLogger(NotificationMessagePublisher.class);
    @Autowired
    private RabbitTemplate template;


    public void publishMessage(String emailId, String mailSubject, String mailBody) {
        logger.info("Publishing Message");
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setEmail(emailId);
        notificationDto.setMailSubject(mailSubject);
        notificationDto.setMailBody(mailBody);
        template.convertAndSend(AppConstants.NOTIFICATION_EXCHANGE, AppConstants.NOTIFICATION_ROUTING_KEY, notificationDto);
    }
}
