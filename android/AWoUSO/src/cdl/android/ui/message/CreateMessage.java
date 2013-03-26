package cdl.android.ui.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.R.bool;
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
		// Done the above TODO
		SharedPreferences mPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String user = mPreferences.getString("username", null);
		final String senderId = mPreferences.getString("id", null);
		String reply_to = null;
		String topic = null;
		
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			String receiver = extras.getString("receiver");
			topic = extras.getString("subject");
			reply_to = extras.getString("reply_to");
			if (receiver != null){
				toEdit.setText(receiver);
			}
			if (topic != null){	
				subjectEdit.setText(topic);
			}
			textEdit.setFocusableInTouchMode(true);
			textEdit.requestFocus();
		}
		
		final String recipient = reply_to;
		final String message = topic;
		
		sendButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ServerResponse res;
				/*The message was a response or not? true - it was, false - it wasn't */
				boolean replyOrNot = true; 
				if (recipient != null){
					res = sendMessage(user, recipient, message,
							text.toString(), senderId);
				}
				else{
					res = sendMessage(user, to.toString(), subject.toString(),
							text.toString(), senderId);
					replyOrNot = false;
				}
				if (res.getStatus() == false)
					Toast.makeText(getApplicationContext(), res.getError(),
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Message sent!",
							Toast.LENGTH_SHORT).show();
				if (replyOrNot){
					finish();
				}
				else {
					((MessageTabs) getParent()).switchTab(1);
				}
			}
		});
	}

	public ServerResponse sendMessage(String user, String to, String subject,
			String text, String reply_to) {
		/** Add data */	
	
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		
		nameValuePairs.add(new BasicNameValuePair("receiver", to));
		nameValuePairs.add(new BasicNameValuePair("text", text));
		nameValuePairs.add(new BasicNameValuePair("subject", subject));
		nameValuePairs.add(new BasicNameValuePair("reply_to", reply_to));
		
		return ApiHandler.sendPost(ApiHandler.msgSendAPICallURL,
				nameValuePairs, this);
	}

	protected void onExit() {
		Intent in = new Intent();
		setResult(RESULT_OK, in);
		finish();
	}

}