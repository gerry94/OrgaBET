package com.example.Orgabet.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Orgabet.models.User;



public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);
	User findByEmail(String email);

}
