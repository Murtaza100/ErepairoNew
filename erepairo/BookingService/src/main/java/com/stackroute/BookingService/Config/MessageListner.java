package com.stackroute.BookingService.Config;

import com.stackroute.BookingService.Service.ServiceProviderInterface;
import com.stackroute.BookingService.Util.AppConstants;
import com.stackroute.BookingService.model.ServiceCenterDetails;
import com.stackroute.BookingService.model.ServiceProvider;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListner {

    @Autowired
    ServiceProviderInterface serviceProviderInterface;

    @RabbitListener(queues = AppConstants.BOOKING_QUEUE)
    public void getServiceDtoFromRabbitMq(ServiceProvider serviceProvider) {
       ServiceCenterDetails serviceCenterDetails=new ServiceCenterDetails();
       serviceCenterDetails.set_id(Integer.parseInt(serviceProvider.getUniqueId()));
       serviceCenterDetails.setServiceCenterName(serviceProvider.getName());
       if(null != serviceProvider.getAddress()) {
    	   serviceCenterDetails.setServiceCenterLocation(serviceProvider.getAddress().toString());
       }
       if(null != serviceProvider.getContactNumber()) {
    	   serviceCenterDetails.setHelplineNo(Long.parseLong(serviceProvider.getContactNumber()));
       }
       serviceCenterDetails.setEmailId(serviceProvider.getEmailId());
       serviceProviderInterface.AddServiceCenterDetails(serviceCenterDetails);
    }
}
