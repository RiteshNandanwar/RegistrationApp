package com.ritesh.springsecurity.demo.dao;


import com.ritesh.springsecurity.demo.entity.User;

public interface UserDao
{
	com.ritesh.springsecurity.demo.entity.User findByUserName(String userName);
	
	void save(User user);
}
