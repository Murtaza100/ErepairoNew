package com.stackroute.BookingService.Service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.stackroute.BookingService.DTO.PaymentResponse;
import com.stackroute.BookingService.model.CustomerRequestsDetails;
import com.stackroute.BookingService.model.ServiceCenterDetails;

public interface ServiceProviderInterface {
    List<CustomerRequestsDetails> GetAllBookings();

    CustomerRequestsDetails GetBookingById(int serviceId);

    List<CustomerRequestsDetails> DeleteBookingById(int serviceId);

    CustomerRequestsDetails UpdateStatusById(int serviceId, String Status);

    List<CustomerRequestsDetails> UpdateAllStatus(String status,int id);

    ServiceCenterDetails AddServiceCenterDetails(ServiceCenterDetails serviceCenterDetails);

    ServiceCenterDetails UpdateServiceCenterDetailsById(int id, ServiceCenterDetails serviceCenterDetails);

    ServiceCenterDetails GetServiceCenterDetailsById(int id);

    List<ServiceCenterDetails> ListOfAllServiceCenters();

    ServiceCenterDetails UpdateRegularOffDay(int id, String regularoff);

    ServiceCenterDetails UpdateNoOfTicketsInADay(int id, int NoOfTickets);

    List<CustomerRequestsDetails> GetBookingByEmailID(String email);

    List<CustomerRequestsDetails> GetBookingByMobilNo(Long mob_no);

    ServiceCenterDetails UpdateCustomNonWorkingDayById(int id, String nonworkingday);
    ServiceCenterDetails UpdateServiceCenterTimings(int id,LocalTime OpensAt, LocalTime CloseAt);
    
    ResponseEntity<PaymentResponse> initiatePayment(int bookingId);
    
    String updatePayment(int bookingId, int amount);

}

