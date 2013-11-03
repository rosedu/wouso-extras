package cdl.android.ui.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class ReadMessageReceived extends Activity {
	
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_list_item);

		Bundle data = getIntent().getExtras();

		TextView subject = (TextView) findViewById(R.id.subject);
		subject.setText(data.getString("subject"));

		TextView from = (TextView) findViewById(R.id.from);
		from.setText(data.getString("from"));

		TextView text = (TextView) findViewById(R.id.text);
		text.setText(data.getString("text"));
		
		final String receiver = data.getString("from");
		final String reply_to = data.getString("reply_to");
		final String messageId = data.getString("id");
		final String to_id = data.getString("to_id");
		final String from_id = data.getString("from_id");

		ServerResponse res = setReadMessage(messageId);
		if (res.getStatus() == false)
			Toast.makeText(getApplicationContext(), res.getError(),
					Toast.LENGTH_SHORT).show();
		
		Button back = (Button) findViewById(R.id.readmsg);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		Button reply = (Button) findViewById(R.id.reply);
		reply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, CreateMessage.class);
				TextView subject = (TextView) findViewById(R.id.subject);
				i.putExtra("messageId", messageId);
				i.putExtra("isItAReply", true);
				i.putExtra("receiver", receiver);
				i.putExtra("from_id", from_id);
				if (subject.getText().toString().startsWith("Re: ")){
					i.putExtra("subject", subject.getText().toString());
				}
				else{
					i.putExtra("subject", "Re: " + subject.getText().toString());
				}
				startActivity(i);
				finish();
			}
		});
		
		Button delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				ServerResponse res = deleteMessage(messageId);
				if (res.getStatus() == false)
					Toast.makeText(getApplicationContext(), res.getError(),
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Message deleted!",
							Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
	
	public ServerResponse deleteMessage(String messageId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		
		nameValuePairs.add(new BasicNameValuePair("msg_id", messageId));
		return ApiHandler.sendPost(ApiHandler.msgArchiveAPICallURL + messageId + "/",
				nameValuePairs , this);
	}
	
	public ServerResponse setReadMessage(String messageId) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		
		nameValuePairs.add(new BasicNameValuePair("msg_id", messageId));
		return ApiHandler.sendPost(ApiHandler.msgSetReadAPICallURL + messageId + "/",
				nameValuePairs , this);
	}
}