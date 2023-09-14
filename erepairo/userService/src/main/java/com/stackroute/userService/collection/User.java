package com.stackroute.userService.collection;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
	@Id
	private String userId;
	@Size(min = 3,message = "First name should be minimum of 3 characters !")
	private String firstName;
	private String lastName;
	@Size(min = 4,max = 12,message = "Password must be minimum of 4 characters and maximum of 12 characters !")
	private String password;
	@Pattern(regexp = "^\\d{10}$",message = "Entered mobile number is not valid !")
	private String contactNo;
	@Pattern(regexp = "^\\d{10}$",message = "Entered mobile number is not valid !")
	private String alternateContactNo;
	@Email(message = "Entered email address is not valid !")
	private String emailId;
	private List<Address> address;
	private List<Product> products;
	private Role role;
	
}
