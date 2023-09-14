package com.stackroute.ServiceProviderService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private	String productId;
	private String name;
	private String category;
	private String brand;
	//Image productImage;

}
