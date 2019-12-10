package com.example.Orgabet.models;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
public class User {

  @Id 
  private String id;
  @Indexed(unique = true)
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  @Indexed(unique = true)
  private String email;
  @DBRef
  private Set<Role> roles;
  private String banned;
  private List<Coupon> coupons;

	public String getUsername() {
	  return username;
	}
	
	public String getPassword() {
		return password;
	}
}