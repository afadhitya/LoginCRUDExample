package com.fadhit.repositories;

import org.springframework.data.repository.CrudRepository;

import com.fadhit.models.User;

public interface UserRepository extends CrudRepository <User, Integer>{
	public User findByEmail(String email);
}
