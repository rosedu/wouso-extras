package cdl.android.ui.bazaar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Bazaar Summary Tab
 */
public class Summary extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView textview = new TextView(this);
		textview.setText("This is the Summary tab");
		setContentView(textview);
	}
}
