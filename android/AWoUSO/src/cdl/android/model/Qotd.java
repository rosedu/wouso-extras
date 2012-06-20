package cdl.android.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Question of the Day container class
 */
public class Qotd {
	boolean had_answered;
	String question;
	ArrayList<String> answers;
	ArrayList<String> keys;

	public boolean hadAnswered() {
		return had_answered;
	}

	public void hadAnswered(boolean had_answered) {
		this.had_answered = had_answered;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public ArrayList<String> getAnswers() {
		return answers;
	}

	public ArrayList<String> getKeys() {
		return keys;
	}

	public Qotd(JSONObject obj) {
		answers = new ArrayList<String>();
		keys = new ArrayList<String>();
		
		try {
			question = obj.getString("text");
			had_answered = obj.getBoolean("had_answered");
			JSONObject vObj = obj.getJSONObject("answers");
			
			@SuppressWarnings("rawtypes")
			Iterator iter = vObj.keys();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = vObj.getString(key);
				answers.add(value);
				keys.add(key);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
