package cdl.android.ui.challenge;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.server.ChallengeHandler;

/**
 * Represents an active challenge.
 * 
 */
public class ActiveChallenge extends Activity {

	private int challenge_id, activeChallenge;
	private final int totalChallenges = 5;
	private ChallengeInfo info;
	private CheckBox[] cboxes = new CheckBox[4];
	private TextView chtext;
	private Button back, next;
	private Boolean[][] ticked = new Boolean[5][4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achallenge);

		for (int i = 0; i <= 4; i++)
			for (int j = 0; j <= 3; j++)
				ticked[i][j] = false;

		startChallenge(getIntent().getExtras().getInt("data"));
	}

	/**
	 * Starts the challenge which has that id.
	 * 
	 * @param challenge_id
	 *            The challenge's id.
	 */
	public void startChallenge(int challenge_id) {
		this.challenge_id = challenge_id;
		info = ChallengeHandler.getChallengeInfo(challenge_id);

		int remainingSeconds = info.getSeconds();
		if (remainingSeconds < 0) {
			Toast.makeText(getApplication(), "The timer for this challenge has expired!", 1).show();
			finish();
			postAnswers();
			return;
		}
		activeChallenge = 0;

		// Set up all the buttons and stuff...
		back = (Button) findViewById(R.id.chalback);
		next = (Button) findViewById(R.id.chalnext);
		chtext = (TextView) findViewById(R.id.chaltext);

		back.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				saveCurrent();
				activeChallenge--;
				if (activeChallenge == -1) {
					activeChallenge = 0;
					Toast.makeText(getApplicationContext(), "How did you even do that? >__>", 1).show();
				}
				refreshForActiveQuestion();
			}
		});

		next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				saveCurrent();
				activeChallenge++;
				if (activeChallenge == totalChallenges) {
					Toast.makeText(getApplication(), "Sending answers...", Toast.LENGTH_SHORT).show();
					try {
						postAnswers();
					} catch (Exception ex) {
						Toast.makeText(getApplication(), "Error sending answers", Toast.LENGTH_SHORT).show();
					} finally {
						Toast.makeText(getApplication(), "Challenge finished!", Toast.LENGTH_SHORT).show();
					}
					finish();
					return;

				}
				refreshForActiveQuestion();

			}
		});

		cboxes[0] = (CheckBox) findViewById(R.id.fposa);
		cboxes[1] = (CheckBox) findViewById(R.id.sposa);
		cboxes[2] = (CheckBox) findViewById(R.id.tposa);
		cboxes[3] = (CheckBox) findViewById(R.id.foposa);

		refreshForActiveQuestion();
	}

	/**
	 * Sends the current answers to the server.
	 */
	private void postAnswers() {
		ChallengeHandler.post(this);

	}

	/**
	 * Save the current ticked boxes to the matrix.
	 */
	private void saveCurrent() {
		for (int i = 0; i < 4; i++) {
			ticked[activeChallenge][i] = cboxes[i].isChecked();
		}

	}

	/**
	 * Refreshes the activity for the current question.
	 */
	private void refreshForActiveQuestion() {
		for (int i = 0; i <= 3; i++) {
			cboxes[i].setChecked(ticked[activeChallenge][i]);
		}
		if (activeChallenge == 0) {
			back.setVisibility(4);
		} else {
			if (back.getVisibility() == 4)
				back.setVisibility(0);
		}

		if (activeChallenge == totalChallenges - 1) {
			next.setText("Finish");
		} else {
			next.setText("Next");
		}

		ChallengeQuestion curQuestion = info.getQuestion(activeChallenge);
		chtext.setText(curQuestion.getText());
		for (int i = 0; i <= 3; i++) {
			cboxes[i].setText(curQuestion.getAnswer(i));
		}

	}

	/**
	 * Gets the current challenge id.
	 * 
	 * @return The challenge id.
	 */
	public int getChallengeId() {
		return challenge_id;
	}

	/**
	 * Gets information about the current question.
	 * 
	 * @return a Wrapper around the information.
	 */
	public ChallengeInfo getInfo() {
		return info;
	}

	/**
	 * Converts the tick data to a Map which can be sent through POST to the
	 * server.
	 * 
	 * @return A wrapped map.
	 */
	public Map<String, String> getWrappable() {
		Map<String, String> toRet = new HashMap<String, String>();
		for (int i = 0; i <= 4; i++) {
			ChallengeQuestion curQuestion = info.getQuestion(i);
			String toBuild = "";
			for (int tick = 0; tick <= 3; tick++) {
				if (ticked[i][tick]) {
					Map<Integer, AnswerPair> pp = curQuestion.getAnswers();
					AnswerPair pair = pp.get(tick);
					String key = pair.getKey() + ",";
					toBuild += key;
				}
			}
			if (toBuild.length() > 1)
				toBuild = toBuild.substring(0, toBuild.length() - 1);
			toRet.put(curQuestion.getKey(), toBuild);
		}
		return toRet;
	}

}
