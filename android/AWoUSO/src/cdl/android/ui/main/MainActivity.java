package cdl.android.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import cdl.android.R;
import cdl.android.server.OAuthHelper;

public class MainActivity extends Activity {
	/** Local Storage files */
	public static final String CONFIG_SPELLS = "/mnt/sdcard/awouso/spells/spell_config";
	
	OAuthHelper helper;
	String[] accessToken;
	public final int REQ_OK = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main); 
		
		if (checkLogin()) {
			goToProfile();
		} else if (!getIntent().hasExtra("verifier")) {
			System.out.println(" no verifier ");
			accessToken = new String[2];
			helper = new OAuthHelper();
			Log.d("Wouso", "Hello");
			String uri = helper.getRequestToken();
			Log.d("Wouso", "Hello");
			Intent authAct = new Intent(this, AuthorizeActivity.class);
			Log.d("Wouso", "Hello");
			authAct.putExtra("url", uri);
			Log.d("Wouso", "Hello");
			startActivityForResult(authAct, REQ_OK);
			Log.d("Wouso", "Hello");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		accessToken = helper.getAccessToken(data.getExtras().getString("verifier"));
		doLogin();
	}
	
	boolean checkLogin() {
		SharedPreferences mAppInfo = PreferenceManager.getDefaultSharedPreferences(this);
		
		//TODO: also check c2dm registration id
		// auth_token, auth_secret - key de login
		if (mAppInfo.getString("auth_token", null) == null ||
				mAppInfo.getString("auth_secret", null) == null)
			return false;
		
		return true;
	}
	
	public static void logOut(Context context) {
		/** Clear Shared Preferences */
		SharedPreferences mAppInfo = PreferenceManager.getDefaultSharedPreferences(context);
		Editor ed = mAppInfo.edit();
		ed.clear();
		ed.commit();

		//TODO: Unregister from c2dm

		/** Go to Main Activity */
		Intent mainM = new Intent(context, MainActivity.class);
		context.startActivity(mainM);
		((Activity) context).finish();
	}
	
	public void doLogin() {
		SharedPreferences mAppInfo = PreferenceManager.getDefaultSharedPreferences(this);
		Editor ed = mAppInfo.edit();
		ed.putString("auth_token", accessToken[0]);
		ed.putString("auth_secret", accessToken[1]);
		ed.commit();
		goToProfile();

		//TODO:
		// Register to cd2m and save the registration_id
		// in Shared Preferences
		// Send the registration_id to the WoUSO Server
		// POST /api/notifications/register/
	}

	public void goToProfile() {
		final Intent mainM = new Intent(this, Profile.class);
		startActivity(mainM);
		finish();
	}
}
