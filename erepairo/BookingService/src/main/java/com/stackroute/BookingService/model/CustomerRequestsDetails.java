package com.stackroute.BookingService.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(
        name = "booking_service_model"
)
@Table(
        name = "booking_service_model"
)
public class CustomerRequestsDetails {

    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Id
    private int bookingId;
    @Column(
            nullable = false
    )
    private String Name;
    @Column(
            nullable = false
    )
    private long MobileNumber;
    private String EmailId;
    private String Address;
    private String Status;
    private String TicketDescription;
    private String ProductType;
    private String ProductName;

    private LocalTime PrefferedTime;
    private LocalDate PrefferedDate;
    private LocalDateTime BookedOn;
    private int ServiceCenterId;
}

