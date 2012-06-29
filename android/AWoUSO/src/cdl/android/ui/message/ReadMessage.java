package cdl.android.ui.message;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cdl.android.R;

public class ReadMessage extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_list_item);

		Bundle data = getIntent().getExtras();

		EditText subject = (EditText) findViewById(R.id.subject);
		subject.setText(data.getString("subject"));

		EditText from = (EditText) findViewById(R.id.from);
		from.setText(data.getString("from"));

		EditText text = (EditText) findViewById(R.id.text);
		text.setText(data.getString("text"));

		Button back = (Button) findViewById(R.id.readmsg);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}