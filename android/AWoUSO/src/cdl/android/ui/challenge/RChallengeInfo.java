package cdl.android.ui.challenge;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents wrapped info about a specific challenge.
 */
public class RChallengeInfo {
	private int challengeId;
	private String fromId, toId;
	private String status;
	private String date;

	/**
	 * Creates a new wrapper from a compiled JSONObject.
	 * @param jo The parsed JSONObject, will be read for Challenge information.
	 */
	public RChallengeInfo(JSONObject jo) {
		try {
			status = jo.getString("status");
			toId = jo.getString("user_to");
			fromId = jo.getString("user_from");
			date = jo.getString("date");
			challengeId = jo.getInt("id");
			
		} catch (JSONException jex) {
			jex.printStackTrace();
		}
	}

	/**
	 * Gets the challenge's id.
	 * @return
	 */
	public int getChallengeId() {
		return challengeId;
	}

	/**
	 * Gets the full name of whoever sent the challenge.
	 * @return The challenger's name.
	 */
	public String getFrom() {
		return fromId;
	}

	/**
	 * Gets the full name of whoever received the challenge.
	 * @return The challenged's name.
	 */
	public String getTo() {
		return toId;
	}

	/**
	 * Returns the status of the challenge
	 * L = not started
	 * A = started
	 * @return The challenge's status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets a new status for the challenge.
	 * @param string The new status.
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * Returns the date at which the challenge was started.
	 * @return The starting date.
	 */
	public String getDate() {
		return date;
	}
}
