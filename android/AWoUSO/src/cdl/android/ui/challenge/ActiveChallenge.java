package cdl.android.ui.challenge;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.server.ChallengeHandler;

/**
 * Represents an active challenge.
 * 
 */
public class ActiveChallenge extends Activity {

	private int challenge_id;
	private int activeChallengeQuestion;
	private final int totalChallenges = 5;
	private ChallengeInfo info;
	private CheckBox[] cboxes;
	private TextView chtext;
	private Button back, next;
	private Boolean[][] ticked;
	
	//Timer
	private TextView dots;
	private TextView minutes;
	private TextView seconds;
	
	private int remainingSeconds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achallenge);
		
		cboxes = new CheckBox[8];
		ticked = new Boolean[5][8];

		for (int i = 0; i <= 4; i++)
			for (int j = 0; j <= 7; j++)
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
		info = ChallengeHandler.getChallengeInfo(this, challenge_id);

		Log.d("Wouso", info.getSeconds() + "");
		remainingSeconds = info.getSeconds();
		if (remainingSeconds < 0) {
			Toast.makeText(getApplication(), "The timer for this challenge has expired!", 1).show();
			postAnswers();
			finish();
			return;
		}

		activeChallengeQuestion = 0;

		dots = (TextView) findViewById(R.id.challenge_timer_dots);
		minutes = (TextView) findViewById(R.id.challenge_timer_minutes);
		seconds = (TextView) findViewById(R.id.challenge_timer_seconds);
		
		minutes.setText("" + remainingSeconds / 60 );
		if (remainingSeconds % 60 > 9){
			seconds.setText(remainingSeconds % 60 + "");
		}
		else {
			seconds.setText("0" + remainingSeconds % 60);
		}
		
		CountDownTimer timer = new CountDownTimer(info.getSeconds() * 1000, 500) {
			boolean oneSecond = false;
			
			@Override
			public void onTick(long millisUntilFinished) {
				if (oneSecond == false){
					oneSecond = true;
					dots.setVisibility(4);
				}
				else {
					oneSecond = false;
					dots.setVisibility(0);
					if ( seconds.getText().equals("00") && !minutes.getText().equals("0")){
						minutes.setText("" + (Integer.parseInt((String) minutes.getText()) - 1));
						seconds.setText("59");
						return;
					}
					
					if ( seconds.getText().equals("00") && minutes.getText().equals("0")){
						onFinish();
					}
					
					if ( Integer.parseInt((String) seconds.getText()) > 10) {
						seconds.setText("" + (Integer.parseInt((String) seconds.getText()) - 1));
						return;
					}
					else {
						seconds.setText("0" + (Integer.parseInt((String) seconds.getText()) - 1));
						return;
					}
				}
			}
			
			@Override
			public void onFinish() {
				Toast.makeText(getApplicationContext(), "Done", 1).show();
				postAnswers();
				finish();
			}
		};
		timer.start();
		
		back = (Button) findViewById(R.id.chalback);
		next = (Button) findViewById(R.id.chalnext);
		chtext = (TextView) findViewById(R.id.chaltext);

		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				saveCurrent();
				activeChallengeQuestion--;
				if (activeChallengeQuestion == -1) {
					activeChallengeQuestion = 0;
					Toast.makeText(getApplicationContext(), "How did you even do that? >__>", 1).show();
				}
				refreshForActiveQuestion();
			}
		});

		next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				saveCurrent();
				activeChallengeQuestion++;
				if (activeChallengeQuestion == totalChallenges) {
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

		cboxes[0] = (CheckBox) findViewById(R.id.challenge_checkbox_1);
		cboxes[1] = (CheckBox) findViewById(R.id.challenge_checkbox_2);
		cboxes[2] = (CheckBox) findViewById(R.id.challenge_checkbox_3);
		cboxes[3] = (CheckBox) findViewById(R.id.challenge_checkbox_4);
		cboxes[4] = (CheckBox) findViewById(R.id.challenge_checkbox_5);
		cboxes[5] = (CheckBox) findViewById(R.id.challenge_checkbox_6);
		cboxes[6] = (CheckBox) findViewById(R.id.challenge_checkbox_7);
		cboxes[7] = (CheckBox) findViewById(R.id.challenge_checkbox_8);

		refreshForActiveQuestion();
	}

	/**
	 * Sends the current answers to the server.
	 */
	private void postAnswers() {
		saveCurrent();
		ChallengeHandler.post(this, this);
	}

	/**
	 * Save the current ticked boxes to the matrix.
	 */
	private void saveCurrent() {
		for (int i = 0; i < 8; i++) {
			ticked[activeChallengeQuestion][i] = cboxes[i].isChecked();
		}

	}

	/**
	 * Refreshes the activity for the current question.
	 */
	private void refreshForActiveQuestion() {
		
		for (int i=0; i<=7; i++){
			cboxes[i].setVisibility(8);
		}
		
		ChallengeQuestion curQuestion = info.getQuestion(activeChallengeQuestion);
		chtext.setText(curQuestion.getText());
		for (int i = 0; i < curQuestion.getAnswers().size(); i++) {
			cboxes[i].setText(curQuestion.getAnswer(i));
			cboxes[i].setVisibility(0);
		}
		
		for (int i = 0; i <= 7; i++) {
			cboxes[i].setChecked(ticked[activeChallengeQuestion][i]);
		}
		if (activeChallengeQuestion == 0) {
			back.setVisibility(4);
		} else {
			if (back.getVisibility() == 4)
				back.setVisibility(0);
		}

		if (activeChallengeQuestion == totalChallenges - 1) {
			next.setText("Finish");
		} else {
			next.setText("Next");
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
			for (int tick = 0; tick <= 7; tick++) {
				if (ticked[i][tick]) {
					Map<Integer, AnswerPair> pp = curQuestion.getAnswers();
					AnswerPair pair = pp.get(tick);
					String key = pair.getKey() + ",";
					toBuild += key;
				}
			}
			
			if (toBuild.length() > 1) {
				toBuild = toBuild.substring(0, toBuild.length() - 1);
			}
			toRet.put(curQuestion.getKey(), toBuild);
		}
		
		return toRet;
	}

}
