package cdl.android.ui.bazaar;

import cdl.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Bazaar exchange tab
 */
public class Exchange extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textview = new TextView(this);
		textview.setText("This is the Exchange tab");
		setContentView(R.layout.exchange);
	}
}
