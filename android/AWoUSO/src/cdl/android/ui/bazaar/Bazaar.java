package cdl.android.ui.bazaar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cdl.android.R;
import cdl.android.general.BazaarItem;
import cdl.android.server.GeneralHandler;

public class Bazaar extends Activity {
	private ArrayList<BazaarItem> mItems;
	private SharedPreferences mPreferences;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bazaar);

		ListView mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String username = mPreferences.getString("username", null);
		mItems = GeneralHandler.getBazaar(this);
		mListView.setAdapter(new BazaarAdapter(this, mItems, new OnClickListener() {
			public void onClick(View v) {
			}
		}));

	}

}
