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
	private int id;
	private String spellTitle;
	private String image;
	
	public void parseContent(JSONObject jobj) throws JSONException{
		playerId = jobj.getInt("player_id");
		spellId = jobj.getInt("spell_id");
		amount = jobj.getInt("amount");
		id = jobj.getInt("id");
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
			if(item.getId().equals("" + spellId)){
				spellTitle = item.getTitle();
				image = item.getImage();
			}
		}
	}
	
	public String getAmount(){
		return "" + amount;
	}
	
	public String getSpellTitle(){
		return spellTitle;
	}
	
	public String getImage(){
		return image;
	}
	
	
}
