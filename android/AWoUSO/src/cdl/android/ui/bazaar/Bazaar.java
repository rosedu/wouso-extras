package cdl.android.ui.bazaar;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.BazaarItem;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class Bazaar extends Activity {
	private ArrayList<BazaarItem> mItems;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bazaar);

		ListView mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));

		// Get Bazaar items from server 
		// TODO: in the future it will parse the items from the local storage
		mItems = new ArrayList<BazaarItem>();
		ServerResponse resp = ApiHandler.get(ApiHandler.bazaarInfoURL, this);

		if (resp.getStatus() == false) {
			Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
		} else {
			try {
				JSONArray arr = resp.getData().getJSONArray("spells");
				for (int i = 0; i < arr.length(); i++) {
					BazaarItem bazaar = new BazaarItem();
					bazaar.parseContent(arr.getJSONObject(i));
					mItems.add(bazaar);
				}
			} catch (JSONException e) {
				Toast.makeText(this, "Server response format error.",
						Toast.LENGTH_SHORT).show();
			}
		}

		mListView.setAdapter(new BazaarAdapter(this, mItems,
				new OnClickListener() {
					public void onClick(View v) {
					}
				}));
	}
}
