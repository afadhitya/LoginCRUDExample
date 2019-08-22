package com.fadhit.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fadhit.exception.ResourceNotFoundException;
import com.fadhit.models.Password;
import com.fadhit.models.User;
import com.fadhit.repositories.UserRepository;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;


	public void createUser(User user) {
		
		userRepository.save(user);
	}
	
	public List<User> getAllUser() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);
		
		return users;
	}
	
	public User getUserByEmail(String email){
		User user = new User();
		user = userRepository.findByEmail(email);
		return user;
	}

	public User getUserById(Integer id){
		User user = new User();
		user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return user;
	}

	public User editUser(User user) {
		userRepository.save(user);
		
		return user;
	}

	public void deleteUser(Integer id) {
		User user = userRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		
		userRepository.delete(user);
	}

	public User editName(User user) {
		User userLocal = userRepository.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
		
		userLocal.setName(user.getName());
		userRepository.save(userLocal);
		
		return userLocal;
	}


	public User editPassword(User user, Password pass) {
		User userLocal = userRepository.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(encoder.matches(pass.getOldPassword(), userLocal.getPassword())) {
			System.out.println(pass.getNewPassword());
			userLocal.setPassword(encoder.encode(pass.getNewPassword()));
		}else {
			return null;
		}
		
		userRepository.save(userLocal);
		return userLocal;
	}

	public User editAll(User user) {
		User userLocal = userRepository.findById(user.getId())
	            .orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
		
		userLocal.setEmail(user.getEmail());
		userLocal.setName(user.getName());
		userLocal.setRole(user.getRole());
		userLocal.setStatus(user.getStatus());
		
		userRepository.save(userLocal);
		return userLocal;
	}

}
