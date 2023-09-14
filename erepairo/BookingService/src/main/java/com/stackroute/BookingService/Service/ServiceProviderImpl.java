package com.stackroute.BookingService.Service;

import com.stackroute.BookingService.DTO.NotificationDto;
import com.stackroute.BookingService.DTO.OrderStatusRequest;
import com.stackroute.BookingService.DTO.PaymentResponse;
import com.stackroute.BookingService.DTO.ServiceStatus;
import com.stackroute.BookingService.Exception.DuplicateValueFound;
import com.stackroute.BookingService.Exception.IdNotFoundException;
import com.stackroute.BookingService.Repository.CustomerServiceRepository;
import com.stackroute.BookingService.Repository.ServiceProviderRepository;
import com.stackroute.BookingService.Util.AppConstants;
import com.stackroute.BookingService.Util.FeignClientOrderStatusUtil;
import com.stackroute.BookingService.model.CustomerRequestsDetails;
import com.stackroute.BookingService.model.ServiceCenterDetails;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;



@Service
public class ServiceProviderImpl implements ServiceProviderInterface {
    @Autowired
    CustomerServiceRepository customerServiceRepository;
    @Autowired
    ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private FeignClientOrderStatusUtil feignClientOrderStatusUtil;


/*
*Returns all the bookings
*/
    public List<CustomerRequestsDetails> GetAllBookings() {
        return this.customerServiceRepository.findAll();
    }
/*
*Return a booking by Unique Id
* @Param ServiceId
*/
    public CustomerRequestsDetails GetBookingById(int serviceId) {
        return customerServiceRepository.findById(serviceId).orElseThrow(() -> new IdNotFoundException(AppConstants.SERVICE_ID_NOT_MESSAGE));
    }

    public List<CustomerRequestsDetails> DeleteBookingById(int serviceId) {
        this.customerServiceRepository.deleteById(serviceId);
        return this.GetAllBookings();
    }

    public CustomerRequestsDetails UpdateStatusById(int serviceId, String status) {
        CustomerRequestsDetails customerRequestsDetails1 = customerServiceRepository.findById(serviceId).orElseThrow(() -> {
            return new IdNotFoundException("Service id not found,please provide valid id");
        });
        customerRequestsDetails1.setStatus(status);
        customerRequestsDetails1 = customerServiceRepository.save(customerRequestsDetails1);
        NotificationDto notificationDto= new NotificationDto();
        notificationDto.setEmail(customerRequestsDetails1.getEmailId());
        notificationDto.setMailSubject("Status has been updated ");
        notificationDto.setMailBody("The Status of your ticket is : "+status);
        System.out.println("Email sent to Customer.");
        rabbitTemplate.convertAndSend(AppConstants.NOTIFICATION_EXCHANGE,AppConstants.NOTIFICATION_ROUTING_KEY,notificationDto);
        
        OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
        orderStatusRequest.setBookingId(serviceId);
        orderStatusRequest.setCurrentStatus(ServiceStatus.PROCESSING);
        orderStatusRequest.setTrackStatus(status);
        rabbitTemplate.convertAndSend(AppConstants.STATUS_EXCHANGE,AppConstants.STATUS_ROUTING_KEY,orderStatusRequest);
        return customerRequestsDetails1;
    }

    public List<CustomerRequestsDetails> UpdateAllStatus(String status,int id) {
        List<CustomerRequestsDetails> list = customerServiceRepository.GetAllBookingsForAServiceCenter(id);

        for (CustomerRequestsDetails customerRequestsDetails : list) {
            customerRequestsDetails.setStatus(status);
            OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
            orderStatusRequest.setBookingId(customerRequestsDetails.getBookingId());
            orderStatusRequest.setCurrentStatus(ServiceStatus.PROCESSING);
            orderStatusRequest.setTrackStatus(status);
            rabbitTemplate.convertAndSend(AppConstants.STATUS_EXCHANGE,AppConstants.STATUS_ROUTING_KEY,orderStatusRequest);
        }

        customerServiceRepository.saveAll(list);
        NotificationDto notificationDto= new NotificationDto();
        notificationDto.setMailSubject("Status has been updated ");
        notificationDto.setMailBody("The Status of your ticket is : "+status);
        System.out.println("Email sent to all Customer.");
        rabbitTemplate.convertAndSend(AppConstants.NOTIFICATION_EXCHANGE,AppConstants.NOTIFICATION_ROUTING_KEY,notificationDto);
        return list;
    }

    public ServiceCenterDetails AddServiceCenterDetails(ServiceCenterDetails serviceCenterDetails) {

        return serviceProviderRepository.save(serviceCenterDetails);
    }

    @Override
    public ServiceCenterDetails UpdateServiceCenterDetailsById(int id, ServiceCenterDetails serviceCenterDetails) {
        ServiceCenterDetails serviceCenterDetails1 = GetServiceCenterDetailsById(id);
        if (serviceCenterDetails1 == null) {
            throw new IdNotFoundException(AppConstants.ID_NOT_FOUND_MESSAGE);
        }



        if(serviceCenterDetails.getServiceCenterLocation()!=null)
        {
            serviceCenterDetails1.setServiceCenterLocation(serviceCenterDetails.getServiceCenterLocation());
        }

        if(serviceCenterDetails.getServiceCenterName()!=null)
        {
            serviceCenterDetails1.setServiceCenterName(serviceCenterDetails.getServiceCenterName());
        }

        if(serviceCenterDetails.getRegularOff()!=null)
        {
            serviceCenterDetails1.setRegularOff(serviceCenterDetails.getRegularOff());
        }

        if(serviceCenterDetails.getHelplineNo()!=0)
        {
            serviceCenterDetails1.setHelplineNo(serviceCenterDetails.getHelplineNo());
        }

        if(serviceCenterDetails.getNoOfTicketsPerDay()!=0)
        {
            serviceCenterDetails1.setNoOfTicketsPerDay(serviceCenterDetails.getNoOfTicketsPerDay());
        }
        if(serviceCenterDetails.getCloseAt()!=null)
        {
            serviceCenterDetails1.setCloseAt(serviceCenterDetails.getCloseAt());
        }
        if(serviceCenterDetails.getOpensAt()!=null)
        {
            serviceCenterDetails1.setOpensAt(serviceCenterDetails.getOpensAt());
        }
        if(!serviceCenterDetails.getCustomNonWorkingDay().isEmpty())
        {
            for(int i=0;i<serviceCenterDetails.getCustomNonWorkingDay().size();i++) {
          for(int j=0;j<serviceCenterDetails1.getCustomNonWorkingDay().size();j++)
          {
              System.out.println("Value of erviceCenterDetails1.getCustomNonWorkingDay().get(j) is "+serviceCenterDetails1.getCustomNonWorkingDay().get(j));
              System.out.println("Value of erviceCenterDetails.getCustomNonWorkingDay().get(i) is "+serviceCenterDetails.getCustomNonWorkingDay().get(i));

              if(serviceCenterDetails1.getCustomNonWorkingDay().get(j).equalsIgnoreCase(serviceCenterDetails.getCustomNonWorkingDay().get(i))) {
                  throw new DuplicateValueFound("Duplicate Value found for custom non working day");
              }

          }
                serviceCenterDetails1.getCustomNonWorkingDay().add(serviceCenterDetails.getCustomNonWorkingDay().get(i));

            }
        }
        return AddServiceCenterDetails(serviceCenterDetails1);
    }

    @Override
    public ServiceCenterDetails GetServiceCenterDetailsById(int id) {

        ServiceCenterDetails serviceCenterDetails = serviceProviderRepository.getbyid(id);
        if (serviceCenterDetails == null && id!=0) {
            throw new IdNotFoundException(AppConstants.ID_NOT_FOUND_MESSAGE);
        }
        else
        return serviceCenterDetails;

    }

    @Override
    public List<ServiceCenterDetails> ListOfAllServiceCenters() {
        return serviceProviderRepository.findAll();
    }

    @Override
    public ServiceCenterDetails UpdateRegularOffDay(int id, String regularoff) {
        ServiceCenterDetails serviceCenterDetails = GetServiceCenterDetailsById(id);
        serviceCenterDetails.setRegularOff(regularoff.toUpperCase());
        serviceProviderRepository.save(serviceCenterDetails);
        return serviceCenterDetails;
    }

    @Override
    public ServiceCenterDetails UpdateNoOfTicketsInADay(int id, int NoOfTickets) {
        ServiceCenterDetails serviceCenterDetails = GetServiceCenterDetailsById(id);
        serviceCenterDetails.setNoOfTicketsPerDay(NoOfTickets);
        AddServiceCenterDetails(serviceCenterDetails);
        return serviceCenterDetails;
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

    @Override
    public ServiceCenterDetails UpdateCustomNonWorkingDayById(int id, String nonworkingday) {
        ServiceCenterDetails serviceCenterDetails = GetServiceCenterDetailsById(id);
        List<String> customnonworkingdays=new ArrayList<String>();

        if (serviceCenterDetails == null) {
            throw new IdNotFoundException(AppConstants.ID_NOT_FOUND_MESSAGE);
        } else {
            System.out.println("At line 212");
            if(!CollectionUtils.isEmpty(serviceCenterDetails.getCustomNonWorkingDay())) {
            	for(String str:serviceCenterDetails.getCustomNonWorkingDay())
                {            System.out.println("At line 214");

                    customnonworkingdays.add(str);
                }
            }
            System.out.println("customnonworkingdays= "+customnonworkingdays);
            for (int i=0;i<customnonworkingdays.size();i++)
            {            System.out.println("At line 220");

                if(customnonworkingdays.get(i).equalsIgnoreCase(nonworkingday))
                    throw new DuplicateValueFound("Duplicate value found for custom non working day");
            }
            System.out.println("At line 225");
            if(!CollectionUtils.isEmpty(serviceCenterDetails.getCustomNonWorkingDay())) {
            	serviceCenterDetails.getCustomNonWorkingDay().add(nonworkingday);
            }else {
            	serviceCenterDetails.setCustomNonWorkingDay(Arrays.asList(nonworkingday));
            }
            return AddServiceCenterDetails(serviceCenterDetails);
        }
    }

    @Override
    public ServiceCenterDetails UpdateServiceCenterTimings(int id,LocalTime OpensAt, LocalTime CloseAt) {
        ServiceCenterDetails serviceCenterDetails = GetServiceCenterDetailsById(id);
        if (serviceCenterDetails == null) {
            throw new IdNotFoundException(AppConstants.ID_NOT_FOUND_MESSAGE);
        }
            if (OpensAt != null) {
                serviceCenterDetails.setOpensAt(OpensAt);
            }
            if (CloseAt != null) {
                serviceCenterDetails.setCloseAt(CloseAt);
            }

            return AddServiceCenterDetails(serviceCenterDetails);

    }
	
	@Override
	public ResponseEntity<PaymentResponse> initiatePayment(int bookingId) {
		return feignClientOrderStatusUtil.initiatePayment(bookingId);
	}
	
	@Override
	public String updatePayment(int bookingId, int amount) {
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
        orderStatusRequest.setBookingId(bookingId);
        orderStatusRequest.setCurrentStatus(ServiceStatus.COMPLETED);
        orderStatusRequest.setCalculatedCost(amount);
        orderStatusRequest.setTrackStatus("The product issue has been resolved. The calculated service charge is: "+ amount);
        rabbitTemplate.convertAndSend(AppConstants.STATUS_EXCHANGE,AppConstants.STATUS_ROUTING_KEY,orderStatusRequest);
		return "Payment details updated successfully";
	}
}

