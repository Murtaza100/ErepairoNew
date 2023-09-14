package com.stackroute.ServiceProviderService.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

	@Transient
	public static final String SEQUENCE_NAME = "serviceprovider_sequence";

	@Id
	private String uniqueId;
	private String name;
	@Email(message = "Entered email address is not valid !")
	private String emailId;
	@Size(min = 4,max = 12,message = "Password must be minimum of 4 characters and maximum of 12 characters !")
	private String password;
	private Address address;
	@Pattern(regexp = "^\\d{10}$",message = "Entered mobile number is not valid !")
	private String contactNumber;
	private List<Product> products;
	private List<Brand> brands;
	private Boolean isHomeServiceAvailable;
	private List<Slot> slots;


	public ServiceProvider(String uniqueId, String name, String emailId, String password, String contactNumber) {
		this.uniqueId = uniqueId;
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.contactNumber = contactNumber;
	}
}
