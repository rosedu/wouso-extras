package cdl.android.ui.questionoftheday;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class QuestionOfTheDayActivity extends Activity {
	private RadioGroup radioGroup;
	private TextView question;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.qotd);
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		radioGroup = (RadioGroup) findViewById(R.id.radiogrp_qotd);
		question = (TextView) findViewById(R.id.textview_qotd);
		Button submitButton = (Button) findViewById(R.id.button_qotd);
		
		final Qotd qotd = new Qotd();
		ServerResponse resp = ApiHandler.get(ApiHandler.qotdInfoURL, this);
		if (resp.getStatus() == false) {
			Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
			finish();
		} else {
			if (!resp.getData().has("text")) {
				Toast.makeText(this, "No question of the day today.",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			try {
				qotd.parseContent(resp.getData());
			} catch (JSONException e) {
				Toast.makeText(this, "Server response format error.",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		
		Boolean ans = qotd.hadAnswered();
		if (ans == false) {
			//** Launch QOTD *//*
			question.setText(qotd.getQuestion());
			for (int i=0; i<qotd.getAnswers().size(); i++) {
				RadioButton radioButton = new RadioButton(this);
				radioButton.setText(qotd.getAnswers().get(i));
				radioGroup.addView(radioButton, layoutParams);
			}
			
			submitButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int answer = radioGroup.getCheckedRadioButtonId();
					if (answer == -1) {
						Toast.makeText(getApplicationContext(), "Please select one answer",
								Toast.LENGTH_SHORT).show();
					}
					else {
						RadioButton radioButtonChecked = (RadioButton) findViewById(answer);
						for (int i=0; i<qotd.getAnswers().size(); i++) {
							if (radioButtonChecked.getText().equals(qotd.getAnswers().get(i))) {
								List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
								nameValuePairs.add(new BasicNameValuePair("answer",
										qotd.getKeys().get(i)));

								ServerResponse res = ApiHandler.sendPost(ApiHandler.qotdInfoURL,
										nameValuePairs, getApplicationContext());
								if (res.getStatus() == false) {
									Toast.makeText(getApplicationContext(), res.getError(),
											Toast.LENGTH_SHORT).show();
								}
								else {
									Toast.makeText(getApplicationContext(), "Qotd answered!",
											Toast.LENGTH_SHORT).show();
								}
								finish();
							}
						}
					}
				}
			});
		}
		else {
			Toast.makeText(getApplicationContext(), "You have already answered!",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}
}
