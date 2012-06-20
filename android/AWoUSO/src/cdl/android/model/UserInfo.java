package cdl.android.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * User info container class
 */
public class UserInfo {
	String firstName;
	String lastName;
	LevelInfo levelInfo;
	int levelNo;
	String race;
	String email;
	int points;
	
	public UserInfo(JSONObject result) {
		try {
			firstName = result.getString("first_name");
			lastName = result.getString("last_name");
			points = result.getInt("points");
		} catch (JSONException e) {
			e.printStackTrace();
		}
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

	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
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
}
