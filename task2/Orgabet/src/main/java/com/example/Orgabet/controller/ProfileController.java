package com.example.Orgabet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Orgabet.models.User;
import com.example.Orgabet.repositories.UserRepository;
import com.example.Orgabet.services.MongoUserDetailsService;

@Controller
public class ProfileController
{
	@Autowired
	private MongoUserDetailsService userService;
	@Autowired
	private UserRepository repository;
	
    @GetMapping("admin/users/{username}")
    public String usersGet(@PathVariable("username") String username, Model model)
    {   
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User currentUser = userService.findUserByUsername(auth.getName());
		model.addAttribute("currentUser", currentUser);
    	User user=repository.findByUsername(username);
    	model.addAttribute("user", user);
        return "profile";
    }
    
    @GetMapping("user/profile")
    public String profileGet(Model model)
    {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUsername(auth.getName());
    	model.addAttribute("currentUser", user);
    	model.addAttribute("user", user);
        return "profile";
    }
}