package cdl.android.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import cdl.android.R;

public class AWoUSOActivity extends TabActivity {

    @Override
    //http://developer.android.com/resources/tutorials/views/hello-tabwidget.html
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, Bazaar.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("bazaar").setIndicator("Bazaar",
	                      res.getDrawable(R.drawable.spell_purple))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, Exchange.class);
	    spec = tabHost.newTabSpec("exchange").setIndicator("Exchange",
	                      res.getDrawable(R.drawable.spell_yellow))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, Summary.class);
	    spec = tabHost.newTabSpec("summary").setIndicator("Summary",
	                      res.getDrawable(R.drawable.spell_green))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(2);
    }
}