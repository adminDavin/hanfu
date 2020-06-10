package com.hanfu.user.center.manual.model;

import java.io.Serializable;
import java.util.List;

import com.hanfu.user.center.Jurisdiction.model.Roles;

public class AccountRolesType implements Serializable{
	
	private String roleCode;
	private List<Roles> roles;
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public List<Roles> getRoles() {
		return roles;
	}
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	
}
