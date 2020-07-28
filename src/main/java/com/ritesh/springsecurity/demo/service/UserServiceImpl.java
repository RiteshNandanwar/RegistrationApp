package com.ritesh.springsecurity.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ritesh.springsecurity.demo.dao.RoleDao;
import com.ritesh.springsecurity.demo.dao.UserDao;
import com.ritesh.springsecurity.demo.entity.Role;
import com.ritesh.springsecurity.demo.entity.User;
import com.ritesh.springsecurity.demo.user.CrmUser;

@Service
public class UserServiceImpl implements UserService
{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	@Transactional
	public User findByUserName(String userName)
	{
		return userDao.findByUserName(userName);				
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) 
	{
		User user = new User();
		user.setUserName(crmUser.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(crmUser.getPassword()));
		user.setEmail(crmUser.getEmail());
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		
		// Give user default role as "employee"
		
		List<Role> roles = new ArrayList<>();
		
		roles.add(roleDao.findRoleByName("ROLE_EMPLOYEE"));
		if(crmUser.getRole() != null)
		{
			System.out.println("\n\n If block in UserService class "+crmUser.getRole()+"\n\n");  // for debugging
			
			roles.add(roleDao.findRoleByName(crmUser.getRole()));
		}
		
		user.setRole(roles);
				
		System.out.println("\n\n"+user+"\n\n");    //Debug 
		
		userDao.save(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userDao.findByUserName(username);
		if(user == null)
		{
			throw new UsernameNotFoundException("Invalid username or password");
		}	
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRole()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles)
	{
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
