package com.stackroute.userAuthentication.dto;


/**
 * This is Class UserDto.
 *
 * @author by Zaid
 */

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class UserDto {

	private String email;
	
	@NotBlank(message = "First name can't be blank.")
    @NotNull(message = "First name can't be null.")
    private String userFirstName;
	
	@NotBlank(message = "Last name can't be blank.")
    @NotNull(message = "Last name can't be null.")
    private String userLastName;
	
    private String userPassword;

	public UserDto() {
		super();
		
	}

	public UserDto(String email,
			@NotBlank(message = "First name can't be blank.") @NotNull(message = "First name can't be null.") String userFirstName,
			@NotBlank(message = "Last name can't be blank.") @NotNull(message = "Last name can't be null.") String userLastName,
			String userPassword) {
		super();
		this.email = email;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
    

}