package cdl.android.ui.challenge.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.server.ChallengeHandler;
import cdl.android.ui.challenge.ActiveChallenge;

public class ChallengeMenu extends ListActivity {

	private RChallengeInfo currentSelected;
	private ArrayList<String> listItems = new ArrayList<String>();
	private Map<String, RChallengeInfo> mapped = new HashMap<String, RChallengeInfo>();
	private ArrayAdapter<String> adapter;
	private String currentSelectedName;
	private Context context;
	private Button accept, play, reject, cancel;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.challenge);

		accept = (Button) findViewById(R.id.challenge_button_accept);
		play = (Button) findViewById(R.id.challenge_button_play);
		reject = (Button) findViewById(R.id.challenge_button_reject);
		cancel = (Button) findViewById(R.id.challenge_button_cancel);
		
		context = this;

		accept.setVisibility(4);
		play.setVisibility(4);
		reject.setVisibility(4);
		cancel.setVisibility(4);

		accept.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (view.getVisibility() == 4 || currentSelected == null){
					return;
				}
				
				accept.setVisibility(4);
				play.setVisibility(0);
				reject.setVisibility(4);
				cancel.setVisibility(4);
				ChallengeHandler.changeChallengeState(view.getContext(), currentSelected.getChallengeId(), 0);
				currentSelected.setStatus("L");//was W. why?
				Toast.makeText(getApplicationContext(), "Challenge accepted!", 1).show();
			}
		});

		play.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (view.getVisibility() == 4 || currentSelected == null){
					return;
				}
				
				Intent x = new Intent(context, ActiveChallenge.class);
				Bundle b = new Bundle();
				b.putInt("data", currentSelected.getChallengeId());
				x.putExtras(b);
				startActivity(x);
				finish();

			}
		});
		
		reject.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (view.getVisibility() == 4 || currentSelected == null){
					return;
				}
				
				accept.setVisibility(4);
				play.setVisibility(4);
				reject.setVisibility(4);
				cancel.setVisibility(4);
				listItems.remove(currentSelectedName);
				adapter.remove(currentSelectedName);
				ChallengeHandler.changeChallengeState(view.getContext(), currentSelected.getChallengeId(), 2);
				currentSelected.setStatus("L");//was W. why?
				Toast.makeText(getApplicationContext(), "Challenge denied!", 1).show();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (view.getVisibility() == 4 || currentSelected == null){
					return;
				}
				
				accept.setVisibility(4);
				play.setVisibility(4);
				reject.setVisibility(4);
				cancel.setVisibility(4);
				listItems.remove(currentSelectedName);
				adapter.remove(currentSelectedName);
				ChallengeHandler.changeChallengeState(view.getContext(), currentSelected.getChallengeId(), 1);
				currentSelected.setStatus("L");//was W. why?
				Toast.makeText(getApplicationContext(), "Challenge canceled!", 1).show();
			}
		});

		// Set the challenge list
		RChallengeList list = ChallengeHandler.getChallengeList(this);
		for (int l = 0; l < list.getTotalChallenges(); l++) {
			RChallengeInfo rc = list.getChallenge(l);
			String toAdd = "From " + rc.getFromUser() + " to " + rc.getToUser();
			listItems.add(toAdd);
			mapped.put(toAdd, rc);
		}

		// Android stuff
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String clicked = listItems.get(position);
		currentSelectedName = clicked;
		RChallengeInfo rinfo = mapped.get(clicked);
		currentSelected = rinfo;

		if (rinfo.getStatus().equals("L")) {
			if (!rinfo.isMyChallenge()){
				accept.setVisibility(0);
				play.setVisibility(4);
				reject.setVisibility(0);
				cancel.setVisibility(4);
			}
			else {
				accept.setVisibility(4);
				play.setVisibility(4);
				reject.setVisibility(4);
				cancel.setVisibility(0);
			}
		} 
		else {
			accept.setVisibility(4);
			play.setVisibility(0);
			reject.setVisibility(4);
			cancel.setVisibility(4);
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
		adapter.notifyDataSetChanged();
	}
}
