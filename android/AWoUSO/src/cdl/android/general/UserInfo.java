package cdl.android.general;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import cdl.android.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * User info container class
 */
public class UserInfo {
	String firstName;
	String lastName;
	int levelNo;
	int gold;
	String group;
	String race;
	String email;
	String avatarUrl;
	ImageView avatar;
	int points;
	double levelPercent;

	/**
	 * Creates new UserInfo from a JSONObject probably taken from the website's
	 * API.
	 * 
	 * @param jObj
	 *            The JSONObject to be parsed.
	 */
	public UserInfo(JSONObject jObj) {
		try {
			levelNo = jObj.getInt("level_no");
			firstName = jObj.getString("first_name");
			lastName = jObj.getString("last_name");
			points = jObj.getInt("points");
			gold = jObj.getInt("gold");
			group = jObj.getString("group");
			levelNo = jObj.getInt("level_no");
			levelPercent = jObj.getJSONObject("level_progress").getDouble(
					"percent");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public double getLevelPercent() {
		return levelPercent;
	}

	public void setLevelPercent(double levelPercent) {
		this.levelPercent = levelPercent;
	}

	public void setAvatar() {
		Bitmap b = BitmapFactory.decodeResource(getResources(),
				R.drawable.empty);
		avatar.setImageBitmap(b);
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(avatarUrl))
					.openConnection();
			con.connect();
			b = BitmapFactory.decodeStream(con.getInputStream());
			avatar.setImageBitmap(b);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public ImageView getAvatar() {
		setAvatar();
		return avatar;
	}

	private Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public void setAvatar(ImageView avatar) {
		this.avatar = avatar;
	}

	/**
	 * Gets the last name of the user.
	 * 
	 * @return the last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets a new last name for the user.
	 * 
	 * @param lastName
	 *            The new last name of the user.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the first name of the user.
	 * 
	 * @return The first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user.
	 * 
	 * @param firstName
	 *            The first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the user's level.
	 * 
	 * @return The user's level.
	 */
	public int getLevelNo() {
		return levelNo;
	}

	/**
	 * Sets a new user level.
	 * 
	 * @param levelNo
	 *            The new level.
	 */
	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}

	/**
	 * Gets the user's email.
	 * 
	 * @return The user's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email.
	 * 
	 * @param email
	 *            The email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user's race.
	 * 
	 * @return The race.
	 */
	public String getRace() {
		return race;
	}

	/**
	 * Sets the user's race.
	 * 
	 * @param race
	 *            The new race.
	 */
	public void setRace(String race) {
		this.race = race;
	}

	/**
	 * Gets the user's points.
	 * 
	 * @return How many points the user has.
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Sets the user's points.
	 * 
	 * @param points
	 *            The new value for the user's points.
	 */
	public void setPoints(int points) {
		this.points = points;
	}
}
