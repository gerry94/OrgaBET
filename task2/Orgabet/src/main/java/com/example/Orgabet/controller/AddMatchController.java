package com.example.Orgabet.controller;

import com.example.Orgabet.models.User;
import com.example.Orgabet.repositories.MatchRepository;
import com.example.Orgabet.services.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class AddMatchController
{
	@Autowired
	private MongoUserDetailsService userService;
	@Autowired
	MatchRepository matchRepository;
	
	@GetMapping("admin/addMatches")
	public String addMatchesGet(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = userService.findUserByUsername(auth.getName());
		model.addAttribute("currentUser", currentUser);
		return "addMatches";
	}
	
	@PostMapping("/uploadFile")
	public String handleFileUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException
	{
		File file = new File(System.getProperty("java.io.tmpdir")+"/"+multipartFile.getName());
		multipartFile.transferTo(file); //converts sping.multipart to java.io.File
		
		matchRepository.uploadFile(file);
		
		return "addMatches";
	}
}
