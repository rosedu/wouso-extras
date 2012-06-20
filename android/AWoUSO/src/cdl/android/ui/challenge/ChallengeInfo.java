package cdl.android.ui.challenge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class ChallengeInfo {

	private boolean success;
	private String status;
	private String date;
	private String from, to;
	private int seconds;
	private Map<Integer, ChallengeQuestion> questions = new HashMap<Integer, ChallengeQuestion>();
	
	
	public ChallengeInfo(JSONObject object) {
		try{
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
			questions.put(nr, new ChallengeQuestion(key, vObj.getJSONObject(""+key)));
			nr++;
		}
		} catch(JSONException ex) {
			ex.printStackTrace();
		}
	}


	public boolean isSuccess() {
		return success;
	}


	public String getStatus() {
		return status;
	}


	public String getDate() {
		return date;
	}


	public String getFrom() {
		return from;
	}


	public String getTo() {
		return to;
	}


	public int getSeconds() {
		return seconds;
	}


	public Map<Integer, ChallengeQuestion> getQuestions() {
		return questions;
	}


	public ChallengeQuestion getQuestion(int activeChallenge) {
		return questions.get(activeChallenge);
	}

}
