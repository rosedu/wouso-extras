package cdl.android.general;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Question of the Day container class
 */
public class Qotd {
	private boolean hadAnswered;
	private String question;
	private ArrayList<String> answers, keys;

	/**
	 * Returns whether the question has been answered.
	 * 
	 * @return boolean representing whether the question has been answered.
	 */
	public boolean hadAnswered() {
		return hadAnswered;
	}

	/**
	 * Sets whether the question has been answered.
	 * 
	 * @param hadAnswered
	 *            the new answered value.
	 */
	public void setHadAnswered(boolean hadAnswered) {
		this.hadAnswered = hadAnswered;
	}

	/**
	 * Gets the QOTD question.
	 * 
	 * @return The question.
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Sets the question to a new value.
	 * 
	 * @param question
	 *            The new question's value.
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Gets the possible answers for the question.
	 * 
	 * @return The possible answers.
	 */
	public ArrayList<String> getAnswers() {
		return answers;
	}

	/**
	 * Gets the keys for the question.
	 * 
	 * @return The keys.
	 */
	public ArrayList<String> getKeys() {
		return keys;
	}

	/**
	 * Creates a new QOTD object from a JSONObject probably taken from the
	 * website's API.
	 * 
	 * @param obj
	 *            The object to be parsed.
	 * @throws JSONException
	 */
	public Qotd(JSONObject obj) throws JSONException {
		answers = new ArrayList<String>();
		keys = new ArrayList<String>();

		question = obj.getString("text");
		hadAnswered = obj.getBoolean("had_answered");
		JSONObject vObj = obj.getJSONObject("answers");

		@SuppressWarnings("rawtypes")
		Iterator iter = vObj.keys();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = vObj.getString(key);
			answers.add(value);
			keys.add(key);
		}
	}

}
