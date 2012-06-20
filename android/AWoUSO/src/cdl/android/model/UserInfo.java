package cdl.android.model;

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
	int gold;
	double level_percent;
	
	public UserInfo(JSONObject obj) {
		try {
			firstName = obj.getString("first_name");
			lastName = obj.getString("last_name");
			points = obj.getInt("points");
			gold = obj.getInt("gold");
			levelNo = obj.getJSONObject("levelNo").getInt("levelNo");
			level_percent = obj.getJSONObject("level_progress").getDouble("percent");
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
	
	public void setGold(int gold){
		this.gold = gold;
	}
	
	public int getGold(){
		return gold;
	}
	
	public double getLevelPercent() {
		return level_percent;
	}
	public void setLevelPercent() {
		this.level_percent = level_percent;
	}
>>>>>>> Vertical progress bar added
}
