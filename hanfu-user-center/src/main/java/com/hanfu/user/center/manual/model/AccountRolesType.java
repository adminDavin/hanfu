package com.hanfu.user.center.manual.model;

import java.io.Serializable;
import java.util.List;

import com.hanfu.user.center.Jurisdiction.model.Roles;

public class AccountRolesType implements Serializable{
	
	private String roleName;
	private List<Roles> roles;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Roles> getRoles() {
		return roles;
	}
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	
}
