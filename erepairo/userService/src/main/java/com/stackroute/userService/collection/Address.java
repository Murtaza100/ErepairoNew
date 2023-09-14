package com.stackroute.userService.collection;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	private String addressLine1;
	private String addressLine2;
	private String district;
	private String state;
	private String country;
	private int pinCode;
}
