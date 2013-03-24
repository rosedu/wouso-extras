package cdl.android.ui.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class CreateMessage extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compose_msg_layout);

		EditText toEdit = (EditText) findViewById(R.id.to);
		EditText subjectEdit = (EditText) findViewById(R.id.subject);
		EditText textEdit = (EditText) findViewById(R.id.text);
		Button sendButton = (Button) findViewById(R.id.send);

		final Editable to = toEdit.getText();
		final Editable subject = subjectEdit.getText();
		final Editable text = textEdit.getText();

		// TODO: set global username and id on profile load
		SharedPreferences mPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String user = mPreferences.getString("username", null);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			String receiver = extras.getString("receiver");
			String topic = extras.getString("subject");
			if (receiver != null){
				toEdit.setText(receiver);
			}
			if (topic != null){
				subjectEdit.setText(topic);
			}
			textEdit.setFocusableInTouchMode(true);
			textEdit.requestFocus();
		}
		
		sendButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ServerResponse res = sendMessage(user, to.toString(),
						subject.toString(), text.toString());
				if (res.getStatus() == false)
					Toast.makeText(getApplicationContext(), res.getError(),
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Message sent!",
							Toast.LENGTH_SHORT).show();
				//((MessageTabs) getParent()).switchTab(1);
			}
		});
	}

	public ServerResponse sendMessage(String user, String to, String subject,
			String text) {
		/** Add data */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("receiver", to));
		nameValuePairs.add(new BasicNameValuePair("text", text));
		nameValuePairs.add(new BasicNameValuePair("subject", subject));

		return ApiHandler.sendPost(ApiHandler.msgSendAPICallURL,
				nameValuePairs, this);
	}

	protected void onExit() {
		Intent in = new Intent();
		setResult(RESULT_OK, in);
		finish();
	}

}