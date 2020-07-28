package com.ritesh.springsecurity.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController 
{
//	@GetMapping("/")
//	public String getHomePage()
//	{
//		return "index";
//	}
	
	@GetMapping("/")
	public String getEmployeePage()
	{
		return "home";
	}
	
	@GetMapping("/leaders")
	public String getLeadersPage()
	{
		return "leaders";
	}
	
	@GetMapping("/systems")
	public String getSystemPage()
	{
		return "systems";
	}

		
//	
//	@GetMapping("/leaders")
//	public String getEmployee1Page()
//	{
//		return "<h1>Hello Leader</h1>";
//	}
//	@GetMapping("/systems")
//	public String getEmployee2Page()
//	{
//		return "<h1>Hello Admin</h1>";
//	}
}
