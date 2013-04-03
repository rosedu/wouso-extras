package cdl.android.ui.bazaar;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.R;
import cdl.android.general.BazaarItem;
import cdl.android.general.ServerResponse;
import cdl.android.general.SummaryItem;
import cdl.android.server.ApiHandler;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Bazaar Summary Tab
 */
public class Summary extends Activity {
	private ArrayList<SummaryItem> mItems;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
		
		Log.d("stopped>>>>>>>>>>>", "stopped");
		/*TextView textview = new TextView(this);
		textview.setText("This is the Summary tab");
		setContentView(textview);*/
		
		ListView mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));
		
		mItems = new ArrayList();
		
		ServerResponse resp = ApiHandler.get(ApiHandler.baseURL + "bazaar/inventory/", this);
		
		if(resp.getStatus() == false){
		
			Toast.makeText(this, resp.getError() , Toast.LENGTH_SHORT).show();
		
		}else{
			try {
				Log.d("type", resp.getData().toString());
				JSONArray spellData = (JSONArray) ((JSONObject)resp.getData()).get("spells_available");
			
				System.out.println(spellData.length());
				for(int i = 0; i < spellData.length(); i++){
					SummaryItem summaryItem = new SummaryItem();
					
					try{
						
						summaryItem.parseContent(spellData.getJSONObject(i));
						mItems.add(summaryItem);
						summaryItem.setAll(this);
						Log.d("spell", mItems.get(i).getSpellTitle());
					}catch(JSONException e){
						Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		
		mListView.setAdapter(new SummaryAdapter(this, mItems,
				new OnClickListener() {
					public void onClick(View v) {
						
					}
				}));
		
	}
}
