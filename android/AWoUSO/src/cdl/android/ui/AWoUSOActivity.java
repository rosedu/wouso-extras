package cdl.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cdl.android.R;

public class AWoUSOActivity extends Activity {
	SharedPreferences mPreferences;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		/** Checks if the user is already logged in */
		checkLogin();

		Button logButton = (Button) findViewById(R.id.loginButton);
		final Toast loginNotification = Toast.makeText(getApplicationContext(),
				"Buffer", Toast.LENGTH_SHORT);

		logButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				loginNotification.cancel();

				EditText userBuffer = (EditText) findViewById(R.id.username);
				String userName = userBuffer.getText().toString();

				EditText passBuffer = (EditText) findViewById(R.id.password);
				String password = passBuffer.getText().toString();
				int clear = 1;

				loginNotification.setGravity(Gravity.CENTER, 0, 0);

				if (userName.isEmpty()) {
					loginNotification
							.setText("Introduceti numele de utilizator!");
					clear = 0;
				}

				if (password.isEmpty()) {
					if (clear == 0) {
						loginNotification
								.setText("Introduceti numele de utilizator\n                    si parola!");
					} else {
						loginNotification.setText("Introduceti parola!");
						clear = 0;
					}
				}

				if (clear == 1) {
					login(userName, password);
				} else {
					loginNotification.show();
				}
			}
		});
	}

	public void checkLogin() {
		String username = mPreferences.getString("username", null);
		if (username != null) {
			final Intent mainM = new Intent(this, MainMenu.class);
			startActivity(mainM);
			finish();
		}
	}

	public void login(String username, String password) {
		//TODO: call API login
		Editor ed = mPreferences.edit();
		ed.putString("username", username);
		ed.commit();
		final Intent mainM = new Intent(this, MainMenu.class);
		startActivity(mainM);
		finish();
	}

}