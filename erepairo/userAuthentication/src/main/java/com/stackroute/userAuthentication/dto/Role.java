package com.stackroute.userAuthentication.dto;

/**
 * This is Class Role.
 *
 * @author by Zaid
 */

public class Role {
    private String roleName;
    private String roleDescription;
    
	public Role() {
		super();
	}
	
	public Role(String roleName, String roleDescription) {
		super();
		this.roleName = roleName;
		this.roleDescription = roleDescription;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
    
}
