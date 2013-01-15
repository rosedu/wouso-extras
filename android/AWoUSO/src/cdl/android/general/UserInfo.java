package cdl.android.general;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import cdl.android.R;

/**
 * User info container class
 */
public class UserInfo implements Parcelable {
	String firstName;
	String lastName;
	int levelNo;
	int gold;
	String group;
	String race;
	String email;
	String avatarUrl;
	int points;
	double levelPercent;

	public UserInfo() {
	}

	public UserInfo(Parcel in) {
		readFromParcel(in);
	}

	private void readFromParcel(Parcel in) {
		firstName = in.readString();
		lastName = in.readString();
		levelNo = in.readInt();
		gold = in.readInt();
		group = in.readString();
		race = in.readString();
		email = in.readString();
		avatarUrl = in.readString();
		points = in.readInt();
		levelPercent = in.readDouble();
	}

	public void writeToParcel(Parcel dst, int arg1) {
		dst.writeString(firstName);
		dst.writeString(lastName);
		dst.writeInt(levelNo);
		dst.writeInt(gold);
		dst.writeString(group);
		dst.writeString(race);
		dst.writeString(email);
		dst.writeString(avatarUrl);
		dst.writeInt(points);
		dst.writeDouble(levelPercent);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public UserInfo createFromParcel(Parcel in) {
			return new UserInfo(in);
		}

		public UserInfo[] newArray(int size) {
			return new UserInfo[size];
		}
	};

	/**
	 * Creates new UserInfo from a JSONObject
	 * 
	 * @param jObj
	 *            The JSONObject to be parsed.
	 * @throws JSONException
	 */
	public void parseContent(JSONObject jObj) throws JSONException {
		levelNo = jObj.getInt("level_no");
		firstName = jObj.getString("first_name");
		lastName = jObj.getString("last_name");
		points = jObj.getInt("points");
		gold = jObj.getInt("gold");
		group = jObj.getString("group");
		levelNo = jObj.getInt("level_no");
		avatarUrl = jObj.getString("avatar");
		race = jObj.getString("race");
		levelPercent = jObj.getJSONObject("level_progress")
				.getDouble("percent");
	}

	// TODO: http request on a new thread
	// and decide where to keep the method for a more general purpose
	public static void setAvatar(ImageView avatar, String url) {

		Bitmap b = BitmapFactory.decodeResource(avatar.getContext()
				.getResources(), R.drawable.empty);
		avatar.setImageBitmap(b);
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(url))
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

	public double getLevelPercent() {
		return levelPercent;
	}

	public void setLevelPercent(double levelPercent) {
		this.levelPercent = levelPercent;
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

	public int describeContents() {
		return 0;
	}
}
