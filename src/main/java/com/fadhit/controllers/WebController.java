package com.fadhit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.fadhit.models.Password;
import com.fadhit.models.User;
import com.fadhit.services.UserService;

@Controller
public class WebController {
	@Autowired
	private UserService userService; 
	
	@RequestMapping("/")
	public String homePage() {return "home";}
	
	@RequestMapping("/login")
	public String loginPage() {return "login";}
	
	@RequestMapping("/logout-success")
	public String logoutPage() {return "logout";}
	
	@RequestMapping("/signup")
	public String signUpPage(WebRequest request, Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "signup";
		
	}
	
	@RequestMapping("/edit")
	public String editPage(Model model) {
		User user = new User();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails)
			user = userService.getUserByEmail(((UserDetails)principal).getUsername());
		
		model.addAttribute("user", user);
		return "edit";
	}
	
	@RequestMapping("/edit-password")
	public String editPassPage(Model model) {
		User user = new User();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
			user = userService.getUserByEmail(((UserDetails)principal).getUsername());
		
		model.addAttribute("user", user);
		model.addAttribute("password", new Password());
		
		return "edit-password";
	}

	@RequestMapping("{id}/edit-all")
	public String editAllDataPage(@PathVariable("id")Integer id, Model model) {	
		User user = userService.getUserById(id);

		model.addAttribute("user", user);
		return "edit-all";
	}
	
	@RequestMapping("/all")
	public String showAllPage(Model model){
		User userLogin = new User();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails)
			userLogin = userService.getUserByEmail(((UserDetails)principal).getUsername());
		
		
		List<User> users;
		users = userService.getAllUser();
		model.addAttribute("userLogin", userLogin);
		model.addAttribute("users",users);
		
		return "all-data";
	}
}
