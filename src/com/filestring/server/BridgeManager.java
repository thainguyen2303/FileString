package com.filestring.server;

import org.json.JSONObject;

import com.filestring.server.BridgeClientService.MethodType;
import com.filestring.statics.BridgeEnum;

public class BridgeManager{

	private BridgeClientService bridgeClientServer;
	private JSONObject jsonObjectData;
	private RegisterObjectCustom register = null;
	private String serviceURL;
	private MethodType methodType;

	public BridgeManager(MethodType methodType
			) {
		this.serviceURL = BridgeEnum.SERVICE_URL;
		this.methodType = methodType;
	}

	public void requestDataToServer(String uriTemplate,String jsonInput) {

		bridgeClientServer = new BridgeClientService(serviceURL, uriTemplate,
				methodType, jsonInput);
		try {
			jsonObjectData = bridgeClientServer.requestDataToService();
		} catch (Exception e) {

		}
	}
	public void conversionJsonDataToCustomObject(String typeObject){
		if (jsonObjectData != null) {
			register = new RegisterObjectCustom(jsonObjectData);
			register.registerObjectCustomData(typeObject);
		}else{
			System.out.println("Login failed");
		}
		
	}

	public Object getObjectCustomData() {
		if (register != null) {
			return register.getObjectCustomData();
		}
		return null;
	}

}
