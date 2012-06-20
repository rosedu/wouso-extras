package cdl.android.ui.user;

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

	}	

}