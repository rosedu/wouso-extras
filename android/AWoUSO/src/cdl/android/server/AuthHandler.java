package cdl.android.server;

import cdl.android.ui.main.LoginScreen;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Class that handles user authentication
 */
public class AuthHandler {
	private Context mContext;
	private SharedPreferences mAppInfo;

	/**
	 * Creates a new AuthHandler with the set context.
	 * 
	 * @param context
	 *            The context for the auth handler.
	 */
	public AuthHandler(Context context) {
		mContext = context;
		mAppInfo = PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * Checks whether the user is logged in.
	 * 
	 * @return whether the user is currently logged in.
	 */
	public boolean checkLogin() {
		String username = mAppInfo.getString("username", null);
		return username != null;
	}

	/**
	 * Logs the user out of the application.
	 */
	public void logOut() {
		/** Clear Shared Preferences */
		Editor ed = mAppInfo.edit();
		ed.clear();
		ed.commit();

		// TODO 9: Unregister from c2dm

		/** Go to Main Activity */
		Intent mainM = new Intent(mContext, LoginScreen.class);
		mContext.startActivity(mainM);
		((Activity) mContext).finish();
	}

	/**
	 * Logs the user in, authenticating him with the server.
	 * 
	 * @param username
	 *            The username.
	 */
	public void login(String username) {
		Editor ed = mAppInfo.edit();
		ed.putString("username", username);
		ed.commit();

		// TODO 9:
		// Register to cd2m and save the registration_id
		// in Shared Preferences
		// Send the registration_id to the WoUSO Server
		// POST /api/notifications/register/
	}
}
