package com.stackroute.BookingService.Controller;


import com.stackroute.BookingService.DTO.PaymentResponse;
import com.stackroute.BookingService.Service.CustomerServiceInterface;
import com.stackroute.BookingService.Service.SequenceGeneratorService;
import com.stackroute.BookingService.Service.ServiceProviderInterface;
import com.stackroute.BookingService.model.ServiceCenterDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalTime;

@RestController
@RequestMapping({"/BookingService/ServiceProvider"})
public class ServiceProviderController {
    @Autowired
    ServiceProviderInterface serviceProviderInterface;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    CustomerServiceInterface customerServiceInterface;


    @GetMapping({"/getallbookings"})
    public ResponseEntity<?> GetAllBooking() {
        return ResponseEntity.ok(this.serviceProviderInterface.GetAllBookings());
    }

    @GetMapping({"/getbookingbyid/{BookingId}"})
    public ResponseEntity<?> GetBookingById(@PathVariable("BookingId") int serviceId) {
        return ResponseEntity.ok(this.serviceProviderInterface.GetBookingById(serviceId));
    }

    @DeleteMapping({"/deletebooking/{BookingId}"})
    public ResponseEntity<?> DeleteBookingById(@PathVariable("BookingId") int serviceId) {
        return ResponseEntity.ok(this.serviceProviderInterface.DeleteBookingById(serviceId));
    }

    @PutMapping({"/UpdateStatusById/{BookingId}/{status}"})
    public ResponseEntity<?> UpdateStatusById(@PathVariable("BookingId") int serviceId, @PathVariable("status") String status) {
        return ResponseEntity.ok(this.serviceProviderInterface.UpdateStatusById(serviceId, status));
    }

    @PutMapping({"/UpdateAllStatus/{status}/{ServiceCenterId}"})
    public ResponseEntity<?> UpdateAllStatus(@PathVariable("status") String status,@PathVariable("ServiceCenterId") int servicecenterid) {
        return ResponseEntity.ok(this.serviceProviderInterface.UpdateAllStatus(status,servicecenterid));
    }

    @PutMapping({"/UpdateServiceCenterDetails/{id}"})
    public ResponseEntity<?> UpdateDetailsById(@PathVariable("id") int id, @RequestBody ServiceCenterDetails serviceCenterDetails) {
        return ResponseEntity.ok(this.serviceProviderInterface.UpdateServiceCenterDetailsById(id, serviceCenterDetails));
    }

    @GetMapping({"/GetServiceCenterDetailsById/{ServiceCenterId}"})
    public ResponseEntity<?> GetServiceCenterDetailsById(@PathVariable(value = "ServiceCenterId") int id) {
        return ResponseEntity.ok(this.serviceProviderInterface.GetServiceCenterDetailsById(id));
    }

    @GetMapping({"/GetAllServiceCentersList"})
    public ResponseEntity<?> GetAllServiceCentersList() {
        return ResponseEntity.ok(this.serviceProviderInterface.ListOfAllServiceCenters());
    }

    @PutMapping({"/UpdateRegularOffDay/{id}/{regularoff}"})
    public ResponseEntity<?> UpdateRegularOffDay(@PathVariable("id") int id, @PathVariable("regularoff") String regularoff) {
        return ResponseEntity.ok(this.serviceProviderInterface.UpdateRegularOffDay(id, regularoff));
    }

    @PutMapping({"/UpdateNoOfTicketsInADay/{id}/{NoOfTickets}"})
    public ResponseEntity<?> UpdateNoOfTicketsInADay(@PathVariable("id") int id, @PathVariable("NoOfTickets") int NoOfTickets) {
        return ResponseEntity.ok(this.serviceProviderInterface.UpdateNoOfTicketsInADay(id, NoOfTickets));
    }

    @GetMapping({"/getbookingbyemailid/{Email_ID}"})
    public ResponseEntity<?> GetBookingByEmailId(@PathVariable("Email_ID") String Email_ID) {
        return ResponseEntity.ok(this.customerServiceInterface.GetBookingByEmailID(Email_ID));
    }

    @GetMapping({"/getbookingbymobilenumber/{Mobile_number}"})
    public ResponseEntity<?> GetBookingByMobileNumber(@PathVariable("Mobile_number") Long Mobile_number) {
        return ResponseEntity.ok(this.customerServiceInterface.GetBookingByMobilNo(Mobile_number));
    }

    @PutMapping({"/UpdateCustomNonWorkingDayById/{id}/{CustomNonWorkingDay}"})
    public ResponseEntity<?> UpdateCustomNonWorkingDayById(@PathVariable("id") int id, @PathVariable("CustomNonWorkingDay") String CustomNonWorkingDay) {
        return ResponseEntity.ok(this.serviceProviderInterface.UpdateCustomNonWorkingDayById(id, CustomNonWorkingDay));
    }
    @PutMapping({"/UpdateServiceCenterTimings/{id}/{OpensAt}/{CloseAt}"})
    public ResponseEntity<?> UpdateServiceCenterTimings(@PathVariable("id") int id, @RequestParam(value = "OpensAt",required = false)LocalTime OpensAt, @RequestParam(value = "CloseAt",required = false) LocalTime CloseAt)  {
        return ResponseEntity.ok(this.serviceProviderInterface.UpdateServiceCenterTimings(id,OpensAt,CloseAt));
    }

	@GetMapping("provider/payment/{bookingId}")
	public ResponseEntity<PaymentResponse> initiatePayment(@PathVariable int bookingId) {
		return serviceProviderInterface.initiatePayment(bookingId);
	}
	
	@PutMapping("provider/payment/{bookingId}/price/{amount}")
	public ResponseEntity<String> updatePayment(@PathVariable int bookingId, @PathVariable int amount) {
		return new ResponseEntity<String>(serviceProviderInterface.updatePayment(bookingId, amount), HttpStatus.ACCEPTED);
	}
    
}

