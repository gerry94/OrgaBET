package com.example.Orgabet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Orgabet.models.User;
import com.example.Orgabet.repositories.UserRepository;

@Controller
@RequestMapping("/users")
public class UsersController
{
	
	@Autowired
	private UserRepository repository;
	
    @GetMapping("/{username}")
    @PreAuthorize("authentication.principal.username == #username) || hasRole('ADMIN')")
    public String usersGet(@PathVariable("username") String username, Model model)
    {
    	User user=repository.findByUsername(username);
    	model.addAttribute("user", user);
        return "profile";
    }
}