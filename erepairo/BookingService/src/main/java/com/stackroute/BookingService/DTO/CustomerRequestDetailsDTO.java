package com.stackroute.BookingService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDetailsDTO {
    private int ServiceCenterId;
    private String Name;
    private long MobileNumber;
    private String EmailId;
    private String Address;
    private String TicketDescription;
    private String ProductType;
    private String ProductName;
    private LocalTime PrefferedTime;
    private LocalDate PrefferedDate;
}
