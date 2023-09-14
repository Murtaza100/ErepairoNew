package com.stackroute.ServiceProviderService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
