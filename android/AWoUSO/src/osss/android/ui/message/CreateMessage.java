package osss.android.ui.message;

import cdl.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

public class CreateMessage extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compose_msg_layout);

		EditText toEdit = (EditText) findViewById(R.id.to);
		EditText subjectEdit = (EditText) findViewById(R.id.subject);
		EditText textEdit = (EditText) findViewById(R.id.text);

		final Editable to = toEdit.getText();
		final Editable subject = subjectEdit.getText();
		final Editable text = textEdit.getText();
	}

}
