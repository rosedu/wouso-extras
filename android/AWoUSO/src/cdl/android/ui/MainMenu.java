package cdl.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import cdl.android.R;

public class MainMenu extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainmenu);

        Button specialQuest = (Button) findViewById(R.id.spcQbtn);

        specialQuest.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast hello = Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT);
				hello.show();

			}
		});
	}

}
