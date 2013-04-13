package cdl.android.ui.tops;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;
import cdl.android.general.ServerResponse;
import cdl.android.general.UserInfo;
import cdl.android.server.ApiHandler;

public class TopUsersItem {
	private String firstName;
	private String lastName;
	private int id;
	private int points;
	private String avatarURL;
	private int place;
	private int level;
	private String displayName;
	
	public void parseContent(JSONObject jobj) throws JSONException{
		firstName = jobj.getString("first_name");
		lastName = jobj.getString("last_name");
		id = jobj.getInt("id");
		points = jobj.getInt("points");
		level = jobj.getInt("level");
		avatarURL = jobj.getString("avatar");
		displayName = jobj.getString("display_name");
	}
	

	
	public void setPlace(int place){
		this.place = place;
	}
	
	public String getPlace(){
		return "" + place;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public String getId(){
		return "" + id;
	}
	
	public String getPoints(){
		return "" + points;
	}
	
	public String getLevel(){
		return " " + level;
	}
	
	public String getAvatarURL(){
		return avatarURL;
	}
	
	public String toString(){
		return "" + firstName + " " + lastName + "" + id + "" + points;
	}
}
