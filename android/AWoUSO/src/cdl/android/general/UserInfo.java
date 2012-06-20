package cdl.android.general;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User info container class
 */
public class UserInfo {
	String firstName;
	String lastName;
	int levelNo;
	String race;
	String email;
	int points;
	
	/**
	 * Creates new UserInfo from a JSONObject probably taken from the website's API.
	 * @param jObj The JSONObject to be parsed.
	 */
	public UserInfo(JSONObject jObj) {
		try {
			firstName = jObj.getString("first_name");
			lastName = jObj.getString("last_name");
			points = jObj.getInt("points");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the last name of the user..
	 * @return the last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets a new last name for the user.
	 * @param lastName The new last name of the user.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the first name of the user.
	 * @return The first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user.
	 * @param firstName The first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	/**
	 * Gets the user's level.
	 * @return The user's level.
	 */
	public int getLevelNo() {
		return levelNo;
	}

	/**
	 * Sets a new user level.
	 * @param levelNo The new level.
	 */
	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}

	/**
	 * Gets the user's email.
	 * @return The user's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email.
	 * @param email The email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user's race.
	 * @return The race.
	 */
	public String getRace() {
		return race;
	}

	/**
	 * Sets the user's race.
	 * @param race The new race.
	 */
	public void setRace(String race) {
		this.race = race;
	}

	/**
	 * Gets the user's points.
	 * @return How many points the user has.
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Sets the user's points.
	 * @param points The new value for the user's points.
	 */
	public void setPoints(int points) {
		this.points = points;
	}
}
