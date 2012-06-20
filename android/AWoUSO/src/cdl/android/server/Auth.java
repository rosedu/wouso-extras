package cdl.android.server;

import cdl.android.ui.main.AWoUSOActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Class that handles user authentication
 */
public class Auth {
	private Context mContext;
	private SharedPreferences mAppInfo;
	
	public Auth(Context context) {
		mContext = context;
		mAppInfo = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public boolean checkLogin() {
		String username = mAppInfo.getString("username", null);
		return username != null;
	}
	
	public void logOut() {
		/** Clear Shared Preferences */
		Editor ed = mAppInfo.edit();
		ed.clear();
		ed.commit();
		
		//TODO 9: Unregister from c2dm 
		
		/** Go to Main Activity */
		Intent mainM = new Intent(mContext, AWoUSOActivity.class);
		mContext.startActivity(mainM);
		((Activity)mContext).finish();
	}
	
	public void login(String username) {
		Editor ed = mAppInfo.edit();
		ed.putString("username", username);
		ed.commit();
		
		//TODO 9:
		// Register to cd2m and save the registration_id
		// in Shared Preferences
		// Send the registration_id to the WoUSO Server
		// POST /api/notifications/register/
	}
}
