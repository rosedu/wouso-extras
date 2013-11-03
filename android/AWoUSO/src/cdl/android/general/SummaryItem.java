package cdl.android.general;

import org.json.JSONException;
import org.json.JSONObject;

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
		imageURL = jobj.getString("image_url");
		amount = jobj.getInt("amount");
	}
	
	public int getId(){
		return spellId;
	}
	
	public String getAmount(){
		return "" + amount;
	}
	
	public int getAmountInt(){
		return amount;
	}
	
	public String getSpellTitle(){
		return spellTitle;
	}
	
	public String getImageURL(){
		return imageURL;
	}
	
	
}
