package cdl.android.ui.map;

import org.json.JSONArray;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cdl.android.R;
import cdl.android.general.ServerResponse;
import cdl.android.server.ApiHandler;

public class Race extends FragmentActivity{
	public RaceEnum crtRace;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.races);
		
		String raceId = null; 
	    String serie = null;
	    String members = null;
	    Bundle extras = getIntent().getExtras();
	    if (extras != null){
	       raceId = extras.getString("raceId");
	        	
	       //get Members
	       ServerResponse resp = ApiHandler.getArray(
	   			ApiHandler.raceURL + raceId + "/members/", this);
        	
	       //determinarea seriei
	       if (raceId.equals("2")){
	       	serie = "CA";
	       }
	       else if (raceId.equals("3")){
	      		serie = "CB";
	       	} else serie = "CC";
	       	
	    	if (resp.getStatus() == false) {
	    		Toast.makeText(this, resp.getError(), Toast.LENGTH_SHORT).show();
	   		} else {
	   			JSONArray arr = resp.getArrayData();
				members = Integer.toString(arr.length());
	   		}
	   	}
	    				
	    TextView group = (TextView) findViewById(R.id.serie);
	    TextView numbers = (TextView) findViewById(R.id.numberOfMembers);
	    		
	   	group.setText(serie);
	   	numbers.setText(members);
	   	
	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		ft.add(R.id.races_content, new Members());
		
		crtRace = RaceEnum.Members;
		Resources r = this.getResources();
		((Button)this.findViewById(R.id.race_members)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_selected));
		((Button)this.findViewById(R.id.race_groups)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		Log.d("ceva", ft.toString());
		try{
			ft.commit();
		} catch (NullPointerException e){
		}
		
	}
	
	
	public void  setAllUnselected(){
		Resources r = this.getResources();
		((Button)this.findViewById(R.id.race_members)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
		((Button)this.findViewById(R.id.race_groups)).setBackgroundDrawable(r.getDrawable(R.drawable.round_tab_notselected));
	}
	
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
					//TODO Groups and replace the Members 
					ft.replace(R.id.races_content, new Members());
					ft.commit();
				
					break;
			
			default:break;
			
		}
	}
}