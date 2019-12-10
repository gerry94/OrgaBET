package com.example.Orgabet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.Orgabet.models.Role;
import com.example.Orgabet.repositories.RoleRepository;

@SpringBootApplication
public class OrgabetApplication {

  public static void main(String[] args) throws Throwable {
    SpringApplication.run(OrgabetApplication.class, args);
  }
  @Bean
  CommandLineRunner init(RoleRepository roleRepository) {

      return args -> {

          Role adminRole = roleRepository.findByRole("ADMIN");
          if (adminRole == null) {
              Role newAdminRole = new Role();
              newAdminRole.setRole("ADMIN");
              roleRepository.save(newAdminRole);
          }

          Role userRole = roleRepository.findByRole("USER");
          if (userRole == null) {
              Role newUserRole = new Role();
              newUserRole.setRole("USER");
              roleRepository.save(newUserRole);
          }
      };

  }
}



