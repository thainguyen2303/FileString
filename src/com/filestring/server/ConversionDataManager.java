package com.filestring.server;

import org.json.JSONObject;

public abstract class ConversionDataManager {
	
	public JSONObject jsonData;
	public ConversionDataManager(JSONObject jsonData){
		this.jsonData = jsonData;
	}
	
	
}
