package com.example.Orgabet;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface PersonRepository extends MongoRepository<Person, String> {

  List<Person> findByLastName(@Param("name") String name);

}
