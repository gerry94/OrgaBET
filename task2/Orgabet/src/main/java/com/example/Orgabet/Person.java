package com.example.Orgabet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
@Getter
@Setter
public class Person {

  @Id private String id;
  private String userName;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private int privilege;
  private int banned;

}
