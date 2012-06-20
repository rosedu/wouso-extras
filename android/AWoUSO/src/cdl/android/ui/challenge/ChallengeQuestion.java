package cdl.android.ui.challenge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a specific question from a challenge.
 * 
 */
public class ChallengeQuestion {

	private String text, myKey;
	private Map<Integer, AnswerPair> answers = new HashMap<Integer, AnswerPair>();

	/**
	 * Creates a new ChallengeQuestion with the specific wrapper info.
	 * 
	 * @param myKey
	 *            The challenge's key.
	 * @param jsonObject
	 *            The parsed JSON data.
	 */
	public ChallengeQuestion(String myKey, JSONObject jsonObject) {
		this.myKey = myKey;
		try {
			text = jsonObject.getString("text");
			JSONObject vObj = jsonObject.getJSONObject("answers");
			@SuppressWarnings("rawtypes")
			Iterator iter = vObj.keys();
			int nr = 0;
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = vObj.getString(key);
				answers.put(nr, new AnswerPair(key, value));
				nr++;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the challenge's text.
	 * 
	 * @return The text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the possible answers to the question.
	 * 
	 * @return A map which contains the question's number, and the answer+id.
	 */
	public Map<Integer, AnswerPair> getAnswers() {
		return answers;
	}

	/**
	 * Gets the key (sort of an id) of the question.
	 * 
	 * @return The key.
	 */
	public String getKey() {
		return myKey;
	}

	/**
	 * Gets the answer at one of the positions.
	 * 
	 * @param i
	 *            The position.
	 * @return The answer, or null;
	 */
	public String getAnswer(int i) {
		try {
			return answers.get(i).getAnswer();
		} catch (Exception ex) {
			return null;
		}
	}

}
