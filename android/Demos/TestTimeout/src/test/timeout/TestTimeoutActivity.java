package test.timeout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestTimeoutActivity extends Activity {
	long mStartTime = 0;
	TextView mTimeLabel;
	Handler mHandler = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTimeLabel = (TextView) findViewById(R.id.timeLabel);
		mHandler = new Handler();

		final Runnable mUpdateTimeTask = new Runnable() {
			public void run() {
				mStartTime--;
				int seconds = (int) (mStartTime);
				int minutes = seconds / 60;
				seconds = seconds % 60;

				System.out.println("unuuu");
				mTimeLabel.setText("" + minutes + ":" + seconds);
				mHandler.removeCallbacks(this);
				mHandler.postDelayed(this, 1000);
			}
		};

		OnClickListener mStartListener = new OnClickListener() {
			public void onClick(View v) {
				mStartTime = 60;
				System.out.println("staaart " + mStartTime);
				mHandler.removeCallbacks(mUpdateTimeTask);
				mHandler.postDelayed(mUpdateTimeTask, 1000);
			}
		};
		Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(mStartListener);

		OnClickListener mStopListener = new OnClickListener() {
			public void onClick(View v) {
				mHandler.removeCallbacks(mUpdateTimeTask);
			}
		};
		Button stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(mStopListener);
	}

}