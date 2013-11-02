package cdl.android.ui.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import cdl.android.general.ServerResponse;
import cdl.android.general.UserInfo;
import cdl.android.server.ApiHandler;
import cdl.android.ui.bazaar.BazaarTabs;
import cdl.android.ui.challenge.menu.ChallengeMenu;
import cdl.android.ui.map.GroupsMap;
import cdl.android.ui.message.MessageTabs;
import cdl.android.ui.questionoftheday.QuestionOfTheDayActivity;
import cdl.android.ui.tops.Tops;
import cdl.android.ui.user.UserProfile;

/**
 * User's profile and main application menu
 */
public class Profile extends Activity {
	UserInfo userInfo;
	Toast weekQ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 11) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR); 
		
		/** Gets user info from the server */
		userInfo = new UserInfo();
		ServerResponse resp = ApiHandler.get(ApiHandler.userInfoURL, this);
		if (resp.getStatus() == false) {
			Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
			userInfo = null;
		} else
			try {
				userInfo.parseContent(resp.getData());
			} catch (JSONException e) {
				Toast.makeText(this, "Server response format error.",
						Toast.LENGTH_SHORT).show();
				userInfo = null;
			}

		if (userInfo == null) {
			setContentView(R.layout.main);
			Button retry = (Button) findViewById(R.id.retryButton);
			retry.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					MainActivity.logOut(arg0.getContext());
				}
			});
		} else {
			/* Setting global values for username and id. */
			//username
			SharedPreferences mPreference= PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = mPreference.edit();
			editor.putString("username", userInfo.getUserame());
			editor.commit();
			
			//id
			final String username = mPreference.getString("username", null);
			ServerResponse result = ApiHandler.getArray(ApiHandler.searchURL
					+ username, this);
			JSONArray mData = result.getArrayData();
			if (mData != null){
					JSONObject user;
					try{
						user = mData.getJSONObject(0);
						editor.putString("id", user.getString("id"));
						editor.commit();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			setContentView(R.layout.mainmenu);
			initProfile();
		}
	}

	public void initProfile() {
		/** Fill Activity Views */
		ActionBar actionBar = this.getActionBar();
		actionBar.show();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayUseLogoEnabled(false);
		View cView = getLayoutInflater().inflate(R.layout.actionbar, null);
		actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(cView);
		
		Typeface myTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Medium.ttf");
		
/*		ImageView userLevelImage = (ImageView) findViewById(R.id.level);
		File iconFile = new File("/mnt/sdcard" + File.separator + "awouso"
				+ File.separator + "levels", userInfo.getRace() + "-level-"
				+ userInfo.getLevelNo() + ".png");
		Bitmap iconBitmap = BitmapFactory.decodeFile(iconFile.toString());
		userLevelImage.setImageBitmap(iconBitmap);*/
		
		TextView userProfile = (TextView) findViewById(R.id.profile_name);
		userProfile.setText(userInfo.getFirstName() + " "
				+ userInfo.getLastName());
		userProfile.setTypeface(myTypeface);

		ImageView avatar = (ImageView) findViewById(R.id.profile_image);
		UserInfo.setAvatar(avatar, userInfo.getAvatarUrl(), 100);

		ProgressBar mProgress = (ProgressBar) findViewById(R.id.vertical_progressbar);
		mProgress.setProgress((int) userInfo.getLevelPercent());

		final Intent bazaarMenu = new Intent(this, BazaarTabs.class);
		final Intent challMenu = new Intent(this, ChallengeMenu.class);
		final Intent messageMenu = new Intent(this, MessageTabs.class);
		final Intent topsMenu = new Intent(this, Tops.class);
		final Intent qotdActivity = new Intent(this, QuestionOfTheDayActivity.class);
		
		TextView chalText = (TextView) findViewById(R.id.challenges_text);
		chalText.setTypeface(myTypeface);
		
		TextView messText = (TextView) findViewById(R.id.messages_text);
		messText.setTypeface(myTypeface);
		
		TextView topsText = (TextView) findViewById(R.id.tops_text);
		topsText.setTypeface(myTypeface);
		
		TextView bazaarText = (TextView) findViewById(R.id.bazaar_text);
		bazaarText.setTypeface(myTypeface);
		
		ImageView bazaarButton = (ImageView) findViewById(R.id.bazaar_btn);
		//Button challButton = (Button) findViewById(R.id.chalbtn);
		ImageView chalButton = (ImageView) findViewById(R.id.challenges_btn);
		//Button qotdButton = (Button) findViewById(R.id.qotdbtn);
		//Button specialQuest = (Button) findViewById(R.id.spcQbtn);
		ImageView msgButton = (ImageView) findViewById(R.id.messages_btn);
		
		ImageView topsButton = (ImageView) findViewById(R.id.tops_btn);
		//Button msgButton = (Button) findViewById(R.id.msgbtn);

		TextView qotd = (TextView) findViewById(R.id.qotd);
		
		TextView pointsCount = (TextView) findViewById(R.id.points1_val);
		pointsCount.setTypeface(myTypeface);
		pointsCount.setText(userInfo.getPoints() + "");
		
		TextView pointsText = (TextView) findViewById(R.id.points1);
		pointsText.setTypeface(myTypeface);

		
		TextView goldCount = (TextView) findViewById(R.id.gold1_val);
		goldCount.setTypeface(myTypeface);
		goldCount.setText(userInfo.getGold() + "");
		
		TextView goldText = (TextView) findViewById(R.id.gold1);
		goldText.setTypeface(myTypeface);
		
		TextView levelNo = (TextView) findViewById(R.id.level1_val);
		levelNo.setTypeface(myTypeface);
		levelNo.setText(userInfo.getLevelNo() + "");
		
		TextView levelText = (TextView) findViewById(R.id.level1);
		levelText.setTypeface(myTypeface);
		
		TextView groupDescription = (TextView) findViewById(R.id.groups1_val);
		groupDescription.setTypeface(myTypeface);
		groupDescription.setText(userInfo.getGroup() + ", " + userInfo.getRaceSlug());
		
		TextView groupText = (TextView) findViewById(R.id.groups1);
		groupText.setTypeface(myTypeface);
		
		weekQ = Toast.makeText(getApplicationContext(),
				"Sorry, no weekly quest!", Toast.LENGTH_SHORT);
		weekQ.setGravity(Gravity.CENTER, 0, 0);
		

		bazaarButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(bazaarMenu);
			}
		});

		chalButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(challMenu);
			}
		});

		msgButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(messageMenu);
			}
		});
		
		topsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(topsMenu);
			}
		});
		
		qotd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(qotdActivity);
			}
		});
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
		case R.id.map:
			startActivity(new Intent(this, GroupsMap.class));
			break;
		case R.id.search:
			onSearchRequested();
			return true;
		case R.id.logout:
			MainActivity.logOut(this);
		default:
			return true;
		}
			
			Toast myToast = Toast.makeText(this, toast, Toast.LENGTH_SHORT);
			myToast.setGravity(Gravity.CENTER, myToast.getXOffset() / 2,
				myToast.getYOffset() / 2);
			if(!toast.isEmpty())
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
