package com.stackroute.userService.config;

import com.stackroute.userService.dto.UserDto;
import com.stackroute.userService.collection.User;
import com.stackroute.userService.service.UserServiceImpl;
import com.stackroute.userService.util.AppConstants;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    org.slf4j.Logger logger = LoggerFactory.getLogger(MessageListener.class);
    @Autowired
    UserServiceImpl userService;
    @RabbitListener(queues = AppConstants.QUEUE)
    public void listener(UserDto userDto) {
        logger.info("{}",userDto);
        User user = new User();
        user.setUserId(userDto.getUserName());
        user.setFirstName(userDto.getUserFirstName());
        user.setLastName(userDto.getUserLastName());
        user.setPassword(userDto.getUserPassword());
        user.setEmailId(userDto.getUserName());
        user.setRole(userDto.getRole());
        userService.addUser(user);
    }
}
