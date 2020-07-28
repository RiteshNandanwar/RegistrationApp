package com.ritesh.springsecurity.demo.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ritesh.springsecurity.demo.entity.User;
import com.ritesh.springsecurity.demo.service.UserService;
import com.ritesh.springsecurity.demo.user.CrmUser;

@Controller
@RequestMapping("/register")
public class RegistrationController 
{
	 private Logger logger = Logger.getLogger(getClass().getName());
	   
	 @Autowired
	 private UserService userService;
	 
		@InitBinder
		public void initBinder(WebDataBinder dataBinder)
		{
			
			StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
			
			dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		}	
		
	@GetMapping("/viewRegistration")
	public String viewRegistrationPage( Model model)
	{
		model.addAttribute("crmUser", new CrmUser());
		return "registration-form";
	}
	
	@PostMapping("/processRegistrationForm")
	public String submitForm(@Valid @ModelAttribute("crmUser") CrmUser crmUser,
			BindingResult bindingResult, Model model)
	{
		String userName = crmUser.getFirstName();
		System.out.println("\n\n Getting role in controller class : "+ crmUser.getRole()+"\n\n");
		logger.info("Processing registration form for : "+userName);
		
		// Form validation 
		if(bindingResult.hasErrors())
		{
			return "registration-form";
		}
		
		//check DB if user already exist or not
		User existingUser  =  userService.findByUserName(userName);
		
		if(existingUser != null)
		{
			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("registrationError", "User name already Exist");
			logger.warning("User name already exists.");
			return "registration-form";
		}
		
		//create user account
		userService.save(crmUser);
		
		logger.info("Successfully created user : "+userName);
		
		return "registration-confirmation";
	}
}
