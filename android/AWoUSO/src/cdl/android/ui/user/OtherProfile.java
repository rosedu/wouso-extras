package cdl.android.ui.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;

import cdl.android.R;
import android.support.v4.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.general.*;
import cdl.android.server.ApiHandler;
import cdl.android.ui.bazaar.SummaryFragment;

public class OtherProfile extends FragmentActivity{
	private Fragment fragment;
	private FragmentManager fm;
	private enum SpellInventoryState{
		SHOWN, HIDDEN;
	};
	private SpellInventoryState state = SpellInventoryState.HIDDEN;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.otherprofile);
		
		Bundle bundle = new Bundle();
		bundle.putInt("playerID", Integer.parseInt(getIntent().getExtras().getString("id")));
		fragment = new SummaryFragment(bundle);
		fm = this.getSupportFragmentManager();
		
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.other_summary, fragment);
		ft.hide(fragment);
		ft.commit();
		

		Intent intent = getIntent();
		final String id = (String) intent.getExtras().get("id");
		
		ServerResponse resp =  ApiHandler.get(ApiHandler.baseURL + "player/" + id + "/info/", this);
		
		final UserInfo userInfo = new UserInfo();
		
		if (resp.getStatus() == false) {
			Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
			return;
		} else{
			try {
				userInfo.parseContent(resp.getData());
			} catch (JSONException e) {
				Toast.makeText(this, "Server response format error.",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			int raceId = Integer.parseInt(userInfo.getRaceId());
			RelativeLayout panel = (RelativeLayout ) this.findViewById(R.id.panel);
			
			Log.d("raceId", ""+raceId );
			String serie;
			switch(raceId){
			
				case 1:	panel.setBackgroundResource(R.drawable.profiles_ca); serie = "ca"; break;
				case 2:	panel.setBackgroundResource(R.drawable.profiles_cb); serie = "cb"; break;
				case 3:	panel.setBackgroundResource(R.drawable.profiles_cc); serie = "cc"; break;
				default: panel.setBackgroundResource(R.drawable.profiles_cc); serie = "NaN"; break;
			
			}
			
			
			TextView nameField = (TextView) this.findViewById(R.id.name_field);
			nameField.setText(userInfo.getFirstName());
			//Display name
			
			ImageView avatar = (ImageView) this.findViewById(R.id.user_pic);
			userInfo.setAvatar(avatar, userInfo.getAvatarUrl(), 0);
			//Display avatar
			
			TextView scoreField = (TextView) this.findViewById(R.id.scorefield);
			scoreField.setText("" + userInfo.getPoints());
			//Display score
			
			TextView levelField = (TextView) this.findViewById(R.id.levelfield);
			levelField.setText("level "+userInfo.getLevelNo()+ " |");
			//Display level
			
			String levelPicName = "levels_" + serie + "_" + userInfo.getLevelNo();
			//Path to level icon
			ImageView levelPic = (ImageView) this.findViewById(R.id.levelpic);
			levelPic.setBackgroundResource(getResources().getIdentifier(levelPicName, "drawable", "cdl.android"));
			//Display level icon
			
			TextView raceSlugField = (TextView) this.findViewById(R.id.raceslugfield);
			raceSlugField.setText(serie.toUpperCase());
			
			TextView groupField = (TextView) this.findViewById(R.id.groupfield);
			groupField.setText(userInfo.getGroup());
			
			ImageView usermsgbtn = (ImageView) this.findViewById(R.id.usermsgbtn);
			
			final Context context = this;
			usermsgbtn.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, OtherMessage.class);

					intent.putExtra("username", userInfo.getUserame());
					startActivity(intent);
				}});
			
			ImageView castSpellBtn = (ImageView ) this.findViewById(R.id.userspellbtn);
			castSpellBtn.setOnClickListener(new View.OnClickListener(){
				
				@Override
				public void onClick(View v) {
					if(state == SpellInventoryState.HIDDEN){
						state = SpellInventoryState.SHOWN;
						System.out.println("smthing");
						FragmentTransaction ft = fm.beginTransaction();
						ft.show(fragment);
						ft.commit();
					} else {
						state = SpellInventoryState.HIDDEN;
						FragmentTransaction ft = fm.beginTransaction();
						ft.hide(fragment);
						ft.commit();
					}
					 
				}
			});
		}
	}
	
	
}