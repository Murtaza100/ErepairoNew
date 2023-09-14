package com.stackroute.userAuthentication.dto;


/**
 * This is Class ServiceProviderDTO.
 *
 * @author by Zaid
 */

public class ServiceProviderDTO {

	private String userFirstName;
    private String email;
    private String userPassword;
    
	public ServiceProviderDTO() {
		super();
		
	}

	public ServiceProviderDTO(String userFirstName, String email, String userPassword) {
		super();
		this.userFirstName = userFirstName;
		this.email = email;
		this.userPassword = userPassword;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
    
}