package com.filestring.server;
import org.json.JSONObject;

import com.filestring.login.ConversionSignIn;
import com.filestring.statics.BridgeEnum;

public class RegisterObjectCustom {

	private JSONObject jsonData = null;
	private Object objData = null;
	public RegisterObjectCustom(JSONObject jsonData){
		this.jsonData = jsonData;
	}
	public void registerObjectCustomData(String type){
		if (type.equals(BridgeEnum.USER_SIGNIN)) {
			ConversionSignIn signIn = new ConversionSignIn(jsonData);
			objData = signIn.getUserSignIn();
		}
	}
	public Object getObjectCustomData(){
		return objData;
	}
}
