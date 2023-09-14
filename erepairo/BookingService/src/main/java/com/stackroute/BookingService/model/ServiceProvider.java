package com.stackroute.BookingService.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class ServiceProvider
 *
 * @author Shivani Pakde
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ServiceProvider")
public class ServiceProvider {

	public static final String SEQUENCE_NAME = "serviceprovider_sequence";

	private String uniqueId;
	private String name;
	private String emailId;
	private String password;
	private Address address;
	private String contactNumber;
	private List<Product> products;
	private List<Brand> brands;
	private Boolean isHomeServiceAvailable;
//	private List<Slot> slots;

	public ServiceProvider(String uniqueId, String name, String emailId, String password, String contactNumber) {
		this.uniqueId = uniqueId;
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.contactNumber = contactNumber;
	}
}
