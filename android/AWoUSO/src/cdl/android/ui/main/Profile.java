package cdl.android.ui.main;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.Qotd;
import cdl.android.general.UserInfo;
import cdl.android.server.HTTPResultReceiver;
import cdl.android.server.HTTPService;
import cdl.android.ui.bazaar.BazaarTabs;
import cdl.android.ui.challenge.menu.ChallengeMenu;
import cdl.android.ui.map.GroupsMap;
import cdl.android.ui.message.MessageTabs;

/**
 * User's profile and main application menu
 */
public class Profile extends Activity implements HTTPResultReceiver.Receiver {
	public HTTPResultReceiver mReceiver;
	ProgressDialog prog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		prog = new ProgressDialog(this);
		mReceiver = new HTTPResultReceiver(new Handler());
		mReceiver.setReceiver(this);

		setContentView(R.layout.main);
		getUserInfo();
	}

	/** 
	 * Fills Activity Views 
	 */
	public void initProfile(UserInfo userInfo) {
		ImageView userLevelImage = (ImageView) findViewById(R.id.level);
		File iconFile = new File("/mnt/sdcard" + File.separator + "awouso"
				+ File.separator + "levels", userInfo.getRace() + "-level-"
				+ userInfo.getLevelNo() + ".png");
		Bitmap iconBitmap = BitmapFactory.decodeFile(iconFile.toString());
		userLevelImage.setImageBitmap(iconBitmap);

		TextView userProfile = (TextView) findViewById(R.id.profileName);
		userProfile.setText(userInfo.getFirstName() + " "
				+ userInfo.getLastName());

		TextView pointsCount = (TextView) findViewById(R.id.points);
		pointsCount.setText(userInfo.getPoints() + "");

		TextView goldCount = (TextView) findViewById(R.id.gold);
		goldCount.setText(userInfo.getGold() + "");

		TextView levelNo = (TextView) findViewById(R.id.levelNo);
		String level = "Level " + userInfo.getLevelNo() + " -";
		levelNo.setText(level);

		// TODO: currently just a displays test, update this with real spells from server
		LinearLayout hs = (LinearLayout) findViewById(R.id.group_list);
		for (int i = 0; i < 4; i++) {
			ImageView spell = new ImageView(this);
			spell.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			spell.setBackgroundResource(R.drawable.spell_blue);
			hs.addView(spell);
		}

		ImageView avatar = (ImageView) findViewById(R.id.profileImage);
		UserInfo.setAvatar(avatar, userInfo.getAvatarUrl());

		ProgressBar mProgress = (ProgressBar) findViewById(R.id.vertical_progressbar);
		mProgress.setProgress((int) userInfo.getLevelPercent());

		final Intent bazaarMenu = new Intent(this, BazaarTabs.class);
		final Intent challMenu = new Intent(this, ChallengeMenu.class);
		final Intent messageMenu = new Intent(this, MessageTabs.class);
		Button bazaarButton = (Button) findViewById(R.id.shopbtn);
		Button challButton = (Button) findViewById(R.id.chalbtn);
		Button qotdButton = (Button) findViewById(R.id.qotdbtn);
		Button specialQuest = (Button) findViewById(R.id.spcQbtn);
		Button msgButton = (Button) findViewById(R.id.msgbtn);

		final Toast weekQ = Toast.makeText(getApplicationContext(),
				"Sorry, no weekly quest!", Toast.LENGTH_SHORT);
		weekQ.setGravity(Gravity.CENTER, 0, 0);
		specialQuest.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				weekQ.show();
			}
		});

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

		msgButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(messageMenu);
			}
		});

		qotdButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getQotd();
			}
		});

	}

	/**
	 * Displays Question of the Day
	 */
	public void launchQOTD(final Qotd qotd) {
		Boolean ans = qotd.hadAnswered();
		if (ans == false) {
			/** Launch QOTD */
			final CharSequence[] items = new String[qotd.getAnswers().size()];
			for (int i = 0; i < qotd.getAnswers().size(); i++) {
				items[i] = qotd.getAnswers().get(i);
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.androidicon_small);
			builder.setTitle(qotd.getQuestion());
			builder.setSingleChoiceItems(items, -1,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int item) {
							postQotd(qotd.getKeys().get(item));
							dialog.dismiss();
						}
					});

			AlertDialog alert = builder.create();
			alert.show();

		} else {
			Toast.makeText(getApplicationContext(), "You already answered!",
					Toast.LENGTH_SHORT).show();
		}
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
		myToast.show();
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mReceiver.setReceiver(null); // avoid mem leaks
	}

	public void getUserInfo() {
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
				HTTPService.class);
		intent.putExtra("receiver", mReceiver);
		intent.putExtra("command", HTTPService.USER_INFO);
		startService(intent);
	}
	
	public void getQotd() {
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
				HTTPService.class);
		intent.putExtra("receiver", mReceiver);
		intent.putExtra("command", HTTPService.GET_QOTD);
		startService(intent);
	}
	
	public void postQotd(String answer) {
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
				HTTPService.class);
		intent.putExtra("receiver", mReceiver);
		intent.putExtra("answer", answer);
		intent.putExtra("command", HTTPService.POST_QOTD);
		startService(intent);
	}

	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
		case HTTPService.STATUS_RUNNING: // show progress
			prog.show();
			break;
		case HTTPService.STATUS_ERROR_NOTIF: // error or simple notification
			prog.dismiss();
			Toast.makeText(this, resultData.getString(Intent.EXTRA_TEXT),
					Toast.LENGTH_SHORT).show();
			break;
		case HTTPService.STATUS_FINISHED1: // finished get user info
			prog.dismiss();
			UserInfo userInfo = resultData.getParcelable("results");
			setContentView(R.layout.mainmenu);
			initProfile(userInfo);
			break;
		case HTTPService.STATUS_FINISHED2: // finished get qotd 
			prog.dismiss();
			Qotd qotd = resultData.getParcelable("results");
			launchQOTD(qotd);
			break;
		}
	}
}
