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
	private UserInfo userInfo;
	private int place;
	
	public void parseContent(JSONObject jobj) throws JSONException{
		firstName = jobj.getString("first_name");
		lastName = jobj.getString("last_name");
		id = jobj.getInt("id");
		points = jobj.getInt("points");
	}
	
	public void setUserInfo(Context context){
				ServerResponse resp =  ApiHandler.get(ApiHandler.baseURL + "player/" + id + "/info/", context);
				
				userInfo = new UserInfo();
				
				if (resp.getStatus() == false) {
					Toast.makeText(context, resp.getError(), Toast.LENGTH_SHORT).show();
					return;
				} else{
					try {
						userInfo.parseContent(resp.getData());
					} catch (JSONException e) {
						Toast.makeText(context, "Server response format error.",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}			
	}
	
	public UserInfo getUserInfo(){
		return userInfo;
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
	
	public String getId(){
		return "" + id;
	}
	
	public String getPoints(){
		return "" + points;
	}
	
	public String toString(){
		return "" + firstName + " " + lastName + "" + id + "" + points;
	}
}
