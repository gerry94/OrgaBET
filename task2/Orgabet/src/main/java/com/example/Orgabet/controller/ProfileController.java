package com.example.Orgabet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.Orgabet.dto.EditProfileDTO;
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
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
    
    @GetMapping("admin/users")
    public String usersGet(Model model)
    {   
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User currentUser = userService.findUserByUsername(auth.getName());
		model.addAttribute("currentUser", currentUser);
    	List<User> userList=repository.findAllBy();
    	model.addAttribute("userList",userList);
        return "userlist";
    }
    
    @GetMapping("/profile/{username}")
    public String profileGet(Model model)
    {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUsername(auth.getName());
    	model.addAttribute("currentUser", user);
    	model.addAttribute("user", user);
        return "profile";
    }
    
    @RequestMapping(value = {"/profile/{username}", "/admin/users/{username}"}, method = RequestMethod.POST)
    public String profileEdit(@PathVariable("username") String username,EditProfileDTO edits, Model model)
    {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User currentUser = userService.findUserByUsername(auth.getName());
    	User user=repository.findByUsername(username);
    	user.setFirstName(edits.getFirstName());
    	user.setLastName(edits.getLastName());
    	user=repository.save(user);
    	model.addAttribute("currentUser", currentUser);
    	model.addAttribute("user", user);
        return "profile";
    }
    
}