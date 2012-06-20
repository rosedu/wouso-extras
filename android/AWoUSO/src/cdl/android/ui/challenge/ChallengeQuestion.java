package cdl.android.ui.challenge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class ChallengeQuestion {

	private String text, myKey;
	private Map<Integer, AnswerPair> answers = new HashMap<Integer, AnswerPair>();
	
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
				answers.put(nr, new AnswerPair(key,value));
				nr++;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getText() {
		return text;
	}
	
	public Map<Integer, AnswerPair> getAnswers() {
		return answers;
	}
	
	public String getKey() {
		return myKey;
	}

	public String getAnswer(int i) {
		return answers.get(i).getAnswer();
	}

}
