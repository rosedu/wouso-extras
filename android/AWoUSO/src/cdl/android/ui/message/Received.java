package cdl.android.ui.message;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import cdl.android.R;

public class Received extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textview = new TextView(this);
		textview.setText("This is the Exchange tab");
		setContentView(R.layout.exchange);
	}
}
