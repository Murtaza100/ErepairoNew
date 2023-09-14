package com.stackroute.userAuthentication.entity;

/**
 * This is Class User.
 *
 * @author by Zaid
 */

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {

    
	@Id
    private String email;
	
	@NotBlank(message = "First name can't be blank.")
    @NotNull(message = "First name can't be null.")
    private String userFirstName;
	
	@NotBlank(message = "Last name can't be blank.")
    @NotNull(message = "Last name can't be null.")
    private String userLastName;
	
    private String userPassword;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE")
    private Role role;

    
    public User() {
		super();
	}

	public User(String email, String userFirstName, String userLastName, String userPassword, Role role) {
		super();
		this.email = email;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.role = role;
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



	public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}