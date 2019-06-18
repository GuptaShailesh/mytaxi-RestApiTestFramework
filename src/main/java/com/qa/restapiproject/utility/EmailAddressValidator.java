package com.qa.restapiproject.utility;

import java.util.regex.Pattern;

public class EmailAddressValidator {

	
	public Boolean isEmailIdValid(String emailId) {
		
		 String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$"; 
		 Pattern pattern = Pattern.compile(emailRegex); 
		   return pattern.matcher(emailId).matches();
	
	}


}
