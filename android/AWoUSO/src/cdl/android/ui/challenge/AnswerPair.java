package cdl.android.ui.challenge;

/**
 * Represents an answer pair.
 *
 */
public class AnswerPair {
	private String answer, key;
	
	
	/**
	 * Creates a new answer pair.
	 * @param key The answer's key.
	 * @param answer The answer itself.
	 */
	public AnswerPair(String key, String answer) {
		this.answer = answer;
		this.key = key;
	}


	/**
	 * Gets the question's answer.
	 * @return The answer.
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Gets the question's key.
	 * @return The key.
	 */
	public String getKey() {
		return key;
	}

}
