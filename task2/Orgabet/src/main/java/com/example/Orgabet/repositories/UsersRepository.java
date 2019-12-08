package com.example.Orgabet.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.Orgabet.models.Users;


public interface UsersRepository extends MongoRepository<Users, String> {

	Users findByUsername(String username);

}
