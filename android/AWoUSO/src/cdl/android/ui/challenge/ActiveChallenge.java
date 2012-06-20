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

public class ActiveChallenge extends Activity {

	private int challenge_id;
	private ChallengeInfo info;
	private int activeChallenge;
	private final int totalChallenges = 5;
	private CheckBox[] cboxes = new CheckBox[4];
	private TextView chtext;

	private Button back, next;
	private Boolean[][] ticked = new Boolean[5][4];

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achallenge);
		
		for(int i=0;i<=4;i++)
			for(int j=0;j<=3;j++)
				ticked[i][j] = false;
		
		startChallenge(ChallengeMenu.currentSelected.getChallengeId());
	}

	public void startChallenge(int challenge_id) {
		this.challenge_id = challenge_id;
		info = ChallengeHandler.getChallengeInfo(challenge_id);
		
		int remainingSeconds = info.getSeconds();
		if(remainingSeconds<0) {
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
					Toast.makeText(getApplicationContext(),
							"How did you even do that? >__>", 1).show();
				}
				refreshForActiveChallenge();
			}
		});

		next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				saveCurrent();
				activeChallenge++;
				if (activeChallenge == totalChallenges) {
					postAnswers();
					Toast.makeText(getApplication(), "Challenge finished!", 1).show();
					finish();
					return;
					
				}
				refreshForActiveChallenge();

			}
		});
		
		cboxes[0] = (CheckBox) findViewById(R.id.fposa);
		cboxes[1] = (CheckBox) findViewById(R.id.sposa);
		cboxes[2] = (CheckBox) findViewById(R.id.tposa);
		cboxes[3] = (CheckBox) findViewById(R.id.foposa);

		refreshForActiveChallenge();
	}

	private void postAnswers() {
		(new ChallengeConnection()).post(this);
		
	}

	protected void saveCurrent() {
		for(int i=0;i<4;i++) {
			ticked[activeChallenge][i] = cboxes[i].isChecked();
		}
		
	}

	private void refreshForActiveChallenge() {
		//TODO get current stuff, etc. etc.
		for(int i=0;i<=3;i++) {
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
		for(int i=0;i<=3;i++) {
			cboxes[i].setText(curQuestion.getAnswer(i));
		}
		
		
		
	}

	public int getChallengeId() {
		return challenge_id;
	}

	public ChallengeInfo getInfo() {
		return info;
	}

	public Map<String, String> getWrappable() {
		Map<String, String> toRet = new HashMap<String, String>();
		for(int i=0;i<=4;i++) {
			ChallengeQuestion curQuestion = info.getQuestion(i);
			String toBuild = "";
			for(int tick = 0;tick<=3;tick++) {
				if(ticked[i][tick]) {
					Map<Integer, AnswerPair> pp = curQuestion.getAnswers();
					AnswerPair pair = pp.get(tick);
					String key = pair.getKey()+",";
					toBuild+=key;
				}
			}
			if(toBuild.length() > 1)
				toBuild = toBuild.substring(0, toBuild.length() - 1);
			toRet.put(curQuestion.getKey(), toBuild);
		}
		return toRet;
	}

}
