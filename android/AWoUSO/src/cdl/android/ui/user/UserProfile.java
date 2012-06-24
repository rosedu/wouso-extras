package cdl.android.ui.user;

import java.io.File;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.UserInfo;
import cdl.android.server.ChallengeHandler;
import cdl.android.server.GeneralHandler;

/**
 * Other users' profiles
 */
public class UserProfile extends Activity {
	SharedPreferences mPreferences;
	UserInfo userInfo;
	Button messageButton;
	Button spellButton;
	Button challengeButton;
	LinearLayout userInfoLayout;
	ImageView userAvatar;
	ImageView userLevelImage;
	TextView userName;
	TextView userPoints;
	TextView userRank;
	TextView userLevel;
	TextView userRace;
	TextView userGroup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_profile);

		final String username = getIntent().getExtras().getString("username");
		try {
			userInfo = GeneralHandler.getUserInfo(username);
		} catch (NullPointerException ex) {
			// User does not exist.
			Toast.makeText(getApplicationContext(),
					"Username is not in the database", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		// Get interface elements.
		messageButton = (Button) findViewById(R.id.usermsgbtn);
		spellButton = (Button) findViewById(R.id.userspellbtn);
		challengeButton = (Button) findViewById(R.id.userchalbtn);
		userInfoLayout = (LinearLayout) findViewById(R.id.usercontainer);
		userAvatar = (ImageView) findViewById(R.id.useravatar);
		userLevelImage = (ImageView) findViewById(R.id.userlevelimg);
		userName = (TextView) findViewById(R.id.username);
		userPoints = (TextView) findViewById(R.id.userpoints);
		userRank = (TextView) findViewById(R.id.userrank);
		userLevel = (TextView) findViewById(R.id.userlevel);
		userRace = (TextView) findViewById(R.id.userrace);
		userGroup = (TextView) findViewById(R.id.usergroup);

		// Set background for main user profile.
		File sdcard = Environment.getExternalStorageDirectory();
		File background = new File(sdcard + File.separator + "awouso"
				+ File.separator + "profiles", userInfo.getRace() + ".png");
		Bitmap backgroundBitmap = BitmapFactory.decodeFile(background
				.toString());
		@SuppressWarnings("deprecation")
		Drawable backgroundImage = new BitmapDrawable(backgroundBitmap);
		userInfoLayout.setBackgroundDrawable(backgroundImage);

		// Display user avatar.
		UserInfo.setAvatar(userAvatar, userInfo.getAvatarUrl());

		// Display user name.
		userName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());

		// Display user points.
		userPoints.setText(userInfo.getPoints() + "");

		// Display user rank.
		userRank.setText("rank: " + "wat?");

		// Display user level icon.
		File iconFile = new File(sdcard + File.separator + "awouso"
				+ File.separator + "levels", userInfo.getRace() + "-level-"
				+ userInfo.getLevelNo() + ".png");
		Bitmap iconBitmap = BitmapFactory.decodeFile(iconFile.toString());
		userLevelImage.setImageBitmap(iconBitmap);

		// Display user level.
		userLevel.setText("Level " + userInfo.getLevelNo());

		// Display user race.
		userRace.setText(userInfo.getRace());

		// Display user group.
		userGroup.setText(userInfo.getGroup());

		// TODO Add spell list.

		// Set buttons listeners.
		messageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO add send message action
				Toast.makeText(
						getApplicationContext(),
						"Send a message to " + userInfo.getFirstName() + " "
								+ userInfo.getLastName(), Toast.LENGTH_SHORT)
						.show();
			}
		});

		spellButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO add cast spell action
				Toast.makeText(
						getApplicationContext(),
						"Cast a spell on " + userInfo.getFirstName() + " "
								+ userInfo.getLastName(), Toast.LENGTH_SHORT)
						.show();
			}
		});

		challengeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ChallengeHandler.startChallenge(username); // Start a challenge
															// from logged
				// user to currently visited
				// user.
			}
		});
	}

}