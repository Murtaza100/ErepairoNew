package com.stackroute.BookingService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {

   
	private String addressId;
	private List<String> addressLines;
	private String district;
	private String state;
	private String country;
	private String pinCode;

}
