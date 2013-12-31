package com.filestring.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.filestring.statics.LoginEnum;

public class Validator {

	public boolean isEmailValid(String email){
		
		boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	public boolean isPasswordValid(String password){
		int length = password.length();
		if (length > LoginEnum.PASSWORD_LENGTH) {
			return true;
		}
		return false;
	}
}
