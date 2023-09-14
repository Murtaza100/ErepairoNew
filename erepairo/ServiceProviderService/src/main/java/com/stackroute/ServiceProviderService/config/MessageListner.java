package com.stackroute.ServiceProviderService.config;

import com.stackroute.ServiceProviderService.model.ServiceProvider;
import com.stackroute.ServiceProviderService.dto.ServiceProviderDTO;
import com.stackroute.ServiceProviderService.service.SequenceGeneratorService;
import com.stackroute.ServiceProviderService.service.ServiceProviderServiceImpl;
import com.stackroute.ServiceProviderService.util.AppConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListner {

    @Autowired
    ServiceProviderServiceImpl serviceProviderService;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;


    @RabbitListener(queues = AppConstants.Message_queue)
    public void getServiceDtoFromRabbitMq(ServiceProviderDTO serviceProviderDto) {

        //System.out.println(serviceProviderDto.toString());
        ServiceProvider serviceProvider= new ServiceProvider();
        serviceProvider.setEmailId(serviceProviderDto.getEmail());
        serviceProvider.setName(serviceProviderDto.getUserFirstName());
        serviceProvider.setPassword(serviceProviderDto.getUserPassword());
        serviceProvider.setUniqueId(sequenceGeneratorService.generateSequence(ServiceProvider.SEQUENCE_NAME));
        serviceProviderService.addServiceProvider(serviceProvider);
    }
}
