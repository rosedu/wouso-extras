package cdl.android.ui.challenge.menu;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Represents wrapped info about a specific challenge.
 */
public class RChallengeInfo {
	private int challengeId;
	private String fromUser;
	private String toUser;
	private int fromUserId;
	private int toUserId;
	private String status;
	private String date;
	private boolean myChallenge;

	/**
	 * Creates a new wrapper from a compiled JSONObject.
	 * 
	 * @param jo
	 *            The parsed JSONObject, will be read for Challenge information.
	 */
	public RChallengeInfo(JSONObject jo, Context context) {
		try {
			status = jo.getString("status");
			toUser = jo.getString("user_to");
			fromUser = jo.getString("user_from");
			fromUserId = jo.getInt("user_from_id");
			toUserId = jo.getInt("user_to_id");
			date = jo.getString("date");
			challengeId = jo.getInt("id");
			String id = PreferenceManager.getDefaultSharedPreferences(context).getString("id", null);
			Log.d("Wouso", "From id " + fromUserId + " myId = " + id);
			if (fromUserId == Integer.parseInt(id)){
				myChallenge = true;
			}
			else {
				myChallenge = false;
			}

		} catch (JSONException jex) {
			jex.printStackTrace();
		}
	}

	/**
	 * Gets the challenge's id.
	 * 
	 * @return
	 */
	public int getChallengeId() {
		return challengeId;
	}

	/**
	 * Gets the full name of whoever sent the challenge.
	 * 
	 * @return The challenger's name.
	 */
	public String getFromUser() {
		return fromUser;
	}

	/**
	 * Gets the full name of whoever received the challenge.
	 * 
	 * @return The challenged's name.
	 */
	public String getToUser() {
		return toUser;
	}

	/**
	 * Returns the status of the challenge L = not started A = started
	 * 
	 * @return The challenge's status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets a new status for the challenge.
	 * 
	 * @param string
	 *            The new status.
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * Returns the date at which the challenge was started.
	 * 
	 * @return The starting date.
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Returns if the challenge was launched by me or not.
	 * 
	 * @return true if it was launched by me, false otherwise.
	 */
	public boolean isMyChallenge() {
		return myChallenge;
	}
}
