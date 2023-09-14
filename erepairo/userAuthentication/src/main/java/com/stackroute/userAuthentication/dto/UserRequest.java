package com.stackroute.userAuthentication.dto;


/**
 * This is Class UserRequest.
 *
 * @author by Zaid
 */

public class UserRequest{
	
	
	private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private Role role;
    
	public UserRequest() {
		super();
	}
	
	public UserRequest(String userName, String userFirstName, String userLastName, String userPassword, Role role) {
		super();
		this.userName = userName;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.role = role;
		
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
