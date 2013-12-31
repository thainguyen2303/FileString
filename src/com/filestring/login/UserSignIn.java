package com.filestring.login;

public class UserSignIn {

	private boolean isMatchPassword;
	private boolean isPrimaryEmailAddressMatchUsername ;
	private UserData userData;
	public boolean isMatchPassword() {
		return isMatchPassword;
	}
	public void setMatchPassword(boolean isMatchPassword) {
		this.isMatchPassword = isMatchPassword;
	}
	public boolean isPrimaryEmailAddressMatchUsername() {
		return isPrimaryEmailAddressMatchUsername;
	}
	public void setPrimaryEmailAddressMatchUsername(
			boolean isPrimaryEmailAddressMatchUsername) {
		this.isPrimaryEmailAddressMatchUsername = isPrimaryEmailAddressMatchUsername;
	}
	public UserData getUserData() {
		return userData;
	}
	public void setUserData(UserData userData) {
		this.userData = userData;
	}
}
