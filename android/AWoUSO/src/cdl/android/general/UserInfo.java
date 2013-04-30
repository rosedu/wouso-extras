package cdl.android.general;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.widget.ImageView;
import cdl.android.R;

/**
 * User info container class
 */
public class UserInfo {
	String username;
	String id;
	String firstName;
	String lastName;
	String raceSlug;
	String raceId;
	int levelNo;
	int gold;
	String group;
	String race;
	String email;
	String avatarUrl;
	int points;
	double levelPercent;

	/**
	 * Creates new UserInfo from a JSONObject
	 * 
	 * @param jObj
	 *            The JSONObject to be parsed.
	 * @throws JSONException 
	 */
	public void parseContent(JSONObject jObj) throws JSONException {
		levelNo = jObj.getInt("level_no");
		username = jObj.getString("username");
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
		raceId = jObj.getString("race_id");
		raceSlug = jObj.getString("race_slug");
		username = jObj.getString("username");
	}

	// TODO: separate parse method with throw

	public double getLevelPercent() {
		return levelPercent;
	}

	public void setLevelPercent(double levelPercent) {
		this.levelPercent = levelPercent;
	}

	// TODO: http request on a new thread
	// and decide where to keep the method for a more general purpose
	//&arg pixels must bet set 0 for rectangular
	public static void setAvatar(ImageView avatar, String url, int pixels) {

		Bitmap b = BitmapFactory.decodeResource(avatar.getContext()
				.getResources(), R.drawable.empty);
		b = UserInfo.getRoundedCornerBitmap(b, pixels);
		avatar.setImageBitmap(b);
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(url))
					.openConnection();
			con.connect();
			b = BitmapFactory.decodeStream(con.getInputStream());
			b = UserInfo.getRoundedCornerBitmap(b, pixels);
			avatar.setImageBitmap(b);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
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
	 * Gets the userame of the user.
	 * 
	 * @return The userame.
	 */
	public String getUserame() {
		return username;
	}

	/**
	 * Sets the userame of the user.
	 * 
	 * @param username
	 *            The username.
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * Gets the user's race_slug.
	 * 
	 * @return The race_slug.
	 */
	public String getRaceSlug() {
		return raceSlug;
	}
	
	/**
	 * Sets the user's race_slug.
	 * 
	 * @param race
	 *            The new race_slug.
	 */
	public void setRaceSlug(String raceSlug) {
		this.raceSlug = raceSlug;
	}
	
	/**
	 * Gets the user's race_id.
	 * 
	 * @return The race_id.
	 */
	public String getRaceId() {
		return raceId;
	}
	
	/**
	 * Sets the user's race_id.
	 * 
	 * @param race
	 *            The new race_id.
	 */
	public void setRaceId(String raceId) {
		this.raceId = raceId;
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
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        
        
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
	
}
