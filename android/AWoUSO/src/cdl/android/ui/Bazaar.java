package cdl.android.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cdl.android.R;
import cdl.android.model.BazaarItem;
import cdl.android.server.ApiRequests;
import cdl.android.utils.BazaarAdapter;

public class Bazaar extends Activity {
	private ArrayList<BazaarItem> mItems;
	SharedPreferences mPreferences;



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bazaar);

		ListView mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));
		
		//final String[] list = getResources().getStringArray(R.array.list_array);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String username = mPreferences.getString("username", null);
		ApiRequests req = new ApiRequests();
		mItems = req.getBazaar(username);
		
		mListView.setAdapter(new BazaarAdapter(this, mItems, new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		}));
		
		//setListAdapter(new ArrayAdapter<String>(this, R.layout.bazaar_list_item, list));

//		ListView lv = getListView();
//		lv.setTextFilterEnabled(true);
//
//		final Toast listItem = Toast.makeText(getApplicationContext(), "Buffer", Toast.LENGTH_SHORT);
//
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				listItem.cancel();
//				listItem.setText(((TextView) view).getText());
//				listItem.show();
//			}
//		});
	}

}
