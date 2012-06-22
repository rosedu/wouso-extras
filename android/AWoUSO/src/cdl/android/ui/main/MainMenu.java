package cdl.android.ui.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.Qotd;
import cdl.android.general.ServerResponse;
import cdl.android.general.UserInfo;
import cdl.android.server.ApiHandler;
import cdl.android.server.AuthHandler;
import cdl.android.server.GeneralHandler;
import cdl.android.ui.bazaar.BazaarTabs;
import cdl.android.ui.challenge.menu.ChallengeMenu;
import cdl.android.ui.user.UserProfile;

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

		try {
			userInfo = GeneralHandler.getUserInfo(username);
		} catch (NullPointerException ex) {
			AuthHandler helper = new AuthHandler(this);
			helper.logOut();
			Toast.makeText(this, "Login error, please relogin!",
					Toast.LENGTH_SHORT).show();
			return;

		}

		/** Fill Activity Views */
		TextView userProfile = (TextView) findViewById(R.id.profileName);
		userProfile.setText(userInfo.getLastName());

		TextView pointsCount = (TextView) findViewById(R.id.points);
		pointsCount.setText(userInfo.getPoints() + "");

		TextView goldCount = (TextView) findViewById(R.id.gold);
		goldCount.setText(userInfo.getGold() + "");

		TextView levelNo = (TextView) findViewById(R.id.levelNo);
		levelNo.setText("Level "+userInfo.getLevelNo()+"-");
		
	//	TextView group = (TextView) findViewById(R.id.group);
	//	group.setText(userInfo.getGroup());

		ImageView playerLevel = (ImageView) findViewById(R.id.level);
		playerLevel.setImageResource(R.drawable.levelex);
		
		/*
		 * ImageView avatar = (ImageView) findViewById(R.id.profileImage);
		 */
		ProgressBar mProgress = (ProgressBar)
		findViewById(R.id.vertical_progressbar);
		mProgress.setProgress((int)userInfo.getLevelPercent());
		

		final Intent bazaarMenu = new Intent(this, BazaarTabs.class);
		final Intent challMenu = new Intent(this, ChallengeMenu.class);
		Button bazaarButton = (Button) findViewById(R.id.shopbtn);
		Button challButton = (Button) findViewById(R.id.chalbtn);
		Button qotdButton = (Button) findViewById(R.id.qotdbtn);
		Button specialQuest = (Button) findViewById(R.id.spcQbtn);

		final Toast weekQ = Toast.makeText(getApplicationContext(),
				"Sorry, no weekly quest!", Toast.LENGTH_SHORT);
		weekQ.setGravity(Gravity.CENTER, 0, 0);
		
		//TODO: start other user profile (from Tops/Groups/Search
//		btn.setOnClickListener(new View.OnClickListener(){
//
//			public void onClick(View v) {
//				startUserProfileActivity(MainMenu.getLoggedUsername());
//				
//			}});

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
			public void onClick(View v) {
				String username = mPreferences.getString("username", null);
				final Qotd qotd;
				try {
					qotd = GeneralHandler.getQOTD(username);
				} catch (JSONException e) {
					Toast.makeText(getApplication(), "No QOTD today!",
							Toast.LENGTH_SHORT).show();
					return;
				}

				Boolean ans = qotd.hadAnswered();
				if (ans == false) {

					final CharSequence[] items = new String[qotd.getAnswers()
							.size()];
					for (int i = 0; i < qotd.getAnswers().size(); i++) {
						items[i] = qotd.getAnswers().get(i);
					}

					AlertDialog.Builder builder = new AlertDialog.Builder(v
							.getContext());
					builder.setIcon(R.drawable.androidicon_small);
					builder.setTitle(qotd.getQuestion());
					builder.setSingleChoiceItems(items, -1,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int item) {

									List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
											1);
									nameValuePairs
											.add(new BasicNameValuePair(
													"answer", qotd.getKeys()
															.get(item)));
									String url = "http://wouso-next.rosedu.org/api/qotd/today/?user="
											+ mPreferences.getString(
													"username", null);
									ServerResponse res = ApiHandler.sendPost(
											url, nameValuePairs);
									if (res.getResponse() == false)
										Toast.makeText(getApplicationContext(),
												res.getError(),
												Toast.LENGTH_SHORT).show();
									else {
										Toast.makeText(getApplicationContext(),
												"QotD answered!",
												Toast.LENGTH_SHORT);
									}
									dialog.dismiss();
								}
							});

					AlertDialog alert = builder.create();
					alert.show();

				}

				else {
					Toast.makeText(getApplicationContext(),
							"You already answered!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}


	public static String getLoggedUsername() {
		return globalUsername;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String toast = "";
		switch (item.getItemId()) {
		case R.id.top:
			toast = "Not yet";
			break;
		case R.id.map:
			toast = "Not yet";
			break;
		case R.id.search:
			toast = "Not yet";
			break;
		case R.id.logout:
			AuthHandler authHelper = new AuthHandler(this);
			authHelper.logOut();
		default:
			return true;
		}
		Toast myToast = Toast.makeText(this, toast, Toast.LENGTH_SHORT);
		myToast.setGravity(Gravity.CENTER, myToast.getXOffset() /2, myToast.getYOffset()/2);
		myToast.show();
		return false;
	}

	// Use this method to display a user profile. You need to provide the
	// username, which is checked against the database

	public void startUserProfileActivity(String username) {

		final Intent otherUserProfile = new Intent(this, UserProfile.class);
		Bundle b = new Bundle();
		b.putString("username", username);
		otherUserProfile.putExtras(b);
		startActivity(otherUserProfile);

	}

}
