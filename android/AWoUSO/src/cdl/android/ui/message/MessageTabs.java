package cdl.android.ui.message;

import android.app.*;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import cdl.android.R;

/**
 * Message Tab Activity
 */
public class MessageTabs extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_layout);
		
		Button back = (Button) findViewById(R.id.button_back);
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		intent = new Intent().setClass(this, Received.class);
		spec = tabHost.newTabSpec("received").setIndicator("Received");
		spec.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, Sent.class);
		spec = tabHost.newTabSpec("sent").setIndicator("Sent");
		spec.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, All.class);
		spec = tabHost.newTabSpec("all").setIndicator("All");
		spec.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, CreateMessage.class);
		spec = tabHost.newTabSpec("compose").setIndicator("Compose");
		spec.setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
	}
}
