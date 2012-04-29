package cdl.android.model;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Qotd {
	public boolean isHad_answered() {
		return had_answered;
	}

	public void setHad_answered(boolean had_answered) {
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

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	boolean had_answered;
	String question;
	ArrayList<String> answers;

	public Qotd(JSONObject obj) {
		answers = new ArrayList<String>();
		try {
			question = obj.getString("text");
			had_answered = obj.getBoolean("had_answered");
			JSONObject vObj = obj.getJSONObject("answers");
			int length = vObj.length();

			for (int i = 0; i < length; i++)
				answers.add(i, vObj.getString((i+1)+""));
	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.out.println("Checkkk ");
		for (int i = 0; i < answers.size(); i++) {
			System.out.println("a " + answers.get(i));
		}
	}

}
