package cdl.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;

public class MainMenu extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainmenu);

        final TextView userProfile = (TextView) findViewById(R.id.profileName);
        userProfile.setText(getIntent().getExtras().getString("userName"));
        
        final TextView pointsCount = (TextView) findViewById(R.id.points);
        pointsCount.setText("420");
        final TextView goldCount = (TextView) findViewById(R.id.gold);
        goldCount.setText("0");
        
        final ImageView playerLevel = (ImageView) findViewById(R.id.level);
        playerLevel.setImageResource(R.drawable.levelex);

        Button specialQuest = (Button) findViewById(R.id.spcQbtn);

        final Toast hello = Toast.makeText(getApplicationContext(), "Sorry, no special quest!", Toast.LENGTH_SHORT);
        hello.setGravity(Gravity.CENTER, 0, 0);

        specialQuest.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				hello.cancel();
				hello.show();

			}
		});

	}

}
