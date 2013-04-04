package cdl.android.ui.bazaar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
						
						summaryItem.parseSpellsAvailable(spellData.getJSONObject(i));
						mItems.add(summaryItem);
						summaryItem.setAll(this);
						
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
