package cdl.android.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User info container class
 */
public class UserInfo {
	int levelNo;
	int points;
	int rank;

	String firstName;
	String lastName;
	String race;
	String email;
	String avatar;
	String group;

	LevelInfo levelInfo;

	public UserInfo(JSONObject obj) {
		try {
			firstName = obj.getString("first_name");
			lastName = obj.getString("last_name");
			points = obj.getInt("points");
			avatar = obj.getString("avatar");
			levelNo = obj.getInt("level_no");
			group = obj.getString("group");
			race = obj.getString("race");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
