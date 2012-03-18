package cdl.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import cdl.android.R;

public class AWoUSOActivity extends Activity {
	
	Intent intent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		Button button = (Button) findViewById(R.id.button_magic);
		intent = new Intent(AWoUSOActivity.this, Tabs.class);
		
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(intent);
			}
		});
		
	}
}