package cdl.android.ui.bazaar;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.general.SummaryItem;
import cdl.android.server.ApiHandler;

public class SummaryFragment extends Fragment {
	private ArrayList<SummaryItem> mItems;
	private Bundle bundle = new Bundle();;
	
	public void setBundle(int userID){
		bundle.putInt("userID", userID);
	}
	
	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		View view =  inflater.inflate(R.layout.summary, container, false);
		
		final ListView mListView = (ListView) view.findViewById(android.R.id.list);
		mListView.setEmptyView(view.findViewById(android.R.id.empty));
		
		mItems = new ArrayList();

		ServerResponse resp = ApiHandler.get(ApiHandler.baseURL + "bazaar/inventory/", view.getContext());

		if(resp.getStatus() == false){
		
			Toast.makeText(view.getContext(), resp.getError() , Toast.LENGTH_SHORT).show();
		
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
	
	
	public static void castSpell(int playerID, int spellID, Context context){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		
		nameValuePairs.add(new BasicNameValuePair("spell", "" + spellID));
		try{
			Log.d("====================", "spell casted");
			ApiHandler.sendPost(ApiHandler.baseURL + "player/" + playerID + "/cast/", nameValuePairs, context);
			Log.d("====================", "spell casted 2 ");
		} catch(Exception e){
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
		}
		Toast.makeText(context, "Spell cast", Toast.LENGTH_SHORT);
	}	
	
	
	
}