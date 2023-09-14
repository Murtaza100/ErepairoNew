package com.stackroute.BookingService.Service;

import com.stackroute.BookingService.DTO.CustomerRequestDetailsDTO;
import com.stackroute.BookingService.model.CustomerRequestsDetails;
import com.stackroute.BookingService.model.ServiceCenterDetails;


import java.util.List;

public interface CustomerServiceInterface {
    CustomerRequestsDetails GetBookingById(int serviceId);

    CustomerRequestsDetails BookSlot(CustomerRequestDetailsDTO customerRequestDetailsDTO);

    List<ServiceCenterDetails> ShowServiceCenterDetails();

    List<ServiceCenterDetails> ShowServiceCentersByLocationName(String val);

    List<CustomerRequestsDetails> GetBookingByEmailID(String email);

    List<CustomerRequestsDetails> GetBookingByMobilNo(Long mob_no);


}

