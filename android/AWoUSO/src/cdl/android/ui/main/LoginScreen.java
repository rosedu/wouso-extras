package cdl.android.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.server.AuthHandler;

/**
 * Main class that is loaded when the application starts Checks if the user is
 * already logged in if so, it moves further to the main menu/profile view else,
 * it tries to authenticate at the server
 */
public class LoginScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		/** Checks if the user is already logged in */
		final AuthHandler authHelper = new AuthHandler(this);
		if (authHelper.checkLogin() == true) {
			goToMainMenu();
		}

		/** Init login view */
		Button logButton = (Button) findViewById(R.id.loginButton);
		final Toast loginNotification = Toast.makeText(getApplicationContext(), "Buffer", Toast.LENGTH_SHORT);

		TextView bauhs = null;
		setFont(bauhs, "fonts/handsean.ttf", R.id.usernameLabel);

		logButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// loginNotification.cancel();

				EditText userBuffer = (EditText) findViewById(R.id.username);
				String userName = userBuffer.getText().toString();
				loginNotification.setGravity(Gravity.CENTER, 0, 0);

				if (userName.length() == 0) {
					loginNotification.setText("Insert username!");
					loginNotification.show();
				} else {
					authHelper.login(userName);
					goToMainMenu();
				}
			}
		});
	}

	public void goToMainMenu() {
		final Intent mainM = new Intent(this, MainMenu.class);
		startActivity(mainM);
		finish();
	}

	void setFont(TextView name, String path, int res) {
		name = (TextView) findViewById(res);
		Typeface font = Typeface.createFromAsset(getAssets(), path);
		name.setTypeface(font);
	}

}