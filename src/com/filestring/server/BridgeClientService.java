package com.filestring.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class BridgeClientService {

	public static enum MethodType {
		POST, GET
	}
	private String serviceURL;
	private String uriTemplate;
	private MethodType methodType;
	private String jsonInput;
	public BridgeClientService(String serviceURL, String uriTemplate,
			MethodType methodType, String jsonInput) {

		this.serviceURL = serviceURL;
		this.uriTemplate = uriTemplate;
		this.methodType = methodType;
		this.jsonInput = jsonInput;
	}

	public JSONObject requestDataToService() throws ClientProtocolException,
			IOException {

		HttpParams httpParam = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParam, 10000);
		HttpConnectionParams.setSoTimeout(httpParam, 15000);
		HttpClient httpclient = new DefaultHttpClient(httpParam);

		JSONObject responseMessage = null;
		String fullServiceURL = serviceURL + "/" + uriTemplate;


		switch (methodType) {
		case POST:
			
			HttpPost httppostreq = new HttpPost(fullServiceURL);
			StringEntity se = new StringEntity(jsonInput);
			se.setContentType("application/json");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httppostreq.setEntity(se);
			HttpResponse httpresponse = httpclient.execute(httppostreq);
			responseMessage = responseDataFromService(httpresponse);

			break;
		case GET:
			HttpGet httpGet = new HttpGet(fullServiceURL);
			HttpResponse response = httpclient.execute(httpGet);
			responseMessage = responseDataFromService(response);
			break;

		default:
			break;
		}

		return responseMessage;

	}

	public JSONObject responseDataFromService(HttpResponse response)
			throws IllegalStateException, IOException {
		JSONObject jsonObj = null;
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"utf-8"), 8);
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
		
			sb.append(line + "\n");
		}
		is.close();
		try {
			jsonObj = new JSONObject(sb.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj;
	}
}
