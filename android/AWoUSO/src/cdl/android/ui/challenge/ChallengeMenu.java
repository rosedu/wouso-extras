package cdl.android.ui.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.server.ChallengeHandler;
import cdl.android.ui.main.MainMenu;

public class ChallengeMenu extends ListActivity {

	ArrayList<String> listItems = new ArrayList<String>();
	Map<String, RChallengeInfo> mapped = new HashMap<String, RChallengeInfo>();
	ArrayAdapter<String> adapter;
	int clickCounter = 0;
	public static RChallengeInfo currentSelected;
	String currentSelectedName;

	private Button accept, play, reject;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.challenge);

		accept = (Button) findViewById(R.id.buttleft);
		play = (Button) findViewById(R.id.buttmid);
		reject = (Button) findViewById(R.id.buttright);
		
		accept.setVisibility(4);
		play.setVisibility(4);
		reject.setVisibility(4);

		accept.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (arg0.getVisibility()==4||currentSelected == null)
					return;
				accept.setVisibility(4);
				play.setVisibility(0);
				reject.setVisibility(4);
				ChallengeHandler.changeChallengeState(currentSelected.getChallengeId(), 0);
				currentSelected.setStatus("W");
				Toast.makeText(getApplicationContext(), "Challenge accepted!", 1).show();
			}
		});
		
		final ChallengeMenu javaSucks = this;

		play.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (arg0.getVisibility()==4||currentSelected == null)
					return;
				Intent x = new Intent(javaSucks, ActiveChallenge.class);
				startActivity(x);
				finish();
				
			}
		});
		reject.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (arg0.getVisibility()==4||currentSelected == null)
					return;
				
				accept.setVisibility(4);
				play.setVisibility(4);
				reject.setVisibility(4);
				listItems.remove(currentSelectedName);
				adapter.remove(currentSelectedName);
				ChallengeHandler.changeChallengeState(currentSelected.getChallengeId(), 2);
				currentSelected.setStatus("W");
				Toast.makeText(getApplicationContext(), "Challenge denied!", 1).show();
			}
		});

		// Set the challenge list
		RChallengeList list = ChallengeHandler.getChallengeList(MainMenu.globalUsername);
		for (int l = 0; l < list.getTotalChallenges(); l++) {
			RChallengeInfo rc = list.getChallenge(l);
			String toAdd = "From " + rc.getFrom() + " to " + rc.getTo();
			listItems.add(toAdd);
			mapped.put(toAdd, rc);
		}

		// Android stuff
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);

		// Set the back button
		Button back = (Button) findViewById(R.id.buttback);
		back.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				onExit();

			}

		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String clicked = listItems.get(position);
		currentSelectedName = clicked;
		RChallengeInfo rinfo = mapped.get(clicked);
		currentSelected = rinfo;
		
		if(rinfo.getStatus().equals("L")) {
			accept.setVisibility(0);
			play.setVisibility(4);
			reject.setVisibility(0);

		} else {
			accept.setVisibility(4);
			play.setVisibility(0);
			reject.setVisibility(4);

		}

	}

	/**
	 * Sets result (for the caller Activity) and exits
	 */
	protected void onExit() {
		setResult(RESULT_OK);
		finish();
	}

	// METHOD WHICH WILL HANDLE DYNAMIC INSERTION
	public void addItems(View v) {
		listItems.add("Clicked : " + clickCounter++);
		adapter.notifyDataSetChanged();
	}
}
