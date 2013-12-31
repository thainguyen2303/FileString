package com.filestring.login;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;
import com.filestring.login.UserSignIn;
import com.filestring.main.MainFileStringActivity;
import com.filestring.main.R;
import com.filestring.server.BridgeClientService.MethodType;
import com.filestring.server.BridgeManager;
import com.filestring.server.RequestConversionData;
import com.filestring.statics.BridgeEnum;
import com.filestring.statics.Internet;
import com.filestring.statics.LoginEnum;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserLoginActivity extends Activity implements OnClickListener,
		RequestConversionData {

	private EditText edtEmail;
	private EditText edtPassword;
	private Button btnLogin;
	private TextView txtError;
	private TextView txtHelp;
	private LoginValidator loginValidator;
	private String uriTemplate = "User_signIn";
	private String jsonInput;
	private BridgeManager manager;
	private ProgressDialog progress;
	private static final int typeUrlServer = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		setUrlServer(typeUrlServer);
		initViews();
		onClickListener();
	}

	private void initViews() {
		if (edtEmail == null) {
			edtEmail = (EditText) findViewById(R.id.login_email);
		}
		if (edtPassword == null) {
			edtPassword = (EditText) findViewById(R.id.login_password);
		}
		if (btnLogin == null) {
			btnLogin = (Button) findViewById(R.id.login_submit);
		}
		if (txtError == null) {
			txtError = (TextView) findViewById(R.id.login_error);
		}
		if (txtHelp == null) {
			txtHelp = (TextView) findViewById(R.id.login_help);
		}
		if (loginValidator == null) {
			loginValidator = new LoginValidator();
		}
		if (progress == null) {
			progress = new ProgressDialog(this);
			progress.setMessage("Loading...");
		}
	}

	private void onClickListener() {
		btnLogin.setOnClickListener(this);
		txtHelp.setOnClickListener(this);
	}

	private void validatorData() {
		String emailValid = loginValidator.messageEmailValid(edtEmail.getText()
				.toString().trim());
		String passwordValid = loginValidator.messagePasswordValid(edtPassword
				.getText().toString().trim());
		if (emailValid.equals(LoginEnum.VALIDATE_SUCCESS)
				&& passwordValid.equals(LoginEnum.VALIDATE_SUCCESS)) {
			requestDataToService();
		} else if (emailValid.equals(LoginEnum.VALIDATE_SUCCESS)
				&& !passwordValid.equals(LoginEnum.VALIDATE_SUCCESS)) {
			txtError.setText(passwordValid);
		} else if (!emailValid.equals(LoginEnum.VALIDATE_SUCCESS)
				&& passwordValid.equals(LoginEnum.VALIDATE_SUCCESS)) {
			txtError.setText(emailValid);
		} else {
			txtError.setText(emailValid + "." + passwordValid);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_submit:
			if (Internet.isOnline(this)) {
				validatorData();
			}else{
				setMessageLoginError(Internet.messageInternetError());
			}
			break;
		case R.id.login_help:
			if (Internet.isOnline(this)) {
				userLoginHelp(LoginEnum.NEED_HELP_URL);
			}else{
				setMessageLoginError(Internet.messageInternetError());
			}
		default:
			break;
		}
	}

	private void userLoginHelp(String strUri) {
		Uri uri = Uri.parse(strUri);
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(browserIntent);
	}

	private String getHashMd5Final() {
		String messageSecurity = "Transittechonology1234567XGHd(@!DFASDFASDFsdafasjlkdf";
		String encryptePass = getHashMD5(messageSecurity,
				editTextGetText(edtPassword));
		return getHashMD5(encryptePass, messageSecurity);
	}

	private String editTextGetText(EditText edt) {
		return edt.getText().toString().trim();
	}

	private String getDeviceName() {
		return android.os.Build.MODEL;
	}

	private String getDeviceCodeName() {
		return android.os.Build.DEVICE;
	}

	private String getDeviceOS() {
		return android.os.Build.VERSION.RELEASE;
	}

	private String getDeviceIdentify() {
		return Secure.getString(getBaseContext().getContentResolver(),
				Secure.ANDROID_ID);
	}

	private String getFlatfrom() {
		return android.os.Build.VERSION.INCREMENTAL;
	}

	private void setUrlServer(int typeUrlServer) {

		switch (typeUrlServer) {
		case 0:
			BridgeEnum.SERVICE_URL = BridgeEnum.SERVICE_DEVELOPING_URL;
			break;
		case 1:
			BridgeEnum.SERVICE_URL = BridgeEnum.SERVICE_STAGING_URL;
			break;
		case 2:
			BridgeEnum.SERVICE_URL = BridgeEnum.SERVICE_PRODUCTION_URL;
			break;
		default:
			break;
		}
	}

	private String getHashMD5(String messageSecurity, String password) {
		try {
			String input = messageSecurity + password;
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			StringBuilder str = new StringBuilder(hashtext);
			int i = 2;
			while (i < 47) {
				str.insert(i, "-");
				i = i + 3;
			}
			hashtext = str.toString().toUpperCase();
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private void setMessageLoginError(String messageError) {
		txtError.setText(messageError);
	}

	@Override
	public void requestDataToService() {
		// TODO Auto-generated method stub
		JSONObject jbTransfer = new JSONObject();
		try {
			jbTransfer.put("username", editTextGetText(edtEmail));
			jbTransfer.put("password", getHashMd5Final());
			jbTransfer.put("deviceName", getDeviceName());
			jbTransfer.put("deviceCodeName", getDeviceCodeName());
			jbTransfer.put("deviceIdentify", getDeviceIdentify());
			jbTransfer.put("deviceOS", getDeviceOS());
			jbTransfer.put("platform", getFlatfrom());

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		jsonInput = jbTransfer.toString();
		manager = new BridgeManager(MethodType.POST);
		new UserSignInLoading().execute();
	}

	class UserSignInLoading extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				manager.requestDataToServer(uriTemplate, jsonInput);
			} catch (Exception e) {
				System.out.println(">>>>>>>> Error: " + e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
			manager.conversionJsonDataToCustomObject(BridgeEnum.USER_SIGNIN);
			UserSignIn userSignIn = (UserSignIn) manager.getObjectCustomData();
			if (userSignIn != null) {
				if (!userSignIn.isPrimaryEmailAddressMatchUsername()) {
					setMessageLoginError(loginValidator.messageErrorEmail());
				} else if (!userSignIn.isMatchPassword()) {
					setMessageLoginError(loginValidator.messageErrorPassword());
				} else {
					Intent intentMain = new Intent(getBaseContext(),
							MainFileStringActivity.class);
					startActivity(intentMain);
					setMessageLoginError(loginValidator.messageLoginSuccess());
				}
			} else {
				setMessageLoginError(loginValidator.messageServerError());
			}
		}

	}
}
