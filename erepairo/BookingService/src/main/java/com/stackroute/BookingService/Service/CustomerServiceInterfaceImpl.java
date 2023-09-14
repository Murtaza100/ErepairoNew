package com.stackroute.BookingService.Service;

import com.stackroute.BookingService.DTO.CustomerRequestDetailsDTO;
import com.stackroute.BookingService.DTO.NotificationDto;
import com.stackroute.BookingService.DTO.OrderStatusRequest;
import com.stackroute.BookingService.DTO.ServiceStatus;
import com.stackroute.BookingService.Exception.DateTimeException;
import com.stackroute.BookingService.Exception.IdNotFoundException;
import com.stackroute.BookingService.Repository.CustomerServiceRepository;
import com.stackroute.BookingService.Repository.ServiceProviderRepository;
import com.stackroute.BookingService.Util.AppConstants;
import com.stackroute.BookingService.model.CustomerRequestsDetails;
import com.stackroute.BookingService.model.ServiceCenterDetails;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CustomerServiceInterfaceImpl implements CustomerServiceInterface {
    @Autowired
    CustomerServiceRepository customerServiceRepository;
    @Autowired
    ServiceProviderRepository serviceProviderRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public CustomerRequestsDetails GetBookingById(int serviceId) {
        return customerServiceRepository.findById(serviceId).orElseThrow(() -> new IdNotFoundException(AppConstants.SERVICE_ID_NOT_MESSAGE));
    }

    public CustomerRequestsDetails BookSlot(CustomerRequestDetailsDTO customerRequestDetailsDTO) {

        ServiceCenterDetails serviceCenterDetails = serviceProviderRepository.findById(customerRequestDetailsDTO.getServiceCenterId()).get();
        if(serviceCenterDetails==null)
        {
            throw new IdNotFoundException(AppConstants.ID_NOT_FOUND_MESSAGE);
        }
        int count = this.customerServiceRepository.CountOfRequests(customerRequestDetailsDTO.getPrefferedDate());
        LocalTime CloseAt = serviceCenterDetails.getCloseAt();
        LocalTime OpensAt = serviceCenterDetails.getOpensAt();
        int val1 = customerRequestDetailsDTO.getPrefferedTime().compareTo(CloseAt);
        int val2 = customerRequestDetailsDTO.getPrefferedTime().compareTo(OpensAt);


        if (customerRequestDetailsDTO.getPrefferedDate() == null || customerRequestDetailsDTO.getPrefferedTime() == null) {
            throw new DateTimeException(AppConstants.INVALID_DATE_TIME);
        }
        if(!CollectionUtils.isEmpty(serviceCenterDetails.getCustomNonWorkingDay())) {
        	for (String list : serviceCenterDetails.getCustomNonWorkingDay()) {
                if (list.equalsIgnoreCase(customerRequestDetailsDTO.getPrefferedDate().getDayOfWeek().toString()))
                    throw new DateTimeException("Sorry but our Service Center timings are " + serviceCenterDetails.getOpensAt() + "-" + serviceCenterDetails.getCloseAt() + " and remain closed on " + serviceCenterDetails.getRegularOff());
            }
        }


        if (count >= serviceCenterDetails.getNoOfTicketsPerDay()) {
            throw new DateTimeException(AppConstants.NO_SLOTS_AVAILABLE_ON_DATE_MESSAGE);
        } else if (customerRequestDetailsDTO.getPrefferedDate().getDayOfWeek().toString().equalsIgnoreCase(serviceCenterDetails.getRegularOff())) {
            throw new DateTimeException("Sorry but our Service Center timings are " + serviceCenterDetails.getOpensAt() + "-" + serviceCenterDetails.getCloseAt() + " and remain closed on " + serviceCenterDetails.getRegularOff());
        } else if (val1 <= 0 && val2 >= 0) {

            CustomerRequestsDetails customerRequestsDetails = new CustomerRequestsDetails();
            customerRequestsDetails = CustomerRequestDetailsToCustomerRequestDetailsDTO(customerRequestDetailsDTO);
            customerRequestsDetails.setBookedOn(LocalDateTime.now());
            customerRequestsDetails.setStatus("Submitted SuccesFully");
            CustomerRequestsDetails customerRequestsDetails1 = customerServiceRepository.save(customerRequestsDetails);
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setEmail(customerRequestsDetails1.getEmailId());
            notificationDto.setMailSubject("New Request has been raised");
            notificationDto.setMailBody("New Request has been raised in the system and the Request ID is: " + customerRequestsDetails1.getBookingId());
            System.out.println("Email sent to Customer.");
            rabbitTemplate.convertAndSend(AppConstants.NOTIFICATION_EXCHANGE, AppConstants.NOTIFICATION_ROUTING_KEY, notificationDto);
            
            OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
            orderStatusRequest.setBookingId(customerRequestsDetails.getBookingId());
            orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
            orderStatusRequest.setCustomerEmail(customerRequestsDetails.getEmailId());
            orderStatusRequest.setServiceCenterName(serviceCenterDetails.getServiceCenterName());
            orderStatusRequest.setTrackStatus("Booking confirmed with booking id: " + customerRequestsDetails.getBookingId());
            rabbitTemplate.convertAndSend(AppConstants.STATUS_EXCHANGE,AppConstants.STATUS_ROUTING_KEY,orderStatusRequest);
            System.out.println("exiting");
            return customerRequestsDetails1;
        } else {
            throw new DateTimeException("Sorry but our Service Center timings are " + serviceCenterDetails.getOpensAt() + "-" + serviceCenterDetails.getCloseAt() + " and remain closed on " + serviceCenterDetails.getRegularOff());

        }
    }

    public List<ServiceCenterDetails> ShowServiceCenterDetails() {
        return this.serviceProviderRepository.findAll();
    }

    @Override
    public List<ServiceCenterDetails> ShowServiceCentersByLocationName(String val) {
        return serviceProviderRepository.GetByLocationName(val);
    }

    @Override
    public List<CustomerRequestsDetails> GetBookingByEmailID(String email) {
        List<CustomerRequestsDetails> customerRequestsDetails = customerServiceRepository.GetDetailsByEmailID(email);
        if (customerRequestsDetails.isEmpty()) {
            throw new IdNotFoundException(AppConstants.EMAILID_NOT_FOUND_MESSAGE);
        } else
            return customerRequestsDetails;
    }

    @Override
    public List<CustomerRequestsDetails> GetBookingByMobilNo(Long mob_no) {
        List<CustomerRequestsDetails> customerRequestsDetails = customerServiceRepository.GetDetailsByPhnNo(mob_no);
        if (customerRequestsDetails.isEmpty()) {
            throw new IdNotFoundException(AppConstants.MOBILE_NUMBER_NOT_FOUND_MESSAGE);
        } else
            return customerRequestsDetails;

    }

    private CustomerRequestsDetails CustomerRequestDetailsToCustomerRequestDetailsDTO(CustomerRequestDetailsDTO customerRequestDetailsDTO) {
        CustomerRequestsDetails customerRequestsDetails = modelMapper.map(customerRequestDetailsDTO, CustomerRequestsDetails.class);
        return customerRequestsDetails;
    }

}

