package com.ritesh.springsecurity.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.ritesh.springsecurity.demo.entity.User;
import com.ritesh.springsecurity.demo.user.CrmUser;

public interface UserService extends UserDetailsService
{
	User findByUserName(String userName);

	void save(CrmUser user);
	
}
