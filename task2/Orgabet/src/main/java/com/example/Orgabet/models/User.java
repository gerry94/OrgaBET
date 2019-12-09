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
  private String banned;
  private List<Coupon> coupons;
  
  public User(String username, String firstName, String lastName, String email, String password) 
  {
      this.setUsername(username);
      this.setFirstName(firstName);
      this.setLastName(lastName);
      this.setEmail(email);
      this.setPassword(password);
      this.setBanned("N");
  }
	public User() {
    }
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