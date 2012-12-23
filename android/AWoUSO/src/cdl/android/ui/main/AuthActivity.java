package cdl.android.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Window;
import cdl.android.R;

public class AuthActivity extends Activity {
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
			goToMainMenu();
		} else if (!getIntent().hasExtra("verifier")) {
			accessToken = new String[2];
			helper = new OAuthHelper();
			System.out.println("get req");
			String uri = helper.getRequestToken();
			System.out.println("got uri " + uri);
			Intent authAct = new Intent(this, AuthorizeActivity.class);
			authAct.putExtra("url", uri);
			startActivityForResult(authAct, REQ_OK);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("on result "
				+ data.getExtras().getString("verifier"));
		
		accessToken = helper.getAccessToken(data.getExtras().getString("verifier"));
		doLogin();
		
		//signed request
		/*OAuthConsumer mConsumer = new CommonsHttpOAuthConsumer("key", "secret");
		mConsumer.setMessageSigner(new PlainTextMessageSigner());
		mConsumer.setTokenWithSecret(accessToken[0], accessToken[1]); 
		try {
		//	doGet("http://wouso-next.rosedu.org/api/info/", mConsumer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}
	
	boolean checkLogin() {
		SharedPreferences mAppInfo = PreferenceManager.getDefaultSharedPreferences(this);
		
		//TODO: also check c2dm registration id
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

		/** Go to First Activity */
		Intent mainM = new Intent(context, AuthActivity.class);
		context.startActivity(mainM);
		((Activity) context).finish();
	}
	
	public void doLogin() {
		SharedPreferences mAppInfo = PreferenceManager.getDefaultSharedPreferences(this);
		Editor ed = mAppInfo.edit();
		ed.putString("auth_token", accessToken[0]);
		ed.putString("auth_secret", accessToken[1]);
		ed.commit();
		goToMainMenu();

		//TODO:
		// Register to cd2m and save the registration_id
		// in Shared Preferences
		// Send the registration_id to the WoUSO Server
		// POST /api/notifications/register/
	}

	public void goToMainMenu() {
		final Intent mainM = new Intent(this, MainMenu.class);
		startActivity(mainM);
		finish();
	}
}
