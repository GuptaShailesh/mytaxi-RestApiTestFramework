package com.qa.restapiproject.utility;

import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class EmailAddressValidator {
	private static final Logger logger = LogManager.getLogger(EmailAddressValidator.class);

	public Boolean isValidEmaiIdFormat(String emailId) {
		boolean isEmaiIdFormatValid= false;
		try {
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
					"[a-zA-Z0-9_+&*-]+)*@" + 
					"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
					"A-Z]{2,7}$"; 
			Pattern pattern = Pattern.compile(emailRegex); 
			isEmaiIdFormatValid =pattern.matcher(emailId).matches();
		}
		catch (Exception e)
		{
			logger.error("Exception: " + e.getMessage());
		}
		return isEmaiIdFormatValid; 
	}

	
	


}
