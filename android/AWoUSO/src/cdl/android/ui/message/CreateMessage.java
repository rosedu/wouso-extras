package cdl.android.ui.message;

import cdl.android.server.ApiRequests;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cdl.android.R;

public class CreateMessage extends Activity{
	
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
		
		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		final String user = mPreferences.getString("username", null);
		
        sendButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			ApiRequests req = new ApiRequests();
    			req.sendMessage(user, to.toString(), subject.toString(), text.toString());
    			onExit();
    		}
    	});
	}
	
	protected void onExit() {
		Intent in = new Intent();
		setResult(RESULT_OK, in);
		finish();
	}

}
