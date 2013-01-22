package cdl.android.ui.challenge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents wrapped info about a specific challenge, without the id! This is
 * the difference to RChallengeInfo
 */
public class ChallengeInfo {

	private boolean success;
	private String status, date, from, to;
	private int seconds;
	private Map<Integer, ChallengeQuestion> questions = new HashMap<Integer, ChallengeQuestion>();

	/**
	 * Creates a new wrapper around the specified JSONObject,
	 * 
	 * @param object
	 *            The object to be wrapped.
	 */
	public ChallengeInfo(JSONObject object) {
		try {
			success = object.getBoolean("success");
			status = object.getString("status");
			date = object.getString("date");
			from = object.getString("from");
			to = object.getString("to");
			seconds = object.getInt("seconds");
			JSONObject vObj = object.getJSONObject("questions");
			@SuppressWarnings("rawtypes")
			Iterator iter = vObj.keys();
			int nr = 0;
			while (iter.hasNext()) {
				String key = (String) iter.next();
				questions.put(nr, new ChallengeQuestion(key, vObj.getJSONObject("" + key)));
				nr++;
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Checkes whether the challenge request was a success.
	 * 
	 * @return Whether the request was successfull.
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Gets the challenge's status.
	 * 
	 * @return The status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Gets the challenge's date.
	 * 
	 * @return The date.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Gets who the challenge is from.
	 * 
	 * @return Who sent the challenge;
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Gets who the challenge is to.
	 * 
	 * @return Who received the challenge.
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Gets the challenge's seconds.
	 * 
	 * @return The challenge's remaining seconds.
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * Gets the challenge's questions.
	 * 
	 * @return The challenge's questions.
	 */
	public Map<Integer, ChallengeQuestion> getQuestions() {
		return questions;
	}

	/**
	 * Gets the question at a certain number.
	 * 
	 * @param which
	 *            The challenge's number.
	 * @return The specific question.
	 */
	public ChallengeQuestion getQuestion(int which) {
		return questions.get(which);
	}

}
