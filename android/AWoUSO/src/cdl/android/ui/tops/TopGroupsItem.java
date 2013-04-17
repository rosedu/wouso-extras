package cdl.android.ui.tops;

import org.json.JSONException;
import org.json.JSONObject;

public class TopGroupsItem {
	private String title;
	private int points;
	private int id;
	private int place;

	
	public void parseContent(JSONObject jobj) throws JSONException{
		title = jobj.getString("title");
		id = jobj.getInt("id");
		points = jobj.getInt("points");
	}
	
	
	public void setPlace(int place){
		this.place = place;
	}
	
	public int getPlace(){
		return place;
	}
	
	public int getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getPoints(){
		return points;
	}

}
