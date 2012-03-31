package cdl.android.ui;

import cdl.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Exchange extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		TextView textview = new TextView(this);
		textview.setText("This is the Exchange tab");
		setContentView(R.layout.exchange);
	}
}
