package com.example.Orgabet.models;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class User {

  @Id 
  private ObjectId id;
  @Indexed(unique = true)
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  @Indexed(unique = true)
  private String email;
  private String role;
  private int banned;
  private List<Coupon> coupons;
  
	public String getUsername() {
	  return username;
	}
	
	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}  
  

}