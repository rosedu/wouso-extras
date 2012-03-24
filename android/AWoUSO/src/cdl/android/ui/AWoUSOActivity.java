package cdl.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cdl.android.R;

public class AWoUSOActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        Button logButton = (Button) findViewById(R.id.loginButton);
        final Intent mainM = new Intent(this, MainMenu.class);
        final Toast loginNotification = Toast.makeText(getApplicationContext(), "Buffer", Toast.LENGTH_SHORT);

        logButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				loginNotification.cancel();

				EditText userBuffer = (EditText) findViewById(R.id.username);
				String userName = userBuffer.getText().toString();

				EditText passBuffer = (EditText) findViewById(R.id.password);
		        String password = passBuffer.getText().toString();
		        int clear = 1;

				loginNotification.setGravity(Gravity.CENTER, 0, 0);

				if ( userName.isEmpty() ){
					loginNotification.setText("Introduceti numele de utilizator!");
					clear = 0;
				}
				else{
					mainM.putExtra("userName", userName);
				}

				/*if ( password.isEmpty() ){
					if ( clear == 0 ){
						loginNotification.setText("Introduceti numele de utilizator\n                    si parola!");
					}
					else {
						loginNotification.setText("Introduceti parola!");
						clear = 0;
					}
				}*/

				if (clear == 1){
					startActivity(mainM);
				}
				else {
					loginNotification.show();
				}
			}
		});
    }
}