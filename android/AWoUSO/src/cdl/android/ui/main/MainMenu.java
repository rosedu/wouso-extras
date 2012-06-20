package cdl.android.ui.main;

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
import cdl.android.server.AuthHandler;
import cdl.android.server.GeneralHandler;
import cdl.android.ui.bazaar.BazaarTabs;
import cdl.android.ui.challenge.ChallengeMenu;

/** 
 * User's profile and main application menu 
 */
public class MainMenu extends Activity {
	SharedPreferences mPreferences;
	UserInfo userInfo;
	private static String globalUsername;

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
        String username = mPreferences.getString("username", null);
        globalUsername = username;

        /** Gets user info from the server */
        
        try{
        userInfo = GeneralHandler.getUserInfo(username);
        } catch(NullPointerException ex) {
        	AuthHandler helper= new AuthHandler(this); //Logout if an error occurs during login.
        	helper.logOut();
        	Toast.makeText(this, "Login error, please relogin!", 1);
        	return;
        	
        }

        /** Fill Activity Views */
        TextView userProfile = (TextView) findViewById(R.id.profileName);
        userProfile.setText(username);
        
        TextView pointsCount = (TextView) findViewById(R.id.points);
        pointsCount.setText(userInfo.getPoints()+"");
        
		TextView goldCount = (TextView) findViewById(R.id.gold);
        goldCount.setText("0");
        
        ImageView playerLevel = (ImageView) findViewById(R.id.level);
        playerLevel.setImageResource(R.drawable.levelex);
        
        final Intent bazaarMenu = new Intent(this, BazaarTabs.class);
        final Intent challMenu = new Intent(this, ChallengeMenu.class);
        Button bazaarButton = (Button) findViewById(R.id.shopbtn);
        Button challButton = (Button) findViewById(R.id.chalbtn);
        Button qotdButton = (Button) findViewById(R.id.qotdbtn);
        Button specialQuest = (Button) findViewById(R.id.spcQbtn);
        Button logoutButton = (Button) findViewById(R.id.logtbtn);

        final Toast weekQ = Toast.makeText(getApplicationContext(), 
        		"Sorry, no weekly quest!", Toast.LENGTH_SHORT);
        weekQ.setGravity(Gravity.CENTER, 0, 0);

        bazaarButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			startActivity(bazaarMenu);
    		}
    	});
        
        challButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(challMenu);
				
			}
		});

        specialQuest.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				weekQ.show();
			}
		});
                
        qotdButton.setOnClickListener(new OnClickListener() {
        	//TODO 1: Handle Question of the Day
			public void onClick(View v) {
		        String username = mPreferences.getString("username", null);
				final Qotd qotd = GeneralHandler.getQOTD(username);
				
				final CharSequence[] items = new String[qotd.getAnswers().size()];
				for (int i = 0; i < qotd.getAnswers().size(); i++) {
					items[i] = qotd.getAnswers().get(i);
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle(qotd.getQuestion());
				builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				        Toast.makeText(getApplicationContext(), items[item] + ":" + 
				        		qotd.getKeys().get(item), Toast.LENGTH_SHORT).show();
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
        
        logoutButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AuthHandler authHelper = new AuthHandler(v.getContext());
				authHelper.logOut();
			}
		});
	}	
	
	public static String getLoggedUsername() {
		return globalUsername;
	}
	
}
