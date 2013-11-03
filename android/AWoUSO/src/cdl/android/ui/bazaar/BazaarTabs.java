package cdl.android.ui.bazaar;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import cdl.android.R;

/**
 * Bazaar Tab Activity TODO this is all deprecated, should be switched to using
 * Fragments instead!
 */
@SuppressWarnings("deprecation")
public class BazaarTabs extends TabActivity {
	private Intent notyfiableIntent;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_layout);

		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, Bazaar.class);
		spec = tabHost.newTabSpec("bazaar").setIndicator("Bazaar", res.getDrawable(R.drawable.spell_purple)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, Summary.class);
		spec = tabHost.newTabSpec("summary").setIndicator("Summary", res.getDrawable(R.drawable.spell_green)).setContent(intent);
		tabHost.addTab(spec);
		
		notyfiableIntent = intent;
		
		tabHost.setCurrentTab(0);
	}
	
	public void notifyOnChangeSummaryTab(){
		this.notyfiableIntent.putExtra("notify", true);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

}