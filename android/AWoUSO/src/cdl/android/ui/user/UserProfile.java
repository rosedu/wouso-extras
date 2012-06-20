package cdl.android.ui.user;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.model.Qotd;
import cdl.android.model.UserInfo;
import cdl.android.server.ApiRequests;
import cdl.android.server.Auth;
import cdl.android.ui.bazaar.BazaarTabs;

/** 
 * Other users' profiles
 */
public class UserProfile extends Activity {
	SharedPreferences mPreferences;
	UserInfo userInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_profile);
        
        Button messageButton = (Button) findViewById(R.id.usermsgbtn);
        Button spellButton = (Button) findViewById(R.id.userspellbtn);
        Button challengeButton = (Button) findViewById(R.id.userchalbtn);
        LinearLayout userInfoLayout = (LinearLayout) findViewById(R.id.usercontainer);
        
        
        File sdcard = Environment.getExternalStorageDirectory();
        File background = new File(sdcard + File.separator + "awouso" + File.separator +
        		"profiles", "CA.png");
        Bitmap backgroundBitmap = BitmapFactory.decodeFile(background.toString());
        System.err.println(background);
        @SuppressWarnings("deprecation")
		Drawable backgroundImage = new BitmapDrawable(backgroundBitmap);
        userInfoLayout.setBackgroundDrawable(backgroundImage);
        
        messageButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			Toast.makeText(getApplicationContext(), "Send a message to ", Toast.LENGTH_SHORT).show();
    		}
    	});
        
        spellButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			Toast.makeText(getApplicationContext(), "Cast a spell on ", Toast.LENGTH_SHORT).show();
    		}
    	});
        
        challengeButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			Toast.makeText(getApplicationContext(), "Challenge ", Toast.LENGTH_SHORT).show();
    		}
    	});
	}	

}