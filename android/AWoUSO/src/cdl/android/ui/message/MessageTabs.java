package cdl.android.ui.message;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import cdl.android.R;

/**
 * Message Tab Activity
 */
@SuppressWarnings("deprecation")
public class MessageTabs extends TabActivity {
	private TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_layout);
		
		tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, Received.class);
		spec = tabHost.newTabSpec("received").setIndicator("Received");
		spec.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, Sent.class);
		spec = tabHost.newTabSpec("sent").setIndicator("Sent");
		spec.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, CreateMessage.class);
		spec = tabHost.newTabSpec("compose").setIndicator("Compose");
		spec.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}

	public void switchTab(int tab) {
		tabHost.setCurrentTab(tab);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	
	
}