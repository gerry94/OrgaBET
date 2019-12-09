package com.example.Orgabet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Orgabet.models.User;
import com.example.Orgabet.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
@Component
public class MongoUserDetailsService implements UserDetailsService{
  @Autowired
  private UserRepository repository;
  
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  public User findUserByUsername(String username) {
	    return repository.findByUsername(username);
	}
  public User findUserByEmail(String mail) {
	    return repository.findByEmail(mail);
	}
  
  public void saveUser(User user) {
	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    user.setRole("ROLE_USER");
	    repository.insert(user);
	}
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

      User user = repository.findByEmail(email);
      if(user != null) {
    	  List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user.getRole()"));
          return buildUserForAuthentication(user, authorities);
      } else {
          throw new UsernameNotFoundException("username not found");
      }
  }
  private UserDetails buildUserForAuthentication(User user, List<SimpleGrantedAuthority> authorities) {
	    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}
}