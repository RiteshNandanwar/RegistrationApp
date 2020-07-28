package com.ritesh.springsecurity.demo.dao;

import com.ritesh.springsecurity.demo.entity.Role;

public interface RoleDao 
{	
	public Role findRoleByName(String roleName);
}
