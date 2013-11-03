package cdl.android.ui.map;

import org.json.JSONArray;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class Race extends FragmentActivity{
	public RaceEnum crtRace;
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.races);
		
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.race_profile);
		
		String raceId = null; 
	    String serie = null;
	    String members = null;
	    String groups = null;
	    Bundle extras = getIntent().getExtras();
	    if (extras != null){
	       raceId = extras.getString("raceId");
	        	
	       //get Members
	       ServerResponse resp = ApiHandler.getArray(
	   			ApiHandler.raceURL + raceId + "/members/", this);
	       
	       ServerResponse resp2 = ApiHandler.getArray(
		   			ApiHandler.raceURL + raceId + "/groups/", this);
        	
	       //determinarea seriei
	       if (raceId.equals("2")){
	       	serie = "CA";
	       	rl.setBackgroundResource(R.drawable.profiles_ca);
	       }
	       else if (raceId.equals("3")){
	      		serie = "CB";
	      		rl.setBackgroundResource(R.drawable.profiles_cb);
	       	} else { serie = "CC";
	       			 rl.setBackgroundResource(R.drawable.profiles_cc);
	       	}
	       	
	    	if (resp.getStatus() == false) {
	    		Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
	   		} else {
	   			JSONArray arr = resp.getArrayData();
				members = Integer.toString(arr.length());
	   		}
	    	
	    	if (resp2.getStatus() == false) {
	    		Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
	   		} else {
	   			JSONArray arr = resp2.getArrayData();
				groups = Integer.toString(arr.length());
	   		}
	   	}
	    				
	    TextView group = (TextView) findViewById(R.id.serie);
	    TextView numbers = (TextView) findViewById(R.id.numberOfMembers);
	    TextView memberNumbers = (TextView) findViewById(R.id.numberOfGroups);
	    		
	   	group.setText(serie);
	   	numbers.setText(members);
	   	memberNumbers.setText(groups);
	   	
	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		ft.replace(R.id.races_content, new Members());
		
		crtRace = RaceEnum.Members;
		Resources r = this.getResources();
		((Button)this.findViewById(R.id.race_members)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
		((Button)this.findViewById(R.id.race_groups)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		
		ft.commit();
	}
	
	
	@SuppressWarnings("deprecation")
	public void  setAllUnselected(){
		Resources r = this.getResources();
		((Button)this.findViewById(R.id.race_members)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		((Button)this.findViewById(R.id.race_groups)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
	}
	
	@SuppressWarnings("deprecation")
	public void changeFragmentTo(RaceEnum race){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Resources r = this.getResources();
		
		switch(race){
		
			case Members:
					setAllUnselected();
					((Button)this.findViewById(R.id.race_members)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
					ft.replace(R.id.races_content, new Members());
					ft.commit();
					
					break;
			
			case Groups:
					setAllUnselected();
					((Button)this.findViewById(R.id.race_groups)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
					ft.replace(R.id.races_content, new Groups());
					ft.commit();
				
					break;
			
			default:break;
			
		}
	}
}