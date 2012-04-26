package cdl.android.ui;

import android.app.Activity;
import android.content.Intent;
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
        
        final Intent bazaarMenu = new Intent(this, Tabs.class);
        Button bazaarButton = (Button) findViewById(R.id.shopbtn);

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
	}	
	
}
