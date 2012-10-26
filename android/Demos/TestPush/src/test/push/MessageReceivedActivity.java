package test.push;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageReceivedActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_result);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String message = extras.getString("payload");	
			if (message != null && message.length() > 0) {
				System.out.println("am primt " + message);
				TextView view = (TextView) findViewById(R.id.result);
				view.setText(message);
			}
		}

		super.onCreate(savedInstanceState);
	}

}
