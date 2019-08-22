package com.fadhit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fadhit.UserPrincipal;
import com.fadhit.models.User;
import com.fadhit.repositories.UserRepository;

@Service
public class UserDetailsServiceMine implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("User with " +email+ " not found");
		}
		
		return new UserPrincipal(user);
	}

}
