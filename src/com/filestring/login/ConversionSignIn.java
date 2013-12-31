package com.filestring.login;

import org.json.JSONException;
import org.json.JSONObject;

public class ConversionSignIn {

	private JSONObject jsonData;
	public ConversionSignIn(JSONObject jsonData){
		this.jsonData = jsonData;
	}
	public UserSignIn getUserSignIn(){
		UserSignIn userSignIn = null;
		try {
			userSignIn = new UserSignIn();
			userSignIn.setMatchPassword(jsonData.getBoolean("IsMatchPassword"));
			userSignIn.setPrimaryEmailAddressMatchUsername(jsonData.getBoolean("IsPrimaryEmailAddressMatchUsername"));
			userSignIn.setUserData(setUserData());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userSignIn;
	}
	private UserData setUserData(){
		JSONObject jsonUserData = null;
		UserData userData = null;
		try {
			userData = new UserData();
			jsonUserData = jsonData.getJSONObject("UserData");
			userData.setCreatedBy(jsonUserData.getString("CreatedBy"));
			userData.setCreatedDate(jsonUserData.getString("CreatedDate"));
			userData.setFirstName(jsonUserData.getString("FirstName"));
			userData.setIsActive(jsonUserData.getString("IsActive"));
			userData.setIsEnabled(jsonUserData.getString("IsEnabled"));
			userData.setIsSuspended(jsonUserData.getString("IsSuspended"));
			userData.setLastName(jsonUserData.getString("LastName"));
			userData.setModifiedBy(jsonUserData.getString("ModifiedBy"));
			userData.setModifiedDate(jsonUserData.getString("ModifiedDate"));
			userData.setPassword(jsonUserData.getString("Password"));
			userData.setUserAccountType(jsonUserData.getString("UserAccountType"));
			userData.setUserID(jsonUserData.getString("UserID"));
			userData.setUsername(jsonUserData.getString("Username"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userData;
	}
}
