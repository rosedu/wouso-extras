package cdl.android.ui.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.compose_message);

		EditText toEdit = (EditText) findViewById(R.id.to);
		EditText subjectEdit = (EditText) findViewById(R.id.subject);
		EditText textEdit = (EditText) findViewById(R.id.text);
		Button sendButton = (Button) findViewById(R.id.send);

		final Editable to = toEdit.getText();
		final Editable subject = subjectEdit.getText();
		final Editable text = textEdit.getText();
		
		String messageId = null;
		String topic = null;
		String from_id = null;
		boolean isItAReply = false;
		
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			String receiver = extras.getString("receiver");
			messageId = extras.getString("messageId");
			topic = extras.getString("subject");
			isItAReply = extras.getBoolean("isItAReply");
			from_id = extras.getString("from_id");
			if (receiver != null){
				toEdit.setText(receiver);
				toEdit.setEnabled(false);
			}
			if (topic != null){	
				subjectEdit.setText(topic);
				subjectEdit.setEnabled(false);
			}
			textEdit.setFocusableInTouchMode(true);
			textEdit.requestFocus();
		}
		
		final String recipient = from_id;
		final String message = topic;
		final boolean sendAsReply = isItAReply;
		final String idOfTheMessageToReplyTo = messageId;
		
		sendButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ServerResponse res;
				/*The message was a response or not? true - it was, false - it wasn't */
				boolean replyOrNot = true;
				
				if (sendAsReply){
					res = sendMessage(recipient, message,
							text.toString(), idOfTheMessageToReplyTo);
				}
				else{
					res = sendMessage(to.toString(), subject.toString(),
							text.toString(), null);
					replyOrNot = false;
				}
				if (res.getStatus() == false)
					Toast.makeText(getApplicationContext(), res.getError(),
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Message sent!",
							Toast.LENGTH_SHORT).show();
				
				EditText toEdit = (EditText) findViewById(R.id.to);
				EditText subjectEdit = (EditText) findViewById(R.id.subject);
				EditText textEdit = (EditText) findViewById(R.id.text);
				
				toEdit.setText("");
				subjectEdit.setText("");
				textEdit.setText("");
				
				if (replyOrNot){
					finish();
				}
				else {
					((MessageTabs) getParent()).switchTab(1);
				}
			}
		});
	}

	public ServerResponse sendMessage(String to, String subject,
			String text, String reply_to) {
		/** Add data */	
	
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		
		nameValuePairs.add(new BasicNameValuePair("receiver", to));
		nameValuePairs.add(new BasicNameValuePair("text", text));
		nameValuePairs.add(new BasicNameValuePair("subject", subject));
		if (reply_to != null){
			nameValuePairs.add(new BasicNameValuePair("reply_to", reply_to));
		}
		
		return ApiHandler.sendPost(ApiHandler.msgSendAPICallURL,
				nameValuePairs, this);
	}
	
	public void goBack(View view){
		this.finish();
	}

	protected void onExit() {
		Intent in = new Intent();
		setResult(RESULT_OK, in);
		finish();
	}

}