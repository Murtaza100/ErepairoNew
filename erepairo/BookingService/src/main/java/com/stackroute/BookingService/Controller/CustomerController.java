package com.stackroute.BookingService.Controller;

import com.stackroute.BookingService.DTO.CustomerRequestDetailsDTO;
import com.stackroute.BookingService.Service.CustomerServiceInterface;
import com.stackroute.BookingService.Service.ServiceProviderInterface;
import com.stackroute.BookingService.model.CustomerRequestsDetails;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping({"/BookingService/Customer"})
public class CustomerController {
    @Autowired
    CustomerServiceInterface customerServiceInterface;

    @Autowired
    ServiceProviderInterface serviceProviderInterface;


    @PostMapping({"/BookSlot"})
    public ResponseEntity<?> BookSlot(@RequestBody CustomerRequestDetailsDTO customerRequestsDetailDTO) {

        CustomerRequestsDetails customerRequestsDetails1 = customerServiceInterface.BookSlot(customerRequestsDetailDTO);

        return ResponseEntity.ok(customerRequestsDetails1);
    }

    @GetMapping({"/getbookingbyid/{BookingId}"})
    public ResponseEntity<?> GetBookingById(@PathVariable("BookingId") int BookingId) {
        return ResponseEntity.ok(this.customerServiceInterface.GetBookingById(BookingId));
    }

    @GetMapping({"/GetServiceCenterDetailsById/{id}"})
    public ResponseEntity<?> GetServiceCenterDetailsById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.serviceProviderInterface.GetServiceCenterDetailsById(id));
    }

    @GetMapping({"/GetServiceCentersByLocationName/{val}"})
    public ResponseEntity<?> GetAllServiceCentersList(@PathVariable("val") String val) {
        return ResponseEntity.ok(this.customerServiceInterface.ShowServiceCentersByLocationName(val));
    }

    @GetMapping({"/GetAllServiceCentersList"})
    public ResponseEntity<?> GetAllServiceCentersList() {
        return ResponseEntity.ok(this.serviceProviderInterface.ListOfAllServiceCenters());
    }

    @GetMapping({"/getbookingbyemailid/{Email_ID}"})
    public ResponseEntity<?> GetBookingByEmailId(@PathVariable("Email_ID") String Email_ID) {
        return ResponseEntity.ok(this.customerServiceInterface.GetBookingByEmailID(Email_ID));
    }

    @GetMapping({"/getbookingbymobilenumber/{Mobile_number}"})
    public ResponseEntity<?> GetBookingByMobileNumber(@PathVariable("Mobile_number") Long Mobile_number) {
        return ResponseEntity.ok(this.customerServiceInterface.GetBookingByMobilNo(Mobile_number));
    }

}

