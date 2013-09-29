package cdl.android.ui.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;
import cdl.android.ui.message.MessageTabs;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OtherMessage extends Activity{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.othermessage);
		
		TextView usernameField = (TextView)this.findViewById(R.id.field1);
		usernameField.setText(this.getIntent().getStringExtra("username"));
	}
	
	public void goBack(View view){
		this.finish();
	}
	
	public void sendMsg(View view){
		TextView usernameField = (TextView)this.findViewById(R.id.field1);
		EditText subjectField = (EditText)this.findViewById(R.id.field2);
		EditText contentField = (EditText)this.findViewById(R.id.field3);
		
		/*SharedPreferences mPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String username = mPreferences.getString("username", null);
		final String senderId = mPreferences.getString("id", null);*/
		
		ServerResponse res = sendMessage(usernameField.getText().toString(),
				subjectField.getText().toString(), contentField.getText().toString());
		if (res.getStatus() == false)
			Toast.makeText(getApplicationContext(), res.getError(),
					Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "Message sent!",
					Toast.LENGTH_SHORT).show();
		
		finish();
	}
	
	public ServerResponse sendMessage(String to, String subject, String text) {
		/** Add data */
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
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
