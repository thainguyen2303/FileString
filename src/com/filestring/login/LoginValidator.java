package com.filestring.login;

public class LoginValidator {

	private static final String EMAIL_ERROR = "Incorrect email";
	private static final String PASSWORD_ERROR = "Password is too short";
	private static final String LOGIN_EMAIL_ERROR = "Your email address has not yet been verified.Check the email inbox for the verification email";
	private static final String VALIDATE_SUCCESS = "validate_success";
	private static final String LOGIN_PASSWORD_ERROR = "The password does not exists";
	private static final String LOGIN_SUCCESS = "";
	private static final String LOGIN_SERVER_ERROR = "Can't connect to server.Please login try again";
	private Validator validator;

	public LoginValidator() {
		validator = new Validator();
	}

	public String messageEmailValid(String email) {
		if (validator.isEmailValid(email)) {
			return VALIDATE_SUCCESS;
		}
		return EMAIL_ERROR;
	}

	public String messagePasswordValid(String password) {
		if (validator.isPasswordValid(password)) {
			return VALIDATE_SUCCESS;
		}
		return PASSWORD_ERROR;
	}

	public String messageErrorEmail() {
		return LOGIN_EMAIL_ERROR;
	}
	public String messageErrorPassword(){
		return LOGIN_PASSWORD_ERROR;
	}
	public String messageLoginSuccess(){
		return LOGIN_SUCCESS;
	}
	public String messageServerError(){
		return LOGIN_SERVER_ERROR;
	}
}
