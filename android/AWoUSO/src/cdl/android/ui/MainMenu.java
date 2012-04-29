package cdl.android.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.model.Qotd;
import cdl.android.model.UserInfo;
import cdl.android.server.ApiRequests;

public class MainMenu extends Activity {
	SharedPreferences mPreferences;
	UserInfo userInfo;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
	    super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainmenu);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        
		// get auth token/info from Shared Preferences
        String username = mPreferences.getString("username", null);
        // get user info from server and set activity content
        ApiRequests req = new ApiRequests();
        userInfo = req.getUserInfo(username);

        final TextView userProfile = (TextView) findViewById(R.id.profileName);
        userProfile.setText(username);
        
        final TextView pointsCount = (TextView) findViewById(R.id.points);
        pointsCount.setText(userInfo.getPoints()+"");
        
		final TextView goldCount = (TextView) findViewById(R.id.gold);
        goldCount.setText("0");
        
        final ImageView playerLevel = (ImageView) findViewById(R.id.level);
        playerLevel.setImageResource(R.drawable.levelex);
        
        final Intent bazaarMenu = new Intent(this, Tabs.class);
        Button bazaarButton = (Button) findViewById(R.id.shopbtn);
        Button qotdButton = (Button) findViewById(R.id.qotdbtn);
        Button specialQuest = (Button) findViewById(R.id.spcQbtn);

        final Toast weekQ = Toast.makeText(getApplicationContext(), "Sorry, no weekly quest!", Toast.LENGTH_SHORT);
        weekQ.setGravity(Gravity.CENTER, 0, 0);

        bazaarButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			startActivity(bazaarMenu);
    		}
    	});

        specialQuest.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				weekQ.cancel();
				weekQ.show();

			}
		});
        
        qotdButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		        String username = mPreferences.getString("username", null);
				ApiRequests req = new ApiRequests();
				Qotd qotd = req.getQOTD(username);
				
				final CharSequence[] items = new String[qotd.getAnswers().size()];
				for (int i = 0; i < qotd.getAnswers().size(); i++) {
					items[i] = qotd.getAnswers().get(i);
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle(qotd.getQuestion());
				builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
				
			}
		});
	}	
	
}
