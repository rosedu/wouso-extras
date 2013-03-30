package cdl.android.ui.message;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class Received extends Activity {
	private ArrayList<MessageItem> mItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.received);

		ListView mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));

		// Get Received messages from the server
		mItems = new ArrayList<MessageItem>();
		ServerResponse resp = ApiHandler.getArray(ApiHandler.msgReceivedAPICallURL,
				this);

		if (resp.getStatus() == false) {
			Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
		} else {
			try {
				JSONArray arr = resp.getArrayData();
				for (int i = 0; i < arr.length(); i++) {
					MessageItem mes = new MessageItem();
					mes.parseContent(arr.getJSONObject(i));
					mItems.add(0, mes); // newer on top
				}
			} catch (JSONException e) {
				Toast.makeText(this, "Server response format error.",
						Toast.LENGTH_SHORT).show();
			}
		}

		mListView.setAdapter(new MessageAdapterReceived(this, mItems,
				new OnClickListener() {
					public void onClick(View v) {
					}
				}));
	}
	
    protected void onResume(){
    	super.onResume();
    	
    	ListView mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));

    	mItems = new ArrayList<MessageItem>();
		ServerResponse resp = ApiHandler.getArray(ApiHandler.msgReceivedAPICallURL,
				this);

		if (resp.getStatus() == false) {
			Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
		} else {
			try {
				JSONArray arr = resp.getArrayData();
				for (int i = 0; i < arr.length(); i++) {
					MessageItem mes = new MessageItem();
					mes.parseContent(arr.getJSONObject(i));
					mItems.add(0, mes); // newer on top
				}
			} catch (JSONException e) {
				Toast.makeText(this, "Server response format error.",
						Toast.LENGTH_SHORT).show();
			}
		}
		
		mListView.setAdapter(new MessageAdapterReceived(this, mItems,
				new OnClickListener() {
					public void onClick(View v) {
					}
				}));
    }

}