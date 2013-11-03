package cdl.android.ui.bazaar;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.support.v4.app.*;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.general.SummaryItem;
import cdl.android.server.ApiHandler;


public class SummaryFragment extends Fragment {
	private ArrayList<SummaryItem> mItems;
	private Bundle bundle;
	private View view;
	private ListView mListView;
	
	public SummaryFragment(){
		this.bundle = new Bundle();
		
	}
	
	public void setBundle(Bundle bundle){
		this.bundle.putInt("playerID", bundle.getInt("playerID"));
	}
	
	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		view =  inflater.inflate(R.layout.summary, container, false);
		
	    mListView = (ListView) view.findViewById(android.R.id.list);
		mListView.setEmptyView(view.findViewById(android.R.id.empty));
		
		mItems = new ArrayList<SummaryItem>();

		ServerResponse resp = ApiHandler.get(ApiHandler.baseURL + "bazaar/inventory/", view.getContext());

		if(resp.getStatus() == false){
			Toast.makeText(view.getContext(), resp.getError() , Toast.LENGTH_SHORT).show();
		}else{
			try {
				JSONArray spellData = (JSONArray) ((JSONObject)resp.getData()).get("spells_available");
				for(int i = 0; i < spellData.length(); i++){
					SummaryItem summaryItem = new SummaryItem();
					try{
						summaryItem.parseSpellsAvailable(spellData.getJSONObject(i));
						mItems.add(summaryItem);
					}catch(JSONException e){
						Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		
		
		final Context con = view.getContext();
		bundle.putString("action", "castspell");
		
		mListView.setAdapter(new SummaryAdapter(con, mItems, bundle));
		
		return view;
		
	}

	
	public void reset(){
		mItems.clear();
		
		ServerResponse resp = ApiHandler.get(ApiHandler.baseURL + "bazaar/inventory/", view.getContext());
		if(resp.getStatus() == false){
			Toast.makeText(view.getContext(), resp.getError() , Toast.LENGTH_SHORT).show();
		}else{
			try {
				JSONArray spellData = (JSONArray) ((JSONObject)resp.getData()).get("spells_available");
				for(int i = 0; i < spellData.length(); i++){
					SummaryItem summaryItem = new SummaryItem();
					try{
						summaryItem.parseSpellsAvailable(spellData.getJSONObject(i));
						mItems.add(summaryItem);
					}catch(JSONException e){
						Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		
		final Context con = view.getContext();
		bundle.putString("action", "castspell");
		mListView.setAdapter(new SummaryAdapter(con, mItems, bundle));
	}
	
	public static void castSpell(int playerID, int spellID,Context context, Method method){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		
		nameValuePairs.add(new BasicNameValuePair("spell", "" + spellID));
		nameValuePairs.add(new BasicNameValuePair("days", "" + 3));
		
		Object answer = null;
		try{
			answer = ApiHandler.sendPost(ApiHandler.baseURL + "player/" + playerID + "/cast/", nameValuePairs, context);
		} catch(Exception e){
			
		} finally {
			if(((ServerResponse) answer).getStatus() == true){
				Toast.makeText(context, "Witchcraft has been done", Toast.LENGTH_SHORT).show();
				method.handleStuff();
			} else {
				Toast.makeText(context, "Spell casting failed", Toast.LENGTH_SHORT).show();
			}
		}
	}	
}

interface Method{
	public void handleStuff();
}