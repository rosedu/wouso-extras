package cdl.android.model;

import android.content.res.Resources;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import cdl.android.R;

/**
 * User info container class
 */
public class UserInfo {
	String firstName;
	String lastName;
	LevelInfo levelInfo;
	int levelNo;
	int gold;
	String group;
	String race;
	String email;
	String avatarUrl;
	ImageView avatar;
	int points;
	double level_percent;
	
	public UserInfo(JSONObject obj) {
		try {
			levelNo = obj.getInt("level_no");
			firstName = obj.getString("first_name");
			lastName = obj.getString("last_name");
			points = obj.getInt("points");
			gold = obj.getInt("gold");
			group = obj.getString("group");
			levelNo = obj.getInt("level_no");
			level_percent = obj.getJSONObject("level_progress").getDouble("percent");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

    public void setAvatar() {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        avatar.setImageBitmap(b);
        try
        {
                HttpURLConnection con = (HttpURLConnection)(new URL(avatarUrl)).openConnection();
                con.connect();
                b = BitmapFactory.decodeStream(con.getInputStream());
                avatar.setImageBitmap(b);
        } catch (IOException e) {
                e.printStackTrace();
        } catch (NullPointerException e) {
                e.printStackTrace();
        }
}
    
    public ImageView getAvatar(){
    	setAvatar();
    	return avatar;
    }
	
	private Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public LevelInfo getLevelInfo() {
		return levelInfo;
	}

	public void setLevelInfo(LevelInfo level) {
		this.levelInfo = level;
	}

	public int getLevelNo() {
		return levelNo;
	}
	
	public String getGroup() {
		return group;
	}

	public int getGold() {
		return gold;
	}
	
	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
	public double getLevelPercent() {
		return level_percent;
	}
	public void setLevelPercent(double level_percent) {
		this.level_percent = level_percent;
	}
}
