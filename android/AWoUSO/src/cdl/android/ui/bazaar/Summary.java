package cdl.android.ui.bazaar;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.general.SummaryItem;
import cdl.android.server.ApiHandler;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Bazaar Summary Tab
 */
public class Summary extends Activity {
	private ArrayList<SummaryItem> mItems;
	private ListView mListView;
	private boolean firstTime = true;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary);
		
		
		mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));
		
		mItems = new ArrayList<SummaryItem>();
	}
	
	public void onResume(){
		super.onResume();
		boolean changesMade = false;
		if(!firstTime){
			try{
				changesMade = (Boolean) this.getIntent().getExtras().get("notify");
			} catch (Exception e){
				
			}
		}
		
		if(!firstTime && changesMade){	
			mItems.clear();
			setListAdapter();
		} else if(firstTime){
			setListAdapter();
			firstTime = false;
		}
		
	}

	
	private void setListAdapter(){
		
		ServerResponse resp = ApiHandler.get(ApiHandler.baseURL + "bazaar/inventory/", this);
		
		if(resp.getStatus() == false){
		
			Toast.makeText(this, resp.getError() , Toast.LENGTH_SHORT).show();
		
		}else{
			try {
				JSONArray spellData = (JSONArray) ((JSONObject)resp.getData()).get("spells_available");
			
				for(int i = 0; i < spellData.length(); i++){
					SummaryItem summaryItem = new SummaryItem();
					
					try{
						
						summaryItem.parseSpellsAvailable(spellData.getJSONObject(i));
						mItems.add(summaryItem);

					}catch(JSONException e){
						Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		
		mListView.setAdapter(new SummaryAdapter(this, mItems,
				new Bundle()));
		
	}
	
}
