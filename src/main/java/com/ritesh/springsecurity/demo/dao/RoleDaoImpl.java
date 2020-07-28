package com.ritesh.springsecurity.demo.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ritesh.springsecurity.demo.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao
{

	//need to inject SessionFactory
	@Autowired		
	private SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	@Override
	public Role findRoleByName(String roleName) 
	{
		//get current hibernate session 
		Session currentSession = sessionFactory.getCurrentSession();
		
		//now retrieve / read from DB using name
		Query<Role> query = currentSession.createQuery("from Role where name=:roleName", Role.class);
		query.setParameter("roleName", roleName);
		
		Role role = null;
		
		try
		{
			role = query.getSingleResult();
		}
		catch (Exception e) {
			role = null;
		}
		return role;
	}

}
