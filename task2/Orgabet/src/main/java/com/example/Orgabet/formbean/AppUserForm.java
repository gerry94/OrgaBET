package com.example.Orgabet.formbean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserForm {
	
	    private String username;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String password;
	    private String confirmPassword;

	 
	    public AppUserForm() {
	 
	    }
	 
	    public AppUserForm(String userName, String firstName, String lastName, String email, String password, String confirmPassword) 
	    {
	        this.username = userName;
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.email = email;
	        this.password = password;
	        this.confirmPassword = confirmPassword;
	    }
}
