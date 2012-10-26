package cdl.android.ui.message;

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
import cdl.android.server.MessageHandler;

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

		SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		final String user = mPreferences.getString("username", null);

		sendButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MessageHandler.sendMessage(user, to.toString(), subject.toString(), text.toString());
				Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_SHORT).show();
				((MessageTabs) getParent()).switchTab(1);
			}
		});
	}

	protected void onExit() {
		Intent in = new Intent();
		setResult(RESULT_OK, in);
		finish();
	}

}