package com.stackroute.BookingService.model;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;


import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ServiceCenterDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCenterDetails {

    @Transient
    public static final String SEQUENCE_NAME = "service_id_seq";
    @Id
    private int _id;
    private String ServiceCenterName;
    private long HelplineNo;
    private String ServiceCenterLocation;
    private String RegularOff;
    private int NoOfTicketsPerDay;
    private List<String> CustomNonWorkingDay;
    private String EmailId;
    private LocalTime OpensAt;
    private LocalTime CloseAt;




}

