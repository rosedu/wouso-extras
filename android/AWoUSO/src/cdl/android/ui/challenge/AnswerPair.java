package cdl.android.ui.challenge;

public class AnswerPair {
	private String answer, key;
	
	
	public AnswerPair(String key, String answer) {
		this.answer = answer;
		this.key = key;
	}


	public String getAnswer() {
		return answer;
	}


	public String getKey() {
		return key;
	}

}
