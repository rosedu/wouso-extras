package cdl.android.general;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;
import cdl.android.server.ApiHandler;
import cdl.android.ui.bazaar.Bazaar;

public class SummaryItem {
	private int playerId;
	private int spellId;
	private int amount;
	private String spellName;
	private String spellTitle;
	private String imageURL;
	
	public void parseSpellsAvailable(JSONObject jobj) throws JSONException{
		spellName = jobj.getString("spell_name");
		spellTitle = jobj.getString("spell_title");
		spellId = jobj.getInt("spell_id");
	}
	
	public void setAll(Context context){
		ArrayList<BazaarItem> mItems = new ArrayList();
		
		ServerResponse resp = ApiHandler.get(ApiHandler.baseURL + "bazaar/", context);
		
		if(resp.getStatus() == false){
		
			Toast.makeText(context, resp.getError() , Toast.LENGTH_SHORT).show();
		
		}else{
			try {
				JSONArray spellData = (JSONArray) ((JSONObject)resp.getData()).get("spells");
			
				System.out.println(spellData.length());
				for(int i = 0; i < spellData.length(); i++){
					BazaarItem bazaarItem = new BazaarItem();
					
					try{
						bazaarItem.parseContent(spellData.getJSONObject(i));
						mItems.add(bazaarItem);
						
					}catch(JSONException e){
						Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		
		for(BazaarItem item : mItems){
			if(item.getTitle().equals("" + spellTitle)){
				imageURL = item.getImageURL();
			}
		}
	}
	
	public String getAmount(){
		return "" + amount;
	}
	
	public String getSpellTitle(){
		return spellTitle;
	}
	
	public String getImageURL(){
		return imageURL;
	}
	
	
}
