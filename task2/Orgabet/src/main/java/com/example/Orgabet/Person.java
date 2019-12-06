package com.example.Orgabet;

import org.springframework.data.annotation.Id;

public class Person {

  @Id private String id;
  private String userName;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private int privilege;
  private int banned;


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

 public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return lastName;
  }	
 
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getPrivilege() {
    return privilege;
  }

  public void setPrivilege(int privilege) {
    this.privilege = privilege;
  }

  public int getBanned() {
    return banned;
  }

  public void setBanned(int banned) {
    this.banned = banned;
  }
}
