package com.fadhit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fadhit.models.Password;
import com.fadhit.models.User;
import com.fadhit.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, value="/create-user")
	public void createUser(@RequestBody User user) {
		userService.createUser(user);
	}
	
	@PostMapping("/create-user-form")
	public void createUserForm(@ModelAttribute("user")User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Strength set as 16
	    String encodedPassword = encoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
		userService.createUser(user);
	}
	
	@RequestMapping("/fetch-all")
	public List<User> getAllUser(){
		return userService.getAllUser();
	}
	
	@PostMapping("/{id}/edit-name")
	public User editName(@ModelAttribute("user") User user, @PathVariable("id")Integer id) {
		System.out.print(user.getEmail());
		user.setId(id);
		return userService.editName(user);
	}
	
	@PostMapping("/{id}/edit-password")
	public User editPassword(@ModelAttribute("user") User user, @ModelAttribute("password")Password pass, @PathVariable("id")Integer id) {
		user.setId(id);
		
			return userService.editPassword(user, pass);
	}
	
	@PostMapping("/{id}/edit-all")
	public User editPassword(@ModelAttribute("user") User user, @PathVariable("id")Integer id) {
		user.setId(id);
		
			return userService.editAll(user);
	}
	
//	For update all data for each user	
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
	public User editUser(@PathVariable("id") Integer id, @RequestBody User user) {
		user.setId(id);
		return userService.editUser(user);
	}
	
	@RequestMapping("/{id}/delete")
	public ResponseEntity<?> deleteNote(@PathVariable("id") Integer id){
		userService.deleteUser(id);
		
		return ResponseEntity.ok().build();		
	}

	
}
